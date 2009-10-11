<%@ include file="/WEB-INF/html/includes.jsp" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<script src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php/en_US"
        type="text/javascript"></script>
<script type="text/javascript">
    function updateProfile() {
        var user_box = document.getElementById("profile-pic");
        user_box.innerHTML = "<fb:profile-pic uid='loggedinuser' facebook-logo='false'></fb:profile-pic>";
        FB.XFBML.Host.parseDomTree();
    }
    FB_RequireFeatures(["Connect"], function() {
        FB.init("3ebf10862f08033d0c8fce32551d6e90", "xd_receiver.htm", { "ifUserConnected": updateProfile });
//        FB.Connect.showPermissionDialog("publish_stream,status_update");
//        FB.Connect.requireSession();
    });
</script>
<div class="menu">
    <ul>
        <li id="profile-pic"><fb:login-button length="short" size="medium" onlogin="updateProfile();"></fb:login-button>|</li>
        <li><a href="<c:url value="/game/list.html"/>">Join A Game</a>|</li>
        <li><a href="<c:url value="/game/board.html"/>">My Game</a>|</li>
        <li><a href="/help.html">Help</a>|</li>
        <li><a href="<c:url value="/game/quit.html"/>">Quit</a>|</li>
        <li>&nbsp;&nbsp;&nbsp;&nbsp;|</li>
        <li><a href="<c:url value="/package/public.html"/>">Packages</a>|</li>
        <li><a href="<c:url value="/package/custom.html"/>">Custom Packages</a>|</li>
        <li><a href="<c:url value="/package/my.html"/>">My Packages</a>|</li>
        <li><a href="/help.html">Open API</a>|</li>
        <li><a href="<%=UserServiceFactory.getUserService().createLogoutURL("/index.jsp")%>">Logout</a></li>
    </ul>
</div>
<hr/>