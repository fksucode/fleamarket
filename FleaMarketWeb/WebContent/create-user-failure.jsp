<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<h2>Error creating user <em>${param.username}</em>.</h2>
	Details: ${requestScope.errorMessage}<br/>
	<a href="${pageContext.request.contextPath}/create-user.jsp">Try again?</a>
</h:wrapper>