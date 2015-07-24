<div>
    <div class="row">
        <div class="col-md-12 col-lg-12">

        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <span class="pull-right" style="cursor: pointer"><a ui-sref="user.newRole"><i class="fa fa-plus" title="Add role"></i></a></span>
        </div>
    </div>

    <div class="row-top-buffer" style="margin-top: 15px"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="input-group" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query_r" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12 col-lg-12">

            <div class="row-top-buffer" style="margin-top: 15px"></div>

            <div style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                <div class="col-md-6 col-lg-6"><span style="font-weight: bold; padding-right: 10;">Name</span></div>
                <div class="col-md-2 col-lg-2" style="padding-left: 0"><span style="font-weight: bold; padding-right: 150;">Last updated</span></div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="row-top-buffer" style="margin-top: 5px"/>
            <div ng-show="!roles">Loading roles...</div>
            <table class="table table-striped table-hover table-bordered">
                <thead>
                <tr>
                </tr>
                </thead>
                <tbody>
                <tr data-dismiss="modal" ng-repeat="role in r = (roles | filter:query_r)">
                    <td style="width: 22%;">{{role.name}}</td>
                    <td style="width: 20%;">{{role.updatedAt | date:'MMM dd, yyyy HH:mm a'}}</td>
                    <td style='width: 4%; vertical-align: middle; text-align: center'>
                        <a style='padding: 0' title='View' ui-sref="user.roleDetail({roleId: role.id})"><i class='fa fa-search'></i></a>
                        <a style='padding: 0' title='Edit' ui-sref="user.roleEdit({roleId: role.id})"><i class='fa fa-edit'></i></a>
                    </td>
                </tr>
                <tr ng-show="r.length == 0"><td colspan="4" align="center">No records found</td></tr>
                </tbody>
            </table>
        </div>
    </div>
</div>