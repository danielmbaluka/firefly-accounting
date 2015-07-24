<div ng-controller="addEditCaCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="ca" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}}</div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="cadate">Date</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group date">
                            <span class="input-group-addon">
                                <i class="fa fa-calendar">
                                </i>
                            </span>
                        <input class="datepicker" type="text" class="input-sm form-control" id="cadate" name="cadate" value="05/14/2014">
                    </div>
                </div>
                <form-error err_field="errors.err_payee"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="name">Employee Name</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="selectedSupplier.name" id="name" name="name" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse supplier"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button sl-entity-browser
                            handler="entity_selection_handler"
                            types="entityTypes1"
                            class="btn btn-primary">Browse Employee</button>
                </div>
                <form-error err_field="errors.err_supplier"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="cadate">Liquidation Date</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group date">
                            <span class="input-group-addon">
                                <i class="fa fa-calendar">
                                </i>
                            </span>
                        <input class="datepicker" type="text" class="input-sm form-control" id="cadate" name="cadate" value="05/14/2014">
                    </div>
                </div>
                <form-error err_field="errors.err_payee"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="purpose">Purpose</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <textarea  maxlength="1024" rows="5" cols="59" ng-model="apv.particulars" id="purpose"
                                   name="purpose" placeholder="Enter purpose"></textarea>
                    </div>
                </div>
                <form-error err_field="errors.err_particulars"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="amount">Amount</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <input type="text" class="input-sm form-control" id="amount" name="amount" value="0.00">
                </div>
                <form-error err_field="errors.err_payee"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="requested">Requested By</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <select required="" class="form-control" id="requested">
                            <option value="">Select Person</option>
                            <option value="">John Doe</option>
                        </select>
                    </div>
                </div>
                <form-error err_field="errors.err_checker"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="approved">Approved By</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <select required="" class="form-control" id="approved">
                            <option value="">Select Person</option>
                            <option value="">John Doe</option>
                        </select>
                    </div>
                </div>
                <form-error err_field="errors.err_checker"></form-error>
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
    });

    $(function () {
        $('[data-toggle="popover"]').popover({ trigger: "hover", html: true })
    })
</script>