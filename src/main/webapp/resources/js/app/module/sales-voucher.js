var salesVoucherApp = angular.module('salesVoucher', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnAccountBrowserWithSegmentApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'salesVoucherFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory'
]);

salesVoucherApp.controller('addEditSalesVoucherCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'salesVoucherFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, salesVoucherFactory, ledgerFactory) {

        var resourceURI = '/sales-voucher/create';

        $scope.glAmountForSL = 0;
        $scope.signatories = [1];
        $scope.salesVoucher = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create sales voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.salesVoucher.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        $scope.selectedGlAccount = {}; // selected gl account
        $scope.accountIdsWithTouchedSl = []; // gl accounts which sl has been updated
        $scope.allSlEntries = [];
        $scope.glSlEntries = [];

        $scope.voucher_sl_setter_handler = function(newSlEntries) {
            $scope.allSlEntries[$scope.journalIdx] = newSlEntries;
            $scope.glSlEntries = newSlEntries;
            $scope.slLoaded = false;
        }

        $scope.checker_selection_handler = function(entity){
            $scope.salesVoucher.checker = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.salesVoucher.approvingOfficer = entity;
        }

        $scope.editMode = !($stateParams.salesVoucherId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update sales voucher';
            $scope.showForm = false;

            $scope.salesVoucherId = $stateParams.salesVoucherId;

            salesVoucherFactory.getSalesVoucher($scope.salesVoucherId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/sales-voucher';
                } else {
                    try {

                        $scope.salesVoucher = data;
                        $scope.salesVoucher.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));

                        $scope.showForm = true;

                        ledgerFactory.getGLEntries($scope.salesVoucher.transId).success(function (data) {
                            $scope.journalEntries = data;
                            $scope.updateTotal(0,0);
                        }).error(function (error) {
                            toastr.warning('Journal entries not loaded!');
                        });

                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/sales-voucher';
                });

            resourceURI = '/sales-voucher/update';
        }

        // load journal entries
        if ($scope.journalEntries.length > 0) {
            $scope.journalTotals = voucherUtil.calculateJournalTotals($scope.journalEntries);
        }

        $scope.processForm = function() {

            if (!$scope.submit) return; // check if save button is clicked
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.salesVoucher.particulars || $.trim($scope.salesVoucher.particulars) == '') {
                $scope.errors['err_particulars'] =  ['Please enter particulars.'];
            }

            if (undefined == $scope.salesVoucher.voucherDate || $scope.salesVoucher.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }

            if (undefined == $scope.salesVoucher.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.salesVoucher.approvingOfficer) {
                $scope.errors['err_approvingOfficer'] =  ['Please select approving officer.'];
            }
            if (undefined == $scope.journalTotals) {
                $scope.errors['err_generalLedgerLines'] =  ['Please complete journal entries'];
            } else if (parseFloat($scope.journalTotals.debit) != parseFloat($scope.journalTotals.credit)) {
                $scope.errors['err_generalLedgerLines'] =  ['Journal entries totals are not balanced.'];
            } else if (parseFloat($scope.journalTotals.debit) <= 0 || parseFloat($scope.journalTotals.credit) <= 0) {
                $scope.errors['err_generalLedgerLines'] =  ['Journal entries totals must be greater than zero'];
            }

            var subLedgerLines = [];
            angular.forEach($scope.allSlEntries, function (innerArr, k) {
                angular.forEach(innerArr, function (object, v) { subLedgerLines.push(object); });
            });

            var $postData = {
                "id": $scope.salesVoucher.id,
                "particulars": $scope.salesVoucher.particulars,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.salesVoucher.voucherDate),
                "checker": $scope.salesVoucher.checker,
                "approvingOfficer": $scope.salesVoucher.approvingOfficer,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "amount": $scope.journalTotals.debit,
                "transaction": { 'id' : $scope.salesVoucher.transId }
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
                    window.location = '/#/sales-voucher';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/sales-voucher/' + data.modelId + '/detail';
                    toastr.success('Sales Voucher successfully saved!');
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

        $scope.updateTotal = function(newAmount, oldAmount) {
            if (isNaN(newAmount)) {
                $scope.journalTotals = 0;
                toastr.clear();
                toastr.warning("Entered amount is invalid!");
            } else {
                $scope.journalTotals  = voucherUtil.calculateJournalTotals($scope.journalEntries);
            }
        }

        $scope.newBlankRow = function() {
            $scope.journalEntries.push({});
        }

        $scope.removeJournalRow = function(index, account) {
            try {
                if ($scope.journalEntries.length > 0) {
                    $scope.journalEntries.splice(index, 1);
                    $scope.updateTotal(0,0);

                    $scope.allSlEntries.splice(index, 1);
                    $scope.updateTotal(0,0);
                }
            }catch (e){}
        }

        $scope.accounts_selection_handler = function(account){
            var rowSelectedAccount = {
                "id": account.id,
                "segmentAccountId": account.segmentAccountId,
                "segmentAccountCode": account.segmentAccountCode,
                "description": account.title,
                "debit" : $scope.journalEntries[$scope.journalIdx].debit,
                "credit" : $scope.journalEntries[$scope.journalIdx].credit
            }
            // put back with updates
            $scope.journalEntries[$scope.journalIdx] = rowSelectedAccount;
        }

        $scope.setSelectedIdx = function(index){
            $scope.journalIdx = index;
        }

        $scope.loadExistingSlEntries = function(glAccount, index) {
            $scope.setSelectedIdx(index);

            if(glAccount === undefined || $.isEmptyObject(glAccount) || !glAccount.hasOwnProperty('segmentAccountId')) {
                toastr.clear();
                toastr.warning("Please select an account!");
                $scope.selectedGlAccount = {};
            } else if ((glAccount.debit === undefined || parseFloat(glAccount.debit) <= 0) && (glAccount.credit === undefined || parseFloat(glAccount.credit) <= 0)) {
                toastr.clear();
                toastr.warning("Please enter journal entry amount!");
                $scope.selectedGlAccount = {};
            } else {

                $scope.glAmountForSL = glAccount.debit > 0 ? glAccount.debit : glAccount.credit;

                if ($scope.selectedGlAccount.segmentAccountId != glAccount.segmentAccountId) {

                    $scope.selectedGlAccount = {
                        "id": glAccount.id,
                        "segmentAccountId": glAccount.segmentAccountId,
                        "segmentAccountCode": glAccount.segmentAccountCode,
                        "description": glAccount.description,
                        "debit": glAccount.debit,
                        "credit": glAccount.credit
                    };

                    if (!$scope.editMode) {
                        var certainSlEntries = $scope.allSlEntries[$scope.journalIdx];
                        certainSlEntries = (certainSlEntries === undefined || $.isEmptyObject(certainSlEntries)) ? [] : certainSlEntries;
                        $scope.glSlEntries = certainSlEntries;
                        $scope.slLoaded = true;
                    } else {

                        if (($scope.allSlEntries[$scope.journalIdx] == undefined ||
                            ($scope.allSlEntries[$scope.journalIdx]).length == 0) && glAccount.id !== undefined) {

                            var response = ledgerFactory.getSLEntriesSync(glAccount.id);
                            $scope.glSlEntries = response.data;
                            $scope.allSlEntries[$scope.journalIdx] = response.data;

                            $scope.slLoaded = true;

                        } else {

                            var certainSlEntries = $scope.allSlEntries[$scope.journalIdx];
                            certainSlEntries = (certainSlEntries === undefined || $.isEmptyObject(certainSlEntries)) ? [] : certainSlEntries;
                            $scope.glSlEntries = certainSlEntries;

                            $scope.slLoaded = true;
                        }
                    }
                } else {
                    $scope.selectedGlAccount = { "debit": glAccount.debit, "credit": glAccount.credit };
                    $scope.slLoaded = true;
                }
            }
        }
    }]);

