<h4 ng-show="!doneLoading">Loading voucher data...</h4>
<div>
    <a ng-click="main()" class="btn btn-primary">Back to list</a>
</div>
<div ng-show="doneLoading">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h2>{{po.localCode}} <span class="bold"> {{po.vendor.name}}</span></h2>
            </div>

            <div class="col-md-3 col-lg-3">
                <div class="input-group" style="padding-top: 20px;" ng-show="actions && actions.length > 0">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <select ng-change="setSelectedAction(selectedAction)" class="form-control" id="actions" ng-model="selectedAction"
                            ng-options="action.action for action in actions track by action.actionMapId">
                        <option value="">Select Action</option>
                    </select>
                </div>
            </div>
            <div class="col-md-1 col-lg-1" style="padding-top: 20px;">
                <a class="btn btn-primary" target="_blank" ng-href="{{url}}"  ng-mousedown="print('pdf')">Print</a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <h3>Details</h3>
            <div class="col-md-6 col-lg-6">
                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Status</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label"><span class="label {{statusLabel}}">{{po.documentStatus.status}}</span></label>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Voucher Date</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{po.voucherDate | date:'MMM dd, yyyy'}}</label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-6">
                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Amount</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{po.amount | currency : "" : 2}}</label>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Requested By</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{po.createdBy.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Noted By</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{po.notedBy.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Approved By</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{po.approvedBy.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Last update</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{po.lastUpdated | date:'MMM dd, yyyy HH:mm a'}}</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-lg-12">
            <h3>Items</h3>

            <div class="col-md-12 col-lg-12">
                <div class="table-responsive white-bg">
                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th style="text-align: center">Item No.</th>
                            <th>Description</th>
                            <th style="width: 10%; text-align: center">Quantity</th>
                            <th>Unit</th>
                            <th style="text-align: center">Price</th>
                            <th style="text-align: center">Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="pod in podItems">
                            <td style="text-align: center;">{{ $index + 1 }}</td>
                            <td>{{pod.itemDescription}}</td>
                            <td style="text-align: right;">{{pod.quantity}}</td>
                            <td>{{ pod.unitCode }}</td>
                            <td style="text-align: right;">{{pod.unitPrice | currency : "" : 2}}</td>
                            <td style="text-align: right;">{{pod.itemAmount | currency : "" : 2}}</td>
                        </tr>
                        </tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>