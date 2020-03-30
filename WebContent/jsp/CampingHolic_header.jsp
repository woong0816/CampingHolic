<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="${contextPath}/css/modal_open.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src="${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src="${contextPath}/js/message.js"></script>

<script type="text/javascript">
$(document).ready(function(){		
	
	 var modal = document.getElementById('myModal');
	 var  modal2 = document.getElementById('myModal2');
     var btn = document.getElementById('myBtn');
	 var btn1 = document.getElementById("myBtn1");
     var span = document.getElementsByClassName("close")[0]; 
     var span2 = document.getElementsByClassName("close2")[0]; 
    
     btn.onclick = function() {
         modal.style.display = "block";
     }
     btn1.onclick = function() {
    	 modal2.style.display = "block";
     }

     span.onclick = function() {
         modal.style.display = "none";
     }
     span2.onclick = function() {
         modal2.style.display = "none";
     }

     window.onclick = function(event) {
         if (event.target == modal) {
             modal.style.display = "none";
         }
     }
   
     
     
});

 
var memberNum = "${memberNum}";
var nickName = "${nickName}";
     
   $(function(){
	 
	   var frm = $("#joinfrm");
	 	var email = $("#joinEmail");   
		var pass = $("#joinPassword");
		var pass2 = $("#joinPassword2");
		var name = $("#name");
		var nickname = $("#nickName");
		var gender = $(".gender");
		var emailDup = $("#emailCheck");

  frm.on("submit" ,function(){
	   
		if(email.val()==""){
	         alert("이메일 필수입니다.");
	         email.focus();
	        return false;
		}else if (pass.val() == "") {
	      alert("비밀번호는 필수입니다.");
	      pass.focus();
	      return false;
	   }else if(pass2.val()==""){
	      alert("비밀번호 재입력은 필수입니다.");
	      pass2.focus();
	      return false;
	   }else if(name.val()==""){
	         alert("이름 필수입니다.");
	         name.focus();
	         return false;
	   }else if(nickname.val()==""){
	         alert("별명 필수입니다.");
	         nickname.focus();
	         return false;
	   }else if(!$(':input:radio[name=gender]:checked').val()) {
	       alert("성별 필수입니다.");
	       gender.focus();
	       return false;
	   }else if(emailDup.text()=="이미 가입 되어있는 이메일 입니다"){
		   alert("이미 가입된 이메일입니다.");
		   email.focus();
		   return false;
	   }

	  alert("이메일로 인증메일을 발송하였습니다.");
  });		   
	   
		  
	   
	   
	   
	   
     $("#joinEmail").blur(function(){
    	 
    	 var email = $("#joinEmail").val();
	   	  var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	   	var result =  regExp.test(email);
		   	  if(result){
		   		$.ajax({
		   			data : {"email" : email},
		   			url : "../member/checkEmail",
		   			dataType : "json",
		   			success :function(data){
			   			if(data.result){
			   			  $("#emailCheck").html("<small>이미 가입 되어있는 이메일 입니다</small>");
				   		  $("#emailCheck").css("color","red");
			   			}else{

			   			 $("#emailCheck").html("");
				   		  $("#emailCheck").css("color","red");
			   			}
		   			}
		   		});
		   		  
		   		  
		   	  }else{
		   		  $("#emailCheck").html("<small>이메일 형식이 아닙니다</small>");
		   		  $("#emailCheck").css("color","red");
		   		  
		   	  }
     });//이메일 정규식 체크 
     
     $("#nickName").blur(function(){
  
	      var data = {"nickName": $("#nickName").val()};
		
	      	if(data.nickName==""){
	     		$("#nickCheck").html("<small>필수 항목입니다.</small>");
	            $("#nickCheck").css("color", "red");
	     	}else{
	     		
	     		 $.ajax({
		 	         url:"../member/nickcheck",
		 	         data:data,
		 	         dataType:"json",
		 	         success : function(data){
		 	            if(data.result){
		 	            	 $("#nickCheck").html("<small>이미 사용중인 닉네임</small>");
		 		               $("#nickCheck").css("color", "red");
		 		               $("#nickName").css("border","1px solid red");
		 	            }else{
		 	            	   $("#nickCheck").html("");
//		 		               $("#nickDup").css("color", "blue");
		 		               $("#nickName").css("border","1px solid #DBDBDB");
		 	              
		 	            }
		 	         }
		 	         
		 	      });
	     	}

	   }); //닉네임 중복 확인
    	
     
     $("#joinPassword2").blur(function(){
	      var password = $("#joinPassword").val();
	      var password2 = $("#joinPassword2").val();
	   
	      $.ajax({
	         url:"../member/passwordDup",
	         data:{"password":password,"password2":password2},
	         dataType:"json",
	         success : function(data){
	            
	               if(data.result){
	                  $("#passwordCheck").html("");
//	                  $("#passwordDup").css("color", "blue");
	                  $("#joinPassword").css("border","1px solid #DBDBDB");
	                  $("#joinPassword2").css("border","1px solid #DBDBDB");
	               }else{
	                  $("#passwordCheck").html("<small>비밀번호가 불일치합니다</small>");
	                  $("#passwordCheck").css("color", "red");
	                  $("#joinPassword").css("border","1px solid red");
	                  $("#joinPassword2").css("border","1px solid red");
	               }
	         }
	         
	      });
	      
	   });//비밀번호 확인
    	
	   
	   $("#name").blur(function(){
		  var tmpName = $("#name").val();
		  	if(tmpName!=""){
			  	var regExp = /^[가-힣]{2,6}$/;
				var name = regExp.test(tmpName);
				console.log(name);
				 	if(name){
				  	  $("#nameCheck").html("");
				  	}else{
				  		$("#nameCheck").html("<small>이름을 확인해주세요</small>");
				        $("#nameCheck").css("color", "red");
				  	}
		  	}else{
		  		 $("#nameCheck").html("<small>필수 항목입니다.</small>");
	              $("#nameCheck").css("color", "red");
		  	}
		  
		  /* 	console.log(name);
			  if(name){
				  $("#nameCheck").html("");
				  
			  }else{
				  
				  $("#nameCheck").html("<small>필수 항목입니다.</small>");
	              $("#nameCheck").css("color", "red");
			  } */
		 });

	   $("#bith").blur(function(){
		   var tmpBith = $("#bith").val();
		  
		   console.log(tmpBith);
		   		if(tmpBith!=""){	
		   			var regExp = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
					var bith = regExp.test(tmpBith);
					console.log(bith);
						if(bith){
							  $("#bithCheck").html("");
						}else{
							$("#bithCheck").html("<small>생년월일을 확인해주세요.</small>");
					        $("#bithCheck").css("color", "red");
						}
		   		}else{
		   			$("#bithCheck").html("<small>필수 항목입니다.</small>");
		            $("#bithCheck").css("color", "red");
		   		}
		   
	   });
	   
	   
	   
  });
  	
   function naverLogin(){
	   var settings = 'width=300, heigth=300';
	   	$.ajax({
	   		url : "../naverLogin/login",
	   		cacth : false,
	   		type : "post",
	   		success : function(data){
	   			console.log(data);
	   			location.href= data;
	   			
	   			}
	   	});
	   
	   
	   
   }
   
   
   
</script>
</head>
<body>
<div class="top">
		<c:if test="${memberNum==0}">
			<div class="loginButton" id="loginButton">
				<ul class="header_ul">
					<li><a class="nav-main" onclick="location.href='../main/mainView'">Camping Holic</a></li>
					<li><button class="my_btn" id="myBtn" style="margin-right: 10px;">로그인</button></li>
					<li><button class="my_btn" id="myBtn1">회원가입</button></li>
				</ul>
		<!-- The Modal -->
		<div id="myModal" class="modal1">

			<!-- Modal content -->
			<div class="modal-content1">
				<span class="close">&times;</span>
				<div style="text-align: center;"> 
					<form action="${contextPath}/member/loginAction" method="POST">
						<input type="text" name="email" id="loginEmail" placeholder="이메일 주소" style="width:240px;"><br>
						<input type="password" name="password" id="loginPassword" placeholder="비밀번호" style="width:240px;"><br><br>
						<input type="submit" value="로그인"><br><br>
					</form>
					<div style="width: 307px;">
						<a class="naverLogin_btn" onclick="naverLogin();"> <img
							width="230"
							src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png" style="cursor: pointer;"/></a>
					</div>
				</div>
			</div>
		</div>

		<div id="myModal2" class="modal2">

			<!-- Modal content -->
			<div class="modal-content2">
					<span style="font-size: 20px;">회원가입</span>
				<span class="close2">&times;</span>
				<div style="text-align: center; width: 349px;">
					<form name="loginAction" class="loginAction" method="post" action="${contextPath}/member/joinAction" id="joinfrm">
	
						<div>이메일</div>
						<input type="text" name="email" id="joinEmail" placeholder="이메일"> 
							<br><span id="emailCheck"></span><br>
						<input type="password" name="password" id="joinPassword" placeholder="비밀번호"><br>
						<input type="password" name="searchPassword" id="joinPassword2" placeholder="비밀번호 확인"> 
							<br><small><span id="passwordCheck"></span></small><br>
						<input type="text" name="name" id="name" placeholder="이름">
							<br><span id="nameCheck"></span><br>
						<input type="text" name="nickName" id="nickName" placeholder="닉네임"> 
							<br><span id="nickCheck"></span><br>
						<div style="width: 430px;">
						<input type="text" name="bith" id="bith" placeholder="생년월일"><small>ex:19901010</small>
						</div>
							<span id="bithCheck"></span><br>
						
						<input type="radio" name="gender" class="gender" value="M">남자 
						<input type="radio" name="gender" class="gender" value="W">여자 <br> 
						<input class="fw_btn" type="submit" value="등록">
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
		
	<c:if test="${memberNum!=0}">		
		<%-- <div class="logoutForm">
			<a>${nickName}님 안녕하세요</a> 
		</div> --%>
		
			<div class="login">
				<ul class="header_ul" >
					<li class="in_li"><a class="nav-main" onclick="location.href='../main/mainView'">Camping Holic</a></li>
					<li class="in_li"><a>${nickName}님 안녕하세요</a> 
					<li><input class="my_btn" type="button" value="로그아웃" onclick="location.href='${contextPath}/member/logoutAction'" style="margin-right: 10px;"></li>
					<li><button class="my_btn" onclick="location.href='${contextPath}/message/receive'" style="margin-right: 10px;">쪽지함</button></li>
					<li><button class="my_btn" onclick="location.href='${contextPath}/member/memberInfo'">회원정보 수정</button></li>
				</ul>
			</div>				
	</c:if>
		<div>
			<ul class="category_ul">
				<li class="category_item"><a onclick="location.href='../glamping/glamping_main'" style="cursor: pointer">글램핑</a></li>
				<li class="category_item"><a onclick="location.href='../backCamping/backCamping_main'" style="cursor: pointer">백패킹</a></li>
				<li class="category_item"><a onclick="location.href='../autoCamping/autoCamping_main'" style="cursor: pointer">오토캠핑</a></li>
			</ul>
		</div>
	
	
</div>
</body>
</html>