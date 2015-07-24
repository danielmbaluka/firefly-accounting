<div class="modal-header">
    <button type="button" class="close"  ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">{{ print ? 'Print check for ' : 'Assign check number for ' }}<span class="label badge-success">{{account.segmentAccountCode}} {{account.description}}</span></h4>
</div>
<div class="modal-body">
    <div class="row">
        <input class="form-control" placeholder="Enter check number" ng-model="e.check"/>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="checkEntered()">{{ print ? 'Save' : 'OK'}}</button>
    <button ng-show="print" type="button" class="btn btn-info" data-dismiss="modal" ng-click="saveAndPrint()">Save & Print</button>
    <button type="button" class="btn btn-default" data-dismiss="modal" ng-click="cancel()">Cancel</button>
</div>