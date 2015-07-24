var apvFactory = angular.module('apvFactory', []);

apvFactory.factory('apvFactory', ['$http', function($http) {
    this.getList = function (status) {
        if (status === undefined) {
            return $http.get('/apv/list');
        } else {
            return $http.get('/apv/list/' + status);
        }
    };

    this.getApv = function (apvId) {
        return $http.get('/apv/'+apvId);
    };
    return this;
}]);