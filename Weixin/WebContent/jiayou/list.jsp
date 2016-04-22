<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<title>油站列表</title>
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc"></script>
<style type="text/css">
body{font-size: 90%}
.list{font-size: 100%}
li{list-style-type: none}
.adress{font-weight: bold}
.distance{color: red}
.cityName{font-weight: bold;
		  color: black;}
.tel{font-weight: bold}
.name{border-style: none;
	  background-color: white;
	  font-size: 105%;
	  color: #6FAA18;
	  outline: none;
	  /*下面这条清楚safri的默认input效果*/
 	  -webkit-appearance: none;
	  font-weight: bold;
 	  padding-left: 0px;
      padding-right: 0px;
      }
.map{border-style: none;
    background-color: white;
    font-size: 112%;
    color: blueviolet;
    outline: none;
    font-weight: bold;
    -webkit-appearance: none;
    }
.map_locate{
    float: right;
}
hr{
    background-color: #dddddd;
    height: 0.8px;
    border: none;
}
a{
	text-decoration: none;
}	
</style>
</head>
<body>
	<div>
	    <form action="map.jsp">
	        <span class="city">当前城市:<span class="cityName"></span></span>
	        <a href="citylist.jsp">切换城市</a>
	        <span><img class="headimg" style="height: 8%; width: 8%; float: right" src=""></span>
	    </form>
	</div>
	<hr>
	<div class="list"></div>
</body>

<%
request.setCharacterEncoding("utf-8");
response.setContentType("text/html;charset=utf-8");
%>

<!-- <script type="text/javascript">

	$(document).ready(
		function(){
			var geolocation = new BMap.Geolocation();
			geolocation.getCurrentPosition(function(r){
				if(this.getStatus() == BMAP_STATUS_SUCCESS){
					/*  alert('第一次定位：'+r.point.lng+','+r.point.lat); */
				}
				else {
					alert('failed'+this.getStatus());
				}        
			},{enableHighAccuracy: true})
		}		
	)
</script> -->

<script type="text/javascript">

	$.getJSON("/Weixin/userinfo/jssdk.do",{url:location.href.split('#')[0]}, function(data){
		
		var _appId = data.appId;
		var _signature = data.signature;
		var _timestamp = data.timestamp;
		var _nonceStr = data.noncestr;
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: data.appId, // 必填，公众号的唯一标识
		    timestamp: data.timestamp, // 必填，生成签名的时间戳
		    nonceStr: data.noncestr, // 必填，生成签名的随机串
		    signature: data.signature,// 必填，签名，见附录1
		    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		wx.ready(function () {
			    // 在这里调用 API
			wx.getLocation({
			    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			    success: function (res) {
			        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
			        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
			        var speed = res.speed; // 速度，以米/每秒计
			        var accuracy = res.accuracy; // 位置精度
			        /* alert(latitude+","+longitude); */
			        
			        var location = new BMap.Point(longitude, latitude);
			        translateCallback = function (data){
			            if(data.status === 0) {
			              /* map.setCenter(data.points[0]); */
			            
			  			var cityName = '<%=request.getParameter("cityName") %>';
						var lat = data.points[0].lat;
						var lng = data.points[0].lng;
						
						if(cityName == "null"){
					        var url = "/Weixin/station/calculate.do";
					        $.getJSON(url, {"lng":lng, "lat":lat}, function(data){
					        	if(data.station_list != null){
				    	        	cityName = data.city;
				    	        	$(".cityName").text(cityName);
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
				    	        		
				    	        		$(".list").append(name_content).append(adress_content).append(distance_content).append("</ul><hr>");
				    	        	})
					        	}
					        	else{
					        		var no_station = $("<p>当前城市没有合作加油站</p>");
					        		$(".list").append(no_station);
					        	}
					        })
							
						}
						else{
							
					        var url = "/Weixin/station/calculate.do";
					        $(".cityName").text(cityName);
					        //在传参数前一定要对城市名，进行utf-8转码。
					        //下面的方法，在后台仍然打印不出中文，但是功能不影响
					        $.getJSON(url, {city:encodeURI(cityName,"utf-8"),"lng":lng, "lat":lat}, function(data){
					        	if(data.station_list != null){
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
				    	        		
				    	        		$(".list").append(name_content).append(adress_content).append(distance_content).append("</ul><hr>");
				    	        	})
					        	}
					        	else{
					        		var no_station = $("<p>当前城市没有合作加油站</p>");
					        		$(".list").append(no_station);
					        	}
					        })
						}
						
						
			            }
			        }
			        
			        var convertor = new BMap.Convertor();
			        var pointArr = [];
			        pointArr.push(location);
			        convertor.translate(pointArr, 1, 5, translateCallback)
			        
			    }
			
			});
			    
		});
		
	});
</script>

