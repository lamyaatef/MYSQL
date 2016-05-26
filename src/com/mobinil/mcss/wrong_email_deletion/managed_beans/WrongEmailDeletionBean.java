/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.wrong_email_deletion.managed_beans;

import com.mobinil.mcss.wrong_email_deletion.model.WrongEmailDeletionModel;
import com.mobinil.mcss.wrong_email_deletion.service.WrongEmailDeletionService;
import com.mobinil.mcss.wrong_email_deletion.service.WrongEmailDeletionServiceImp;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
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
 * @author SAND
 */
@ManagedBean(name = "wrongEmail")
@ViewScoped
public class WrongEmailDeletionBean {
    
    private String posCode;
    private Date date;
    private Date dateTo;
    private List<WrongEmailDeletionModel> emailList = new ArrayList<WrongEmailDeletionModel>();
    WrongEmailDeletionService wrongEmailService = new WrongEmailDeletionServiceImp();
    private Map<Integer, String> checked = new HashMap<Integer, String>();
    private boolean review = false;
    private String reason = null;
    
    public void findWrongEmails() {
        Format formatter;
        String date2 = null;
        String date3 = null;
        formatter = new SimpleDateFormat("dd-MMM-yy");
        if (date != null) {
            date2 = formatter.format(date);
        }
        if (dateTo != null) {
            date3 = formatter.format(dateTo);
        }
        
        System.out.println("Pos code is :" + posCode);
        emailList = wrongEmailService.getEmails(posCode, date2, date3);
        
        
        
        
    }
    
    public void reviewTrue() {
        review = true;
        
    }
    
    public void archiveAndDelete() {
        
        
        List<WrongEmailDeletionModel> adEmailList = new ArrayList<WrongEmailDeletionModel>();
        
        for (WrongEmailDeletionModel wrongEmailDeletionModel : emailList) {
            
            if (checked.get(wrongEmailDeletionModel.getId()) != null) {
                if (checked.get(wrongEmailDeletionModel.getId()).equals("true")) {
                    adEmailList.add(wrongEmailDeletionModel);
                    
                }
            }
        }
        
        wrongEmailService.archiveAndDelete(adEmailList, reason);
        
        setReview(false);
        emailList.clear();
        setReason("");
    }
    
    public Map<Integer, String> getChecked() {
        return checked;
    }
    
    public void setChecked(Map<Integer, String> checked) {
        this.checked = checked;
    }
    
    public List<WrongEmailDeletionModel> getEmailList() {
        return emailList;
    }
    
    public void setEmailList(List<WrongEmailDeletionModel> emailList) {
        this.emailList = emailList;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getPosCode() {
        return posCode;
    }
    
    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
    
    public boolean isReview() {
        return review;
    }
    
    public void setReview(boolean review) {
        this.review = review;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Date getDateTo() {
        return dateTo;
    }
    
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
