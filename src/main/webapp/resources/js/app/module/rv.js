var rvApp = angular.module('rv', [
    'requisitionVoucherFactory',
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService',
    'cmnSLEntityBrowserApp',
    'cmnItemBrowserApp',
    'unitFactory',
    'rvDetailFactory',
    'workflowFactory'
]);

rvApp.controller('rvListCtrl', ['$scope', '$http', 'requisitionVoucherFactory', 'documentStatusUtil',
    function($scope,  $http, requisitionVoucherFactory, documentStatusUtil) {

        $scope.doneLoading = false;

        requisitionVoucherFactory.getRequisitionVouchers()
            .success(function (data) {
                $scope.requisitionVouchers = data;
                $scope.doneLoading = true;
            })
            .error(function (error) {
                toastr.error('Failed to load requisitions!');
                $scope.doneLoading = true;
            });

        $scope.getURL = function(type, rvId) {
            if (type == 1) {
                return '.editPO({id:requisitionVoucher.id})';
            } else if (type == 2) {
                return '.editIT({id:requisitionVoucher.id})';
            } else if (type == 3) {
                return '.editRep({id:requisitionVoucher.id})';
            } else if (type == 4) {
                return '.editLab({id:requisitionVoucher.id})';
            }
        }

        $scope.label = function(status) {
            return (documentStatusUtil.classType(status));
        }
    }]);

