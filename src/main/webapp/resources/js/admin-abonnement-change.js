'use strict';

$(function () {
    $('#changeToUserAbonnement').click(function () {
        $('#abonnement-list').hide();
        $('#users-with-assign-abonnements').show();
        clearSearchFields(abonnementTable);
    });

    $('#changeToAbonnements').click(function () {
        $('#users-with-assign-abonnements').hide();
        $('#abonnement-list').show();
        clearSearchFields(purchasedAbonnementsTable);
    });

    function clearSearchFields(table) {
        $('.search-input').each(function() {
            this.value = '';
        });
        table.ajax.reload(null, false);
    }
});
