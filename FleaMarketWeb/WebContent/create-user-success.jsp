<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<h2>User <em>${param.username}</em> was successfully created!</h2>
	<a href="${pageContext.request.contextPath}/login.jsp">Login</a>
</h:wrapper>