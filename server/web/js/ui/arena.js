(function($) {
    $.widget("ui.arena", {
        _init: function() {
            var options = this.options;
            var arena = this;

            this.corner = this.element.find(options.corner);
            this.player = this.element.find(options.player);
            this.board = this.element.find(options.board);
            this.boardSeparator = this.element.find(options.boardSeparator);
            this.card = this.element.find(options.card);
            this.cardlist = this.element.find(options.cardlist);
            this.targetList = [];
            this.targets = [];

            this.card.find('.cardimage').click(function() {
//                $(this).effect('explode', {pieces: 16}, 800, function() {
//                    var card = $(this);
//                    setTimeout(function() {
//                        card.fadeIn(2000);
//                    }, 1000);
//                });
                if (arena.targetList != null && arena.targetList.length > 0) {
                    if (arena.targetList[arena.targets.length] == 'owncard'
                            || arena.targetList[arena.targets.length] == 'otherscard') {
                        arena.targets.push($(this).attr('src'));
                        arena.fillTargets();
                    }
                }
            });

            this.card.find('.skillimage').click(function() {
                if (arena.targetList.length == 0) {
                    arena.targetList = ['owncard', 'self', 'other', 'otherscard', 'all'];
                    arena.fillTargets();
                }
            });

            this.player.click(function() {
                $.get('/arena/join.html', {'number':$(this).attr('number')});
            });

            this.player.find('li :first').click(function() {
                var playerDiv = $(this).parent().parent().parent();
                if (!playerDiv.attr('self')) {
                    var number = playerDiv.attr('number');
                    var cardList = $('#player' + number + ' img');
                    if (cardList.is(':hidden')) {
                        $('.other img:visible').hide('drop', {direction:'down'});
                        cardList.show('drop', {direction:'up'});
                    }
                }
            });

            this.player.find('li :eq(1)').click(function() {
                if (arena.targetList != null && arena.targetList.length > 0) {
                    if (arena.targetList[arena.targets.length] == 'other') {
                        arena.targets.push($(this).attr('src'));
                        arena.fillTargets();
                    }
                }
            });

            this.player.find('li :last').click(function() {
                var head = $(this).parent().prev();
                head.effect('shake', {direction:'left'});
                $('.message').dialog('open');
            });

            this.corner.eq(0).click(function() {
                $('#toppanel').show('drop', {direction:'up'}, function() {
                    var items = $('#toppanel .center .list ul li');
                    $('#toppanel .center .list ul').width(items.outerWidth() * items.length);
                });
            }).css('cursor', 'pointer');

            this.corner.eq(2).hover(
                    function() {
                        $(this).find('img').attr('src', 'image/corner/corner1a.png');
                    },
                    function() {
                        $(this).find('img').attr('src', 'image/corner/corner1.png');
                    }).mousedown(
                    function() {
                        $(this).find('img').attr('src', 'image/corner/corner1b.png');
                    }
                    ).mouseup(
                    function() {
                        $(this).find('img').attr('src', 'image/corner/corner1a.png');
                        $('#bottompanel').show('drop', {direction:'down'}, function() {
                            var items = $('#bottompanel .center .list ul li');
                            $('#bottompanel .center .list ul').width(items.outerWidth() * items.length);
                            $('.item').draggable({ opacity: 0.7, helper: 'clone' });
                        });
                    }
                    ).css('cursor', 'pointer');

            $('.self .card').droppable({
                accept: '.item',
                drop: function(event, ui) {
                    $.post('arena/playcard.json',{
                        'elementSchoolName':ui.draggable.attr('elementSchool'),
                        'cardName':ui.draggable.attr('cardName'),
                        'cardPosition':$(this).attr('position'),
                        'targetPosition':0});
                }
            });

            $('#toppanel').click(function() {
                $(this).hide('drop', {direction:'up'});
            });

            $('#bottompanel').click(function() {
                $(this).hide('drop', {direction:'down'});
            });

            this.resize();
        },

        destroy: function() {
            this.card.data('sortable').destroy();
            this.player.find('.message').data('dialog').destroy();
        },

        _setData: function(key, value) {
            $.widget.prototype._setData.apply(this, arguments);
        },

        fillTargets: function() {
            while (this.targets.length < this.targetList.length) {
                if (this.targetList[this.targets.length] == 'all') {
                    this.targets.push('all');
                }
                if (this.targetList[this.targets.length] == 'self') {
                    this.targets.push('self');
                }
                if (this.targetList[this.targets.length] == 'other') {
                    $('#messagebox').text('Please select a player.');
                    break;
                }
                if (this.targetList[this.targets.length] == 'owncard') {
                    $('#messagebox').text('Please select a card of yours.');
                    break;
                }
                if (this.targetList[this.targets.length] == 'otherscard') {
                    $('#messagebox').text('Please select a card of others.');
                    break;
                }
            }
            if ($('#messagebox').is(':hidden')) {
                $('#messagebox').show();
            }
            if (this.targets.length == this.targetList.length) {
                $('#messagebox').hide();
                this.targetList = [];
                this.targets = [];
            }
        },

        getInt: function(element, attr) {
            var result = parseInt(element.css(attr));
            if (isNaN(result)) return 0;
            return result;
        },

        getHorizontalGap: function(element) {
            return this.getInt(element, 'margin-left') + this.getInt(element, 'margin-right')
                    + ($.support.boxModel ? this.getInt(element, 'padding-left') + this.getInt(element, 'padding-right')
                    + this.getInt(element, 'border-left-width') + this.getInt(element, 'border-right-width') : 0);
        },

        getVerticalGap: function(element) {
            return this.getInt(element, 'margin-top') + this.getInt(element, 'margin-bottom')
                    + ($.support.boxModel ? this.getInt(element, 'padding-top') + this.getInt(element, 'padding-bottom')
                    + this.getInt(element, 'border-top-width') + this.getInt(element, 'border-bottom-width') : 0);
        },

        getHorizontalBorder: function(element) {
            return this.getInt(element, 'border-left-width') + this.getInt(element, 'border-right-width');
        },

        getVerticalBorder: function(element) {
            return this.getInt(element, 'border-top-width') + this.getInt(element, 'border-bottom-width');
        },

        getHorizontalMargin: function(element) {
            return this.getInt(element, 'margin-left') + this.getInt(element, 'margin-right');
        },

        getVerticalMargin: function(element) {
            return this.getInt(element, 'margin-top') + this.getInt(element, 'margin-bottom');
        },

        getHorizontalPadding: function(element) {
            return this.getInt(element, 'padding-left') + this.getInt(element, 'padding-right');
        },

        getVerticalPadding: function(element) {
            return this.getInt(element, 'padding-top') + this.getInt(element, 'padding-bottom');
        },

        resize: function() {
            var options = this.options;

            /* Retrieve width and size of arena. */
            var w = Math.min(this.element.parent().width(), $(window).width());
            var h = Math.min(this.element.parent().height(), $(window).height());
            if (w <= 0) w = this.element.width();
            if (h <= 0) h = this.element.height();

            /* Calculate width/height ratio of board. */
            var boardGap = 2 * (options.boardMarginRatio + options.boardPaddingRatio);
            var boardWidthRatio = 54 + 5 * options.cardSeparatorRatio + boardGap;
            var boardHeightRatio = 28 + options.boardSeparatorRatio + boardGap;
            var boardRatio = boardWidthRatio / boardHeightRatio;

            /* Since the width/height ratios of arena and board are different, we need to scale arena. */
            var arenaPaddingRatio = 2 * options.arenaPaddingRatio;
            var arenaWidthRatio = w - w * (2 + arenaPaddingRatio) / (27 + arenaPaddingRatio);
            var arenaHeightRatio = h - h * (2 + arenaPaddingRatio) / (14 + arenaPaddingRatio);
            var arenaScale = arenaWidthRatio / (arenaHeightRatio * boardRatio);
            var arenaMargin = 0;

            if (arenaScale == 1) {
                this.element.eq(0).css({
                    'margin-left' : 0,
                    'margin-right' : 0,
                    'margin-top' : 0,
                    'margin-bottom' : 0
                });
            } else if (arenaScale > 1) {
                arenaMargin = parseInt(w * (arenaScale - 1) / (2 * arenaScale));
                this.element.eq(0).css({
                    'margin-left' : arenaMargin,
                    'margin-right' : arenaMargin,
                    'margin-top' : 0,
                    'margin-bottom' : 0
                });
                w = w - 2 * arenaMargin;
            } else {
                arenaMargin = parseInt(h * (1 - arenaScale) / 3);
                this.element.eq(0).css({
                    'margin-left' : 0,
                    'margin-right' : 0,
                    'margin-top' : arenaMargin,
                    'margin-bottom' : arenaMargin
                });
                h = h - 3 * arenaMargin;
            }

            /* Caculate length unit of arena's border. */
            var lengthUnit = h / (14 + arenaPaddingRatio);

            /* Caculate player related size as integers. */
            var playerShortEdge = parseInt(lengthUnit);
            var playerLongEdge = parseInt(3 * lengthUnit);

            var arenaPadding = parseInt(lengthUnit * options.arenaPaddingRatio);
            this.element.eq(0).css({
                'padding-left' : arenaPadding,
                'padding-right' : arenaPadding,
                'padding-top' : arenaPadding,
                'padding-bottom' : arenaPadding
            });

            var playerHorizontalMargin = parseInt((w - 2 * arenaPadding - 2 * playerShortEdge - 3 * playerLongEdge)
                    / 8);
            var playerVerticalMargin = parseInt((h - 2 * arenaPadding - 2 * playerShortEdge - 2 * playerLongEdge) / 3);

            var cornerMargin = w - 2 * (arenaPadding + playerShortEdge) - 3 * (playerLongEdge + 2
                    * playerHorizontalMargin);
            var cornerRightMargin = parseInt(cornerMargin / 2);
            var cornerLeftMargin = cornerMargin - cornerRightMargin;

            /* Render corners */
//            this.corner.width(playerShortEdge);
//            this.corner.height(playerShortEdge);
            this.corner.each(function() {
                $('img', this).width(playerShortEdge);
                $('img', this).height(playerShortEdge);
                $(this).width(playerShortEdge);
                $(this).height(playerShortEdge);
            });
//            this.corner.find('img').width(playerShortEdge);
//            this.corner.find('img').height(playerShortEdge);

            this.corner.eq(0).css('margin-right', cornerRightMargin);
            this.corner.eq(1).css('margin-left', cornerLeftMargin);
            this.corner.eq(2).css('margin-right', cornerRightMargin);
            this.corner.eq(3).css('margin-left', cornerLeftMargin);

            /* Render players */
            /* North */
            var player = this.player.slice(0, 3);
            player.width(playerLongEdge);
            player.height(playerShortEdge);
            player.css({
                'margin-left' : playerHorizontalMargin,
                'margin-right' : playerHorizontalMargin,
                'margin-top' : 0,
                'margin-bottom' : 0
            });

            /* West & East */
            player = this.player.slice(3, 7);
            player.width(playerShortEdge);
            player.height(playerLongEdge);
            player.css({
                'margin-left' : 0,
                'margin-right' : 0,
                'margin-top' : playerVerticalMargin,
                'margin-bottom' : playerVerticalMargin
            });

            /* South */
            player = this.player.slice(7);
            player.width(playerLongEdge);
            player.height(playerShortEdge);
            player.css({
                'margin-left' : playerHorizontalMargin,
                'margin-right' : playerHorizontalMargin,
                'margin-top' : 0,
                'margin-bottom' : 0
            });

            /* Set player size. */
            this.player.find('img').width(playerShortEdge);
            this.player.find('img').height(playerShortEdge);

            /* We calculate board size as integers. */
            var boardWidth = w - 2 * (arenaPadding + playerShortEdge);
            var boardHeight = h - 2 * (arenaPadding + playerShortEdge);

            /* These numbers are real, we transfer them to integer as needed. */
            var boardLengthUnit = boardWidth / boardWidthRatio;

            /* Calculate board margin and card size as integers. */
            var boardMargin = parseInt(options.boardMarginRatio * boardLengthUnit);
            var boardPadding = parseInt(options.boardPaddingRatio * boardLengthUnit);

            this.board.css({
                'margin-left' : boardMargin - this.getInt(this.board, 'border-left-width'),
                'margin-right' : boardMargin - this.getInt(this.board, 'border-right-width'),
                'margin-top' : boardMargin - this.getInt(this.board, 'border-top-width'),
                'margin-bottom' : boardMargin - this.getInt(this.board, 'border-bottom-width'),
                'padding-left' : boardPadding,
                'padding-right' : boardPadding,
                'padding-top' : boardPadding,
                'padding-bottom' : boardPadding
            });

            this.board.width(boardWidth - this.getHorizontalGap(this.board));
            this.board.height(boardHeight - this.getVerticalGap(this.board));

            /* Calculate card size as integers. */
            this.cardWidth = parseInt(9 * boardLengthUnit);
            this.cardHeight = parseInt(14 * boardLengthUnit);
            var cardSeparator = parseInt((parseInt(this.board.width()) - 6 * this.cardWidth) / 5);
            this.cardSeparator = cardSeparator;

            var boardSeparatorMargin = boardHeight - 2 * boardMargin - 2 * boardPadding - 2 * this.cardHeight -
                                       this.getVerticalBorder(this.board);
            this.boardSeparator.css('margin-top', parseInt(boardSeparatorMargin / 2));
            this.boardSeparator.css('margin-bottom', boardSeparatorMargin - parseInt(boardSeparatorMargin / 2));

            /* Render cards */
            this.otherCardsLeft = arenaPadding + playerShortEdge + boardMargin + boardPadding +
                                  parseInt(this.board.css('border-top-width'));
            this.otherCardsTop = arenaPadding + playerShortEdge + boardMargin + boardPadding +
                                 parseInt(this.board.css('border-left-width'));
            this.myCardsLeft = this.otherCardsLeft;
            this.myCardsTop = arenaPadding + playerShortEdge + boardMargin + boardPadding + this.cardHeight +
                              boardSeparatorMargin + this.getVerticalBorder(this.boardSeparator);
            $('.other').css('left', this.otherCardsLeft);
            $('.other').css('top', this.otherCardsTop);
            this.boardSeparator.width(this.board.width());
//            this.boardSeparator.height(0);
            this.boardSeparator.css('left', this.otherCardsLeft);
            this.boardSeparator.css('top', this.otherCardsTop + this.cardHeight);
            $('.self').css('left', this.myCardsLeft);
            $('.self').css('top', this.myCardsTop);

            this.cardlist.height(this.cardHeight);
            this.card.css('border', 'none');
            this.card.css('background', 'none');
            $('.other').find('.card:lt(5)').css('margin-right', cardSeparator);
            $('.self').find('.card:lt(5)').css('margin-right', cardSeparator);

            this.card.width(this.cardWidth);
            this.card.height(this.cardHeight);
            this.card.find('.cardimage').width(this.cardWidth);
            this.card.find('.cardimage').height(this.cardHeight);
            $('.item').width(this.cardWidth);
            $('.item').height(this.cardHeight);

            $('.other').each(function() {
                $(this).find('.card').each(function(i, e) {
                    $(e).css('left', i * (cardSeparator + parseInt(9 * boardLengthUnit)));
                });
            });
            $('.self').find('.card').each(function(i, e) {
                $(e).css('left', i * (cardSeparator + parseInt(9 * boardLengthUnit)));
            });

            this.card.find('.skillimage').css('left', this.cardWidth / 3);
            this.card.find('.skillimage').css('top', this.cardHeight - this.cardWidth / 3);
            this.card.find('.skillimage').width(this.cardWidth / 3);
            this.card.find('.skillimage').height(this.cardWidth / 6);

            $('.bottompanel').css('top', $(window).height() - this.cardHeight - 2 * boardLengthUnit);

            $('#messagebox').width(400);
            $('#messagebox').css('top', playerShortEdge + arenaPadding + boardHeight / 2 - $('#messagebox').height() / 2);
            $('#messagebox').css('left', arenaPadding + playerShortEdge + boardWidth / 2 - 200);
            if (arenaScale > 1) {
                $('#messagebox').css('left', arenaMargin + arenaPadding + playerShortEdge + boardWidth / 2 - 200);
            }
            if (arenaScale < 1) {
                $('#messagebox').css('top', arenaMargin + playerShortEdge + arenaPadding + boardHeight / 2 - $('#messagebox').height() / 2);
            }
        }
    });

    $.extend($.ui.arena, {
        version: "0.1",
        defaults: {
            corner : '.corner',
            player : '.player',
            board : '.board',
            boardSeparator : '.separator',
            cardlist : '.cardlist',
            card : '.card',
            arenaPaddingRatio : 0.75,
            boardMarginRatio : 1.5,
            boardPaddingRatio : 2,
            boardSeparatorRatio : 2,
            cardSeparatorRatio : 2
        }
    });
})(jQuery);

