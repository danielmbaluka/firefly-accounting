<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ui-sref="user.newRole" class="btn btn-primary">Add</a>
        <a ui-sref="user.roleEdit({roleId: roleId})" class="btn btn-primary">Edit</a>
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="alert alert-info">{{ title }}</div>
    </div>
</div>
<div class="row" ng-show="showDetails">
    <div class="col-md-12 col-lg-12">
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Name</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ role.name }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Date created</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ role.createdAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Last updated</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ role.updatedAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Assigned modules</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <ul style="padding-top: 8px; padding-left: 20px;">
                        <li class="{{menu.parentMenu.id > 0 ? 'sub-menu' : ''}}"  ng-repeat="menu in role.menus">
                            <label style="font-weight: normal">{{menu.title}}</label>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-2 col-lg-2">
                        <label class="input-label">Allowed pages and components</label>
                    </div>
                    <div class="col-md-8 col-lg-8">
                        <div class="panel-body" style="padding-left: 0px;">
                            <div class="panel-group" id="accordion">
                                <div class="panel panel-default" ng-repeat="page in pages track by $index">
                                    <div class="panel-heading">
                                        <h5 class="panel-title">
                                            <a ng-click="showPageComponents(page.id)" data-toggle="collapse" data-parent="#accordion" href="{{ path + '#' + page.id}}" aria-expanded="false" class="collapsed">{{page.name}}</a>
                                        </h5>
                                    </div>
                                    <div id="{{page.id}}" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                        <div class="panel-body">
                                            <ul style="list-style: none; padding-left: 2px;">
                                                <li ng-repeat="component in pageComponents[page.id]">
                                                    <label>{{component.description}}</label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>