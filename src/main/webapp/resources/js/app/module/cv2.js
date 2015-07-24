var cvApp = angular.module('cv', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnJournalEntrySetterModalApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'cvFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnAssignCheckApp',
    'cmnApvBrowserApp',
    'cmnJournalEntriesApp'
]);

cvApp.controller('addEditCvCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'cvFactory', 'ledgerFactory', 'allocationFactorFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, cvFactory, ledgerFactory, allocationFactorFactory) {

        var resourceURI = '/cv/create';

        $scope.glAmountForSL = 0;
        $scope.payees = [ENTITY_EMPLOYEE, ENTITY_SUPPLIER];
        $scope.signatories = [1];
        $scope.cv = {};
        $scope.journalEntries = [{}];
        $scope.subLedgerLines = [];
        $scope.taxCodes = [];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.mysqlFormatDate = $.datepicker.formatDate('yy-mm-dd', new Date());

        $scope.title = 'Create check voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;
        $scope.checkNumbers = [];

        $scope.cv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        $scope.payee_selection_handler = function(entity){
            $scope.cv.payee = entity;
        }

        $scope.checker_selection_handler = function(entity){
            $scope.cv.checker = entity;
        }

        $scope.recommendar_selection_handler = function(entity){
            $scope.cv.recommendingOfficer = entity;
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.cv.auditor = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.cv.approvingOfficer = entity;
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

                    angular.forEach($scope.journalEntries, function (entry, key) {
                        ledgerFactory.getSLEntriesAsync($scope.selectedApv.transId, entry.accountId).success(function (data) {
                            try {
                                if (data !== undefined && data.length > 0){
                                    $scope.subLedgerLines[key] = data;
                                }
                            } catch (e) {}
                        });
                    });

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
                    $scope.subLedgerLines = [];
                    $scope.selectedApv = undefined;
                    $scope.apvLabel = undefined;
                }
            } else {
                $scope.selectedApv = undefined;
                $scope.apvLabel = undefined;
            }
        }

        cvFactory.getTaxCodes().success(function (data) {
            $scope.taxCodes = data;
        });

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
                        if ([DOC_STAT_CREATED_DESC, DOC_RETURNED_TO_CREATOR_DESC].indexOf(data.documentStatus.status) < 0) {
                            window.location.hash = '#/forbidden/check-vouchers';
                            return;
                        }
                        if (data.apvDto !== undefined && data.apvDto != null) {
                            $scope.selectedApv = {
                                'id': data.apvDto.id,
                                'transId': data.apvDto.transId,
                                'localCode': data.apvDto.localCode,
                                'supplier': data.apvDto.vendor.name,
                                'amount': data.apvDto.amount
                            }
                            $scope.apvLabel = $scope.selectedApv.localCode + ' ' + $scope.selectedApv.supplier;
                        }
                        $scope.cv = data;
                        $scope.cv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.mysqlFormatDate = $.datepicker.formatDate('yy-mm-dd', new Date(data.voucherDate));

                        cvFactory.getCvChecks($scope.cv.transId).success(function (data) {
                            $scope.savedCheckNumbers = data;
                        });

                        $scope.showForm = true;

                    }catch (e) {
                        console.log(e);
                        toastr.warning('Something went wrong');
                    }
                }
            }).error(function (error) {
                toastr.warning('Voucher not found!');
                window.location.hash = '#/check-vouchers';
            });

            resourceURI = '/cv/update';
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

            $scope.journalTotals = voucherUtil.calculateJournalTotals($scope.journalEntries);

            if (undefined == $scope.journalTotals) {
                $scope.errors['err_generalLedgerLines'] =  ['Please complete journal entries'];
            } else if (parseFloat($scope.journalTotals.debit) != parseFloat($scope.journalTotals.credit)) {
                $scope.errors['err_generalLedgerLines'] =  ['Journal entries totals are not balanced.'];
            } else if (parseFloat($scope.journalTotals.debit) <= 0 || parseFloat($scope.journalTotals.credit) <= 0) {
                $scope.errors['err_generalLedgerLines'] =  ['Journal entries totals must be greater than zero'];
            }

            if ($scope.selectedApv !== undefined) {
                if (parseFloat($scope.selectedApv.amount) !=  parseFloat($scope.journalTotals.debit)) {
                    $scope.errors['err_generalLedgerLines'] =  ['Journal total is not equal to the amount of the selected APV.'];
                }
            }

            var subLedgerLines = [];
            angular.forEach($scope.subLedgerLines, function (innerArr, k) {
                angular.forEach(innerArr, function (object, v) { subLedgerLines.push(object); });
            });

            var checkNumbers = [];
            for (var key in $scope.checkNumbers) {
                var value =  $scope.checkNumbers[key];
                checkNumbers.push(value);
            }

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
                "transaction": { 'id' : $scope.cv.transId },
                "checkNumbers": checkNumbers
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
            $scope.save = 'Save';
            // flags
            $scope.submitting = false;
            $scope.submit = false;
        }

        // check assignment stuffs here
        var setJDist = function(idx, entry) {
            if($scope.editMode) {
                ledgerFactory.getGLEntriesAsync($scope.cv.transId, entry.accountId).success(function (data) {
                    angular.forEach(data, function (d, key) {
                        d['amount'] = parseFloat(d.debit) + parseFloat(d.credit);

                        // assign existing check numbers
                        if ($scope.savedCheckNumbers !== undefined && $scope.savedCheckNumbers.length > 0) {
                            if (($scope.checkNumbers === undefined || $scope.checkNumbers.length == 0)) {
                                angular.forEach($scope.savedCheckNumbers, function (sc, key) {
                                    if (sc.bankAccount.id == d.segmentAccountId) {
                                        d['checkNumber'] = sc.checkNumber;

                                        var idx = entry.code + d.segmentAccountId;
                                        $scope.checkNumbers[idx] = { 'bankAccount': sc.bankAccount, 'checkNumber': sc.checkNumber };
                                    }
                                });
                            }
                        }
                    });

                    $scope.journalEntries[idx]['distribution'] = data;
                });
            }
        }

        $scope.assignCheck = function () {
            $scope.assignCheckAllowed = false;
            angular.forEach($scope.journalEntries, function (entry, key) {
                if (entry.distribution === undefined || entry.distribution == 0) {
                    setJDist(key, entry);
                }
            });

            while ($.active > 0) {/*check running ajax*/}

            $scope.assignCheckAllowed = true;
        }

        $scope.assign_check_handler = function(accId, seg, ch, print) {
            var idx = accId + seg;
            if (ch !== undefined && $.trim(ch).length > 0) {
                $scope.checkNumbers[idx] = { 'bankAccount': { 'id': seg}, 'checkNumber': ch };
            } else {
                delete $scope.checkNumbers[idx];
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
            $scope.allowCheckPrinting = false;

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
                            $scope.allowCheckPrinting = $scope.cv.documentStatus.status == DOC_FOR_CHECK_WRITING_DESC;

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

                            cvFactory.getCvChecks($scope.cv.transId).success(function (data) {
                                $scope.savedCheckNumbers = data;
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
                    var response = ledgerFactory.getSLEntriesSync2($scope.cv.transId, glAccount.accountId);
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

            $scope.print2307 = function(type) {
                $http.get('/download/token').success(function(response) {
                    // Store token
                    var token = response.message[0];

                    // Start download
                    $scope.url = '/check-voucher/print-2307/'+ $scope.cv.payee.accountNo+ '/' + $scope.cv.transId + '?token='+token;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }


            // check printing stuffs here
            var setJDist = function(idx, entry) {
                ledgerFactory.getGLEntriesAsync($scope.cv.transId, entry.accountId).success(function (data) {
                    angular.forEach(data, function (d, key) {
                        d['amount'] = parseFloat(d.debit) + parseFloat(d.credit);

                        // assign existing check numbers
                        if ($scope.savedCheckNumbers !== undefined && $scope.savedCheckNumbers.length > 0) {
                            angular.forEach($scope.savedCheckNumbers, function (sc, key) {
                                if (sc.bankAccount.id == d.segmentAccountId) {
                                    d['checkNumber'] = sc.checkNumber;
                                    d['released'] = sc.released;
                                }
                            });
                        }
                    });
                    $scope.journalEntries[idx]['distribution'] = data;
                });
            }
            $scope.printCheck = function () {
                $scope.assignCheckAllowed = false;
                angular.forEach($scope.journalEntries, function (entry, key) {
                    if (entry.distribution === undefined || entry.distribution == 0) {
                        setJDist(key, entry);
                    }
                });

                while ($.active > 0) {/*check running ajax*/}

                $scope.assignCheckAllowed = true;
            }

            $scope.assign_check_handler = function(code, segAcctId, ch, print) {

                if ($scope.allowCheckPrinting) {
                    var trCheck = ch !== undefined ? $.trim(ch) : '';
                    if (trCheck.length > 0) {
                        if (print) {
                            while ($.active > 0) {/*check running ajax*/}
                            var newTab = window.open('/check-voucher/print-check/' + $scope.cv.transId + "/" + segAcctId, '_blank');
                            newTab.focus();
                            newTab.print();
                        } else {
                            var $postData = {
                                'checkNumber':  ch,
                                'transaction': {'id': $scope.cv.transId},
                                'bankAccount': {'id': segAcctId}
                            };

                            var res = $http.post("/cv/update-check", $postData);
                            res.success(function(data) {
                                if (data.notAuthorized) {
                                    toastr.error(data.messages[0]);
                                    window.location.reload();
                                } else if (!data.success) {
                                    toastr.warning('Error found.');
                                }
                            });
                        }
                    }
                }
            }
        }]
);