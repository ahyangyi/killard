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
            $('.center .content .list', this.element).width(this.centerWidth());
        },

        centerWidth: function() {
            return $(this.element).width() - 2 * 37;
        }
    });

    $.extend($.ui.carousel, {
        version: "0.1",
        defaults: {}
    });
})(jQuery);