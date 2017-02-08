package com.mobinil.sds.core.system.commercial.dao;

import com.mobinil.sds.core.system.supervisor.dao.*;
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
import org.apache.poi.hpbf.model.qcbits.QCBit;


public class CommercialFileDAO{

    
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
                    + "  SET  STATUS = '" + status + "' , file_upload_date = sysdate where GEN_DCM_NOMAD_FILE_ID = '" + file_id + "'";

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
    
    
    public static void insertCommercialData(Connection con, Statement stat,int posAddressIndex,String userId,String[] lineFields,int count/*, String fileDate, int updatedIndex*/) throws ParseException {
        /*ChannelCode	PosCode	PosENm	ArabicName	Owner	IDnumber	IDType	SalesRegion	City	Governerate	DistrictID	District	ImDistrict	AreaCode	Area	Address	DocNumber	Documents	entryDt	PosStatus	OwnerPhone	LevelCode	RegionalName	TeamLeader	RepName	StkDialNo	StkStatus	StkActivationDate	IqrarReceiveDate	PayStatus	PayLevelName	ArabicAddress	IqrarReceived	VerifyOk	DocumentLocation	SurveyID	POS_OWNER_PHONE_NUMBER	branch	MBB_Rep	ImDistCode	L1	Ex	Sign	QC	CommercialGov
       '1','0001.001','Pyramids Telecom','بيراميدز تيليكوم','Khaled Mohamed Kamel Abdo Salem','25811181400351','','Greater Cairo','Cairo','Cairo','NDD000','Qaser El Nile','Qaser El Nile','11103','Qasr El Dobara','27 Gharb El Seka El Hadid St. Bassateen','','','6/21/2011','ACTIVE','1224011000','2','Mohamed Aly Morsy','Mohamed Ali Morsi','Hatem Fathy Ibrahim','01278535655','Active','','6/1/2011','ELIGIBLE','Dist Show Rooms','32 ش سليمان باشا','Received','Verified','0','null','','0001','','NDD000','Y','','','','Cairo'
*/
        String concatFields = "";
        String strSql = "";
        String mbbRepName="";
        String imgDistrictCode="";
        String L1="";
        String Ex="";
        String Sign="";
        String Qc="";
        String commercialGovernName="";
        
        
        
        System.out.println("Line Fields LENGTH "+lineFields.length);
        System.out.println("insertCommercialData : go to Line no. "+count);
        for (int i=0; i<lineFields.length;i++)
        {
            /*if(i==posAddressIndex && lineFields[i].contains(","))
                lineFields[i]=lineFields[i].replace(",", "");*/
            concatFields += "'"+lineFields[i]+"'"+",";

        }
        concatFields = concatFields.substring(0, concatFields.length()-1);
        System.out.println("Line text Concatenated : "+concatFields);

        String channelCode=(lineFields[0].compareTo("")==0)? " " : lineFields[0];
        String posCode=(lineFields[1].compareTo("")==0)? " " : lineFields[1];
        String posEnName=(lineFields[2].compareTo("")==0)? " " : lineFields[2];
        String posArName=(lineFields[3].compareTo("")==0)? " " : lineFields[3];
        String posOwnerName=(lineFields[4].compareTo("")==0)? " " : lineFields[4];
        String posOwnerIdNumber=(lineFields[5].compareTo("")==0)? " " : lineFields[5];
        String posOwnerIdType=(lineFields[6].compareTo("")==0)? " " : lineFields[6];
        String regionName=(lineFields[7].compareTo("")==0)? " " : lineFields[7];
        String cityName=(lineFields[8].compareTo("")==0)? " " : lineFields[8];
        String governName=(lineFields[9].compareTo("")==0)? " " : lineFields[9];
        String districtCode=(lineFields[10].compareTo("")==0)? " " : lineFields[10];
        String districtName=(lineFields[11].compareTo("")==0)? " " : lineFields[11];
        String imgDistrictName=(lineFields[12].compareTo("")==0)? " " : lineFields[12];
        String areaCode=(lineFields[13].compareTo("")==0)? " " : lineFields[13];
        String areaName=(lineFields[14].compareTo("")==0)? " " : lineFields[14];
        String posAddress=(lineFields[15].compareTo("")==0)? " " : lineFields[15];
        String docNumber=(lineFields[16].compareTo("")==0)? " " : lineFields[16];
        String posDocuments=(lineFields[17].compareTo("")==0)? " " : lineFields[17];
        String assignEntryDate=(lineFields[18].compareTo("")==0)? "" : lineFields[18];
        String posStatusName=(lineFields[19].compareTo("")==0)? " " : lineFields[19];
        String posOwnerPhoneNumber=(lineFields[20].compareTo("")==0)? " " : lineFields[20];
        String posLevelId=(lineFields[21].compareTo("")==0)? " " : lineFields[21];//LEVEL CODE 
        String supervisorName=(lineFields[22].compareTo("")==0)? " " : lineFields[22];
        String teamleaderName=(lineFields[23].compareTo("")==0)? " " : lineFields[23];
        String salesrepName=(lineFields[24].compareTo("")==0)? " " : lineFields[24];
        String stkDialNumber=(lineFields[25].compareTo("")==0)? " " : lineFields[25];
        String stkStatusName=(lineFields[26].compareTo("")==0)? " " : lineFields[26];
        String stkActivationDate=(lineFields[27].compareTo("")==0)? "" : lineFields[27];
        String iqrarReceivedDate=(lineFields[28].compareTo("")==0)? "" : lineFields[28];
        String paymentStatusName=(lineFields[29].compareTo("")==0)? " " : lineFields[29];
        String paymentLevelName=(lineFields[30].compareTo("")==0)? " " : lineFields[30];
        String posArAddress=(lineFields[31].compareTo("")==0)? " " : lineFields[31];
        String isIqrarReceivedName=(lineFields[32].compareTo("")==0)? " " : lineFields[32]; //table scm_stk_owner
        String isVerifiedName=(lineFields[33].compareTo("")==0)? " " : lineFields[33];//table scm_stk_owner
        String docLocation=(lineFields[34].compareTo("")==0)? " " : lineFields[34];
        String surveyId=(lineFields[35].compareTo("")==0)? " " : lineFields[35];
        String posOwnerPhoneNumber2=(lineFields[36].compareTo("")==0)? " " : lineFields[36];
        String branch=(lineFields[37].compareTo("")==0)? " " : lineFields[37];
        mbbRepName=(lineFields[38].compareTo("")==0)? " " : lineFields[38];
        imgDistrictCode=(lineFields[39].compareTo("")==0)? " " : lineFields[39];
        L1=(lineFields[40].compareTo("")==0)? " " : lineFields[40];
        Ex=(lineFields[41].compareTo("")==0)? " " : lineFields[41];
        Sign=(lineFields[42].compareTo("")==0)? " " : lineFields[42];
        Qc=(lineFields[43].compareTo("")==0)? " " : lineFields[43];
        commercialGovernName=(lineFields[44].compareTo("")==0)? " " : lineFields[44];
        
        
        
        
        try {  
                
                if(districtCode.compareToIgnoreCase(imgDistrictCode)!=0 && imgDistrictCode.compareTo("")!=0)
                {
                    insertSCMUserDistrictForSupervisor(con, stat, imgDistrictName, supervisorName, posCode);
                    insertSCMUserDistrictForTeamleader(con, stat, imgDistrictName, teamleaderName, posCode);
                    insertSCMUserDistrictForSalesRep(con, stat, imgDistrictName, salesrepName, posCode);

                }
                else
                {
                    insertSCMUserDistrictForSupervisor(con, stat, districtName, supervisorName, posCode);
                    insertSCMUserDistrictForTeamleader(con, stat, districtName, teamleaderName, posCode);
                    insertSCMUserDistrictForSalesRep(con, stat, districtName, salesrepName, posCode);

                }
                
                
                
                updateSCMTeamLeaderTable(con, stat, teamleaderName, supervisorName);
                updateSCMSalesRepTable(con, stat, teamleaderName, salesrepName,supervisorName);

                //insertSCMSTKOwnerTable(con, stat, posCode, supervisorName, userId, isVerifiedName, iqrarReceivedDate, stkStatusName, isIqrarReceivedName, assignEntryDate, stkActivationDate);

                //supposed to be stk number
                //updateGenDCMTable(con, stat, stkDialNumber, Ex, Qc, L1, Sign, posLevelId, districtName, cityName, channelCode, posEnName, posAddress, paymentLevelName, posStatusName, posCode);
                updateDcmPosDetailTable(con, stat, userId,surveyId, Ex, Qc, L1, Sign, posArAddress, docNumber, docLocation, supervisorName, teamleaderName, salesrepName, paymentLevelName, posLevelId, posStatusName, posCode, channelCode, regionName, districtCode, governName, districtName, cityName, areaName, posEnName , posArName, posAddress);

                updateDistrictCode(con, stat, districtCode, districtName);

                updateAreaCode(con, stat, areaCode, areaName);
                //supposed to be stk number
                //updateCAM_PAYMENT_SCM_STATUSTable(con, stat, stkDialNumber, stkStatusName, paymentStatusName, posCode);

                //updatePOSOwnerTable(con, stat, userId, posOwnerIdType, posOwnerIdNumber, posOwnerName, posCode);
                //updatePOSOwnerPhoneTable(con, stat, userId, posOwnerPhoneNumber, posCode);

                //updatePOSDocumentsTable(con, stat, assignEntryDate, iqrarReceivedDate, stkActivationDate, stkDialNumber, posDocuments, docNumber, posCode);




                System.out.println("............LINE INSERTED Number........"+count);

        } catch (Exception e) {
            
                     System.out.println("............LINE NOT INSERTED Number........"+count+" "+"EXCEPTION in " + strSql);
            e.printStackTrace();
        }
        

    }
    
    
    private static int insertSCMSTKOwnerTable(Connection con, Statement stat, String dcmCode,String supervisorName,String userId,String dcmVerifiedStatusName, String iqrarReceivedDate, String stkStatusName,String iqrarReceivedStatusName, String stkEntryAssignDate, String stkActivationDate)
    {
        System.out.println("...FUNC insertSCMSTKOwnerTable...");
        int inserted=-1;
        int supervisorId=-1;
        int dcmVerifiedStatusId = -1;
        int stkStatusId = -1;
        int iqrarReceivedStatusId=-1;
        String stkEntryAssignDateSql="null";
        String stkActivationDateSql="null";
        String iqrarReceivedDateSql="null";
        DateValidator validateDate = new DateValidator();
       try{    
           
           System.out.println("...insertSCMSTKOwnerTable...");
            
            if(iqrarReceivedDate.compareTo("")!=0 && validateDate.isThisDateValid(iqrarReceivedDate, "mm/dd/yyyy"))
                iqrarReceivedDateSql = "to_date('"+iqrarReceivedDate+"','mm/dd/yyyy')";
            
            if(stkActivationDate.compareTo("")!=0 && validateDate.isThisDateValid(stkActivationDate, "mm/dd/yyyy"))
                stkActivationDateSql = "to_date('"+stkActivationDate+"','mm/dd/yyyy')";
            
            
            if(stkEntryAssignDate.compareTo("")!=0 && validateDate.isThisDateValid(stkEntryAssignDate, "mm/dd/yyyy"))
                stkEntryAssignDateSql = "to_date('"+stkEntryAssignDate+"','mm/dd/yyyy')";
            
            
            
            
            String sqlSupervisorId="select supervisor_id from scm_supervisor where LOWER(supervisor_name) like LOWER('%"+supervisorName+"%')";
            System.out.println("sql for supervisor id in insertSCMSTKOwnerTable : "+sqlSupervisorId);
            ResultSet rs8 = stat.executeQuery(sqlSupervisorId);
            if(rs8.next())
                supervisorId = Integer.parseInt(rs8.getString("supervisor_id"));
            
            
            
            String sqlDcmVerifiedStatusId="select dcm_verified_status_id from scm_verified_status where LOWER(name) like LOWER('%"+dcmVerifiedStatusName+"%')";
            ResultSet rs1 = stat.executeQuery(sqlDcmVerifiedStatusId);
            if(rs1.next())
                dcmVerifiedStatusId = Integer.parseInt(rs1.getString("dcm_verified_status_id"));
            
            
            
            
            
            
            
            String sqlIqrarReceivedStatusId="select iqrar_receving_status_id from scm_iqrar_receving_status where LOWER(name) like LOWER('%"+iqrarReceivedStatusName+"%')";
            ResultSet rs2 = stat.executeQuery(sqlIqrarReceivedStatusId);
            if(rs2.next())
                iqrarReceivedStatusId = Integer.parseInt(rs2.getString("iqrar_receving_status_id"));
            
            
            
            
            String sqlIStkStatusId="select stk_status_id from scm_stk_status where LOWER(name) like LOWER('%"+stkStatusName+"%')";
            ResultSet rs3 = stat.executeQuery(sqlIStkStatusId);
            if(rs3.next())
                stkStatusId = Integer.parseInt(rs3.getString("stk_status_id"));
            
            
            
            String checkSql = "select * from scm_stk_owner where dcm_id in (select dcm_id from gen_dcm where dcm_code='"+dcmCode+"') and dcm_user_id="+supervisorId;
            System.out.println("CHECKING STK OWNER "+checkSql);
            ResultSet rs = stat.executeQuery(checkSql);
            if(!rs.next())
            {
                System.out.println("insert STK");
                Long scmStkId = Utility.getSequenceNextVal(con, "SEQ_STK_OWNER_ID");
                String strSql = "insert into scm_stk_owner values("+scmStkId+", (select dcm_id from gen_dcm where dcm_code='"+dcmCode+"'),'"+supervisorId+"','"+userId+"',SYSTIMESTAMP,"+dcmVerifiedStatusId+","+iqrarReceivedStatusId+", "+stkStatusId+", "+iqrarReceivedDateSql+", SYSTIMESTAMP,"+stkActivationDateSql+",null,null,null,"+stkEntryAssignDateSql+","+stkActivationDateSql+",null,null,0)";
                System.out.println("SQL insertSCMSTKOwnerTable is " + strSql);
                inserted = stat.executeUpdate(strSql);
            }
            
            else
            {
                System.out.println("update STK");
                String strSql = "update scm_stk_owner set updated_in=SYSTIMESTAMP,dcm_verified_status_id="+dcmVerifiedStatusId+",iqrar_receving_status_id="+iqrarReceivedStatusId+",stk_status_id="+stkStatusId+",iqrar_recieved_date="+iqrarReceivedDateSql+",dcm_verification_date=SYSTIMESTAMP,active_date="+stkActivationDateSql+",stk_verification=null,stk_delivery_date=null,iqrar_delivery_date=null,stk_assign_date="+stkEntryAssignDateSql+",stk_active_date="+stkActivationDateSql+",stk_import_date=null,reason=null,stk_report_flag=0";
                System.out.println("SQL insertSCMSTKOwnerTable is " + strSql);
                inserted = stat.executeUpdate(strSql);
            }
            
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inserted;
        
    }
    
    
    
    
    private static int updateGenDCMTable(Connection con, Statement stat,String stkNumber,String isExclusive, String isQualityClub,String isLevelOne,String hasSign, String dcmLevelCode , String dcmDistrictName, String dcmCityName, String channeId,String dcmName,String dcmAddress, String dcmPaymentLevelName,String dcmStatusName,String posCode)
    {
        int updated=-1;
        int districtId=-1;
        int cityId=-1;
        int dcmPayLevelId=-1;
        int dcmStatusId=-1;
        int dcmLevelId=-1;
        try {
            String ex="";
            String l1="";
            String qc="";
            String sign="";
            if(isExclusive.compareToIgnoreCase("Y")==0 || isExclusive.compareToIgnoreCase("Yes")==0)
                ex="1";
            else ex="0";
            
            if(isQualityClub.compareToIgnoreCase("Y")==0 || isQualityClub.compareToIgnoreCase("Yes")==0)
                qc="1";
            else qc="0";
            
            if(isLevelOne.compareToIgnoreCase("Y")==0 || isLevelOne.compareToIgnoreCase("Yes")==0)
                l1="1";
            else l1="0";
            
            if(hasSign.compareToIgnoreCase("Y")==0 || hasSign.compareToIgnoreCase("Yes")==0)
                sign="1";
            else sign="0";
            
            
            
            String sqlDistrictId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+dcmDistrictName+"%') and region_level_type_id=4";
            ResultSet rs1 = stat.executeQuery(sqlDistrictId);
            if(rs1.next())
                districtId = Integer.parseInt(rs1.getString("region_id"));
            
            
            
            String sqlCityId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+dcmCityName+"%') and region_level_type_id=5";
            ResultSet rs2 = stat.executeQuery(sqlCityId);
            if(rs2.next())
                cityId = Integer.parseInt(rs2.getString("region_id"));
            
            
            
            String sqlPayLevelId="select dcm_payment_level_id from gen_dcm_payment_level where LOWER(dcm_payment_level_name) like LOWER('%"+dcmPaymentLevelName+"%')";
            ResultSet rs3 = stat.executeQuery(sqlPayLevelId);
            if(rs3.next())
                dcmPayLevelId = Integer.parseInt(rs3.getString("dcm_payment_level_id"));
            
            
            
            String sqlDcmStatusId="select dcm_status_id from gen_dcm_status where LOWER(dcm_status_name) like LOWER('%"+dcmStatusName+"%')";
            ResultSet rs4 = stat.executeQuery(sqlDcmStatusId);
            if(rs4.next())
                dcmStatusId = Integer.parseInt(rs4.getString("dcm_status_id"));
            
           /* String sqlDcmLevelId="select dcm_level_id from gen_dcm_level where LOWER(dcm_level_name)  like LOWER('"+dcmLevelName+"')";
            ResultSet rs5 = stat.executeQuery(sqlDcmLevelId);
            if(rs5.next())
                dcmLevelId = Integer.parseInt(rs5.getString("dcm_level_id"));*/
            
            
            String strSql = "update gen_dcm set /*stk_number='"+stkNumber+"',*/is_exclusive='"+ex+"',is_quality_club='"+qc+"',is_level_one='"+l1+"',has_sign='"+sign+"',DCM_LEVEL_ID="+dcmLevelCode+" ,dcm_district_id="+districtId+", dcm_city_id="+cityId+", channel_id="+channeId+", dcm_name='"+dcmName+"',dcm_address='"+dcmAddress+"',dcm_payment_level_id="+dcmPayLevelId+",dcm_status_id="+dcmStatusId+" where dcm_code='"+posCode+"'";
            System.out.println("SQL updateGenDCMTable is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    
    private static int updateDistrictCode(Connection con, Statement stat,String districtcode, String districtName)
    {
        int updated=-1;
        int districtId=-1;
        
        try {
            
            
            String sqlDistrictId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+districtName+"%') and region_level_type_id=4";
            ResultSet rs1 = stat.executeQuery(sqlDistrictId);
            if(rs1.next())
                districtId = Integer.parseInt(rs1.getString("region_id"));
            
            
            
            String strSql = "update dcm_region set region_code='"+districtcode+"' where region_level_type_id=4 and region_id="+districtId;
            System.out.println("SQL updateDistrictCode is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }

    
    private static int updatePOSOwnerPhoneTable(Connection con, Statement stat,String userId, String posOwnerPhoneNumber, String dcmCode)
    {
        int updated=-1;
        
        try {
            
            
            
            String strSql = "update dcm_pos_owner_phone set user_id='"+userId+"',updated_in = SYSTIMESTAMP ,pos_owner_phone_number='"+posOwnerPhoneNumber+"' where pos_owner_id in (select dcm_pos_owner.pos_owner_id from dcm_pos_owner,dcm_pos_detail where dcm_pos_detail.pos_detail_id = dcm_pos_owner.pos_detail_id and dcm_pos_detail.pos_code='"+dcmCode+"' and dcm_pos_detail.flage is null)";
            System.out.println("SQL updatePOSOwnerPhoneTable is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    
    private static int updateSCMSalesRepTable(Connection con, Statement stat,String teamleaderName, String salesrepName, String supervisorName)
    {
        int updated=-1;
        int teamleaderId=-1;
        int supervisorId=-1;
        int salesrepId=-1;
        try {
            
            
            String sqlSupervisorId="select supervisor_id from scm_supervisor where LOWER(supervisor_name) like LOWER('%"+supervisorName+"%')";
            ResultSet rs8 = stat.executeQuery(sqlSupervisorId);
            if(rs8.next())
                supervisorId = Integer.parseInt(rs8.getString("supervisor_id"));
            
            
            
            String sqlTeamleaderId="select teamleader_id from scm_teamleader where LOWER(teamleader_name) like LOWER('%"+teamleaderName+"%')";
            ResultSet rs9 = stat.executeQuery(sqlTeamleaderId);
            if(rs9.next())
                teamleaderId = Integer.parseInt(rs9.getString("teamleader_id"));
            
            
            String sqlRepId="select salesrep_id from scm_salesrep where LOWER(salesrep_name)  like LOWER('%"+salesrepName+"%')";
            ResultSet rs10 = stat.executeQuery(sqlRepId);
            if(rs10.next())
                salesrepId = Integer.parseInt(rs10.getString("salesrep_id"));
            
            if(salesrepId==-1)
            {
                //Long repId = Utility.getSequenceNextVal(con, "SEQ_SCM_SALESREP_ID"); 
                Long repId = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_ID");
                String strSql = "insert into SCM_SALESREP ( SALESREP_ID, SALESREP_NAME, teamlead_id,sup_id,CREATION_TIMESTAMP) values ("+repId+",'"+salesrepName+"',"+teamleaderId+","+supervisorId+",sysdate)" ;
                System.out.println("SQL insertSCMSalesRepTable is " + strSql);
                stat.execute(strSql);
            }
            else
            {
                String strSql = "update scm_salesrep set teamlead_id="+teamleaderId+" where salesrep_id="+salesrepId;
                System.out.println("SQL updateSCMSalesRepTable is " + strSql);
                updated = stat.executeUpdate(strSql);
            }
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    
    
    private static int updateSCMTeamLeaderTable(Connection con, Statement stat,String teamleaderName, String supervisorName)
    {
        int updated=-1;
        int teamleaderId=-1;
        int supervisorId=-1;
        
        try {
            
            
            String sqlTeamleaderId="select teamleader_id from scm_teamleader where LOWER(teamleader_name)  like LOWER('%"+teamleaderName+"%')";
            ResultSet rs9 = stat.executeQuery(sqlTeamleaderId);
            if(rs9.next())
                teamleaderId = Integer.parseInt(rs9.getString("teamleader_id"));
            
            
            String sqlSupervisorId="select supervisor_id from scm_supervisor where LOWER(supervisor_name) like LOWER('%"+supervisorName+"%')";
            ResultSet rs8 = stat.executeQuery(sqlSupervisorId);
            if(rs8.next())
                supervisorId = Integer.parseInt(rs8.getString("supervisor_id"));
            
            
            if(teamleaderId==-1)
            {
                //Long teamId = Utility.getSequenceNextVal(con, "SEQ_SCM_TEAMLEADER_ID"); 
                Long teamId = Utility.getSequenceNextVal(con, "SEQ_DCM_USER_ID");
                String strSql = "insert into SCM_TEAMLEADER ( TEAMLEADER_ID, TEAMLEADER_NAME, sup_id ,CREATION_TIMESTAMP) values ("+teamId+",'"+teamleaderName+"',"+supervisorId+",sysdate)" ;
                System.out.println("SQL insertSCMTeamLeaderTable is " + strSql);
                stat.execute(strSql);
            }
            
            else
            {
                String strSql = "update scm_teamleader set sup_id="+supervisorId+" where teamleader_id="+teamleaderId;
                System.out.println("SQL updateSCMTeamLeaderTable is " + strSql);
                updated = stat.executeUpdate(strSql);
            }
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    
    private static int insertSCMUserDistrictForSupervisor(Connection con, Statement stat,String districtName, String supervisorName, String dcmCode)
    {
        int inserted=-1;
        int districtId=-1;
        int supervisorId=-1;
        try {
            
            
            String sqlDistrictId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+districtName+"%') and region_level_type_id=4";
            ResultSet rs1 = stat.executeQuery(sqlDistrictId);
            if(rs1.next())
                districtId = Integer.parseInt(rs1.getString("region_id"));
            
            
            String sqlSupervisorId="select supervisor_id from scm_supervisor where LOWER(supervisor_name) like LOWER('%"+supervisorName+"%')";
            ResultSet rs8 = stat.executeQuery(sqlSupervisorId);
            if(rs8.next())
                supervisorId = Integer.parseInt(rs8.getString("supervisor_id"));
            
            
            String checkSql = "select * from scm_user_region where user_id="+supervisorId+" and region_id="+districtId+" and USER_LEVEL_TYPE_ID=4 and REGION_LEVEL_TYPE_ID=4";
            ResultSet rs = stat.executeQuery(checkSql);
            if(!rs.next())
            {
                System.out.println("INSERT SCM USER REGION - supervisor : "+supervisorId+" district : "+districtId);
                String strSql = "insert into scm_user_region (REGION_ID,USER_ID/*,POS_CODE*/,USER_LEVEL_TYPE_ID,REGION_LEVEL_TYPE_ID) values ("+districtId+","+supervisorId+",/*'"+dcmCode+"',*/4,4)";
                System.out.println("SQL insertSCMUserRegionForSupervisor is " + strSql);
                inserted = stat.executeUpdate(strSql);
            }
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inserted;
        
    }
    private static int insertSCMUserDistrictForTeamleader(Connection con, Statement stat,String districtName, String teamleaderName, String dcmCode)
    {
        int inserted=-1;
        int districtId=-1;
        int teamleaderId=-1;
        try {
            
            
            String sqlDistrictId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+districtName+"%') and region_level_type_id=4";
            ResultSet rs1 = stat.executeQuery(sqlDistrictId);
            if(rs1.next())
                districtId = Integer.parseInt(rs1.getString("region_id"));
            
            
            String sqlTeamleaderId="select teamleader_id from scm_teamleader where LOWER(teamleader_name) like LOWER('%"+teamleaderName+"%')";
            ResultSet rs9 = stat.executeQuery(sqlTeamleaderId);
            if(rs9.next())
                teamleaderId = Integer.parseInt(rs9.getString("teamleader_id"));
            
            String checkSql = "select * from scm_user_region where user_id="+teamleaderId+" and region_id="+districtId+" and USER_LEVEL_TYPE_ID=5 and REGION_LEVEL_TYPE_ID=4";
            ResultSet rs = stat.executeQuery(checkSql);
            if(!rs.next())
            {
                System.out.println("INSERT SCM USER REGION - teamleader : "+teamleaderId+" district : "+districtId);
                String strSql = "insert into scm_user_region (REGION_ID,USER_ID/*,POS_CODE*/,USER_LEVEL_TYPE_ID,REGION_LEVEL_TYPE_ID) values ("+districtId+","+teamleaderId+"/*,'"+dcmCode+"'*/,5,4)";
                System.out.println("SQL insertSCMUserRegionForTeamleader is " + strSql);
                inserted = stat.executeUpdate(strSql);
            }
            
                
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inserted;
        
    }
    
    
    private static int insertSCMUserDistrictForSalesRep(Connection con, Statement stat,String districtName, String salesrepName, String dcmCode)
    {
        int inserted=-1;
        int districtId=-1;
        int salesrepId=-1;
        try {
            
            
            String sqlDistrictId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+districtName+"%') and region_level_type_id=4";
            ResultSet rs1 = stat.executeQuery(sqlDistrictId);
            if(rs1.next())
                districtId = Integer.parseInt(rs1.getString("region_id"));
            
            
            String sqlRepId="select salesrep_id from scm_salesrep where LOWER(salesrep_name) like LOWER('%"+salesrepName+"%')";
            ResultSet rs10 = stat.executeQuery(sqlRepId);
            if(rs10.next())
                salesrepId = Integer.parseInt(rs10.getString("salesrep_id"));
            
            
            String checkSql = "select * from scm_user_region where user_id="+salesrepId+" and region_id="+districtId+" and USER_LEVEL_TYPE_ID=6 and REGION_LEVEL_TYPE_ID=4";
            ResultSet rs = stat.executeQuery(checkSql);
            if(!rs.next())
            {
                System.out.println("INSERT SCM USER REGION - rep : "+salesrepId+" district : "+districtId);
                String strSql = "insert into scm_user_region (REGION_ID,USER_ID/*,POS_CODE*/,USER_LEVEL_TYPE_ID,REGION_LEVEL_TYPE_ID) values ("+districtId+","+salesrepId+",/*'"+dcmCode+"',*/6,4)";
                System.out.println("SQL insertSCMUserRegionForSalesRep is " + strSql);
                inserted = stat.executeUpdate(strSql);
            }
            
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return inserted;
        
    }
    
    
    private static int updatePOSDocumentsTable(Connection con, Statement stat,String assignEntryDate, String iqrarReceivedDate,String stkActivationDate, String stkDialNumber, String posDocuments,String posDocumentNumber, String dcmCode)
    {
        int updated=-1;
        String stkEntryAssignDateSql="null";
        String stkActivationDateSql="null";
        String iqrarReceivedDateSql="null";
        DateValidator validateDate = new DateValidator();
       
            
        try{    
            
            if(iqrarReceivedDate.compareTo("")!=0 && validateDate.isThisDateValid(iqrarReceivedDate, "mm/dd/yyyy"))
                iqrarReceivedDateSql = "to_date('"+iqrarReceivedDate+"','mm/dd/yyyy')";
            
            if(stkActivationDate.compareTo("")!=0 && validateDate.isThisDateValid(stkActivationDate, "mm/dd/yyyy"))
                stkActivationDateSql = "to_date('"+stkActivationDate+"','mm/dd/yyyy')";
            
            
            if(assignEntryDate.compareTo("")!=0 && validateDate.isThisDateValid(assignEntryDate, "mm/dd/yyyy"))
                stkEntryAssignDateSql = "to_date('"+assignEntryDate+"','mm/dd/yyyy')";
            
            String strSql = "update pos_documents set assign_date="+stkEntryAssignDateSql+",iqrarrcvdt="+iqrarReceivedDateSql+", stkactvdt="+stkActivationDateSql+", stkdialno='"+stkDialNumber+"',posdocuments = '"+posDocuments+"',posdocumentnum='"+posDocumentNumber+"' where code='"+dcmCode+"'";
            System.out.println("SQL updatePOSDocumentsTable is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    private static boolean isNumeric(String str) {
    if (str == null) {
        return false;
    }
    int sz = str.length();
    for (int i = 0; i < sz; i++) {
        if (Character.isDigit(str.charAt(i)) == false) {
            return false;
        }
    }
    return true;
}
    
    private static int updatePOSOwnerTable(Connection con, Statement stat,String userId, String posOwnerIdTypeName, String posOwnerIdNumber,String posOwnerName,String dcmCode)
    {
        int updated=-1;
        int posOwnerIdTypeId = -1;
        String posOwnerIdNumberSql="";
        try {
            
            String sqlIdtypeId="select id_type_id from dcm_id_type where LOWER(id_type_name) like LOWER('%"+posOwnerIdTypeName+"%')";
            ResultSet rs1 = stat.executeQuery(sqlIdtypeId);
            if(rs1.next())
                posOwnerIdTypeId = Integer.parseInt(rs1.getString("id_type_id"));
            
            
            if(isNumeric(posOwnerIdNumber))
                posOwnerIdNumberSql = "pos_owner_id_number="+Long.parseLong(posOwnerIdNumber)+",";
            
            
            String strSql = "update dcm_pos_owner set user_id='"+userId+"',updated_in = SYSTIMESTAMP , pos_owner_id_type_id="+posOwnerIdTypeId+","+posOwnerIdNumberSql+" pos_owner_name='"+posOwnerName+"' where pos_detail_id in (select pos_detail_id from dcm_pos_detail where pos_code='"+dcmCode+"' and flage is null) ";
            System.out.println("SQL updatePOSOwnerTable is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    
    
    private static int updateCAM_PAYMENT_SCM_STATUSTable(Connection con, Statement stat,String stkNumber, String stkStatusName,String paymentCamStateName,String dcmCode)
    {
        int updated=-1;
        int stkStatusId=-1;
        int payStatusId=-1;
        try {
            
            
            String sqlSTKId="select stk_status_id from scm_stk_status where LOWER(name) like LOWER('"+stkStatusName+"')";
            ResultSet rs1 = stat.executeQuery(sqlSTKId);
            if(rs1.next())
                stkStatusId = Integer.parseInt(rs1.getString("stk_status_id"));
            
            
            String sqlPayStatusId="select id from CAM_PAYMENT_cam_state where LOWER(cam_status_for_payment) like LOWER('"+paymentCamStateName+"')";
            ResultSet rs2 = stat.executeQuery(sqlPayStatusId);
            if(rs2.next())
                payStatusId = Integer.parseInt(rs2.getString("id"));
            
            
            String strSql = "update CAM_PAYMENT_SCM_STATUS set /*stk_number='"+stkNumber+"',*/ stk_status="+stkStatusId+",PAYMENT_cam_state_id="+payStatusId+" where scm_id in (select dcm_id from gen_dcm where dcm_code='"+dcmCode+"')";
            System.out.println("SQL updateCAM_PAYMENT_SCM_STATUSTable is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    
    
    

    private static int updateAreaCode(Connection con, Statement stat,String areacode, String areaName)
    {
        int updated=-1;
        int areaId=-1;
        
        try {
            
            
            String sqlAreaId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+areaName+"%') and region_level_type_id=5";
            ResultSet rs1 = stat.executeQuery(sqlAreaId);
            if(rs1.next())
                areaId = Integer.parseInt(rs1.getString("region_id"));
            
            
            
            String strSql = "update dcm_region set region_code='"+areacode+"' where region_level_type_id=5 and region_id="+areaId;
            System.out.println("SQL updateAreaCode is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
   
    private static int updateDcmPosDetailTable(Connection con, Statement stat,String userId,String surveyId,String isExclusive, String isQualityClub,String isLevelOne,String hasSign,String posArabicAddress,String posDocNum,String docLocation, String supervisorName, String teamleaderName,String salesrepName,String dcmPaymentLevelName, String dcmLevelCode ,String dcmStatusName,String posCode, String posChannelId, String regionName, String districtCode,String posGovernName,String posDistrictName, String posCityName,String posAreaName,String posName,String posArabicName,String posAddress)
    {
        int updated=-1;
        int districtId=-1;
        int cityId=-1;
        int dcmPayLevelId=-1;
        int dcmStatusId=-1;
        int dcmLevelId=-1;
        int regionId=-1;
        int governId=-1;
        int areaId=-1;
        int supervisorId=-1;
        int teamleaderId=-1;
        int salesrepId=-1;
        try {
            String ex="";
            String l1="";
            String qc="";
            String sign="";
            if(isExclusive.compareToIgnoreCase("Y")==0 || isExclusive.compareToIgnoreCase("Yes")==0)
                ex="1";
            else ex="0";
            
            if(isQualityClub.compareToIgnoreCase("Y")==0 || isQualityClub.compareToIgnoreCase("Yes")==0)
                qc="1";
            else qc="0";
            
            if(isLevelOne.compareToIgnoreCase("Y")==0 || isLevelOne.compareToIgnoreCase("Yes")==0)
                l1="1";
            else l1="0";
            
            if(hasSign.compareToIgnoreCase("Y")==0 || hasSign.compareToIgnoreCase("Yes")==0)
                sign="1";
            else sign="0";
            
            
            /*String sqlDcmLevelId="select dcm_level_id from gen_dcm_level where LOWER(dcm_level_name)  like LOWER('"+dcmLevelName+"')";
            ResultSet rs11 = stat.executeQuery(sqlDcmLevelId);
            if(rs11.next())
                dcmLevelId = Integer.parseInt(rs11.getString("dcm_level_id"));*/
            
            
            String sqlRegionId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+regionName+"%') and region_level_type_id=1";
            ResultSet rs5 = stat.executeQuery(sqlRegionId);
            if(rs5.next())
                regionId = Integer.parseInt(rs5.getString("region_id"));
            
            
            String sqlGovernId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+posGovernName+"%') and region_level_type_id=2";
            ResultSet rs6 = stat.executeQuery(sqlGovernId);
            if(rs6.next())
                governId = Integer.parseInt(rs6.getString("region_id"));
            
            String sqlAreaId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+posAreaName+"%') and region_level_type_id=5";
            ResultSet rs7 = stat.executeQuery(sqlAreaId);
            if(rs7.next())
                areaId = Integer.parseInt(rs7.getString("region_id"));
            
            
            
            String sqlDistrictId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+posDistrictName+"%') and region_level_type_id=4";
            ResultSet rs1 = stat.executeQuery(sqlDistrictId);
            if(rs1.next())
                districtId = Integer.parseInt(rs1.getString("region_id"));
            
            
            
            String sqlCityId="select region_id from dcm_region where LOWER(region_name) like LOWER('%"+posCityName+"%') and region_level_type_id=3";
            ResultSet rs2 = stat.executeQuery(sqlCityId);
            if(rs2.next())
                cityId = Integer.parseInt(rs2.getString("region_id"));
            
            
            
            String sqlPayLevelId="select dcm_payment_level_id from gen_dcm_payment_level where LOWER(dcm_payment_level_name) like LOWER('%"+dcmPaymentLevelName+"%')";
            ResultSet rs3 = stat.executeQuery(sqlPayLevelId);
            if(rs3.next())
                dcmPayLevelId = Integer.parseInt(rs3.getString("dcm_payment_level_id"));
            
            
            
            String sqlDcmStatusId="select dcm_status_id from gen_dcm_status where LOWER(dcm_status_name) like LOWER('%"+dcmStatusName+"%')";
            ResultSet rs4 = stat.executeQuery(sqlDcmStatusId);
            if(rs4.next())
                dcmStatusId = Integer.parseInt(rs4.getString("dcm_status_id"));
            
            
            
            String sqlSupervisorId="select supervisor_id from scm_supervisor where LOWER(supervisor_name) like LOWER('%"+supervisorName+"%')";
            ResultSet rs8 = stat.executeQuery(sqlSupervisorId);
            if(rs8.next())
                supervisorId = Integer.parseInt(rs8.getString("supervisor_id"));
            
            String sqlTeamleaderId="select teamleader_id from scm_teamleader where LOWER(teamleader_name) like LOWER('%"+teamleaderName+"%')";
            ResultSet rs9 = stat.executeQuery(sqlTeamleaderId);
            if(rs9.next())
                teamleaderId = Integer.parseInt(rs9.getString("teamleader_id"));
            
            String sqlRepId="select salesrep_id from scm_salesrep where LOWER(salesrep_name) like LOWER('%"+salesrepName+"%')";
            ResultSet rs10 = stat.executeQuery(sqlRepId);
            if(rs10.next())
                salesrepId = Integer.parseInt(rs10.getString("salesrep_id"));
            
            
            
            String strSql = "update dcm_pos_detail set user_id="+userId+",survey_id='"+surveyId+"',is_exclusive='"+ex+"',is_quality_club='"+qc+"',is_level_one='"+l1+"',has_sign='"+sign+"',pos_arabic_address = '"+posArabicAddress+"',pos_doc_num='"+posDocNum+"',doc_location='"+docLocation+"',supervisor_id="+supervisorId+", teamleader_id="+teamleaderId+",salesrep_id="+salesrepId+",dcm_payment_level_id="+dcmPayLevelId+", DCM_LEVEL_ID="+dcmLevelCode+" ,pos_status_type_id="+dcmStatusId+", pos_channel_id="+posChannelId+", region_id="+regionId+", district_code='"+districtCode+"',pos_governrate = "+governId+",pos_district_id="+districtId+", pos_city_id="+cityId+",pos_area_id="+areaId+",pos_name='"+posName+"',pos_arabic_name='"+posArabicName+"',pos_address='"+posAddress+"' where pos_code='"+posCode+"'";
            System.out.println("SQL updateDcmPosDetailTable is " + strSql);
            updated = stat.executeUpdate(strSql);
            
                

        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
        
    }
    
    public static int getDataRecords(Connection con, Statement stat,Long fileID) throws ParseException {
        int count=-1;
        try {
            String strSql = "select count(*) from SCM_SUPERVISOR";
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
            String strSql = "select AUTH_RES_STATISTICS.file_id,no_of_read_lines,no_of_inserted_lines ,start_SYSTIMESTAMP,end_SYSTIMESTAMP "
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
        str.append(",sysdate,0,");
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