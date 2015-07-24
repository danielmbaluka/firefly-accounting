<div ng-controller="addEditRvForPOCtrl">
<div class="row-top-buffer"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ui-sref="rv" class="btn btn-primary">Back to list</a>
    </div>
</div>
<div class="row-top-buffer"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="alert alert-info">{{title}} <span class="label label-success">{{rv.localCode}}</span></div>
    </div>
</div>
<form ng-submit="processForm()" ng-show="showForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="rvdate">Date</label>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="input-group date">
                    <span class="input-group-addon">
                        <i class="fa fa-calendar">
                        </i>
                    </span>
                    <input ng-model="rv.voucherDate" class="datepicker" type="text" class="input-sm form-control" id="rvdate" name="rvdate" value="05/14/2014">
                </div>
            </div>
            <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_rvDate"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="deldate">Del. Date</label>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="input-group date">
                    <span class="input-group-addon">
                        <i class="fa fa-calendar">
                        </i>
                    </span>
                    <input ng-model="rv.deliveryDate" class="datepicker" type="text" class="input-sm form-control" id="deldate" name="deldate" value="05/14/2014">
                </div>
            </div>
            <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_deliveryDate"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="purpose">Reason(s)/Purpose(s)</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group">
                    <textarea ng-model="rv.purpose" maxlength="1024" rows="5" cols="59" ng-model="apv.particulars" id="purpose"
                               name="purpose" placeholder="Enter purpose"></textarea>
                </div>
            </div>
            <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_purpose"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label">Items to be Requested</label>
            </div>
            <div class="col-md-7 col-lg-7">
                <div class="table-responsive white-bg">
                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th style="text-align: center">Item No.</th>
                            <th>Description</th>
                            <th>Quantity</th>
                            <th>Unit</th>
                            <th>
                                <button class="btn btn-primary" item-browser handler="item_selection_handler" >Add item</button>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="item in poItems">
                                <td>{{ item.itemCode }}</td>
                                <td>{{ item.itemDescription }}</td>
                                <td style="width: 15%"><input style="width: 90%; text-align: right;" type="number" min="1" ng-model="item.quantity"></td>
                                <td>{{ item.unitCode }}</td>
                                <td style="text-align: center"><a ng-click="test($index)" item-browser handler="item_selection_handler2" style='padding: 0' title='Update'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a ng-click="removeRow($index)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
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
            <div  class="col-md-10 col-lg-10"><form-error err_field="errors.err_poItems"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="recapp">Rec. Approval</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input disabled="" ng-model="rv.recAppBy.name" id="recapp" name="recapp" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Rec. Approval Officer"/>
                </div>
            </div>
            <div class="col-md-1 col-lg-1">
                <button sl-entity-browser
                        handler="recapp_selection_handler"
                        types="signatories"
                        class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Rec. Approval Officer"></i>&nbsp;</button>
            </div>
            <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_recAppOfficer"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="check">Checked By</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input disabled="" ng-model="rv.checkedBy.name" id="check" name="check" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Checking Officer"/>
                </div>
            </div>
            <div class="col-md-1 col-lg-1">
                <button sl-entity-browser
                        handler="checker_selection_handler"
                        types="signatories"
                        class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Checking Officer"></i>&nbsp;</button>
            </div>
            <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_checker"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="audit">Audit By</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input disabled="" ng-model="rv.auditedBy.name" id="audit" name="audit" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Auditing Officer"/>
                </div>
            </div>
            <div class="col-md-1 col-lg-1">
                <button sl-entity-browser
                        handler="auditor_selection_handler"
                        types="signatories"
                        class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Auditing Officer"></i>&nbsp;</button>
            </div>
            <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_auditor"></form-error></div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="approve">Approved By</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input disabled="" ng-model="rv.approvedBy.name" id="approve" name="approve" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Approving Officer"/>
                </div>
            </div>
            <div class="col-md-1 col-lg-1">
                <button sl-entity-browser
                        handler="app_selection_handler"
                        types="signatories"
                        class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Approving Officer"></i>&nbsp;</button>
            </div>
            <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_appOfficer"></form-error></div>
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
    $('.datepicker').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    }).datepicker("setDate", $.datepicker.formatDate('mm/dd/yy', new Date()));

    $(function () {
        $('[data-toggle="popover"]').popover({ trigger: "hover", html: true })
    })
</script>