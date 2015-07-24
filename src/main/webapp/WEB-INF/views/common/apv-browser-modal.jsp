<div class="modal-header">
    <button type="button" class="close"  ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Select Payable Vouchers</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="input-group pull-right" style="width: 300px">
            <input class="form-control ng-pristine ng-untouched ng-valid" placeholder="Search" ng-model="query">
            <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
        </div>
    </div>
    <div class="row" style="max-height: 500px; overflow: auto">
        <div class="table-responsive">
            <div ng-show="!doneLoading">Loading vouchers...</div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Supplier</th>
                    <th><span class="pull-right">Amount</span></th>
                    <th>Particulars</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <tr  style="cursor: pointer" data-dismiss="modal" ng-repeat="apv in apvs = (vouchers | filter:query)" ng-click="select(apv)">
                    <td>{{apv.localCode}}</td>
                    <td>{{apv.supplier}}</td>
                    <td><span class="pull-right">{{apv.amount | currency:""}}</span></td>
                    <td>{{apv.particulars}}</td>
                    <td>{{apv.date | date:'MMM dd, yyyy'}}</td>
                </tr>
                <tr ng-show="apvs.length == 0"><td colspan="7" align="center">No records found</td></tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal" ng-click="cancel()">Cancel</button>
</div>