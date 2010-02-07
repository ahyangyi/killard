<%--<%@ include file="header.jsp" %>--%>
<%--<div id="profile_pics"></div>--%>
<%--<script type="text/javascript">--%>
    <%--var widget_div = document.getElementById("profile_pics");--%>
    <%--FB.ensureInit(function () {--%>
        <%--FB.Facebook.get_sessionState().waitUntilReady(function() {--%>
            <%--FB.Facebook.apiClient.friends_get(null, function(result) {--%>
                <%--var markup = "";--%>
                <%--var num_friends = result ? Math.min(5, result.length) : 0;--%>
                <%--if (num_friends > 0) {--%>
                    <%--for (var i = 0; i < num_friends; i++) {--%>
                        <%--markup +=--%>
                        <%--'<fb:profile-pic size="square" uid="' + result[i]--%>
                                <%--+ '" facebook-logo="true"></fb:profile-pic>';--%>
                    <%--}--%>
                <%--}--%>
                <%--widget_div.innerHTML = markup;--%>
                <%--FB.XFBML.Host.parseDomElement(widget_div);--%>
            <%--});--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>
<%--<%@ include file="footer.jsp" %>--%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://apps.facebook.com/ns/1.0" lang="en">
<head>
    <link type="text/css" href="css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/richlist.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/rating.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="js/ui/carousel.js"></script>
    <script type="text/javascript" src="js/ui/richlist.js"></script>
    <script type="text/javascript" src="js/ui/searchbar.js"></script>
    <title>Killard Games</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background:#f9f9f9;
        }

        div.dashboard {
            display: block;
            margin: 0 100px;
            padding: 0;
            text-align: center;
        }

        div.dashboard img {
            display: none;
            /*border: solid gray thin;*/
        }
    </style>
    <script type="text/javascript">
        $(function () {
            function resizeWindow() {
                var dashboardDiv = $('.dashboard');
                var dashboardImg = $('.dashboard img');

                var h = $(window).height() - $('.topbar').outerHeight() - $('.bottombar').outerHeight();
                var dashboardVerticalMargin = parseInt(dashboardDiv.css('margin-top'));
                var packageInnerHeight = h - 2 * dashboardVerticalMargin;

                dashboardDiv.width($(window).width() - parseInt(dashboardDiv.css('margin-left')) - parseInt(dashboardDiv.css('margin-right')));
                dashboardDiv.height(packageInnerHeight);

                var wUnit = dashboardImg.width() / 4;
                var hUnit = dashboardDiv.height() / 3;
                var unit = Math.min(wUnit, hUnit);
                dashboardImg.width(parseInt(4 * unit));
                dashboardImg.height(parseInt(3 * unit));
                dashboardImg.fadeIn('slow');
            }
            $.event.add(window, 'load', resizeWindow);
            $.event.add(window, 'resize', resizeWindow);
        });
    </script>
</head>
<body>
<div class="topbar">
    <div id="logo">
        <img src="image/logo.png"/>
        <img src="image/title.png"/>
    </div>
    <div class="menu">
        <ul>
            <li><a href="index.html">Home</a>|</li>
            <li><a href="packages.html">All Games</a>|</li>
            <li><a href="arena.html">Arena</a>|</li>
            <li><a href="#">DIY</a>|</li>
            <li><a href="#">Help</a>|</li>
            <li><a href="#">Logout</a></li>
        </ul>
    </div>
</div>
<div class="dashboard">
    <h1 style="display:inline;width:40%;">Gaming! Gaming!</h1>
    <img src="image/index.png" style="vertical-align:middle;width:60%;"/>
</div>
<div class="bottombar">
<div class="menu">
    <ul>
        <li>Killard &copy; 2010|</li>
        <li><a href="#">Terms</a>|</li>
        <li><a href="#">Privacy</a></li>
    </ul>
</div>
</div>
</body>
</html>