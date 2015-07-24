var reportsAccounting = angular.module('reportAccounting', [
    'jvFactory',
    'cvFactory',
    'errorHandlerService',
    'jQueryFnWrapperService',
    'utilService'
]);

reportsAccounting.controller('accountingReportsMainCtrl', ['$scope', '$state', function($scope, $state) {
        $scope.title = 'Accounting Reports';

        $scope.finStatementReportTypes = [
            {"id": 1, "toString" : "Trial Balance", "uiSref": "reportsAccounting.trialBalance"},
            {"id": 2, "toString" : "Balance Sheet", "uiSref": "reportsAccounting.trialBalance"},
            {"id": 3, "toString" : "Income Statement", "uiSref": "reportsAccounting.trialBalance"}
        ];

        $scope.registersReportTypes = [
            {"id": 1, "toString" : "Journal Voucher Registers", "uiSref": "reportsAccounting.jvRegister"},
            {"id": 2, "toString" : "Check Voucher Registers", "uiSref": "reportsAccounting.cvRegister"},
            {"id": 3, "toString" : "Accounts Payable Voucher Registers", "uiSref": "reportsAccounting.apvRegister"}
        ];

        $scope.loadFilter = function(reportType) {
            if (reportType !== undefined ){
                $state.go(reportType.uiSref)
            }
        }

    }]
);

reportsAccounting.controller('trialBalanceCtrl', ['$http', '$scope', 'errorToElementBinder', 'csrf',
    function($http, $scope) {
        $scope.title = 'Trial Balance';
        $scope.save = 'Save';
        $scope.showForm = true;
    }]
);

reportsAccounting.controller('registerCtrl', ['$http', '$scope', '$stateParams', 'errorToElementBinder', 'dateUtil', 'jvFactory', 'cvFactory',
    function($http, $scope, $stateParams, errorToElementBinder, dateUtil, jvFactory, cvFactory) {
        $scope.title = $stateParams.title;
        $scope.showForm = false;
        $scope.data = [];

        $scope.firstDateOfMonth = $.datepicker.formatDate('mm/dd/yy', dateUtil.firstDateOfMonth());
        $scope.lastDateOfMonth = $.datepicker.formatDate('mm/dd/yy', dateUtil.lastDateOfMonth());


        var getRange = function() {
            var from = new Date($scope.firstDateOfMonth);
            var to = new Date($scope.lastDateOfMonth);

            var mysqlFormatFrom = $.datepicker.formatDate('yy-mm-dd',from);
            var mysqlFormatTo = $.datepicker.formatDate('yy-mm-dd', to);

            return {'from' : mysqlFormatFrom, 'to': mysqlFormatTo};

        }
        $scope.search = function() {

            var range = getRange();
            var req = undefined;

            switch($stateParams.exportUrl) {
                case "jv-register":
                    req = jvFactory.getListForRegister(range.from, range.to);
                    break;
                case "cv-register":
                    req = cvFactory.getListForRegister(range.from, range.to);
                    break;
                default:
                    console.log('There should not be a default failover at this thing.');
            }

            if (req !== undefined) {
                req.success(function (data) {
                    $scope.data = data;
                    $scope.showForm = true;
                }).error(function (error) {
                    toastr.error('Failed to load data.');
                    $scope.showForm = true;
                });
            }

        }

        $scope.search();

        $scope.label = function(status) {
            return (documentStatusUtil.classType(status));
        }

        $scope.print = function(type) {
            $http.get('/download/token').success(function(response) {
                // Store token
                var token = response.message[0];

                var range = getRange();
                // Start download
                $scope.url = '/reports/export/' + $stateParams.exportUrl + '/' + range.from + '/' + range.to + '?token='+token+'&type='+type;
                var newTab = window.open($scope.url, '_blank');
                newTab.focus();
            });
        }

    }]
);

