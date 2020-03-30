<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    request.setAttribute("contextPath", request.getContextPath()); 
 %>
<html>
<head>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ebde98a774affbd3e0e745441dd9db87&libraries=services"></script>
	
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/board_write.css">
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.11/summernote-bs4.css" rel="stylesheet">

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
					var tr = $("<tr class='backCampingWrite_tb' style='border-bottom: 1px solid grey;' onclick='showmap(this);' data-road='"+data.addressList[i].roadAddress+"'>"); 
					
					$("<td class='backCampingWrite_tb' style='width:20px;text-align: center;'>").html(Number(i)+1).appendTo(tr);
					$("<td class='backCampingWrite_tb' style='text-align: center;'>").html(data.addressList[i].title).appendTo(tr);
					$("<td class='backCampingWrite_tb' style='text-align: center;'>").html(data.addressList[i].category).appendTo(tr);
					$("<td class='backCampingWrite_tb' style='text-align: center;'>").html(data.addressList[i].telephone).appendTo(tr);
					$("<td class='backCampingWrite_tb' style='text-align: center;'>").html(data.addressList[i].roadAddress).appendTo(tr);
					
					tr.appendTo(backCampingWrite_tb);	
					
				}
			},
			error : function(){
				
			}
		});
	}
 
 function goSubmit(){
	 $("#modify_Frm").submit();
 }
 

</script>
	<div
		style="background-image: url(../img/MainImg.jpg); background-size: cover; width: 100%; height: 200px; top: 0; margin: auto; float: inherit; overflow: auto;">
	</div>
<div class="container">
	<form action="backCampingModify" id="modify_Frm" method="post">
	<br>
	<div class="title_box">
	<input type="text" class="title" name="bc_title" id="bc_title" value="${backCampingModify.BC_TITLE}">
	<input type="hidden" id="bc_num" name="bc_num" value="${backCampingModify.BC_NUM}">
	<input type="hidden" id="bc_address" name="bc_address" value="${backCampingModify.BC_ADDRESS}">
	</div>
	
		<div style="text-align:-webkit-center;"><textarea rows="15" cols="70" id="summernote" name="bc_content">${backCampingModify.BC_CONTENT}</textarea></div>
	<div id="search_box">
		<input type="text" name="addressSearch" id="addressSearch">
		<button type="button" onclick="SearchAddress();">주소검색</button>
	</div>		
	<table id="backCampingWrite_tb" style="width: 550px; border: 1px solid grey; float: left;">
		<tr style="border-bottom: 1px solid grey; height: 40px;">
			<th style="text-align: center; width: 8%;">번호</th>
			<th style="text-align: center; width: 23%;">상호명</th>
			<th style="text-align: center; width: 23%;">구분</th>
			<th style="text-align: center; width: 23%;">전화번호</th>
			<th style="text-align: center; width: 23%;">주소</th>
		</tr>
	</table>
		<div id="map" style="width: 50%; height:490px; float: right;"></div>
				<div style="height: 24px; width: 600px; float: right; margin-top: 15px; text-align: center;">	
					<span style="font-size:20px; margin: auto">${backCampingModify.BC_ADDRESS}</span>
				</div>
	<script>
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div
		        mapOption = {
		            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
		            level: 3 // 지도의 확대 레벨
		        };
			    //지도를 미리 생성
			    var map = new daum.maps.Map(mapContainer, mapOption);
			    //주소-좌표 변환 객체를 생성
			    var geocoder = new daum.maps.services.Geocoder();
			    //마커를 미리 생성
			    var marker = new daum.maps.Marker({
		        	position: new daum.maps.LatLng(37.537187, 127.005476),
		        	map: map
		    	});
				
			    geocoder.addressSearch('${backCampingModify.BC_ADDRESS}', function(result, status) {

				    // 정상적으로 검색이 완료됐으면 
				     if (status === daum.maps.services.Status.OK) {

				        var coords = new daum.maps.LatLng(result[0].y, result[0].x);

				        // 결과값으로 받은 위치를 마커로 표시합니다
				        var marker = new daum.maps.Marker({
				            map: map,
				            position: coords
				        });

				        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
				        map.setCenter(coords);
				    } 
				});    
			    
			   function showmap(tr){
			    	
			    
				//$(".foodwrite_tr").on("click",function(){ 
					var tempMaproad = $(tr).attr('data-road');

					//alert(tempMaproad + " : " );
					$("#bc_address").val(tempMaproad);
					//alert($("#FR_ADDRESS").val());
					geocoder.addressSearch(tempMaproad, function(result, status) {

					    // 정상적으로 검색이 완료됐으면 
					     if (status === daum.maps.services.Status.OK) {

					        var coords = new daum.maps.LatLng(result[0].y, result[0].x);

					        // 결과값으로 받은 위치를 마커로 표시합니다
					        var marker = new daum.maps.Marker({
					            map: map,
					            position: coords
					        });

					        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					        map.setCenter(coords);
					    } 
					});    
			    //}); 
				
			    }
				</script>
		<div class="under_btn">
			<input onclick="goSubmit()" class="modity_btn" type="button" value="수정" > 
			<input type="button" value="목록" onclick="location.href='backCamping_mainView'">
 		</div>
		</form>
		
</div>
</body>
</html>