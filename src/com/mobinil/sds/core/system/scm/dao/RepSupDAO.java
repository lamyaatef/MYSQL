/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import com.mobinil.sds.core.system.dcm.user.model.DCMUserModel;
import com.mobinil.sds.core.system.scm.model.DCMUserDetailModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author AHMED SAFWAT
 */
public class RepSupDAO {


//    public static String getAreaRegionId(Connection con,String areaId){
//        String sqlStatement;
//        sqlStatement="SELECT PARENT_REGION_ID FROM DCM_REGION WHERE"
//                    +" REGION_ID=(SELECT PARENT_REGION_ID FROM DCM_REGION"
//                    +" WHERE REGION_ID=((SELECT PARENT_REGION_ID FROM DCM_REGION "
//                    +" WHERE REGION_ID=(SELECT PARENT_REGION_ID FROM DCM_REGION WHERE REGION_ID="+areaId+"))))";
//        String regionId=DBUtil.executeQuerySingleValueString(sqlStatement, "PARENT_REGION_ID", con);
//        return regionId;
//    }

    
    public static String getDistrictId(Connection con,String userID, String userLevelTypeId){
        String sqlStatement;
        sqlStatement="select region_id from scm_user_region where user_id='"+userID+"' and user_level_type_id='"+userLevelTypeId+"'";
        System.out.println("getDistrictId "+sqlStatement);
        String districtId=DBUtil.executeQuerySingleValueString(sqlStatement, "region_id", con);
        return districtId;
    }
    
    public static String getDistrictRegionId(Connection con,String districtID){
        String sqlStatement;
        sqlStatement="SELECT PARENT_REGION_ID FROM DCM_REGION WHERE"
                    +" REGION_ID=(SELECT PARENT_REGION_ID FROM DCM_REGION"
                    +" WHERE REGION_ID=((SELECT PARENT_REGION_ID FROM DCM_REGION "
                    +" WHERE REGION_ID="+districtID+")))";
        System.out.println("getDistrictRegionId "+sqlStatement);
        String regionId=DBUtil.executeQuerySingleValueString(sqlStatement, "PARENT_REGION_ID", con);
        return regionId;
    }


    public static Vector<DCMUserModel> getRegionSupervisors(Connection con,String regionId){
        String sqlStatement;
        Vector <DCMUserModel> supervisors=new Vector();
        /*sqlStatement="SELECT DU.DCM_USER_ID,DUD.USER_FULL_NAME FROM DCM_USER DU,DCM_USER_DETAIL DUD" 
                    +" WHERE DU.DCM_USER_ID=DUD.USER_ID AND DU.USER_LEVEL_TYPE_ID=4 AND DU.REGION_ID="+regionId+" ORDER BY LOWER(DUD.USER_FULL_NAME)";*/
        sqlStatement="select scm_user_region.region_id , scm_supervisor.supervisor_id as DCM_USER_ID , scm_supervisor.supervisor_name as USER_FULL_NAME from scm_user_region, scm_supervisor, DCM_USER_DETAIL where DCM_USER_DETAIL.user_id = scm_supervisor.supervisor_id and scm_user_region.user_id = scm_supervisor.supervisor_id and scm_user_region.user_level_type_id=4 and scm_user_region.region_id="+regionId;
        System.out.println("getRegionSupervisors "+sqlStatement);
        supervisors=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForRepSupAssign", con);
        return supervisors;
    }
    
