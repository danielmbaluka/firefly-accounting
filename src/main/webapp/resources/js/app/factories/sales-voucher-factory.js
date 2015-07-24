var salesVoucherFactory = angular.module('salesVoucherFactory', []);

salesVoucherFactory.factory('salesVoucherFactory', ['$http', function($http) {
    this.getList = function (status) {
        if (status === undefined) {
            return $http.get('/sales-voucher/list');
        } else {
            return $http.get('/sales-voucher/list/' + status);
        }
    };

    this.getSalesVoucher = function (salesVoucherId) {
        return $http.get('/sales-voucher/'+salesVoucherId);
    };
    return this;
}]);