
<div class="modal-header">
    <button type="button" class="close" ng-click="close()" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Browse items</h4>
</div>
<div class="modal-body">
    <div>
        <div class="row">
            <div class="input-group pull-right" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12">
                <div class="row-top-buffer" style="margin-top: 15px"/>
                <div class='row' style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                    <div class="col-md-1 col-lg-1"><span style="font-weight: bold; padding-left: 2px;">Code</span></div>
                    <div class="col-md-5 col-lg-5"><span style="font-weight: bold; padding-left: 30px;">Description</span></div>
                    <div class="col-md-1 col-lg-1"><span style="font-weight: bold">Unit</span></div>
                    <div class="col-md-5 col-lg-5"><span style="font-weight: bold">Account</span></div>
                </div>
                <div class="row-top-buffer" style="margin-top: 5px"/>
                <div class="row" style='max-height: 500px; overflow: auto;'>
                    <div ng-show="!items">Loading items...</div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <tr data-dismiss="modal" ng-repeat="item in its = (items | filter:query)" style="cursor: pointer" ng-click="selectItem(item)">
                            <td style="width: 120px;">{{item.code}}</td>
                            <td style="width: 480px;">{{item.description}}</td>
                            <td style="width: 90px;">{{item.unit.code}}</td>
                            <td style="max-width: 300px;">{{item.segmentAccount.accountCode + ' ' + item.segmentAccount.account.title}}</td>
                        </tr>
                        <tr ng-show="its.length == 0"><td colspan="4" align="center">No records found</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
</div>