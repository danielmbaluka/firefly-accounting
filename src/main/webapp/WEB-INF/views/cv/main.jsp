<div ui-view autoscroll="true">
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Check Vouchers </h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-3">
                            <a ui-sref="cv.new" class="btn btn-primary">Create</a>
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
                                <th>Voucher date</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr  data-dismiss="modal" ng-repeat="cv in cvs = (vouchers | filter:query)">
                                <td>{{cv.code}}</td>
                                <td>{{cv.payee}}</td>
                                <td><span class="pull-right">{{cv.checkAmount | currency:""}}</span></td>
                                <td>{{cv.particulars}}</td>
                                <td>{{cv.voucherDate | date:'MMM dd, yyyy'}}</td>
                                <td><span class="label {{label(cv.status)}}">{{cv.status}}</span></td>
                                <td style="width: 5%; text-align: right">
                                    <a style='padding: 0' title='View' ui-sref=".detail({cvId:cv.id})"><i class='fa fa-search'></i></a>
                                    <a ng-show="['Document Created', 'Returned to creator'].indexOf(cv.status) >= 0" style='padding: 0' title='Edit' ui-sref=".edit({cvId:cv.id})"><i class='fa fa-edit'></i></a>
                                </td>
                            </tr>
                            <tr ng-show="cvs.length == 0"><td colspan="7" align="center">No records found</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
