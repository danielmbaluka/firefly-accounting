var unitOfMeasureApp = angular.module('unit', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService',
    'unitFactory'
]);



unitOfMeasureApp.controller('unitListCtrl', ['$scope', '$http', 'unitFactory', function($scope,  $http, unitFactory) {

    unitFactory.getUnits()
            .success(function (data) {
                $scope.units = data;
            })
            .error(function (error) {
                toastr.error('Failed to load units!');
            });
}]);

unitOfMeasureApp.controller('unitDetailsCtrl', ['$scope', '$state', '$stateParams', '$http', 'unitFactory', 'routeUtil',
    function($scope, $state, $stateParams, $http, unitFactory, routeUtil) {

        $scope.main = function() {
            routeUtil.gotoMain($state);
        }

        $scope.showDetails = false;

        if(!($stateParams.unitId === undefined)) {
            $scope.title = 'Unit details';

            $scope.unitId = $stateParams.unitId;

            unitFactory.getUnit( $scope.unitId)
                .success(function (data) {

                    if (data === '' || data.id <= 0) {    // not found
                        toastr.warning('Unit found!');
                        window.location.hash = '#/unit-measures';
                    } else {
                        $scope.unit = data;
                        $scope.showDetails = true;
                    }
                });
        } else {
            toastr.warning('Unit not found!');
            window.location.hash = '#/unit-measures';
        }

        $scope.pointToEditForm = function() {
            window.location.hash = '#/unit-measures/' + $scope.unitId + "/edit";
        }
    }]);


unitOfMeasureApp.controller('addEditUnitCtrl', ['$scope', '$state', '$stateParams', '$http', 'unitFactory',
    'errorToElementBinder', 'csrf', 'routeUtil',
    function($scope, $state, $stateParams, $http, unitFactory, errorToElementBinder, csrf, routeUtil) {

    $scope.main = function() {
        routeUtil.gotoMain($state);
    }

    $scope.title = 'Add unit';
    $scope.save = 'Save';
    $scope.showForm = true;

    $scope.unit = {};

    var resourceURI = '/unit/create';
    if(!($stateParams.unitId === undefined)) {  // update mode
        $scope.title = 'Update unit';
        $scope.showForm = false;

        $scope.unitId = $stateParams.unitId;

        unitFactory.getUnit($scope.unitId).success(function (data) {
            if (data === '' || data.id <= 0) {    // not found
                window.location.hash = '#/unit-measures';
            } else {
                $scope.unit = data;
                $scope.showForm = true;
            }
        })
            .error(function (error) {
                toastr.warning('Unit not found!');
                window.location.hash = '#/unit-measures';
            });

        resourceURI = '/unit/update';
    }


    $scope.processForm = function() {

        $scope.save ='Saving...';

        $scope.errors = {};
        $scope.submitting = true;
        csrf.setCsrfToken();

        console.log($scope.unit);

        var res = $http.post(resourceURI, $scope.unit);
        res.success(function(data) {
            if (!data.success) {
                $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                $scope.save ='Save';
                $scope.submitting = false;
                toastr.warning('Error found.');
            } else {
                window.location.hash = '#/unit-measures/' + data.modelId + '/detail';
                toastr.success('Unit successfully saved!');
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
