<div class="modal-header">
    <button type="button" class="close" ng-click="close()" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">MRCT to MIR Posting</h4>
</div>
<div class="modal-body">
    <div>
        <div ng-controller="addEditMrctToMirCtrl">
            <div class="row-top-buffer"></div>
            <form ng-submit="processForm()" ng-show="showForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="table-responsive white-bg" style="max-height: 400px; overflow: auto">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Description</th>
                            <th>Quantity</th>
                            <th>Unit</th>
                            <th>Price</th>
                            <th>Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="item in mrctDetails">
                            <td>{{item.description}}</td>
                            <td>{{item.quantity}}</td>
                            <td>{{item.unit}}</td>
                            <td>{{item.price}}</td>
                            <th>{{item.amount}}</th>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="row-top-buffer"></div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                            <label class="input-label" for="particulars">Particulars</label>
                        </div>
                        <div class="col-md-5 col-lg-5">
                            <div class="input-group">
                                <textarea  maxlength="1024" rows="5" cols="59" ng-model="mir.particulars" id="particulars"
                                           name="particulars" placeholder="Enter particulars"></textarea>
                            </div>
                        </div>
                        <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_particulars"></form-error></div>
                    </div>
                </div>

                <div class="row-top-buffer"></div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                            <label class="input-label" for="vdate">Voucher Date</label>
                        </div>
                        <div class="col-md-3 col-lg-3">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                                <input ng-model="mir.voucherDate" class="datepicker" type="text" class="input-sm form-control" id="vdate" name="vdate">
                            </div>
                        </div>
                        <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_voucherDate"></form-error></div>
                    </div>
                </div>

                <div class="row-top-buffer"></div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                            <label class="input-label">Journal Entries</label>
                        </div>
                        <div class="col-md-10 col-lg-10">
                            <div class="table-responsive white-bg" style="max-height: 400px; overflow: auto">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>

                                        <th style="width: 50%">Account</th>
                                        <th class="text-center"><span>Debit</span></th>
                                        <th class="text-center"><span>Credit</span></th>
                                        <th style="width: 60px"  class="text-center"><a ng-click="newBlankRow()" style='padding: 0' title='Add'><i class='fa fa-plus'></i></a></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="entry in journalEntries">
                                        <td>{{entry.segmentAccountCode}}<br/>{{entry.description}}</td>
                                        <td><input ng-model="entry.debit" ng-change="updateTotal(entry.debit, '{{entry.debit}}')" class="text-right form-control" type="text"/></td>
                                        <td><input ng-model="entry.credit" ng-change="updateTotal(entry.credit, '{{entry.credit}}')" class="text-right form-control" type="text"/></td>
                                        <td style="text-align: center"><a ng-click="setSelectedIdx($index)" account-browser-s handler="accounts_selection_handler" style='padding: 0' title='Account'><i class='fa fa-angellist'></i></a>&nbsp;&nbsp;<a ng-click="loadExistingSlEntries(entry)" voucher-sl-setter handler="voucher_sl_setter_handler" slentries="glSlEntries" glaccount="selectedGlAccount" slloaded="slLoaded" style='padding: 0' title='SL'><i class='fa fa-user'></i></a>&nbsp;&nbsp;<a ng-click="removeJournalRow($index, entry)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                                    </tr>

                                    <tr>
                                        <td class="font-bold">{{journalEntries.length == 0 ? "Add journal entries here!" : "TOTAL"}}</td>
                                        <td class="text-right font-bold"><span ng-show="journalEntries.length > 0"  style="margin-right: 12px; font-size: 16px;">{{journalTotals.debit}}</span></td>
                                        <td class="text-right font-bold"><span ng-show="journalEntries.length > 0"  style="margin-right: 12px; font-size: 16px;">{{journalTotals.credit}}</span></td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                        </div>
                        <div  class="col-md-10 col-lg-10"><form-error err_field="errors.err_journal"></form-error></div>
                    </div>
                </div>

                <div class="row-top-buffer"></div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                            <label class="input-label" for="checker">Checker</label>
                        </div>
                        <div class="col-md-5 col-lg-5">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                                <input disabled="" ng-model="mir.checker.name" id="checker" name="checker" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse checker"/>
                            </div>
                        </div>
                        <div class="col-md-1 col-lg-1">
                            <button sl-entity-browser
                                    handler="checker_selection_handler"
                                    types="signatories"
                                    class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse checker"></i>&nbsp;</button>
                        </div>
                        <div  class="col-md-3 col-lg-3"><form-error err_field="errors.err_checker"></form-error></div>
                    </div>
                </div>

                <div class="row-top-buffer"></div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                            <label class="input-label" for="approvar">Approving Officer</label>
                        </div>
                        <div class="col-md-5 col-lg-5">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                                <input disabled="" ng-model="mir.approvingOfficer.name" id="approvar" name="approvar" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse approving officer"/>
                            </div>
                        </div>
                        <div class="col-md-1 col-lg-1">
                            <button sl-entity-browser
                                    handler="approvar_selection_handler"
                                    types="signatories"
                                    class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse approving officer"></i>&nbsp;</button>
                        </div>
                        <div  class="col-md-3 col-lg-3"><form-error err_field="errors.err_approvingOfficer"></form-error></div>
                    </div>
                </div>

                <div class="row-top-buffer"></div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-2 col-lg-2">
                        </div>
                        <div class="col-md-5 col-lg-5">
                            <fieldset ng-disabled="submitting">
                                <button ng-mousedown="submit = true" type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> {{save}}</button>
                                <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(function () {
        $('.datepicker').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        }).datepicker("setDate", $.datepicker.formatDate('mm/dd/yy', new Date()));

        $('[data-toggle="popover"]').popover({ trigger: "hover", html: true })
    })
</script>

