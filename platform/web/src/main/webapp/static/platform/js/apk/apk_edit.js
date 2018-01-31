$.validator.setDefaults({
    submitHandler: function() {
        $("#edit_form").ajaxSubmit(function (result) {
            var code = result.responseCode;
            var msg = result.responseMessage;
            console.log(result);
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
});
$().ready(function() {
    $("#edit_form").validate({
    });
});

$(function(){
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
});