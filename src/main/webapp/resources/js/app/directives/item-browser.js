(function () {
    var app;
    var app = angular.module('cmnItemBrowserApp', ['itemFactory']);

    app.directive('itemBrowser', ['$timeout', 'itemFactory', function ($timeout, itemFactory) {
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
                        templateUrl: '/common/item-browser',
                        controller: function($scope, $modalInstance) {
                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            // expose selected item to the outside world :)
                            $scope.selectItem = function(account) {
                                $scope.handler()(account);
                                $modalInstance.close();
                            }
                        }
                    });
                };
            },
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    if (angular.isDefined(scope.items) && scope.items.length > 0)  {  // cache
                        scope.open();
                    } else {
                        scope.$apply(function () {
                            itemFactory.getItems().success(function (data) {
                                scope.open();
                                scope.items = data;
                            });
                        });
                    }
                });
            }
        };
    }]);
}).call(this);
