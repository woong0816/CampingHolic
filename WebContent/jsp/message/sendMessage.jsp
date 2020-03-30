<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("contextPath", request.getContextPath()); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
<script type="text/javascript" src="${contextPath}/js/sockjs.js"></script>
<script type="text/javascript" src="${contextPath}/js/stomp.js"></script>
<script type="text/javascript" src="${contextPath}/js/message.js"></script>


<script type="text/javascript">
	var memberNum = "${memberNum}";
	var nickName = "${nickName}";
</script>
</head>
<body>
	<table>
		<tr>
			<td width="300px">받는 사람 닉네임</td>
			<td width="500px"><input type="text" name="recv_name" id="recv_name"></td>
		</tr>
		<tr>
			<td>제목</td>
			<td width="500px" height="30px">
				<input type="text" name="msg_title" id="msg_title">
			</td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea rows="10" cols="30" name="msg_content" id="msg_content"></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="button" id="sendmessagebtn" class="sendmessagebtn" value="보내기">
					<input type="button" onclick="window.close();" class="sendmessagebtn" value="취소"></td>
		</tr>
	</table>


</body>
</html>