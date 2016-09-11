'use strict';

App.factory('DayOffService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/home/adm-days-off/all';

    var factory = {
        getAllDaysOff: getAllDaysOff

    };

    return factory;

    function getAllDaysOff() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Users');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);
