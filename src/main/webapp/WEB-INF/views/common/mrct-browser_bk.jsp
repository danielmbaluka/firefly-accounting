<div class="modal-header">
    <button type="button" class="close" ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">Browse Material Request Charge Ticket</h4>
</div>
<div class="modal-body">
    <div>
        <div class="row">
            <div class="input-group pull-right" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12 col-md-12">
                <div class="row-top-buffer" style="margin-top: 15px"/>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                        <tr>
                            <th>Item Description</th>
                            <th>Quantity</th>
                            <th>Unit Cost</th>
                            <th>Item Cost</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr data-dismiss="modal" ng-repeat="item in selectedMRCT.details | filter:query" style="cursor: pointer">
                            <td style="width: 500px;">{{item.itemDescription}}</td>
                            <td style="width: 100px;">{{item.quantity}}</td>
                            <td style="width: 100px;">{{item.unitCost}}</td>
                            <td style="width: 100px;">{{item.itemCost}}</td>
                        </tr>
                        <tr ng-show="selectedMRCT.details.length == 0"><td colspan="3" align="center">No records found</td></tr>
                        </tbody>
                    </table>
                </div>


            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label" for="recapp">Prepared By:</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <div class="input-group">
                            <select required="" class="form-control" id="recapp">
                                <option value="">Select Person</option>
                                <option value="">John Doe</option>
                            </select>
                        </div>
                    </div>
                    <form-error err_field="errors.err_checker"></form-error>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label" for="recapp">Approved By:</label>
                    </div>
                    <div class="col-md-5 col-lg-5">
                        <div class="input-group">
                            <select required="" class="form-control" id="recapp">
                                <option value="">Select Person</option>
                                <option value="">John Doe</option>
                            </select>
                        </div>
                    </div>
                    <form-error err_field="errors.err_checker"></form-error>
                </div>
            </div>

            <br>

            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-primary">Post</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>