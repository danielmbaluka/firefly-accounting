var supplierFactory = angular.module('supplierFactory', []);

supplierFactory.factory('supplierFactory', ['$http', function($http) {
    this.getSuppliers = function () {
        return $http.get('/supplier/list/');
    };
    this.getSupplier = function (id) {
        return $http.get('/supplier/'+id);
    };
    return this;
}]);