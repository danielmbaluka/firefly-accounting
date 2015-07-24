package com.tri.erp.spring.service.implementations;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.tri.erp.spring.commons.Debug;
import com.tri.erp.spring.commons.GlobalConstant;
import com.tri.erp.spring.commons.facade.AuthenticationFacade;
import com.tri.erp.spring.commons.facade.DocumentProcessingFacade;
import com.tri.erp.spring.commons.facade.GeneratorFacade;
import com.tri.erp.spring.commons.facade.LedgerFacadeImpl;
import com.tri.erp.spring.commons.helpers.Checker;
import com.tri.erp.spring.commons.helpers.MessageFormatter;
import com.tri.erp.spring.commons.helpers.ReportUtil;
import com.tri.erp.spring.commons.helpers.StringFormatter;
import com.tri.erp.spring.dtoers.LedgerDtoerImpl;
import com.tri.erp.spring.model.*;
import com.tri.erp.spring.model.Document;
import com.tri.erp.spring.model.DocumentStatus;
import com.tri.erp.spring.model.Workflow;
import com.tri.erp.spring.repo.*;
import com.tri.erp.spring.response.*;
import com.tri.erp.spring.response.reports.CommonLedgerDetail;
import com.tri.erp.spring.response.reports.CheckDto;
import com.tri.erp.spring.service.interfaces.Bir2307;
import com.tri.erp.spring.service.interfaces.CvService;
import com.tri.erp.spring.service.interfaces.PrintableCheque;
import com.tri.erp.spring.service.interfaces.PrintableVoucher;
import com.tri.erp.spring.validator.CvValidator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * Created by TSI Admin on 10/9/2014.
 */

@Service(value = "cvServiceImpl")
public class CvServiceImpl implements CvService, PrintableVoucher, PrintableCheque, Bir2307 {

    @Autowired
    GeneratorFacade generatorFacade;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    CheckVoucherRepo cvRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    SlEntityRepo slEntityRepo;

    @Autowired
    LedgerFacadeImpl ledgerFacade;

    @Autowired
    LedgerDtoerImpl ledgerDtoers;

    @Autowired
    DocumentWorkflowActionMapRepo workflowActionMapRepo;

    @Autowired
    DocumentProcessingFacade documentProcessingFacade;

    @Autowired
    CheckVoucherChequeRepo chequeRepo;

    @Autowired
    CheckConfigRepo checkConfigRepo;

    @Autowired
    CheckVoucherApvRepo checkVoucherApvRepo;

    @Autowired
    AccountsPayableVoucherRepo apvRepo;

    @Autowired
    ReleasedCheckRepo releasedCheckRepo;

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    private TokenService tokenService;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    CheckVoucherIncomePaymentRepo incomePaymentRepo;

