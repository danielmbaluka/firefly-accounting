var accountService = angular.module('accountService', []);

accountService.service('accountService', function() {
    this.createAccountJson = function($scope) {

        // create json to be posted to the server
        var jAccount = angular.copy($scope.account);
        jAccount.isActive = jAccount.isActive ? 1 : 0;
        jAccount.hasSL =jAccount.hasSL ? 1 : 0;
        jAccount.isHeader = jAccount.isHeader ? 1 : 0;

        jAccount.accountGroup = angular.copy($scope.accountGroup);
        jAccount.accountType = angular.copy($scope.accountType);
        jAccount.parentAccount = angular.copy($scope.parentAccount);
        jAccount.parentAccountId = jAccount.parentAccount == null ? 0 : jAccount.parentAccount.id;

        var segmentAccounts = [];
        var scopeSegments = angular.copy($scope.segments);
        angular.forEach(scopeSegments, function(segment, key) {
            if (segment.selected) {
                delete segment['selected']; // hibernate will complain, so delete it
                delete segment['assigned'];

                var segmentAccount = {
                    "accountCode" : "",
                    "businessSegment" :segment
                };
                segmentAccounts.push(segmentAccount);
            }
        });
        jAccount.segmentAccounts = segmentAccounts;

        return jAccount;
    };

});