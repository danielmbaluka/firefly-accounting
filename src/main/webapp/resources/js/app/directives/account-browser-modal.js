(function () {
    var app;
    var app = angular.module('cmnAccountBrowserModalApp', ['accountFactory', 'utilService']);

    app.directive('accountBrowserModal', ['$timeout', 'accountFactory', function ($timeout, accountFactory) {
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
                            templateUrl: '/common/account-browser-modal',
                            controller: function($scope, $modalInstance) {
                                $scope.close  = function() {
                                    $modalInstance.close();
                                };

                                // expose selected account to the outside world :)
                                $scope.select = function(entity) {
                                    $scope.handler()(entity);
                                    $modalInstance.close();
                                }
                            }
                        });
                    };


                },
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    if (angular.isDefined(scope.accounts) && scope.accounts.length > 0)  {
                        scope.open();
                        return;
                    } else {
                        scope.$apply(function () {
                            accountFactory.getAccountsWithSegment().success(function (data) {
                                scope.accounts = data;
                                scope.open();
                            });
                        });
                    }
                });
            }
        };
    }]);
}).call(this);
