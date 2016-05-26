/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.core.system.authenticationResult.engine;

import com.mobinil.sds.core.system.authenticationResult.dao.SimInfoDao;
import com.mobinil.sds.core.system.authenticationResult.model.SimInfoModel;
import com.mobinil.sds.core.system.sa.importdata.DataImportEngine;
import com.mobinil.sds.core.system.sa.importdata.model.DataImportReport;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;

/**
 *
 * @author mabdelaal
 */
public class SimInfoInsertJob extends Thread {
    
    private String Status;
    private String label;
    private String filepath;
    private String user_id;    
    
    public SimInfoInsertJob(String Status, String label, String user_id, String filepath) {
        this.Status = Status;
        this.label = label;
        this.user_id = user_id;
        this.filepath = filepath;
    }

    public synchronized void run() {
        Connection con = null;
        try {            
            con = Utility.getConnection();            
            SimInfoDao simDao = new SimInfoDao(con, new SimInfoModel(Status,user_id, label));            
        //    simDao.insertFileInfo();            
            DataImportEngine dataEngine = new DataImportEngine();
            DataImportReport report = dataEngine.ImportFile(filepath, "INSERT", "58");
            simDao.runAllUpdates();            
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    Utility.closeConnection(con);
                } catch (Exception e) {
                }
            }
        }
        
    }
    
//   public static void main (String args[]){
//   SimInfoInsertJob simInsertThread= new  SimInfoInsertJob("Processing","6","262","C:\\Documents and Settings\\mabdelaal\\Desktop\\testSim.xls");
//      simInsertThread.start();      
//   }
    
}
