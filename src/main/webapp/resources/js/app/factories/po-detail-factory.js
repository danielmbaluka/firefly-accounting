var poDetailFactory = angular.module('poDetailFactory', []);

poDetailFactory.factory('poDetailFactory', ['$http', function($http) {
    this.getPurchaseOrderDetails = function (poId) {
        return $http.get('/po_detail/pod/'+poId);
    };
    return this;
}]);
