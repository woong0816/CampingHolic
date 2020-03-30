<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<header>
		<%@include file="../CampingHolic_header.jsp" %>
	</header>
	<div style="width: 1050px;">
		<sidebar>
			<%@include file="../message/Message_sidebar.jsp"%>
		</sidebar>

<div id="list">
			<h2>쪽지 메인</h2>
			<div id="outside">
				<input type="button" id="writeMsg" value="쪽지 쓰기">
				<div id="inside">
					<div id="recv" class="letterbox">받은 쪽지함</div>
					<div id="send" class="letterbox">보낸 쪽지함</div>
					<div id="rcvlist">
						<table id="rcvtable">
							<tr id="firsttr">
								<th width="10%">번호</th>
								<th width="15%">보낸사람</th>
								<th width="50%">제목</th>
								<th width="25%">작성날짜</th>
							</tr>
						</table>
					<div id="paging">
					</div>
 		<c:if test=" != 1"> 
 			<a href="received?page=1">처음</a>
 			<a href="received?page=startpage();-1">이전</a> 
		</c:if> 
 		<c:forEach var="pagenum" begin="startpage();" 
  			end="endpage(); < pagetotalcount(); ? endpage(); : pagetotalcount();">  
  			<c:choose>  
 				<c:when test="${pagenum} == currentpage();">  
 					<b>[${pagenum}]</b>   
 				</c:when>   
  				<c:otherwise> 
  					<a href="received?page=${pagenum}">[${pagenum}]</a> 
  				</c:otherwise>  
			</c:choose>  
   		</c:forEach>  
  		<c:if test="endpage(); < pagetotalcount();">  
 			<a href="received?page=endpage();+1">다음</a> 
			<a href="received?page=pagetotalcount();">마지막</a> 
		</c:if> 
					</div>
					<div id="sendlist" style="display: none;">
						<table id="sendtable">
							<tr id="firsttr">
								<th width="10%">번호</th>
								<th width="15%">받는사람</th>
								<th width="45%">제목</th>
								<th width="20%">작성날짜</th>
								<th width="10">수신 확인</th>
							</tr>

						</table>
					</div>
	
				</div>
			</div>
		</div>
</div>

</body>
</html>