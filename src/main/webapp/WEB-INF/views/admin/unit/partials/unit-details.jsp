<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ng-click="main()" class="btn btn-primary">Back to list</a>
        <a ui-sref="unit.new" class="btn btn-primary">Add</a>
        <a ui-sref="unit.edit({unitId: unitId})" class="btn btn-primary">Edit</a>
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="alert alert-info">{{ title }}</div>
    </div>
</div>
<div class="row" ng-show="showDetails" style="margin-bottom: 35px;">
    <div class="col-md-6 col-lg-6">
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Code</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ unit.code }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Description</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{unit.description}}</label>
                </div>
            </div>
        </div>
    </div>
</div>