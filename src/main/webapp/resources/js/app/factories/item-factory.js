var itemFactory = angular.module('itemFactory', []);

itemFactory.factory('itemFactory', ['$http', function($http) {
    this.getItems = function () {
        return $http.get('/item/list/');
    };

    this.getItem = function (id) {
        return $http.get('/item/'+id);
    };
    return this;
}]);