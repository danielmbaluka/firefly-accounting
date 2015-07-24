var workflowFactory = angular.module('workflowFactory', []);

workflowFactory.factory('workflowFactory', ['$http', function($http) {
    this.getActions = function (transId) {
        return $http.get('/json/workflow-actions/' + transId);
    };
    return this;
}]);