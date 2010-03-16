<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="/css/layout.css" rel="stylesheet"/>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.layout-latest.js"></script>
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
        }
    </style>
    <script type="text/javascript">
        $(function () {
            function resize() {
                var centerPadding = parseInt(Math.min($(window).width() / 80, $(window).height() / 45));
                var centerHeight = $(window).height() - $('.topbar').outerHeight() - $('.bottombar').outerHeight();
                $('.container').css({
                    'padding-top' : centerPadding,
                    'padding-bottom' : centerPadding,
                    'padding-left' : 2 * centerPadding,
                    'padding-right' : 2 * centerPadding
                }).height(centerHeight - 2 * centerPadding);
            }

            resize();

            $('.container').layout({
                resizable: false,
                closable: false,
                spacing_open: 0,
                west__size: "30%",
                west__onresize: function () {
                },
                center__onresize: function () {
                }
            });
            $.event.add(window, 'load', resize);
            $.event.add(window, 'resize', resize);
        });
    </script>
</head>
<body>
<%@include file="../topbar.jsp"%>
<div class="container">
    <div class="ui-layout-west" style="display:none;">
        <div class="ui-layout-content">
            <h3><spring:message code="message.welcome.title1"/></h3>
            <p style="vertical-align:middle;"><spring:message code="message.welcome.content1"/></p>
        </div>
    </div>
    <div class="ui-layout-center" style="display:none;">
        <div class="ui-layout-content">
            <div style="border:2px dashed #a9a9a9;text-align: center;">
                <img src="image/index.png" style="width:70%;"/>
            </div>
        </div>
    </div>
</div>
<%@include file="../bottombar.jsp"%>
</body>
</html>