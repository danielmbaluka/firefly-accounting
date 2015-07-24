var jvFactory = angular.module('jvFactory', []);

jvFactory.factory('jvFactory', ['$http', function($http) {
    this.getList = function () {
        return $http.get('/jv/list/');
    };
    this.getJv = function (id) {
        return $http.get('/jv/'+id);
    };
    this.getListForRegister = function (from, to) {
        return $http.get('/jv/register/'+from+'/'+to);
    };
    return this;
}]);