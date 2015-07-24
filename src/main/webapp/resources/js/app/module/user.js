var userApp = angular.module('user', [
    'userManagementFactory',
    'roleFactory',
    'errorHandlerService',
    'jQueryFnWrapperService',
    'menuFactory',
    'pageFactory',
    'cmnFormErrorApp',
    'utilService'
]);

userApp.controller('userListCtrl', ['$scope', '$http', 'userFactory',
    function($scope,  $http, userFactory) {

        console.log(this);
        userFactory.getUsers()
            .success(function (data) {
                $scope.users = data;
            })
            .error(function (error) {
                toastr.error('Failed to load users!');
            });

    }]);

userApp.controller('addEditUserCtrl', ['$scope', '$stateParams', '$http', 'userFactory', 'errorToElementBinder',
    'roleFactory', 'csrf',
    function($scope, $stateParams, $http, userFactory, errorToElementBinder, roleFactory, csrf) {

        $scope.title = 'Add user';
        $scope.save = 'Save';
        $scope.showForm = true;

        $scope.user = {};
        $scope.roles = [];

        var resourceURI = '/user/create';

        roleFactory.getRoles()
            .success(function (data) {
                $scope.roles = data;
            })
            .error(function (error) {
                toastr.error('Failed to load roles.');
            });

        if(!($stateParams.userId === undefined)) {  // update mode
            $scope.title = 'Update user';
            $scope.showForm = false;

            $scope.userId = $stateParams.userId;

            userFactory.getUser($scope.userId)
                .success(function (data) {

                    if (data === '' || data.id <= 0) {    // not found
                        toastr.warning('User not found!');
                        window.location.hash = '#/users-and-roles/users';
                    } else {
                        $scope.user = data;
                        $scope.user.password = "";

                        // roles
                        angular.forEach(data.roles, function(role, key) {
                            $scope.checkAssignedRole(role.id);
                        });

                        $scope.showForm = true;
                    }
                })
                .error(function (error) {
                    toastr.warning('User not found!');
                    window.location.hash = '#/users-and-roles/users';
                });

            resourceURI = '/user/update';
        }

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
                    window.location.hash = '#/users-and-roles/user/' + data.modelId + '/detail';
                    toastr.success('User successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                $scope.save ='Save';
                $scope.submitting = false;
            });
        }
    }]);

userApp.controller('userDetailsCtrl', ['$scope', '$state', '$stateParams', '$http', 'userFactory', 'routeUtil',
    function($scope, $state, $stateParams, $http, userFactory, routeUtil) {

        $scope.main = function() {
            routeUtil.gotoMain($state);
        }

        $scope.showDetails = false;

        if(!($stateParams.userId === undefined)) {
            $scope.title = 'User details';

            $scope.userId = $stateParams.userId;

            userFactory.getUser($scope.userId)
                .success(function (data) {

                    console.log(data);

                    if (data === '' || data.id <= 0) {    // not found
                        toastr.warning('User not found!');
                        window.location.hash = '#/users-and-roles';
                    } else {
                        $scope.user = data;
                        $scope.showDetails = true;
                    }
                })
                .error(function (error) {
                    toastr.warning('User not found!');
                    window.location.hash = '#/users-and-roles';
                });

        } else {
            toastr.warning('User not found!');
            window.location.hash = '#/users-and-roles';
        }

        $scope.pointToEditForm = function() {
            window.location.hash = '#/user/' + $scope.userId + "/edit";
        }
    }]);

userApp.controller('roleListCtrl', ['$scope', '$http','roleFactory', function($scope,  $http, roleFactory) {

    roleFactory.getRoles()
        .success(function (data) {
            $scope.roles = data;
        })
        .error(function (error) {
            toastr.error('Failed to load roles.');
        });
}]);

userApp.controller('roleDetailsCtrl', ['$location', '$scope', '$state', '$stateParams', '$http', 'roleFactory',
    'routeUtil', 'pageFactory',
    function($location, $scope, $state,  $stateParams, $http, roleFactory, routeUtil, pageFactory) {

        $scope.main = function() {
            routeUtil.gotoMain($state);
        }

        $scope.path = '#' + $location.path();
        $scope.showDetails = false;
        $scope.pageComponents = [];

        $scope.showPageComponents = function(pageId) {
            if ($scope.pageComponents[pageId] == undefined) {
                pageFactory.getRolePageComponents($scope.roleId, pageId).success(function (data) {
                    $scope.pageComponents[pageId] = data;
                });
            }
        }

        if(!($stateParams.roleId === undefined)) {
            $scope.title = 'Role details';

            $scope.roleId = $stateParams.roleId;

            roleFactory.getRole($scope.roleId)
                .success(function (data) {

                    if (data === '' || data.id <= 0) {    // not found
                        toastr.warning('Role not found!');
                        window.location.hash = '#/users';
                    } else {
                        $scope.role = data;

                        pageFactory.getRolePages($scope.roleId).success(function (data) {
                            $scope.pages = data;
                        });

                        $scope.showDetails = true;
                    }
                })
                .error(function (error) {
                    toastr.warning('Role not found!');
                    window.location.hash = '#/users';
                });

        } else {
            toastr.warning('Role not found!');
            window.location.hash = '#/users';
        }

        $scope.pointToEditForm = function() {
            window.location.hash = '#/role/' + $scope.roleId + "/edit";
        }
    }]);

