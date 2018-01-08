
var shoplist = [];
var goodlist = [];
var focusobj = null;
var focushop = null;
var gouwuche = "gouwuche";

$(function(){
//设置类别列表
    var p = {};
    p.tpl = '<li><a href="#" onclick="toShop(%s);">'+
                '<img src="'+fileurl+'%s" class="ui-li-thumb">'+
                '<h2>%s</h2>'+
                '<p>%s</p>'+
            '</a></li>';
    p.colums = ["id","img","sname","note"];
    $("#shops").data("property",JSON.stringify(p));

    var p2 = {};
    p2.tpl = '<li><a href="#" onclick="toGood(%s);">'+
        '<img src="'+fileurl+'%s" class="ui-li-thumb">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '<p style="color: red;">%s 元</p>'+
        '</a></li>';
    p2.colums = ["id","img","gname","note","price"];
    $("#goods").data("property",JSON.stringify(p2));

    var p3 = {};
    p3.tpl = '<li>'+
        '<img src="'+fileurl+'%s" class="ui-li-thumb">'+
        '<h2>%s</h2>'+
        '<p style="color: red;">%s 元</p>'+
        '<p class="ui-li-aside"><a href="#" onclick="removeCar(%s);" style="font-size: 20px;text-decoration:none;">删除</a></p>'+
        '</li>';
    p3.colums = ["img","gname","price","id"];
    $("#cars").data("property",JSON.stringify(p3));

    var p4 = {};
    p4.tpl = '<li><a href="#" onclick="billDetail(%s);">'+
        '<h2>%s</h2>'+
        '<p>%s</p>'+
        '<p style="color: red;">总价:%s</p>'+
        '<p>电话:%s</p>'+
        '<p>地址:%s</p>'+
        '</a></li>';
    p4.colums = ["id","ndate","gnames","total","tel","address"];
    $("#bills").data("property",JSON.stringify(p4));

});

function toMain(){
    changePage("mainpage");
    listShop();
}

function listShop(){
    ajaxCallback("listShop",{},function(data){
        shoplist = data;
        $("#shops").refreshShowListView(data);
    });
}

function toShop(id){
    for(var i=0;i<shoplist.length;i++){
        if(shoplist[i].id == id){
            focushop = shoplist[i];
            break;
        }
    }
    changePage("goodspage");
    listGood(id);
}

function listGood(id){
    ajaxCallback("listGood",{sid:id},function(data){
        goodlist = data;
        $("#goods").refreshShowListView(data);
    });
}

function toGood(id){
    var obj = getGoodById(id);
    focusobj = obj;
    changePage("goodpage");
    $("#gname2").text("菜名:"+obj.gname);
    $("#gimg2").attr("src",fileurl+obj.img);
    $("#gnote2").text("菜品简介:"+obj.note);
    $("#gprice2").text("价格:"+obj.price);
}

function getGoodById(id){
    for(var i=0;i<goodlist.length;i++){
        var good = goodlist[i];
        if(good.id == id){
            return good;
        }
    }
    return null;
}

function tijiaouser(){
    var note = $("#infobeizhu2").val();
    var bill = {};
    bill.uid = userinfo.id;
    bill.user = userinfo.username;
    bill.shop = focushop.sname;
    bill.sid = focushop.id;
    bill.gids = focusobj.id;
    bill.gnames = focusobj.gname;
    bill.total = focusobj.price;
    bill.tel = userinfo.tel;
    bill.address = userinfo.address;
    bill.note = note;
    ajaxCallback("saveBill",bill,function(){
        showLoader("订单提交成功!",true);
        showTipTimer("订单提交成功!",function(){
            toMyBill();
        });

    });
}

function tijiao(){
    if(userinfo){
        changePage("infopage2");
        $("#iscar2").val("1");
    }else{
        changePage("infopage");
        $("#iscar").val("1");
    }
}

function tijiaoyouke(){
    var tel = $("#infotel").val();
    var address = $("#infoaddress").val();
    var note = $("#infobeizhu").val();
    if($.trim(tel)=="" || $.trim(address)==""){
        showLoader("请填写电话和地址信息!",true);
        return;
    }
    if(tel.length<11){
        showLoader("请填写正确的电话号码!",true);
        return;
    }
    var bill = {};
    bill.shop = focushop.sname;
    bill.sid = focushop.id;
    bill.gids = focusobj.id;
    bill.gnames = focusobj.gname;
    bill.total = focusobj.price;
    bill.tel = tel;
    bill.address = address;
    bill.note = note;
    ajaxCallback("saveBill",bill,function(){
        showLoader("订单提交成功!",true);
        showTipTimer("订单提交成功!",function(){
            toMyBill();
        });

    });
}

function youketijiao(){
    var type = $("#iscar").val();
    if(type == 1){
        tijiaoyouke();
    }else{
        tijiaocaryouke();
    }
}

function usertijiao(){
    var type = $("#iscar2").val();
    if(type == 1){
        tijiaouser()
    }else{
        tijiaocaruser();
    }
}

function addToCar(){
    var str = localStorage[gouwuche];
    var list = [];
    if(str){
        list = JSON.parse(str);
    }
    list.push(focusobj);
    localStorage[gouwuche] = JSON.stringify(list);
    showLoader("已经添加到购物车!",true);
}

