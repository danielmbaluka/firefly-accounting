(function () {
    var app;
    var app = angular.module('cmnTempLedgerEntrySelectorModalApp', ['ledgerFactory']);

    app.directive('tempLedgerEntrySelector', ['$timeout', '$http', 'ledgerFactory',
        function ($timeout, $http, ledgerFactory) {

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
                            templateUrl: '/common/temp-ledger-entry-selector-modal',
                            controller: function($scope, $modalInstance) {

                                $scope.select = function(selectedBatch) {
                                    if (!$.isEmptyObject(selectedBatch)) {
                                        $scope.handler()(selectedBatch);
                                        $modalInstance.close();
                                    }
                                }

                                $scope.close = function() {
                                    $modalInstance.close();
                                }
                            }
                        });
                    };
                },
            link: function (scope, elem, attrs) {
                elem.bind('click', function () {
                    if (angular.isDefined(scope.data) && scope.data.length > 0)  {
                        scope.open();
                        return;
                    } else {
                        scope.$apply(function () {
                            ledgerFactory.getTempLedgerBatch().success(function (data) {
                                scope.data = data;
                                scope.open();
                            });
                        });
                    }
                });
            }
        };
    }]);
}).call(this);
