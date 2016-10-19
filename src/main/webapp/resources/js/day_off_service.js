'use strict';

App.factory('DayOffService', ['$http', '$q', function ($http, $q) {

    var REST_URI_ALL = '/home/adm-days-off/all';
    var REST_URI_ROOMS = '/home/adm-edit-room/all';
    var REST_URI_DAY = '/home/adm-days-off/day/';

    var factory = {
        getAllDaysOff: getAllDaysOff,
        getDayOff: getDayOff,
        createDayOff: createDayOff,
        updateDayOff: updateDayOff,
        deleteDayOff: deleteDayOff,
        getAllRooms: getAllRooms
    };

    return factory;

    function getAllDaysOff() {
        var deferred = $q.defer();
        $http.get(REST_URI_ALL)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while fetching all Days Off');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getDayOff(id) {
        var deferred = $q.defer();
        $http.get(REST_URI_DAY + id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while fetching Day Off');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function createDayOff(dayOff) {
        var deferred = $q.defer();
        $http.post(REST_URI_DAY, dayOff)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while creating Day Off');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function updateDayOff(id, dayOff) {
        var deferred = $q.defer();
        $http.put(REST_URI_DAY + id, dayOff)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while updating Day Off');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteDayOff(id) {
        var deferred = $q.defer();
        $http.delete(REST_URI_DAY + id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while deleting Day Off');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getAllRooms() {
        var deferred = $q.defer();
        return $http.get(REST_URI_ROOMS)
            .then(
                function (response) {
                    return response.data;
                },
                function (errResponse) {
                    console.error('Error while fetching rooms');
                    return $q.reject(errResponse);
                }
            );
    }
}]);
