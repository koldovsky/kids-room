App.controller('DayOffController', ['$scope', 'DayOffService', '$filter', function ($scope, DayOffService, $filter) {
    $scope.dayOff = {id: null, name: '', startDate: ''.to, endDate: '', rooms: ''};
    $scope.daysOff = [];
    $scope.rooms = [];

    $scope.getAllDaysOff = function () {
        DayOffService.getAllDaysOff()
            .success(function (result) {
                $scope.daysOff = result;
            })
            .error(function (err) {
                console.error('Error while fetching all Days Off' + err);
            });
    };

    $scope.getAllDaysOff();

    DayOffService.getAllRooms()
        .success(function (result) {
            $scope.rooms = result;
        })
        .error(function (err) {
            console.error('Error while fetching all rooms: ' + err);
        });

    $scope.getDayOff = function (id) {
        DayOffService.getDayOff(id)
            .success(function (result) {
                $scope.daysOff = result;
            })
            .error(function (err) {
                console.error('Error while fetching Day Off' + err);
            });
    };

    $scope.createDay = function (day) {
        $scope.inserted = {
            name: '',
            startDate: new Date(),
            endDate: new Date(),
            rooms: []
        };
        $scope.daysOff.unshift($scope.inserted);
    };

    $scope.saveDay = function (day) {

        day.startDate = $filter('date')(day.startDate, "yyyy-MM-dd");
        day.endDate = $filter('date')(day.endDate, "yyyy-MM-dd");

        if (day.id === undefined) {
            DayOffService.createDayOff(day)
                .success(res => $scope.getAllDaysOff())
                .error(err => console.error('Error while creating Day Off' + err));
        } else {
            DayOffService.updateDayOff(day)
                .success(res => $scope.getAllDaysOff())
                .error(err =>console.error('Error while updating Day Off' + err));
        }
    };

    $scope.deleteDay = function (id, index) {
        $scope.daysOff.splice(index, 1);

        DayOffService.deleteDayOff(id)
            .then(
                $scope.getAllDaysOff,
                function (errResponse) {
                    console.error('Error while deleting Day Off');
                }
            );
    };

    $scope.edit = function (id) {
        $scope.dayOff.id = id;

        $scope.dayOff.startDate = $filter('date')($scope.dayOff.startDate, "yyyy-MM-dd");
        $scope.dayOff.endDate = $filter('date')($scope.dayOff.endDate, "yyyy-MM-dd");
    };

    $scope.dateParser = function (date) {
        date = $filter('date')(date, "yyyy-MM-dd");
    };

    $scope.showAllRooms = function () {
        var selected = [];
        angular.forEach($scope.rooms, function (s) {
            if ($scope.dayOff.rooms.indexOf(s.value) >= 0) {
                selected.push(s.text);
            }
        });
        return selected.length ? selected.join(', ') : 'Not set';
    };

    $scope.checkForData = function (day) {
        if (day.name == '') {
            $scope.daysOff.splice(0, 1);
        }
    };

    $scope.checkName = function (data) {
        if (!data) {
            return "Enter a name";
        }
    };

    $scope.isRoomChecked = function (dayOffRooms, room) {
        if (dayOffRooms != null) {
            return dayOffRooms
                .map(function (room) {
                    return room.id
                })
                .includes(room.id);
        }
    };

    $scope.assignRoom = function ($event, day, selectedRoom) {
        if ($event.target.checked) {
            day.rooms.push(selectedRoom);
        } else {
            day.rooms = day.rooms.filter(room => room.id !== selectedRoom.id);
        }
        if (day.id !== undefined) {
            $scope.saveDay(day);
        }
    };

    $scope.dateParser = function (day) {
        day.startDate = $filter('date')(day.startDate, "yyyy-MM-dd");
        day.endDate = $filter('date')(day.endDate, "yyyy-MM-dd");
    };

    $scope.startDateOpened = {};

    $scope.openStartDate = function($event, elementOpened) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.startDateOpened[elementOpened] = !$scope.startDateOpened[elementOpened];
    };

    $scope.endDateOpened = {};

    $scope.openEndDate = function($event, elementOpened) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.endDateOpened[elementOpened] = !$scope.endDateOpened[elementOpened];
    };

}]);
