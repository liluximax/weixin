<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<style type="text/css">
html, body, nav, ul, li, h2, h3, figure, h1, p {
	padding: 0px;
	margin: 0px;
}

.name {
	border-style: none;
	background-color: white;
	font-size: 105%;
	color: white;
	outline: none;
	/*下面这条清楚safri的默认input效果*/
	-webkit-appearance: none;
	padding-left: 0px;
	padding-right: 0px;
}

body {
	min-width: 320px;
	font: normal 14px/1.5 Tahoma, "Lucida Grande", Verdana,
		"Microsoft Yahei", STXihei, hei;
	color: #000;
	overflow-x: hidden;
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0.5);
	-webkit-text-size-adjust: none;
	-moz-user-select: none;
}

img {
	border: none;
}

a {
	color: #000;
	text-decoration: none;
}

li {
	list-style-type: none;
}

header.jsmodule {
	position: relative;
	width: 100%;
	overflow: hidden;
	background: #fff;
}

.connect a {
	padding: 0 18px;
}

a.phone {
	color: #0E68D5;
}

.versions a {
	height: 12px;
	line-height: 12px;
	padding: 0 18px;
	border-right: 1px solid #000;
}

.connect a:last-child, .versions a:last-child {
	border-right: none;
}

.title {
	position: relative;
	z-index: 12;
	height: 30px;
	padding: 9px 10px 7px 0px;
	background-color: #099fde;
	line-height: 30px;
	color: #fff;
	font-size: 15px;
	overflow: hidden;
	text-align: center;
}

.title div {
	overflow: hidden;
	text-align: center;
	position: relative;
	height: 30px;
	line-height: 30px;
	color: #fff;
	font-size: 15px;
}

img {
	height: auto;
	width: auto\9;
	width: 100%;
}
/*main nav*/
.nav-list {
	margin: auto 8px 0 2px;
	overflow: hidden;
}

.nav-list li {
	position: relative;
	float: left;
	width: 33.333333%;
	border-left: 6px solid rgba(255, 255, 0, 0);
	margin-bottom: 6px;
	-webkit-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
}

.nav-list li:before {
	content: "\0020";
	position: absolute;
	z-index: 10;
	left: 50%;
	-webkit-transform: translate(-50%, 0);
	-ms-transform: translate(-50%, 0);
	transform: translate(-50%, 0);
}

.nav-list li div {
	width: 100%;
	height: 100%;
	line-height: 65px;
	border-radius: 3px;
	color: #fff;
	font-size: 16px;
	text-align: center;
}

.nav-list li div a {
	text-align: center;
	color: #FFF;
}

.nav-list li:nth-child(1), .nav-list li:nth-child(2), .nav-list li:nth-child(3),
	.nav-list li:nth-child(4), .nav-list li:nth-child(5), .nav-list li:nth-child(6)
	{
	height: 65px;
}

.nav-list li:nth-child(7), .nav-list li:nth-child(8), .nav-list li:nth-child(9)
	{
	height: 65px;
}

.nav-list li:nth-child(10), .nav-list li:nth-child(12), .nav-list li:nth-child(11)
	{
	height: 65px;
}

.nav-list li:nth-child(1) div {
	background-color: #41BCA4;
}

.nav-list li:nth-child(2) div {
	background-color: #f7386d;
}

.nav-list li:nth-child(3) div {
	background-color: #cbd6ae;
}

.nav-list li:nth-child(4) div {
	background-color: #bbcdb3;
}

.nav-list li:nth-child(5) div {
	background-color: #70ae95;
}

.nav-list li:nth-child(6) div {
	background-color: #EB6C9F;
}

.nav-list li:nth-child(7) div {
	background-color: #8dcd9b;
}

.nav-list li:nth-child(8) div {
	background-color: #76c2e9;
}

.nav-list li:nth-child(9) div {
	background-color: #2BE8B3;
}

.nav-list li:nth-child(10) div {
	background-color: #fec43e;
}

.nav-list li:nth-child(11) div {
	background-color: #fab6a0;
}

.nav-list li:nth-child(12) div {
	background-color: #FAD2A0;
}

.nav-list li:nth-child(1) div {
	background-color: #41BCA4;
}

.nav-list li:nth-child(2) div {
	background-color: #f7386d;
}

.nav-list li:nth-child(3) div {
	background-color: #cbd6ae;
}

.nav-list li:nth-child(4) div {
	background-color: #bbcdb3;
}

.nav-list li:nth-child(5) div {
	background-color: #70ae95;
}

.nav-list li:nth-child(6) div {
	background-color: #EB6C9F;
}

.nav-list li:nth-child(7) div {
	background-color: #8dcd9b;
}

.nav-list li:nth-child(8) div {
	background-color: #76c2e9;
}

.nav-list li:nth-child(9) div {
	background-color: #2BE8B3;
}

.nav-list li:nth-child(10) div {
	background-color: #fec43e;
}

.nav-list li:nth-child(11) div {
	background-color: #fab6a0;
}

.nav-list li:nth-child(12) div {
	background-color: #FAD2A0;
}

.nav-list li .icon-new {
	position: absolute;
	width: 18px;
	height: 9px;
	display: inline-block;
	right: 0;
	top: 0;
	background-position: -72px -215px;
}

#mainDiv {
/* 	overflow: hidden;*/
	background-image: url("test.png");
	background-size: 100% auto;
	background-repeat: no-repeat;
}
</style>
<title>开通城市列表</title>
</head>
<body>

	<div>
		<div id="nav">
			<span
				style="color: #0093D9; font-size: 18px; font-weight: bold; font-family: KaiTi_GB2312, KaiTi_GB2312;">&nbsp;</span>
			<nav class="city">
				<ul class="nav-list">
					<!--                 <li>
                    <div>
                        <form action='list.jsp' method='post'>
                            <input class="name" type="submit" style="background-color: #41BCA4" value="北京市">
                            <input name='cityName' type="hidden" value="北京市">
                        </form>
                    </div>
                </li>
                <li>
                    <div>
                        <form action='list.jsp' method='post'>
                            <input class="name" type="submit" style="background-color: #f7386d" value="上海市">
                            <input name='cityName' type="hidden" value="上海市">
                        </form>
                    </div>
                </li> -->
				</ul>
			</nav>
		</div>
	</div>

</body>
<%
request.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");
%>
<script type="text/javascript">
	var url = "/Weixin/station/city.do";
	$.getJSON(url,function(data){
		$.each(data.city_list,function(index,item){
			var city = item.cityname;
			var cityId = item.city_id;
/* 			var content = $("<li><div><form action='list.jsp' method='post'>"
							+"<input name='cityName' type='hidden' value="+city+">"
							+"<input name='cityId' type='hidden' value="+cityId+">"
							+"<input class='name' type='submit' value="+city+">"
							+"</form></div></li>"); */
			var content = $("<li>"
							+"<a href='list.jsp?cityId="+cityId+"'><div>"+city+"</div></a>"
							+"</li>");
			$(".nav-list").append(content);
		})
	});
</script>
</html>