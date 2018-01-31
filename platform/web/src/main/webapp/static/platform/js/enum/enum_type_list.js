/*字典类型-增加*/
function enum_type_add(){
    //请求的路径
    var url="/enum/showEnumType";
    var title="添加字典类型";
    var width="";
    var height="510";
    url+="?operateType=1";
    layer_show(title,url,width,height);
}

/*批量删除*/
function batchDel(){
    //保存一组选中的编号
    var selectedNos="";
    //获取复选框元素
    var checkedsNode=document.getElementsByName("id");
    //保存已删除的一组复选框元素
    var deletedCheckNode=new Array();
    /* 代表选中被删除元素的索引 */
    var index=0;
    for (var i=0;i<checkedsNode.length;i++){
        if(checkedsNode[i].checked == true){
            //保存待删除的节点
            deletedCheckNode[index]=checkedsNode[i];
            selectedNos+=checkedsNode[i].value+",";
            index++;
        }
    }
    selectedNos = selectedNos.substr(0,selectedNos.length-1);
    singleDel(deletedCheckNode, selectedNos);
}
/*单个删除*/
function singleDel(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        /* 后台异步执行的结果 */
        var status=false;
        //访问后台的请求路径
        var requestUrl="/enum/deleteEnumType";
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
            if(isArray(obj)){
                for (var i = 0; i < obj.length; i++) {
                    $(obj[i]).parents("tr").remove();
                }
            }else{
                $(obj).parents("tr").remove();
            }
            layer.msg('已删除!',{icon:1,time:1000});
        }else{
            layer.msg('删除失败!',{icon:2,time:2000});
        }
    });
}
//判断元素是否是数组
function isArray(object){
    return object && typeof object==='object' &&
        Array == object.constructor;
}