<div ng-controller="addEditPoCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="po" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}} <span class="label label-success">{{po.localCode}}</span></div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="podate">Date</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group date">
                                <span class="input-group-addon">
                                    <i class="fa fa-calendar">
                                    </i>
                                </span>
                    <input ng-model="po.voucherDate" class="datepicker" type="text" class="input-sm form-control" id="podate" name="podate" value="05/14/2014">
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
                    <input disabled="" ng-model="po.vendor.name" id="vendor" name="vendor" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse supplier"/>
                </div>
            </div>
            <div class="col-md-5 col-lg-5">
                <button sl-entity-browser
                        handler="supplier_selection_handler"
                        types="suppliers"
                        class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse supplier"></i>&nbsp;</button>
            </div>
            <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_vendor"></form-error></div>
        </div>
    </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Items For Purchase</label>
                </div>
                <div class="col-md-7 col-lg-10">
                    <div class="table-responsive white-bg">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="text-align: center">Item No.</th>
                                <th>Description</th>
                                <th style="text-align: center">Quantity</th>
                                <th>Unit</th>
                                <th style="text-align: center">Price</th>
                                <th style="text-align: center">Total</th>
                                <th>
                                    <button class="btn btn-primary" rv-detail-for-po-browser handler="rvd_selection_handler" >Add item</button>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="pod in podItems">
                                <td style="text-align: center">{{ $index + 1 }}</td>
                                <td>{{ pod.itemDescription }}</td>
                                <td style="width: 10%;"><input style="width: 90%; text-align: right;" type="number" min="1" max="{{pod.rvdQuantity}}" ng-model="pod.quantity" ng-change="updateAmount(pod.quantity, $index)"></td>
                                <td>{{ pod.unitCode }}</td>
                                <td style="width: 20%;"><input style="width: 90%; text-align: right;" type="number" min="1" ng-model="pod.unitPrice" ng-change="updateAmount(pod.quantity, $index)"></td>
                                <td style="text-align: right">{{ pod.itemAmount }}</td>
                                <td style="text-align: center"><a ng-click="test($index)" rv-detail-for-po-browser handler="rvd_selection_handler2" style='padding: 0' title='Update'><i class='fa fa-edit'></i></a>&nbsp;&nbsp;<a ng-click="removeRow($index)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right"></span></td>
                                <td><span class="pull-right">TOTAL:</span></td>
                                <td><span class="pull-right">{{totalAmount}}</span></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <form-error err_field="errors.err_podItems"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label" for="request">Prepared By</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input disabled="" ng-model="po.createdBy.name" id="request" name="request" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Preparing Officer"/>
                </div>
            </div>
            <div class="col-md-1 col-lg-1">
                <button sl-entity-browser
                        handler="prep_selection_handler"
                        types="signatories"
                        class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Officer"></i>&nbsp;</button>
            </div>
            <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_prepBy"></form-error></div>
        </div>
    </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="noted">Noted By</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="po.notedBy.name" id="noted" name="noted" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Noting Officer"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button sl-entity-browser
                            handler="note_selection_handler"
                            types="signatories"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Officer"></i>&nbsp;</button>
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_notedBy"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="app">Approved By</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled="" ng-model="po.approvedBy.name" id="app" name="app" class="form-control ng-pristine ng-valid" type="text" placeholder="Browse Approving Officer"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button sl-entity-browser
                            handler="app_selection_handler"
                            types="signatories"
                            class="btn btn-primary">&nbsp;<i class="fa fa-chevron-up" title="Browse Officer"></i>&nbsp;</button>
                </div>
                <div class="col-md-3 col-lg-3"><form-error err_field="errors.err_approvedBy"></form-error></div>
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