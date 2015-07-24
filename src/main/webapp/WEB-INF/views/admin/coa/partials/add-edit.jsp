<div ng-controller="newAccountCtrl">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="coa" class="btn btn-primary">Chart of Accounts</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{ title }}</div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="title">Title</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required ng-model="account.title" id="title" name="title" class="form-control" type="text" placeholder="Enter account title"/>
                    </div>
                </div>
                <form-error err_field="errors.err_title"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="gl_acct">GL Account</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required ng-model="account.glaccount" id="gl_acct" name="gl_acct" class="form-control" type="text"  pattern="[0-9]{3,3}" maxlength="3" placeholder="(000)"/>
                    </div>
                </div>
                <form-error err_field="errors.err_GLAccount"></form-error>
            </div>
        </div>
        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="sl_acct">SL Account</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required ng-model="account.slaccount" id="sl_acct" name="sl_acct" class="form-control"  type="text" pattern="[0-9]{2,2}" maxlength="2" placeholder="(00)"/>
                    </div>
                </div>
                <form-error err_field="errors.err_SLAccount"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="auxilliary_acct">Auxiliary Account</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required ng-model="account.auxAccount" id="auxilliary_acct" name="auxilliary_acct" class="form-control"  type="text" pattern="[0-9]{3,3}" maxlength="3" placeholder="(000)"/>
                    </div>
                </div>
                <form-error err_field="errors.err_auxiliaryAccount"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="normal_balance">Normal balance</label>
                </div>
                <div class="col-md-3 col-lg-3">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <select required ng-model="account.normalBalance" id="normal_balance" name="normal_balance" class="form-control">
                            <option value="1">Debit</option>
                            <option value="2">Credit</option>
                        </select>
                    </div>
                </div>
                <form-error err_field="errors.err_normalBalance"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="acct_group_id">Group</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <select required class="form-control" id="acct_group_id" ng-model="accountGroup"
                                ng-options="accountGroup.description for accountGroup in accountGroups track by accountGroup.id">
                            <option value="">Select group</option>
                        </select>
                    </div>
                </div>
                <form-error err_field="errors.err_accountGroup"></form-error>
            </div>
        </div>


        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="acct_type_id">Type</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <select required class="form-control" id="acct_type_id" ng-model="accountType"
                                ng-options="accountType.description for accountType in accountTypes track by accountType.id">
                            <option value="">Select type</option>
                        </select>
                    </div>
                </div>
                <form-error err_field="errors.err_accountType"></form-error>
            </div>
        </div>


        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="parent_acct_id">Parent account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input disabled ng-model="parentAccount.title" id="parent_acct_id" name="parent_acct_id" class="form-control"  type="text" placeholder="Browse an account"/>
                    </div>
                </div>
                <div class="col-md-2 col-lg-2" style="padding-left: 0">
                        <div>
                            <button title="browse account" type="button" class="btn btn-primary glyphicon glyphicon-new-window" ng-click="showAccountBrowser()"></button>
                            <button title="clear account" type="button" class="btn btn-primary glyphicon glyphicon-remove" ng-click="clearAccountSelectedFromBrowser()"></button>
                        </div>
                </div>
                <form-error err_field="errors.err_parentAccountId"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-9 col-lg-9">
                    <div class="inline-group">
                        <label style="margin-right: 20px;"><input ng-model="account.hasSL" type="checkbox" id="has_sl" name="has_sl"> Has SL</label>
                        <label style="margin-right: 20px;"><input ng-model="account.isHeader" type="checkbox" id="is_header" name="is_header"> Is Header</label>
                        <label><input ng-model="account.isActive" type="checkbox" id="isActive" name="isActive"> Active</label>
                    </div>
                </div>
            </div>
        </div>


        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label>Segments</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <ul style="list-style: none; padding-left: 2px; border: 1px solid lightgray">
                        <li ng-repeat="segment in segments">
                            <label><input ng-disabled="segment.assigned" ng-model="segment.selected" type="checkbox" /> {{segment.description}}</label>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-5 col-lg-5">
                    <fieldset ng-disabled="submitting">
                        <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> {{ save }}</button>
                        <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                    </fieldset>
                </div>
            </div>
        </div>
    </form>
    <div ng-include="modalBodyTemplateUrl" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"></div>
</div>


