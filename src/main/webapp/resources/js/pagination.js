$(function () {
    paginate();
    addListenerForExcelButton();
});

function addListenerForExcelButton() {
    $('#export').click(function () {
        var urlParts = window.location.href.split('?');

        window.location.href ="excel?" + urlParts[1];
    });
}

function paginate() {
    $('.pager').remove();
    $('#bookings').each(function () {
        var currentPage = 0;
        var numPerPage = parseInt($('#itemsPerPage').val());
        var $table = $(this);
        $table.bind('repaginate', function () {
            $table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
        });
        $table.trigger('repaginate');
        var numRows = $table.find('tbody tr').length;
        var numPages = Math.ceil(numRows / numPerPage);
        var $pager = $('<div class="pager"></div>');
        for (var page = 0; page < numPages; page++) {
            $('<span class="page-number"></span>').text(page + 1).bind('click', {
                newPage: page
            }, function (event) {
                currentPage = event.data['newPage'];
                $table.trigger('repaginate');
                $(this).addClass('active').siblings().removeClass('active');
            }).appendTo($pager).addClass('clickable');
        }
        $pager.insertBefore($table).find('span.page-number:first').addClass('active');
    });
}
