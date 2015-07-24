<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<div  ui-view>
    <div ng-controller="acbCtrl">
        <p>You selected account: <b>{{selectedAccount ? (selectedAccount.segmentAccountCode + ' ' + selectedAccount.title) : 'None'}}</b></p>
        <button class="btn btn-primary" account-browser-s handler="accounts_selection_handler" >Browse accounts</button>

        <p>You selected account: <b>{{selectedAccount2 ? (selectedAccount2.segmentAccountCode + ' ' + selectedAccount2.title) : 'None'}}</b></p>
        <button class="btn btn-primary" account-browser-s handler="accounts_selection_handler2" >Browse accounts</button>

    </div>
    <p></p>
    <div ng-controller="rvCtrl">

        <p>You selected RV: <b>{{selectedRv1 ? (selectedRv1.code + ' ' + selectedRv1.purpose) : 'None'}}</b></p>
        <button class="btn btn-primary" requisition-voucher-browser handler="rv_selection_handler" >Browse Rv 1</button>
    </div>
    <p></p>
    <p></p>
    <div ng-controller="rvdCtrl">

        <p>You selected RVD: <b>{{selectedRvd1 ? (selectedRvd1.itemDescription) : 'None'}}</b></p>
        <button class="btn btn-primary" rv-detail-for-canvass-browser handler="rvd_selection_handler" >Browse Rvd 1</button>
    </div>
    <p></p>
    <div ng-controller="sleCtrl">

        <p>You selected entity: <b>{{selectedEntity ? (selectedEntity.accountNo + ' ' + selectedEntity.name) : 'None'}}</b></p>
        <button sl-entity-browser
                types="entityTypes1"
                handler="entity_selection_handler"
                class="btn btn-primary">Browse entity 1</button>

        <p>You selected entity: <b>{{selectedEntity2 ? (selectedEntity2.accountNo + ' ' + selectedEntity2.name) : 'None'}}</b></p>
        <button sl-entity-browser
                types="entityTypes2"
                handler="entity_selection_handler2"
                class="btn btn-primary">Browse entity 2</button>
    </div>
    <p></p>
    <div ng-controller="itemCtrl">

        <p>You selected item: <b>{{selectedItem1 ? (selectedItem1.code + ' ' + selectedItem1.description) : 'None'}}</b></p>
        <button class="btn btn-primary" item-browser handler="item_selection_handler1" >Browse item 1</button>

        <p>You selected item: <b>{{selectedItem2 ? (selectedItem2.code + ' ' + selectedItem2.description) : 'None'}}</b></p>
        <button class="btn btn-primary" item-browser handler="item_selection_handler2" >Browse item 2</button>

        <p></p>
        <a class="btn btn-primary" title="export to excel" target="_blank" ng-href="{{url}}"  ng-mousedown="export('xls')">Export items to excel</a>
    </div>
    <p></p>
    <div ng-controller="slSetterCtrl">
        <button class="btn btn-primary" voucher-sl-setter handler="voucher_sl_setter_handler" slentries="existingSLEntries" glaccount="glAccount">Set SL</button>
    </div>
</div>