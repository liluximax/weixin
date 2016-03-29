<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提交信息至服务器</title>
</head>
<body>
	
	<%
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	%>
	<jsp:useBean id="locate" class="com.baidu.model.Location" scope="request"></jsp:useBean>
	<jsp:setProperty property="*" name="locate"/>
	
	<div>
		<p>经度:<%=locate.getLat() %></p>
		<p>纬度:<%=locate.getLng() %></p>
		<p>id:<%=locate.getId() %></p>
		<p>名字:<%=locate.getName() %></p>
		<p>地址:<%=locate.getAdress() %></p>
	</div>
	
	<div>
		<form action="/Weixin/com/baidu/ServletService" method="get">
			<input type="hidden" name="lat" value="<%=locate.getLat() %>">
			<input type="hidden" name="lng" value="<%=locate.getLng() %>">
			<input type="hidden" name="id" value="<%=locate.getId() %>">
			<input type="submit" value="转给相关信息至服务器，可进行进一步操作">
		</form>
	</div>
	
	
</body>
</html>