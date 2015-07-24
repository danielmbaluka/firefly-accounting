/**
 * Created by TSI Admin on 3/23/2015.
 */
var pcvApp = angular.module('pcv', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService'
]);

pcvApp.controller('addEditPCVCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    function($scope, $stateParams, $http, errorToElementBinder, csrf) {

        $scope.title = 'Create petty cash voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
    }]);
