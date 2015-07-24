var purchaseOrderFactory = angular.module('purchaseOrderFactory', []);

purchaseOrderFactory.factory('purchaseOrderFactory', ['$http', function($http) {
    this.getPurchaseOrderList = function () {
        return $http.get('/purchase-order/list/');
    };
    this.getPurchaseOrder = function (id) {
        return $http.get('/purchase-order/'+id);
    };
    return this;
}]);