userApp.controller('addEditRoleCtrl', ['$location', '$scope', '$stateParams', '$http', 'roleFactory', 'errorToElementBinder',
    'csrf', 'menuFactory', 'pageFactory',
    function($location, $scope, $stateParams, $http, roleFactory, errorToElementBinder, csrf, menuFactory, pageFactory) {

    $scope.path = '#' + $location.path();
    $scope.title = 'Add role';
    $scope.save = 'Save';
    $scope.showForm = true;

    $scope.pageComponents = [];
    $scope.assignedPageComponents = {};
    $scope.role = {};


    var resourceURI = '/role/create';

    function loadMenus() {
        menuFactory.getMenus().success(function (data) {
            $scope.menus = data;
            if ($scope.role != undefined) {
                setSelectedMenu();
            }
        });
    }

    pageFactory.getPages().success(function (data) {
        $scope.pages = data;
    });

    $scope.showPageComponents = function(pageId) {
        if ($scope.pageComponents[pageId] == undefined) {
            pageFactory.getPageComponents(pageId).success(function (data) {
                $scope.pageComponents[pageId] = data;
                setSelectedPageComponents(pageId);
            });
        }
    }

    function setSelectedMenu() {
        if ($scope.menus == undefined) return;

        angular.forEach($scope.menus, function(menu, key) {
            angular.forEach($scope.role.menus, function(roleMenu, key) {
                if (menu.id == roleMenu.id) {
                    menu.selected = true;
                    return;
                }
            });
        });
    }

    function setSelectedPageComponents(pageId) {
        if ($scope.pageComponents == undefined) return;

        angular.forEach($scope.pageComponents[pageId], function(pageComponent, key) {
            angular.forEach($scope.role.pageComponents, function(rolePageComponent, key) {
                if (rolePageComponent.page.id = pageId) {   // for same page components
                    if (pageComponent.id == rolePageComponent.id) {
                        pageComponent.selected = true;
                        return;
                    }
                }
            });
        });
    }

    loadMenus();

    if(!($stateParams.roleId === undefined)) {

        $scope.title = 'Update role';
        $scope.showForm = false;

        $scope.roleId = $stateParams.roleId;

        roleFactory.getRole($scope.roleId)
            .success(function (data) {

                if (data === '' || data.id <= 0) {    // not found
                    window.location.hash = '#/users-and-roles/roles';
                } else {
                    $scope.role = data;
                    setSelectedMenu();
                    $scope.showForm = true;
                }
            })
            .error(function (error) {
                toastr.warning('Role not found!');
                window.location.hash = '#/users-and-roles/roles';
            });

        resourceURI = '/role/update';
    }


    $scope.processForm = function() {

        $scope.save ='Saving...';

        $scope.errors = {};
        $scope.submitting = true;
        csrf.setCsrfToken();

        var roleMenus = [];
        var roleMenusToEvict = [];
        var menus = angular.copy($scope.menus);
        angular.forEach(menus, function(menu, key) {
            if (menu.selected) {
                delete menu['selected']; // hibernate will complain, so delete it
                roleMenus.push(menu);
            } else {
                roleMenusToEvict.push(menu);
            }
        });

        $scope.role.menus = roleMenus;
        $scope.role.menusToEvict = roleMenusToEvict;

        var rolePageComponents = [];
        var pageComponentsToEvict = [];
        var pages = angular.copy($scope.pageComponents);    // $scope.pageComponents is grouped by page
        angular.forEach(pages, function(page, key) {  // loop all pages
            if (page != undefined) {    // for pages with no page components yet
                angular.forEach(page, function(pageComponent, key) {  // loop all components of a page

                    pageComponent['viewRoute'] = {'id' : pageComponent.viewRouteId};
                    pageComponent['actionRoute'] = {'id' : pageComponent.actionRouteId};

                    if (pageComponent.selected) {
                        delete pageComponent['selected']; // hibernate will complain, so delete it
                        rolePageComponents.push(pageComponent);
                    } else {
                        delete pageComponent['selected']; // hibernate will complain, so delete it
                        pageComponentsToEvict.push(pageComponent);
                    }
                });
            }
        });

        $scope.role.pageComponents = rolePageComponents;
        $scope.role.pageComponentsToEvict = pageComponentsToEvict;

        var res = $http.post(resourceURI, $scope.role);
        res.success(function(data) {
            if (!data.success) {
                $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                $scope.save ='Save';
                $scope.submitting = false;
                toastr.warning('Error found.');
            } else {
                window.location.hash = '#/users-and-roles/role/' + data.modelId + '/detail';
                toastr.success('Role successfully saved!');
            }
        });
        res.error(function(data, status, headers, config) {
            toastr.error('Something went wrong!');
            $scope.save ='Save';
            $scope.submitting = false;
        });
    }

    $scope.toggleParent = function(selectedMenu) {


        if (selectedMenu.parentMenu == null) { // selected top level menu

            angular.forEach($scope.menus, function(menu, key) {
                if (menu.parentMenu != null) {
                    if (menu.parentMenu.id == selectedMenu.id) {
                        menu.selected = selectedMenu.selected;
                    }
                }
            });
        } else {

            selectedMenu.selected = selectedMenu.selected;

            var parentMenu = {};
            var checked = false;

            // uncheck parent if no selected child
            angular.forEach($scope.menus, function(menu, key) {

                if (menu.id == selectedMenu.parentMenu.id) { parentMenu = menu; } // needed for binding
                if (menu.parentMenu != null) {  // get menu with parent

                    if (menu.parentMenu.id == selectedMenu.parentMenu.id) {

                        if (menu.selected) { // after selection
                            checked = true;
                            return;
                        }
                    }
                }

            });
            parentMenu.selected = checked;
        }
    }
}]);