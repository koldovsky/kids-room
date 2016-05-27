
'use strict';

function AllKidsTableController($scope, allKidsTableService) {

    $scope.children = [];
    $scope.parents = [];
    $scope.newChildFormIsShown = false;
    $scope.pageSize = 10;
    $scope.newChild = {};

    $scope.predicate = 'parent.fullName';
    $scope.reverse = true;

    loadRemoteData();

    function applyRemoteChildrenData( newChildren ) {
        for (var i = 0; i < newChildren.length; i++) {
            linkParent(newChildren[i]);
        }
        $scope.children = unifyNames(newChildren);
    }

    function applyRemoteParentData( newParents ) {
        $scope.parents = unifyNames(newParents);
    }

    function emptyParents() {
        $scope.parents = [];
    }

    function loadRemoteData() {

        allKidsTableService.getChildren()
            .then(
                function( children ) {
                    applyRemoteChildrenData( children );
                }
            )
        ;
    }

    function searchChildren( field ) {
        if (field.length >= 3) {
            allKidsTableService.searchChildren( field )
                .then(
                    function( children ) {
                        applyRemoteChildrenData( children );
                    }
                );
        } else {
            loadRemoteData();
        }
    }

    function searchParents( field ) {
        if (field.length >= 3) {
            allKidsTableService.searchParents( field )
                .then(
                    function( parents ) {
                        applyRemoteParentData( parents );
                    }
                );
        } else {
            emptyParents();
        }
    }

    function linkParent( child ) {
        allKidsTableService.getParent( child.id )
            .then(
                function( parent ) {
                    unifyName(parent)
                    child.parent = parent;
                }
            );
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

    function splitName( person ) {
        person.firstName = person.fullName.split(' ').slice(0, -1).join(' ');
        person.lastName = person.fullName.split(' ').slice(-1).join(' ');
        delete(person.fullName);
    }

    function toggleCollapseButton(buttonId) {
        buttonId = '#' + buttonId;
        $(buttonId).find('span')
            .toggleClass('glyphicon-triangle-bottom')
            .toggleClass('glyphicon-triangle-top');
    }

    function toggleNewChild() {
        $scope.newChild = {};
        $scope.newChildFormIsShown = !$scope.newChildFormIsShown;
    }

    function addChild( child ) {
        splitName(child)
        allKidsTableService.addChild(child);
        toggleNewChild();
    }

    function orderTable( predicate ) {
        $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
        $scope.predicate = predicate;
    };

    $scope.searchChildren = searchChildren;
    $scope.searchParents = searchParents;
    $scope.toggleCollapseButton = toggleCollapseButton;
    $scope.toggleNewChild = toggleNewChild;
    $scope.addChild = addChild;
    $scope.orderTable = orderTable;

}
