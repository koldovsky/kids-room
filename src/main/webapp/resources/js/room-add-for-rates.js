
var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', function($scope) {

   $scope.rates = [];
   $scope.managers = [];


   $scope.addNewManager = function() {
       var newItemNo = $scope.managers.length+1;
       $scope.managers.push({'id':'manager'+newItemNo})
   }
   $scope.removeManager = function() {
       var lastItem = $scope.managers.length-1;
       $scope.managers.splice(lastItem);
   };

   $scope.addNewRate = function() {
       var newItemNo = $scope.rates.length+1;
       $scope.rates.push({'id':'rate'+newItemNo});
   };

   $scope.removeRate = function() {
       var lastItem = $scope.rates.length-1;
       $scope.rates.splice(lastItem);
   };

   $scope.submit = function(){
       $("#rates-json").val(JSON.stringify($scope.rates));
       $("#managers-json").val(JSON.stringify($scope.managers));
   };
});