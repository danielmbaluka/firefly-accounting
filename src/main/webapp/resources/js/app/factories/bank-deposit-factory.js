var bankDepositFactory = angular.module('bankDepositFactory', []);

bankDepositFactory.factory('bankDepositFactory', ['$http', function($http) {
    this.getList = function (status) {
        if (status === undefined) {
            return $http.get('/bank-deposit/list');
        } else {
            return $http.get('/bank-deposit/list/' + status);
        }
    };

    this.getBankDeposit = function (bankDepositId) {
        return $http.get('/bank-deposit/'+bankDepositId);
    };
    return this;
}]);