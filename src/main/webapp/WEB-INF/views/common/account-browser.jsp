<div class="modal-dialog modal-lg">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title" id="myModalLabel">Browse accounts</h4>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="input-group" style="width: 300px">
                    <input class="form-control" placeholder="Search" ng-model="query" />
                    <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                </div>
            </div>
            <div class="row-top-buffer" style="margin-top: 15px"/>
            <div class='row' style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 7px;">Code</span></div>
                <div class="col-md-7 col-lg-7"><span style="font-weight: bold; padding-left: 80px;">Title</span></div>
                <div class="col-md-2 col-lg-2"><span style="font-weight: bold">Type</span></div>
            </div>
            <div class="row-top-buffer" style="margin-top: 5px"/>
            <div class="row" style='max-height: 500px; overflow: auto;'>
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="account in accounts = (parentAccounts | filter:query)" style="cursor: pointer" ng-click="accountSelectedFromBrowser(account)">
                        <td class='code-col'>{{account.code}}</td>
                        <td>{{account.title}}</td>
                        <td class='type-col'>{{account.accountType.description}}</td>
                    </tr>
                    <tr ng-show="!parentAccounts || accounts.length == 0"><td colspan="3" align="center">No records found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
    </div>
</div>