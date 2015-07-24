<div ng-controller="addEditJoCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="jo" class="btn btn-primary">Back to list</a>
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
                    <label class="input-label" for="jodate">Date</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group date">
                    <span class="input-group-addon">
                        <i class="fa fa-calendar">
                        </i>
                    </span>
                        <input class="datepicker" type="text" class="input-sm form-control" id="jodate" name="jodate" value="05/14/2014">
                    </div>
                </div>
                <form-error err_field="errors.err_payee"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="supplier">Supplier</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="selectedSupplier.name" id="supplier" name="supplier" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse supplier"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button sl-entity-browser
                            handler="supplier_selection_handler"
                            types="suppliers"
                            class="btn btn-primary">Browse supplier</button>
                </div>
                <form-error err_field="errors.err_supplier"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Items For Labor/Repair</label>
                </div>
                <div class="col-md-7 col-lg-10">
                    <div class="table-responsive white-bg">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="text-align: center">Item No.</th>
                                <th style="width: 450px">Description</th>
                                <th colspan="2" style="text-align: center">Quantity</th>
                                <th style="text-align: right">Unit Cost</th>
                                <th style="text-align: right">Total</th>
                                <th style="width: 60px"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td style="text-align: center">1</td>
                                <td>Labor for general overhaul & repair alternator of Kia Single Cab No. 25</td>
                                <td style="text-align: right">1</td>
                                <td>pcs</td>
                                <td><span class="pull-right">2500.00</span></td>
                                <td><span class="pull-right">2500.00</span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Edit'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td style="text-align: center">2</td>
                                <td>Labor for check -up & repair electrical wiring of Kia Single Cab No. 15</td>
                                <td style="text-align: right">2</td>
                                <td>pcs</td>
                                <td><span class="pull-right">1500.00</span></td>
                                <td><span class="pull-right">3000.00</span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Edit'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td style="text-align: center">3</td>
                                <td>Labor for check-up & repair starter, alternator wiring of Kia D/C No. 6</td>
                                <td style="text-align: right">1</td>
                                <td>pcs</td>
                                <td><span class="pull-right">1500.00</span></td>
                                <td><span class="pull-right">1500.00</span></td>
                                <td style="text-align: center"><a style='padding: 0' title='Edit'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td style="text-align: center"><a style='padding: 0' title='Add'><i class='fa fa-plus'></i></a></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right">SUBTOTAL:</span></td>
                                <td><span class="pull-right">7000.00</span></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right">DISCOUNT:</span></td>
                                <td><span class="pull-right">0.00</span></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right">VAT:</span></td>
                                <td><span class="pull-right">0.00</span></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right">TOTAL:</span></td>
                                <td><span class="pull-right">7000.00</span></td>
                                <td></td>
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
                    <label class="input-label" for="check">Prepared By</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <select required="" class="form-control" id="check">
                            <option value="">Mikho Malto</option>
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
                    <label class="input-label" for="audit">Rec. Approval</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <select required="" class="form-control" id="audit">
                            <option value="">Select Person</option>
                            <option value="">John Doe</option>
                        </select>
                    </div>
                </div>
                <form-error err_field="errors.err_approvar"></form-error>
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
                        <select required="" class="form-control" id="approve">
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
                    <label class="input-label" for="receive">Received By</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <select required="" class="form-control" id="receive">
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