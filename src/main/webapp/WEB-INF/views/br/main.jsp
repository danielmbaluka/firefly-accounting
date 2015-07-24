<div ui-view>
    <div class="row">
        <div class="col-md-12 col-lg-12">
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>List of Bank Reconciliations </h5>
                </div>
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                        </div>
                        <div class="col-sm-3 pull-right" style="width: 230px">
                            <a ui-sref="br.new" class="btn btn-primary">Add Other Deposits</a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-2 col-sm-1" style=" height: 51px; width: 50px;">
                            <label class="input-label" for="audit">Type</label>
                        </div>
                        <div class="col-sm-2 col-sm-3">
                            <div class="input-group">
                                <select required="" class="form-control" id="audit">
                                    <option value="">Default</option>
                                    <option value="">OC</option>
                                    <option value="">DT</option>
                                    <option value="">OD</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-2 col-sm-1" style=" height: 51px; width: 50px;">
                            <label class="input-label" for="audit">Status</label>
                        </div>
                        <div class="col-sm-2 col-sm-3">
                            <div class="input-group">
                                <select required="" class="form-control" id="audit2">
                                    <option value="">Default</option>
                                    <option value="">Cleared</option>
                                    <option value="">Not Cleared</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-2 col-sm-1" style=" height: 51px; width: 50px;">
                            <label class="input-label" for="date">Date</label>
                        </div>
                        <div class="col-sm-1 col-sm-3">
                            <div class="input-group date">
                                <span class="input-group-addon">
                                    <i class="fa fa-calendar">
                                    </i>
                                </span>
                                <input class="datepicker" type="text" class="input-sm form-control" id="date" name="date" value="02/14/2015">
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Referemce No.</th>
                                <th>Account</th>
                                <th>Date</th>
                                <th>Particulars</th>
                                <th><span class="pull-right">Amount</span></th>
                                <th>Type</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>CV-15-0064</td>
                                <td>Cash in Bank General Fund Current - LBP</td>
                                <td>Feb 15, 2015</td>
                                <td>Payment for various office supplies per APV#2014-02-74</td>
                                <td><span class="pull-right">23,630.45</span></td>
                                <td>OC</td>
                                <td><span class="label label-danger">Not Cleared</span></td>
                            </tr>
                            <tr>
                                <td>CV-15-0143</td>
                                <td>Cash in Bank Savings Metrobank</td>
                                <td>Mar 26, 2015</td>
                                <td>Payment for transformer which was not installed but paid</td>
                                <td><span class="pull-right">4,717.52</span></td>
                                <td>OC</td>
                                <td><span class="label label-info">Cleared</span></td>
                            </tr>
                            <tr>
                                <td>CRR-15-0003</td>
                                <td>Cash in Bank General Fund Current - PNB</td>
                                <td>Mar 03, 2015</td>
                                <td></td>
                                <td><span class="pull-right">10,654.32</span></td>
                                <td>DT</td>
                                <td><span class="label label-danger">Not Cleared</span></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>Cash in Bank General Fund Current - MetroBank</td>
                                <td>Jan 25, 2015</td>
                                <td>Unidentified deposits for Sept. 2014, Amount: 1,185.16</td>
                                <td><span class="pull-right">1,185.16</span></td>
                                <td>OD</td>
                                <td><span class="label label-danger">Not Cleared</span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('.datepicker').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });

    $(function () {
        $('[data-toggle="popover"]').popover({ trigger: "hover", html: true })
    })
</script>
