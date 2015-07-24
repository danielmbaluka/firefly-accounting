var entityFactory = angular.module('entityFactory', []);

entityFactory.factory('entityFactory', ['$http', function($http) {
    this.getEntities = function (typesAsMarkers) {
        if ($.isArray(typesAsMarkers) && typesAsMarkers.length > 0){
            return $http.get('/json/entities/', {params : {entityTypes : typesAsMarkers}});
        } else {
            return $http.get('/json/entities/');
        }
    };
    return this;
}]);