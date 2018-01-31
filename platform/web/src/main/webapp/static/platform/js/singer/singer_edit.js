//初始化填充值
$().ready(function() {
    //歌曲编号不能修改
   /* var songId = $("#singerNo").val();
    if(songId.length > 0){
        $("#singerNo").attr("readonly","readonly");
    }*/
    //校验表单
    $("#edit_form").validate({
        rules:{singerType:{min:0}},
        messages:{singerType:{ min:'必选'}}
    });
});
$.validator.setDefaults({
    submitHandler: function () {
        $("#edit_form").ajaxSubmit(function (result) {

            if (result.responseCode == '1') {
                layer.msg("提交成功!");
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                layer.msg("提交失败!原因为："+result.responseMessage);
            }
        });
    }
});
// function requestForm(){
//     var status=false;
//     var formData=new FormData();
//     var singerId=$("#singer_id").val();
//     if(singerId==null || singerId==""){
//         layer.msg("请输入歌星的编号!");
//         return false;
//     }
//     formData.append("singerId",singerId);
//     var singerName=$("#singer_name").val();
//     if(singerName==null || singerName==""){
//         layer.msg("请输入歌星的名字!");
//         return false;
//     }
//     formData.append("singerName",singerName);
//     var	singerPinyin=$("#singer_pinyin").val();
//     if(singerPinyin==null || singerPinyin==""){
//         layer.msg("请输入歌星的拼音!");
//         return false;
//     }
//     formData.append("singerPinyin",singerPinyin);
//     var	singerType=$("#singer_type").val();
//     if(singerType==-1){
//         layer.msg("请选择歌星的类型!");
//         return false;
//     }
//     formData.append("singerType",singerType);
//
//     var headerFile=$("#headerFile")[0].files[0];
//     if(document.getElementById("header_path").value==""){
//         if(headerFile==null){
//             layer.msg("请选择歌星的头像!");
//             return false;
//         }
//     }
//     formData.append("headerFile",headerFile);
//
//     var singerVersion=document.getElementById("singer_version").value;
//     if(singerVersion==null||singerVersion==''){
//         layer.msg("请输入歌手版本!");
//         return false;
//     }
//     formData.append("singerVersion",singerVersion);
//     //获取请求路径
//     var form=$("#edit_form");
//     var url=form.attr("action");
//     if(headerFile!=undefined){
//         $.ajax({
//             url: url,
//             data: formData,
//             type:"post",
//             async:false,
//             processData: false,
//             contentType: false,
//             cache: false,
//             success: function(result){
//                 status=result;
//             }
//         });
//     }else{
//         var url="/singer/modifySinger";
//         var requestData={"singerId":singerId,"singerName":singerName,
//             "singerPinyin":singerPinyin,"singerType":singerType,"singerVersion":singerVersion};
//         $.ajax({
//             url: url,
//             data: requestData,
//             type:"post",
//             async:false,
//             success: function(result){
//                 status=result;
//             }
//         });
//     }
//     if(status){
//         var index = parent.layer.getFrameIndex(window.name);
//         parent.layer.close(index);
//     }else{
//         layer.msg("请上传图片类型的文件!");
//         return false;
//     }
// }
$(function(){
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
});