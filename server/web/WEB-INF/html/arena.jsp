<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="css/jquery/base/ui.all.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/arena.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="js/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="js/ui/transparent.js"></script>
    <script type="text/javascript" src="js/ui/arena.js"></script>
    <script type="text/javascript" src="js/ui/richlist.js"></script>
    <script type="text/javascript" src="js/ui/carousel.js"></script>
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
            background-image: url(image/grid/grid.png);
        }

        .carousel {
            background-image: url(image/texture/darkgray.png);
        }

        .carousel .arrow {
            background-image: url(image/texture/white.png);
        }

        .ui-accordion .ui-accordion-content {
            background: url(image/texture/gray.png);
            padding: 1em 1em
        }
    </style>
    <script type="text/javascript">
        $(function() {
            function resizeWindow() {
                var arena = $(".arena").data('arena');
                var sidebar = $('#sidebar').data('accordion');
                var carousel = $('.carousel');
                arena.resize();
                if (sidebar) sidebar.resize();
                var cards = $('.carousel .center .list ul li');
                var cardPadding = parseInt(arena.cardSeparator / 2);
                cards.width(arena.cardWidth).height(arena.cardHeight)
                        .css({'padding-left': cardPadding, 'padding-right': cardPadding, 'padding-top': cardPadding});
                carousel.height(arena.cardHeight + arena.cardSeparator + 32);
                carousel.each(function() {
                    $(this).data('carousel').resize();
                });
            }
            $.event.add(window, "load", resizeWindow);
            $.event.add(window, "resize", resizeWindow);
            $('.arena').arena();
            $('#sidebar').accordion({
                autoHeight: false,
                fillSpace: true
            });
//            $('#sidebar').resizable({
//                handles: 'w',
//                alsoResize: '.container',
//                stop: function(event, ui) {
//                    $('.arena').data('arena').resize();
//                }
//            });
            $('.player').toggleClass('emptyPlayer');
            $('.carousel').carousel();
            $('.player > ul > li > img').hide();
            $('.other').hide();
            function update() {
                $.getJSON('arena/actions.json', {'since':$(window).data('since')},
                        function(actions, textStatus) {
                            $.each(actions, actionUpdate);
                            setTimeout(checkStatus, 2000);
                        });
            }
            function checkStatus() {
                $.get('arena/status.json', function(data, textStatus) {
                    if (parseInt(data) > $(window).data('since')) update();
                    else setTimeout(checkStatus, 2000);
                });
            }
            $('#warning').ajaxError(function(event, request, settings, error) {
                $(this).text('Error:' + error).fadeIn("slow", function(){
                    setTimeout(function(){$('#warning').fadeOut("slow")}, 2000);
                });
            });
            $.getJSON('arena/board.json', function(data, textStatus) {
                $(window).data('since', data.lastAction);
                $.each(data.players, playerUpdate);
                setTimeout(checkStatus, 2000);
            });
        });
    </script>
</head>
<body>
<div class="container">
<div class="carousel" id="toppanel">
    <div class="top"></div>
    <div class="center">
        <ul>
            <li class="arrow"><a class="back"><img src="image/arrow/left.png"/></a></li>
            <li class="content">
                <div class="list">
                    <ul class="cards">
                        <li><img class="item" src="image/0.png"/></li>
                    </ul>
                </div>
            </li>
            <li style="float:right;" class="arrow"><a class="forward"><img src="image/arrow/right.png"/></a></li>
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
            <li class="arrow"><a class="back"><img src="image/arrow/left.png"/></a></li>
            <li class="content">
                <div class="list dealtCards">
                    <ul class="cards">
                    </ul>
                </div>
            </li>
            <li style="float:right;" class="arrow"><a class="forward"><img src="image/arrow/right.png"/></a></li>
        </ul>
    </div>
    <div class="bottom">
        <div class="searchbar">
            <ul>
            </ul>
        </div>
    </div>
</div>
<div class="arena">
    <ul class="top">
        <li>
            <div class="corner" style="text-align:center;color:mediumvioletred;">
                <img src="image/corner/corner4.png"/>
            </div>
        </li>
        <li>
            <div class="player" number="7">
                <ul>
                    <li><img src="image/role/role1.png"/></li>
                    <li><img src="image/head/female.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
            <div class="player" number="6">
                <ul>
                    <li><img src="image/role/role4.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
            <div class="player" number="5">
                <ul>
                    <li><img src="image/role/role3.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
        </li>
        <li>
            <div class="corner" style="color:gold;text-align:center;">
                <img src="image/corner/corner3.png"/>
            </div>
        </li>
    </ul>

    <ul class="center">
        <li>
            <div class="player" number="8">
                <ul>
                    <li><img src="image/role/role2.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
            <div class="player" number="9">
                <ul>
                    <li><img src="image/role/role1.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
        </li>
        <li>
            <div class="board">
                <ul class="cardlist other" number="1">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="2">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="3">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="4">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="5">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="6">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="7">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="8">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="9">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <ul class="cardlist other" number="10">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
                <div class="separator"></div>
                <ul class="cardlist self">
                    <li class="card" position="1"></li>
                    <li class="card" position="2"></li>
                    <li class="card" position="3"></li>
                    <li class="card" position="4"></li>
                    <li class="card" position="5"></li>
                </ul>
            </div>
        </li>
        <li>
            <div class="player" number="4">
                <ul>
                    <li><img src="image/role/role1.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
            <div class="player" number="3">
                <ul>
                    <li><img src="image/role/role3.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
        </li>
    </ul>

    <ul class="bottom">
        <li>
            <div class="corner">
                <img src="image/corner/corner1.png"/>
            </div>
        </li>
        <li>
            <div class="player" number="10">
                <ul>
                    <li><img src="image/role/role2.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
            <div class="player" number="1">
                <ul>
                    <li><img src="image/role/role3.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
            <div class="player" number="2">
                <ul>
                    <li><img src="image/role/role1.png"/></li>
                    <li><img src="image/head/head.png"/></li>
                    <li>
                        <img src="image/msg/msg.png"/>
                        <div class="score"></div>
                    </li>
                </ul>
            </div>
        </li>
        <li>
            <div class="corner">
                <img src="image/corner/corner2.png"/>
            </div>
        </li>
    </ul>
</div>
<div id="warning"></div>
<div id="tip"></div>
<div id="sidebar">
    <h3><a href="#">Chat</a></h3>
    <div>
        <label>
            <input type="text" size="15"/>
        </label>
        <hr/>
        <ul>
        </ul>
    </div>
    <h3><a href="#">Players</a></h3>
    <div>Second content</div>
    <h3><a href="#">Game Information</a></h3>
    <div>Invite others to join this game</div>
</div>
</div>
</body>
</html>