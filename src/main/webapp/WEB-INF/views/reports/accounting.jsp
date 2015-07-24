<h1>{{title}}</h1>


<div ui-view>

    <div class="row">
        <div class="col-lg-6 col-md-6">
            <div class="panel panel-primary">
                <div class="panel-heading">Financial Statements</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3 col-lg-3">
                            <h3>Report type</h3>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <select ng-change="loadFilter(fsReportType)" required="" class="form-control" ng-model="fsReportType"
                                    ng-options="fsReportType.toString for fsReportType in finStatementReportTypes track by fsReportType.id">
                                <option value="">Select financial statement</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-6 col-md-6">
            <div class="panel panel-info">
                <div class="panel-heading">Registers</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3 col-lg-3">
                            <h3>Report type</h3>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <select ng-change="loadFilter(regsReportType)" required="" class="form-control" ng-model="regsReportType"
                                    ng-options="regsReportType.toString for regsReportType in registersReportTypes track by regsReportType.id">
                                <option value="">Select register</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
