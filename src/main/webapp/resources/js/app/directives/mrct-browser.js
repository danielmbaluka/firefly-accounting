
(function () {
    var app;
    var app = angular.module('cmnMRCTBrowserApp', ['mrctFactory', 'utilService']);

    app.controller('mrctTestController',['$scope','$modal','mrctFactory',function($scope,$modal,mrctFactory){

        $scope.title = 'Select MRCT to be posted to MIR';

        $scope.mrctList = mrctFactory.getMRCTList1();
        $scope.selectedMRCT = {};
        $scope.selectMRCT = function(selected){
            $scope.selectedMRCT = selected;
        };

        $scope.open = function(){
            $modal.open({
                scope: $scope,
                windowClass: 'mrct-browser-modal-window',
                templateUrl: '/common/mrct-browser',
                controller: function($scope, $modalInstance) {
                    $scope.close  = function() {
                        $modalInstance.close();
                    };
                }
            });
        };

    }])

    app.directive('mrctBrowser',function(){
        return{
            restrict: 'AE',
//            templateUrl: '/common/mrct-browser',
            link: function(scope, elem, attrs){
                elem.bind('click',function(){
                    scope.open();
                });
            }
        };
    });

}).call(this);
