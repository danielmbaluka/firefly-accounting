
<div ng-controller="addEditItemCtrl">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="item" class="btn btn-primary">Items</a>
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
                    <label class="input-label" for="code">Code</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required maxlength="255" ng-model="item.code" id="code" name="code" class="form-control"
                               type="text" placeholder="Enter item code"/>
                    </div>
                </div>
                <form-error err_field="errors.err_code"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="description">Description</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <textarea  maxlength="1024" rows="5" cols="59" ng-model="item.description" id="description"
                                   name="description" placeholder="Enter description"></textarea>
                    </div>
                </div>
                <form-error err_field="errors.err_description"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="unit">Unit</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <select required="" class="form-control" id="unit" ng-model="unit"
                                ng-options="unit.code for unit in units track by unit.id">
                            <option value="">Select unit</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="segmentAccount">Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input value="" disabled ng-model="selectedAccount" id="segmentAccount" name="segmentAccount" class="form-control"  type="text" placeholder="Browse an account"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button class="btn btn-primary" account-browser-s handler="accounts_selection_handler" >Browse accounts</button>
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
                        <button ng-mousedown="submit = true" type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> {{ save }}</button>
                        <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                    </fieldset>
                </div>
            </div>
        </div>
    </form>
</div>


