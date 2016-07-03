/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import com.mobinil.sds.core.system.scm.model.DCMUserDetailModel;
import com.mobinil.sds.core.system.dcm.user.model.DCMUserModel;
import com.mobinil.sds.core.system.gn.dcm.model.DCMModel;
import com.mobinil.sds.core.system.sa.persons.model.PersonModel;
import com.mobinil.sds.core.system.scm.model.DCMUserLevelTypeModel;
import com.mobinil.sds.core.system.scm.model.POSGroupModel;
import com.mobinil.sds.core.system.scm.model.RepPOSGroupModel;
import com.mobinil.sds.core.system.scm.model.RepSupervisorModel;
import com.mobinil.sds.core.system.scm.model.RepTeamleaderModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author AHMED SAFWAT
 */
public class RepManagementDAO {

    public static Vector<RegionModel> getRegions(Connection con){
        
        Vector<RegionModel> regions=new Vector();
        String sqlStatement;
        sqlStatement="SELECT REGION_ID,REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'region' ) ORDER BY REGION_NAME ASC";
        regions=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regions;
    }

    public static Vector<DCMUserLevelTypeModel>getUserLevelsForSupervisorAndRep(Connection con){

        Vector<DCMUserLevelTypeModel> repAndSupervisorLevels=new Vector();
        String sqlStatement;
        sqlStatement="SELECT USER_LEVEL_TYPE_ID,USER_LEVEL_TYPE_NAME FROM DCM_USER_LEVEL_TYPE WHERE USER_LEVEL_TYPE_ID=3 OR USER_LEVEL_TYPE_ID=4 OR USER_LEVEL_TYPE_ID=5";
        repAndSupervisorLevels=DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserLevelTypeModel.class, con);
        return repAndSupervisorLevels;

    }

    public static Vector<DCMUserModel>searchRepsAndSupervisor(Connection con,String name,Integer regionId,Integer levelTypeId,String rowNum){


        Vector<DCMUserModel> dcmUser=new Vector();
        String sqlStatement;
        String sqlSearch="";

        if(levelTypeId!=null||name!=null||regionId!=null){
            sqlSearch+=" WHERE";
        }
        if(levelTypeId!=null)
            sqlSearch+=" USER_LEVEL_TYPE_ID="+levelTypeId.intValue();
        if(name!=null){
            if(levelTypeId!=null){
               sqlSearch+=" AND";
            }
           sqlSearch+=" LOWER(USER_FULL_NAME) LIKE '%"+name.toLowerCase()+"%'";
        }

        if(regionId!=null){
            if(levelTypeId!=null||name!=null){
                sqlSearch+=" AND";
            }
            Vector <RegionModel> areas=new Vector();
            StringBuffer areasId=new StringBuffer("");

//            areas=RepSupDAO.getRegionAreas(con, regionId.toString());
            areas=RepSupDAO.getRegionDistricts(con, regionId.toString());

                if(areas!=null&&areas.size()!=0){
                    for(int i=0;i<areas.size();i++){
                       RegionModel area=(RegionModel)areas.get(i) ;
                       areasId.append(area.getRegionId());
                       if(areas.size()!=i+1){
                       areasId.append(",");
                       }
                    }
                }
            String areasIdString=areasId.toString();
            if(areasIdString!=null && !areasIdString.trim().equalsIgnoreCase("")){
                areasIdString=","+areasIdString;
            }

            sqlSearch+=" REGION_ID IN("+regionId+areasIdString+")";

        }

        sqlStatement="SELECT * from"
            +"("
            +"SELECT x.*, ROWNUM as row_num FROM "
            +"	(SELECT DCM_USER.DCM_USER_ID, DCM_USER.USER_ID, DCM_USER.USER_LEVEL_TYPE_ID,"
            +"		DCM_USER_DETAIL.USER_FULL_NAME, DCM_USER.REGION_ID, DCM_REGION.REGION_NAME,"
            +"		DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_NAME, DCM_USER_DETAIL.CREATION_TIMESTAMP "
            +"	FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION,DCM_USER_LEVEL_TYPE "
            +"	WHERE "
            +"		DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND "
            +"		DCM_USER.USER_LEVEL_TYPE_ID=DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_ID AND "
            +"		DCM_USER.USER_STATUS_TYPE_ID=1 AND DCM_USER.REGION_ID=DCM_REGION.REGION_ID "
            +"		AND DCM_USER.USER_LEVEL_TYPE_ID IN(3,4) "
            +"	) x"
            +" "+sqlSearch +"   "
            +" ) WHERE row_num > = ('"+rowNum+"'*20)+1 AND row_num < = ('"+rowNum+"'+1)*20 ORDER BY ROWNUM ";
        dcmUser= DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class, "fillForRepManagementSearch", con);

        return dcmUser;
    }

    public static String getAllRepsAndSupPageCount(Connection con,String name,Integer regionId,Integer levelTypeId){
        String sqlStatement;
        String sqlStringSearch="";



        if(levelTypeId!=null)
            sqlStringSearch+=" AND DCM_USER.USER_LEVEL_TYPE_ID="+levelTypeId.intValue();
        if(name!=null){

            sqlStringSearch+=" AND LOWER(DCM_USER_DETAIL.USER_FULL_NAME) LIKE '%"+name.toLowerCase()+"%'";
        }


        if(regionId!=null){

        Vector <RegionModel> areas=new Vector();
        StringBuffer areasId=new StringBuffer("");

//        areas=RepSupDAO.getRegionAreas(con, regionId.toString());

            areas=RepSupDAO.getRegionDistricts(con, regionId.toString());
            if(areas!=null&&areas.size()!=0){
                for(int i=0;i<areas.size();i++){
                   RegionModel area=(RegionModel)areas.get(i) ;
                   areasId.append(area.getRegionId());
                   if(areas.size()!=i+1){
                   areasId.append(",");
                   }
                }
            }
        String areasIdString=areasId.toString();
        if(areasIdString!=null && !areasIdString.equalsIgnoreCase("")){
            areasIdString=","+areasIdString;
        }

        sqlStringSearch+=" AND DCM_USER.REGION_ID IN("+regionId+areasIdString+")";

        }

        sqlStatement="SELECT CEIL(COUNT(*)/20) COUNT FROM (SELECT ROWNUM as row_num, DCM_USER.DCM_USER_ID, DCM_USER.USER_ID, DCM_USER.REGION_ID, DCM_USER.USER_LEVEL_TYPE_ID,DCM_USER_DETAIL.USER_FULL_NAME,DCM_REGION.REGION_NAME,DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_NAME, DCM_USER_DETAIL.CREATION_TIMESTAMP"
            +" FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION,DCM_USER_LEVEL_TYPE"
            +" WHERE DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
            +" DCM_USER.USER_LEVEL_TYPE_ID=DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_ID"
            +" AND DCM_USER.USER_STATUS_TYPE_ID=1 AND DCM_USER.REGION_ID=DCM_REGION.REGION_ID "+sqlStringSearch
            +" ) ";

        String count="0";
        count=DBUtil.executeQuerySingleValueString(sqlStatement, "COUNT", con);
        return count;
    }

    public static void addNewRepOrSupervisor(Connection con,DCMUserModel repOrSupervisor,DCMUserDetailModel userDetail,String userId){
        long dcmUserDetailId=DBUtil.getSequenceNextVal(con, "SEQ_DCM_USER_DETAIL_ID");
        long dcmUserId=DBUtil.getSequenceNextVal(con, "SEQ_DCM_USER_ID");
        String insertDCMUserSqlStatement="insert into DCM_USER (DCM_USER_ID, USER_ID, MANAGER_DCM_USER_ID, USER_LEVEL_TYPE_ID, USER_DETAIL_ID, USER_STATUS_TYPE_ID, REGION_ID, USER_LEVEL_ID) " +
                "values (?, ?, NULL, ?, ?, 1, ?, 0)";
        String insertDCMUserDeatilSqlStatement="insert into SDS.DCM_USER_DETAIL " +
                "(USER_DETAIL_ID, USER_ID, USER_FULL_NAME, USER_ADDRESS, USER_EMAIL, USER_MOBILE, REGION_ID, USER_DETAIL_STATUS_ID, CREATION_TIMESTAMP, CREATION_USER_ID) " +
                "values (?, ?, ?, ?, ?, ?, ?, 1, sysdate, ?)";
        DBUtil.executePreparedStatment(insertDCMUserSqlStatement, con, new Object[]{dcmUserId,repOrSupervisor.getUserId(),Integer.parseInt(repOrSupervisor.getUserLevelTypeId()),dcmUserDetailId,Integer.parseInt(repOrSupervisor.getRegionId())});
        DBUtil.executePreparedStatment(insertDCMUserDeatilSqlStatement,con,new Object[]{dcmUserDetailId,dcmUserId,userDetail.getUserFullName(),userDetail.getUserAddress(),userDetail.getUserEmail(),userDetail.getUserMobile(),repOrSupervisor.getRegionId(),userId});

    }

    public static Vector<RegionModel> getGovernorates(Connection con,String regionId){
        
        Vector<RegionModel> regionGovernorates=new Vector();
        String sqlStatement="SELECT REGION_ID, REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'governorate' ) AND PARENT_REGION_ID="+regionId;
        regionGovernorates=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regionGovernorates;

    }
    public static Vector<RegionModel> getCities(Connection con,String governorateId){

        Vector<RegionModel> regionGovernorates=new Vector();
        String sqlStatement="SELECT REGION_ID, REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'city' ) AND PARENT_REGION_ID="+governorateId;
        regionGovernorates=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regionGovernorates;

    }
    public static Vector<RegionModel> getDistricts(Connection con,String cityId){

        Vector<RegionModel> regionGovernorates=new Vector();
        String sqlStatement="SELECT REGION_ID, REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'district' ) AND PARENT_REGION_ID="+cityId;
        regionGovernorates=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regionGovernorates;

    }
    public static Vector<RegionModel> getAreas(Connection con,String districtId){

        Vector<RegionModel> regionGovernorates=new Vector();
        String sqlStatement="SELECT REGION_ID, REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'area' ) AND PARENT_REGION_ID="+districtId;
        regionGovernorates=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regionGovernorates;

    }

    public static DCMUserModel getDcmUser(Connection con,String dcmUserId){

        String sqlStatement;
        sqlStatement="SELECT DCM_USER_ID, USER_DETAIL_ID, USER_ID, REGION_ID, USER_LEVEL_TYPE_ID FROM DCM_USER WHERE DCM_USER_ID="+dcmUserId;
        DCMUserModel dcmUser=(DCMUserModel)DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserModel.class, "fillRepDcmUserModel", con);
        return dcmUser;

    }


        public static DCMUserDetailModel getDcmUserDetail(Connection con,String dcmUserDetailId){

        String sqlStatement;
        sqlStatement="SELECT USER_FULL_NAME, USER_ADDRESS, USER_EMAIL, USER_MOBILE FROM DCM_USER_DETAIL WHERE USER_DETAIL_ID="+dcmUserDetailId;
        DCMUserDetailModel dcmUserDetail=(DCMUserDetailModel)DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserDetailModel.class, "fillRepDCMUserDetail", con);
        return dcmUserDetail;

    }
        public static String getParentRegionId(Connection con,String regionId){
            String sqlStatement;
            sqlStatement="SELECT PARENT_REGION_ID FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_ID="+regionId;
            String parentRegionId=DBUtil.executeQuerySingleValueString(sqlStatement, "PARENT_REGION_ID", con);
            return parentRegionId;
        }

    public static void updateRepOrSupervisor(Connection con,DCMUserModel dcmUser,DCMUserDetailModel dcmUserDetail,String userId){
        String updateDCMUserSqlStatement="UPDATE DCM_USER SET USER_LEVEL_TYPE_ID=? , REGION_ID=? WHERE DCM_USER_ID=?";
        String updateDCMUserDetailSqlStatement="UPDATE DCM_USER_DETAIL SET USER_FULL_NAME=?, USER_ADDRESS=?, USER_EMAIL=?, USER_MOBILE=?, " +
                "REGION_ID=? WHERE USER_DETAIL_ID=?";

        DBUtil.executePreparedStatment(updateDCMUserSqlStatement, con, new Object[]{dcmUser.getUserLevelTypeId(),dcmUser.getRegionId(),dcmUser.getDcmUserId()});
        DBUtil.executePreparedStatment(updateDCMUserDetailSqlStatement,con,new Object[]{dcmUserDetail.getUserFullName(),dcmUserDetail.getUserAddress(),dcmUserDetail.getUserEmail(),dcmUserDetail.getUserMobile(),dcmUser.getRegionId(),dcmUser.getUserDetailId()});
    }

    public static void deleteRepOrSupervisor(Connection con, String dcmUserId,String userLevelTypeId){
            String deleteDCMUserSqlStatement="UPDATE DCM_USER SET USER_STATUS_TYPE_ID=3 WHERE DCM_USER_ID="+dcmUserId;

        if(userLevelTypeId.equals("3")){
        String deleteAssignedPOSGroups="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+dcmUserId;
        String deleteAssignedSups="DELETE FROM SCM_REP_SUPERVISORS WHERE REP_ID="+dcmUserId; 
        DBUtil.executeSQL(deleteAssignedPOSGroups);
        DBUtil.executeSQL(deleteAssignedSups);        
        }

        if(userLevelTypeId.equals("4")){
                    String deleteAssignedReps="DELETE FROM SCM_REP_SUPERVISORS WHERE SUP_ID="+dcmUserId;
                    DBUtil.executeSQL(deleteAssignedReps);
        }

        DBUtil.executeSQL(deleteDCMUserSqlStatement, con);
    }

    public static boolean checkIfRegionIsDistrict(Connection con, String regionId){

        String sqlStatement="SELECT LOWER(DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_NAME) REGION_LEVEL_TYPE_NAME  FROM DCM_REGION,DCM_REGION_LEVEL_TYPE WHERE DCM_REGION.REGION_LEVEL_TYPE_ID=DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID AND REGION_ID="+regionId;
        String regionLevelTypeName=DBUtil.executeQuerySingleValueString(sqlStatement, "REGION_LEVEL_TYPE_NAME");
        if(regionLevelTypeName.equalsIgnoreCase("district"))
            return true;
        else
            return false;
    }


    public static DCMUserDetailModel getRepSupDetail(Connection con,String userLevelTypeId,String dcmUserId){

        DCMUserDetailModel dcmUserDetail=new DCMUserDetailModel();
        String sqlStatement;
        sqlStatement="SELECT DCM_USER.DCM_USER_ID, DCM_USER.USER_LEVEL_TYPE_ID, DCM_USER.REGION_ID ,DCM_USER_DETAIL.USER_FULL_NAME,DCM_USER_DETAIL.USER_ADDRESS,DCM_USER_DETAIL.USER_EMAIL,DCM_USER_DETAIL.USER_MOBILE,DCM_REGION.REGION_NAME"
                    +" FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION"
                    +" WHERE DCM_USER.USER_STATUS_TYPE_ID<>3 AND DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
                    +" DCM_USER.REGION_ID=DCM_REGION.REGION_ID AND DCM_USER.USER_LEVEL_TYPE_ID="+userLevelTypeId+" AND DCM_USER.DCM_USER_ID="+dcmUserId;

           dcmUserDetail= DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserDetailModel.class, "fillForRepSupDetail", con);
           return dcmUserDetail;
    }
    public static DCMUserDetailModel getRepTeamleadDetail(Connection con,String userLevelTypeId,String dcmUserId){

        DCMUserDetailModel dcmUserDetail=new DCMUserDetailModel();
        String sqlStatement;
        sqlStatement="SELECT DCM_USER.DCM_USER_ID, DCM_USER.USER_LEVEL_TYPE_ID, DCM_USER.REGION_ID ,DCM_USER_DETAIL.USER_FULL_NAME,DCM_USER_DETAIL.USER_ADDRESS,DCM_USER_DETAIL.USER_EMAIL,DCM_USER_DETAIL.USER_MOBILE,DCM_REGION.REGION_NAME"
                    +" FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION"
                    +" WHERE DCM_USER.USER_STATUS_TYPE_ID<>3 AND DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
                    +" DCM_USER.REGION_ID=DCM_REGION.REGION_ID AND DCM_USER.USER_LEVEL_TYPE_ID="+userLevelTypeId+" AND DCM_USER.DCM_USER_ID="+dcmUserId;

           dcmUserDetail= DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserDetailModel.class, "fillForRepTeamleadDetail", con);
           return dcmUserDetail;
    }

    public static RepPOSGroupModel getRepPOSGroup(Connection con, String dcmUserId){

            RepPOSGroupModel repPOSGroup=new RepPOSGroupModel();
            String sqlStatement="SELECT PG.GROUP_ID,PG.GROUP_NAME FROM SCM_REP_POS_GROUP RG ,SCM_POS_GROUP PG WHERE RG.GROUP_ID=PG.GROUP_ID AND RG.DCM_USER_ID="+dcmUserId;
            repPOSGroup=DBUtil.executeSqlQuerySingleValue(sqlStatement, RepPOSGroupModel.class, con);
            return repPOSGroup;
    }

        public static Vector<RepSupervisorModel> getRepSupervisors(Connection con, String dcmUserId){

            Vector<RepSupervisorModel> repSupervisors=new Vector();
            String sqlStatement="SELECT RS.SUP_ID,RS.REP_ID,UD.USER_FULL_NAME SUP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_SUPERVISORS RS WHERE RS.SUP_ID=UD.USER_ID"
                                +" AND RS.REP_ID="+dcmUserId;
            repSupervisors=DBUtil.executeSqlQueryMultiValue(sqlStatement, RepSupervisorModel.class, con);
            return repSupervisors;
    }
        
        
        
           public static RepSupervisorModel getRepSupervisor(Connection con, String dcmUserId, String superId){

            RepSupervisorModel repSupervisor= null;
            String sqlStatement="SELECT RS.SUP_ID,RS.REP_ID,UD.USER_FULL_NAME SUP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_SUPERVISORS RS WHERE RS.SUP_ID=UD.USER_ID"
                                +" AND RS.REP_ID="+dcmUserId
                                +" AND RS.SUP_ID="+superId;
            System.out.println("GET : "+sqlStatement);
            repSupervisor=DBUtil.executeSqlQuerySingleValue(sqlStatement, RepSupervisorModel.class, con);
            return repSupervisor;
    }
        
           public static Vector<RepTeamleaderModel> getRepTeamleaders(Connection con, String dcmUserId){

            Vector<RepTeamleaderModel> repTeamleaders=new Vector();
            String sqlStatement="SELECT RT.TEAMLEAD_ID,RT.REP_ID,UD.USER_FULL_NAME TEAMLEAD_NAME,RT.CREATED_BY,RT.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RT WHERE RT.TEAMLEAD_ID=UD.USER_ID"
                                +" AND RT.REP_ID="+dcmUserId;
            repTeamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, RepTeamleaderModel.class, con);
            return repTeamleaders;
    }
           
        public static RepTeamleaderModel getRepTeamleader(Connection con, String dcmUserId, String teamleadId){

            RepTeamleaderModel repTeamleader= null;
            String sqlStatement="SELECT RT.TEAMLEAD_ID,RT.REP_ID,UD.USER_FULL_NAME TEAMLEAD_NAME,RT.CREATED_BY,RT.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RT WHERE RT.TEAMLEAD_ID=UD.USER_ID"
                                +" AND RT.REP_ID="+dcmUserId
                                +" AND RT.TEAMLEAD_ID="+teamleadId;
            repTeamleader=DBUtil.executeSqlQuerySingleValue(sqlStatement, RepTeamleaderModel.class, con);
            return repTeamleader;
    }
           
           
    public static Vector<RepSupervisorModel> getSupervisorReps(Connection con, String dcmUserId){

            Vector<RepSupervisorModel> repSupervisors=new Vector();
            String sqlStatement="SELECT RS.REP_ID,RS.SUP_ID,UD.USER_FULL_NAME SUP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_SUPERVISORS RS WHERE RS.REP_ID=UD.USER_ID"
                                +" AND RS.SUP_ID="+dcmUserId;
            repSupervisors=DBUtil.executeSqlQueryMultiValue(sqlStatement, RepSupervisorModel.class, con);
            return repSupervisors;
    }
    public static Vector<RepTeamleaderModel> getTeamleaderReps(Connection con, String dcmUserId){

            Vector<RepTeamleaderModel> repTeamleaders=new Vector();
            String sqlStatement="SELECT RS.REP_ID,RS.TEAMLEAD_ID,UD.USER_FULL_NAME TEAMLEAD_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RS WHERE RS.REP_ID=UD.USER_ID"
                                +" AND RS.TEAMLEAD_ID="+dcmUserId;
            System.out.println("getTeamleaderReps "+sqlStatement);
            repTeamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, RepTeamleaderModel.class, con);
            return repTeamleaders;
    }

    public static Vector<PersonModel> searchGENPersons(Connection con,String personName){
        Vector<PersonModel> persons=new Vector();
        String sqlStatement="SELECT GP.PERSON_ID,GP.PERSON_FULL_NAME, GP.PERSON_ADDRESS, GP.PERSON_EMAIL"
                            +" FROM GEN_PERSON GP, GEN_USER GU"
                            +" WHERE GU.USER_ID=GP.PERSON_ID AND GU.USER_STATUS_ID=1 AND GP.PERSON_ID NOT IN ("
                            +" SELECT USER_ID FROM DCM_USER WHERE USER_STATUS_TYPE_ID <>3 AND USER_ID IS NOT NULL)";
        if(personName!=null&& !personName.trim().equalsIgnoreCase("")){
           sqlStatement+=" AND  LOWER(GP.PERSON_FULL_NAME) LIKE ?";
           personName="%"+personName.toLowerCase()+"%";
           persons=DBUtil.executePreparedSqlQueryMultiValue(sqlStatement, PersonModel.class, con,new Object[]{personName});
        }
        else{
           persons=DBUtil.executeSqlQueryMultiValue(sqlStatement, PersonModel.class, con);
        }

        return persons;
    }
    public static boolean checkIfUserAlreadyCreated(Connection con,String personId){
        String sqlStatement;
        sqlStatement="SELECT COUNT(DCM_USER_ID) COUNT FROM DCM_USER WHERE USER_STATUS_TYPE_ID<>3 AND USER_ID="+personId;
        int count=-1;
        count =DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);

        if(count>0)
            return true;
        else
            return false;


    }


}