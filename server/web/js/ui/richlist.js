(function ($) {
    $.widget("ui.richlist", {
        _init: function() {
            var options = this.options;
            // 1. setup
            // capture a cache of all the list items
            // chomp the list down to options.limit li elements
            var $this = this;
            this.items = []; // uninitialised
            this.currentItem = 0;

            // capture the cache
            $(this.element).find('> ul > li').each(function () {
                $this.items.push('<li>' + $(this).html() + '</li>');
            });

            $(this.element).find('> ul > li').filter(':gt(' + (options.limit - 1) + ')').remove();
            this.resize();
        },

        destroy : function() {
        },

        _setData: function(key, value) {
            $.widget.prototype._setData.apply(this, arguments);
        },

        resize: function() {
            var options = this.options;
            this.height = parseInt(this.element.parent().height() / options.limit);
            $(this.element).find('> ul > li').height(this.height);
            $(this.element).find('img').width(this.height);
            $(this.element).find('img').height(this.height);
        },

        _update: function() {
            var options = this.options;
            var height = this.height;

            if ($(this.element).find('> ul > li').length < options.limit) {
                if ($(this.element).find('> ul > li').length < this.items.length) {
                    // insert a new item with opacity and height of zero
                    var $insert = $(this.items[this.currentItem]).css({
                        height : 0,
                        opacity : 0,
                        display : 'none'
                    }).prependTo($(this.element.find('> ul')));
                    $insert.find('img').width(this.height);
                    $insert.find('img').height(this.height);
                    $insert.animate({ height : height }, 1000).animate({ opacity : 1 }, 1000);
                    this.currentItem++;
                }
            } else {
                // insert a new item with opacity and height of zero
                var $insert = $(this.items[this.currentItem]).css({
                    height : 0,
                    opacity : 0,
                    display : 'none'
                }).prependTo($(this.element.find('> ul')));
                $insert.find('img').width(this.height);
                $insert.find('img').height(this.height);
                // fade the LAST item out
                $(this.element).find('> ul > li:last').animate({ opacity : 0}, 1000, function () {
                    // increase the height of the NEW first item
                    $insert.animate({ height : height }, 1000).animate({ opacity : 1 }, 1000);
                    // AND at the same time - decrease the height of the LAST item
                    // $(this).animate({ height : 0 }, 1000, function () {
                    // finally fade the first item in (and we can remove the last)
                    $(this).remove();
                    // });
                });

                this.currentItem++;
            }

            if (this.currentItem >= this.items.length) {
                this.currentItem = 0;
            }

            var list = this;
            setTimeout(function() {list._update();}, options.interval);
        },

        _clear: function() {
            this.items = [];
            this.currentItem = 0;
        }
    });

    $.extend($.ui.richlist, {
        version: "0.1",
        defaults: {
            limit : 4,
            interval : 5000
        }
    });

})(jQuery);