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
	</div>
	
	<script type="text/javascript">
		wx.config({
		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '', // 必填，公众号的唯一标识
		    timestamp: , // 必填，生成签名的时间戳
		    nonceStr: '', // 必填，生成签名的随机串
		    signature: '',// 必填，签名，见附录1
		    jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
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