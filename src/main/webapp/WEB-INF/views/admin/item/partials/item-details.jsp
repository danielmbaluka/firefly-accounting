<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ng-click="main()" class="btn btn-primary">Items</a>
        ${detailsAddItem}
        ${detailsEditItem}
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="alert alert-info">{{ title }}</div>
    </div>
</div>
<div class="row" ng-show="showDetails">
    <div class="col-md-6 col-lg-6">
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Code</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ item.code }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Description</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ item.description }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Account</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{item.segmentAccount.accountCode + ' ' + item.segmentAccount.account.title}}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Unit</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ item.unit.code }}</label>
                </div>
            </div>
        </div>
    </div>
</div>