<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">

        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info" style="margin-bottom: 5px;">Check Configurations <span class="pull-right" style="cursor: pointer"><a ui-sref="check.new"><i class="fa fa-plus" title="Add check config"></i></a></span></div>
        </div>
    </div>

    <div class="row-top-buffer" style="margin-top: 15px"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="input-group" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">

            <div class="row-top-buffer" style="margin-top: 15px"></div>

            <div style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                <div class="col-md-2 col-lg-2">Code</div>
                <div class="col-md-6 col-lg-6">Account</div>
                <div class="col-md-2 col-lg-2">Last updated</div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="row-top-buffer" style="margin-top: 5px"/>
            <div style='max-height: 500px; overflow: auto;'>
                <div ng-show="!configs">Loading configs...</div>
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-dismiss="modal" ng-repeat="config in sup = (configs | filter:query)">
                        <td style="width: 7%;">{{config.code}}</td>
                        <td style="width: 30%;">{{config.bankSegmentAccount.accountCode}} {{config.bankSegmentAccount.account.title}}</td>
                        <td style="width: 15%;">{{config.updatedAt | date:'MMM dd, yyyy HH:mm a'}}</td>
                        <td style='width: 4%; vertical-align: middle'>
                            <a style='padding: 0' title='View' ui-sref=".detail({checkId:config.id})"><i class='fa fa-search'></i></a>
                            <a style='padding: 0' title='Edit' ui-sref=".edit({checkId:config.id})"><i class='fa fa-edit'></i></a>
                        </td>
                    </tr>
                    <tr ng-show="sup.length == 0"><td colspan="4" align="center">No records found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
