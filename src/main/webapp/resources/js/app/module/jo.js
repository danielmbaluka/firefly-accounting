var joApp = angular.module('jo', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnSLEntityBrowserApp',
    'utilService'
]);

joApp.controller('addEditJoCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    function($scope, $stateParams, $http, errorToElementBinder, csrf) {
        $scope.suppliers = [1,2];


        $scope.supplier_selection_handler = function(entity){
            $scope.selectedSupplier = entity;
        }
        $scope.title = 'Create Job Order';
        $scope.save = 'Save';
        $scope.showForm = true;
    }]);