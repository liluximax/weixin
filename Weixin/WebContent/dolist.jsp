<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc"></script>
	<script type="text/javascript" src="jquery-2.2.1.min.js"></script>
	<title>浏览器定位</title>
</head>
<body>
	<div id = "allmap"></div>
</body>

<script type="text/javascript">
 	var map = new BMap.Map("allmap");
	var lat = '<%=request.getParameter("lat")%>';
	var lng = '<%=request.getParameter("lng")%>';
	var point = new BMap.Point(lat, lng);
    map.centerAndZoom(point, 15);

    var marker = new BMap.Marker(point);
    map.addOverlay(marker);
   /*  $("#p").append(lat).append("<br>").append(lng); */
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