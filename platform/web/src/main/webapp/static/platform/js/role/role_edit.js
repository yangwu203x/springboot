$.validator.setDefaults({
    submitHandler: function() {
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
});
$().ready(function() {
    $("#edit_form").validate({
    });
});
// function roleForm(){
//     var status = false;
//
//     //角色描述
//     var description = $("#description").val();
//
//     //角色名并且验证不能为空
//     var roleName = $("#roleName").val();
//     if(roleName == ''){
//         layer.msg("角色名不能为空!");
//         return false;
//     }
//
//     //获取id
//     var id = $("#id").val();
//
//     //封装数据
//     var requestData = {"roleName":roleName,"description":description,"id":id}
//
//     //获取请求路径
//     var form = $("#form-admin-role-add");
//     var url = form.attr("action");
//     $.ajax({
//         url: url,
//         data:requestData,
//         type:"post",
//         async:false,
//         success: function(result){
//             status = result;
//         }
//     });
//     if(status){
//         var index  =  parent.layer.getFrameIndex(window.name);
//         parent.layer.close(index);
//     }else{
//         return false;
//     }
// }