rvApp.controller('addEditRvForPOCtrl', ['$scope', '$stateParams', '$http',
    'errorToElementBinder', 'csrf', 'requisitionVoucherFactory', 'rvDetailFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, requisitionVoucherFactory, rvDetailFactory) {

        $scope.signatories = [1];
        $scope.poItems = [];
        $scope.rv = {};

        $scope.item_selection_handler = function(item){
            var add = true;
            angular.forEach($scope.poItems, function(value) {  // avoid duplicates here!
                if (item.id == value.itemId) {
                    add = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (add) {
                var rvItems = {
                    "itemId": item.id,
                    "itemCode": item.code,
                    "unitId": item.unit.id,
                    "unitCode": item.unit.code,
                    "itemDescription": item.description,
                    "quantity": 1
                }  // default qty
                item.quantity = 1;  // default qty
                $scope.poItems.push(rvItems);
            }
        }

        $scope.recapp_selection_handler = function(entity){
            $scope.rv.recAppBy = entity;
        }

        $scope.checker_selection_handler = function(entity){
            $scope.rv.checkedBy = entity;
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.rv.auditedBy = entity;
        }

        $scope.app_selection_handler = function(entity){
            $scope.rv.approvedBy = entity;
        }
        $scope.title = 'Create Requisition Voucher For PO';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.rv.deliveryDate =  $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/requisition-voucher/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Requisition Voucher For PO';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            requisitionVoucherFactory.getRequisitionVoucher($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/requisition-voucher';
                } else {
                    try{
                        $scope.rv = data;
                        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.rv.deliveryDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.deliveryDate));
                        $scope.showForm = true;

                        rvDetailFactory.getRvDetails($scope.rv.id).success(function (data) {
                            $scope.poItems = data;
                        }).error(function (error) {
                            toastr.warning('Requested Items not loaded!');
                        });
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('RV not found!');
                    window.location.hash = '#/requisition-voucher';
                });

            resourceURI = '/requisition-voucher/update';
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.rv.purpose || $.trim($scope.rv.purpose) == '') {
                $scope.errors['err_purpose'] =  ['Please enter purpose.'];
            }
            if (undefined == $scope.rv.voucherDate || $scope.rv.voucherDate == '') {
                $scope.errors['err_rvDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.rv.deliveryDate || $scope.rv.deliveryDate == '') {
                $scope.errors['err_deliveryDate'] =  ['Please enter delivery date.'];
            }
            if (undefined == $scope.rv.recAppBy) {
                $scope.errors['err_recAppOfficer'] =  ['Please select Rec. Approval Officer.'];
            }
            if (undefined == $scope.rv.checkedBy) {
                $scope.errors['err_checker'] =  ['Please select Checker.'];
            }
            if (undefined == $scope.rv.auditedBy) {
                $scope.errors['err_auditor'] =  ['Please select Auditor.'];
            }
            if (undefined == $scope.rv.approvedBy) {
                $scope.errors['err_appOfficer'] =  ['Please select Approving Officer.'];
            }
            if ($scope.poItems.length == 0) {
                $scope.errors['err_poItems'] =  ['Please add requisition items.'];
            } else if ($scope.poItems.quantity <= 0) {
                $scope.errors['err_poItems'] =  ['Quantity must be greater than zero.'];
            }

            $postData = {
                "id": $scope.rv.id,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.voucherDate),
                "deliveryDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.deliveryDate),
                "purpose": $scope.rv.purpose,
                "recAppBy": $scope.rv.recAppBy,
                "checkedBy": $scope.rv.checkedBy,
                "auditedBy": $scope.rv.auditedBy,
                "approvingOfficer": $scope.rv.approvedBy,
                "documentStatus": $scope.rv.documentStatus,
                "rvType": 1,
                "rvDetails": $scope.poItems
            };

            if (!$.isEmptyObject($scope.errors)) {
                toastr.warning('Error found2.');
                cleanOnErrors();
                return;
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    window.location = '/#/requisition-voucher';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found1.');
                } else {
                    window.location.hash = '#/requisition-voucher/' + data.modelId + '/detail';
                    toastr.success('RV successfully saved!');
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

        $scope.removeRow = function(index) {
            if ( $scope.poItems.length > 0) {
                $scope.poItems.splice(index, 1);
            }
        }

        $scope.item_selection_handler2 = function(item){
            var update = true;
            angular.forEach($scope.poItems, function(value) {  // avoid duplicates here!
                if (item.id == value.itemId) {
                    update = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (update) {
                item.quantity = 1;
                var rvItems = {
                    "itemId": item.id,
                    "itemCode": item.code,
                    "unitId": item.unit.id,
                    "unitCode": item.unit.code,
                    "itemDescription": item.description,
                    "quantity" : $scope.poItems[$scope.itemIdx].quantity
                }  // default qty
                $scope.poItems[$scope.itemIdx] = rvItems;
            }
        }

        $scope.test = function(index){
            $scope.itemIdx = index;
        }
    }]);

rvApp.controller('addEditRvForITCtrl', ['$scope', '$stateParams', '$http',
    'errorToElementBinder', 'csrf', 'requisitionVoucherFactory', 'rvDetailFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, requisitionVoucherFactory, rvDetailFactory) {

        $scope.signatories = [1];
        $scope.poItems = [];
        $scope.rv = {};

        $scope.item_selection_handler = function(item){
            var add = true;
            angular.forEach($scope.poItems, function(value) {  // avoid duplicates here!
                if (item.id == value.itemId) {
                    add = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (add) {
                var rvItems = {
                    "itemId": item.id,
                    "itemCode": item.code,
                    "unitId": item.unit.id,
                    "unitCode": item.unit.code,
                    "itemDescription": item.description,
                    "quantity": 1
                }  // default qty
                item.quantity = 1;  // default qty
                $scope.poItems.push(rvItems);
            }
        }

        $scope.rvItType =  {
            id : "1",
            name : ""
        };

        $scope.recapp_selection_handler = function(entity){
            $scope.rv.recAppBy = entity;
        }

        $scope.checker_selection_handler = function(entity){
            $scope.rv.checkedBy = entity;
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.rv.auditedBy = entity;
        }

        $scope.app_selection_handler = function(entity){
            $scope.rv.approvedBy = entity;
        }

        $scope.conform_selection_handler = function(entity){
            $scope.rv.conformedBy = entity;
        }
        $scope.title = 'Create Requisition Voucher For IT';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.rv.deliveryDate =  $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/requisition-voucher/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Requisition Voucher For IT';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            requisitionVoucherFactory.getRequisitionVoucher($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/requisition-voucher';
                } else {
                    try{
                        $scope.rv = data;
                        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.rv.deliveryDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.deliveryDate));
                        $scope.showForm = true;

                        rvDetailFactory.getRvDetails($scope.rv.id).success(function (data) {
                            $scope.poItems = data;
                        }).error(function (error) {
                            toastr.warning('Journal entries not loaded!');
                        });

                        $scope.rvItType =  {
                            id : data.rvItType == "1" || data.rvItType == "2" ? data.rvItType : "3",
                            name : data.rvItType == "1" || data.rvItType == "2" ? "" : data.rvItType
                        };
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('RV not found!');
                    window.location.hash = '#/requisition-voucher';
                });

            resourceURI = '/requisition-voucher/update';
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.rv.purpose || $.trim($scope.rv.purpose) == '') {
                $scope.errors['err_purpose'] =  ['Please enter purpose.'];
            }
            if (undefined == $scope.rv.voucherDate || $scope.rv.voucherDate == '') {
                $scope.errors['err_rvDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.rv.deliveryDate || $scope.rv.deliveryDate == '') {
                $scope.errors['err_deliveryDate'] =  ['Please enter delivery date.'];
            }
            if (undefined == $scope.rv.recAppBy) {
                $scope.errors['err_recAppOfficer'] =  ['Please select Rec. Approval Officer.'];
            }
            if (undefined == $scope.rv.checkedBy) {
                $scope.errors['err_checker'] =  ['Please select Checker.'];
            }
            if (undefined == $scope.rv.auditedBy) {
                $scope.errors['err_auditor'] =  ['Please select Auditor.'];
            }
            if (undefined == $scope.rv.approvedBy) {
                $scope.errors['err_appOfficer'] =  ['Please select Approving Officer.'];
            }
            if (undefined == $scope.rv.approvedBy) {
                $scope.errors['err_conOfficer'] =  ['Please select Conforming Officer.'];
            }
            if ($scope.poItems.length == 0) {
                $scope.errors['err_poItems'] =  ['Please add requisition items.'];
            } else if ($scope.poItems.quantity <= 0) {
                $scope.errors['err_poItems'] =  ['Quantity must be greater than zero.'];
            }

            $postData = {
                "id": $scope.rv.id,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.voucherDate),
                "deliveryDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.deliveryDate),
                "purpose": $scope.rv.purpose,
                "recAppBy": $scope.rv.recAppBy,
                "checkedBy": $scope.rv.checkedBy,
                "auditedBy": $scope.rv.auditedBy,
                "approvingOfficer": $scope.rv.approvedBy,
                "conformedBy": $scope.rv.conformedBy,
                "documentStatus": $scope.rv.documentStatus,
                "rvType": 2,
                "rvItType": $scope.rvItType.id == 3 ? $scope.rvItType.name : $scope.rvItType.id,
                "rvDetails": $scope.poItems
            };

            if (!$.isEmptyObject($scope.errors)) {
                toastr.warning('Error found2.');
                cleanOnErrors();
                return;
            }

            var res = $http.post(resourceURI, $postData);

            res.success(function(data) {

                if (data.notAuthorized) {
                    toastr.error(data.messages[0]);
                    window.location = '/#/requisition-voucher';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found1.');
                } else {
                    window.location.hash = '#/requisition-voucher/' + data.modelId + '/detail';
                    toastr.success('RV successfully saved!');
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

        $scope.removeRow = function(index) {
            if ( $scope.poItems.length > 0) {
                $scope.poItems.splice(index, 1);
            }
        }

        $scope.item_selection_handler2 = function(item){
            var update = true;
            angular.forEach($scope.poItems, function(value) {  // avoid duplicates here!
                if (item.id == value.itemId) {
                    update = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (update) {
                item.quantity = 1;
                var rvItems = {
                    "itemId": item.id,
                    "itemCode": item.code,
                    "unitId": item.unit.id,
                    "unitCode": item.unit.code,
                    "itemDescription": item.description,
                    "quantity" : $scope.poItems[$scope.itemIdx].quantity
                }  // default qty
                $scope.poItems[$scope.itemIdx] = rvItems;
            }
        }

        $scope.test = function(index){
            $scope.itemIdx = index;
        }

        $scope.nameChange = function(id) {
            if (id != "3") {
                $scope.rvItType.name = ""
            }
        }
    }]);

rvApp.controller('addEditRvForRepCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    'requisitionVoucherFactory', 'rvDetailFactory', 'unitFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, requisitionVoucherFactory, rvDetailFactory, unitFactory) {

        $scope.signatories = [1];
        $scope.poItems = [];
        $scope.rv = {};
        $scope.unit = {};
        $scope.item = {};

        $scope.units = [];
        unitFactory.getUnits()
            .success(function (data) {
                $scope.units = data;
            })
            .error(function (error) {
                toastr.error('Failed to load units!');
            });

        $scope.item_selection_handler = function(item){
            var add = true;
            angular.forEach($scope.poItems, function(value) {  // avoid duplicates here!
                if (item.id == value.itemId) {
                    add = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (add) {
                var rvItems = {
                    "itemId": item.id,
                    "itemCode": item.code,
                    "unitId": item.unit.id,
                    "unitCode": item.unit.code,
                    "itemDescription": item.description,
                    "quantity": 1
                }  // default qty
                item.quantity = 1;  // default qty
                $scope.poItems.push(rvItems);
            }
        }

        $scope.recapp_selection_handler = function(entity){
            $scope.rv.recAppBy = entity;
        }

        $scope.checker_selection_handler = function(entity){
            $scope.rv.checkedBy = entity;
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.rv.auditedBy = entity;
        }

        $scope.app_selection_handler = function(entity){
            $scope.rv.approvedBy = entity;
        }
        $scope.title = 'Create Requisition Voucher For REPAIR';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/requisition-voucher/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Requisition Voucher For REPAIR';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            requisitionVoucherFactory.getRequisitionVoucher($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/requisition-voucher';
                } else {
                    try{
                        $scope.rv = data;
                        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.showForm = true;

                        rvDetailFactory.getRvDetails($scope.rv.id).success(function (data) {
                            $scope.poItems = data;
                            $scope.unit = data.unit();
                        }).error(function (error) {
                            toastr.warning('Items not loaded!');
                        });
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('RV not found!');
                    window.location.hash = '#/requisition-voucher';
                });

            resourceURI = '/requisition-voucher/update';
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();
            $scope.poItems.unit = $scope.unit;

            // validate here
            if (undefined == $scope.rv.purpose || $.trim($scope.rv.purpose) == '') {
                $scope.errors['err_purpose'] =  ['Please enter purpose.'];
            }
            if (undefined == $scope.rv.voucherDate || $scope.rv.voucherDate == '') {
                $scope.errors['err_rvDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.rv.recAppBy) {
                $scope.errors['err_recAppOfficer'] =  ['Please select Rec. Approval Officer.'];
            }
            if (undefined == $scope.rv.checkedBy) {
                $scope.errors['err_checker'] =  ['Please select Checker.'];
            }
            if (undefined == $scope.rv.auditedBy) {
                $scope.errors['err_auditor'] =  ['Please select Auditor.'];
            }
            if (undefined == $scope.rv.approvedBy) {
                $scope.errors['err_appOfficer'] =  ['Please select Approving Officer.'];
            }
            if ($scope.poItems.length == 0) {
                $scope.errors['err_poItems'] =  ['Please add requisition items.'];
            } else if ($scope.poItems.quantity <= 0) {
                $scope.errors['err_poItems'] =  ['Quantity must be greater than zero.'];
            }

            $postData = {
                "id": $scope.rv.id,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.voucherDate),
                "deliveryDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.voucherDate),
                "purpose": $scope.rv.purpose,
                "recAppBy": $scope.rv.recAppBy,
                "checkedBy": $scope.rv.checkedBy,
                "auditedBy": $scope.rv.auditedBy,
                "approvingOfficer": $scope.rv.approvedBy,
                "documentStatus": $scope.rv.documentStatus,
                "rvType": 3,
                "rvDetails": $scope.poItems
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
                    window.location = '/#/requisition-voucher';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/requisition-voucher/' + data.modelId + '/detail';
                    toastr.success('RV successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!'+$scope.poItems.unit.unitId + $scope.unit.unitId);
                cleanOnErrors();
            });
        }

        var cleanOnErrors = function() {
            $scope.save = 'Save';
            // flags
            $scope.submitting = false;
            $scope.submit = false;
        }

        $scope.newLabor = function(item, unit) {
            if((undefined != item.joDescription || $.trim(item.joDescription) != '')
                && undefined != item.quantity && undefined != unit.code ) {
                var rvItems = {
                    "itemId": 0,
                    "unitId": unit.id,
                    "unitCode": unit.code,
                    "quantity": item.quantity,
                    "joDescription": item.joDescription
                }  // default qty
                $scope.poItems.push(rvItems);
                item.joDescription = undefined;
                item.quantity = undefined;
                delete $scope.unit;
            }
        }

        $scope.item_selection_handler2 = function(item) {
            var update = true;
            angular.forEach($scope.poItems, function (value) {  // avoid duplicates here!
                if (item.id == value.itemId) {
                    update = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (update) {
                item.quantity = 1;
                var rvItems = {
                    "itemId": item.id,
                    "itemCode": item.code,
                    "unitId": item.unit.id,
                    "unitCode": item.unit.code,
                    "itemDescription": item.description,
                    "quantity": $scope.poItems[$scope.itemIdx].quantity
                }  // default qty
                $scope.poItems[$scope.itemIdx] = rvItems;
            }
        }

        $scope.removeRow = function(index) {
            if ( $scope.poItems.length > 0) {
                $scope.poItems.splice(index, 1);
            }
        }

        $scope.test = function(index){
            $scope.itemIdx = index;
        }
    }]);

rvApp.controller('addEditRvForLabCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    'requisitionVoucherFactory', 'rvDetailFactory', 'unitFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, requisitionVoucherFactory, rvDetailFactory, unitFactory) {

        $scope.signatories = [1];
        $scope.poItems = [];
        $scope.rv = {};
        $scope.unit = {};
        $scope.item = {};

        $scope.units = [];
        unitFactory.getUnits()
            .success(function (data) {
                $scope.units = data;
            })
            .error(function (error) {
                toastr.error('Failed to load units!');
            });

        $scope.emp_selection_handler = function(entity){
            $scope.rv.employee = entity;
        }

        $scope.recapp_selection_handler = function(entity){
            $scope.rv.recAppBy = entity;
        }

        $scope.checker_selection_handler = function(entity){
            $scope.rv.checkedBy = entity;
        }

        $scope.auditor_selection_handler = function(entity){
            $scope.rv.auditedBy = entity;
        }

        $scope.app_selection_handler = function(entity){
            $scope.rv.approvedBy = entity;
        }
        $scope.title = 'Create Requisition Voucher For LABOR';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.rv.durationStart = $.datepicker.formatDate('mm/dd/yy', new Date());
        $scope.rv.durationEnd = $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/requisition-voucher/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Requisition Voucher For LABOR';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            requisitionVoucherFactory.getRequisitionVoucher($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/requisition-voucher';
                } else {
                    try{
                        $scope.rv = data;
                        $scope.rv.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.rv.durationStart = $.datepicker.formatDate('mm/dd/yy', new Date(data.durationStart));
                        $scope.rv.durationEnd = $.datepicker.formatDate('mm/dd/yy', new Date(data.durationStart));
                        $scope.showForm = true;

                        rvDetailFactory.getRvDetails($scope.rv.id).success(function (data) {
                            $scope.poItems = data;
                            $scope.unit = data.unit();
                        }).error(function (error) {
                            toastr.warning('Items not loaded!');
                        });
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('RV not found!');
                    window.location.hash = '#/requisition-voucher';
                });

            resourceURI = '/requisition-voucher/update';
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();
            $scope.poItems.unit = $scope.unit;

            // validate here
            if (undefined == $scope.rv.purpose || $.trim($scope.rv.purpose) == '') {
                $scope.errors['err_purpose'] =  ['Please enter purpose.'];
            }
            if ((undefined == $scope.rv.durationStart || undefined == $scope.rv.durationStart) ||
                (undefined == $scope.rv.durationEnd || undefined == $scope.rv.durationEnd) ||
                $.trim($scope.rv.durationStart) == '' || $.trim($scope.rv.durationEnd) == '') {
                $scope.errors['err_duration'] =  ['Please enter duration.'];
            }
            if (undefined == $scope.rv.voucherDate || $scope.rv.voucherDate == '') {
                $scope.errors['err_rvDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.rv.employee) {
                $scope.errors['err_employee'] =  ['Please select Employee.'];
            }
            if (undefined == $scope.rv.recAppBy) {
                $scope.errors['err_recAppOfficer'] =  ['Please select Rec. Approval Officer.'];
            }
            if (undefined == $scope.rv.checkedBy) {
                $scope.errors['err_checker'] =  ['Please select Checker.'];
            }
            if (undefined == $scope.rv.auditedBy) {
                $scope.errors['err_auditor'] =  ['Please select Auditor.'];
            }
            if (undefined == $scope.rv.approvedBy) {
                $scope.errors['err_appOfficer'] =  ['Please select Approving Officer.'];
            }
            if ($scope.poItems.length == 0) {
                $scope.errors['err_poItems'] =  ['Please add requisition items.'];
            } else if ($scope.poItems.quantity <= 0) {
                $scope.errors['err_poItems'] =  ['Quantity must be greater than zero.'];
            }

            $postData = {
                "id": $scope.rv.id,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.voucherDate),
                "deliveryDate": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.voucherDate),
                "purpose": $scope.rv.purpose,
                "employee": $scope.rv.employee,
                "recAppBy": $scope.rv.recAppBy,
                "checkedBy": $scope.rv.checkedBy,
                "auditedBy": $scope.rv.auditedBy,
                "approvingOfficer": $scope.rv.approvedBy,
                "employee": $scope.rv.employee,
                "durationStart": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.durationStart),
                "durationEnd": $.datepicker.parseDate("mm/dd/yy",  $scope.rv.durationEnd),
                "documentStatus": $scope.rv.documentStatus,
                "rvType": 4,
                "rvDetails": $scope.poItems
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
                    window.location = '/#/requisition-voucher';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/requisition-voucher/' + data.modelId + '/detail';
                    toastr.success('RV successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!'+$scope.poItems.unit.unitId + $scope.unit.unitId);
                cleanOnErrors();
            });
        }

        var cleanOnErrors = function() {
            $scope.save = 'Save';
            // flags
            $scope.submitting = false;
            $scope.submit = false;
        }

        $scope.newLabor = function(item, unit) {
            if((undefined != item.joDescription || $.trim(item.joDescription) != '')
                && undefined != item.quantity && undefined != unit.code ) {
                var rvItems = {
                    "itemId": 0,
                    "unitId": unit.id,
                    "unitCode": unit.code,
                    "quantity": item.quantity,
                    "joDescription": item.joDescription
                }  // default qty
                $scope.poItems.push(rvItems);
                item.joDescription = undefined;
                item.quantity = undefined;
                delete $scope.unit;
            }
        }

        $scope.removeRow = function(index) {
            if ( $scope.poItems.length > 0) {
                $scope.poItems.splice(index, 1);
            }
        }

        $scope.test = function(index){
            $scope.itemIdx = index;
        }
    }]);

