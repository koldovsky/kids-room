
'use strict';

angular
    .module('allKidsList', [])
    .controller('AllKidsTableController', ['$scope', 'AllKidsService', AllKidsTableController])
    .service('AllKidsService', AllKidsTableService)
    .directive('allKidsTable', AllKidsTable);
