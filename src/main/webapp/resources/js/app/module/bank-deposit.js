var bankDepositApp = angular.module('bankDeposit', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnJournalEntrySetterModalApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'bankDepositFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnJournalEntriesApp'
]);

bankDepositApp.controller('addEditBankDepositCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'bankDepositFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, bankDepositFactory, ledgerFactory) {

        var resourceURI = '/bank-deposit/create';

        $scope.glAmountForSL = 0;
        $scope.bankDeposit = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create bank deposit';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.bankDeposit.depositDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.bankDeposit.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.checker_selection_handler = function(entity){
            $scope.bankDeposit.checker = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.bankDeposit.approvingOfficer = entity;
        }

        $scope.editMode = !($stateParams.bankDepositId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update bank deposit';
            $scope.showForm = false;

            $scope.bankDepositId = $stateParams.bankDepositId;

            bankDepositFactory.getBankDeposit($scope.bankDepositId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/bank-deposit';
                } else {
                    try {

                        $scope.bankDeposit = data;
                        $scope.bankDeposit.depositDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.depositDate));
                        $scope.bankDeposit.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));

                        $scope.showForm = true;

                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/bank-deposit';
                });

            resourceURI = '/bank-deposit/update';
        }

        $scope.processForm = function() {

            if (!$scope.submit) return; // check if save button is clicked
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.bankDeposit.depositNumber || $.trim($scope.bankDeposit.depositNumber) == '') {
                $scope.errors['err_slipNo'] =  ['Please enter slip number.'];
            }
            if (undefined == $scope.bankDeposit.depositDate || $scope.bankDeposit.depositDate == '') {
                $scope.errors['err_depositDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.bankDeposit.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.bankDeposit.approvingOfficer) {
                $scope.errors['err_approvar'] =  ['Please select approving officer.'];
            }

            $scope.journalTotals = voucherUtil.calculateJournalTotals($scope.journalEntries);

            if (undefined == $scope.journalTotals) {
                $scope.errors['err_generalLedgerLines'] =  ['Please complete journal entries'];
            } else if (parseFloat($scope.journalTotals.debit) != parseFloat($scope.journalTotals.credit)) {
                $scope.errors['err_generalLedgerLines'] =  ['Journal entries totals are not balanced.'];
            } else if (parseFloat($scope.journalTotals.debit) <= 0 && parseFloat($scope.journalTotals.credit) <= 0) {
                $scope.errors['err_generalLedgerLines'] =  ['Journal entries totals must be greater than zero'];
            }

            var subLedgerLines = [];
            angular.forEach($scope.subLedgerLines, function (innerArr, k) {
                angular.forEach(innerArr, function (object, v) { subLedgerLines.push(object); });
            });

            var $postData = {
                "id": $scope.bankDeposit.id,
                "depositNumber": $scope.bankDeposit.depositNumber,
                "depositDate": $.datepicker.parseDate("mm/dd/yy",  $scope.bankDeposit.depositDate),
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.bankDeposit.voucherDate),
                "checker": $scope.bankDeposit.checker,
                "approvingOfficer": $scope.bankDeposit.approvingOfficer,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "amount": $scope.journalTotals.debit,
                "transaction": { 'id' : $scope.bankDeposit.transId }
            };

            if (!$.isEmptyObject($scope.errors)) {
                toastr.warning('Error found.');
                cleanOnErrors();
                return;
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    window.location = '/#/bank-deposit';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/bank-deposit/' + data.modelId + '/detail';
                    toastr.success('Cash receipts successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                cleanOnErrors();
            });
        }

        var cleanOnErrors = function() {
            $scope.save ='Save';
            // flags
            $scope.submitting = false;
            $scope.submit = false;
        }
    }]);

bankDepositApp.controller('bankDepositMainCtrl', ['$scope', '$http', 'bankDepositFactory', 'documentStatusUtil',
        function($scope, $http, bankDepositFactory, documentStatusUtil) {

            $scope.doneLoading = false;

            bankDepositFactory.getList()
                .success(function (data) {
                    $scope.vouchers = data;
                    $scope.doneLoading = true;
                })
                .error(function (error) {
                    toastr.error('Failed to load vouchers.');
                    $scope.doneLoading = true;
                });

            $scope.label = function(status) {
                return (documentStatusUtil.classType(status));
            }
        }]
);

bankDepositApp.controller('bankDepositDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'bankDepositFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, bankDepositFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};

            $scope.bankDepositId = $stateParams.bankDepositId;
            var hasValidId = !($stateParams.bankDepositId === undefined);

            var loadBankDeposit = function() {
                bankDepositFactory.getBankDeposit($scope.bankDepositId).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/bank-deposit';
                    } else {
                        try {
                            $scope.bankDeposit = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.bankDeposit.documentStatus.status);

                            ledgerFactory.getGLEntries($scope.bankDeposit.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.bankDeposit.transId).success(function (k) {
                                    $scope.actions = k;
                                }).error(function (error) {
                                    toastr.warning('Actions not loaded!');
                                });

                                $scope.journalEntries = d;
                                $scope.journalTotals  = voucherUtil.calculateJournalTotals($scope.journalEntries);
                                $scope.doneLoading = true;

                            }).error(function (error) {
                                $scope.doneLoading = true;
                                toastr.warning('Journal entries not loaded!');
                            });
                        }catch (e) {}
                    }
                }).error(function (error) {
                    toastr.warning('Voucher not found!');
                });
            }
            if(hasValidId) {
                loadBankDeposit();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.loadSL = function(glAccount, index) {
                if($scope.cSls[index] !== undefined && $scope.cSls[index].length > 0) {
                    $scope.cSls[index] = [];
                } else {
                    var response = ledgerFactory.getSLEntriesSync2($scope.bankDeposit.transId, glAccount.accountId);
                    $scope.cSls[index] = response.data === undefined ? [] : response.data;
                }
            }

            $scope.setSelectedAction = function(action) {
                $scope.selectedAction = action;
                if ($scope.selectedAction === undefined) return;

                var $postData = {
                    'documentId' : $scope.bankDepositId,
                    'transId' : $scope.bankDeposit.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/bank-deposit/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/bank-deposit';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('Bank deposit successfully processed!');
                        loadBankDeposit();
                    }
                });
                res.error(function(data, status, headers, config) {
                    toastr.error('Something went wrong!');
                });
            }

            $scope.print = function(type) {
                $http.get('/download/token').success(function(response) {
                    // Store token
                    var token = response.message[0];

                    // Start download
                    $scope.url = '/bank-deposit-mgt/export/' + $scope.bankDepositId+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }
        }]
);