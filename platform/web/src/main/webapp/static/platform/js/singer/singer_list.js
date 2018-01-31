$().ready(function() {
    //是否有审核
    $(".hidden").hide();
    var arr = ["singerType"];
    for(var i =0; i< arr.length;i++){
        parseValue(arr[i]);
    }
});
function parseValue(title){
    var formatJson;
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
    var data = {"title":title};
    var formatNodes = $("."+title);

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

$(function(){
    $("#singer_name").keyup(function(){
        var singerName=$(this).val();
        if(singerName==null ||singerName==""){
            return false;
        }
        var requestUrl="/singer/autoSearch";
        var requestData={"name":singerName};
        $.ajax({
            url:requestUrl,
            type:"POST",
            data:requestData,
            dataType:"json",
            async:false,
            success:function(result){
                $("#singer_name").autocomplete({
                    source: eval(result)
                });
            }
        });
    });

});


/*歌手-增加*/
function singer_add(){
    //请求的路径
    var url="/singer/showSingerEdit.do";
    var title="添加歌手";
    var width="";
    var height="510";
    url+="?viewName=singer_edit&operateType=1";
    layer_show(title,url,width,height);
}


/*歌手-编辑*/
function singer_edit(id){
    //请求的路径
    var url="/singer/showSingerEdit.do";
    var title="编辑歌手类型";
    var width="";
    var height="510";
    url+="?viewName=singer_edit&operateType=2&id="+id;
    layer_show(title,url,width,height);
}

/*单个删除*/
function singleDel(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        /* 后台异步执行的结果 */
        var status=false;
        //访问后台的请求路径
        var requestUrl="/singer/deleteSinger.do";
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
        if(status){
            $(obj).parents("tr").remove();
            layer.msg('已删除!',{icon:1,time:1000});
        }else{
            layer.msg('删除失败!',{icon:2,time:2000});
        }
    });
}
function passCheck(id){
    var status=false;
    var requestUrl="/singer/passCheck";
    var requestData={"id":id};
    $.ajax({
        url:requestUrl,
        type:'POST',
        data:requestData,
        async:false,
        success:function(result){
            status=result;
            if(result.responseCode == '1'){
                layer.msg("审核通过!");
                setTimeout(function(){
                    location.replace(location.href);
                },300);
            }else{
                layer.msg("审核失败!,原因为："+result.responseMessage);
            }
        }
    });

}
/*通过审核*/
//分页

