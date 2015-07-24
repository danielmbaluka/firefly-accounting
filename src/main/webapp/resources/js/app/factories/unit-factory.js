var unitFactory = angular.module('unitFactory', []);

unitFactory.factory('unitFactory', ['$http', function($http) {
    this.getUnits = function () {
        return $http.get('/unit/list/');
    };

    this.getUnit = function (id) {
        return $http.get('/unit/'+id);
    };
    return this;
}]);