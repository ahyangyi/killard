<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="css/layout.css" rel="stylesheet"/>
    <link type="text/css" href="css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/package.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/richlist.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/rating.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="js/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="js/ui/carousel.js"></script>
    <script type="text/javascript" src="js/ui/richlist.js"></script>
    <script type="text/javascript" src="js/ui/searchbar.js"></script>
    <title>Killard Games</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background: #f9f9f9;
        }

        .ui-layout-center {
            padding-left: 50px;
        }

        .ui-layout-east {
            padding-right:50px;
        }

        .carousel .content {
            background: #D1D1D1;
        }

        .carousel .bottom {
            display: none;
        }

        .cards-show img {
            padding: 15px;
            border: 1px solid #ccc;
            -webkit-border-radius: 20px;
            -moz-border-radius: 20px;
            background-color: #eee;
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

                var cornerLength = parseInt(Math.min($(window).width() / 16, $(window).height() / 9));
                $('.dashboard .corner img').width(cornerLength).height(cornerLength);
                $('.dashboard .corner').width(cornerLength).height(cornerLength);

                $('.dashboard .title').css({
                    'font-size': parseInt(cornerLength / 2) + 'px',
                    'line-height': cornerLength + 'px'
                }).height(cornerLength);

                $('.dashboard .description img').width( 2 * cornerLength);
            }

            resize();
            $('.container').layout({
                resizable: false,
                closable: false,
                spacing_open: 0,
                east__size: "30%",
                south__size: "20%",
                center__onresize: function () {
                },
                east__onresize: function () {
                    $('.richlist').richlist('resize');
                },
                south__onresize: function () {
                    $('.carousel').carousel('resize');
                    $('.searchbar').searchbar('resize');
                }
            });

            $('.searchbar').searchbar();
            $('.carousel').carousel();
            $('.richlist').richlist();

            $.event.add(window, 'load', resize);
            $.event.add(window, 'resize', resize);

            function updateGameList() {
                //            $.getJSON('test/package/boards.json', '', function(data, textStatus) {
                //                var richlist = $('.richlist').data('richlist');
                //                richlist._clear();
                //                $.each(data, function(i, item) {
                //                    richlist.items.push('<li>\
                //                        <a href="arena.html" title="View round">\
                //                        <img width="' + richlist.height + '" height="' + richlist.height + '" src="image/head/head.png" title=""/></a>\
                //                        <h5><a href="arena.html" title="Join Game">Join</a></h5>\
                //                        <p class="info">Nov 29th 2008 by <a href="arena.html">hello</a></p>\
                //                        </li>');
                //                });
                //            });
            }

            function showPackage(image) {
                $('#package #description img').attr('src', image);
                $('.richlist > ul > li').each(function() {
                    $(this).remove();
                });
                $('.richlist').data('richlist')._clear();
                setTimeout(updateGameList, 0);
            }

            $.getJSON('games/packages.json', '', function(data, textStatus) {
                $.each(data, function(i, item) {
                    $("#packageList").append('<li><img onclick="showPackage(\'' + item.picture
                            + '\')" class="item" src="' + item.picture + '"/></li>');
                });
                resizeWindow();
            });
            updateGameList();
        });
    </script>
</head>
<body>
<%@include file="../topbar.jsp"%>
<div class="container">
    <div class="ui-layout-center">
        <div class="dashboard">
            <div class="corner" style="top:0;left:0;"><img src="image/cycle.png"/></div>
            <div class="corner" style="top:0;right:0;"><img src="image/cycle.png"/></div>
            <div class="corner" style="bottom:0;left:0;"><img src="image/cycle.png"/></div>
            <div class="corner" style="bottom:0;right:0;"><img src="image/cycle.png"/></div>
            <div class="title">
                <div class="menu">
                    <ul>
                        <li>Animals In Danger</li>
                        <li><img src="image/arrow1.png"/></li>
                        <li><img src="image/arrow2.png"/></li>
                    </ul>
                </div>
            </div>
            <div class="description">
                <div class="menu">
                    <ul>
                        <li>
                            <div class="cards-show">
                                <img src="arena/AIR/Spellbreaker.png"/>
                            </div>
                        </li>
                        <li>
                            <p>This game is talking about animals.</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="new-game-form">
                <form action="/arena/new.html" method="POST">
                    <input type="hidden" name="packageBundleId" value=""/>
                    <b>Player Number:</b>
                    <label>
                        <select name="playerNumber">
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </label>
                    <input type="submit" value="New Game" style="vertical-align:middle;">
                </form>
            </div>
        </div>
    </div>
    <div class="ui-layout-east">
        <div class="richlist" style="height:95%;">
            <ul>
            </ul>
        </div>
    </div>
    <div class="ui-layout-south">
        <div class="carousel" style="height:100%;">
            <div class="center" style="height:100%;">
                <ul>
                    <li class="arrow"><a class="back"><img src="image/arrow/left.png"/></a></li>
                    <li class="content">
                        <div class="list">
                            <ul id="packageList"></ul>
                        </div>
                    </li>
                    <li class="arrow"><a class="forward"><img src="image/arrow/right.png"/></a></li>
                </ul>
            </div>
            <div class="bottom">
                <div class="searchbar">
                    <ul>
                        <li class="tag"><a href="browser.jsp#">card</a></li>
                        <li class="tag"><a href="browser.jsp#">chess</a></li>
                        <li class="searchbox">
                            <div class="wrapper">
                                <input class="text" type="text"/>
                                <input class="submit" type="submit" value="Search"/>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../bottombar.jsp"%>
</body>
</html>