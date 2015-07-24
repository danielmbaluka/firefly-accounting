<div ng-controller="registerCtrl">
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
            <div class="col-md-2 col-lg-2">
                <h4>From</h4>
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input ng-model="firstDateOfMonth"  style="width: 120px !important;" type="text" class="input-sm form-control datepicker">
                </div>
            </div>
            <div class="col-md-2 col-lg-2">
                <h4>To</h4>
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <input ng-model="lastDateOfMonth"  style="width: 120px !important;" type="text" class="input-sm form-control datepicker">
                </div>
            </div>
            <button class="btn btn-primary" ng-click="search()">Go</button>
        </div>
    </div>

    <div class="row row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <span>
                        <a title="Export to PDF" ng-click="print('pdf')">Pdf</a>
                        <a title="Export to Excel" ng-click="print('xls')">Excel</a>
                    </span>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-12 col-lg-12">
                            <p ng-show="!showForm">Loading data...</p>
                            <div class="table-responsive white-bg">
                                <table class="table table-striped" ng-init="items.total = {}">
                                    <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Reference</th>
                                        <th style="width: 50%">Explanation</th>
                                        <th style="width: 15%">Code</th>
                                        <th><span class="pull-right">Debit</span></th>
                                        <th><span class="pull-right">Credit</span></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr ng-repeat="jv in jvs = (data | filter:query)">
                                        <td>{{jv.voucherDate | date:'MMM dd, yyyy'}}</td>
                                        <td>{{jv.reference}}</td>
                                        <td>{{jv.explanation}}</td>
                                        <td>{{jv.code}}</td>
                                        <td ng-init="items.total.debit = items.total.debit + jv.debit"><span class="pull-right">{{jv.debit | currency:""}}</span></td>
                                        <td ng-init="items.total.credit = items.total.credit + jv.credit"><span class="pull-right">{{jv.credit | currency:""}}</span></td>
                                    </tr>
                                    <tr style="font-weight: bolder">
                                        <td>{{ data.length > 0 ? 'TOTAL' : 'No data' }}</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td><span class="pull-right bold">{{data.length > 0 ? (items.total.debit | currency:"") : ''}}</span></td>
                                        <td><span class="pull-right bold">{{data.length > 0 ? (items.total.credit | currency:"") : ''}}</span></td>
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
        $(document).find('.datepicker').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true,
            setDate: new Date()
        }).datepicker("setDate", $.datepicker.formatDate('mm/dd/yy', new Date()));
    })
</script>

