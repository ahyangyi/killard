(function ($) {

    $.widget("ui.searchbar", {
        _init: function() {
            var options = this.options;
            this.clearBtn = false;
        },

        destroy : function() {
        },

        _setData: function(key, value) {
            $.widget.prototype._setData.apply(this, arguments);
        },

        resize: function() {
            var options = this.options;
            //            alert('h=' + $(this.element).parent().height());
            this.height = parseInt($(this.element).parent().height() / options.limit);
            this.wrapper.parent().css({ height : this.height * options.limit });
        },

        _onChange: function() {
            var fld = $(this.element).find('input');
            var btn = $(this.element).find('span .srch_clear');
            if (fld.value.length > 0 && !this.clearBtn)
            {
                btn.style.background = "white url('srch_r_f2.gif') no-repeat top left";
                btn.fldID = fldID; // btn remembers it's field
                btn.onclick = this.clearBtnClick;
                this.clearBtn = true;
            } else if (fld.value.length == 0 && this.clearBtn)
            {
                btn.style.background = "white url('srch_r.gif') no-repeat top left";
                btn.onclick = null;
                this.clearBtn = false;
            }
        }
    });

    $.extend($.ui.searchbar, {
        version: "0.1",
        defaults: {
            placeholder : "Search...",
            autosave : "applestyle_srch",
            results : "5"
        }
    });

})(jQuery);