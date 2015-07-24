var requisitionVoucherFactory = angular.module('requisitionVoucherFactory', []);

requisitionVoucherFactory.factory('requisitionVoucherFactory', ['$http', function($http) {
    this.getRequisitionVouchers = function () {
        return $http.get('/requisition-voucher/list/');
    };
    this.getRequisitionVoucher = function (id) {
        return $http.get('/requisition-voucher/'+id);
    };
    return this;
}]);