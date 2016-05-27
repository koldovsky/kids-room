
'use strict';

function addBookingController($scope, allKidsTableService) {

    $scope.children;
    $scope.newChildFormIsShown = false;
    $scope.pageSize = 10;


    function applyRemoteChildrenData( newChildren ) {
        $scope.children = unifyNames(newChildren);
    }

    function searchChildren( field ) {
        if (field.length >= 3) {
            allKidsTableService.searchChildren( field )

                .then(
                    function( children ) {
                        unifyNames(children);
                        applyRemoteChildrenData( children );

                    }
                );
        } else {
            $scope.children = [];
        }
    }

    function unifyName( person ) {
        person.fullName = person.firstName + ' ' + person.lastName;
    }

    function unifyNames( list ) {
        for (var i = 0; i < list.length; i++) {
            unifyName(list[i]);
        }
        return list;
    }

    $scope.searchChildren = searchChildren;

}
