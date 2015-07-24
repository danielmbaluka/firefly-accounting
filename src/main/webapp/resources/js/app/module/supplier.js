var supplierApp = angular.module('supplier', [
    'jQueryFnWrapperService',
    'supplierFactory',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService'
]);


supplierApp.controller('supplierListCtrl', ['$scope', '$http', 'supplierFactory',
    function($scope,  $http, supplierFactory) {

        supplierFactory.getSuppliers()
            .success(function (data) {
                $scope.suppliers = data;
            })
            .error(function (error) {
                toastr.error('Failed to load suppliers!');
            });
    }]);

supplierApp.controller('addEditSupplierCtrl', ['$scope', '$state', '$stateParams', '$http', 'supplierFactory',
    'errorToElementBinder', 'csrf', 'routeUtil',
    function($scope, $state, $stateParams, $http, supplierFactory, errorToElementBinder, csrf, routeUtil) {

        $scope.main = function() {
            routeUtil.gotoMain($state);
        }

        $scope.title = 'Add supplier';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.supplier = {};

        var resourceURI = '/supplier/create';
        if(!($stateParams.supplierId === undefined)) {  // update mode
            $scope.title = 'Update supplier';
            $scope.showForm = false;

            $scope.supplierId = $stateParams.supplierId;

            supplierFactory.getSupplier($scope.supplierId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/suppliers';
                } else {
                    $scope.supplier = data;
                    $scope.showForm = true;
                }
            })
                .error(function (error) {
                    toastr.warning('Supplier not found!');
                    window.location.hash = '#/suppliers';
                });

            resourceURI = '/supplier/update';
        }


        $scope.processForm = function() {

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            var res = $http.post(resourceURI, $scope.supplier);
            res.success(function(data) {
                if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    $scope.submitting = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/suppliers/' + data.modelId + '/detail';
                    toastr.success('Supplier successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                $scope.save ='Save';
                $scope.submitting = false;
            });
        }

        $scope.showError = function(val) {
            console.log(val);
        }
    }]);

supplierApp.controller('supplierDetailsCtrl', ['$scope', '$state', '$stateParams', '$http', 'supplierFactory', 'routeUtil',
function($scope, $state, $stateParams, $http, supplierFactory, routeUtil) {

    $scope.showDetails = false;

    $scope.main = function() {
        routeUtil.gotoMain($state);
    }

    if(!($stateParams.supplierId === undefined)) {
        $scope.title = 'Supplier details';

        $scope.supplierId = $stateParams.supplierId;

        supplierFactory.getSupplier( $scope.supplierId)
            .success(function (data) {


                if (data === '' || data.id <= 0) {    // not found
                    toastr.warning('Supplier not found!');
                    window.location.hash = '#/suppliers';
                } else {
                    $scope.supplier = data;
                    $scope.showDetails = true;
                }
            });
    } else {
        toastr.warning('Supplier not found!');
        window.location.hash = '#/suppliers';
    }
}]);