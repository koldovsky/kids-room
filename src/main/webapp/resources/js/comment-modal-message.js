/**
 * Created by shuk on 16.11.16.
 */
function callCommentDialog()  {
    $('[id^=comment]').click(function() {
        var comment = $(this).attr('title');
            $('#kidCommentMessage').find('h4').html(comment);
            $('#kidCommentMessage').modal('show');
    });
}