function showCar(){
    changePage("carspage");
    carlist();
}

function carlist(){
    var str = localStorage[gouwuche];
    var list = [];
    if(str){
        list = JSON.parse(str);
    }
    $("#cars").refreshShowListView(list);
}

function removeCar(id){
    var str = localStorage[gouwuche];
    var list = [];
    var newlist = [];
    if(str){
        list = JSON.parse(str);
        for(var i=0;i<list.length;i++){
            var obj = list[i];
            if(obj.id == id){
                continue;
            }
            newlist.push(obj);
        }
        localStorage[gouwuche] = JSON.stringify(newlist);
        $("#cars").refreshShowListView(newlist);
    }
}

function tijiaocar(){
    if(userinfo){
        changePage("infopage2");
        $("#iscar2").val("2");
    }else{
        changePage("infopage");
        $("#iscar").val("2");
    }
}

function tijiaocaruser(){
    var note = $("#infobeizhu2").val();
    var str = localStorage[gouwuche];
    var sids = [];
    var shopgoods = {};
    var bills = [];
    if(str){
        var list = JSON.parse(str);
        for(var i=0;i<list.length;i++){
            var flag = false;
            var good = list[i];
            for(var n=0;n<sids.length;n++){
                if(sids[n]==good.sid){
                    shopgoods[good.sid].push(good);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                shopgoods[good.sid] = [];
                shopgoods[good.sid].push(good);
                sids.push(good.sid);
            }
        }
    }

    for(var i=0;i<sids.length;i++){
        var goodlist = shopgoods[sids[i]];
        var gids = "";
        var gnames = "";
        var sname = "";
        var total = 0;
        var sid = sids[i];
        var bill = {};
        bill.uid = userinfo.id;
        bill.user = userinfo.username;
        for(var n=0;n<goodlist.length;n++){
            var good = goodlist[n];
            if(n==0){
                sname = good.shop;
                gids+=good.id;
                gnames+=good.gname;
            }else{
                gids+=","+good.id;
                gnames+=","+good.gname;
            }
            total+=parseInt(good.price);
        }
        bill.shop = sname;
        bill.sid = sid;
        bill.gids = gids;
        bill.gnames = gnames;
        bill.total = total;
        bill.tel = userinfo.tel;
        bill.address = userinfo.address;
        bill.note = note;
        bills.push(bill);
    }
    if(bills.length){
        ajaxCallback("saveBills",{bills:JSON.stringify(bills)},function(data){
            localStorage[gouwuche] = "";
            showTipTimer("订单提交成功!",function(){
                toMyBill();
            });
        });
    }

}

function tijiaocaryouke(){
    var tel = $("#infotel").val();
    var address = $("#infoaddress").val();
    var note = $("#infobeizhu").val();
    if($.trim(tel)=="" || $.trim(address)==""){
        showLoader("请填写电话和地址信息!",true);
        return;
    }
    if(tel.length<11){
        showLoader("请填写正确的电话号码!",true);
        return;
    }
    var str = localStorage[gouwuche];
    var sids = [];
    var shopgoods = {};
    var bills = [];
    if(str){
        var list = JSON.parse(str);
        for(var i=0;i<list.length;i++){
            var flag = false;
            var good = list[i];
            for(var n=0;n<sids.length;n++){
                if(sids[n]==good.sid){
                    shopgoods[good.sid].push(good);
                    flag = true;
                    break;
                }
            }
            if(!flag){
                shopgoods[good.sid] = [];
                shopgoods[good.sid].push(good);
                sids.push(good.sid);
            }
        }
    }

    for(var i=0;i<sids.length;i++){
        var goodlist = shopgoods[sids[i]];
        var gids = "";
        var gnames = "";
        var sname = "";
        var total = 0;
        var sid = sids[i];
        var bill = {};
        bill.uid = "";
        bill.user = "";
        for(var n=0;n<goodlist.length;n++){
            var good = goodlist[n];
            if(n==0){
                sname = good.shop;
                gids+=good.id;
                gnames+=good.gname;
            }else{
                gids+=","+good.id;
                gnames+=","+good.gname;
            }
            total+=parseInt(good.price);
        }
        bill.shop = sname;
        bill.sid = sid;
        bill.gids = gids;
        bill.gnames = gnames;
        bill.total = total;
        bill.tel = tel;
        bill.address = address;
        bill.note = note;
        bills.push(bill);
    }
    if(bills.length){
        ajaxCallback("saveBills",{bills:JSON.stringify(bills)},function(data){
            localStorage[gouwuche] = "";
            showTipTimer("订单提交成功!",function(){
                toMyBill();
            });
        });
    }

}



function toMyBill(){
    if(userinfo){
        changePage("mybillpage");
        mybillslist();
    }else{
        changePage("loginpage");
    }

}

function mybillslist(){
    ajaxCallback("mybills",{uid:userinfo.id},function(data){
        $("#bills").refreshShowListView(data);
    });
}

function billDetail(id){
    var obj = getGoodById(12);
    focusobj = obj;
    changePage("goodpage");

    $("#gname2").text("菜名:"+obj.gname);
    $("#gimg2").attr("src",fileurl+obj.img);
    $("#gnote2").text("菜品简介:"+obj.note);
    $("#gprice2").text("价格:"+obj.price);
}