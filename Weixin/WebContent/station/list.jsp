<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>油站列表</title>
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc"></script>
</head>

<body>
	<div>
		<p id="city">定位城市:</p>
		<form action="ditu.jsp">
			<input type="submit" value="地图">
		</form>
	</div>
	<div id="list"></div>
</body>

	<script type="text/javascript">
    function myFun(result){
        var cityName = result.name;
        $("#city").append(cityName);
        var url = "/Weixin/com/baidu/util/Jason";
        //在传参数前一定要对城市名，进行utf-8转码。
        //下面的方法，在后台仍然打印不出中文，但是功能不影响
        $.getJSON(url, {city:encodeURI(cityName,"utf-8")}, function(data){
        	$.each(data.station_list,function(index,item){
        		$("#list").append("<ul>");
        		
        		var lat = item.latitude;
        		var lng = item.longitude;
        		var id = item.station_id;
        		var name = item.name;
        		var adress = item.address;
        		
				var lat_content = $("<li>"+lat+"</li>");
        		var lng_content = $("<li>"+lng+"</li>");
        		var id_content = $("<li>"+id+"</li>");
        		var name_content =$("<a href='dolist.jsp?lat="+lat+"&lng="+lng+"'>"+name+"</a>");
        		var adress_content = $("<li>地址:"+adress+"</li>");
        		var tel = $("<li>联系电话:</li>");
        		
        		$("#list").append(id_content).append(name_content).append(adress_content).append(tel).append("</ul>");
        		
        	})
        })
    }
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
</script>
</html>