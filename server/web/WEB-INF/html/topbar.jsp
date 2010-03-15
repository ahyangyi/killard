<%@ include file="/WEB-INF/html/includes.jsp" %>
<div class="topbar">
    <div id="logo">
        <img src="<c:url value="/image/logo.png"/>"/>
        <img src="<c:url value="/image/title.png"/>"/>
    </div>
    <div class="menu">
        <ul>
            <li><a href="/">Home</a></li>
            <li>|</li>
            <li><a href="<c:url value="/games"/>">All Games</a></li>
            <li>|</li>
            <li><a href="<c:url value="/arena"/>">Arena</a></li>
            <li>|</li>
            <li><a href="#">DIY</a></li>
            <li>|</li>
            <li><a href="#">Help</a></li>
            <li>|</li>
            <li><a href="#">Logout</a></li>
        </ul>
    </div>
</div>