<%-- use as angular directive --%>
<div class="modal-header">
    <button type="button" class="close" ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Browse accounts</h4>
</div>
<div class="modal-body">
    <div>
        <div class="row">
            <div class="input-group pull-right" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12">
                <div class="row-top-buffer" style="margin-top: 15px"/>
                <div class='row' style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                    <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 7px;">Code</span></div>
                    <div class="col-md-7 col-lg-7"><span style="font-weight: bold; padding-left: 80px;">Title</span></div>
                    <div class="col-md-2 col-lg-2"><span style="font-weight: bold">Type</span></div>
                </div>
                <div class="row-top-buffer" style="margin-top: 5px"/>
                <div class="row" style='max-height: 500px; overflow: auto;'>
                    <div ng-show="!accounts">Loading accounts...</div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="account in accs = (accounts | filter:query)" style="cursor: pointer" ng-click="select(account)">
                            <td class='code-col'>{{account.code}}</td>
                            <td>{{account.title}}</td>
                            <td class='type-col'>{{account.accountType.description}}</td>
                        </tr>
                        <tr ng-show="accounts.length == 0"><td colspan="3" align="center">No records found</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>