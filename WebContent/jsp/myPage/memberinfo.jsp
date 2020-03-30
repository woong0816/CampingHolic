<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% request.setAttribute("contextPath", request.getContextPath()); %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script> 
<title>Insert title here</title>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
.container{text-align: center;}
#my_pass{
margin-bottom: 20px;
}

</style>

<script type="text/javascript">

$(function(){
	$("#pwCheck_btn").on("click",function(){
		
		var m_num = $("#m_num").val();
		var my_pass = $("#my_pass").val();
		$.ajax({
			url : "../member/pwCheck",
			type : "post",
			data : {"m_num" : m_num,
					"my_pass" : my_pass
					},
			dataType : "json",
			success : function(data){
				if(data.result){
					location.href="../member/mypage";
					
				}else{
					alert("비밀번호 불일치");
				}
				
			}
	
			
		});return false;
	
	});
	
});
</script>

</head>
<body>
	<header>
		<%@include file="../CampingHolic_header.jsp" %>
	</header> 
		
			<!-- 여기서 회원 비밀 번호를 입력 하여 확인 -->
<div class="container">
		<div style="text-align: center;">
			<img src="${contextPath}/img/main_img.jpg" style="width: 1880px; height:500px;"></img>
		</div>
	<div id="pwFrm">
		<h3>회원정보 수정</h3>
			<div class="panel panel-info" id="pwAuth">
			<div class="panel-heading panel-info"><h4 id="mypage_h4">회원 정보 변경을 위한 비밀번호</h4></div>
				<div class="panel-body">
					<form method="post" id="passFrm" name="passFrm">
						<input type="hidden" id="m_num" name="m_num" value="${memberNum}">
						<input type="hidden" id="tmpVal" name="tmpVal" value="1">
							<div class="input-group input-group-lg">
							 <span>비밀번호</span>
							 <input type="password" id="my_pass" name="my_pass" placeholder="비밀번호를 입력하세요" class="form-control">
							</div>
							<input type="submit" value="확인" id="pwCheck_btn" class="btn btn-info">
					</form>
				</div>
			</div>
	</div>
	<footer>
		<%@include file="../CampingHolic_footer.jsp" %>
	</footer>

</div>

</body>
</html>