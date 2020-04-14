<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%request.setAttribute("contextPath", request.getContextPath());%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/backCamping_main.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script type="text/javascript">
$(function(){
		var memberNum = <%=session.getAttribute("memberNum")%>
		$(".write_btn").click(function(){
			if(memberNum==0){
				alert("로그인후 이용해주세요");
			}else{	
				location.href= "../backCamping/backCamping_writeView";
			}
		});
});


</script>
</head>
<body>

	<div>
		<jsp:include page="/jsp/CampingHolic_header.jsp"></jsp:include>
	</div> 
	<div style="text-align:center;"> 
		<img src="${contextPath}/img/backcamping_main.jpg" style="width: 1400px; height:500px;"></img>
	</div>
	
		<br>
<div class="container">		
	<div class="search_bar">
		<form action="backCamping_main" name="selectBox">
			<select name="type" id="typeBox">
					<option value="1">&nbsp;&nbsp;&nbsp;&nbsp;제목</option>
					<option value="2">&nbsp;&nbsp;&nbsp;&nbsp;작성자</option>
					<option value="3">&nbsp;&nbsp;&nbsp;&nbsp;주소</option>
					<option value="4">&nbsp;&nbsp;&nbsp;&nbsp;내용</option>
			</select>
				 <input type="text" id="keyword" name="keyword" style="width: 200px; height: 23px;">
		         <input type="submit" id="submit" value="검색">
		</form>
			<div style="float: right; width: 855px; margin-top: 25px;">
				<button type="button" class="write_btn">백패킹 후기 등록</button>
			</div>
	</div>
		<br>
		<div class="category_bar">
			<button type="button" class="order" onclick="location.href='backCamping_main?type=0'">최신글</button>
			<button type="button" class="order" onclick="location.href='backCamping_main?filter=like&type=${viewData.type}&keyword${viewData.keyword}'">좋아요 순</button>
			<button type="button" class="order" onclick="location.href='backCamping_main?filter=readCount&type=${viewData.type}&keyword${viewData.keyword}'">조회수 순</button>
		</div>
		<br>
			
			
			
	<div class="review">
		<c:forEach items="${viewData.viewList}" var="backCampingList">
			<div class="review_List">
				<div style="font-weight: bolder;"><span>No.&nbsp;</span>${backCampingList.BC_NUM}</div>
				<div class="title_dv">
					<a onclick="location.href='../backCamping/backCampingBoardView?bc_num=${backCampingList.BC_NUM}'">${backCampingList.BC_TITLE}</a>
				</div>
				<div><img src="${contextPath}${backCampingList.BCI_IMGFILENAME}" onclick="location.href='../backCamping/backCampingBoardView?bc_num=${backCampingList.BC_NUM}'" 
				class="a_img" style= "width: 300px; height:300px;"></div>
				<span style="color: #DF0101;">♥</span><small id="like">좋아요${backCampingList.LIKECOUNT}</small>
				<div id="readCount_no"><span style="font-size:15px;">조회수&nbsp;</span>
				${backCampingList.READCOUNT}</div>
				<div style="padding:5px;"><span>작성자&nbsp;</span>${backCampingList.NICKNAME}</div>
				<hr>
				${backCampingList.BC_ADDRESS}
		</div>
		</c:forEach>	
	</div>		
</div>		
		
		 <div class="page_number">
			<div>
				<br>
					<div class="page_n_menu">
						<c:if test="${viewData.startPage !=1 }">
							<a href = "backCamping_main?page=1"
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							
							>&lt; 처음</a>
							<a href = "backCamping_main?page=${viewData.startPage-1}">&lt; 이전</a>
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
						</c:if>
						<c:forEach var = "pageNum" begin="${viewData.startPage}" end="${viewData.endPage < viewData.pageTotalCount ? viewData.endPage : viewData.pageTotalCount}">
							<c:choose>
								<c:when test="${pageNum == viewData.currentPage}">
									<span class="current">${pageNum}</span>
								</c:when>
								<c:otherwise>
									<a href="backCamping_main?page=${pageNum}"
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
									>${pageNum}</a>	
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test = "${viewData.endPage < viewData.pageTotalCount}">
							<a href = "backCamping_main?page=${viewData.endPage+1}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							
							">다음 &gt;</a>
							<a href = "backCamping_main?page=${viewData.pageTotalCount}
								<c:if test="${viewData.type != null}">&type=${viewData.type}</c:if>
								<c:if test="${viewData.keyword != null}">&keyword=${viewData.keyword}</c:if>
								<c:if test="${viewData.filter!=null}">&filter=${viewData.filter}</c:if>
							">&nbsp;끝&nbsp; &gt;</a>
						</c:if>
					</div>
				<br>
			</div>
		</div> 
</body>
</html>