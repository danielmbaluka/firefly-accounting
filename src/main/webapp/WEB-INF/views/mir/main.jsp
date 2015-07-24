<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Posted Material Request Tickets</h5>
                </div>
                <div ng-controller="mirCtrl">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3">
                                <a ui-sref="mir.mrctList" class="btn btn-primary">Browse MRCT</a>
                            </div>
                            <div class="col-sm-3 pull-right">
                                <div class="input-group"><input ng-model="code" type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                            </div>
                        </div>


                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>Code</th>
                                    <th>Date</th>
                                    <th>Particulars</th>
                                    <th>User</th>
                                    <th>Checked By</th>
                                    <th>Approved By</th>
                                    <th>Status</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr ng-repeat="item in mrctList | filter: code ">
                                    <td>{{item.localCode}}</td>
                                    <td>{{item.voucherDate | date:'MMM dd, yyyy'}}</td>
                                    <td>{{item.particulars}}</td>
                                    <td>{{item.createdBy.fullName}}</td>
                                    <td>{{item.checker.fullName}}</td>
                                    <td>{{item.approvingOfficer.fullName}}</td>
                                    <td><span class="label {{documentStatusUtil.classType(item.status)}}">{{item.status}}</span></td>
                                    <td style='width: 8%; vertical-align: middle'>
                                        <a style='padding: 0' title='View' ui-sref=".detail({id:item.id})"><i class='fa fa-search'></i></a>
                                        <a style='padding: 0' title='Edit' ui-sref=".edit({id:item.id})"><i class='fa fa-edit'></i></a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
