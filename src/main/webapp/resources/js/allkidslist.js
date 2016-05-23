
'use strict';

angular
    .module('allKidsList', [])
    .controller('AllKidsTableController', ['$scope', 'AllKidsService', AllKidsTableController])
    .service('AllKidsService', AllKidsTableService)
    .directive('allKidsTable', AllKidsTable);

function AllKidsTableController($scope, allKidsTableService) {

    $scope.children = [];
    $scope.newChildFormIsShown = false;
    $scope.pageSize = 10;

    loadRemoteData();

    function applyRemoteData( newChildren ) {
        $scope.children = unifyName(newChildren);
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

    function searchChildren( field ) {
        if (field.length >= 3) {
            allKidsTableService.searchChildren( field )
                .then(
                    function( children ) {
                        applyRemoteData( unifyName( children ) );
                    }
                );
        } else {
            loadRemoteData();
        }
    }

    function unifyName( list ) {
        for (var i = 0; i < list.length; i++) {
            list[i].fullName = list[i].firstName + ' ' + list[i].lastName;
        }
        return list;
    }

    function toggleCollapseButton(buttonId) {
        buttonId = '#' + buttonId;
        $(buttonId).find('span')
            .toggleClass('glyphicon-triangle-bottom')
            .toggleClass('glyphicon-triangle-top');
    }

    function go( path ) {
        $location.path( path );
    };

    $scope.searchChildren = searchChildren;
    $scope.toggleCollapseButton = toggleCollapseButton;
    $scope.go = go;

}

function AllKidsTableService($http, $q) {

    return({
        getChildren: getChildren,
        searchChildren: searchChildren
    });

    function getChildren() {

        var request = $http({
            method: "get",
            url: "api/child",
            params: {
                action: "get"
            }
        });

        return ( request.then( handleSuccess, handleError ) );
    }

    function searchChildren(field) {

        var request = $http({
            method: "get",
            url: "api/child/search",
            params: {
                action: "get",
                field: field
            }
        });

        return ( request.then( handleSuccess, handleError ) );
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
        }
    };
}
