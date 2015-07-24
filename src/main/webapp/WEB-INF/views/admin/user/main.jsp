<style>
    #users li {display: inline}
</style>
<div style="margin-bottom: 50px;">
    <ul id="users">
        <li  ui-sref-active="active">
            <a ui-sref="user.list"><i class="fa fa-laptop"></i> <span class="nav-label">Users</span> </a>
        </li>

        <li  ui-sref-active="active">
            <a ui-sref="user.roles"><i class="fa fa-laptop"></i> <span class="nav-label">Roles</span> </a>
        </li>
    </ul>

    <div ui-view></div>

</div>