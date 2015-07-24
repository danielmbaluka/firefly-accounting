<%-- use as angular directive --%>
<div class="modal-header">
    <button type="button" class="close" ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Temporary General Ledger entries</h4>
</div>
<div class="modal-body">
    <div>
        <div class="row" style="border-bottom: 1px solid #dcdcdc; padding-bottom: 10px">
            <div class="input-group pull-left" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12">
                <div class="row-top-buffer" style="margin-top: 15px"/>
                <div class='row'>
                    <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 2px;">Date</span></div>
                    <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 2px;">Document</span></div>
                    <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 2px;">Amount</span></div>
                    <div class="col-md-5 col-lg-5"><span style="font-weight: bold">Remarks</span></div>
                </div>
                <div class="row-top-buffer" style="margin-top: 5px"/>
                <div class="row" style='max-height: 500px; overflow: auto;'>
                    <div ng-show="!data">Loading data...</div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="line in ds = (data | filter:query)" style="cursor: pointer" ng-click="select(line)">
                            <td style="width: 13%">{{line.tempBatchDate | date:'MMM dd, yyyy'}}</td>
                            <td style="width: 20%">{{line.docTypeDesc}}</td>
                            <td style="width: 12%" class="text-right">{{line.amount | currency : "" : 2}}</td>
                            <td style="width: 55%">{{line.remarks}}</td>
                        </tr>
                        <tr ng-show="ds.length == 0"><td colspan="4" align="center">No records found</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>