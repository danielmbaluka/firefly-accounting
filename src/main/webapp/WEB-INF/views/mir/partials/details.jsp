<h4 ng-show="!doneLoading">Loading voucher data...</h4>
<div>
    <a ng-click="main()" class="btn btn-primary">Back to list</a>
</div>
<div ng-show="doneLoading">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h2>{{mir.localCode}}</h2>
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
                            <label class="value-label"><span class="label {{statusLabel}}">{{mir.status}}</span></label>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Voucher Date</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{mir.voucherDate | date:'MMM dd, yyyy'}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Particulars</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{mir.particulars}}</label>
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
                            <label class="value-label">{{mir.amount | currency : "" : 2}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Checker</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{mir.sLChecker.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Approving Officer</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{mir.sLApprovingOfficer.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Last update</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{mir.lastUpdated | date:'MMM dd, yyyy HH:mm a'}}</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-lg-12">
            <h3>Journal Entries</h3>

            <div class="col-md-12 col-lg-12">
                <div class="table-responsive white-bg">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th width="60%">Account</th>
                            <th>Debit</th>
                            <th>Credit</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="entry in journalEntries">
                            <td><span ng-show="entry.hasSL" class="pull-right"><a><i ng-click="loadSL(entry,$index)" class="fa fa-chevron-circle-{{cSls[$index].length > 0 ? 'down' : 'right'}}" title="Show SL"></i></a></span>{{entry.segmentAccountCode}}<br/>{{entry.description}}
                                <div ng-show="cSls[$index].length > 0" style="background-color: #dff0d8">
                                    <ul class="list-unstyled" style="padding: 10px;">
                                        <li ng-repeat="ent in cSls[$index]">{{ent.name}}<span class="pull-right">{{ent.amount | currency : "" : 2}}</span></li>
                                    </ul>
                                </div>
                            </td>
                            <td><span class="text-right form-control">{{entry.debit | currency : "" : 2}}</span></td>
                            <td><span class="text-right form-control">{{entry.credit | currency : "" : 2}}</span></td>
                        </tr>

                        <tr>
                            <td class="font-bold">TOTAL</td>
                            <td class="text-right font-bold"><span style="margin-right: 12px; font-size: 16px;">{{journalTotals.debit}}</span></td>
                            <td class="text-right font-bold"><span style="margin-right: 12px; font-size: 16px;">{{journalTotals.credit}}</span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>