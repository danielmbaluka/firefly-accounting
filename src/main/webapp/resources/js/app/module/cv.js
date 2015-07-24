var cvApp = angular.module('cv', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnAccountBrowserWithSegmentApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'cvFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnAssignCheckApp',
    'cmnApvBrowserApp'
]);

cvApp.controller('addEditCvCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'cvFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, cvFactory, ledgerFactory) {

        var resourceURI = '/cv/create';

        $scope.glAmountForSL = 0;
        $scope.payees = [1,2];
        $scope.signatories = [1];
        $scope.cv = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create check voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.cv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        $scope.selectedGlAccount = {}; // selected gl account
        $scope.accountIdsWithTouchedSl = []; // gl accounts which sl has been updated
        $scope.allSlEntries = [];
        $scope.glSlEntries = [];
        $scope.enteredCheckNumber = 'default';

        $scope.voucher_sl_setter_handler = function(newSlEntries) {
            $scope.allSlEntries[$scope.journalIdx] = newSlEntries;
            $scope.glSlEntries = newSlEntries;
            $scope.slLoaded = false;
        }

        $scope.payee_selection_handler = function(entity){
            $scope.cv.payee = entity;
        }
        $scope.checker_selection_handler = function(entity){
            $scope.cv.checker = entity;
        }

        $scope.recommendar_selection_handler = function(entity){
            $scope.cv.recommendingOfficer = entity;
        }

        $scope.apv_selection_handler = function(apv){
            if (apv !== undefined) {
                $scope.selectedApv = apv;
                $scope.apvLabel = apv.localCode + ' ' + apv.supplier;
                $scope.cv.checkAmount = apv.amount;
                $scope.cv.payee = { 'accountNo': apv.supplierAccountNo, 'name': apv.supplier};
                $scope.cv.particulars = apv.particulars;
                // load ledgers
                ledgerFactory.getGLEntries($scope.selectedApv.transId).success(function (data) {
                    $scope.journalEntries = data;
                    $scope.updateTotal(0,0);
                    $scope.allSlEntries = [];
                    $scope.glSlEntries = [];
                }).error(function (error) {
                    toastr.warning('APV journal entries not loaded!');
                });
            }
        }

        $scope.clearApv = function() {
            if ($scope.editMode && $scope.selectedApv !== undefined) {
                var r = confirm("Removing APV will clear the CV's journal entries. Do you want to continue?");
                if (r == true) {
                    $scope.journalEntries = [];
                    $scope.updateTotal(0,0);
                    $scope.allSlEntries = [];
                    $scope.glSlEntries = [];
                }
            } else {
                $scope.selectedApv = undefined;
                $scope.apvLabel = undefined;
            }
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.cv.auditor = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.cv.approvingOfficer = entity;
        }

        $scope.editMode = !($stateParams.cvId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update check voucher';
            $scope.showForm = false;

            $scope.cvId = $stateParams.cvId;

            cvFactory.getCv($scope.cvId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/check-vouchers';
                } else {
                    try {
                        if (data.apvDto !== undefined && data.apvDto != null) {
                            $scope.selectedApv = {
                                'id': data.apvDto.id,
                                'transId': data.apvDto.transId,
                                'localCode': data.apvDto.localCode,
                                'supplier': data.apvDto.vendor.name
                            }
                            $scope.apvLabel = $scope.selectedApv.localCode + ' ' + $scope.selectedApv.supplier;
                        }
                        $scope.cv = data;
                        $scope.cv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));

                        ledgerFactory.getGLEntries($scope.cv.transId).success(function (data) {
                            $scope.journalEntries = data;
                            $scope.updateTotal(0,0);
                        }).error(function (error) {
                            toastr.warning('Journal entries not loaded!');
                        });

                        $scope.showForm = true;

                    }catch (e) {
                        console.log(e);
                        toastr.warning('Something went wrong');
                    }
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/check-vouchers';
                });

            resourceURI = '/cv/update';
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
            if (undefined == $scope.cv.payee) {
                $scope.errors['err_payee'] = ['Please select payee.']
            }
            if (undefined == $scope.cv.checkAmount || isNaN(parseFloat($scope.cv.checkAmount))) {
                $scope.errors['err_checkAmount'] =  ['Please enter check amount.'];
            }
            if (undefined == $scope.cv.particulars || $.trim($scope.cv.particulars) == '') {
                $scope.errors['err_particulars'] =  ['Please enter particulars.'];
            }
            if (undefined == $scope.cv.voucherDate || $scope.cv.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.cv.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.cv.recommendingOfficer) {
                $scope.errors['err_recommendingOfficer'] =  ['Please select recommending officer.'];
            }
            if (undefined == $scope.cv.auditor) {
                $scope.errors['err_auditor'] =  ['Please select auditor.'];
            }
            if (undefined == $scope.cv.approvingOfficer) {
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
                "id": $scope.cv.id,
                "payee": $scope.cv.payee,
                "particulars": $scope.cv.particulars,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.cv.voucherDate),
                "checker": $scope.cv.checker,
                "approvingOfficer": $scope.cv.approvingOfficer,
                "recommendingOfficer": $scope.cv.recommendingOfficer,
                "auditor": $scope.cv.auditor,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "amount": $scope.journalTotals.debit,
                "checkAmount": parseFloat($scope.cv.checkAmount),
                "transaction": { 'id' : $scope.cv.transId }
            };

            try {
                $postData['accountsPayableVouchers'] = [{ 'id': $scope.selectedApv.id }];
            } catch (e) {}

            if (!$.isEmptyObject($scope.errors)) {
               toastr.warning('Error found.');
               cleanOnErrors();
               return;
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    window.location = '/#/check-vouchers';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/check-vouchers/' + data.modelId + '/detail';
                    toastr.success('CV successfully saved!');
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

                    if (!$scope.editMode && $scope.selectedApv === undefined) {
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

        // check assignment stuffs here
        $scope.e = {};
        $scope.ea = {};
        $scope.assignCheck = function (a, i) {
            $scope.journalIdx = i;
            if(a === undefined || $.isEmptyObject(a) || !a.hasOwnProperty('segmentAccountId')) {
                toastr.clear();
                toastr.warning("Please select an account!");
            } else {
                try {
                    $scope.e = {'check' : a.checkNumber};
                } catch (e) {}
                $scope.ea = a;
                $scope.assignCheckAllowed = true;
            }
        }

        $scope.assign_check_handler = function(ch) {
            if (ch !== undefined) {
                $scope.journalEntries[$scope.journalIdx].checkNumber = ch.check;
            }
        }
    }]);

