var cvFactory = angular.module('cvFactory', []);

cvFactory.factory('cvFactory', ['$http', function($http) {
    this.getList = function () {
        return $http.get('/cv/list/');
    };

    this.getCvForReleasing = function () {
        return $http.get('/cv/for-check-releasing');
    };

    this.getChecksForReleasing = function (transId) {
        return $http.get('/cv/checks-for-releasing/' + transId);
    };

    this.getCv = function (id) {
        return $http.get('/cv/'+id);
    };

    this.getCvChecks = function (transId) {
        return $http.get('/cv/checks/'+transId);
    };

    this.getTaxCodes = function () {
        return $http.get('/json/tax-codes');
    };

    this.getListForRegister = function (from, to) {
        return $http.get('/cv/register/'+from+'/'+to);
    };
    return this;
}]);