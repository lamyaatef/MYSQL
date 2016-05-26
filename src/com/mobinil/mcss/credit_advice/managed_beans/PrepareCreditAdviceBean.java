/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.managed_beans;

import com.mobinil.mcss.credit_advice.model.CAMemo;
import com.mobinil.mcss.credit_advice.model.CamPayment;
import com.mobinil.mcss.credit_advice.service.PrepareCreditAdviceService;
import com.mobinil.mcss.credit_advice.service.PrepareCreditAdviceServiceImpl;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
//import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Brain
 */
@ManagedBean(name = "prepareCa")
@ViewScoped
public class PrepareCreditAdviceBean implements Serializable{

    private Long memoId=null;
    private String memoName=null;
    private Float totalOfAllPayments;
    private Date memoCreationDateFrom ;
    private Date memoCreationDateTo ;
    private List<CAMemo> memosList;
    private List<CAMemo> memosList2;
    private Map<Long, String> checked = new HashMap<Long, String>();
    PrepareCreditAdviceService prepareCreditAdviceService = new PrepareCreditAdviceServiceImpl();

    public void findAvailableMemos() {
        System.out.println("Action On Search Button");
        Format formatter;
        String dateFrom = null;
        String dateTo = null;
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        if (memoCreationDateFrom != null) {
            dateFrom = formatter.format(memoCreationDateFrom);
        }
        if (memoCreationDateTo != null) {
            dateTo = formatter.format(memoCreationDateTo);
        }
        memosList = prepareCreditAdviceService.getAvailableMemos(memoId, memoName, dateFrom, dateTo);

    }

    public void prepareMemos() {
        FacesContext context = FacesContext.getCurrentInstance();  
          
        context.addMessage(null, new FacesMessage("Successful", "Memos are prepared "));  
        
        System.out.println("Action On Prepare Button");

        List<CAMemo> cAMemos = new ArrayList<CAMemo>();
       
        String userId = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(InterfaceKey.HASHMAP_KEY_USER_ID);

        System.out.println("Useeeeeeeeeer ID:" + userId);

        for (CAMemo cAMemo : memosList) {
            if (checked.get(cAMemo.getMemoId()).equals("true")) {
                cAMemos.add(cAMemo);
                System.out.println("Checked Memos:" + cAMemo.getMemoId());
            }
            

        }
        prepareCreditAdviceService.prepareCA(cAMemos, userId);
        memosList.clear();

    }

    public void findpMemoPayments() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("memoId");
        Long memoId2 = Long.valueOf(id);
        
        this.setMemoId(memoId2);

        System.out.println("MEMO ID: " + id);
        memosList2 = prepareCreditAdviceService.reviewCrPayment(memoId2);
        totalOfAllPayments = 0F;
        for (CAMemo cAMemo : memosList2) {

            totalOfAllPayments = totalOfAllPayments + cAMemo.getTotalPayment();

        }

    }

    public List<CAMemo> getMemosList2() {
        return memosList2;
    }

    public void setMemosList2(List<CAMemo> memosList2) {
        this.memosList2 = memosList2;
    }

    public List<CAMemo> getMemosList() {
        return memosList;
    }

    public void setMemosList(List<CAMemo> memosList) {
        this.memosList = memosList;
    }

    public Date getMemoCreationDateFrom() {
        return memoCreationDateFrom;
    }

    public void setMemoCreationDateFrom(Date memoCreationDateFrom) {
        this.memoCreationDateFrom = memoCreationDateFrom;
    }

    public Date getMemoCreationDateTo() {
        return memoCreationDateTo;
    }

    public void setMemoCreationDateTo(Date memoCreationDateTo) {
        this.memoCreationDateTo = memoCreationDateTo;
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

    public Float getTotalOfAllPayments() {
        return totalOfAllPayments;
    }

    public void setTotalOfAllPayments(Float totalOfAllPayments) {
        this.totalOfAllPayments = totalOfAllPayments;
    }

    public Map<Long, String> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, String> checked) {
        this.checked = checked;
    }
}