function playerUpdate(i, player) {
    var playerDiv = $('.player[number="' + player.number + '"]');
    playerDiv.unbind('click');
    var image = playerDiv.find('> ul > li > img');
    if (image.is(':hidden')) {
        image.show();
        if (player.self) {
            playerDiv.attr('self', true);
            playerDiv.toggleClass('selfPlayer');
            $('.player').unbind('click');
        } else {
            playerDiv.toggleClass('emptyPlayer');
        }
    }
    if (player.self && player.dealtCards) {
        $.each(player.dealtCards, dealCard);
    }
    if (player.equippedCards) {
        $.each(player.equippedCards, function(i, card) {placeCard(player.number, card, player.self);});
    }
    if (player.self && player.current) {
        var btn = $('.corner').eq(3);
        btn.unbind('click');
        btn.click(function() {
            $.get('/arena/endturn.html');
        });
        btn.find('img').attr('src', '/image/corner/corner2a.png');
    }
}

function playerQuit(player) {
    var playerDiv = $('.player[number="' + player.number + '"]');
    playerDiv.click(function() {
        $.get('/arena/join.html', {'number':playerDiv.attr('number')});
    });
    var image = playerDiv.find('> ul > li > img');
    if (image.is(':visible')) {
        image.hide();
    }
}

