<%@ page language="java" contentType="text/html; charset=UTF-8" %>


<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">

        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info" style="margin-bottom: 5px;">Supplier <span class="pull-right" style="cursor: pointer"><a ui-sref="supplier.new"><i class="fa fa-plus" title="Add supplier"></i></a></span></div>
        </div>
    </div>

    <div class="row-top-buffer" style="margin-top: 15px"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="input-group" style="width: 300px">
                <input class="form-control" placeholder="Search" ng-model="query" />
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">

            <div class="row-top-buffer" style="margin-top: 15px"></div>

            <div style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                <div class="col-md-1 col-lg-1"><span style="font-weight: bold; padding-right: 0;">Code</span></div>
                <div class="col-md-3 col-lg-3" style="margin-right: 70px"><span style="font-weight: bold;">Name</span></div>
                <div class="col-md-2 col-lg-2" style="padding-left: 0"><span style="font-weight: bold;">Contact Person</span></div>
                <div class="col-md-1 col-lg-1" style="padding: 0"><span style="font-weight: bold;">Status</span></div>
                <div class="col-md-1 col-lg-1" style="padding-right: 0px; padding-left: 0px; margin-left: 25px;"><span style="font-weight: bold;">Phone</span></div>
                <div class="col-md-2 col-lg-2" style="margin-left: 50px"><span style="font-weight: bold;">Last updated</span></div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="row-top-buffer" style="margin-top: 5px"/>
            <div style='max-height: 500px; overflow: auto;'>
                <div ng-show="!suppliers">Loading suppliers...</div>
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-dismiss="modal" ng-repeat="supplier in sup = (suppliers | filter:query)">
                        <td style="width: 7%;">{{supplier.accountNumber}}</td>
                        <td style="width: 30%;">{{supplier.name}}</td>
                        <td style="width: 15%;">{{supplier.contactPerson}}</td>
                        <td style="width: 10%;">{{supplier.status}}</td>
                        <td style="width: 13%;">{{supplier.phone}}</td>
                        <td style="width: 15%;">{{supplier.updatedAt | date:'MMM dd, yyyy HH:mm a'}}</td>
                        <td style='width: 4%; vertical-align: middle'>
                            <a style='padding: 0' title='View' ui-sref=".detail({supplierId:supplier.id})"><i class='fa fa-search'></i></a>
                            <a style='padding: 0' title='Edit' ui-sref=".edit({supplierId:supplier.id})"><i class='fa fa-edit'></i></a>
                        </td>
                    </tr>
                    <tr ng-show="sup.length == 0"><td colspan="4" align="center">No records found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
