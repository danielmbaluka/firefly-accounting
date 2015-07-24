<div ui-view autoscroll="true">
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Check Vouchers for Check Releasing</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-3">
                        </div>
                        <div class="col-sm-3 pull-right">
                            <div class="input-group"><input ng-model="query" type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <div ng-show="!doneLoading">Loading vouchers...</div>
                        <table class="table table-striped">
                            <thead>
                            <tr>

                                <th>Code</th>
                                <th>Payee</th>
                                <th><span class="pull-right">Check amount</span></th>
                                <th>Particulars</th>
                                <th>Remarks</th>
                                <th>Voucher date</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr  data-dismiss="modal" ng-repeat="v in vs = (vouchers | filter:query)">
                                <td>{{v.code}}</td>
                                <td>{{v.payee}}</td>
                                <td><span class="pull-right">{{v.checkAmount | currency:""}}</span></td>
                                <td>{{v.particulars}}</td>
                                <td>{{v.remarks}}</td>
                                <td>{{v.voucherDate | date:'MMM dd, yyyy'}}</td>
                                <td style="width: 5%; text-align: right">
                                    <a target="_blank" style='padding: 0' title='View' ui-sref="cv.detail({cvId:v.id})"><i class='fa fa-search'></i></a>
                                    <a style='padding: 0' title='Edit' ui-sref=".release({transId:v.transId, cvId:v.id})"><i class='fa fa-ambulance'></i></a>
                                </td>
                            </tr>
                            <tr ng-show="vs.length == 0"><td colspan="7" align="center">No records found</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
