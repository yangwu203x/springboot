$.validator.setDefaults({
    submitHandler: function() {

        var status = false;
        var id = $("#id").val();
        var username = $("#username").val();
        var password = $("#password").val();

        var status = $("#status").val();
        if(password != ""){
            var password2 = $("#password2").val();
            if(password.length < 6 ){
                layer.msg('密码至少需要6位数！', {icon: 5, time: 1000});
                return false;
            }
            if(password!=password2){
                layer.msg('两次密码不相同，请重新输入！', {icon: 5, time: 1000});
                return false;
            }
        }

        //获取请求路径
        var form=$("#form-account-add");
        var url=form.attr("action");
        var requestData = {"username":username,"password":password,"status":status,"id":id};
        $.ajax({
            url: url,
            data: requestData,
            type:"post",
            async:false,
            success: function(result){
                status=result;
            }
        });
        if(status){
            var index = parent.layer.getFrameIndex(window.name);
            layer.msg('操作成功！', {icon: 1, time: 1000});
            parent.layer.close(index);
        }else{
            layer.msg('error!', {icon: 5, time: 1000});
            return false;
        }
    }
});
$().ready(function() {
    // 在键盘按下并释放及提交后验证提交表单
    $("#form-account-add").validate({
        rules: {
            username: {
                required: true,
                minlength: 4
            },
            // password: {
            //     required: true,
            //     minlength: 6
            // },
            // password2: {
            //     required: true,
            //     minlength: 6,
            //     equalTo: "#password"
            // },
            status: "required"
        },
        messages: {
            username: {
                required: "请输入登录名",
                minlength: "用户名必需大于四个长度的字符"
            },
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于6个字符"
            },
            password2: {
                required: "请输入密码",
                minlength: "密码长度不能小于6个字符",
                equalTo: "两次密码输入不一致"
            },
            status: "选择账户状态",
        },
        remote: {
            url: "/account/getAccountByUsername",     //后台处理程序
            type: "post",               //数据发送方式
            dataType: "json",           //接受数据格式
            data: {                     //要传递的数据
                username: function() {
                    return $("#username").val();
                }
            }
        }
    });
});