<%@ include file="/WEB-INF/html/includes.jsp" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost
            + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    try {
        var pageTracker = _gat._getTracker("UA-6297932-3");
        pageTracker._trackPageview();
    } catch(err) {
    }
</script>
<div class="topbar">
    <div id="logo">
        <img src="/image/logo.png"/>
        <img src="/image/title.png"/>
    </div>
    <div class="menu">
        <ul>
            <li><a href="/index.html">Home</a></li>
            <li>|<a href="/packages.html">Games</a></li>
            <c:if test="${pageContext.request.userPrincipal != null}">
            <li>|<a href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">Logout</a></li>
            </c:if>
        </ul>
    </div>
</div>