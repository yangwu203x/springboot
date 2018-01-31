/*菜单-添加*/
function menu_add(){
    var title = "添加菜单";
    var url = "/menu/showAddMenu";
    var w = "";
    var h = "510";
    url+="?operateType=1";
    layer_show(title,url,w,h);
}


/*菜单-编辑*/
function menu_edit(id){
    var title = '编辑';
    var url = '/menu/showAddMenu';
    var w = '';
    var h = '500';
    url+="?operateType=2&id="+id;
    layer_show(title,url,w,h);
}

/*菜单-批量删除*/
function menus_del(){
    var param = selectedNos("menuNo");
    menu_del(param.deletedCheckNode, param.selectedNos);
    // //保存一组菜单编号
    // var menuNos="";
    // //获取复选框元素
    // var menuNosNode=document.getElementsByName("menuNo");
    // //保存已删除的一组复选框元素
    // var deletedMenuNode = new Array();
    //
    // /* 代表选中被删除元素的索引 */
    // var index=0;
    // for (i=0;i<menuNosNode.length;i++){
    //     if(menuNosNode[i].checked == true){
    //         //保存待删除的节点
    //         deletedMenuNode[index]=menuNosNode[i];
    //         menuNos+=menuNosNode[i].value+",";
    //         index++;
    //     }
    //
    // }
    // menuNos = menuNos.substr(0,menuNos.length-1);
    // console.log(menuNos+"<>");
    // menu_del(deletedMenuNode, menuNos);
}
/*菜单-删除*/
function menu_del(obj,ids){
    layer.confirm('确认要删除吗？',function(index){
        var param = {};
        param["idName"] = "hot_id";
        param["successMsg"] = "成功";
        param["failuredMsg"] = "失败";
        param["requestUrl"] = "/menu/deleteMenu";
        param["requestParam"] = "ids";
        param["id"] = ids;
        ajaxToJson(obj,param);
        // /* 后台异步执行的结果 */
        // var status=false;
        // //访问后台的请求路径
        // var requestUrl="/menu/deleteMenu";
        // //请求后台的数据
        // var requestData={"ids":ids};
        // $.ajax({
        //     url:requestUrl,
        //     type:'POST',
        //     data:requestData,
        //     async:false,
        //     success:function(result){
        //         status=result;
        //     }
        // });
        // if(status){
        //     if(isArray(obj)){
        //         for (var i = 0; i < obj.length; i++) {
        //             $(obj[i]).parents("tr").remove();
        //         }
        //     }else{
        //         $(obj).parents("tr").remove();
        //     }
        //     layer.msg('已删除!',{icon:1,time:1000});
        // }else{
        //     layer.msg('删除失败,可能待删除的菜单中存在子菜单!',{icon:2,time:2000});
        // }
    });
}

//判断元素是否是数组
function isArray(object){
    return object && typeof object==='object' &&
        Array == object.constructor;
}