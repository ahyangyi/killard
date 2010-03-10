(function($) {
    $.widget("ui.arena", {
        _init: function() {
            var options = this.options;
            var arena = this;

            this.corner = $('.corner', this.element);
            this.player = $('.player', this.element);
            this.board = $('.board', this.element);
            this.boardSeparator = $('.separator', this.element);
            this.cardlist = $('.cardlist', this.element);
            this.targetList = [];
            this.targets = [];

            $('.cardimage').live('click', function() {
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
            $('.skillimage').live('click', function() {
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
                    var cardList = $('.cardlist[number=' + number + ']');
                    if (cardList.is(':hidden')) {
                        $('.other:visible').hide('drop', {direction:'down'});
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

            this.player.find('.score').click(function() {
                $(this).parent().find('.element').slideToggle();
            });

            this.corner.eq(0).click(function() {
                $('#toppanel').slideToggle();
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
                        $('#bottompanel').slideToggle();
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
                        $('#bottompanel').slideToggle();
                    }
                }
            });

            $('#toppanel').click(function() {
                $(this).slideToggle();
            });

            $('#bottompanel').click(function() {
                $(this).slideToggle();
            });

            $('#chatinput').keyup(function(event) {
                if (event.keyCode == '13') {
                    var msg = $(this).attr('value');
                    $.post('arena/message.json', {'message': msg}, arena.updateChatMessages, 'json');
                }
            });

            $('#warning').ajaxError(function(event, request, settings, error) {
                $(this).text('Error:' + error).fadeIn("slow", function(){
                    setTimeout(function(){$('#warning').fadeOut("slow")}, 2000);
                });
            });
            
            this.player.toggleClass('emptyPlayer');
            this.player.find('> ul > li > img').hide();
            $('.other', this.element).hide();

            this.resize();
            this.load();
//            this.board.transparent({opacity:options.boardOpacity});
        },

        destroy: function() {
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

        updateProgressBar: function(value) {
            var progress = $('#loadingbar').data('progressbar');
            $('#loadingbar').progressbar('value', progress.value() + value);
            if (Math.ceil(progress.value()) >= 100) $('#loading').hide();
        },

        load: function() {
            this.updateProgressBar(5);
            var arena = this;
            $.getJSON('arena/package.json', function(data, textStatus) {
                var elementSchool, card;
                var count = 0;
                for (elementSchool in data) for (card in data[elementSchool]) count++;
                for (elementSchool in data) {
                    for (card in data[elementSchool]) {
                        $(new Image).load(function() {
                            arena.updateProgressBar(85 / count);
                        }).attr({'src': 'arena/' + elementSchool + '/' + card + '.png'});
                    }
                }
            });
            $.getJSON('arena/board.json', function(data, textStatus) {
                arena.updateProgressBar(5);
                $(window).data('since', data.lastAction);
                $.each(data.players, function(i, player){$('#arena').arena('updatePlayer', player);});
                arena.updateProgressBar(5);
                setTimeout(function(){arena.checkStatus();}, 2000);
            });
        },

        update: function() {
            var arena = this;
            $.getJSON('arena/actions.json', {'since':$(window).data('since')}, function(actions, textStatus) {
                $.each(actions, function(i, action) {
                    arena.actionUpdate(i, action);
                });
                setTimeout(function() {arena.checkStatus();}, 2000);
            });
        },

        checkStatus: function() {
            var arena = this;
            $.get('arena/status.json', function(data, textStatus) {
                if (parseInt(data) > $(window).data('since')) arena.update();
                else setTimeout(function(){arena.checkStatus();}, 2000);
            });
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
            this.playerLengthUnit = playerShortEdge;

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
            this.corner.find('img').width(playerShortEdge).height(playerShortEdge);
            this.corner.find('.label').css({
                'font-size': parseInt(playerShortEdge / 2) + 'px', 'line-height': playerShortEdge + 'px'
            });
            this.corner.width(playerShortEdge).height(playerShortEdge);

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
            
            this.updateElements();

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
            }).width(boardWidth - this.getHorizontalGap(this.board))
                    .height(boardHeight - this.getVerticalGap(this.board));
//            this.board.transparent({update: true, opacity:options.boardOpacity});

            /* Calculate card size as integers. */
            this.cardWidth = parseInt(9 * boardLengthUnit);
            this.cardHeight = parseInt(14 * boardLengthUnit);
            this.cardSeparator = parseInt((this.board.width() - options.cardAmount * this.cardWidth) / (options.cardAmount - 1));

            var boardSeparatorMargin = boardHeight - 2 * boardMargin - 2 * boardPadding - 2 * this.cardHeight -
                                       this.getVerticalBorder(this.board);
            this.boardSeparator.css({
                'margin-top': parseInt(boardSeparatorMargin / 2),
                'margin-bottom': boardSeparatorMargin - parseInt(boardSeparatorMargin / 2)
            });

            /* Render cards */
            this.otherCardsLeft = boardPadding + parseInt(this.board.css('border-top-width'));
            this.otherCardsTop = boardPadding + parseInt(this.board.css('border-left-width'));
            this.myCardsLeft = this.otherCardsLeft;
            this.myCardsTop = boardPadding + this.cardHeight +
                              boardSeparatorMargin + this.getVerticalBorder(this.boardSeparator);
            $('.other').css({'left': this.otherCardsLeft, 'top': this.otherCardsTop});
            this.boardSeparator.width(this.board.width()).css({
                'left': this.otherCardsLeft,
                'top': this.otherCardsTop + this.cardHeight
            });
            $('.self').css({'left': this.myCardsLeft,'top': this.myCardsTop});

            this.cardlist.width(this.cardWidth * options.cardAmount + this.cardSeparator * (options.cardAmount - 1))
                    .height(this.cardHeight);
            this.cardlist.find('.card:lt(4)').css('margin-right', this.cardSeparator);

            $('.card').width(this.cardWidth).height(this.cardHeight)
                    .find('.cardimage').width(this.cardWidth).height(this.cardHeight);

            var labelSize = parseInt(this.cardWidth / 3);
            $('.card .label').width(labelSize).height(labelSize)
                    .css({'font-size': parseInt(this.cardWidth / 8), 'line-height': labelSize + 'px'});

            $('.skillimage').width(parseInt(this.cardWidth / 3))
                    .height(parseInt(this.cardWidth / 6)).css({
                'left': parseInt(this.cardWidth / 3),
                'top': parseInt(this.cardHeight - this.cardWidth / 2)
            });

            var cardPadding = parseInt(this.cardSeparator / 2);
            var dealtCards = $('#dealtCards > li');
            dealtCards.width(this.cardWidth).height(this.cardHeight).css({
                'padding-left': cardPadding,
                'padding-right': cardPadding,
                'padding-top': cardPadding,
                'padding-bottom': cardPadding
            });
            $('#dealtCards').width(dealtCards.length * (this.cardWidth + this.cardSeparator));
            $('#bottompanel > .center').height(this.cardHeight + this.cardSeparator);
            $('.carousel').carousel('resize');
        },
        
        updateElements: function() {

            var lengthUnit = this.playerLengthUnit;

            /* North */
            this.player.slice(0, 3).find('.element').each(function(i, e) {
                $(e).css({'right': (i - 2) * lengthUnit, 'bottom': - lengthUnit});
            });

            /* West */
            this.player.slice(3, 5).each(function() {
                $(this).find('.element').each(function(i, e) {
                    $(e).css({'right': - lengthUnit, 'bottom': (i - 2.5) * lengthUnit});
                });
            });

            /* East */
            this.player.slice(5, 7).each(function() {
                $(this).find('.element').each(function(i, e) {
                    $(e).css({'right': lengthUnit, 'bottom': (i - 2.5) * lengthUnit});
                });
            });

            /* South */
            this.player.slice(7).find('.element').each(function(i, e) {
                $(e).css({'right': (2.5 - i) * lengthUnit, 'bottom': lengthUnit});
            });

            this.player.find('img').width(lengthUnit).height(lengthUnit);
            this.player.find('div').width(lengthUnit).height(lengthUnit)
                    .css({'font-size': parseInt(lengthUnit / 2), 'line-height': lengthUnit + 'px'});
        },

        updateCards: function() {
            var labelSize = parseInt(this.cardWidth / 3);
            $('.card .label').width(labelSize).height(labelSize)
                    .css({'font-size': parseInt(this.cardWidth / 8), 'line-height': labelSize + 'px'});
        },

        updatePlayer: function(player) {
            var arena = this;
            var playerDiv = $('.player[number="' + player.number + '"]');
            playerDiv.unbind('click');
            var image = playerDiv.find('> ul > li img');
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
            var scoreArea = playerDiv.find('> ul > li:last');
            scoreArea.find('.score').text(player.health);
            if (player.elementRecords) {
                $.each(player.elementRecords, function(i, elementRecord) {
                    var element = scoreArea.find('> .element[elementSchool=' + elementRecord.elementSchool + ']');
                    if (element.length > 0) {
                        element.find('.elementAmount').text(elementRecord.amount);
                    } else {
                        element = $('<div class="element"></div>').attr({elementSchool: elementRecord.elementSchool})
                                .css({'z-index': 10})
                                .appendTo(scoreArea);
                        $(new Image).attr({src:'image/msg/msg.png'}).appendTo(element);
                        $('<div class="elementAmount">' + elementRecord.amount + '</div>').appendTo(element);
                    }
                    if (player.self) {
                        var searchbar = $('#bottompanel > .bottom > .searchbar > ul');
                        var barElement = searchbar.find('.element[elementSchool=' + elementRecord.elementSchool + ']');
                        if (barElement.length > 0) {
                            barElement.find('span:last').text(elementRecord.amount);
                        } else {
                            barElement =
                            $('<li class="tag element"></li>').attr({elementSchool: elementRecord.elementSchool})
                                    .appendTo(searchbar);
                            $('<span>' + elementRecord.elementSchool + ':</span>').appendTo(barElement);
                            $('<span>' + elementRecord.amount + '</span>').appendTo(barElement);
                        }
                        if (elementRecord.dealtCards) {
                            $.each(elementRecord.dealtCards, function(i, card) {arena.dealCard(i, card);});
                        }
                    }
                });
                this.updateElements();
            }
            if (player.equippedCards) {
                $.each(player.equippedCards, function(i, card) {
                    arena.equipCard(player.number, card, player.self);
                });
            }
            if (!player.self) {
                $('.cardlist[number=' + player.number + ']').show();
            }
            if (player.self && player.current) this.beginTurn();
        },

        quitPlayer: function(player) {
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
        },

        dealCard: function(i, card) {
            var cardPadding = parseInt(this.cardSeparator / 2);
            var dealtCards = $('#dealtCards');
            var cardLi = $('<li></li>').width(this.cardWidth).height(this.cardHeight).css({
                'padding-left': cardPadding,
                'padding-right': cardPadding,
                'padding-top': cardPadding,
                'padding-bottom': cardPadding
            }).appendTo(dealtCards);

            dealtCards.width(dealtCards.find('li').length * (this.cardWidth + this.cardSeparator));
            
            var cardDiv = $('<div></div>').addClass('item').addClass('card')
                    .width(this.cardWidth).height(this.cardHeight)
                    .attr({'elementSchool': card.elementSchool, 'cardName': card.name})
                    .appendTo(cardLi)
                    .draggable({ opacity: 0.7, helper: 'clone', zIndex: 500 });

            $(new Image).attr({'src': 'arena/' + card.elementSchool + '/' + card.name + '.png'})
                    .addClass('cardimage')
                    .width(this.cardWidth).height(this.cardHeight)
                    .appendTo(cardDiv);

            var fontSize = this.cardWidth / 8;
            var labelSize = parseInt(this.cardWidth / 3);
            if (card.level > 0)
                $('<div>' + card.level
                        + '</div>').addClass('label').addClass('level').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            if (card.maxHealth > 0)
                $('<div>' + card.maxHealth
                        + '</div>').addClass('label').addClass('health').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            if (card.maxHealth > 0)
                $('<div>' + 1 + '</div>').addClass('label').addClass('range').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            if (card.attack > 0)
                $('<div>' + card.attack
                        + '</div>').addClass('label').addClass('attack').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            cardDiv.find('.label').width(labelSize).height(labelSize)
                    .css({'font-size': fontSize, 'line-height': labelSize + 'px'});
        },

        renderCard: function(playerNumber, card, self) {
            var arena = this;
            var cardList = self ? $('.self') : $('.cardlist[number=' + playerNumber + ']');
            var cardDiv = cardList.find('li[position="' + card.position + '"]');
            if (cardDiv.children().length > 0) {
                $('.cardimage', cardDiv).fadeTo(1000, 1);
            } else {
                $(new Image).attr('src', 'arena/' + card.elementSchool + '/' + card.name + '.png').addClass('cardimage')
                        .width(this.cardWidth)
                        .height(this.cardHeight)
                        .hide()
                        .appendTo(cardDiv)
                        .fadeIn(1000);
            }
            var fontSize = this.cardWidth / 8;
            if (card.skills.length > 0 && self) {
                $.each(card.skills, function(i, skill) {
                    $(new Image).attr('src', 'image/skill.png').addClass('skillimage')
                            .css({'top': parseInt(arena.cardHeight - arena.cardWidth/ 2), 'left': parseInt(arena.cardWidth / 3)})
                            .width(parseInt(arena.cardWidth / 3))
                            .height(parseInt(arena.cardWidth / 6))
                            .data('index', i)
                            .data('targets', skill.targets)
                            .appendTo(cardDiv);
                });
            }
            var labelSize = parseInt(cardDiv.width() / 3);
            if (card.level > 0)
                $('<div>' + card.level
                        + '</div>').addClass('label').addClass('level').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            if (card.maxHealth > 0)
                $('<div>' + card.health
                        + '</div>').addClass('label').addClass('health').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            if (card.health > 0)
                $('<div>' + 1 + '</div>').addClass('label').addClass('range').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            if (card.attack > 0)
                $('<div>' + card.attack
                        + '</div>').addClass('label').addClass('attack').width(labelSize).height(labelSize)
                        .css({'font-size': fontSize, 'line-height': labelSize + 'px'}).appendTo(cardDiv);
            this.updateCards();
        },

        equipCard: function(playerNumber, card, self) {
            var arena = this;
            var cardList = self ? $('.self') : $('.cardlist[number=' + playerNumber + ']');
            if (cardList.is(':hidden')) {
                $('.other:visible').hide('drop', {direction:'down'});
                cardList.show('drop', {direction:'up'}, function() {
                    arena.renderCard(playerNumber, card, self);
                });
            } else {
                this.renderCard(playerNumber, card, self);
            }
        },

        updateChatMessages: function(messages) {
            $.each(messages, function(i, message) {
                $('<li>' + message.player + ':' + message.content + '</li>')
                        .appendTo($('#sidebar > div:first > ul'));
            });
        },

        beginTurn: function() {
            $('.corner').eq(3).unbind('click').click(this.endTurn)
                    .find('img').attr('src', 'image/corner/corner2a.png').css('cursor', 'pointer');
            $('.self .skillimage').show();
            $('#tip').text('Your Turn');
        },

        endTurn: function() {
            var arena = this;
            $.getJSON('arena/endturn.json', function(actions) {
                $.each(actions, arena.actionUpdate);
                $('.corner').eq(3).unbind('click')
                        .find('img').attr('src', 'image/corner/corner2.png').css('cursor', 'default');
                $('#tip').text('Waiting For Others');
            });
        },

        endCall: function() {
            var arena = this;
            $.getJSON('arena/endcall.json', function(actions) {
                $.each(actions, arena.actionUpdate);
                $('.self .skillimage').hide();
            });
        },

        actionUpdate: function(i, action) {
            if (action.id <= $(window).data('since')) return;
            $(window).data('since', action.id);
            try {
                eval('this.' + action.action + '(action)');
            } catch (e) {
                alert(e);
            }
        },

        PlayerJoinAction: function(action) {
            if (action.self) action.target.self = action.self;
            this.updatePlayer(action.target);
        },

        PlayerQuitAction: function(action) {
            if (action.self) action.target.self = action.self;
            this.quitPlayer(action.target);
        },

        BeginTurnAction: function(action) {
            if (action.self) this.beginTurn();
        },

        EndTurnAction: function(action) {
            if (action.self) $('.self .skillimage').hide();
        },

        ChangePlayerHealthAction: function(action) {
            var playerDiv = $('.player[number="' + action.target.number + '"]');
            var score = playerDiv.find('> ul > li:last').find('.score');
            score.text(parseInt(score.text()) + action.value);
        },

        ChangePlayerElementAction: function(action) {
            var playerDiv = $('.player[number="' + action.target.number + '"]');
            var scoreArea = playerDiv.find('> ul > li:last');
            var element = scoreArea.find('> .element[elementSchool=' + action.elementSchool + ']');
            if (element.length > 0) {
                var elementAmount = element.find('.elementAmount');
                elementAmount.text(parseInt(elementAmount.text()) + action.value);
            } else {
                element = $('<div class="element"></div>').attr({elementSchool: action.elementSchool})
                        .appendTo(scoreArea);
                $(new Image).attr({src:'image/msg/msg.png'}).appendTo(element);
                $('<div class="elementAmount">' + action.value + '</div>').appendTo(element);
            }
            if (action.self) {
                var searchbar = $('#bottompanel > .bottom > .searchbar > ul');
                var barElement = searchbar.find('.element[elementSchool=' + action.elementSchool + ']');
                if (barElement.length > 0) {
                    var elementLabel = barElement.find('span:last');
                    elementLabel.text(parseInt(elementLabel.text()) + action.value);
                } else {
                    barElement = $('<li class="tag element"></li>').attr({elementSchool: action.elementSchool})
                            .appendTo(searchbar);
                    $('<span>' + action.elementSchool + ':</span>').appendTo(barElement);
                    $('<span>' + action.value + '</span>').appendTo(barElement);
                }
            }
        },

        CastCardAction: function(action) {
        },

        DealCardAction: function(action) {
            if (action.self) this.dealCard(0, action.source);
        },

        EquipCardAction: function(action) {
            this.equipCard(action.source.number, action.target, action.self);
        },

        DropCardAction: function(action) {
            var cardList = action.self ? $('.self') : $('.cardlist[number=' + action.source.number + ']');

            var animateDropCardHealth = function() {
                var cardDiv = cardList.find('li[position="' + action.target.position + '"]');
                cardDiv.effect('explode', {pieces: 9}, 800, function() {
                    $(this).find('.skillimage').removeData('index');
                    $(this).find('.skillimage').removeData('targets');
                    $(this).empty();
                    $(this).show();
                });
            };

            if (cardList.is(':hidden')) {
                $('.other:visible').hide('drop', {direction:'down'});
                cardList.show('drop', {direction:'up'}, animateDropCardHealth);
            } else {
                animateDropCardHealth();
            }
        },

        ChangeCardHealthAction: function(action) {
            var cardList = action.self ? $('.self') : $('.cardlist[number=' + action.target.playerNumber + ']');

            var animateChangeCardHealth = function() {
                var cardDiv = cardList.find('li[position="' + action.target.position + '"]');
                cardDiv.effect('shake', {direction:'left'});
                var healthSpan = cardDiv.find('span').eq(2);
                healthSpan.text((parseInt(healthSpan.text()) - action.healthChange));
            };

            if (cardList.is(':hidden')) {
                $('.other:visible').hide('drop', {direction:'down'});
                cardList.show('drop', {direction:'up'}, animateChangeCardHealth);
            } else {
                animateChangeCardHealth();
            }
        }
    });

    $.extend($.ui.arena, {
        version: "0.1",
        defaults: {
            boardOpacity: 0.6,
            cardAmount : 5,
            arenaPaddingRatio : 0.75,
            boardMarginRatio : 1.5,
            boardPaddingRatio : 2,
            boardSeparatorRatio : 2,
            cardSeparatorRatio : 2
        }
    });
})(jQuery);
