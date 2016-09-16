'use strict';

App.controller('DayOffController', ['$scope', 'DayOffService', function ($scope, DayOffService) {
    var self = this;

    self.dayOff = {id: null, name: '', startDate: '', endDate: '', rooms: ''};
    self.daysOff = [];
    $scope.daysOff = getAllDaysOff();

    getAllDaysOff();

    function getAllDaysOff() {
        DayOffService.getAllDaysOff()
            .then(
                function (d) {
                    self.daysOff = d;
                },
                function (errResponse) {
                    console.error('Error while fetching all Days Off');
                }
            );
    }

    function getDayOff(id) {
        DayOffService.getDayOff(id)
            .then(
                function (d) {
                    self.dayOff = d;
                },
                function (errResponse) {
                    console.error('Error while fetching Day Off');
                }
            );
    }

    $scope.createDay = function(day){
        DayOffService.createDayOff(day)
            .then(
                getAllDaysOff(),
                function(errResponse){
                    console.error('Error while creating Day Off');
                }
            );
    };

    $scope.updateDay = function (day) {
        DayOffService.updateDayOff(day.id, day)
            .then(
                getAllDaysOff,
                function (errResponse) {
                    console.error('Error while updating Day Off');
                }
            );
    };

    $scope.deleteDay = function (id, index) {
        console.log("id:" + id + " | index:" + index);

        self.daysOff.splice(index, 1);

        DayOffService.deleteDayOff(id)
            .then(
                getAllDaysOff,
                function (errResponse) {
                    console.error('Error while deleting Day Off');
                }
            );
    };

    $scope.edit = function (id) {
        console.log('id to be edited', id);
        self.dayOff.id = id;
    };

    $scope.checkName = function (data) {
        if (!data) {
            return "Enter a name";
        }
    };
}]);

