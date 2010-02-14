<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="/css/ui/arena.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/searchbar.css" rel="stylesheet"/>
    <link type="text/css" href="/css/jquery/base/ui.all.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="/js/ui/arena.js"></script>
    <script type="text/javascript" src="/js/ui/richlist.js"></script>
    <script type="text/javascript" src="/js/ui/carousel.js"></script>
    <title>Killard Arena</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            border: 0;
            overflow: hidden;
        }

        .container {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            border: 0;
            z-index: 0;
            top: 0;
            left: 0;
            background-image: url(/image/grid/grid.png);
        }

        .panellayer {
            position: absolute;
            left: 0;
            top: 0;
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
        }

        #toppanel {
            position: absolute;
            top: 0;
            left: 0;
            z-index: 20;
            display: none;
        }

        #bottompanel {
            position: absolute;
            left: 0;
            z-index: 100;
            display: none;
        }

        #messagebox {
            position: absolute;
            left: 0;
            top: 0;
            height: 2em;
            z-index: 30;
            background-image: url('/image/texture/white.png');
            text-align: center;
            display: none;
        }

        .carousel {
            background-color: gray;
        }

        .arena {
            position: absolute;
            z-index: 0;
            top: 0;
            left: 0;
        }
    </style>
    <script type="text/javascript">
        $(function() {
            function resizeWindow() {
                var arena = $(".arena").data('arena');
                var carousel = $('.carousel');
                arena.resize();
                var cards = $('.carousel .center .list ul li');
                cards.width(arena.cardWidth);
                cards.css('padding-right', arena.cardSeparator);
                if (carousel.is(':visible')) {
                    carousel.height(arena.cardHeight + 32);
                } else {
                    carousel.height(arena.cardHeight);
                }
                carousel.find('.list').height(arena.cardHeight);
                carousel.each(function() {
                    $(this).data('carousel').resize();
                });
                if (carousel.is(':visible')) {
                    $('#bottompanel').css('top', $(window).height() - carousel.height());
                } else {
                    $('#bottompanel').css('top', $(window).height() - arena.cardHeight - 32);
                }
            }
            $.event.add(window, "load", resizeWindow);
            $.event.add(window, "resize", resizeWindow);
            $(".arena").arena();
            $('.player').toggleClass('emptyPlayer');
            $('.carousel').carousel();
            $('.other img').hide();

            function update() {
                $.getJSON('arena/actions.json', {'lastUpdatedTime':$(window).attr('lastUpdatedTime')},
                        function(data, textStatus) {
                            $.each(data.actions, actionsUpdate);
                            setTimeout(update, 1000);
                        });
            }

            $.getJSON('arena/board.json', function(data, textStatus) {
                $(window).attr('lastUpdatedTime', data.time);
                $.each(data.players, playerUpdate);
                update();
            });
        });
    </script>
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
</head>
<body>
<div class="container">
<div class="carousel" id="toppanel">
    <div class="top"></div>
    <div class="center">
        <ul>
            <li class="arrow"><a class="back"><img src="/image/arrow/left.png"/></a></li>
            <li class="content">
                <div class="list">
                    <ul class="cards">
                        <li><img class="item" src="/image/0.png"/></li>
                    </ul>
                </div>
            </li>
            <li style="float:right;" class="arrow"><a class="forward"><img src="/image/arrow/right.png"/></a></li>
        </ul>
    </div>
    <div class="bottom">
        <div class="searchbar">
            <ul>
                <li class="tag"><a href="#">card</a></li>
                <li class="tag"><a href="#">chess</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="carousel" id="bottompanel" >
    <div class="top"></div>
    <div class="center">
        <ul>
            <li class="arrow"><a class="back"><img src="/image/arrow/left.png"/></a></li>
            <li class="content">
                <div class="list dealtCards">
                    <ul class="cards">
                    </ul>
                </div>
            </li>
            <li style="float:right;" class="arrow"><a class="forward"><img src="/image/arrow/right.png"/></a></li>
        </ul>
    </div>
    <div class="bottom">
        <div class="searchbar">
            <ul>
                <li class="tag"><a href="#">card</a></li>
                <li class="tag"><a href="#">chess</a></li>
            </ul>
        </div>
    </div>
</div>
<div id="messagebox">
</div>
<div class="arena">
    <ul class="top">
        <li>
            <div class="corner" style="text-align:center;color:mediumvioletred;">
                <img src="/image/corner/corner4.png"/>
            </div>
        </li>
        <li>
            <div class="player" number="7">
                <ul>
                    <li><img src="/image/role/role1.png"/></li>
                    <li><img src="/image/head/female.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
            <div class="player" number="6">
                <ul>
                    <li><img src="/image/role/role4.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
            <div class="player" number="5">
                <ul>
                    <li><img src="/image/role/role3.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
        </li>
        <li>
            <div class="corner" style="color:gold;text-align:center;">
                <img src="/image/corner/corner3.png"/>
            </div>
        </li>
    </ul>

    <ul class="center">
        <li>
            <div class="player" number="4">
                <ul>
                    <li><img src="/image/role/role2.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
            <div class="player" number="3">
                <ul>
                    <li><img src="/image/role/role1.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
        </li>
        <li>
            <div class="board">
                <ul class="cardlist other" id="player1">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player2">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player3">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player4">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player5">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player6">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player7">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player8">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player9">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <ul class="cardlist other" id="player10">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <div class="separator"></div>
                <ul class="cardlist self">
                    <li class="card ui-state-default" position="1"></li>
                    <li class="card ui-state-default" position="2"></li>
                    <li class="card ui-state-default" position="3"></li>
                    <li class="card ui-state-default" position="4"></li>
                    <li class="card ui-state-default" position="5"></li>
                    <li class="card ui-state-default" position="6"></li>
                </ul>
                <div class="messagebox">
                    <p></p>
                </div>
            </div>
        </li>
        <li>
            <div class="player" number="8">
                <ul>
                    <li><img src="/image/role/role1.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
            <div class="player" number="9">
                <ul>
                    <li><img src="/image/role/role3.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
        </li>
    </ul>

    <ul class="bottom">
        <li>
            <div class="corner">
                <img src="/image/corner/corner1.png"/>
            </div>
        </li>
        <li>
            <div class="player" number="10">
                <ul>
                    <li><img src="/image/role/role2.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
            <div class="player" number="1">
                <ul>
                    <li><img src="/image/role/role3.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
            <div class="player" number="2">
                <ul>
                    <li><img src="/image/role/role1.png"/></li>
                    <li><img src="/image/head/head.png"/></li>
                    <li><img src="/image/msg/msg.png"/></li>
                </ul>
            </div>
        </li>
        <li>
            <div class="corner">
                <img src="/image/corner/corner2a.png"/>
            </div>
        </li>
    </ul>
</div>
</div>
</body>
</html>