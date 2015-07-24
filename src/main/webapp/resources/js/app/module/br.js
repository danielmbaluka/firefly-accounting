var brApp = angular.module('br', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnAccountBrowserWithSegmentApp',
    'utilService'
]);

brApp.controller('addEditBrCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    function($scope, $stateParams, $http, errorToElementBinder, csrf) {

        $scope.accounts_selection_handler = function(account){
            $scope.selectedAccount = account;
        }
        $scope.title = 'Create Other Deposits';
        $scope.save = 'Save';
        $scope.showForm = true;
    }]);