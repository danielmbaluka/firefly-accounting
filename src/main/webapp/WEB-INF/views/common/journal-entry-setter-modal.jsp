<%-- use as angular directive --%>
<div class="modal-header">
    <button type="button" class="close" ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Browse accounts</h4>
</div>
<div class="modal-body">
    <div>
        <div class="row" style="border-bottom: 1px solid #dcdcdc; padding-bottom: 10px">
            <div class="input-group pull-left" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-md-8">
                <div class="row-top-buffer" style="margin-top: 15px"/>
                <div class='row'>
                    <div class="col-md-1 col-lg-1"><span style="font-weight: bold; padding-left: 7px;">Code</span></div>
                    <div class="col-md-9 col-lg-9"><span style="font-weight: bold; padding-left: 80px;">Title</span></div>
                    <div class="col-md-1 col-lg-1"><span style="font-weight: bold">Type</span></div>
                </div>
                <div class="row-top-buffer" style="margin-top: 5px"/>
                <div class="row" style='max-height: 500px; overflow: auto;'>
                    <div ng-show="!accounts">Loading accounts...</div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="account in accs = (accounts | filter:query)" style="cursor: pointer" ng-click="select(account)">
                            <td style="width: 20%">{{account.code}}</td>
                            <td style="width: 55%">{{account.title}}</td>
                            <td style="width: 25%">{{account.accountType.description}}</td>
                        </tr>
                        <tr ng-show="accounts.length == 0"><td colspan="3" align="center">No records found</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-lg-4 col-md-4">
                <div class="row" style="margin-left: 1px; margin-top: 3px;">
                    <div class="panel panel-primary" ng-show="!noSelectedAccount()">
                        <div class="panel-heading">
                            <div class="font-bold">{{selectedAccount.code}}<br/>{{selectedAccount.title}}</div></div>
                        <div class="panel-body">
                            <div class="col-lg-12 col-md-12" style="padding: 0;">
                                <div class="col-lg-2 col-md-2" style="padding-left: 5px;">
                                    <label for="amount" style="padding-top: 10px;">Amount</label>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                                        <input ng-blur="accountAmountChanged()" ng-model="selectedAccount.amount" class="text-right form-control" id="amount" type="text" value="{{selectedAccount.amount}}"/>
                                    </div>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                    <div class="inline-group">
                                        <label style="cursor: pointer" title="Check - Debit, Uncheck - Credit"><input  ng-click="toggleNormalBalance()" ng-checked="debit" type="checkbox" id="normalBalance" name="normalBalance" checked=""> Debit</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row"  style="margin-left: 1px;">
                    <div class="panel panel-primary">
                        <div class="panel-heading"><label style="cursor: pointer"><input  ng-click="toggleAutoAllocation()" ng-checked="autoAllocation" type="checkbox" id="auto" name="auto" checked=""> Apply allocation factor automatic distribution</label></div>
                        <div class="panel-body" style="padding-bottom: 0">
                            <div class="row">
                                <span ng-show="!noSelectedAccount() && segmentAllocation.length == 0" style="color: red; margin-right: 15px"> No segment allocation factors</span>
                                <ul class="list-unstyled" style="padding-left: 5px;">
                                    <li ng-repeat="s in segmentAllocation" style="margin-bottom: 10px;">
                                        <div class="row">
                                            <div class="col-lg-12 col-md-12">
                                                <div>
                                                    <p><span style="font-weight:  normal; font-size: 13px;" class="label">{{s.segmentDescription}} <span style="padding: 3px;" class="badge-info">{{s.percentage}}%</span></span></p>
                                                    <p><input ng-blur="segmentAmountChanged(s.amount, $index)" ng-model="s.amount" class="text-right form-control" style="width: 50%" type="text"/></p>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row" style="margin-left: 1px;" ng-show="taxCodes || taxCodes.length > 0">
                    <div class="panel panel-primary">
                        <div class="panel-heading"><label style="cursor: pointer"><input  ng-click="toggleWTax()" ng-checked="isWTax" type="checkbox" id="isWTax" name="isWTax" checked=""> Setup Withholding Tax</label></div>
                        <div class="panel-body" ng-show="isWTax">
                            <div class="row">
                                <div class="col-md-2 col-lg-2">
                                    <label style="margin-top: 8px;">Account:</label>
                                </div>
                                <div class="col-md-10 col-lg-10">
                                    <div class="inline-group">
                                        <label style="margin-top: 8px;">{{wtax.account.code}} {{wtax.account.title}}</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-1 col-lg-1">
                                    <label style="margin-top: 8px;">ATC:&nbsp;&nbsp;</label>
                                </div>
                                <div class="col-md-11 col-lg-11">
                                    <div class="inline-group">
                                        <select required="" ng-change="setSelectedATC(selectedATC)" class="form-control" id="atc" ng-model="selectedATC"
                                                ng-options="(tc.code + ' - ' + tc.description) for tc in taxCodes track by tc.id">
                                            <option value="">Select ATC</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row row-top-buffer">
                                <div class="col-md-2 col-lg-2">
                                    <label style="margin-top: 8px;">Percent:</label>
                                </div>
                                <div class="col-md-6 col-lg-6">
                                    <div class="inline-group">
                                        <input ng-blur="wtaxPercentageChanged(wtax.percentage)" ng-model="wtax.percentage" class="text-right form-control"  placeholder="Enter percentage" type="number" min="0.01" max="100"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row row-top-buffer">
                                <div class="col-md-2 col-lg-2">
                                    <label style="margin-top: 8px;">Amount:</label>
                                </div>
                                <div class="col-md-10 col-lg-10">
                                    <div class="inline-group">
                                        <label style="margin-top: 8px;">{{wtax.amount | currency:"":2}}</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" style="margin-left: 1px;" ng-show="segmentAllocation.length > 0">
                    <div class="panel panel-primary">
                        <div class="panel-heading"><label>SL Entity Entries</label></div>
                        <div class="panel-body" style="margin: 0 !important; padding: 0 !important;">
                            <table class="table table-striped table-hover table-bordered table-responsive" style="margin-bottom: 0">
                                <thead>
                                <tr>
                                    <td>Entities</td>
                                    <td style="width: 25%"><span class="pull-right">Amount</span></td>
                                    <td style="width: 7%"><a ng-click="newBlankRow()" style='padding: 0' title='Add'><i class='text-center fa fa-plus'></i></a></td>
                                </tr>
                                </thead>
                                <tbody>
                                <tr data-dismiss="modal" ng-repeat="entry in slentries" style="cursor: pointer">
                                    <td class='code-col'>{{entry.name}}</td>
                                    <td><input ng-model="entry.amount" ng-change="updateSLTotal(entry.amount, '{{entry.amount}}')" class="text-right form-control" type="text"/></td>
                                    <td style="text-align: center"><a ng-click="setSelectedIdx($index)" sl-entity-browser handler="entity_selection_handler" types="entities" ><i style="padding-bottom: 7px;" class="fa fa-chevron-up" title="Browse entity"></i></a><a ng-click="removeEntryRow($index)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                                </tr>
                                <tr class="{{(selectedAccount.amount != slTotalAmount) ? 'sl-setter-total-red' : ''}}">
                                    <td class="font-bold">TOTAL</td>
                                    <td class="text-right font-bold"><span style="margin-right: 12px; font-size: 16px;">{{slTotalAmount}} / {{selectedAccount.amount}}</span></td>
                                    <td></td>
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
<div class="modal-footer">
    <button ng-click="close()" type="button" class="btn btn-default">Cancel</button>
    <button ng-click="saveEntries()" type="button" class="btn btn-success">Save</button>
</div>