<div ng-controller="trialBalanceCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="reportsAccounting" class="btn btn-primary">Back</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}}</div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-1 col-lg-1">
                <h4>As of</h4>
            </div>
            <div class="col-md-2 col-lg-2">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input style="width: 120px !important;" type="text" class="input-sm form-control datepicker">
                </div>
            </div>
            <button class="btn btn-primary">Go</button>
        </div>
    </div>

    <div class="row row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    Results
                        <span class="pull-right">
                            <a title="Export to PDF">Pdf</a>
                            <a title="Export to Excel">Excel</a>
                        </span>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-12 col-lg-12">
                            <div class="table-responsive white-bg">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Code</th>
                                        <th style="width: 50%">Title</th>
                                        <th><span class="pull-right">Debit</span></th>
                                        <th><span class="pull-right">Credit</span></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th>1001</th>
                                        <th style="width: 50%">Petty Cash</th>
                                        <td><span class="pull-right">200.00</span></td>
                                        <td><span class="pull-right"></span></td>
                                    </tr>

                                    <tr>
                                        <th>1008</th>
                                        <th style="width: 50%">Inventory reserves</th>
                                        <td><span class="pull-right"></span></td>
                                        <td><span class="pull-right">2,343.00</span></td>
                                    </tr>

                                    <tr>
                                        <td>TOTAL</td>
                                        <td></td>
                                        <td><span class="pull-right">20.00</span></td>
                                        <td><span class="pull-right">2,343.00</span></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div style="margin-bottom: 100px;" />


<script>
    $(function () {
        $('.datepicker').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true,
            setDate: new Date()
        });
        $(".datepicker").datepicker("setDate", new Date());
    })
</script>

