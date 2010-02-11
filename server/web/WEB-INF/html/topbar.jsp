<%@ include file="/WEB-INF/html/includes.jsp" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<div class="topbar">
    <div id="logo">
        <img src="/image/logo.png"/>
        <img src="/image/title.png"/>
    </div>
    <div class="menu">
        <ul>
            <li><a href="/index.html">Home</a></li>
            <li><a href="/packages.html">|Games</a></li>
            <c:if test="${pageContext.request.userPrincipal != null}">
            <li>|<a href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">Logout</a></li>
            </c:if>
        </ul>
    </div>
</div>