package com.mobinil.sds.core.system.cr.sheet.dao;

import java.sql.*;

import oracle.jdbc.driver.OracleTypes;
import com.mobinil.sds.core.utilities.*;

import java.util.*;

import com.mobinil.sds.core.system.cr.sheet.model.*;
import com.mobinil.sds.core.system.cr.sheet.dto.*;
import com.mobinil.sds.core.system.cr.contract.dao.*;
import com.mobinil.sds.web.interfaces.cr.*;
import com.mobinil.sds.core.system.cr.batch.dao.*;
import com.mobinil.sds.core.system.cr.contract.model.*;
import com.mobinil.sds.core.system.cr.warning.model.*;
import com.mobinil.sds.core.system.cr.sheet.dto.SheetTypeDto;
import com.mobinil.sds.core.system.sfr.sheets.model.MonthsModel;
import com.mobinil.sds.web.interfaces.cr.*;

/*
 * the constructor is private so that no one can instantiate any instiance of this object 
 * this was inteneded cause no one make any instance of this object
 * and use all the static methods
 * 1- get sheet status types by phase in a SheetStatusTypeDto
 * 2-update sheet status in the contract verification phase 
 * this is the method to call when updateing a sheet status from any of the contract verfication screens 
 * 3- get sheet contract verification statistic model this method return a  SheetContractVerificationStatisticModel
 * object of the sheet its id sent to the method 
 * 4- get sheet Model by sheet id 
 * 5-get sheet model based on the sheet serial number pos code super dealer code
 * 6- get sheet type status
 * 7- get sheet types    
 * 8- get sheet types Vector of SheetTypeModel objects
 * 9-a debug method taht print its message if the debug flag is set to true 
 * 10- update sheet warning status 
 */

