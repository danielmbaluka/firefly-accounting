var rvDetailFactory = angular.module('rvDetailFactory', []);

rvDetailFactory.factory('rvDetailFactory', ['$http', function($http) {
    this.getRvDetails = function (rvId) {
        return $http.get('/rv_detail/rvd/'+rvId);
    };

    this.rvDetailsByStatus = function (statusId) {
        return $http.get('/rv_detail/rvd/status/'+statusId);
    };
    return this;
}]);
