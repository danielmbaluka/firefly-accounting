var cashReceiptsFactory = angular.module('cashReceiptsFactory', []);

cashReceiptsFactory.factory('cashReceiptsFactory', ['$http', function($http) {
    this.getList = function (status) {
        if (status === undefined) {
            return $http.get('/cash-receipts/list');
        } else {
            return $http.get('/cash-receipts/list/' + status);
        }
    };

    this.getCashReceipts = function (cashReceiptsId) {
        return $http.get('/cash-receipts/'+cashReceiptsId);
    };
    return this;
}]);