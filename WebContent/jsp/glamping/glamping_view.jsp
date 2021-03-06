<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
request.setAttribute("contextPath", request.getContextPath());
%>



<!DOCTYPE html>

<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/board_view.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ebde98a774affbd3e0e745441dd9db87&libraries=services"></script>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<meta charset="UTF-8">
<title>캠핑홀릭-글램핑</title>
<script>(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.0&appId=494744677817831";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));</script>

<script type="text/javascript">
	var gl_num = ${glampingView.glampingBoard.GL_NUM};
	var m_num = <%=session.getAttribute("memberNum")%>
	var board_memberNum = ${glampingView.glampingBoard.M_NUM};

	$(document).ready(function(){
		if(m_num==0){
			document.getElementById('replyContent').readOnly = true;
			document.getElementById('replyContent').placeholder = '로그인후에 이용가능한 서비스입니다.';
			document.getElementById('reply_btn').disabled = 'disabled';
			
		}

	})
	
	$(function(){
		getReplyList();
 		$("#replyForm").on("submit",function(){
		var replyContent = $("#replyContent").val();
 			
 			
			$.ajax({
				url : "${contextPath}/glampingReply/replyInsert",
				data : {"gl_num" : gl_num,
						"replyContent" : replyContent,
						"m_num": m_num
					
						},
				dataType : "json",
				type : "post",
				success : function(data){
					if(data.result){
						alert("댓글등록");
						getReplyList();
						/*여기서 댓글 리스트 불러와 줘야 */
					}else{
						alert("댓글 등록 실패");
					}
					
				},
				
				error :function(){
					alert("request : "+request+"\n"+ "status"+status+"\n"+"error"+error);
				}

				
			});
 			
 			
			return false;
 		});
 
 		
 		$(".deleteButton").on("click",function(){
 			if(confirm("삭제하시겠습니까?")){
 				location.href="glampingDelete?num=${glampingView.glampingBoard.GL_NUM}"
 			}else{
 				alert("삭제 취소되었습니다");
 				return;
 			}
 		});
 		
 		
 		
 		$('input[type="text"]').keydown(function() {
 			  if (event.keyCode === 13) {
 			    event.preventDefault();
 			  };
 			});
 		$("#reply_btn").on("click",function(){
 			var replyContent = $("#replyContent").val();
 			if(replyContent==""){
 				alert("메시지를 입력해주세요");
 			return false;
 				
 			}
 		});
 		
 		$("#writeButton").click(function(){
			if(m_num==0){
				alert("로그인후 이용해주세요");
				return false;
			}else{	
				location.href= "../glamping/glamping_writeView";
			}
		});
 		
	});
	function getReplyList(){
			var replyContainer = $("#replyContainer");
			replyContainer.html("");
			$.ajax({
				url : "${contextPath}/glampingReply/getReplyList",
				type : "get",
				data : {"gl_num" : gl_num},
				dataType : "json",
				success : function(data){
					var html = "";
					$(data).each(function(){
						var rNum = this.GBR_NUM;
						var content = this.CONTENT;
						var nickName = this.NICKNAME;
						var regDate = this.WRITEDATE;
						var date = new Date(regDate);
						var y = date.getFullYear();
						var m = date.getMonth()+1;
						var d = date.getDate();
						
						
						 html += '<tr id="tableTr">';
				         html += '<td id="tableUserName">'+nickName+'</td>';
				         html += '<td id="tableContent"><div id="replyContentList">'+content+'</div></td>';
				         html += '<td id="regDate"><small>'+y+'- '+m+'- '+d+'</td>';
						
				         if(m_num==this.M_NUM){
				        	 html += '<td><button type="button" id="btn-replyDelete" style="border: solid 1px #1f8dd6; background-color: #1f8dd6; color:white; margin-left: 20px;"'+
							    ' onclick="deleteBtn('+rNum+')">삭제</button></td></tr>';
				         }
				         replyContainer.html(html);
				         
					});
					
					
					
				},
				error : function(request, status, error) {
					alert("request :" + request + "\n" + "status :"
							+ status + "\n" + "error :" + error);
				}
				
			});

	}
	
	
	
	
	function deleteBtn(rNum){
		//댓글 번호 받아와서 삭제
		if(confirm("삭제하시겠습니까?")){
		}else{
			alert("삭제 취소되었습니다.");
		return;
			
		}
		
		$.ajax({
			url : "${contextPath}/glampingReply/deleteReply",
			data : {"gbr_num" : rNum,
					"gl_num" : gl_num,
					"m_num" : m_num
			
			},
			type : "post",
			dataType : "json",
			success : function(result){
				if(result){
					alert("댓글 삭제");
					getReplyList();
					
				}else{
					alert("삭제 실패");
				}
				
				
			},
			error : function(request, status, error) {
				alert("request :" + request + "\n" + "status :"
						+ status + "\n" + "error :" + error);
			}
			
			
			
		});
		
		
	}
	
	
 function like_func(){
	
	$.ajax({
		url : "${contextPath}/glamping/checkLike",
		data : {"gl_num" : gl_num,
			    "m_num" : m_num
				},
		cacth : false,
		type : "post",
		dataType : "json",
		success : function(data){
			var likeImgTag = '';
			
	
		if (data.result) {
						var likeUser = Number($("#likeCount").text());
						likeUser += 1;
						$("#likeCount").html(likeUser);
						likeImgTag = "♥";
	
					} else {
						var likeUser = Number($("#likeCount").text());
						likeUser -= 1;
						$("#likeCount").html(likeUser);
	
						likeImgTag = "♡";
					}
	
					$(".checkHeart").text(likeImgTag);
				},
				error : function(request, status, error) {
	
					alert("request : " + request + "\n" + "status" + status + "\n"
							+ "error" + error);
	
				}

		});

	}
 
 function facebookUrl(){
		
		FB.ui({
	        method       : 'share_open_graph',
	        action_type: 'og.shares',
	        action_properties: JSON.stringify({
	            object: {       
	                'og:title': '캠핑을 떠나보자',
	                'og:image': '${contextPath}/img/main_img.jpg',
	                'og:url': 'http://15.165.161.115:8080/CampingHolic/autoCamping/autoCampingView?ac_num='+'${glampingView.glampingBoard.GL_NUM}',
	                'og:description': '캠핑을 떠나보자',
	            	'og:type' : 'article',
	            }
	        })
	    });
		
	}
	

	  Kakao.init('d969de59a43d643d6d0eb474511d8cf8');
	function kakaoUrl(){
	  Kakao.Link.sendDefault({
		    objectType: 'feed',
		    content: {
		      title: '캠핑홀릭으로 떠자나',
		      imageUrl: "https://file.okky.kr/images/1513127807083.jpg",
		      
		      link: {             
		        mobileWebUrl: 'http://15.165.161.115:8080/CampingHolic/autoCamping/autoCampingView?ac_num='+'${glampingView.glampingBoard.GL_NUM}',
		        webUrl: 'http://15.165.161.115:8080/CampingHolic/autoCamping/autoCampingView?ac_num='+'${glampingView.glampingBoard.GL_NUM}'
		      }
		    }
		  });
	  
		}


	function twitterUrl(){
		
		    var content = "[캠핑홀릭] "
		    var link = 'http://15.165.161.115:8080/CampingHolic/autoCamping/autoCampingView?ac_num='+'${glampingView.glampingBoard.GL_NUM}';
		    var popOption = "width=370, height=518, resizable=no, scrollbars=no, status=no;";
		    var wp = window.open("http://twitter.com/share?url=" + encodeURIComponent(link) + "&text=" + encodeURIComponent(content), 'twitter', popOption);
		    if (wp) {wp.focus();}    
		
	}
 
 	
</script>
</head>
<body>

	<div>
		<jsp:include page="/jsp/CampingHolic_header.jsp"></jsp:include>
	</div>

	<div style="text-align: center;">
		<img src="${contextPath}/img/glamping_main.jpg"
			style="width: 1880px; height: 500px;"></img>
	</div>
	<div class="container">
		<br>
		<br>
		<br>
		<br>
		<br>

		<div>
			<span id="title">${glampingView.glampingBoard.GL_TITLE}</span>
		</div>
		<div class="write_na">
			작성자: ${glampingView.glampingBoard.NICKNAME}
			<div style="display: inline-block; text-align: right; float: right;">
				<span style="margin-right: 180px;">조회 수:
					${glampingView.glampingBoard.READCOUNT}</span>
			</div>
		</div>
		<div class="write_date">
			<fmt:parseDate var="parseRegDate"
				value="${glampingView.glampingBoard.WRITEDATE}" pattern="yyyy-mm-dd" />
			<fmt:formatDate var="resultRegDt" value="${parseRegDate}"
				pattern="yyyy-mm-dd" />
			<span>작성일 : ${resultRegDt}</span>
		</div>

		<hr style="margin-bottom: 70px">
		<div style="text-align: center;">${glampingView.glampingBoard.GL_CONTENT}</div>

		<br>

		<div
			style="text-align: center; margin-right: 80px; margin-bottom: 15px;">
			<c:choose>
				<c:when test="${glampingView.liketo == 0}">
					<a href="javascript:like_func();" class="checkHeart1"><span
						class="checkHeart">♡</span></a>
					<br>
					<span id="likeCount">${glampingView.likeCount}</span>
				</c:when>
				<c:otherwise>
					<a href="javascript:like_func();" class="checkHeart1"><span
						class="checkHeart">♥</span></a>
					<br>
					<span id="likeCount">${glampingView.likeCount}</span>
				</c:otherwise>
			</c:choose>
		</div>
		<div
			style="width: 95%; text-align: center; margin-bottom: 64px; margin-top: 40px;">
			<a href="javascript:facebookUrl();" class="fb" title="facebook 공유"><img
				src="../img/Facebook.png" /></a> <a href="javascript:kakaoUrl();"
				class="kakao" title="kakao 공유"><img
				src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"></a>
			<a href="javascript:twitterUrl();" class="tw" title="twitter 공유"><img
				src="../img/Twitter.png" /></a>
		</div>


		<div>
			<div class="map_box" id="map"
				style="width: 50%; height: 350px; float: right;"></div>
		</div>
		<div class="address_box">
			<span id="address">${glampingView.glampingBoard.GL_ADDRESS}</span>
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
				
			    
			    geocoder.addressSearch('${glampingView.glampingBoard.GL_ADDRESS}', function(result, status) {

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
			    </script>
		<div style="width: 100%; height: 380px; display: table-cell;">
			<div class="w3-content w3-display-container" id="display-container"
				style="height: 434px; width: 680px; clear: both; float: left;">

				<c:forEach items="${glampingView.imgList}" var="fileList">
					<img src="${contextPath}${fileList.GLI_IMGFILENAME}">
				</c:forEach>

				<button class="w3-button w3-black w3-display-right"
					onclick="plusDivs(1)">&#10095;</button>
				<button class="w3-button w3-black w3-display-left"
					onclick="plusDivs(-1)">&#10094;</button>
			</div>
			<script>
				$("#display-container > img").each(function(){
					$(this).attr('class','imgTag');
				})
				var slideIndex = 1;
				showDivs(slideIndex);
				
				function plusDivs(n) {
				  	showDivs(slideIndex += n);
				}
				
				function showDivs(n) {
					var i;
					var x = document.getElementsByClassName("imgTag");
					
					if (n > x.length) {slideIndex = 1}    
					if (n < 1) {slideIndex = x.length}
					for (i = 0; i < x.length; i++) {
					   x[i].style.display = "none";  
					}
					x[slideIndex-1].style.width = "470px";
					x[slideIndex-1].style.height = "427px";
					x[slideIndex-1].style.margin = "auto";
					
					x[slideIndex-1].style.display = "block";
				}
			</script>
		</div>



		<div style="text-align: center;">
			<span class="userNameText"
				style="font-size: 17px; font-weight: bold;">${nickName}</span>
			<form name="replyForm" id="replyForm">
				<input type="hidden" name="M_NUM" id="replyWriter"
					value=<%=session.getAttribute("memberNum")%>> <input
					type="text" name="CONTENT" id="replyContent"
					placeholder="내용을 입력해주세요." style="width: 65%; height: 83px;">
				<input type="submit" id="reply_btn"
					style="border: solid 1px #1f8dd6; background-color: #1f8dd6; width: 70px; height: 80px; color: white;"
					value="등록">
			</form>
			<br>
		</div>


		<div class="container-1">
			<table id="replyContainer">
			</table>
		</div>

		<div class="underButton">
			<input type="button" id="writeButton" class="order_button"
				value="새글 작성">
			<c:if test="${memberNum == glampingView.glampingBoard.M_NUM}">
				<input type="button" class="order_button" value="수정"
					onclick="location.href='glampingModifyForm?num=${glampingView.glampingBoard.GL_NUM}'">
				<input type="button" class="order_button" value="삭제">
			</c:if>
			<input type="button" class="order_button" value="목록"
				onclick="location.href='glamping_main'">
		</div>

	</div>
	<div><%@include file="../CampingHolic_footer.jsp"%></div>
</body>
</html>