var canvassDetailFactory = angular.module('canvassDetailFactory', []);

canvassDetailFactory.factory('canvassDetailFactory', ['$http', function($http) {
    this.getCanvassDetails = function (canvassId) {
        return $http.get('/canvass_detail/cnvsd/'+canvassId);
    };
    return this;
}]);
