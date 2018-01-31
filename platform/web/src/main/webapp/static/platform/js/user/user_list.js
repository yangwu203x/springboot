$(function(){
    $("#email").keyup(function(){
        autoComplete(this,"/user/autoSearch");
    });
    $("#name").keyup(function(){
        autoComplete(this,"/user/autoSearch");
    });
});
function autoComplete(obj,requestUrl){
    var keyValue = $(obj).val();
    if(keyValue == "") {
        return false;
    }
    var paramName = $(obj).attr("name");
    var requestData = '{"'+ paramName +'":"'+keyValue +'"}';
    var data = JSON.parse(requestData);
    $.ajax({
        url:requestUrl,
        type:"POST",
        data:data,
        dataType:"json",
        async:false,
        success:function(result){
            $(obj).autocomplete({
                source: eval(result)
            });
        }
    });
}
/*用户-增加*/
function user_add(){
    //请求的路径
    var url="/user/showUserEdit";
    var title="添加用户";
    var width="";
    var height="370";
    url+="?operateType=1";
    layer_show(title,url,width,height);
}


/*用户-编辑*/
function user_edit(id){
    //请求的路径
    var url="/user/showUserEdit";
    var title="编辑用户";
    var width="";
    var height="370";
    url+="?operateType=2&id="+id;
    layer_show(title,url,width,height);
}

/*批量删除*/
function batchDel(){
    var param = selectedNos("hot_id");
    singleDel(param.deletedCheckNode, param.selectedNos);
    // //保存一组选中的编号
    // var selectedNos="";
    // //获取复选框元素
    // var checkedsNode=document.getElementsByName("id");
    // //保存已删除的一组复选框元素
    // var deletedCheckNode=new Array();
    // /* 代表选中被删除元素的索引 */
    // var index=0;
    // for (i=0;i<checkedsNode.length;i++){
    //     if(checkedsNode[i].checked == true){
    //         //保存待删除的节点
    //         deletedCheckNode[index]=checkedsNode[i];
    //         selectedNos+=checkedsNode[i].value+",";
    //         index++;
    //     }
    //
    // }
    // selectedNos = selectedNos.substr(0,selectedNos.length-1);
    // singleDel(deletedCheckNode, selectedNos);
}
/*单个删除*/
function singleDel(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        var param = {};
        param["idName"] = "hot_id";
        param["successMsg"] = "成功";
        param["failuredMsg"] = "失败";
        param["requestUrl"] = "/user/deleteUser";
        param["requestParam"] = "ids";
        param["id"] = ids;
        ajaxToJson(obj,param);
        // /* 后台异步执行的结果 */
        // var status=false;
        // //访问后台的请求路径
        // var requestUrl="/user/deleteUser";
        // //请求后台的数据
        // console.log("id="+id);
        // var requestData={"ids":id};
        // $.ajax({
        //     url:requestUrl,
        //     type:'POST',
        //     data:requestData,
        //     async:false,
        //     success:function(result){
        //         status=result;
        //     }
        // });
        // if(status.responseCode == '1'){
        //     if(isArray(obj)){
        //         for (var i = 0; i < obj.length; i++) {
        //             $(obj[i]).parents("tr").remove();
        //         }
        //     }else{
        //         $(obj).parents("tr").remove();
        //     }
        //     layer.msg('已删除!',{icon:1,time:1000});
        // }else{
        //     layer.msg('删除失败!',{icon:2,time:2000});
        // }
    });
}
//判断元素是否是数组
function isArray(object){
    return object && typeof object==='object' &&
        Array == object.constructor;
}