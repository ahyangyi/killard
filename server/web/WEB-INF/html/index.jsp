<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/html/includes.jsp" %>
<head>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/richlist.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/rating.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/ui/carousel.js"></script>
    <script type="text/javascript" src="/js/ui/richlist.js"></script>
    <script type="text/javascript" src="/js/ui/searchbar.js"></script>
    <title>Killard</title>
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
<%@ include file="topbar.jsp" %>
<div class="dashboard">
    <h1 style="display:inline;width:40%;">Gaming! Gaming!</h1>
    <img src="/image/index.png" style="vertical-align:middle;width:60%;"/>
</div>
<%@ include file="bottombar.jsp" %>
</body>
</html>