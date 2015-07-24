<div class="modal-header">
    <button type="button" class="close" ng-click="close()" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Browse Requisition Voucher Detail Items</h4>
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
                <div class="row" style='max-height: 500px; overflow: auto;'>
                    <div ng-show="!rvDetails">Loading Items...</div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                        <tr>
                            <th style="text-align: center">RV No.</th>
                            <th>Date</th>
                            <th>Item No.</th>
                            <th>Description</th>
                            <th>Quantity</th>
                            <th>Unit</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr data-dismiss="modal" ng-repeat="rvDetail in its = (rvDetails | filter:query)" style="cursor: pointer" ng-click="selectRvDetail(rvDetail)">
                            <td style="width: 300px;">{{rvDetail.rvNumber}}</td>
                            <td style="width: 300px;">{{rvDetail.rvDate | date:'MMM dd, yyyy'}}</td>
                            <td style="width: 480px;">{{rvDetail.itemCode}}</td>
                            <td style="width: 480px;">{{rvDetail.itemDescription}}</td>
                            <td style="max-width: 300px; text-align: right;">{{rvDetail.quantity}}</td>
                            <td style="max-width: 300px;">{{rvDetail.unitCode}}</td>
                        </tr>
                        <tr ng-show="its.length == 0"><td colspan="4" align="center">No records found</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
</div>