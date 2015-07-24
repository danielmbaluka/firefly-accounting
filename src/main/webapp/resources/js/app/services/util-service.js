var utilService = angular.module('utilService', []);

utilService.service('slEntityUtil', function() {
    this.markerToString = function(marker) {
        if (marker == 1) return {type : "Employee", class: "label-warning"};
        if (marker == 2) return {type : "Supplier", class: "label-info"};
        if (marker == 3) return {type : "Continuing Property Record", class: "label-danger"};
        if (marker == 4) return {type : "Work Order", class: "label-default"};
        if (marker == 5) return {type : "Prepayments", class: "label-success"};
        if (marker == 6) return {type : "Petty Cash Fund", class: "label-primary"};
    }
});


utilService.service('documentStatusUtil', function() {
    this.classType = function(status) {
        if (status == 'Document Created') return "label-default";
        if (status == 'Sent For Checking') return "label-warning";
        if (status == 'For Audit') return "label-info";
        if (status == 'For Approval') return "label-success";
        if (status == 'Approved') return "label-success";
        if (status == 'Denied') return "label-danger";
    }
});

utilService.service('routeUtil', function() {
    this.gotoMain = function($state) {
        $state.go('^', $state.params, { reload: true });
    }
});


utilService.service('voucherUtil', function() {
    this.calculateJournalTotals = function(journalEntries) {
        var sumDr = 0;
        var sumCr = 0;
        var totals =  {"debit" : 0, "credit" : 0};

        if (typeof journalEntries !== 'undefined') {

            angular.forEach(journalEntries, function (entry, key) {
                try {
                    var dr = parseFloat(entry['debit']);
                    var cr = parseFloat(entry['credit']);
                    if (isNaN(dr)) {
                        dr = 0;
                    }
                    if (isNaN(cr)) {
                        cr = 0;
                    }

                    sumDr += dr
                    sumCr += cr
                } catch (e) {
                    console.log(e)
                }
            });

            totals['debit'] = sumDr;
            totals['credit'] = sumCr;
        }

        return totals;
    }

    this.calculateTotalAmount = function(podItems) {
        var sumAmount = 0;
        var totalAmount =  0;

        if (typeof podItems !== 'undefined') {

            angular.forEach(podItems, function (entry, key) {
                try {
                    var amt = parseFloat(entry['itemAmount']);
                    if (isNaN(amt)) {
                        amt = 0;
                    }

                    sumAmount += amt
                } catch (e) {
                    console.log(e)
                }
            });

            totalAmount = sumAmount;
        }

        return totalAmount;
    }

    this.calculateItemAmount = function(quantity, unitPrice) {
        var itemAmount = 0;

        try {
            var qty = parseFloat(quantity);
            var up = parseFloat(unitPrice);
            if (isNaN(qty)) {
                qty = 0;
            }

            itemAmount = qty * up;
        } catch (e) {
            console.log(e)
        }

        return itemAmount;
    }

    this.calculateMonthlyCost = function(balance, months) {
        var monthlyCost = 0;

        try {
            var tc = parseFloat(balance);
            var m = parseFloat(months);
            if (isNaN(balance)) {
                tc = 0;
            }

            monthlyCost = tc / m;
        } catch (e) {
            console.log(e)
        }

        return monthlyCost;
    }

    this.calculateBalance = function(totalCost, appliedCost) {
        var balance = 0;

        try {
            var tc = parseFloat(totalCost);
            var ac = parseFloat(appliedCost);
            if (isNaN(totalCost)) {
                tc = 0;
            }

            balance = tc - ac;
        } catch (e) {
            console.log(e)
        }

        return balance;
    }
});

utilService.service('dateUtil', function() {
    this.firstDateOfMonth = function() {
        var date = new Date();
        var y = date.getFullYear();
        var m = date.getMonth();
        return new Date(y, m, 1);
    }

    this.lastDateOfMonth = function() {
        var date = new Date();
        var y = date.getFullYear();
        var m = date.getMonth();
        return new Date(y, m + 1, 0);
    }
});