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
                    console.error('Error while fetching all Days Off');
                }
            );
    }

    function getDayOff(id){
        DayOffService.getDayOff(id)
            .then(
                function(d) {
                    self.dayOff = d;
                },
                function(errResponse){
                    console.error('Error while fetching Day Off');
                }
            );
    }

    function updateName(name) {
        self.dayOff.name = name;
    }

    function updateDay(data) {
        updateName();

        angular.extend(data, {id: self.dayOff.id});
        self.dayOff = data;
        DayOffService.updateDayOff(self.dayOff, self.dayOff.id)
            .then(
                getAllDaysOff,
                function (errResponse) {
                    console.error('Error while updating Day Off');
                }
            );
    }

    self.submit = submit;
    self.edit = edit;

    function submit() {
        if (self.dayOff.id === null) {
            console.log('Saving New User', self.dayOff);
            createDayOff(self.dayOff);
        } else {
            updateDayOff(self.dayOff, self.dayOff.id);
            console.log('User updated with id ', self.dayOff.id);
        }
    }

    function edit(id) {
        console.log('id to be edited', id);
        self.dayOff.id = id;
    }

    $scope.checkName = function(data) {
        if (!data) {
            return "Enter a name";
        }
    };
}]);

