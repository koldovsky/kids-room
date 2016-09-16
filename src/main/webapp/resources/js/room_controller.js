'use strict';

App.controller('RoomController', ['$scope', 'RoomService', function ($scope, RoomService) {
    var self = this;

    self.rooms = [];
    $scope.rooms = getAllRooms();Room

    function getAllRooms() {
        RoomService.getAllRooms()
            .then(
                function (d) {
                    self.rooms = d;
                },
                function (errResponse) {
                    console.error('Error while fetching all Days Off');
                }
            );
    }

}]);

