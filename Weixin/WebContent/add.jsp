<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<title>Insert title here</title>
</head>
<body>
	已经获取网页授权
	<p>code: <%=request.getParameter("code") %></p>
	<div>
		<span class="user"></span>
		<input id="btn" type="button" value="测试">
		<img id="img" src="">
	</div>
	
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
			    jsApiList: ['getLocation','chooseImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			
			wx.ready(function () {
				    // 在这里调用 API
				wx.getLocation({
				    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				    success: function (res) {
				        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				        var speed = res.speed; // 速度，以米/每秒计
				        var accuracy = res.accuracy; // 位置精度
				    }
				});
				    
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
			});
		});
	</script>
		
	<script type="text/javascript">
		var code = '<%=request.getParameter("code") %>';
		var state = '<%=request.getParameter("state") %>';
		var url = "/Weixin/userinfo/getuserinfo.do";
		$.getJSON(url,{"code":code, "state":state},function(data){
			var nickname = data.nickname;
			var headimgurl = data.headimgurl;
			var openid = data.openid;
			$(".user").append(nickname+","+headimgurl);
		});
	</script>
</body>
</html>