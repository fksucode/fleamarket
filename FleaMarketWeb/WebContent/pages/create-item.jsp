<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<form method="post" action="${pageContext.request.contextPath}/actions/create-item">
		<h2>Create item</h2>
		<fieldset class="labels-left">
			<legend>Item information</legend>
			<label for="title">Title</label>
			<input type="text" id="title" name="title" required="required" maxlength="50"/>
			<label for="bidEndDate">Bid end date</label> 
			<input type="date" id="bidEndDate" name="bidEndDate" required="required"/>
			<label for="initialPrice">Initial price</label> 
			<input type="number" id="initialPrice" name="initialPrice" required="required" step="any" pattern="^\d+(\.\d{1,2})?$"/>
			<label for="description">Description</label>
			<textarea id="description" name="description" rows="2" maxlength="500" placeholder="max 500 symbols"></textarea>
		</fieldset>
		<div>
			<input type="submit" value="Save" /> 
			<input type="reset" value="Reset" />
			<a href="${pageContext.request.contextPath}/index.jsp">Back</a>
		</div>
	</form>
</h:wrapper>