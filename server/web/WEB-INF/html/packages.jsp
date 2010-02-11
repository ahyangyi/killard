<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/html/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <link type="text/css" href="/css/menu.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/richlist.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/rating.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/carousel.css" rel="stylesheet"/>
    <link type="text/css" href="/css/ui/searchbar.css" rel="stylesheet"/>
    <script type="text/javascript" src="/js/jquery-1.4.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="/js/ui/carousel.js"></script>
    <script type="text/javascript" src="/js/ui/richlist.js"></script>
    <script type="text/javascript" src="/js/ui/searchbar.js"></script>
    <title>Killard Games</title>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background:#f9f9f9;
        }

        div.dashboard {
            margin: 40px;
            padding: 0;
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
        }

        div.dashboard ul {
            width: 100%;
            height: 100%;
            list-style-image: none;
            list-style-type: none;
            margin: 0;
            padding: 0;
        }

        div.dashboard ul li {
            display: inline;
            float: left;
            margin: 0;
            padding: 0;
        }

        #package {
            margin: 0;
            padding: 0;
        }

        #package div {
            position: absolute;
            left: 0;
            top: 0;
        }

        #package div.title {
            text-align: center;
            margin-top: 20px;
            margin-left: 50px;
        }

        #newGameForm {
            margin: 0;
            padding: 0;
            display: inline;
            text-align: center;
            display:none;
        }

        .carousel .content {
            background: #D1D1D1;
        }
    </style>
    <script type="text/javascript">
        function updateGameList() {
            $.getJSON('package/' + window.packageBundleId + '/boards.json', '', function(data, textStatus) {
                var richlist = $('.richlist').data('richlist');
                richlist._clear();
                $.each(data, function(i, item) {
                    richlist.items.push('<li>\
                        <a href="arena.html" title="View round">\
                        <img width="' + richlist.height + '" height="' + richlist.height + '" src="image/head/head.png" title=""/></a>\
                        <h5><a href="arena/join.html?packageBundleId=' + item.packageBundleId + '&boardId=' + item.id + '">' + item.players + ' players now, Join</a></h5>\
                        <p class="info">Created by ' + item.username + '</p>\
                        </li>');
                });
            });
//            setTimeout(updateGameList(), 5000);
        }
        function showPackage(image, id, title, description) {
            $('#package > .title > a').attr('href', '/package/' + id + '/view.html');
            $('#package > .title > a').html(title);
            $('#package > #description > .menu > ul > li > p').html(description);
            $('#packageBundleId').attr('value', id);
            $('#package #description img').attr('src', image);
            $('.richlist > ul > li').each(function() {
                $(this).remove();
            });
            $('#newGameForm').show();
            $('.richlist').data('richlist')._clear();
            window.packageBundleId = id;
            updateGameList();
        }
        $(function () {
            function resizeWindow() {
                var packageDiv = $('#package');
                var richlistDiv = $('.richlist');
                var dashboardDiv = $('.dashboard');
                var arrowImg = $('.carousel .arrow img');
                var itemDiv = $('.carousel .center .item');
                var cornerDiv = $('.corner');
                var titleDiv = $('.title');
                var descriptionDiv = $('#description');
                var newGameFormDiv = $('#newGameForm');

                var richlist = richlistDiv.data('richlist');
                var carousel = $('.carousel').data('carousel');
                var searchbar = $('.searchbar').data('searchbar');

                var h = $(window).height() - $('.topbar').height() - $('.bottombar').outerHeight();
                var dashboardVerticalMargin = parseInt(dashboardDiv.css('margin-top'));
                var dashboardHorizontalMargin = parseInt(dashboardDiv.css('margin-left'));
                var packageHeight = parseInt(h * 0.8);
                var packageInnerHeight = packageHeight - 2 * dashboardVerticalMargin;
                var cornerLength = parseInt(packageInnerHeight / 7);

                dashboardDiv.width($(window).width() - parseInt(dashboardDiv.css('margin-left')) - parseInt(dashboardDiv.css('margin-right')));
                dashboardDiv.height(packageInnerHeight);

                cornerDiv.width(cornerLength);
                cornerDiv.height(cornerLength);
                cornerDiv.find('img').width(cornerLength);
                cornerDiv.find('img').height(cornerLength);

                cornerDiv.eq(0).css('top', $('.topbar').height() + dashboardVerticalMargin);
                cornerDiv.eq(0).css('left', dashboardHorizontalMargin);

                cornerDiv.eq(1).css('top', $('.topbar').height() + dashboardVerticalMargin);
                cornerDiv.eq(1).css('left', dashboardHorizontalMargin + packageDiv.width() - cornerDiv.width() - cornerLength / 2);

                cornerDiv.eq(2).css('top', $('.topbar').height() + packageInnerHeight + dashboardVerticalMargin - cornerLength);
                cornerDiv.eq(2).css('left', dashboardHorizontalMargin);

                cornerDiv.eq(3).css('top', $('.topbar').height() + packageInnerHeight + dashboardVerticalMargin - cornerLength);
                cornerDiv.eq(3).css('left', dashboardHorizontalMargin + packageDiv.width() - cornerDiv.width() - cornerLength / 2);

                titleDiv.css('top', $('.topbar').outerHeight() + dashboardVerticalMargin);
                titleDiv.css('left', dashboardHorizontalMargin + cornerLength);

                var descriptionHeight = parseInt(packageInnerHeight * 4 / 5);
                var descriptionWidth = parseInt(dashboardDiv.width() * 0.7 - cornerLength);
                descriptionDiv.css('top', $('.topbar').outerHeight() + dashboardVerticalMargin + cornerLength);
                descriptionDiv.css('left', dashboardHorizontalMargin + cornerLength);
                descriptionDiv.height(descriptionHeight);
                descriptionDiv.find('.menu').width(descriptionWidth);
                var showUnit = Math.min(descriptionHeight / 28, descriptionWidth / 18);
                descriptionDiv.find('img').height(parseInt(showUnit * 14));
                descriptionDiv.find('img').width(parseInt(showUnit * 9));
                packageDiv.height(packageInnerHeight);
                newGameFormDiv.css('top', $('.topbar').outerHeight() + dashboardVerticalMargin + cornerLength + descriptionHeight);
                newGameFormDiv.css('left', dashboardHorizontalMargin + cornerLength);
                newGameFormDiv.width(descriptionWidth - 2 * cornerLength);
                newGameFormDiv.height(cornerLength);

                richlistDiv.css('left', packageDiv.width());
                richlistDiv.height(packageInnerHeight);
                richlist.resize();

                var carouselHeight = h - packageHeight;
                $('.carousel').height(carouselHeight);

                var itemHeight = carouselHeight - $('.searchbar').height();
                itemDiv.width(itemHeight);
                itemDiv.css('margin-right', itemHeight / 4);
                $('.carousel .center .list ul li').width( itemDiv.width() * 5 / 4);
                $('.carousel .center .list ul').width(itemDiv.length * itemDiv.width() * 5 / 4);
                arrowImg.css('margin-top', (itemHeight - arrowImg.height()) / 2);

                carousel.resize();
            }
            $.event.add(window, 'load', function(){resizeWindow();$('.richlist').data('richlist')._update();});
            $.event.add(window, 'resize', resizeWindow);

            $('.searchbar').searchbar();
            $('.carousel').carousel();
            $('.richlist').richlist();

            $.getJSON('packages.json', function(data, textStatus) {
                $.each(data, function(i, item) {
                    $("#packageList").append('<li><img onclick="showPackage(\'' + item.picture + '\',' + item.id + '\',' + item.title + '\',' + item.description + ')" class="item" src="' + item.picture + '"/></li>');
                });
                resizeWindow();
                showPackage(data[0].picture, data[0].id, data[0].title, data[0].description);
            });
        });
    </script>
</head>
<body>
<%@ include file="topbar.jsp" %>
<div class="dashboard">
    <ul>
        <li style="width:70%;">
            <div id="package">
                <div class="corner"><img src="/image/ring.png"/></div>
                <div class="corner"><img src="/image/ring.png"/></div>
                <div class="corner"><img src="/image/ring.png"/></div>
                <div class="corner"><img src="/image/ring.png"/></div>
                <div class="title"><a href="#"></a></div>
                <div id="description">
                    <div class="menu">
                        <ul>
                            <li><img src="/image/progress.gif"/></li>
                            <li>
                                <p>Loading...</p>
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="newGameForm">
                    <form action="/arena/new.html" method="POST">
                        <input id="packageBundleId" type="hidden" name="packageBundleId" value=""/>
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
        </li>
        <li style="width:30%;">
            <div class="richlist">
                <ul></ul>
            </div>
        </li>
    </ul>
</div>
<div class="carousel">
    <div class="top"></div>
    <div class="center">
        <ul>
            <li class="arrow"><a class="back"><img src="/image/arrow/left.png"/></a></li>
            <li class="content">
                <div class="list">
                    <ul id="packageList"></ul>
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
<%@ include file="bottombar.jsp" %>
</body>
</html>