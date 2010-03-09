<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link type="text/css" href="css/jquery/base/ui.all.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/arena.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="js/jquery.layout.min.js"></script>
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

        .carousel .arrow {
            background-image: url(image/texture/gray.png);
        }

        .ui-accordion .ui-accordion-content {
            background: url(image/texture/gray.png);
            padding: 1em 1em
        }

        .ui-layout-resizer {
        /* all 'resizer-bars' */
            background: #DDD;
            border: 1px solid #BBB;
            border-width: 0;
        }

        .ui-layout-resizer-drag {
        /* REAL resizer while resize in progress */
        }

        .ui-layout-resizer-hover {
        /* affects both open and closed states */
        }

        /* NOTE: It looks best when 'hover' and 'dragging' are set to the same color,
           otherwise color shifts while dragging when bar can't keep up with mouse */
        .ui-layout-resizer-open-hover, /* hover-color to 'resize' */
        .ui-layout-resizer-dragging {
        /* resizer beging 'dragging' */
            background: #C4E1A4;
        }

        .ui-layout-resizer-dragging {
        /* CLONED resizer being dragged */
            border-left: 1px solid #BBB;
            border-right: 1px solid #BBB;
        }

        /* NOTE: Add a 'dragging-limit' color to provide visual feedback when resizer hits min/max size limits */
        .ui-layout-resizer-dragging-limit {
        /* CLONED resizer at min or max size-limit */
            background: #E1A4A4; /* red */
        }

        .ui-layout-resizer-closed-hover {
        /* hover-color to 'slide open' */
            background: #EBD5AA;
        }

        .ui-layout-resizer-sliding {
        /* resizer when pane is 'slid open' */
            opacity: .10; /* show only a slight shadow */
            filter: alpha(opacity = 10);
        }

        .ui-layout-resizer-sliding-hover {
        /* sliding resizer - hover */
            opacity: 1.00; /* on-hover, show the resizer-bar normally */
            filter: alpha(opacity = 100);
        }

        /* sliding resizer - add 'outside-border' to resizer on-hover
         * this sample illustrates how to target specific panes and states */
        .ui-layout-resizer-north-sliding-hover {
            border-bottom-width: 1px;
        }

        .ui-layout-resizer-south-sliding-hover {
            border-top-width: 1px;
        }

        .ui-layout-resizer-west-sliding-hover {
            border-right-width: 1px;
        }

        .ui-layout-resizer-east-sliding-hover {
            border-left-width: 1px;
        }

        /*
        *#########################
        *	ACCORDIAN WIDGET
        *#########################
        */
        /*.ui-layout-east {*/
        /* content-div has Accordion */
            /*padding: 0;*/
            /*overflow: hidden;*/
        /*}*/
        /*div.basic {*/
            /*margin: 0;*/
            /*padding: 0;*/
        /*}*/

        /*.basic > a,*/
        /*.basic > a.selected,*/
        /*.basic > a.active,*/
        /*.basic > a:hover {*/
            /*color: black; *//*font-weight:	bold;*/
            /*text-transform: uppercase;*/
            /*text-decoration: none;*/
            /*outline: none;*/
            /*border-top: 1px solid #FFF;*/
            /*border-bottom: 1px solid #999;*/
            /*cursor: pointer;*/
            /*display: block;*/
            /*padding: 5px;*/
        /*}*/

        /*.basic > a {*/
            /*background: #00a0c6 url("image/tab0.gif");*/
        /*}*/

        /*.basic > a.selected,*/
        /*.basic > a.active,*/
        /*.basic > a:hover {*/
            /*background-image: url("image/tab2.gif");*/
        /*}*/
        /*.basic > a.active {*/
            /*color: #00C;*/
        /*}*/
        /*.basic > div.scrolling {*/
            /*overflow: auto !important;*/
        /*}*/
        /*.basic > div > p {*/
            /*padding: 0 10px;*/
            /*margin: 1.5ex 0;*/
        /*}*/
        /*.basic > div > ul {*/
            /*margin-top: 1ex;*/
        /*}*/
    </style>
    <script type="text/javascript">
        $(function() {
            $('body').layout({
                east__size: 250,
                north__spacing_open: 0,
                south__spacing_open: 0,
                east__togglerLength_open: "100%",
                east__togglerLength_closed: "100%",
                center__onresize: function () {
                    $('#arena').arena('resize');
                },
                east__onresize: function () {
                    $('#sidebar').accordion('resize');
                }
            });

            $('.carousel').carousel();
            $('#sidebar').accordion({fillSpace: true});
            $('#arena').arena();

            $('.player').toggleClass('emptyPlayer');
            $('.player > ul > li > img').hide();
            $('.other').hide();

            function update() {
                $.getJSON('arena/actions.json', {'since':$(window).data('since')},
                        function(actions, textStatus) {
                            $.each(actions, function(i, action) {$('#arena').arena('actionUpdate', i, action);});
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
                $.each(data.players, function(i, player){$('#arena').arena('updatePlayer', player);});
                setTimeout(checkStatus, 2000);
            });
        });
    </script>
</head>
<body>
<div class="ui-layout-north" style="display: none;">
    <div id="warning"></div>
</div>

<div class="ui-layout-center" style="display: none;">
<div id="arena">
<div class="carousel" id="toppanel">
    <div class="center">
        <div class="arrow"><a class="back"><img src="image/arrow/left.png"/></a></div>
        <div class="content">
            <ul class="cards">
                <div><img class="item card" src="image/0.png"/></div>
            </ul>
        </div>
        <div class="arrow"><a class="forward"><img src="image/arrow/right.png"/></a></div>
    </div>
</div>

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

<div class="carousel" id="bottompanel">
    <div class="center">
        <div class="arrow"><a class="back"><img src="image/arrow/left.png"/></a></div>
        <div class="content">
            <ul class="cards" id="dealtCards">
            </ul>
        </div>
        <div class="arrow"><a class="forward"><img src="image/arrow/right.png"/></a></div>
    </div>
    <div class="bottom">
        <div class="searchbar">
            <ul>
            </ul>
        </div>
    </div>
</div>

</div>
</div>

<div class="ui-layout-east" style="display: none;">
<div id="sidebar" class="basic">
    <h3><a href="#">Chat</a></h3>
    <div>
        <label>
            <input type="text" size="15" id="chatinput"/>
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

<div class="ui-layout-south" style="display: none;">
    <div id="tip"></div>
</div>
</body>
</html>