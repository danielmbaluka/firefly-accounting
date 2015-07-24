var ppApp = angular.module('pp', [
    'prepaymentFactory',
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnAccountBrowserWithSegmentApp',
    'utilService'
]);

ppApp.controller('prepaymentListCtrl', ['$scope', '$http', 'prepaymentFactory', 'documentStatusUtil',
    function($scope,  $http, prepaymentFactory, documentStatusUtil) {
        $scope.doneLoading = false;

        prepaymentFactory.getPrepaymentList()
            .success(function (data) {
                $scope.prepaymentList = data;
                $scope.doneLoading = true;
            })
            .error(function (error) {
                toastr.error('Failed to load Prepayments!');
                $scope.doneLoading = true;
            });
    }]);

ppApp.controller('addEditPpCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    'voucherUtil', 'prepaymentFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, prepaymentFactory) {
        $scope.pp = {};
        $scope.pp.totalCost = 0.00;
        $scope.pp.monthlyCost = 0.00;
        $scope.pp.noOfMonths = 0;
        $scope.pp.appliedCost = 0.00;
        $scope.pp.balance = 0.00;

        $scope.prepAccounts_selection_handler = function(account){
            var segmentAccount = {};
            segmentAccount['id'] =  account.segmentAccountId;
            $scope.pp.prepaymentAccount = segmentAccount;
            $scope.selectedAccount1 = account.title;
        }

        $scope.expAccounts_selection_handler = function(account){
            var segmentAccount = {};
            segmentAccount['id'] =  account.segmentAccountId;
            $scope.pp.expenseAccount = segmentAccount;
            $scope.selectedAccount2 = account.title;
        }

        $scope.title = 'Create Prepayment';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.pp.datePaid = $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/pre-payment/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Prepayment';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            prepaymentFactory.getPrepayment($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/pre-payment';
                } else {
                    try{
                        $scope.pp = data;
                        $scope.pp.datePaid = $.datepicker.formatDate('mm/dd/yy', new Date(data.datePaid));
                        $scope.selectedAccount1 = data.prepaymentAccount.account.title;
                        $scope.selectedAccount2 = data.expenseAccount.account.title;
                        $scope.showForm = true;
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Prepayment not found!');
                    window.location.hash = '#/pre-payment';
                });

            resourceURI = '/pre-payment/update';
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.pp.datePaid || $scope.pp.datePaid == '') {
                $scope.errors['err_datePaid'] =  ['Please enter date paid.'];
            }
            if (undefined == $scope.pp.description) {
                $scope.errors['err_description'] =  ['Please enter Description.'];
            }
            if (undefined == $scope.pp.totalCost || $scope.pp.totalCost == 0) {
                $scope.errors['err_totalCost'] =  ['Please enter Total Cost.'];
            }
            if (undefined == $scope.pp.noOfMonths || $scope.pp.noOfMonths == 0) {
                $scope.errors['err_noOfMonths'] =  ['Please enter No. Of Months.'];
            }
            if (undefined == $scope.pp.monthlyCost || $scope.pp.monthlyCost == 0) {
                $scope.errors['err_monthlyCost'] =  ['Please enter Monthly Cost.'];
            }
            if (undefined == $scope.pp.appliedCost || $scope.pp.appliedCost == 0) {
                $scope.errors['err_appliedCost'] =  ['Please enter AppliedCost.'];
            }
            if (undefined == $scope.pp.balance || $scope.pp.balance == 0) {
                $scope.errors['err_balance'] =  ['Please enter Balance.'];
            }
            if (undefined == $scope.pp.prepaymentAccount) {
                $scope.errors['err_prepaymentAccount'] =  ['Please select account.'];
            }
            if (undefined == $scope.pp.expenseAccount) {
                $scope.errors['err_expenseAccount'] =  ['Please select account.'];
            }

            $postData = {
                "id": $scope.pp.id,
                "datePaid": $.datepicker.parseDate("mm/dd/yy",  $scope.pp.datePaid),
                "description": $scope.pp.description,
                "totalCost": $scope.pp.totalCost,
                "noOfMonths": $scope.pp.noOfMonths,
                "monthlyCost": $scope.pp.monthlyCost,
                "appliedCost": $scope.pp.appliedCost,
                "balance": $scope.pp.balance,
                "prepaymentAccount": $scope.pp.prepaymentAccount,
                "expenseAccount": $scope.pp.expenseAccount,
                "accountNo": 0,
                "approvingOfficer": $scope.pp.expenseAccount
            };

            if (!$.isEmptyObject($scope.errors)) {
                toastr.warning('Error found2.');
                cleanOnErrors();
                return;
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    window.location = '/#/pre-payment';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found1.');
                } else {
                    window.location.hash = '/#/pre-payment/' + data.modelId + '/detail';
                    toastr.success('Prepayment successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                cleanOnErrors();
            });
        }

        var cleanOnErrors = function() {
            $scope.save = 'Save';
            // flags
            $scope.submitting = false;
            $scope.submit = false;
        }

        $scope.updateMonthlyCost = function(totalCost, months) {
            if (isNaN(totalCost) || isNaN(months)) {
                $scope.pp.monthlyCost = 0.00;
                toastr.clear();
            } else if(months == 0){
                $scope.pp.balance = voucherUtil.calculateBalance(totalCost, $scope.pp.appliedCost);
            } else {
                $scope.pp.balance = voucherUtil.calculateBalance(totalCost, $scope.pp.appliedCost);
                $scope.pp.monthlyCost = voucherUtil.calculateMonthlyCost($scope.pp.balance, months);
            }
        }

        $scope.updateBalance = function(totalCost, appliedCost) {
            if (isNaN(totalCost) || isNaN(appliedCost)) {
                $scope.pp.balance = 0.00;
                toastr.clear();
            } else {
                $scope.pp.balance = voucherUtil.calculateBalance(totalCost, appliedCost);
                $scope.pp.monthlyCost = voucherUtil.calculateMonthlyCost($scope.pp.balance, $scope.pp.noOfMonths);
            }
        }
    }]);