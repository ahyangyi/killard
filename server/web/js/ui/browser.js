$(function () {
    function resize() {
        var centerPadding = parseInt(Math.min($(window).width() / 80, $(window).height() / 45));
        var centerHeight = $(window).height() - $('.topbar').outerHeight() - $('.bottombar').outerHeight();
        $('.container').css({
            'padding-top' : centerPadding,
            'padding-bottom' : centerPadding,
            'padding-left' : 2 * centerPadding,
            'padding-right' : 2 * centerPadding
        }).height(centerHeight - 2 * centerPadding);
    }

    resize();

    $('.container').layout({
        resizable: false,
        closable: false,
        spacing_open: 0,
        west__size: 250,
        center__size: "40%",
        east__size: "40%",
        west__onresize: function () {
        },
        center__onresize: function () {
        },
        east__onresize: function () {
        }
    });
    $.event.add(window, 'load', resize);
    $.event.add(window, 'resize', resize);

    $('input[type="text"],textarea').addClass("idleField");
    $('input[type="text"],textarea').focus(function() {
        $(this).removeClass("idleField").addClass("focusField");
        if (this.value == this.defaultValue) {
            this.value = '';
        }
        if (this.value != this.defaultValue) {
            this.select();
        }
    });
    $('input[type="text"],textarea').blur(function() {
        $(this).removeClass("focusField").addClass("idleField");
        if ($.trim(this.value) == '') {
            this.value = (this.defaultValue ? this.defaultValue : '');
        }
    });
});