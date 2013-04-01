<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h:wrapper>
	<h2>Items:</h2>
	<c:choose>
		<c:when test="${not empty items}">
			<ol id="ordered-list" class="hfeed">
				<c:forEach items="${items}" var="item">
					<li><article class="hentry">
							<header>
								<h2 class="entry-title">
									<a
										href="${pageContext.request.contextPath}/actions/show-item?itemId=${item.itemId}"
										rel="bookmark" title="${item.title}">${item.title}</a> - ${item.initialPrice} BGN
								</h2>
							</header>
							<footer class="post-info">
								<abbr title="<fmt:formatDate value="${item.bidEndDate}" pattern="yyyy-MM-dd'T'hh:mm:ss ZZZZ" />">
									<fmt:formatDate value="${item.bidEndDate}" type="date"
										dateStyle="long" />
								</abbr>
								<address class="vcard author">
									By <a class="url fn" href="#">${item.seller.fullName}</a>
								</address>
							</footer>
							<div class="entry-content">
								<p>
									${item.shortDescription}
								</p>

							</div>
							<!-- /.entry-content -->
						</article></li>
				</c:forEach>
			</ol>
		</c:when>
		<c:otherwise>
        	No items found.
    	</c:otherwise>
	</c:choose>

</h:wrapper>