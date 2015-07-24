var apvApp = angular.module('apv', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnAccountBrowserWithSegmentApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'apvFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory'
]);

apvApp.controller('addEditApvCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'apvFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, apvFactory, ledgerFactory) {

        var resourceURI = '/apv/create';

        $scope.glAmountForSL = 0;
        $scope.suppliers = [1,2];
        $scope.signatories = [1];
        $scope.apv = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create accounts payable voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.apv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.apv.dueDate =  $.datepicker.formatDate('mm/dd/yy', new Date());

        $scope.selectedGlAccount = {}; // selected gl account
        $scope.accountIdsWithTouchedSl = []; // gl accounts which sl has been updated
        $scope.allSlEntries = [];
        $scope.glSlEntries = [];

        $scope.voucher_sl_setter_handler = function(newSlEntries) {
            $scope.allSlEntries[$scope.journalIdx] = newSlEntries;
            $scope.glSlEntries = newSlEntries;
            $scope.slLoaded = false;
        }

        $scope.supplier_selection_handler = function(entity){
            $scope.apv.vendor = entity;
        }
        $scope.checker_selection_handler = function(entity){
            $scope.apv.checker = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.apv.approvingOfficer = entity;
        }

        $scope.editMode = !($stateParams.apvId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update accounts payable voucher';
            $scope.showForm = false;

            $scope.apvId = $stateParams.apvId;

            apvFactory.getApv($scope.apvId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/accounts-payable';
                } else {
                    try {

                        $scope.apv = data;
                        $scope.apv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.apv.dueDate =  $.datepicker.formatDate('mm/dd/yy', new Date(data.dueDate));

                        $scope.showForm = true;

                        ledgerFactory.getGLEntries($scope.apv.transId).success(function (data) {
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
                    window.location.hash = '#/accounts-payable';
                });

            resourceURI = '/apv/update';
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
            if (undefined == $scope.apv.vendor) {
                $scope.errors['err_vendor'] = ['Please select vendor.']
            }
            if (undefined == $scope.apv.particulars || $.trim($scope.apv.particulars) == '') {
                $scope.errors['err_particulars'] =  ['Please enter particulars.'];
            }
            if (undefined == $scope.apv.voucherDate || $scope.apv.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.apv.dueDate || $scope.apv.dueDate == '') {
                $scope.errors['err_dueDate'] =  ['Please enter due date.'];
            }
            if (undefined == $scope.apv.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.apv.approvingOfficer) {
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
                "id": $scope.apv.id,
                "vendor": $scope.apv.vendor,
                "particulars": $scope.apv.particulars,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.apv.voucherDate),
                "dueDate": $.datepicker.parseDate("mm/dd/yy",  $scope.apv.dueDate),
                "checker": $scope.apv.checker,
                "approvingOfficer": $scope.apv.approvingOfficer,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "amount": $scope.journalTotals.debit,
                "transaction": { 'id' : $scope.apv.transId }
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
                    window.location = '/#/accounts-payable';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/accounts-payable/' + data.modelId + '/detail';
                    toastr.success('APV successfully saved!');
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

apvApp.controller('apvMainCtrl', ['$scope', '$http', 'apvFactory', 'documentStatusUtil',
        function($scope, $http, apvFactory, documentStatusUtil) {

            $scope.doneLoading = false;

            apvFactory.getList()
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


apvApp.controller('apvDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'apvFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, apvFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};

            $scope.apvId = $stateParams.apvId;
            var hasValidId = !($stateParams.apvId === undefined);

            var loadApv = function() {
                apvFactory.getApv($scope.apvId).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/accounts-payable';
                    } else {
                        try {
                            $scope.apv = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.apv.documentStatus.status);

                            ledgerFactory.getGLEntries($scope.apv.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.apv.transId).success(function (k) {
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
                loadApv();
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
                    'documentId' : $scope.apvId,
                    'transId' : $scope.apv.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/apv/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/accounts-payable';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('APV successfully processed!');
                        loadApv();
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
                    $scope.url = '/accounts-payable/export/' + $scope.apvId+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }

        }]
);