cvApp.controller('cvMainCtrl', ['$scope', '$http', 'cvFactory', 'documentStatusUtil',
        function($scope, $http, cvFactory, documentStatusUtil) {

            $scope.doneLoading = false;

            cvFactory.getList()
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
cvApp.controller('cvDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'cvFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, cvFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};

            $scope.cvId = $stateParams.cvId;
            var hasValidId = !($stateParams.cvId === undefined);

            var loadCv = function() {
                cvFactory.getCv($scope.cvId).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/check-vouchers';
                    } else {
                        try {
                            $scope.cv = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.cv.documentStatus.status);

                            ledgerFactory.getGLEntries($scope.cv.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.cv.transId).success(function (k) {
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
                loadCv();
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
                    'documentId' : $scope.cvId,
                    'transId' : $scope.cv.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/cv/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/check-vouchers';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('CV successfully processed!');
                        loadCv();
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
                    $scope.url = '/check-voucher/export/' + $scope.cvId+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }

            // check printing stuffs here
            $scope.e = {};
            $scope.ea = {};
            $scope.cIdx = undefined;
            $scope.printCheck = function (a, i) {
                $scope.cIdx = i;
                if(a === undefined || $.isEmptyObject(a) || !a.hasOwnProperty('segmentAccountId')) {
                    toastr.clear();
                    toastr.warning("Please select an account!");
                } else {
                    try {
                        $scope.e = {'check' : a.checkNumber};
                    } catch (e) {}
                    $scope.ea = a;
                    $scope.assignCheckAllowed = true;
                }
            }

            $scope.print_check_handler = function(ne, p) {
                if (ne !== undefined && $scope.e.check != ne.check) {
                    $scope.journalEntries[$scope.cIdx].checkNumber = ne.check;

                    // saving new check number
                    var $postData = {
                        'checkNumber': ne.check,
                        'transaction': {'id': $scope.cv.transId},
                        'bankAccount': {'id': $scope.ea.segmentAccountId}
                    };

                    var res = $http.post("/cv/update-check", $postData);

                    res.success(function(data) {

                        if (data.notAuthorized) {
                            toastr.error(data.messages[0]);
                            window.location.reload();
                        } else if (!data.success) {
                            toastr.warning('Error found.');
                        } else {
                            toastr.success('Changes successfully saved!');
                            if (p) {
                                triggerPrinter(ne);
                            }
                        }
                    });
                    res.error(function(data, status, headers, config) {
                        toastr.error('Something went wrong!');
                    });
                } else if (p) {
                    triggerPrinter(ne);
                }
            }

            var triggerPrinter = function(ne) {
                if (ne !== undefined && $.trim(ne.check).length > 0) {
                    var newTab = window.open('/check-voucher/print-check/' + $scope.cv.transId + "/" + $scope.ea.segmentAccountId, '_blank');
                    newTab.focus();
                    newTab.print();
                }
            }
            // check printing stuffs end here
        }]
);