var mirApp = angular.module('mir', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnJournalEntrySetterModalApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'mirFactory',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnJournalEntriesApp'
]);

mirApp.controller('addEditMirCtrl', ['$scope', '$state', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'routeUtil', 'voucherUtil',
    'mirFactory', 'ledgerFactory',
    function($scope, $state, $stateParams, $http, errorToElementBinder, csrf, routeUtil, voucherUtil, mirFactory, ledgerFactory) {

        var resourceURI = '/mir/create';

        $scope.glAmountForSL = 0;

        $scope.main = function() {
            routeUtil.gotoMain($state);
        }

        $scope.mrctDetails = mirFactory.mockSelectedMrct();

        $scope.signatories = [1];
        $scope.mir = {};
        $scope.journalEntries = [{}];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.title = 'Create new voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;
        $scope.mir.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        $scope.checker_selection_handler = function(entity){
            $scope.mir.checker = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.mir.approvingOfficer = entity;
        }

        $scope.editMode = !($stateParams.id === undefined);
        if($scope.editMode) {
            $scope.title = 'Update material issue register';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            mirFactory.getMIR($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/mir';
                } else {
                    try {

                        $scope.mir = {
                            "id": data.id,
                            "code": data.localCode,
                            "particulars": data.particulars,
                            "checker": data.sLChecker,
                            "approvingOfficer": data.sLApprovingOfficer,
                            "generalLedgerLines": $scope.journalEntries
                        };
                        $scope.mir.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));

                        $scope.showForm = true;
                        $scope.mir.generalLedgerLines = $scope.journalEntries;

                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/mir';
                });

            resourceURI = '/mir/update';
        }

        $scope.processForm = function() {

            if (!$scope.submit) return; // check if save button is clicked
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.mir.particulars || $.trim($scope.mir.particulars) == '') {
                $scope.errors['err_particulars'] =  ['Please enter particulars.'];
            }
            if (undefined == $scope.mir.voucherDate || $scope.mir.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }

            if (undefined == $scope.mir.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.mir.approvingOfficer) {
                $scope.errors['err_approvingOfficer'] =  ['Please select approving officer.'];
            }
            $scope.journalTotals = voucherUtil.calculateJournalTotals($scope.journalEntries);
            if (undefined == $scope.journalTotals) {
                $scope.errors['err_journal'] =  ['Please complete journal entries'];
            } else if (parseFloat($scope.journalTotals.debit) != parseFloat($scope.journalTotals.credit)) {
                $scope.errors['err_journal'] =  ['Journal entries totals are not balanced.'];
            } else if (parseFloat($scope.journalTotals.debit) <= 0 || parseFloat($scope.journalTotals.credit) <= 0) {
                $scope.errors['err_journal'] =  ['Journal entries totals must be greater than zero'];
            }

            var subLedgerLines = [];
            angular.forEach($scope.allSlEntries, function (innerArr, k) {
                angular.forEach(innerArr, function (object, v) { subLedgerLines.push(object); });
            });


            var mirDetails = [];
            angular.forEach($scope.mrctDetails, function(v, i){

                var mrct = {
                    description: v.description,
                    unit: v.unit,
                    quantity: v.quantity,
                    unitPrice: v.unitPrice,
                    amount: v.amount
                };

                mirDetails.push(mrct);

            });

            var $postData = {
                "id": $scope.mir.id,
                "particulars": $scope.mir.particulars,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.mir.voucherDate),
                "checker": $scope.mir.checker,
                "approvingOfficer": $scope.mir.approvingOfficer,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "materialIssueRegisterDetails": mirDetails
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
                    window.location = '/#/mir';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/mir/' + data.modelId + '/detail';
                    toastr.success('MIR successfully saved!');
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

mirApp.controller('mirCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'mirFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, mirFactory) {

        $scope.title = 'Select MRCT to be posted to MIR';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.selectedItemData = {};
        $scope.mrctList = [];
        $scope.select_item_handler = function(item){
            $scope.selectedItemData = item;
            alert($scope.selectedItemData.code);
        };

        mirFactory.mirLst().success(function(data){
            $scope.mrctList = data;
        });
    }]);

mirApp.controller('mrctTestController',['$scope','mirFactory',function($scope,mirFactory){

    $scope.title = 'Select MRCT to be posted to MIR';

    $scope.mrctList = mirFactory.mrctLst();
    $scope.selectedMRCT = {};
    $scope.selectMRCT = function(selected){
        $scope.selectedMRCT = selected;
    };

}]);

mirApp.controller('mirDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'mirFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, mirFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};
            $scope.id = $stateParams.id;
            var hasValidId = !($stateParams.id === undefined);

            var loadMIR = function() {
                mirFactory.getMIR($scope.id).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/mir';
                    } else {
                        try {
                            $scope.mir = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.mir.status);

                            ledgerFactory.getGLEntries($scope.mir.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.mir.transId).success(function (k) {
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
                loadMIR();
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
                    'documentId' : $scope.id,
                    'transId' : $scope.mir.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/mir/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/mir';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('MIR successfully processed!');
                        loadMIR();
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
                    $scope.url = '/mir-mgt/export/' + $scope.id+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }
        }]
);