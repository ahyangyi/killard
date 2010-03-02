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

            this.card.find('.cardimage').live('click', function() {
                if (arena.targetList != null && arena.targetList.length > 0) {
                    var card = $(this).parent();
                    var cardList = $(this).parent().parent();
                    var self = cardList.hasClass('self');
                    if ((self && arena.targetList[arena.targets.length] == 'owncard')
                            || (!self && arena.targetList[arena.targets.length] == 'otherscard')) {
                        arena.targets.push('' + cardList.attr('number') + ':' + card.attr('position'));
                        arena.targets.push($(this).attr('src'));
                        arena.fillTargets();
                    }
                }
            });
            this.card.find('.skillimage').live('click', function() {
                if (arena.targetList.length == 0) {
                    var card = $(this).parent();
                    arena.castCardPosition = card.attr('position');
                    arena.castSkillIndex = $(this).data('index');
                    arena.targetList = $(this).data('targets');
                    arena.fillTargets();
                } else {
                    arena.castCardPosition = 0;
                    arena.castSkillIndex = 0;
                    arena.targetList = [];
                    arena.targets = [];
                    $('#tip').text('');
                }
            });

            this.player.click(function() {
                $.get('/arena/join.json', {'number':$(this).attr('number')});
            });

            this.player.find('li :first').click(function() {
                var playerDiv = $(this).parent().parent().parent();
                if (!playerDiv.attr('self')) {
                    var number = playerDiv.attr('number');
                    var cardList = $('.cardlist[number=' + number + '] img');
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
                hoverClass: 'cardholder',
                drop: function(event, ui) {
                    if ($(this).children().length == 0) {
                        var elementSchool = ui.draggable.attr('elementSchool');
                        var cardName = ui.draggable.attr('cardName');
                        var position = $(this).attr('position');
                        var cardImage = $('<img src="arena/' + elementSchool + '/' + cardName + '.png" class="cardimage"/>');
                        cardImage.width(arena.cardWidth).height(arena.cardHeight).hide()
                                .appendTo($(this)).fadeTo(1000, 0.5);
                        $.post('arena/playcard.json', {
                            'elementSchoolName':elementSchool,
                            'cardName':cardName,
                            'cardPosition':position}, function(actions) {
                            var equipped = false;
                            $.each(actions, function(i, action) {
                                if (action.action == 'EquipCardAction' && action.self
                                        && action.target.position == position) equipped = true;
                            });
                            if (!equipped) {
                                cardImage.fadeOut("slow", function() {$(this).remove()});
                            }
                            $.each(actions, actionUpdate);
                        }, 'json');
                        $('#bottompanel').hide('drop', {direction:'down'});
                    }
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
            // Fill skill targets, if all filled then fire the skill.
            while (this.targets.length < this.targetList.length) {
                if (this.targetList[this.targets.length] == 'all') {
                    this.targets.push('all');
                }
                if (this.targetList[this.targets.length] == 'self') {
                    this.targets.push('self');
                }
                if (this.targetList[this.targets.length] == 'other') {
                    $('#tip').text('Please select a player.');
                    break;
                }
                if (this.targetList[this.targets.length] == 'owncard') {
                    $('#tip').text('Please select a card of yours.');
                    break;
                }
                if (this.targetList[this.targets.length] == 'otherscard') {
                    $('#tip').text('Please select a card of others.');
                    break;
                }
            }
            if (this.targets.length == this.targetList.length) {
                $('#tip').text('');
                var skillImage = $('.self li[position=' + this.castCardPosition
                        + '] .skillimage:eq(' + this.castSkillIndex + ')');
                skillImage.fadeTo(1000, 0.5);
                $.post('arena/cast.json', {
                    cardPosition:this.castCardPosition,
                    skillIndex:this.castSkillIndex,
                    target:this.targets
                }, function(actions) {
                    var casted = false;
                    $.each(actions, function(i, action) {
                        if (action.action == 'CastCardAction' && action.self
                                && action.target.position == position) casted = true;
                    });
                    if (casted) skillImage.effect('puff', function() {skillImage.hide()});
                    else skillImage.fadeTo(1000, 1);
                    $.each(actions, actionUpdate);
                }, 'json');
                this.castCardPosition = 0;
                this.castSkillIndex = 0;
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
            w = w - $('#sidebar').width();
            h = h - $('#tip').outerHeight() - $('#warning').outerHeight();

            $('#warning').width(w);
            $('#tip').width(w).css('bottom', 0);

            this.element.eq(0).css('top', $('#warning').height());

            /* Calculate width/height ratio of board. */
            var boardGap = 2 * (options.boardMarginRatio + options.boardPaddingRatio);
            var boardWidthRatio = 9 * options.cardAmount + (options.cardAmount - 1) * options.cardSeparatorRatio + boardGap;
            var boardHeightRatio = 14 * 2 + options.boardSeparatorRatio + boardGap;
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
            this.corner.each(function() {
                $('img', this).width(playerShortEdge).height(playerShortEdge);
                $(this).width(playerShortEdge);
                $(this).height(playerShortEdge);
            });

            this.corner.eq(0).css('margin-right', cornerRightMargin);
            this.corner.eq(1).css('margin-left', cornerLeftMargin);
            this.corner.eq(2).css('margin-right', cornerRightMargin);
            this.corner.eq(3).css('margin-left', cornerLeftMargin);

            /* Render players */
            /* North */
            this.player.slice(0, 3).width(playerLongEdge).height(playerShortEdge).css({
                'margin-left' : playerHorizontalMargin,
                'margin-right' : playerHorizontalMargin,
                'margin-top' : 0,
                'margin-bottom' : 0
            });

            /* West & East */
            this.player.slice(3, 7).width(playerShortEdge).height(playerLongEdge).css({
                'margin-left' : 0,
                'margin-right' : 0,
                'margin-top' : playerVerticalMargin,
                'margin-bottom' : playerVerticalMargin
            });

            /* South */
            this.player.slice(7).width(playerLongEdge).height(playerShortEdge).css({
                'margin-left' : playerHorizontalMargin,
                'margin-right' : playerHorizontalMargin,
                'margin-top' : 0,
                'margin-bottom' : 0
            });

            /* Set player size. */
            this.player.find('img').width(playerShortEdge).height(playerShortEdge);

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

            this.board.width(boardWidth - this.getHorizontalGap(this.board))
                    .height(boardHeight - this.getVerticalGap(this.board));

            /* Calculate card size as integers. */
            var cardWidth = parseInt(9 * boardLengthUnit);
            var cardHeight = parseInt(14 * boardLengthUnit);
            var cardSeparator = parseInt((this.board.width() - options.cardAmount * this.cardWidth) / (options.cardAmount - 1));
            this.cardWidth = cardWidth;
            this.cardHeight = cardHeight;
            this.cardSeparator = cardSeparator;

            var boardSeparatorMargin = boardHeight - 2 * boardMargin - 2 * boardPadding - 2 * this.cardHeight -
                                       this.getVerticalBorder(this.board);
            this.boardSeparator.css({
                'margin-top': parseInt(boardSeparatorMargin / 2),
                'margin-bottom': boardSeparatorMargin - parseInt(boardSeparatorMargin / 2)
            });

            /* Render cards */
            this.otherCardsLeft = arenaPadding + playerShortEdge + boardMargin + boardPadding +
                                  parseInt(this.board.css('border-top-width'));
            this.otherCardsTop = arenaPadding + playerShortEdge + boardMargin + boardPadding +
                                 parseInt(this.board.css('border-left-width'));
            this.myCardsLeft = this.otherCardsLeft;
            this.myCardsTop = arenaPadding + playerShortEdge + boardMargin + boardPadding + this.cardHeight +
                              boardSeparatorMargin + this.getVerticalBorder(this.boardSeparator);
            $('.other').css({'left': this.otherCardsLeft, 'top': this.otherCardsTop});
            this.boardSeparator.width(this.board.width());
            this.boardSeparator.css({'left': this.otherCardsLeft,'top': this.otherCardsTop + cardHeight});
            $('.self').css({'left': this.myCardsLeft, 'top': this.myCardsTop});

            this.cardlist.height(this.cardHeight);
            $('.other').find('.card:lt(5)').css('margin-right', cardSeparator);
            $('.self').find('.card:lt(5)').css('margin-right', cardSeparator);

            this.card.width(this.cardWidth);
            this.card.height(this.cardHeight);
            this.card.find('.cardimage').width(this.cardWidth).height(this.cardHeight);
            $('.item').width(this.cardWidth).height(this.cardHeight);

            $('.other').each(function() {
                $(this).find('.card').each(function(i, e) {
                    $(e).css('left', i * (cardSeparator + cardWidth));
                });
            });
            $('.self').find('.card').each(function(i, e) {
                $(e).css('left', i * (cardSeparator + cardWidth));
            });
            this.card.each(function(i, c){
                var numbers = $(c).find('span');
                var cardWidth = parseInt(9 * boardLengthUnit);
                var cardHeight = parseInt(14 * boardLengthUnit);
                var fontSize = parseInt(9 * boardLengthUnit / 6);
                numbers.css('font-size', fontSize);
                numbers.eq(0).css('top', 0).css('left', 0);
                numbers.eq(1).css('top', 0).css('left', cardWidth - fontSize);
                numbers.eq(2).css('top', cardHeight - fontSize).css('left', 0);
                numbers.eq(3).css('top', cardHeight - fontSize).css('left', cardWidth - fontSize);
            });

            this.card.find('.skillimage').css({
                'width': parseInt(cardWidth / 3),
                'height': parseInt(cardWidth / 6),
                'left': parseInt(cardWidth / 3),
                'top': parseInt(cardHeight - cardWidth / 2)
            });

            $('.bottompanel').css('top', $(window).height() - this.cardHeight - 2 * boardLengthUnit);
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
            cardAmount : 5,
            arenaPaddingRatio : 0.75,
            boardMarginRatio : 1.5,
            boardPaddingRatio : 2,
            boardSeparatorRatio : 2,
            cardSeparatorRatio : 2.5
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
        $.each(player.equippedCards, function(i, card) {equipCard(player.number, card, player.self);});
    }
    if (player.self && player.current) beginTurn();
}

function playerQuit(player) {
    var playerDiv = $('.player[number="' + player.number + '"]');
    playerDiv.click(function() {
        $.get('/arena/join.json', {'number':playerDiv.attr('number')});
    });
    var image = playerDiv.find('> ul > li > img');
    if (image.is(':visible')) {
        image.hide();
    }
    playerDiv.toggleClass('emptyPlayer');

    var cardList = $('.cardlist[number=' + player.number + ']');
    cardList.find('li').empty();
}

function dealCard(i, card) {
    var arena = $(".arena").data('arena');
    $('<li><img class="item" src="arena/' + card.elementSchool + '/' + card.name + '.png"/></li>')
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

function renderCard(playerNumber, card, self) {
    var arena = $(".arena").data('arena');
    var cardList = self ? $('.self') : $('.cardlist[number=' + playerNumber + ']');
    var cardDiv = cardList.find('li[position="' + card.position + '"]');
    if (cardDiv.children().length > 0) {
        $('.cardimage', cardDiv).fadeTo(1000, 1);
    } else {
        $('<img src="arena/' + card.elementSchool + '/' + card.name + '.png" class="cardimage"/>')
                .width(arena.cardWidth)
                .height(arena.cardHeight)
                .hide()
                .appendTo(cardDiv)
                .fadeIn(1000);
    }
    var fontSize = arena.cardWidth / 6;
    if (card.skills.length > 0 && self) {
        $.each(card.skills, function(i, skill) {
            $('<img src="image/skill.png' + '" class="skillimage"/>')
                    .css('top', parseInt(arena.cardHeight - arena.cardWidth / 2))
                    .css('left', parseInt(arena.cardWidth / 3))
                    .width(parseInt(this.cardWidth / 3))
                    .height(parseInt(this.cardWidth / 6))
                    .data('index', i)
                    .data('targets', skill.targets)
                    .appendTo(cardDiv);
        });
    }
    if (card.level > 0)
        $('<span>' + card.level + '</span>')
                .css('top',0)
                .css('left',0)
                .css('font-size', fontSize)
                .appendTo(cardDiv);
    if (card.maxHealth > 0)
        $('<span>' + card.maxHealth + '</span>')
                .css('top',0)
                .css('left',arena.cardWidth - fontSize)
                .css('font-size', fontSize)
                .appendTo(cardDiv);
    if (card.health > 0)
        $('<span>' + card.health + '</span>')
                .css('top',arena.cardHeight - fontSize)
                .css('left',0)
                .css('font-size', fontSize)
                .appendTo(cardDiv);
    if (card.attack > 0)
        $('<span>' + card.attack + '</span>')
                .css('top',arena.cardHeight - fontSize)
                .css('left',arena.cardWidth - fontSize)
                .css('font-size', fontSize)
                .appendTo(cardDiv);
    //TODO fix the resizing issue of skill image
    arena.resize();
}

function equipCard(playerNumber, card, self) {
    var cardList = self ? $('.self') : $('.cardlist[number=' + playerNumber + ']');
    if (cardList.is(':hidden')) {
        $('.other:visible').hide('drop', {direction:'down'});
        cardList.show('drop', {direction:'up'}, function() {
            renderCard(playerNumber, card, self);
        });
    } else {
        renderCard(playerNumber, card, self);
    }
}

function dropCard(playerNumber, card, self) {
    var cardList = self ? $('.self') : $('.cardlist[number=' + playerNumber + ']');
    if (cardList.is(':hidden')) {
        $('.other:visible').hide('drop', {direction:'down'});
        cardList.show('drop', {direction:'up'});
    }
    var cardDiv = cardList.find('li[position="' + card.position + '"]');
    cardDiv.effect('explode', {pieces: 9}, 800, function() {
        $(this).find('.skillimage').removeData('index');
        $(this).find('.skillimage').removeData('targets');
        $(this).empty();
        $(this).show();
    });
}

function changeCardHealth(action) {
    var cardList = action.self ? $('.self') : $('.cardlist[number=' + action.target.playerNumber + ']');
    if (cardList.is(':hidden')) {
        $('.other:visible').hide('drop', {direction:'down'});
        cardList.show('drop', {direction:'up'});
    }
    var cardDiv = cardList.find('li[position="' + action.target.position + '"]');
    cardDiv.effect('shake', {direction:'left'});
    var healthSpan = cardDiv.find('span').eq(2);
    healthSpan.text((parseInt(healthSpan.text()) - action.healthChange));
}

function actionUpdate(i, action) {
    if (action.id <= $(window).data('since')) return;
    $(window).data('since', action.id);
    if (action.action == 'PlayerJoinAction') {
        if (action.self) action.target.self = action.self;
        playerUpdate(i, action.target);
    }
    else if (action.action == 'PlayerQuitAction') {
        if (action.self) action.target.self = action.self;
        playerQuit(action.target);
    }
    else if (action.action == 'BeginTurnAction') {
        if (action.self) beginTurn();
    }
    else if (action.action == 'EndTurnAction') {
        if (action.self) $('.self .skillimage').hide();
    }
    else if (action.action == 'EquipCardAction') {
        equipCard(action.source.number, action.target, action.self);
    }
    else if (action.action == 'DropCardAction') {
        dropCard(action.source.number, action.target, action.self);
    }
    else if (action.action == 'ChangeCardHealthAction') {
        changeCardHealth(action);
    }
    else if (action.action == 'DealCardAction') {
        if (action.self)
            dealCard(0, action.source);
    }
}

function beginTurn() {
    $('.corner').eq(3).unbind('click').click(endTurn)
            .find('img').attr('src', 'image/corner/corner2a.png').css('cursor', 'pointer');
    $('.self .skillimage').show();
    $('#tip').text('Your Turn');
}

function endTurn(){
    $('.corner').eq(3).unbind('click').find('img').attr('src', 'image/corner/corner2.png').css('cursor', 'default');
    $('#tip').text('Waiting For Others');
    $.getJSON('arena/endturn.json', function(actions){
        $.each(actions, actionUpdate);
    });
}

function endCall(){
    $.getJSON('arena/endcall.json', function(actions){
        $.each(actions, actionUpdate);
        $('.self .skillimage').hide();
    });
}