rvApp.controller('rvDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'requisitionVoucherFactory', 'rvDetailFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, requisitionVoucherFactory, rvDetailFactory, voucherUtil, workflowFactory, documentStatusUtil) {

            $scope.poItems = [];
            $scope.selectedAction = {};

            $scope.id = $stateParams.id;
            var hasValidId = !($stateParams.id === undefined);

            var loadRv = function() {
                requisitionVoucherFactory.getRequisitionVoucher($scope.id).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/requisition-voucher';
                    } else {
                        try{
                            $scope.rv = data;
                            $scope.showForm = true;

                            rvDetailFactory.getRvDetails($scope.rv.id).success(function (data) {
                                // load actions
                                workflowFactory.getActions($scope.rv.transId).success(function (k) {
                                    $scope.actions = k;
                                }).error(function (error) {
                                    toastr.warning('Actions not loaded!');
                                });
                                $scope.poItems = data;
                                $scope.unit = data.unit;
                            }).error(function (error) {
                                toastr.warning('Items not loaded!');
                            });
                        }catch (e) {}
                    }
                })
                    .error(function (error) {
                        toastr.warning('RV not found!');
                    });
            }
            if(hasValidId) {
                loadRv();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.setSelectedAction = function(action) {
                $scope.selectedAction = action;
                if ($scope.selectedAction === undefined) return;

                var $postData = {
                    'documentId' : $scope.id,
                    'transId' : $scope.rv.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/requisition-voucher/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/requisition-voucher';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('RV successfully processed!');
                        loadRv();
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
                    if($scope.rv.rvType == 'For REP') {
                        $scope.url = '/requisition-voucher/export2/' + $scope.id + '?token=' + token + '&type=' + type;
                        toastr.clear();
                    } else if($scope.rv.rvType == 'For LAB') {
                        $scope.url = '/requisition-voucher/export3/' + $scope.id + '?token=' + token + '&type=' + type;
                        toastr.clear();
                    } else if($scope.rv.rvType == 'For IT') {
                        $scope.url = '/requisition-voucher/export1/' + $scope.id + '?token=' + token + '&type=' + type;
                        toastr.clear();
                    } else {
                        $scope.url = '/requisition-voucher/export/' + $scope.id + '?token=' + token + '&type=' + type;
                        toastr.clear();
                    }
                });
            }

        }]
);