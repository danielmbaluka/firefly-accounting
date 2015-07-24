var accountFactory = angular.module('businessSegmentFactory', []);

accountFactory.factory('businessSegmentFactory', ['$http', function($http) {
    this.getSegments = function () {
        return $http.get('/bus-seg/list');
    };

    return this;
}]);