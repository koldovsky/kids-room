'use strict';

var App = angular.module('myApp', ["xeditable"]);

App.run(function(editableOptions) {
    editableOptions.theme = 'bs3';
});

