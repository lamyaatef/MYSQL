/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.managed_beans;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.mobinil.mcss.credit_advice.model.CAFile;
import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.CreditDetails;
import com.mobinil.mcss.credit_advice.model.StatusFile;
import com.mobinil.mcss.credit_advice.service.SearchCreditAdviceService;
import com.mobinil.mcss.credit_advice.service.SearchCreditAdviceServiceImpl;
import com.mobinil.mcss.credit_advice.util.DbReportFill;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Shady Akl
 */
@ManagedBean(name = "searchCa")
@ViewScoped
public class SearchCreditAdviceBean {

    private Long memoId;
    private String memoName = null;
    private String scmCode = null;
    private String scmName = null;
    private String creditSerial = null;
    private String creditName = null;
    private Date creationDateTo = null;
    private Date creationDateFrom = null;
    private String amount = null;
    private int status = 0;
    private Long batchId = null;
    private List<CAFile> files;
    private List<CAMemo> memosList;
    private List<StatusFile> statusFileList;
    List<CreditDetails> CreditDetailsList;
    SearchCreditAdviceService searchCreditAdviceService = new SearchCreditAdviceServiceImpl();
    private Map<Long, String> checked = new HashMap<Long, String>();
    private StreamedContent detailedReport;
    private StreamedContent creditAdviceReport;
    String fileName;

    @PostConstruct
    public void fillLookup() {

        if (statusFileList == null) {
            statusFileList = searchCreditAdviceService.fillStatus();

            for (StatusFile status : statusFileList) {

                System.out.println("status:" + status.getStatusName());

            }
        }

    }

    public void setDetailedReport(StreamedContent detailedReport) {
        this.detailedReport = detailedReport;
    }

    public void generateDetailedReport(ActionEvent ae) throws AbortProcessingException {
        System.out.println("generateDetailedReport:");

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String fileId = params.get("fileIdToPrint");
        Long fileId2 = Long.valueOf(fileId);
        System.out.println("FILLLLLLLLEIDDDDDDD" + fileId2);
        FacesContext context = FacesContext.getCurrentInstance();

        ExternalContext externalContext = context.getExternalContext();

        String fileContextPath = externalContext.getRealPath("ca/reports/crd_file.jrxml");
        fileName = searchCreditAdviceService.updateCAFilePrinting(fileId2, fileContextPath);
    }

    public StreamedContent getDetailedReport() throws FileNotFoundException {
        System.out.println("getDetailedReport:");

        File result = new File("sds//credit_advice//crd_file_" + fileName + ".pdf");
        InputStream stream2 = new FileInputStream(result.getAbsolutePath());
        detailedReport = new DefaultStreamedContent(stream2, "application/pdf", "crd_file_" + fileName + ".pdf");
        return detailedReport;
    }

    public StreamedContent getCreditAdviceReport() throws FileNotFoundException {
        System.out.println("Download Report:");

        File result = new File("sds//credit_advice//crd_" + fileName + ".pdf");

        System.out.println("Download Report Path is:"+result.getAbsolutePath());
        InputStream stream2 = new FileInputStream(result.getAbsolutePath());
        creditAdviceReport = new DefaultStreamedContent(stream2, "application/pdf", "crd_" + fileName + ".pdf");
        
        return creditAdviceReport;
    }

    public void setCreditAdviceReport(StreamedContent creditAdviceReport) {
        this.creditAdviceReport = creditAdviceReport;
    }

    public void viewCreditAdvice() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String memoId = params.get("memoId");
        Long memoId2 = Long.valueOf(memoId);
        String fileId = params.get("fileId");
        Long fileId2 = Long.valueOf(fileId);
        CreditDetailsList = searchCreditAdviceService.getCreditAdvices(memoId2, fileId2);

    }

    public void printCA() {

        List<Long> cAMemos = new ArrayList<Long>();
        String fileContextPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("ca/reports/crd.jrxml");

        for (CreditDetails creditDetial : CreditDetailsList) {
            if (checked.get(creditDetial.getCaId()).equals("true")) {
                cAMemos.add(creditDetial.getCaId());
                System.out.println("Checked Credit Advices:" + creditDetial.getCaId());

            }

        }
        searchCreditAdviceService.updateCAPrinting(cAMemos, fileContextPath);


    }

    public void generateCAReport(ActionEvent ae) throws AbortProcessingException {
        List<Long> cAMemos = new ArrayList<Long>();
        String fileContextPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("ca/reports/crd.jrxml");

        for (CreditDetails creditDetial : CreditDetailsList) {
            if (checked.get(creditDetial.getCaId()).equals("true")) {
                cAMemos.add(creditDetial.getCaId());
                System.out.println("Checked Credit Advices:" + creditDetial.getCaId());

            }

        }
        fileName = searchCreditAdviceService.updateCAPrinting(cAMemos, fileContextPath);
    }

    public void findGeneratedMemos() {

        files = searchCreditAdviceService.getMemos(memoId, memoName, creationDateFrom, creationDateTo, scmCode, scmName, creditSerial, amount, status, batchId);
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public String getCreditSerial() {
        return creditSerial;
    }

    public void setCreditSerial(String creditSerial) {
        this.creditSerial = creditSerial;
    }

    public Long getMemoId() {
        return memoId;
    }

    public void setMemoId(Long memoId) {
        this.memoId = memoId;
    }

    public String getMemoName() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName = memoName;
    }

    public String getScmCode() {
        return scmCode;
    }

    public void setScmCode(String scmCode) {
        this.scmCode = scmCode;
    }

    public String getScmName() {
        return scmName;
    }

    public void setScmName(String scmName) {
        this.scmName = scmName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<StatusFile> getStatusFileList() {
        return statusFileList;
    }

    public void setStatusFileList(List<StatusFile> statusFileList) {
        this.statusFileList = statusFileList;
    }

    public List<CAMemo> getMemosList() {
        return memosList;
    }

    public void setMemosList(List<CAMemo> memosList) {
        this.memosList = memosList;
    }

    public SearchCreditAdviceService getSearchCreditAdviceService() {
        return searchCreditAdviceService;
    }

    public void setSearchCreditAdviceService(SearchCreditAdviceService searchCreditAdviceService) {
        this.searchCreditAdviceService = searchCreditAdviceService;
    }

    public List<CAFile> getFiles() {
        return files;
    }

    public void setFiles(List<CAFile> files) {
        this.files = files;
    }

    public List<CreditDetails> getCreditDetailsList() {
        return CreditDetailsList;
    }

    public void setCreditDetailsList(List<CreditDetails> CreditDetailsList) {
        this.CreditDetailsList = CreditDetailsList;
    }

    public Map<Long, String> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, String> checked) {
        this.checked = checked;
    }
}
