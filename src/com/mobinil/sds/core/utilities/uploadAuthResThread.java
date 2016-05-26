package com.mobinil.sds.core.utilities;

import com.mobinil.mcss.lcs.model.LCSContractData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mobinil.sds.core.system.authenticationResult.dao.AuthResDAO;
import com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey;

public class uploadAuthResThread extends Thread {

    Connection con = null;
    private String year;
    private String month;
    private String Status;
    private String label;
    private String filepath;
    private String description;
    private String catId;
    private String fileTypeId;
    private Boolean simPureCheck;
    private String user_id;
    private boolean uploadSearchFlag = false;

    public uploadAuthResThread() {
    }

    public uploadAuthResThread(String year, String month, String Status, String label, String user_id, String filepath) {

        this.year = year;
        this.month = month;
        this.Status = Status;
        this.label = label;
        this.user_id = user_id;
        this.filepath = filepath;



    }

    public uploadAuthResThread(String year, String month, String Status, String label, String description, String user_id, String filepath, String catId, String fileTypeId, Boolean simPureCheck) {

        this.year = year;
        this.month = month;
        this.Status = Status;
        this.label = label;
        this.user_id = user_id;
        this.filepath = filepath;
        this.description = description;
        this.simPureCheck = simPureCheck;
        this.catId = catId;
        this.fileTypeId = fileTypeId;

        uploadSearchFlag = true;
    }

