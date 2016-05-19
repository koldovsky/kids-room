
'use strict';

angular
    .module('allKidsList', [])
    .controller('AllKidsTableController', ['$scope', 'AllKidsService', AllKidsTableController])
    .service('AllKidsService', AllKidsTableService)
    .directive('allKidsTable', AllKidsTable);

function AllKidsTableController($scope, allKidsTableService) {

    $scope.children = [];

    loadRemoteData();

    function applyRemoteData( newChildren ) {
        $scope.children = newChildren;
    }

    function loadRemoteData() {

        allKidsTableService.getChildren()
            .then(
                function( children ) {
                    applyRemoteData( children );
                }
            )
        ;
    }

    $scope.isIndexEven = function(id) {
        return id % 2 == 0;
    }

}

function AllKidsTableService($http, $q) {

    return({
        getChildren: getChildren
    });

    function getChildren() {

        var request = $http({
            method: "get",
            url: "api/child",
            params: {
                action: "get"
            }
        });

        return( request.then( handleSuccess, handleError ) );
    }

    function handleError( response ) {

        if (
            ! angular.isObject( response.data ) ||
            ! response.data.message
            ) {
            return( $q.reject( "An unknown error occurred." ) );
        }

        return( $q.reject( response.data.message ) );
    }

    function handleSuccess( response ) {
        return( response.data );
    }
}

function AllKidsTable() {

    var link = function (scope, element, attrs) {

    }

    var compile = function() {

    }

    return {
        restrict: 'E',
        templateUrl: 'resources/templates/allkidstable.html',
        link: link,
        controller: 'AllKidsTableController',
        scope: {
            children: "="
        }
    };
}
