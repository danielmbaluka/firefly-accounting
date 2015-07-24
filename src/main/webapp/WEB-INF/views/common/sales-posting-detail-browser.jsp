
<div class="modal-header">
    <button type="button" class="close" ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Sales Voucher Details</h4>
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
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>Account</th>
                        <th>Debit</th>
                        <th>Credit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-dismiss="modal" ng-repeat="item in selectedSP.details | filter:query" style="cursor: pointer">
                        <td style="width: 500px;">{{item.account}}</td>
                        <td style="width: 100px;">{{item.debit}}</td>
                        <td style="width: 100px;">{{item.credit}}</td>
                    </tr>
                    <tr ng-show="selectedSP.details.length == 0"><td colspan="3" align="center">No records found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="modal-footer">
            <button type="button" class="btn btn-primary">Ok</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        </div>
    </div>
</div>
</div>