    @Override
    @Transactional
    public PostResponse processUpdate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        CheckVoucher cv = (CheckVoucher) v;
        return this.processCreate(cv, bindingResult, messageSource);
    }

    @Override
    @Transactional
    public PostResponse processCreate(Document v, BindingResult bindingResult, MessageSource messageSource) {
        CheckVoucher cv = (CheckVoucher) v;
        PostResponse response = new PostResponse();
        MessageFormatter messageFormatter = new MessageFormatter(bindingResult, messageSource, response);

        CvValidator validator = new CvValidator();
        validator.setLedgerDtoer(this.ledgerDtoers); // this is f*cking workaround
        validator.validate(cv, bindingResult);

        if (bindingResult.hasErrors()) {
            messageFormatter.buildErrorMessages();
            response = messageFormatter.getResponse();
        } else {
            User createdBy = authenticationFacade.getLoggedIn();
            CheckVoucher existingCv = null;

            Integer voucherYear = Integer.parseInt(GlobalConstant.YYYY_DATE_FORMAT.format(cv.getVoucherDate()));
            User approvingOfficer = userRepo.findOneByAccountNo(cv.getApprovingOfficer().getAccountNo());
            User checker = userRepo.findOneByAccountNo(cv.getChecker().getAccountNo());
            User recApp = userRepo.findOneByAccountNo(cv.getRecommendingOfficer().getAccountNo());
            User auditor = userRepo.findOneByAccountNo(cv.getAuditor().getAccountNo());

            Boolean insertMode = cv.getId() == null;
            if (insertMode) { // insert mode
                Object latestApvCode = cvRepo.findLatestCvCodeByYear(voucherYear);
                cv.setCode(generatorFacade.voucherCode("CV", (latestApvCode == null ? "" : String.valueOf(latestApvCode)), cv.getVoucherDate()));

                DocumentStatus documentStatus = new DocumentStatus();
                documentStatus.setId(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                Workflow wf = new Workflow();
                wf.setId(com.tri.erp.spring.model.enums.Workflow.CV.getId());

                cv.setDocumentStatus(documentStatus);
                cv.setCreatedBy(createdBy);
                cv.setTransaction(generatorFacade.transaction());
                cv.setWorkflow(wf);

                existingCv = cv;

            } else {
                existingCv = cvRepo.findOne(cv.getId());

                List<Integer> statusAllowed = new ArrayList();
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.DOCUMENT_CREATED.getId());
                statusAllowed.add(com.tri.erp.spring.model.enums.DocumentStatus.RETURNED_TO_CREATOR.getId());

                if (statusAllowed.indexOf(existingCv.getDocumentStatus().getId()) < 0) {

                    ArrayList<String> messages = new ArrayList();
                    messages.add("Action is not allowed");

                    response.isNotAuthorized(true);
                    response.setMessages(messages);
                    response.setSuccess(false);

                    return response;
                }
            }

            // editable fields
            existingCv.setPayee(cv.getPayee());
            existingCv.setParticulars(cv.getParticulars());
            existingCv.setVoucherDate(cv.getVoucherDate());
            existingCv.setApprovingOfficer(approvingOfficer);
            existingCv.setChecker(checker);
            existingCv.setRecommendingOfficer(recApp);
            existingCv.setAuditor(auditor);
            existingCv.setYear(voucherYear);
            existingCv.setAmount(cv.getAmount());
            existingCv.setCheckAmount(cv.getCheckAmount());

            CheckVoucher newCv = cvRepo.save(existingCv);

            if (newCv != null) {
                // save apv
                checkVoucherApvRepo.deleteByCheckVoucherId(newCv.getId()); // with or without

                if (!Checker.collectionIsEmpty(cv.getAccountsPayableVouchers())) {
                    for(AccountsPayableVoucher apv:cv.getAccountsPayableVouchers()) {

                        CheckVoucherApv s = new CheckVoucherApv();
                        s.setCheckVoucher(newCv);
                        s.setAccountsPayableVoucher(apv);

                        checkVoucherApvRepo.save(s);
                    }
                }

                List<GeneralLedgerLineDto2> cvGeneralLedgerLines = cv.getGeneralLedgerLines();

                // save check numbers
                chequeRepo.deleteByTransactionId(newCv.getTransaction().getId());
                List<CheckVoucherCheque> checkNumbers = cv.getCheckNumbers();
                if (!Checker.collectionIsEmpty(checkNumbers)) {
                    for(CheckVoucherCheque ch:checkNumbers) {
                        ch.setReleased(false);
                        ch.setTransaction(newCv.getTransaction());
                        chequeRepo.save(ch);
                    }
                }
                ledgerFacade.postGeneralLedger(newCv.getTransaction(), cvGeneralLedgerLines, cv.getSubLedgerLines());

                if (insertMode) { // log action only when adding document
                    documentProcessingFacade.processAction(newCv.getTransaction(), null, newCv.getWorkflow(), createdBy);
                }

                response.setModelId(newCv.getId());
                response.setSuccessMessage("CV successfully saved!");
                response.setSuccess(true);
            }
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CvListDto> findAll() {
        List<CheckVoucher> vouchers = cvRepo.findAll();

        List<CvListDto> returnVouchers = new ArrayList<>();
        if (!Checker.collectionIsEmpty(vouchers)) {
            for(CheckVoucher cv : vouchers) {
                CvListDto cvListDto = new CvListDto();
                cvListDto.setPayee(cv.getPayee().getName());
                cvListDto.setId(cv.getId());
                cvListDto.setCheckAmount(cv.getCheckAmount());
                cvListDto.setCode(cv.getCode());
                cvListDto.setParticulars(cv.getParticulars());
                cvListDto.setStatus(cv.getDocumentStatus().getStatus());
                cvListDto.setVoucherDate(cv.getVoucherDate());

                returnVouchers.add(cvListDto);
            }

            return returnVouchers;
        }
        return null;
    }

    @Override
    public CvDto findById(Integer id) {

        CheckVoucher checkVoucher =  cvRepo.findOne(id);
        CvDto cvDto = new CvDto();

        List<Object[]> list = checkVoucherApvRepo.findByCheckVoucherId(id);
        ApvDto apvDto = null;
        if (!Checker.collectionIsEmpty(list)) {
            Integer apvId = (Integer)(list.get(0)[0]); // take one only for now
            AccountsPayableVoucher v = apvRepo.findOne(apvId);
            if (v != null) {
                apvDto = new ApvDto();
                apvDto.setId(v.getId());
                apvDto.setAmount(v.getAmount());
                apvDto.setVendor(v.getVendor());
                apvDto.setLocalCode(v.getCode());
                apvDto.setTransId(v.getTransaction().getId());
            }
        }

        if (checkVoucher != null) {
            cvDto.setId(checkVoucher.getId());
            cvDto.setParticulars(checkVoucher.getParticulars());
            cvDto.setLocalCode(checkVoucher.getCode());
            cvDto.setTransId(checkVoucher.getTransaction().getId());

            SlEntity approvingOfficer = slEntityRepo.findOne(checkVoucher.getApprovingOfficer().getAccountNo());
            SlEntity checker = slEntityRepo.findOne(checkVoucher.getChecker().getAccountNo());
            SlEntity rao = slEntityRepo.findOne(checkVoucher.getRecommendingOfficer().getAccountNo());
            SlEntity auditor = slEntityRepo.findOne(checkVoucher.getAuditor().getAccountNo());

            if (apvDto != null) {
                cvDto.setApvDto(apvDto);
            }
            cvDto.setPayee(checkVoucher.getPayee());
            cvDto.setApprovingOfficer(approvingOfficer);
            cvDto.setAuditor(auditor);
            cvDto.setRecommendingOfficer(rao);
            cvDto.setChecker(checker);
            cvDto.setVoucherDate(checkVoucher.getVoucherDate());
            cvDto.setAmount(checkVoucher.getAmount());
            cvDto.setCheckAmount(checkVoucher.getCheckAmount());
            cvDto.setDocumentStatus(checkVoucher.getDocumentStatus());
            cvDto.setCreated(checkVoucher.getCreatedAt());
            cvDto.setLastUpdated(checkVoucher.getUpdatedAt());
        }

        return  cvDto;
    }

    @Override
    public ApvDto checkPrintingParams(Integer transId, Integer backAccountId) {
        return null;
    }

    @Override
    public PostResponse updateCheckNumber(CheckVoucherCheque cheque, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        chequeRepo.deleteByBankAccountIdAndTransactionId(cheque.getBankAccount().getId(), cheque.getTransaction().getId());

        if (cheque.getCheckNumber() != null && cheque.getCheckNumber().trim().length() > 0) {
            cheque.setReleased(false);
            CheckVoucherCheque newCh = chequeRepo.save(cheque);
            if (newCh != null) {
                response.setSuccess(true);
            }
        } else { // when removing
            response.setSuccess(true);
        }

        return response;
    }

    @Override
    public CheckDto findForPrintCheckDetails(Integer transId, Integer bankAccountId) {
        CheckVoucher checkVoucher = cvRepo.findOneByTransactionId(transId);

        if (checkVoucher != null && checkVoucher.getDocumentStatus().getId() == com.tri.erp.spring.model.enums.DocumentStatus.FOR_CHECK_WRITING.getId()) {

            CheckVoucherCheque c = chequeRepo.findOneByBankAccountIdAndTransactionId(bankAccountId, transId);

            CheckDto check = new CheckDto();
            check.setCheckNumber(c.getCheckNumber());
//            check.setAlphaAmount(CurrencyIntoWords.convert(checkVoucher.getCheckAmount()));
            check.setDate(new SimpleDateFormat("MMMM dd, yyyy").format(checkVoucher.getVoucherDate()));
            check.setNumericAmount(new DecimalFormat("#,##0.00").format(checkVoucher.getCheckAmount()));
            check.setPayee(checkVoucher.getPayee().getName());
            check.setSig1(checkVoucher.getApprovingOfficer().getFullName());

            return check;
        }
        return null;
    }

    @Override
    public PostResponse process(ProcessDocumentDto postData, BindingResult bindingResult, MessageSource messageSource) {
        PostResponse response = new PostResponse();

        User processedBy = authenticationFacade.getLoggedIn();
        CheckVoucher checkVoucher =  cvRepo.findOne(postData.getDocumentId());

        if (checkVoucher != null && checkVoucher.getDocumentStatus().getId() != com.tri.erp.spring.model.enums.DocumentStatus.APPROVED.getId()) {
            DocumentWorkflowActionMap actionMap = workflowActionMapRepo.findOne(postData.getWorkflowActionsDto().getActionMapId());
            DocumentStatus afterActionDocumentStatus = actionMap.getAfterActionDocumentStatus();

            checkVoucher.setDocumentStatus(afterActionDocumentStatus);
            checkVoucher.setUpdatedAt(null);
            checkVoucher = cvRepo.save(checkVoucher);

            if (checkVoucher != null) {
                documentProcessingFacade.processAction(checkVoucher.getTransaction(), actionMap, null, processedBy);

                response.setSuccessMessage("Document successfully processed");
                response.setSuccess(true);
            }

        }
        return response;
    }

    @Override
    public HashMap reportParameters(Integer vid, HttpServletRequest request) {
        HashMap<String, Object> params = ReportUtil.setupSharedReportHeaders(request);

        CheckVoucher checkVoucher = cvRepo.findOne(vid);

        if (checkVoucher != null) {
            params.put("TRANS_ID", checkVoucher.getTransaction().getId());
            params.put("VOUCHER_NO", checkVoucher.getCode());
            params.put("V_DATE", checkVoucher.getVoucherDate());
//            params.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(checkVoucher.getCheckAmount()) + "***" + "  (P " + new DecimalFormat("#,##0.00").format(checkVoucher.getCheckAmount()) + ")***");
            params.put("TOTAL", "P " + new DecimalFormat("#,##0.00").format(checkVoucher.getCheckAmount()));
            params.put("APPROVAR", checkVoucher.getApprovingOfficer().getFullName());
            params.put("APPROVAR_POS", "");
            params.put("CHECKER", checkVoucher.getChecker().getFullName());
            params.put("CHECKER_POS", "");
            params.put("PREPARAR", checkVoucher.getCreatedBy().getFullName());
            params.put("PREPARAR_POS", "");
            params.put("RECOMMENDAR", checkVoucher.getRecommendingOfficer().getFullName());
            params.put("RECOMMENDAR_POS", "");
            params.put("AUDITOR", checkVoucher.getAuditor().getFullName());
            params.put("AUDITOR_POS", "");
            params.put("SUPPLIER_NAME", checkVoucher.getPayee().getName());
            params.put("SUPPLIER_ADDR", checkVoucher.getPayee().getAddress());
            params.put("REMARKS", checkVoucher.getParticulars().trim());
            params.put("NOTES", checkVoucher.getRemarks());
            params.put("CHECK_NOS", "");
        }

        return params;
    }

    @Override
    public JRDataSource datasource(Integer vid) {
        List<CommonLedgerDetail> details = new ArrayList<>();

        CheckVoucher voucher = cvRepo.findOne(vid);
        if (voucher != null) {
            details = ledgerDtoers.getVoucherLedgerLines(voucher.getTransaction().getId());
        }
        return new JRBeanCollectionDataSource(details);
    }

    @Override
    public HashMap reportParameters(Integer transId, Integer bankAccountId) {
        HashMap<String, Object> par = new HashMap<String, Object>();
        CheckVoucher checkVoucher = cvRepo.findOneByTransactionId(transId);
        CheckVoucherCheque ch = chequeRepo.findOneByBankAccountIdAndTransactionId(bankAccountId, transId);
        CheckConfig config = checkConfigRepo.findOneByBankSegmentAccountId(bankAccountId);

        par.put("DATE", new SimpleDateFormat("MMMM dd, yyyy").format(checkVoucher.getVoucherDate()));
        par.put("PAYEE", checkVoucher.getPayee().getName() + "***");
        par.put("AMOUNT", new DecimalFormat("#,##0.00").format(checkVoucher.getCheckAmount()) + "***");
//        par.put("AMOUNT_IN_WORDS", CurrencyIntoWords.convert(checkVoucher.getCheckAmount()).toUpperCase() + "***");
        par.put("CHECK_NO", ch.getCheckNumber());

        Debug.print(config.getWithSigner());
        if (config.getWithSigner().equals(1)) {
            String s1 = "<b>" + checkVoucher.getApprovingOfficer().getFullName().toUpperCase() + "</b>";
            par.put("SIGN1", s1);
        }
        return par;
    }

    @Override
    public List<Map> findCvChecks(Integer transId) {
        List<CheckVoucherCheque> cheques = chequeRepo.findByTransactionId(transId);

        List<Map> map = new ArrayList<>();

        if (!Checker.collectionIsEmpty(cheques)) {
            for(CheckVoucherCheque ch:cheques) {
                Map m = new HashMap();

                Map bankAccount = new HashMap();
                bankAccount.put("id", ch.getBankAccount().getId());

                m.put("transId", ch.getTransaction().getId());
                m.put("checkNumber", ch.getCheckNumber());
                m.put("bankAccount", bankAccount);
                m.put("released", ch.isReleased());

                map.add(m);
            }
        }

        return map;
    }

    @Override
    public List<Map> findChequeNumbersForCheckReleasing(Integer transId) {
        List<Map> map = new ArrayList<>();
        List<CheckVoucherCheque> cheques = chequeRepo.findByTransactionIdAndReleased(transId, false);

        if (!Checker.collectionIsEmpty(cheques)) {
            for(CheckVoucherCheque cvc: cheques) {

                Map c = new HashMap();
                c.put("segmentAccountCode", cvc.getBankAccount().getAccountCode());
                c.put("account", cvc.getBankAccount().getAccount().getTitle());
                c.put("checkNumber", cvc.getCheckNumber());
                c.put("id", cvc.getId());

                map.add(c);
            }
        }

        return map;
    }

    @Override
    public List<Map> findCheckVouchersForReleasing() {
        List<Map> map = new ArrayList<>();
        List<Object[]> checkVouchers = cvRepo.findAllForCheckReleasing();
        if (!Checker.collectionIsEmpty(checkVouchers)) {
            for(Object[] row: checkVouchers) {

                Map cv = new HashMap();
                cv.put("id", row[0]);
                cv.put("transId", row[1]);
                cv.put("checkAmount", row[2]);
                cv.put("particulars", row[3]);
                cv.put("code", row[4]);
                cv.put("voucherDate", row[5]);
                cv.put("payee", row[6]);
                cv.put("remarks", row[7]);

                map.add(cv);
            }
        }

        return map;
    }

    @Override
    public void fillPdf(Integer transId, Integer payeeAccountNo, String token, HttpServletResponse response) {
        HashMap<String, Object> par = new HashMap<String, Object>();
        try {
            CheckVoucher checkVoucher = cvRepo.findOneByTransactionId(transId);

            if (checkVoucher == null) return;

            Calendar myCal = new GregorianCalendar();
            myCal.setTime(checkVoucher.getVoucherDate());

            int firstDate = myCal.getActualMinimum(Calendar.DATE);
            myCal.set(Calendar.DATE, firstDate);
            par.put("DATE_FROM_DD", new SimpleDateFormat("dd").format(myCal.getTime()));
            par.put("DATE_FROM_MM", new SimpleDateFormat("MM").format(myCal.getTime()));
            par.put("DATE_FROM_YY", new SimpleDateFormat("yy").format(myCal.getTime()));

            int lastDate =  myCal.getActualMaximum(Calendar.DATE);
            myCal.set(Calendar.DATE, lastDate);
            par.put("DATE_TO_DD",  new SimpleDateFormat("dd").format(myCal.getTime()));
            par.put("DATE_TO_MM",  new SimpleDateFormat("MM").format(myCal.getTime()));
            par.put("DATE_TO_YY",  new SimpleDateFormat("yy").format(myCal.getTime()));

            par.put("PAYOR_NAME", "ILOILO I ELECTRIC COOPERATIVE, INC");
            par.put("PAYOR_REG_ADDRESS", "PBrgy. Namocon, Tigbauan Iloilo, Philippines");
            par.put("PAYOR_REG_ZIP", "5021");
            par.put("PAYOR_TIN1", "***");
            par.put("PAYOR_TIN2", "***");
            par.put("PAYOR_TIN3", "***");
            par.put("PAYOR_TIN4", "***");


            Supplier supplier = supplierRepo.findOneByAccountNumber(payeeAccountNo);
            if (supplier == null) {
                Employee employee = employeeRepo.findOneByAccountNumber(payeeAccountNo);
                if (employee != null) {
                    String[] tinArr = StringFormatter.breakTIN(employee.getTin());
                    par.put("PAYEE_TIN1", tinArr[0]);
                    par.put("PAYEE_TIN2", tinArr[1]);
                    par.put("PAYEE_TIN3", tinArr[2]);
                    par.put("PAYEE_TIN4", tinArr[3]);

                    par.put("PAYEE_NAME", employee.getName());

                    par.put("PAYEE_REG_ADDRESS", employee.getAddress("R"));
                    par.put("PAYEE_REG_ZIP", employee.getZip());
                    par.put("PAYEE_FOR_ADDRESS", employee.getAddress("F"));
                    par.put("PAYEE_FOR_ZIP", "");
                }
            } else {

                String[] tinArr = StringFormatter.breakTIN(supplier.getTin());
                par.put("PAYEE_TIN1", tinArr[0]);
                par.put("PAYEE_TIN2", tinArr[1]);
                par.put("PAYEE_TIN3", tinArr[2]);
                par.put("PAYEE_TIN4", tinArr[3]);

                par.put("PAYEE_NAME", supplier.getName());
                par.put("PAYEE_REG_ADDRESS", supplier.getAddress());
                par.put("PAYEE_REG_ZIP", supplier.getZip());
                par.put("PAYEE_FOR_ADDRESS", "");
                par.put("PAYEE_FOR_ZIP", "");
            }
            // Create an output byte stream where data will be written
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            String template = GlobalConstant.JASPER_BASE_PATH + "/templates/BIRForm2307.pdf";
            String newPdfFilename = "/bir2307-" + (new SimpleDateFormat("MMMddyyyy").format(checkVoucher.getVoucherDate())) + ".pdf";

            PdfReader.unethicalreading = true;
            PdfReader pdfReader = new PdfReader(template);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);

            // write contents here:
            BaseFont font = BaseFont.createFont();
            PdfContentByte overContent = pdfStamper.getOverContent(1);
            overContent.saveState();
            overContent.setFontAndSize(font, 10.0f);

            overContent.beginText();
            overContent.moveText(103, 899);
            overContent.showText(par.get("DATE_FROM_MM").toString() + "     " + par.get("DATE_FROM_DD").toString() + "      " + par.get("DATE_FROM_YY").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(330, 899);
            overContent.showText(par.get("DATE_TO_MM").toString() + "     " + par.get("DATE_TO_DD").toString() + "      " + par.get("DATE_TO_YY").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(116, 865);
            overContent.showText(par.get("PAYEE_TIN1").toString() + "            " +
                    par.get("PAYEE_TIN2").toString() + "             " +
                    par.get("PAYEE_TIN3").toString() + "             " +
                    par.get("PAYEE_TIN4").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(115, 845);
            overContent.showText(par.get("PAYEE_NAME").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(115, 818);
            overContent.showText(par.get("PAYEE_REG_ADDRESS").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(541, 820);
            overContent.showText(par.get("PAYEE_REG_ZIP").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(115, 801);
            overContent.showText(par.get("PAYEE_FOR_ADDRESS").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(541, 802);
            overContent.showText(par.get("PAYEE_FOR_ZIP").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(116, 771);
            overContent.showText(par.get("PAYOR_TIN1").toString() + "            " +
                    par.get("PAYOR_TIN2").toString() + "              " +
                    par.get("PAYOR_TIN3").toString() + "              " +
                    par.get("PAYOR_TIN4").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(115, 750);
            overContent.showText(par.get("PAYOR_NAME").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(115, 725);
            overContent.showText(par.get("PAYOR_REG_ADDRESS").toString());
            overContent.endText();

            overContent.beginText();
            overContent.moveText(541, 726);
            overContent.showText(par.get("PAYOR_REG_ZIP").toString());
            overContent.endText();

            // income payments
            CheckVoucherIncomePayment incomePayment = incomePaymentRepo.findOneByTransactionId(transId);

            String incomePaymentDesc = incomePayment.getTaxCode().getDescription();
            Font f = new Font(font);
            f.setSize(9.0f);

            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase(incomePaymentDesc, f));
            cell.setLeading(1.1f, 1.1f);
            cell.setNoWrap(false);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            ColumnText ct = new ColumnText(overContent);
            ct.setSimpleColumn(180, 500, 2, 670, 9, PdfContentByte.ALIGN_LEFT);
            ct.addElement(table);
            ct.go();

            // tax code: ATC
            overContent.setFontAndSize(font, 10.0f);
            overContent.beginText();
            overContent.moveText(165, 659);
            overContent.showText(incomePayment.getTaxCode().getCode());
            overContent.endText();

            // total
            BigDecimal amount = incomePayment.getAmount();
            String total = new DecimalFormat("#,##0.00").format(amount);
            overContent.beginText();
            overContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, total, 485, 659, 0);
            overContent.endText();

            // Tax Withheld for the quarter
            overContent.beginText();
            overContent.showTextAligned(PdfContentByte.ALIGN_RIGHT, total, 591, 659, 0);
            overContent.endText();

            overContent.restoreState();

            pdfStamper.close();
            pdfReader.close();

            // Set our response properties
            response.setHeader("Content-Disposition", "inline; filename="+ newPdfFilename);

            // Set content type
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            // Write to response stream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();

            tokenService.remove(token);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
