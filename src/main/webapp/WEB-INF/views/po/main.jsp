<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Purchase Orders</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-2">
                            <a ui-sref="po.new" class="btn btn-primary">Create</a>
                        </div>
                        <div class="col-sm-3 pull-right">
                            <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>PO Number</th>
                                <th>Date</th>
                                <th>Supplier</th>
                                <th>Amount</th>
                                <th>Prepared By</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr data-dismiss="modal" ng-repeat="po in pos = (purchaseOrderList | filter:query)">
                                <td style="width:13%;">{{po.localCode}}</td>
                                <td style="width:10%;">{{po.voucherDate | date:'MMM dd, yyyy'}}</td>
                                <td>{{po.supplier}}</td>
                                <td style="text-align: right;">{{po.amount | currency:""}}</td>
                                <td>{{po.preparedBy}}</td>
                                <td><span class="label {{documentStatusUtil.classType(po.status)}}">{{po.status}}</span></td>
                                <td style="width: 3%; text-align: center">
                                    <a style='padding: 0' title='View' ui-sref=".detail({id:po.id})"><i class='fa fa-search'></i></a>
                                    <a style='padding: 0' title='Edit' ui-sref=".edit({id:po.id})"><i class='fa fa-edit'></i></a>
                                </td>
                            </tr>
                            <tr ng-show="pos.length == 0"><td colspan="7" align="center">No records found</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
