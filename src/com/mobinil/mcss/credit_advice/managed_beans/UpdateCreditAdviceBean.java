/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.managed_beans;

import com.mobinil.mcss.credit_advice.service.UpdateCreditAdviceService;
import com.mobinil.mcss.credit_advice.service.UpdateCreditAdviceServiceImpl;
import com.mobinil.mcss.credit_advice.util.ExcelReader;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.ErrorInfo;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;
import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import jxl.*;
import jxl.read.biff.BiffException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Shady Akl
 */
@ManagedBean(name = "updateCa")
@ViewScoped
public class UpdateCreditAdviceBean {

    UpdateCreditAdviceService updateCreditAdviceService = new UpdateCreditAdviceServiceImpl();
    private StreamedContent template;
    private StreamedContent templateWithSerial;
    private UploadedFile file;
    Vector<ErrorInfo> report;
    int updatedRecords;

    public void generateTemplate(ActionEvent ae) throws AbortProcessingException {

        FacesContext context = FacesContext.getCurrentInstance();

        ExternalContext externalContext = context.getExternalContext();

        String userId = (String) externalContext.getSessionMap().get(InterfaceKey.HASHMAP_KEY_USER_ID);

        String templatePath = externalContext.getRealPath("ca/template/template.xls");

        String generatedPath = externalContext.getRealPath("ca/template/update_credit_advice_with_id.xls");

        System.err.println("Path From Bean:" + templatePath);

        updateCreditAdviceService.generateTemplate(templatePath, generatedPath, userId);
    }

    public void generateTemplateWithSerials(ActionEvent ae) throws AbortProcessingException {

        FacesContext context = FacesContext.getCurrentInstance();

        ExternalContext externalContext = context.getExternalContext();

        String userId = (String) externalContext.getSessionMap().get(InterfaceKey.HASHMAP_KEY_USER_ID);

        String templatePath = externalContext.getRealPath("ca/template/template_serial.xls");

        String generatedPath = externalContext.getRealPath("ca/template/update_credit_advice_with_serial.xls");

        System.err.println("Path From Bean:" + templatePath);

        updateCreditAdviceService.generateTemplate(templatePath, generatedPath, userId);
    }

    public StreamedContent getTemplate() throws FileNotFoundException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();

        String templatePath = externalContext.getRealPath("ca/template/update_credit_advice_with_id.xls");
        InputStream stream2 = new FileInputStream(templatePath);
        template = new DefaultStreamedContent(stream2, "application/xls", "update_credit_advice_with_id.xls");
        return template;
    }

    public void setTemplate(StreamedContent template) {
        this.template = template;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void uploadWithId() throws IOException {

        System.out.println("Into Upload");

        UUID uuid = UUID.randomUUID();


        if (file != null) {

            String filePath = "sds//credit_advice//upload//" + uuid + ".xls";
            File outputFile = new File(filePath);

            System.out.println("File Path:" + outputFile.getAbsolutePath());

            byte[] bFile = file.getContents();

            try {

                //convert array of bytes into file
                FileOutputStream fileOuputStream =
                        new FileOutputStream(outputFile);
                fileOuputStream.write(bFile);
                fileOuputStream.close();
                System.out.println("Done");


                updateCreditAdviceService.clearTempTableCRDetail();

                DataImportEngine importEngine = new DataImportEngine();
                DataImportReport importReport = importEngine.ImportFile(filePath, AdministrationInterfaceKey.DATA_IMPORT_INSERT, "67");
                report = importReport.getReport();
                updatedRecords = importReport.getNumOfRecordsUpdated();
                FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                System.out.println("Before Update");
                updateCreditAdviceService.updateRDetailWithId();
                System.out.println("After Update");
                System.out.println("Before Clear");

                updateCreditAdviceService.clearTempTableCRDetail();
                System.out.println("After Clear");

            } catch (Exception e) {
                e.printStackTrace();
            }

            // ExcelReader.contentReading(file.getInputstream());

        }


    }

    public void uploadWithSerial() throws IOException {

        System.out.println("Into Upload");

        UUID uuid = UUID.randomUUID();


        if (file != null) {

            String filePath = "sds//credit_advice//upload//" + uuid + ".xls";
            File outputFile = new File(filePath);

            System.out.println("File Path:" + outputFile.getAbsolutePath());

            byte[] bFile = file.getContents();

            try {

                //convert array of bytes into file
                FileOutputStream fileOuputStream =
                        new FileOutputStream(outputFile);
                fileOuputStream.write(bFile);
                fileOuputStream.close();
                System.out.println("Done");


                updateCreditAdviceService.clearTempTableCRDetailWithSerial();

                DataImportEngine importEngine = new DataImportEngine();
                DataImportReport importReport = importEngine.ImportFile(filePath, AdministrationInterfaceKey.DATA_IMPORT_INSERT, "68");
                report = importReport.getReport();
                updatedRecords = importReport.getNumOfRecordsUpdated();
                FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                updateCreditAdviceService.updateRDetailWithSerial();
                updateCreditAdviceService.clearTempTableCRDetailWithSerial();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // ExcelReader.contentReading(file.getInputstream());

        }


    }

    public StreamedContent getTemplateWithSerial() throws FileNotFoundException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();

        String templatePath = externalContext.getRealPath("ca/template/update_credit_advice_with_serial.xls");
        InputStream stream2 = new FileInputStream(templatePath);
        templateWithSerial = new DefaultStreamedContent(stream2, "application/xls", "update_credit_advice_with_serial.xls");
        return templateWithSerial;
    }

    public void setTemplateWithSerial(StreamedContent templateWithSerial) {
        this.templateWithSerial = templateWithSerial;
    }

    public Vector<ErrorInfo> getReport() {
        return report;
    }

    public void setReport(Vector<ErrorInfo> report) {
        this.report = report;
    }

    public int getUpdatedRecords() {
        return updatedRecords;
    }

    public void setUpdatedRecords(int updatedRecords) {
        this.updatedRecords = updatedRecords;
    }
}
