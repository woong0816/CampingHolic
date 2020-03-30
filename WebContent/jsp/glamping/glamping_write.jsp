<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%
    request.setAttribute("contextPath", request.getContextPath()); 
 %>
<!DOCTYPE html>
<html>
<head>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
 <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ebde98a774affbd3e0e745441dd9db87&libraries=services"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/board_write.css">
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${contextPath}/css/modal_open.css"> 
</head>
<body>
	 <div>
	<jsp:include page="/jsp/CampingHolic_header.jsp"></jsp:include>
	</div> 
 	
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
	<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.js"></script>
	<script src="${contextPath}/plugins/summernote/summernote-ko-KR.js"></script>
	
<script type="text/javascript">
$(document).ready(function(){
	$('#summernote').summernote({
		width : 1000,
		height : 600,
        lang : 'ko-KR',
        callbacks : {
        	onImageUpload : function(files, editor,	welEditable){
        		for(var i = files.length - 1; i>=0; i--){
        			sendFile(files[i], editor,welEditable);	
        		}

        	}
        }

	});
	
	
}); 
$(function(){
	$('input[type="text"]').keydown(function() {
		  if (event.keyCode === 13) {
		    event.preventDefault();
		  };
		});
	
})
	function sendFile(file , editer, welEditable){
		
		/* ajax 로 파일 업로드 처리*/
		data = new FormData();
		data.append("file",file);
	
		$.ajax({
			data : data,
			type : "post",
			dataType : "json",
			url : "imgUpload",
			cache: false,
        	contentType: false,
        	processData : false,
        	
        	success : function(result){
        
        		var $summernote = $('#summernote');
				if (result.imgWidth > 780) {
					var changeWidth = 780;
					var changeHeight = (changeWidth * result.imgHeight) / result.imgWidth;
					console.log("changeWidth -> " + changeWidth);
					console.log("changeHeight -> " + changeHeight);
					
					var img = $("<img src='${contextPath}/upload/" + result.fileName+"' style='width: " + changeWidth + "px; height: " + changeHeight + "px;''>")[0];
				} else {
					var img = $("<img src='${contextPath}/upload/" + result.fileName+"'>")[0];
					
				}
				$summernote.summernote('insertNode', img);
        	},
        	error : function(e) {
				$.each(e, function(idx, val) {
					console.log(idx + " " + val.title);
				});
			}
			
		});
	}
	
	function SearchAddress(num){
		var addressVal =  $('#addressSearch').val();
		var pageNum = num;
		
		$.ajax({
			data : {"addressVal" : addressVal,
					"pageNum" : pageNum
				},
			type : "post",
			url  : "addressSearch",
			dataType : "json",
			success : function(data){
				
		//	alert(data.addressList[0].pageTotalCount);
				for(var i=0; i<data.addressList.length; i++){
					var tr = $("<tr class='glampingwrite_tb' style='border-bottom: 1px solid grey;' onclick='showmap(this);' data-road='"+data.addressList[i].roadAddress+"'>"); 
					
					$("<td class='glampingwrite_tb' style='width:20px;text-align: center;'>").html(Number(i)+1).appendTo(tr);
					$("<td class='glampingwrite_tb' style='text-align: center;'>").html(data.addressList[i].title).appendTo(tr);
					$("<td class='glampingwrite_tb' style='text-align: center;'>").html(data.addressList[i].category).appendTo(tr);
					$("<td class='glampingwrite_tb' style='text-align: center;'>").html(data.addressList[i].telephone).appendTo(tr);
					$("<td class='glampingwrite_tb' style='text-align: center;'>").html(data.addressList[i].roadAddress).appendTo(tr);
					
					tr.appendTo(glampingwrite_tb);	
					
				}
			},
			error : function(){
				
			}
			
			
			
			
			
		});
		
		
	}
	

</script>
	
		
		<div style="background-image: url(../img/MainImg.jpg); background-size: cover; width: 100%; height: 200px; top:0; margin: auto; float: inherit; overflow: auto;"> </div>
		<div class="container">
		<form action="glampingWrite" method="post">
			<br>
				<div class="title_box">
					<input type="text" class="title" name="gl_title" id="gl_title" value="글램핑 제목을 입력해주세요" onfocus="this.value=''; return true;">
					<input type="hidden" id="gl_address" name="gl_address">
				</div>
					<div style="text-align: -webkit-center;"><textarea id="summernote" name="gl_content" rows="15" cols="70" ></textarea></div>
				<div id="search_box">					
					<input type="text" name="addressSearch" id="addressSearch">
					<button type="button" onclick="SearchAddress();">주소검색</button>
				</div>
					<table id="glampingwrite_tb" style="width: 550px; border: 1px solid grey; float: left;">
						<tr style="border-bottom:  1px solid grey; height: 40px;">
							<th style="text-align: center; width:8%;">번호</th>
							<th style="text-align: center; width:23%;">상호명</th>
							<th style="text-align: center; width:23%;">구분</th>
							<th style="text-align: center; width:23%;">전화번호</th>
							<th style="text-align: center; width:23%;">주소</th>
						</tr>
					</table>
		
				<div id="map" style="width: 50%; height:490px; float: right;"></div>	
		
		<script>
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			    mapOption = { 
			        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			        level: 3 // 지도의 확대 레벨
			    };
			var map = new kakao.maps.Map(mapContainer, mapOption); 
			
			var geocoder = new kakao.maps.services.Geocoder();
			
			  var marker = new daum.maps.Marker({
		        	position: new daum.maps.LatLng(37.537187, 127.005476),
		        	map: map
		    	});
			
			function showmap(tr){
			
				var tmpMap = $(tr).attr('data-road');
				//var tempMaproad = $(tr).attr('data-road');

			
				$("#gl_address").val(tmpMap);
				
				geocoder.addressSearch(tmpMap, function(result, status) {

				    // 정상적으로 검색이 완료됐으	면 
				     if (status === kakao.maps.services.Status.OK) {

				        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

				        // 결과값으로 받은 위치를 마커로 표시합니다
				        var marker = new kakao.maps.Marker({
				            map: map,
				            position: coords
				        });

				        // 인포윈도우로 장소에 대한 설명을 표시합니다
				       

				        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
				        map.setCenter(coords);
				    } 
				});
			}
			
    </script>
		<div class="under_btn">
			<input type="submit" value="등록">&nbsp;&nbsp;
			<input type="button" value="목록" onclick="location.href='glamping_main'"> &nbsp;&nbsp;
			<input type="reset" value="다시작성">
		</div>
		</form>
		
		<br>
</div>		
</body>

</html>