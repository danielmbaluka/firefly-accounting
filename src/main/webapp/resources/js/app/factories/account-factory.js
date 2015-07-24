var accountFactory = angular.module('accountFactory', []);

accountFactory.factory('accountFactory', ['$http', function($http) {
    this.getAccounts = function () {
        return $http.get('/account/list/');
    };

    this.getAccountsBySegment = function (segmentIds) {
        return $http.get('/account/by-segment', {params : {segmentIds : segmentIds}});
    };

    this.getAccountsWithSegment = function () {
        return $http.get('/account/with-segment');
    };

    this.getAccountsExcept = function (accountId) {
        return $http.get('/account/' + accountId + '/except');
    };

    this.getAccount = function (accountId) {
        return $http.get('/account/'+accountId);
    };

    this.getAccountTypes = function () {
        return $http.get('/account/type/list');
    };

    this.getAccountGroups = function () {
        return $http.get('/account/group/list');
    };

    this.getWTaxAccount = function () {
        return $http.get('/account/wtax');
    };

    this.getTreeAccounts = function () {
        var response = {};

        // jquery ajax
        $.ajax({
            url:  '/account/list/tree',
            type: 'GET',
            async: false
        }).done(function(data) {
            response.data = data;
        });
        return response;
    };
    return this;
}]);