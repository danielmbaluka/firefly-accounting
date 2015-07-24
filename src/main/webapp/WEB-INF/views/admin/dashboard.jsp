<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>


<mytag:master>
    <jsp:attribute name="body">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-9">
                <h2><b>ILECO I - Accounting and Inventory System</b></h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="index.html">Home</a>
                    </li>
                    <li class="active">
                        <strong>Dashboard</strong>
                    </li>
                </ol>
            </div>
        </div>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">

                        <div class="col-sm-3">
                            <h2>Welcome Lester</h2>
                            <small>You have 42 pending tasks</small>
                        </div>
                        <div class="col-lg-6">
                        <div>
                            <table class="table">
                                <tbody>
                                <tr>
                                    <td>
                                        <button type="button" class="btn btn-danger m-r-sm">12</button>
                                        Requisition
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-primary m-r-sm">28</button>
                                        Check Voucher
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-info m-r-sm">15</button>
                                        Accounts Payable
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <button type="button" class="btn btn-info m-r-sm">20</button>
                                        Job Order
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-success m-r-sm">40</button>
                                        Purchase Order
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger m-r-sm">30</button>
                                        Canvass
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <button type="button" class="btn btn-warning m-r-sm">20</button>
                                        Acceptance
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-default m-r-sm">40</button>
                                        Withdrawal
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-warning m-r-sm">30</button>
                                        Journal Voucher
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

            </div>
        </div>
    </jsp:attribute>
</mytag:master>
