/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import com.mobinil.sds.core.system.dcm.user.model.DCMUserModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
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

    public static String getDistrictRegionId(Connection con,String districtID){
        String sqlStatement;
        sqlStatement="SELECT PARENT_REGION_ID FROM DCM_REGION WHERE"
                    +" REGION_ID=(SELECT PARENT_REGION_ID FROM DCM_REGION"
                    +" WHERE REGION_ID=((SELECT PARENT_REGION_ID FROM DCM_REGION "
                    +" WHERE REGION_ID="+districtID+")))";
        String regionId=DBUtil.executeQuerySingleValueString(sqlStatement, "PARENT_REGION_ID", con);
        return regionId;
    }


    public static Vector<DCMUserModel> getRegionSupervisors(Connection con,String regionId){
        String sqlStatement;
        Vector <DCMUserModel> supervisors=new Vector();
        sqlStatement="SELECT DU.DCM_USER_ID,DUD.USER_FULL_NAME FROM DCM_USER DU,DCM_USER_DETAIL DUD" 
                    +" WHERE DU.DCM_USER_ID=DUD.USER_ID AND DU.USER_LEVEL_TYPE_ID=4 AND DU.REGION_ID="+regionId+" ORDER BY LOWER(DUD.USER_FULL_NAME)";
        supervisors=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class,"fillForRepSupAssign", con);
        return supervisors;
    }

    public static void assignRepToSupervisor(Connection con,String repId,String supId,String userId){
        String sqlStatement;
        sqlStatement="INSERT INTO SCM_REP_SUPERVISORS VALUES("+repId+","+supId+",sysdate,"+userId+")";
        DBUtil.executeSQL(sqlStatement, con);
    }

    public static void unassignRepFromSupervisor(Connection con,String repId,String supId){
        String sqlStatement;
        sqlStatement="DELETE FROM SCM_REP_SUPERVISORS WHERE REP_ID="+repId+" AND SUP_ID="+supId;
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
        sqlStatement="SELECT DU.DCM_USER_ID,DUD.USER_FULL_NAME FROM DCM_USER DU,DCM_USER_DETAIL DUD" 
                    +" WHERE DU.DCM_USER_ID=DUD.USER_ID AND DU.USER_LEVEL_TYPE_ID=3 AND DU.REGION_ID IN("+areasId+") ORDER BY LOWER(DUD.USER_FULL_NAME)";
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
