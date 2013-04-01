<!-- NOTE: jsp/tags - tag file -->
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="head" fragment="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Flea Market</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" >
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("nav ul li").each(function(elem) {
			var url = document.URL;
			var suffix = $("a", this).attr("href");
			if (url.indexOf(suffix, url.length - suffix.length) !== -1) {
				$(this).addClass("active");
			} else {
				$(this).removeClass("active");
			}
		});
		if ($("nav ul li.active").length == 0) {
			// set to first by default
			$("nav ul li").first().addClass("active");
		}
	});
</script>
<jsp:invoke fragment="head" />
</head>
<body>
	<header id="banner" class="body">
		<h1>
			<a href="#">Flea Market <br /> <strong>where all the
					cool fleas go</strong>
			</a>
		</h1>
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath}/index.jsp">home</a></li>
				<!-- NOTE: jsp/tags - jstl core choose -->
				<c:choose>
					<c:when test="${not empty pageContext.request.userPrincipal.name}">
						<!-- NOTE: jsp/el - logical operation -->
						<li><a href="${pageContext.request.contextPath}/actions/items">items</a></li>
						<!-- NOTE: jsp/tags - jstl core if -->
						<c:if test="${isCurrentUserSeller}">
							<li><a href="${pageContext.request.contextPath}/actions/user-items">my items</a></li>
							<li><a href="${pageContext.request.contextPath}/pages/create-item.jsp">create item</a></li>
						</c:if>
						<c:if test="${isCurrentUserBidder}">
							<li><a href="${pageContext.request.contextPath}/actions/user-bids">my bids</a></li>
							<li><a href="${pageContext.request.contextPath}/actions/user-winner-bids">my won bids</a></li>
						</c:if>
						<li><a href="${pageContext.request.contextPath}/logout">logout (${pageContext.request.userPrincipal.name})</a></li>
					</c:when>
					<c:otherwise>
			        	<li><a href="${pageContext.request.contextPath}/actions/items">login</a></li> <!-- directly list items after login -->
			    	</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</header>
	<section id="content" class="body">
		<jsp:doBody />
	</section>
	<footer id="contentinfo" class="body">
		<address id="about" class="body">
			<span class="primary"> <strong> <a 
					href="http://www.tu-sofia.bg" class="fn url" target="blank">Technical
						University - Sofia</a>
			</strong> <span class="role">Faculty for Computer Systems and
					Technology</span>
			</span> <img
				src="${pageContext.request.contextPath}/resources/images/avatar.png"
				alt="tu-sofia logo" class="logo" /> <span class="secondary">Flea
				Market is a demonstration of the capabilities of Java EE for
				creating a multi-tiered enterprise application, containing
				presentation, business logic and persistance layers.</span>

		</address>
		<p>2013 - created by Petyo Dimitrov</p>
	</footer>
</body>
</html>