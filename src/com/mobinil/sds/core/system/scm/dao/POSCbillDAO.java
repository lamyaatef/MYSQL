/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.scm.model.POSCbillModel;
import com.mobinil.sds.core.system.scm.model.POSModel;
import com.mobinil.sds.core.system.scm.model.STKOwnerModel;
import com.mobinil.sds.core.system.scm.model.STKStockModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author Ahmed Safwat
 */
public class POSCbillDAO {

/*
 * Check if the the  POS is exist , POS Level and ACTIVE
 @return DCM_ID if it is Found, -2 if an exception occurred, -1 if POS not found
 */
public static long checkPOS(Connection argCon, POSCbillModel posCbillModel) {

            String sqlStatement;
            //UPDATED BY MEDHAT
            sqlStatement="SELECT DCM_ID FROM VW_GEN_DCM_SCM WHERE  DCM_CODE=?  AND DCM_STATUS_ID=1"+
           // " AND DCM_PAYMENT_LEVEL_ID=4"+
            " AND CHANNEL_ID=1"  ;
            POSModel posModel=(POSModel)DBUtil.executePreparedSqlQuerySingleValue(sqlStatement, POSModel.class, argCon, new Object[]{posCbillModel.getPOSCode()});
            if(posModel!=null)
                return posModel.getPOS_ID();
            else
            return -1;
}

/*
 * Check if the the  STK is exist and New
 @return STK ID if STK Found, -1 if STK not found
 */
public static long checkSTK(Connection argCon, POSCbillModel posCbillModel) {


            String sqlStatement;
            sqlStatement="SELECT STK_ID FROM SCM_STK_STOCK WHERE STK_NUMBER=? AND STK_STATUS_ID=2";
            STKStockModel stkStockModel= DBUtil.executePreparedSqlQuerySingleValue(sqlStatement, STKStockModel.class, argCon, new Object[]{posCbillModel.getSTKNo()});
            if(stkStockModel!=null)
                return stkStockModel.getStkId();
            else
                return -1;
}

/*
 * Check if the the  POS owns this STK
 @return STKOwnerModel if POS owns this STK , null if not
 */
public static STKOwnerModel checkPOSOwnSTK(Connection argCon, long stkId, long dcmId) {


            String sqlStatement;
            sqlStatement="SELECT STK_ID,DCM_ID,IQRAR_RECEVING_STATUS_ID,STK_STATUS_ID FROM SCM_STK_OWNER WHERE STK_ID=? AND DCM_ID=?";
            return DBUtil.executePreparedSqlQuerySingleValue(sqlStatement, STKOwnerModel.class, argCon, new Object[]{stkId,dcmId}) ;
}


/*
 * @return Vector of all imported cbill
 */

public static Vector<POSCbillModel> getPOSImportedCBill(Connection argCon) {

            String sqlStatement;
            sqlStatement="SELECT POS_CODE,STK_NO,CASE_ID FROM SCM_CBILL_TEMP";
            return DBUtil.executeSqlQueryMultiValue(sqlStatement, POSCbillModel.class, argCon);
    }


public static void importcbillCase(String cbillCase,String STK_ID)
{
  String checkSTK = "SELECT * FROM SDS.SCM_CBILL_CASE WHERE STK_ID= "+ STK_ID;

    if (DBUtil.executeSQLExistCheck(checkSTK)){
    return;
    }else{
  String sqlstatement="INSERT INTO SDS.SCM_CBILL_CASE ("+"CASE, STK_ID)"+"VALUES ("+cbillCase+","+STK_ID+")" ;
    DBUtil.executeSQL(sqlstatement);
    }
}

/*
 * Used to truncate cbill table
 * 
 */
public static void truncatePOSCbillTempTable(Connection argCon) {

            String sqlStatement;
            sqlStatement="TRUNCATE TABLE SCM_CBILL_TEMP";
            DBUtil.executeSQL(sqlStatement, argCon);
    }

/*
 * Used to update DCM_VERFIFIED_STATUS_ID on SCM_STK_OWNER TABLE to verify the pos
 * 
 */
public static void updatePOSVerification(Connection argCon,long dcmId,long stkId) {

            String sqlStatement;
            sqlStatement="UPDATE SCM_STK_OWNER SET DCM_VERIFIED_STATUS_ID=2,DCM_VERIFICATION_DATE=sysdate WHERE DCM_ID=? AND STK_ID=?";
            DBUtil.executePreparedStatment(sqlStatement, argCon, new Object[]{dcmId,stkId});
    }

/*
 * Used to update DCM_VERFIFIED_STATUS_ID on SCM_STK_OWNER TABLE and STK_STATUS_ID if the
 * the iqrar recieved  pos
 * 
 */
public static void updatePOSVerificationAndActivateStk(Connection argCon,long dcmId,long stkId) {

            String sqlStatement;
            sqlStatement="UPDATE SCM_STK_OWNER SET DCM_VERIFIED_STATUS_ID=2,STK_STATUS_ID=4 WHERE DCM_ID=? AND STK_ID=?";
            DBUtil.executePreparedStatment(sqlStatement, argCon, new Object[]{dcmId,stkId});
    }

}