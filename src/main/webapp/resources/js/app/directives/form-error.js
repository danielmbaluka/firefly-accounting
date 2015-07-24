(function () {
    var app;
    var app = angular.module('cmnFormErrorApp', []);

    app.directive('formError', ['$timeout', function ($timeout) {
        return {
            scope : {
                errField : '='
            },
            restrict: 'E',
            templateUrl: "/common/form-field-error-msg"
        };
    }]);
}).call(this);
