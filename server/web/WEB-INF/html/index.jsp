<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="css/layout.css" rel="stylesheet"/>
    <link type="text/css" href="css/menu.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.layout-latest.js"></script>
    <title>Killard Games</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background: rgb(200, 233, 115);
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

        div.bottombar {
            border: 0;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            function resize() {
                var centerPadding = 0;//parseInt(Math.min($(window).width() / 80, $(window).height() / 45));
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

<!--<div class="topbar">-->
    <!--<div id="logo">-->
        <!--<img src="image/logo.png"/>-->
        <!--<img src="image/title.png"/>-->
    <!--</div>-->
    <!--<div class="menu">-->
        <!--<ul>-->
            <!--<li><a href="/">Home</a>|</li>-->
            <!--<li><a href="/game">All Games</a>|</li>-->
            <!--<li><a href="/arena">Arena</a>|</li>-->
            <!--<li><a href="#">DIY</a>|</li>-->
            <!--<li><a href="#">Help</a>|</li>-->
            <!--<li><a href="#">Logout</a></li>-->
        <!--</ul>-->
    <!--</div>-->
<!--</div>-->

<div class="container">
    <div class="ui-layout-center" style="display:none;">
        <div class="ui-layout-content">
            <div style="text-align: center;">
                <img src="image/bg.png" style="width:100%;"/>
            </div>
        </div>
    </div>
</div>

<div class="bottombar">
    <div class="menu">
        <ul>
            <li>Killard &copy; 2010</li>
            <li>|</li>
            <li><a href="#">Terms</a></li>
            <li>|</li>
            <li><a href="#">Privacy</a></li>
        </ul>
    </div>
</div>
</body>
</html>