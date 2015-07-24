<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Process Voucher</h1>

<div  ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h2>APV-2014-0023 <span class="bold">Acestar</span></h2>
            </div>
            <div class="col-md-1 col-lg-1" style="padding-top: 20px;">
                <button class="btn btn-dropbox">Print</button>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                    <select class="form-control ng-pristine ng-valid ng-valid-required ng-touched" id="unit">
                        <option value="1">Send for Checking</option>
                        <option value="2">Send for Approval</option>
                        <option value="3">Return to Creator</option>
                        <option value="4">Return to Checker</option>
                        <option value="5">Approve</option>
                        <option value="6">Deny</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <h3>Details</h3>
            <div class="col-md-6 col-lg-6">
                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Status</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label"><span class="label label-info">For Approval</span></label>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Voucher Date</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">Jan 24, 2015</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Due Date</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">May 24, 2015</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-3 col-lg-3">
                            <label class="input-label">Particulars</label>
                        </div>
                        <div class="col-md-9 col-lg-9">
                            <label class="value-label">This is a particular</label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-6">
                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Amount</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">45,662.00</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Checker</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">John Doe</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Approving Officer</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">Foo Bar</label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12 col-lg-12">
                        <div class="col-md-4 col-lg-4">
                            <label class="input-label">Last update</label>
                        </div>
                        <div class="col-md-8 col-lg-8">
                            <label class="value-label">Jan 26, 2015 12:34 AM</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <hr>

    <div class="row">
        <div class="col-lg-12">
            <h3>Journal Entries</h3>

            <div class="col-md-12 col-lg-12">
                <div class="table-responsive white-bg">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Account</th>
                            <th><span class="pull-right">Debit</span></th>
                            <th><span class="pull-right">Credit</span></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>00-11-2221 Office supplies</td>
                            <td><span class="pull-right">100.00</span></td>
                            <td><span class="pull-right"></span></td>
                        </tr>
                        <tr>
                            <td>00-11-2221 Water pipes</td>
                            <td><span class="pull-right">45,562.00</span></td>
                            <td><span class="pull-right"></span></td>
                        </tr>
                        <tr>
                            <td class="hasSLAccount" data-container="body" data-toggle="popover" data-placement="right" title="" data-content="CITI hardware<br></td>Ace hardware" data-original-title="SL Account">22-11-2221 Supplier</td>
                            <td><span class="pull-right"></span></td>
                            <td><span class="pull-right">45,662.00</span></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><span class="pull-right">45,662.00</span></td>
                            <td><span class="pull-right">45,662.00</span></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div style="margin-bottom: 100px;"></div>


<script>
    $(function () {
        $('[data-toggle="popover"]').popover({ trigger: "hover", html: true })
    })
</script>