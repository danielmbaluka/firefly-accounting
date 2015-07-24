
<div ng-controller="addEditJvCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="jv" class="btn btn-primary">Back to list</a>
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
                <div class="col-md-7 col-lg-7">
                    <div ng-show="!tempBatch">
                        You may select temporary GL/SL
                    </div>
                    <div ng-show="tempBatch">
                        <div class="row">
                            <div class="col-md-2 col-lg-2">
                                <label>Document</label>
                            </div>
                            <div class="col-md-9 col-lg-9">
                                {{tempBatch.docTypeDesc}}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2 col-lg-2">
                                <label>Remarks</label>
                            </div>
                            <div class="col-md-9 col-lg-9">
                                {{tempBatch.remarks}}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2 col-lg-2">
                                <label>Amount</label>
                            </div>
                            <div class="col-md-9 col-lg-9">
                                {{tempBatch.amount}}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2 col-lg-2">
                                <label>Date</label>
                            </div>
                            <div class="col-md-9 col-lg-9">
                                {{tempBatch.tempBatchDate | date:'MMM dd, yyyy'}}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-lg-3  pull-right">
                    <div class="pull-right">
                        <button temp-ledger-entry-selector
                                handler="temp_selection_handler"
                                class="btn btn-primary"><i class="fa fa-chevron-up"
                                                                 title="Browse temporary journal entries">&nbsp;Open Temp</i>&nbsp;</button>
                        <button ng-click="clearTemp()" class="btn btn-danger">&nbsp;<i class="fa fa-minus" title="Clear temp selection"></i>&nbsp;</button>
                    </div>
                </div>
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
                        <input ng-model="jv.voucherDate" class="datepicker" type="text" class="input-sm form-control" id="vdate" name="vdate">
                    </div>
                </div>
                <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_voucherDate"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="explanation">Explanation</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <textarea  maxlength="1024" rows="5" cols="59" ng-model="jv.explanation" id="explanation"
                                   name="explanation" placeholder="Enter explanation"></textarea>
                    </div>
                </div>
                <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_explanation"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <journal-entries
                trans-id="jv.transId"
                voucher-id="jv.id"
                journal-entries="journalEntries"
                entities="entities"
                sub-ledger-lines="subLedgerLines"
                voucher-date="mysqlFormatDate"
                ></journal-entries>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div  class="col-md-8 col-lg-8" style="margin-top: 10px;"><form-error err_field="errors.err_generalLedgerLines"></form-error></div>
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
                        <input disabled="" ng-model="jv.checker.name" id="checker" name="checker" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse checker"/>
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
                        <input disabled="" ng-model="jv.recommendingOfficer.name" id="recommendar" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse recommending officer"/>
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
                        <input disabled="" ng-model="jv.auditor.name" id="auditor" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse auditor"/>
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
                        <input disabled="" ng-model="jv.approvingOfficer.name" id="approvar" name="approvar" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse approving officer"/>
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
        $(document).find('.datepicker').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        }).datepicker("setDate", $.datepicker.formatDate('mm/dd/yy', new Date()));
    })
</script>

