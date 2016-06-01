
'use strict';

angular
    .module('allKidsList', ['ngMaterial'])
    .controller('AllKidsTableController', ['$scope', 'AllKidsService', AllKidsTableController])
    .controller('addBookingController', ['$scope', 'AllKidsService', addBookingController])
    .service('AllKidsService', AllKidsTableService)
    .directive('allKidsTable', AllKidsTable);

