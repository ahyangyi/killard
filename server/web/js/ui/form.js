$(function () {
    $('.new').live('click', function(){
        $('#' + $(this).attr('options')).clone().appendTo($(this).parent().parent()).show();
    });
    $('.delete').live('click', function(){
        if ($(this).parent().parent().parent().children().length == 2) {
            alert('Can not delete default description.');
        } else {
            $(this).parent().parent().remove();
        }
    });
});