<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:wrapper>
	<form method="post" action="createuser" enctype="multipart/form-data">
		<h2>Create user</h2>
		<fieldset class="labels-left">
			<legend>Account information</legend>
			<label for="username">Username</label>
			<input type="text" id="username" name="username" required="required" autofocus="autofocus"/>
			<label for="password">Password</label> 
			<input type="password" id="password" name="password" required="required" autocomplete="off"/>
			<label>Role</label>
			<input type="radio" id="bidder-role" name="role" value="bidder" checked="checked"/><span>bidder</span>
			<input type="radio" id="seller-role" name="role" value="seller" /><span>seller</span>
			<label for="email">Email</label> 
			<input type="email" id="email" name="email" required="required" />
		</fieldset>
		<fieldset class="labels-left">
			<legend>Basic information</legend>
			<label for="firstname">First name</label> 
			<input type="text" id="firstname" name="firstname" required="required" />
			<label for="lastname">Last name</label> 
			<input type="text" id="lastname" name="lastname" required="required" />
			<label for="picture">Picture</label> 
			<input type="file" id="picture" name="picture" size="20" />
			<label for="birthdate">Birth date</label> 
			<input type="date" id="birthdate" name="birthdate" />
			<label for="telephone">Telephone</label> 
			<input type="text" id="telephone" name="telephone" />	
		</fieldset>
		<fieldset class="labels-left">
			<legend>Address</legend>
			<label for="street">Street name</label> 
			<input type="text" id="street" name="street" />
			<label for="city">City</label> 
			<input type="text" id="city" name="city" />
			<label for="zipcode">Zip code</label> 
			<input type="number" id="zipcode" name="zipcode" />
			<label for="country">Country</label> 
			<input type="text" id="country" name="country" />
		</fieldset>
		<div>
			<input type="submit" value="Save" /> 
			<input type="reset" value="Reset" />
			<a href="${pageContext.request.contextPath}/login.jsp">Back</a>
		</div>
	</form>
</h:wrapper>