salesVoucherApp.controller('salesVoucherMainCtrl', ['$scope', '$http', 'salesVoucherFactory',
        function($scope, $http, salesVoucherFactory) {

            $scope.doneLoading = false;

            salesVoucherFactory.getList()
                .success(function (data) {
                    $scope.vouchers = data;
                    $scope.doneLoading = true;
                })
                .error(function (error) {
                    toastr.error('Failed to load vouchers.');
                    $scope.doneLoading = true;
                });
        }]
);

salesVoucherApp.controller('salesVoucherDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'salesVoucherFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, salesVoucherFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};

            $scope.salesVoucherId = $stateParams.salesVoucherId;
            var hasValidId = !($stateParams.salesVoucherId === undefined);

            var loadSalesVoucher = function() {
                salesVoucherFactory.getSalesVoucher($scope.salesVoucherId).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/sales-voucher';
                    } else {
                        try {
                            $scope.salesVoucher = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.salesVoucher.documentStatus.status);

                            ledgerFactory.getGLEntries($scope.salesVoucher.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.salesVoucher.transId).success(function (k) {
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
                loadSalesVoucher();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.loadSL = function(glAccount, index) {
                if($scope.cSls[index] !== undefined && $scope.cSls[index].length > 0) {
                    $scope.cSls[index] = [];
                } else {
                    var response = ledgerFactory.getSLEntriesSync(glAccount.id);
                    $scope.cSls[index] = response.data === undefined ? [] : response.data;
                }
            }

            $scope.setSelectedAction = function(action) {
                $scope.selectedAction = action;
                if ($scope.selectedAction === undefined) return;

                var $postData = {
                    'documentId' : $scope.salesVoucherId,
                    'transId' : $scope.salesVoucher.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/sales-voucher/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/sales-voucher';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('Sales Voucher successfully processed!');
                        loadSalesVoucher();
                    }
                });
                res.error(function(data, status, headers, config) {
                    toastr.error('Something went wrong!');
                });
            }

            $scope.print = function(type) {

                toastr.options.extendedTimeOut = 0;
                toastr.options.timeOut = 0;
                toastr.info("Processing report...");

                $http.get('/download/token').success(function(response) {
                    // Store token
                    var token = response.message[0];

                    // Start download
                    $scope.url = '/sales-voucher/export/' + $scope.salesVoucherId+'?token='+token+'&type='+type;
                    toastr.clear();
                });
            }
        }]
);