function dealCard(i, card) {
    var arena = $(".arena").data('arena');
    $('<li><img class="item" src="/arena/' + card.elementSchool + '/' + card.name + '/image.png"/></li>')
            .appendTo($('.dealtCards > .cards'))
            .width(arena.cardWidth)
            .height(arena.cardHeight)
            .css('padding-right', arena.cardSeparator)
            .find('img')
            .width(arena.cardWidth)
            .height(arena.cardHeight)
            .attr('elementSchool', card.elementSchool)
            .attr('cardName', card.name);
}

function placeCard(playerNumber, card, self) {
    var arena = $(".arena").data('arena');
    var cardList = self ? $('.self') : $('#player' + playerNumber);
    if (cardList.is(':hidden')) {
        $('.other:visible').hide('drop', {direction:'down'});
        cardList.show('drop', {direction:'up'});
    }
    var cardDiv = cardList.find('li[position="' + card.position + '"]');
    $('<img src="/arena/' + card.elementSchool + '/' + card.name + '/' + 'image.png" class="cardimage"/>')
            .width(arena.cardWidth)
            .height(arena.cardHeight)
            .appendTo(cardDiv);
}

function actionsUpdate(i, action) {
    $(window).attr('lastUpdatedTime', action.time);
    if (action.actionClass == 'PlayerJoinAction') {
        playerUpdate(i, action.player);
    }
    else if (action.actionClass == 'PlayerQuitAction') {
        playerQuit(action.player);
    }
    else if (action.actionClass == 'BeginTurnAction') {
        if (action.player.self) {
            var btn = $('.corner').eq(3);
            btn.unbind('click');
            btn.click(function() {
                $.get('/arena/endturn.html');
            });
            btn.find('img').attr('src', '/image/corner/corner2a.png');
        }
    }
    else if (action.actionClass == 'EndTurnAction') {
        if (action.player.self) {
            var btn = $('.corner').eq(3);
            btn.unbind('click');
            btn.find('img').attr('src', '/image/corner/corner2.png');
        }
    }
    else if (action.actionClass == 'EquipCardAction') {
        placeCard(action.playerNumber, action.card, action.self);
    }
    else if (action.actionClass == 'DealCardAction') {
        dealCard(0, action.card);
    }
}
