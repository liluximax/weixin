<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<title>油站列表</title>
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
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

<script type="text/javascript">
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			alert('第一次定位：'+r.point.lng+','+r.point.lat);
		}
		else {
			alert('failed'+this.getStatus());
		}        
	},{enableHighAccuracy: true})
</script>

<script type="text/javascript">
	
	<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	%>
	var cityName = '<%=request.getParameter("cityName") %>';
	var lat = 0;
	var lng = 0;
	if(cityName == "null"){
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
		    if(this.getStatus() == BMAP_STATUS_SUCCESS){
		    	lng = r.point.lng;
		    	lat = r.point.lat;
		        var url = "/Weixin/station/calculate.do";
		        //在传参数前一定要对城市名，进行utf-8转码。
		        //下面的方法，在后台仍然打印不出中文，但是功能不影响
		        alert('第二次定位：'+lng+','+lat);
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
	
</script>


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
</script>
</html>