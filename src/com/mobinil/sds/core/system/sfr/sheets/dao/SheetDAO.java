package com.mobinil.sds.core.system.sfr.sheets.dao;

import java.util.*;
import java.sql.*;

import oracle.jdbc.driver.OracleTypes;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sfr.*;
import com.mobinil.sds.web.interfaces.cr.*;
import com.mobinil.sds.core.system.sfr.sheets.model.*;

public class SheetDAO
{
    private SheetDAO()
    {
    }

    private static final String CONTRACT_WARNING_NOT_EXIST_IN_INVENTORY = "CONTRACT DOES NOT EXIST IN INVENTORY";
    private static final String CONTRACT_WARNING_NOT_SOLD = "CONTRACT IS WITH A NON-ISSUED STATUS IN INVENTORY";
    private static final String CONTRACT_WARNING_LCS_CONNECTION_FAILED = "LCS CONNECTION FAILED TO CONNECT";
    private static final String CONTRACT_WARNING_NOT_EXIST_IN_BSCS = "CONTRACT DOES NOT EXIST IN POSTPAID DB";
    private static final String CONTRACT_WARNING_NOT_EXIST_IN_PREPAID = "CONTRACT NOT INITIALIZED IN THE PREPAID DB";

    private static final String CONTRACT_WARNING_NOT_ACTIVATED_POSTPAID = "CONTRACT WAS NOT ACTIVATED";
    private static final String CONTRACT_WARNING_NOT_INITIALIZED_PREPAID = "CONTRACT WAS NOT INITIALIZED";
    private static final String CONTRACT_WARNING_EMPTY_FIELDS = "CONTRACT HAS EMPTY FIELDS";
    private static final String CONTRACT_WARNING_SOLD_TO_DIFFERENT_DCM = "CONTRACT WAS SOLD TO DIFFERENT DCM";
    private static final String CONTRACT_EXIST_WITH_A_DIFFERENT_TYPE_IN_LCS = "CONTRACT EXIST WITH A DIFFERENT TYPE IN LCS";

    private static final String CONTRACT_POST_PAID_HAS_DIFFERENT_DIAL_NO = "CONTRACT EXIST WITH DIFFERENT DIAL IN POST PAID DB";
    private static final String CONTRACT_SIM_SWAP_HAS_DIFFERENT_DIAL_NO = "CONTRACT EXIST WITH DIFFERENT DIAL IN POST PAID DB";
    private static final String CONTRACT_PRE_PAID_HAS_DIFFERENT_DIAL_NO = "CONTRACT EXIST WITH DIFFERENT DIAL IN PRE PAID DB";

    private static Vector<BatchStatusTypeModel> batchStatusTypeVec = null;

