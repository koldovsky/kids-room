'use strict';

var App = angular.module('myApp', ["xeditable", "checklist-model"]);

App.run(function(editableOptions) {
    editableOptions.theme = 'bs3';
});

