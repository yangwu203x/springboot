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
$(function(){
    //对歌曲的输入加以筛选
    $("#song_name").on("keyup",function(){
        var requestUrl="/song/obtainSongIdByName";
        var songName=$(this).val();
        if(songName==""||songName==" "){
            return;
        }
        var requestData={"name":songName};
        $.ajax({
            url:requestUrl,
            data:requestData,
            type:"POST",
            success:function(result){
                var songIdNode=$("#song_id");
                var songIdHtml="";
                for ( var i in result) {
                    songIdHtml+="<option value='"+result[i]+"'>"+result[i]+"</option>";
                }
                songIdNode.html(songIdHtml);
            }
        });
    });
    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
});