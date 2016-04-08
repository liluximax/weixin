<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<title>开通城市列表</title>
</head>
<body>
	<h3>开通城市列表</h3>
	<div class="city"></div>
	<div>
		<form action="list.jsp" method="post">
			<input name="cityName" type="hidden" value="上海市">
			<input type="submit" value="点击测试当前城市无加油站情况">
		</form>
	</div>
</body>
<script type="text/javascript">
	var url = "/Weixin/station/city.do";
	$.getJSON(url,function(data){
		$(".city").append("<ul>");
		$.each(data.city_list,function(index,item){
			var city = item.city;
			var content = $("<form action='list.jsp' method='post'>"
							+"<input name='cityName' type='hidden' value="+city+">"
							+"<input type='submit' value="+city+">"
							+"</form>");
			$(".city").append(content);
		})
		$(".city").append("</ul>");
	});
</script>
</html>