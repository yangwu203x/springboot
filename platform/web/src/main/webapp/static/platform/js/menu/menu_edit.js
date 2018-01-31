function iclassText(classname){
    var text = '<i class = "icon Hui-iconfont '+ classname +'" onclick = obtain_menu_icon(this,"' + classname + '");></i>';
    return text;
}
function select_menu_icon(){
    //图标数组
    var array = ["Hui-iconfont-home","Hui-iconfont-home2","Hui-iconfont-news","Hui-iconfont-tuku","Hui-iconfont-music","Hui-iconfont-tags","Hui-iconfont-yuyin3","Hui-iconfont-system","Hui-iconfont-help","Hui-iconfont-chuku","Hui-iconfont-picture","Hui-iconfont-fenlei","Hui-iconfont-hetong","Hui-iconfont-quanbudingdan","Hui-iconfont-renwu","Hui-iconfont-feedback","Hui-iconfont-feedback2","Hui-iconfont-dangan","Hui-iconfont-log","Hui-iconfont-pages","Hui-iconfont-file","Hui-iconfont-manage2","Hui-iconfont-order","Hui-iconfont-yuyin2","Hui-iconfont-yuyin","Hui-iconfont-picture1","Hui-iconfont-tuwenxiangqing","Hui-iconfont-moban-2","Hui-iconfont-jieri","Hui-iconfont-moban","Hui-iconfont-user","Hui-iconfont-tongji-bing","Hui-iconfont-user-group","Hui-iconfont-kefu"];
    var menuIcon = '<div id = "menu_icon" style = "font-size:30px;padding-left:20px;">';
    for(var m = 0;m < array.length; m++){ menuIcon += iclassText(array[m]);}
    menuIcon +=  '</div>';

    //在这里面输入任何合法的js语句
    layer.open({
        type: 1,
        area: ['50%', '70%'],
        title: '图标选择',
        shade: 0.5,//遮罩透明度
        shadeClose:true,//点击遮罩关闭层
        moveType: 1,//拖拽风格，0是默认，1是传统拖动
        shift: 1,//0-6的动画形式，-1不开启
        fix:true,//不随滚动条滚动，一直在可视区域。
        border:[0],
        content: menuIcon,
        btn:['确定','取消']
    });
}

//显示所选择的图标
function obtain_menu_icon(obj,menuIcon){
    $(obj).addClass("active").siblings().removeClass("active");
    menuIcon = "Hui-iconfont " + menuIcon;
    var iconHtml = '<a class = "btn btn-default round" href = "javascript:;" onclick = "select_menu_icon()">单击选择菜单图标</a>&nbsp;&nbsp;'+
        '<i class = "red '+ menuIcon +'" style = "font-size:30px;"></i>';
    $("#show_menu_icon").html(iconHtml);
    $("#menuIcon").val(menuIcon);
}
//新增子菜单时验证form表单但是不提交表单
function validateFormForSub() {
    return $('#menu_add_form').validate({
        rules:{  name:'required',url:'required', },
    });
}

function menuForm(){
    var status = false;
    //验证菜单类型
    var menuType = $("#menu-type").val();
    if(menuType == 0){
        layer.msg("请选择菜单类型!");
        return false;
    }

    //获取访问子菜单的url
    var url = $("#url").val();
    //获取子菜单的父菜单编号,若不存在父菜单则该菜单为顶级菜单
    var parentMenuId = $("#menu-select").val();
    if(parentMenuId == undefined||parentMenuId == 0) parentMenuId = 0;
    if(menuType == 2){
        if(parentMenuId == undefined||parentMenuId == 0){
            layer.msg("请选择父菜单类型!");
            return false;
        }
        if(url == ''){
            layer.msg("请输入子菜单访问的路径!");
            return false;
        }
    }

    //获取菜单名并且验证不能为空
    var name = $("#name").val();
    if(name == ''){
        layer.msg("菜单名不能为空!");
        return false;
    }

    //获取id
    var id = $("#id").val();

    //当添加父级菜单时应选择图标
    var menuIcon = null;
    //封装数据
    var requestData = null;
    if(menuType == 1){
        menuIcon = $("#menuIcon").val();
        requestData = {"name":name,"menuIcon":menuIcon,"id":id}
    }else{
        requestData = {"url":url,"parentMenuId":parentMenuId,"name":name,"menuIcon":menuIcon,"id":id}
    }

    //获取请求路径
    var form = $("#menu_add_form");
    var url = form.attr("action");

    $.ajax({
        url: url,
        data:requestData,
        type:"post",
        async:false,
        success: function(result){
            status = result;
            if(status){
                var index  =  parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }else{
                return false;
            }
        }
    });


}
var parentMenus;
$(function(){
    //顶级菜单数量

    $.ajax({
        url: "/menu/parentMenu",
        type:"post",
        async:false,
        success: function(result){
            parentMenus = result;
            console.log(parentMenus);
            changeMenu()
        }
    });
    //当菜单类型发生改变时页面元素一起改变
    $("#menu-type").on("change",function(){
        changeMenu();
    });



    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });

});

function changeMenu(){
    var parentMenuId = $("#parentMenuId").val();
    var menuType = $("#menu-type").val();
    //当选择子菜单的时候
    if(menuType == 2){
        //当不存在顶级菜单的时候
        if(parentMenus.length == 0){
            $("#menu-type").val("1");
            layer.msg('当前没有顶级菜单,不能增加子菜单!');
            $("#url").attr("readonly","true");
        }else{
            //追加子菜单选项卡
            $("#url").removeAttr("readonly");
            $("#row_menu_icon").hide();
            var selectMenuHtml = '<option value = "0" selected>请选择顶级菜单</option>';
            for ( var index in parentMenus) {
                if(parentMenuId == parentMenus[index].id){
                    selectMenuHtml += '<option value = "'+parentMenus[index].id +'" selected>'+parentMenus[index].name+'</option>';
                }else{
                    selectMenuHtml += '<option value = "'+parentMenus[index].id+'">'+parentMenus[index].name+'</option>';
                }
            }
            $("#menu-select").html(selectMenuHtml);
            $("#parent-menu").show();
        }
    }else{
        $("#url").attr("readonly","true");
        $("#parent-menu").hide();
        $("#row_menu_icon").show();
    }
}