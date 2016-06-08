
    var ratesList = $("#for-json").val();
    var ratesJson = JSON.parse(ratesList);

    var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', function($scope) {

   $scope.choices = [];

   if(ratesJson.length > 0 ){
       for (var i = 0; i < ratesJson.length; ++i){
           var newItemNo = $scope.choices.length+1;
           $scope.choices.push({id: 'choice'+(i+1), hourRate: ratesJson[i].hourRate, priceRate: ratesJson[i].priceRate});
       }
   }


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
       $("#for-json").val(JSON.stringify($scope.choices));

       document.getElementById("for-json").value = JSON.stringify($scope.choices);
   };
});