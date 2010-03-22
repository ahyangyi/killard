<%@ include file="/WEB-INF/html/includes.jsp" %>
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
        </ul>
    </div>
</div>