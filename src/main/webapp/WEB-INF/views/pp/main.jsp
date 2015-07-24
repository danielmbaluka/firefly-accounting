<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Prepayments</h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-2">
                            <a ui-sref="pp.new" class="btn btn-primary">Create</a>
                        </div>
                        <div class="col-sm-3 pull-right">
                            <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <div ng-show="!doneLoading">Loading Prepayments...</div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Description</th>
                                <th>Date</th>
                                <th style="text-align: center;">Total Cost</th>
                                <th style="text-align: center;">Months</th>
                                <th style="text-align: center;">Monthly Cost</th>
                                <th style="text-align: center;">Applied Cost</th>
                                <th style="text-align: center;">Balance</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr data-dismiss="modal" ng-repeat="prepayment in pps = (prepaymentList | filter:query)">
                                <td style="width:30%;">{{prepayment.description}}</td>
                                <td style="width:10%;">{{prepayment.datePaid | date:'MMM dd, yyyy'}}</td>
                                <td style="text-align: right;">{{prepayment.totalCost | currency:""}}</td>
                                <td style="width:6%; text-align: right;">{{prepayment.noOfMonths}}</td>
                                <td style="text-align: right;">{{prepayment.monthlyCost | currency:""}}</td>
                                <td style="text-align: right;">{{prepayment.appliedCost | currency:""}}</td>
                                <td style="text-align: right;">{{prepayment.balance | currency:""}}</td>
                                <td style="width: 3%; text-align: center">
                                    <a style='padding: 0' title='View' ui-sref=".detail({id:prepayment.id})"><i class='fa fa-search'></i></a>
                                    <a style='padding: 0' title='Edit' ui-sref=".edit({id:prepayment.id})"><i class='fa fa-edit'></i></a>
                                </td>
                            </tr>
                            <tr ng-show="pps.length == 0"><td colspan="7" align="center">No records found</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
