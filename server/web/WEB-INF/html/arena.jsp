<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="css/jquery/base/ui.all.css" rel="stylesheet"/>
    <link type="text/css" href="css/accordion.css" rel="stylesheet"/>
    <link type="text/css" href="css/layout.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/arena.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="js/jquery.layout-latest.js"></script>
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
            background-image: url(image/grid/grid.png);
        }

        .carousel {
            background-image: url(image/texture/darkgray.png);
        }

        .ui-state-disabled {
            opacity: .80;
            filter: alpha(opacity = 80);
        }

        .ui-accordion .ui-accordion-content {
            background: url(image/texture/gray.png);
            padding: 1em 1em
        }

        .ui-layout-east {
            padding: 0;
            overflow: hidden;
        }

        #sidebar h4 {
            font-size: 0.9em;
            font-weight: normal;
            border-width: 1px 0 0;
        }
    </style>
    <script type="text/javascript">
        $(function() {
            $('#loadingbar').progressbar();
            $('body').layout({
                togglerLength_open: 35,
                togglerLength_closed: 35,
                north__resizable: true,
                north__closable: false,
                north__spacing_open: 1,
                south__resizable: true,
                south__closable: false,
                south__spacing_open: 1,
                east__size: 250,
                center__onresize: function () {
                    $('#arena').arena('resize');
                },
                east__onresize: function () {
                    $('#sidebar').accordion('resize');
                },
                east__onopen: function () {
                    $('#sidebar').accordion({fillSpace: true});
                },
                east__onclose: function () {
                    $('#sidebar').accordion('destroy');
                }
            });
            $('.carousel').carousel();
            $('#sidebar').accordion({fillSpace: true});
            $('#arena').arena();
        });
    </script>
</head>
<body>

<div id="loading">
    <div style="padding:100px;text-align:center;">
        <h3>Loading...</h3>
        <div id="loadingbar"></div>
    </div>
</div>

<div class="ui-layout-north" style="display: none;">
    <div id="warning"></div>
    <div id="menu">
        <a href="arena/quit.html">Quit</a>
    </div>
</div>

<div class="ui-layout-center" style="display: none;">
<div class="carousel" id="toppanel">
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
</div>
<div id="arena">

<ul class="top">
    <li>
        <div class="corner">
            <img src="image/corner/corner4.png"/>

            <div class="label">1</div>
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
        <div class="corner">
            <img src="image/corner/corner3.png"/>

            <div class="label">1</div>
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

<div class="carousel" id="bottompanel">
    <div class="center">
        <ul>
            <li class="arrow"><a class="back"><img src="image/arrow/left.png"/></a></li>
            <li class="content">
                <div class="list">
                    <ul id="dealtCards">
                    </ul>
                </div>
            </li>
            <li class="arrow"><a class="forward"><img src="image/arrow/right.png"/></a></li>
        </ul>
    </div>
    <div class="bottom">
        <div class="searchbar">
            <ul>
            </ul>
        </div>
    </div>
</div>
</div>

<div class="ui-layout-east" style="display: none;">
    <div id="sidebar" class="basic">
        <a href="#">Chat</a>

        <div>
            <label>
                <input type="text" size="15" id="chatinput"/>
            </label>
            <hr/>
            <ul>
            </ul>
        </div>
        <a href="#">Players</a>

        <div>Second content</div>
        <a href="#">Game Information</a>

        <div>Invite others to join this game</div>
    </div>
</div>

<div class="ui-layout-south" style="display: none;">
    <div id="tip"></div>
</div>
</body>
</html>