package com.mobinil.sds.core.system.cr.contract.dao;

import com.mobinil.mcss.lcs.dao.LcsDao;
import com.mobinil.mcss.lcs.model.LcsModel;
import oracle.jdbc.driver.OracleTypes;
import com.mobinil.sds.core.utilities.*;

import java.util.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import com.mobinil.sds.core.system.cr.contract.dto.*;
import com.mobinil.sds.core.system.cr.contract.dao.*;
import com.mobinil.sds.core.system.cr.contract.dto.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import com.mobinil.sds.core.system.cr.sheet.dao.*;
import com.mobinil.sds.web.interfaces.cr.*;
import java.sql.*;
/*
 * this is the contract dao responsible of many of db operations about the
 * contract 1- get contract status types as ContractStatusTypeModel in the
 * contractStatusTypeDto that the method return for this phase that is being
 * sent to the method as an argument 2- get contract by the contract id this
 * method return a contractModel object that have the information of this
 * contract of the id that is sent to the method 3- update contract take
 * contract id , sheet id , batch id , new status , String[] of applied user
 * warnings, Vector contract system warning and update this contract in the
 * database by inserting a new status to it and inserting the applicaed user
 * warning+ system warning 4- insert a new status to the contract specified by
 * the contract id 5- check if any field of the contract information is missing
 * 6- generete the system warnings of this contract id upon verifying with
 * external database which are the inventory , post paid , pre paid databases 7-
 * get user contract warning by phase takes an inpute a phase name and retrieve
 * all activated warning of type user warning in this phase 8- get contract
 * warnings of this contract id which are the warnings that are connected to teh
 * lastest status this contract took 9- get contract by main sim no and batch id
 * 10- get all contracts that have a status in the authentication phase this is
 * the method utilized to view all authenticated contracts in this sheet 11-
 * insert a new status to all contracts in this sheet 12- get a contract by its
 * main sim no and its batch id the returned type is contract model object 13-
 * get contract dto for the list of contract ids that are sent as string objects
 * in the contracts vector the contractdto retruend contain contract information
 * for all the ids sent in the contracts vector that have teh status either
 * accpeted verify or accepted authintication or rejected authinticatino 14- get
 * sheet contracts eligable for authintication for the input sheet Id contracts
 * that are eligable for authintication which have the status of accepted verify
 * or accepted authintication or rejected authintication 15- get sheet contracts
 * of this sheet id it return ContractDto Object 16-get all batch contracts in
 * this phase phaseName it return a contract dto object 17- get contract status
 * id of this contract status name 18- get contract history of this contract
 * main sim no the method return a vector of ContractHistoryModel objects 19-
 * update contract warning status the method update the contract warning of the
 * id sent to the status sent 20- is contract has warning in verification this
 * method check if the contract designated by the contract main sim no had a
 * warning on it in the verification stage or not the method retuirn a boolean
 * either true or false 21 - is contract has warning in the authentication phase
 * takes an input string contract main sim no and return a true if the contract
 * has a warning in this phase false other wise 22- get pos question flag this
 * method check the sheet type of the sheet id sent to it and return the boolean
 * value of the flag which decide if the pos question apply to this sheet type
 * or not 23- get batch sheet contracts random sample return the randome sammple
 * chosen contracts of this sheet in this batch as ContractDto object also the
 * method take an array of the contracts that are alerady dispalyed to the user
 * so the method union them with the random sample and insure that the generate
 * sample is not one of the already displayed to the user the percentage param
 * is the percentage of contracts needed to commission which is one of the
 * properties of the sheet type
 */

public class ContractDao {

    /*
     * TRUN THIS FLAG TO TRUE IF U WANT TO DEBUG THIS CODE dont forget to turn
     * it back to false before deployment casue it slow the system when it is
     * true since it prints alot of debuging messages
     */
    private static final boolean debugFlag = false;

    /*
     * constructor made private to make no one able to instantiate an instance
     * of this class since all its methods are static
     */
    private ContractDao() {
    }

    /*
     * these are the constants of the names of the warnings from external
     * database taht the system checks and add them when needed in the contract
     * verification phase
     */
    //SYSTEM WARNING 
    //If any of these warnings existis it forbids the update of the contract status
    private static final String CONTRACT_WARNING_NOT_EXIST_IN_INVENTORY = "CONTRACT DOES NOT EXIST IN INVENTORY";
    private static final String CONTRACT_WARNING_NOT_SOLD = "CONTRACT IS WITH A NON-ISSUED STATUS IN INVENTORY";
    private static final String CONTRACT_WARNING_LCS_CONNECTION_FAILED = "LCS CONNECTION FAILED TO CONNECT";
    private static final String CONTRACT_WARNING_NOT_EXIST_IN_BSCS = "CONTRACT DOES NOT EXIST IN POSTPAID DB";
    private static final String CONTRACT_WARNING_NOT_EXIST_IN_PREPAID = "CONTRACT NOT INITIALIZED IN THE PREPAID DB";
    //SYSTEM OPTIONAL WARNING
    //These warnings does not forbid the update of the contract status
    private static final String CONTRACT_WARNING_NOT_ACTIVATED_POSTPAID = "CONTRACT WAS NOT ACTIVATED";
    private static final String CONTRACT_WARNING_NOT_INITIALIZED_PREPAID = "CONTRACT WAS NOT INITIALIZED";
    private static final String CONTRACT_WARNING_EMPTY_FIELDS = "CONTRACT HAS EMPTY FIELDS";
    private static final String CONTRACT_WARNING_SOLD_TO_DIFFERENT_DCM = "CONTRACT WAS SOLD TO DIFFERENT DCM";
    private static final String CONTRACT_EXIST_WITH_A_DIFFERENT_TYPE_IN_LCS = "CONTRACT EXIST WITH A DIFFERENT TYPE IN LCS";
    private static final String CONTRACT_POST_PAID_HAS_DIFFERENT_DIAL_NO = "CONTRACT EXIST WITH DIFFERENT DIAL IN POST PAID DB";
    private static final String CONTRACT_SIM_SWAP_HAS_DIFFERENT_DIAL_NO = "CONTRACT EXIST WITH DIFFERENT DIAL IN POST PAID DB";
    private static final String CONTRACT_PRE_PAID_HAS_DIFFERENT_DIAL_NO = "CONTRACT EXIST WITH DIFFERENT DIAL IN PRE PAID DB";

    public static final void updateCrLastStatus(Connection con, String contractId, String statusId) throws SQLException {
        Statement stat = con.createStatement();
        String strSql = "update CR_CONTRACT "
                + "SET CONTRACT_LAST_STATUS_ID = " + statusId + " "
                + "WHERE CONTRACT_ID = " + contractId;

        System.out.println("The update query issssssssssssssss " + strSql);
        stat.executeUpdate(strSql);
        stat.close();
    }

    public static final void deleteTmpSim(Connection con) {
        String sql = "delete from CR_SIM_NUMBER_TEMP";
        DBUtil.executeSQL(sql, con);

    }

