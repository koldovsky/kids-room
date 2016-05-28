
    var result = $("#for-json").val();
    result = result.substring(1, result.length-1);

    result = result.split(',');
    var objects = [];

    for (var i = 0; i < result.length; i++) {
        var string = result[i];
        var stringToArray = string.split('|');

        stringToArray[0] = parseInt(stringToArray[0].substring(10));
        stringToArray[1] = stringToArray[1].substring(0, stringToArray[1].length - 1);
        stringToArray[1] = parseInt(stringToArray[1].substring(10));

        objects[i] = {
            hourRate: stringToArray[0],
            priceRate: stringToArray[1]
        }
    }


var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', function($scope) {

   $scope.choices = [];

   for (var i = 0; i < objects.length; ++i){
       var newItemNo = $scope.choices.length+1;
       $scope.choices.push({id: 'choice'+(i+1), hourRate: objects[i].hourRate, priceRate: objects[i].priceRate});
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