function parseValue(){
    var formatJson;
    var role_id = $("."+"containAccount").val();
    var formatNodes = $("."+"containAccount");
    $.ajax({
        url:'/role/getAccountOfRole',
        // data:{role_id:role_id},
        type:"POST",
        async:false,
        cache: false,
        success: function(result){
            for(var i =0; i<formatNodes.length;i++){
                var role_id = formatNodes[i].innerHTML;
                formatNodes[i].innerHTML = "无";
                $.each( result, function(index,content)
                {
                    if(role_id == content.role_id){
                        formatNodes[i].innerHTML = content.username;
                    }
                });
            }
        }
    });

}
parseValue();
var menuList = null;
$.ajax({
    url:"/menu/obtainAllMenu",
    type:"POST",
    success:function(result){
        menuList = result;
    }
});

//在一个数组中检查是否存在某一个值
function checkExist(privilegeMenuList,menuNo){
    var status = false;
    for (var i = 0; i < privilegeMenuList.length; i++) {
        if(privilegeMenuList[i]  == menuNo)
            status = true;
    }
    return status;
}

//同步父级菜单的选项
function selectPrevious(role_id,current){
    var checkNode = $(current).parent(".privilege_menu_span").children(":first")[0];
    if(current.checked  == true){
        var requestData = null;
        if(checkNode.checked  == true){
            requestData = {"role_id":role_id,"menuNo":current.value};
        }else{
            requestData = {"role_id":role_id,"menuNo":current.value+","+checkNode.value};
        }
        checkNode.checked = true;

        //增加菜单权限
        $.ajax({
            url:"/role_menu/givePrivilege",
            type:"POST",
            data:requestData,
            success:function(result){
                //result true:false
            }
        });
    }else{
        var requestData = {"role_id":role_id,"menuNo":current.value};
        //取消选中子菜单权限
        $.ajax({
            url:"/role_menu/cancelPrivilege",
            type:"POST",
            data:requestData,
            success:function(result){
                //result true:false
            }
        });
    }
}

//同步子菜单选项
function selectChildren(role_id,current){
    console.log("role_id="+role_id);
    var inputNode = $(current).parent(".privilege_menu_span").children("input");
    var menuNo = "";
    if(current.checked  == false){
        for (var i = 1; i < inputNode.length; i++) {
            inputNode[i].checked = false;
            menuNo += inputNode[i].value+",";
        }
        //要取消权限的菜单编号
        menuNo += current.value;
        var requestData = {"role_id":role_id,"menuNo":menuNo};
        //取消选中子菜单权限
        $.ajax({
            url:"/role_menu/cancelPrivilege",
            type:"POST",
            data:requestData,
            success:function(result){
            }
        });
    }else{
        for (var i = 1; i < inputNode.length; i++) {
            inputNode[i].checked = true;
            menuNo += inputNode[i].value+",";
        }
        menuNo += current.value;
        var requestData = {"role_id":role_id,"menuNo":menuNo};
        //增加菜单权限
        $.ajax({
            url:"/role_menu/givePrivilege",
            type:"POST",
            data:requestData,
            success:function(result){
                //alert(result);
            }
        });
    }

}

