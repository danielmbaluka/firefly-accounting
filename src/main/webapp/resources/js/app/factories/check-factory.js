var checkFactory = angular.module('checkFactory', []);

checkFactory.factory('checkFactory', ['$http', function($http) {
    this.getChecks = function () {
        return $http.get('/check/list/');
    };

    this.getCheck = function (id) {
        return $http.get('/check/'+id);
    };
    return this;
}]);