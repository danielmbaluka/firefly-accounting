var checkReleasing = angular.module('checkReleasing', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService',
    'cvFactory'
]);

checkReleasing.controller('mainCtrl', ['$scope', '$http', 'cvFactory', function($scope,  $http, cvFactory) {
    cvFactory.getCvForReleasing().success(function (data) {
        $scope.vouchers = data;
        $scope.doneLoading = true;
    }).error(function (error) {
        toastr.error('Failed to load check vouchers.');
        $scope.doneLoading = true;
    });
}]);

checkReleasing.controller('releaseCtrl', ['$scope', '$state', '$http', '$stateParams', 'cvFactory', 'routeUtil', 'csrf',
    function($scope, $state, $http, $stateParams, cvFactory, routeUtil, csrf) {

        $scope.title = 'Release check';
        $scope.save = 'Release';
        $scope.showForm = false;
        $scope.models = [];
        $scope.cv = {};

        if(!($stateParams.cvId === undefined)) {
            $scope.cvId = $stateParams.cvId;

            cvFactory.getCv($scope.cvId).success(function (data) {
                $scope.cv = data;

                cvFactory.getChecksForReleasing($scope.cv.transId).success(function (data) {

                    if (data != undefined && data.length > 0) {
                        $scope.models = data;
                        angular.forEach($scope.models, function (v, k) {
                            v.dateReleased = $.datepicker.formatDate('mm/dd/yy', new Date());
                        });

                        $scope.showForm = true;
                    } else {
                        routeUtil.gotoMain($state);
                    }
                }).error(function (error) {
                    toastr.error('Failed to load checks');
                    $scope.showForm = true;
                });
            });
        }

        var cleanOnErrors = function() {
            $scope.save = 'Release';
            // flags
            $scope.submitting = false;
        }

        $scope.processForm = function(model) {
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == model.payee || $.trim(model.payee).length == 0) {
                $scope.errors['err_receivedBy'] =  ['Please enter person will receive'];
            }
            if (undefined == model.dateReleased || model.dateReleased == '') {
                $scope.errors['err_dateReleased'] =  ['Please enter date'];
            }
            if (undefined == model.remarks || $.trim(model.remarks).length == 0) {
                $scope.errors['err_remarks'] =  ['Please enter remarks'];
            }
            if (!$.isEmptyObject($scope.errors)) {
                toastr.warning('Error found.');
                cleanOnErrors();
                return;
            }

            var postData = {
                "check": {'id': model.id},
                "receivedBy":  model.payee,
                "dateReleased":  $.datepicker.parseDate("mm/dd/yy",  model.dateReleased),
                "orNumber":  model.orNumber,
                "remarks":  model.remarks
            }

            var res = $http.post("/check-releasing/release", postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    gotoMain($state);
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    cleanOnErrors();
                    toastr.warning('Error found.');
                } else {
                    window.location.reload();
                    toastr.success(data.successMessage);
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                cleanOnErrors();
            });
        }
}]);