    public static Vector getSimNum(Connection con) {
        Vector<String> simVec = new Vector<String>();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from CR_SIM_NUMBER_TEMP";

            //Utility.logger.debug(strSql);
            System.out.println("The query is " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                simVec.add(res.getString("SIM_NUMBER"));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simVec;
    }

    public static String getContractIdBySimNum(Connection con, String simNum) {
        String contractId = "0";
        try {
            Statement stat = con.createStatement();
            String strSql = "select CONTRACT_ID from CR_CONTRACT where CONTRACT_MAIN_SIM_NO = '" + simNum + "'";

            //Utility.logger.debug(strSql);
            System.out.println("The query is " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                contractId = res.getString("CONTRACT_ID");
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractId;
    }

    public static final void insertContractStatusRecord(String contractId, String userId, String newStatus) {

        String statusId = null;
        Connection con = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            con = Utility.getConnection();
            stat = con.createStatement();
            String getNewSeqValue = "select seq_cr_contract_status.nextval from dual";
            res = stat.executeQuery(getNewSeqValue);

            if (res.next()) {
                statusId = res.getString(1);
            }

            String insertSql = "insert into Cr_contract_status(contract_status_id, user_id, contract_id, contract_status_type_id, contract_status_date)"
                    + " values(" + statusId + "," + userId + "," + contractId + "," + newStatus + ",sysdate)";
            System.out.println("The insert query isssssssssssssss " + insertSql);

            stat.execute(insertSql);

            updateCrLastStatus(con, contractId, statusId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void updateSimRejectedStatus(String simNumber) {


        Connection con = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            con = Utility.getConnection();
            stat = con.createStatement();
            String updateSql = "update cr_contract_status z  set  z.CONTRACT_STATUS_TYPE_ID =6 where z.CONTRACT_STATUS_ID in "
                    + "(select z.CONTRACT_STATUS_ID  from cr_contract a , cr_contract_status z ,  cr_contract_status_type x "
                    + "where a.CONTRACT_LAST_STATUS_ID = z.CONTRACT_STATUS_ID "
                    + "and z.CONTRACT_STATUS_TYPE_ID =x.CONTRACT_STATUS_TYPE_ID "
                    + "and a.CONTRACT_MAIN_SIM_NO ='" + simNumber + "')";

            System.out.println("The insert query isssssssssssssss " + updateSql);

            stat.executeUpdate(updateSql);

            stat.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 3- update contract take contract id , sheet id , batch id , new status ,
     * String[] of applied user warnings, Vector contract system warning and
     * update this contract in the database by inserting a new status to it and
     * inserting the applicaed user warning+ system warning
     *
     */
    public static Vector getLCSProductMapping(Connection con) {
        Vector lcsVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select CR_PRODUCT_INVENTORY_CODES.product_id,product_name,inventory_item_type from CR_PRODUCT_INVENTORY_CODES,gen_product"
                    + " where  CR_PRODUCT_INVENTORY_CODES.product_id = gen_product.product_id(+) order by product_name asc";

            //Utility.logger.debug(strSql);
            System.out.println("The query is " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                lcsProductMappingModel lcsProductMappingModel = new lcsProductMappingModel(res);
                lcsVec.add(lcsProductMappingModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lcsVec;
    }

    public static lcsProductMappingModel selectLcsProductMapping(Connection con, String productId, String inventoryItemtype) {
        lcsProductMappingModel lcsProductMappingModel = null;
        System.out.println("productId" + productId);
        System.out.println("inventoryitem type = " + inventoryItemtype);
        if (productId != null && productId.compareTo("-1") != 0) {
            try {

                Statement stat = con.createStatement();
                String strSql = "select CR_PRODUCT_INVENTORY_CODES.product_id,product_name,inventory_item_type "
                        + "from CR_PRODUCT_INVENTORY_CODES,gen_product "
                        + "where  CR_PRODUCT_INVENTORY_CODES.product_id = gen_product.product_id "
                        + "and  CR_PRODUCT_INVENTORY_CODES.product_id = '" + productId + "' "
                        + "and  inventory_item_type = '" + inventoryItemtype + "'"
                        + "order by product_name asc";

                System.out.println(strSql);

                ResultSet res = stat.executeQuery(strSql);
                while (res.next()) {
                    lcsProductMappingModel = new lcsProductMappingModel(res);
                }
                stat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            lcsProductMappingModel = new lcsProductMappingModel();
            lcsProductMappingModel.setInventoryItemType(inventoryItemtype);
        }
        return lcsProductMappingModel;
    }
    private static Vector<ProductNameModel> products = null;
    //optimized by hagry 27/2/2010

    public static Vector<ProductNameModel> getAllProducts(Connection con) {
        if (products == null) {
            //synchronized (products)
            //{
            products = new Vector<ProductNameModel>();
            try {
                Statement stat = con.createStatement();
                ResultSet res = stat.executeQuery("select distinct(product_name) ,product_id from gen_product");
                while (res.next()) {
                    products.add(new ProductNameModel(res));
                }
                res.close();
                stat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // }
        }

        return products;
    }

    public static boolean updateLcsProductMapping(Connection con, String productId, String inventoryItemType, String newProductId, String newInventoryItemType) {
        boolean check = false;
        try {

            /*
             * String aql = "select * from CR_PRODUCT_INVENTORY_CODES where
             * INVENTORY_ITEM_TYPE = '"+newInventoryItemType+"' and
             * '"+productId+"'= '"+newProductId+"'"; System.out.println("The
             * check query isssss " + aql); Statement stat2 =
             * con.createStatement(); ResultSet res = stat2.executeQuery(aql);
             * if(res.next()) { check = true; stat2.close(); } else {
             *
             */

            Statement stat = con.createStatement();
            String strSql;
            if (!productId.equals("")) {
                strSql = "update CR_PRODUCT_INVENTORY_CODES "
                        + "SET INVENTORY_ITEM_TYPE = '" + newInventoryItemType + "' "
                        + ",PRODUCT_ID = '" + newProductId + "' "
                        + "WHERE INVENTORY_ITEM_TYPE = '" + inventoryItemType + "'";
            } else {
                strSql = "update CR_PRODUCT_INVENTORY_CODES "
                        + "SET INVENTORY_ITEM_TYPE = '" + newInventoryItemType + "' "
                        + ",PRODUCT_ID =  '" + newProductId + "' "
                        + "WHERE INVENTORY_ITEM_TYPE = '" + inventoryItemType + "'";
            }
            System.out.println("The update query issssssssssssssss " + strSql);
            stat.executeUpdate(strSql);
            stat.close();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static boolean saveLcsProductMapping(Connection con, String productId, String inventoryItemType) {
        boolean check = false;
        try {

            String aql = "select * from CR_PRODUCT_INVENTORY_CODES where INVENTORY_ITEM_TYPE = '" + inventoryItemType + "'";
            Statement stat2 = con.createStatement();
            ResultSet res = stat2.executeQuery(aql);
            if (res.next()) {
                check = true;
                stat2.close();
            } else {
                Statement stat = con.createStatement();
                String strSql = "insert into CR_PRODUCT_INVENTORY_CODES(PRODUCT_ID, INVENTORY_ITEM_TYPE)"
                        + " values('" + productId + "','" + inventoryItemType + "')";
                System.out.println("The insert query issssssssssssssss " + strSql);
                stat.executeUpdate(strSql);
                stat.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static Vector<LcsDcmMappingModel> getLCSDcmMapping(Connection con) {
        String strSql = "SELECT CR_DCM_LCS_NAME.DCM_LCS_NAME_ID,CR_DCM_LCS_NAME.DCM_CODE,gen_dcm.dcm_name,CR_DCM_LCS_NAME.LCS_NAME"
                + " FROM CR_DCM_LCS_NAME,gen_dcm"
                + " where CR_DCM_LCS_NAME.dcm_code = gen_dcm.dcm_code(+)";
        Vector<LcsDcmMappingModel> lcsVec = DBUtil.executeSqlQueryMultiValue(strSql, LcsDcmMappingModel.class, con);
        return lcsVec;
    }

    public static LcsDcmMappingModel selectLcsDcmMapping(Connection con, String dcmLcsNameId) {
        String strSql = "select * "
                + "from CR_DCM_LCS_NAME "
                + " where DCM_LCS_NAME_ID = '" + dcmLcsNameId + "' ";
        LcsDcmMappingModel lcsDcmMappingModel = DBUtil.executeSqlQuerySingleValue(strSql, LcsDcmMappingModel.class, con);
        return lcsDcmMappingModel;
    }

    public static boolean updateLcsDcmMapping(Connection con, String dcmLcsId, String dcmCode, String dcmName, String lcsName, String newDcmCode, String newDcmName, String newLcsName) {
        boolean check = false;
        try {

            String aql = "select * from gen_dcm where DCM_CODE = '" + newDcmCode + "' ";
            System.out.println("The check query isssss " + aql);
            Statement stat2 = con.createStatement();
            ResultSet res = stat2.executeQuery(aql);
            if (res.next()) {
                Statement stat = con.createStatement();
                //String strSql = "update CR_DCM_LCS_NAME "+
                //"SET DCM_CODE = '"+newDcmCode+"' "+
                //",LCS_NAME = '"+newLcsName+"' "+
                //"WHERE DCM_CODE = '"+dcmCode+"' and DCM_LCS_NAME_ID = '"+dcmLcsId+"' and LCS_NAME = '"+lcsName+"'";

                String strSql = "update CR_DCM_LCS_NAME "
                        + "SET DCM_CODE = '" + newDcmCode + "' "
                        + ",LCS_NAME = '" + newLcsName + "' "
                        + " ,DCM_NAME = (select dcm_name from gen_dcm where dcm_code = '" + newDcmCode + "')"
                        + "WHERE DCM_CODE = '" + dcmCode + "' and DCM_LCS_NAME_ID = '" + dcmLcsId + "' and LCS_NAME = '" + lcsName + "'";

                System.out.println("The update query issssssssssssssss " + strSql);
                stat.executeUpdate(strSql);
                stat.close();
            } else {
                check = true;
                stat2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static boolean saveLcsDcmMapping(Connection con, Long dcmLcsId, String dcmCode, String dcmName, String lcsName) {
        boolean check = false;
        try {

            String aql = "select * from gen_dcm where DCM_CODE = '" + dcmCode + "' ";
            Statement stat2 = con.createStatement();
            ResultSet res = stat2.executeQuery(aql);
            if (res.next()) {
                Statement stat = con.createStatement();
                //String strSql =  "insert into CR_DCM_LCS_NAME(DCM_LCS_NAME_ID, DCM_CODE,DCM_NAME,LCS_NAME)"
                //	+" values('"+dcmLcsId+"','"+dcmCode+"','"+dcmName+"','"+lcsName+"')";


                String strSql = "insert into CR_DCM_LCS_NAME(DCM_LCS_NAME_ID, DCM_CODE,DCM_NAME,LCS_NAME)"
                        + " values('" + dcmLcsId + "','" + dcmCode + "',(select dcm_name from gen_dcm where dcm_code = '" + dcmCode + "'),'" + lcsName + "')";


                System.out.println("The insert query issssssssssssssss " + strSql);
                stat.executeUpdate(strSql);
                stat.close();
            } else {
                check = true;
                stat2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static final String updateContract(String contractId, String userId, String sheetId, String batchId, String newStatus, String[] applied_user_warnings, Vector contractSystemWarningModelVector) {
        //debug("updateContract 1 " );
        String statusId = null;

        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String getNewSeqValue = "select seq_cr_contract_status.nextval from dual";
            ResultSet res = stat.executeQuery(getNewSeqValue);
            //debug("getting next id time = " + (System.currentTimeMillis()-startTime));


            res.next();
            //getting the sequeince number
            statusId = res.getString(1);
            //insert the new status 
            String insertSql = "insert into vw_Cr_contract_status(contract_status_id, user_id, contract_id, contract_status_type_id, contract_status_date)"
                    + " values(" + statusId + "," + userId + "," + contractId + "," + newStatus + ",sysdate)";


            stat.execute(insertSql);
            //inserting user warnings 
            //debug("getting insert sql time = " + (System.currentTimeMillis()-startTime));

            //debug(insertSql);


            if (applied_user_warnings != null && (applied_user_warnings.length != 0) && (applied_user_warnings[0] != null) && (applied_user_warnings[0].length() != 0)) {
                for (int i = 0; i < applied_user_warnings.length; i++) {

                    String userWarning = "insert into vw_cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                            + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + applied_user_warnings[i] + ")";
                    //debug(userWarning);

                    stat.execute(userWarning);
                }
            }


            if (contractSystemWarningModelVector != null) {
                //inserting system warning
                for (int j = 0; j < contractSystemWarningModelVector.size(); j++) {
                    ContractWarningModel contractWarning = (ContractWarningModel) contractSystemWarningModelVector.elementAt(j);
                    String systemWarning = "insert into vw_cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                            + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + contractWarning.getContractWarningID() + ")";
                    //debug(systemWarning);
                    stat.execute(systemWarning);
                }
            }
            //con.commit();


            //update the sheet status 

            SheetDao.updateSheetStatus(sheetId, batchId, userId, con);



            res.close();
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Utility.logger.debug (" updating contract time = " + (System.currentTimeMillis() - startTime));
        return statusId;
    }

    public static final Vector<SIMCheckFromLCS> getTmpCrCheckSim(Connection con, String strUserId) {
        Vector<SIMCheckFromLCS> vec = new Vector<SIMCheckFromLCS>();
        try {
            Statement stat = con.createStatement();
            String sql = "select TMP_CR_CHECK_SIM.*,GEN_PRODUCT.PRODUCT_NAME,CR_TRANSACTION_TYPE.TRANSACTION_TYPE_NAME from TMP_CR_CHECK_SIM,GEN_PRODUCT,CR_TRANSACTION_TYPE "
                    + " where TMP_CR_CHECK_SIM.LCS_CONTRACT_TYPE_ID = GEN_PRODUCT.PRODUCT_ID(+) "
                    + " and TMP_CR_CHECK_SIM.TRANSACTION_TYPE_ID = CR_TRANSACTION_TYPE.TRANSACTION_TYPE_ID(+) "
                    + " and TMP_CR_CHECK_SIM.USER_ID = " + strUserId;

            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                SIMCheckFromLCS simCheckFromLCS = new SIMCheckFromLCS(res);
                vec.add(simCheckFromLCS);
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    public static final void deleteTmpCrCheckSim(Connection con, String strUserId) {
        String sql = "delete from TMP_CR_CHECK_SIM where USER_ID = " + strUserId + " ";
        DBUtil.executeSQL(sql, con);

    }

    public static final void checkSIMFromLcs(Connection con, String strUserId) {
        try {
            //Connection lcsConnection = Utility.getLCSConnection();

            Statement stat = con.createStatement();
            ResultSet res = null;
            java.sql.Date issueDate = null;

            String sql = "select * from tmp_cr_check_sim where USER_ID = " + strUserId + " ";

            //Utility.logger.debug(sql);  
            String sql2 = "select LCS_NAME,DCM_NAME from VW_CR_DCM_LCS_NAME ";
            HashMap dcmLcsNameHashMap = Utility.getMap(con, sql2);

            String sql5 = "select INVENTORY_ITEM_ID,LCS_PRODUCT_TYPE from SDS_INVENTORY_SYSTEM_ITEMS ";
            HashMap lcsProductTypeHashMap = Utility.getMap(con, sql5);
            //HashMap lcsProductTypeHashMap = Utility.getMap(con,sql5);

            String sql4 = " select INVENTORY_ITEM_TYPE,product_id from VW_CR_PRODUCT_INVENTORY_CODES ";
            HashMap productInventoryHashMap = Utility.getMap(con, sql4);

            String sql9 = "select transaction_type_name,transaction_type_id from cr_transaction_type ";
            HashMap transactionTypeHashMap = Utility.getMap(con, sql9);

            String sqlGetProductCategory = "select product_id,product_category_id from gen_product";
            HashMap productsCategoryTable = Utility.getMap(con, sqlGetProductCategory);


            /*
             * Statement stat9 = con.createStatement(); ResultSet res9 =
             * stat9.executeQuery(sql9); while(res9.next()) { String
             * x_transactionTypeId = res9.getString("transaction_type_id");
             * String x_transactionTypeName =
             * res9.getString("transaction_type_name");
             * transactionTypeHashMap.put(x_transactionTypeName,x_transactionTypeId);
             * } res9.close(); res9.close();
             */


            res = stat.executeQuery(sql);
            int countContracts = 0;
            long stime = System.currentTimeMillis();
            long totalTime = stime;
            while (res.next()) {
                //String contractId = res.getString("contract_id")  ;
                String contractMainSimNoY = res.getString("main_sim_no");
                //Utility.logger.debug(contractMainSimNoY);
                String contractMainSimNoX = "";
                //contractMainSimNoX = "0"+contractMainSimNoY.substring(0,19);
                contractMainSimNoX = contractMainSimNoY;

                //Utility.logger.debug(contractMainSimNoX);

                countContracts++;

                if (countContracts % 10000 == 0) {
                    Utility.logger.debug("Done So Far = " + countContracts);
                    Utility.logger.debug("took = " + (System.currentTimeMillis() - stime) / 1000);
                    Utility.logger.debug("total time took = " + (System.currentTimeMillis() - totalTime) / 1000);
                    stime = System.currentTimeMillis();
                }

                //boolean flagGetLcsInfo = false;

                String contractMainSIMNo = contractMainSimNoX;
                if (true) {

                    /////////////////////////////////////////////////
                    //does this work as utility or u change the utility


                    //	Statement statLCS = lcsConnection.createStatement();

                    //	String lcsSql ="{call XXMBN.XX_SERIAL_NUM_ISSUED_DATE (?,?,?,?,?,?,?,?)}";

//					CallableStatement cs =  lcsConnection.prepareCall(lcsSql);
                    //CallableStatement cs =  con.prepareCall(lcsSql);

                    String mainSimNo_LCS_Format = "";

                    if (contractMainSIMNo.length() >= 19) {
                        mainSimNo_LCS_Format = contractMainSIMNo.substring(1, 19);
                    } else {
                        mainSimNo_LCS_Format = contractMainSIMNo.substring(1);
                    }

//                    cs.setString(1, mainSimNo_LCS_Format);
//                    cs.registerOutParameter(2, Types.INTEGER);
//                    
//                    cs.registerOutParameter(3, Types.INTEGER);
//                    cs.registerOutParameter(4, Types.INTEGER);
//                    cs.registerOutParameter(5, Types.VARCHAR);
//                    cs.registerOutParameter(6, Types.VARCHAR);
//                    cs.registerOutParameter(7, Types.VARCHAR);
//                    cs.registerOutParameter(8, Types.DATE);
//                    try {
//                        cs.execute();
//                    } catch (Exception e) {
//                        System.out.println("Serial Number Procedure Cannot execute");
//                    }


                    LcsModel lcsModel = LcsDao.getLcsData(mainSimNo_LCS_Format);


//                    //      String transactionId = cs.getString(2);
//                    String inventoryItemId = cs.getString(3);
//                    //      String sourceTypeId = cs.getString(4);
//                    String sourceTypeName = cs.getString(5);
//                    String currentStatus = cs.getString(6);
//                    String transactionTypeName = cs.getString(7);
//                    issueDate = cs.getDate(8);
//
//                    //     cs.close();



                    String inventoryItemId = lcsModel.getInventoryItemId().toString();
                    String sourceTypeName = lcsModel.getFromLocation();
                    String currentStatus = lcsModel.getStatus();
                    String transactionTypeName = lcsModel.getTransacitonName();
                    issueDate = lcsModel.getLastTransactionDate();






                    ///////////////////////////// get lcs dcm id///////////////////////


                    //lets load all that table in hashmap in the begining
                    String dcmNameInLCSMapping = null;
                    //         String dcmIDInLCS =null; 

                    if (dcmLcsNameHashMap.containsKey(sourceTypeName)) {
                        dcmNameInLCSMapping = (String) dcmLcsNameHashMap.get(sourceTypeName);
                    }


                    String sqlGetDCMID = "select dcm_code from vw_gen_dcm where dcm_name='" + dcmNameInLCSMapping + "' and dcm_level_id = 1 ";
                    //Utility.logger.debug(sqlGetDCMID);
                    Statement statDCM = con.createStatement();
                    ResultSet resDCM = statDCM.executeQuery(sqlGetDCMID);

                    String dcmId = "-1";
                    if (resDCM.next()) {
                        dcmId = resDCM.getString("dcm_code");
                    }

                    if (dcmId.compareTo("-1") == 0 && sourceTypeName != null) {
                        if (sourceTypeName.compareTo("null") != 0 && sourceTypeName.compareTo("-1") != 0) {
                            //Utility.logger.debug("Contract Id : "+contractId +"DCM from Lcs : "+sourceTypeName);
                            //Utility.logger.debug("DCM from Mapping Table : "+dcmNameInLCSMapping);
                        }
                    }
                    resDCM.close();
                    statDCM.close();


                    ///////////////////////////// get lcs product type///////////////////////

                    //lets load all this table in hashmap in the begining
                    String productId = "-1";
                    String lcsType = "-1";
                    if (lcsProductTypeHashMap.containsKey(inventoryItemId)) {
                        //sql = "select * from SDS_INVENTORY_SYSTEM_ITEMS  where INVENTORY_ITEM_ID = " + inventoryItemId ;
                        //ResultSet res3 = statLCS.executeQuery(sql);
                        //if (res3.next())
                        //{
                        //String lcsType = res3.getString("LCS_PRODUCT_TYPE");
                        lcsType = (String) lcsProductTypeHashMap.get(inventoryItemId);

                        if (productInventoryHashMap.containsKey(lcsType)) {

                            //sql = " select * from VW_CR_PRODUCT_INVENTORY_CODES where INVENTORY_ITEM_TYPE = '"+lcsType+"'";
                            //Statement stat2 = con.createStatement();
                            //ResultSet typeMappingres = stat2.executeQuery(sql);
                            //if (typeMappingres.next())
                            //{
                            //   productId = typeMappingres.getString("product_id");              
                            productId = (String) productInventoryHashMap.get(lcsType);
                        }
                        //typeMappingres.close();
                        //stat2.close();
                    }
                    //res3.close();

                    /////////////////////// get transaction type id//////////////////////////////////////

                    String transactionTypeId = "-1";

                    if (transactionTypeName.compareTo("-1") != 0 && transactionTypeName != null) {
                        if (transactionTypeHashMap.containsKey(transactionTypeName)) {
                            transactionTypeId = (String) transactionTypeHashMap.get(transactionTypeName);
                        } else {
                            Long ltransactionTypeId = Utility.getSequenceNextVal(con, "SEQ_CR_TRANSACTION_TYPE_ID");
                            String sql22 = "insert into cr_transaction_type(transaction_type_id,transaction_type_name) values(" + ltransactionTypeId + ",'" + transactionTypeName + "')";
                            Statement stat22 = con.createStatement();
                            stat22.executeUpdate(sql22);
                            stat22.close();

                            transactionTypeHashMap.put(transactionTypeName, ltransactionTypeId + "");
                        }
                    }



                    //get activation date //////////////////////////// 

                    // it is better that we use the contractlcstype id instead of the contract type id in this query 

                    String productCategoryId = null;
                    if (productsCategoryTable.containsKey(productId)) {
                        productCategoryId = (String) productsCategoryTable.get(productId);
                    }
                    //    System.out.println("************* "+"GET_SIM_ACT_DATE_BY_CATEGORY");
                    String sqlActivation = "select GET_SIM_ACT_DATE_BY_CATEGORY('" + contractMainSIMNo + "'," + productCategoryId + ") as activation_date from dual";


                    Statement actStat = con.createStatement();
                    ResultSet actRes = actStat.executeQuery(sqlActivation);

                    String strActivationDate = null;
                    if (actRes.next()) {
                        strActivationDate = actRes.getString("activation_date");
                    }
                    actRes.close();
                    actStat.close();


                    //////////////////////////////////////////////////////////////////////////////////
                    // this will update contract lcs info
                    if (sourceTypeName != null) {
                        sourceTypeName = sourceTypeName.replaceAll("'", "''");
                    }

                    String sqlUpdate = "";
                    if (issueDate != null) {
                        sqlUpdate = "update tmp_cr_check_sim set LCS_CURRENT_STATUS = '" + currentStatus + "',DCM_NAME_FROM_LCS = '" + sourceTypeName + "',CONTRACT_TYPE_FROM_LCS='" + lcsType + "',lcs_dcm_id='" + dcmId + "',LCS_CONTRACT_TYPE_Id='" + productId + "',transaction_type_id = '" + transactionTypeId + "',lcs_issue_date = to_date('" + issueDate + "','yyyy-mm-dd')";
                        System.out.println("The update query issssss " + sqlUpdate);
                        if (strActivationDate != null) {
                            if (strActivationDate.compareTo("") != 0) {
                                sqlUpdate += " ,LCS_INIT_DATE = to_date('" + strActivationDate.substring(0, 10) + "','yyyy-mm-dd') ";
                            }
                        }
                    } else {
                        sqlUpdate = "update tmp_cr_check_sim set LCS_CURRENT_STATUS = '" + currentStatus + "',DCM_NAME_FROM_LCS = '" + sourceTypeName + "',CONTRACT_TYPE_FROM_LCS='" + lcsType + "',lcs_dcm_id='" + dcmId + "',LCS_CONTRACT_TYPE_Id='" + productId + "',transaction_type_id = '" + transactionTypeId + "'";
                        System.out.println("The update query issssss " + sqlUpdate);
                        if (strActivationDate != null) {
                            if (strActivationDate.compareTo("") != 0) {
                                sqlUpdate += " ,LCS_INIT_DATE = to_date('" + strActivationDate.substring(0, 10) + "','yyyy-mm-dd') ";
                            }
                        }
                    }


                    sqlUpdate += " where main_sim_no ='" + contractMainSimNoY + "' ";

                    Statement stat3 = con.createStatement();
                    stat3.execute(sqlUpdate);
                    stat3.close();
//                    statLCS.close();
                }
            }
            res.close();
            stat.close();
//            lcsConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 3- update contract take contract id , sheet id , batch id , new status ,
     * String[] of applied user warnings, Vector contract system warning and
     * update this contract in the database by inserting a new status to it and
     * inserting the applicaed user warning+ system warning
     *
     */
    public static final String updateContract(String contractId, String userId, String sheetId, String batchId, String newStatus, String[] applied_user_warnings, String[] system_warnings) {
        String statusId = null;
        Connection con = null;
        try {
            con = Utility.getConnection();
            Statement stat = con.createStatement();
            String getNewSeqValue = "select seq_cr_contract_status.nextval from dual";
            ResultSet res = stat.executeQuery(getNewSeqValue);
            res.next();
            //getting the sequeince number
            statusId = res.getString(1);
            //insert the new status 
            String insertSql = "insert into vw_Cr_contract_status(contract_status_id, user_id, contract_id, contract_status_type_id, contract_status_date)"
                    + " values(" + statusId + "," + userId + "," + contractId + "," + newStatus + ",sysdate)";
            //debug(insertSql);
            stat.execute(insertSql);
            //inserting user warnings 
            for (int i = 0; i < applied_user_warnings.length; i++) {
                String userWarning = "insert into vw_cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                        + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + applied_user_warnings[i] + ")";
                //debug(userWarning);
                stat.execute(userWarning);
            }
            //inserting system warning
            for (int j = 0; j < system_warnings.length; j++) {
                String systemWarning = "insert into vw_cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                        + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + system_warnings[j] + ")";
                //debug(systemWarning);
                stat.execute(systemWarning);
            }
            //update the sheet status 
            SheetDao.updateSheetStatus(sheetId, batchId, userId, con);
            stat.close();
            //  Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //if (stat!=null)try{stat.close();}catch(Exception e){}
            if (con != null) {
                try {
                    Utility.closeConnection(con);
                } catch (Exception e) {
                }
            }
        }
        return statusId;
    }

    /*
     * 4- insert a new status to the contract specified by the contract id
     */
    public static final String updateContract(String contractId, String newStatus, String userId, String sheetId) {
        String statusId = "";
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String getNewSeqValue = "select seq_cr_contract_status.nextval from dual";

            ResultSet res = stat.executeQuery(getNewSeqValue);
            res.next();
            statusId = res.getString(1);

            String insertSql = "insert into vw_Cr_contract_status(contract_status_id, user_id, contract_id, contract_status_type_id, contract_status_date)"
                    + " values(" + statusId + "," + userId + "," + contractId + "," + newStatus + ",sysdate)";
            //debug(insertSql);
            stat.execute(insertSql);
            //SheetDao.updateSheetStatus(sheetId,batchId);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusId;
    }

    /*
     * 5- check if any field of the contract information is missing
     *
     */
    public static final boolean checkNullValuesExist(ResultSet res) {
        ContractModel contractModel = new ContractModel(res);
        return contractModel.isExistMissingInfo();
    }

    public static Vector getContractWarnings(Connection con, String contractLastStatusId) {
        String strQuerySystemWarnings = " select CONTRACT_WARNING_TYPE_ID,CONTRACT_WARNING_TYPE_NAME,CONTRACT_WARNING_TYPE_DESC,PHASE_ID,WARNING_TYPE_ID,WARNING_TYPE_NAME,WARNING_STATUS_ID,WARNING_STATUS_NAME,WARNING_SUGGESTED_STATUS_ID,WARNING_SUGGESTED_STATUS_NAME  from VW_CR_CONTRACT_WARNING where VW_CR_CONTRACT_WARNING.CONTRACT_STATUS_ID = " + contractLastStatusId
                + " and VW_CR_CONTRACT_WARNING.WARNING_TYPE_ID <> 1";

        Statement stat = null;
        ResultSet resWarning = null;
        Vector v = new Vector();

        try {
            stat = con.createStatement();
            resWarning = stat.executeQuery(strQuerySystemWarnings);
            while (resWarning.next()) {
                v.add(new ContractWarningModel(resWarning));
            }

        } catch (Exception e) {
            System.out.println("strQuerySystemWarnings = " + strQuerySystemWarnings);
            e.printStackTrace();
        } finally {
            if (resWarning != null) {
                try {
                    resWarning.close();
                } catch (Exception e) {
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }

        }

        return v;

    }

    public static HashMap getTransactionTypes(Connection con) {
        HashMap transactionTypeHashMap = new HashMap();
        String sql = "select transaction_type_id,transaction_type_name from cr_transaction_type ";

        Statement stat = null;
        ResultSet res = null;

        try {
            stat = con.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {
                String name = res.getString("transaction_type_name");
                int id = res.getInt("transaction_type_id");
                String idS = null;
                if (!res.wasNull()) {
                    idS = id + "";
                    transactionTypeHashMap.put(name, idS);
                } else {
                    System.out.println("****************");
                    System.out.println("Transaction id is null");
                    System.out.println("transaction name=" + name);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }

        }

        return transactionTypeHashMap;
    }

    public static String getContractCheckedStatuc(Connection con, String contractId) {

        String sql = "select CONTRACT_SYS_WARNING_CHECKED from VW_CR_CONTRACT where CONTRACT_ID = " + contractId + " ";

        Statement stat = null;
        ResultSet res = null;
        String strContractChecked = null;

        try {
            stat = con.createStatement();
            res = stat.executeQuery(sql);
            if (res.next()) {
                strContractChecked = res.getString("CONTRACT_SYS_WARNING_CHECKED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }

        }
        return strContractChecked;


    }

    public static void addProductInventoryCode(Connection con, String lcsType) {

        String sql = "insert into CR_PRODUCT_INVENTORY_CODES  (INVENTORY_ITEM_TYPE,product_id) values ('" + lcsType + "','-1')";
        DBUtil.executeSQL(sql, con);
    }

    public static void AddDCMLCSMapping(Connection con, String sourceTypeName) {
        String sql = "insert into CR_DCM_LCS_NAME  (dcm_lcs_name_id, LCS_NAME,dcm_name,dcm_code) values (SEQ_DCM_LCS.nextval,'" + sourceTypeName + "','-1','-1')";
        DBUtil.executeSQL(sql, con);
    }

    /*
     * 6- generete the system warnings of this contract id upon verifying with
     * external database which are the inventory , post paid , pre paid
     * databases
     */
    public static final Vector validateContractInContractVerification(Connection con, String contractId, String automaticFlag, String contractLastStatusId) {
        //Utility.logger.debug("**************Inside the validateContractInContractVerification*******"+contractId);
        Vector v = new Vector();
        try {
            Statement stat = con.createStatement();
            Statement warningStat = con.createStatement();
            ResultSet resWarning = null;

            if (automaticFlag != null && automaticFlag.compareTo("1") == 0) {
                v = getContractWarnings(con, contractLastStatusId);
            } else {
                HashMap transactionTypeHashMap = ContractDao.getTransactionTypes(con);
                HashMap allContractSystemWarningTypes = ContractDao.getAllContractSystemWarningTypes(con);

                String strContractChecked = getContractCheckedStatuc(con, contractId);

                if (strContractChecked == null || strContractChecked.compareTo("0") == 0 || strContractChecked.compareTo("") == 0 || strContractChecked.compareTo("null") == 0) {


                    String sql = "select sheet_id, product_category_name ,contract_main_sim_no, contract.contract_id, contract_type_id,contract_status_Type_name"
                            + " , contracT_dial_no, contract_customer_name , contract_area, contract_address, contract_customer_id, "
                            + " contract_customer_id_type , customer_id_type_name, contract_home_phone "
                            + " from   VW_CR_CUSTOMER_ID_TYPE , vw_gen_product, vw_CR_contract_status_type ,  vw_CR_contract contract , vw_CR_contract_status where  "
                            + "  contract.contract_id = " + contractId
                            + " and contract.contract_last_status_id = vw_CR_contract_status.contract_status_id "
                            + " and vw_CR_contract_status_type.contract_status_type_id = vw_CR_contract_status.contract_status_type_id"
                            + " and contract.CONTRACT_TYPE_ID = vw_gen_product.PRODUCT_ID "
                            + " and contract.contract_type_id = vw_gen_product.product_id "
                            + " and CONTRACT.CONTRACT_CUSTOMER_ID_TYPE =   VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) ";




                    ResultSet res = stat.executeQuery(sql);
                    res.next();


                    //ContractModel contractModel = new ContractModel(res); //commented this code for optimization               
                    String mainSimNum = res.getString(ContractModel.MAIN_SIM_NO);
                    int typeIdAsInt = res.getInt(ContractModel.CONTRACT_TYPE_ID);
                    String typeId = null;
                    if (!res.wasNull()) {
                        typeId = typeIdAsInt + "";
                    }

                    String statusTypeName = res.getString(ContractModel.CONTRACT_STATUS_TYPE_NAME);
                    String phoneDialNum = res.getString(ContractModel.CONTRACT_DIAL_NO);
                    String customerName = res.getString(ContractModel.CONTRACT_CUSTOMER_NAME);
                    String address = res.getString(ContractModel.CONTRACT_ADDRESS);
                    String area = res.getString(ContractModel.CONTRACT_AREA);
                    String customerIdNum = res.getString(ContractModel.CONTRACT_CUSTOMER_ID);
                    String customerIdType = res.getString(ContractModel.CONTRACT_CUSTOMER_ID_TYPE);
                    String customerIdTypeName = res.getString(ContractModel.CONTRACT_CUSTOMER_ID_TYPE_NAME);
                    String homePhone = res.getString(ContractModel.CONTRACT_HOME_PHONE);
                    String sheetId = res.getString("sheet_id");
                    String contractCategoryType = res.getString("product_category_name");

                    res.close();

                    boolean nullFieldsExist;
                    if (address == null || customerIdNum == null || customerIdType == null
                            || customerIdTypeName == null || customerName == null || phoneDialNum == null
                            || mainSimNum == null || statusTypeName == null || typeId == null) {
                        nullFieldsExist = true;
                    } else {
                        nullFieldsExist = false;
                    }

                    // boolean nullFieldsExist = contractModel.isExistMissingInfo();

                    if (nullFieldsExist) {
                        v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_EMPTY_FIELDS));
                    }

                    String contractMainSimNo = mainSimNum;


                    String contractType = typeId;

                    sql = " select * from vw_gen_dcm_payment_level,vw_CR_sheet, vw_gen_dcm where  "
                            + "  sheet_id = " + sheetId
                            + " and   vw_CR_sheet.SHEET_DISTRIBUTER_CODE = vw_gen_dcm.DCM_CODE "
                            + " and vw_gen_dcm.DCM_PAYMENT_LEVEL_ID  = vw_gen_dcm_payment_level.dcm_payment_level_id (+)";


                    ResultSet res2 = stat.executeQuery(sql);

                    //Utility.logger.debug(sql);
                    String contractDCMName = "";
                    String sheetTypeName = "";
                    String dcmPaymentLevelName = "";
                    if (res2.next()) {
                        contractDCMName = res2.getString("dcm_name");
                        sheetTypeName = res2.getString("sheet_type_name");
                        dcmPaymentLevelName = res2.getString("dcm_payment_level_name");
                    }
                    res2.close();
                    //format for the main sim no in the services 
                    String mainSimNo_Services_Format = "";
                    String mainSimNo_PrePaid_Format = "";
                    String mainSimNo_PostPaid_Format = "";
                    String mainSimNo_LCS_Format = "";
                    boolean isBundel = false;
                    if (contractMainSimNo.length() == 20) {
                        //format for the main sim no in the services 
                        mainSimNo_Services_Format = contractMainSimNo.substring(1, 20) + "F";

                        //format for the sim no in the prepaid
                        mainSimNo_PrePaid_Format = contractMainSimNo.substring(1, 20);

                        //format for the sim no in the post paid
                        mainSimNo_PostPaid_Format = contractMainSimNo.substring(1, 20) + "F";

                        //format mainsim no for the lcs
                        mainSimNo_LCS_Format = contractMainSimNo.substring(1, 19);

                    } else {
                        isBundel = true;

                        //format for the main sim no in the services 
                        mainSimNo_Services_Format = "8" + contractMainSimNo.substring(8, 24);
                        //format for the sim no in the prepaid
                        mainSimNo_PrePaid_Format = "8" + contractMainSimNo.substring(8, 24);
                        //format for the sim no in the post paid
                        mainSimNo_PostPaid_Format = "8" + contractMainSimNo.substring(8, 24);
                        //format mainsim no for the lcs
                        mainSimNo_LCS_Format = contractMainSimNo;

                    }


                    //String mainSimNo_18_char = contractMainSimNo.substring(1,19);
                    //lcs format is only 18 character just as the normal format we look into post paid , services, prepaid


                    //lcs validation


                    //   Connection conToLCS = Utility.getLCSConnection();
//                    Statement statLCS = null;
                    try {
                        //      statLCS = conToLCS.createStatement();
                    } catch (Exception e) {

                        v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_LCS_CONNECTION_FAILED));

                        Statement statInsertContractSysWarning = con.createStatement();
                        for (int k = 0; k < v.size(); k++) {
                            ContractWarningModel contractWarningModel = (ContractWarningModel) v.get(k);
                            String strWarningId = contractWarningModel.getContractWarningID();
                            String sqlInsertContractSysWarning = "insert into CR_CONTRACT_SYSTEM_WARNING(CONTRACT_SYSTEM_WARNING_ID,CONTRACT_ID,CONTRACT_WARNING_ID)"
                                    + " values(SEQ_CR_CONTRACT_SYS_WARNING_ID.nextval," + contractId + "," + strWarningId + ")";
                            //Utility.logger.debug("sss"+sqlInsertContractSysWarning);                 
                            statInsertContractSysWarning.executeUpdate(sqlInsertContractSysWarning);
                        }
                        statInsertContractSysWarning.close();

                        Statement statUpdateContract = con.createStatement();
                        String sqlUpdateContract = "update CR_CONTRACT set CONTRACT_SYS_WARNING_CHECKED = 1 where CONTRACT_ID = " + contractId + " ";
                        //Utility.logger.debug("kkk"+sqlUpdateContract);
                        statUpdateContract.executeUpdate(sqlUpdateContract);
                        statUpdateContract.close();
                    }

                    //Utility.logger.debug("time taken to establish a link to lcs database " + ( System.currentTimeMillis() - startTime ));


                    String ZdcmId = "-1";
                    String ZtransactionTypeId = "-1";
                    String ZproductId = "-1";
                    java.sql.Date issueDate = null;
                    try {


                        //           sql = "{call XXMBN.XX_SERIAL_NUM_ISSUED_DATE (?,?,?,?,?,?,?,?)}";

                        //    CallableStatement cs = conToLCS.prepareCall(sql);

//                        cs.setString(1, mainSimNo_LCS_Format);
//
//                        cs.registerOutParameter(2, Types.INTEGER);
//                        cs.registerOutParameter(3, Types.INTEGER);
//                        cs.registerOutParameter(4, Types.INTEGER);
//                        cs.registerOutParameter(5, Types.VARCHAR);
//                        cs.registerOutParameter(6, Types.VARCHAR);
//                        cs.registerOutParameter(7, Types.VARCHAR);
//                        cs.registerOutParameter(8, Types.DATE);
//
//                        cs.execute();
//
//                        String transactionId = cs.getString(2);
//                        String inventoryItemId = cs.getString(3);
//                        //      String sourceTypeId = cs.getString(4);
//                        String sourceTypeName = cs.getString(5);
//                        String currentStatus = cs.getString(6);
//                        String transactionTypeName = cs.getString(7);
//                        issueDate = cs.getDate(8);
//
//                        cs.close();

                        LcsModel lcsModel = LcsDao.getLcsData(mainSimNo_LCS_Format);


                        String transactionId = lcsModel.getLastTransactionId().toString();
                        String inventoryItemId = lcsModel.getInventoryItemId().toString();
                        //      String sourceTypeId = cs.getString(4);
                        String sourceTypeName = lcsModel.getFromLocation();
                        String currentStatus = lcsModel.getStatus();
                        String transactionTypeName = lcsModel.getTransacitonName();
                        issueDate = lcsModel.getLastTransactionDate();

                        if (transactionId.compareTo("-1") != 0) {
                            if (transactionTypeName.compareTo("-1") != 0 && transactionTypeName != null) {
                                if (transactionTypeHashMap.containsKey(transactionTypeName)) {
                                    ZtransactionTypeId = (String) transactionTypeHashMap.get(transactionTypeName);
                                } else {
                                    Long ltransactionTypeId = Utility.getSequenceNextVal(con, "SEQ_CR_TRANSACTION_TYPE_ID");
                                    String sql22 = "insert into cr_transaction_type(transaction_type_id,transaction_type_name) values(" + ltransactionTypeId + ",'" + transactionTypeName + "')";
                                    Statement stat22 = con.createStatement();
                                    stat22.executeUpdate(sql22);
                                    stat22.close();

                                    ZtransactionTypeId = ltransactionTypeId + "";
                                    transactionTypeHashMap.put(transactionTypeName, ltransactionTypeId + "");
                                }
                            }


                            //checking if the last status of this contract was issued 
                            if (currentStatus.compareTo(ContractRegistrationInterfaceKey.LCS_ISSUED_CODE) == 0) {
                                //do nothing
                            } else {
                                v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_SOLD));
                            }

                            // String lastTransactionId = transactionId ;

                            // sql = "select * from VW_CR_DCM_LCS_NAME where LCS_NAME ='"+ sourceTypeName+"'";
                            sql = "select dcm_lcs_name_id, gen_dcm.dcm_id,gen_dcm.dcm_code from cr_dcm_lcs_name,gen_dcm where cr_Dcm_lcs_name.dcm_code = gen_dcm.dcm_code and lcs_name='" + sourceTypeName + "'";

                            ResultSet resNameDCMMapping = stat.executeQuery(sql);
                            if (resNameDCMMapping.next()) {
                                ZdcmId = resNameDCMMapping.getString("dcm_id");
                            } else {
                                //here should be the insertion in the table 
                                //we also should have the lcs name as unique 
                                //hagry
                                AddDCMLCSMapping(con, sourceTypeName);
                            }

                            resNameDCMMapping.close();
                            //not the same dcm 
                            //if ( contractDCMName.compareTo(dcmNameInLCSMapping)!=0)
                            //{

                            if (sheetTypeName.compareTo("NORMAL") == 0) {
                                //normal batchs different dcm will not generate a system warning 
                                //reference to the changes done in may 2006                
                            } else {

                                if (dcmPaymentLevelName != null && dcmPaymentLevelName.compareTo("Authorized Super Dealers") == 0) {
                                    //authorized super dealers will not produce a system warning for them
                                    //when different dcm in batches of types other than normal
                                } else {
                                    v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_SOLD_TO_DIFFERENT_DCM));
                                }

                            }

                            sql = "select * from SDS_INVENTORY_SYSTEM_ITEMS  where INVENTORY_ITEM_ID = " + inventoryItemId;
                            ResultSet res3 = stat.executeQuery(sql);
                            if (res3.next()) {
                                String lcsType = res3.getString("LCS_PRODUCT_TYPE");

                                sql = " select * from VW_CR_PRODUCT_INVENTORY_CODES where INVENTORY_ITEM_TYPE = '" + lcsType + "'";

                                //     Utility.logger.debug(sql);
                                ResultSet typeMappingres = stat.executeQuery(sql);

                                if (typeMappingres.next()) {
                                    String productId = typeMappingres.getString("product_id");
                                    ZproductId = productId;
                                    if (productId.compareTo(contractType) != 0) {
                                        v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_EXIST_WITH_A_DIFFERENT_TYPE_IN_LCS));
                                    }
                                } else {
                                    //this is the place to add the product to the system
                                    addProductInventoryCode(con, lcsType);
                                }
                                typeMappingres.close();
                            } else {
                            }

                            res3.close();
                        } else //case of not finding the serial in the lcs 
                        {
                            v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_EXIST_IN_INVENTORY));
                        }

                    } catch (Exception e) {
                        v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_EXIST_IN_INVENTORY));


                        if (resWarning != null) {
                            try {
                                resWarning.close();
                            } catch (Exception ee) {
                            }
                        }
                        if (warningStat != null) {
                            try {
                                warningStat.close();
                            } catch (Exception ee) {
                            }
                        }
                        if (stat != null) {
                            try {
                                stat.close();
                            } catch (Exception ee) {
                            }
                        }

                        e.printStackTrace();
                        return null;

                    }


                    //checking upon the contract type

                    //service contracts check 


                    Statement statContractActivation = con.createStatement();
                    String contractActivationDate = "";

                    if (contractCategoryType.compareTo("SERVICE") == 0) {
                        sql = "select SIM_SWAP_DIAL,SIM_SWAP_ACTIVATION_DATE from VW_CR_SIM_SWAP where  NEW_SIM_NO  ='" + mainSimNo_Services_Format + "'";

                        res = stat.executeQuery(sql);
                        if (res.next()) {
                            //do nothing 
                            String dialNum = res.getString("SIM_SWAP_DIAL");
                            contractActivationDate = res.getString("SIM_SWAP_ACTIVATION_DATE");
                            if (phoneDialNum.compareTo(dialNum) != 0) {
                                v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_SIM_SWAP_HAS_DIFFERENT_DIAL_NO));
                            }

                        } else {
                            v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_EXIST_IN_BSCS));
                        }

                        res.close();
                    } //prepaid checking 
                    else if (contractCategoryType.compareTo("PREPAID") == 0) {
                        sql = "select GET_SIM_ACT_DATE_BY_CATEGORY('" + mainSimNum + "',17 ) as  pre_paid_activation_date  from dual ";
                        res = stat.executeQuery(sql);

                        if (res.next()) {
                            /*
                             * checking the activation date
                             */
                            contractActivationDate = res.getString("pre_paid_activation_date");
                            if (contractActivationDate == null || contractActivationDate.length() == 0) {
                                v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_INITIALIZED_PREPAID));
                            }

                            res.close();
                        } else if (contractCategoryType.compareTo("POSTPAID") == 0) {
                            //main sim no 18 is a variable that has the characters from 1 to 19 in the origianl sim no 
                            //we use it in the query and we trim the field new sim no by usign substr since there is extra padding character in that view


                            if (isBundel) {
                                //sql ="select substr(POST_PAID_DIAL, 3,7)post_paid_dial,main_sim_no,post_paid_activation_date,cr_post_paid_service_package.service_package, cr_post_paid_rate_plan.RATE_PLAN from cr_post_paid,cr_post_paid_service_package,cr_post_paid_rate_plan   where cr_post_paid.SERVICE_PACKAGE = cr_post_paid_service_package.SERVICE_PACKAGE_DESC and cr_post_paid.RATE_PLAN = cr_post_paid_rate_plan.RATE_PLAN_DESC and main_sim_no ='"+mainSimNo_PostPaid_Format+"'";

                                sql = "SELECT * FROM DEM_LOGISTICS_POSTPAID WHERE SUBSTR(SIMNO,2,17) =  SUBSTR('" + mainSimNum + "' ,8,17) order by activation_date desc";

                            } else {
                                //sql ="select substr(POST_PAID_DIAL, 3,7)post_paid_dial,main_sim_no,post_paid_activation_date,cr_post_paid_service_package.service_package, cr_post_paid_rate_plan.RATE_PLAN from cr_post_paid,cr_post_paid_service_package,cr_post_paid_rate_plan  where cr_post_paid.SERVICE_PACKAGE = cr_post_paid_service_package.SERVICE_PACKAGE_DESC and cr_post_paid.RATE_PLAN = cr_post_paid_rate_plan.RATE_PLAN_DESC and main_sim_no like '"+mainSimNo_PostPaid_Format+"%'";
                                sql = "SELECT * FROM DEM_LOGISTICS_POSTPAID WHERE SIMNO =  CONCAT(SUBSTR('" + mainSimNum + "',2,19),'F') order by activation_date desc";
                            }

                            //sql= "select GET_SIM_ACT_DATE_BY_CATEGORY('"+mainSimNum+"',18 ) as  pre_paid_activation_date  from dual ";

                            //debug(sql);
                            res = stat.executeQuery(sql);
                            if (res.next()) {
                                contractActivationDate = res.getString("activation_date");

                                String servicePackage = res.getString("service_package");
                                String ratePlan = res.getString("rate_plan");

                                Statement s2 = con.createStatement();
                                s2.execute("delete from CR_CONTRACT_POST_PAID_DATA where contract_id = '" + contractId + "'");
                                s2.execute("insert into CR_CONTRACT_POST_PAID_DATA  (contract_id,service_package,rate_plan) values('" + contractId + "'," + servicePackage + "," + ratePlan + ")");
                                s2.close();

                                if (contractActivationDate == null || contractActivationDate.compareTo("") == 0) {
                                    v.add((ContractWarningModel) allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_ACTIVATED_POSTPAID));
                                }

                                /*
                                 * checking the dial number in the post paid
                                 * database
                                 */
                                String dialNum = res.getString("POST_PAID_DIAL");
                                if (phoneDialNum.compareTo(dialNum) != 0) {
                                    Object o = allContractSystemWarningTypes.get(CONTRACT_POST_PAID_HAS_DIFFERENT_DIAL_NO);
                                    if (o != null) {
                                        v.add((ContractWarningModel) o);
                                    } else {
                                        System.out.println("*************");
                                        System.out.println("warning not found =" + CONTRACT_POST_PAID_HAS_DIFFERENT_DIAL_NO);
                                    }
                                }
                            } else {
                                Object o = allContractSystemWarningTypes.get(CONTRACT_POST_PAID_HAS_DIFFERENT_DIAL_NO);
                                if (o != null) {
                                    v.add((ContractWarningModel) o);
                                } else {
                                    System.out.println("*************");
                                    System.out.println("warning not found =" + CONTRACT_WARNING_NOT_EXIST_IN_BSCS);
                                }
                            }
                            //v.add((ContractWarningModel)allContractSystemWarningTypes.get(CONTRACT_WARNING_NOT_EXIST_IN_BSCS));
                        }

                        res.close();
                    }

                    ////////////////////update contract info from LCS and activation date/////////////////////

                    String sqlUpdateContractLCSAndActivation = "update CR_contract set lcs_dcm_id='" + ZdcmId + "',LCS_CONTRACT_TYPE_Id='" + ZproductId + "',TRANSACTION_TYPE_ID='" + ZtransactionTypeId + "'";

                    if (issueDate != null) {
                        sqlUpdateContractLCSAndActivation += " , LCS_ISSUE_DATE=to_date('" + issueDate.toString() + "','yyyy-mm-dd')";
                    }

                    if (contractActivationDate != null) {
                        if (contractActivationDate.compareTo("") != 0 && contractActivationDate.length() > 9) {
                            //commented by sameh
                            sqlUpdateContractLCSAndActivation += " ,LCS_INIT_DATE = to_date('" + contractActivationDate.substring(0, 10) + "','yyyy-mm-dd') ";
                            //Utility.logger.debug("Activation Date : "+contractActivationDate); 
                        }
                    }
                    sqlUpdateContractLCSAndActivation += " where contract_id='" + contractId + "'";
                    //Utility.logger.debug(sqlUpdateContractLCSAndActivation);
                    try {
                        statContractActivation.executeUpdate(sqlUpdateContractLCSAndActivation);
                    } catch (Exception e) {
                        System.out.println("Alert ");
                        System.out.println(sqlUpdateContractLCSAndActivation);
                        e.printStackTrace();

                    }
                    statContractActivation.close();

                    //debug("time taken to do final stage of contract verification  " + (  System.currentTimeMillis() - startTime));
                    Statement statInsertContractSysWarning = con.createStatement();
                    for (int k = 0; k < v.size(); k++) {
                        ContractWarningModel contractWarningModel = (ContractWarningModel) v.get(k);
                        if (contractWarningModel != null) {
                            String strWarningId = contractWarningModel.getContractWarningID();
                            String sqlInsertContractSysWarning = "insert into CR_CONTRACT_SYSTEM_WARNING(CONTRACT_SYSTEM_WARNING_ID,CONTRACT_ID,CONTRACT_WARNING_ID)"
                                    + " values(SEQ_CR_CONTRACT_SYS_WARNING_ID.nextval," + contractId + "," + strWarningId + ")";
                            //Utility.logger.debug("sss"+sqlInsertContractSysWarning);                 
                            statInsertContractSysWarning.executeUpdate(sqlInsertContractSysWarning);
                        }
                    }
                    statInsertContractSysWarning.close();

                    Statement statUpdateContract = con.createStatement();
                    String sqlUpdateContract = "update CR_CONTRACT set CONTRACT_SYS_WARNING_CHECKED = 1 where CONTRACT_ID = " + contractId + " ";
                    //Utility.logger.debug("kkk"+sqlUpdateContract);
                    statUpdateContract.executeUpdate(sqlUpdateContract);

                    try {
                        statUpdateContract.close();
                    } catch (Exception e) {
                    }
                    try {
                        res.close();
                    } catch (Exception e) {
                    }
//                    try {
//                        statLCS.close();
//                    } catch (Exception e) {
//                    }
//                    try {
//                        conToLCS.close();
//                    } catch (Exception e) {
//                    }


                } else if (strContractChecked.compareTo("1") == 0) {
                    String sqlGetContractSysWarnings = "select * from vw_CR_CONTRACT,cr_contract_system_warning,vw_cr_contract_warning_type where "
                            + " vw_CR_CONTRACT.CONTRACT_ID = " + contractId + " and vw_CR_CONTRACT.CONTRACT_ID = cr_contract_system_warning.CONTRACT_ID and  cr_contract_system_warning.CONTRACT_WARNING_ID = vw_cr_contract_warning_type.contract_warning_type_id";
                    //Utility.logger.debug("www"+sqlGetContractSysWarnings);  
                    ResultSet resWarning19 = warningStat.executeQuery(sqlGetContractSysWarnings);
                    while (resWarning19.next()) {
                        v.add(new ContractWarningModel(resWarning19));
                    }
                    resWarning19.close();
                }
                String updateAutomaticFlag = "update CR_CONTRACT set AUTOMATIC_FLAG = 1 where CONTRACT_ID = " + contractId + " ";
                Statement statAutomaticFlag = con.createStatement();
                statAutomaticFlag.executeUpdate(updateAutomaticFlag);
                statAutomaticFlag.close();

            }
            try {
                if (resWarning != null) {
                    resWarning.close();
                }
            } catch (Exception e) {
            }
            try {
                stat.close();
            } catch (Exception e) {
            }
            try {
                warningStat.close();
            } catch (Exception e) {
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public static final Vector getUserContractWarningByPhase(String phaseName) {
        //Utility.logger.debug("getUserContractWarningbyPhase " + phaseName);


        Connection con = null;
        Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_WARNINGS + phaseName);
        //Utility.logger.debug("objected created  : getUserContractWarningByPhase"+phaseName);
        Vector v = null;

        if (obj == null) {

            v = new Vector();
            try {
                con = Utility.getConnection();
                Statement stat = con.createStatement();
                String sql = "select * from vw_cR_contract_warning_type, vw_cr_phase,vw_cR_warning_type "
                        + " where vw_CR_contract_warning_type.PHASE_ID = vw_cR_phase.PHASE_ID "
                        + " and vw_cr_warning_type.WARNING_TYPE_ID =vw_Cr_contract_warning_type.WARNING_TYPE_ID "
                        + " and vw_cr_warning_type.warning_type_name='USER WARNING' "
                        + " AND vw_cR_contract_warning_type.phase_name='" + phaseName + "'"
                        + " and vw_cR_contract_warning_type.WARNING_STATUS_NAME not in('DELETED')";
                ResultSet res = stat.executeQuery(sql);
                while (res.next()) {
                    v.add(new ContractWarningModel(res));
                }
                stat.close();
                //   Utility.closeConnection(con);   

                CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_WARNINGS + phaseName, v);
            } catch (Exception e) {
            } finally {
                // if (stat!=null)try{stat.close();}catch(Exception e){}
                if (con != null) {
                    try {
                        Utility.closeConnection(con);
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            v = (Vector) obj;
        }
        //Utility.logger.debug("time took = " +( System.currentTimeMillis() - startTime ));
        return v;
    }

    public static final Vector getSystemContractWarningByPhase(String phaseName) {
        //Utility.logger.debug("getUserContractWarningbyPhase " + phaseName);


        Connection con = null;
        Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_SYSTEM_WARNINGS + phaseName);
        //Utility.logger.debug("objected created  : getUserContractWarningByPhase"+phaseName);
        Vector v = null;

        if (obj == null) {

            v = new Vector();
            try {
                con = Utility.getConnection();
                Statement stat = con.createStatement();
                String sql = "select * from vw_cR_contract_warning_type, vw_cr_phase,vw_cR_warning_type "
                        + " where vw_CR_contract_warning_type.PHASE_ID = vw_cR_phase.PHASE_ID "
                        + " and vw_cr_warning_type.WARNING_TYPE_ID =vw_Cr_contract_warning_type.WARNING_TYPE_ID "
                        + " and vw_cr_warning_type.warning_type_name='SYSTEM WARNING' "
                        + " AND vw_cR_contract_warning_type.phase_name='" + phaseName + "'"
                        + " and vw_cR_contract_warning_type.WARNING_STATUS_NAME not in('DELETED')";
                ResultSet res = stat.executeQuery(sql);
                while (res.next()) {
                    v.add(new ContractWarningModel(res));
                }
                stat.close();
                //          /Utility.closeConnection(con);   

                CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_CONTRACT_SYSTEM_WARNINGS + phaseName, v);
            } catch (Exception e) {
            } finally {
                //     if (stat!=null)try{stat.close();}catch(Exception e){}
                if (con != null) {
                    try {
                        Utility.closeConnection(con);
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            v = (Vector) obj;
        }
        //Utility.logger.debug("time took = " +( System.currentTimeMillis() - startTime ));
        return v;
    }

    /*
     * 7- get user contract warning by phase takes an inpute a phase name and
     * retrieve all activated warning of type user warning in this phase
     */
    public static final Vector getActiveUserContractWarningByPhase(String phaseName, Connection con) {
        //Utility.logger.debug ("getActiveUserContractWarningByPhase ");

        Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_ACTIVE_CONTRACT_WARNINGS + phaseName);
        Vector v = null;

        if (obj == null) {

            v = new Vector();
            try {

                Statement stat = con.createStatement();
                String sql = "select * from vw_cR_contract_warning_type, vw_cr_phase,vw_cR_warning_type "
                        + " where vw_CR_contract_warning_type.PHASE_ID = vw_cR_phase.PHASE_ID "
                        + " and vw_cr_warning_type.WARNING_TYPE_ID =vw_Cr_contract_warning_type.WARNING_TYPE_ID "
                        + " and vw_cr_warning_type.warning_type_name='USER WARNING' "
                        + " AND vw_cR_contract_warning_type.phase_name='" + phaseName + "'"
                        + " and vw_cR_contract_warning_type.WARNING_STATUS_NAME = 'ACTIVE'";

                ResultSet res = stat.executeQuery(sql);
                while (res.next()) {
                    v.add(new ContractWarningModel(res));
                }
                res.close();
                stat.close();

                CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_ACTIVE_CONTRACT_WARNINGS + phaseName, v);
            } catch (Exception e) {
            }

        } else {
            v = (Vector) obj;
        }
        //Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));
        return v;
    }

    /*
     * 8- get contract warnings of this contract id which are the warnings that
     * are connected to teh lastest status this contract took
     */
    public static final Vector getContractWarnings(String contractID) {
        Vector v = new Vector();
        Connection con = null;
        try {
            con = Utility.getConnection();
            Statement stat = con.createStatement();

            /*
             * String sql = "select * from vw_cR_contract_warning_type ,
             * vw_cR_contract_warning , vw_Cr_contract_last_status " +" where
             * vw_CR_contract_last_status.CONTRACT_ID =" +contractID +" and
             * vw_Cr_contract_warning_type.WARNING_TYPE_NAME = 'USER WARNING'"
             * +" and vw_Cr_contract_last_status.CONTRACT_STATUS_ID =
             * vw_cr_contract_warning.CONTRACT_STATUS_ID" +" and
             * vw_cr_contract_warning.CONTRACT_WARNING_TYPE_ID =
             * vw_cR_contract_warning_type.contracT_WARNING_TYPE_ID(+)" ;
             */

            //the above statement was changed from using vw_CR_contract_last_status to vw_CR_contract to  optimize it 

            String sql = "select * from vw_cR_contract_warning_type ,  vw_cR_contract_warning , vw_Cr_contract "
                    + " where vw_CR_contract.CONTRACT_ID =" + contractID
                    + " and vw_Cr_contract_warning_type.WARNING_TYPE_NAME = 'USER WARNING'"
                    + " and vw_Cr_contract.CONTRACT_LAST_STATUS_ID = vw_cr_contract_warning.CONTRACT_STATUS_ID"
                    + " and vw_cr_contract_warning.CONTRACT_WARNING_TYPE_ID = vw_cR_contract_warning_type.contracT_WARNING_TYPE_ID(+)";


            //Utility.logger.debug(sql);         

            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                v.add(new ContractWarningModel(res));
            }
            stat.close();
            //  Utility.closeConnection(con);      
        } catch (Exception e) {
        } finally {
            //      if (stat!=null)try{stat.close();}catch(Exception e){}
            if (con != null) {
                try {
                    Utility.closeConnection(con);
                } catch (Exception e) {
                }
            }
        }
        return v;
    }

    /*
     * 1- get contract status types as ContractStatusTypeModel in the
     * contractStatusTypeDto that the method return for this phase that is being
     * sent to the method as an argument if no status types are found in this
     * phase it return null
     */
    public static final ContractStatusTypeDto getContractStatusTypeByPhase(String phaseName, Connection con) {

        try {

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from vw_cr_contract_status_type,vw_cr_phase where vw_cr_contract_status_type.contract_status_PHASE_ID = vw_cr_phase.PHASE_ID and  vw_cr_phase.phase_name=\'" + phaseName + "\' order by contract_status_type_name");
            ContractStatusTypeDto contractStatusTypeDto = new ContractStatusTypeDto();
            while (res.next()) {
                String contractStatusTypeName = res.getString("contract_Status_type_name");
                int contractStatusTypeId = res.getInt("contract_Status_type_id");
                contractStatusTypeDto.addContractStatusTypeModel(new ContractStatusTypeModel(contractStatusTypeId, contractStatusTypeName));
            }
            stat.close();

            return contractStatusTypeDto;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 2- get contract by the contract id this method return a contractModel
     * object that have the information of this contract of the id that is sent
     * to the method
     */
    public static ContractModel getContract(String contractId) {
        try {
            Connection con = Utility.getConnection();
            //Statement stat = con.createStatement();
			/*
             * String sql = "SELECT GEN_CITY.*,SDS_GET_CONTRACT_REIMPORT_FLAG
             * (CONTRACT_MAIN_SIM_NO, CONTRACT_ID, contract.Sheet_Id)
             * RE_IMPORTED_FLAG , " +" vw_CR_sheet.*,CONTRACT.*,
             * VW_CR_CONTRACT_STATUS_TYPE.*,VW_CR_PHASE.* ,VW_GEN_PRODUCT.*,
             * VW_CR_CUSTOMER_ID_TYPE.*,gen_dcm.dcm_name
             * sheet_pos_name,gen_dcm.DCM_ADDRESS " +" FROM vw_CR_sheet,
             * VW_CR_CONTRACT_ID_LAST_STATUS
             * CONTRACT,VW_CR_CONTRACT_STATUS_TYPE,VW_CR_PHASE,VW_GEN_PRODUCT,VW_CR_CUSTOMER_ID_TYPE,
             * gen_Dcm,GEN_CITY WHERE " +" vw_CR_sheet.sheet_id =
             * contract.sheet_id " +" AND vw_CR_sheet.sheet_pos_code =
             * gen_dcm.DCM_CODE " +" AND
             * VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =
             * contract.CONTRACT_STATUS_TYPE_ID " +" AND VW_CR_PHASE.PHASE_ID =
             * VW_CR_CONTRACT_STATUS_TYPE.contract_status_PHASE_ID " +" AND
             * CONTRACT.CONTRACT_CUSTOMER_ID_TYPE =
             * VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) " +" AND
             * CONTRACT.CONTRACT_TYPE_ID = VW_GEN_PRODUCT.PRODUCT_ID(+) " +" and
             * contract.contract_id = "+contractId +" and gen_dcm.DCM_CITY_ID =
             * GEN_CITY.CITY_CODE"; //debug(sql); //Utility.logger.debug(sql);
             */


            String sql = "begin ? := SDS_GET_Contract_details(" + contractId + "); end;";
            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);






            ContractModel contractModel = null;
            if (res.next()) {
                contractModel = ContractModel.getContractModelPlusAuthData(res);
                contractModel.setPosName(res.getString(ContractModel.SHEET_POS_NAME));
                contractModel.setPosAddress(res.getString(ContractModel.DCM_ADDRESS));
            }
            res.close();
            colableStat.close();


            Utility.closeConnection(con);
            return contractModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * 9- get contract by main sim no and batch id
     */

    public static ContractModel getContractBySimNo(String simNo, String batchId) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            /*
             * String sql = "begin ? := SDS_CONTRACT_BY_SIM ('"+simNo+"',
             * "+batchId+"); end;";
             *
             * Utility.logger.debug(sql);
             *
             * CallableStatement colableStat = con.prepareCall(sql);
             * colableStat.registerOutParameter(1, OracleTypes.CURSOR);
             * colableStat.execute(); ResultSet res =
             * (ResultSet)colableStat.getObject(1);
             */


            /*
             * i replaced this sql with an optimized one to improve performance
             * since it takes too long to get executed
             *
             * String sql = "SELECT * FROM
             * VW_CR_SHEET,VW_CR_CONTRACT_STATUS_REIMPORT
             * CONTRACT,VW_CR_CONTRACT_STATUS_TYPE,VW_CR_PHASE,VW_GEN_PRODUCT,VW_CR_CUSTOMER_ID_TYPE"
             * +" WHERE " +" contract.sheet_id = vw_cr_sheet.SHEET_ID" +" AND
             * vw_cr_sheet.BATCH_ID = "+batchId +" AND
             * contract.CONTRACT_MAIN_SIM_NO = '"+simNo +"'" +" and
             * VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =
             * contract.CONTRACT_STATUS_TYPE_ID " +" AND VW_CR_PHASE.PHASE_ID =
             * VW_CR_CONTRACT_STATUS_TYPE.contract_status_PHASE_ID " +" AND
             * CONTRACT.CONTRACT_CUSTOMER_ID_TYPE =
             * VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) " +" AND
             * CONTRACT.CONTRACT_TYPE_ID = VW_GEN_PRODUCT.PRODUCT_ID(+) ";
             */

            String sql =
                    " select vw_CR_sheet.*, vw_CR_contract.CONTRACT_ID,CONTRACT_MAIN_SIM_NO , CONTRACT_TYPE_ID , CONTRACT_DIAL_NO ,CONTRACT_CUSTOMER_NAME , "
                    + " CONTRACT_ADDRESS , CONTRACT_AREA , CONTRACT_CUSTOMER_ID ,CONTRACT_CUSTOMER_ID_TYPE , vw_CR_contract.SHEET_ID "
                    + " , CONTRACT_HOME_PHONE , SHEET_SERIAL_ID ,PRODUCT_NAME , CUSTOMER_ID_TYPE_NAME ,  CONTRACT_STATUS_TYPE_NAME,user_id "
                    + " , CONTRACT_LAST_STATUS_ID , SDS_GET_CONTRACT_REIMPORT_FLAG  (CONTRACT_MAIN_SIM_NO,  vw_CR_contract.CONTRACT_ID ,    vw_CR_contract.Sheet_Id)   RE_IMPORTED_FLAG "
                    + " ,vw_gen_person.PERSON_FULL_NAME "
                    + " from      VW_CR_CONTRACT_STATUS_TYPE,VW_CR_CUSTOMER_ID_TYPE , VW_GEN_PRODUCT ,vw_gen_person, vw_CR_sheet , vw_CR_contract , "
                    + " vw_CR_contract_status  where CONTRACT_MAIN_SIM_NO = '" + simNo + "' and "
                    + " vw_cR_sheet.batch_id = " + batchId + " and "
                    + " vw_CR_sheet.SHEET_ID = vw_CR_contract.SHEET_ID and "
                    + " vw_CR_contract_Status.CONTRACT_STATUS_ID = vw_CR_contract.CONTRACT_LAST_STATUS_ID and "
                    + " vw_gen_product.PRODUCT_ID = vw_CR_contract.CONTRACT_TYPE_ID and "
                    + " VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =   vw_CR_contract_status.CONTRACT_STATUS_TYPE_ID  AND "
                    + " vw_CR_contract.CONTRACT_CUSTOMER_ID_TYPE = VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) "
                    + " and  vw_cr_contract_status.USER_ID = vw_gen_person.PERSON_ID ";

            //Utility.logger.debug(sql);

            debug(sql);
            ResultSet res = stat.executeQuery(sql);

            ContractModel contractModel = null;
            if (res.next()) {
                contractModel = new ContractModel(res);
            }
            res.close();
            stat.close();
            Utility.closeConnection(con);
            return contractModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ContractModel getContractBySimNo(String simNo, String batchId, String sheetID, Connection con) {
        Statement stat = null;
        try {

            stat = con.createStatement();


            String sql =
                    " select vw_CR_contract.AUTOMATIC_FLAG,vw_CR_contract.CONTRACT_ID,vw_CR_contract.CONTRACT_CUSTOMER_BIRTH_DATE,CONTRACT_MAIN_SIM_NO , CONTRACT_TYPE_ID , CONTRACT_DIAL_NO ,CONTRACT_CUSTOMER_NAME , "
                    + " CONTRACT_ADDRESS , CONTRACT_AREA , CONTRACT_CUSTOMER_ID ,CONTRACT_CUSTOMER_ID_TYPE , vw_CR_contract.SHEET_ID "
                    + " , CONTRACT_HOME_PHONE , SHEET_SERIAL_ID ,PRODUCT_NAME , CUSTOMER_ID_TYPE_NAME ,  CONTRACT_STATUS_TYPE_NAME,user_id "
                    + " , CONTRACT_LAST_STATUS_ID , SDS_GET_CONTRACT_REIMPORT_FLAG  (CONTRACT_MAIN_SIM_NO,  vw_CR_contract.CONTRACT_ID ,    vw_CR_contract.Sheet_Id)   RE_IMPORTED_FLAG "
                    + " ,vw_gen_person.PERSON_FULL_NAME "
                    + " from      VW_CR_CONTRACT_STATUS_TYPE,VW_CR_CUSTOMER_ID_TYPE , VW_GEN_PRODUCT ,vw_gen_person, vw_CR_sheet , vw_CR_contract , "
                    + " vw_CR_contract_status  where CONTRACT_MAIN_SIM_NO = '" + simNo + "' and "
                    + " vw_cR_sheet.sheet_id = " + sheetID + " and "
                    + " vw_CR_sheet.SHEET_ID = vw_CR_contract.SHEET_ID and "
                    + " vw_CR_contract_Status.CONTRACT_STATUS_ID = vw_CR_contract.CONTRACT_LAST_STATUS_ID and "
                    + " vw_gen_product.PRODUCT_ID = vw_CR_contract.CONTRACT_TYPE_ID and "
                    + " VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =   vw_CR_contract_status.CONTRACT_STATUS_TYPE_ID  AND "
                    + " vw_CR_contract.CONTRACT_CUSTOMER_ID_TYPE = VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) "
                    + " and  vw_cr_contract_status.USER_ID = vw_gen_person.PERSON_ID ";


            ResultSet res = stat.executeQuery(sql);

            ContractModel contractModel = null;
            if (res.next()) {
                contractModel = new ContractModel(res);
                contractModel.setAutomaticFlag(res.getString(contractModel.AUTOMATIC_FLAG));
                contractModel.setCustomerBirthDate(res.getString("CONTRACT_CUSTOMER_BIRTH_DATE"));
                //contractModel.setContractFormNumber(res.getString("contract_Form_Number"));
            }
            res.close();



            return contractModel;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }

        }
        return null;
    }

    /*
     * 11- insert a new status to all contracts in this sheet
     */
    public static void insertNewStatusAllContractsInSheet(String sheetId, String contractStatusId, String userId) {
        try {
            Connection con = Utility.getConnection();
            /*
             * String sql = "begin SDS_SHEET_CONTRACTS_INS_STAT1 ("+sheetId+",
             * "+contractStatusId+", "+userId+"); end;"; CallableStatement stat
             * = con.prepareCall(sql); stat.executeUpdate();
             */

            Statement stat = con.createStatement();
            Statement insertStat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from vw_Cr_contract_last_Status where sheet_id =" + sheetId);
            while (res.next()) {
                String contractId = res.getString("contract_id");
                String contractStatusTypeId = res.getString("contract_status_type_id");
                //debug("***************");        
                if (contractStatusTypeId.compareTo(contractStatusId) == 0) {
                    //debug("same status ");
                } else {
                    String insertSql = "insert into vw_Cr_contract_status(user_id, contract_status_id, contract_id, contract_status_type_id, contract_status_date)"
                            + " values(" + userId + ",seq_cr_contract_status.nextval," + contractId + "," + contractStatusId + ",sysdate)";
                    //debug(insertSql);
                    insertStat.execute(insertSql);
                }
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 12- get a contract by its main sim no and its batch id the returned type
     * is contract model object
     */
    public static ContractModel getContractByMainSimNoAndBatchId(String batchId, String contractMainSimNo) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            /*
             * commented for optimization
             *
             * String sql = " select * from
             * VW_CR_CONTRACT_STATUS_REIMPORT,vw_CR_sheet_last_status where " +"
             * VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id =
             * vw_CR_sheet_last_status.sheet_id " +" and
             * vw_CR_sheet_last_status.batch_id = "+ batchId +" and
             * VW_CR_CONTRACT_STATUS_REIMPORT.contracT_main_sim_no =
             * '"+contractMainSimNo+"'";
             */
            String sql =
                    " select vw_CR_sheet.*,vw_CR_contract.CONTRACT_ID,CONTRACT_MAIN_SIM_NO , CONTRACT_TYPE_ID , CONTRACT_DIAL_NO ,CONTRACT_CUSTOMER_NAME , "
                    + " CONTRACT_ADDRESS , CONTRACT_AREA , CONTRACT_CUSTOMER_ID ,CONTRACT_CUSTOMER_ID_TYPE , vw_CR_contract.SHEET_ID "
                    + " , CONTRACT_HOME_PHONE , SHEET_SERIAL_ID ,PRODUCT_NAME , CUSTOMER_ID_TYPE_NAME ,  CONTRACT_STATUS_TYPE_NAME,user_id "
                    + " , CONTRACT_LAST_STATUS_ID , SDS_GET_CONTRACT_REIMPORT_FLAG  (CONTRACT_MAIN_SIM_NO,  vw_CR_contract.CONTRACT_ID ,    vw_CR_contract.Sheet_Id)   RE_IMPORTED_FLAG "
                    + " ,vw_gen_person.PERSON_FULL_NAME "
                    + " from      VW_CR_CONTRACT_STATUS_TYPE,VW_CR_CUSTOMER_ID_TYPE , VW_GEN_PRODUCT ,vw_gen_person, vw_CR_sheet , vw_CR_contract , "
                    + " vw_CR_contract_status  where CONTRACT_MAIN_SIM_NO = '" + contractMainSimNo + "' and "
                    + " vw_cR_sheet.batch_id = " + batchId + " and "
                    + " vw_CR_sheet.SHEET_ID = vw_CR_contract.SHEET_ID and "
                    + " vw_CR_contract_Status.CONTRACT_STATUS_ID = vw_CR_contract.CONTRACT_LAST_STATUS_ID and "
                    + " vw_gen_product.PRODUCT_ID = vw_CR_contract.CONTRACT_TYPE_ID and "
                    + " VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =   vw_CR_contract_status.CONTRACT_STATUS_TYPE_ID  AND "
                    + " vw_CR_contract.CONTRACT_CUSTOMER_ID_TYPE = VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) "
                    + " and  vw_cr_contract_status.USER_ID = vw_gen_person.PERSON_ID ";


            debug(sql);

            ResultSet res = stat.executeQuery(sql);
            ContractModel contractModel = null;
            if (res.next()) {
                contractModel = new ContractModel(res);
            }
            stat.close();
            Utility.closeConnection(con);
            return contractModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 13- get contract dto for the list of contract ids that are sent as string
     * objects in the contracts vector the contractdto retruend contain contract
     * information for all the ids sent in the contracts vector that have teh
     * status either accpeted verify or accepted authintication or rejected
     * authinticatino
     */
    public static ContractDto getContractsDto(Vector contracts, String sheetID) {
        StringBuffer contractsId = new StringBuffer();
        ContractDto contractDto = new ContractDto();

        if (contracts.size() > 0) {
            contractsId.append("(" + (String) contracts.get(0));
        }
        for (int i = 1; i < contracts.size(); i++) {
            contractsId.append("," + (String) contracts.get(i));
        }
        contractsId.append(")");
        //debug(contractsId);
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();



            String sql = "select * from VW_CR_CONTRACT_STATUS_REIMPORT,vw_CR_sheet where "
                    + " VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetID
                    + " and vw_CR_sheet.sheet_id = VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id "
                    + "and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
                    + " or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION'"
                    + " or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION')"
                    + "and VW_CR_CONTRACT_STATUS_REIMPORT.contract_id in " + contractsId.toString();

            //Utility.logger.debug(sql);


            ResultSet res = stat.executeQuery(sql);

            //Utility.logger.debug("time =  " + (System.currentTimeMillis() - startT));

            //   Statement statContractWarning  = con.createStatement();
            Hashtable contractLastStatusContractID = new Hashtable();
            Vector contractLastStatusIDVector = new Vector();

            while (res.next()) {
                ContractModel contract = new ContractModel(res);
                contractDto.addContractModel(contract);
                contractLastStatusContractID.put(contract.getContractLastStatusId(), contract.getId());
                contractLastStatusIDVector.add(contract.getContractLastStatusId());
            }

            StringBuffer contractLastStatusIDString = new StringBuffer();
            if (contractLastStatusIDVector.size() > 0) {
                contractLastStatusIDString.append("(" + (String) contractLastStatusIDVector.get(0));

                for (int i = 1; i < contractLastStatusIDVector.size(); i++) {
                    contractLastStatusIDString.append("," + (String) contractLastStatusIDVector.get(i));
                }
                contractLastStatusIDString.append(")");

                String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID in " + contractLastStatusIDString.toString()
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and WARNING_TYPE_NAME = 'USER WARNING'";

                //Utility.logger.debug(contractWarningSql );

                res = stat.executeQuery(contractWarningSql);
                while (res.next()) {
                    String contractStatusID = res.getString("contract_status_id");

                    contractDto.addContractWarning((String) contractLastStatusContractID.get(contractStatusID), res);
                }

            }


            //   statContractWarning.close();
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractDto;
    }

    /*
     * 14- get sheet contracts eligable for authintication for the input sheet
     * Id contracts that are eligable for authintication which have the status
     * of accepted verify or accepted authintication or rejected authintication
     */
    public static ContractDto getSheetContractsEligableForAuthintication(String sheetId) {
        try {
            Hashtable prevRejectedContractsInAuthentication = getContractsListPrevioslyRejected(sheetId, "REJECTED AUTHINTICATION");

            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String sql = "select * from VW_CR_CONTRACT_STATUS_REIMPORT,vw_CR_sheet, vw_gen_product where "
                    + " vw_CR_sheet.sheet_id = VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id "
                    + " and vw_gen_product.product_id= VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
                    + " and vw_gen_product.product_category_name='PREPAID' "
                    + "and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
                    + " or CONTRACT_STATUS_PHASE_ID =4)"
                    + "and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId;



            long startT = System.currentTimeMillis();

            ResultSet res = stat.executeQuery(sql);
            debug("time to exec sql =" + (System.currentTimeMillis() - startT));

            ContractDto contractDto = new ContractDto();



            while (res.next()) {
                ContractModel contract = new ContractModel(res);

                if (prevRejectedContractsInAuthentication.containsKey(contract.getMainSimNum())) {
                    contract.setContractPrevRejectedInAuthentication(true);
                }

                contractDto.addContractModel(contract);


            }

            /*
             * this is a new optimization instead of getting the warning for
             * each contract here i get it all at once and add them since the
             * dto data structure allow this
             */
            Statement statContractWarning = con.createStatement();
            startT = System.currentTimeMillis();

            String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING,vw_CR_contract"
                    + " where sheet_id =  " + sheetId
                    + " and  contract_last_status_id = CONTRACT_STATUS_ID "
                    + " and WARNING_STATUS_NAME = 'ACTIVE'"
                    + " and WARNING_TYPE_NAME = 'USER WARNING'";
            ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);

            debug("time to get warning = " + (System.currentTimeMillis() - startT));
            debug(contractWarningSql);

            while (warningsSet.next()) {
                contractDto.addContractWarning(warningsSet.getString("contract_id"), warningsSet);
            }




            statContractWarning.close();
            stat.close();
            Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ContractDto();
    }


    /*
     * 20-
     */
    public static Hashtable getContractsListPrevioslyRejected(String sheetId, String prevStatus) {
        Hashtable contractsPrevRejected = new Hashtable();
        if ((sheetId == null) || (sheetId.compareTo("") == 0)) {
            return new Hashtable();
        }

        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String sql = " select * from vw_CR_contract_status_reimport contract_status_reimport "
                    + " where ( select contract_Status_type_name from ("
                    + " select id_last_status.* from vw_CR_contract_id_last_status id_last_status "
                    + " order by id_last_status.contract_status_date  desc "
                    + "		  ) contract_stauts_by_id "
                    + " where contract_status_reimport.CONTRACT_MAIN_SIM_NO = contract_stauts_by_id .contract_main_sim_no"
                    + " and contract_status_reimport.CONTRACT_STATUS_ID <> contract_stauts_by_id .contract_status_id"
                    + "    and rownum<=1 )"
                    + " ='" + prevStatus + "' and sheet_id=" + sheetId;

            debug(sql);

            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                String contractMainSimNo = res.getString("contract_main_sim_no");
                contractsPrevRejected.put(contractMainSimNo, "");
            }

            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractsPrevRejected;
    }

    /*
     * 10- get all contracts that have a status in the authentication phase this
     * is the method utilized to view all authenticated contracts in this sheet
     *
     * NOTE:ammandment happend to this method to include contracts that were
     * rejected in the authintication phase previously to be shown to the user
     * too that is why i added a hashtable of the contract mian sim number that
     * were previously rejected getting this hashtable is by another method
     */
    public static ContractDto getSheetContractsInAuthinticationPhaseOPT(String sheetId, String sheetSerial, Connection con) {
        try {

            //Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            /*
             * String sql = "select * from
             * VW_CR_CONTRACT_STATUS_REIMPORT,vw_CR_sheet where
             * vw_CR_sheet.sheet_id = VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id
             * and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME
             * ='ACCEPTED AUTHINTICATION' or
             * VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME
             * ='REJECTED AUTHINTICATION') and
             * VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId;
             */

            String sql = "begin ? := SDS_get_CNT_Auth_sheet(" + sheetId + "); end;";
            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);

            ContractDto contractDto = new ContractDto();

            while (res.next()) {
                ContractModel contract = new ContractModel();


                contract.mainSimNum = res.getString(ContractModel.MAIN_SIM_NO);
                contract.typeId = res.getString(ContractModel.CONTRACT_TYPE_ID);
                contract.id = res.getString(ContractModel.CONTRACT_ID);
                contract.statusTypeName = res.getString(ContractModel.CONTRACT_STATUS_TYPE_NAME);
                contract.typeName = res.getString(ContractModel.CONTRACT_TYPE_NAME);
                contract.dialNum = res.getString(ContractModel.CONTRACT_DIAL_NO);
                contract.sheetId = res.getString(ContractModel.CONTRACT_SHEET_ID);

                contract.sheetSerialNo = sheetSerial;

                contractDto.addContractModel(contract);


            }
            colableStat.close();
            stat.close();
            //Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ContractDto getSheetContractsVerifiabileInAuthinticationPhaseOPT(String sheetId, String sheetSerial, Connection con) {
        try {

            //Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            /*
             * String sql = "select * from
             * VW_CR_CONTRACT_STATUS_REIMPORT,vw_CR_sheet where
             * vw_CR_sheet.sheet_id = VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id
             * and ( VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME
             * ='ACCEPTED AUTHINTICATION' or
             * VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME
             * ='REJECTED AUTHINTICATION') and
             * VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId;
             */

            String sql = "begin ? := SDS_get_CNT_All_Auth_sheet(" + sheetId + "); end;";
            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);

            ContractDto contractDto = new ContractDto();

            while (res.next()) {
                ContractModel contract = new ContractModel();


                contract.mainSimNum = res.getString(ContractModel.MAIN_SIM_NO);
                contract.typeId = res.getString(ContractModel.CONTRACT_TYPE_ID);
                contract.id = res.getString(ContractModel.CONTRACT_ID);
                contract.statusTypeName = res.getString(ContractModel.CONTRACT_STATUS_TYPE_NAME);
                contract.typeName = res.getString(ContractModel.CONTRACT_TYPE_NAME);
                contract.dialNum = res.getString(ContractModel.CONTRACT_DIAL_NO);
                contract.sheetId = res.getString(ContractModel.CONTRACT_SHEET_ID);

                contract.sheetSerialNo = sheetSerial;

                contractDto.addContractModel(contract);


            }
            colableStat.close();
            stat.close();
            //Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ContractDto getSheetContractsInAuthinticationPhase(String sheetId, Hashtable contractsPrevRejected) {
        try {

            Enumeration contractsPrevRejectedSimNoEnum = contractsPrevRejected.keys();
            //Utility.logger.debug(" contractsPrevRejected" +  contractsPrevRejected.size());

            String prevRejectedContractsSet = "(";

            while (contractsPrevRejectedSimNoEnum.hasMoreElements()) {
                String tempMainSimNo = (String) contractsPrevRejectedSimNoEnum.nextElement();
                if (tempMainSimNo.length() == 0) {
                    continue;
                } else {
                    prevRejectedContractsSet += "'" + tempMainSimNo + "',";
                }
            }

            //Utility.logger.debug("pprev " + prevRejectedContractsSet);
            String sqlAmmendment = "";

            if (prevRejectedContractsSet.length() > 1) {
                prevRejectedContractsSet = prevRejectedContractsSet.substring(0, prevRejectedContractsSet.length() - 1);
                prevRejectedContractsSet += ")";
                sqlAmmendment = " or VW_CR_CONTRACT_STATUS_REIMPORT.contract_main_sim_no in " + prevRejectedContractsSet;
            }

            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String sql = "select * from VW_CR_CONTRACT_STATUS_REIMPORT,vw_CR_sheet where "
                    + " vw_CR_sheet.sheet_id = VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id "
                    + "and ("
                    + "  VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED AUTHINTICATION'"
                    + " or VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='REJECTED AUTHINTICATION'"
                    + sqlAmmendment + ")"
                    + "and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId;

            //debug("getSheetContracts --> " + sql);
            //Utility.logger.debug(sql);

            //   System.out.println("sql ="+sql);
            ResultSet res = stat.executeQuery(sql);
            ContractDto contractDto = new ContractDto();
            Statement statContractWarning = con.createStatement();
            while (res.next()) {
                ContractModel contract = new ContractModel(res);

                if (contractsPrevRejected.containsKey(contract.getMainSimNum())) {
                    contract.setContractPrevRejectedInAuthentication(true);
                }

                contractDto.addContractModel(contract);
                String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID = " + contract.getContractLastStatusId()
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and WARNING_TYPE_NAME = 'USER WARNING'";

                //   System.out.println("contractWarningSql" + contractWarningSql);  
                ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);
                while (warningsSet.next()) {
                    contractDto.addContractWarning(contract.getId(), warningsSet);
                }
            }
            statContractWarning.close();
            stat.close();
            Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 15- get sheet contracts of this sheet id it return ContractDto Object
     */
    public static ContractDto getSheetContracts(String sheetId, Connection con) {

        try {

            Statement stat = con.createStatement();
            Statement statContractWarning = con.createStatement();
            String sql = "begin ? := SDS_SHEET_CONTRACTS (" + sheetId + "); end;";

            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);



            ContractDto contractDto = new ContractDto();

            while (res.next()) {
                ContractModel contractModel = new ContractModel(res);
                contractModel.setAutomaticFlag(res.getString(contractModel.AUTOMATIC_FLAG));
                contractDto.addContractModel(contractModel);
                String contractID = res.getString("contract_id");
                int lastContractStatusRecordId = res.getInt("contract_status_id");
                String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID = " + lastContractStatusRecordId
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and WARNING_TYPE_NAME = 'USER WARNING'";
                ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);

                while (warningsSet.next()) {
                    contractDto.addContractWarning(contractID, warningsSet);
                }

                //get System Warnings
                contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID = " + lastContractStatusRecordId
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and (WARNING_TYPE_NAME = 'SYSTEM WARNING' or WARNING_TYPE_NAME = 'SYSTEM OPTIONAL WARNING')";
                warningsSet = statContractWarning.executeQuery(contractWarningSql);
                while (warningsSet.next()) {
                    contractDto.addContractSystemWarning(contractID, warningsSet);
                }
                warningsSet.close();
                /////////////
            }
            res.close();
            statContractWarning.close();
            colableStat.close();
            stat.close();

            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ContractDto getBatchContracts(String batchId) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            Statement statContractWarning = con.createStatement();
            String sql = "begin ? := SDS_BATCH_CONTRACTS (" + batchId + "); end;";
            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);


            /*
             * String sql = "SELECT * FROM
             * VW_CR_SHEET,VW_CR_CONTRACT_STATUS_REIMPORT
             * CONTRACT_STATUS,VW_CR_CONTRACT
             * CONTRACT,VW_CR_CONTRACT_STATUS_TYPE,VW_CR_PHASE,VW_GEN_PRODUCT,VW_CR_CUSTOMER_ID_TYPE,
             * VW_CR_STATUS_TYPE" +" WHERE " +" CONTRACT.CONTRACT_ID =
             * CONTRACT_STATUS.CONTRACT_ID " +" AND VW_cR_SHEET.SHEET_ID =
             * CONTRACT.SHEET_ID " +" AND VW_cR_STATUS_TYPE.STATUS_TYPE_ID
             * =VW_CR_CONTRACT_STATUS_TYPE.TYPE_ID " +" AND
             * VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =
             * CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID " +" AND
             * VW_CR_PHASE.PHASE_ID =
             * VW_CR_CONTRACT_STATUS_TYPE.contract_status_PHASE_ID " +" AND
             * CONTRACT.CONTRACT_CUSTOMER_ID_TYPE =
             * VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) " +" AND
             * CONTRACT.CONTRACT_TYPE_ID = VW_GEN_PRODUCT.PRODUCT_ID(+) " +" AND
             * CONTRACT.SHEET_ID = "+sheetId +"order by VW_cr_PHASE.ORDER_ID,
             * VW_cR_STATUS_TYPE.STATUS_TYPE_NAME DESC,
             * contract_status.contract_main_sim_no";
             *
             * debug("getSheetContracts --> " + sql); ResultSet res =
             * stat.executeQuery(sql);
             */



            ContractDto contractDto = new ContractDto();

            while (res.next()) {
                ContractModel contractModel = new ContractModel(res);
                contractModel.setAutomaticFlag(res.getString(contractModel.AUTOMATIC_FLAG));
                contractDto.addContractModel(contractModel);
                String contractID = res.getString("contract_id");
                int lastContractStatusRecordId = res.getInt("contract_status_id");
                String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID = " + lastContractStatusRecordId
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and WARNING_TYPE_NAME = 'USER WARNING'";
                ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);

                while (warningsSet.next()) {
                    contractDto.addContractWarning(contractID, warningsSet);
                }

                //get System Warnings
                contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID = " + lastContractStatusRecordId
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and (WARNING_TYPE_NAME = 'SYSTEM WARNING' or WARNING_TYPE_NAME = 'SYSTEM OPTIONAL WARNING')";
                warningsSet = statContractWarning.executeQuery(contractWarningSql);
                while (warningsSet.next()) {
                    contractDto.addContractSystemWarning(contractID, warningsSet);
                }
                warningsSet.close();
                /////////////
            }
            res.close();
            statContractWarning.close();
            colableStat.close();
            stat.close();
            Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ContractDto getSheetContractsByContractStatus(String sheetId, String strContractStatusTypeName) {
        Connection con = null;
        try {
            con = Utility.getConnection();
            Statement stat = con.createStatement();
            Statement statContractWarning = con.createStatement();
            String sql = "begin ? := SDS_SHEET_CONTRACTS (" + sheetId + "); end;";
            CallableStatement colableStat = con.prepareCall(sql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);

            /*
             * String sql = "SELECT * FROM
             * VW_CR_SHEET,VW_CR_CONTRACT_STATUS_REIMPORT
             * CONTRACT_STATUS,VW_CR_CONTRACT
             * CONTRACT,VW_CR_CONTRACT_STATUS_TYPE,VW_CR_PHASE,VW_GEN_PRODUCT,VW_CR_CUSTOMER_ID_TYPE,
             * VW_CR_STATUS_TYPE" +" WHERE " +" CONTRACT.CONTRACT_ID =
             * CONTRACT_STATUS.CONTRACT_ID " +" AND VW_cR_SHEET.SHEET_ID =
             * CONTRACT.SHEET_ID " +" AND VW_cR_STATUS_TYPE.STATUS_TYPE_ID
             * =VW_CR_CONTRACT_STATUS_TYPE.TYPE_ID " +" AND
             * VW_CR_CONTRACT_STATUS_TYPE.CONTRACT_STATUS_TYPE_ID =
             * CONTRACT_STATUS.CONTRACT_STATUS_TYPE_ID " +" AND
             * VW_CR_PHASE.PHASE_ID =
             * VW_CR_CONTRACT_STATUS_TYPE.contract_status_PHASE_ID " +" AND
             * CONTRACT.CONTRACT_CUSTOMER_ID_TYPE =
             * VW_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+) " +" AND
             * CONTRACT.CONTRACT_TYPE_ID = VW_GEN_PRODUCT.PRODUCT_ID(+) " +" AND
             * CONTRACT.SHEET_ID = "+sheetId +"order by VW_cr_PHASE.ORDER_ID,
             * VW_cR_STATUS_TYPE.STATUS_TYPE_NAME DESC,
             * contract_status.contract_main_sim_no";
             *
             * debug("getSheetContracts --> " + sql); ResultSet res =
             * stat.executeQuery(sql);
             */



            ContractDto contractDto = new ContractDto();

            while (res.next()) {
                ContractModel contractModel = new ContractModel(res);
                contractModel.setAutomaticFlag(res.getString(contractModel.AUTOMATIC_FLAG));
                String contractStatus = contractModel.getStatusTypeName();

                if (strContractStatusTypeName.compareTo(contractStatus) == 0) {
                    contractDto.addContractModel(contractModel);
                    String contractID = res.getString("contract_id");
                    int lastContractStatusRecordId = res.getInt("contract_status_id");
                    String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                            + " where CONTRACT_STATUS_ID = " + lastContractStatusRecordId
                            + " and WARNING_STATUS_NAME = 'ACTIVE'"
                            + " and WARNING_TYPE_NAME = 'USER WARNING'";
                    ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);
                    while (warningsSet.next()) {
                        contractDto.addContractWarning(contractID, warningsSet);
                    }

                    //get System Warnings
                    contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                            + " where CONTRACT_STATUS_ID = " + lastContractStatusRecordId
                            + " and WARNING_STATUS_NAME = 'ACTIVE'"
                            + " and (WARNING_TYPE_NAME = 'SYSTEM WARNING' or WARNING_TYPE_NAME = 'SYSTEM OPTIONAL WARNING')";
                    warningsSet = statContractWarning.executeQuery(contractWarningSql);
                    while (warningsSet.next()) {
                        contractDto.addContractSystemWarning(contractID, warningsSet);
                    }
                    warningsSet.close();
                    /////////////
                }
            }
            res.close();
            colableStat.close();
            statContractWarning.close();
            stat.close();
            Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /*
     * 16-get all batch contracts in this phase phaseName it return a contract
     * dto object
     */

    public static ContractDto getBatchContractsByPhase(String batchId, String phaseName) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String sql = "select * from VW_CR_CONTRACT_STATUS_REIMPORT,vw_cr_sheet,vw_CR_phase"
                    + " where"
                    + " VW_CR_CONTRACT_STATUS_REIMPORT.SHEET_ID = vw_Cr_sheet.SHEET_ID"
                    + " and VW_CR_CONTRACT_STATUS_REIMPORT.PHASE_ID= vw_cr_phase.PHASE_ID"
                    + " and batch_id = " + batchId
                    + " and vw_CR_phase.PHASE_name='" + phaseName + "'"
                    + " ORDER BY VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME,VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_MAIN_SIM_NO";
            ResultSet res = stat.executeQuery(sql);
            ContractDto contractDto = new ContractDto();
            Statement statContractWarning = con.createStatement();
            while (res.next()) {
                ContractModel contract = new ContractModel(res);
                contractDto.addContractModel(contract);
                String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                        + " where CONTRACT_STATUS_ID = " + contract.getContractLastStatusId()
                        + " and WARNING_STATUS_NAME = 'ACTIVE'"
                        + " and WARNING_TYPE_NAME = 'USER WARNING'";
                ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);
                while (warningsSet.next()) {
                    contractDto.addContractWarning(contract.getId(), warningsSet);
                }

            }
            statContractWarning.close();
            stat.close();
            Utility.closeConnection(con);
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFromAuthTmp() {
        String sql = "delete from TMP_AUTHENTICATION_STATUS";
        DBUtil.executeSQL(sql);
    }


    /*
     * 17- get contract status id of this contract status name
     */
    public static String getContractStatusID(String contractStatusName) {
        String contractStatusID = "";
        String sql = "select CONTRACT_STATUS_TYPE_ID from VW_CR_CONTRACT_STATUS_TYPE where CONTRACT_STATUS_TYPE_NAME = '" + contractStatusName + "'";
        contractStatusID = DBUtil.executeQuerySingleValueString(sql, "CONTRACT_STATUS_TYPE_ID");

        return contractStatusID;
    }

    public static String getContractStatusByContractStatusId(String contractStatusID) {
        String contractStatusTypeId = "";
        String sql = "select CONTRACT_STATUS_TYPE_ID from VW_CR_CONTRACT_STATUS where CONTRACT_STATUS_ID = '" + contractStatusID + "'";
        contractStatusTypeId = DBUtil.executeQuerySingleValueString(sql, "CONTRACT_STATUS_TYPE_ID");

        return contractStatusTypeId;
    }
    /*
     * public static Vector getContractHistoryUntillVerify (String mainSimNo) {
     * Vector contractHistoryVec = new Vector(); try { Connection con =
     * Utility.getConnection(); Statement stat = con.createStatement(); String
     * sql = "select * from VW_cr_CONTRACT_HIST_VERFIY where
     * contract_main_sim_no = '"+ mainSimNo+"'"; //debug(sql); ResultSet res =
     * stat.executeQuery(sql); ContractHistoryModel tempContractHistoryModel =
     * null; while (res.next()) { if (tempContractHistoryModel==null) {
     * tempContractHistoryModel = new ContractHistoryModel(res);
     * contractHistoryVec.add(tempContractHistoryModel); } else if
     * (tempContractHistoryModel.getContractStatusId().compareTo(res.getString(ContractHistoryModel.CONTRACT_STATUS_ID))==0)
     * { String newWarning =
     * res.getString(ContractHistoryModel.CONTRACT_WARNING_NAME); if (newWarning
     * !=null) { String oldWarning =
     * tempContractHistoryModel.getContractWarningName(); if
     * (oldWarning.length()>0) {
     * tempContractHistoryModel.setContractWarningName(oldWarning+",
     * "+newWarning); } else {
     * tempContractHistoryModel.setContractWarningName(newWarning); } } } else {
     * tempContractHistoryModel = new ContractHistoryModel(res);
     * contractHistoryVec.add(tempContractHistoryModel); } } } catch (Exception
     * e){ e.printStackTrace(); } return contractHistoryVec; }
     */
    /*
     * public static Vector getContractHistoryUntillAuth (String mainSimNo) {
     * Vector contractHistoryVec = new Vector(); try { Connection con =
     * Utility.getConnection(); Statement stat = con.createStatement(); String
     * sql = "select * from VW_CR_CONTRACT_HIST_AUTH where contract_main_sim_no
     * = '"+ mainSimNo+"'"; //debug(sql); ResultSet res =
     * stat.executeQuery(sql); ContractHistoryModel tempContractHistoryModel =
     * null; while (res.next()) { if (tempContractHistoryModel==null) {
     * tempContractHistoryModel = new ContractHistoryModel(res);
     * contractHistoryVec.add(tempContractHistoryModel); } else if
     * (tempContractHistoryModel.getContractStatusId().compareTo(res.getString(ContractHistoryModel.CONTRACT_STATUS_ID))==0)
     * { String newWarning =
     * res.getString(ContractHistoryModel.CONTRACT_WARNING_NAME); if (newWarning
     * !=null) { String oldWarning =
     * tempContractHistoryModel.getContractWarningName(); if
     * (oldWarning.length()>0) {
     * tempContractHistoryModel.setContractWarningName(oldWarning+",
     * "+newWarning); } else {
     * tempContractHistoryModel.setContractWarningName(newWarning); } } } else {
     * tempContractHistoryModel = new ContractHistoryModel(res);
     * contractHistoryVec.add(tempContractHistoryModel); } } } catch (Exception
     * e){ e.printStackTrace(); } return contractHistoryVec; }
     */

    /*
     * 18- get contract history of this contract main sim no the method return a
     * vector of ContractHistoryModel objects
     */
    public static Vector getContractHistory(String mainSimNo) {
        Vector contractHistoryVec = new Vector();
        Connection con = null;
        try {
            con = Utility.getConnection();
            Statement stat = con.createStatement();


            String sql = "select * from vw_CR_contract_history where contract_main_sim_no = '" + mainSimNo + "' order by sheet_serial_id";
//			String sql = "select * from vw_CR_contract_history where contract_main_sim_no = '"+ mainSimNo+"' UNION ALL select * from vw_CR_contract_history_bkup where contract_main_sim_no ='"+ mainSimNo+"'";

            System.out.println("the query isssssssss " + sql);
            //debug(sql);
            ResultSet res = stat.executeQuery(sql);
            ContractHistoryModel tempContractHistoryModel = null;
            while (res.next()) {
                if (tempContractHistoryModel == null) {
                    System.out.println("111111111111111111111111");
                    tempContractHistoryModel = new ContractHistoryModel(res);
                    contractHistoryVec.add(tempContractHistoryModel);
                } else if (tempContractHistoryModel.getContractStatusId().compareTo(res.getString(ContractHistoryModel.CONTRACT_STATUS_ID)) == 0) {
                    System.out.println("22222222222222222222222");
                    String newWarning = res.getString(ContractHistoryModel.CONTRACT_WARNING_NAME);
                    if (newWarning != null) {
                        String oldWarning = tempContractHistoryModel.getContractWarningName();
                        if (oldWarning.length() > 0) {
                            tempContractHistoryModel.setContractWarningName(oldWarning + ", " + newWarning);
                        } else {
                            tempContractHistoryModel.setContractWarningName(newWarning);
                        }
                    }
                } else {
                    System.out.println("33333333333333333333333333333333333");
                    tempContractHistoryModel = new ContractHistoryModel(res);
                    contractHistoryVec.add(tempContractHistoryModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  if (stat!=null)try{stat.close();}catch(Exception e){}
            if (con != null) {
                try {
                    Utility.closeConnection(con);
                } catch (Exception e) {
                }
            }
        }
        return contractHistoryVec;
    }

    /*
     * 19- update contract warning status the method update the contract warning
     * of the id sent to the status sent
     */
    public static void updateContractWarningStatus(String argWarningID, String argStatusID, String argSuggestedStatusID, Connection con) {
        String sql = "update CR_CONTRACT_WARNING_TYPE set WARNING_STATUS_ID=? "
                + " , WARNING_SUGGESTED_STATUS_ID = ? where CONTRACT_WARNING_TYPE_ID = ?";
        DBUtil.executePreparedStatment(sql, con, argStatusID, argSuggestedStatusID, argWarningID);
    }

    /*
     * 20-is contract has warning in verification this method check if the
     * contract designated by the contract main sim no had a warning on it in
     * the verification stage or not the method retuirn a boolean either true or
     * false
     */
    public static boolean isContractHasWarningInVerification(String contractMainSimNo) {
        String sql = "select 1 from VW_cr_CONTRACT_HIST_VERFIY where contract_Status_type_name='" + ContractModel.STATUS_REJECTED_VERIFY + "'and  contract_main_sim_no='" + contractMainSimNo + "'";
        boolean flag = DBUtil.executeSQLExistCheck(sql);
        return flag;
    }

    /*
     * 21 - is contract has warning in the authentication phase takes an input
     * string contract main sim no and return a true if the contract has a
     * warning in this phase false other wise
     */
    public static boolean isContractHasWarningInAuthentication(String contractMainSimNo) {
        String sql = "select 1 from VW_CR_CONTRACT_HIST_AUTH where contract_Status_type_name='" + ContractModel.STATUS_REJECTED_AUTHINTICATION + "'   and  contract_main_sim_no='" + contractMainSimNo + "'";
        boolean flag = DBUtil.executeSQLExistCheck(sql);
        return flag;
    }

    /*
     * 22- get pos question flag this method check the sheet type of the sheet
     * id sent to it and return the boolean value of the flag which decide if
     * the pos question apply to this sheet type or not
     */
    public static boolean getPOSQuestionFlag(String argSheetID) {
        boolean flag = false;
        try {
            Connection con = Utility.getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT POS_QUESTION FROM vw_cr_sheet, VW_CR_SHEET_TYPE"
                    + " where vw_cr_sheet.SHEET_TYPE_ID = vw_cr_sheet_type.SHEET_TYPE_ID"
                    + " and sheet_id =" + argSheetID;
            System.out.println("The query issssssssssssssss " + sql);
            //debug(sql);
            ResultSet res = stmt.executeQuery(sql);
            if (res.next()) {
                flag = res.getBoolean("POS_QUESTION");
            }
            stmt.close();
            Utility.closeConnection(con);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /*
     * 23- get batch sheet contracts random sample return the randome sammple
     * chosen contracts of this sheet in this batch as ContractDto object also
     * the method take an array of the contracts that are alerady dispalyed to
     * the user so the method union them with the random sample and insure that
     * the generate sample is not one of the already displayed to the user the
     * percentage param is the percentage of contracts needed to commission
     * which is one of the properties of the sheet type
     */
    public static ContractDto getBatchSheetContractRandomSample(String sheetId, int total, int percentage, String[] alreadyChosenContracts) {


        try {
            Hashtable prevRejectedContractsInAuthentication = getContractsListPrevioslyRejected(sheetId, ContractModel.STATUS_REJECTED_AUTHINTICATION);

            //Utility.logger.debug(" time to get getBatchSheetContractRandomSample   = "+  (System.currentTimeMillis() - startTime));

            //startTime = System.currentTimeMillis();

            //debug ( " sheet id = " + sheetId) ;
            //debug("percentage = " + percentage + "   total = " + total );

            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = null;

            //constructing the list of already chosen contracts 
            String contractsChosen = "(";
            if (alreadyChosenContracts != null) {
                for (int i = 0; i < alreadyChosenContracts.length; i++) {
                    contractsChosen += alreadyChosenContracts[i] + ",";
                }
            }
            if (contractsChosen.length() > 1) {
                contractsChosen = contractsChosen.substring(0, contractsChosen.length() - 1);
                contractsChosen += ")";
            }
            //debug(contractsChosen);      

            //selecing all  the contracts that are eligable for contract authentication and not 
            //authenticated yet and 
            //not dispalyed alreayd to the user     
            String sql = "select count(*) count_rows from VW_CR_CONTRACT_STATUS_REIMPORT ,vw_gen_product "
                    + " where  VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_PHASE_NAME = 'CONTRACT VERIFICATION' "
                    + " and VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
                    + " and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId
                    + " and vw_gen_product.product_id = VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
                    + " and vw_gen_product.product_category_name='PREPAID' ";


            if (contractsChosen.length() > 2) {
                sql += " and VW_CR_CONTRACT_STATUS_REIMPORT.contract_id not in " + contractsChosen;
            }



            //getting the percentage of contracts needed to be authenticated
            double percentageOfContract = (double) percentage / (double) 100;
            //number of contracts needed to authenticate

            int randomSampleSize = (int) (Math.ceil((double) percentageOfContract * (double) total));

            //random sample size initializing


            debug(sql);
            debug("percentageofcontract  = " + percentageOfContract);
            //debug("random sample size in contract dao= "+ randomSampleSize);

            res = stat.executeQuery(sql);
            res.next();

            //Utility.logger.debug(" 2  = " + (System.currentTimeMillis() - startTime));  
            //startTime = System.currentTimeMillis();


            //Utility.logger.debug(sql);

            //this is the number of contracts eligable for authentication and not dispalyed 
            int totalRowCount = res.getInt("count_rows");
            //Utility.logger.debug("total = "+ total);

            //if that total row count is less than the random sample size 
            //that mean we can't fulfil its requirment so we =just sleect all of the contracts 
            if (totalRowCount < randomSampleSize) {
                sql = "select *  from VW_CR_CONTRACT_STATUS_REIMPORT ,vw_gen_product "
                        + " where  VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_PHASE_NAME = 'CONTRACT VERIFICATION' "
                        + " and VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
                        + " and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId
                        + " and vw_gen_product.product_id = VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
                        + " and vw_gen_product.product_category_name='PREPAID' ";
            } else //here the total row count is greater thtan the sampel size so we can select ony the sample size
            {
                Hashtable rowIds = new Hashtable();
                Random randomGen = new Random(System.currentTimeMillis());
                //this mechanism is done to garantee no duplicates id are generated
                //debug("********************");
                //debug("randomSampleSize = " + randomSampleSize);


                while (rowIds.size() < randomSampleSize) {
                    int newRandom = randomGen.nextInt(totalRowCount);
                    //debug ("new random index = " + newRandom);         

                    Integer newRandomInt = new Integer(newRandom + 1);
                    rowIds.put(newRandomInt, newRandomInt);
                }
                //debug("********************");
                Enumeration keys = rowIds.keys();

                //sql statmenet for selecting the random sample union with it the contracts that are already in display
                sql = "select * from ("
                        + " select VW_CR_CONTRACT_STATUS_REIMPORT.contract_id,rownum row_num  from vw_gen_product,VW_CR_CONTRACT_STATUS_REIMPORT"
                        + " where  VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_PHASE_NAME = 'CONTRACT VERIFICATION' "
                        + " and VW_CR_CONTRACT_STATUS_REIMPORT.CONTRACT_STATUS_TYPE_NAME ='ACCEPTED VERIFY' "
                        + " and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = " + sheetId
                        + " and vw_gen_product.product_id = VW_CR_CONTRACT_STATUS_REIMPORT.contract_type_id "
                        + " and vw_gen_product.product_category_name='PREPAID' ";

                if (contractsChosen.length() > 1) {
                    sql += " and  CONTRACT_ID not in " + contractsChosen;
                }

                sql += " ) ";

                System.out.println("rowIds size =" + rowIds.size());

                if (rowIds.size() > 0) {
                    sql += " where ";
                }

                String ids = null;
                while (keys.hasMoreElements()) {
                    Integer idInteger = (Integer) keys.nextElement();
                    if (ids == null) {
                        ids = " (row_num = " + idInteger.intValue();
                    } else {
                        ids += " or row_num = " + idInteger.intValue();
                    }
                }

                if (ids != null) {
                    sql += ids;
                    sql += ")";
                }
                //debug("ides = "+ ids);

            }
            //debug("percentage = " + randomSampleSize);
            debug(sql);

            if (contractsChosen.length() > 1) {
                contractsChosen = contractsChosen.substring(0, contractsChosen.length() - 1);
            }

            System.out.println(sql);
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%5");

            res = stat.executeQuery(sql);


            while (res.next()) {
                String contractId = res.getString("contract_id");

                if (contractsChosen.length() > 1) {
                    contractsChosen += "," + contractId;
                } else {
                    contractsChosen += contractId;
                }

            }
            contractsChosen += ")";
            ContractDto contractDto = new ContractDto();

            if (contractsChosen.length() > 2) {

                //sql to get the contracts 
                sql = "select * from VW_CR_CONTRACT_STATUS_REIMPORT , vw_CR_sheet where "
                        + "  VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id= " + sheetId
                        + " and VW_CR_CONTRACT_STATUS_REIMPORT.sheet_id = vw_CR_sheet.sheet_id "
                        + " and VW_CR_CONTRACT_STATUS_REIMPORT.contract_id in " + contractsChosen;

                //debug(sql);

                //Utility.logger.debug(sql);
                res = stat.executeQuery(sql);

                Statement statContractWarning = con.createStatement();

                while (res.next()) {
                    //filling contract model and adding it to contractdto
                    ContractModel contract = new ContractModel(res);
                    if (prevRejectedContractsInAuthentication.containsKey(contract.getMainSimNum())) {
                        contract.setContractPrevRejectedInAuthentication(true);
                    }
                    contractDto.addContractModel(contract);
                    String contractWarningSql = "select * from VW_CR_CONTRACT_WARNING"
                            + " where CONTRACT_STATUS_ID = " + contract.getContractLastStatusId()
                            + " and WARNING_STATUS_NAME = 'ACTIVE'"
                            + " and WARNING_TYPE_NAME = 'USER WARNING'";

                    //Utility.logger.debug(contractWarningSql);
                    ResultSet warningsSet = statContractWarning.executeQuery(contractWarningSql);
                    while (warningsSet.next()) {
                        contractDto.addContractWarning(contract.getId(), warningsSet);
                    }
                    warningsSet.close();
                }
                //Utility.logger.debug(" 4  = "+ (System.currentTimeMillis() - startTime));  
                //startTime = System.currentTimeMillis(); 

                statContractWarning.close();
            }
            res.close();
            stat.close();
            Utility.closeConnection(con);

            //Utility.logger.debug(" getBatchSheetContractRandomSample time = " +(System.currentTimeMillis() - startTime));
            return contractDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
     * debug method
     */
    private static void debug(String msg) {
        if (debugFlag) {
            Utility.logger.debug(msg);
        }
    }

    public static final String updateContractStatusRecord(String contractId, String userId, String sheetId, String batchId, String newStatus, String[] applied_user_warnings, Vector contractSystemWarningModelVector) {

        String statusId = null;
        Connection con = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            con = Utility.getConnection();
            stat = con.createStatement();
            String getNewSeqValue = "select seq_cr_contract_status.nextval from dual";
            res = stat.executeQuery(getNewSeqValue);
            //debug("getting next id time = " + (System.currentTimeMillis()-startTime));
            //Hagry to revisit 

            // debug (getNewSeqValue);
            if (res.next()) {
                statusId = res.getString(1);
            }

            //insert the new status 
            String insertSql = "insert into Cr_contract_status(contract_status_id, user_id, contract_id, contract_status_type_id, contract_status_date)"
                    + " values(" + statusId + "," + userId + "," + contractId + "," + newStatus + ",sysdate)";
            System.out.println("The insert query isssssssssssssss " + insertSql);

            stat.execute(insertSql);


            if (applied_user_warnings != null && (applied_user_warnings.length != 0) && (applied_user_warnings[0] != null) && (applied_user_warnings[0].length() != 0)) {
                for (int i = 0; i < applied_user_warnings.length; i++) {

                    String userWarning = "insert into vw_cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                            + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + applied_user_warnings[i] + ")";
                    debug(userWarning);
                    System.out.println("The insert warning query isssssssssssssss " + userWarning);
                    stat.execute(userWarning);
                }
            }

            if (contractSystemWarningModelVector != null) {
                //inserting system warning
                for (int j = 0; j < contractSystemWarningModelVector.size(); j++) {
                    ContractWarningModel contractWarning = (ContractWarningModel) contractSystemWarningModelVector.elementAt(j);
                    String systemWarning = "insert into vw_cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                            + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + contractWarning.getContractWarningID() + ")";

                    System.out.println("The insert system Warning query isssssssssssssss " + systemWarning);
                    //debug(systemWarning);
                    stat.execute(systemWarning);
                }
            }
            // con.commit(); 


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    Utility.closeConnection(con);
                } catch (Exception e) {
                }
            }
        }
        return statusId;
    }

    public static final String updateContractRecord(String contractId, String userId, String sheetId, String batchId, String newStatus, String[] applied_user_warnings, Vector contractSystemWarningModelVector, Connection con) {

        String statusId = null;
        Statement stat = null;
        ResultSet res = null;
        try {
            stat = con.createStatement();
            String getNewSeqValue = "select seq_cr_contract_status.nextval from dual";
            res = stat.executeQuery(getNewSeqValue);

            //Hagry to revisit 

            // debug (getNewSeqValue);
            if (res.next()) {
                statusId = res.getString(1);
            }

            //insert the new status 
            String insertSql = "insert into Cr_contract_status(contract_status_id, user_id, contract_id, contract_status_type_id, contract_status_date)"
                    + " values(" + statusId + "," + userId + "," + contractId + "," + newStatus + ",sysdate)";


            stat.execute(insertSql);


            if (applied_user_warnings != null && (applied_user_warnings.length != 0) && (applied_user_warnings[0] != null) && (applied_user_warnings[0].length() != 0)) {
                for (int i = 0; i < applied_user_warnings.length; i++) {

                    String userWarning = "insert into cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                            + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + applied_user_warnings[i] + ")";
                    //	debug(userWarning);

                    stat.execute(userWarning);
                }
            }

            if (contractSystemWarningModelVector != null) {
                //inserting system warning
                for (int j = 0; j < contractSystemWarningModelVector.size(); j++) {
                    ContractWarningModel contractWarning = (ContractWarningModel) contractSystemWarningModelVector.elementAt(j);
                    String systemWarning = "insert into cr_contract_warning(contract_warning_id,contract_status_id,contract_warning_date,contract_warning_type_id)"
                            + " values(seq_cr_contract_warning.nextval," + statusId + ",sysdate," + contractWarning.getContractWarningID() + ")";
                    //debug(systemWarning);
                    stat.execute(systemWarning);
                }
            }
            // con.commit(); 


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }

        }
        return statusId;
    }

    public static final boolean updateContractWarningChecked(String contractId, Connection con) {
        boolean returnvalue = false;

        try {

            Statement stat = con.createStatement();

            String updatetSql = "update CR_CONTRACT set CONTRACT_SYS_WARNING_CHECKED = 0 where CONTRACT_ID = " + contractId + " ";

            stat.execute(updatetSql);

            returnvalue = true;

            stat.close();

        } catch (Exception e) {
            returnvalue = false;
            e.printStackTrace();
        } finally {
            //       if (stat!=null)try{stat.close();}catch(Exception e){}
        }
        return returnvalue;
    }

    public static final boolean deleteContractSystemWarning(String contractId, Connection con) {
        boolean returnvalue = false;

        try {

            Statement stat = con.createStatement();
            String updatetSql = "delete from CR_CONTRACT_SYSTEM_WARNING where CONTRACT_ID = " + contractId;


            stat.execute(updatetSql);

            returnvalue = true;

            stat.close();

        } catch (Exception e) {
            returnvalue = false;
            e.printStackTrace();
        } finally {
            //   if (stat!=null)try{stat.close();}catch(Exception e){}
        }
        return returnvalue;
    }

    public static ContractWarningModel getVwCrContractWarningType(Connection con, String contractWarningTypeId) {
        ContractWarningModel contractWarningModel = null;
        // Connection con=null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_CR_CONTRACT_WARNING_TYPE where CONTRACT_WARNING_TYPE_ID = " + contractWarningTypeId;
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                contractWarningModel = new ContractWarningModel();
                contractWarningModel.setContractWarningID(res.getString("CONTRACT_WARNING_TYPE_ID"));
                contractWarningModel.setContractWarningName(res.getString("CONTRACT_WARNING_TYPE_NAME"));
                contractWarningModel.setContractwarningDesc(res.getString("CONTRACT_WARNING_TYPE_DESC"));
                contractWarningModel.setContractPhaseID(res.getString("PHASE_ID"));
                contractWarningModel.setContractWarningTypeID(res.getString("WARNING_TYPE_ID"));
                contractWarningModel.setContractwarningStatusID(res.getString("WARNING_STATUS_ID"));
                contractWarningModel.setContractwarningSuggestedStatusID(res.getString("WARNING_SUGGESTED_STATUS_ID"));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractWarningModel;
    }

    public static void updateVwCrContractWarningType(Connection con, String contractWarningTypeId, String contractWarningTypeName, String contractWarningTypeDesc, String contractWarningStatusId, String contractWarningSuggestedStatusId) {
        String strSql = "update CR_CONTRACT_WARNING_TYPE set CONTRACT_WARNING_TYPE_NAME = '" + contractWarningTypeName + "' "
                + " ,CONTRACT_WARNING_TYPE_DESC = '" + contractWarningTypeDesc + "' "
                + " ,WARNING_STATUS_ID = " + contractWarningStatusId + " "
                + " ,WARNING_SUGGESTED_STATUS_ID = " + contractWarningSuggestedStatusId + " "
                + " where CONTRACT_WARNING_TYPE_ID = " + contractWarningTypeId;

        DBUtil.executeSQL(strSql, con);

    }

    public static void insertVwCrContractWarningType(Connection con, String contractWarningTypeId, String contractWarningTypeName, String contractWarningTypeDesc, String contractWarningStatusId, String contractWarningSuggestedStatusId, String warningPhaseId, String warningTypeId) {
        String strSql = "insert into CR_CONTRACT_WARNING_TYPE(CONTRACT_WARNING_TYPE_ID,CONTRACT_WARNING_TYPE_NAME,CONTRACT_WARNING_TYPE_DESC,WARNING_STATUS_ID,WARNING_SUGGESTED_STATUS_ID,PHASE_ID,WARNING_TYPE_ID) "
                + " values(" + contractWarningTypeId + ",'" + contractWarningTypeName + "','" + contractWarningTypeDesc + "'," + contractWarningStatusId + "," + contractWarningSuggestedStatusId + "," + warningPhaseId + "," + warningTypeId + ")   ";
        DBUtil.executeSQL(strSql, con);
    }

    public static HashMap getTmpCrArchivingContracts(Connection con) {
        HashMap contractsHashMap = new HashMap();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from TMP_CR_ARCHIVING_CONTRACTS ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String contractMainSIMNo = res.getString("CONTRACT_MAIN_SIM_NO");
                String contractWarningTypeName = res.getString("CONTRACT_WARNING_TYPE_NAME");
                if (contractWarningTypeName == null) {
                    contractWarningTypeName = "";
                }
                //         String rowNum = res.getString("ROW_NUM");
                contractsHashMap.put(contractMainSIMNo, contractWarningTypeName);
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractsHashMap;
    }

    public static void deleteTmpCrArchivingContracts(Connection con) {
        String strSql = "delete from TMP_CR_ARCHIVING_CONTRACTS ";
        DBUtil.executeSQL(strSql, con);
    }

    public static boolean getBatchOpenedForArchiving(Connection con, String batchId) {
        String strSql = "select 1 from CR_BATCH where batch_id = " + batchId + " and phase_id = 3 and archiving_flag  = 1";
        boolean batchExist = DBUtil.executeSQLExistCheck(strSql, con);
        return batchExist;
    }

    public static HashMap getContractWarningTypeId(Connection con, String suggestedStatus) {
        HashMap contractWarningTypes = null;
        String strSql = "select CONTRACT_WARNING_TYPE_NAME, CONTRACT_WARNING_TYPE_ID from cr_contract_warning_type where phase_id = 3 and warning_status_id = 1 and warning_type_id = 1 and warning_suggested_status_id = " + suggestedStatus;
        contractWarningTypes = DBUtil.getMap(con, strSql);
        return contractWarningTypes;
    }

    public static HashMap getAllContractSystemWarningTypes(Connection con) {
        HashMap contractSystemWarningTypes = new HashMap();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from vw_cr_contract_warning_type where warning_type_id in (2,3) and warning_status_id = 1 ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                //    String warningTypeId = res.getString("CONTRACT_WARNING_TYPE_ID");
                String warningTypeName = res.getString("CONTRACT_WARNING_TYPE_NAME");
                contractSystemWarningTypes.put(warningTypeName, new ContractWarningModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contractSystemWarningTypes;
    }

    public static boolean checkBatchExist(Connection con, String batchId) {
        String strSql = "select 1 from CR_BATCH where batch_id = " + batchId;
        boolean batchExist = DBUtil.executeSQLExistCheck(strSql, con);
        return batchExist;
    }

    public static String getBatchDate(Connection con, String batchId) {
        String date = "";
        Statement stat;
        try {
            stat = con.createStatement();
            String strSql = "select BATCH_DATE from CR_BATCH where batch_id = " + batchId;
            System.out.println(strSql);
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                date = res.getString("BATCH_DATE");
            }
        } catch (Exception e) {
        }
        return date;
    }

    public static void chanegBatchDate(Connection con, String fromBatchId, String toBatchId, String date) {
        String strSql = "update CR_BATCH set BATCH_DATE=to_date('" + date + "'" + ",'mm/dd/yyyy')" + "where batch_id between " + fromBatchId + " and " + toBatchId;
        DBUtil.executeSQL(strSql, con);

    }

    public static void chanegBatchDateByID(Connection con, String batchId, String date) {
        String strSql = "update CR_BATCH set BATCH_DATE=to_date('" + date + "'" + ",'mm/dd/yyyy')" + "where batch_id =" + batchId;
        DBUtil.executeSQL(strSql, con);

    }

    public static void insertBatchHistory(String batchId, String userId, String action) {
        String strSql = "insert into CR_batch_history(batch_id,user_id,time_stamp,batch_date,STATUS)values"
                + "('" + batchId + "','" + userId + "',SYSDATE,(SELECT BATCH_DATE FROM CR_BATCH WHERE BATCH_ID = '" + batchId + "'),'" + action + "')";

        DBUtil.executeSQL(strSql);
    }

    public static void daleteBatch(String batchId) {
        Connection con = null;
        CallableStatement colableStat = null;
        try {
            con = Utility.getConnection();
            String sql = "begin SDS.delete_cr_batch(?); end;";
            colableStat = con.prepareCall(sql);
            colableStat.setInt(1, Integer.parseInt(batchId.trim()));
            colableStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(colableStat);
            DBUtil.close(con);
        }

    }
}
