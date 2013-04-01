<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<h2>Created item with id <em>${item.itemId}</em>!</h2>
	<a href="${pageContext.request.contextPath}/actions/items">List active items</a>
</h:wrapper>