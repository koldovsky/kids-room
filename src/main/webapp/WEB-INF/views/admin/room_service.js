'use strict';

App.factory('RoomService', ['$http', '$q', function ($http, $q) {

    var REST_URI_ROOMS = 'http://localhost:8080/home/adm-edit-room/all';

    return {

        getAllRooms: function (REST_URI_ROOMS) {
            return $http.get()
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        console.error('Error while fetching users');
                        return $q.reject(errResponse);
                    }
                );
        }
    };

}]);
