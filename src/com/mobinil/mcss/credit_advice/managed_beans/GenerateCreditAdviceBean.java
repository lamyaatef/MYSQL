/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.managed_beans;

import com.mobinil.mcss.credit_advice.model.CAFile;
import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.service.GenerateCreditAdviceService;
import com.mobinil.mcss.credit_advice.service.GenerateCreditAdviceServiceImpl;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author JOE
 */
@ManagedBean(name = "generateCa")
@ViewScoped
public class GenerateCreditAdviceBean {

    private Long memoId = null;
    private String memoName;
    private Date filePrepareDateFrom;
    private Date filePrepareDateTo;
    private Long fileId = null;
    private Long creditId;
    private String creditSerial;
    private Long filestatus;
    private List<CAFile> filesList;
    private List<CAMemo> memosList2;
    private Map<Long, String> checked = new HashMap<Long, String>();
    GenerateCreditAdviceService generateCreditAdviceService = new GenerateCreditAdviceServiceImpl();
    List<Long> fileIds = new ArrayList<Long>();

    public void findAvailableCredits() {
        System.out.println("Action On Search Button");
        Format formatter;
        String dateFrom = null;
        String dateTo = null;
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        if (filePrepareDateFrom != null) {
            dateFrom = formatter.format(filePrepareDateFrom);
        }
        if (filePrepareDateTo != null) {
            dateTo = formatter.format(filePrepareDateTo);
        }
        filesList = generateCreditAdviceService.getPreparedCredits(memoId, memoName, dateFrom, dateTo, fileId);
    }

    public void generateCredits() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Files are generated "));
        System.out.println("Action On Generate Button");
        HashMap dataHashMap = new HashMap(100);
        String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        generateCreditAdviceService.generateCAFile(strUserID, fileIds);
        filesList.clear();


    }

    public void viewFiles() {

        System.out.println("Action in view Files");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("fileId");
        Long fileId = Long.valueOf(id);
        System.out.println("FILE ID: " + id);
        memosList2 = generateCreditAdviceService.viewCredits(fileId);

    }

    public void deleteFiles() {
        System.out.println("Action in Delete Files");
        FacesContext context=FacesContext.getCurrentInstance();
        context.addMessage(null,new FacesMessage("Successfull", "File deleted"));
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("fileId");
        Long fileId = Long.parseLong(id);
        String userId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(InterfaceKey.HASHMAP_KEY_USER_ID);

        generateCreditAdviceService.deleteCredits(fileId, userId);

        filesList.clear();

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

    public Date getFilePrepareDateFrom() {
        return filePrepareDateFrom;
    }

    public void setFilePrepareDateFrom(Date filePrepareDateFrom) {
        this.filePrepareDateFrom = filePrepareDateFrom;
    }

    public Date getFilePrepareDateTo() {
        return filePrepareDateTo;
    }

    public void setFilePrepareDateTo(Date filePrepareDateTo) {
        this.filePrepareDateTo = filePrepareDateTo;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public List<CAFile> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<CAFile> filesList) {
        this.filesList = filesList;
    }

    public Long getCreditId() {
        return creditId;
    }

    public void setCreditId(Long creditId) {
        this.creditId = creditId;
    }

    public String getCreditSerial() {
        return creditSerial;
    }

    public void setCreditSerial(String creditSerial) {
        this.creditSerial = creditSerial;
    }

    public Long getFilestatus() {
        return filestatus;
    }

    public void setFilestatus(Long filestatus) {
        this.filestatus = filestatus;
    }

    public GenerateCreditAdviceService getGenerateCreditAdviceService() {
        return generateCreditAdviceService;
    }

    public void setGenerateCreditAdviceService(GenerateCreditAdviceService generateCreditAdviceService) {
        this.generateCreditAdviceService = generateCreditAdviceService;
    }

    public Map<Long, String> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, String> checked) {
        this.checked = checked;
    }

    public List<CAMemo> getMemosList2() {
        return memosList2;
    }

    public void setMemosList2(List<CAMemo> memosList2) {
        this.memosList2 = memosList2;
    }

    public void testSamy() {
        System.out.println("Test Samy");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("generateCacheckedfileId");
//        System.out.println("Id : " + id);
        Long newId = Long.parseLong(id);


        if (checked.get(newId).equals("true")) {
            fileIds.add(newId);
//            System.out.println("Trueeeeeeee");
        } else {
            fileIds.remove(newId);
//            System.out.println("Falseeeeeeeeeee");
        }
        for (Long long1 : fileIds) {
            System.out.println(long1);
        }

    }
}