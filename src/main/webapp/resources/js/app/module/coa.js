var coaApp = angular.module('coa', [
    'treeGrid',
    'errorHandlerService',
    'jQueryFnWrapperService',
    'accountService',
    'accountFactory',
    'businessSegmentFactory',
    'cmnFormErrorApp',
    'utilService'
]);

coaApp.controller('accountDetailsCtrl', ['$scope', '$state', '$stateParams', '$http', 'accountFactory', '$state', 'routeUtil',
    function($scope, $state, $stateParams, $http, accountFactory, $state, routeUtil) {

        $scope.main = function() {
            routeUtil.gotoMain($state);
        }

        $scope.showDetails = false;

        if(!($stateParams.accountId === undefined)) {
            $scope.title = 'Account details';

            $scope.accountId = $stateParams.accountId;

            accountFactory.getAccount($stateParams.accountId)
                .success(function (data) {

                    if (data === '' || data.id <= 0) {    // not found
                        toastr.warning('Account not found!');
                        window.location.hash = '#/coa';
                    } else {
                        $scope.account = data;
                        $scope.showDetails = true;
                    }
                })
                .error(function (error) {
                    toastr.warning('Account not found!');
                    window.location.hash = '#/coa';
                });

        } else {
            toastr.warning('Account not found!');
            window.location.hash = '#/coa';
        }

        $scope.pointToEditForm = function() {
            window.location.hash = '#/account/' + $scope.accountId + "/edit";
        }
    }]);

coaApp.controller('newAccountCtrl', ['$scope', '$state', '$stateParams', '$http', 'errorToElementBinder', 'accountFactory',
    'modalToggler', 'businessSegmentFactory', 'accountService', 'csrf',
    function($scope, $state, $stateParams, $http, errorToElementBinder, accountFactory, modalToggler, businessSegmentFactory,
             accountService, csrf) {

        $scope.account = {};
        $scope.showForm = true;
        // setup defaults
        var accountGroup = {"id" : "1"};
        var accountType = {"id" : "1"};
        var initParentAccount = {"id" : "1"};
        $scope.segments = [];
        $scope.accountGroup = accountGroup;
        $scope.accountType = accountType;
        $scope.account['normalBalance'] = "1";
        $scope.account['accountGroup'] = accountGroup;
        $scope.account['accountType'] = accountType;
        $scope.account['isActive'] = true;
        $scope.account['hasSL'] = false;
        $scope.account['isHeader'] = false;
        $scope.account['parentAccount'] = initParentAccount;
        $scope.errors = {};
        $scope.submitting = false;
        $scope.save ='Save';
        $scope.title = 'Add an account';

        $scope.modalBodyTemplateUrl = "/common/account-browser";
        var resourceURI = '/account/create';

        businessSegmentFactory.getSegments().success(function (data) { $scope.segments = data; });
        accountFactory.getAccountTypes().success(function (data) { $scope.accountTypes = data; });
        accountFactory.getAccountGroups().success(function (data) { $scope.accountGroups = data; });

        var updateMode = $stateParams.accountId !== undefined;
        if(updateMode) {
            $scope.title = 'Update account';
            $scope.showForm = false;

            $scope.accountId = $stateParams.accountId;

            accountFactory.getAccountsExcept($scope.accountId)
                .success(function (data) {
                    $scope.parentAccounts = data;
                })
                .error(function (error) {
                    toastr.warning('Failed to load accounts.');
                });

            accountFactory.getAccount($scope.accountId)
                .success(function (data) {
                    if (data === '' || data.id <= 0) {    // not found
                        window.location.hash = '#/coa/account/' + $scope.accountId;
                    } else {
                        data.isActive = (data.isActive == 1);
                        data.isHeader = (data.isHeader == 1);
                        data.hasSL = (data.hasSL == 1);

                        $scope.account = data;

                        $scope.accountType = data.accountType;
                        $scope.accountGroup = data.accountGroup;
                        $scope.parentAccount = data.parentAccount;
                        // segments
                        angular.forEach(data.segmentAccounts, function(segmentAccount, key) {
                            $scope.checkAssignedSegment(segmentAccount.businessSegment.id);
                        });

                        $scope.showForm = true;
                    }
                })
                .error(function (error) {
                    toastr.warning('Account not found!');
                    window.location.hash = '#/coa';
                });

            resourceURI = '/account/update';
        }

        $scope.checkAssignedSegment = function (businessSegmentId) {
            angular.forEach($scope.segments, function(segment, key) {
                if (segment.id == businessSegmentId) {
                    segment.selected = true;
                    if ($scope.account.id > 0) {
                        // check if is in newly selected segments
                        var index = newSelectedSegment.indexOf(businessSegmentId);
                        if (index < 0) { // not found
                            segment.assigned = true;
                        }
                    }
                    $scope.segments[key] = segment;
                    return;
                }
            });
        }

        $scope.showAccountBrowser = function () {
            if (angular.isUndefined($scope.parentAccounts) || $scope.parentAccounts.length == 0) {
                accountFactory.getAccounts()
                    .success(function (data) {
                        $scope.parentAccounts = data;
                        modalToggler.show('myModal');
                    })
                    .error(function (error) {
                        toastr.warning('Failed to load accounts.');
                    });
            }  else {
                modalToggler.show('myModal');
            }
        }

        $scope.accountSelectedFromBrowser = function (selectedAccount) {
            if (!angular.isUndefined(selectedAccount)) {
                $scope.parentAccount = selectedAccount;
                modalToggler.hide('myModal');
            }
        }

        $scope.clearAccountSelectedFromBrowser = function () {
            $scope.parentAccount = initParentAccount;
        }

        var newSelectedSegment = [];
        $scope.toggleSegment = function(idx, segment) {
            newSelectedSegment.push(segment.id);
        };

        $scope.processForm = function() {
            $scope.save ='Saving...';

            $scope.errors = {};
            $scope.submitting = true;
            csrf.setCsrfToken();

            // create json to be posted to the server
            var jAccount = accountService.createAccountJson($scope);

            var res = $http.post(resourceURI, jAccount);
            res.success(function(data) {
                if (!data.success) {
                    $scope.errors = errorToElementBinder.bindToElements(data, $scope.errors);
                    $scope.save ='Save';
                    $scope.submitting = false;
                    toastr.warning('Error found.');
                } else {
                    window.location.hash = '#/coa/account/' + data.modelId;
                    toastr.success('Account successfully saved!');
                }
            });
            res.error(function(data, status, headers, config) {
                toastr.error('Something went wrong!');
                $scope.save ='Save';
                $scope.submitting = false;
            });
        };
    }]);

