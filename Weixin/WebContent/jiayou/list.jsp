<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<title>油站列表</title>
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc"></script>
<link href="/Weixin/css/list.css" rel="stylesheet" />
</head>
<body>
	<div>
	    <form action="map.jsp">
	        <span class="city">定位城市:</span>
	        <span class="cityName"></span>
	        <span><img style="height: 8%; width: 8%; float: right" src=""></span>
	    </form>
	</div>
	<hr>
	<div class="list"></div>
</body>

<script type="text/javascript">
	var code = '<%=request.getParameter("code") %>';
	var state = '<%=request.getParameter("state") %>';
	var url = "/Weixin/userinfo/getuserinfo.do";
	$.getJSON(url,{"code":code, "state":state},function(data){
		var nickname = data.nickname;
		var headimgurl = data.headimgurl;
		var openid = data.openid;
		$("img").attr("src",headimgurl);
	});
</script>

<script type="text/javascript">
    function myFun(result){
    	var lat = 0;
    	var lng = 0;
        var cityName = result.name;
        $(".cityName").text(cityName);
    	var geolocation = new BMap.Geolocation();
    	geolocation.getCurrentPosition(function(r){
    	    if(this.getStatus() == BMAP_STATUS_SUCCESS){
    	    	lng = r.point.lng;
    	    	lat = r.point.lat;
    	        var url = "/Weixin/station/calculate.do";
    	        //在传参数前一定要对城市名，进行utf-8转码。
    	        //下面的方法，在后台仍然打印不出中文，但是功能不影响
    	        $.getJSON(url, {city:encodeURI(cityName,"utf-8"), "lng":lng, "lat":lat}, function(data){
    	        	$.each(data.station_list,function(index,item){
    	        		$(".list").append("<ul class='single'>");
    	        		/*
    	        		!!!接口中经纬度标反了!!!
    	        		*/
    	        		var lng = item.latitude;
    	        		var lat = item.longitude;
    	        		var id = item.station_id;
    	        		var name = item.name;
    	        		var address = item.address;
    	        		var distance = item.distance;
    	        		
    					var lat_content = $("<li>"+lat+"</li>");
    	        		var lng_content = $("<li>"+lng+"</li>");
    	        		var id_content = $("<li>"+id+"</li>");
    	                var name_content = 
    	                    $("<form action='dolist.jsp' method='post'>"
    	                    +"<input id='lng' name='lng' type='hidden' value="+lat+">"
    	                    +"<input id='lat' name='lat' type='hidden' value="+lng+">"
    	                    +"<input id='id' name='id' type='hidden' value="+id+">"
    	                    +"<input id='name' name='name' type='hidden' value="+name+">"
    	                    +"<input id='adress' name='adress' type='hidden' value="+address+">"
    	                    +"<input class='name' type='submit' align='center' value="+name+">"
    	                    +"</form>");
    	                /* var name_content = "<a id='dolist' href='dolist.jsp'>"+name+"</a>"; */
    	        		var adress_content = $("<li><span class='adress'>地址:</span>"+address+"</li>");
    	        		var tel = $("<li class='tel'>联系电话:</li>");
    	        		var distance_content = $("<li class='adress'>距您约<span class='distance'>"+distance+"</span>公里</li>");
    	        		
    	        		$(".list").append(id_content).append(name_content).append(adress_content).append(tel).append(distance_content).append("</ul><hr>");
    	        		
    	        	})
    	        })
    	 		
    	    }
    	    else {
    	        alert('failed'+this.getStatus());
    	        return "**";
    	    }
    	},{enableHighAccuracy: true});
    }
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
</script>

<%--
  
这段js代码，试图通过百度地图的坐标转换接口，对位置修正后，再去计算距离。
可是调了好久，总是不进入回调函数
故，在后台自己重写坐标修正算法

<script type="text/javascript">
	function myFun(result){
		var lat_loc = 0;
		var lng_loc = 0;
	    var cityName = result.name;
	    $(".cityName").text(cityName);
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
		    if(this.getStatus() == BMAP_STATUS_SUCCESS){
		    	lng_loc = r.point.lng;
		    	lat_loc = r.point.lat;
		        var url = "/Weixin/spring/changeJson.do";
		        //在传参数前一定要对城市名，进行utf-8转码。
		        //下面的方法，在后台仍然打印不出中文，但是功能不影响
		        $.getJSON(url, {city:encodeURI(cityName,"utf-8"), "lng":lng_loc, "lat":lat_loc}, function(data){
		        	$.each(data.station_list,function(index,item){
		        		$(".list").append("<ul class='single'>");
		        		/*
		        		!!!接口中经纬度标反了!!!
		        		*/
		        		var lng = item.latitude;
		        		var lat = item.longitude;
		        		var id = item.station_id;
		        		var name = item.name;
		        		var address = item.address;

		        		var distance = 0;
		        		var point_real = new BMap.Point(lat, lng);
		        		var ponit_baidu = new BMap.Point(lat, lng);
		        		
		        	    translateCallback = function (data){
		        	        if(data.status === 0) {
								var lng_baidu = data.points[0].lng;
								var lat_baidu = data.points[0].lat;
								var url = "/Weixin/spring/calTest.do";
								alert(lng_baidu);
								/* $.get(url, {"lng":lng_baidu, "lat":lat_baidu, "lngLoc":lng_loc, "latLoc":lat_loc}); */
		        	      }
		        	    }
		        		var convertor = new BMap.Convertor();
		                var pointArr = [];
		                pointArr.push(point_real);
		                convertor.translate(pointArr, 1, 5, translateCallback);
		        		
						var lat_content = $("<li>"+lat+"</li>");
		        		var lng_content = $("<li>"+lng+"</li>");
		        		var id_content = $("<li>"+id+"</li>");
		                var name_content = 
		                    $("<form action='dolist.jsp' method='post'>"
		                    +"<input id='lng' name='lng' type='hidden' value="+lat+">"
		                    +"<input id='lat' name='lat' type='hidden' value="+lng+">"
		                    +"<input id='id' name='id' type='hidden' value="+id+">"
		                    +"<input id='name' name='name' type='hidden' value="+name+">"
		                    +"<input id='adress' name='adress' type='hidden' value="+address+">"
		                    +"<input class='name' type='submit' align='center' value="+name+">"
		                    +"</form>");
		                /* var name_content = "<a id='dolist' href='dolist.jsp'>"+name+"</a>"; */
		        		var adress_content = $("<li><span class='adress'>地址:</span>"+address+"</li>");
		        		var tel = $("<li class='tel'>联系电话:</li>");
		        		var distance_content = $("<li>"+distance+"</li>");
		        		
		        		$(".list").append(id_content).append(name_content).append(adress_content).append(tel).append(distance_content).append("</ul><hr>");
		        		
		        	})
		        })
		 		
		    }
		    else {
		        alert('failed'+this.getStatus());
		        return "**";
		    }
		},{enableHighAccuracy: true});
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
</script> --%>
</html>