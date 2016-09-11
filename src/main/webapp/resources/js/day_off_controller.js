'use strict';

App.controller('DayOffController', ['$scope', 'DayOffService', function($scope, DayOffService) {
    var self = this;
    self.dayOff={id:null,name:'',startDate:'',endDate:'',rooms:''};
    self.daysOff=[];

    getAllDaysOff();

    function getAllDaysOff(){
        DayOffService.getAllDaysOff()
            .then(
                function(d) {
                    self.daysOff = d;
                },
                function(errResponse){
                    console.error('Error while fetching Currencies');
                }
            );
    }

}]);

