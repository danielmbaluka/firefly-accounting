/**
 * Created by TSI Admin on 3/23/2015.
 */
var jvApp = angular.module('jv', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnJournalEntrySetterModalApp',
    'cmnSLEntityBrowserApp',
    'utilService',
    'ledgerFactory',
    'cmnVoucherSlSetterApp',
    'workflowFactory',
    'cmnJournalEntriesApp',
    'jvFactory',
    'cmnTempLedgerEntrySelectorModalApp'
]);

jvApp.controller('jvMainCtrl', ['$scope', '$http', 'jvFactory', 'documentStatusUtil',
        function($scope, $http, jvFactory, documentStatusUtil) {

            $scope.doneLoading = false;

            jvFactory.getList()
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

jvApp.controller('addEditJvCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf', 'voucherUtil',
    'jvFactory', 'ledgerFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, jvFactory, ledgerFactory) {

        var resourceURI = '/jv/create';

        $scope.entities = [ENTITY_EMPLOYEE, ENTITY_SUPPLIER];
        $scope.signatories = [1];
        $scope.jv = {};
        $scope.journalEntries = [{}];
        $scope.subLedgerLines = [];
        $scope.journalTotals = {"debit" : 0, "credit" : 0};
        $scope.mysqlFormatDate = $.datepicker.formatDate('yy-mm-dd', new Date());

        $scope.title = 'Create journal voucher';
        $scope.save = 'Save';
        $scope.showForm = true;
        $scope.slLoaded = false;

        $scope.jv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.jv.dueDate =  $.datepicker.formatDate('mm/dd/yy', new Date());

        $scope.checker_selection_handler = function(entity){
            $scope.jv.checker = entity;
        }

        $scope.recommendar_selection_handler = function(entity){
            $scope.jv.recommendingOfficer = entity;
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.jv.auditor = entity;
        }

        $scope.approvar_selection_handler = function(entity){
            $scope.jv.approvingOfficer = entity;
        }

        $scope.clearTemp = function() {
            $scope.tempBatch = undefined;
        }

        $scope.temp_selection_handler = function(temp){
            if (temp !== undefined && !$.isEmptyObject(temp)) {
                $scope.tempBatch = temp;
                // load ledgers
                ledgerFactory.getTempGLEntries(temp.tempBatchId).success(function (data) {
                    $scope.journalEntries = data;

                    angular.forEach($scope.journalEntries, function (entry, key) {
                        ledgerFactory.getTempSLEntries(temp.tempBatchId, entry.accountId).success(function (data) {
                            try {
                                if (data !== undefined && data.length > 0){
                                    $scope.subLedgerLines[key] = data;
                                }
                            } catch (e) {
                                console.log(e);
                            }
                        });
                    });

                }).error(function (error) {
                    toastr.warning('Temporary journal entries not loaded!');
                });
            }
        }

        $scope.editMode = !($stateParams.jvId === undefined);
        if($scope.editMode) {
            $scope.title = 'Update journal voucher';
            $scope.showForm = false;

            $scope.jvId = $stateParams.jvId;

            jvFactory.getJv($scope.jvId).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/journal-voucher';
                } else {
                    try {
                        if ([DOC_STAT_CREATED_DESC, DOC_RETURNED_TO_CREATOR_DESC].indexOf(data.documentStatus.status) < 0) {
                            window.location.hash = '#/forbidden/journal-voucher';
                            return;
                        }
                        $scope.jv = data;
                        $scope.jv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.mysqlFormatDate =  $.datepicker.formatDate('yy-mm-dd', new Date(data.voucherDate));

                        $scope.showForm = true;

                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Voucher not found!');
                    window.location.hash = '#/journal-voucher';
                });

            resourceURI = '/jv/update';
        }

        $scope.processForm = function() {

            if (!$scope.submit) return; // check if save button is clicked
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.jv.explanation || $.trim($scope.jv.explanation) == '') {
                $scope.errors['err_explanation'] =  ['Please enter explanation.'];
            }
            if (undefined == $scope.jv.voucherDate || $scope.jv.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.jv.auditor) {
                $scope.errors['err_auditor'] =  ['Please select auditor.'];
            }
            if (undefined == $scope.jv.recommendingOfficer) {
                $scope.errors['err_recommendingOfficer'] =  ['Please select recommending officer.'];
            }
            if (undefined == $scope.jv.checker) {
                $scope.errors['err_checker'] =  ['Please select checker.'];
            }
            if (undefined == $scope.jv.approvingOfficer) {
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
                "id": $scope.jv.id,
                "explanation": $scope.jv.explanation,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.jv.voucherDate),
                "checker": $scope.jv.checker,
                "approvingOfficer": $scope.jv.approvingOfficer,
                "recommendingOfficer": $scope.jv.recommendingOfficer,
                "auditor": $scope.jv.auditor,
                "generalLedgerLines": $scope.journalEntries,
                "subLedgerLines": subLedgerLines,
                "amount": $scope.journalTotals.debit,
                "tempBatchId" : $scope.tempBatch !== undefined ? $scope.tempBatch.tempBatchId : 0
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
                    window.location = '/#/journal-voucher';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/journal-voucher/' + data.modelId + '/detail';
                    toastr.success('JV successfully saved!');
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
    }]
);

jvApp.controller('jvDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'jvFactory', 'ledgerFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, jvFactory, ledgerFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.doneLoading = false;
            $scope.slLoaded = false;
            $scope.cSls = {};
            $scope.selectedAction = {};

            $scope.jvId = $stateParams.jvId;
            var hasValidId = !($stateParams.jvId === undefined);

            var loadJv = function() {
                jvFactory.getJv($scope.jvId).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/journal-voucher';
                    } else {
                        try {
                            $scope.jv = data;
                            $scope.statusLabel = documentStatusUtil.classType( $scope.jv.documentStatus.status);

                            ledgerFactory.getGLEntries($scope.jv.transId).success(function (d) {
                                // load actions
                                workflowFactory.getActions($scope.jv.transId).success(function (k) {
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
                loadJv();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.loadSL = function(glAccount, index) {
                if($scope.cSls[index] !== undefined && $scope.cSls[index].length > 0) {
                    $scope.cSls[index] = [];
                } else {
                    var response = ledgerFactory.getSLEntriesSync2($scope.jv.transId, glAccount.accountId);
                    $scope.cSls[index] = response.data === undefined ? [] : response.data;
                }
            }

            $scope.setSelectedAction = function(action) {
                $scope.selectedAction = action;
                if ($scope.selectedAction === undefined) return;

                var $postData = {
                    'documentId' : $scope.jvId,
                    'transId' : $scope.jv.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/jv/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/journal-voucher';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('JV successfully processed!');
                        loadJv();
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
                    $scope.url = '/journal-voucher/export/' + $scope.jvId+'?token='+token+'&type='+type;
                    var newTab = window.open($scope.url, '_blank');
                    newTab.focus();
                });
            }

        }]
);