/*显示权限分配的弹窗*/
function allocatePrivilege(role_id){
    //保存账户拥有权限的菜单
    var privilegeMenu = null;
    $.ajax({
        url:"/menu/obtainMenuIdByRole",
        type:"POST",
        data:{"id":role_id},
        async:false,
        success:function(result){
            privilegeMenu = result;
        }
    });
    var contentData = '<div style = "padding-left:50px;font-size:18px;padding-top:15px;">';
    for (var i = 0; i <menuList.length; i++) {
        var menuNo = menuList[i].id;
        var menuName = menuList[i].name;
        if(checkExist(privilegeMenu,menuNo)){
            contentData += '<span class = "privilege_menu_span"><input type = "checkbox" value = '+menuNo+' checked = true  onclick = selectChildren('+role_id+',this) />'+menuName+"<br/>";
        }else{
            contentData += '<span class = "privilege_menu_span"><input type = "checkbox" value = '+menuNo+'  onclick = selectChildren('+role_id+',this) />'+menuName+"<br/>";
        }
        if(menuList[i].subMenuList.length != 0){
            var subMenuList = menuList[i].subMenuList;
            for (var j = 0; j < subMenuList.length; j++) {
                var subMenuNo = subMenuList[j].id;
                var subMenuName = subMenuList[j].name;
                if(checkExist(privilegeMenu,subMenuNo)){
                    contentData += '&nbsp;&nbsp;&nbsp;<input type = "checkbox" checked = true value = '+subMenuNo+' onclick = selectPrevious('+role_id+',this) />'+subMenuName+"<br/>";
                }else{
                    contentData += '&nbsp;&nbsp;&nbsp;<input type = "checkbox" value = '+subMenuNo+' onclick = selectPrevious('+role_id+',this) />'+subMenuName+"<br/>";
                }
            }
        }
        contentData += "</span>";
    }
    contentData += '</div>';
    //在这里面输入任何合法的js语句
    layer.open({
        type: 1,
        area: ['15%', '40%'],
        title: '权限管理',
        shade: 0.5,//遮罩透明度
        shadeClose:true,//点击遮罩关闭层
        moveType: 1,//拖拽风格，0是默认，1是传统拖动
        shift: 0,//0-6的动画形式，-1不开启
        fix:true,//不随滚动条滚动，一直在可视区域。
        border:[0],
        content: contentData,
        btn:['确定    ']
    });
}

/*管理员-角色-添加*/
function admin_role_add(){
    var title = '添加角色';
    var url = '/role/showRole?operateType=1';
    var w = '';
    var h = '300';
    layer_show(title,url,w,h);
}
/*管理员-角色-编辑*/
function admin_role_edit(id){
    var title = '编辑角色';
    var url = '/role/showRole';
    var w = '';
    var h = '300';
    url += "?operateType=2&id="+id;
    console.log("id="+id+",url="+url);
    layer_show(title,url,w,h);
}

/*添加角色到管理员*/
function account_role_add(id){
    var title = '添加角色到管理员';
    var url = '/role/roleToAccount';
    var w = '';
    var h = '600';
    url += "?operateType=2&role_id="+id;
    layer_show(title,url,w,h);
}

/*从角色删除管理员*/
function account_role_del(id){
    var title = '删除角色下管理员';
    var url = '/role/roleToAccount';
    var w = '';
    var h = '600';
    url += "?operateType=1&role_id="+id;
    layer_show(title,url,w,h);
}

/*角色-批量删除*/
function datadel(){
    var param = selectedNos("roleNo");
    admin_role_del(param.deletedCheckNode, param.selectedNos);
    // //保存一组角色编号
    // var roleNos = "";
    // //获取复选框元素
    // var roleNosNode = document.getElementsByName("roleNo");
    // //保存已删除的一组复选框元素
    // var deletedRoleNode = new Array();
    //
    // /* 代表选中被删除元素的索引 */
    // var index = 0;
    // for (i = 0;i<roleNosNode.length;i++){
    //     if(roleNosNode[i].checked  == true){
    //         //保存待删除的节点
    //         deletedRoleNode[index] = roleNosNode[i];
    //         roleNos += roleNosNode[i].value+",";
    //         index++;
    //     }
    // }
    // roleNos = roleNos.substr(0,roleNos.length-1);
    // admin_role_del(deletedRoleNode, roleNos);
}
/*角色-删除*/
function admin_role_del(obj,ids){

    layer.confirm('确认要删除吗？',function(index){
        var param = {};
        param["idName"] = "hot_id";
        param["successMsg"] = "成功";
        param["failuredMsg"] = "失败";
        param["requestUrl"] = "/role/deleteRole";
        param["requestParam"] = "ids";
        param["id"] = ids;
        ajaxToJson(obj,param);
        // /* 后台异步执行的结果 */
        // var status = false;
        // //访问后台的请求路径
        // var requestUrl = "/role/deleteRole";
        // //请求后台的数据
        // var requestData = {"ids":ids};
        // $.ajax({
        //     url:requestUrl,
        //     type:'POST',
        //     data:requestData,
        //     async:false,
        //     success:function(result){
        //         status = result;
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
        //     layer.msg('删除失败,可能待删除的角色中还有用户!',{icon:2,time:2000});
        // }
    });
}
//判断元素是否是数组
function isArray(object){
    return object && typeof object==='object' &&
        Array == object.constructor;
}