    public void run() {

        if (uploadSearchFlag == true) {
            Statement st = null;


            try {
                con = Utility.getConnection();
                st = con.createStatement();

                Long fileid = null;
                String line = null;


                BufferedReader input = null;
                try {
                    input = new BufferedReader(new FileReader(filepath));
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                int count = 0;
                try {



                    while ((line = input.readLine()) != null) {
                        count++;

                        if (count == 1) {
                            fileid = AuthResDAO.insertSearchFile(con, year, month, "processing", description, user_id, label, catId, fileTypeId);
                        }

                        System.out.println("line issss " + line);
                        if (count != 0) {
                            String fields = line;
                            String v1 = fields;
                            String[] lineFields = v1.split(",");
                            Boolean checkTypeFromDBFunORDAOMethod = null;
                            if (v1 == null) {
                                v1 = "";
                                System.out.println("count isssssss      " + count);


                            }
                            System.out.println("the values" + v1);


                            if (simPureCheck) {
                                checkTypeFromDBFunORDAOMethod = AuthResDAO.checkIfSimSerialExist(con, v1, year, month, label);
                            } else {
                                checkTypeFromDBFunORDAOMethod = AuthResDAO.checkDBFunctionIfSimSerialExist(con, v1, year, month, label);

                            }


                            if (checkTypeFromDBFunORDAOMethod) {
                                AuthResDAO.insertAuthResSearchData(con, st, fileid, lineFields, year, month, label, fileTypeId);
                            } else {

                                AuthResDAO.insertInvalidSimSerial(con, st, fileid.toString(), lineFields, fileTypeId);

                            }


                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try {
                        input.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                AuthResDAO.updateAuthResASearchStatusFile(con, fileid.toString(), "active");


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (st != null) {
                    try {
                        st.close();
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

        } else {
            try {
                con = Utility.getConnection();


                Long fileid = null;
                String line = null;


                BufferedReader input = null;
                try {
                    input = new BufferedReader(new FileReader(filepath));
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


                int count = 0;
                try {

                    LCSContractData lcsContractDataUtility = new LCSContractData(con);

                    //Connection lcsConnection = Utility.getLCSConnection();
//
//                    lcsContractDataUtility = new com.mobinil.sds.core.lcs.LCSContractData();
//
//                    lcsContractDataUtility.openLCSConnection();
//                    Connection sdsConnection = lcsContractDataUtility.openSDSConnection();

                    lcsContractDataUtility.loadData();


                    Statement stat = con.createStatement();

                    fileid = AuthResDAO.insertNewFile(year, month, "processing", user_id, label, Thread.currentThread().getId());

                    long stime = System.currentTimeMillis();
                    while ((line = input.readLine()) != null) {
                        count++;




                        String[] fields = line.split(",");
                        String[] fields_apr = new String[3];

                        for (int i = 0; i < fields.length && 3 > i; i++) {
                            fields_apr[i] = fields[i];
                        }

                        if (fields_apr[0] != null && !fields_apr[0].trim().equalsIgnoreCase("")) {

                            AuthResDAO.insertAuthResData(fileid, fields_apr[0], fields_apr[1], fields_apr[2], lcsContractDataUtility, stat);

                        } else {
                            System.out.println("The problimatic line number is" + count);
                        }
                        fields = null;
                        fields_apr = null;


                    }

                    String strSql = null;

                    strSql = "	UPDATE AUTH_RES_DATA  SET (CIF_BATCH_DATE,SHEET_DISTRIBUTOR_CODE,SHEET_SUPER_DEALER,SHEET_POS_CODE,CONTRACT_CUSTOMER_1ST_NAME,  CONTRACT_CUSTOMER_2ND_NAME ,CONTRACT_CUSTOMER_LST_NAME , CONTRACT_CUSTOMER_ID,CONTRACT_CUSTOMER_ID_TYPE)=( select cr_batch.BATCH_DATE , cr_sheet.SHEET_DISTRIBUTER_CODE , cr_sheet.SHEET_SUPER_DEALER_CODE , cr_sheet.SHEET_POS_CODE,CONTRACT_CUSTOMER_1ST_NAME,  CONTRACT_CUSTOMER_2ND_NAME ,CONTRACT_CUSTOMER_LST_NAME , CONTRACT_CUSTOMER_ID,CONTRACT_CUSTOMER_ID_TYPE from "
                            + "  cr_batch, cr_sheet, cr_contract,cr_contract_status,cr_contract_status_type"
                            + "  where  cr_batch.batch_id  = cr_sheet.batch_id and cr_sheet.sheet_id = cr_contract.sheet_id and cr_contract.CONTRACT_MAIN_SIM_NO = sim_serial and cr_contract.CONTRACT_LAST_STATUS_ID = cr_contract_status.CONTRACT_STATUS_ID and cr_contract_status.CONTRACT_STATUS_TYPE_ID = cr_contract_status_type.CONTRACT_STATUS_TYPE_ID"
                            + "  and cr_contract_status_type.TYPE_ID = 1 and rownum < 2) where  file_id=" + fileid;

                    System.out.println("Step 1");
                    System.out.println(strSql);
                    stat.execute(strSql);
                    //underneath you will find the decode 
                    strSql =
                            "  UPDATE auth_res_data  "
                            + "  SET (pos_code, second_pos_code, stf_batch_date) =  "
                            + " (SELECT   vw_sfr_sheet.pos_code,  "
                            + " vw_sfr_sheet.second_pos_code, batch_date  "
                            + " FROM vw_sfr_sheet, vw_sfr_batch, sfr_sim   "
                            + " WHERE sfr_sim.SIM_SERIAL =  AUTH_RES_DATA.sim_serial"
                            + " AND vw_sfr_sheet.sheet_id = sfr_sim.sheet_id  "
                            + " AND vw_sfr_sheet.batch_id = vw_sfr_batch.batch_id  "
                            + " AND sfr_sim.sim_status_type_id = 1  "
                            + " and ROWNUM <= 1)  "
                            + " WHERE file_id = " + fileid;


                    /*
                     * " UPDATE AUTH_RES_DATA SET
                     * (POS_CODE,SECOND_POS_CODE,STF_BATCH_DATE) = (select *
                     * from ( select vw_sfr_sheet.pos_code,
                     * vw_sfr_sheet.second_pos_code , batch_date"+ " from
                     * vw_sfr_sheet,vw_sfr_batch ,sfr_sim WHERE sfr_sim.sim_pure
                     * = decode (length ( auth_res_data.sim_serial) , '20',
                     * substr(auth_res_Data.sim_serial,2,18)
                     * ,auth_res_Data.sim_serial)" +"and vw_sfr_sheet.sheet_id =
                     * sfr_sim.sheet_id and
                     * vw_sfr_sheet.BATCH_ID=vw_sfr_batch.BATCH_ID"+ " and
                     * sfr_sim.SIM_STATUS_TYPE_ID = 1 order by batch_date desc )
                     * where rownum<=1) where file_id=" +fileid ;
                     */
                    System.out.println("Step 2");
                    System.out.println(strSql);
                    stat.execute(strSql);

                    //Ahmed Adel 
                    strSql = "UPDATE AUTH_RES_DATA"
                            + " SET (AUTH_RES_DATA.CODE_131 , AUTH_RES_DATA.COMMSSION_DATE_131 )= "
                            + " (SELECT POS_CODE , TIME_STAMP FROM NTRA_EGY_ACTIVE_SIM_FILTER "
                            + " WHERE CONCAT ( SUBSTR(AUTH_RES_DATA.SIM_SERIAL,2,20) ,'F') = NTRA_EGY_ACTIVE_SIM_FILTER.SIM_SERIAL)"
                            + " WHERE file_id = " + fileid;


                    System.out.println("Step 3");
                    System.out.println(strSql);
                    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

                    stat.execute(strSql);






                    long etime = System.currentTimeMillis();
                    System.out.println("Auth Res **************************");
                    System.out.println("Count = " + count);
                    System.out.println("Time =" + ((etime - stime) / (60 * 1000)) + " Minutes");
                    stat.close();

                    //	lcsConnection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    //	logger.fatal(e);
                    //	systemLogger.fatal(" Process Cannot Connect to Database");
                    //	systemLogger.fatal(e);
                    //	counter.incCounter(countErrors + "");

                } finally {
                    try {
                        input.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                System.out.println("%%%%%%%%%%%%%");
                AuthResDAO.updateAuthResStatus(con, fileid.toString(), "active");

                int insertCoumt = AuthResDAO.getinsrerteValue(fileid);

                AuthResDAO.insertStatistics(fileid.intValue(), count, insertCoumt);

                System.out.println("ihe inserted   count  is" + insertCoumt);



                // this.stop();  //not needed at all  it will make u not close the connection even

            } catch (Exception e) {
                // TODO Auto-generated catch block
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

    }
}
