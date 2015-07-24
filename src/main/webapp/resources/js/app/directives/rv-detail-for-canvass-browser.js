(function () {
    var app;
    var app = angular.module('cmnRvDetailForCanvassBrowserApp', ['rvDetailFactory']);

    app.directive('rvDetailForCanvassBrowser', ['$timeout', 'rvDetailFactory', function ($timeout, rvDetailFactory) {
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
                        templateUrl: '/common/rv-detail-for-canvass-browser',
                        controller: function($scope, $modalInstance) {
                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            // expose selected item to the outside world :)
                            $scope.selectRvDetail = function(account) {
                                $scope.handler()(account);
                                $modalInstance.close();
                            }
                        }
                    });
                };
            },
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    if (angular.isDefined(scope.rvDetails) && scope.rvDetails.length > 0)  {  // cache
                        scope.open();
                    } else {
                        scope.$apply(function () {
                            rvDetailFactory.rvDetailsByStatus(DOC_STAT_FOR_CANVASSING).success(function (data) {
                                scope.open();
                                scope.rvDetails = data;
                            });
                        });
                    }
                });
            }
        };
    }]);
}).call(this);
