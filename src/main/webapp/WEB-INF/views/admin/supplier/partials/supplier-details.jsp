<div class="row">
    <div class="col-md-12 col-lg-12">
        <a ng-click="main()" class="btn btn-primary">Suppliers</a>
        <a ui-sref="supplier.new" class="btn btn-primary">Add</a>
        <a ui-sref="supplier.edit({supplierId: supplierId})" class="btn btn-primary">Edit</a>
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="alert alert-info">{{ title }}</div>
    </div>
</div>
<div class="row" ng-show="showDetails">
    <div class="col-md-6 col-lg-6">
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Account Code</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.accountNumber }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Name</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.name }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Email</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label"><a  href="mailto:{{ user.email }}">{{ supplier.email }}</a></label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Address</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.address }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Contact Person</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.contactPerson }} - {{ supplier.contactPersonPosition }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Credit Limit</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.creditLimit }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Fax</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.fax }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Phone</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.phone }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">TIN</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.tin }}</label>
                </div>
            </div>


            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Status</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.status }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">VATable</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.vatable == 1 ? 'Yes' : 'No' }}</label>
                </div>
            </div>

        </div>
    </div>

    <%--second column--%>
    <div class="col-md-6 col-lg-6">
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Created By</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.createdBy.fullName }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Date created</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.createdAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                </div>
            </div>

            <div class="col-md-12 col-lg-12">
                <div class="col-md-3 col-lg-3">
                    <label class="input-label">Last updated</label>
                </div>
                <div class="col-md-9 col-lg-9">
                    <label class="value-label">{{ supplier.updatedAt | date:'MMM dd, yyyy HH:mm a' }}</label>
                </div>
            </div>
        </div>
    </div>
</div>