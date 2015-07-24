<div class="row">
    <div class="col-md-12 col-lg-12">
        <div class="col-md-2 col-lg-2">
            <label class="input-label">Journal Entries</label>
        </div>
        <div class="col-md-10 col-lg-10">
            <div class="table-responsive white-bg" style="max-height: 400px; overflow: auto">
                <table class="table table-striped">
                    <thead>
                    <tr>

                        <th style="width: 50%">Account</th>
                        <th class="text-center"><span>Debit</span></th>
                        <th class="text-center"><span>Credit</span></th>
                        <th style="width: 60px"  class="text-center"><a ng-click="newBlankRow()" style='padding: 0' title='Add'><i class='fa fa-plus'></i></a></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="entry in journalEntries">
                        <td>{{entry.code}}<br/>{{entry.description}}</td>
                        <td><label class="text-right form-control">{{entry.debit | currency: "": 2}}</label></td>
                        <td><label class="text-right form-control">{{entry.credit| currency: "": 2}}</label></td>
                        <td style="text-align: center"><a ng-click="setSelectedIdx($index)"
                                                          journal-entry-setter entities="entities"
                                                          handler="account_selection_handler"
                                                          account="entry"
                                                          sl="subLedgerLines[journalIdx]"
                                                          transid="transId"
                                                          distribution="journalEntries[journalIdx].distribution"
                                                          voucherdate="voucherDate"
                                                          tax-codes = "taxCodes"
                                                          style='padding: 0' title='Account'><i class='fa fa-angellist'></i></a>&nbsp;&nbsp;<a ng-click="removeJournalRow($index, entry)" style='padding: 0' title='Remove'><i class='fa fa-minus'></i></a></td>
                    </tr>

                    <tr>
                        <td class="font-bold">{{journalEntries.length == 0 ? "Add journal entries here!" : "TOTAL"}}</td>
                        <td class="text-right font-bold"><span ng-show="journalEntries.length > 0"  style="margin-right: 12px; font-size: 16px;">{{journalTotals.debit}}</span></td>
                        <td class="text-right font-bold"><span ng-show="journalEntries.length > 0"  style="margin-right: 12px; font-size: 16px;">{{journalTotals.credit}}</span></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>