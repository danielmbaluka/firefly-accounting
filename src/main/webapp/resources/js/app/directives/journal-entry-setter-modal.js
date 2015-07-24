(function () {
    var app;
    var app = angular.module('cmnJournalEntrySetterModalApp', ['accountFactory',  'allocationFactorFactory', 'ledgerFactory']);

    app.directive('journalEntrySetter', ['$timeout', '$http', 'accountFactory', 'allocationFactorFactory', 'ledgerFactory',
        function ($timeout, $http, accountFactory, allocationFactorFactory, ledgerFactory) {

        return {
            scope : {
                transid: '=',
                voucherdate: '=',
                entities: '=',
                account: '=',
                sl: '=',
                distribution: '=',
                taxCodes: '=',
                handler: '&'
            },
            restrict: 'AE',
                controller: function($scope, $modal) {
                    // create open() method
                    // to open a modal
                    $scope.open = function() {
                        $modal.open({
                            scope: $scope,
                            windowClass: 'modal-100',
                            templateUrl: '/common/journal-entry-setter-modal',
                            controller: function($scope, $modalInstance) {
                                $scope.editMode = $scope.transid !== undefined && $scope.transid > 0;
                                $scope.segmentAllocation = [];
                                $scope.selectedAccount = {'amount':0};
                                $scope.slentries = $.extend([], $scope.sl);
                                $scope.slTotalAmount = 0;
                                $scope.autoAllocation = true;
                                $scope.isWTax = false;
                                $scope.selectedATC = {};
                                $scope.debit = true;
                                $scope.segmentAllocationTotalAmount = 0;

                                $scope.wtax = { 'percentage': 0, 'amount': 0, 'account': {} };
                                accountFactory.getWTaxAccount().success(function (data) {
                                    $scope.wtax['account'] = data;
                                });

                                var distributeAmountToSegments = function() {
                                    $scope.segmentAllocationTotalAmount = 0;
                                    angular.forEach($scope.segmentAllocation, function (entry, key) {
                                        if ($scope.autoAllocation) {
                                            var a = parseFloat(entry.percentage / 100).toFixed(2);
                                            a = parseFloat(a);
                                            var b = a * $scope.selectedAccount.amount;

                                            entry['amount'] = isNaN(b) ? 0 : b.toFixed(2);
                                            $scope.segmentAllocation[key] = entry;

                                            $scope.segmentAllocationTotalAmount += parseFloat(b.toFixed(2));
                                        } else {
                                            var b = entry['amount'];
                                            $scope.segmentAllocationTotalAmount += parseFloat(b);
                                        }
                                    });
                                }

                                $scope.accountAmountChanged = function() {
                                    var x = parseFloat($scope.selectedAccount.amount);
                                    if (isNaN(x)) {
                                        $scope.selectedAccount.amount = 0;
                                    } else { // when input has non numeric
                                        $scope.selectedAccount.amount = x.toFixed(2);
                                    }

                                    distributeAmountToSegments();
                                }
                                $scope.segmentAmountChanged = function(val, idx) {
                                    var x = parseFloat(val);
                                    if (isNaN(x)) {
                                        $scope.segmentAllocation[idx].amount = 0;
                                    } else { // when input has non numeric
                                        $scope.segmentAllocation[idx].amount = x.toFixed(2);
                                    }
                                    distributeAmountToSegments();
                                }
                                $scope.wtaxPercentageChanged = function(val) {
                                    var x = parseFloat(val);
                                    var vA = 0;
                                    var amt = 0;
                                    if (!isNaN(x)) {
                                        vA = parseFloat(x.toFixed(2));
                                        amt = $scope.selectedAccount.amount * (vA / 100);
                                    }
                                    $scope.wtax['percentage'] = vA;
                                    $scope.wtax['amount'] = amt;

                                    $scope.selectedAccount.amount = $scope.selectedAccount.amount - amt;
                                    $scope.accountAmountChanged();
                                }

                                $scope.toggleAutoAllocation = function() {
                                    $scope.autoAllocation = !$scope.autoAllocation;
                                    distributeAmountToSegments();
                                }

                                $scope.toggleWTax = function() {
                                    $scope.isWTax = !$scope.isWTax;
                                }

                                $scope.toggleNormalBalance = function() {
                                    $scope.debit = !$scope.debit;
                                }

                                $scope.loadAccountSegmentAllocation = function() {
                                    if ($scope.selectedAccount.id === undefined) return;

                                    if ($scope.editMode) {
                                        allocationFactorFactory.getAllocatedFactors($scope.selectedAccount.id, $scope.voucherdate).success(function (data) {
                                            $scope.segmentAllocation = data;
                                            if ($scope.segmentAllocation !== undefined && $scope.segmentAllocation.length > 0) checkIfAuto();

                                            // trigger distribution
                                            if ($scope.autoAllocation && $scope.segmentAllocation.length > 0) {
                                                distributeAmountToSegments();
                                            }
                                        }).error(function (error) {
                                            toastr.error('Failed to allocation factor.');
                                        });
                                    } else {
                                        allocationFactorFactory.getFactorSegments($scope.selectedAccount.id).success(function (data) {
                                            $scope.segmentAllocation = data;
                                            if ($scope.segmentAllocation !== undefined && $scope.segmentAllocation.length > 0) checkIfAuto();

                                            // trigger distribution
                                            if ($scope.autoAllocation && $scope.segmentAllocation.length > 0) {
                                                distributeAmountToSegments();
                                            }
                                        }).error(function (error) {
                                            toastr.error('Failed to allocation factor.');
                                        });
                                    }
                                };

                                var checkIfAuto = function() {
                                    // get accounts gl entries by transId and account id
                                    if ($scope.editMode) {
                                        if (($scope.distribution !== undefined && $scope.distribution.length > 0)) { // distribution already set
                                            var $postData = { "allocations": $scope.segmentAllocation, "distribution": $scope.distribution };
                                            var res = $http.post("/allocation-factor/test-auto-allocation", $postData);
                                            res.success(function (d) {
                                                $scope.autoAllocation = d.auto;
                                                $scope.segmentAllocation = d.allocations;
                                            });
                                        } else {
                                            ledgerFactory.getGLEntriesAsync($scope.transid, $scope.selectedAccount.id).success(function (data) {
                                                // calculate from server
                                                if (data !== undefined && data.length > 0) {
                                                    var $postData = { "allocations": $scope.segmentAllocation, "glentries": data };
                                                    var res = $http.post("/allocation-factor/test-auto-allocation", $postData);
                                                    res.success(function (d) {
                                                        $scope.autoAllocation = d.auto;
                                                        $scope.segmentAllocation = d.allocations;
                                                    });
                                                }
                                            });
                                        }
                                    } else if (($scope.distribution !== undefined && $scope.distribution.length > 0)) {
                                        var $postData = { "allocations": $scope.segmentAllocation, "distribution": $scope.distribution };
                                        var res = $http.post("/allocation-factor/test-auto-allocation", $postData);
                                        res.success(function (d) {
                                            $scope.autoAllocation = d.auto;
                                            $scope.segmentAllocation = d.allocations;
                                        });
                                    }
                                };

                                $scope.select = function(account) {
                                    $scope.selectedAccount = account;
                                    $scope.loadAccountSegmentAllocation();
                                }

                                // reading starts here
                                // preload normal balance
                                $scope.debit = ($scope.account.debit !== undefined && parseFloat($scope.account.debit) > 0);
                                // trigger account selection
                                var amt = parseFloat($scope.account.credit) == 0 ?  $scope.account.debit : $scope.account.credit;
                                $scope.select({'id': $scope.account.accountId, 'code': $scope.account.code, 'title': $scope.account.description, 'amount': amt});

                                // SL setters stuffs
                                $scope.newBlankRow = function() {
                                    if ($scope.slTotalAmount < $scope.selectedAccount.amount) {
                                        $scope.slentries.push({'account': $scope.selectedAccount});
                                    }
                                }

                                $scope.entity_selection_handler = function(object){
                                    // put back with updates
                                    $scope.slentries[$scope.entryIdx] =
                                    {
                                        "generalLedgerId" : $scope.selectedAccount.id,
                                        "segmentAccountId": $scope.selectedAccount.segmentAccountId,
                                        "accountId": $scope.selectedAccount.id,
                                        "accountNo": object.accountNo,
                                        'name': object.name,
                                        "amount" : $scope.slentries[$scope.entryIdx].amount
                                    };
                                }

                                $scope.setSelectedIdx = function(index){
                                    $scope.entryIdx = index;
                                }

                                $scope.updateSLTotal  = function(newAmount, oldAmount) {
                                    if (isNaN(newAmount)) {
                                        $scope.total = 0;
                                        toastr.clear();
                                        toastr.warning("Entered amount is invalid!");
                                    } else {
                                        if ($scope.slentries !== undefined && $scope.slentries.length > 0) {
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

                                            $scope.slTotalAmount = sum;
                                        } else {
                                            $scope.slTotalAmount = 0;
                                        }
                                    }
                                };

                                $scope.removeEntryRow = function(index) {
                                    if ($scope.slentries.length > 0) {
                                        $scope.slentries.splice(index, 1);
                                        $scope.updateSLTotal(0,0);
                                    }
                                }
                                // End: SL setters

                                $scope.noSelectedAccount = function() {
                                    return angular.equals({}, $scope.selectedAccount);
                                }

                                $scope.close  = function() {
                                    // set sl to undefined if slentries length is zero
                                    $scope.sl = ($scope.slentries !== undefined && $scope.slentries.length == 0) ? undefined : $scope.slentries;
                                    $modalInstance.close();
                                };

                                var exposeHandler = function() {
                                    if ($scope.selectedAccount.id === undefined) {
                                        toastr.clear();
                                        toastr.warning("No account selected");
                                        return;
                                    }

                                    var entries = [];
                                    var data = {
                                        'account': $scope.selectedAccount,
                                        'slentries': $scope.slentries,
                                        'debit': $scope.debit,
                                        'distribution': $scope.segmentAllocation
                                    };
                                    entries.push(data);


                                    if ($scope.isWTax && parseFloat($scope.wtax.percentage) > 0) {
                                        $scope.wtax.account['amount'] = $scope.wtax.amount;
                                        var wtax = {
                                            'account': $scope.wtax.account,
                                            'slentries': [],
                                            'debit': $scope.debit
                                        };

                                        $scope.wtax['atc'] = $scope.selectedATC;
                                        wtax['wTaxEntry'] = $scope.wtax;

                                        entries.push(wtax);
                                    }
                                    $scope.handler()(entries);
                                    $modalInstance.close();
                                }

                                // expose modal to the outside world :)
                                $scope.saveEntries = function() {
                                    if (parseFloat($scope.slTotalAmount) > 0 && parseFloat($scope.segmentAllocationTotalAmount) != parseFloat($scope.selectedAccount.amount)) {
                                        toastr.clear();
                                        toastr.warning("Total amount of all segments is not equal to account's amount.");
                                        return;
                                    }
                                    // check if has missing sl entity
                                    var hasMissingSL = false;
                                    var hasMissingAmount= false;
                                    angular.forEach($scope.slentries, function (entry, key) {

                                        if (!entry.hasOwnProperty('accountNo') || entry.accountNo === undefined) { // no sl entity
                                            hasMissingSL = true;
                                            return;
                                        }
                                        if (entry.hasOwnProperty('accountNo') && (entry.hasOwnProperty('amount') && (entry.amount === undefined || parseFloat(entry.amount) == 0))) {
                                            hasMissingAmount = true;
                                            return;
                                        }
                                    });

                                    if (parseFloat($scope.segmentAllocationTotalAmount) != parseFloat($scope.selectedAccount.amount)) {
                                        toastr.warning("Total amount of all segments is not equal to GL amount!");
                                        return;
                                    } else if (hasMissingSL && $scope.selectedAccount.amount == $scope.slTotalAmount) {
                                        toastr.warning("Missing SL entity!");
                                        return;
                                    } else if (hasMissingAmount) {
                                        toastr.warning("Input SL amount");
                                        return;
                                    } else if (hasMissingSL && !hasMissingAmount) {
                                        toastr.warning("SL entry line is empty");
                                        return;
                                    } if ($scope.slTotalAmount > 0 && $scope.selectedAccount.amount != $scope.slTotalAmount) {
                                        toastr.warning("Total SL amount is not equal to GL amount!");
                                        return;
                                    } else if ($scope.isWTax) {
                                        if ($scope.selectedATC === undefined || $.isEmptyObject($scope.selectedATC)) {
                                            toastr.warning("Select income payment.");
                                            return;
                                        } else if (parseFloat($scope.wtax.percentage) <= 0) {
                                            toastr.warning("Enter withholding tax percentage.");
                                            return;
                                        } else {
                                            toastr.clear();
                                            exposeHandler();
                                        }
                                    } else {
                                        toastr.clear();
                                        exposeHandler();
                                    }
                                }

                                $scope.setSelectedATC = function(s) {
                                    $scope.selectedATC = s;
                                }

                                $scope.updateSLTotal(0, 0);
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