    public static Vector<DCMUserModel> getAllTeamleadersData(Connection con) {
        
        Vector <DCMUserModel> teamleaders=new Vector();
        System.out.println("getAllTeamleadersData ");
        
              
        try {
            Statement stat = con.createStatement();
            String sqlStatement = "select dcm_user_level_type.*, scm_teamleader.*, dcm_user.user_id from dcm_user_level_type, dcm_user , scm_teamleader where dcm_user.user_level_type_id = dcm_user_level_type.user_level_type_id and dcm_user.dcm_user_id=scm_teamleader.teamleader_id and dcm_user.user_level_type_id=5 order by teamleader_name";
            System.out.println("get ALL teamleaders to assign from : "+sqlStatement);
            teamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForRepAssignTeamleader", con);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return teamleaders;
    }
    
    
    public static Vector<DCMUserModel> getAllRepsData(Connection con) {
        
        Vector <DCMUserModel> reps=new Vector();
        System.out.println("getAllRepsData ");
        
              
        try {
            Statement stat = con.createStatement();
            String sqlStatement = "select dcm_user_level_type.*, scm_salesrep.*, dcm_user.user_id from dcm_user_level_type, dcm_user , scm_salesrep where dcm_user.user_level_type_id = dcm_user_level_type.user_level_type_id and dcm_user.dcm_user_id=scm_salesrep.salesrep_id and dcm_user.user_level_type_id=6 order by salesrep_name";
            System.out.println("get ALL reps to assign from : "+sqlStatement);
            reps=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForSupervisorAssignRep", con);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return reps;
    }
    
    
    public static Vector<DCMUserModel> getAllSupervisorsData(Connection con) {
        
        Vector <DCMUserModel> supervisors=new Vector();
        System.out.println("getAllSupervisorsData ");
        
              
        try {
            Statement stat = con.createStatement();
            String sqlStatement = "select dcm_user_level_type.*, scm_supervisor.*, dcm_user.user_id from dcm_user_level_type, dcm_user , scm_supervisor where dcm_user.user_level_type_id = dcm_user_level_type.user_level_type_id and dcm_user.dcm_user_id=scm_supervisor.supervisor_id and dcm_user.user_level_type_id=4 order by supervisor_name";
            System.out.println("get ALL supervisors to assign from : "+sqlStatement);
            supervisors=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForRepAssignSupervisor", con);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return supervisors;
    }
    
    
    public static Vector<DCMUserModel> getRegionTeamleaders(Connection con,String regionId){
        String sqlStatement;
        Vector <DCMUserModel> teamleaders=new Vector();
        /*sqlStatement="SELECT DU.DCM_USER_ID,DUD.USER_FULL_NAME FROM DCM_USER DU,DCM_USER_DETAIL DUD" 
                    +" WHERE DU.DCM_USER_ID=DUD.USER_ID AND DU.USER_LEVEL_TYPE_ID=5 AND DU.REGION_ID="+regionId+" ORDER BY LOWER(DUD.USER_FULL_NAME)";*/
        sqlStatement = "select scm_user_region.region_id , scm_teamleader.teamleader_id as DCM_USER_ID , scm_teamleader.teamleader_name as USER_FULL_NAME from scm_user_region, scm_teamleader, DCM_USER_DETAIL where DCM_USER_DETAIL.user_id = scm_teamleader.teamleader_id and scm_user_region.user_id = scm_teamleader.teamleader_id and scm_user_region.user_level_type_id=5 and scm_user_region.region_id="+regionId;
        System.out.println("getRegionTeamleaders "+sqlStatement);
        teamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForRepTeamleadAssign", con);
        return teamleaders;
    }

    public static void assignRepToSupervisor(Connection con,String repId,String supId,String userId){
        String sqlStatement;
        sqlStatement="update scm_salesrep set sup_id="+supId+" where salesrep_id="+repId;
        DBUtil.executeSQL(sqlStatement, con);
    }
    public static void reassignRepToTeamleader(Connection con,String repId,String teamleadId,String userId){
        String sqlStatement;
        sqlStatement="UPDATE SCM_REP_TEAMLEADERS SET REP_ID = "+repId+",TEAMLEAD_ID="+teamleadId+",CREATED_IN=systimestamp,CREATED_BY="+userId;
        DBUtil.executeSQL(sqlStatement, con);
    }
    public static void reassignRepToSupervisor(Connection con,String repId,String supId,String userId){
        String sqlStatement;
        sqlStatement="UPDATE SCM_REP_SUPERVISORS SET REP_ID = "+repId+",SUP_ID="+supId+",CREATED_IN=systimestamp,CREATED_BY = "+userId;
        DBUtil.executeSQL(sqlStatement, con);
    }
    
    public static void addNewSupervisor(Connection con, DCMUserDetailModel userDetail,String supervisorId) 
    {
        String strSql = "insert into SCM_SUPERVISOR ( SUPERVISOR_ID, SUPERVISOR_NAME, EMAIL, MOBILE ,CREATION_TIMESTAMP) values ("+supervisorId+",'"+userDetail.getUserFullName()+"','"+userDetail.getUserEmail()+"','"+userDetail.getUserMobile()+"',systimestamp)"; 
        System.out.println("query1 addNewSupervisor "+strSql);
        try{
            Statement stat = con.createStatement();
            stat.execute(strSql);   
        }catch(Exception e){}
         
    }
    public static void addNewSalesRep(Connection con, DCMUserDetailModel userDetail,String repId) 
    {
        String strSql = "insert into SCM_SALESREP ( SALESREP_ID, SALESREP_NAME, EMAIL, MOBILE ,CREATION_TIMESTAMP) values ("+repId+",'"+userDetail.getUserFullName()+"','"+userDetail.getUserEmail()+"','"+userDetail.getUserMobile()+"',systimestamp)"; 
        System.out.println("query1 addNewSalesRep"+strSql);
        try{
            Statement stat = con.createStatement();
            stat.execute(strSql);   
        }catch(Exception e){}
         
    }
    public static void addNewTeamleader(Connection con, DCMUserDetailModel userDetail,String teamleaderId) 
    {
        String strSql = "insert into SCM_TEAMLEADER ( TEAMLEADER_ID, TEAMLEADER_NAME, EMAIL, MOBILE ,CREATION_TIMESTAMP) values ("+teamleaderId+",'"+userDetail.getUserFullName()+"','"+userDetail.getUserEmail()+"','"+userDetail.getUserMobile()+"',systimestamp)"; 
        System.out.println("query1 addNewTeamleader"+strSql);
        try{
            Statement stat = con.createStatement();
            stat.execute(strSql);   
        }catch(Exception e){}
         
    }
    public static void assignRepToTeamleader(Connection con,String repId,String teamleadId,String userId) throws SQLException{
        
        
        String sqlStatement;
        sqlStatement="update scm_salesrep set teamlead_id="+teamleadId+" where salesrep_id="+repId;
        DBUtil.executeSQL(sqlStatement, con);
        
        
    }
    
    
    public static void assignTeamleaderToSupervisor(Connection con,String teamleaderId,String supervisorId, String systemUserId) throws SQLException{
        String sqlStatement;
        sqlStatement="update scm_teamleader set sup_id="+supervisorId+" where teamleader_id="+teamleaderId;
        DBUtil.executeSQL(sqlStatement, con);
    }
    
     

    public static void unassignRepFromSupervisor(Connection con,String repId,String supId){
        String sqlStatement;
        sqlStatement="update scm_salesrep set sup_id=null where salesrep_id="+repId;
        System.out.println("unassignRepFromSupervisor : "+sqlStatement);
        DBUtil.executeSQL(sqlStatement, con);   
    }
    public static void unassignTeamleadFromSupervisor(Connection con,String teamleadId,String supId){
        String sqlStatement;
        sqlStatement="update scm_teamleader set sup_id=null where teamleader_id="+teamleadId;
        DBUtil.executeSQL(sqlStatement, con);   
    }
    
    public static void unassignRepFromTeamleader(Connection con,String repId,String teamleadId){
        String sqlStatement;
        sqlStatement="update scm_salesrep set teamlead_id=null where salesrep_id="+repId;
        System.out.println("UNASSIGN REP FROM TEAMLEADER "+sqlStatement);
        DBUtil.executeSQL(sqlStatement, con);   
    }
    

    public static boolean checkIfRepAssigntoMoreThanTwoSupervisors(Connection con,String repId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(SUP_ID) COUNT FROM SCM_REP_SUPERVISORS WHERE REP_ID="+repId;
        int checkFlag=-1;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag<2)
            return false;
        else
            return true;
    }

    public static boolean checkIfRepAssigntoMoreThanTwoTeamleaders(Connection con,String repId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(TEAMLEAD_ID) COUNT FROM SCM_REP_TEAMLEADERS WHERE REP_ID="+repId;
        int checkFlag=-1;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag<2)
            return false;
        else
            return true;
    }
    
    
    
    
    
    
    public static boolean checkIfTeamleadAssigntoMoreThanTwoSupervisors(Connection con,String teamleadId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(SUP_ID) COUNT FROM SCM_TEAMLEADER_SUPERVISORS WHERE TEAMLEAD_ID="+teamleadId;
        int checkFlag=-1;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag<2)
            return false;
        else
            return true;
    }



