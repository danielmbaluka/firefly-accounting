var salesVoucherApp = angular.module('salesVoucher', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnJournalEntrySetterModalApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'salesVoucherFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnJournalEntriesApp'
]);

salesVoucherApp.controller('addEditSalesVoucherCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'salesVoucherFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, salesVoucherFactory, ledgerFactory) {

        var resourceURI = '/sales-voucher/create';

        $scope.glAmountForSL = 0;
        $scope.suppliers = [1,2];
        $scope.signatories = [1];
        $scope.salesVoucher = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create sales voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.salesVoucher.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
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

                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/sales-voucher';
                });

            resourceURI = '/sales-voucher/update';
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
                    toastr.success('Sales voucher successfully saved!');
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

salesVoucherApp.controller('salesVoucherMainCtrl', ['$scope', '$http', 'salesVoucherFactory', 'documentStatusUtil',
        function($scope, $http, salesVoucherFactory, documentStatusUtil) {

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

            $scope.label = function(status) {
                return (documentStatusUtil.classType(status));
            }
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
                    var response = ledgerFactory.getSLEntriesSync2($scope.salesVoucher.transId, glAccount.accountId);
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
                        toastr.success('Sales voucher successfully processed!');
                        loadSalesVoucher();
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
                    $scope.url = '/sales-voucher-mgt/export/' + $scope.salesVoucherId+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }
        }]
);