<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.8, maximum-scale=1.5, user-scalable=no" />
<title>油站列表</title>
<style type="text/css">
        body{font-size: 90%}
        .list{font-size: 100%}
        li{list-style-type: none}
        .adress{font-weight: bold}
        .cityName{font-weight: bold}
        .tel{font-weight: bold}
        .name{border-style: none;
       		  background-color: white;
       		  font-size: 105%;
       		  color: #6FAA18;
       		  outline: none;
       		  -webkit-appearance: none;
       		  font-weight: bold;
       		  text-align:left}
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
</style>
<script type="text/javascript" src="../jquery-2.2.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=raLVAEr8zl02CFnroyu3C7Bc"></script>
<link href="/Weixin/css/changelist.css" rel="stylesheet" />
</head>

<body>
	<div>
	    <form action="map.jsp">
	        <span class="city">定位城市:</span>
	        <span class="cityName"></span>
	        <span class="map_locate">
	        	<input class="map" type="submit" value="地图">
	        </span>
	    </form>
	</div>
	<hr>
	<div class="list"></div>
</body>
	<script type="text/javascript">
    function myFun(result){
        var cityName = result.name;
        $(".cityName").text(cityName);
        var url = "/Weixin/com/baidu/util/Jason";
        //在传参数前一定要对城市名，进行utf-8转码。
        //下面的方法，在后台仍然打印不出中文，但是功能不影响
        $.getJSON(url, {city:encodeURI(cityName,"utf-8")}, function(data){
        	$.each(data.station_list,function(index,item){
        		$(".list").append("<ul class='single'>");
        		
        		var lat = item.latitude;
        		var lng = item.longitude;
        		var id = item.station_id;
        		var name = item.name;
        		var adress = item.address;
        		
				var lat_content = $("<li>"+lat+"</li>");
        		var lng_content = $("<li>"+lng+"</li>");
        		var id_content = $("<li>"+id+"</li>");
                var name_content = 
                    $("<form action='dolist.jsp' method='post'>"
                    +"<input id='lng' name='lng' type='hidden' value="+lng+">"
                    +"<input id='lat' name='lat' type='hidden' value="+lat+">"
                    +"<input id='id' name='id' type='hidden' value="+id+">"
                    +"<input id='name' name='name' type='hidden' value="+name+">"
                    +"<input id='adress' name='adress' type='hidden' value="+adress+">"
                    +"<input class='name' type='submit' align='center' value="+name+">"
                    +"</form>");
                /* var name_content = "<a id='dolist' href='dolist.jsp'>"+name+"</a>"; */
        		var adress_content = $("<li><span class='adress'>地址:</span>"+adress+"</li>");
        		var tel = $("<li class='tel'>联系电话:</li>");
        		
        		$(".list").append(id_content).append(name_content).append(adress_content).append(tel).append("</ul><hr>");
        		
        	})
        })
    }
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
</script>
</html>