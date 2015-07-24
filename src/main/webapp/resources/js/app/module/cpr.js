/**
 * Created by TSI Admin.
 */
var cprApp = angular.module('cpr', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService',
    'accountFactory'
]);

cprApp.controller('addEditCPRCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'accountFactory', 'modalToggler',
    function ($scope, $stateParams, $http, errorToElementBinder, csrf, accountFactory, modalToggler) {

        var resourceURI = '/cpr/create';

        $scope.title = 'Create continuing property record';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.cpr = {};
        $scope.modalBodyTemplateUrl = "/common/account-browser";

        $scope.processForm = function () {
            // Check if save button is clicked.
            if (!$scope.submit) return;

            $scope.save = 'Saving...';
            $scope.errors = {};
            $scope.submitting = true;

            csrf.setCsrfToken();

            // Validate here.
            if (undefined == $scope.cpr.description || $.trim($scope.cpr.description) == '') {
                $scope.errors['err_description'] = ['Please enter description...' + $scope.cpr.description]
            }

            if (undefined == $scope.cpr.refNo || $.trim($scope.cpr.refNo) == '') {
                $scope.errors['err_ref_no'] = ['Please enter reference number...']
            }

            if (undefined == $scope.cpr.value || $.trim($scope.cpr.value) == '') {
                $scope.errors['err_value'] = ['Please enter value...']
            }

            if (undefined == $scope.cpr.depreciationYears || $.trim($scope.cpr.depreciationYears) == '') {
                $scope.errors['err_depreciation_years'] = ['Please enter depreciation years...']
            }

            if (undefined == $scope.cpr.depreciationMonths || $.trim($scope.cpr.depreciationMonths) == '') {
                $scope.errors['err_depreciation_months'] = ['Please enter depreciation months...']
            }

            if (undefined == $scope.cpr.depreciatedValue || $.trim($scope.cpr.depreciatedValue) == '') {
                $scope.errors['err_depreciated_value'] = ['Please enter depreciated value...']
            }

            if (undefined == $scope.cpr.startYear || $.trim($scope.cpr.startYear) == '') {
                $scope.errors['err_start_year'] = ['Please enter start year...']
            }

            if (undefined == $scope.cpr.startMonth || $.trim($scope.cpr.startMonth) == '') {
                $scope.errors['err_start_month'] = ['Please enter start month...']
            }

            if (undefined == $scope.cpr.acquisitionDate || $.trim($scope.cpr.acquisitionDate) == '') {
                $scope.errors['err_acquisition_date'] = ['Please enter acquisition date...']
            }

            var $postData = {
                "id": $scope.cpr.id,
                "fkAccountNo": 535,// $scope.cpr.fkAccountNo,
                "refNo": $scope.cpr.refNo,
                "description": $scope.cpr.description,
                "acquisitionDate": $("#acquisitionDate").data('datepicker').getFormattedDate('yyyy-mm-dd'),
                "value": $scope.cpr.value,
                "depreciationMonths": $scope.cpr.depreciationMonths,
                "depreciationYears": $scope.cpr.depreciationYears,
                "startYear": $scope.cpr.startYear,
                "startMonth": $("#startMonth").data('datepicker').getFormattedDate('mm'),
                "endYear": $scope.cpr.endYear,
                "endMonth": $scope.cpr.endMonth,
                "monthlyDepreciation": $scope.cpr.monthlyDepreciation,
                "depreciatedValue": $scope.cpr.depreciatedValue,
                "remainingValue": $scope.cpr.remainingValue,
                "fkAssetAccountId": 13,// $scope.cpr.fkAssetAccountId,
                "fkExpenseAccountId": 13,// $scope.cpr.fkExpenseAccountId,
                "fkAccountPepAccountId": 13,// $scope.cpr.fkAccountPepAccountId,
                "fkCreatedByUserId": 13// $scope.cpr.fkCreatedByUserId
            };

            if (!$.isEmptyObject($scope.errors)) {
                toastr.warning('Error found.');

                cleanOnErrors();

                return;
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function (data) {
                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);

                    window.location = '/#/continuing-property-record';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save = 'Save';

                    // Flags.
                    $scope.submitting = false;
                    $scope.submit = false;

                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/continuing-property-record/' + data.modelId + '/detail';

                    toastr.success('CPR successfully saved!');
                }
            });

            res.error(function (data, status, headers, config) {
                toastr.error('Something went wrong!');

                cleanOnErrors();
            });
        };

        var cleanOnErrors = function () {
            $scope.save = 'Save';

            // Flags.
            $scope.submitting = false;
            $scope.submit = false;
        };

        $scope.showAccuDepreAccountBrowser = function () {
            if (angular.isUndefined($scope.parentAccounts) || $scope.parentAccounts.length == 0) {
                accountFactory.getAccounts()
                    .success(function (data) {
                        $scope.parentAccounts = data;
                        modalToggler.show('accuDepreAccountModal');
                    })
                    .error(function (error) {
                        toastr.warning('Failed to load accounts.');
                    });
            } else {
                modalToggler.show('accuDepreAccountModal');
            }
        }

        $scope.accountSelectedFromBrowser = function (selectedAccount) {
            $scope.selectedAccount = selectedAccount;
            modalToggler.hide('accuDepreAccountModal');
        }
    }]);
