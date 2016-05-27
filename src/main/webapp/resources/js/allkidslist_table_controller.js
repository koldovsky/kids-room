
'use strict';

function AllKidsTableController($scope, allKidsTableService) {

    $scope.children = [];
    $scope.newChildFormIsShown = false;
    $scope.pageSize = 10;
    $scope.newChild = {};

    loadRemoteData();

    function applyRemoteChildrenData( newChildren ) {
        for (var i = 0; i < newChildren.length; i++) {
            linkParent(newChildren[i]);
        }
        $scope.children = unifyNames(newChildren);
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

    function toggleModal() {
        ngDialog.open({ template: 'resources/templates/allkidstable_modal.html',
            className: 'ngdialog-theme-plain', controller: 'AllKidsTableController' });
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

    $scope.searchChildren = searchChildren;
    $scope.toggleCollapseButton = toggleCollapseButton;
    $scope.toggleNewChild = toggleNewChild;
    $scope.addChild = addChild;

}
