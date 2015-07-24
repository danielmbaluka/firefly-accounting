var poApp = angular.module('po', [
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'utilService',
    'purchaseOrderFactory',
    'rvDetailFactory',
    'poDetailFactory',
    'cmnSLEntityBrowserApp',
    'cmnRvDetailForPoBrowserApp',
    'workflowFactory'
]);

poApp.controller('poListCtrl', ['$scope', '$http', 'purchaseOrderFactory', 'documentStatusUtil',
    function($scope,  $http, purchaseOrderFactory, documentStatusUtil) {
        $scope.doneLoading = false;

        purchaseOrderFactory.getPurchaseOrderList()
            .success(function (data) {
                $scope.purchaseOrderList = data;
                $scope.doneLoading = true;
            })
            .error(function (error) {
                toastr.error('Failed to load Purchase Orders!');
                $scope.doneLoading = true;
            });
    }]);

poApp.controller('addEditPoCtrl', ['$scope', '$stateParams', '$http', 'errorToElementBinder', 'csrf',
    'voucherUtil', 'poDetailFactory', 'purchaseOrderFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, voucherUtil, poDetailFactory, purchaseOrderFactory) {
        $scope.signatories = [1];
        $scope.podItems = [];
        $scope.po = {};
        $scope.totalAmount = 0;

        $scope.rvd_selection_handler = function(rvd){
            var add = true;
            angular.forEach($scope.podItems, function(value) {  // avoid duplicates here!
                if (rvd.id == value.rvDetailId) {
                    add = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (add) {
                var podItems = {
                    "rvDetailId": rvd.id,
                    "itemCode": rvd.itemCode,
                    "unitCode": rvd.unitCode,
                    "itemDescription": rvd.itemDescription,
                    "quantity" : rvd.quantity - rvd.poQuantity,
                    "rvdQuantity" : rvd.quantity - rvd.poQuantity,
                    "vat" : 0.00,
                    "discount" : 0.00,
                    "itemAmount" : 0.00,
                    "unitPrice" : 0.00
                }  // default qty
                $scope.podItems.push(podItems);
                $scope.totalAmount = voucherUtil.calculateTotalAmount($scope.podItems);
                $scope.rvdQuantity = rvd.quantity;
            }
        }

        $scope.supplier_selection_handler = function(entity){
            $scope.po.vendor = entity;
        }

        $scope.prep_selection_handler = function(entity){
            $scope.po.createdBy = entity;
        }

        $scope.note_selection_handler = function(entity){
            $scope.po.notedBy = entity;
        }

        $scope.app_selection_handler = function(entity){
            $scope.po.approvedBy = entity;
        }

        $scope.title = 'Create Purchase Order';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.po.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/purchase-order/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Purchase Order';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            purchaseOrderFactory.getPurchaseOrder($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/requisition-voucher';
                } else {
                    try{
                        $scope.po = data;
                        $scope.po.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.totalAmount = data.amount;
                        $scope.showForm = true;

                        poDetailFactory.getPurchaseOrderDetails(data.id).success(function (data) {
                            $scope.podItems = data;
                        }).error(function (error) {
                            toastr.warning('Purchase Order Items not loaded!');
                        });
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Purchase Order not found!');
                    window.location.hash = '#/purchase-order';
                });

            resourceURI = '/purchase-order/update';
        }

        if ($scope.podItems.length > 0) {
            $scope.totalAmount = voucherUtil.calculateTotalAmount($scope.podItems);
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.po.voucherDate || $scope.po.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.po.vendor) {
                $scope.errors['err_vendor'] =  ['Please select Supplier.'];
            }
            if (undefined == $scope.po.createdBy) {
                $scope.errors['err_prepBy'] =  ['Please select preparing officer.'];
            }
            if (undefined == $scope.po.notedBy) {
                $scope.errors['err_notedBy'] =  ['Please select noting officer.'];
            }
            if (undefined == $scope.po.approvedBy) {
                $scope.errors['err_approvedBy'] =  ['Please select approving officer.'];
            }
            if ($scope.podItems.length == 0) {
                $scope.errors['err_podItems'] =  ['Please add PO items.'];
            }

            $postData = {
                "id": $scope.po.id,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.po.voucherDate),
                "vendor": $scope.po.vendor,
                "approvingOfficer": $scope.po.approvedBy,
                "notedBy": $scope.po.notedBy,
                "amount": $scope.totalAmount,
                "poDetails": $scope.podItems
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
                    window.location = '/#/purchase-order';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found1.');
                } else {
                    window.location.hash = '/#/purchase-order' + data.modelId + '/detail';
                    toastr.success('Purchase Order successfully saved!');
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
            if ( $scope.podItems.length > 0) {
                $scope.podItems.splice(index, 1);
                $scope.totalAmount = voucherUtil.calculateTotalAmount($scope.podItems);
            }
        }

        $scope.rvd_selection_handler2 = function(rvd){
            var add = true;
            angular.forEach($scope.podItems, function(value) {  // avoid duplicates here!
                if (rvd.id == value.rvDetailId) {
                    add = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (add) {
                var podItems = {
                    "rvDetailId": rvd.id,
                    "itemCode": rvd.itemCode,
                    "unitCode": rvd.unitCode,
                    "itemDescription": rvd.itemDescription,
                    "quantity" : rvd.quantity - rvd.poQuantity,
                    "rvdQuantity" : rvd.quantity - rvd.poQuantity,
                    "vat" : 0.00,
                    "discount" : 0.00,
                    "itemAmount" : 0.00,
                    "unitPrice" : 0.00
                }  // default qty
                $scope.podItems[$scope.rvdIdx] = podItems;
                $scope.totalAmount = voucherUtil.calculateTotalAmount($scope.podItems);
            }
        }

        $scope.test = function(index){
            $scope.rvdIdx = index;
        }

        $scope.updateTotal = function(qty) {
            if (isNaN(qty)) {
                $scope.totalAmount = 0;
                toastr.clear();
                toastr.warning("Entered amount is invalid!");
            } else {
                $scope.totalAmount = voucherUtil.calculateTotalAmount($scope.podItems);
            }
        }

        $scope.updateAmount = function(qty, index) {
            if (isNaN(qty)) {
                $scope.podItems[index].itemAmount = 0;
                toastr.clear();
                toastr.warning("Entered quantity is invalid!" +
                    "<br/>Minimum quantity is 1!" +
                    "<br/>Maximum quantity is "+$scope.podItems[index].rvdQuantity+"!");
            } else {
                $scope.podItems[index].itemAmount = voucherUtil.calculateItemAmount(qty, $scope.podItems[index].unitPrice);
                $scope.totalAmount = voucherUtil.calculateTotalAmount($scope.podItems);
            }
        }
    }]);

poApp.controller('poDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'purchaseOrderFactory', 'poDetailFactory', 'voucherUtil', 'workflowFactory', 'documentStatusUtil',
        function($scope, routeUtil, $state, $stateParams, $http, purchaseOrderFactory, poDetailFactory, voucherUtil, workflowFactory, documentStatusUtil) {
            $scope.podItems = [];
            $scope.selectedAction = {};

            $scope.id = $stateParams.id;
            var hasValidId = !($stateParams.id === undefined);

            var loadPo = function() {
                purchaseOrderFactory.getPurchaseOrder($scope.id).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/purchase-order';
                    } else {
                        try{
                            $scope.po = data;
                            $scope.showForm = true;

                            poDetailFactory.getPurchaseOrderDetails($scope.po.id).success(function (data) {
                                // load actions
                                $scope.podItems = data;
                                workflowFactory.getActions($scope.po.transId).success(function (k) {
                                    $scope.actions = k;
                                }).error(function (error) {
                                    toastr.warning('Actions not loaded!');
                                });
                            }).error(function (error) {
                                alert('asd'+$scope.po.id)
                                toastr.warning('Items not loaded!');
                            });
                        }catch (e) {}
                    }
                })
                    .error(function (error) {
                        toastr.warning('PO not found!');
                    });
            }
            if(hasValidId) {
                loadPo();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.setSelectedAction = function(action) {
                $scope.selectedAction = action;
                if ($scope.selectedAction === undefined) return;

                var $postData = {
                    'documentId' : $scope.id,
                    'transId' : $scope.po.transId,
                    'workflowActionsDto' : $scope.selectedAction
                }

                var res = $http.post("/purchase-order/process", $postData);

                res.success(function(data) {
                    if (data.notAuthorized) {
                        toastr.error("Request unauthorized!");
                        window.location = '/#/purchase-order';
                    } else if (!data.success) {
                        toastr.error('Something went wrong!');
                    } else {
                        $scope.selectedAction = undefined;
                        toastr.clear();
                        toastr.success('PO successfully processed!');
                        loadPo();
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
                        $scope.url = '/purchase-order/export/' + $scope.id + '?token=' + token + '&type=' + type;
                        toastr.clear();
                });
            }

        }]
);