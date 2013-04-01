<!-- NOTE: jsp/directive - page -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sample JSP</title>
</head>
<body>
<!-- This sample JSP demonstrates the basic available constructs-->

<%-- directive --%>
<p>
	<!-- NOTE: jsp/directive - static include -->
	Static: <%@ include file="./embeddable.jspf" %>
<p>


<%-- declaration --%>
<p>
	<!-- NOTE: jsp/declaration - member variable -->
	<%! private static int counter = 0; %>
</p>

<%-- expression --%>
<p>
	<!-- NOTE: jsp/expression - example -->
	<%= counter++ %>
</p>	

<%-- scriplet --%>
<p>
	<!-- NOTE: jsp/scriplet - example -->
	<% 
		String queryString = request.getQueryString();
		out.println("queryString: " + queryString);
		session.setAttribute("opa", 42);
	%>
</p>

<%-- actions --%>
<p>
	<!-- NOTE: jsp/action - dynamic include -->
	Dynamic: <jsp:include page="./embeddable.jspf" />
<p>

<%-- expression language --%>
<!-- NOTE: jsp/el - example -->
<em>Opa = ${opa}</em>

</body>
</html>