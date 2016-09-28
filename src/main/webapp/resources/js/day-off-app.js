'use strict';

var App = angular.module('myApp', ['xeditable', 'checklist-model', 'ui.bootstrap']);

App.run(function(editableOptions) {
    editableOptions.theme = 'bs3';
});

