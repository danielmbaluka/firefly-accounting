package com.tri.erp.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by TSI Admin on 9/18/2014.
 */

@Controller
@RequestMapping("/common")
public class CommonViewController {

    @RequestMapping("/account-browser")
    public String getAccountBrowser() {
        return "common/account-browser";
    }

    @RequestMapping("/account-browser-with-segment")
    public String getAccountBrowserWithSegment() {
        return "common/account-browser-with-segment";
    }

    @RequestMapping("/voucher-sl-setter")
    public String getVoucherSlSetter() {
        return "common/voucher-sl-setter2";
    }

    @RequestMapping("/sl-entity-browser")
    public String getSlEntityBrowser() {
        return "common/sl-entity-browser";
    }

    @RequestMapping("/item-browser")
    public String getItemBrowser() {
        return "common/item-browser";
    }

    @RequestMapping("/form-field-error-msg")
    public String getErrorViewer() {
        return "common/form-field-error-msg";
    }

    @RequestMapping("/notifications-page")
    public String getNotificationsPage() {
        return "common/notifications";
    }

    @RequestMapping("/process-voucher")
    public String getProcessVoucherPage() {
        return "common/process-voucher";
    }

    @RequestMapping("/requisition-voucher-browser")
    public String getRequisitionVoucherBrowser() {
        return "common/requisition-voucher-browser";
    }

    @RequestMapping("/mrct-browser")
    public String getMRCTBrowser() {
        return "common/mrct-browser";
    }

    @RequestMapping("/cashflow")
    public String getCashflow() {
        return "common/cashflow";
    }

    @RequestMapping("/sales-posting-detail-browser")
    public String getSalesVoucherDetailBrowser() {
        return "common/sales-posting-detail-browser";
    }

    @RequestMapping("/rv-detail-for-canvass-browser")
    public String getRvDetailForCanvassBrowser() {
        return "common/rv-detail-for-canvass-browser";
    }

    @RequestMapping("/rv-detail-for-po-browser")
    public String getRvDetailForPoBrowser() {
        return "common/rv-detail-for-po-browser";
    }

    @RequestMapping("/apv-browser-modal")
    public String getApvBrowserModal() {
        return "common/apv-browser-modal";
    }

    @RequestMapping("/account-browser-modal")
    public String getAccountBrowserModal() {
        return "common/account-browser-modal";
    }

    @RequestMapping("/journal-entries")
    public String getJournalEntriesDirective() {
        return "common/journal-entries";
    }

    @RequestMapping("/journal-entry-setter-modal")
    public String getJournalEntrySetterModal() {
        return "common/journal-entry-setter-modal";
    }

    @RequestMapping("/temp-ledger-entry-selector-modal")
    public String getTempLedgerEntrySelectorModal() {
        return "common/temp-ledger-entry-selector-modal";
    }
}
