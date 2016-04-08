<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>油站地理位置</title>
    <style type="text/css">
        html{height:100%}
        body{height:100%;margin:0px;padding:0px}
        #container{height:100%}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc">
        //v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥"
        //v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
    </script>
    <script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
    
</head>

<body>
<div id="container"></div>

<!-- <div id="self">
    <p id="p2"></p>
    <p id="p"></p>
    <p id="name"></p>
</div> -->

<script type="text/javascript">
    var map = new BMap.Map("container");          // 创建地图实例
    var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
    map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别 */

	//事件监听，显示当前位置坐标
/* 	map.addEventListener("click",function(e){
		$("#p2").text("点击点坐标:" + e.point.lng + "," + e.point.lat + " ");
	}); */
    
    function myFun(result){
        var cityName = result.name;
        $("#p").append(cityName + ",");
        var url = "/Weixin/station/changeJson.do";
        //在传参数前一定要对城市名，进行utf-8转码。
        //下面的方法，在后台仍然打印不出中文，但是功能不影响
        $.getJSON(url, {city:encodeURI(cityName,"utf-8")}, function(data){
        	$.each(data.station_list,function(index,item){
        		var lat = item.latitude;
        		var lng = item.longitude;
        		var id = item.station_id;
        		var name = item.name;
        		var adress = item.address;
        		
        		var point_target = new BMap.Point(lat, lng);
        		addMarker(point_target, id, name, adress);
        	})
        })
    }
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
	
    /* 定位模块 */
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

<script id="demo">

    function addMarker(point, id, name, adress){  // 创建图标对象

        var myIcon = new BMap.Icon("/Weixin/image/jiayouzhan.png",new BMap.Size(30,30));
		
    	//下面的回调函数是对坐标进行修正
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
    }
</script>
</body>
</html>