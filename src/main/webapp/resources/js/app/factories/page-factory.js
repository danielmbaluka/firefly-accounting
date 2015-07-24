var pageFactory = angular.module('pageFactory', []);

pageFactory.factory('pageFactory', ['$http', function($http) {
    this.getPages = function () {
        return $http.get('/json/pages');
    };

    this.getPageComponents = function (pageId) {
        return $http.get('/json/page-components/' + pageId);
    };

    this.getRolePages = function (roleId) {
        return $http.get('/role/pages/'+roleId);
    };

    this.getRolePageComponents = function (roleId, pageId) {
        return $http.get('/role/page-components/ ' + roleId + '/' + pageId);
    };

    return this;
}]);