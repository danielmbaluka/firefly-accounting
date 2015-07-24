<div class="modal-header">
    <button type="button" class="close"  ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">{{ print ? 'Print check' : 'Assign check number' }}</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="row">
            <div class="col-lg-12 col-md-12" ng-repeat="entry in journalEntries">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        {{entry.code}} {{entry.description}} {{(entry.credit + entry.debit) | currency:"": 2}}
                    </div>
                    <div class="panel-body">
                        <ul class="list-unstyled" style="padding-left: 5px;">
                            <li ng-repeat="s in entry.distribution" style="margin-bottom: 5px;">
                                <div class="row">
                                    <div class="col-lg-7 col-md-7" style="margin-top: 10px;">
                                        <span style="font-weight:  normal; font-size: 13px;" class="label">{{s.segmentDescription}} <span style="padding: 3px;" class="badge-info"> {{s.amount | currency: "":2}}</span></span>
                                    </div>
                                    <div class="col-lg-4 col-md-4">
                                        <input ng-change="save(entry.code, s.segmentAccountId, s.checkNumber, '{{s.checkNumber}}')" ng-model-options="{ updateOn: 'blur' }" ng-model="s.checkNumber" class="text-right form-control" type="text" placeholder="Check number" />
                                    </div>
                                    <div class="col-lg-1 col-md-1" style="padding-left: 0">
                                        <button ng-show="print && !s.released" type="button" class="btn btn-info" ng-click="saveAndPrint(entry.code, s.segmentAccountId, s.checkNumber)" title="Print"><i class='fa fa-print'></i></button>
                                        <div style="margin-top: 12px;" ng-show="s.released"><span class="label badge-success">Released</span></div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>