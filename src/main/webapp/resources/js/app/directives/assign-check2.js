(function () {
    var app;
    var app = angular.module('cmnAssignCheckApp', []);

    app.directive('assignCheck', ['$timeout', 'ledgerFactory', function ($timeout, ledgerFactory) {
        return {
            scope : {
                journalEntries : '=',
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

                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            $scope.save = function(code, seg, newCh, oldCh) {
                                if (newCh != oldCh) {
                                    $scope.handler()(code, seg, newCh);
                                }
                            }

                            $scope.saveAndPrint = function(code, seg, ch) {
                                $scope.handler()(code, seg, ch, true);
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
