
/*批量删除*/
function role_to_admin(operation){

    //保存一组菜单编号
    var ids="";
    //获取复选框元素
    var idNosNode = document.getElementsByName("id");
    //保存已删除的一组复选框元素
    var deletedIdNode = new Array();

    /* 代表选中被删除元素的索引 */
    var index = 0;
    for (i = 0;i < idNosNode.length; i++){
        if(idNosNode[i].checked == true){
            //保存待删除的节点
            deletedIdNode[index] = idNosNode[i];
            if(i < idNosNode.length - 1){
                ids += idNosNode[i].value+",";
            }else{
                ids += idNosNode[i].value;
            }
            index++;
        }

    }
    admin_del(deletedIdNode, ids ,operation);
}


function admin_del(obj,id,operation){
    var url,msg,returnmsg,data;
    var role_id = $("#role_id").val();
    if(operation == '1'){
        data = {"account_ids": id,"operateType": '1',"role_id": role_id};
        msg = '确认要删除么？';
        returnmsg = "删除成功！";
    }else if(operation == '2'){
        data = {"account_ids": id,"operateType": '2',"role_id": role_id};
        returnmsg = "添加成功！";
        msg = '确认要添加么？';
    }else{
        layer.msg("参数错误");
        return false;
    }
console.log("url="+url+",msg="+msg+",data="+data+',operation='+operation);
    layer.confirm(msg,function(index){
        $.ajax({
            type: 'POST',
            url: '/role/modifthAccountToRole',
            dataType: 'json',
            data:data,
            success: function(data){
                if(data.responseCode == '1'){
                    $(obj).parents("tr").remove();
                    layer.msg(returnmsg,{icon:1,time:1000});
                }else{
                    layer.msg('操作失败!'+data.responseMessage,{icon:5,time:1000});
                }
            },
            error:function(data) {
                console.log(data);
            },
        });
    });
}
/*管理员-删除*/
