var profileApp = angular.module('profile', [
    'userManagementFactory',
    'roleFactory',
    'errorHandlerService',
    'jQueryFnWrapperService',
    'menuFactory',
    'cmnFormErrorApp',
    'utilService'
]);


profileApp.controller('editProfileCtrl', ['$scope', '$http', 'userFactory', 'errorToElementBinder', 'roleFactory', 'csrf',
    function($scope, $http, userFactory, errorToElementBinder, roleFactory, csrf) {

        $scope.title = 'Update profile';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.user = {};
        $scope.roles = [];

        var resourceURI = '/user/update';

        roleFactory.getRoles().success(function (data) { $scope.roles = data; });

        userFactory.getUserProfile().success(function (data) {

            if (data === '' || data.id <= 0) {    // not found
                toastr.warning('User not found!');
                window.location = "/";
            } else {
                $scope.user = data;
                $scope.user.password = "";

                // roles
                angular.forEach(data.roles, function(role, key) {
                    $scope.checkAssignedRole(role.id);
                });
            }
        });

        $scope.checkAssignedRole = function (roleId) {
            angular.forEach($scope.roles, function(role, key) {
                if (role.id == roleId) {
                    role.selected = true;
                    $scope.roles[key] = role;
                    return;
                }
            });
        }

        $scope.processForm = function() {

            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            var userRoles = [];
            var roles = angular.copy($scope.roles);
            angular.forEach(roles, function(role, key) {
                if (role.selected) {
                    delete role['selected']; // hibernate will complain, so delete it
                    userRoles.push(role);
                }
            });

            $scope.user.roles = userRoles;
            console.log($scope.user);

            var res = $http.post(resourceURI, $scope.user);
            res.success(function(data) {
                if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    $scope.submitting = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/user/profile';
                    toastr.success('Profile successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                $scope.save ='Save';
                $scope.submitting = false;
            });
        }
    }]);

profileApp.controller('userDetailsCtrl', ['$scope', '$http', 'userFactory',
    function($scope, $http, userFactory) {


        $scope.showDetails = false;

        userFactory.getUserProfile().success(function (data) {
            if (data === '' || data.id <= 0) {    // not found
                toastr.warning('Profile not found!');
                window.location = "/";
            } else {
                $scope.user = data;
                $scope.showDetails = true;
            }
        })
        .error(function (error) {
            toastr.warning('Profile not found!');
            window.location = "/";
        });

    }]);
