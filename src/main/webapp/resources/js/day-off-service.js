'use strict';

App.factory('DayOffService', ['$http', '$q', function ($http, $q) {

    var REST_URI_ALL = '/home/adm-days-off/all';
    var REST_URI_ROOMS = '/home/adm-edit-room/all';
    var REST_URI_DAY = '/home/adm-days-off/day/';

    return {
        getAllDaysOff: getAllDaysOff,
        getDayOff: getDayOff,
        createDayOff: createDayOff,
        updateDayOff: updateDayOff,
        deleteDayOff: deleteDayOff,
        getAllRooms: getAllRooms
    };

    function getAllDaysOff() {
        return $http.get(REST_URI_ALL);
    }

    function getDayOff(id) {
        return $http.get(REST_URI_DAY + id);
    }

    function createDayOff(dayOff) {
        return $http.post(REST_URI_DAY, dayOff);
    }

    function updateDayOff(id, dayOff) {
        return $http.put(REST_URI_DAY + id, dayOff);
    }

    function deleteDayOff(id) {
       return $http.delete(REST_URI_DAY + id);
    }

    function getAllRooms() {
        return $http.get(REST_URI_ROOMS);
    }
}]);
