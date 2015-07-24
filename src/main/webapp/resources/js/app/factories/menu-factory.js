var menuFactory = angular.module('menuFactory', []);

menuFactory.factory('menuFactory', ['$http', function($http) {
    this.getMenus = function () {
        return $http.get('/json/menus');
    };
    return this;
}]);