$().ready(function() {
    //是否有审核
    $(".hidden").hide();
    parseValue("accountId")
});
function parseValue(title){
    var formatJson;
    var data = {"id":title};
    var formatNodes = $("."+title);
    $.ajax({
        url:'/account/listAccountName',
        data:data ,
        type:"POST",
        async:false,
        cache: false,
        success: function(result){
            formatJson = result;
        }
    });
    for(var i =0; i<formatNodes.length;i++){
        var value = formatNodes[i].innerHTML;
        $.each( formatJson, function(index,content)
        {
            if(value == content.id){
                formatNodes[i].innerHTML = content.username;
            }else if (value == '-1' || value == ''){
                formatNodes[i].innerHTML = '';
            }
        });
    }
}