coaApp.controller('treeGridCtrl',  ['$scope', 'accountFactory', '$http', function($scope, accountFactory, $http) {
    var tree, myTreeData;
    var rawTreeData;

    rawTreeData = accountFactory.getTreeAccounts().data;

    myTreeData = getTree(rawTreeData, 'id', 'parentAccountId');
    $scope.tree_data = myTreeData;
    $scope.accounts_tree = tree = {};
    $scope.expanding_property = "title";
    $scope.data_loaded = true;
    $scope.col_defs = [
        { field: "code"},
        { field: "accountType"},
        { field: "id"}
    ];

    $scope.accounts_tree_handler = function(branch){
        console.log('you clicked on', branch)
    }

    $scope.printCoa = function(type) {

        toastr.options.extendedTimeOut = 0;
        toastr.options.timeOut = 0;
        toastr.info("Processing report...");

        $http.get('/download/token').success(function(response) {
            // Store token
            var token = response.message[0];

            // Start download
            $scope.url = '/admin/coa/export'+'?token='+token+'&type='+type;
            toastr.clear();
        });
    }

    function getTree(data, primaryIdName, parentIdName){
        if(!data || data.length==0 || !primaryIdName ||!parentIdName)
            return [];

        var tree = [],
            rootIds = [],
            item = data[0],
            primaryKey = item[primaryIdName],
            treeObjs = {},
            parentId,
            parent,
            len = data.length,
            i = 0;

        while(i<len){
            item = data[i++];
            primaryKey = item[primaryIdName];
            treeObjs[primaryKey] = item;
            parentId = item[parentIdName];

            if(parentId){
                parent = treeObjs[parentId];

                if(parent.children){
                    parent.children.push(item);
                }
                else{
                    parent.children = [item];
                }
            }
            else{
                rootIds.push(primaryKey);
            }
        }

        for (var i = 0; i < rootIds.length; i++) {
            tree.push(treeObjs[rootIds[i]]);
        };

        return tree;
    }
}]);