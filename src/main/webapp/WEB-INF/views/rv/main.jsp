<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Requisition Issue Vouchers</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-2">
                            <a ui-sref="rv.newPO" class="btn btn-primary">Create For PO</a>
                        </div>
                        <div class="col-sm-2">
                            <a ui-sref="rv.newIT" class="btn btn-primary">Create For IT</a>
                        </div>
                        <div class="col-sm-2">
                            <a ui-sref="rv.newRep" class="btn btn-primary">Create For Repair</a>
                        </div>
                        <div class="col-sm-2">
                            <a ui-sref="rv.newLab" class="btn btn-primary">Create For Labor</a>
                        </div>
                        <div class="col-sm-3 pull-right">
                            <div class="input-group"><input type="text" placeholder="Search" ng-model="query" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <div ng-show="!doneLoading">Loading RVs...</div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>RV Number</th>
                                <th>Delivery Date</th>
                                <th>Purpose</th>
                                <th>Type</th>
                                <th>Date</th>
                                <th>Prepared By</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr data-dismiss="modal" ng-repeat="requisitionVoucher in rvs = (requisitionVouchers | filter:query)">
                                <td style="width:13%;">{{requisitionVoucher.localCode}}</td>
                                <td style="width:10%;">{{requisitionVoucher.deliveryDate | date:'MMM dd, yyyy'}}</td>
                                <td>{{requisitionVoucher.purpose}}</td>
                                <td style="width:6%;">{{requisitionVoucher.rvType}}</td>
                                <td style="width:10%;">{{requisitionVoucher.voucherDate | date:'MMM dd, yyyy'}}</td>
                                <td>{{requisitionVoucher.preparedBy}}</td>
                                <td><span class="label {{label(requisitionVoucher.status)}}">{{requisitionVoucher.status}}</span></td>
                                <td style="width: 3%; text-align: center">
                                    <a style='padding: 0' title='View' ui-sref=".detail({id:requisitionVoucher.id})"><i class='fa fa-search'></i></a>
                                    <a style='padding: 0' title='Edit' ui-sref="{{getURL(requisitionVoucher.rvTypeId, requisitionVoucher.id)}}"><i class='fa fa-edit'></i></a>
                                </td>
                            </tr>
                            <tr ng-show="rvs.length == 0"><td colspan="7" align="center">No records found</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
