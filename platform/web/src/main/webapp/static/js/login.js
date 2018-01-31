 validateSlider=false;

$(function() {
	
	var loginSlider= new SliderUnlock("#slider", {
		successLabelTip : "验证成功"
	}, function() {
		validateSlider=true;
	});
	loginSlider.init();
	$("#reset").on('click', function(){
		loginSlider.reset();
		loginSlider.init();
		validateSlider=false;
		$("#account").val("");
		$("#password").val("");
		$("#labelTip").text("拖动滑块验证");
    });
});

//登陆的单击事件
function validate(){
	var account=$("#account").val();
	if(account==""){
		layer.msg('请输入账户!');
		return false;
	}
	var password=$("#password").val();
	if(password==""){
		layer.msg('请输入密码!');
		return false;
	}
	if(!validateSlider){
		layer.msg('请拖动滑块完成验证!');
		return false;
	}
	
	return true;
}
