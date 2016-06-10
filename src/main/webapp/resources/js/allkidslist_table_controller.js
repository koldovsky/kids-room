
'use strict';

function AllKidsTableController($scope, allKidsTableService) {

    $scope.children = [];
    $scope.parents = [];
    $scope.locale = [];

    $scope.newChildFormIsShown = false;
    $scope.pageSize = 10;
    $scope.newChild = {};

    $scope.predicate = 'id';
    $scope.reverse = true;

    $scope.genders = {'Boy': 'MALE', 'Girl': 'FEMALE'};
    $scope.genderKeys = ['Boy', 'Girl'];

    loadRemoteData();
    loadLocale("ua");

    function applyRemoteChildrenData( newChildren ) {
        for (var i = 0; i < newChildren.length; i++) {
            unifyName(newChildren[i].parentId);
        }
        $scope.children = unifyNames(newChildren);
    }

    function applyRemoteParentData( newParents ) {
        $scope.parents = unifyNames(newParents);
    }

    function applyLocale( locale ) {
        $scope.locale = locale;
        console.log(locale);
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
        } else if (field.length == 0) {
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

    function loadLocale( locale ) {
        allKidsTableService.getLocale( locale )
            .then(
                function( locale ) {
                    applyLocale( locale );
                }
            )
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

    function splitChildName( child ) {
        var splitted = child.fullName.split(' ');
        if (splitted.length > 1) {
            child.firstName = splitted.slice(0, -1).join(' ');
            child.lastName = splitted.slice(-1).join(' ');
        } else {
            child.firstName = child.fullName;
            child.lastName = child.parent.lastName;
        }
        delete(child.fullName);
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

    function addChild( newChild ) {
        splitChildName(newChild);
        changeParentToParentId(newChild);
        allKidsTableService.addChild(newChild);
        toggleNewChild();
    }

    function addChildToList( newChild ) {
        $scope.children.push(newChild);
    }

    function orderTable( predicate ) {
        $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
        $scope.predicate = predicate;
    }

    function selectNewChildParent( parent ) {
        $scope.newChild.parent = parent;
    }

    function changeParentToParentId( child ) {
        child.parentId = child.parent;
        delete(child.parent);
    }

    $scope.searchChildren = searchChildren;
    $scope.searchParents = searchParents;
    $scope.toggleCollapseButton = toggleCollapseButton;
    $scope.toggleNewChild = toggleNewChild;
    $scope.addChild = addChild;
    $scope.orderTable = orderTable;
    $scope.selectNewChildParent = selectNewChildParent;

}
