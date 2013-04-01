<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<form method="post" action="j_security_check" class="login">
		<h4>Enter your credentials:</h4>
		<div>
			<input type="text" name="j_username" required="required" placeholder="username" />
		</div>
		<div>
			<input type="password" name="j_password" required="required" placeholder="password" />
		</div>
		<div>
			<input type="submit" value="Login"/>
			<input type="reset" value="Reset"/>
			<a href="${pageContext.request.contextPath}/create-user.jsp">Register</a>
		</div>
	</form>
</h:wrapper>