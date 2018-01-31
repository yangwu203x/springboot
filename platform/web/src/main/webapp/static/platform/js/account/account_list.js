/*
 参数解释：
 title	标题
 url		请求的url
 id		需要操作的数据id
 w		弹出层宽度（缺省调默认值）
 h		弹出层高度（缺省调默认值）
 */
/*管理员-增加*/
function admin_add(){
    var title = '添加管理员';
    var url = '/account/showAccount';
    var w='800';
    var h='500';
    url = url + '?operateType=1';
    layer_show(title,url,w,h);
}

/*批量删除*/
function datadel(){
    var param = selectedNos("hot_id");
    admin_del(param.deletedCheckNode, param.selectedNos);
    // //保存一组菜单编号
    // var ids="";
    // //获取复选框元素
    // var idNosNode = document.getElementsByName("id");
    // //保存已删除的一组复选框元素
    // var deletedIdNode = new Array();
    //
    // /* 代表选中被删除元素的索引 */
    // var index = 0;
    // for (i = 0;i < idNosNode.length; i++){
    //     if(idNosNode[i].checked == true){
    //         //保存待删除的节点
    //         deletedIdNode[index] = idNosNode[i];
    //         if(i < idNosNode.length - 1){
    //             ids += idNosNode[i].value+",";
    //         }else{
    //             ids += idNosNode[i].value;
    //         }
    //         index++;
    //     }
    //
    // }
    // admin_del(deletedIdNode, ids);
}


/*管理员-删除*/
function admin_del(obj,id){
    layer.confirm('确认要删除吗？',function(index){
        var param = {};
        param["idName"] = "hot_id";
        param["successMsg"] = "成功";
        param["failuredMsg"] = "失败";
        param["requestUrl"] = "/account/deleteAccount";
        param["requestParam"] = "ids";
        param["id"] = id;
        ajaxToJson(obj,param);
        // $.ajax({
        //     type: 'POST',
        //     url: '/account/deleteAccount',
        //     dataType: 'json',
        //     data:data,
        //     success: function(data){
        //         $(obj).parents("tr").remove();
        //         layer.msg('已删除!',{icon:1,time:1000});
        //     },
        //     error:function(data) {
        //         console.log(data.msg);
        //     },
        // });
    });
}

/*管理员-编辑*/
function admin_edit(id){
    var title = '管理员编辑';
    var url = '/account/showAccount';
    var w = '800';
    var h = '500';
    url = url + '?operateType=2&id=' + id;
    layer_show(title,url,w,h);
}
/*管理员-停用*/
function admin_stop(obj,id){
    layer.confirm('确认要停用吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……
        $.ajax({
            url:"/account/accountForbid",
            type:"POST",
            data:{"id":id},
            success:function(result){
                $(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,' + id + ')" href="javascript:;" title="启用" style="text-decoration:none"><i class="Hui-iconfont Hui-iconfont-gouxuan"></i></a>');
                $(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已禁用</span>');
                $(obj).remove();
                layer.msg('已停用!',{icon: 5,time:1000});
            }
        });
    });
}

/*管理员-启用*/
function admin_start(obj,id){
    layer.confirm('确认要启用吗？',function(index){
        //此处请求后台程序，下方是成功后的前台处理……
        $.ajax({
            url:"/account/accountOpen",
            type:"POST",
            data:{"id":id},
            success:function(result){
                $(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,' + id + ')" href="javascript:;" title="停用" style="text-decoration:none"><i class="Hui-iconfont Hui-iconfont-weigouxuan"></i></a>');
                $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
                $(obj).remove();
                layer.msg('已启用!', {icon: 6,time:1000});
            }
        });


    });
}