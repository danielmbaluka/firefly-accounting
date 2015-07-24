(function () {
    var app;
    var app = angular.module('cmnAssignCheckApp', []);

    app.directive('assignCheck', ['$timeout', 'ledgerFactory', function ($timeout, ledgerFactory) {
        return {
            scope : {
                check: "=",
                allowAssign: "=",
                account: "=",
                print: "=",
                handler: '&'
            },
            restrict: 'AE',
            controller: function($scope, $modal) {
                // create open() method
                // to open a modal
                $scope.open = function() {
                    $modal.open({
                        scope: $scope,
                        windowClass: 'assign-check-modal-window',
                        templateUrl: '/check-voucher/assign-check-modal',
                        controller: function($scope, $modalInstance) {
                            $scope.e = $.extend({}, $scope.check);

                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            $scope.cancel  = function() {
                                $scope.handler()(undefined);
                                $modalInstance.close();
                            };

                            $scope.checkEntered = function() {
                                $scope.handler()($scope.e);
                                $modalInstance.close();
                            }

                            $scope.saveAndPrint= function() {
                                $scope.handler()($scope.e, true);
                                $modalInstance.close();
                            }
                        }
                    });
                };


            },
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    if (scope.allowAssign) {
                        scope.open();
                    } else {
                        scope.$watch(scope.allowAssign, function() {
                            if (scope.allowAssign) {
                                scope.open();
                            }
                        });
                    }
                });
            }
        };
    }]);
}).call(this);
