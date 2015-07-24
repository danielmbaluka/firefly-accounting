
<div ng-controller="addEditUnitCtrl" style="margin-bottom: 50px;">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <button ui-sref="unit" class="btn btn-primary">Back to list</button>
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
                        <input required maxlength="255" ng-model="unit.code" id="code" name="code" class="form-control"
                               type="text" placeholder="Enter unit code"/>
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
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required maxlength="255" ng-model="unit.description" id="description" name="description" class="form-control"
                               type="text" placeholder="Enter unit description"/>
                    </div>
                </div>
                <form-error err_field="errors.err_description"></form-error>
            </div>
        </div>

        <div class="row-top-buffer" style="margin-top: 35px;"></div>

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
</div>