    public static boolean checkIfRepAlreadyAssignedToThisSup(Connection con,String repId,String supId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(SUP_ID) COUNT FROM SCM_REP_SUPERVISORS WHERE REP_ID="+repId+" AND SUP_ID="+supId;
        int checkFlag=-1;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag>0)
            return true;
        else
            return false;

    }
    
    
    public static boolean checkIfTeamleadAlreadyAssignedToThisSup(Connection con,String teamleadId,String supId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(SUP_ID) COUNT FROM SCM_TEAMLEADER_SUPERVISORS WHERE TEAMLEAD_ID="+teamleadId+" AND SUP_ID="+supId;
        int checkFlag=-1;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag>0)
            return true;
        else
            return false;

    }
    
    public static boolean checkIfRepAlreadyAssignedToThisTeamlead(Connection con,String repId,String supId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(TEAMLEAD_ID) COUNT FROM SCM_REP_TEAMLEADERS WHERE REP_ID="+repId+" AND TEAMLEAD_ID="+supId;
        int checkFlag=-1;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag>0)
            return true;
        else
            return false;

    }
//    public static Vector<RegionModel> getRegionAreas(Connection con,String regionId){
//        String sqlStatement;
//        Vector <RegionModel> areas=new Vector();
//        sqlStatement="SELECT REGION_ID,REGION_NAME FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'area' ) START WITH PARENT_REGION_ID="+regionId+" CONNECT BY PRIOR REGION_ID=PARENT_REGION_ID";
//        areas=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class,"fillForRepManagementSearch", con);
//        return areas;
//
//    }

    public static Vector<RegionModel> getRegionDistricts(Connection con,String regionId){
        String sqlStatement;
        Vector <RegionModel> areas=new Vector();
        sqlStatement="SELECT REGION_ID,REGION_NAME FROM DCM_REGION WHERE REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'district' ) START WITH PARENT_REGION_ID="+regionId+" CONNECT BY PRIOR REGION_ID=PARENT_REGION_ID";
        areas=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class,"fillForRepManagementSearch", con);
        return areas;

    }
    public static Vector<DCMUserModel> getRegionReps(Connection con,String areasId){
        String sqlStatement;
        Vector <DCMUserModel> reps=new Vector();
        /*sqlStatement="SELECT DU.DCM_USER_ID,DUD.USER_FULL_NAME FROM DCM_USER DU,DCM_USER_DETAIL DUD" 
                    +" WHERE DU.DCM_USER_ID=DUD.USER_ID AND DU.USER_LEVEL_TYPE_ID=3 AND DU.REGION_ID IN("+areasId+") ORDER BY LOWER(DUD.USER_FULL_NAME)";*/
        sqlStatement = "select scm_user_region.region_id , scm_salesrep.salesrep_id as DCM_USER_ID , scm_salesrep.salesrep_name as USER_FULL_NAME from scm_user_region, scm_salesrep, DCM_USER_DETAIL where DCM_USER_DETAIL.user_id = scm_salesrep.salesrep_id and scm_user_region.user_id = scm_salesrep.salesrep_id and scm_user_region.user_level_type_id=6 and scm_user_region.region_id="+areasId;
        System.out.println("getRegionReps : "+sqlStatement);
        reps=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForRepSupAssign", con);
        return reps;
    }
    
    
  

    public static String getUserName(String userId)
    {
        String query="SELECT GEN_PERSON.PERSON_FULL_NAME FROM GEN_PERSON  WHERE GEN_PERSON.PERSON_ID="+userId;
        String userName=DBUtil.executeQuerySingleValueString(query,"PERSON_FULL_NAME");
        return userName;
    }
    public static String getUserNameByUserTable(String userId)
    {
        String query="SELECT GEN_PERSON.PERSON_FULL_NAME FROM GEN_PERSON  WHERE GEN_PERSON.PERSON_ID=" +
                " (SELECT USER_ID FROM DCM_USER WHERE DCM_USER_ID = "+userId+")";
        String userName=DBUtil.executeQuerySingleValueString(query,"PERSON_FULL_NAME");
        return userName;
    }
    public static String getTypeofPersonID(String idType)
    {
        String query="SELECT D.ID_TYPE_NAME"
                    +" FROM SDS.DCM_ID_TYPE D WHERE ID_TYPE_ID ="+idType;
        String type=DBUtil.executeQuerySingleValueString(query,"ID_TYPE_NAME");
        return type;
    }
}
