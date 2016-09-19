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

    $scope.createDay = function (day) {
        $scope.inserted = {
            name: 'Enter the name',
            startDate: new Date().toISOString(),
            endDate: new Date().toISOString(),
            rooms: null
        };
        self.daysOff.unshift($scope.inserted);
    };

    $scope.updateDay = function (day) {
        if (typeof day.id == 'undefined') {
            DayOffService.createDayOff(day)
                .then(
                    getAllDaysOff(),
                    function (errResponse) {
                        console.error('Error while creating Day Off');
                    }
                );
        } else {
            DayOffService.updateDayOff(day.id, day)
                .then(
                    getAllDaysOff,
                    function (errResponse) {
                        console.error('Error while updating Day Off');
                    }
                );
        }
    };

    $scope.deleteDay = function (id, index) {
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

    $scope.checkForData = function (day) {
        if (day.name == 'Enter the name') {
            self.daysOff.splice(0, 1);
        }
    };

    $scope.checkName = function (data) {
        if (!data) {
            return "Enter a name";
        }
    };
}]);

