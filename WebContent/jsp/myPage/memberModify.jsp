<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
.container{ text-align: center;
margin-top: 73px;
    margin-bottom: 73px;
}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	var ModifyForm = $("#ModifyForm");
	var pass1 = $("#modifyPassword");
	var pass2 = $("#modifyPassword2");	
	
	ModifyForm.on("submit",function(){
		if(pass1.val() == ""){
			alert("비밀번호를 입력해주세요");
			pass1.focus();
			return false;
		}else if(pass2.val() == ""){
			alert("비밀번호 재입력해주세요");
			pass2.focus();
			return false;
		}
	});

	$("#modifyPassword2").blur(function(){
		var pass1 = $("#modifyPassword").val();
		var pass2 = $("#modifyPassword2").val();	
		if(pass1!=pass2){
			$("#pass_check").html("<small>비밀번호가 일치하지 않습니다.");
			$("#pass_check").css("color","red");
			$("#modify_btn").prop("disabled",true);
		}else{
			$("#pass_check").html("");
			$("#modify_btn").prop("disabled",false);
		}
	
	});
});


</script>


</head>
<body>
	
	<header>
		<%@include file="../CampingHolic_header.jsp" %>
	</header> 		
		<div style="text-align: center;">
			<img src="${contextPath}/img/main_img.jpg" style="width: 1880px; height:500px;"></img>
		</div>
<div class="container">
	<div id=myPageFrm>
		<h3>회원 정보 수정입니다</h3>
			<form action="../member/memberModify" method="post" id="ModifyForm">
				<span>이메일</span><input type="text" id="m_email" name="email" value="${member.EMAIL}" readonly="readonly"><br>
				<span>비밀번호</span><input type="password" id=modifyPassword name="password" placeholder="비밀번호를 입력하세요"><br>
				<div><span>비밀번호 확인</span><input type="password" id=modifyPassword2 name="password2" placeholder="비밀번호를 입력하세요"><br>
					<span id="pass_check"></span>
				</div>
				<span>이름</span><input type="text" id="m_name" name="name" value="${member.NAME}" readonly="readonly"><br>
				<span>별명</span><input type="text" id="m_nickName" name="nickName" value="${member.NICKNAME}" readonly="readonly"><br>
				<span>성별</span><input type="text" id="m_gender" name="gender" value="${member.GENDER}" readonly="readonly"><br>
				<span>생년월일</span><input type="text" id="m_bith" name="bith" value="${member.BITH}" readonly="readonly"><br>
			<input type="submit" id="modify_btn" value="수정">
			</form>

	</div>
</div>
	<footer>
		<%@include file="../CampingHolic_footer.jsp" %>
	</footer>

	
</body>
</html>