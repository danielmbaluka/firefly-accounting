(function () {
    var app;
    var app = angular.module('cmnVoucherSlSetterApp', []);

    app.directive('voucherSlSetter', ['$timeout', 'ledgerFactory', function ($timeout, ledgerFactory) {
        return {
            scope : {
                slentries: "=",
                slloaded: "=",
                glaccount: "=",
                sltotalamount: "=",
                handler: '&'
            },
            restrict: 'AE',
            controller: function($scope, $modal) {
                // create open() method
                // to open a modal
                $scope.open = function() {
                    $modal.open({
                        scope: $scope,
                        windowClass: 'sl-setter-modal-window',
                        templateUrl: '/common/voucher-sl-setter',
                        controller: function($scope, $modalInstance) {

                            $scope.close  = function() {
                                $modalInstance.close();
                            };

                            $scope.updateTotal  = function(newAmount, oldAmount) {
                                if (isNaN(newAmount)) {
                                    $scope.total = 0;
                                    toastr.clear();
                                    toastr.warning("Entered amount is invalid!");
                                } else {
                                    if (typeof $scope.slentries !== 'undefined') {
                                        var sum = 0;
                                        angular.forEach($scope.slentries, function (entry, key) {
                                            try {
                                                var amount = parseFloat(entry['amount']);
                                                if (!isNaN(amount)) {
                                                    sum += amount;
                                                }
                                            } catch (e) {
                                                console.log(e)
                                            }
                                        });

                                        $scope.total = sum;
                                    }
                                }
                            };

                            $scope.newBlankRow = function() {
                                if ($scope.total < $scope.sltotalamount) {
                                    // 'account' is for checking only
                                    $scope.slentries.push({'account': $scope.glaccount});
                                }
                            }

                            $scope.setSelectedIdx = function(index){
                                $scope.entryIdx = index;
                            }

                            $scope.removeEntryRow = function(index) {
                                if ($scope.slentries.length > 0) {
                                    $scope.slentries.splice(index, 1);
                                    $scope.updateTotal(0,0);
                                }
                            }

                            $scope.entity_selection_handler = function(object){
                                // put back with updates
                                $scope.slentries[$scope.entryIdx] =
                                {
                                    "generalLedgerId" : $scope.glaccount.id,
                                    "segmentAccountId": $scope.glaccount.segmentAccountId,
                                    "accountNo": object.accountNo,
                                    'name': object.name,
                                    "amount" : $scope.slentries[$scope.entryIdx].amount
                                };
                            }

                            $scope.cancelEntries = function(){
                                $scope.slentries = $.extend([], $scope.originalSlEntries);
                                exposeHandler();
                            }

                            $scope.resetEntries = function(){
                                $scope.slentries = $.extend([], $scope.originalSlEntries);
                                $scope.updateTotal(0,0);
                            }

                            // expose Sl's to the outside world :)
                            $scope.saveEntries = function() {
                                // check if has missing sl entity
                                var hasMissingSL = false;
                                angular.forEach($scope.slentries, function (entry, key) {
                                    if (entry.hasOwnProperty('account')) { // no sl entity
                                        hasMissingSL = true;
                                        return;
                                    }
                                });

                                if (hasMissingSL && $scope.total == $scope.sltotalamount) {
                                    toastr.warning("Missing SL entity!");
                                    return;
                                } if ($scope.total != $scope.sltotalamount && $scope.total > 0){
                                    toastr.warning("Total SL amount is not equal to GL amount!");
                                } else {
                                    toastr.clear();
                                    exposeHandler();
                                }
                            }

                            var exposeHandler = function() {
                                $scope.handler()($scope.slentries);
                                $modalInstance.close();
                            }

                            $scope.updateTotal(0,0);    // initialize total
                        }
                    });
                };


            },
            link: function (scope, elem, attrs) {
                elem.bind('click', function () {

                    scope.$watch(scope.slloaded, function() {

                        if (scope.glaccount !== undefined && !$.isEmptyObject(scope.glaccount) && scope.glaccount.hasOwnProperty('segmentAccountId')) {

                            if (scope.slloaded) {
                                scope.originalSlEntries = $.extend([], scope.slentries);
                                scope.open();
                            }
                        }
                    });
                });
            }
        };
    }]);
}).call(this);
