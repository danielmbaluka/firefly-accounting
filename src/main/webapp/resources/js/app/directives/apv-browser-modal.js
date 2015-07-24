(function () {
    var app;
    var app = angular.module('cmnApvBrowserApp', ['apvFactory']);

    app.directive('apvBrowser', ['$timeout', 'apvFactory', function ($timeout, apvFactory) {
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
                        windowClass: 'apv-browser-modal',
                        templateUrl: '/common/apv-browser-modal',
                        controller: function($scope, $modalInstance, apvFactory) {

                            $scope.doneLoading = false;

                            apvFactory.getList(DOC_STAT_APPROVED).success(function (data) {
                                $scope.vouchers = data;
                                $scope.doneLoading = true;
                            }).error(function (error) {
                                toastr.error('Failed to load vouchers.');
                                $scope.doneLoading = true;
                            });

                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            $scope.cancel  = function() {
                                $modalInstance.close();
                            };

                            $scope.select = function(e) {
                                $scope.handler()(e);
                                $modalInstance.close();
                            }
                        }
                    });
                };


            },
            link: function (scope, elem, attrs) {

                elem.bind('click', function () {
                    scope.open();
                });
            }
        };
    }]);
}).call(this);
