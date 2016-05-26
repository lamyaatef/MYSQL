/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm;

import com.mobinil.sds.core.system.scm.model.CaseModel;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.core.system.scm.dao.STKDAO;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mabdelaal
 */
public class TestActivation {
public static void main(String [] args){
String path = "d:\\df\\ss.ss";
    System.out.println(path.substring(path.lastIndexOf("\\")+1, path.length()));
//Vector errorVector = new Vector();
//	Vector statusVector = new Vector();
//String fileUniqueName = System.currentTimeMillis() + ".xls";
//    String path = "C:\\Documents and Settings\\mabdelaal\\My Documents\\SCM\\";
//    String DCM_CODE = "IMS00";
//	DataImportEngine importEngine = new DataImportEngine();
//
//	DataImportReport importReport = importEngine
//			.ImportFileWithRowNumber(path+"scm upload.xls" ,
//					SCMInterfaceKey.INSERT_OPERATION, "56");
//
//	Vector report = importReport.getReport();
//	int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();
//
//        Connection con;
//        try {
//            con = Utility.getConnection();
//            STKDAO.updateAllTempTable(DCM_CODE, con);
//            STKDAO.beforeImportTOActive(DCM_CODE, con);
//
//
//        Utility.closeConnection(con);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }


}
}
