<h4 ng-show="!doneLoading">Loading voucher data...</h4>
<div>
    <a ng-click="main()" class="btn btn-primary">Back to list</a>
</div>
<div ng-show="doneLoading">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h2>{{canvass.localCode}}<span class="bold">{{canvass.vendor.name}}</span></h2>
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
                            <label class="value-label"><span class="label {{statusLabel}}">{{canvass.documentStatus.status}}</span></label>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Voucher Date</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{canvass.voucherDate | date:'MMM dd, yyyy'}}</label>
                        </div>
                    </div>
                </div>

            </div>

            <div class="col-md-6 col-lg-6">
                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Prepared By</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{canvass.createdBy.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">PBAC Chairman</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{canvass.approvedBy.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Last update</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{canvass.lastUpdated | date:'MMM dd, yyyy HH:mm a'}}</label>
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
                            <th style="width: 10%">Quantity</th>
                            <th>Unit</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="cnvsd in canvassItems">
                            <td style="text-align: center;">{{ cnvsd.itemCode }}</td>
                            <td>{{ cnvsd.itemDescription}}</td>
                            <td style="text-align: right;">{{cnvsd.quantity}}</td>
                            <td>{{ cnvsd.unitCode }}</td>
                        </tr>
                        </tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>