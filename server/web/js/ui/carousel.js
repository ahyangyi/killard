(function ($) {
    $.widget("ui.carousel", {
        _init: function() {
            var carousel = this;

            $('.list', this.element).mousewheel(function (event, delta) {
                return carousel.gotoPage(-delta);
            });

            // Bind to the forward and back buttons
            $('a.back', this.element).click(function () {
                var items = $('.center .content ul li', carousel.element);
                var page = carousel.centerWidth() / items.width() / 2;
                return carousel.gotoPage(-page);
            });

            $('a.forward', this.element).click(function () {
                var items = $('.center .content ul li', carousel.element);
                var page = carousel.centerWidth() / items.width() / 2;
                return carousel.gotoPage(page);
            });

            // create a public interface to move to a specific page
            $(this.element).bind('goto', function (event, page) {
                carousel.gotoPage(page);
            });

            this.resize();
        },

        destroy : function() {
        },

        _setData: function(key, value) {
            $.widget.prototype._setData.apply(this, arguments);
        },

        gotoPage: function(page) {
            var $wrapper = $('.list', this.element);
            var $single = $wrapper.find('> ul > li :first');
            var singleWidth = $single.outerWidth();

            if (page > 0 && page < 1) page = 1;
            if (page < 0 && page > -1) page = -1;
            var left = singleWidth * page;

            $wrapper.filter(':not(:animated)').animate({
                scrollLeft : '+=' + left
            });

            return false;
        },

        resize: function() {
            var centerHeight = $('.center', this.element).height();
            $('.center .content .list', this.element).width(this.centerWidth());
//            $('.center', this.element).height($(this.element).height() - $('.bottom', this.element).height());

            if (centerHeight > 0) {
                $('.center ul li', this.element).height(centerHeight);
                $('.center ul .arrow a', this.element).css({
                    'margin-top': parseInt((centerHeight - 37) / 2)
                });
            }
        },

        centerWidth: function() {
            return $(this.element).width() - 2 * 50;
        }
    });

    $.extend($.ui.carousel, {
        version: "0.1",
        defaults: {}
    });
})(jQuery);