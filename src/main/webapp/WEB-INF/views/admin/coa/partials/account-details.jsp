<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ng-click="main()" class="btn btn-primary">Chart of Accounts</a>
        <a ui-sref="coa.new" class="btn btn-primary">Add</a>
        <a ui-sref="coa.edit({accountId: accountId})" class="btn btn-primary">Edit</a>
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="alert alert-info">{{ title }}</div>
    </div>
</div>

<div class="row" ng-show="showDetails">
    <div class="col-md-7 col-lg-7">
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Code</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.code }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Title</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.title }}</label>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">GL Account</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.glaccount }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">SL Account</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.slaccount }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Auxiliary Account</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.auxAccount }}</label>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Normal Balance</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.normalBalance == 1 ? 'Debit' : 'Credit' }}</label>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Group</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.accountGroup.description }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Type</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.accountType.description }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Parent Account</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.parentAccount.title }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Active</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.isActive == 1 ? 'Yes' : 'No' }}</label>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Has SL</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.hasSL == 1 ? 'Yes' : 'No' }}</label>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Header</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ account.isHeader == 1 ? 'Yes' : 'No' }}</label>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-5 col-lg-5">
        <div class="row"><label class="input-label">Segments</label></div>
        <div class="row">
            <ul style="list-style: disc; padding-left: 10px;">
                <li ng-repeat="sa in account.segmentAccounts">
                    {{sa.businessSegment.description}}
                </li>
            </ul>
        </div>
    </div>
</div>