<%@ page language="java" contentType="text/html; charset=UTF-8" %>


<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info" style="margin-bottom: 5px;">Items <span class="pull-right" style="cursor: pointer">${addItem}</span></div>
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
                <div class="col-md-5 col-lg-5"><span style="margin-left: 30px; font-weight: bold;">Description</span></div>
                <div class="col-md-4 col-lg-4"><span style="font-weight: bold;">Account</span></div>
                <div class="col-md-1 col-lg-1"><span style="font-weight: bold; padding-left: 25px;">Unit</span></div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="row-top-buffer" style="margin-top: 5px"/>
            <div style='max-height: 500px; overflow: auto;'>
                <div ng-show="!items">Loading items...</div>
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                    </tr>
                    </thead>
                    <tbody>
                    <tr data-dismiss="modal" ng-repeat="item in its = (items | filter:query)">
                        <td style="width: 7%;">{{item.code}}</td>
                        <td style="width: 25%;">{{item.description}}</td>
                        <td style="width: 25%;">{{item.segmentAccount.accountCode + ' ' + item.segmentAccount.account.title}}</td>
                        <td style="width: 6%;">{{item.unit.code}}</td>
                        <td style="width: 3%; text-align: center">
                            <a style='padding: 0' title='View' ui-sref=".detail({itemId:item.id})"><i class='fa fa-search'></i></a>
                            ${editItem}
                        </td>
                    </tr>
                    <tr ng-show="its.length == 0"><td colspan="4" align="center">No records found</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
