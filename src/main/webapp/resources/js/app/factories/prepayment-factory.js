var prepaymentFactory = angular.module('prepaymentFactory', []);

prepaymentFactory.factory('prepaymentFactory', ['$http', function($http) {
    this.getPrepaymentList = function () {
        return $http.get('/pre-payment/list/');
    };
    this.getPrepayment = function (id) {
        return $http.get('/pre-payment/'+id);
    };
    return this;
}]);