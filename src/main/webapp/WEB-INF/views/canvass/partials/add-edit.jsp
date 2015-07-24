<div ng-controller="addEditCnvsCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="canvass" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}} <span class="label label-success">{{cnvs.localCode}}</span></div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="cnvsdate">Date</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group date">
                            <span class="input-group-addon">
                                <i class="fa fa-calendar">
                                </i>
                            </span>
                        <input ng-model="cnvs.voucherDate" class="datepicker" type="text" class="input-sm form-control" id="cnvsdate" name="cnvsdate" value="05/14/2014">
                    </div>
                </div>
                <form-error err_field="errors.err_voucherDate"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="vendor">Supplier</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="cnvs.vendor.name" id="vendor" name="vendor" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse supplier"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button sl-entity-browser
                            handler="supplier_selection_handler"
                            types="suppliers"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse supplier"></i>&nbsp;</button>
                </div>
                <form-error err_field="errors.err_vendor"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Items For Canvass</label>
                </div>
                <div class="col-md-10 col-lg-10">
                    <div class="table-responsive white-bg">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>RV No.</th>
                                <th>Item No.</th>
                                <th>Description</th>
                                <th>Quantity</th>
                                <th>Unit</th>
                                <th>
                                    <button class="btn btn-primary" rv-detail-for-canvass-browser handler="rvd_selection_handler" >Add item</button>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="cnvs in canvassItems">
                                <td>{{ cnvs.rvNumber }}</td>
                                <td>{{ cnvs.itemCode }}</td>
                                <td>{{ cnvs.itemDescription }}</td>
                                <td style="width: 10%; text-align: right;">{{cnvs.quantity}}</td>
                                <td>{{ cnvs.unitCode }}</td>
                                <td style="text-align: center"><a ng-click="test($index)" rv-detail-for-canvass-browser handler="rvd_selection_handler2" style='padding: 0' title='Update'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a ng-click="removeRow($index)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <form-error err_field="errors.err_canvassItems"></form-error>
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