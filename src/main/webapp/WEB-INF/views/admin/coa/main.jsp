<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>

<link href="<c:url value="/resources/css/treeGrid/treeGrid.css" />" rel="stylesheet">

<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <a ui-sref=".new" class="btn btn-primary">Add</a>
            <a class="btn btn-primary" target="_blank" ng-href="{{url}}"  ng-mousedown="printCoa('pdf')">Print</a>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div id="search-div" class="col-md-4 col-lg-4">
            </div>
            <div class="col-md-8 col-lg-8">
                <div class="pull-right">
                    <button ng-init="init()" ng-click="accounts_tree.expand_all()" class="btn btn-default btn-sm">Expand All</button>
                    <button ng-click="accounts_tree.collapse_all()" class="btn btn-default btn-sm">Collapse All</button>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div  class="col-md-12 col-lg-12">
            <div ng-show="true">
                <tree-grid tree-data="tree_data" tree-control="accounts_tree" col-defs="col_defs" expand-on="expanding_property"
                           on-select="accounts_tree_handler(branch)" expand-level="5" icon-expand="glyphicon glyphicon-tag"
                           icon-collapse="glyphicon glyphicon-tags" icon-leaf="glyphicon glyphicon-list-alt"></tree-grid>
            </div>
        </div>
    </div>
</div>