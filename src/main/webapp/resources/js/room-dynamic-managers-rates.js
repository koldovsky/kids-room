
var ratesList = $("#rates-json").val();
var ratesJson = 0;
if (ratesList.length != 0){
    ratesJson = JSON.parse(ratesList);
}

var managersList = $("#managers-json").val();
var managersJson = 0;
if (managersList.length != 0){
    managersJson = JSON.parse(managersList);
}

var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', function($scope) {

   $scope.rates = [];
   $scope.managers = [];


   if (managersJson.length != 0) {
       for (var i = 0; i < managersJson.length; ++i){
           var newItemNo = $scope.managers.length+1;
           var idManagerNew = parseInt(managersJson[i].id);
           $scope.managers.push({idIns: 'manager'+(i+1), id: idManagerNew.toString()});
       }
   }

   if (ratesJson.length != 0 ) {
       for (var i = 0; i < ratesJson.length; ++i){
           var newItemNo = $scope.rates.length+1;
           $scope.rates.push({idIns: 'rate'+(i+1), hourRate: ratesJson[i].hourRate, priceRate: ratesJson[i].priceRate});
       }
   }


   $scope.addNewManager = function() {
       var newItemNo = $scope.managers.length+1;
       $scope.managers.push({'idIns':'manager'+newItemNo})
   }
   $scope.removeManager = function() {
       var lastItem = $scope.managers.length-1;
       $scope.managers.splice(lastItem);
   };

   $scope.addNewRate = function() {
       var newItemNo = $scope.rates.length+1;
       $scope.rates.push({'idIns':'rate'+newItemNo});
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