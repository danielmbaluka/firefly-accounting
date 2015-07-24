
<div ng-controller="addEditCvCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="cv" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}} <span class="label label-success">{{cv.code}}</span></div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="apvLabel" id="apv" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse payable vouchers"/>
                    </div>
                </div>
                <div class="col-md-2 col-lg-2">
                    <button apv-browser
                            handler="apv_selection_handler"
                            allow-apv-select="allowApvSelect"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse payable vouchers"></i>&nbsp;</button>
                    <button ng-click="clearApv()" class="btn btn-danger">&nbsp;<i class="fa fa-minus" title="Clear selection"></i>&nbsp;</button>
                </div>
                <div  class="col-md-3 col-lg-3"><form-error err_field="errors.err_apv"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="payee">Payee</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="cv.payee.name" id="payee" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse payee"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button sl-entity-browser
                            handler="payee_selection_handler"
                            types="payees"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse payee"></i>&nbsp;</button>
                </div>
                <div  class="col-md-3 col-lg-3"><form-error err_field="errors.err_payee"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="checkAmount">Check Amount</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <input class="text-right form-control ng-pristine ng-valid ng-touched" type="text" id="checkAmount" name="checkAmount" ng-model="cv.checkAmount" placeholder="Enter check amount"/>
                    </div>
                </div>
                <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_checkAmount"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="particulars">Particulars</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <textarea  maxlength="1024" rows="5" cols="59" ng-model="cv.particulars" id="particulars"
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
                        <input ng-model="cv.voucherDate" class="datepicker" type="text" class="input-sm form-control" id="vdate" name="vdate">
                    </div>
                </div>
                <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_voucherDate"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <journal-entries
                trans-id="cv.transId"
                voucher-id="cv.id"
                journal-entries="journalEntries"
                entities="payees"
                sub-ledger-lines="subLedgerLines"
                voucher-date="mysqlFormatDate"
                tax-codes = "taxCodes"
                ></journal-entries>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div  class="col-md-8 col-lg-8" style="margin-top: 10px;"><form-error err_field="errors.err_generalLedgerLines"></form-error></div>
                <div  class="col-md-2 col-lg-2" style="margin-top: 10px;">
                    <div class="pull-right">
                        <a class="btn btn-primary" ng-click="assignCheck()" assign-check handler="assign_check_handler" print="false" journal-entries="journalEntries" allow-assign="assignCheckAllowed" title='Assign check number'><i class='fa fa-tasks'></i></a>
                    </div>
                </div>
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
                        <input disabled="" ng-model="cv.checker.name" id="checker" name="checker" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse checker"/>
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
                    <label class="input-label" for="recommendar">Recommending Officer</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="cv.recommendingOfficer.name" id="recommendar" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse recommending officer"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button sl-entity-browser
                            handler="recommendar_selection_handler"
                            types="signatories"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse recommending officer"></i>&nbsp;</button>
                </div>
                <div  class="col-md-3 col-lg-3"><form-error err_field="errors.err_recommendingOfficer"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="auditor">Auditor</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="cv.auditor.name" id="auditor" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse auditor"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button sl-entity-browser
                            handler="auditor_selection_handler"
                            types="signatories"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse auditor"></i>&nbsp;</button>
                </div>
                <div  class="col-md-3 col-lg-3"><form-error err_field="errors.err_auditor"></form-error></div>
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
                        <input disabled="" ng-model="cv.approvingOfficer.name" id="approvar" name="approvar" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse approving officer"/>
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

<div style="margin-bottom: 100px;" />


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

