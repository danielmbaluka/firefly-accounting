<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">

        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info" style="margin-bottom: 5px;">Units of Measurement <span class="pull-right" style="cursor: pointer"><a ui-sref="unit.new"><i class="fa fa-plus" title="Add unit"></i></a></span></div>
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
                <div class="col-md-10 col-lg-10">Description</div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="row-top-buffer" style="margin-top: 5px"/>
            <div style='max-height: 500px; overflow: auto;'>
                <div ng-show="!units">Loading units...</div>
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-dismiss="modal" ng-repeat="unit in uns = (units | filter:query)">
                        <td style="width: 7%;">{{unit.code}}</td>
                        <td style="width: 60%;">{{unit.description}}</td>
                        <td style='width: 4%; vertical-align: middle'>
                            <a style='padding: 0' title='View' ui-sref=".detail({unitId:unit.id})"><i class='fa fa-search'></i></a>
                            <a style='padding: 0' title='Edit' ui-sref=".edit({unitId:unit.id})"><i class='fa fa-edit'></i></a>
                        </td>
                    </tr>
                    <tr ng-show="uns.length == 0"><td colspan="4" align="center">No records found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
