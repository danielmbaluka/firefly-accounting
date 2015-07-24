(function () {
    var app;
    var app = angular.module('cmnRequisitionVoucherBrowserApp', ['requisitionVoucherFactory']);

    app.directive('requisitionVoucherBrowser', ['$timeout', 'requisitionVoucherFactory', function ($timeout, requisitionVoucherFactory) {
        return {
            scope : {
                handler: '&'
            },
            restrict: 'AE',
            controller: function($scope, $modal) {
                // create open() method
                // to open a modal
                $scope.open = function() {
                    $modal.open({
                        scope: $scope,
                        windowClass: 'sl-entity-browser-modal-window',
                        templateUrl: '/common/requisition-voucher-browser',
                        controller: function($scope, $modalInstance) {
                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            // expose selected item to the outside world :)
                            $scope.selectRequisitionVoucher = function(account) {
                                $scope.handler()(account);
                                $modalInstance.close();
                            }
                        }
                    });
                };
            },
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    if (angular.isDefined(scope.requisitionVouchers) && scope.requisitionVouchers.length > 0)  {  // cache
                        scope.open();
                    } else {
                        scope.$apply(function () {
                            requisitionVoucherFactory.getRequisitionVouchers().success(function (data) {
                                scope.open();
                                scope.requisitionVouchers = data;
                            });
                        });
                    }
                });
            }
        };
    }]);
}).call(this);
