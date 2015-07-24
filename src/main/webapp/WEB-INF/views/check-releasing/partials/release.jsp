<div ng-controller="releaseCtrl">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="checkReleasing" class="btn btn-primary">Back to list</a>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h2>{{cv.localCode}} {{cv.payee.name}}</h2>
            </div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row-top-buffer"></div>
    <div  ng-repeat="model in models">
        <form ng-submit="processForm(model)" ng-show="showForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label">Account</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <label class="input-label" >{{model.account}} {{model.segmentAccountCode}}</label>
                    </div>
                </div>
            </div>

            <div class="row-top-buffer"></div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label">Check number</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <label class="input-label" >{{model.checkNumber}}</label>
                    </div>
                </div>
            </div>

            <div class="row-top-buffer"></div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label" for="payee">Received by</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                            <input ng-model="model.payee" ng-init="model.payee = cv.payee.name" id="payee" name="payee" class="form-control ng-pristine ng-valid" type="text" placeholder="Enter receiver"/>
                        </div>
                    </div>
                    <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_receivedBy"></form-error></div>
                </div>
            </div>

            <div class="row-top-buffer"></div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label" for="or">OR Number</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                            <input ng-model="model.orNumber" id="or" name="or" class="form-control ng-pristine ng-valid" type="text" placeholder="Enter OR Number"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row-top-buffer"></div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label" for="releaseDate">Release Date</label>
                    </div>
                    <div class="col-md-3 col-lg-3">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                            <input ng-model="model.dateReleased" class="datepicker" type="text" class="input-sm form-control" id="releaseDate" name="releaseDate">
                        </div>
                    </div>
                    <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_dateReleased"></form-error></div>
                </div>
            </div>

            <div class="row-top-buffer"></div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label" for="remarks">Remarks</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <div class="input-group">
                            <textarea  maxlength="1024" rows="5" cols="58" ng-model="model.remarks" id="remarks"
                                       name="remarks" placeholder="Enter Remarks"></textarea>
                        </div>
                    </div>
                    <div  class="col-md-5 col-lg-5"><form-error err_field="errors.err_remarks"></form-error></div>
                </div>
            </div>

            <div class="row-top-buffer"></div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <fieldset ng-disabled="submitting">
                            <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> {{save}}</button>
                            <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                        </fieldset>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<div style="margin-bottom: 100px;" />


<script>
    $(function () {

        $(document).on('focusin', '.datepicker', function(){
            var d = $('.datepicker').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
            if ($(this).val() == ''){
                d.datepicker("setDate", $.datepicker.formatDate('mm/dd/yy', new Date()));
            }
        });
    })
</script>

