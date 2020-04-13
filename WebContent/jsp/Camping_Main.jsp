<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.review{
display:grid;
grid-template-columns: auto auto auto auto;
grid-gap: 50px;
width: fit-content;
}
.a_img{
border-radius: 20px;
}
#autoCamping_list, #glamping_list, #autoCamping_list{
padding: 0 10px 0 10px;
}
.more_view{
text-align: right;
padding: 10px;
}
.title_dv{
text-align: center; 
padding-bottom: 5px;
cursor: pointer;
}
.a_img, .view{ cursor: pointer; }

.readCount_no{
width: 225px;
text-align: right; 
font-weight: bolder;
display: inline-table;
}

#like{ font-weight: bold; }

.hr{
height:1px;
width: 1000px;
border-top: 3px dotted black;
}

#box1{
width: 1124px;
height: 31px;
}

.view{font-size: 35px;}
 </style>


<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	
	<header>
		<%@include file="./CampingHolic_header.jsp" %>
	</header> 
	
	
		<div style="text-align: center;">
			<img src="${contextPath}/img/main_img.jpg" style="width: 1400px; height:500px;"></img>
		</div>
	<div id="container" style="margin: 0 auto; width:1400px;">
	
			<div id="box1">
				<h2 id="category" style="float: left;">최근 등록된 오토캠핑 후기 </h2>
			</div>
		<hr class="hr">
				<div class="more_view"><span class="view" onclick="location.href='../autoCamping/autoCamping_main'">더보기</span></div>
				<div class="review">
			
				<c:forEach items="${autoData}" var="autoCampingMainList">
					<div id="autoCamping_list">
						<div style="font-weight: bolder;"><span>No.&nbsp;</span>${autoCampingMainList.AC_NUM}</div>
						<div class="title_dv">
						<a onclick="location.href='../autoCamping/autoCampingView?ac_num=${autoCampingMainList.AC_NUM}'">${autoCampingMainList.AC_TITLE}</a>
						</div>
						<div><img src="${contextPath}/CampingHolic${autoCampingMainList.ACI_IMGFILENAME}" onclick="location.href='../autoCamping/autoCampingView?ac_num=${autoCampingMainList.AC_NUM}'"
						 class="a_img" style= "width: 300px; height:300px;"></div>
						<span style="color: #DF0101;">♥</span><small id="like">좋아요${autoCampingMainList.LIKECOUNT}</small>
						<div class="readCount_no"><span style="font-size:15px;">조회수&nbsp;</span>
						${autoCampingMainList.READCOUNT}</div>
						<div style="padding:5px;"><span>작성자&nbsp;</span>${autoCampingMainList.NICKNAME}</div>
						<hr>
						${autoCampingMainList.AC_ADDRESS}
					</div>
				</c:forEach>
			</div>		
		<br><br>
		
			<div id="box1">
				<h2 id="category" style="float: left;">최근 등록된 글램핑 후기 </h2>
			</div>
		<hr class="hr">
			<div class="more_view"><span class="view" onclick="location.href='../glamping/glamping_main'">더보기</span></div>
			
		<div class="review">
		
				<c:forEach items="${glampingData}" var="glampingMainList">
					<div id="glamping_list">
						<div style="font-weight: bolder;"><span>No.&nbsp;</span>${glampingMainList.GL_NUM}</div>
						<div class="title_dv">
						<a onclick="location.href='../glamping/glampingBoardView?gl_num=${glampingMainList.GL_NUM}'">${glampingMainList.GL_TITLE}</a>
						</div>
					    <div><img src="${contextPath}${glampingMainList.GLI_IMGFILENAME}" onclick="location.href='../glamping/glampingBoardView?gl_num=${glampingMainList.GL_NUM}'" 
					    class="a_img" style= "width: 300px; height:300px;"></div>
					    <span style="color: #DF0101;">♥</span><small id="like">좋아요${glampingMainList.LIKECOUNT}</small>
						<div class="readCount_no"><span style="font-size:15px;">조회수&nbsp;</span>
						${glampingMainList.READCOUNT}</div>
						<div style="padding:5px;"><span>작성자&nbsp;</span>${glampingMainList.NICKNAME}</div>
						<hr>
						${glampingMainList.GL_ADDRESS}
					</div>
				</c:forEach>
		</div>
		<br><br>
	
			<div id="box1">
				<h2 id="category" style="float: left;">최근 등록된 백패킹 후기 </h2>
			</div>
		<hr class="hr">
				<div class="more_view"><span class="view" onclick="location.href='../backCamping/backCamping_main'">더보기</span></div>
			<div class="review">
				<c:forEach items="${backData}" var="backCampingMainList">
					<div id="backCamping_list">
						<div style="font-weight: bolder;"><span>No.&nbsp;</span>${backCampingMainList.BC_NUM}</div>
						<div class="title_dv">
						<a onclick="location.href='../backCamping/backCampingBoardView?bc_num=${backCampingMainList.BC_NUM}'">${backCampingMainList.BC_TITLE}</a>
						</div>
						<div><img src="${contextPath}${backCampingMainList.BCI_IMGFILENAME}" onclick="location.href='../backCamping/backCampingBoardView?bc_num=${backCampingMainList.BC_NUM}'" 
						class="a_img" style= "width:300px; height:300px;"></div>
						<span style="color: #DF0101;">♥</span><small id="like">좋아요${backCampingMainList.LIKECOUNT}</small>
						<div class="readCount_no"><span style="font-size:15px;">조회수&nbsp;</span>
						${backCampingMainList.READCOUNT}</div>
						<div style="padding:5px;"><span>작성자&nbsp;</span>${backCampingMainList.NICKNAME}</div>
						<hr>
						${backCampingMainList.BC_ADDRESS}
					</div>
				</c:forEach>
				
			</div>
</div>


	<footer>
		<%@include file="./CampingHolic_footer.jsp" %>
	</footer>
</body>
</html>