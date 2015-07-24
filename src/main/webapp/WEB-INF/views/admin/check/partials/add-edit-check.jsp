
<div ng-controller="addEditCheckCtrl" style="margin-bottom: 50px;">
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <button ui-sref="check" class="btn btn-primary">Back to list</button>
        </div>
    </div>
    <div class="row-top-buffer"></div>
    <div class="row">
        <div class="col-md-12 col-lg-12">
            <div class="alert alert-info">{{ title }}</div>
        </div>
    </div>
    <form ng-submit="processForm()" ng-show="showForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="code">Code</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input required maxlength="10" ng-model="check.code" id="code" name="code" class="form-control"
                               type="text" placeholder="Enter check code"/>
                    </div>
                </div>
                <form-error err_field="errors.err_code"></form-error>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label" for="segmentAccount">Account</label>
                </div>
                <div class="col-md-5 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
                        <input value="" disabled ng-model="selectedAccount" id="segmentAccount" name="segmentAccount" class="form-control"  type="text" placeholder="Browse an account"/>
                    </div>
                </div>
                <div class="col-md-5 col-lg-5">
                    <button class="btn btn-primary" account-browser-s handler="accounts_selection_handler" >Browse accounts</button>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-9 col-lg-9">
                    <div class="inline-group">
                        <label><input ng-model="check.withSigner" type="checkbox" id="withSigner" name="withSigner"> withSigner</label>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-9 col-lg-9">
                    <div class="inline-group">
                        <label><input ng-model="check.showDesignation" type="checkbox" id="showDesignation" name="showDesignation"> showDesignation</label>
                    </div>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Fields</label>
                </div>
                <div class="col-md-2 col-lg-2" style="text-align: center">
                    <label class="input-label">Left</label>
                </div>
                <div class="col-md-2 col-lg-2" style="text-align: center">
                    <label class="input-label">Top</label>
                </div>
                <div class="col-md-2 col-lg-2" style="text-align: center">
                    <label class="input-label">Width</label>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Check number</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.checkNoX" id="checkNoX" name="checkNoX" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.checkNoY" id="checkNoY" name="checkNoY" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Date</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.dateX" id="dateX" name="dateX" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.dateY" id="dateY" name="dateY" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Payee</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.payeeX" id="payeeX" name="payeeX" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.payeeY" id="payeeY" name="payeeY" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.payeeW" id="payeeW" name="payeeW" class="form-control"
                           type="number" placeholder="0"/>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Amount</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.numericAmountX" id="numericAmountX" name="numericAmountX" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.numericAmountY" id="numericAmountY" name="numericAmountY" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Amount in Words</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.alphaAmountX" id="alphaAmountX" name="alphaAmountX" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.alphaAmountY" id="alphaAmountY" name="alphaAmountY" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.alphaAmountW" id="alphaAmountW" name="alphaAmountW" class="form-control"
                           type="number" placeholder="0"/>
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">First signature</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.sig1X" id="sig1X" name="sig1X" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.sig1Y" id="sig1Y" name="sig1Y" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                </div>
            </div>
        </div>

        <div class="row-top-buffer"></div>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                    <label class="input-label">Second signature</label>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.sig2X" id="sig2X" name="sig2X" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                    <input min="0" required ng-model="check.sig2Y" id="sig2Y" name="sig2Y" class="form-control"
                           type="number" placeholder="0"/>
                </div>
                <div class="col-md-2 col-lg-2">
                </div>
            </div>
        </div>

        <div class="row-top-buffer" style="margin-top: 35px;"></div>

        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-2 col-lg-2">
                </div>
                <div class="col-md-5 col-lg-5">
                    <fieldset ng-disabled="submitting">
                        <button ng-mousedown="submit = true" type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-disk"></span> {{ save }}</button>
                        <button type="reset" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                    </fieldset>
                </div>
            </div>
        </div>
    </form>
</div>