public class SheetDao
    {
        /*
         * TRUN THIS FLAG TO TRUE IF U WANT TO DEBUG THIS CODE dont forget to
         * turn it back to false before deployment casue it slow the system when
         * it is true since it prints alot of debuging messages
         */
        private static boolean debugFlag = false;

        private SheetDao()
            {
            }

        public static boolean isSheetReImported(String sheetId,
                String sheetSerialId)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = "select SDS_GET_sheet_REIMPORT_FLAG("
                                + sheetSerialId + "," + sheetId
                                + ") SHEET_RE_IMPORT_FLAG  from dual";
                        ResultSet res = stat.executeQuery(sql);
                        res.next();
                        boolean sheetReimportFlag = res.getBoolean(SheetModel.SHEET_RE_IMPORT_FLAG);

                        stat.close();
                        Utility.closeConnection(con);
                        return sheetReimportFlag;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return true;
            }

        /*
         * public static SheetStatisticContractsTypes
         * getSheetStatisticContractsTypes(String sheetId) { try { Connection
         * con = Utility.getConnection(); Statement stat =
         * con.createStatement(); String sql =
         * "select PRODUCT_NAME , count (contract_id) contracts_count from vw_cr_contract contract,vw_gen_product product "
         * +" where contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
         * +" sheet_id = " + sheetId +" group by PRODUCT_NAME";
         * SheetStatisticContractsTypes sheetStat = new
         * SheetStatisticContractsTypes(stat.executeQuery(sql));
         * Utility.closeConnection(con); return sheetStat; } catch (Exception e)
         * { e.printStackTrace(); } return null; }
         */

        /*
         * 1- get sheet status types by phase in a SheetStatusTypeDto
         */
        public static final SheetStatusTypeDto getSheetStatusTypeByPhase(
                String phaseName, Connection con)
            {
                try
                    {

                        Statement stat = con.createStatement();
                        ResultSet res = stat.executeQuery("select * from vw_cr_sheet_status_type,vw_cr_phase where vw_cr_sheet_status_type.PHASE_ID = vw_cr_phase.PHASE_ID and  vw_cr_phase.phase_name=\'"
                                + phaseName
                                + "\' order by sheet_status_type_name");
                        SheetStatusTypeDto sheetStatusTypeDto = new SheetStatusTypeDto();
                        while (res.next())
                            {
                                String sheetStatusTypeName = res.getString("sheet_Status_type_name");
                                int sheetStatusTypeId = res.getInt("sheet_Status_type_id");
                                sheetStatusTypeDto.addSheetStatusTypeModel(new SheetStatusTypeModel(sheetStatusTypeId, sheetStatusTypeName));
                            }

                        return sheetStatusTypeDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        /*
         * 2-update sheet status this is the method to call when updateing a
         * sheet status
         */
        public static void updateSheetStatus(String sheetId, String batchId,
                String userId, Connection con)
            {
                // debug("updateSheetStatus updateSheetStatus ");
                try
                    {

                        Statement stat = con.createStatement();

                        String sql = "select contract_status_type_name,count(*) contract_status_type_count from   VW_CR_CONTRACT_ID_LAST_STATUS "
                                + " where sheet_id = "
                                + sheetId
                                + " group by contract_status_type_name";

                        ResultSet res = stat.executeQuery(sql);

                        Hashtable statusTable = new Hashtable();
                        int total = 0;
                        while (res.next())
                            {
                                String statusName = res.getString("contract_status_type_name");
                                Integer statusCount = new Integer(res.getString("contract_status_type_count"));
                                statusTable.put(statusName, statusCount);
                                total += statusCount.intValue();
                            }
                        res.close();

                        sql = "select * from vw_CR_sheet, vw_CR_phase where vw_CR_sheet.phase_id = vw_CR_phase.phase_id and  sheet_id = "
                                + sheetId;
                        res = stat.executeQuery(sql);
                        res.next();
                        String currentSheetPhase = res.getString("phase_name");

                        // count of how many contracts in this sheet of each
                        // status

                        Integer rejectedImportCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_IMPORT);
                        int rejectedImportCount = (rejectedImportCountInt == null
                                ? 0
                                : rejectedImportCountInt.intValue());
                        Integer newStatusCountInt = (Integer) statusTable.get(ContractModel.STATUS_IMPORTED);
                        int newStatusCount = (newStatusCountInt == null
                                ? 0
                                : newStatusCountInt.intValue());
                        Integer acceptedDeliveryCountInt = (Integer) statusTable.get(ContractModel.STATUS_ACCEPTED_DELIVERY);
                        int acceptedDeliveryCount = (acceptedDeliveryCountInt == null
                                ? 0
                                : acceptedDeliveryCountInt.intValue());
                        Integer rejectedDeliveryCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_DELIVERY);
                        int rejectedDeliveryCount = (rejectedDeliveryCountInt == null
                                ? 0
                                : rejectedDeliveryCountInt.intValue());
                        Integer acceptedVerifyCountInt = (Integer) statusTable.get(ContractModel.STATUS_ACCEPTED_VERIFY);
                        int acceptedVerifyCount = (acceptedVerifyCountInt == null
                                ? 0
                                : acceptedVerifyCountInt.intValue());
                        Integer rejectedVerifyCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_VERIFY);
                        int rejectedVerifyCount = (rejectedVerifyCountInt == null
                                ? 0
                                : rejectedVerifyCountInt.intValue());
                        Integer acceptedAuthinticationCountInt = (Integer) statusTable.get(ContractModel.STATUS_ACCEPTED_AUTHINTICATION);
                        int acceptedAuthinticationCount = (acceptedAuthinticationCountInt == null
                                ? 0
                                : acceptedAuthinticationCountInt.intValue());
                        Integer rejectedAuthinticationCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_AUTHINTICATION);
                        int rejectedAuthinticationCount = (rejectedAuthinticationCountInt == null
                                ? 0
                                : rejectedAuthinticationCountInt.intValue());

                        String statusSQL = "";
                        String updatedPhaseName = "";
                        if ((newStatusCount == 0)
                                && (rejectedVerifyCount + rejectedImportCount
                                        + rejectedDeliveryCount == total))
                            {
                                // rejected contract verification , phase =
                                // contract
                                // verification
                                statusSQL = "select * from vw_cr_sheet_status_type where SHEET_status_type_name=\'"
                                        + SheetModel.STATUS_REJECTED_VERIFY
                                        + "\'";
                                updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
                            } else if ((newStatusCount == 0)
                                && (acceptedVerifyCount > 0)
                                && (acceptedDeliveryCount == 0)
                                && (acceptedAuthinticationCount == 0)
                                && (rejectedAuthinticationCount == 0))
                            {
                                // accepted verify , phase = contract
                                // verification phase
                                statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                        + SheetModel.STATUS_ACCEPTED_VERIFY
                                        + "\'";
                                updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
                            } else if ((newStatusCount == 0)
                                && (acceptedVerifyCount == 0)
                                && (acceptedDeliveryCount == 0)
                                && (acceptedAuthinticationCount == 0)
                                && (rejectedVerifyCount + rejectedImportCount
                                        + rejectedDeliveryCount
                                        + rejectedAuthinticationCount == total))
                            {
                                // rejected authentication , phase =
                                // authentication call
                                statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                        + SheetModel.STATUS_REJECTED_AUTHINTICATION
                                        + "\'";
                                updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
                            } else if ((newStatusCount == 0)
                                && (acceptedDeliveryCount == 0)
                                && ((acceptedAuthinticationCount > 0) || (currentSheetPhase.compareTo(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE) == 0)))
                            {

                                SheetAuthinticationStatisticModel sheetAuthinticationStatisticModel = SheetDao.getSheetAuthinticationStatisticModel(sheetId, con);

                                if (sheetAuthinticationStatisticModel != null)
                                    {
                                        int totalAccepted = sheetAuthinticationStatisticModel.getTotalContractsAuthinticated();
                                        int totalRejected = sheetAuthinticationStatisticModel.getTotalContractsRejectedAuthinticated();

                                        if (sheetAuthinticationStatisticModel.getDiscardUnknownUnreachble())
                                            {
                                                int totalRejectedUnreachable = sheetAuthinticationStatisticModel.getTotalRejectedUnreachable();
                                                int totalAcceptedUnReachable = sheetAuthinticationStatisticModel.getTotalAcceptedUnrechable();
                                                int totalRejectedPOSUnknown = sheetAuthinticationStatisticModel.getTotalRejectedPosUnknown();
                                                int totalAcceptedPOSUnknow = sheetAuthinticationStatisticModel.getTotalAcceptedPosUnknown();

                                                int totalDescarded = totalRejectedUnreachable
                                                        + totalAcceptedUnReachable
                                                        + totalRejectedPOSUnknown
                                                        + totalAcceptedPOSUnknow;

                                                // this if statment check that
                                                // we have one contract
                                                // which is accepted
                                                // authentication
                                                // and check the value of the
                                                // number of contracts
                                                // authinticated after we remove
                                                // the number of
                                                // contracts discarded either
                                                // pos unknonw or unreachable
                                                // we compare the result of that
                                                // substraction
                                                // to the number of contracts
                                                // needed to allow commission
                                                if ((totalAccepted >= 1)
                                                        && (totalAccepted
                                                                + totalRejected - totalDescarded) >= sheetAuthinticationStatisticModel.getTotalContractsNeededToCommission())
                                                    {
                                                        // accepted commission
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                                                + "\'";
                                                    }
                                                /*
                                                 * this check that one contract
                                                 * is accpeted and that all teh
                                                 * contracts in this sheet are
                                                 * discarded since they are
                                                 * either unreachable or unknown
                                                 */
                                                else if ((totalAccepted >= 1)
                                                        && (totalDescarded == sheetAuthinticationStatisticModel.getTotalContractsEligableForAuthintication()))
                                                    {
                                                        // accepted commission
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                                                + "\'";
                                                    }
                                                // if both the above conditions
                                                // are not valid then it go
                                                // here where it set the status
                                                // to
                                                // accepted authinticaiton
                                                else
                                                    {
                                                        // accepted verify
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                                                + "\'";
                                                    }
                                            } else
                                            {
                                                // debug("case 3");
                                                if ((totalAccepted >= 1)
                                                        && ((totalAccepted + totalRejected) >= sheetAuthinticationStatisticModel.getTotalContractsNeededToCommission()))
                                                    {
                                                        // accepted commission
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                                                + "\'";
                                                    } else
                                                    {
                                                        // accepted verify
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                                                + "\'";
                                                    }
                                                // debug(statusSQL);
                                            }
                                    } else
                                    {
                                        // accepted authentication
                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                                + "\'";
                                    }
                                // update phase sql
                                updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
                            }

                        // stat =con.createStatement();

                        if (statusSQL.compareTo("") != 0)
                            {

                                ResultSet resStatus = stat.executeQuery(statusSQL);
                                resStatus.next();
                                int newStatusId = resStatus.getInt("sheet_status_type_id");

                                // this is to check if the old status of this
                                // sheet is the same
                                // as this new status
                                // then it doenst insert

                                String sqlOldStatus = "select * from vw_cr_sheet_last_status where sheet_id ="
                                        + sheetId;
                                resStatus = stat.executeQuery(sqlOldStatus);
                                boolean insertNewStatusFlag = true;
                                if (resStatus.next())
                                    {
                                        int status = resStatus.getInt("sheet_Status_type_id");
                                        if (status == newStatusId)
                                            {
                                                // should not insert since it is
                                                // the same old status
                                                insertNewStatusFlag = false;
                                            }
                                    }

                                String insertNewStatus = "insert into vw_cr_sheet_status_insert(sheet_status_id,sheet_id,sheet_status_type_id,user_id,sheet_status_date) "
                                        + " values(seq_cr_sheet_status.nextval,"
                                        + sheetId
                                        + ","
                                        + newStatusId
                                        + ","
                                        + userId + ",sysdate)";

                                // debug("sql = " + sql);
                                stat.execute(insertNewStatus);
                                sql = "select * from vw_CR_phase where phase_name ='"
                                        + updatedPhaseName + "'";
                                // debug("sql = " + sql);
                                res = stat.executeQuery(sql);
                                if (res.next())
                                    {
                                        String phaseId = res.getString("phase_id");
                                        String updatePhase = "update CR_sheet set phase_id="
                                                + phaseId
                                                + " where sheet_Id="
                                                + sheetId;
                                        // debug("update phase sql = " +
                                        // updatePhase);
                                        stat.execute(updatePhase);
                                    }

                                // con.commit();
                                // debug("sheet not changed "
                                // +insertNewStatusFlag );
                                if (insertNewStatusFlag)
                                    {
                                        // propagate the update to the batch
                                        // only if the sheet
                                        // status changed
                                        BatchDao.updateBatchStatus(batchId, con);
                                    }

                            }

                        DBUtil.close(stat);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }

        /*
         * 3- get sheet contract verification statistic model this method return
         * a SheetContractVerificationStatisticModel object of the sheet its id
         * sent to the method
         */
        public static SheetContractVerificationStatisticModel getSheetContractVerificationStatisticModel(
                String sheetId)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = " select count(contract.contract_id) from vw_cr_contract_status contract_status,vw_cr_contract contract, vw_cr_contract_status_type, vw_cr_phase, vw_gen_product,vw_CR_CUSTOMER_ID_TYPE"
                                + " where"
                                + " contract.CONTRACT_ID = contract_status.CONTRACT_ID"
                                + " and contract.sheet_id = "
                                + sheetId
                                + " and  contract.CONTRACT_TYPE_ID = vw_gen_product.PRODUCT_ID(+)"
                                + " and contract.CONTRACT_CUSTOMER_ID_TYPE = vw_CR_CUSTOMER_ID_TYPE.CUSTOMER_ID_TYPE_ID(+)"
                                + " and vw_cr_contract_status_type.CONTRACT_STATUS_TYPE_ID = contract_status.CONTRACT_STATUS_TYPE_ID"
                                + " and vw_cR_phase.PHASE_ID = vw_cr_contract_status_type.PHASE_ID "
                                + " and contract_status.CONTRACT_STATUS_ID = (select contract_status_id from (select * from vw_cr_contract_status  order by contracT_status_date desc)contract_status_history where rownum<=1 and  contract_status_history.CONTRACT_ID = contract.CONTRACT_ID)"
                                + " and (phase_name = 'CONTRACT VERIFICATION')";
                        ResultSet res = stat.executeQuery(sql);
                        res.next();
                        SheetContractVerificationStatisticModel temp = new SheetContractVerificationStatisticModel(res.getInt(1));
                        stat.close();
                        Utility.closeConnection(con);
                        return temp;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        /*
         * 4- get sheet Model by sheet id
         */
        public static SheetModel getSheetById(String sheetId)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = "select * from vw_cr_sheet_last_status where sheet_id = "
                                + sheetId;
                        Utility.logger.debug(sql);
                        ResultSet res = stat.executeQuery(sql);
                        if (!res.next())
                            {
                                Utility.closeConnection(con);
                                return null;
                            }

                        // SheetModel sheetModel =new
                        // SheetModel(sheetId,contractcount,sheetName,sheetTypeId,sheetTypeName,sheetPosCode,sheetSuperDealerCode);

                        SheetModel sheetModel = new SheetModel(res);

                        // int lastSheetStatusTypeId =
                        // res.getInt("sheet_status_type_id");
                        // String lastSheetStatusName =
                        // res.getString("sheet_status_type_name");
                        // sheetModel.setSheetStatusId(lastSheetStatusTypeId);
                        // sheetModel.setSheetStatusName(lastSheetStatusName);

                        int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                        // contract count statistic of the sheet
                        String sqlSheetContractsCount = "select PRODUCT_NAME , count (contract_id) contracts_count from vw_cr_contract contract,vw_gen_product product "
                                + " where contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
                                + " and sheet_id = "
                                + sheetId
                                + " group by PRODUCT_NAME";
                        ResultSet resStatistic = stat.executeQuery(sqlSheetContractsCount);
                        // debug(sqlSheetContractsCount);

                        sheetModel.setContractsTypesCount(resStatistic);

                        resStatistic.close();
                        Utility.closeConnection(con);
                        return sheetModel;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                // return null in case of exception
                return null;
            }

        public static SheetModel getSheetBySerialNumberAndBatchId(
                String batchId, String sheetSerialNumber)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = "select * from vw_cr_sheet_last_status where sheet_serial_id = "
                                + sheetSerialNumber
                                + " and batch_id = "
                                + batchId;
                        ResultSet res = stat.executeQuery(sql);
                        if (!res.next())
                            {
                                Utility.closeConnection(con);
                                return null;
                            }

                        // SheetModel sheetModel =new
                        // SheetModel(sheetId,contractcount,sheetName,sheetTypeId,sheetTypeName,sheetPosCode,sheetSuperDealerCode);

                        SheetModel sheetModel = new SheetModel(res);
                        sheetModel.setSheetLocalAuthPercentage(res.getString("SHEET_LOCAL_AUTH_PERCENTAGE"));
                        // int lastSheetStatusTypeId =
                        // res.getInt("sheet_status_type_id");
                        // String lastSheetStatusName =
                        // res.getString("sheet_status_type_name");
                        // sheetModel.setSheetStatusId(lastSheetStatusTypeId);
                        // sheetModel.setSheetStatusName(lastSheetStatusName);

                        // int lastSheetStatusRecordId =
                        // res.getInt("sheet_status_id");

                        // contract count statistic of the sheet
                        String sqlSheetContractsCount = "select PRODUCT_NAME , count (contract_id) contracts_count from vw_cr_contract contract,vw_gen_product product "
                                + " where contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
                                + " and sheet_serial_id = "
                                + sheetSerialNumber
                                + " and batch_id = "
                                + batchId
                                + " group by PRODUCT_NAME";
                        // ResultSet resStatistic =
                        // stat.executeQuery(sqlSheetContractsCount);
                        // debug(sqlSheetContractsCount);

                        // sheetModel.setContractsTypesCount(resStatistic);

                        // resStatistic.close();
                        Utility.closeConnection(con);
                        return sheetModel;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                // return null in case of exception
                return null;
            }

        public static void deleteFromSheetPercentageTmp()
            {
                String sql = "delete from TMP_SHEET_PERCENTAGE";
                DBUtil.executeSQL(sql);

            }

        /**
         * 5- verify Sheet based on the sheet serial number pos code super
         * dealer code which just return the model object if the sheet exist
         * with the pos and super dealer code
         * 
         * @param String
         *            sheetSerialNumber , String sheetPOSCode, String
         *            sheetSuperDealerCode
         * @throws
         * @return SheetModel or null in case of exception
         * @see
         */
        public static SheetModel verifySheet(String sheetSerialNumber,
                String sheetPOSCode, String sheetSuperDealerCode,
                String batchId, Connection con)
            {
                try
                    {

                        Statement stat = con.createStatement();

                        /*
                         * this sql was abandoned since we wanted to know if the
                         * sheet was re imported or not instead of just not
                         * showing the sheet String sql =
                         * "select * from vw_cr_sheet_last_status"+
                         * " where SHEET_SERIAL_ID = "+sheetSerialNumber+
                         * " and batch_id = "+ batchId +
                         * " and SHEET_POS_CODE = '"+sheetPOSCode+"'"+
                         * " and SHEET_SUPER_DEALER_CODE = '"
                         * +sheetSuperDealerCode+"'"+
                         * " and sheet_status_type_name not in ('"
                         * +SheetModel.STATUS_REJECTED_DELIVERY +
                         * "','"+SheetModel.STATUS_REJECTED_IMPORT+"')";
                         */

                        String sql = "select SDS_GET_sheet_REIMPORT_FLAG(sheet_serial_id, sheet_id) SHEET_RE_IMPORT_FLAG,"
                                + "  sheet_id, sheet_contract_count ,sheet_type_name ,sheet_type_id, batch_id , SHEET_STATUS_TYPE_ID , "
                                + "  SHEET_STATUS_TYPE_NAME , sheet_status_id ,SHEET_SERIAL_ID ,SHEET_STATUS_DATE , "
                                + "  SHEET_DISTRIBUTER_CODE , SHEET_POS_CODE , SHEET_SUPER_DEALER_CODE , PERSON_FULL_NAME "
                                + "  from     VW_CR_SHEET_ID_LAST_STATUS"
                                + " where SHEET_SERIAL_ID = "
                                + sheetSerialNumber
                                + " and batch_id = "
                                + batchId
                                + " and SHEET_POS_CODE = '"
                                + sheetPOSCode
                                + "'"
                                + " and SHEET_SUPER_DEALER_CODE = '"
                                + sheetSuperDealerCode
                                + "'"
                                + " and sheet_status_type_name not in ('"
                                + SheetModel.STATUS_REJECTED_DELIVERY
                                + "','"
                                + SheetModel.STATUS_REJECTED_IMPORT + "')";

                        ResultSet res = stat.executeQuery(sql);

                        // empty result set return null
                        if (!res.next())
                            {
                                stat.close();
                                return null;
                            }

                        // getting sheet model
                        String sheetId = res.getString("sheet_id");
                        int contractcount = res.getInt("sheet_contract_count");
                        String sheetTypeId = res.getString("sheet_type_id");
                        String sheetTypeName = res.getString("sheet_type_name");
                        String batchID = res.getString("batch_id");
                        int sheetStatusTypeID = res.getInt("SHEET_STATUS_TYPE_ID");
                        String sheetStatusTypeName = res.getString("SHEET_STATUS_TYPE_NAME");

                        SheetModel sheetModel = new SheetModel(res);

                        sheetModel.setSheetReImportFlag(res.getBoolean(SheetModel.SHEET_RE_IMPORT_FLAG));

                        int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                        // getting sheet contracts count

                        String sqlSheetContractsCount = "select PRODUCT_NAME , count (contract_id) contracts_count from vw_cr_contract contract,vw_gen_product product "
                                + " where contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
                                + " and sheet_id = "
                                + sheetId
                                + " group by PRODUCT_NAME";

                        ResultSet resStatistic = stat.executeQuery(sqlSheetContractsCount);

                        sheetModel.setContractsTypesCount(resStatistic);

                        resStatistic.close();
                        stat.close();

                        return sheetModel;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        /**
         * 18- get sheet
         * 
         * @param String
         *            sheetId
         * @throws
         * @return SheetDto or return null in case of error
         * @see
         */
        public static SheetDto getSheet(String sheetId)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        Statement statSheetStatistic = con.createStatement();
                        Statement statGetStatus = con.createStatement();

                        String sql = "select * from vw_CR_sheet_id_last_status where sheet_id = "
                                + sheetId;
                        // debug(sql);
                        ResultSet res = stat.executeQuery(sql);

                        SheetDto sheetDto = new SheetDto();
                        while (res.next())
                            {
                                // getting sheet information

                                String sheetName = res.getString("sheet_serial_id");
                                /*
                                 * int contractcount =
                                 * res.getInt("sheet_contract_count"); String
                                 * sheetTypeId = res.getString("sheet_type_id");
                                 * String sheetTypeName =
                                 * res.getString("sheet_type_name"); String
                                 * sheetPosCode =
                                 * res.getString("sheet_pos_code"); String
                                 * sheetSuperDealerCode =
                                 * res.getString("sheet_super_dealer_code");
                                 * 
                                 * SheetModel sheetModel =new
                                 * SheetModel(sheetId,contractcount,sheetName
                                 * ,sheetTypeId,sheetTypeName
                                 * ,sheetPosCode,sheetSuperDealerCode); int
                                 * lastSheetStatusTypeId =
                                 * res.getInt("sheet_status_type_id"); String
                                 * lastSheetStatusName =
                                 * res.getString("sheet_status_type_name");
                                 * sheetModel
                                 * .setSheetStatusId(lastSheetStatusTypeId);
                                 * sheetModel
                                 * .setSheetStatusName(lastSheetStatusName); int
                                 * lastSheetStatusRecordId =
                                 * res.getInt("sheet_status_id"); String
                                 * lastSheetStatusName =
                                 * res.getString("sheet_status_type_name");
                                 */
                                SheetModel sheetModel = new SheetModel(res);
                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                                // getting the warning of this sheet on this
                                // status
                                String sheetWarningSql = "select * from vw_cr_sheet_warning"
                                        + " where "
                                        + "  vw_cr_sheet_warning.sheet_status_id = "
                                        + lastSheetStatusRecordId;
                                ResultSet warningsSet = statSheetStatistic.executeQuery(sheetWarningSql);
                                // adding the warning to teh sheetDto
                                while (warningsSet.next())
                                    {
                                        sheetDto.addSheetWarning(sheetId, warningsSet);
                                    }
                                // getting the sheet contracts count information
                                // grouped by
                                // product name
                                // so it is number of each prodoctu type of
                                // contracts in this
                                // sheet

                                String sqlSheetContractsCount = "select PRODUCT_NAME , count (contract_id) contracts_count from vw_cr_contract contract,vw_gen_product product "
                                        + " where contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
                                        + " and sheet_id = "
                                        + sheetId
                                        + " group by PRODUCT_NAME";
                                // debug(sqlSheetContractsCount);
                                ResultSet resStatistic = statSheetStatistic.executeQuery(sqlSheetContractsCount);
                                // seting the contract types count
                                sheetModel.setContractsTypesCount(resStatistic);

                                resStatistic.close();
                                // add the sheetModel to the sheetDTO
                                sheetDto.addSheetModel(sheetModel);
                            }

                        statGetStatus.close();
                        statSheetStatistic.close();
                        stat.close();
                        Utility.closeConnection(con);
                        return sheetDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        public static SheetDto getSheetWithOutStatistic(String sheetId,
                Connection con)
            {
                try
                    {

                        Statement stat = con.createStatement();
                        Statement statSheetStatistic = con.createStatement();
                        Statement statGetStatus = con.createStatement();

                        String sql = "select * from vw_CR_sheet_id_last_status where sheet_id = "
                                + sheetId;
                        /*
                         * String sql =
                         * "SELECT  SHEET.SHEET_ID,SHEET.SHEET_SERIAL_ID,SHEET.SHEET_RECIEVE_DATE,SHEET.SHEET_DISTRIBUTER_CODE,SHEET.SHEET_SUPER_DEALER_CODE,SHEET.SHEET_POS_CODE, "
                         * +
                         * "  SHEET.SHEET_TIME_STAMP,SHEET.SHEET_CONTRACT_COUNT,SHEET.SHEET_TYPE_ID, sheet_status.sheet_status_id, "
                         * +
                         * " SHEET.SHEET_TYPE_NAME,SHEET.BATCH_ID,PHASE_ID ,SHEET.SHEET_last_STATUS_ID,SHEET_STATUS.SHEET_STATUS_TYPE_ID, SHEET_STATUS.SHEET_STATUS_TYPE_NAME,SHEET_STATUS.SHEET_STATUS_DATE,SHEET_STATUS.STATUS_TYPE_NAME  , "
                         * +
                         * " sheet_Status.user_id,vw_gen_user_details.PERSON_FULL_NAME,sheet_status.status_phase_name "
                         * +
                         * " FROM  vw_gen_user_details  , VW_CR_SHEET SHEET, VW_CR_SHEET_STATUS SHEET_STATUS "
                         * +" WHERE sheet.sheet_id = "+ sheetId
                         * +" and SHEET.SHEET_ID = SHEET_STATUS.SHEET_ID and "+
                         * " SHEET.SHEET_last_STATUS_ID = sheet_status.SHEET_STATUS_ID and   "
                         * +
                         * " sheet_Status.USER_ID = vw_gen_user_details.USER_ID  "
                         * ;
                         */
                        // debug(sql);
                        ResultSet res = stat.executeQuery(sql);

                        SheetDto sheetDto = new SheetDto();
                        while (res.next())
                            {
                                // getting sheet information

                                String sheetName = res.getString("sheet_serial_id");

                                SheetModel sheetModel = new SheetModel(res);
                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                                // getting the warning of this sheet on this
                                // status
                                String sheetWarningSql = "select * from vw_cr_sheet_warning"
                                        + " where "
                                        + "  vw_cr_sheet_warning.sheet_status_id = "
                                        + lastSheetStatusRecordId;
                                ResultSet warningsSet = statSheetStatistic.executeQuery(sheetWarningSql);
                                // adding the warning to teh sheetDto
                                while (warningsSet.next())
                                    {
                                        sheetDto.addSheetWarning(sheetId, warningsSet);
                                    }

                                // add the sheetModel to the sheetDTO
                                sheetDto.addSheetModel(sheetModel);
                            }

                        statGetStatus.close();
                        statSheetStatistic.close();
                        stat.close();

                        return sheetDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        /**
         * 17- get sheet Active user warning by phase
         * 
         * @param String
         *            phaseName
         * @throws
         * @return Vector of SheetWarningModel
         * @see
         */
        public static Vector getSheetActiveUserWarningByPhase(String phaseName,
                Connection con)
            {
                Vector warnings = new Vector();
                try
                    {
                        Statement stat = con.createStatement();

                        String sql = " select * from vw_CR_sheet_warning_type,vw_CR_phase "
                                + " where vw_CR_sheet_warning_type.PHASE_ID= vw_CR_phase.PHASE_ID "
                                + " and vw_CR_sheet_warning_type.PHASE_NAME ='"
                                + phaseName
                                + "'"
                                + " and vw_CR_sheet_warning_type.WARNING_STATUS_NAME = 'ACTIVE'";
                        ResultSet warningsSet = stat.executeQuery(sql);

                        while (warningsSet.next())
                            {
                                SheetWarningModel sheetWarning = new SheetWarningModel(warningsSet);
                                warnings.add(sheetWarning);
                            }

                        warningsSet.close();
                        stat.close();

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return warnings;
            }

        /**
         * 16- get Warning Statuses
         * 
         * @param String
         *            phaseName
         * @throws
         * @return Vector of WarningStatusModel
         * @see
         */
        public static Vector getWarningStatuses(Connection con)
            {

                Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_WARNING_STATUS);
                Vector warningStatuses = null;

                if (obj == null)
                    {

                        warningStatuses = new Vector();
                        try
                            {

                                Statement stat = con.createStatement();

                                String sql = " select * from VW_CR_WARNING_STATUS";

                                ResultSet warningStatusesSet = stat.executeQuery(sql);
                                while (warningStatusesSet.next())
                                    {
                                        String warningStatusId = warningStatusesSet.getString("WARNING_STATUS_ID");
                                        String warningStatusName = warningStatusesSet.getString("WARNING_STATUS_NAME");
                                        WarningStatusModel warningStatus = new WarningStatusModel(warningStatusId, warningStatusName);
                                        warningStatuses.add(warningStatus);
                                    }

                                stat.close();

                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_WARNING_STATUS, warningStatuses);
                    } else
                    {
                        warningStatuses = (Vector) obj;
                    }
                return warningStatuses;
            }

        public static Vector<WarningSuggestedStatusModel> getWarningSuggestedStatuses(
                Connection con)
            {

                Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_WARNING_SUGGESTED_STATUS);
                Vector<WarningSuggestedStatusModel> warningSuggestedStatuses = null;

                if (obj == null)
                    {

                        warningSuggestedStatuses = new Vector<WarningSuggestedStatusModel>();
                        try
                            {

                                Statement stat = con.createStatement();

                                String sql = " select * from CR_WARNING_SUGGESTED_STATUS";

                                ResultSet warningStatusesSet = stat.executeQuery(sql);
                                while (warningStatusesSet.next())
                                    {
                                        String warningSuggestedStatusId = warningStatusesSet.getString("WARNING_SUGGESTED_STATUS_ID");
                                        String warningSuggestedStatusName = warningStatusesSet.getString("WARNING_SUGGESTED_STATUS_NAME");
                                        WarningSuggestedStatusModel warningSuggestedStatusModel = new WarningSuggestedStatusModel(warningSuggestedStatusId, warningSuggestedStatusName);
                                        warningSuggestedStatuses.add(warningSuggestedStatusModel);
                                    }

                                stat.close();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_WARNING_SUGGESTED_STATUS, warningSuggestedStatuses);
                    } else
                    {
                        warningSuggestedStatuses = (Vector) obj;
                    }

                return warningSuggestedStatuses;
            }

        /**
         * 15- get Sheet User Warning By Phase
         * 
         * @param String
         *            phaseName
         * @throws
         * @return Vector of SheetWarningModel
         * @see
         */
        public static Vector getSheetUserWarningByPhase(String phaseName)
            {
                Vector warnings = new Vector();
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        String sql = " select * from vw_CR_sheet_warning_type,vw_CR_phase "
                                + " where vw_CR_sheet_warning_type.PHASE_ID= vw_CR_phase.PHASE_ID "
                                + " and vw_CR_sheet_warning_type.PHASE_NAME ='"
                                + phaseName
                                + "'"
                                + " and vw_CR_sheet_warning_type.WARNING_TYPE_NAME ='USER WARNING'"
                                + " and vw_CR_sheet_warning_type.WARNING_STATUS_NAME not in('DELETED')";
                        ResultSet warningsSet = stat.executeQuery(sql);

                        while (warningsSet.next())
                            {
                                SheetWarningModel sheetWarning = new SheetWarningModel(warningsSet);
                                warnings.add(sheetWarning);
                            }

                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return warnings;
            }

        /**
         * 14- update Sheet User Warning
         * 
         * @param String
         *            warningName , String warningDesc, String phaseID, String
         *            warningTypeID, String warningStatusID
         * @throws
         * @return
         * @see
         */
        public static void updateSheetUserWarning(String warningName,
                String warningDesc, String phaseID, String warningTypeID,
                String warningStatusID)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        String sql = "insert into VW_CR_SHEET_WARNING_TYPE_INST"
                                + " (SHEET_WARNING_TYPE_ID, SHEET_WARNING_TYPE_NAME,"
                                + " SHEET_WARNING_TYPE_DESC, PHASE_ID, WARNING_TYPE_ID,"
                                + " WARNING_STATUS_ID)"
                                + " values(SEQ_CR_SHEET_WARNING_TYPE.nextval, '"
                                + warningName
                                + "'"
                                + ", '"
                                + warningDesc
                                + "', "
                                + phaseID
                                + ", "
                                + warningTypeID
                                + ", "
                                + warningStatusID + ")";
                        stat.executeUpdate(sql);

                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }

        /**
         * 13- get Sheet Warnings
         * 
         * @param String
         *            sheetID
         * @throws
         * @return Vector of sheet warnings
         * @see
         */
        public static final Vector getSheetWarnings(String sheetID,
                Connection con)
            {

                Vector warnings = new Vector();
                try
                    {

                        Statement stat = con.createStatement();

                        long startT = System.currentTimeMillis();

                        String sql = " SELECT SHEET_WARNING_TYPE_ID, SHEET_WARNING_TYPE_NAME , SHEET_WARNING_TYPE_DESC , PHASE_ID , WARNING_TYPE_ID , WARNING_STATUS_ID  FROM vw_CR_sheet, vw_CR_sheet_warning, vw_CR_sheet_warning_type, vw_CR_phase"
                                + " WHERE"
                                + "  sheet_id = "
                                + sheetID
                                + " and vw_CR_sheet.SHEET_last_STATUS_ID = vw_CR_sheet_warning.SHEET_STATUS_ID"
                                + " and vw_CR_sheet_Warning.SHEET_WARNING_TYPE_ID = vw_CR_sheet_warning_type.SHEET_WARNING_TYPE_ID"
                                + " and vw_CR_sheet_warning_type.PHASE_ID= vw_CR_phase.PHASE_ID"
                                + " and vw_CR_sheet_warning_type.WARNING_STATUS_NAME = 'ACTIVE'";

                        ResultSet warningsSet = stat.executeQuery(sql);
                        while (warningsSet.next())
                            {
                                SheetWarningModel sheetWarning = new SheetWarningModel(warningsSet);
                                warnings.add(sheetWarning);
                            }
                        warningsSet.close();
                        stat.close();

                    } catch (Exception e)
                    {
                    }
                return warnings;
            }

        /**
         * 12- get batch Sheets
         * 
         * @param String
         *            batchId , String phaseName
         * @throws
         * @return SheetDto or null in case of error
         * @see
         */
        public static SheetDto getBatchSheets(String batchId, String phaseName,
                Connection con)
            {
                try
                    {

                        Statement stat = con.createStatement();

                        /*
                         * String sql =
                         * "begin ? := BATCH_SHEETS ("+batchId+", '"
                         * +phaseName+"'); end;"; CallableStatement colableStat
                         * = con.prepareCall(sql);
                         * colableStat.registerOutParameter(1,
                         * OracleTypes.CURSOR); colableStat.execute(); ResultSet
                         * res = (ResultSet)colableStat.getObject(1);
                         */

                        String sql = "select product_id, product_name from vw_gen_product";
                        ResultSet res = stat.executeQuery(sql);

                        sql = " select sheet.sheet_id,SDS_GET_sheet_REIMPORT_FLAG(sheet.sheet_serial_id, sheet.sheet_id) SHEET_RE_IMPORT_FLAG";

                        int productsCount = 0;

                        while (res.next())
                            {
                                String productID = res.getString("product_id");
                                String productName = res.getString("product_name");

                                productsCount++;

                                sql += ",'"
                                        + productName
                                        + "' "
                                        + " , sum( decode(vw_CR_contract.CONTRACT_TYPE_ID ,"
                                        + productID + " , 1, 0) ) ";
                            }

                        sql += " from vw_CR_sheet sheet,vw_CR_contract where batch_id = "
                                + batchId
                                + " and sheet.sheet_id = vw_CR_contract.sheet_id(+) group by sheet.sheet_id,sheet.sheet_serial_id ";

                        sql = "select * from ("
                                + sql
                                + ") sheet_statistic, vw_CR_phase, vw_CR_sheet sheet , vw_CR_sheet_status where sheet.batch_id = "
                                + batchId
                                + " and sheet_statistic.sheet_id = Sheet.sheet_id "
                                + " and sheet.sheet_last_status_id = vw_cr_sheet_status.sheet_status_id "
                                + " and sheet_last_status_id = (SELECT MAX( VW_CR_SHEET.SHEET_LAST_STATUS_ID )  FROM   VW_CR_SHEET "
                                + "   WHERE  VW_CR_SHEET.SHEET_SERIAL_ID = sheet.SHEET_SERIAL_ID   "
                                + "	and vw_CR_sheet.batch_id = "
                                + batchId
                                + ") "
                                + " and vw_CR_phase.phase_id = vw_CR_sheet_status.status_phase_id "
                                + " order by vw_CR_PHASE.ORDER_ID DESC,VW_CR_SHEET_STATUs.sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID ";

                        res = stat.executeQuery(sql);
                        // debug("batch _sheet time is = " +
                        // (System.currentTimeMillis() -
                        // startT ));
                        // debug(sql);
                        /*
                         * String sql =
                         * "select * from vw_CR_sheet_last_status where batch_id = "
                         * + batchId ; debug(sql); ResultSet res =
                         * stat.executeQuery(sql);
                         */
                        // Statement statGetStatus= con.createStatement();
                        SheetDto sheetDto = new SheetDto();

                        while (res.next())
                            {
                                // geting informaiton from the database about
                                // this sheet
                                String sheetId = res.getString(SheetModel.SHEET_ID);
                                String sheetName = res.getString(SheetModel.SHEET_SERIAL_ID);
                                int contractcount = res.getInt(SheetModel.SHEET_CONTRACT_COUNT);
                                String sheetTypeId = res.getString(SheetModel.SHEET_TYPE_ID);
                                String sheetTypeName = res.getString(SheetModel.SHEET_TYPE_NAME);
                                String sheetDCMCode = res.getString(SheetModel.SHEET_DISTRIBUTER_CODE);
                                String sheetPosCode = res.getString(SheetModel.SHEET_POS_CODE);
                                String sheetSuperDealerCode = res.getString(SheetModel.SHEET_SUPER_DEALER_CODE);
                                int lastSheetStatusTypeId = res.getInt("sheet_status_type_id");
                                String lastSheetStatusName = res.getString("sheet_status_type_name");
                                // creating a sheet model using the info
                                // obtained above
                                SheetModel sheetModel = new SheetModel(sheetId, contractcount, sheetName, sheetTypeId, sheetTypeName, sheetPosCode, sheetSuperDealerCode);
                                sheetModel.setSheetDCMCode(sheetDCMCode);
                                sheetModel.setSheetStatusName(lastSheetStatusName);
                                sheetModel.setSheetStatusId(lastSheetStatusTypeId);

                                sheetModel.setSheetReImportFlag(res.getInt(SheetModel.SHEET_RE_IMPORT_FLAG));

                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");
                                // getting sheet warning

                                // count of sheet contracts per product
                                // startT=System.currentTimeMillis();

                                /*
                                 * String sqlSheetContractsCount =
                                 * "select PRODUCT_NAME , count (contract_id) contracts_count from vw_gen_product product  , vw_cr_contract contract"
                                 * + " where sheet_id = " + sheetId +
                                 * " and  product.PRODUCT_ID = contract.CONTRACT_TYPE_ID group by PRODUCT_NAME order by PRODUCT_NAME"
                                 * ;
                                 * 
                                 * //i removed the (+) from beside product id
                                 * cause i dont find a need for it
                                 */

                                /*
                                 * String sqlSheetContractsCount =
                                 * "begin ? := GET_SHEET_PRODUCTS_COUNT ('"
                                 * +sheetId+"'); end;";
                                 * 
                                 * CallableStatement colableStat2 =
                                 * con.prepareCall(sqlSheetContractsCount);
                                 * colableStat2.registerOutParameter(1,
                                 * OracleTypes.CURSOR); colableStat2.execute();
                                 * 
                                 * 
                                 * ResultSet resStatistic =
                                 * (ResultSet)colableStat2.getObject(1);
                                 */

                                // ResultSet resStatistic =
                                // statSheetStatistic.executeQuery(sqlSheetContractsCount);

                                sheetModel.setContractsTypesCount(res, productsCount);

                                // resStatistic.close();
                                // statSheetStatistic.close();
                                // colableStat2.close();
                                sheetDto.addSheetModel(sheetModel);

                            }

                        String sheetWarningSql = "select * from vw_CR_sheet, vw_cr_sheet_warning where "
                                + "vw_cr_sheet.batch_id = "
                                + batchId
                                + " and WARNING_STATUS_NAME = 'ACTIVE'"
                                + " and vw_CR_sheet.sheet_last_status_id =  vw_CR_Sheet_warning.sheet_status_id";

                        Statement statSheetStatistic = con.createStatement();

                        long startT = System.currentTimeMillis();

                        ResultSet warningsSet = statSheetStatistic.executeQuery(sheetWarningSql);

                        debug("get warning set time = "
                                + (System.currentTimeMillis() - startT));

                        // debug(sheetWarningSql);

                        while (warningsSet.next())
                            {
                                // add warning to the sheet dto
                                sheetDto.addSheetWarning(warningsSet.getString("sheet_id"), warningsSet);
                            }
                        warningsSet.close();

                        statSheetStatistic.close();
                        res.close();
                        stat.close();
                        // colableStat.close();

                        return sheetDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        public static SheetDto getBatchSheetsInContractVerification(
                String batchId, Connection con)
            {
                SheetDto sheetDto = new SheetDto();
                try
                    {
                        // Connection con = Utility.getConnection();

                        Statement stat = con.createStatement();

                        /*
                         * String sql =
                         * "begin ? := BATCH_SHEETS ("+batchId+", '"
                         * +phaseName+"'); end;"; CallableStatement colableStat
                         * = con.prepareCall(sql);
                         * colableStat.registerOutParameter(1,
                         * OracleTypes.CURSOR); colableStat.execute(); ResultSet
                         * res = (ResultSet)colableStat.getObject(1);
                         */

                        /*
                         * String sql =
                         * "select * from vw_CR_sheet_last_status where batch_id = "
                         * +batchId+" and sheet_status_type_name not in ('"+
                         * SheetModel. STATUS_REJECTED_DELIVERY+"')";
                         * 
                         * i commented this statemnt and replaced the view
                         * vw_CR_sheet_last_status to VW_CR_SHEET_ID_LAST_STATUS
                         * in order to optimize
                         */

                        String sql = "select product_id, product_name from vw_gen_product";
                        ResultSet res = stat.executeQuery(sql);

                        sql = " select sheet.sheet_id,SDS_GET_sheet_REIMPORT_FLAG(sheet.sheet_serial_id, sheet.sheet_id) SHEET_RE_IMPORT_FLAG";

                        int productsCount = 0;

                        while (res.next())
                            {
                                String productID = res.getString("product_id");
                                String productName = res.getString("product_name");

                                productsCount++;

                                sql += ",'"
                                        + productName
                                        + "' "
                                        + " , sum( decode(vw_CR_contract.CONTRACT_TYPE_ID ,"
                                        + productID + " , 1, 0) ) ";
                            }

                        sql += " from vw_CR_sheet sheet,vw_CR_contract where batch_id = "
                                + batchId
                                + " and sheet.sheet_id = vw_CR_contract.sheet_id group by sheet.sheet_id ,sheet.sheet_serial_id ";

                        sql = "select * from ("
                                + sql
                                + ") sheet_statistic,  vw_CR_sheet , vw_CR_sheet_status where vw_CR_sheet.batch_id = "
                                + batchId
                                + " and sheet_statistic.sheet_id = vw_CR_Sheet.sheet_id "
                                + " and vw_CR_sheet.sheet_last_status_id = vw_cr_sheet_status.sheet_status_id "
                                + " and sheet_status_type_name not in  ('"
                                + SheetModel.STATUS_REJECTED_DELIVERY
                                + "','"
                                + SheetModel.STATUS_REJECTED_IMPORT
                                + "')"
                                + " order by sheet_status_Type_name, vw_CR_sheet.sheet_serial_id";

                        /*
                         * the following statement was replaced with the above
                         * code for optimization purposes to get teh statistic
                         * beside each row information
                         * 
                         * String sql =
                         * "select * from VW_CR_SHEET_ID_LAST_STATUS where batch_id = "
                         * +batchId+" and sheet_status_type_name not in ('"+
                         * SheetModel. STATUS_REJECTED_DELIVERY+"')";
                         */
                        // debug(sql);

                        // Utility.logger.debug(sql);
                            System.out.println("The sheet query isssssss  " + sql);
                        res = stat.executeQuery(sql);

                        // Statement statGetStatus= con.createStatement();

                        Statement statSheetStatistic = con.createStatement();

                        while (res.next())
                            {
                                // geting informaiton from the database about
                                // this sheet
                                String sheetId = res.getString(SheetModel.SHEET_ID);
                                String sheetName = res.getString(SheetModel.SHEET_SERIAL_ID);
                                int contractcount = res.getInt(SheetModel.SHEET_CONTRACT_COUNT);
                                String sheetTypeId = res.getString(SheetModel.SHEET_TYPE_ID);
                                String sheetTypeName = res.getString(SheetModel.SHEET_TYPE_NAME);
                                String sheetDCMCode = res.getString(SheetModel.SHEET_DISTRIBUTER_CODE);
                                String sheetPosCode = res.getString(SheetModel.SHEET_POS_CODE);
                                String sheetSuperDealerCode = res.getString(SheetModel.SHEET_SUPER_DEALER_CODE);
                                int lastSheetStatusTypeId = res.getInt("sheet_status_type_id");
                                String lastSheetStatusName = res.getString("sheet_status_type_name");
                                // creating a sheet model using the info
                                // obtained above
                                SheetModel sheetModel = new SheetModel(sheetId, contractcount, sheetName, sheetTypeId, sheetTypeName, sheetPosCode, sheetSuperDealerCode);
                                sheetModel.setSheetDCMCode(sheetDCMCode);
                                sheetModel.setSheetStatusName(lastSheetStatusName);
                                sheetModel.setSheetStatusId(lastSheetStatusTypeId);
                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                                sheetModel.setSheetReImportFlag(res.getBoolean(SheetModel.SHEET_RE_IMPORT_FLAG));

                                // getting sheet warning
                                /*
                                 * String sheetWarningSql =
                                 * "select * from vw_cr_sheet_warning where" +
                                 * " vw_cr_sheet_warning.sheet_status_id = "
                                 * +lastSheetStatusRecordId +
                                 * " and WARNING_STATUS_NAME = 'ACTIVE'";
                                 * 
                                 * startT = System.currentTimeMillis();
                                 * ResultSet warningsSet =
                                 * statSheetStatistic.executeQuery
                                 * (sheetWarningSql); debug("time 2  = " +
                                 * (System.currentTimeMillis() - startT));
                                 * debug(sheetWarningSql ); while
                                 * (warningsSet.next()) { //add warning to the
                                 * sheet dto sheetDto.addSheetWarning(sheetId,
                                 * warningsSet); } warningsSet.close();
                                 */
                                // count of sheet contracts per product
                                /*
                                 * startT = System.currentTimeMillis(); String
                                 * sqlSheetContractsCount =
                                 * "select PRODUCT_NAME , count (contract_id) contracts_count "
                                 * +
                                 * "from vw_gen_product product , vw_cr_contract contract "
                                 * + " where sheet_id = " + sheetId +
                                 * " and contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
                                 * +
                                 * " group by PRODUCT_NAME order by PRODUCT_NAME"
                                 * ; //debug(sqlSheetContractsCount);
                                 * 
                                 * ResultSet resStatistic =
                                 * statSheetStatistic.executeQuery
                                 * (sqlSheetContractsCount); debug("time 3  = "
                                 * + (System.currentTimeMillis() - startT));
                                 * debug(sqlSheetContractsCount );
                                 */
                                sheetModel.setContractsTypesCount(res, productsCount);

                                // resStatistic.close();
                                // statSheetStatistic.close();
                                sheetDto.addSheetModel(sheetModel);
                            }
                        res.close();

                        String sheetWarningSql = "select * from vw_CR_sheet, vw_cr_sheet_warning where "
                                + "vw_cr_sheet.batch_id = "
                                + batchId
                                + " and WARNING_STATUS_NAME = 'ACTIVE'"
                                + " and vw_CR_sheet.sheet_last_status_id =  vw_CR_Sheet_warning.sheet_status_id";

                        // Statement statSheetStatistic = con.createStatement();

                        ResultSet warningsSet = statSheetStatistic.executeQuery(sheetWarningSql);

                        // debug(sheetWarningSql);

                        while (warningsSet.next())
                            {
                                // add warning to the sheet dto
                                sheetDto.addSheetWarning(warningsSet.getString("sheet_id"), warningsSet);
                            }
                        warningsSet.close();

                        stat.close();

                        statSheetStatistic.close();
                        // colableStat.close();

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return sheetDto;
            }

        public static SheetDto getBatchSheetsEligableForAuthintication(
                String batchId, String sheetId)
            {

                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        if (sheetId == null || sheetId.length() == 0)
                            {
                                Utility.logger.debug("problem sheet id is null");
                            }
                        /*
                         * String sql =
                         * "begin ? := BATCH_SHEETS ("+batchId+", '"
                         * +phaseName+"'); end;"; CallableStatement colableStat
                         * = con.prepareCall(sql);
                         * colableStat.registerOutParameter(1,
                         * OracleTypes.CURSOR); colableStat.execute(); ResultSet
                         * res = (ResultSet)colableStat.getObject(1);
                         */

                        String sql = "select product_id, product_name from vw_gen_product";
                        ResultSet res = stat.executeQuery(sql);

                        sql = " select sheet.sheet_id,SDS_GET_sheet_REIMPORT_FLAG(sheet.sheet_serial_id, sheet.sheet_id) SHEET_RE_IMPORT_FLAG";

                        int productsCount = 0;

                        while (res.next())
                            {
                                String productID = res.getString("product_id");
                                String productName = res.getString("product_name");

                                productsCount++;

                                sql += ",'"
                                        + productName
                                        + "' "
                                        + " , sum( decode(vw_CR_contract.CONTRACT_TYPE_ID ,"
                                        + productID + " , 1, 0) ) ";
                            }

                        sql += " from vw_CR_sheet sheet,vw_CR_contract where batch_id = "
                                + batchId
                                + " and sheet.sheet_id="
                                + sheetId
                                + " and sheet.sheet_id = vw_CR_contract.sheet_id group by sheet.sheet_id ,sheet.sheet_serial_id";

                        sql = "select sheet_statistic.* ,sheet_status_id , SHEET_SERIAL_ID , SHEET_CONTRACT_COUNT , SHEET_TYPE_ID , SHEET_TYPE_NAME , SHEET_DISTRIBUTER_CODE , SHEET_POS_CODE   ,SHEET_SUPER_DEALER_CODE,  vw_CR_sheet_Status_type.sheet_status_type_id, sheet_status_type_name from ("
                                + sql
                                + ") sheet_statistic, vw_cr_phase,vw_CR_sheet_Status_type,  vw_CR_sheet , CR_sheet_status where vw_CR_sheet.batch_id = "
                                + batchId
                                + " and sheet_statistic.sheet_id = vw_CR_Sheet.sheet_id and vw_CR_sheet.sheet_id ="
                                + sheetId
                                + " and vw_CR_sheet.sheet_last_status_id = cr_sheet_status.sheet_status_id "
                                + " and CR_sheet_status.sheet_status_type_id = vw_CR_sheet_status_type.sheet_Status_type_id"
                                + " and sheet_status_type_name  in ('"
                                + SheetModel.STATUS_REJECTED_AUTHINTICATION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_VERIFY
                                + "')"
                                + " and  vw_CR_sheet_status_type.phase_id=vw_CR_phase.phase_id "

                                + " order by vw_CR_PHASE.ORDER_ID DESC,sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID";

                        // ////88888888888888888888888888888888888
                        /*
                         * this sql was removed and replaced with the one above
                         * in order to optimize
                         * 
                         * String sql =
                         * "select * from vw_CR_phase,vw_CR_sheet_last_status where batch_id = "
                         * +
                         * batchId+" and SHEET_STATUS_TYPE_NAME in ('"+SheetModel
                         * . STATUS_REJECTED_AUTHINTICATION+"','"+
                         * SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                         * +"','"+SheetModel.STATUS_ACCEPTED_COMMISSION+"','"+
                         * SheetModel.STATUS_ACCEPTED_VERIFY+
                         * "') and vw_CR_phase.phase_id=vw_CR_sheet_last_status.sheet_phase_id "
                         * +
                         * " order by vw_CR_PHASE.ORDER_ID DESC,sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID"
                         * ;
                         */

                        debug("sql : to get sheet eligable for authentication ");
                        debug(sql);
                        debug("************");

                        res = stat.executeQuery(sql);

                        // Statement statGetStatus= con.createStatement();
                        SheetDto sheetDto = new SheetDto();
                        while (res.next())
                            {
                                // geting informaiton from the database about
                                // this sheet
                                sheetId = res.getString(SheetModel.SHEET_ID);
                                String sheetName = res.getString(SheetModel.SHEET_SERIAL_ID);
                                int contractcount = res.getInt(SheetModel.SHEET_CONTRACT_COUNT);
                                String sheetTypeId = res.getString(SheetModel.SHEET_TYPE_ID);
                                String sheetTypeName = res.getString(SheetModel.SHEET_TYPE_NAME);
                                String sheetDCMCode = res.getString(SheetModel.SHEET_DISTRIBUTER_CODE);
                                String sheetPosCode = res.getString(SheetModel.SHEET_POS_CODE);
                                String sheetSuperDealerCode = res.getString(SheetModel.SHEET_SUPER_DEALER_CODE);
                                int lastSheetStatusTypeId = res.getInt("sheet_status_type_id");
                                String lastSheetStatusName = res.getString("sheet_status_type_name");
                                // creating a sheet model using the info
                                // obtained above
                                SheetModel sheetModel = new SheetModel(sheetId, contractcount, sheetName, sheetTypeId, sheetTypeName, sheetPosCode, sheetSuperDealerCode);
                                sheetModel.setSheetDCMCode(sheetDCMCode);
                                sheetModel.setSheetStatusName(lastSheetStatusName);
                                sheetModel.setSheetStatusId(lastSheetStatusTypeId);
                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                                sheetModel.setSheetReImportFlag(res.getBoolean(SheetModel.SHEET_RE_IMPORT_FLAG));

                                // getting sheet warning
                                /*
                                 * String sheetWarningSql =
                                 * "select * from vw_cr_sheet_warning where" +
                                 * " vw_cr_sheet_warning.sheet_status_id = "
                                 * +lastSheetStatusRecordId +
                                 * " and WARNING_STATUS_NAME = 'ACTIVE'";
                                 * Statement statSheetStatistic =
                                 * con.createStatement(); ResultSet warningsSet
                                 * =
                                 * statSheetStatistic.executeQuery(sheetWarningSql
                                 * ); while (warningsSet.next()) { //add warning
                                 * to the sheet dto
                                 * sheetDto.addSheetWarning(sheetId,
                                 * warningsSet); } warningsSet.close();
                                 */
                                // count of sheet contracts per product
                                /*
                                 * String sqlSheetContractsCount =
                                 * "select PRODUCT_NAME , count (contract_id) contracts_count "
                                 * +
                                 * "from vw_cr_contract contract,vw_gen_product product "
                                 * + " where sheet_id = " + sheetId +
                                 * " and contract.CONTRACT_TYPE_ID =product.PRODUCT_ID(+) "
                                 * +
                                 * " group by PRODUCT_NAME order by PRODUCT_NAME"
                                 * ; debug(sqlSheetContractsCount);
                                 * 
                                 * ResultSet resStatistic =
                                 * statSheetStatistic.executeQuery
                                 * (sqlSheetContractsCount);
                                 * 
                                 * 
                                 * sheetModel.setContractsTypesCount(resStatistic
                                 * );
                                 */
                                // resStatistic.close();
                                // statSheetStatistic.close();

                                sheetModel.setContractsTypesCount(res, productsCount);

                                sheetDto.addSheetModel(sheetModel);
                            }
                        res.close();
                        stat.close();

                        String sheetWarningSql = "select * from vw_cr_sheet_warning , vw_CR_sheet where "
                                + "vw_cr_sheet.batch_id = "
                                + batchId
                                + " and vw_CR_sheet.sheet_last_status_id =  vw_CR_Sheet_warning.sheet_status_id"
                                + " and WARNING_STATUS_NAME = 'ACTIVE'";

                        Statement statSheetStatistic = con.createStatement();

                        long startT = System.currentTimeMillis();

                        ResultSet warningsSet = statSheetStatistic.executeQuery(sheetWarningSql);

                        debug("get warning set time = "
                                + (System.currentTimeMillis() - startT));

                        debug(sheetWarningSql);

                        while (warningsSet.next())
                            {
                                // add warning to the sheet dto
                                sheetDto.addSheetWarning(warningsSet.getString("sheet_id"), warningsSet);
                            }
                        warningsSet.close();

                        Utility.closeConnection(con);

                        return sheetDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        public static SheetDto getBatchSheetsEligableForAuthinticationOPT(
                String batchId, Connection con)
            {
                long startTime = System.currentTimeMillis();
                try
                    {
                        // Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        String sql = "select vw_cr_sheet.sheet_id,sheet_status_id , SHEET_SERIAL_ID , SHEET_CONTRACT_COUNT , SHEET_TYPE_ID , SHEET_TYPE_NAME , SHEET_DISTRIBUTER_CODE , SHEET_POS_CODE   ,SHEET_SUPER_DEALER_CODE,  vw_CR_sheet_Status_type.sheet_status_type_id, sheet_status_type_name from  vw_cr_phase,vw_CR_sheet_Status_type,  vw_CR_sheet , CR_sheet_status where vw_CR_sheet.batch_id = "
                                + batchId
                                + " and vw_CR_sheet.sheet_last_status_id = cr_sheet_status.sheet_status_id "
                                + " and CR_sheet_status.sheet_status_type_id = vw_CR_sheet_status_type.sheet_Status_type_id"
                                + " and sheet_status_type_name  in ('"
                                + SheetModel.STATUS_REJECTED_AUTHINTICATION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_VERIFY
                                + "')"
                                + " and  vw_CR_sheet_status_type.phase_id=vw_CR_phase.phase_id "

                                + " order by vw_CR_PHASE.ORDER_ID DESC,sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID";

                        System.out.println("ma: = " + sql);
                        // ////88888888888888888888888888888888888
                        /*
                         * this sql was removed and replaced with the one above
                         * in order to optimize
                         * 
                         * String sql =
                         * "select * from vw_CR_phase,vw_CR_sheet_last_status where batch_id = "
                         * +
                         * batchId+" and SHEET_STATUS_TYPE_NAME in ('"+SheetModel
                         * . STATUS_REJECTED_AUTHINTICATION+"','"+
                         * SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                         * +"','"+SheetModel.STATUS_ACCEPTED_COMMISSION+"','"+
                         * SheetModel.STATUS_ACCEPTED_VERIFY+
                         * "') and vw_CR_phase.phase_id=vw_CR_sheet_last_status.sheet_phase_id "
                         * +
                         * " order by vw_CR_PHASE.ORDER_ID DESC,sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID"
                         * ;
                         */

                        ResultSet res = stat.executeQuery(sql);

                        // Statement statGetStatus= con.createStatement();
                        SheetDto sheetDto = new SheetDto();
                        while (res.next())
                            {
                                // geting informaiton from the database about
                                // this sheet
                                String sheetId = res.getString(SheetModel.SHEET_ID);
                                String sheetName = res.getString(SheetModel.SHEET_SERIAL_ID);
                                int contractcount = res.getInt(SheetModel.SHEET_CONTRACT_COUNT);
                                String sheetTypeId = res.getString(SheetModel.SHEET_TYPE_ID);
                                String sheetTypeName = res.getString(SheetModel.SHEET_TYPE_NAME);
                                String sheetDCMCode = res.getString(SheetModel.SHEET_DISTRIBUTER_CODE);
                                String sheetPosCode = res.getString(SheetModel.SHEET_POS_CODE);
                                String sheetSuperDealerCode = res.getString(SheetModel.SHEET_SUPER_DEALER_CODE);
                                int lastSheetStatusTypeId = res.getInt("sheet_status_type_id");
                                String lastSheetStatusName = res.getString("sheet_status_type_name");
                                // creating a sheet model using the info
                                // obtained above
                                SheetModel sheetModel = new SheetModel(sheetId, contractcount, sheetName, sheetTypeId, sheetTypeName, sheetPosCode, sheetSuperDealerCode);
                                sheetModel.setSheetDCMCode(sheetDCMCode);
                                sheetModel.setSheetStatusName(lastSheetStatusName);
                                sheetModel.setSheetStatusId(lastSheetStatusTypeId);
                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                                // sheetModel.setSheetReImportFlag(res.getBoolean(SheetModel.SHEET_RE_IMPORT_FLAG));

                                // sheetModel.setContractsTypesCount(res,productsCount);

                                sheetDto.addSheetModel(sheetModel);
                            }
                        res.close();
                        stat.close();

                        /*
                         * String sheetWarningSql =
                         * "select * from vw_cr_sheet_warning , vw_CR_sheet where "
                         * +"vw_cr_sheet.batch_id = " +batchId+
                         * " and vw_CR_sheet.sheet_last_status_id =  vw_CR_Sheet_warning.sheet_status_id"
                         * +" and WARNING_STATUS_NAME = 'ACTIVE'";
                         * 
                         * 
                         * 
                         * Statement statSheetStatistic = con.createStatement();
                         * 
                         * long startT=System.currentTimeMillis();
                         * 
                         * ResultSet warningsSet =
                         * statSheetStatistic.executeQuery(sheetWarningSql);
                         * 
                         * System.out.println("get warning set time = "+
                         * (System.currentTimeMillis() -startT));
                         * 
                         * System.out.println(sheetWarningSql);
                         * 
                         * while (warningsSet.next()) { //add warning to the
                         * sheet dto
                         * sheetDto.addSheetWarning(warningsSet.getString
                         * ("sheet_id"), warningsSet); } warningsSet.close();
                         */

                        // Utility.closeConnection(con);

                        System.out.println("getBatchSheetsEligableForAuthintication time = "
                                + (System.currentTimeMillis() - startTime));
                        return sheetDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        public static SheetDto getBatchSheetsEligableForAuthintication(
                String batchId)
            {
                long startTime = System.currentTimeMillis();
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        String sql = "select product_id, product_name from vw_gen_product";
                        ResultSet res = stat.executeQuery(sql);

                        sql = " select sheet.sheet_id,SDS_GET_sheet_REIMPORT_FLAG(sheet.sheet_serial_id, sheet.sheet_id) SHEET_RE_IMPORT_FLAG";

                        int productsCount = 0;

                        while (res.next())
                            {
                                String productID = res.getString("product_id");
                                String productName = res.getString("product_name");

                                productsCount++;

                                sql += ",'"
                                        + productName
                                        + "' "
                                        + " , sum( decode(vw_CR_contract.CONTRACT_TYPE_ID ,"
                                        + productID + " , 1, 0) ) ";
                            }

                        sql += " from vw_CR_sheet sheet,vw_CR_contract where batch_id = "
                                + batchId
                                + " and sheet.sheet_id = vw_CR_contract.sheet_id group by sheet.sheet_id ,sheet_serial_id ";

                        sql = "select sheet_statistic.* ,sheet_status_id , SHEET_SERIAL_ID , SHEET_CONTRACT_COUNT , SHEET_TYPE_ID , SHEET_TYPE_NAME , SHEET_DISTRIBUTER_CODE , SHEET_POS_CODE   ,SHEET_SUPER_DEALER_CODE,  vw_CR_sheet_Status_type.sheet_status_type_id, sheet_status_type_name from ("
                                + sql
                                + ") sheet_statistic, vw_cr_phase,vw_CR_sheet_Status_type,  vw_CR_sheet , CR_sheet_status where vw_CR_sheet.batch_id = "
                                + batchId
                                + " and sheet_statistic.sheet_id = vw_CR_Sheet.sheet_id "
                                + " and vw_CR_sheet.sheet_last_status_id = cr_sheet_status.sheet_status_id "
                                + " and CR_sheet_status.sheet_status_type_id = vw_CR_sheet_status_type.sheet_Status_type_id"
                                + " and sheet_status_type_name  in ('"
                                + SheetModel.STATUS_REJECTED_AUTHINTICATION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                + "','"
                                + SheetModel.STATUS_ACCEPTED_VERIFY
                                + "')"
                                + " and  vw_CR_sheet_status_type.phase_id=vw_CR_phase.phase_id "

                                + " order by vw_CR_PHASE.ORDER_ID DESC,sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID";

                        System.out.println("ma: = " + sql);
                        // ////88888888888888888888888888888888888
                        /*
                         * this sql was removed and replaced with the one above
                         * in order to optimize
                         * 
                         * String sql =
                         * "select * from vw_CR_phase,vw_CR_sheet_last_status where batch_id = "
                         * +
                         * batchId+" and SHEET_STATUS_TYPE_NAME in ('"+SheetModel
                         * . STATUS_REJECTED_AUTHINTICATION+"','"+
                         * SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                         * +"','"+SheetModel.STATUS_ACCEPTED_COMMISSION+"','"+
                         * SheetModel.STATUS_ACCEPTED_VERIFY+
                         * "') and vw_CR_phase.phase_id=vw_CR_sheet_last_status.sheet_phase_id "
                         * +
                         * " order by vw_CR_PHASE.ORDER_ID DESC,sheet_STATUS_TYPE_NAME DESC,SHEET_SERIAL_ID"
                         * ;
                         */

                        res = stat.executeQuery(sql);

                        // Statement statGetStatus= con.createStatement();
                        SheetDto sheetDto = new SheetDto();
                        while (res.next())
                            {
                                // geting informaiton from the database about
                                // this sheet
                                String sheetId = res.getString(SheetModel.SHEET_ID);
                                String sheetName = res.getString(SheetModel.SHEET_SERIAL_ID);
                                int contractcount = res.getInt(SheetModel.SHEET_CONTRACT_COUNT);
                                String sheetTypeId = res.getString(SheetModel.SHEET_TYPE_ID);
                                String sheetTypeName = res.getString(SheetModel.SHEET_TYPE_NAME);
                                String sheetDCMCode = res.getString(SheetModel.SHEET_DISTRIBUTER_CODE);
                                String sheetPosCode = res.getString(SheetModel.SHEET_POS_CODE);
                                String sheetSuperDealerCode = res.getString(SheetModel.SHEET_SUPER_DEALER_CODE);
                                int lastSheetStatusTypeId = res.getInt("sheet_status_type_id");
                                String lastSheetStatusName = res.getString("sheet_status_type_name");
                                // creating a sheet model using the info
                                // obtained above
                                SheetModel sheetModel = new SheetModel(sheetId, contractcount, sheetName, sheetTypeId, sheetTypeName, sheetPosCode, sheetSuperDealerCode);
                                sheetModel.setSheetDCMCode(sheetDCMCode);
                                sheetModel.setSheetStatusName(lastSheetStatusName);
                                sheetModel.setSheetStatusId(lastSheetStatusTypeId);
                                int lastSheetStatusRecordId = res.getInt("sheet_status_id");

                                sheetModel.setSheetReImportFlag(res.getBoolean(SheetModel.SHEET_RE_IMPORT_FLAG));

                                sheetModel.setContractsTypesCount(res, productsCount);

                                sheetDto.addSheetModel(sheetModel);
                            }
                        res.close();
                        stat.close();

                        String sheetWarningSql = "select * from vw_cr_sheet_warning , vw_CR_sheet where "
                                + "vw_cr_sheet.batch_id = "
                                + batchId
                                + " and vw_CR_sheet.sheet_last_status_id =  vw_CR_Sheet_warning.sheet_status_id"
                                + " and WARNING_STATUS_NAME = 'ACTIVE'";

                        Statement statSheetStatistic = con.createStatement();

                        long startT = System.currentTimeMillis();

                        ResultSet warningsSet = statSheetStatistic.executeQuery(sheetWarningSql);

                        System.out.println("get warning set time = "
                                + (System.currentTimeMillis() - startT));

                        System.out.println(sheetWarningSql);

                        while (warningsSet.next())
                            {
                                // add warning to the sheet dto
                                sheetDto.addSheetWarning(warningsSet.getString("sheet_id"), warningsSet);
                            }
                        warningsSet.close();

                        Utility.closeConnection(con);

                        System.out.println("getBatchSheetsEligableForAuthintication time = "
                                + (System.currentTimeMillis() - startTime));
                        return sheetDto;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        /**
         * 11- insert Sheet status
         * 
         * @param String
         *            sheetId , String sheetNewStatus
         * @throws
         * @return String sheet Status Id
         * @see
         */
        public static String insertSheetStatus(String sheetId,
                String sheetNewStatus, String userId, Connection con)
            {

                String sheetStatusId = null;
                if (sheetId == null || sheetId.trim().length() == 0
                        || sheetNewStatus == null
                        || sheetNewStatus.trim().length() == 0)
                    {

                        return null;
                    }
                try
                    {
                        Integer.parseInt(sheetId);

                        String sql = "begin ? := SDS_SHEET_INS_STATUS ("
                                + sheetId + ", " + sheetNewStatus + ", "
                                + userId + "); end;";

                        // debug(sql);

                        CallableStatement stat = con.prepareCall(sql);
                        stat.registerOutParameter(1, OracleTypes.INTEGER);
                        stat.execute();
                        sheetStatusId = stat.getString(1);

                        stat.close();

                    } catch (NumberFormatException nfEx)
                    {
                        return null;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return sheetStatusId;
            }

        private static boolean isAllArrayElementsInt(String[] array)

            {
                try
                    {
                        for (String element : array)
                            {
                                Integer.parseInt(element);
                            }

                    } catch (Exception e)
                    {
                        return false;
                    }
                    return true;
            }
    
        /**
         * 11- insert Sheet Warnings
         * 
         * @param String
         *            sheetStatusId , String [] applied_user_warnings
         * @throws
         * @return
         * @see
         */
        public static final void insertSheetWarnings(String sheetStatusId,
                String[] applied_user_warnings, Connection con)
            {
                /*
                 * test that all the warnings id in the array are numbers
                 */
               if (!isAllArrayElementsInt(applied_user_warnings))
                   return;
               
                try
                    {

                        Statement stat = con.createStatement();

                        // loop on the applied_user_warnings and insert each
                        // warning in it
                        for (int i = 0; i < applied_user_warnings.length; i++)
                            {
                                String sql = "insert into CR_SHEET_WARNING (SHEET_WARNING_ID,SHEET_STATUS_ID,SHEET_WARNING_DATE ,SHEET_WARNING_TYPE_ID ) "
                                        + "values(SEQ_CR_SHEET_WARNING.nextval,"
                                        + sheetStatusId
                                        + ",sysdate,"
                                        + applied_user_warnings[i] + ")";

                                stat.execute(sql);
                            }

                        stat.close();

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }

        public static SheetAuthinticationStatisticModel getSheetAuthinticationStatisticModel(
                String sheetId)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        SheetAuthinticationStatisticModel model = getSheetAuthinticationStatisticModel(sheetId, con);
                        Utility.closeConnection(con);
                        return model;
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return null;
            }

        /**
         * 10- get sheet authentication statistic model
         * 
         * @param String
         *            sheetId
         * @throws
         * @return SheetAuthinticationStatisticModel
         * @see
         */
        public static SheetAuthinticationStatisticModel getSheetAuthinticationStatisticModel(
                String sheetId, Connection con)
            {

                // debug("getSheetAuthinticationStatisticModel = " + sheetId);
                SheetAuthinticationStatisticModel sheetAuthinticationStatisticModel = null;
                long startTime = System.currentTimeMillis();
                try
                    {
                        // Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        // create sheetAuthinticationStatisticModel
                        sheetAuthinticationStatisticModel = new SheetAuthinticationStatisticModel();
                        sheetAuthinticationStatisticModel.setSheetId(sheetId);

                        String sql = "begin ? := SDS_get_SHEET_AUTH_STAT("
                                + sheetId + "); end;";
                        CallableStatement colableStat = con.prepareCall(sql);
                        colableStat.registerOutParameter(1, OracleTypes.CURSOR);
                        colableStat.execute();
                        ResultSet res = (ResultSet) colableStat.getObject(1);

                        // debug(sql);

                        Hashtable statusContractCount = new Hashtable();
                        int total = 0;
                        while (res.next())
                            {
                                // get the count number and the status name of
                                // this count and
                                // add it to the statusContractcount hashtable
                                // whcih should have all the status that any of
                                // the sheet
                                // contracts have it and number of contracts
                                // with this status in this sheet
                                String statusName = res.getString("CONTRACT_STATUS_TYPE_NAME");
                                Integer statusCount = new Integer(res.getInt("contracts_count"));
                                statusContractCount.put(statusName, statusCount);
                                // add to the total
                                total += statusCount.intValue();
                                // debug("status name  = " + statusName +
                                // "  statuc count = " +
                                // statusCount.intValue() + "   total = " +
                                // total);
                            }
                        res.close();
                        colableStat.close();

                        // debug(" timeeeee  = " + (System.currentTimeMillis() -
                        // startTime));
                        // set the total number of contracts eligable for
                        // authintication
                        sheetAuthinticationStatisticModel.setTotalContractsEligableForAuthintication(total);
                        // debug("setTotalContractsEligableForAuthintication " +
                        // total);

                        if (statusContractCount.containsKey(ContractModel.STATUS_ACCEPTED_AUTHINTICATION))
                            {
                                Integer accepted = (Integer) statusContractCount.get(ContractModel.STATUS_ACCEPTED_AUTHINTICATION);
                                sheetAuthinticationStatisticModel.setTotalContractsAuthinticated(accepted.intValue()
                                        + "");
                            }
                        if (statusContractCount.containsKey(ContractModel.STATUS_REJECTED_AUTHINTICATION))
                            {
                                Integer rejected = (Integer) statusContractCount.get(ContractModel.STATUS_REJECTED_AUTHINTICATION);

                                sheetAuthinticationStatisticModel.setTotalContractsRejectedAuthinticated(rejected.intValue()
                                        + "");
                            }

                        /*
                         * if the number of contracts eligable for
                         * authenticaition is zero then the method return null
                         */
                        if (sheetAuthinticationStatisticModel.getTotalContractsEligableForAuthintication() == 0)
                            {
                                // debug(" returning null cause of number of contracats = 0 ");
                                stat.close();
                                // Utility.closeConnection(con);
                                return null;
                            }

                        /*
                         * getting information about this sheet type the
                         * information needed is from the sheet type
                         * definishtion whic are the percentage needed to
                         * commission and the discared unknow and unrechable
                         * rule flag
                         */
                        sql = "select * from vw_CR_sheet_type, vw_CR_sheet_id_last_status where "
                                + " vw_CR_sheet_id_last_status."
                                + SheetModel.SHEET_TYPE_ID
                                + " = vw_cr_sheet_type."
                                + SheetModel.SHEET_TYPE_ID
                                + " and vw_CR_sheet_id_last_status."
                                + SheetModel.SHEET_ID + "= " + sheetId;

                        // Utility.logger.debug("ssss"+sql);

                        res = stat.executeQuery(sql);
                        int percentage = 0;
                        // int sheetLocalPercentage = 0;
                        boolean discardRule = false;
                        String sheetSerialId = "";
                        String sheetStatus = "";
                        if (res.next())
                            {
                                sheetSerialId = res.getString(SheetModel.SHEET_SERIAL_ID);
                                percentage = res.getInt("SHEET_AUTH_PERCENTAGE");

                                String sheetLocalPercentage = res.getString("SHEET_LOCAL_AUTH_PERCENTAGE");
                                // Utility.logger.debug("sheetLocalPercentage = "+sheetLocalPercentage);
                                if (sheetLocalPercentage == null
                                        || sheetLocalPercentage.compareTo("") == 0)
                                    {

                                    } else
                                    {
                                        try
                                            {
                                                percentage = Integer.parseInt(sheetLocalPercentage);
                                            } catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                    }

                                discardRule = res.getBoolean("DISCARD_UNREAL_UNREACHABLE");
                                sheetStatus = res.getString("sheet_status_type_name");
                                res.close();
                            }
                        // debug ("sheetSerialId " + sheetSerialId
                        // +" percentage "+
                        // percentage+" discardRule"+discardRule);
                        sheetAuthinticationStatisticModel.setSheetSerial(sheetSerialId);
                        sheetAuthinticationStatisticModel.setDiscardUnknownUnreachble(discardRule);
                        sheetAuthinticationStatisticModel.setSheetStatus(sheetStatus);
                        // ///////if sheet doesn't have percentage set
                        // percentage as batch
                        // percentage

                        // Utility.logger.debug("Local Percentage = "+sheetLocalPercentage);
                        // Utility.logger.debug("Percentage = "+percentage);
                        // calcualting the4 number of contracts needed to
                        // satisfy this
                        // percentage
                        double percentageOfContract = (double) percentage
                                / (double) 100;
                        // debug("percentageOfContract " +
                        // percentageOfContract);
                        percentageOfContract = (double) percentageOfContract
                                * (double) total;
                        int randomSampleSize = (int) Math.ceil((double) percentageOfContract);
                 
                        sheetAuthinticationStatisticModel.setTotalContractsNeededToCommission(randomSampleSize
                                + "");

                        stat.close();
                 
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                return sheetAuthinticationStatisticModel;
            }

        /**
         * 11- get Sheet History
         * 
         * @param String
         *            sheetSerialNum
         * @throws
         * @return it return a vector of SheetDto object and an empty vector in
         *         case of error or not found sheet in database
         * @see
         */
        public static Vector<SheetDto> getSheetHistory(String sheetSerialNum)
            {
                Vector<SheetDto> sheetDto = new Vector<SheetDto>();
                try
                    {
                        Connection con = Utility.getConnection();
                        sheetDto = getSheetHistory(sheetSerialNum, con);
                        Utility.closeConnection(con);
                    } 
                catch (Exception e)
                    
                    {
                        e.printStackTrace();
                    }
                return sheetDto;
            }

        public static Vector<SheetDto> getSheetHistory(String sheetSerialNum,
                Connection con)
            {
                Vector<SheetDto> historyVec = new Vector<SheetDto>();
                try
                    {

                        Statement stat = con.createStatement();

//                        String sql = "select * from vw_CR_sheet_history where "+ SheetModel.SHEET_SERIAL_ID + " = '"+ sheetSerialNum + "' UNION ALL select * from vw_CR_sheet_history_bkup where "+ SheetModel.SHEET_SERIAL_ID + " = '"+ sheetSerialNum + "' ";
                          String sql = "select * from vw_CR_sheet_history where "
 	 	 		 	                                + SheetModel.SHEET_SERIAL_ID + " = '"
 	 	 		 	                                + sheetSerialNum + "'";
                      System.out.println("The sheet history query isssssssss " + sql);
                        ResultSet res = stat.executeQuery(sql);

                        String batchId = null;
                        String statusDate = null;
                        SheetDto sheetDto = null;
                        String sheetId = null;

                        while (res.next())
                            {
                                String tempBatchId = res.getString(SheetModel.BATCH_ID);

                                String sheetStatusDate = null;
                                sheetStatusDate = res.getString(SheetModel.SHEET_STATUS_DATE);

                                // this case apply only when the sheet is not
                                // assgined yet to
                                // any batch
                                if (tempBatchId == null)
                                    tempBatchId = "";

                                /*
                                 * this if statement is responsible of checking
                                 * if this row describe a sheet in a new batch
                                 * than the previous sheet or that the sheet
                                 * status date is different from the previous
                                 * sheet status date this is since this sql the
                                 * sql return a result set that has teh
                                 * following if a sheet has a two warning
                                 * asssgined to the same status it will return
                                 * two rows there fore by this if statment i
                                 * manage to distingquesh between a warning on
                                 * the same status or a new status all togerer
                                 * if it was a new status i create a new sheet
                                 * dto then fill it with sheet info then add
                                 * warning to it if twas the same then i just
                                 * add warning to the previous sheet dto so both
                                 * two cases have the same last part of adding
                                 * warning to th esheet dto thus no checking fo
                                 * rth esecond case is needed so after the if
                                 * statmeent the executeino of the adding
                                 * warning happen
                                 */
                                if (batchId == null
                                        || tempBatchId == null
                                        || (batchId.compareTo(tempBatchId) != 0)
                                        || statusDate == null
                                        || (statusDate.compareTo(sheetStatusDate) != 0))
                                    {
                                        /*
                                         * the following is the hadnling of the
                                         * case that this row mean a new status
                                         * of the sheet not just a warning on
                                         * the same status of the previous row
                                         */

                                        // check if the sheet dto was not null
                                        // and add it to the history vector
                                        // before we re initialize
                                        // it
                                        if (sheetDto != null)
                                            {
                                                historyVec.add(sheetDto);
                                            }

                                        // creating a new sheet dto
                                        sheetDto = new SheetDto();

                                        sheetId = res.getString(SheetModel.SHEET_ID);
                                        SheetModel sheetModel = new SheetModel(res);
                                        sheetModel.setSheetDCMName(res.getString("DISTRIBUTER_NAME"));
                                        sheetModel.setSheetPOSName(res.getString("POS_NAME"));
                                        sheetModel.setSheetSuperDealerName(res.getString("SUPER_DEALER_NAME"));

                                        // adding the sheet dto to the vector
                                        sheetDto.addSheetModel(sheetModel);
                                    }
                                /*
                                 * staring form here is the general case of
                                 * adding the warning to the sheet dto and this
                                 * apply to both cases the new status of a sheet
                                 * or the same status since in both case at this
                                 * stage we have the SheetDto object which we
                                 * just add sheet warning to it
                                 */
                                sheetDto.addSheetWarning(sheetId, res);

                                // assing temp batch id and sheet status date to
                                // be the previous
                                // one before looping again
                                batchId = tempBatchId;
                                statusDate = sheetStatusDate;
                            }

                        // this code exute after we finish teh while loop it
                        // check if there
                        // is a sheet dto which coorrospon tot the last row
                        // read in the while loop and add it to the history
                        // vector if found
                        if (sheetDto != null)
                            {
                                historyVec.add(sheetDto);
                            }

                        stat.close();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return historyVec;
            }

        /**
         * 11- update sheet types percentage values
         * 
         * @param String
         *            [] sheetTypeIds , String[] sheetTypePercentage
         * @throws
         * @return
         * @see
         */
        public static void updateSheetTypesPercentage(String[] sheetTypeIds,
                String[] sheetTypePercentage)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        for (int i = 0; i < sheetTypeIds.length; i++)
                            {
                                String sql = " update vw_CR_sheet_type set SHEET_AUTH_PERCENTAGE='"
                                        + sheetTypePercentage[i]
                                        + "'"
                                        + " where sheet_type_id="
                                        + sheetTypeIds[i];
                                stat.execute(sql);
                            }

                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }

        public static void updateSheetLocalAuthPercentage(String batchId,
                String sheetSerialId, int sheetLocalAuthPercentage)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();

                        String sql = " update vw_CR_sheet set SHEET_LOCAL_AUTH_PERCENTAGE = "
                                + sheetLocalAuthPercentage
                                + " "
                                + " where batch_id="
                                + batchId
                                + " and sheet_serial_id = " + sheetSerialId;
                        stat.execute(sql);

                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }

        /**
         * 10- update sheet warning status
         * 
         * @param String
         *            argWarningID , String argStatusID
         * @throws
         * @return
         * @see
         */
        public static void updateSheetWarningStatus(String argWarningID,
                String argStatusID)
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stmt = con.createStatement();

                        String sql = "update VW_CR_SHEET_WARNING_TYPE_INST set WARNING_STATUS_ID= "
                                + argStatusID
                                + " where SHEET_WARNING_TYPE_ID = "
                                + argWarningID;
                        stmt.executeUpdate(sql);

                        stmt.close();
                        Utility.closeConnection(con);
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
            }

        /**
         * 9-a debug method that print its msg if the debug flag is set to true
         * 
         * @param String
         *            msg
         * @throws
         * @return
         * @see
         */
        private static void debug(String msg)
            {
                if (debugFlag)
                    Utility.logger.debug(msg);
            }

        /**
         * 8- get sheet types only
         * 
         * @param
         * @throws
         * @return Vector of SheetTypeModel
         * @see
         */
        public static Vector getSheetTypesVector()
            {
                long startTime = System.currentTimeMillis();

                Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_TYPES);
                Vector sheetTypeVec = null;

                if (obj == null)
                    {

                        sheetTypeVec = new Vector();
                        try
                            {
                                Connection con = Utility.getConnection();
                                Statement stat = con.createStatement();

                                ResultSet res = stat.executeQuery("select * from VW_CR_SHEET_TYPE order by "
                                        + SheetTypeModel.SHEET_TYPE_NAME);
                                while (res.next())
                                    {
                                        SheetTypeModel newSheetTypeModel = new SheetTypeModel(res);
                                        sheetTypeVec.add(newSheetTypeModel);
                                    }

                                stat.close();
                                Utility.closeConnection(con);
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_TYPES, sheetTypeVec);
                    } else
                    {
                        sheetTypeVec = (Vector) obj;
                    }
                Utility.logger.debug("took "
                        + (System.currentTimeMillis() - startTime));
                return sheetTypeVec;
            }

        /**
         * 7- get sheet types Dto
         * 
         * @param
         * @throws
         * @return SheetTypeDto
         * @see
         */
        public static SheetTypeDto getSheetTypes()
            {
                SheetTypeDto sheetTypeDto = new SheetTypeDto();
                sheetTypeDto.setSheetTypeModels(getSheetTypesVector());
                sheetTypeDto.setSheetTypeStatusModels(getSheetTypeStatuses());

                return sheetTypeDto;
            }

        /**
         * 6- get sheet type status
         * 
         * @param
         * @throws
         * @return a vector of SheetTypeStatusModel.
         * @see
         */
        public static final Vector getSheetTypeStatuses()
            {
                long startTime = System.currentTimeMillis();

                Object obj = CachEngine.getObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_STATUS);
                Vector sheetTypeStatus = null;

                if (obj == null)
                    {

                        sheetTypeStatus = new Vector();
                        try
                            {
                                Connection con = Utility.getConnection();
                                Statement stat = con.createStatement();

                                ResultSet res = stat.executeQuery("select * from VW_CR_SHEET_TYPE_STATUS order by  "
                                        + SheetTypeStatusModel.SHEET_TYPE_STATUS_NAME);
                                while (res.next())
                                    {
                                        SheetTypeStatusModel typeStatusModel = new SheetTypeStatusModel(res);
                                        sheetTypeStatus.add(typeStatusModel);
                                    }

                                stat.close();
                                Utility.closeConnection(con);
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        CachEngine.storeObject(ContractRegistrationInterfaceKey.CACH_OBJ_SHEET_STATUS, sheetTypeStatus);
                    } else
                    {
                        sheetTypeStatus = (Vector) obj;
                    }
                Utility.logger.debug("took "
                        + (System.currentTimeMillis() - startTime));
                return sheetTypeStatus;
            }

        public static String assignSheetSerialToDCM(String sheetSerialId,
                String dcmId)
            {
                String message = null;
                try
                    {

                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = " select count(*) record_count from VW_CR_DCM_SHEET where sheet_serial_id = "
                                + sheetSerialId + " and dcm_id = " + dcmId;
                        ResultSet res = stat.executeQuery(sql);
                        res.next();
                        int recordCount = res.getInt("record_count");
                        if (recordCount != 0)
                            {
                                message = ContractRegistrationInterfaceKey.SHEET_ASSIGNMENT_ERROR_MESSAGE_SAME_DCM;
                            } else
                            {
                                sql = " select count(*) record_count from VW_CR_DCM_SHEET where sheet_serial_id = "
                                        + sheetSerialId;
                                res = stat.executeQuery(sql);
                                res.next();
                                recordCount = res.getInt("record_count");
                                if (recordCount != 0)
                                    {
                                        message = ContractRegistrationInterfaceKey.SHEET_ASSIGNMENT_ERROR_MESSAGE_ANOTHER_DCM;
                                    } else
                                    {
                                        message = ContractRegistrationInterfaceKey.SHEET_ASSIGNMENT_CONFIRMATION_MESSAGE;
                                        sql = "insert into vw_CR_dcm_sheet (dcm_sheet_id,sheet_serial_id,dcm_id) values(SEQ_CR_SHEET_DCM.nextval,"
                                                + sheetSerialId
                                                + ","
                                                + dcmId
                                                + ")";
                                        stat.execute(sql);
                                    }
                            }
                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return message;
            }

        public static Vector getSheetRangeSerialToDCM(String inputFrom,
                String inputTo)
            {
                Vector sheetVector = new Vector();
                try
                    {

                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = " select *  from VW_CR_DCM_SHEET where sheet_serial_id >= "
                                + inputFrom
                                + " and  sheet_serial_id<= "
                                + inputTo;

                        ResultSet res = stat.executeQuery(sql);
                        while (res.next())
                            {
                                SheetDCMModel sheetDCMModel = new SheetDCMModel(res);
                                sheetVector.add(sheetDCMModel);
                            }

                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return sheetVector;
            }

        public static SheetDCMModel getSheetSerialDCM(String serialNum)
            {
                SheetDCMModel sheetDCMModel = null;
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = "select * from VW_CR_DCM_SHEET where sheet_serial_id = "
                                + serialNum;
                        ResultSet res = stat.executeQuery(sql);
                        if (res.next())
                            {
                                sheetDCMModel = new SheetDCMModel(res);
                            } else
                            {
                                sheetDCMModel = null;
                            }
                        stat.close();

                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return sheetDCMModel;
            }

        public static String assignSheetRangeSerialToDCM(String inputFrom,
                String inputTo, String dcmId)
            {
                String message = null;
                try
                    {
                        int startRange = Integer.parseInt(inputFrom);
                        int endRange = Integer.parseInt(inputTo);

                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String sql = " select count(*) record_count from VW_CR_DCM_SHEET where sheet_serial_id >= "
                                + inputFrom
                                + " and  sheet_serial_id<= "
                                + inputTo + " and dcm_id = " + dcmId;
                        ResultSet res = stat.executeQuery(sql);
                        res.next();
                        int recordCount = res.getInt("record_count");
                        if (recordCount != 0)
                            {
                                message = ContractRegistrationInterfaceKey.SHEET_RANGE_ASSIGNMENT_ERROR_MESSAGE_SAME_DCM;
                            } else
                            {
                                sql = " select count(*) record_count from VW_CR_DCM_SHEET where sheet_serial_id >= "
                                        + inputFrom
                                        + " and  sheet_serial_id<= " + inputTo;
                                res = stat.executeQuery(sql);
                                res.next();
                                recordCount = res.getInt("record_count");
                                if (recordCount != 0)
                                    {
                                        message = ContractRegistrationInterfaceKey.SHEET_RANGE_ASSIGNMENT_ERROR_MESSAGE_ANOTHER_DCM;
                                    } else
                                    {
                                        message = ContractRegistrationInterfaceKey.SHEET_ASSIGNMENT_CONFIRMATION_MESSAGE;
                                        for (int serial = startRange; serial <= endRange; serial++)
                                            {
                                                sql = "insert into vw_CR_dcm_sheet (dcm_sheet_id,sheet_serial_id,dcm_id) values(SEQ_CR_SHEET_DCM.nextval,"
                                                        + serial
                                                        + ","
                                                        + dcmId
                                                        + ")";
                                                stat.execute(sql);
                                            }
                                    }
                            }
                        stat.close();
                        Utility.closeConnection(con);

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return message;
            }

        public static void updateSheetSerialToDCM(String rowId[],
                String dcmId[])
            {
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        for (int i = 0; i < rowId.length; i++)
                            {
                                String sql = "update vw_CR_dcm_sheet set dcm_id = "
                                        + dcmId[i]
                                        + " where dcm_sheet_id = "
                                        + rowId[i];
                                stat.execute(sql);
                            }
                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }

        public static void updateSheetStatusRecord(String sheetId,
                String batchId, String userId)
            {
                // debug("update sheet " + sheetId);

                // debug("updateSheetStatusRecord " ) ;
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        long startTime = System.currentTimeMillis();

                        ResultSet res = stat.executeQuery("select sheet_serial_id from cr_sheet where sheet_id = "
                                + sheetId);
                        String sheetSerial = "";
                        if (res.next())
                            {
                                sheetSerial = res.getString("sheet_serial_id");
                            } else
                            {
                                System.out.println("sheet not found");
                                return;
                            }

                        String sql = "begin ? := CONTRACT_STATUS_TYPE_COUNT("
                                + sheetId + "); end;";
                        CallableStatement colableStat = con.prepareCall(sql);
                        colableStat.registerOutParameter(1, OracleTypes.CURSOR);
                        colableStat.execute();
                        res = (ResultSet) colableStat.getObject(1);

                        debug(" sheet update 1 time = "
                                + (System.currentTimeMillis() - startTime));
                        startTime = System.currentTimeMillis();

                        /*
                         * String sql =
                         * "select contract_status_type_name,count(*) contract_status_type_count from VW_CR_CONTRACT_STATUS_REIMPORT "
                         * +" where sheet_id = "+ sheetId
                         * +" group by contract_status_type_name"; ResultSet res
                         * = stat.executeQuery(sql);
                         */

                        Hashtable statusTable = new Hashtable();
                        int total = 0;
                        while (res.next())
                            {
                                String statusName = res.getString("contract_status_type_name");
                                Integer statusCount = new Integer(res.getString("contract_status_type_count"));
                                statusTable.put(statusName, statusCount);
                                total += statusCount.intValue();
                            }

                        debug(" sheet update 1.5 time = "
                                + (System.currentTimeMillis() - startTime));
                        startTime = System.currentTimeMillis();

                        sql = "select * from vw_CR_sheet_last_status where sheet_id = "
                                + sheetId
                                + " and sheet_serial_id ="
                                + sheetSerial;
                        res = stat.executeQuery(sql);
                        res.next();

                        debug(" sheet update 2 time = "
                                + (System.currentTimeMillis() - startTime));
                        startTime = System.currentTimeMillis();

                        String currentSheetPhase = res.getString("sheet_phase_name");
                        // count of how many contracts in this sheet of each
                        // status

                        Integer rejectedImportCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_IMPORT);
                        int rejectedImportCount = (rejectedImportCountInt == null
                                ? 0
                                : rejectedImportCountInt.intValue());
                        Integer newStatusCountInt = (Integer) statusTable.get(ContractModel.STATUS_IMPORTED);
                        int newStatusCount = (newStatusCountInt == null
                                ? 0
                                : newStatusCountInt.intValue());
                        Integer acceptedDeliveryCountInt = (Integer) statusTable.get(ContractModel.STATUS_ACCEPTED_DELIVERY);
                        int acceptedDeliveryCount = (acceptedDeliveryCountInt == null
                                ? 0
                                : acceptedDeliveryCountInt.intValue());
                        Integer rejectedDeliveryCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_DELIVERY);
                        int rejectedDeliveryCount = (rejectedDeliveryCountInt == null
                                ? 0
                                : rejectedDeliveryCountInt.intValue());
                        Integer acceptedVerifyCountInt = (Integer) statusTable.get(ContractModel.STATUS_ACCEPTED_VERIFY);
                        int acceptedVerifyCount = (acceptedVerifyCountInt == null
                                ? 0
                                : acceptedVerifyCountInt.intValue());
                        Integer rejectedVerifyCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_VERIFY);
                        int rejectedVerifyCount = (rejectedVerifyCountInt == null
                                ? 0
                                : rejectedVerifyCountInt.intValue());
                        Integer acceptedAuthinticationCountInt = (Integer) statusTable.get(ContractModel.STATUS_ACCEPTED_AUTHINTICATION);
                        int acceptedAuthinticationCount = (acceptedAuthinticationCountInt == null
                                ? 0
                                : acceptedAuthinticationCountInt.intValue());
                        Integer rejectedAuthinticationCountInt = (Integer) statusTable.get(ContractModel.STATUS_REJECTED_AUTHINTICATION);
                        int rejectedAuthinticationCount = (rejectedAuthinticationCountInt == null
                                ? 0
                                : rejectedAuthinticationCountInt.intValue());

                        String statusSQL = "";
                        String updatedPhaseName = "";
                        /*
                         * if ((newStatusCount == 0 )
                         * &&(rejectedDeliveryCount+rejectedImportCount==total))
                         * { //rejected delivery status statusSQL =
                         * "select * from vw_cr_sheet_status_type where Sheet_status_type_name=\'"
                         * +SheetModel.STATUS_CLOSED_IMPORT+"\'";
                         * updatedPhaseName =ContractRegistrationInterfaceKey.
                         * DELIVERY_VERIFICATION_PHASE; }
                         * 
                         * else if ((newStatusCount ==
                         * 0)&&(acceptedDeliveryCount>0)
                         * &&(acceptedVerifyCount==0)) { //accepted delivery
                         * status statusSQL =
                         * "select * from vw_cr_sheet_status_type where batch_status_type_name=\'"
                         * +BatchModel.STATUS_OPEN_VERIFY+"\'"; updatedPhaseName
                         * =ContractRegistrationInterfaceKey.
                         * CONTRACT_VERIFICATION_PHASE; }
                         */
                        debug(" sheet update 3 time = "
                                + (System.currentTimeMillis() - startTime));
                        startTime = System.currentTimeMillis();

                        if ((newStatusCount == 0)
                                && (rejectedVerifyCount + rejectedImportCount
                                        + rejectedDeliveryCount == total))
                            {
                                // rejected contract verification , phase =
                                // contract
                                // verification
                                statusSQL = "select * from vw_cr_sheet_status_type where SHEET_status_type_name=\'"
                                        + SheetModel.STATUS_REJECTED_VERIFY
                                        + "\'";
                                updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
                            } else if ((newStatusCount == 0)
                                && (acceptedVerifyCount > 0)
                                && (acceptedDeliveryCount == 0)
                                && (acceptedAuthinticationCount == 0)
                                && (rejectedAuthinticationCount == 0))
                            {
                                // accepted verify , phase = contract
                                // verification phase
                                statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                        + SheetModel.STATUS_ACCEPTED_VERIFY
                                        + "\'";
                                updatedPhaseName = ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE;
                            } else if ((newStatusCount == 0)
                                && (acceptedVerifyCount == 0)
                                && (acceptedDeliveryCount == 0)
                                && (acceptedAuthinticationCount == 0)
                                && (rejectedVerifyCount + rejectedImportCount
                                        + rejectedDeliveryCount
                                        + rejectedAuthinticationCount == total))
                            {
                                // rejected authentication , phase =
                                // authentication call
                                statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                        + SheetModel.STATUS_REJECTED_AUTHINTICATION
                                        + "\'";
                                updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
                            } else if ((newStatusCount == 0)
                                && (acceptedDeliveryCount == 0)
                                && ((acceptedAuthinticationCount > 0) || (currentSheetPhase.compareTo(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE) == 0)))
                            {
                                Utility.logger.debug(" 55555555555555555 ");
                                SheetAuthinticationStatisticModel sheetAuthinticationStatisticModel = getSheetAuthinticationStatisticModel(sheetId);

                                if (sheetAuthinticationStatisticModel != null)
                                    {
                                        int totalAccepted = sheetAuthinticationStatisticModel.getTotalContractsAuthinticated();
                                        int totalRejected = sheetAuthinticationStatisticModel.getTotalContractsRejectedAuthinticated();

                                        debug("totalAccepted  " + totalAccepted);
                                        debug("totalRejected  " + totalRejected);
                                        debug("total needed"
                                                + sheetAuthinticationStatisticModel.getTotalContractsNeededToCommission());

                                        if (sheetAuthinticationStatisticModel.getDiscardUnknownUnreachble())
                                            {
                                                System.out.println("inside if");
                                                int totalRejectedUnreachable = sheetAuthinticationStatisticModel.getTotalRejectedUnreachable();
                                                int totalAcceptedUnReachable = sheetAuthinticationStatisticModel.getTotalAcceptedUnrechable();
                                                int totalRejectedPOSUnknown = sheetAuthinticationStatisticModel.getTotalRejectedPosUnknown();
                                                int totalAcceptedPOSUnknow = sheetAuthinticationStatisticModel.getTotalAcceptedPosUnknown();

                                                int totalDescarded = totalRejectedUnreachable
                                                        + totalAcceptedUnReachable
                                                        + totalRejectedPOSUnknown
                                                        + totalAcceptedPOSUnknow;

                                                debug(" totalDescarded "
                                                        + totalDescarded);

                                                // this if statment check that
                                                // we have one contract
                                                // which is accepted
                                                // authentication
                                                // and check the value of the
                                                // number of contracts
                                                // authinticated after we remove
                                                // the number of
                                                // contracts discarded either
                                                // pos unknonw or unreachable
                                                // we compare the result of that
                                                // substraction
                                                // to the number of contracts
                                                // needed to allow commission
                                                if ((totalAccepted >= 1)
                                                        && (totalAccepted
                                                                + totalRejected - totalDescarded) >= sheetAuthinticationStatisticModel.getTotalContractsNeededToCommission())
                                                    {
                                                        // accepted commission
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                                                + "\'";
                                                    }
                                                /*
                                                 * this check that one contract
                                                 * is accpeted and that all teh
                                                 * contracts in this sheet are
                                                 * discarded since they are
                                                 * either unreachable or unknown
                                                 */
                                                else if ((totalAccepted >= 1)
                                                        && (totalDescarded == sheetAuthinticationStatisticModel.getTotalContractsEligableForAuthintication()))
                                                    {
                                                        // accepted commission
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                                                + "\'";
                                                    }
                                                // if both the above conditions
                                                // are not valid then it go
                                                // here where it set the status
                                                // to
                                                // accepted authinticaiton
                                                else
                                                    {
                                                        // accepted verify
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                                                + "\'";
                                                    }
                                            } else
                                            {
                                                System.out.println("beginign ofelse");
                                                if ((totalAccepted >= 1)
                                                        && ((totalAccepted + totalRejected) >= sheetAuthinticationStatisticModel.getTotalContractsNeededToCommission()))
                                                    {
                                                        // accepted commission
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_COMMISSION
                                                                + "\'";
                                                    } else
                                                    {
                                                        // accepted verify
                                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                                                + "\'";
                                                    }
                                            }
                                    } else
                                    {
                                        System.out.println("sheet authentication statistics is null");
                                        // accepted authentication
                                        statusSQL = "select * from vw_cr_sheet_status_type where sheet_status_type_name=\'"
                                                + SheetModel.STATUS_ACCEPTED_AUTHINTICATION
                                                + "\'";
                                    }
                                // update phase sql
                                updatedPhaseName = ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE;
                            }

                        // deubg info

                        debug("rejectedImport = " + rejectedImportCount);
                        debug("imported = " + newStatusCount);
                        debug("accepted delivery " + acceptedDeliveryCount);
                        debug("rejected delivery " + rejectedDeliveryCount);
                        debug("accepted verify " + acceptedVerifyCount);
                        debug("rejected verify " + rejectedVerifyCount);
                        debug("total " + total);
                        debug(statusSQL);

                        debug(" sheet update 4 time = "
                                + (System.currentTimeMillis() - startTime));
                        startTime = System.currentTimeMillis();

                        if (statusSQL.compareTo("") != 0)
                            {

                                ResultSet resStatus = stat.executeQuery(statusSQL);
                                resStatus.next();
                                int newStatusId = resStatus.getInt("sheet_status_type_id");

                                // this is to check if the old status of this
                                // sheet is the same
                                // as this new status
                                // then it doenst insert

                                String sqlOldStatus = "select * from vw_cr_sheet_last_status where sheet_id ="
                                        + sheetId
                                        + " and sheet_Serial_id="
                                        + sheetSerial;
                                resStatus = stat.executeQuery(sqlOldStatus);
                                boolean insertNewStatusFlag = true;
                                if (res.next())
                                    {
                                        int status = res.getInt("sheet_Status_type_id");
                                        if (status == newStatusId)
                                            {
                                                // should not insert since it is
                                                // the same old status
                                                insertNewStatusFlag = false;
                                            }
                                    }

                                debug(" sheet update 5 time = "
                                        + (System.currentTimeMillis() - startTime));
                                startTime = System.currentTimeMillis();

                                String insertNewStatus = "insert into vw_cr_sheet_status_insert(sheet_status_id,sheet_id,sheet_status_type_id,user_id,sheet_status_date) "
                                        + " values(seq_cr_sheet_status.nextval,"
                                        + sheetId
                                        + ","
                                        + newStatusId
                                        + ","
                                        + userId + ",sysdate)";

                                stat.execute(insertNewStatus);

                                System.out.println("&&&" + insertNewStatus);
                                sql = "select * from vw_CR_phase where phase_name ='"
                                        + updatedPhaseName + "'";
                                // debug("sql = " + sql);
                                res = stat.executeQuery(sql);
                                if (res.next())
                                    {
                                        String phaseId = res.getString("phase_id");
                                        String updatePhase = "update CR_sheet set phase_id="
                                                + phaseId
                                                + " where sheet_Id="
                                                + sheetId;
                                        debug("update phase sql = "
                                                + updatePhase);
                                        stat.execute(updatePhase);
                                    }

                                debug(" sheet update 6 time = "
                                        + (System.currentTimeMillis() - startTime));
                                startTime = System.currentTimeMillis();

                            }

                        if (stat != null)
                            stat.close();

                        Utility.closeConnection(con);

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

            }

        public static SheetWarningModel getSheetWarningType(
                String strSheetWarningTypeId)
            {
                SheetWarningModel newSheetWarningModel = null;
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String strSql = "select * from CR_SHEET_WARNING_TYPE where SHEET_WARNING_TYPE_ID = "
                                + strSheetWarningTypeId + "";
                        ResultSet res = stat.executeQuery(strSql);
                        while (res.next())
                            {
                                newSheetWarningModel = new SheetWarningModel();
                                newSheetWarningModel.setSheetWarningID(res.getString("SHEET_WARNING_TYPE_ID"));
                                newSheetWarningModel.setSheetWarningName(res.getString("SHEET_WARNING_TYPE_NAME"));
                                newSheetWarningModel.setSheetwarningDesc(res.getString("SHEET_WARNING_TYPE_DESC"));
                                newSheetWarningModel.setSheetPhaseID(res.getString("PHASE_ID"));
                                newSheetWarningModel.setSheetWarningTypeID(res.getString("WARNING_TYPE_ID"));
                                newSheetWarningModel.setSheetwarningStatusID(res.getString("WARNING_STATUS_ID"));
                            }
                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return newSheetWarningModel;
            }

        public static void updateSheetWarningType(String strSheetWarningTypeId,
                String strSheetWarningTypeName, String strSheetWarningTypeDesc,
                String strSheetWarningStatusID)
            {
                String strSql = "update CR_SHEET_WARNING_TYPE set SHEET_WARNING_TYPE_NAME = '"
                        + strSheetWarningTypeName
                        + "' "
                        + " ,SHEET_WARNING_TYPE_DESC = '"
                        + strSheetWarningTypeDesc
                        + "' "
                        + " ,WARNING_STATUS_ID = "
                        + strSheetWarningStatusID
                        + " "
                        + " where SHEET_WARNING_TYPE_ID = "
                        + strSheetWarningTypeId + " ";

                DBUtil.executeSQL(strSql);

            }

        public static void insertSheetWarningType(Connection con,
                String strSheetWarningTypeId, String strSheetWarningTypeName,
                String strSheetWarningTypeDesc, String strSheetWarningPhaseID)
            {
                String strSql = "insert into CR_SHEET_WARNING_TYPE (SHEET_WARNING_TYPE_ID,SHEET_WARNING_TYPE_NAME,SHEET_WARNING_TYPE_DESC,PHASE_ID,WARNING_TYPE_ID,WARNING_STATUS_ID) "
                        + " values("
                        + strSheetWarningTypeId
                        + ",'"
                        + strSheetWarningTypeName
                        + "','"
                        + strSheetWarningTypeDesc
                        + "',"
                        + strSheetWarningPhaseID + ",1,1) ";

                DBUtil.executeSQL(strSql, con);

            }

        public static void updateSheetType(String strSheetTypeId,
                String strSheetTypeStatusID)
            {
                String strSql = "update CR_SHEET_TYPE set SHEET_TYPE_STATUS_ID = "
                        + strSheetTypeStatusID
                        + " "
                        + " where SHEET_TYPE_ID = " + strSheetTypeId + " ";
                DBUtil.executeSQL(strSql);

            }

        public static SheetTypeModel getCrSheetType(String strSheetTypeId)
            {
                SheetTypeModel newSheetTypeModel = null;
                try
                    {
                        Connection con = Utility.getConnection();
                        Statement stat = con.createStatement();
                        String strSql = "select * from CR_SHEET_TYPE where SHEET_TYPE_ID = "
                                + strSheetTypeId + "";
                        ResultSet res = stat.executeQuery(strSql);
                        while (res.next())
                            {
                                newSheetTypeModel = new SheetTypeModel();
                                newSheetTypeModel.setSheetTypeId(res.getString("SHEET_TYPE_ID"));
                                newSheetTypeModel.setSheetTypeName(res.getString("SHEET_TYPE_NAME"));
                                newSheetTypeModel.setSheetTypeDesc(res.getString("SHEET_TYPE_DESC"));
                                newSheetTypeModel.setSheetTypeStatusId(res.getString("SHEET_TYPE_STATUS_ID"));
                                newSheetTypeModel.setSheetAuthPercentage(res.getDouble("SHEET_AUTH_PERCENTAGE"));
                                newSheetTypeModel.setSheetPOSQuestionFlag(res.getString("POS_QUESTION"));
                                newSheetTypeModel.setSheetDiscardUnrealUnreachableFlag(res.getString("DISCARD_UNREAL_UNREACHABLE"));
                            }
                        stat.close();
                        Utility.closeConnection(con);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                return newSheetTypeModel;
            }

        public static void insertSheetType(Connection con,
                SheetTypeModel sheetTypeModel)
            {
                String sheetTypeId = sheetTypeModel.getSheetTypeId();
                String sheetTypeName = sheetTypeModel.getSheetTypeName();
                String sheetTypeDesc = sheetTypeModel.getSheetTypeDesc();
                String sheetTypeStatusId = sheetTypeModel.getSheetTypeStatusId();
                double sheetAuthPercentage = sheetTypeModel.getSheetAuthPercentage();
                String sheetPOSQuestionFlag = sheetTypeModel.getSheetPOSQuestionFlag();
                String sheetDiscardUnrealUnreachableFlag = sheetTypeModel.getSheetDiscardUnrealUnreachableFlag();

                String strSql = "insert into CR_SHEET_TYPE (SHEET_TYPE_ID,SHEET_TYPE_NAME,SHEET_TYPE_DESC,SHEET_TYPE_STATUS_ID,SHEET_AUTH_PERCENTAGE,POS_QUESTION,DISCARD_UNREAL_UNREACHABLE) "
                        + " values("
                        + sheetTypeId
                        + ",'"
                        + sheetTypeName
                        + "','"
                        + sheetTypeDesc
                        + "',"
                        + sheetTypeStatusId
                        + ","
                        + sheetAuthPercentage
                        + ","
                        + sheetPOSQuestionFlag
                        + ","
                        + sheetDiscardUnrealUnreachableFlag + ") ";

                DBUtil.executeSQL(strSql, con);

            }

        public static void updateSheetType(Connection con,
                SheetTypeModel sheetTypeModel)
            {
                String sheetTypeId = sheetTypeModel.getSheetTypeId();
                String sheetTypeName = sheetTypeModel.getSheetTypeName();
                String sheetTypeDesc = sheetTypeModel.getSheetTypeDesc();
                String sheetTypeStatusId = sheetTypeModel.getSheetTypeStatusId();
                double sheetAuthPercentage = sheetTypeModel.getSheetAuthPercentage();
                String sheetPOSQuestionFlag = sheetTypeModel.getSheetPOSQuestionFlag();
                String sheetDiscardUnrealUnreachableFlag = sheetTypeModel.getSheetDiscardUnrealUnreachableFlag();

                String strSql = "update CR_SHEET_TYPE set SHEET_TYPE_NAME = '"
                        + sheetTypeName + "' , " + " SHEET_TYPE_DESC = '"
                        + sheetTypeDesc + "' , " + " SHEET_TYPE_STATUS_ID = "
                        + sheetTypeStatusId + " , "
                        + " SHEET_AUTH_PERCENTAGE = " + sheetAuthPercentage
                        + " , " + " POS_QUESTION = " + sheetPOSQuestionFlag
                        + " , " + " DISCARD_UNREAL_UNREACHABLE = "
                        + sheetDiscardUnrealUnreachableFlag
                        + " where SHEET_TYPE_ID = " + sheetTypeId;

                DBUtil.executeSQL(strSql, con);

            }
        
        
        
        
        
        public static Vector getCRAllMonths(Connection con)
        {
            Vector crVec = new Vector();
            try
            {
                Statement stat = con.createStatement();
                String strSql = "select * from CR_MONTHS_MANAGEMENT order by YEAR , MONTH ";
                ResultSet res = stat.executeQuery(strSql);
                while (res.next())
                {
                    crVec.add(new MonthsModel(res));
                }
                res.close();
                stat.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return crVec;
        }
        
        
        
        
        public static String insertCRNewYear(String year, Connection con)
        {
        	String msg = "";
        	 Statement stat;
    		try {
    			stat = con.createStatement();
             String strSql = "select * from CR_MONTHS_MANAGEMENT where YEAR = " + year;
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
               strSql = "insert into CR_MONTHS_MANAGEMENT (YEAR,MONTH,FIRST_STATUS,SECOND_STATUS) values ("+ year+","+ i +",0,0)";
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
        
        
        
        
        public static void changeCRMonthStatus(String strUserID , String statusId , String status , String month , String year, Connection con)
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
              strSql = "update CR_MONTHS_MANAGEMENT set FIRST_STATUS = "+status+" where YEAR ="+year+" and MONTH="+month;
            else
              strSql = "update CR_MONTHS_MANAGEMENT set SECOND_STATUS = "+status+" where YEAR ="+year+" and MONTH="+month;
           
            System.out.println(strSql);     
            DBUtil.executeSQL(strSql, con);
            
            String strSqlQuery = "insert into CR_MONTHS_MANAGEMENT_TRACKER (ACTION,USER_ID,ACTION_GROUP) values ("+ status+","+ strUserID +","+statusId+")";
            System.out.println(strSqlQuery);     
            DBUtil.executeSQL(strSqlQuery, con);
            
          
        	 

        }
        
        /*
         * Function check the first status of batch using date if 0 so open if 1 so closed
         * so function return data if batch closed
         */
        
        public static String selectCRBatchStatus(String year, String month , Connection con)
        {
        	 String msg = "";
        	 Statement stat;
    		
		try {
			  stat = con.createStatement();
			  String strSql = "select * from CR_MONTHS_MANAGEMENT where YEAR = "+ year+" and MONTH="+month+" and FIRST_STATUS=1";
			  
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
        
        
    }