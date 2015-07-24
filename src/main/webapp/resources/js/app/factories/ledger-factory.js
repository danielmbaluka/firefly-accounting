var ledgerFactory = angular.module('ledgerFactory', []);

ledgerFactory.factory('ledgerFactory', ['$http', function($http) {
    this.getGLEntries = function (transId) {
        return $http.get('/ledger/gl/'+transId);
    };

    this.getTempLedgerBatch = function () {
        return $http.get('/ledger/temp/all');
    };

    this.getTempGLEntries = function (tempBatchId) {
        return $http.get('/ledger/temp/gl/'+tempBatchId);
    };

    this.getTempSLEntries = function (tempBatchId, accountId) {
        return $http.get('/ledger/temp/sl/'+ tempBatchId + '/' + accountId);
    };

    this.getSLEntries = function (glId) {
        return $http.get('/ledger/sl/'+glId);
    };

    this.getSLEntriesSync = function (glId) {
        var response = {};

        // jquery ajax
        $.ajax({
            url:  '/ledger/sl/'+glId,
            type: 'GET',
            async: false
        }).done(function(data) {
            response.data = data;
        });
        return response;
    };

    this.getSLEntriesSync2 = function (transId, accountId) {
        var response = {};

        // jquery ajax
        $.ajax({
            url:  '/ledger/sl/'+transId + '/' + accountId,
            type: 'GET',
            async: false
        }).done(function(data) {
            response.data = data;
        });
        return response;
    };

    this.getSLEntriesAsync = function (transId, accountId) {
        return $http.get('/ledger/sl/'+transId + '/' + accountId);
    };

    this.getGLEntriesAsync = function (transId, accountId) {
        return $http.get('/ledger/gl/'+transId + '/' + accountId);
    };

    return this;
}]);