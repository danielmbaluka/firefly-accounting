<div ng-controller="addEditCPRCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="cpr" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}} <span class="label label-success">{{cpr.localCode}}</span></div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="description">Description</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="input-sm text-left form-control" ng-model="cpr.description"
                               id="description" name="description" placeholder="Enter description.">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_description"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="refNo">Reference Number</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="input-sm text-left form-control" ng-model="cpr.refNo" id="refNo"
                               name="refNo"
                               placeholder="">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_ref_no"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="value">Value</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="input-sm text-right form-control" ng-model="cpr.value" id="value"
                               name="value"
                               placeholder="">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_value"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="depreciationYears">Depreciation Years</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="input-sm text-right form-control" ng-model="cpr.depreciationYears"
                               id="depreciationYears" name="depreciationYears"
                               placeholder="">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_depreciation_years"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="depreciationMonths">Depreciation Months</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="input-sm text-right form-control" ng-model="cpr.depreciationMonths"
                               id="depreciationMonths" name="depreciationMonths"
                               placeholder="">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_depreciation_months"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="depreciatedValue">Depreciated Value</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="input-sm text-right form-control" ng-model="cpr.depreciatedValue"
                               id="depreciatedValue" name="depreciatedValue"
                               placeholder="">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_depreciated_value"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="startYear">Start Year</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="dp-start-year input-sm form-control" ng-model="cpr.startYear"
                               id="startYear"
                               name="startYear" placeholder="" value="2015">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_start_year"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="startMonth">Start Month</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="dp-start-month input-sm form-control" ng-model="cpr.startMonth"
                               id="startMonth"
                               name="startMonth" placeholder="" value="March">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_start_month"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="acquisitionDate">Acquisition Date</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" class="dp-acquisition-date input-sm form-control"
                               ng-model="cpr.acquisitionDate"
                               id="acquisitionDate"
                               name="acquisitionDate" placeholder="" value="March 13, 1989">
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_acquisition_date"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="fkAccountPepAccount">Accu. Depre. Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="form-control" type="text"
                               ng-model="cpr.fkAccountPepAccount" id="fkAccountPepAccount"
                               name="fkAccountPepAccount" placeholder="Browse account"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button title="Browse Account" type="button" class="btn btn-primary glyphicon glyphicon-new-window"
                            ng-click="showAccuDepreAccountBrowser()"></button>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="fkAssetAccount">Asset Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="form-control ng-pristine ng-valid" type="text"
                               ng-model="cpr.fkAssetAccount" id="fkAssetAccount"
                               name="fkAssetAccount" placeholder="Browse account"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button sl-entity-browser
                            handler="checker_selection_handler"
                            types="signatories"
                            class="btn btn-primary">Browse account
                    </button>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_asset_account"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="fkExpenseAccount">Expense Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="form-control ng-pristine ng-valid" type="text"
                               ng-model="cpr.fkExpenseAccount" id="fkExpenseAccount"
                               name="fkExpenseAccount" placeholder="Browse account"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button sl-entity-browser
                            handler="checker_selection_handler"
                            types="signatories"
                            class="btn btn-primary">Browse account
                    </button>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_expense_account"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="monthlyDepreciation">Monthly Depreciation</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="input-sm text-right form-control ng-pristine ng-valid" type="text"
                               ng-model="cpr.monthlyDepreciation" id="monthlyDepreciation" name="monthlyDepreciation"
                               placeholder="45,234.00"/>
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_monthly_depreciation"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="remainingValue">Remaining Value</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="input-sm text-right form-control ng-pristine ng-valid" type="text"
                               ng-model="cpr.remainingValue" id="remainingValue" name="remainingValue"
                               placeholder="45,234.00"/>
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_remaining_value"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="endYear">End Year</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="input-sm text-left form-control ng-pristine ng-valid" type="text"
                               ng-model="cpr.endYear" id="endYear" name="endYear" placeholder="2099"/>
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_end_year"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="endMonth">End Month</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" class="input-sm text-left form-control ng-pristine ng-valid" type="text"
                               ng-model="cpr.endMonth" id="endMonth" name="endMonth" placeholder="December"/>
                    </div>
                </div>
                <div class="col-md-3 col-lg-3">
                    <form-error err_field="errors.err_end_month"></form-error>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-5 col-lg-5">
                    <fieldset ng-disabled="submitting">
                        <button ng-mousedown="submit = true" type="submit" class="btn btn-primary"><span
                                class="glyphicon glyphicon-floppy-disk"></span> {{save}}
                        </button>
                        <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span>
                            Reset
                        </button>
                    </fieldset>
                </div>
            </div>
        </div>
    </form>

    <div ng-include="modalBodyTemplateUrl" class="modal fade" id="accuDepreAccountModal" tabindex="-1" role="dialog"
         aria-labelledby="myLargeModalLabel" aria-hidden="true"></div>
</div>


<script>
    $('.dp-start-year').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true,
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years"
    });

    $('.dp-start-month').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: true,
        calendarWeeks: true,
        autoclose: true,
        format: "MM",
        viewMode: "months",
        minViewMode: "months"
    });

    $('.dp-acquisition-date').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: true,
        calendarWeeks: true,
        autoclose: true,
        format: "MM dd, yyyy",
        viewMode: "days",
        minViewMode: "days"
    });

    $(function () {
        $('[data-toggle="popover"]').popover({trigger: "hover", html: true})
    })
</script>

