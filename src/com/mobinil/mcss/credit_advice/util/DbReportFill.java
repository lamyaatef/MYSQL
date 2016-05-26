package com.mobinil.mcss.credit_advice.util;

import com.mobinil.sds.core.utilities.Utility;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class DbReportFill {

    public String generateCreditAdvices(List creditAdviceList, String filePath) throws SQLException {
        Connection connection = Utility.getConnection();
        UUID uuid = UUID.randomUUID();

        try {

            System.out.println("Filling report...");


            Map params = new HashMap();


            JasperReport jasperReport;
            JasperPrint jasperPrint;




            jasperReport = JasperCompileManager.compileReport(filePath);

            params.put("credit_advice_list", creditAdviceList);

            jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "sds//credit_advice//crd_" + uuid
                    + ".pdf");
            System.out.println("Done!");
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            Utility.closeConnection(connection);
        }

        return uuid.toString();
    }

    public String generateCreditAdviceFile(Long fileId, String filePath) throws SQLException, IOException {
        Connection connection = Utility.getConnection();
        UUID uuid = UUID.randomUUID();

        try {

            System.out.println("Filling report...");


            Map params = new HashMap();


            JasperReport jasperReport;
            JasperPrint jasperPrint;


            System.out.print("File Path From Bean" + filePath);


            jasperReport = JasperCompileManager.compileReport(filePath);

            params.put("credit_advice_file_id", fileId);


            jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            JasperExportManager.exportReportToPdfFile(jasperPrint, "sds//credit_advice//crd_file_" + uuid + ".pdf");
            System.out.println("Done!");
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            Utility.closeConnection(connection);
        }

        return uuid.toString();
    }
}
