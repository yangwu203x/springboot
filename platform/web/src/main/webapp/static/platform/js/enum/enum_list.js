var setting = {
    view: {
        dblClickExpand: false,
        showLine: false,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: function(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            if (treeNode.isParent) {
                zTree.expandNode(treeNode);
                return false;
            } else {
                url = "/enum/listEnumValue?enumTypeId="+treeNode.id+"&title="+treeNode.name;
                demoIframe.attr("src",url);
                return true;
            }
        }
    }
};

var zNodes;
var clearFlag = false;
function onCheck(e, treeId, treeNode) {
    if (clearFlag) {
        clearCheckedOldNodes();
    }
}
var code;


$(document).ready(function(){
    $.ajax({
        url:"/enum/listEnumType",
        type:'POST',
        data:{"pageSize":100},
        async:false,
        success:function(result){
            zNodes = new Array();
            var nodefirst = {id:-1, pId:0, name:"数据字典", open:false};
            zNodes.push(nodefirst);
            $.each( result, function(index,content)
            {
                var node = { id:content.id, pId:-1, name:content.name, open:true}
                zNodes.push(node);
            });
        }
    });
    var t = $("#treeMenu");
    t = $.fn.zTree.init(t, setting, zNodes);
    demoIframe = $("#testIframe");
    var zTree = $.fn.zTree.getZTreeObj("tree");
    $("#treeMenu").contextMenu('enumMenu', {
        bindings: {
            'addEnumType': function(t) {
                enum_type_add();
            },
            'delEnumType': function(t) {
                enum_type_del();
            },
        }
    });

});

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

/*字典类型-删除*/
function enum_type_del(){
    //请求的路径
    var url="/enum/listEnumTypeByPage";
    var title="删除字典类型";
    var width="";
    var height="650";
    layer_show(title,url,width,height);
}
