<div ui-view>
    <div style="margin-top: 20px;"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">My Profile</div>
            <a ui-sref="profile.edit()" class="btn btn-primary">Edit</a>
        </div>
    </div>
    <div class="row" ng-show="showDetails" ng-controller="userDetailsCtrl">
        <div class="col-md-7 col-lg-7">
            <div class="row">
                <div class="col-md-12 col-lg-12">
                    <div class="col-md-3 col-lg-3">
                        <label class="input-label">Full name</label>
                    </div>
                    <div class="col-md-9 col-lg-9">
                        <label class="value-label">{{ user.fullName }}</label>
                    </div>
                </div>

                <div class="col-md-12 col-lg-12">
                    <div class="col-md-3 col-lg-3">
                        <label class="input-label">Username</label>
                    </div>
                    <div class="col-md-9 col-lg-9">
                        <label class="value-label">{{ user.username }}</label>
                    </div>
                </div>

                <div class="col-md-12 col-lg-12">
                    <div class="col-md-3 col-lg-3">
                        <label class="input-label">Email</label>
                    </div>
                    <div class="col-md-9 col-lg-9">
                        <label class="value-label"><a  href="mailto:{{ user.email }}">{{ user.email }}</a></label>
                    </div>
                </div>

                <div class="col-md-12 col-lg-12">
                    <div class="col-md-3 col-lg-3">
                        <label class="input-label">Date registered</label>
                    </div>
                    <div class="col-md-9 col-lg-9">
                        <label class="value-label">{{ user.createdAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                    </div>
                </div>

                <div class="col-md-12 col-lg-12">
                    <div class="col-md-3 col-lg-3">
                        <label class="input-label">Last updated</label>
                    </div>
                    <div class="col-md-9 col-lg-9">
                        <label class="value-label">{{ user.updatedAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-5 col-lg-5">
            <div class="row"><label class="input-label">Roles</label></div>
            <div class="row">
                <ul style="list-style: disc; padding-left: 10px;">
                    <li ng-repeat="role in user.roles">
                        {{role.name}}
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>