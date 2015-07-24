(function () {
    var app;
    var app = angular.module('cmnJournalEntriesApp', ['accountFactory']);

    app.directive('journalEntries', ['$timeout', 'ledgerFactory', 'voucherUtil',
        function ($timeout, ledgerFactory, voucherUtil) {
        return {
            scope : {
                transId : '=',
                voucherId : '=',
                journalEntries : '=',
                subLedgerLines : '=',
                entities : '=',
                voucherDate : '=',
                taxCodes : '='
            },
            restrict: 'E',
            templateUrl: "/common/journal-entries",
            link: function (scope, elem, attrs) {

                scope.journalTotals = 0;
                scope.selectedGlAccount = {}; // selected gl account
                scope.glSlEntries = [];
                scope.editMode = false;

                var loadGL = function() {
                    ledgerFactory.getGLEntries(scope.transId).success(function (data) {
                        scope.journalEntries = data;

                        angular.forEach(scope.journalEntries, function (entry, key) {
                            ledgerFactory.getSLEntriesAsync(scope.transId, entry.accountId).success(function (data) {
                                scope.subLedgerLines[key] = data;
                            });
                        });

                        scope.updateTotal(0,0);
                    }).error(function (error) {
                        toastr.warning('Journal entries not loaded!');
                    });
                }

                scope.$watch('transId', function(value){
                    if(value !== undefined){
                        loadGL(); // for edit mode
                    }
                });

                scope.$watch('voucherId', function(value){
                    if(value !== undefined){
                        scope.editMode = scope.voucherId !== undefined;
                    }
                });

                scope.newBlankRow = function() {
                    scope.journalEntries.push({});
                }

                scope.removeJournalRow = function(index, account) {
                    try {
                        if (scope.journalEntries.length > 0) {
                            scope.journalEntries.splice(index, 1);

                            scope.subLedgerLines.splice(index, 1);
                            scope.updateTotal(0,0);
                        }
                    }catch (e){}
                }

                scope.updateTotal = function(newAmount, oldAmount) {
                    if (isNaN(newAmount)) {
                        scope.journalTotals = 0;
                        toastr.clear();
                        toastr.warning("Entered amount is invalid!");
                    } else {
                        scope.journalTotals  = voucherUtil.calculateJournalTotals(scope.journalEntries);
                    }
                }

                scope.setSelectedIdx = function(index){
                    scope.journalIdx = index;
                }

                scope.account_selection_handler = function(data) {
                    if (data !== undefined) {
                        if (data.length == 2) {
                            var account = data[0].account;
                            var rowSelectedAccount = {
                                "accountId": account.id,
                                "code": account.code,
                                "description": account.title,
                                "debit" : data[0].debit ? account.amount : 0,
                                "credit" : data[0].debit ? 0 : account.amount,
                                "distribution" : data[0].distribution
                            }
                            scope.selectedGlAccount = rowSelectedAccount;
                            // put back with updates
                            scope.journalEntries[scope.journalIdx] = rowSelectedAccount;
                            scope.subLedgerLines[scope.journalIdx] = data[0].slentries;

                            // for w.tax account
                            var wTaxAccount = data[1].account;
                            var rowSelectedAccount = {
                                "accountId": wTaxAccount.id,
                                "code": wTaxAccount.code,
                                "description": wTaxAccount.title,
                                "debit" : data[1].debit ? wTaxAccount.amount : 0,
                                "credit" : data[1].debit ? 0 : wTaxAccount.amount,
                                "wTaxEntry" : data[1].wTaxEntry
                            }
                            // put back with updates
                            scope.journalEntries[scope.journalEntries.length] = rowSelectedAccount;

                        } else {
                            var account = data[0].account;
                            var rowSelectedAccount = {
                                "accountId": account.id,
                                "code": account.code,
                                "description": account.title,
                                "debit" : data[0].debit ? account.amount : 0,
                                "credit" : data[0].debit ? 0 : account.amount,
                                "distribution" : data[0].distribution
                            }
                            scope.selectedGlAccount = rowSelectedAccount;
                            // put back with updates
                            scope.journalEntries[scope.journalIdx] = rowSelectedAccount;
                            scope.subLedgerLines[scope.journalIdx] = data[0].slentries;
                        }
                        scope.updateTotal(0,0);
                    }
                }
            }
        };
    }]);
}).call(this);