var app = angular.module('showcase', [
    'cmnAccountBrowserWithSegmentApp',
    'cmnSLEntityBrowserApp',
    'cmnRequisitionVoucherBrowserApp',
    'cmnRvDetailForCanvassBrowserApp',
    'cmnItemBrowserApp',
    'cmnVoucherSlSetterApp'
]);

app.controller('acbCtrl', ['$scope', function($scope) {
    $scope.accounts_selection_handler = function(account){
        $scope.selectedAccount = account;
    }

    $scope.accounts_selection_handler2 = function(account){
        $scope.selectedAccount2 = account;
    }
}]);

app.controller('sleCtrl', ['$scope', function($scope) {
        $scope.entityTypes1 = [1];
        $scope.entity_selection_handler = function(entity){
            $scope.selectedEntity = entity;
        }


        $scope.entityTypes2 = [1,2];
        $scope.entity_selection_handler2 = function(entity){
            $scope.selectedEntity2 = entity;
        }
}]);

app.controller('itemCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.item_selection_handler1 = function(item){
        $scope.selectedItem1 = item;
    }

    $scope.item_selection_handler2 = function(item){
        $scope.selectedItem2 = item;
    }

    $scope.export = function(type) {
        $http.get('/download/token').success(function(response) {
            // Store token
            var token = response.message[0];
            // Start download
            $scope.url = '/admin/item/export'+'?token='+token+'&type='+type;
        });
    }
}]);

app.controller('rvCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.rv_selection_handler = function(requisitionVoucher){
        $scope.selectedRv1 = requisitionVoucher;
    }
}]);

app.controller('slSetterCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.glAccount = {'segmentAccountId' : 1, 'segmentAccountCode':'S2-123-141-00', 'title': 'Account 1'}; // dummy selected gl account

    var loadExistingSlEntries = function(glAccount) {
        $scope.existingSLEntries = [
            {'entity': {'accountNo': 12, 'name':'John Doe'}, 'amount' : 10, 'account': glAccount},
            {'entity': {'accountNo': 13, 'name':'Foo bar'}, 'amount' : 50, 'account': glAccount},
            {'entity': {'accountNo': 14, 'name':'When Ever'}, 'amount' : 30, 'account': glAccount}
        ];
    }

    loadExistingSlEntries($scope.glAccount); // implement using factory

    $scope.voucher_sl_setter_handler = function(newSlEntries) {
        $scope.existingSLEntries = newSlEntries;
        console.log(newSlEntries);
        }
}]);

app.controller('rvdCtrl', ['$scope', '$http', function($scope, $http) {
    $scope.rvd_selection_handler = function(rvDetail){
        $scope.selectedRvd1 = rvDetail;
    }
}]);