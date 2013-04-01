<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<h2>Error deleting item!</h2>
	Details: ${requestScope.errorMessage}<br />
</h:wrapper>