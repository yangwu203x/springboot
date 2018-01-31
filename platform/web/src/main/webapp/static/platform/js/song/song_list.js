$().ready(function() {
    //是否有审核
    $(".hidden").hide();
    //数据渲染
    var arr = ["songFormat","songLang","songScene","tvProgramEnter","songYears","songTheme","tvProgram"];
    for(var i =0; i< arr.length;i++){
        parseValue(arr[i]);
    }
});
function parseValue(title){
    var formatJson;
    var data = {"title":title};
    var formatNodes = $("."+title);
    $.ajax({
        url:'/enum/listEnumValueByTitle',
        data:data ,
        type:"POST",
        async:false,
        cache: false,
        success: function(result){
            formatJson = result;
        }
    });
    for(var i =0; i<formatNodes.length;i++){
        var value = formatNodes[i].innerHTML;
        $.each( formatJson, function(index,content)
        {
            if(value == content.id){
                formatNodes[i].innerHTML = content.name;
            }else if (value == '-1' || value == ''){
                formatNodes[i].innerHTML = '';
            }
        });
    }
}
var shapeStatus=true;
/*批量上传歌曲*/
function uploadSongs(){
    if(!shapeStatus){
        return;
    }
    var url="/song/showUpload";
    shapeStatus=false;
    layer.open({
        type: 2,
        area: ['630px','500px'],
        fix: false, //不固定
        maxmin: true,
        shade:0,
        title: "上传歌曲",
        offset: ['330px', '20px'],
        content: url,
        cancel:function(){
            location.replace(location.href);
        },
        end: function () {
            if(shapeStatus) location.reload();
        }
    });
}

function uploadSong(songId,index){
    var songFile=$("#songFile"+index)[0].files[0];
    $("#song-file-span"+index).html("<progress id='showProgress"+index+"'></progress><br/><span id='song-progress"+index+"'></span>");
    //创建FormData对象，初始化为form表单中的数据。需要添加其他数据可使用formData.append("property", "value");
    var formData = new FormData();
    formData.append("songFile",songFile);
    formData.append("songId",songId);
    //ajax异步上传
    $.ajax({
        url: "/song/uploadSongFile",
        type: "POST",
        data: formData,
        xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数

            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ //检查upload属性是否存在
                //绑定progress事件的回调函数
                /*  myXhr.upload.addEventListener('progress',progressHandlingFunction, false);   */
                myXhr.upload.addEventListener('progress',function(e){
                    if (e.lengthComputable) {
                        $('#showProgress'+index).attr({value : e.loaded, max : e.total}); //更新数据到进度条
                        var percent = (e.loaded/e.total*100);
                        $('#song-progress'+index).html(percent.toFixed(0) + "%");
                    }
                },false);
            }
            return myXhr; //xhr对象返回给jQuery使用
        },
        success: function(result){
            if(result==true)  $("#song-file-span"+index).text("已上传");
        },
        contentType: false, //必须false才会自动加上正确的Content-Type
        processData: false  //必须false才会避开jQuery对 formdata 的默认处理
    });
}

//自动搜索
$(function(){
    $("#song_name").keyup(function(){
        var songName=$(this).val();
        if(songName==null ||songName==""){
            return false;
        }
        var requestUrl="/song/autoSearch";
        var requestData={"songName":songName};
        $.ajax({
            url:requestUrl,
            type:"POST",
            data:requestData,
            dataType:"json",
            async:false,
            success:function(result){
                $("#song_name").autocomplete({
                    source: eval(result)
                });
            }
        });
    });

});
/*歌曲-增加*/
function song_add(){
    //请求的路径
    var url="/song/showSongEdit";
    var title="添加歌曲信息";
    var width="";
    var height="510";
    url+="?operateType=1";
    layer_show(title,url,width,height);
}


/*歌曲-编辑*/
function song_edit(id){
    //请求的路径
    var url="/song/showSongEdit";
    var title="编辑歌曲信息";
    var width="";
    var height="510";
    url+="?operateType=2&id="+id;
    layer_show(title,url,width,height);
}

/*单个删除*/
function singleDel(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        /* 后台异步执行的结果 */
        var status=false;
        //访问后台的请求路径
        var requestUrl="/song/deleteSong";
        //请求后台的数据
        var requestData={"ids":id};
        $.ajax({
            url:requestUrl,
            type:'POST',
            data:requestData,
            async:false,
            success:function(result){
                status=result;
            }
        });
        if(status.responseCode == '1'){
            $(obj).parents("tr").remove();
            layer.msg('已删除!',{icon:1,time:1000});
        }else{
            layer.msg('删除失败!',{icon:2,time:2000});
        }
    });
}
/*通过审核*/
function passCheck(id){
    var status=false;
    var requestUrl="/song/passCheck";
    var requestData={"id":id};
    $.ajax({
        url:requestUrl,
        type:'POST',
        data:requestData,
        async:false,
        success:function(result){
            status=result;
        }
    });
    if(status){
        location.replace(location.href);
        layer.msg("审核通过!");
    }else layer.msg("审核失败!");
}

/*备份歌曲的信息*/
function backup(){
    var requestUrl="/song/backupSong";
    $.ajax({
        url:requestUrl,
        type:"POST",
        success:function(result){
            if(result){
                layer.msg("数据备份成功!");
            }else{
                layer.msg("数据备份失败!");
            }
        }
    });
}
/*还原歌曲的信息*/
function restore(){
    layer.confirm('确认要还原备份的信息吗,请谨慎操作!',function(index){
        var requestUrl="/song/restoreSong";
        var status=false;
        $.ajax({
            url:requestUrl,
            type:"POST",
            async:false,
            success:function(result){
                if(result){
                    layer.msg("数据还原成功!");
                    status=true;
                }else{
                    layer.msg("数据还原失败!");
                }
            }
        });
        if(status) location.replace(location.href);
    });
}
/*导出数据到excel中*/
function expertToExcel(){
    var url="/song/toExportHtml";
    var title="确认";
    var width="320";
    var height="260";
    layer_show(title,url,width,height);
    // location.href="/song/exportToExcel";
    //var songFile=$('#songFile')[0].files[0];
}
function importFromExcel(){
    var status=null;
    var requestUrl="/song/importFromExcel";
    //封装file的表单数据
    var formData=new FormData();
    var excelFile=$("#excel_file")[0].files[0];
    formData.append("excelFile",excelFile);
    $.ajax({
        url: requestUrl,
        data: formData,
        type:"post",
        async:false,
        processData: false,
        contentType: false,
        cache: false,
        success: function(result){
            status=result;
        }
    });
    if(status){
        layer.msg("导入成功!");
        location.replace(location.href);
    }
    else layer.msg("导入失败!");
}

function templateDown(){
    location.href="/song/templateDownload";
}