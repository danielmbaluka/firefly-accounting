<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Canvass</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-2">
                            <a ui-sref="canvass.new" class="btn btn-primary">Create</a>
                        </div>
                        <div class="col-sm-3 pull-right">
                            <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <div ng-show="!doneLoading">Loading CNVs...</div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>CNVS Number</th>
                                <th>Date</th>
                                <th>Supplier</th>
                                <th>Prepared By</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr data-dismiss="modal" ng-repeat="canvass in cnvs = (canvassList | filter:query)">
                                <td style="width:13%;">{{canvass.localCode}}</td>
                                <td style="width:10%;">{{canvass.voucherDate | date:'MMM dd, yyyy'}}</td>
                                <td>{{canvass.supplier}}</td>
                                <td>{{canvass.preparedBy}}</td>
                                <td style="width: 3%; text-align: center">
                                    <a style='padding: 0' title='View' ui-sref=".detail({id:canvass.id})"><i class='fa fa-search'></i></a>
                                    <a style='padding: 0' title='Edit' ui-sref=".edit({id:canvass.id})"><i class='fa fa-edit'></i></a>
                                </td>
                            </tr>
                            <tr ng-show="cnvs.length == 0"><td colspan="7" align="center">No records found</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
