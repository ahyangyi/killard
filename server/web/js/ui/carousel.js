(function ($) {
    $.widget("ui.carousel", {
        _init: function() {
            var $carousel = this;

            $('.list', this.element).mousewheel(function (event, delta) {
                return $carousel.gotoPage(-delta);
            });

            // Bind to the forward and back buttons
            $('a.back', this.element).click(function () {
                return $carousel.gotoPage(-1);
            });

            $('a.forward', this.element).click(function () {
                return $carousel.gotoPage(1);
            });

            // create a public interface to move to a specific page
            $(this.element).bind('goto', function (event, page) {
                $carousel.gotoPage(page);
            });
        },

        destroy : function() {
        },

        _setData: function(key, value) {
            $.widget.prototype._setData.apply(this, arguments);
        },

        gotoPage: function(page) {
            var $wrapper = $('.list', this.element),
                    $single = $wrapper.find('> ul > li :first'),
                    singleWidth = $single.outerWidth();
            if (page > 0 && page < 1) page = 1;
            if (page < 0 && page > -1) page = -1;
            var left = singleWidth * page;

            $wrapper.filter(':not(:animated)').animate({
                scrollLeft : '+=' + left
            });

            return false;
        },

        resize: function() {
            var top = $('.top', this.element);
            var center = $('.center', this.element);
            var bottom = $('.bottom', this.element);
            var centerWidth = $(this.element).width() - 2 * 37;
            var centerHeight = $(this.element).height() - 32;
            center.height(centerHeight);
            $('> ul > .content', center).width(centerWidth);
            $('> ul > li', center).height(centerHeight);
            $('.list', center).height(centerHeight).find('li').height(centerHeight);
        }
    });

    $.extend($.ui.carousel, {
        version: "0.1",
        defaults: {}
    });
})(jQuery);