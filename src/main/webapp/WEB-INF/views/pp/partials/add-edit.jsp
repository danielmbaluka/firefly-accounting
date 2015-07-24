<div ng-controller="addEditPpCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="pp" class="btn btn-primary">Back to list</a>
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
                    <label class="input-label" for="datePaid">Date Paid</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group date">
                    <span class="input-group-addon">
                        <i class="fa fa-calendar">
                        </i>
                    </span>
                        <input ng-model="pp.datePaid" class="datepicker" type="text" class="input-sm form-control" id="datePaid" name="datePaid" value="05/14/2014">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_datePaid"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="description">Description</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <textarea  maxlength="1024" rows="3" cols="59" ng-model="pp.description" id="description"
                                   name="description" placeholder="Enter description"></textarea>
                    </div>
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_description"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="totcost">Total Cost</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <input ng-model="pp.totalCost" ng-change="updateMonthlyCost(pp.totalCost, pp.noOfMonths)" type="text" class="input-sm form-control" id="totcost" name="totcost" value="0.00">
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_totalCost"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="months">No. of Months</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <input ng-model="pp.noOfMonths" ng-change="updateMonthlyCost(pp.totalCost, pp.noOfMonths)" type="text" class="input-sm form-control" id="months" name="months" value="0">
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_noOfMonths"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="mocost">Monthly Cost</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <input disabled="" ng-model="pp.monthlyCost" type="text" class="input-sm form-control" id="mocost" name="mocost" value="0.00">
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_monthlyCost"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="apcost">Applied Cost</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <input ng-model="pp.appliedCost" ng-change="updateBalance(pp.totalCost, pp.appliedCost)" type="text" class="input-sm form-control" id="apcost" name="apcost" value="0.00">
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_appliedCost"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="balance">balance</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <input disabled="" ng-model="pp.balance" type="text" class="input-sm form-control" id="balance" name="balance" value="0.00">
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_balance"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="prepacct">Prepayment Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="selectedAccount1" id="prepacct" name="prepacct" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Accounts"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button account-browser-s
                            handler="prepAccounts_selection_handler"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse accounts"></i>&nbsp;</button>
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_prepaymentAccount"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="exppacct">Expense Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="selectedAccount2" id="exppacct" name="exppacct" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Accounts"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button account-browser-s
                            handler="expAccounts_selection_handler"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse accounts"></i>&nbsp;</button>
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_expenseAccount"></form-error></div>
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