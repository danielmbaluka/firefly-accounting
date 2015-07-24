var allFactApp = angular.module('allocationFactor', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService',
    'accountService',
    'accountFactory',
    'allocationFactorFactory',
    'businessSegmentFactory'
]);

allFactApp.controller('apvMainCtrl', ['$scope', '$http', 'allocationFactorFactory',
        function($scope, $http, allocationFactorFactory) {

            $scope.doneLoading = false;

            allocationFactorFactory.getFactors()
                .success(function (data) {
                    $scope.factors = data;
                    $scope.doneLoading = true;
                })
                .error(function (error) {
                    toastr.error('Failed to allocation factors.');
                    $scope.doneLoading = true;
                });
        }]
);


allFactApp.controller('addEditFactorCtrl', ['$scope', '$stateParams', '$http', 'accountFactory', 'modalToggler',
    'businessSegmentFactory', 'csrf', 'errorToElementBinder', 'allocationFactorFactory',
    function($scope, $stateParams, $http, accountFactory, modalToggler, businessSegmentFactory, csrf, errorToElementBinder,
             allocationFactorFactory
        )
    {
        var resourceURI = '/allocation-factor/create';

        $scope.businessSegments = [];

        $scope.selectedEffDate = {};
        $scope.modalBodyTemplateUrl = "/common/account-browser";
        $scope.title = 'Create factor allocation';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.ranges = [];

        allocationFactorFactory.getDateRanges().success(function (data) { $scope.ranges = data; });

        $scope.editMode = !($stateParams.accountId === undefined) && !($stateParams.effectId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update factor allocation';
            $scope.showForm = false;

            $scope.accountId = $stateParams.accountId;
            $scope.effectId = $stateParams.effectId;

            allocationFactorFactory.getFactor($scope.accountId, $scope.effectId)
                .success(function (data) {
                    if (data === '' || data.account.id <= 0) {    // not found
                        window.location.hash = '#/allocation-factor';
                    } else {
                        try {
                            $scope.selectedAccount = data.account;
                            $scope.businessSegments = data.segmentPercentage;
                            $scope.selectedEffDate = data.effectivity;

                            $scope.showForm = true;
                        }catch (e) {}
                    }
                })
                .error(function (error) {
                    toastr.error('Failed to allocation factor.');
                    window.location.hash = '#/allocation-factor';
                });

            resourceURI = '/allocation-factor/update';
        } else {
            businessSegmentFactory.getSegments().success(function (data) { $scope.businessSegments = data; });
        }


        $scope.checkSum = function(newFactorObj, oldFactor) {
            var sum = $scope.getPercentageSum();
            if (sum > 100) {
                newFactorObj.value = parseFloat(oldFactor);
            }
        }
        $scope.getPercentageSum = function() {
            var percentageSum = 0;
            angular.forEach($scope.businessSegments, function(v, key) {
                percentageSum += v.value;
            }, percentageSum);

            return percentageSum;
        }

        $scope.showAccountBrowser = function () {
            if (angular.isUndefined($scope.parentAccounts) || $scope.parentAccounts.length == 0) {
                accountFactory.getAccounts()
                    .success(function (data) {
                        $scope.parentAccounts = data;
                        modalToggler.show('myModal');
                    })
                    .error(function (error) {
                        toastr.warning('Failed to load accounts.');
                    });
            }  else {
                modalToggler.show('myModal');
            }
        }

        $scope.accountSelectedFromBrowser = function (selectedAccount) {
            $scope.selectedAccount = selectedAccount;
            modalToggler.hide('myModal');
        }

        var cleanOnErrors = function() {
            $scope.save ='Save';
            // flags
            $scope.submitting = false;
            $scope.submit = false;
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            var $postData = {
                'id': $scope.accountId, // demarcate edit and add mode
                'account': $scope.selectedAccount,
                'segmentPercentage': $scope.businessSegments,
                'effectivity': $scope.selectedEffDate
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    window.location = '/#/allocation-factor';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    console.log(data);
                    window.location.hash = '#/allocation-factor/' + data.modelId + '/' + $scope.selectedEffDate.id + '/detail';
                    toastr.success('Factor successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                cleanOnErrors();
            });
        }

        $scope.setSelectedEffDate = function(s) {
            $scope.selectedEffDate = s;
        }

    }]);

allFactApp.controller('factorDetailsCtrl', ['$scope', '$state', '$stateParams', '$http', 'allocationFactorFactory', 'routeUtil',
        function($scope, $state, $stateParams, $http, allocationFactorFactory, routeUtil) {

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.doneLoading = false;

            $scope.accountId = $stateParams.accountId;
            $scope.effectId = $stateParams.effectId;

            allocationFactorFactory.getFactor($scope.accountId, $scope.effectId).success(function (data) {
                if (data === '' || data.account.id <= 0) {    // not found
                    window.location.hash = '#/allocation-factor';
                } else {
                    try {
                        $scope.account = data.account;
                        $scope.businessSegments = data.segmentPercentage;
                        $scope.effDate = data.effectivity;

                        $scope.doneLoading = true;
                    }catch (e) {}
                }
            }).error(function (error) {
                toastr.error('Failed to allocation factor.');
                $scope.doneLoading = true;
            });
        }]
);