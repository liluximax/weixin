<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
<script type="text/javascript" src="jquery-2.2.1.min.js"></script>
</head>

<body>
	<session id="main"></session>
</body>

	<script type="text/javascript">
	
	var url = "/Weixin/com/baidu/util/Jason";
	
	$.ajax({
		url:url,
		type:"GET",
		data:{city:encodeURI("北京市","utf-8")},
		dataType:"json",
		success:function(data){
			$.each(data.station_list,function(i,n){
				$("#main").append("<ul>");
       			var lat = n.latitude;
       			var lng = n.longitude;
       			var id = n.station_id;
       			var name = n.name;
       			var adress = n.address;
								
				var lat_content = $("<li>"+lat+"</li>");
        		var lng_content = $("<li>"+lng+"</li>");
        		var id_content = $("<li>"+id+"</li>");
        		
         		var name_content =$("<a href='dolist.jsp?lat="+lat+"&lng="+lng+"'>"+name+"</a>");
        		var adress_content = $("<li>地址:"+adress+"</li>");
        		var tel = $("<li>联系电话:</li>");
        		
        		$("#main").append(id_content).append(name_content).append(adress_content).append(tel).append("</ul>");
			})
		}
	})
	
</script>



</html>