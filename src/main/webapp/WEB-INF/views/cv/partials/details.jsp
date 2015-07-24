<h4 ng-show="!doneLoading">Loading voucher data...</h4>
<div>
    <a ng-click="main()" class="btn btn-primary">Back to list</a>
</div>
<div ng-show="doneLoading">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-7 col-lg-7">
                <h2>{{cv.localCode}} <span class="bold"> {{cv.payee.name}}</span></h2>
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
                <a class="btn btn-primary" ng-click="print('pdf')">Print</a>
            </div>
            <div class="col-md-1 col-lg-1" style="padding-top: 20px;">
                <a class="btn btn-primary" ng-click="print2307('pdf')">2307</a>
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
                            <label class="value-label"><span class="label {{statusLabel}}">{{cv.documentStatus.status}}</span></label>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Voucher Date</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{cv.voucherDate | date:'MMM dd, yyyy'}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Particulars</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{cv.particulars}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Check amount</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{cv.checkAmount | currency : "" : 2}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Last updated</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">{{cv.lastUpdated | date:'MMM dd, yyyy HH:mm a'}}</label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-6">


                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Checker</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{cv.checker.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Recommending Approval</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{cv.recommendingOfficer.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Auditor</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{cv.auditor.name}}</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Approving Officer</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">{{cv.approvingOfficer.name}}</label>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <hr>

    <div class="row" style="margin-bottom: 10px">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h3>Journal Entries</h3>
            </div>
            <div class="col-md-4 col-lg-4">
                <a class="btn btn-primary pull-right" ng-click="printCheck()" assign-check handler="assign_check_handler" print="allowCheckPrinting" journal-entries="journalEntries" allow-assign="assignCheckAllowed" title='Assign check'><i class='fa fa-tasks'></i> Checks</a>
            </div>
        </div>
    </div>

    <div class="row">
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
                        <td><span ng-show="entry.hasSL" class="pull-right"><a><i ng-click="loadSL(entry,$index)" class="fa fa-chevron-circle-{{cSls[$index].length > 0 ? 'down' : 'right'}}" title="Show SL"></i></a></span>{{entry.code}}<span ng-show="entry.checkNumber" title="Check number" class="label label-info" style="margin-left: 10px"><a ng-click="printCheck(entry, $index)" assign-check handler="print_check_handler" check="e" account="ea" allow-assign="assignCheckAllowed" print="true" style='padding: 0' title='Update & Print check'>{{entry.checkNumber}}</a></span><br/>{{entry.description}}
                            <div ng-show="cSls[$index].length > 0" style="background-color: #dff0d8">
                                <ul class="list-unstyled" style="padding-left: 10px; padding-right: 10px; padding-bottom: 10px;">
                                    <li style="padding-top: 15px; border-bottom: 1px solid lightgrey" ng-repeat="ent in cSls[$index]">{{ent.name}}<span class="pull-right">{{ent.amount | currency : "" : 2}}</span></li>
                                </ul>
                            </div>
                        </td>
                        <td><span class="text-right form-control">{{entry.debit == 0 ? '' : (entry.debit | currency : "" : 2)}}</span></td>
                        <td><span class="text-right form-control">{{entry.credit == 0 ? '' : (entry.credit | currency : "" : 2)}}</span></td>
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