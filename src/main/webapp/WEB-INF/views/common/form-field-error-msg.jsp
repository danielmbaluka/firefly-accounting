<span style="color: red !important;" ng-show="errField">
    <ul class="error-list">
        <li ng-repeat="err in errField track by $index">{{ err }}</li>
    </ul>
</span>