<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%> 	<!-- NOTE: jsp/tags - tag file declaration -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   	<!-- NOTE: jsp/tags - jstl declaration -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h:wrapper>
	<h2>Bids:</h2>
	<c:choose>
		<c:when test="${not empty bids}">
			<ol id="ordered-list" class="hfeed">
				<!-- NOTE: jsp/tags - jstl core forEach -->
				<c:forEach items="${bids}" var="bid">
					<li><article class="hentry">
							<header>
								<h2 class="entry-title">
									<a
										href="${pageContext.request.contextPath}/actions/show-item?itemId=${bid.item.itemId}"
										rel="bookmark" title="${bid.item.title}">${bid.item.title}</a> - ${bid.bidPrice} BGN / ${bid.item.initialPrice} BGN
								</h2>
							</header>
							<footer class="post-info">
								<!-- NOTE: jsp/tags - jstl format date -->
								<abbr title="<fmt:formatDate value="${bid.bidDate}" pattern="yyyy-MM-dd'T'hh:mm:ss ZZZZ" />">
									<fmt:formatDate value="${bid.bidDate}" type="date"
										dateStyle="long" />
								</abbr>
							</footer>
							<div class="entry-content">
								<p>
									<!-- NOTE: jsp/el - access property -->
									Status: ${bid.bidStatus}
								</p>

							</div>
						</article></li>
				</c:forEach>
			</ol>
		</c:when>
		<c:otherwise>
        	No bids found.
    	</c:otherwise>
	</c:choose>

</h:wrapper>