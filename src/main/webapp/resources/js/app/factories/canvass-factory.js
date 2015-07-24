var canvassFactory = angular.module('canvassFactory', []);

canvassFactory.factory('canvassFactory', ['$http', function($http) {
    this.getCanvassList = function () {
        return $http.get('/canvass-rv/list/');
    };
    this.getCanvass = function (id) {
        return $http.get('/canvass-rv/'+id);
    };
    return this;
}]);