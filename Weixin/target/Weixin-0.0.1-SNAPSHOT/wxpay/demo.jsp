<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<title>paytest</title>
</head>
<body>
	paytest
	<div>
		<button id="btn">点击上传图片</button>
		<button id="pay">支付测试</button>
	</div>
	<div>
		<img id="img" style="height: 30%; width: 30%">
	</div>
</body>
<script type="text/javascript">

		$.getJSON("/Weixin/userinfo/jssdk.do",{url:location.href.split('#')[0]}, function(data){
			
			var _appId = data.appId;
			var _signature = data.signature;
			var _timestamp = data.timestamp;
			var _nonceStr = data.noncestr;
			wx.config({
			    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    appId: data.appId, // 必填，公众号的唯一标识
			    timestamp: data.timestamp, // 必填，生成签名的时间戳
			    nonceStr: data.noncestr, // 必填，生成签名的随机串
			    signature: data.signature,// 必填，签名，见附录1
			    jsApiList: ['chooseImage','chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			
			wx.ready(function () {
				    // 在这里调用 API
				    
				document.getElementById("btn").onclick = function(){
					wx.chooseImage({
					    count: 1, // 默认9
					    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
					    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
					    success: function (res) {
					        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					    	$("#img").attr("src",localIds);
					    }
					});
				}
				    
				document.getElementById("pay").onclick = function(){
					
				}
			});
		});
	</script>
</html>