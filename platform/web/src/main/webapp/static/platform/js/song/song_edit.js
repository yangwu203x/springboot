//初始化填充值
$().ready(function() {
    var defaultData ="";
    var selectedValue ='';
    var defaultSingerId = $("#defaultSingerId").val();
    if(defaultSingerId != ''){
        selectedValue = defaultSingerId.split(",");
        var data = {"ids":defaultSingerId};
        $.ajax({
            url: '/singer/obtainIdById',
            data: data,
            type: "POST",
            async: false,
            cache: false,
            success: function (result) {
                defaultData = result;
            }
        });
    }

        //远程筛选
        $("#sel_menu3").select2({
            placeholder: {id:-1,text:"请输入"},
            data:defaultData,
            minimumInputLength: 3,
            maximumSelectionLength: 10,
            ajax: {
                url: '/singer/obtainIdByName',
                dataType: 'json',
                delay: 550,
                data: function (params) {
                    return {
                        singerName: params.term, // search term
                    };
                },
                processResults: function (data) {
                    return {
                        results: data
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) { return markup; },
            templateResult: formatRepoSingerName
        });
    $("#sel_menu3").val(selectedValue).trigger('change');//初始化默认值
    //填充数据
    addData();
    //校验表单
    $("#edit_form").validate({
        rules : {fileFormat:{min:0}, songLang:{ min:0}, tvProgram:{min:0}, songYears:{ min:0}, themeSong:{ min:0}, tvProgramEnter:{ min:0}, singerId:{min:0}, scene:{min:0}},
        messages:{fileFormat:{ min:'必选'}, songLang:{ min:'必选'}, tvProgram:{min:'必选'}, songYears:{min:'必选'}, themeSong:{min:'必选'}, tvProgramEnter:{min:'必选'}, singerId:{min:'必选'}, scene:{min:'必选'}}
    });
});

function formatRepoSingerName(repo) {
    if (repo.loading) return repo.text;
    var markup = "<option value='" + repo.id +"'>"+repo.text+"</option>";
    return markup;
}
//提交表单
$.validator.setDefaults({
    submitHandler: function () {
        var singerName = $("#sel_menu3 option:selected").text();
        $("#singerName").val(singerName);
        var mrsId = $("#mrsId").val();
        var midiId = $("#midiId").val();
        var vodId = $("#vodId").val();
        if(mrsId == '' && midiId == '' && vodId == ''){
            alert("MRS_ID、MIDI_ID、VOD_ID至少得填一项！");
            return ;
        }
        $("#edit_form").ajaxSubmit(function (result) {
            var code = result.responseCode;
            var msg = result.responseMessage;
            if (code == '1') {
                layer.msg("提交成功！");
                setTimeout(function(){
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                },1000);
            } else {
                layer.msg("提交失败！错误代码为："+code+",错误原因："+msg);
            }
        });
    }
});
$(function(){
    //针对vod格式的数据添加进行添加数据调整
    $("#file_format").on("change",function(){
    });

    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });
});

function addData() {
    var arr = [{"id": 'fileFormat', "title": 'songFormat'},
        {"id": 'langId', "title": 'songLang' },
        {"id": 'tvProgram', "title": 'tvProgram'},
        {"id": 'tvProgramEnter', "title": 'tvProgramEnter'},
        {"id": 'songYears', "title": 'songYears'},
        {"id": 'themeSong', "title": 'songTheme'},
        {"id": 'scene', "title": 'songScene'}];
    for (var i = 0; i < arr.length; i++) {
        var title = arr[i].title;
        var id = arr[i].id;
        var data = {"title": title};
        var value = $("#" + arr[i].id).attr("title");
        $.ajax({
            url: '/enum/listEnumValueByTitle',
            data: data,
            type: "POST",
            async: false,
            cache: false,
            success: function (result) {
                var selectMenuHtml = '<option value="-1" selected>请选择</option>';
                $.each(result, function (index, content) {
                    selectMenuHtml += '<option value = "' + content.id + '"';
                    if (value == content.id)
                        selectMenuHtml += ' selected = "selected" ';
                    selectMenuHtml += '>' + content.name + '</option>';
                });
                $("#" + id).html(selectMenuHtml);
            }
        });
    }
}