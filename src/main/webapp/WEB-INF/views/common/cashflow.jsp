<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Cash Flow</h1>

<div  ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <h2>Acestar Solutions</h2>
            </div>
        </div>
        <h2 style="margin-left: 29px;">CV-2015-0023 - 14,000.00</h2>
    </div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-1 col-lg-1">
                <label>Account:</label>
            </div>
            <div class="col-md-4 col-lg-4">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-arrow-up"></i></span>
                    <select class="form-control">
                        <option>Select account</option>
                        <option>Utilities</option>
                        <option>Institutional Activities</option>
                        <option>Insurance</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-1 col-lg-1">
                <label>Amount:</label>
            </div>
            <div class="col-md-4 col-lg-4">
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-arrow-up"></i></span>
                    <input required type="number" step="any" class="form-control" placeholder="Enter amount"/>
                </div>
            </div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-1 col-lg-1">
            </div>
            <div class="col-md-4 col-lg-4">
                <fieldset class="pull-right">
                    <button class="btn btn-default">Reset</button>
                    <button class="btn btn-primary">Add</button>
                </fieldset>
            </div>
        </div>
    </div>

    <div class="row-top-buffer"></div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <th>Cash flow account</th>
                            <th><span class="pull-right">Amount</span></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr style="cursor: pointer">
                                <td>Institutional Activities</td>
                                <td><span class="pull-right">10,000.00</span></td>
                                <td class="text-center"><a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr style="cursor: pointer">
                                <td>Insurance</td>
                                <td><span class="pull-right">1,000.00</span></td>
                                <td class="text-center"><a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                            <tr style="cursor: pointer">
                                <td>Utitilities</td>
                                <td><span class="pull-right">3,000.00</span></td>
                                <td class="text-center"><a style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="col-md-8 col-lg-8">
                <fieldset class="pull-right">
                    <button class="btn btn-default">Clear</button>
                    <button class="btn btn-primary">Save</button>
                </fieldset>
            </div>
        </div>
    </div>
</div>

<div style="margin-bottom: 100px;"></div>