
function uplaodImg(callback){
    if(uploadFileUrl){
        uploadFile(uploadFileUrl,rootUrl+"trip/UploadServlet",function(r){
            var img = r.response;
            callback && callback(img);
        },function(error){
            showLoader(error,true);
        });
    }else{
        showLoader("请选择图片!",true);
    }
}