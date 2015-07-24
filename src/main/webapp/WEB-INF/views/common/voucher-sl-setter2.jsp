<div class="modal-header">
    <button type="button" class="close"  ng-click="close()" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Set voucher SL <span class="label badge-success">{{glaccount.code}} {{glaccount.description}}</span> <span class="label badge-success">{{sltotalamount}}</span></h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-lg-12 col-md-12">
            <div class="row">
                <table class="table table-striped table-hover table-bordered table-responsive">
                    <thead>
                    <tr>
                        <td>Entities</td>
                        <td style="width: 20%"><span class="pull-right">Amount</span></td>
                        <td style="width: 7%"><a ng-click="newBlankRow()" style='padding: 0' title='Add'><i class='text-center fa fa-plus'></i></a></td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr data-dismiss="modal" ng-repeat="entry in slentries" style="cursor: pointer">
                            <td class='code-col'>{{entry.name}}</td>
                            <td><input ng-model="entry.amount" ng-change="updateTotal(entry.amount, '{{entry.amount}}')" class="text-right form-control" type="text"/></td>
                            <td style="text-align: center"><a ng-click="setSelectedIdx($index)" sl-entity-browser handler="entity_selection_handler" types="[1,2]" ><i class="fa fa-chevron-up" title="Browse entity"></i></a>&nbsp;&nbsp;<a ng-click="removeEntryRow($index)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                        </tr>
                        <tr class="{{(total != sltotalamount) ? 'sl-setter-total-red' : ''}}">
                            <td class="font-bold">TOTAL</td>
                            <td class="text-right font-bold"><span style="margin-right: 12px; font-size: 16px;">{{total}} / {{sltotalamount}}</span></td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button ng-click="resetEntries()" type="button" class="btn btn-danger">Reset</button>
    <button ng-click="cancelEntries()" type="button" class="btn btn-default">Cancel</button>
    <button ng-click="saveEntries()" type="button" class="btn btn-success">Save</button>
</div>