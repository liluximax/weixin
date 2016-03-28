<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
			<input type="submit" value="跳转">
		</form>
	</div>
	
	
</body>
</html>