(function () {
    var app;
    var app = angular.module('cmnAccountBrowserWithSegmentApp', ['businessSegmentFactory', 'accountFactory']);

    app.directive('accountBrowserS', ['$timeout', 'businessSegmentFactory', 'accountFactory', function ($timeout, businessSegmentFactory, accountFactory) {
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
                        templateUrl: '/common/account-browser-with-segment',
                        controller: function($scope, $modalInstance) {
                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            // expose selected account to the outside world :)
                            $scope.selectAccount = function(account) {
                                $scope.handler()(account);
                                $modalInstance.close();
                            }
                        }
                    });
                };


            },
            link: function (scope, elem, attrs) {
                scope.segments = [];
                var segmentIds = [];

                var loadAccounts = function () {
                    accountFactory.getAccountsBySegment(segmentIds).success(function (data) { 
                        scope.accounts = data;
                    });
                }

                var collectionSegmentIds = function () {
                    angular.forEach(scope.segments, function (segment, key) {
                        if (segment.selected) {
                            segmentIds.push(segment.id);
                        }
                    });
                }

                elem.bind('click', function () {
                    if (angular.isDefined(scope.accounts) && scope.accounts.length > 0)  { // cache
                        scope.open();
                        return;
                    }
                    scope.$apply(function () {

                        if (scope.segments.length > 0) {
                            scope.open();
                            collectionSegmentIds();
                            loadAccounts();
                        } else {
                            scope.open();
                            businessSegmentFactory.getSegments().success(function (data) {

                                if (data.length > 0) {
                                    angular.forEach(data, function (segment, key) {
                                        segment['selected'] = true;
                                        scope.segments.push(segment);
                                    });
                                    collectionSegmentIds();
                                    loadAccounts();
                                }
                            });
                        }
                    });
                });

                scope.toggleSegment = function(idx, segment) {
                    if (segment.selected) {
                        segmentIds.push(segment.id);
                    } else {
                        var index = segmentIds.indexOf(segment.id);
                        if (index >= 0) { // exists
                            if (segmentIds.length == 1) {
                                segment.selected = true;
                                toastr.warning("Must select at least 1 segment!");
                            } else {
                                segmentIds.splice(index, 1);
                            }
                        }
                    }
                    loadAccounts();
                };
            }
        };
    }]);
}).call(this);
