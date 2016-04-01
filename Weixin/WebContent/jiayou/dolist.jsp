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
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
	    if(this.getStatus() == BMAP_STATUS_SUCCESS){
	        var locate = r.point;
	        var mk = new BMap.Marker(r.point);
	        map.addOverlay(mk);
	        mk.setAnimation(BMAP_ANIMATION_BOUNCE);
	        map.panTo(r.point);
	 		$("#p").append(" 经度: "+ r.point.lng +" 纬度: "+ r.point.lat);
	    }
	    else {
	        alert('failed'+this.getStatus());
	        return "**";
	    }
	},{enableHighAccuracy: true});
	
    var myIcon = new BMap.Icon("/Weixin/image/jiayouzhan.png",new BMap.Size(30,30));
    translateCallback = function (data){
        if(data.status === 0) {
        	
            var marker = new BMap.Marker(data.points[0],{icon:myIcon});
            
            var lng = point.lng;
            var lat = point.lat;

            var form = 
            "<form action='domap.jsp' method='post'>"
            +"<input id='lng' name='lng' type='hidden'>"
            +"<input id='lat' name='lat' type='hidden'>"
            +"<input id='id' name='id' type='hidden'>"
            +"<input id='name' name='name' type='hidden'>"
            +"<input id='adress' name='adress' type='hidden'>"
            +"</form>";
            
            var sContent = "<div id='info'></div>"+form;
            var infoWindow = new BMap.InfoWindow(sContent);
            marker.addEventListener("click", function () {
                map.openInfoWindow(infoWindow,data.points[0]);
                document.getElementById("info").innerHTML = name;
                document.getElementById("lng").value = lng.toFixed(3);
                document.getElementById("lat").value = lat.toFixed(3);
                document.getElementById("id").value = id;
                document.getElementById("name").value = name;
                document.getElementById("adress").value = adress;
            });
            map.addOverlay(marker);
        }
    }
    var convertor = new BMap.Convertor();
    var pointArr = [];
    pointArr.push(point);
    //3,5为google坐标转百度坐标
    //1,5为原始坐标转百度坐标
    convertor.translate(pointArr, 1, 5, translateCallback);
    
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