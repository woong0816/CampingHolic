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
<title>캠핑홀릭-글램핑</title>
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
					
					var img = $("<img src='/CampingHolic/imgUpload/" + result.fileName+"' style='width: " + changeWidth + "px; height: " + changeHeight + "px;''>")[0];
				} else {
					var img = $("<img src='/CampingHolic/imgUpload/" + result.fileName+"'>")[0];
					
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
 
 

 function goSubmit(){
	 	var title = $("#gl_title").val();
		var content = $("#summernote").val();
		var adress = $("#gl_address").val();
		
		if(title==""){
			alert("제목을 입력해주세요");
			return false;
		}else if(content==""){
			alert("내용을 입력해주세요");
			return false;
		}else if(adress==""){
			alert("장소를 입력해주세요");
			return false;
		}
	 $("#modify_Frm").submit();
 }
 
</script>


	<div
		style="background-image: url(../img/glamping_main.jpg); background-size: cover; width: 100%; height: 500px; top: 0; margin: auto; float: inherit; overflow: auto;">
	</div>
<div class="container">

			<form action="glampingModify" id="modify_Frm" method="post"><br>
		<div class="title_box">
			<input type="text" class="title" name="gl_title" id="gl_title" value="${glampingModifyView.GL_TITLE}">
			<input type="hidden" id="gl_num" name="gl_num" value="${glampingModifyView.GL_NUM}">
			<input type="hidden" id="gl_address" name="gl_address" value="${glampingModifyView.GL_ADDRESS}">
		</div>
		
			<div style="text-align: -webkit-center;"><textarea rows="15" cols="70" id="summernote" name="gl_content">${glampingModifyView.GL_CONTENT}</textarea></div>
		<div class="map_wrap" style="margin-top: 20px;">
    				<div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
				   	<div id="menu_wrap" class="bg_white">
				        <div class="option">
				            <div>
				                
				                    키워드 : <input type="text" value="강남" id="keyword" size="15"> 
				                  <button type="button" onclick="searchPlaces(); return false;">검색하기</button> 
				            </div>
				        </div>
				        <hr>
				        <ul id="placesList"></ul>
				        <div id="pagination"></div>
				    </div>
				</div>
	<script>
			var markers = [];
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
				    mapOption = { 
				        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
				        level: 2 // 지도의 확대 레벨
				    };
				var map = new kakao.maps.Map(mapContainer, mapOption); 

				
				
			
			// 장소 검색 객체를 생성합니다
			var ps = new kakao.maps.services.Places();  

			// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
			var infowindow = new kakao.maps.InfoWindow({zIndex:1});

			// 키워드로 장소를 검색합니다
			searchPlaces();

			// 키워드 검색을 요청하는 함수입니다
			function searchPlaces() {

			    var keyword = document.getElementById('keyword').value;

			    if (!keyword.replace(/^\s+|\s+$/g, '')) {
			        alert('키워드를 입력해주세요!');
			        return false;
			    }

			    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
			    ps.keywordSearch( keyword, placesSearchCB); 
			}

			// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
			function placesSearchCB(data, status, pagination) {
			    if (status === kakao.maps.services.Status.OK) {

			        // 정상적으로 검색이 완료됐으면
			        // 검색 목록과 마커를 표출합니다
			        displayPlaces(data);

			        // 페이지 번호를 표출합니다
			        displayPagination(pagination);

			    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

			        alert('검색 결과가 존재하지 않습니다.');
			        return;

			    } else if (status === kakao.maps.services.Status.ERROR) {

			        alert('검색 결과 중 오류가 발생했습니다.');
			        return;

			    }
			}

			// 검색 결과 목록과 마커를 표출하는 함수입니다
			function displayPlaces(places) {
				//검색 결과중에 장소의 결과값을 받아오
			    var listEl = document.getElementById('placesList'), 
			    menuEl = document.getElementById('menu_wrap'),
			    fragment = document.createDocumentFragment(), 
			    bounds = new kakao.maps.LatLngBounds(), 
			    listStr = '';
			    
			    // 검색 결과 목록에 추가된 항목들을 제거합니다
			    removeAllChildNods(listEl);

			    // 지도에 표시되고 있는 마커를 제거합니다
			    removeMarker();
				console.log("삭제되어야 함");	    
			    for ( var i=0; i<places.length; i++ ) {

			        // 마커를 생성하고 지도에 표시합니다
			        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
			            marker = addMarker(placePosition, i), 
			            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
			          
			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			        // LatLngBounds 객체에 좌표를 추가합니다
			        bounds.extend(placePosition);

			        // 마커와 검색결과 항목에 mouseover 했을때
			        // 해당 장소에 인포윈도우에 장소명을 표시합니다
			        // mouseout 했을 때는 인포윈도우를 닫습니다
			        (function(marker, title,itmelEl) {
							if(title!=places[i].address_name){
								kakao.maps.event.addListener(marker, 'mouseout', function() {
								
							
								 });
							}else{
							    	//infowindow.close();
									
							}

			            itemEl.onclick =  function () {
			         //여기서 좌표 중심으로 하고 이미지 변경 
			            	$("#gl_address").val(title);
			            	 var geocoder = new kakao.maps.services.Geocoder();
			            	// 주소로 좌표를 검색합니다
			            	geocoder.addressSearch(title, function(result, status) {
			            	    // 정상적으로 검색이 완료됐으면 
			            	     if (status === kakao.maps.services.Status.OK) {

			            	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

			            	    	 for(var i=0; i<places.length; i++){
						         		if(title==places[i].address_name){
						         			console.log(places[i].x);
						         			console.log(places[i].y);
						         			var coords = new kakao.maps.LatLng(places[i].y, places[i].x);
						         			displayInfowindow(marker,places[i].place_name);
						         					 var level = map.getLevel();
						         				   map.setLevel(2);
						         		}else{
						         			
			            	    		
						         		}
						         	}  
			            	       	

			            	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			            	        map.setCenter(coords);
			            	    } 
			            	});    
			            };

			        })(marker, places[i].address_name);

			        fragment.appendChild(itemEl);
			    }

			    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
			    listEl.appendChild(fragment);
			    menuEl.scrollTop = 0;

			    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			    map.setBounds(bounds);
			}

			// 검색결과 항목을 Element로 반환하는 함수입니다
			function getListItem(index, places) {
				
			    var el = document.createElement('li'),
			    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
			                '<div class="info">' +
			                '   <h5>' + places.place_name + '</h5>';

			    if (places.road_address_name) {
			        itemStr += '<span>' + places.road_address_name + '</span>' +
			                    '<span class="jibun gray">' +  places.address_name  + '</span>';
			    } else {
			        itemStr += '<span>' +  places.address_name  + '</span>'; 
			    }
			                 
			      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
			                '</div>';           

			    el.innerHTML = itemStr;
			    el.className = 'item';

			    return el;
			}

			// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
			function addMarker(position, idx, title) {
			    var imageSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
			        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
			        imgOptions =  {
			            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
			            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
			            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
			        },
			        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
			            marker = new kakao.maps.Marker({
			            position: position, // 마커의 위치
			            image: markerImage 
			        });

			    marker.setMap(map); // 지도 위에 마커를 표출합니다
			    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

			    return marker;
			}

			// 지도 위에 표시되고 있는 마커를 모두 제거합니다
			function removeMarker() {
			    for ( var i = 0; i < markers.length; i++ ) {
			        markers[i].setMap(null);
			    }   
			    markers = [];
			}

			// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
			function displayPagination(pagination) {
			    var paginationEl = document.getElementById('pagination'),
			        fragment = document.createDocumentFragment(),
			        i; 

			    // 기존에 추가된 페이지번호를 삭제합니다
			    while (paginationEl.hasChildNodes()) {
			        paginationEl.removeChild (paginationEl.lastChild);
			    }

			    for (i=1; i<=pagination.last; i++) {
			        var el = document.createElement('a');
			        el.href = "#";
			        el.innerHTML = i;

			        if (i===pagination.current) {
			            el.className = 'on';
			        } else {
			            el.onclick = (function(i) {
			                return function() {
			                    pagination.gotoPage(i);
			                }
			            })(i);
			        }

			        fragment.appendChild(el);
			    }
			    paginationEl.appendChild(fragment);
			}

			// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
			// 인포윈도우에 장소명을 표시합니다
			function displayInfowindow(marker, title) {
			    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';
		
			    infowindow.setContent(content);
			    infowindow.open(map, marker);
			    
			    //여기서 확대를 
			}

			 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
			function removeAllChildNods(el) {   
			    while (el.hasChildNodes()) {
			        el.removeChild (el.lastChild);
			    }
			}
    </script>
		<div class="under_btn">
			<input class="order_button" onclick="goSubmit()" type="button" value="수정" > 
			<input class="order_button" type="button" value="목록" onclick="location.href='glamping_main'">
 		</div>
		</form>
		
</div>	
<footer>
		<%@include file="../CampingHolic_footer.jsp" %>
	</footer>		
</body>
</html>