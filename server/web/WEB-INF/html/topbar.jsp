<%@ include file="/WEB-INF/html/includes.jsp" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<script src="http://static.ak.connect.facebook.com/js/api_lib/v0.4/FeatureLoader.js.php/en_US"
        type="text/javascript"></script>
<script type="text/javascript">
//    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
//    document.write(unescape("%3Cscript src='" + gaJsHost
//            + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
//    try {
//        var pageTracker = _gat._getTracker("UA-6297932-3");
//        pageTracker._trackPageview();
//    } catch(err) {
//    }
//    function updateProfile() {
//        var user_box = document.getElementById("profile-pic");
//        user_box.innerHTML = "<fb:profile-pic uid='loggedinuser' facebook-logo='false'></fb:profile-pic>";
//        FB.XFBML.Host.parseDomTree();
//    }
//    FB_RequireFeatures(["Connect"], function() {
//        FB.init("3ebf10862f08033d0c8fce32551d6e90", "xd_receiver.htm", { "ifUserConnected": updateProfile });
//    });
</script>
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