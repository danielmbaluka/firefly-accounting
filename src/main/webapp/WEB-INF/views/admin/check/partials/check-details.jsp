<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ng-click="main()" class="btn btn-primary">Back to list</a>
        <a ui-sref="check.new" class="btn btn-primary">Add</a>
        <a ui-sref="check.edit({checkId: checkId})" class="btn btn-primary">Edit</a>
        <a target="_blank" href="{{url}}" class="btn btn-primary">Test Print</a>
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
                    <label class="value-label">{{ check.code }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Account</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{check.bankSegmentAccount.accountCode}} {{check.bankSegmentAccount.account.title}}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Last updated</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.updatedAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">dateX</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.dateX }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">dateY</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.dateY }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">payeeX</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.payeeX }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">payeeY</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.payeeY }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">payeeW</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.payeeW }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">alphaAmountX</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.alphaAmountX }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">alphaAmountY</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.alphaAmountY }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">alphaAmountW</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.alphaAmountW }}</label>
                </div>
            </div>
        </div>
    </div>

    <%--second column--%>
    <div class="col-md-6 col-lg-6">
        <div class="row">

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">numericAmountX</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.numericAmountX }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">numericAmountY</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.numericAmountY }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">sig1X</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.sig1X }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">sig2Y</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.sig2Y }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">checkNoX</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.checkNoX }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">checkNoY</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.checkNoY }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">withSigner</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.withSigner == 1 ? 'Yes' : 'No' }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">showDesignation</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ check.showDesignation == 1 ? 'Yes' : 'No' }}</label>
                </div>
            </div>


        </div>
    </div>
</div>