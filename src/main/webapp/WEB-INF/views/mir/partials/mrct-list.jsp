<div ng-controller="mrctTestController">
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref="mir" class="btn btn-primary">Back to list</a>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">

        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{title}}</div>
        </div>

        <div class="col-md-4">
            <div class="input-group"><input type="text" ng-model="itemDescription" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
        </div>

    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-hover table-bordered">
                <thead>
                <tr>
                    <th>Reference No.</th>
                    <th>Date</th>
                    <th>Purpose</th>
                    <th>Prepared By</th>
                    <th>Issued By</th>
                    <th>Received By</th>
                    <th>Approved By</th>
                </tr>
                </thead>
                <tbody>

                <tr mrct-browser ng-repeat="item in mrctList | filter: itemDescription" ng-click="selectMRCT(item)" ui-sref="mir.new" >
                    <td style="width: 120px;">{{item.referenceNo}}</td>
                    <td style="width: 200px;">{{item.date}}</td>
                    <td style="width: 480px;">{{item.purpose}}</td>
                    <td style="width: 120px;">{{item.createdBy}}</td>
                    <td style="width: 120px;">{{item.issuedBy}}</td>
                    <td style="width: 120px;">{{item.receivedBy}}</td>
                    <td style="width: 120px;">{{item.approvedBy}}</td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>