<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc"></script>
	<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
	<title>点击油站地理位置</title>
</head>

<body>
	<div id = "allmap"></div>
</body>

<script type="text/javascript">
 	var map = new BMap.Map("allmap");
	<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	%>
 	var lat = '<%=request.getParameter("lat") %>';
 	var lng = '<%=request.getParameter("lng") %>';
 	var name = '<%=request.getParameter("name") %>';
 	var adress = '<%=request.getParameter("adress") %>';
 	var id = '<%=request.getParameter("id") %>';
 	
	var point = new BMap.Point(lat, lng);
    map.centerAndZoom(point, 15);
    var myIcon = new BMap.Icon("/Weixin/image/jiayouzhan.png",new BMap.Size(30,30));
    var marker = new BMap.Marker(point,{icon:myIcon});
    
    var form = 
        "<form action='domap.jsp' method='post'>"
        +"<input id='lng' name='lng' type='hidden'>"
        +"<input id='lat' name='lat' type='hidden'>"
        +"<input id='id' name='id' type='hidden'>"
        +"<input id='name' name='name' type='hidden'>"
        +"<input id='adress' name='adress' type='hidden'>"
        +"<input type='submit' align='center' value='跳转'>"
        +"</form>";
        
        var sContent = "<div id='info'></div>"+form;
        var infoWindow = new BMap.InfoWindow(sContent);
        marker.addEventListener("click", function () {
            map.openInfoWindow(infoWindow,point);
            document.getElementById("info").innerHTML = "名字: "+name+"<br>"+" 坐标:" + lng +","+lat;
            document.getElementById("lng").value = lng;
            document.getElementById("lat").value = lat;
            document.getElementById("id").value = id;
            document.getElementById("name").value = name;
            document.getElementById("adress").value = adress;
        });
    
    map.addOverlay(marker);
</script>

<!-- 调整标尺比例 -->
<script>
    var navigate = {offset: new BMap.Size(20, 40),anchor: BMAP_ANCHOR_BOTTOM_RIGHT,type: BMAP_NAVIGATION_CONTROL_SMALL};
    map.addControl(new BMap.NavigationControl(navigate));
    var scale = {offset: new BMap.Size(0, 12),anchor: BMAP_ANCHOR_BOTTOM_RIGHT}
    map.addControl(new BMap.ScaleControl(scale));
    map.addControl(new BMap.OverviewMapControl());
    //  map.addControl(new BMap.MapTypeControl());
</script>

</html>