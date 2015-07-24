var jQueryFnWrapperService = angular.module('jQueryFnWrapperService', []);

jQueryFnWrapperService.service('modalToggler', function() {
    this.show = function(modalId) {
        modalId = '#' + modalId
        $(modalId).modal('show');
    };

    this.hide = function(modalId) {
        modalId = '#' + modalId
        $(modalId).modal('hide');
    };
});

jQueryFnWrapperService.service('csrf', ['$http', function($http) {
    this.setCsrfToken = function() {
        $http.defaults.headers.post['X-CSRF-TOKEN'] = $('input[name=_csrf]').val();
    }
}]);