<%-- <script type="text/javascript">
	
	$(document).ready(
		function() {
			var cityName = '<%=request.getParameter("cityName") %>';
			var lat = 0;
			var lng = 0;
			function core(){
				if(cityName == "null"){
					var geolocation = new BMap.Geolocation();
					geolocation.getCurrentPosition(function(r){
					    if(this.getStatus() == BMAP_STATUS_SUCCESS){
					    	lng = r.point.lng;
					    	lat = r.point.lat;
					        var url = "/Weixin/station/calculate.do";
					        //在传参数前一定要对城市名，进行utf-8转码。
					        //下面的方法，在后台仍然打印不出中文，但是功能不影响
					        /* alert('第二次定位：'+lng+','+lat); */
					        $.getJSON(url, {"lng":lng, "lat":lat}, function(data){
					        	if(data.station_list != null){
				    	        	cityName = data.city;
				    	        	$(".cityName").text(cityName);
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
				    	        		
				    	        		$(".list").append(name_content).append(adress_content).append(distance_content).append("</ul><hr>");
				    	        	})
					        	}
					        	else{
					        		var no_station = $("<p>当前城市没有合作加油站</p>");
					        		$(".list").append(no_station);
					        	}
					        })
					 		
					    }
					    else {
					        alert('failed'+this.getStatus());
					        return "**";
					    }
					},{enableHighAccuracy: true});
				}
				else{
					var geolocation = new BMap.Geolocation();
					geolocation.getCurrentPosition(function(r){
					    if(this.getStatus() == BMAP_STATUS_SUCCESS){
					    	lng = r.point.lng;
					    	lat = r.point.lat;
					        var url = "/Weixin/station/calculate.do";
					        $(".cityName").text(cityName);
					        //在传参数前一定要对城市名，进行utf-8转码。
					        //下面的方法，在后台仍然打印不出中文，但是功能不影响
					        $.getJSON(url, {city:encodeURI(cityName,"utf-8"),"lng":lng, "lat":lat}, function(data){
					        	if(data.station_list != null){
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
				    	        		
				    	        		$(".list").append(name_content).append(adress_content).append(distance_content).append("</ul><hr>");
				    	        	})
					        	}
					        	else{
					        		var no_station = $("<p>当前城市没有合作加油站</p>");
					        		$(".list").append(no_station);
					        	}
					        })
					 		
					    }
					    else {
					        alert('failed'+this.getStatus());
					        return "**";
					    }
					},{enableHighAccuracy: true});
				}
			}
			setTimeout(core, 800);
		}		
	)

</script> --%>

<%-- <script type="text/javascript">
	<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	%>
	
	var cityName = '<%=request.getParameter("cityName") %>';
	var lat = 0;
	var lng = 0;
	
	if(cityName == "null"){
		function myFun(result){
            cityName = result.name;
            /* alert("百度地图获得的定位城市: "+cityName); */
            /* var cityName = "sde"; */
            /* $(".cityName").text(cityName); */
        	var geolocation = new BMap.Geolocation();
        	geolocation.getCurrentPosition(function(r){
        	    if(this.getStatus() == BMAP_STATUS_SUCCESS){
        	    	lng = r.point.lng;
        	    	lat = r.point.lat;
        	        var url = "/Weixin/station/calculate.do";
        	        //在传参数前一定要对城市名，进行utf-8转码。
        	        //下面的方法，在后台仍然打印不出中文，但是功能不影响
        	        $.getJSON(url, {city:encodeURI(cityName,"utf-8"), "lng":lng, "lat":lat}, function(data){
        	        	if(data.station_list != null){
            	        	var city = data.city;
            	        	$(".cityName").text(cityName);
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
            	        		
            	        		$(".list").append(name_content).append(adress_content).append(distance_content).append("</ul><hr>");
            	        	})
        	        	}
        	        	else{
        	        		var no_station = $("<p>当前城市没有合作加油站</p>");
        	        		$(".list").append(no_station);
        	        	}
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
    }
	
    else{
    	var geolocation = new BMap.Geolocation();
    	geolocation.getCurrentPosition(function(r){
    	    if(this.getStatus() == BMAP_STATUS_SUCCESS){
    	    	lng = r.point.lng;
    	    	lat = r.point.lat;
    	        var url = "/Weixin/station/calculate.do";
    	       /*  $(".cityName").text(cityName); */
    	        //在传参数前一定要对城市名，进行utf-8转码。
    	        //下面的方法，在后台仍然打印不出中文，但是功能不影响
    	        $.getJSON(url, {city:encodeURI(cityName,"utf-8"), "lng":lng, "lat":lat}, function(data){
    	        	if(data.station_list != null){
    	        		var city = data.city;
        	        	$(".cityName").text(cityName);
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
        	        		
        	        		$(".list").append(name_content).append(adress_content).append(distance_content).append("</ul><hr>");
        	        	})
    	        	}
    	        	else{
    	        		var no_station = $("<p>当前城市没有合作加油站</p>");
    	        		$(".list").append(no_station);
    	        		alert("当前城市没有合作加油站");
    	        	}
    	        })
    	 		
    	    }
    	    else {
    	        alert('failed'+this.getStatus());
    	        return "**";
    	    }
    	},{enableHighAccuracy: true});
    }

</script> --%>

<script type="text/javascript">
	$(document).ready(
		function(){
			function head(){
				var code = '<%=request.getParameter("code") %>';
				var state = '<%=request.getParameter("state") %>';
				if(code != "null"){
					var url = "/Weixin/userinfo/getuserinfo.do";
					$.getJSON(url,{"code":code, "state":state},function(data){
						var nickname = data.nickname;
						var headimgurl = data.headimgurl;
						var openid = data.openid;
						$(".headimg").attr("src",headimgurl);
					});
				}
				var headimgurl = '<%=session.getAttribute("imageurl") %>';
				if(headimgurl != "null"){
					$(".headimg").attr("src",headimgurl);
				}
			}
			setTimeout(head, 500);
		}		
	)

</script>
</html>