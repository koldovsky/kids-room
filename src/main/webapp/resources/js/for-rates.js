
var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', function($scope) {

   $scope.choices = [];

   $scope.addNewChoice = function() {
       var newItemNo = $scope.choices.length+1;
       $scope.choices.push({'id':'choice'+newItemNo});
   };

   $scope.removeChoice = function() {
       var lastItem = $scope.choices.length-1;
       $scope.choices.splice(lastItem);
   };

   $scope.submit = function(){
       var size = $scope.choices.length;
       console.log("Submit{}");

       $.ajax({
           type: 'post',
           contentType: 'application/json',
           url: 'get-json',
           dataType: "text",
           processData: false,
           data: JSON.stringify($scope.choices)
       })
   };

});