<h4 ng-show="!doneLoading">Loading data...</h4>
<div>
    <a ng-click="main()" class="btn btn-primary">Back to list</a>
</div>
<div ng-show="doneLoading">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <p style="font-size: 16px;"> Allocation factor for <b>{{account.code}} {{account.title}}</b></p>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label">Effectivity</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <label  class="value-label">{{ effDate.start | date:'MMM dd, yyyy' }} to {{ effDate.end | date:'MMM dd, yyyy'}}</label>
            </div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-2 col-lg-2">
                <label class="input-label">Segments</label>
            </div>
            <div class="col-md-5 col-lg-5">
                <ul style="padding-top: 8px; padding-left: 0px;" class="list-unstyled">
                    <li ng-repeat="segment in businessSegments" style="border-bottom: 1px solid lightgrey">
                        <div class="row">
                            <div class="col-md-12 col-lg-12" style="padding-left: 0px; padding-right: 0px;">
                                <div class="col-md-9 col-lg-9">
                                    <label  class="value-label">{{segment.description}}</label>
                                </div>
                                <div class="col-md-3 col-lg-3"  style="text-align: right">
                                    <label class="value-label">{{segment.value | currency:"": 2}}%</label>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>