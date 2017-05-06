'use strict';

$(function () {
    $('#changeToUserAbonnement').click(function () {
        $('#abonnement-list').hide();
        $('#users-with-assign-abonnements').show();
    });

    $('#changeToAbonnements').click(function () {
        $('#users-with-assign-abonnements').hide();
        $('#abonnement-list').show();
    });
});
