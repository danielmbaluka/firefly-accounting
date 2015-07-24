
<div ng-controller="addEditPCVCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="pcv" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}}</div>
        </div>
    </div>
    <form>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="approvar">Available Balance</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="" id="availableBalance" name="availableBalance" class="input-sm text-right form-control ng-pristine ng-valid" type="text" placeholder="45,234.00"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="approvar">Voucher Date</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="" id="" name="" class="input-sm text-right form-control ng-pristine ng-valid" type="text" placeholder="March 13, 1989"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="approvar">Batch</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="" id="" name="" class="input-sm text-right form-control ng-pristine ng-valid" type="text" placeholder="69"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="approvar">Payee</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" placeholder="Payee" class="input-sm text-left form-control" name="">
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="approvar">Amount</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input type="text" placeholder="" class="input-sm text-right form-control" name="">
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Expense Accounts</label>
                </div>
                <div class="col-md-7 col-lg-7">
                    <div class="table-responsive white-bg">
                        <table class="table table-striped">
                            <thead>
                            <tr>

                                <th>Account</th>
                                <th><span class="pull-left">Remarks</span></th>
                                <th><span class="pull-right">Amount</span></th>
                                <th style="width: 60px"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>00-11-2221 Office supplies</td>
                                <td><span class="pull-left">This is a remark.</span></td>
                                <td><span class="pull-right">100.00</span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Edit'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td>00-11-2221 Water pipes</td>
                                <td><span class="pull-left">And this one too.</span></td>
                                <td><span class="pull-right">45,562.00</span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Edit'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td class="hasSLAccount" data-container="body" data-toggle="popover" data-placement="right" title="SL Account" data-content="CITI hardware<br/>Ace hardware">22-11-2221 Supplier</td>
                                <td><span class="pull-left">And another one as well.</span></td>
                                <td><span class="pull-right">45,662.00</span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Edit'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"><strong>123,456.00</strong></span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Add'><i class='fa fa-plus'></i></a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <form-error err_field="errors.err_particulars"></form-error>
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
                        <input disabled="" ng-model="selectedApprovingOfficer.name" id="approvar" name="approvar" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse approving officer"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button sl-entity-browser
                            handler="approvar_selection_handler"
                            types="signatories"
                            class="btn btn-primary">Browse approving officer</button>
                </div>
                <form-error err_field="errors.err_approvar"></form-error>
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

