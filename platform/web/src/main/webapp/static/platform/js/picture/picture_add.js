
function article_save_submit(){
    $("#edit_form").ajaxSubmit(function (result) {
        var code = result.responseCode;
        var msg = result.responseMessage;
        if (code == '1') {
            layer.msg("提交成功！");
            setTimeout(function(){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },1000);
        } else {
            layer.msg("提交失败！错误代码为："+code+",错误原因："+msg);
        }
    });
}
$("#name").change(function(){
    var objUrl = getObjectURL(this.files[0]) ;
    console.log("objUrl = "+objUrl) ;
    if (objUrl)
    {
        $("#img0").attr("src", objUrl);
        $("#img0").removeClass("hide");
    }
}) ;
//建立一個可存取到該file的url
function getObjectURL(file)
{
    var url = null ;
    if (window.createObjectURL!=undefined)
    { // basic
        url = window.createObjectURL(file) ;
    }
    else if (window.URL!=undefined)
    {
        // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    }
    else if (window.webkitURL!=undefined) {
        // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}
