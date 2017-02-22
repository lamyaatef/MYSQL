package com.mobinil.sds.core.system.supervisor.dao;

import com.mobinil.sds.core.system.nomad.dao.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import com.mobinil.sds.core.utilities.Utility;

import com.mobinil.sds.core.system.nomadFile.model.NomadFileModel;
import com.mobinil.sds.core.system.nomadFile.model.NomadFileUserLabelModel;
import com.mobinil.sds.core.system.nomadFile.model.NomadLabelModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class SalesrepFileDAO{
public static final String PHONE_NUMBER = "0900";
    public static Vector getallNomadfiles(Connection con, String userId) {
        Vector vec = new Vector();
        String personName="";
        System.out.println("getallNomadfiles ");
        try {
            Statement stat = con.createStatement();
            //String strSql = "select gf.GEN_DCM_NOMAD_FILE_ID, gf.USER_ID, gf.FILE_CREATION_DATE, gf.FILE_UPLOAD_DATE, gf.TOTAL_NUMBER_OF_RECORDS, gl.NOMAD_LABEL_NAME from  GEN_DCM_NOMAD_FILE gf, GEN_DCM_NOMAD_LABEL gl where gf.user_id = '"+userId+"' and gf.label_id = gl.NOMAD_LABEL_ID and gf.status <> 'Deleted'";
            System.out.println("^^^^^^^^^^");
            String strSql = "select * from gen_dcm_nomad_file order by to_number(gen_dcm_nomad_file_id) desc";
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("str sql "+ strSql);
            ResultSet res = stat.executeQuery(strSql);
            personName = getUserNameById(con, userId);
            while (res.next()) {
                System.out.println("in resultttt ");
                vec.add(new NomadFileModel(res,personName));
                }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    /*public static Vector<SimInfoModel> getallAuthSimINfofiles(Connection con, String userId) {
        String query = "select FILE_ID ,STATUS ,TIME_STAMP ,USER_ID , LABEL_NAME LABEL_ID, START_TIME , END_TIME , ROW_COUNT , to_char((END_TIME-START_TIME)*24) Duration  from  AUTH_SIM_INFO_FILE , AUTH_RES_label where "
                + " AUTH_RES_label.label_id in (select label_id from AUTH_RES_USER_LABEL where user_id = '-11') and "
                + " AUTH_SIM_INFO_FILE.LABEL_ID=AUTH_RES_label.LABEL_ID and status <> 'Deleted' order by FILE_ID desc";
        return DBUtil.executeSqlQueryMultiValue(query, SimInfoModel.class, con);
    }*/

    public static void updateNomadFileStatus(Connection con, String file_id, String status) {

        try {

            Statement stat = con.createStatement();

            String strSql = "update GEN_DCM_NOMAD_FILE"
                    + "  SET  STATUS = '" + status + "' , file_upload_date = SYSTIMESTAMP where GEN_DCM_NOMAD_FILE_ID = '" + file_id + "'";

            System.out.println("the change status query  is " + strSql);

            stat.execute(strSql);
            stat.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

  
    
    
            //return nomadfiledao
    public static void updateNomadFile(Long fileId, int totalNoOfRecords/*, String tableId*/)
    {
        Connection con = null;
        try {

            con = Utility.getConnection();
            Statement stat = con.createStatement();
           // String strSql = "update GEN_DCM_NOMAD_FILE set total_number_of_records = '"+totalNoOfRecords+"', file_upload_date = SYSDATE, max_date = (select max(to_char(received_on,'MM-dd-yyyy hh24:mi')) max_date from GEN_DCM_NOMAD), min_date = (select min(to_char(received_on,'MM-dd-yyyy hh24:mi')) min_date from GEN_DCM_NOMAD) where gen_dcm_nomad_file_id='"+fileId+"'";
            
            // Hagry:
            String strSql = "update gen_dcm_nomad_file set (min_date,max_date,total_number_of_records,file_upload_date)= (select min(update_on), max(update_on),count(*),SYSDATE from gen_dcm_nomad where gen_dcm_nomad_file_id = '"+fileId+"') where gen_dcm_nomad_file_id = '"+fileId+"'";
            System.out.println("UPDATE NOMAD QUERY : "+strSql);
            stat.executeUpdate(strSql);
            String commit = "commit";
            stat.executeUpdate(commit);
            stat.close();
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
    public static void delNomadFileById(Connection con, String file_id) {
        DBUtil.executeSQL("delete from gen_dcm_nomad where gen_dcm_nomad_file_id='" + file_id + "'", con);
         DBUtil.executeSQL("delete from gen_dcm_nomad_file where gen_dcm_nomad_file_id='" + file_id + "'", con);
       
     
    }
    private static boolean isValidDate(String inDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    try {
        //System.out.println("in date "+inDate);
      dateFormat.parse(inDate.trim());
    } catch (ParseException pe) {
        //System.out.println("FALSE");
      return false;
    }
    return true;
  }
    
    
    public static void insertSalesrepData(Connection con, Statement stat,String userId,String[] lineFields,boolean isemptyField,int count/*, String fileDate, int updatedIndex*/) {
        //System.out.println("FILE ID : "+fileID+" insertNomadData func (1) : "+lineFields.length+" seller index "+sellerIndex);
        String concatFields = "";
        String strSql = "";
        String strUserSql = "";
        String strUserDetailSql = "";
        System.out.println("insertSalesrepData : go to record no. "+count);
                            
        try {
            //Connection con2 = Utility.getConnection();
            Statement st2 = con.createStatement();
        
        for (int i=0; i<lineFields.length;i++)
        {
            System.out.println("linefield["+i+"] is "+lineFields[i]);
           /* if(isemptyField)
            {
                //lineFields[i]=" ";
                concatFields +="' '" + ",";
                //concatFields +="'"+lineFields[i]+"'"+ ",";
            }*/
            concatFields += "'"+lineFields[i]+"'"+",";
           
        }
        
        concatFields = concatFields.substring(0, concatFields.length()-1);
        System.out.println("Line Text Concatenated REP: "+concatFields);
        
        //Long repId = Utility.getSequenceNextVal(con, "SEQ_SCM_SALESREP_ID");
        Long repId = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_ID");
        Long repDetailId = Utility.getSequenceNextVal(con, "seq_dcm_user_detail_id");
        
        
        String sqlCheckRepName = "select * from scm_salesrep where email= '"+lineFields[1]+"' ";
        System.out.println("check rep email "+sqlCheckRepName);
        ResultSet rs = stat.executeQuery(sqlCheckRepName);
        boolean found = false;
        if(rs.next())
            found = true;
        
        if(found)
        {
            System.out.println("rep name found");
            strUserSql = "update SCM_SALESREP set teamlead_id='',sup_id='', CREATION_TIMESTAMP = SYSTIMESTAMP, SALESREP_NAME='"+lineFields[0]+"', EMAIL='"+lineFields[1]+"', mobile='"+lineFields[2]+"' where salesrep_id ="+rs.getLong("salesrep_id");
            System.out.println("query2 "+strUserSql);
            
            st2.executeUpdate(strUserSql);
            strUserDetailSql = "update dcm_user_detail set creation_user_id="+userId+",user_full_name='"+lineFields[0]+"',user_email='"+lineFields[1]+"',user_mobile='"+lineFields[2]+"',CREATION_TIMESTAMP=SYSTIMESTAMP where user_id="+rs.getLong("salesrep_id");
            System.out.println("query3 inner "+strUserDetailSql);
            st2.executeUpdate(strUserDetailSql);
            
        } 
            
        else
        {
            //Long repDetailId = Utility.getSequenceNextVal(con, "seq_dcm_user_detail_id");
            System.out.println("rep name NOT found");
            strUserSql = "insert into dcm_user (dcm_user_id, user_id,user_level_type_id,user_detail_id,user_status_type_id,user_level_id) values("+repId.longValue()+","+userId+",6,"+repDetailId.longValue()+",1,6)";
            System.out.println("query2 "+strUserSql);
            strUserDetailSql = "insert into dcm_user_detail (REGION_ID,USER_DETAIL_STATUS_ID,user_detail_id, user_id,creation_user_id,user_full_name,user_email,user_mobile,CREATION_TIMESTAMP) values(-1,1,"+repDetailId.longValue()+","+repId.longValue()+","+userId+","+concatFields+",SYSTIMESTAMP)";
            System.out.println("query3 "+strUserDetailSql);
            st2.executeUpdate(strUserSql);
            st2.executeUpdate(strUserDetailSql);
            
            strSql = "insert into SCM_SALESREP ( SALESREP_ID, SALESREP_NAME, EMAIL, MOBILE, CREATION_TIMESTAMP) values ("+repId.longValue()+","+concatFields+",SYSTIMESTAMP)";
            System.out.println("query "+strSql);
            st2.executeUpdate(strSql);
        }
        
        
           
            System.out.println("............INSERTED........"+count);

        } catch (Exception e) {
            
                     System.out.println("............NOT INSERTED........"+count+" "+"EXCEPTION in " + strSql);
            e.printStackTrace();
        }
        

    }

    
    
    public static int getSalesrepDataRecords(Connection con, Statement stat,Long fileID) throws ParseException {
        int count=-1;
        try {
            String strSql = "select count(*) from SCM_SALESREP";
            System.out.println("SQL is " + strSql);
            ResultSet rs = stat.executeQuery(strSql);
            if(rs.next())
                count = Integer.parseInt(rs.getString("count(*)"));
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;

    }

    
    
    //return nomadfiledao
    public static Long insertNewFile(/*String year, String month,*/String status, String user_id, String label_id/*, Long threadId*/) {

        Connection con = null;
        Long FILE_ID = null;

        try {

            con = Utility.getConnection();
            Statement stat = con.createStatement();
            FILE_ID = Utility.getSequenceNextVal(con, "GEN_DCM_NOMAD_FILE_ID_SEQ");//SEQ_AUTH_RES_FILE_ID
            System.out.println("file id = "+ FILE_ID);
            String strSql = "insert into GEN_DCM_NOMAD_FILE (gen_dcm_nomad_file_id,user_id,file_creation_date, label_id ,status) values('"+FILE_ID+"','"+user_id+"',SYSDATE,'"+label_id+"','"+status+"')";
            System.out.println("the nomad file insert query is " + strSql);
            stat.execute(strSql);
            
            stat.close();
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

        return FILE_ID;
    }

   /* public static boolean checkFile(String year, String month, String label) {
        boolean Status = false;
        try {

            Connection con = Utility.getConnection();

            Statement stat = con.createStatement();




            String strSql = "select * from  AUTH_RES_FILE"
                    + "  where YEAR = '" + year + "' and  MONTH='" + month + "' and LABEL_ID='" + label + "' and lower(status) <> 'deleted'  ";

            System.out.println("check status query is " + strSql);

            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {

                Status = true;


            }

            stat.execute(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public static int getinsrerteValue(Long file_id) {
        int count = 0;
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            System.out.println("inserted rows  " + file_id);
            ResultSet res = stat.executeQuery("select count(*)  as counter   from   AUTH_RES_DATA where file_Id=" + file_id + "");
            res.next();
            count = res.getInt("counter");

            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;


    }*/

  /*  public static void insertAuthResData(Long fileid, String sim, String dial, String activationDate, LCSContractData lcsContractDataUtility, Statement stat) {



        String strSql = null;
        if (activationDate == null) {
            strSql = "insert into AUTH_RES_DATA(FILE_ID,SIM_SERIAL,DIAL)"
                    + " values(" + fileid + ",'" + sim + "','" + dial + "')";
        } else {
            strSql = "insert into AUTH_RES_DATA(FILE_ID,SIM_SERIAL,DIAL,ACTIVATION_DATE)"
                    + " values(" + fileid + ",'" + sim + "','" + dial + "',to_Date('" + activationDate + "','dd/mm/yyyy'))";
        }

        try {
            stat.execute(strSql);
        } catch (Exception e) {
            // System.out.println(strSql);
            // e.printStackTrace();
        }


        String simLCS = "";
        if (sim.length() == 20) {
            simLCS = sim.substring(1, 19);
        } else {
            simLCS = sim;
        }

        boolean flag = false;
        LCSContractInfo info = null;
        while (!flag) {
            try {
                info = lcsContractDataUtility.getLCSData(simLCS);
                flag = true;
            } catch (Exception e) {

                e.printStackTrace();
            }
        }


        String sqlLCS = null;

        if (info.issueDate == null) {
            sqlLCS = "update AUTH_RES_DATA set lcs_transaction_type_id=" + info.transactionTypeId + ", lcs_dcm_id=" + info.dcmId + ",lcs_contract_type_id=" + info.productId + " ,activation_date=nvl(activation_date, GET_SIM_ACT_DATE_BY_CATEGORY('" + sim + "',(select product_category_id from gen_product where product_id =" + info.productId + ")))"
                    + " where sim_serial ='" + sim + "' and file_id=" + fileid;
        } else {
            sqlLCS = "update AUTH_RES_DATA set lcs_transaction_type_id=" + info.transactionTypeId + ", lcs_dcm_id=" + info.dcmId + ",lcs_init_date= to_date('" + info.issueDate.toString() + "','yyyy-mm-dd'), lcs_contract_type_id=" + info.productId + ", activation_date=nvl(activation_date, GET_SIM_ACT_DATE_BY_CATEGORY('" + sim + "',(select product_category_id from gen_product where product_id =" + info.productId + ")))"
                    + " where sim_serial ='" + sim + "' and file_id=" + fileid;

        }

        try {
            stat.execute(sqlLCS);
        } catch (Exception e) {
            System.out.println(sqlLCS);
            e.printStackTrace();
        }

        /*
         * This should be removed to another place to be updated once try{
         * String s =" update auth_Res_Data set service_Class = (select
         * service_class from dem_logistics_prepaid where
         * DEM_LOGISTICS_PREPAID.SIMNO = substr(sim_serial,2,24) ) where file_id
         * = "+fileid ; stat.execute (s); } catch(Exception e) {
         * e.printStackTrace(); }
         *
         */
//		   stat.close();			
    //}

    /*public static void insertInvalidSimSerial(Connection con, Statement stat, String search_fileid, String fileFields[], String fileTypeId) {
        String Filefield2 = "";
        String sim = fileFields[0];
        if (fileFields.length > 1 && fileTypeId.compareTo(AuthResInterfaceKey.CONTROL_SELECT_OPTION_SIM_DATA) == 0) {
            Filefield2 = fileFields[1];
        }

        try {



            String strSql = "insert into AUTH_RES_SEARCH_TEMP_SERIAL(SEARCH_ID,SIM_SERIAL,FILE_FIELD_2)"
                    + " values('" + search_fileid + "','" + sim + "','" + Filefield2 + "') ";
            System.out.println("the data insert invalid sims query is " + strSql);


            stat.executeUpdate(strSql);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

    public static String getLabelName(String LABEL_ID) {
        String labelName="";
        try {

            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String strSql = "select nomad_label_name from  GEN_DCM_NOMAD_LABEL where NOMAD_LABEL_ID='" + LABEL_ID + "' ";



            System.out.println("GET  labeName  QUERY ISSSSSSSS" + strSql);
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
               labelName = res.getString("nomad_label_name");
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelName;
    }

    /**
     * UPDATE AUTH_RES_DATA SET (POS_CODE,SECOND_POS_CODE,STF_BATCH_DATE) =
     * (select * from ( select pos_code, second_pos_code , batch_date from
     * vw_sfr_sheet,vw_sfr_batch ,sfr_sim where sfr_sim.sim_serial
     * ='08920011102078153972' and vw_sfr_sheet.sheet_id = sfr_sim.sheet_id and
     * vw_sfr_sheet.BATCH_ID=vw_sfr_batch.BATCH_ID and
     * sfr_sim.SIM_STATUS_TYPE_ID = 1 order by batch_date desc ) where
     * rownum<=1)
     *
     *
     *
     *
     *
     * ------------------------------------------
     *
     *
     *
     *
     * UPDATE AUTH_RES_DATA SET
     * (CIF_BATCH_DATE,SHEET_DISTRIBUTOR_CODE,SHEET_SUPER_DEALER,SHEET_POS_CODE)=(
     * select cr_batch.BATCH_DATE , cr_sheet.SHEET_DISTRIBUTER_CODE ,
     * cr_sheet.SHEET_SUPER_DEALER_CODE , cr_sheet.SHEET_POS_CODE from cr_batch,
     * cr_sheet, cr_contract,cr_contract_status,cr_contract_status_type where
     * cr_batch.batch_id = cr_sheet.batch_id and cr_sheet.sheet_id =
     * cr_contract.sheet_id and cr_contract.CONTRACT_MAIN_SIM_NO =
     * '08920015011043832428' and cr_contract.CONTRACT_LAST_STATUS_ID =
     * cr_contract_status.CONTRACT_STATUS_ID and
     * cr_contract_status.CONTRACT_STATUS_TYPE_ID =
     * cr_contract_status_type.CONTRACT_STATUS_TYPE_ID and
     * cr_contract_status_type.TYPE_ID = 1)
     *
     *
     */
    /*public static void updateCIFData(Connection con) {

        try {

            Statement stat = con.createStatement();

            String strSql = "	UPDATE AUTH_RES_DATA  SET (CIF_BATCH_DATE,SHEET_DISTRIBUTOR_CODE,SHEET_SUPER_DEALER,SHEET_POS_CODE)=( select cr_batch.BATCH_DATE , cr_sheet.SHEET_DISTRIBUTER_CODE , cr_sheet.SHEET_SUPER_DEALER_CODE , cr_sheet.SHEET_POS_CODE from "
                    + "  cr_batch, cr_sheet, cr_contract,cr_contract_status,cr_contract_status_type"
                    + "  where  cr_batch.batch_id  = cr_sheet.batch_id and cr_sheet.sheet_id = cr_contract.sheet_id and cr_contract.CONTRACT_MAIN_SIM_NO = AUTH_RES_DATA.SIM_SERIAL and cr_contract.CONTRACT_LAST_STATUS_ID = cr_contract_status.CONTRACT_STATUS_ID and cr_contract_status.CONTRACT_STATUS_TYPE_ID = cr_contract_status_type.CONTRACT_STATUS_TYPE_ID"
                    + "  and cr_contract_status_type.TYPE_ID = 1)";

            System.out.println("the UPDATE CIF query  is " + strSql);

            stat.execute(strSql);
            stat.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void updateSTFData(Connection con) {

        try {

            Statement stat = con.createStatement();

            String strSql = " UPDATE AUTH_RES_DATA SET (POS_CODE,SECOND_POS_CODE,STF_BATCH_DATE) = (select * from ( select vw_sfr_sheet.pos_code, vw_sfr_sheet.second_pos_code , batch_date"
                    + " from vw_sfr_sheet,vw_sfr_batch ,sfr_sim,AUTH_RES_DATA where  sfr_sim.sim_serial =AUTH_RES_DATA.SIM_SERIAL and vw_sfr_sheet.sheet_id  = sfr_sim.sheet_id and  vw_sfr_sheet.BATCH_ID=vw_sfr_batch.BATCH_ID"
                    + " and sfr_sim.SIM_STATUS_TYPE_ID = 1 order by batch_date desc ) where rownum<=1) ";






            System.out.println("the UPDATE stf query  is " + strSql);

            stat.execute(strSql);
            stat.close();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void insertStatistics(int file_id, int read_lines, int ins_lines) {

        Connection con = null;


        try {

            con = Utility.getConnection();

            Statement stat = con.createStatement();


            String strSql = "insert into AUTH_RES_STATISTICS (FILE_ID,  NO_OF_READ_LINES, NO_OF_INSERTED_LINES) "
                    + " values(" + file_id + "," + read_lines + "," + ins_lines + ")";
            System.out.println("the insertc statistics query is " + strSql);


            stat.execute(strSql);
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Vector getStatistics(Connection con, String file_id) {
        Vector vec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select AUTH_RES_STATISTICS.file_id,no_of_read_lines,no_of_inserted_lines ,start_timestamp,end_timestamp "
                    + "from AUTH_RES_STATISTICS,auth_res_file  where AUTH_RES_STATISTICS.FILE_ID='" + file_id + "' "
                    + " and AUTH_RES_STATISTICS.file_id = auth_res_file.file_id";



            System.out.println("GET Statistics  QUERY ISSSSSSSS" + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vec.add(new AuthResStatisticsModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    public static Vector getallAuthResSearchfiles(Connection con, String userId) {
        Vector vec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = " select SEARCH_FILE_ID ,cat_name,file_type_id, YEAR , MONTH ,STATUS ,TIME_STAMP ,USER_ID,DESCRIPTION, AUTH_RES_SEARCH_FILE.LABEL_ID, AUTH_RES_label.LABEL_NAME from  AUTH_RES_SEARCH_FILE , AUTH_RES_label, AUTH_SEARCH_CATEGORY where  "
                    + "AUTH_RES_label.label_id in (select label_id from AUTH_RES_USER_LABEL where user_id = '" + userId + "') and "
                    + "  AUTH_RES_SEARCH_FILE.CAT_ID = AUTH_SEARCH_CATEGORY.CAT_ID (+) and "
                    + " AUTH_RES_SEARCH_FILE.LABEL_ID=AUTH_RES_label.LABEL_ID and status <> 'Deleted' order by AUTH_RES_label.LABEL_NAME,YEAR,MONTH ";



            System.out.println("GET all files QUERY ISSSSSSSS" + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vec.add(new AuthResSearchFileModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    public static Vector getallAuthResSearchfileData(Connection con, String search_file_id) {
        Vector vec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = " SELECT * FROM AUTH_RES_SEARCH_DATA  where search_id ='" + search_file_id + "'";



            System.out.println("GET search data QUERY ISSSSSSSS" + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vec.add(new AuthResSearchDataModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    public static void insertAuthResSearchData(Connection con, Statement stat, Long searchFileId, String[] fileFields, String year, String month, String label_id, String fileTypeId) {
        String Filefield2 = "";
        String sim = fileFields[0];
        if (fileFields.length > 1 && fileTypeId.compareTo(AuthResInterfaceKey.CONTROL_SELECT_OPTION_SIM_DATA) == 0) {
            Filefield2 = fileFields[1];
        }

        try {


            String strSql = "insert into AUTH_RES_SEARCH_DATA(SEARCH_ID,SIM_SERIAL,FILE_FIELD_2)"
                    + " values(" + searchFileId + ",'" + sim + "','" + Filefield2 + "')";
            System.out.println("the data insert query is " + strSql);

            stat.execute(strSql);

            updateAuthSaerchData(con, stat, year, month, label_id, searchFileId.toString());






        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

   /* public static void updateAuthSaerchData(Connection con, Statement stat, String year, String month, String label_id, String search_file_id) {

        try {



            String sql = " UPDATE AUTH_RES_SEARCH_DATA"
                    + " SET(DIAL,ACTIVATION_DATE,POS_CODE,SECOND_POS_CODE,STF_BATCH_DATE,CIF_BATCH_DATE,SHEET_DISTRIBUTOR_CODE,SHEET_POS_CODE,LCS_DCM_ID, LCS_INIT_DATE,LCS_CONTRACT_TYPE_ID,SECOND_POS_NAME,SHEET_SUPER_DEALER)="
                    + " (select DIAL ,ACTIVATION_DATE,POS_CODE,SECOND_POS_CODE,STF_BATCH_DATE,CIF_BATCH_DATE,SHEET_DISTRIBUTOR_CODE, SHEET_POS_CODE,LCS_DCM_ID,LCS_INIT_DATE,LCS_CONTRACT_TYPE_ID,SECOND_POS_NAME,SHEET_SUPER_DEALER"
                    + " FROM  AUTH_RES_DATA  WHERE    AUTH_RES_DATA.SIM_SERIAL=AUTH_RES_SEARCH_DATA.SIM_SERIAL AND AUTH_RES_DATA.FILE_ID =  (SELECT DISTINCT(file_id) from "
                    + " AUTH_RES_file where AUTH_RES_file.MONTH ='" + month + "' and AUTH_RES_file.year ='" + year + "'and  AUTH_RES_file.LABEL_ID  ='" + label_id + "'and status <> 'Deleted') )   where AUTH_RES_SEARCH_DATA.SEARCH_ID='" + search_file_id + "'";





            System.out.print("the update query is :" + sql);

            int x = stat.executeUpdate(sql);


            System.out.println("Records Updated:" + x);






        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Long insertSearchFile(Connection con, String year, String month, String status, String description, String user_id, String label_id, String catId, String fileTypeId) {


        Long SEARCH_FILE_ID = null;

        try {



            Statement stat = con.createStatement();
            SEARCH_FILE_ID = Utility.getSequenceNextVal(con, "SEQ_AUTH_RES_SEARCH_FILE_ID");

            String strSql = "insert into AUTH_RES_SEARCH_FILE (SEARCH_FILE_ID, YEAR, MONTH, STATUS,DESCRIPTION,TIME_STAMP,USER_ID,LABEL_ID,CAT_ID, FILE_TYPE_ID) "
                    + " values(" + SEARCH_FILE_ID + ",'" + year + "','" + month + "','" + status + "','" + description + "',SYSDATE,'" + user_id + "','" + label_id + "','" + catId + "','" + fileTypeId + "')";
            System.out.println("the insert query is " + strSql);



            stat.execute(strSql);
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return SEARCH_FILE_ID;
    }

    public static boolean checkIfSimSerialExist(Connection con, String simSerial, String year, String month, String label) {
        boolean Status = false;
        try {



            Statement stat = con.createStatement();




//	 AUTH_RES_SEARCH_DATA

            String strsql = "  select * from auth_res_data  where auth_res_data.SIM_SERIAL='" + simSerial + "' and FILE_ID =  (SELECT DISTINCT(file_id) from "
                    + " AUTH_RES_file where AUTH_RES_file.MONTH ='" + month + "' and AUTH_RES_file.year ='" + year + "'and  AUTH_RES_file.LABEL_ID  ='" + label + "'and status <> 'Deleted' ) ";

            System.out.println("check status query is " + strsql);

            ResultSet res = stat.executeQuery(strsql);
            if (res.next()) {

                Status = true;


            }

            stat.execute(strsql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public static boolean checkDBFunctionIfSimSerialExist(Connection con, String simSerial, String year, String month, String label) {
        boolean Status = false;
        try {








            String strsql = " begin ? := CHECK_SIM_EXISTS('" + simSerial + "','" + month + "','" + year + "','" + label + "'); end;";

            CallableStatement colableStat = con.prepareCall(strsql);
            colableStat.registerOutParameter(1, OracleTypes.CURSOR);
            colableStat.execute();
            ResultSet res = (ResultSet) colableStat.getObject(1);

            System.out.println("check status query is " + strsql);

            if (res.next()) {

                Status = true;


            }

            Utility.closeCallbaleStatement(colableStat);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public static Vector getInvalidsimSerials(Connection con, String search_file_Id) {
        Vector vec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = " select * from AUTH_RES_SEARCH_TEMP_SERIAL where SEARCH_ID ='" + search_file_Id + "' ";



            System.out.println("GET all files QUERY ISSSSSSSS" + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vec.add(new AuthResInvalidsimSerialModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }*/

    public static Vector getUserLabel(Connection con) {
        Vector userLabelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select DCM_NOMAD_FILE_USER_LABEL.user_id,"
                    + "PERSON_EMAIL,PERSON_FULL_NAME "
                    + "from DCM_NOMAD_FILE_USER_LABEL,gen_person "
                    + "where DCM_NOMAD_FILE_USER_LABEL.USER_ID = gen_person.PERSON_ID "
                    + "group by DCM_NOMAD_FILE_USER_LABEL.user_id,person_email, person_full_name";
            //System.out.println("The select query issssssssssss " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                userLabelVec.add(new NomadLabelModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLabelVec;
    }

    public static Vector getLabelList(Connection con, String userId) {
        Vector labelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select DCM_NOMAD_FILE_USER_LABEL.LABEL_ID,NOMAD_LABEL_NAME,NOMAD_LABEL_DESC "
                    + "FROM DCM_NOMAD_FILE_USER_LABEL,GEN_DCM_NOMAD_LABEL "
                    + "WHERE GEN_DCM_NOMAD_LABEL.NOMAD_LABEL_ID = DCM_NOMAD_FILE_USER_LABEL.LABEL_ID and DCM_NOMAD_FILE_USER_LABEL.user_id = '" + userId + "'";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                labelVec.add(new NomadLabelModel(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelVec;
    }

    public static String getUserNameById(Connection con, String userId)
    {
        String userName ="";
        try {
            Statement stat = con.createStatement();
            String strSql = "select person_full_name from gen_person where person_id = '" + userId + "'";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                
                userName = res.getString("person_full_name");
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
        
    }
    public static int insertNewUser(Connection con, String userId) {
        int check = 0;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from gen_person where person_id = '" + userId + "'";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                Statement stat2 = con.createStatement();
                String strSql2 = "select * from DCM_NOMAD_FILE_USER_LABEL WHERE user_id = '" + userId + "'";
                ResultSet res2 = stat2.executeQuery(strSql2);
                if (res2.next()) {
                    check = 1;
                } else {
                    Statement stat3 = con.createStatement();
                    String strSql3 = "insert into DCM_NOMAD_FILE_USER_LABEL (USER_ID) VALUES ('" + userId + "')";
                    stat3.executeUpdate(strSql3);
                }
            } else {
                check = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static Vector getLabelByUser(Connection con, String userId) {
        Vector labelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select NOMAD_LABEL_ID,NOMAD_label_name,NOMAD_label_desc from GEN_DCM_NOMAD_LABEL "
                    + "where NOMAD_LABEL_ID not in (select DCM_NOMAD_FILE_USER_LABEL.label_id from DCM_NOMAD_FILE_USER_LABEL "
                    + "where DCM_NOMAD_FILE_USER_LABEL.label_id = GEN_DCM_NOMAD_LABEL.nomad_label_id and DCM_NOMAD_FILE_USER_LABEL.user_id = '" + userId + "')";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                labelVec.add(new NomadLabelModel(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelVec;
    }

    public static void assignLabelToUser(Connection con, String userId, String labelId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "select label_id from DCM_NOMAD_FILE_USER_LABEL where user_id = '" + userId + "'";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                Statement stat2 = con.createStatement();
                String strSql2 = "insert into DCM_NOMAD_FILE_USER_LABEL (user_id,label_id)values ('" + userId + "','" + labelId + "')";
                //System.out.println("The insert query issssssssssssss " + strSql2);
                stat2.executeUpdate(strSql2);
            } else {
                Statement stat3 = con.createStatement();
                String strSql3 = "update DCM_NOMAD_FILE_USER_LABEL set label_id = '" + labelId + "' where user_id = '" + userId + "'";
                //System.out.println("The update query isssssssssssssss " + strSql3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(Connection con, String userId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from DCM_NOMAD_FILE_USER_LABEL where user_id = '" + userId + "'";
            System.out.println("The delete query isssssssssssssssss " + strSql);
            stat.executeUpdate(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserLabel(Connection con, String userId, String labelId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from DCM_NOMAD_FILE_USER_LABEL where user_id = '" + userId + "' and label_id = '" + labelId + "'";

            stat.executeUpdate(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getAllLabels(Connection con) {
        Vector labelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from GEN_DCM_NOMAD_LABEL";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                labelVec.add(new NomadLabelModel(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelVec;
    }

    public static Vector getAllUserLabel(Connection con) {
        Vector userLabelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from DCM_NOMAD_FILE_USER_LABEL";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                userLabelVec.add(new NomadFileUserLabelModel(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userLabelVec;
    }

    public static void updateUserLabel(Connection con, String userId, String labelId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into DCM_NOMAD_FILE_USER_LABEL (USER_ID,LABEL_ID)values('" + userId + "','" + labelId + "')";
            System.out.println("The insert query issssssssssss " + strSql);
            stat.executeUpdate(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static boolean checkMonthAndYearPerTypeForUploadedFile(Connection con, String month, String year, String typeId) {

        String sql = "select * from AUTH_GROSS_ADDS_FILE where MONTH_NUMBER=" + month + " and YEAR_NUMBER=" + year + " and TYPE_ID=" + typeId;

        System.out.println("sql = " + sql);
        return DBUtil.executeSQLExistCheck(sql, con);
    }

    public static String getTableIdByName(Connection con, String TableName) {
        return DBUtil.executeQuerySingleValueString(con, "select TABLE_ID from ADM_DATA_IMPORT_DEF where TABLE_NAME='" + TableName + "'", "TABLE_ID");
    }

    public static String insertFileToGrossAdds(Connection con, String year, String month, String typeId, String userId) {
        long fileId = Utility.getSequenceNextVal(con, "AUTH_GROSS_ADDS_FILE_SEQ");
        StringBuilder str = new StringBuilder("Insert into AUTH_GROSS_ADDS_FILE values(");
        str.append(fileId);
        str.append(",SYSTIMESTAMP,0,");
        str.append(month);
        str.append(",");
        str.append(year);
        str.append(",");
        str.append(userId);
        str.append(",");
        str.append(typeId);
        str.append(")");
        DBUtil.executeSQL(str.toString(), con);
        return String.valueOf(fileId);
    }

    public static void updateFileId(Connection con, String fileId, String tableName, String labelCondition, String userId) {


        DBUtil.executeSQL("update AUTH_GROSS_ADDS_FILE set RECORD_COUNT=(select count(*) from " + tableName + " where File_id is null and User_id=" + userId + ") where File_id=" + fileId, con);
        DBUtil.executeSQL("update " + tableName + " set File_id=" + fileId + labelCondition + " where File_id is null and User_id=" + userId, con);
    }

    public static Vector<AuthResGrossAddsFileModel> getGrossAddsFiles(Connection con) {
        return DBUtil.executeSqlQueryMultiValue("select * from AUTH_GROSS_ADDS_FILE", AuthResGrossAddsFileModel.class, con);
    }

    public static void deleteGrossAddsFiles(Connection con, String fileId) {

        //hagry
        String typeId = "", tableName = "";
        typeId = DBUtil.executeQuerySingleValueString(con, "select TYPE_ID from AUTH_GROSS_ADDS_FILE where file_id=" + fileId, "TYPE_ID");
        System.out.println("type id = " + typeId);



        if (typeId.compareTo("1") == 0) {
            tableName = "AUTH_VOC_MNP_MIGR";
        } else if (typeId.compareTo("2") == 0) {
            tableName = "AUTH_ACHIV_PREPAID";
        } else if (typeId.compareTo("3") == 0) {
            tableName = "AUTH_DATA_LINE";
        } else if (typeId.compareTo("4") == 0) {
            tableName = "AUTH_VOC_MNP_MIGR";
        } else if (typeId.compareTo("5") == 0) {
            tableName = "AUTH_VOC_MNP_MIGR";
        }
        DBUtil.executeSQL("delete from " + tableName + " where file_id=" + fileId, con);
        DBUtil.executeSQL("delete from AUTH_GROSS_ADDS_FILE where file_id=" + fileId, con);


    }*/
}