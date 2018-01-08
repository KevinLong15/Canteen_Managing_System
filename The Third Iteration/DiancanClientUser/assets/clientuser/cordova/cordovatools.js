function exitApp(){
    navigator.app.exitApp();
}
/****************文件上传****************/
//cordovaFlag是否有phonegap环境，从而决定是否调用本地方法
var cordovaFlag = false;
var uploadFileUrl = null;
document.addEventListener("deviceready", onDeviceReady, false);
var pictureSource; //  getPicture:数据来源参数的一个常量
var destinationType; // getPicture中：设置getPicture的结果类型
function onDeviceReady() {
    pictureSource = navigator.camera.PictureSourceType;
    destinationType = navigator.camera.DestinationType;
    cordovaFlag = true;
}

function getFilePic(source, element) {
    navigator.camera.getPicture(function (imageURI) {
        uploadFileUrl = imageURI;
        $("#" + element).attr("src", imageURI);
    }, function () {
        if (source == pictureSource.CAMERA)
            console.log('加载照相机出错!');
        else
            console.log('加载相册出错!');
    }, {
        quality: 50,
        destinationType: destinationType.FILE_URI,
        sourceType: source
    });
}

/**
 * 上传意外终止处理。
 * @param message
 */
function uploadBroken(message) {
    alert(message);
};

/**
 * 上传过程回调，用于处理上传进度，如显示进度条等。
 */
function uploadProcessing(progressEvent) {

    if (progressEvent.lengthComputable) {
        //已经上传
        var loaded = progressEvent.loaded;
        //文件总长度
        var total = progressEvent.total;
        //计算百分比，用于显示进度条
        var percent = parseInt((loaded / total) * 100);
        showLoader("正在上传文件:" + percent + "%");
        //换算成MB
        //loaded=(loaded/1024/1024).toFixed(2);
        //total=(total/1024/1024).toFixed(2);
    }
};

/**
 * 选择文件后回调上传。
 */
function uploadFile(fileURI, url, success, fail) {
    var options = new FileUploadOptions();
    options.fileKey = "file";
    options.fileName = fileURI.substr(fileURI.lastIndexOf('/') + 1);
    options.mimeType = "multipart/form-data";
    options.chunkedMode = false;
    ft = new FileTransfer();
    var uploadUrl = encodeURI(url);
    console.log(fileURI);
    ft.upload(fileURI, uploadUrl, success, fail, options);
    //获取上传进度
    ft.onprogress = uploadProcessing;
}

/**
 * 上传成功回调.
 * @param r
 */
function uploadSuccess(r) {
    alert('文件上传成功:' + r.response);
}

/**
 * 上传失败回调.
 * @param error
 */
function uploadFailed(error) {
    alert('上传失败了。');
}

/****************文件上传结束****************/
function openJisuanqi(){
    cordova.exec(function(data){

    }, null, "Plugs", "jisuanqi", []);
}


function toBaidu(){
	var city = $("#city").val() || "深圳";
	var str = $("#str").val() ||  "酒店";
    cordova.exec(function(){},function(){},"Plugs","tobaidu",[{city: city,str:str}]);
}

function toDail(el){
    var tel = $(el).text();
    tel = tel.split(":")[1];
    cordova.exec(function(){},function(){},"Plugs","toDail",[{tel:tel}]);
}

function toAddress(el){
    var address = $(el).text();
    address = address.split(":")[1];
    cordova.exec(function(){},function(){},"Plugs","toAddress",[{address:address}]);
}
