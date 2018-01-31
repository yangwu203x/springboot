//自动补全
$("#id").keyup(function(){
    autoComplete(this,"autoSearchUrl");
});

//自动补全函数
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



/*批量删除*/
function selectedNos(idName){
    var param = {};
    //保存一组选中的编号
    var selectedNos="";
    //获取复选框元素
    var checkedsNode=$("[name='" + idName + "']");
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
    if(selectedNos.length==0){
        layer.msg("请至少选择一项!");
        return;
    }
    selectedNos = selectedNos.substr(0,selectedNos.length-1);
    param["selectedNos"] = selectedNos;
    param["deletedCheckNode"] = deletedCheckNode;
    return param;
}
/*服务器ajax操作*/
function ajaxToJson(obj,param){
    // /* 后台异步执行的结果 */
    var status=false;
    //访问后台的请求路径
    var requestUrl=param.requestUrl;
    //请求后台的数据
    var requestData={};
    requestData[param.requestParam] = param.id;
    $.ajax({
        url:requestUrl,
        type:'POST',
        data:requestData,
        async:false,
        success:function(result){
            status=result;
        }
    });
    if(status.responseCode == 1){
        if(isArray(obj)){
            for (var i = 0; i < obj.length; i++) {
                $(obj[i]).parents("tr").remove();
            }
        }else{
            $(obj).parents("tr").remove();
        }
        layer.msg(param.successMsg,{icon:1,time:1000});
    }else{
        layer.msg(param.failureMsg+status.responseMessage,{icon:2,time:2000});
    }
}
    //判断元素是否是数组
    function isArray(object){
        return object && typeof object==='object' &&
            Array == object.constructor;
    }



