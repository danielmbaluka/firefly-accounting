var caApp = angular.module('ca', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnSLEntityBrowserApp',
    'utilService'
]);

caApp.controller('addEditCaCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    function($scope, $stateParams, $http, errorToElementBinder, csrf) {
        $scope.entityTypes1 = [1];
        $scope.entity_selection_handler = function(entity){
            $scope.selectedEntity = entity;
        }
        $scope.title = 'Create Cash Advance';
        $scope.save = 'Save';
        $scope.showForm = true;
    }]);