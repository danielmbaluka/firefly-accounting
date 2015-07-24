var cashReceiptsApp = angular.module('cashReceipts', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnJournalEntrySetterModalApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'cashReceiptsFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnJournalEntriesApp'
]);

cashReceiptsApp.controller('addEditCashReceiptsCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'cashReceiptsFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, cashReceiptsFactory, ledgerFactory) {

        var resourceURI = '/cash-receipts/create';

        $scope.glAmountForSL = 0;
        $scope.suppliers = [1,2];
        $scope.signatories = [1];
        $scope.cashReceipts = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create cash receipts';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.cashReceipts.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.checker_selection_handler = function(entity){
            $scope.cashReceipts.checker = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.cashReceipts.approvingOfficer = entity;
        }

        $scope.editMode = !($stateParams.cashReceiptsId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update cash receipts';
            $scope.showForm = false;

            $scope.cashReceiptsId = $stateParams.cashReceiptsId;

            cashReceiptsFactory.getCashReceipts($scope.cashReceiptsId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/cash-receipts';
                } else {
                    try {

                        $scope.cashReceipts = data;
                        $scope.cashReceipts.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));

                        $scope.showForm = true;

                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/cash-receipts';
                });

            resourceURI = '/cash-receipts/update';
        }

        $scope.processForm = function() {

            if (!$scope.submit) return; // check if save button is clicked
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.cashReceipts.particulars || $.trim($scope.cashReceipts.particulars) == '') {
                $scope.errors['err_particulars'] =  ['Please enter particulars.'];
            }
            if (undefined == $scope.cashReceipts.voucherDate || $scope.cashReceipts.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.cashReceipts.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.cashReceipts.approvingOfficer) {
                $scope.errors['err_approvingOfficer'] =  ['Please select approving officer.'];
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
                "id": $scope.cashReceipts.id,
                "particulars": $scope.cashReceipts.particulars,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.cashReceipts.voucherDate),
                "checker": $scope.cashReceipts.checker,
                "approvingOfficer": $scope.cashReceipts.approvingOfficer,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "amount": $scope.journalTotals.debit,
                "transaction": { 'id' : $scope.cashReceipts.transId }
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
                    window.location = '/#/cash-receipts';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/cash-receipts/' + data.modelId + '/detail';
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

cashReceiptsApp.controller('cashReceiptsMainCtrl', ['$scope', '$http', 'cashReceiptsFactory', 'documentStatusUtil',
        function($scope, $http, cashReceiptsFactory, documentStatusUtil) {

            $scope.doneLoading = false;

            cashReceiptsFactory.getList()
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

cashReceiptsApp.controller('cashReceiptsDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'cashReceiptsFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, cashReceiptsFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};

            $scope.cashReceiptsId = $stateParams.cashReceiptsId;
            var hasValidId = !($stateParams.cashReceiptsId === undefined);

            var loadCashReceipts = function() {
                cashReceiptsFactory.getCashReceipts($scope.cashReceiptsId).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/cash-receipts';
                    } else {
                        try {
                            $scope.cashReceipts = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.cashReceipts.documentStatus.status);

                            ledgerFactory.getGLEntries($scope.cashReceipts.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.cashReceipts.transId).success(function (k) {
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
                loadCashReceipts();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.loadSL = function(glAccount, index) {
                if($scope.cSls[index] !== undefined && $scope.cSls[index].length > 0) {
                    $scope.cSls[index] = [];
                } else {
                    var response = ledgerFactory.getSLEntriesSync2($scope.cashReceipts.transId, glAccount.accountId);
                    $scope.cSls[index] = response.data === undefined ? [] : response.data;
                }
            }

            $scope.setSelectedAction = function(action) {
                $scope.selectedAction = action;
                if ($scope.selectedAction === undefined) return;

                var $postData = {
                    'documentId' : $scope.cashReceiptsId,
                    'transId' : $scope.cashReceipts.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/cash-receipts/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/cash-receipts';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('Cash receipts successfully processed!');
                        loadCashReceipts();
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
                    $scope.url = '/cash-receipts-mgt/export/' + $scope.cashReceiptsId+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }
        }]
);