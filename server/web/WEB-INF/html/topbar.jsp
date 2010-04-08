<%@ include file="/WEB-INF/html/includes.jsp" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService();%>
<div class="topbar">
    <div id="logo">
        <img src="<c:url value="/image/logo.png"/>"/>
        <img src="<c:url value="/image/title.png"/>"/>
    </div>
    <div class="menu">
        <ul>
            <li><a href="/"><spring:message code="menu.home"/></a></li>
            <li>|</li>
            <li><a href="<c:url value="/game"/>"><spring:message code="menu.game"/></a></li>
            <li>|</li>
            <li><a href="<c:url value="/arena"/>"><spring:message code="menu.arena"/></a></li>
            <li>|</li>
            <li><a href="<c:url value="/arena"/>"><spring:message code="menu.settings"/></a></li>
            <c:choose>
            <c:when test="${pageContext.request.userPrincipal != null}">
            <li>|</li>
            <li><a href="<c:url value="/game"><c:param name="filter" value="mine"/></c:url>"><spring:message code="menu.mygames"/></a></li>
            </c:when>
            <c:otherwise>
            <li>|</li>
            <li><a href="<%=userService.createLoginURL(request.getRequestURI())%>"><spring:message code="menu.mygames"/></a></li>
            </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>