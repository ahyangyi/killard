(function($) {
    $.fn.transparent = function(options) {
        var opacity = options.opacity == null ? 0.8 : options.opacity;
        var defaults = {
            'background': '#000000',
            'opacity': opacity,
            '-moz-opacity': opacity,
            'filter': 'alpha(opacity=' + opacity * 100 + ')',
            'position': 'absolute',
            'top':0, 'left':0,
            'z-index': '-1',
            'height': $(this).height(), 'width': $(this).width(),
            'border-radius' : $(this).css('border-radius'),
            'padding-top': $(this).css('padding-top'),
            'padding-right': $(this).css('padding-right'),
            'padding-bottom': $(this).css('padding-bottom'),
            'padding-left': $(this).css('padding-left'),
            'border-top-style': $(this).css('border-top-style'),
            'border-top-width': $(this).css('border-top-width'),
            'border-top-color': $(this).css('border-top-color'),
            'border-right-style': $(this).css('border-right-style'),
            'border-right-width': $(this).css('border-right-width'),
            'border-right-color': $(this).css('border-right-color'),
            'border-bottom-style': $(this).css('border-bottom-style'),
            'border-bottom-width': $(this).css('border-bottom-width'),
            'border-bottom-color': $(this).css('border-bottom-color'),
            'border-left-style': $(this).css('border-left-style'),
            'border-left-width': $(this).css('border-left-width'),
            'border-left-color': $(this).css('border-left-color')//,
//            'margin-top': $(this).css('margin-top'),
//            'margin-right': $(this).css('margin-right'),
//            'margin-bottom': $(this).css('margin-bottom'),
//            'margin-left': $(this).css('margin-left')
        };
        if (options.update) $('#' + $(this).attr('id') + 'TransBG').css(defaults);
        else $('<div id="' + $(this).attr('id') + 'TransBG" class="transparent"></div>').css(defaults).prependTo(this);
    };
})(jQuery);
