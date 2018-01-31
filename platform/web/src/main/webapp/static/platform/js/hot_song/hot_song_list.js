/*修改*/
function edit(id){
    //请求的路径
    var url="/hot_song/showHotSongEdit";
    var title="修改热门歌曲";
    var width="";
    var height="300";
    url+="?operateType=2&id="+id;
    layer_show(title,url,width,height);
}
/*增加*/
function add(){
    //请求的路径
    var url="/hot_song/showHotSongEdit";
    var title="添加热门歌曲";
    var width="";
    var height="300";
    url+="?operateType=1";
    layer_show(title,url,width,height);
}

/*批量删除*/
function batchDel(){
    var param = selectedNos("hot_id");
    deleteRank(param.deletedCheckNode, param.selectedNos);
}
/*单个删除*/
function deleteRank(obj,ids){
    layer.confirm('确认要删除吗？',function(index){
        var param = {};
        param["idName"] = "hot_id";
        param["successMsg"] = "成功";
        param["failuredMsg"] = "失败";
        param["requestUrl"] = "/hot_song/deleteHotSong";
        param["requestParam"] = "ids";
        param["id"] = ids;
        ajaxToJson(obj,param);
    });
}
