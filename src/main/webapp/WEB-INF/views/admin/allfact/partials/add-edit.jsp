
<div ng-controller="addEditFactorCtrl">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="allocationFactor" class="btn btn-primary">Back to list</a>
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
                    <label class="input-label" for="account">Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input value="" disabled ng-model="selectedAccount.title" id="account" name="account" class="form-control"  type="text" placeholder="Browse an account"/>
                    </div>
                </div>
                <div class="col-md-1 col-lg-1">
                    <button ng-show="!editMode" title="browse account" type="button" class="btn btn-primary glyphicon glyphicon-new-window" ng-click="showAccountBrowser()"></button>
                </div>
                <div  class="col-md-4 col-lg-4"><form-error err_field="errors.err_account"></form-error></div>
            </div>
        </div>


        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="effDate">Effectivity</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <select required="" ng-change="setSelectedEffDate(selectedEffDate)" class="form-control" id="effDate" ng-model="selectedEffDate"
                                ng-options="r.range for r in ranges track by r.id">
                            <option value="">Select effectivity date</option>
                        </select>
                    </div>
                </div>
                <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_effectivity"></form-error></div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="account">Segments</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <ul style="padding-top: 8px; padding-left: 0px;" class="list-unstyled">
                        <li ng-repeat="segment in businessSegments">
                            <div class="row">
                                <div class="col-md-12 col-lg-12" style="padding-left: 0px; padding-right: 0px;">
                                    <div class="col-md-9 col-lg-9">
                                        <label style="font-weight: normal">{{segment.description}}</label>
                                    </div>
                                    <div class="col-md-3 col-lg-3">
                                        <input ng-change="checkSum(segment, '{{segment.value}}')" autocomplete="off" ng-init="segment.value = segment.hasOwnProperty('value') ? segment.value : 0" max="100" min="0" step="0.5" ng-model="segment.value" id="name" name="name" class="form-control text-right" type="number"/>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>

                        <div class="row">
                            <div class="col-md-12 col-lg-12" style="padding-left: 0px; padding-right: 0px;">
                                <div class="col-md-9 col-lg-9">
                                </div>
                                <div class="col-md-3 col-lg-3 text-right">
                                    <label style="padding-right: 28px; font-size: 14px">{{getPercentageSum()}}</label>
                                </div>
                            </div>
                        </div>
                        </li>
                    </ul>
                </div>
                <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_segmentPercentage"></form-error></div>
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

    <div ng-include="modalBodyTemplateUrl" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"></div>
</div>

<div style="padding-bottom: 100px;"></div>


