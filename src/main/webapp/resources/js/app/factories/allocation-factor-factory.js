var allocationFactorFactory = angular.module('allocationFactorFactory', []);

allocationFactorFactory.factory('allocationFactorFactory', ['$http', function($http) {
    this.getFactors = function () {
        return $http.get('/allocation-factor/list/');
    };
    this.getFactor = function (accountId, effectId) {
        return $http.get('/allocation-factor/'+accountId+'/'+effectId);
    };

    this.getFactorSegments = function (accountId) {
        return $http.get('/allocation-factor/'+accountId);
    };

    this.getAllocatedFactors = function (accountId, date) {
        return $http.get('/allocation-factor/'+accountId + '/date/' + date);
    };

    this.getDateRanges = function () {
        return $http.get('/allocation-factor/date-ranges/');
    };

    return this;
}]);