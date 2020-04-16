<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/message.css">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>
	
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

	$(function(){
		$(".title").click(function(){
			alert("1");
			var view_tr = $(this).parent().next();
			
			$(view_tr).show();
		});
		
		$(".close").click(function(){
			$(this).parents(".content").hide();
		});	
		
		
		$("#writeMsg").on("click",function(){
			window.open("../message/sendmsgForm","","width=500, height=500");
		});
		
		
	});



</script>


</head>
<body>

	<header>
		<%@include file="../CampingHolic_header.jsp" %>
	</header>
	
<div style="width: 1400px; margin:0 auto;">
		<div style="text-align: center;">
			<img src="${contextPath}/img/main_img.jpg" style="width: 1400px; height:500px;"></img>
		</div>
		<sidebar>
			<%@ include file="/jsp/message/Message_sidebar.jsp"%>
		</sidebar>


<div id="mysendbox">
			<h2>내가 보낸 메시지</h2>
			<input type="button" id="writeMsg" value="쪽지 쓰기" class="btn btn-default">
			<br>
			<table class="table table-hover">
				<tr>
					<th width="100px">번호</th>
					<th width="150px">받는사람</th>
					<th width="300px">제목</th>
					<th width="150px">작성일</th>
					<th width="150px">수신확인</th>
				</tr>
				<c:choose>
				<c:when test="${viewData.messageList eq '[]' }">
					<td colspan="5" style="text-align:center; height: 50px; padding-top:15px;">보낸 쪽지가 없습니다.</td>
				</c:when>
				<c:otherwise>
				
				<c:forEach items="${viewData.messageList }" var="msg">
					<tr>
						<td class="msg_num" >${msg.MSG_NUM }</td>
						<td class="nick">${msg.NICKNAME }</td>
						<td class="title">${msg.MSG_TITLE }</td>
						<td class="center">${msg.POINTDATE }</td>
						<td class="center"><c:choose><c:when test="${msg.CHKYN eq 'N' }">읽지 않음</c:when>
							<c:otherwise>읽음</c:otherwise></c:choose></td>
					</tr>
					<tr class="content" style="display: none">
						<td colspan="5" id="msg_content${msg.MSG_NUM }">${msg.MSG_CONTENT }
						<input type='button' value='닫기' class='close'></td>
					</tr>
				</c:forEach>
				</c:otherwise>
				</c:choose>
			</table>
			<br>
		<div class="page_number">
			<div>
				<br>
					<div class="paging">
						<c:if test="${viewData.startPage !=1 }">
							<a class="direction fisrt" href = "send?page=1
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							">&lt; 처음</a>
							<a class="direction prev" href = "send?page=${viewData.startPage-1}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							">&lt; 이전</a>
						</c:if>
						<c:forEach var = "pageNum" begin="${viewData.startPage}" end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
							<c:choose>
								<c:when test="${pageNum == viewData.currentPage}">
									<strong><span class="current">${pageNum}</span></strong>
								</c:when>
								<c:otherwise>
									<a href="send?page=${pageNum}
											<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
											<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
											<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
									">${pageNum}</a>	
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test = "${viewData.endPage < viewData.pageTotalCount}">
							<a class="direction next" href = "send?page=${viewData.endPage+1}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							">다음 &gt;</a>
							<a class="direction last" href = "send?page=${viewData.pageTotalCount}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							">&nbsp;끝&nbsp; &gt;</a>
						</c:if>
					</div>
				<br>
			</div>
		</div>
		</div>
</div>
</body>
</html>