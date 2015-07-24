var cnvsApp = angular.module('canvass', [
    'canvassFactory',
    'rvDetailFactory',
    'canvassDetailFactory',
    'jQueryFnWrapperService',
    'errorHandlerService',
    'cmnFormErrorApp',
    'cmnSLEntityBrowserApp',
    'cmnRvDetailForCanvassBrowserApp',
    'utilService'
]);

cnvsApp.controller('canvassListCtrl', ['$scope', '$http', 'canvassFactory', 'documentStatusUtil',
    function($scope,  $http, canvassFactory, documentStatusUtil) {
        $scope.doneLoading = false;

        canvassFactory.getCanvassList()
            .success(function (data) {
                $scope.canvassList = data;
                $scope.doneLoading = true;
            })
            .error(function (error) {
                toastr.error('Failed to load canvasses!');
                $scope.doneLoading = true;
            });
    }]);

cnvsApp.controller('addEditCnvsCtrl', ['$scope', '$stateParams', '$http',
    'errorToElementBinder', 'csrf', 'canvassDetailFactory', 'canvassFactory',
    function($scope, $stateParams, $http, errorToElementBinder, csrf, canvassDetailFactory, canvassFactory) {

        $scope.signatories = [1];
        $scope.canvassItems = [];
        $scope.cnvs = {};

        $scope.rvd_selection_handler = function(rvd){
            var add = true;
            angular.forEach($scope.canvassItems, function(value) {  // avoid duplicates here!
                if (rvd.id == value.rvDetailId) {
                    add = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (add) {
                var canvassItems = {
                    "rvDetailId": rvd.id,
                    "rvNumber": rvd.rvNumber,
                    "itemCode": rvd.itemCode,
                    "unitCode": rvd.unitCode,
                    "itemDescription": rvd.itemDescription,
                    "quantity" : rvd.quantity
                }  // default qty
                $scope.canvassItems.push(canvassItems);
            }
        }

        $scope.supplier_selection_handler = function(entity){
            $scope.cnvs.vendor = entity;
        }

        $scope.title = 'Create Canvass';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.cnvs.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date());

        var resourceURI = '/canvass-rv/create';

        if(!($stateParams.id === undefined)) {  // update mode
            $scope.title = 'Update Canvass';
            $scope.showForm = false;

            $scope.id = $stateParams.id;

            canvassFactory.getCanvass($scope.id).success(function (data) {
                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/requisition-voucher';
                } else {
                    try{
                        $scope.cnvs = data;
                        $scope.cnvs.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                        $scope.showForm = true;

                        canvassDetailFactory.getCanvassDetails(data.id).success(function (data) {
                            $scope.canvassItems = data;
                        }).error(function (error) {
                            toastr.warning('Canvass Items not loaded!');
                        });
                    }catch (e) {}
                }
            })
                .error(function (error) {
                    toastr.warning('Canvass not found!');
                    window.location.hash = '#/canvass-rv';
                });

            resourceURI = '/canvass-rv/update';
        }

        $scope.processForm = function() {
            if (!$scope.submit) return; // check if save button is clicked

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // validate here
            if (undefined == $scope.cnvs.voucherDate || $scope.cnvs.voucherDate == '') {
                $scope.errors['err_voucherDate'] =  ['Please enter voucher date.'];
            }
            if (undefined == $scope.cnvs.vendor) {
                $scope.errors['err_vendor'] =  ['Please choose vendor.'];
            }
            if ($scope.canvassItems.length == 0) {
                $scope.errors['err_canvassItems'] =  ['Please add canvass items.'];
            }

            $postData = {
                "id": $scope.cnvs.id,
                "voucherDate": $.datepicker.parseDate("mm/dd/yy",  $scope.cnvs.voucherDate),
                "vendor": $scope.cnvs.vendor,
                "approvingOfficer": $scope.cnvs.vendor,
                "canvassDetails": $scope.canvassItems
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
                    window.location = '/#/canvass-rv';
                } else if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    // flags
                    $scope.submitting = false;
                    $scope.submit = false;
                    toastr.warning('Error found1.');
                } else {
                    window.location.hash = '/#/canvass-rv' + data.modelId + '/detail';
                    toastr.success('Canvass successfully saved!');
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
            if ( $scope.canvassItems.length > 0) {
                $scope.canvassItems.splice(index, 1);
            }
        }

        $scope.rvd_selection_handler2 = function(rvd){
            var update = true;
            angular.forEach($scope.canvassItems, function(value) {  // avoid duplicates here!
                if (rvd.id == value.rvDetailId) {
                    update = false;
                    toastr.error('Duplicate Item!');
                    return;
                }
            });

            if (update) {
                var canvassItems = {
                    "rvDetailId": rvd.id,
                    "rvNumber": rvd.rvNumber,
                    "itemCode": rvd.itemCode,
                    "unitCode": rvd.unitCode,
                    "itemDescription": rvd.itemDescription,
                    "quantity" : rvd.quantity
                }  // default qty
                $scope.canvassItems[$scope.rvdIdx] = canvassItems;
            }
        }

        $scope.test = function(index){
            $scope.rvdIdx = index;
        }
    }]);

cnvsApp.controller('cnvsDetailsCtrl', ['$scope', 'routeUtil', '$state', '$stateParams', '$http', 'canvassFactory', 'canvassDetailFactory',
        function($scope, routeUtil, $state, $stateParams, $http, canvassFactory, canvassDetailFactory) {

            $scope.canvassItems = [];

            $scope.id = $stateParams.id;
            var hasValidId = !($stateParams.id === undefined);

            var loadCnvs = function() {
                canvassFactory.getCanvass($scope.id).success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/canvass-rv';
                    } else {
                        try{
                            $scope.canvass = data;
                            $scope.canvass.voucherDate = $.datepicker.formatDate('mm/dd/yy', new Date(data.voucherDate));
                            $scope.showForm = true;

                            canvassDetailFactory.getCanvassDetails($scope.canvass.id).success(function (data) {
                                $scope.canvassItems = data;
                            }).error(function (error) {
                                toastr.warning('Canvass Items not loaded!');
                            });
                        }catch (e) {}
                    }
                })
                    .error(function (error) {
                        toastr.warning('Canvass not found!');
                    });
            }
            if(hasValidId) {
                loadCnvs();
            }

            $scope.main = function() {
                routeUtil.gotoMain($state);
            }

            $scope.print = function(type) {

                toastr.options.extendedTimeOut = 0;
                toastr.options.timeOut = 0;
                toastr.info("Processing report...");

                $http.get('/download/token').success(function(response) {
                    // Store token
                    var token = response.message[0];

                    // Start download
                    $scope.url = '/canvass-rv/export/' + $scope.id + '?token=' + token + '&type=' + type;
                    toastr.clear();
                });
            }

        }]
);