    public static Vector<SheetImportModel> getTmpSfrExcelFile(String strUserId,
            Connection con)
    {
        Vector<SheetImportModel> sfrVec = new Vector<SheetImportModel>(30, 5);
        Statement stat = null;
        try
        {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from TMP_SFR_EXCEL_FILE where USER_ID = '"
                    + strUserId + "' order by ROW_NUM ");
            while (res.next())
            {
                sfrVec.add(new SheetImportModel(res));
            }
            res.close();
            res = null;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            DBUtil.close(stat);

        }
        return sfrVec;
    }

    public static void deleteTmpSfrExcelFile(String strUserId, Connection con)
    {
        String sql = "delete from TMP_SFR_EXCEL_FILE where USER_ID = ?";
        DBUtil.executePreparedStatment(sql, con, strUserId);
    }

    public static void insertTmpSfrExcelFile(Connection con, int rowNumber,
            String posCode, String secondPosCode, String sheetSerial,
            String contractsCount, String userId)
    {
        String sqlQuery = "insert into TMP_SFR_EXCEL_FILE(ROW_NUM,POS_CODE,SECOND_POS_CODE,SHEET_SERIAL,CONTRACTS_COUNT,AGENT_ID) "
                + " values(?,?,?,?,?,?) ";
        DBUtil.executePreparedStatment(sqlQuery, con, rowNumber, posCode, secondPosCode, sheetSerial, contractsCount, userId);
    }

    public static int checkAgentId(String agenId, Connection con)
    {
        String strSql = "select 1 from gen_user where user_id = '" + agenId
                + "'";
        int check = 0;
        boolean checkFlag = DBUtil.executeSQLExistCheck(strSql, con);
        check = (checkFlag ? 0 : 1); // if true return zero otherwise return 1
        return check;
    }

    public static Long insertBatch(String agentId, String batchStatusTypeId,
            Connection con)
    {
        Long lBatchID = null;
        try
        {
            java.util.Date currentDate = new java.util.Date();
            // bad code
            int currentYear = currentDate.getYear() + 1900;
            int currentMonth = currentDate.getMonth() + 1;
            int currentDay = currentDate.getDate();

            String strCurrentDate = currentYear + "/" + currentMonth + "/"
                    + currentDay;
            lBatchID = Utility.getSequenceNextVal(con, "SEQ_SFR_BATCH_ID");
                  
            String insertSql = "insert into SFR_BATCH "
                    + " (BATCH_ID,AGENT_ID,BATCH_STATUS_TYPE_ID,BATCH_DATE) "
                    + " values('"+lBatchID+"'," + agentId + ","
                    + batchStatusTypeId + ",TO_DATE('" + strCurrentDate
                    + "','yyyy/mm/dd'))";
            DBUtil.executeSQL(insertSql, con);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {

        }
        return lBatchID;
    }

    public static void deleteSIMBySheet(Connection con, String sheetId,
            String strSimId)
    {
        String strSql = "delete from SFR_SIM where SHEET_ID = " + sheetId
                + " and SIM_ID = " + strSimId;
        DBUtil.executeSQL(strSql, con);

        strSql = "delete from SFR_SIM_STATUS where SIM_ID = " + strSimId;
        DBUtil.executeSQL(strSql, con);
    }

    private static String modifyBundelSim(String sim)
    {
        if (sim.length() > 22)
        {
            // do nothing
        } else
        {
            sim = sim.substring(1, 19);
        }
        return sim;
    }

    public static boolean simExists(Connection con, String contractSIMNumber,
            String simStatusTypeId)
    {
        contractSIMNumber = modifyBundelSim(contractSIMNumber);
        String strSql = "select 1 from SFR_SIM where SIM_pure = '"
                + contractSIMNumber + "' and SIM_STATUS_TYPE_ID = "
                + simStatusTypeId + " ";
        boolean simExists = DBUtil.executeSQLExistCheck(strSql, con);
        return simExists;
    }

    public static Long insertSIM(Connection con, String contractSIMNumber,
            String sheetId, String simStatusTypeId)
    {
        Long lsimID = null;
        String simPure = modifyBundelSim(contractSIMNumber);

        lsimID = Utility.getSequenceNextVal(con, "SEQ_SFR_SIM_ID");
        String insertSql = "insert into SFR_SIM  (SIM_SERIAL,SHEET_ID,SIM_STATUS_TYPE_ID,SIM_ID,SIM_WARNING_CHECKED,sim_pure)  values('"
                + contractSIMNumber
                + "',"
                + sheetId
                + ","
                + simStatusTypeId
                + "," + lsimID + ",0,'" + simPure + "')";

        DBUtil.executeSQL(insertSql, con);
        return lsimID;
    }

    public static void updateSIM(Connection con, String contractSIMNumber,
            String simId, String simStatusTypeId)
    {

        String insertSql = "update SFR_SIM set SIM_SERIAL = '"
                + contractSIMNumber + "' where SIM_ID =" + simId;
        DBUtil.executeSQL(insertSql, con);
    }

    public static void updateSIMStatus(Connection con, String simId,
            String simStatusTypeId)
    {
        String insertSql = "update SFR_SIM  set SIM_STATUS_TYPE_ID ="
                + simStatusTypeId + " where SIM_ID = " + simId;
        DBUtil.executeSQL(insertSql, con);
    }

    public static void updateSheet(Connection con, String sheetSerial,
            String sheetSIMCount, String sheetId)
    {
        String insertSql = "update SFR_SHEET  set SHEET_SIM_COUNT = "
                + sheetSIMCount + " where SHEET_ID = " + sheetId;
        DBUtil.executeSQL(insertSql, con);
    }

    public static void deleteSheet(Connection con, String sheetSerial,
            String sheetId)
    {
        String insertSql = "delete from SFR_SHEET where SHEET_ID = " + sheetId;
        DBUtil.executeSQL(insertSql, con);
    }

    public static void insertSIMStatus(Connection con,
            String contractSIMNumber, String SIMStatusTypeId, String userId,
            Long lsimId)
    {
        String insertSql = "insert into SFR_SIM_STATUS (SIM_STATUS_ID,SIM_STATUS_TYPE_ID,SIM_SERIAL,SIM_STATUS_TIMESTAMP,USER_ID,SIM_ID)  values("
                + "SEQ_SFR_SIM_STATUS_ID.nextval,"
                + SIMStatusTypeId
                + ",'"
                + contractSIMNumber
                + "',SYSDATE,"
                + userId
                + ","
                + lsimId
                + ")";

        DBUtil.executeSQL(insertSql, con);
    }

    public static void insertBatchStatus(Long lBatchId,
            String batchStatusTypeId, String userId, Connection con)
    {
        String insertSql = "insert into SFR_BATCH_STATUS "
                + " (BATCH_STATUS_ID,BATCH_STATUS_TYPE_ID,BATCH_ID,BATCH_STATUS_TIMESTAMP,USER_ID) "
                + " values(SEQ_SFR_BATCH_STATUS_ID.nextval,"
                + batchStatusTypeId + "," + lBatchId + ",SYSDATE," + userId
                + ")";
        DBUtil.executeSQL(insertSql, con);
    }
//Ahmed Adel
    public static Long insertSheet(int sheetSerial, Long lBatchId,
            String posId, String secondPosId, int sheetSIMCount, Connection con)
    {

        String insertSql = "insert into SFR_SHEET "
                + " (SHEET_ID,SHEET_SERIAL,BATCH_ID,POS_ID,SECOND_POS_ID,SHEET_SIM_COUNT,SHEET_STATUS_TYPE_ID) "
                + " values(SEQ_SFR_SHEET_ID.nextval," + sheetSerial + ","
                + lBatchId + "," + posId + "," + secondPosId + ","
                + sheetSIMCount + ",1)";

        DBUtil.executeSQL(insertSql, con);
        return lBatchId;

    }
    

    public static boolean isDCMCodeExist(String dcmCode, Connection con)
    {
        String strSql = "select 1 from gen_dcm where dcm_code = '" + dcmCode
                + "'";
        return DBUtil.executeSQLExistCheck(strSql, con);
    }
    
    public static boolean isSheetInAcceptedStatusExist(String sheetSerial,Connection con)
    {
        String sheetExistSql = "select 1 from  SFR_SHEET where SHEET_SERIAL = '"
            + sheetSerial + "' and SHEET_STATUS_TYPE_ID <> 5 ";
    boolean sheetExist = DBUtil.executeSQLExistCheck(sheetExistSql, con);
        return sheetExist;
}
    public static int checkPosCode(String posCode, String secondPosCode,
            String sheetSerial, Connection con)
    {
        int check = 0;       
        if (isSheetInAcceptedStatusExist(sheetSerial,con))
        {
            check = 3;
        } else if (!isDCMCodeExist(posCode, con))
        {
            check = 1;
        } else if (!isDCMCodeExist(secondPosCode, con))
        {
            check = 2;
        }

        return check;
    }

    public static Long insertNewSheet(Connection con, int sheetSerial,
            Long lBatchId, String posCode, String secondPosCode,
            int sheetSIMCount)
    {
        Long lSheetID = null;

        lSheetID = Utility.getSequenceNextVal(con, "SEQ_SFR_SHEET_ID");
        String insertSql = "insert into SFR_SHEET "
                + " (SHEET_ID,SHEET_SERIAL,BATCH_ID,POS_ID,SECOND_POS_ID,SHEET_SIM_COUNT,SHEET_STATUS_TYPE_ID) "
                + " values(" + lSheetID + "," + sheetSerial + "," + lBatchId
                + ",(select distinct(dcm_id)from gen_dcm where dcm_code = '"
                + posCode
                + "'),(select distinct(dcm_id)from gen_dcm where dcm_code = '"
                + secondPosCode + "')," + sheetSIMCount + ",1)";
        DBUtil.executeSQL(insertSql, con);

        return lSheetID;
    }
    
   
    
  static final String insertSql = "insert into SFR_SHEET_STATUS "
       + " (SHEET_STATUS_ID,SHEET_STATUS_TYPE_ID,SHEET_ID,SHEET_STATUS_TIMESTAMP,USER_ID) "
       + " values(SEQ_SFR_SHEET_STATUS_ID.nextval,?,?,SYSDATE, ?)";

   public static void insertSheetStatusV2(Connection con, String sheetId,
           String sheetStatusTypeId, String userId)
   {
       
       DBUtil.executePreparedStatment(insertSql, con,sheetStatusTypeId,sheetId,userId);
   } 
   
   public static void insertSheetStatusV3(Connection con, String sheetId,
           String sheetStatusTypeId, String userId)
   {
       String insertSql = "insert into SFR_SHEET_STATUS "
           + " (SHEET_STATUS_ID,SHEET_STATUS_TYPE_ID,SHEET_ID,SHEET_STATUS_TIMESTAMP,USER_ID) "
           + " values(SEQ_SFR_SHEET_STATUS_ID.nextval,?,?,SYSDATE, ?)";
       DBUtil.executePreparedStatment(insertSql, con,sheetStatusTypeId,sheetId,userId);
   } 
   
    public static void insertSheetStatus(Connection con, String sheetId,
            String sheetStatusTypeId, String userId)
    {
        String insertSql = "insert into SFR_SHEET_STATUS "
                + " (SHEET_STATUS_ID,SHEET_STATUS_TYPE_ID,SHEET_ID,SHEET_STATUS_TIMESTAMP,USER_ID) "
                + " values(SEQ_SFR_SHEET_STATUS_ID.nextval,"
                + sheetStatusTypeId + "," + sheetId + ",SYSDATE," + userId
                + ")";

        DBUtil.executeSQL(insertSql, con);
    }

    public static void insertSheetWarning(Connection con, int sheetId,
            String sheetWarningTypeId, String userId)
    {

        String insertSql = "insert into SFR_SHEET_WARNING  (SHEET_WARNING_ID,SHEET_WARNING_TYPE_ID,SHEET_ID,SHEET_WARNING_TIMESTAMP,USER_ID)  values(SEQ_SFR_SHEET_WARNING_ID.nextval,"
                + sheetWarningTypeId
                + ","
                + sheetId
                + ",SYSDATE,"
                + userId
                + ")";

        DBUtil.executeSQL(insertSql, con);
    }

    public static Vector<BatchStatusTypeModel> getAllBatcheStatusType(
            Connection con)
    {

        if (batchStatusTypeVec == null) // the vector is made an class attribute
                                        // for cashing
        {
            Vector<BatchStatusTypeModel> sfrVec = new Vector<BatchStatusTypeModel>();
            Statement stat = null;
            try
            {
                stat = con.createStatement();
                String strSql = "select * from SFR_BATCH_STATUS_TYPE order by BATCH_STATUS_TYPE_ID ";
                ResultSet res = stat.executeQuery(strSql);
                while (res.next())
                {
                    sfrVec.add(new BatchStatusTypeModel(res));
                }
                res.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                DBUtil.close(stat);
            }

            batchStatusTypeVec = sfrVec;
        }
        return batchStatusTypeVec;

    }

    private static Vector<SheetStatusTypeModel> sheetStatusTypeVector = null;

    public static Vector<SheetStatusTypeModel> getAllSheetStatusType(
            Connection con)
    {
        if (sheetStatusTypeVector == null)
        {
            Vector<SheetStatusTypeModel> sfrVec = new Vector<SheetStatusTypeModel>();

            try
            {
                Statement stat = con.createStatement();
                String strSql = "select * from SFR_SHEET_STATUS_TYPE order by SHEET_STATUS_TYPE_ID ";
                ResultSet res = stat.executeQuery(strSql);
                while (res.next())
                {
                    sfrVec.add(new SheetStatusTypeModel(res));
                }
                stat.close();
                sheetStatusTypeVector = sfrVec;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return sheetStatusTypeVector;
    }

    public static Vector getAllSheetWarningType(Connection con)
    {
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_SHEET_WARNING_TYPE order by SHEET_WARNING_TYPE_ID ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                sfrVec.add(new SheetWarningTypeModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static SheetWarningTypeModel getSheetWarningTypeById(Connection con,
            String sheetWarningTypeId)
    {
        SheetWarningTypeModel sheetWarningTypeModel = null;
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_SHEET_WARNING_TYPE where SHEET_WARNING_TYPE_ID = "
                    + sheetWarningTypeId + " ";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next())
            {
                sheetWarningTypeModel = new SheetWarningTypeModel(res);
            }
            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sheetWarningTypeModel;
    }

    public static void updateSheetWarningType(Connection con,
            String sheetWarningTypeId, String sheetWarningTypeName,
            String warningSuggestedStatusId)
    {
        String strSql = "update SFR_SHEET_WARNING_TYPE set SHEET_WARNING_TYPE_NAME = '"
                + sheetWarningTypeName
                + "' , SUGGESTED_SHEET_STATUS_TYPE_ID = "
                + warningSuggestedStatusId
                + " "
                + "where SHEET_WARNING_TYPE_ID = " + sheetWarningTypeId + " ";
        DBUtil.executeSQL(strSql, con);

    }

    public static void insertSheetWarningType(Connection con,
            String sheetWarningTypeName, String warningSuggestedStatusId)
    {
        String strSql = "insert into SFR_SHEET_WARNING_TYPE (SHEET_WARNING_TYPE_ID,SHEET_WARNING_TYPE_NAME,SUGGESTED_SHEET_STATUS_TYPE_ID) "
                + " values(SEQ_SFR_SHEET_WARNING_TYPE_ID.nextval,'"
                + sheetWarningTypeName
                + "',"
                + warningSuggestedStatusId
                + ")  ";
        DBUtil.executeSQL(strSql, con);

    }

    public static void deleteSheetWarningType(Connection con,
            String sheetWarningTypeId)
    {
        String strSql = "delete from SFR_SHEET_WARNING_TYPE where SHEET_WARNING_TYPE_ID ="
                + sheetWarningTypeId;
        DBUtil.executeSQL(strSql, con);
    }

    private static Vector<SIMStatusTypeModel> simStatusType = null;

    public static Vector<SIMStatusTypeModel> getAllSIMStatusType(Connection con)
    {
        if (simStatusType == null)
        {
            Vector<SIMStatusTypeModel> sfrVec = new Vector<SIMStatusTypeModel>();
            try
            {
                Statement stat = con.createStatement();
                String strSql = "select * from SFR_SIM_STATUS_TYPE order by SIM_STATUS_TYPE_ID ";
                ResultSet res = stat.executeQuery(strSql);
                while (res.next())
                {
                    sfrVec.add(new SIMStatusTypeModel(res));
                }
                stat.close();
                simStatusType = sfrVec;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return simStatusType;
    }

    public static Vector getBatches(Connection con)
    {
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_BATCH order by BATCH_STATUS_TYPE_ID ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                sfrVec.add(new BatchModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getSheetWarnings(Connection con, String sheetId)
    {
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_SHEET_WARNING where SHEET_ID = "
                    + sheetId + " order by SHEET_WARNING_TIMESTAMP ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                sfrVec.add(new SheetWarningModel(res));
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getBatches(Vector vecBatchIds, Connection con)
    {
        Vector sfrVec = new Vector();
        try
        {

            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_BATCH ";
            strSql += " where BATCH_ID in ( ";
            for (int i = 0; i < vecBatchIds.size(); i++)
            {
                if (i != 0)
                    strSql += " , ";
                strSql += (Long) vecBatchIds.get(i);
            }
            strSql += " ) order by BATCH_ID ";
            // Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                sfrVec.add(new BatchModel(res));
            }
            stat.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getPendingBatches(Connection con, String agentId)
    {
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_BATCH where BATCH_STATUS_TYPE_ID = "
                    + SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING
                    + " "
                    + " and AGENT_ID = " + agentId + " order by BATCH_ID ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                BatchModel batchModel = new BatchModel(res);
                batchModel = SheetDAO.getBatchSuggestedStatus(con, batchModel);
                sfrVec.add(batchModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static int countOfPendingBatches(Connection con, String agentId)
    {
        int count = 0;
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select count(*) as count from VW_SFR_BATCH where BATCH_STATUS_TYPE_ID = "
                    + SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING
                    + " "
                    + " and AGENT_ID = " + agentId + " order by BATCH_ID ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                count = res.getInt("count");
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public static Vector getPendingBatchesByRowNum(Connection con, int rowNum,
            String agentId)
    {
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from  (select rownum as row_num,BATCH_ID,AGENT_ID,PERSON_FULL_NAME,BATCH_STATUS_TYPE_ID,"
                    + "BATCH_STATUS_TYPE_NAME,BATCH_DATE from VW_SFR_BATCH "
                    + "where BATCH_STATUS_TYPE_ID = "
                    + SFRInterfaceKey.CONST_BATCH_STATUS_TYPE_PENDING
                    + " "
                    + " and AGENT_ID = "
                    + agentId
                    + " ) where row_num > '"
                    + rowNum
                    + "'*30 and row_num <= ('"
                    + rowNum
                    + "'+1)*30 order by BATCH_ID ";
            // Utility.logger.debug("The pending query isssssssssssssssss " +
            // strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                BatchModel batchModel = new BatchModel(res);
                batchModel = SheetDAO.getBatchSuggestedStatus(con, batchModel);
                sfrVec.add(batchModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector<SIMModel> getSIMsBySheet(Connection con,
            String sheetId, String sheetSIMGroup)
    {
        Vector<SIMModel> sfrVec = new Vector<SIMModel>(30, 5);
        try
        {
            Statement stat = con.createStatement();
            String strSql = null;
            if (sheetSIMGroup.compareTo(SFRInterfaceKey.CONST_SHEET_SIM_SCANED_TOTAL) == 0)
            {
                strSql = "select sim_id , sim_serial, sheet_id, sim_status_type_id,sim_status_type_name,info_source  from VW_SFR_SIM where SHEET_ID = '"
                        + sheetId + "' order by SIM_SERIAL ";
            } else if (sheetSIMGroup.compareTo(SFRInterfaceKey.CONST_SHEET_SIM_SCANED_UNIQUE) == 0)
            {
                strSql = "SELECT sim_id , sim_serial, sheet_id, sim_status_type_id,sim_status_type_name,info_source    FROM vw_sfr_sim  WHERE sheet_id = '"
                        + sheetId
                        + "' and sim_id in( "
                        + " select min(sim_id) from vw_sfr_sim  WHERE sheet_id = "
                        + sheetId + " group by sim_serial) ";
            } else if (sheetSIMGroup.compareTo(SFRInterfaceKey.CONST_SHEET_SIM_SCANED_DUBLICATE) == 0)
            {
                strSql = "select sim_id , sim_serial, sheet_id, sim_status_type_id,sim_status_type_name,info_source  from VW_SFR_SIM where SHEET_ID = '"
                        + sheetId
                        + "' and SIM_ID not in( "
                        + " select min(SIM_ID) as SIM_ID from VW_SFR_SIM where SHEET_ID = "
                        + sheetId + " " + " group by SIM_SERIAL) ";

            } else if (sheetSIMGroup.compareTo(SFRInterfaceKey.CONST_SHEET_SIM_SCANED_ACCEPTED) == 0)
            {
                strSql = "select sim_id , sim_serial, sheet_id, sim_status_type_id,sim_status_type_name,info_source  from VW_SFR_SIM where SHEET_ID = '"
                        + sheetId
                        + "' and SIM_STATUS_TYPE_ID = "
                        + SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED
                        + " order by SIM_SERIAL ";
            } else if (sheetSIMGroup.compareTo(SFRInterfaceKey.CONST_SHEET_SIM_SCANED_REJECTED) == 0)
            {
                strSql = "select sim_id , sim_serial, sheet_id, sim_status_type_id,sim_status_type_name,info_source  from VW_SFR_SIM where SHEET_ID = '"
                        + sheetId
                        + "' and SIM_STATUS_TYPE_ID = "
                        + SFRInterfaceKey.CONST_SIM_STATUS_TYPE_REJECTED
                        + " order by SIM_SERIAL ";
            }
            // Utility.logger.debug(strSql);
             System.out.println("isssssssssssssss " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                sfrVec.add(new SIMModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getBatchesByFilter(Connection con, String batchId,
            String agentId, String batchStatusTypeId,
            String batchCreationDateFrom, String batchCreationDateTo)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_BATCH ";
            if (batchId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_ID = " + batchId + " ";
                andFlag = true;
            }
            if (agentId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " AGENT_ID = " + agentId + " ";
                andFlag = true;
            }
            if (batchStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_STATUS_TYPE_ID = " + batchStatusTypeId
                        + " ";
                andFlag = true;
            }
            if (batchCreationDateFrom.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_DATE >= TO_DATE('" + batchCreationDateFrom
                        + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (batchCreationDateTo.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_DATE <= TO_DATE('" + batchCreationDateTo
                        + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            sqlQuery += " order by BATCH_STATUS_TYPE_ID,BATCH_DATE,BATCH_ID ";
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                BatchModel batchModel = new BatchModel(res);
                batchModel = SheetDAO.getBatchSuggestedStatus(con, batchModel);
                sfrVec.add(batchModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getBatchesByFilterByRowNum(Connection con, int rowNum,
            String batchId, String agentId, String batchStatusTypeId,
            String batchCreationDateFrom, String batchCreationDateTo)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from (select rownum as row_num,BATCH_ID,AGENT_ID,PERSON_FULL_NAME,BATCH_STATUS_TYPE_ID,"
                    + "BATCH_STATUS_TYPE_NAME,BATCH_DATE from VW_SFR_BATCH ";
            if (batchId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_ID = " + batchId + " ";
                andFlag = true;
                // sqlQuery += " and BATCH_ID = "+batchId+" ";
            }
            if (agentId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " AGENT_ID = " + agentId + " ";
                andFlag = true;

                // sqlQuery += " and AGENT_ID = "+agentId+" ";
            }
            if (batchStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_STATUS_TYPE_ID = " + batchStatusTypeId
                        + " ";
                andFlag = true;

                sqlQuery += " and BATCH_STATUS_TYPE_ID = " + batchStatusTypeId
                        + " ";
            }
            if (batchCreationDateFrom.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_DATE >= TO_DATE('" + batchCreationDateFrom
                        + "','mm/dd/yyyy') ";
                andFlag = true;

                // sqlQuery +=
                // " and BATCH_DATE >= TO_DATE('"+batchCreationDateFrom+"','mm/dd/yyyy') ";
            }
            if (batchCreationDateTo.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_DATE <= TO_DATE('" + batchCreationDateTo
                        + "','mm/dd/yyyy') ";
                andFlag = true;

                // sqlQuery +=
                // " and BATCH_DATE <= TO_DATE('"+batchCreationDateTo+"','mm/dd/yyyy') ";
            }
            sqlQuery += " )where row_num > '"
                    + rowNum
                    + "'*30 and row_num <= ('"
                    + rowNum
                    + "'+1)*30 order by BATCH_STATUS_TYPE_ID,BATCH_DATE,BATCH_ID ";
            // Utility.logger.debug("The Query of batches isssssssssssssssssssss "
            // + sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                BatchModel batchModel = new BatchModel(res);
                batchModel = SheetDAO.getBatchSuggestedStatus(con, batchModel);
                sfrVec.add(batchModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getBatchesByBatchId(Connection con, Long batchId)
    {
        Vector batchVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_BATCH where batch_id = '"
                    + batchId + "'";
                    System.out.println(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                BatchModel batchModel = new BatchModel(res);
                batchModel = SheetDAO.getBatchSuggestedStatus(con, batchModel);
                // batchModel = SheetDAO.getSheetsByBatchId(con,batchModel);
                batchVec.add(batchModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return batchVec;
    }

    public static Vector getBatchesByFilterByRowNumNotPending(Connection con,
            int rowNum, String batchId, String agentId,
            String batchStatusTypeId, String batchCreationDateFrom,
            String batchCreationDateTo)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from (select rownum as row_num,BATCH_ID,AGENT_ID,PERSON_FULL_NAME,BATCH_STATUS_TYPE_ID,"
                    + "BATCH_STATUS_TYPE_NAME,BATCH_DATE from VW_SFR_BATCH where batch_status_type_id != 4 ";
            if (batchId.compareTo("") != 0)
            {
                // if(!andFlag){sqlQuery += " where ";}
                // else{sqlQuery += " and ";}
                // sqlQuery += " BATCH_ID = "+batchId+" ";
                // andFlag = true;
                sqlQuery += " and BATCH_ID = " + batchId + " ";
            }
            if (agentId.compareTo("") != 0)
            {
                // if(!andFlag){sqlQuery += " where ";}
                // else{sqlQuery += " and ";}
                // sqlQuery += " AGENT_ID = "+agentId+" ";
                // andFlag = true;

                sqlQuery += " and AGENT_ID = " + agentId + " ";
            }
            if (batchStatusTypeId.compareTo("") != 0)
            {
                // if(!andFlag){sqlQuery += " where ";}
                // else{sqlQuery += " and ";}
                // sqlQuery += " BATCH_STATUS_TYPE_ID = "+batchStatusTypeId+" ";
                // andFlag = true;

                sqlQuery += " and BATCH_STATUS_TYPE_ID = " + batchStatusTypeId
                        + " ";
            }
            if (batchCreationDateFrom.compareTo("*") != 0)
            {
                // if(!andFlag){sqlQuery += " where ";}
                // else{sqlQuery += " and ";}
                // sqlQuery +=
                // " BATCH_DATE >= TO_DATE('"+batchCreationDateFrom+"','mm/dd/yyyy') ";
                // andFlag = true;

                sqlQuery += " and BATCH_DATE >= TO_DATE('"
                        + batchCreationDateFrom + "','mm/dd/yyyy') ";
            }
            if (batchCreationDateTo.compareTo("*") != 0)
            {
                // if(!andFlag){sqlQuery += " where ";}
                // else{sqlQuery += " and ";}
                // sqlQuery +=
                // " BATCH_DATE <= TO_DATE('"+batchCreationDateTo+"','mm/dd/yyyy') ";
                // andFlag = true;

                sqlQuery += " and BATCH_DATE <= TO_DATE('"
                        + batchCreationDateTo + "','mm/dd/yyyy') ";
            }
            sqlQuery += " )where row_num > '"
                    + rowNum
                    + "'*30 and row_num <= ('"
                    + rowNum
                    + "'+1)*30 order by BATCH_STATUS_TYPE_ID,BATCH_DATE,BATCH_ID ";
            // Utility.logger.debug("The Query of batches isssssssssssssssssssss "
            // + sqlQuery);
            // Utility.logger.debug("Using Logger DEBUG: " + sqlQuery);
            // Utility.logger.fatal("Using Logger FATAL: " + sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                BatchModel batchModel = new BatchModel(res);
                batchModel = SheetDAO.getBatchSuggestedStatus(con, batchModel);
                // batchModel = SheetDAO.getSheetsByBatchId(con,batchModel);
                sfrVec.add(batchModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static int countOFBatches(Connection con, String batchId,
            String agentId, String batchStatusTypeId,
            String batchCreationDateFrom, String batchCreationDateTo)
    {
        int count = 0;
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select count(*) as count from VW_SFR_BATCH ";
            if (batchId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_ID = " + batchId + " ";
                andFlag = true;
            }
            if (agentId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " AGENT_ID = " + agentId + " ";
                andFlag = true;
            }
            if (batchStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_STATUS_TYPE_ID = " + batchStatusTypeId
                        + " ";
                andFlag = true;
            }
            if (batchCreationDateFrom.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_DATE >= TO_DATE('" + batchCreationDateFrom
                        + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (batchCreationDateTo.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_DATE <= TO_DATE('" + batchCreationDateTo
                        + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            sqlQuery += " order by BATCH_STATUS_TYPE_ID,BATCH_DATE,BATCH_ID ";
            // Utility.logger.debug("The count query issssssssssssssssss " +
            // sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                count = res.getInt("count");
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return count;
    }

    public static BatchModel getBatchSuggestedStatus(Connection con,
            BatchModel batchModel)
    {
        try
        {
            Statement stat = con.createStatement();
            boolean suggestStatusAccept = false;
            boolean suggestStatusClose = false;
            boolean suggestStatusVerify = false;

            String batchId = batchModel.getBatchId();

            String strSql = "select * from SFR_SHEET where BATCH_ID = "
                    + batchId + " ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                String sheetStatusId = res.getString("SHEET_STATUS_TYPE_ID");
                if (sheetStatusId.compareTo(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_ACCEPTED) == 0)
                {
                    suggestStatusAccept = true;
                } else if (sheetStatusId.compareTo(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_CLOSED) == 0)
                {
                    suggestStatusClose = true;
                }
                if (sheetStatusId.compareTo(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_VERIFIED) == 0)
                {
                    suggestStatusVerify = true;
                }
            }
            if (suggestStatusAccept)
            {
                batchModel.setBatchSuggestedStatus(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_ACCEPTED);
            } else
            {
                if (suggestStatusClose)
                {
                    batchModel.setBatchSuggestedStatus(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_CLOSED);
                } else
                {
                    if (suggestStatusVerify)
                    {
                        batchModel.setBatchSuggestedStatus(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_VERIFIED);
                    } else
                    {
                        batchModel.setBatchSuggestedStatus("0");
                    }
                }
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return batchModel;
    }

    public static Vector getSheetbySheetId(Connection con, String batchId,
            Long sheetId)
    {
        Vector sheetVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_SHEET where batch_id = '"
                    + batchId + "' and sheet_id = '" + sheetId + "'";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                SheetModel sheetModel = new SheetModel(res);
                sheetModel = SheetDAO.getSheetStatistics(con, sheetModel);
                sheetVec.add(sheetModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sheetVec;
    }

    public static void deleteSheet(Connection con, String batchId ,int sheetSerial, String sheetId)
    {
       String sqlQuery = "Delete from SFR_SHEET where SHEET_ID = "+sheetId+" and SHEET_SERIAL = "+sheetSerial+" and BATCH_ID = "+ batchId;
       System.out.println(sqlQuery);
       DBUtil.executeSQL(sqlQuery, con);
    }

    public static void deleteSIM(Connection con,  String sheetId)
    {
       String sqlQuery = "Delete from SFR_SIM where SHEET_ID = "+sheetId+" ";
       System.out.println(sqlQuery);
       DBUtil.executeSQL(sqlQuery, con);
    }

    public static Vector getSheetsByFilter(Connection con, String batchId,
            int sheetSerial, String posAgentCode, String sheetStatusTypeId,
            String posCode, String sheetId)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_SHEET ";
            if (batchId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_ID = " + batchId + " ";
                andFlag = true;
            }
            if (sheetSerial > 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SHEET_SERIAL = " + sheetSerial + " ";
                andFlag = true;
            }
            if (posAgentCode.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " POS_AGENT_CODE = '" + posAgentCode + "' ";
                andFlag = true;
            }
            if (sheetStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SHEET_STATUS_TYPE_ID = " + sheetStatusTypeId
                        + " ";
                andFlag = true;
            }
            if (posCode.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " POS_CODE = '" + posCode + "' ";
                andFlag = true;
            }
            if (sheetId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SHEET_ID = " + sheetId + " ";
                andFlag = true;
            }
            sqlQuery += " order by SHEET_STATUS_TYPE_ID,SHEET_SERIAL ";
            // Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                SheetModel sheetModel = new SheetModel(res);
                sheetModel = SheetDAO.getSheetStatistics(con, sheetModel);
                sfrVec.add(sheetModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static int closeSheetsperBatch(Connection con, String batchId,
            String strUserId)
    {
        Vector sfrVec = new Vector();
        int andFlag = 0;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_SHEET where batch_id = '"
                    + batchId + "' ";

            // Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                SheetModel sheetModel = new SheetModel(res);
                sheetModel = SheetDAO.getSheetStatistics(con, sheetModel);
                sfrVec.add(sheetModel);
            }
            // System.out.println("The size of the vector isssssssssssssss " +
            // sfrVec.size());
            stat.close();
            for (int i = 0; i < sfrVec.size(); i++)
            {
                SheetModel sheetModel = (SheetModel) sfrVec.get(i);
                String sheetId = sheetModel.getSheetId();
                String sheetStatusTypeId = sheetModel.getSheetStatusTypeId();
                // System.out.println("sheetStatusTypeId  " +
                // sheetStatusTypeId);
                int sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
                // System.out.println("sheetSIMScanedTotal  " +
                // sheetSIMScanedTotal);
                int sheetSIMCount = sheetModel.getSheetSIMCount();
                // System.out.println("sheetSIMTotal  " + sheetSIMCount);
                if (sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED) == 0
                        && sheetSIMScanedTotal > 0)
                {
                    if (sheetSIMScanedTotal == sheetSIMCount)
                    {
                        // System.out.println("Medhattttttttttttttttttt" );
                        sheetStatusTypeId = SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_CLOSED;
                        SheetDAO.updateSheetStatus(con, sheetId, sheetStatusTypeId, strUserId);
                        andFlag = 1;
                    } else
                    {
                        andFlag = 3;
                    }
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return andFlag;
    }

    public static int rejectSheetsperBatch(Connection con, String batchId,
            String strUserId)
    {
        Vector sfrVec = new Vector();
        int andFlag = 0;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_SHEET where batch_id = '"
                    + batchId + "' ";

            // Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                SheetModel sheetModel = new SheetModel(res);
                sheetModel = SheetDAO.getSheetStatistics(con, sheetModel);
                sfrVec.add(sheetModel);
            }
            // System.out.println("The size of the vector isssssssssssssss " +
            // sfrVec.size());
            stat.close();
            for (int i = 0; i < sfrVec.size(); i++)
            {
                SheetModel sheetModel = (SheetModel) sfrVec.get(i);
                String sheetId = sheetModel.getSheetId();
                String sheetStatusTypeId = sheetModel.getSheetStatusTypeId();
                // System.out.println("sheetStatusTypeId  " +
                // sheetStatusTypeId);
                int sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
                // System.out.println("sheetSIMScanedTotal  " +
                // sheetSIMScanedTotal);
                if (sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED) == 0
                        && sheetSIMScanedTotal == 0)
                {
                    // System.out.println("Medhattttttttttttttttttt" );
                    sheetStatusTypeId = SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_REJECTED;
                    SheetDAO.updateSheetStatus(con, sheetId, sheetStatusTypeId, strUserId);
                    andFlag = 2;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return andFlag;
    }

    public static SheetModel getSheetStatistics(Connection con,
            SheetModel sheetModel)
    {
        try
        {
            Statement stat = con.createStatement();
            String sheetSerial = sheetModel.getSheetSerial();
            String sheetId = sheetModel.getSheetId();
            int sheetSIMScanedTotal = 0;
            String strSql = "select count(SIM_SERIAL),count(distinct SIM_SERIAL) from SFR_SIM where SHEET_ID = "
                    + sheetId + "";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next())
            {
                sheetSIMScanedTotal = res.getInt(1);
                int sheetSIMScanedUnique = res.getInt(2);
                int sheetSIMScanedDublicate = sheetSIMScanedTotal
                        - sheetSIMScanedUnique;
                sheetModel.setSheetSIMScanedTotal(sheetSIMScanedTotal);
                sheetModel.setSheetSIMScanedUnique(sheetSIMScanedUnique);
                sheetModel.setSheetSIMScanedDublicate(sheetSIMScanedDublicate);
            }
            res.close();

            String strSql2 = "select count(SIM_SERIAL) from SFR_SIM where SHEET_ID = "
                    + sheetId
                    + " and SIM_STATUS_TYPE_ID = "
                    + SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED + " ";

            res = stat.executeQuery(strSql2);
            if (res.next())
            {
                int sheetSIMScanedAccepted = res.getInt(1);
                int sheetSIMScanedRejeced = sheetSIMScanedTotal
                        - sheetSIMScanedAccepted;
                sheetModel.setSheetSIMScanedAccepetd(sheetSIMScanedAccepted);
                sheetModel.setSheetSIMScanedRejected(sheetSIMScanedRejeced);
            }

            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sheetModel;
    }

    public static Vector getSheetsByFilter(String batchId, int sheetSerial,
            String sheetStatusTypeId, Connection con)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {

            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_SHEET ";
            if (batchId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_ID = " + batchId + " ";
                andFlag = true;
            }
            if (sheetSerial > 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SHEET_SERIAL = " + sheetSerial + " ";
                andFlag = true;
            }
            if (sheetStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SHEET_STATUS_TYPE_ID = " + sheetStatusTypeId
                        + " ";
                andFlag = true;
            }
            sqlQuery += " order by SHEET_STATUS_TYPE_ID ";
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                sfrVec.add(new SheetModel(res));
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static java.sql.PreparedStatement getSheetExistPreparedStatement(
            Connection con)
    {
        try
        {
            return con.prepareStatement("select 1 from  SFR_SHEET where SHEET_SERIAL = ? and SHEET_STATUS_TYPE_ID <> 5 ");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSheetExistWithNonRejectedStatus(int sheetSerial,
            PreparedStatement stat)
    {
        boolean sheetExist = false;

        try
        {
            // String sqlQuery =
            // "select 1 from  SFR_SHEET where SHEET_SERIAL = "+sheetSerial+" and SHEET_STATUS_TYPE_ID <> 5 ";
            stat.setInt(1, sheetSerial);

            ResultSet res = stat.executeQuery();
            if (res.next())
            {
                sheetExist = true;
            }

            res.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return sheetExist;
    }

    public static Vector getSheetsWithNonRejectedStatus(int sheetSerial,
            Statement stat)
    {
        Vector sfrVec = new Vector(10, 2);
        try
        {
            // Connection con = Utility.getConnection();
            // Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_SHEET where SHEET_SERIAL = "
                    + sheetSerial + " and SHEET_STATUS_TYPE_ID <> 5 ";
            ResultSet res = stat.executeQuery(sqlQuery);
            // System.out.println(sqlQuery);
            while (res.next())
            {
                sfrVec.add(new SheetModel(res));
            }
            res.close();
            // stat.close();
            // Utility.closeConnection(con);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getSheetsWithNonRejectedStatus(int sheetSerial,
            Connection con)
    {
        Vector sfrVec = new Vector();
        try
        {

            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SFR_SHEET where SHEET_SERIAL = "
                    + sheetSerial + " and SHEET_STATUS_TYPE_ID <> 5 ";
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                sfrVec.add(new SheetModel(res));
            }
            res.close();
            stat.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getSheetsStatusByFilter(Connection con,
            String sheetSerial, String posAgentId, String sheetStatusTypeId,
            String changeDateFrom, String changeDateTo)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = true;
        try
        {
            Statement stat = con.createStatement();
            String sqlQuery = "select VW_SFR_SHEET_STATUS.*,VW_SFR_BATCH.AGENT_ID,VW_SFR_BATCH.PERSON_FULL_NAME AGENT_FULL_NAME from VW_SFR_SHEET_STATUS,VW_SFR_BATCH where "
                    + " VW_SFR_SHEET_STATUS.BATCH_ID = VW_SFR_BATCH.BATCH_ID ";
            if (sheetSerial.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " VW_SFR_SHEET_STATUS.SHEET_ID IN( select SHEET_ID from SFR_SHEET where SHEET_SERIAL = "
                        + sheetSerial + " )";
                andFlag = true;
            }
            if (posAgentId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " VW_SFR_SHEET_STATUS.POS_AGENT_ID = " + posAgentId
                        + " ";
                andFlag = true;
            }
            if (sheetStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " VW_SFR_SHEET_STATUS.SHEET_STATUS_TYPE_ID = "
                        + sheetStatusTypeId + " ";
                andFlag = true;
            }
            if (changeDateFrom.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " VW_SFR_SHEET_STATUS.SHEET_STATUS_TIMESTAMP >= TO_DATE('"
                        + changeDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (changeDateTo.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " VW_SFR_SHEET_STATUS.SHEET_STATUS_TIMESTAMP <= TO_DATE('"
                        + changeDateTo + "','mm/dd/yyyy')+1 ";
                andFlag = true;
            }
            sqlQuery += " order by VW_SFR_SHEET_STATUS.BATCH_ID,VW_SFR_SHEET_STATUS.SHEET_SERIAL,VW_SFR_SHEET_STATUS.SHEET_STATUS_TIMESTAMP ";
            // Utility.logger.debug(sqlQuery);
            // System.out.println("The sqlQuery isssssssssssssssssss " +
            // sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                SheetStatusModel sheetStatusModel = new SheetStatusModel(res);
                sheetStatusModel.setAgentId(res.getString("AGENT_ID"));
                sheetStatusModel.setAgentFullName(res.getString("AGENT_FULL_NAME"));
                sfrVec.add(sheetStatusModel);
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static Vector getSIMStatusByFilter(Connection con, String simSerial,
            String sheetSerial, String batchId, String simStatusTypeId,
            String changeByUserId, String changeDateFrom, String changeDateTo,
            String activationDateFrom, String activationDateTo)
    {
        Vector sfrVec = new Vector();
        boolean andFlag = false;
        try
        {
            //Ahmed Adel 24-8-2011
            Statement stat = con.createStatement();
            String sqlQuery = "SELECT   sim_status_id, sim_status_type_id, sim_status_type_name, sim_serial, sim_id, sim_status_timestamp, user_id, person_full_name, sheet_id,sheet_serial, batch_id, second_pos_id, second_pos_code,second_pos_name, pos_id, pos_name, pos_code, agent_id,person_full_name_1,INFO_SOURCE FROM vw_sfr_sim_status ";
            if (simSerial.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SIM_SERIAL = '" + simSerial + "' ";
                andFlag = true;
            }
            if (sheetSerial.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SHEET_SERIAL = " + sheetSerial + " ";
                andFlag = true;
            }
            if (batchId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " BATCH_ID = " + batchId + " ";
                andFlag = true;
            }
            if (simStatusTypeId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SIM_STATUS_TYPE_ID = " + simStatusTypeId + " ";
                andFlag = true;
            }
            if (changeByUserId.compareTo("") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " USER_ID = " + changeByUserId + " ";
                andFlag = true;
            }
            /*
             * if(contractTypeId.compareTo("")!=0) { if(!andFlag){sqlQuery +=
             * " where ";} else{sqlQuery += " and ";} sqlQuery +=
             * " CONTRACT_TYPE = "+contractTypeId+" "; andFlag = true; }
             */
            if (changeDateFrom.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SIM_STATUS_TIMESTAMP >= TO_DATE('"
                        + changeDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (changeDateTo.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " SIM_STATUS_TIMESTAMP <= TO_DATE('" + changeDateTo
                        + "','mm/dd/yyyy')+1 ";
                andFlag = true;
            }
            if (activationDateFrom.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " ACTIVATION_DATE >= TO_DATE('"
                        + activationDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (activationDateTo.compareTo("*") != 0)
            {
                if (!andFlag)
                {
                    sqlQuery += " where ";
                } else
                {
                    sqlQuery += " and ";
                }
                sqlQuery += " ACTIVATION_DATE <= TO_DATE('" + activationDateTo
                        + "','mm/dd/yyyy')+1 ";
                andFlag = true;
            }

              sqlQuery += " order by SIM_STATUS_TYPE_ID,SIM_SERIAL,SHEET_SERIAL,SIM_STATUS_TIMESTAMP ";
//            sqlQuery +=" UNION ALL "+sqlQuery.replace("vw_sfr_sim_status", "vw_sfr_sim_status_bkup")+ " order by SIM_STATUS_TYPE_ID,SIM_SERIAL,SHEET_SERIAL,SIM_STATUS_TIMESTAMP ";

            // Utility.logger.debug(sqlQuery);

            System.out.println("SQL Query:::::::: "+sqlQuery);

            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next())
            {
                sfrVec.add(new SIMStatusModel(res));
            }
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }

    public static String getSecondPOSId(String secondPosCode, Connection con)
    {
        String secondPosId = "";
        try
        {

            Statement stat = con.createStatement();
            String strSql = "select * from VW_SFR_SECOND_POS where SECOND_POS_CODE = '"
                    + secondPosCode + "' ";
            
            
            System.out.println("check second pos code " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            if (res.next())
            {
                secondPosId = res.getString("SECOND_POS_ID");
            }
            stat.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return secondPosId;
    }

    public static String getPOSId(String posCode, Connection con)
    {
        String posId = "";
        try
        {

            Statement stat = con.createStatement();
            String strSql = "select * from GEN_DCM where DCM_CODE = '"
                    + posCode + "' ";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next())
            {
                posId = res.getString("DCM_ID");
            }
            stat.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return posId;
    }

    public static String getAgentName(String agentId, Connection con)
    {
        String agentName = "";
        try
        {

            Statement stat = con.createStatement();
            String strSql = "select PERSON_FULL_NAME from GEN_PERSON where PERSON_ID ="
                    + agentId;
            if (agentId.compareTo("") != 0)
            {
                ResultSet res = stat.executeQuery(strSql);
                if (res.next())
                {
                    agentName = res.getString("PERSON_FULL_NAME");
                }
                res.close();
            }

            stat.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return agentName;
    }

    public static void updateBatchStatus(Connection con, String batchId,
            String batchStatusTypeId, String userId)
    {
        try
        {
            Statement stat = con.createStatement();
            String insertSql = "update SFR_BATCH "
                    + "SET BATCH_STATUS_TYPE_ID = " + batchStatusTypeId + " "
                    + "WHERE BATCH_ID = " + batchId + "";
            stat.execute(insertSql);
            Long lBatchId = Long.valueOf(batchId);
            SheetDAO.insertBatchStatus(lBatchId, batchStatusTypeId, userId, con);

            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void updateSheetStatus(Connection con, String sheetId,
            String sheetStatusTypeId, String userId)
    {
        String insertSql = "update SFR_SHEET SET SHEET_STATUS_TYPE_ID = "
                + sheetStatusTypeId + " WHERE SHEET_ID = " + sheetId;
        DBUtil.executeSQL(insertSql, con);

    }

    public static void updateSheetStatusByBatch(Connection con, String batchId,
            String sheetStatusTypeId, String userId)
    {
        String insertSql = "update SFR_SHEET SET SHEET_STATUS_TYPE_ID = "
                + sheetStatusTypeId + " WHERE BATCH_ID = '" + batchId + "'";
        DBUtil.executeSQL(insertSql, con);

    }

    public static void insertExcelImportLog(String fileName, Connection con)
    {
        String insertSql = "insert into SFR_EXCEL_IMPORT_LOG (EXCEL_IMPORT_LOG_ID,EXCEL_IMPORT_LOG_DATE,FILE_NAME)  values(SEQ_SFR_EXCEL_IMPORT_LOG_ID.nextval,SYSDATE,?)";
        DBUtil.executePreparedStatment(insertSql, con, fileName);
    }

    public static final Vector SearchImportLog(String date, Connection con)
    {
        try
        {

            Statement stat = con.createStatement();
            String sql = "SELECT EXCEL_IMPORT_LOG_ID, to_char(EXCEL_IMPORT_LOG_DATE,'dd/mm/yyyy') IMPORT_LOG_DATE , to_char(EXCEL_IMPORT_LOG_DATE,'hh:mi:ssam')import_log_time ,FILE_NAME "
                    + " FROM SFR_EXCEL_IMPORT_LOG ";

            if (date != null && date.length() > 0)
            {
                sql += " where to_char(EXCEL_IMPORT_LOG_DATE,\'dd/mm/yyyy\') = '"
                        + date + "'";
            }

            sql += " order by excel_import_log_date desc";
            // Utility.logger.debug( sql );

            ResultSet res = stat.executeQuery(sql);

            Vector importLogCol = new Vector();
            while (res.next())
            {
                String excelImportLogID = res.getString("EXCEL_IMPORT_LOG_ID");
                String excelImportDate = res.getString("IMPORT_LOG_DATE");
                String excelImportTime = res.getString("IMPORT_LOG_TIME");
                String fileName = res.getString("FILE_NAME");
                SFRImportLogModel importLogModel = new SFRImportLogModel(excelImportLogID, excelImportDate, excelImportTime, fileName);
                importLogCol.add(importLogModel);
            }

            return importLogCol;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // ///// check SIM in lcs/////////////////

    public static HashMap getAllSIMWarningTypes(Connection con)
    {
        HashMap simWarningTypes = new HashMap();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from sfr_sim_warning_type ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                String warningTypeId = res.getString("SIM_WARNING_TYPE_ID");
                String warningTypeName = res.getString("SIM_WARNING_TYPE_NAME");
                simWarningTypes.put(warningTypeName, warningTypeId);
            }
            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return simWarningTypes;
    }

    public static boolean checkBatchExist(Connection con, String batchId)
    {
        String strSql = "select 1 from SFR_BATCH where batch_id = " + batchId;

        boolean batchExist = DBUtil.executeSQLExistCheck(strSql, con);

        return batchExist;
    }

    public static void chanegBatchDate(Connection con, String fromBatchId,
            String toBatchId, String date)
    {
        String strSql = "update SFR_BATCH set BATCH_DATE=to_date( '" + date
                + "'" + ",'mm/dd/yyyy')" + "where batch_id between "
                + fromBatchId + " and " + toBatchId;
        DBUtil.executeSQL(strSql);

    }
    
    
    
    public static void chanegBatchDateByID(Connection con, String batchId, String date)
    {
        String strSql = "update SFR_BATCH set BATCH_DATE=to_date( '" + date
                + "'" + ",'mm/dd/yyyy')" + "where batch_id = " + batchId;
        DBUtil.executeSQL(strSql);

    }

    public static void insertBatchHistory(String batchId, String userId,
            String action, Connection con)
    {
        String strSql = "insert into sfr_batch_history(batch_id,user_id,time_stamp,batch_date,STATUS)values"
                + "('"
                + batchId
                + "','"
                + userId
                + "',SYSDATE,(SELECT BATCH_DATE FROM SFR_BATCH WHERE BATCH_ID = '"
                + batchId + "'),'" + action + "')";
        DBUtil.executeSQL(strSql, con);

    }

    
    
    public static String getSFRBatchDate(Connection con,String batchId)
	{				
		String date = "";
   	    Statement stat;
	try {
		  stat = con.createStatement();
		  String strSql = "select BATCH_DATE from SFR_BATCH where batch_id = "+batchId;
		  System.out.println(strSql);
		  ResultSet res = stat.executeQuery(strSql);
		  if (res.next()) 
		  {  
			 date = res.getString("BATCH_DATE"); 
		  }
	}
	catch (Exception e) {
		
	}
	return date;
	}
    
    
    /*
     * Function check the first status of batch using date if 0 so open if 1 so closed
     * so function return data if batch closed
     */
    
    public static String selectSFRBatchStatus(String year, String month , Connection con)
    {
    	 String msg = "";
    	 Statement stat;
		
	try {
		  stat = con.createStatement();
		  String strSql = "select * from MONTHS_MANAGEMENT where YEAR = "+ year+" and MONTH="+month+" and FIRST_STATUS=1";
		  
		  System.out.println(strSql);
		  
		  ResultSet res = stat.executeQuery(strSql);
		
		  if (res.next()) 
		  {
			res.close();
			stat.close();
			msg = "Sorry , Batch can't be deleted ..";
		  }
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	return msg;
    }
    
    
    public static void deleteBatch(String batchId, Connection con)
    {
        try
        {

            String sql = "begin  delete_sfr_batch(?); end;";
            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.setInt(1, Integer.parseInt(batchId.trim()));
            colableStat.execute();
            colableStat.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    
    
    
    public static Vector getAllMonths(Connection con)
    {
        Vector sfrVec = new Vector();
        try
        {
            Statement stat = con.createStatement();
            String strSql = "select * from MONTHS_MANAGEMENT order by YEAR , MONTH ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next())
            {
                sfrVec.add(new MonthsModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return sfrVec;
    }
    
    
    public static String insertNewYear(String year, Connection con)
    {
    	String msg = "";
    	 Statement stat;
		try {
			stat = con.createStatement();
         String strSql = "select * from MONTHS_MANAGEMENT where YEAR = " + year;
         ResultSet res = stat.executeQuery(strSql);
        if(res.next())
         {
            res.close();
            stat.close();  
            msg = "Sorry , Year Already in the System .." ;
         }
        else
        {
    	  for(int i = 1 ; i < 13 ; i++)
    	  {
           strSql = "insert into MONTHS_MANAGEMENT (YEAR,MONTH,FIRST_STATUS,SECOND_STATUS) values ("+ year+","+ i +",0,0)";
           System.out.println(strSql);     
           DBUtil.executeSQL(strSql, con);
    	  }
    	  res.close();
          stat.close();  
    	  msg = "Data Created Successfully ...";
        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;

    }
    
    
    public static void changeMonthStatus(String strUserID , String statusId , String status , String month , String year, Connection con)
    {
    	/*
    	 * Status = 0 //// Open
    	 * Status = 1 ///// delete
    	 * statusId = 1 /// sfr
    	 * statusId = 2 /// commission
    	 * Action = 0 //// Open
    	 * Action = 1 ///// delete
    	 */
    	String msg = "";
        String strSql = "";
        if(statusId.equals("1"))
          strSql = "update MONTHS_MANAGEMENT set FIRST_STATUS = "+status+" where YEAR ="+year+" and MONTH="+month;
        else
          strSql = "update MONTHS_MANAGEMENT set SECOND_STATUS = "+status+" where YEAR ="+year+" and MONTH="+month;
       
        System.out.println(strSql);     
        DBUtil.executeSQL(strSql, con);
        
        String strSqlQuery = "insert into MONTHS_MANAGEMENT_TRACKER (ACTION,USER_ID,ACTION_GROUP) values ("+ status+","+ strUserID +","+statusId+")";
        System.out.println(strSqlQuery);     
        DBUtil.executeSQL(strSqlQuery, con);
        
      
    	 

    }
}