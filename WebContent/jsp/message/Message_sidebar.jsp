<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/message_sidebar.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<aside id="sidebar">
			<h3 style="text-align: center; font-weight: bold;">쪽지함</h3>
			<div class="message_sidebar">
				<ul>
					<li style="cursor:pointer;"><a class="side" id="recvbox" onclick="location.href='receive'">받은 쪽지함</a></li>
					<li style="cursor:pointer;"><a class="side" id="sendbox" onclick="location.href='send'">보낸 쪽지함</a></li>
				</ul>
			</div>
		</aside>


</body>
</html>