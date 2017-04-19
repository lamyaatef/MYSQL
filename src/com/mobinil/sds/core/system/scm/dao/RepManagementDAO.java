/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import com.mobinil.sds.core.system.scm.model.DCMUserDetailModel;
import com.mobinil.sds.core.system.dcm.user.model.DCMUserModel;
import com.mobinil.sds.core.system.gn.dcm.model.DCMModel;
import com.mobinil.sds.core.system.regionReport.model.RegionPOSReportModel;
import com.mobinil.sds.core.system.request.model.SupervisorModel;
import com.mobinil.sds.core.system.sa.persons.model.PersonModel;
import com.mobinil.sds.core.system.scm.model.DCMUserLevelTypeModel;
import com.mobinil.sds.core.system.scm.model.POSGroupModel;
import com.mobinil.sds.core.system.scm.model.RepExcelModel;
import com.mobinil.sds.core.system.scm.model.RepPOSGroupModel;
import com.mobinil.sds.core.system.scm.model.RepSupervisorModel;
import com.mobinil.sds.core.system.scm.model.RepTeamleaderModel;
import com.mobinil.sds.core.system.scm.model.SupervisorExcelModel;
import com.mobinil.sds.core.system.scm.model.SupervisorRepsModel;
import com.mobinil.sds.core.system.scm.model.SupervisorTeamleadersModel;
import com.mobinil.sds.core.system.scm.model.TeamleaderExcelModel;
import com.mobinil.sds.core.system.scm.model.TeamleaderRepsModel;
import com.mobinil.sds.core.system.scm.model.TeamleaderSupervisorsModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author AHMED SAFWAT
 */
public class RepManagementDAO {

    
public static Vector<RegionModel> getSubRegions(Connection con, String regionId){
        
        Vector<RegionModel> regions=new Vector();
        String sqlStatement;
        sqlStatement="select region_id,region_name,region_level_type_id,parent_region_id from dcm_region where region_id="+regionId;
        regions=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForSubRegion", con);
        return regions;
    }
    
    
    
    public static Vector<RegionModel> getRegions(Connection con){
        
        Vector<RegionModel> regions=new Vector();
        String sqlStatement;
        //sqlStatement="SELECT REGION_ID,REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'region' ) ORDER BY REGION_NAME ASC";
        sqlStatement="SELECT dcm_region_level_type.REGION_LEVEL_TYPE_NAME,dcm_region.region_level_type_id, REGION_ID,REGION_NAME FROM dcm_region_level_type,DCM_REGION WHERE dcm_region.region_level_type_id = dcm_region_level_type.region_level_type_id and REGION_STATUS_TYPE_ID=1 AND dcm_region.REGION_LEVEL_TYPE_ID=1 order by DCM_REGION.region_name";
        regions=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regions;
    }
    
    
    public static Vector<RegionModel> getRegionChildrenBylevelAndName(Connection con, String regionName, String regionLevel){
        System.out.println("getRegionChildrenBylevelAndName "+regionName+" "+regionLevel);
        Vector<RegionModel> regions=new Vector();
        String sqlStatement;
        //sqlStatement="SELECT REGION_ID,REGION_NAME FROM DCM_REGION WHERE REGION_STATUS_TYPE_ID=1 AND REGION_LEVEL_TYPE_ID=(SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_LEVEL_TYPE_NAME) LIKE 'region' ) ORDER BY REGION_NAME ASC";
        sqlStatement="SELECT dcm_region_level_type.REGION_LEVEL_TYPE_NAME,dcm_region.region_level_type_id, REGION_ID,REGION_NAME FROM dcm_region_level_type,DCM_REGION WHERE dcm_region.region_level_type_id = dcm_region_level_type.region_level_type_id and REGION_STATUS_TYPE_ID=1 AND parent_region_id in (select region_id from dcm_region where region_name='"+regionName+"' and region_level_type_id="+regionLevel+") order by DCM_REGION.region_name";
        System.out.println("getRegionChildrenBylevelAndName sql"+sqlStatement);
        regions=DBUtil.executeSqlQueryMultiValue(sqlStatement, RegionModel.class, "fillForRepManagementSearch", con);
        return regions;
    }
    

    public static Vector<DCMUserLevelTypeModel>getUserLevelsForSupervisorAndRep(Connection con){

        Vector<DCMUserLevelTypeModel> repAndSupervisorLevels=new Vector();
        String sqlStatement;
        sqlStatement="SELECT USER_LEVEL_TYPE_ID,USER_LEVEL_TYPE_NAME FROM DCM_USER_LEVEL_TYPE WHERE USER_LEVEL_TYPE_ID=4 OR USER_LEVEL_TYPE_ID=5 OR USER_LEVEL_TYPE_ID=6";
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
            if(levelTypeId!=null||name!=null)
                sqlSearch+=" AND";
            
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
        
        /*sqlStatement="SELECT * from"
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
            +"		AND DCM_USER.USER_LEVEL_TYPE_ID IN(3,4,5,6) "
            +"	) x"
            +" "+sqlSearch +"   "
            +" ) WHERE row_num > = ('"+rowNum+"'*20)+1 AND row_num < = ('"+rowNum+"'+1)*20 ORDER BY LOWER(USER_FULL_NAME) ";*/
//ROWNUM
        sqlStatement="SELECT * from"
            +"("
            +"SELECT x.*, ROWNUM as row_num FROM "
            +"	(SELECT \n" +
"      scm_user_region.USER_ID ,\n" +
"      scm_user_region.USER_LEVEL_TYPE_ID,\n" +
"      DCM_USER_DETAIL.USER_FULL_NAME,\n" +
"      scm_user_region.REGION_ID,\n" +
"      DCM_REGION.REGION_NAME,\n" +
"      DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_NAME,\n" +
"      DCM_USER_DETAIL.CREATION_TIMESTAMP\n" +
"    FROM \n" +
"      DCM_USER_DETAIL,\n" +
"      DCM_REGION,\n" +
"      DCM_USER_LEVEL_TYPE,\n" +
"      scm_user_region\n" +
"    WHERE --DCM_USER.DCM_USER_ID       =DCM_USER_DETAIL.USER_ID\n" +
"    --AND DCM_USER.USER_LEVEL_TYPE_ID  =DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_ID\n" +
"    scm_user_region.USER_LEVEL_TYPE_ID  =DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_ID\n" +
"    --AND DCM_USER.USER_STATUS_TYPE_ID =1\n" +
"    --AND DCM_USER.REGION_ID           =DCM_REGION.REGION_ID\n" +
"    AND scm_user_region.REGION_ID           =DCM_REGION.REGION_ID\n" +
"    AND    scm_user_region.USER_ID         =DCM_USER_DETAIL.USER_ID\n" +
"    AND    scm_user_region.region_level_type_id         in (4,6)\n" +
"    /*AND scm_user_region.USER_LEVEL_TYPE_ID IN(4,5,6)*/) x"
            +" "+sqlSearch +"   "
 +" ) WHERE row_num > = ('"+rowNum+"'*20)+1 AND row_num < = ('"+rowNum+"'+1)*20 ORDER BY LOWER(USER_FULL_NAME) ";           
// +" ) ORDER BY LOWER(USER_FULL_NAME) ";
        System.out.println("Search Rep Query : "+sqlStatement);
        dcmUser= DBUtil.executeSqlQueryMultiValue(sqlStatement, DCMUserModel.class, "fillForRepManagementSearch", con);

        return dcmUser;
    }

    
    public static Vector getAllRepsData(Connection con) {
        
        Vector vec = new Vector();
        System.out.println("getAllRepsData ");
        
              
        try {
            Statement stat = con.createStatement();
            //String strSql1 = "select * from vw_salesrep_manager_assign order by rep_name";
            //String strSql2 = "select * from vw_salesrep_manager_notexist order by rep_name";
            
            //String strSql = "select * from scm_salesrep order by salesrep_name";
            String strSql = "SELECT scm_salesrep.*,\n" +
"   dcm_region.region_name\n" +
"  -- scm_teamleader.teamleader_name,\n" +
"  -- scm_teamleader.email as teamleader_email,\n" +
"  -- scm_teamleader.mobile as teamleader_mobile,\n" +
"  -- scm_supervisor.supervisor_name,\n" +
" --  scm_supervisor.email as supervisor_email,\n" +
" --  scm_supervisor.mobile as supervisor_mobile\n" +
" FROM --scm_supervisor,\n" +
" scm_salesrep,\n" +
"-- scm_teamleader,\n" +
"   scm_user_region,\n" +
"   dcm_region\n" +
" WHERE scm_salesrep.salesrep_id    = scm_user_region.user_id\n" +
" AND scm_user_region.user_level_type_id=6\n" +
" AND scm_user_region.region_id         = dcm_region.region_id\n" +
" --AND scm_salesrep.teamlead_id = scm_teamleader.teamleader_id\n" +
" --AND scm_teamleader.sup_id = scm_supervisor.supervisor_id";
                    /*"SELECT scm_salesrep.*,\n" +
            "  dcm_region.region_name,\n" +
            "  scm_teamleader.teamleader_name,\n" +
            "  scm_teamleader.email as teamleader_email,\n" +
            "  scm_teamleader.mobile as teamleader_mobile,\n" +
            "  scm_supervisor.supervisor_name,\n" +
            "  scm_supervisor.email as supervisor_email,\n" +
            "  scm_supervisor.mobile as supervisor_mobile\n" +
            "FROM scm_supervisor,\n" +
            "scm_salesrep,\n" +
            "scm_teamleader,\n" +
            "  scm_user_region,\n" +
            "  dcm_region\n" +
            "WHERE scm_salesrep.salesrep_id    = scm_user_region.user_id\n" +
            "AND scm_user_region.user_level_type_id=6\n" +
            "AND scm_user_region.region_id         = dcm_region.region_id\n" +
            "AND scm_salesrep.teamlead_id = scm_teamleader.teamleader_id\n" +
            "AND scm_teamleader.sup_id = scm_supervisor.supervisor_id"*/;
            System.out.println("getAllRepsData - sql "+strSql);
            ResultSet res1 = stat.executeQuery(strSql);
            while (res1.next()) {
               
                vec.add(new RepExcelModel(res1,true));
                }
            res1.close();
            
            /*ResultSet res2 = stat.executeQuery(strSql2);
            while (res2.next()) {
               
                vec.add(new RepExcelModel(res2,false));
                }
            res2.close();*/
 
     
     
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    public static Vector getAllRepsSearchData(Connection con,Vector<DCMUserModel> RepResults) {
        
     
        System.out.println("getAllRepsSearchData ");
        Vector vec = new Vector();
        
              
        try {
            Statement stat = con.createStatement();
            System.out.println("RepResults.size() "+RepResults.size());
            for (int i=0;i<RepResults.size();i++) {
               
                System.out.println("RepResults.get(i) "+RepResults.get(i).getDcmUserId());
                DCMUserModel rep = RepResults.get(i);
                String strSql="SELECT scm_salesrep.*,\n" +
                "  dcm_region.region_name\n" +
                "  --scm_teamleader.teamleader_name,\n" +
                "  --scm_teamleader.email as teamleader_email,\n" +
                "  --scm_teamleader.mobile as teamleader_mobile,\n" +
                "  --scm_supervisor.supervisor_name,\n" +
                "  --scm_supervisor.email as supervisor_email,\n" +
                "  --scm_supervisor.mobile as supervisor_mobile\n" +
                "FROM --scm_supervisor,\n" +
                "scm_salesrep,\n" +
                "--scm_teamleader,\n" +
                "  scm_user_region,\n" +
                "  dcm_region\n" +
                "WHERE scm_salesrep.salesrep_id    = scm_user_region.user_id\n" +
                "AND scm_user_region.user_level_type_id=6\n" +
                "AND scm_user_region.region_id         = dcm_region.region_id\n" +
                "--AND scm_salesrep.teamlead_id = scm_teamleader.teamleader_id\n" +
                "--AND scm_teamleader.sup_id = scm_supervisor.supervisor_id\n" +
                "AND scm_salesrep.salesrep_id = '"+rep.getDcmUserId()+"'";
                System.out.println(strSql);
                /*if(i==0)
                {
                    ResultSet res1 = stat.executeQuery(strSql);
                    while (res1.next()) {

                        vec.add(new RepExcelModel(res1,true));
                        }
                    res1.close();
                }
                if(i!=0 && RepResults.get(i).getDcmUserId().compareTo(RepResults.get(i-1).getDcmUserId())!=0)
                {
                    ResultSet res1 = stat.executeQuery(strSql);
                    while (res1.next()) {

                        vec.add(new RepExcelModel(res1,true));
                        }
                    res1.close();
                }*/
                ResultSet res1 = stat.executeQuery(strSql);
                    while (res1.next()) {

                        vec.add(new RepExcelModel(res1,true));
                        }
                    res1.close();
            }
            
           
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    public static Vector getAllSupervisorsSearchData(Connection con,String supervisorId) {
        
     
        System.out.println("getAllSupervisorsSearchData ");
        Vector vec = new Vector();
        
              
        try {
            Statement stat = con.createStatement();
         
            //for (int i=0;i<SupervisorResults.size();i++) {
               
               
                //DCMUserModel supervisor = SupervisorResults.get(i);
                //String strSql1 = "select * from scm_supervisor where supervisor_id = '"+supervisor.getDcmUserId()+"' ";
                String strSql1= "select scm_supervisor.*, dcm_region.region_name from scm_supervisor, scm_user_region, dcm_region where scm_supervisor.supervisor_id = scm_user_region.user_id and scm_user_region.user_level_type_id=4 and scm_user_region.region_id = dcm_region.region_id and scm_supervisor.supervisor_id='"+supervisorId+"' ";
                System.out.println(strSql1);

                ResultSet res1 = stat.executeQuery(strSql1);
                while (res1.next()) {

                    vec.add(new SupervisorExcelModel(res1, true));
                    }
                res1.close();

               // }
            
           
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    
    
    public static Vector getAllTeamleadersSearchData(Connection con,String teamleaderId) {
        
     
        System.out.println("getAllTeamleadersSearchData ");
        Vector vec = new Vector();
        
              
        try {
            Statement stat = con.createStatement();
            
            //for (int i=0;i<TeamleaderResults.size();i++) {
               
                System.out.println("TeamleaderResults.get(i) "+teamleaderId);
                //DCMUserModel teamleader = TeamleaderResults.get(i);
                //String strSql1 = "select * from scm_teamleader where teamleader_id = '"+teamleader.getDcmUserId()+"' ";
                String strSql1= "select scm_teamleader.*, dcm_region.region_name from scm_teamleader, scm_user_region, dcm_region where scm_teamleader.teamleader_id = scm_user_region.user_id and scm_user_region.user_level_type_id=5 and scm_user_region.region_id = dcm_region.region_id and scm_teamleader.teamleader_id='"+teamleaderId+"' ";
                System.out.println(strSql1);
                
                ResultSet res1 = stat.executeQuery(strSql1);
                    while (res1.next()) {

                        vec.add(new TeamleaderExcelModel(res1, true));
                        }
                    res1.close();
            //}
            
           
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    
     public static Vector<com.mobinil.sds.core.system.scm.model.SupervisorModel> getSupervisors(Connection con){

         Vector vec = new Vector();
        System.out.println("getAllSupervisorsData ");
        
              
        try {
            Statement stat = con.createStatement();
            String strSql1 = "select * from scm_supervisor order by supervisor_name";
            System.out.println("get supervisors : "+strSql1);
            
            ResultSet res1 = stat.executeQuery(strSql1);
            while (res1.next()) {
               
                vec.add(new com.mobinil.sds.core.system.scm.model.SupervisorModel(res1,false));
                }
            res1.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
     
     public static Vector<com.mobinil.sds.core.system.scm.model.TeamleaderModel> getTeamleaders(Connection con){

         Vector vec = new Vector();
        System.out.println("getAllTeamleadersData ");
        
              
        try {
            Statement stat = con.createStatement();
            String strSql1 = "select * from scm_teamleader order by teamleader_name";
            System.out.println("get teamleaders : "+strSql1);
            
            ResultSet res1 = stat.executeQuery(strSql1);
            while (res1.next()) {
               
                vec.add(new com.mobinil.sds.core.system.scm.model.TeamleaderModel(res1,false));
                }
            res1.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    public static Vector<com.mobinil.sds.core.system.scm.model.RepModel> getReps(Connection con){

         Vector vec = new Vector();
        System.out.println("getReps ");
        
              
        try {
            Statement stat = con.createStatement();
            String strSql1 = "select * from scm_salesrep order by salesrep_name";
            System.out.println("get reps : "+strSql1);
            
            ResultSet res1 = stat.executeQuery(strSql1);
            while (res1.next()) {
               
                vec.add(new com.mobinil.sds.core.system.scm.model.RepModel(res1,false));
                }
            res1.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    public static Vector getAllSupervisorsData(Connection con) {
        
        Vector vec = new Vector();
        System.out.println("getAllSupervisorsData ");
        
              
        try {
            Statement stat = con.createStatement();
            String strSql1 = "SELECT scm_supervisor.*,\n" +
"   dcm_region.region_name\n" +
" FROM scm_supervisor,\n" +
"   scm_user_region,\n" +
"   dcm_region\n" +
" WHERE scm_supervisor.supervisor_id    = scm_user_region.user_id\n" +
" AND scm_user_region.user_level_type_id=4\n" +
" AND scm_user_region.region_id         = dcm_region.region_id\n";
//"select * from scm_supervisor order by supervisor_name";
            System.out.println("get supervisors : "+strSql1);
            
            ResultSet res1 = stat.executeQuery(strSql1);
            while (res1.next()) {
               
                vec.add(new SupervisorExcelModel(res1,true));
                }
            res1.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    
    
    public static Vector getUserRegionDataAll(Connection con, String userLevelTypeId, String userFullName, String regionId) {
        
        Vector vec = new Vector();
        int level=-1;
        boolean isDist=false;
        boolean isImgDist=false;
        String checkRegionLevel ="";
        try {
            Statement stat = con.createStatement();
            if(userLevelTypeId!=null && userLevelTypeId.compareTo("")!=0)
                checkRegionLevel ="select region_level_type_id from scm_user_region where user_id in (select dcm_user_id from dcm_user where user_level_type_id="+userLevelTypeId+" )";
            if(userLevelTypeId!=null && userLevelTypeId.compareTo("")!=0 && userFullName!=null && userFullName.compareTo("")!=0)
                checkRegionLevel ="select region_level_type_id from scm_user_region where user_id in (select dcm_user_id from dcm_user where user_level_type_id="+userLevelTypeId+" and user_detail_id in (select user_detail_id from dcm_user_detail where user_full_name= '"+userFullName+"'))";
            if(checkRegionLevel.compareTo("")!=0)
            {
                ResultSet result = stat.executeQuery(checkRegionLevel);
                while(result.next())
                {

                    level = result.getInt("region_level_type_id");
                    if(level==4)
                        isDist = true;
                    if(level==5)
                        isImgDist = true;

                }
            }
            System.out.println("isDist "+isDist+" isImgDist "+isImgDist);
            
            
            StringBuilder strSql = new StringBuilder("SELECT gen_dcm.channel_id AS channel_code,");
            strSql.append("  gen_dcm.dcm_code AS pos_code," );

            strSql.append("  gen_dcm.dcm_name  AS pos_english_name," );
            strSql.append("  dcm_pos_detail.pos_arabic_name,") ;
            strSql.append("  dcm_pos_owner.pos_owner_name,");
            strSql.append("  dcm_pos_owner.pos_owner_id_number,");

            strSql.append("  dcm_id_type.id_type_name," );
            strSql.append("  dcm_region.region_name," );
            strSql.append("  city.region_name as city_name,");
            strSql.append("  govern.region_name as govern_name," );
            strSql.append("  dcm_pos_detail.district_code,");
            if(isDist)
                strSql.append("  district.region_name as district_name,");
            if(isImgDist)
                strSql.append("  imgDist.region_name as image_district_name,");
            if(isDist && isImgDist)
                strSql.append("  district.region_name as district_name,imgDist.region_name as image_district_name, ");
            if(regionId!=null && regionId.compareTo("")!=0 && !isDist && !isImgDist)
                strSql.append("  district.region_name as district_name,imgDist.region_name as image_district_name, ");
            
            strSql.append("  dcm_pos_detail.pos_img_district_code,");
            strSql.append("  area.region_code as area_code,");
            strSql.append("  area.region_name as area_name," );
 
            
            strSql.append("  dcm_pos_detail.pos_address,");
            strSql.append("  dcm_pos_detail.pos_doc_num,");
            strSql.append("  pos_documents.posdocuments,");
            strSql.append("  pos_documents.assign_date,");
            strSql.append("  gen_dcm_status.dcm_status_name as pos_status,");
            strSql.append("  dcm_pos_owner_phone.pos_owner_phone_number," );
            strSql.append("  gen_dcm_level.dcm_level_id as pos_level_code,");
            strSql.append("  scm_supervisor.supervisor_name," );
            strSql.append("  scm_teamleader.teamleader_name,");
            strSql.append("  scm_salesrep.salesrep_name," );
            strSql.append("  pos_documents.StkDialNo," );
            strSql.append("  scm_stk_status.name as stk_status,");
            strSql.append("  pos_documents.stkactvdt as stk_activation_date," );
            strSql.append("  pos_documents.iqrarrcvdt as iqrar_received_date," );
            strSql.append("  CAM_PAYMENT_cam_state.cam_status_for_payment as payment_status,");
            strSql.append("  gen_dcm_payment_level.dcm_payment_level_name as payment_level,");
            strSql.append("  dcm_pos_detail.pos_arabic_address,");
            
            strSql.append("  scm_iqrar_receving_status.name as is_iqrar_received," );
            strSql.append("  scm_verified_status.name as is_verified," );
            
            strSql.append("  dcm_pos_detail.doc_location," );
            strSql.append("  dcm_pos_detail.survey_id,");
            strSql.append("  dcm_pos_detail.is_level_one as L1,");
            strSql.append("  dcm_pos_detail.is_exclusive as Ex,");
            strSql.append("  dcm_pos_detail.has_sign as Sign," );
            strSql.append("  dcm_pos_detail.is_quality_club as Qc" );
            strSql.append(" FROM scm_stk_stock,gen_dcm," );
            strSql.append("  dcm_pos_detail,");
            strSql.append("  dcm_pos_owner," );
            strSql.append("  dcm_id_type,");
            strSql.append("  dcm_region," );
            strSql.append("  dcm_region city,");
            strSql.append("  dcm_region govern,");
            if(isDist)
                strSql.append("  dcm_region district," );
            if(isImgDist)
                strSql.append("  dcm_region imgDist," );
            if(isDist && isImgDist)
                strSql.append("  dcm_region district, dcm_region imgDist, " );
            if(regionId!=null && regionId.compareTo("")!=0 && !isDist && !isImgDist)
                strSql.append("  dcm_region district, dcm_region imgDist, " );
            strSql.append("  dcm_region area," );
            strSql.append("  pos_documents," );
            strSql.append("  gen_dcm_status,");
            strSql.append("  dcm_pos_owner_phone," );
            strSql.append("  gen_dcm_level,");
            strSql.append("  scm_supervisor,");
            strSql.append("  scm_teamleader,");
            strSql.append("  scm_salesrep," );
            strSql.append("  scm_stk_status,");
            strSql.append("  CAM_PAYMENT_SCM_STATUS," );
            strSql.append("  CAM_PAYMENT_cam_state,");
            strSql.append("  gen_dcm_payment_level,scm_user_region, " );
            
            strSql.append("  scm_stk_owner,");
            strSql.append("  scm_iqrar_receving_status,");
            strSql.append("  scm_verified_status");
            
            strSql.append(" WHERE gen_dcm.dcm_id            = dcm_pos_detail.pos_id" );
            strSql.append(" AND gen_dcm.dcm_code            = dcm_pos_detail.pos_code" );
            strSql.append(" AND dcm_pos_owner.pos_detail_id = dcm_pos_detail.pos_detail_id" );
            strSql.append(" AND dcm_pos_owner.pos_owner_id_type_id = dcm_id_type.id_type_id");
            strSql.append(" AND dcm_region.region_id = dcm_pos_detail.region_id");
            
            strSql.append(" AND city.region_id = dcm_pos_detail.pos_city_id");
            
            strSql.append(" AND govern.region_id = dcm_pos_detail.pos_governrate");
            
            
            
            if(isDist)
            {
                strSql.append(" AND district.region_id = dcm_pos_detail.pos_district_id");
                strSql.append(" and scm_user_region.region_id = district.region_id and scm_user_region.region_level_type_id=4 ");
            }
            if(isImgDist)
            {
                strSql.append(" AND imgDist.region_id = dcm_pos_detail.pos_img_district_id");
                strSql.append(" and scm_user_region.region_id = imgDist.region_id and scm_user_region.region_level_type_id=6 ");
            }
            if(isDist && isImgDist || (regionId!=null && regionId.compareTo("")!=0 && !isDist && !isImgDist) )
            {
                strSql.append(" AND district.region_id = dcm_pos_detail.pos_district_id");
                strSql.append(" AND imgDist.region_id = dcm_pos_detail.pos_img_district_id");
                strSql.append(" and scm_user_region.region_level_type_id in (4,6) and district.region_id = scm_user_region.region_id and imgDist.region_id = scm_user_region.region_id ");
            }
            strSql.append(" AND area.region_id = dcm_pos_detail.pos_area_id");
            
            strSql.append(" AND pos_documents.code = dcm_pos_detail.pos_code");
            strSql.append(" AND gen_dcm_status.dcm_status_id = dcm_pos_detail.pos_status_type_id");
            strSql.append(" AND dcm_pos_owner.pos_owner_id = dcm_pos_owner_phone.pos_owner_id  (+)");
            strSql.append(" AND gen_dcm_level.dcm_level_id = dcm_pos_detail.DCM_LEVEL_ID" );

            strSql.append(" AND scm_supervisor.supervisor_id = dcm_pos_detail.supervisor_id");
            strSql.append(" AND scm_teamleader.teamleader_id = dcm_pos_detail.teamleader_id");
            strSql.append(" AND scm_salesrep.salesrep_id = dcm_pos_detail.salesrep_id");
            if(userLevelTypeId!=null && userLevelTypeId.compareTo("4")==0)
            {
                strSql.append(" and scm_user_region.user_id = scm_supervisor.supervisor_id and scm_user_region.user_level_type_id=4");
                 if(userFullName!=null && userFullName.compareTo("")!=0)
                    strSql.append(" AND scm_supervisor.supervisor_name = '"+userFullName+"'");
            }
            if(userLevelTypeId!=null && userLevelTypeId.compareTo("5")==0)
            {
                strSql.append(" and scm_user_region.user_id = scm_teamleader.teamleader_id and scm_user_region.user_level_type_id=5");
                if(userFullName!=null && userFullName.compareTo("")!=0)
                    strSql.append(" AND scm_teamleader.teamleader_name = '"+userFullName+"'");
            }
                
            if(userLevelTypeId!=null && userLevelTypeId.compareTo("6")==0)
            {
                strSql.append(" and scm_user_region.user_id = scm_salesrep.salesrep_id and scm_user_region.user_level_type_id=6");
                if(userFullName!=null && userFullName.compareTo("")!=0)
                    strSql.append(" AND scm_salesrep.salesrep_name = '"+userFullName+"'");
            }
            if(regionId!=null && regionId.compareTo("")!=0)
                strSql.append(" AND dcm_region.region_id = '"+regionId+"' and dcm_region.region_level_type_id=1");
            
            
            //strSql.append(" AND scm_stk_status.stk_status_id = CAM_PAYMENT_SCM_STATUS.stk_status");
            
            
            strSql.append(" AND scm_stk_status.stk_status_id = scm_stk_owner.stk_status_id");
            strSql.append(" AND scm_stk_owner.stk_id = scm_stk_stock.stk_id");
            strSql.append(" AND scm_stk_owner.dcm_id = CAM_PAYMENT_SCM_STATUS.scm_id");
            
            strSql.append(" AND CAM_PAYMENT_SCM_STATUS.scm_id = gen_dcm.dcm_id");
            strSql.append(" AND CAM_PAYMENT_cam_state.id = CAM_PAYMENT_SCM_STATUS.PAYMENT_cam_state_id" );
            strSql.append(" AND gen_dcm_payment_level.dcm_payment_level_id = dcm_pos_detail.dcm_payment_level_id " );
            
            strSql.append(" AND scm_iqrar_receving_status.iqrar_receving_status_id = scm_stk_owner.iqrar_receving_status_id");
            strSql.append(" AND scm_stk_owner.dcm_id = dcm_pos_detail.pos_id ");
            strSql.append(" AND scm_verified_status.dcm_verified_status_id = scm_stk_owner.dcm_verified_status_id");
            
            strSql.append(" AND dcm_pos_detail.flage       IS NULL order by dcm_pos_detail.pos_code");
           
            
            System.out.println("SQL ^^^ : \n"+ strSql);
            ResultSet res = stat.executeQuery(strSql.toString());
            while (res.next()) {
               if(isImgDist)
                    vec.add(new RegionPOSReportModel(res,"",true));
               else
                   vec.add(new RegionPOSReportModel(res,"",false));
               
              /* if(isDist)
                    vec.add(new RegionPOSReportModel(res,"4"));
               if(isImgDist)
                    vec.add(new RegionPOSReportModel(res,"6"));
               if(isDist && isImgDist)
                    vec.add(new RegionPOSReportModel(res,"4&6"));*/
                }
            res.close();
            //stat2.close();
           // stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
    }
    
    
    
    
    
    public static Vector getAllTeamleadersData(Connection con) {
        
        Vector vec = new Vector();
        System.out.println("getAllTeamleadersData ");
        
              
        try {
            Statement stat = con.createStatement();
            String strSql1 = "SELECT scm_teamleader.*,\n" +
"   dcm_region.region_name\n" +
" FROM " +
" scm_teamleader,\n" +
"   scm_user_region,\n" +
"   dcm_region\n" +
" WHERE scm_teamleader.teamleader_id    = scm_user_region.user_id\n" +
" AND scm_user_region.user_level_type_id=5\n" +
" AND scm_user_region.region_id         = dcm_region.region_id\n" ;//"select * from scm_teamleader order by teamleader_name";
            
            ResultSet res1 = stat.executeQuery(strSql1);
            while (res1.next()) {
               
                vec.add(new TeamleaderExcelModel(res1,true));
                }
            res1.close();
            
            stat.close();
           // con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return vec;
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
            +" FROM  scm_user_region, DCM_USER,DCM_USER_DETAIL,DCM_REGION,DCM_USER_LEVEL_TYPE"
            +" WHERE DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
            +" DCM_USER.USER_LEVEL_TYPE_ID=DCM_USER_LEVEL_TYPE.USER_LEVEL_TYPE_ID"
            +" AND DCM_USER.USER_STATUS_TYPE_ID=1 /*AND DCM_USER.REGION_ID=DCM_REGION.REGION_ID*/ "+sqlStringSearch
            +" and scm_user_region.region_id = dcm_region.region_id\n" +
"  and scm_user_region.user_id = DCM_USER.DCM_USER_ID\n" +
"  ) ";

        System.out.println("sql : "+sqlStatement);
        String count="0";
        count=DBUtil.executeQuerySingleValueString(sqlStatement, "COUNT", con);
        return count;
    }

    public static void addNewRepOrSupervisor(Connection con,String userRegionLevelTypeId,DCMUserModel repOrSupervisor,DCMUserDetailModel userDetail,String userId){
        long dcmUserDetailId=DBUtil.getSequenceNextVal(con, "SEQ_DCM_USER_DETAIL_ID");
        long dcmUserId=DBUtil.getSequenceNextVal(con, "SEQ_DCM_USER_ID");
        System.out.println("addNewRepOrSupervisor - detail id : "+dcmUserDetailId+" and id (rep/supervisor/teamleader) : "+dcmUserId+" and system login id : "+userId);
        
        repOrSupervisor.setDcmUserId(Long.toString(dcmUserId));
        repOrSupervisor.setUserDetailId(Long.toString(dcmUserDetailId));
        repOrSupervisor.setUserId(userId);
        userDetail.setUserId(Integer.valueOf(Long.toString(dcmUserId)));
            
        String insertDCMUserSqlStatement="insert into DCM_USER (DCM_USER_ID, USER_ID, MANAGER_DCM_USER_ID, USER_LEVEL_TYPE_ID, USER_DETAIL_ID, USER_STATUS_TYPE_ID, /*REGION_ID,*/ USER_LEVEL_ID) " +
                "values (?, ?, NULL, ?, ?, 1, 0)";
        String insertDCMUserDeatilSqlStatement="insert into SDS.DCM_USER_DETAIL " +
                    "(USER_DETAIL_ID, USER_ID, USER_FULL_NAME, USER_ADDRESS, USER_EMAIL, USER_MOBILE, REGION_ID, USER_DETAIL_STATUS_ID, CREATION_TIMESTAMP, CREATION_USER_ID) " +
                    "values (?, ?, ?, ?, ?, ?, ?, 1, systimestamp, ?)";
        String insertScmUserRegionSqlStatement="insert into scm_user_region " +
                    "(region_id, USER_ID, USER_LEVEL_TYPE_ID,region_LEVEL_TYPE_ID) " +
                    "values (?, ?, ?, ?)";    
            //DBUtil.executePreparedStatment(insertDCMUserSqlStatement, con, new Object[]{dcmUserId,repOrSupervisor.getUserId(),Integer.parseInt(repOrSupervisor.getUserLevelTypeId()),dcmUserDetailId,Integer.parseInt(repOrSupervisor.getRegionId())});
        DBUtil.executePreparedStatment(insertDCMUserSqlStatement, con, new Object[]{dcmUserId,/*repOrSupervisor.getUserId()*/userId,Integer.parseInt(repOrSupervisor.getUserLevelTypeId()),dcmUserDetailId/*,Integer.parseInt(repOrSupervisor.getRegionId())*/});
        DBUtil.executePreparedStatment(insertDCMUserDeatilSqlStatement,con,new Object[]{dcmUserDetailId,dcmUserId,userDetail.getUserFullName(),userDetail.getUserAddress(),userDetail.getUserEmail(),userDetail.getUserMobile(),repOrSupervisor.getRegionId(),userId});
        DBUtil.executePreparedStatment(insertScmUserRegionSqlStatement,con,new Object[]{Integer.parseInt(repOrSupervisor.getRegionId()),dcmUserId,Integer.parseInt(repOrSupervisor.getUserLevelTypeId()),userRegionLevelTypeId});
            
        

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
        System.out.println("getDcmUser sqlStatement : "+sqlStatement);
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

    public static void updateRepOrSupervisor(Connection con,String userRegionLevelTypeId,DCMUserModel dcmUser,DCMUserDetailModel dcmUserDetail,String userId){
        //String updateDCMUserSqlStatement="UPDATE DCM_USER SET USER_LEVEL_TYPE_ID=? , REGION_ID=? WHERE DCM_USER_ID=?";
        String updateDCMUserSqlStatement="UPDATE DCM_USER SET USER_LEVEL_TYPE_ID=? , REGION_ID=?, USER_ID=? WHERE DCM_USER_ID=?";
        String updateDCMUserDetailSqlStatement="UPDATE DCM_USER_DETAIL SET USER_FULL_NAME=?, USER_ADDRESS=?, USER_EMAIL=?, USER_MOBILE=?, " +
                "REGION_ID=? WHERE USER_DETAIL_ID=?";
        String updateScmUserRegionSqlStatement="update scm_user_region " +
                    " set REGION_LEVEL_TYPE_ID=?, region_id=?, USER_LEVEL_TYPE_ID=? where user_id=? "; 
        
        DBUtil.executePreparedStatment(updateDCMUserSqlStatement, con, new Object[]{dcmUser.getUserLevelTypeId(),dcmUser.getRegionId(),dcmUser.getUserId(),dcmUser.getDcmUserId()});
        DBUtil.executePreparedStatment(updateDCMUserDetailSqlStatement,con,new Object[]{dcmUserDetail.getUserFullName(),dcmUserDetail.getUserAddress(),dcmUserDetail.getUserEmail(),dcmUserDetail.getUserMobile(),dcmUser.getRegionId(),dcmUser.getUserDetailId()});
        DBUtil.executePreparedStatment(updateScmUserRegionSqlStatement, con, new Object[]{userRegionLevelTypeId,dcmUser.getRegionId(),Integer.parseInt(dcmUser.getUserLevelTypeId()),dcmUser.getDcmUserId()});
    }
    
    
    public static void updateSupervisor(Connection con,DCMUserModel dcmUser,DCMUserDetailModel dcmUserDetail){
        
        String updateSupervisorSqlStatement="UPDATE scm_supervisor SET supervisor_name=? , email=?, mobile=? ,creation_timestamp=systimestamp WHERE supervisor_id=?";
        
        DBUtil.executePreparedStatment(updateSupervisorSqlStatement, con, new Object[]{dcmUserDetail.getUserFullName(),dcmUserDetail.getUserEmail(),dcmUserDetail.getUserMobile(),dcmUser.getDcmUserId()});
        
    }
    
    public static void updateTeamleader(Connection con,DCMUserModel dcmUser,DCMUserDetailModel dcmUserDetail){
        
        String updateSupervisorSqlStatement="UPDATE scm_teamleader SET teamleader_name=? , email=?, mobile=? ,creation_timestamp=systimestamp WHERE teamleader_id=?";
        
        DBUtil.executePreparedStatment(updateSupervisorSqlStatement, con, new Object[]{dcmUserDetail.getUserFullName(),dcmUserDetail.getUserEmail(),dcmUserDetail.getUserMobile(),dcmUser.getDcmUserId()});
        
    }
    
    
    
    public static void updateUsersForARegion(Connection con,String regionId,String supervisorId,String teamleaderId,String repId, String regionLevelTypeId){
        
        try{
        String sqlFindRegionSupervisor = "select * from scm_user_region where region_level_type_id="+regionLevelTypeId+" and user_level_type_id=4 /*and user_id= "+supervisorId+"*/ and region_id = "+regionId;
        String sqlFindRegionTeamleader = "select * from scm_user_region where region_level_type_id="+regionLevelTypeId+" and user_level_type_id=5 /*and user_id= "+teamleaderId+"*/ and region_id = "+regionId;
        String sqlFindRegionRep = "select * from scm_user_region where region_level_type_id="+regionLevelTypeId+" and user_level_type_id=6 /*and user_id= "+repId+"*/ and region_id = "+regionId;
        boolean supervisorExists = false;
        boolean teamleaderExists = false;
        boolean repExists = false;
        Statement stmt = con.createStatement();
        System.out.println("sqlFindRegionSupervisor "+sqlFindRegionSupervisor);
        System.out.println("sqlFindRegionTeamleader "+sqlFindRegionTeamleader);
        System.out.println("sqlFindRegionRep "+sqlFindRegionRep);
        
        ResultSet rs1 = stmt.executeQuery(sqlFindRegionSupervisor);
        if (rs1.next())
            supervisorExists = true;
            
        
        if(supervisorExists)
        {
            String sqlString3 = "update scm_user_region set user_id= "+supervisorId+" where region_level_type_id="+regionLevelTypeId+" and user_level_type_id=4 and region_id = "+regionId;
            System.out.println("rs1 true "+sqlString3);
            //DBUtil.executeSQL(sqlString3, con);
            stmt.executeUpdate(sqlString3);
        }
            
        else{
            String sqlString3 = "insert into scm_user_region values ("+regionId+", "+supervisorId+" , 4 , "+regionLevelTypeId+")";//4
            //DBUtil.executeSQL(sqlString3, con);
            System.out.println("rs1 false "+sqlString3);
            stmt.executeUpdate(sqlString3);
        }
        
        ResultSet rs2 = stmt.executeQuery(sqlFindRegionTeamleader);
        if (rs2.next())
            teamleaderExists=true;
            
        
        if(teamleaderExists)
        {
            String sqlString4 = "update scm_user_region set user_id= "+teamleaderId+" where region_level_type_id="+regionLevelTypeId+" and user_level_type_id=5 and region_id = "+regionId;
            //DBUtil.executeSQL(sqlString4, con);
            System.out.println("rs2 true "+sqlString4);
            stmt.executeUpdate(sqlString4);
        }
        else{
            String sqlString4 = "insert into scm_user_region values ("+regionId+", "+teamleaderId+" , 5 , "+regionLevelTypeId+")";//4
            //DBUtil.executeSQL(sqlString4, con);
            System.out.println("rs2 false "+sqlString4);
            stmt.executeUpdate(sqlString4);
        }
        
        
        ResultSet rs3 = stmt.executeQuery(sqlFindRegionRep);
        if (rs3.next())
            repExists=true;
            
        
        if(repExists)
        {
            String sqlString5 = "update scm_user_region set user_id= "+repId+" where region_level_type_id="+regionLevelTypeId+" and user_level_type_id=6 and region_id = "+regionId;
            //DBUtil.executeSQL(sqlString5, con);
            System.out.println("rs3 true "+sqlString5);
            stmt.executeUpdate(sqlString5);
        }
        else{
            String sqlString5 = "insert into scm_user_region values ("+regionId+", "+repId+" , 6 , "+regionLevelTypeId+")";//4
            //DBUtil.executeSQL(sqlString5, con);
            System.out.println("rs3 false "+sqlString5);
            stmt.executeUpdate(sqlString5);
        }
        
        
        stmt.close();
        }catch(SQLException e){System.out.println("sql exception "+e);}
        
        String sqlString="UPDATE scm_teamleader set sup_id = "+supervisorId+" where teamleader_id = "+teamleaderId;
        String sqlString2="UPDATE scm_salesrep set sup_id = "+supervisorId+" , teamlead_id ="+teamleaderId+" where salesrep_id = "+repId;
        DBUtil.executeSQL(sqlString, con);
        DBUtil.executeSQL(sqlString2, con);    
        
    }
    
    
    public static void updateSalesRep(Connection con,DCMUserModel dcmUser,DCMUserDetailModel dcmUserDetail){
        
        System.out.println("dcmUserDetail.getUserFullName() "+dcmUserDetail.getUserFullName());
        String updateSupervisorSqlStatement="UPDATE scm_salesrep SET salesrep_name=? , email=?, mobile=? ,creation_timestamp=systimestamp WHERE salesrep_id=?";
        
        DBUtil.executePreparedStatment(updateSupervisorSqlStatement, con, new Object[]{dcmUserDetail.getUserFullName(),dcmUserDetail.getUserEmail(),dcmUserDetail.getUserMobile(),dcmUser.getDcmUserId()});
        
    }
    

    public static void deleteRepOrSupervisor(Connection con, String dcmUserId,String userLevelTypeId){
            String deleteDCMUserSqlStatement="UPDATE DCM_USER SET USER_STATUS_TYPE_ID=3 WHERE DCM_USER_ID="+dcmUserId;
            String deleteScmUserRegionSqlStatement="UPDATE scm_user_region SET user_id=-1 WHERE USER_ID="+dcmUserId;

        /*if(userLevelTypeId.equals("3")){
        String deleteAssignedPOSGroups="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+dcmUserId;
        String deleteAssignedSups="DELETE FROM SCM_REP_SUPERVISORS WHERE REP_ID="+dcmUserId; 
        DBUtil.executeSQL(deleteAssignedPOSGroups);
        DBUtil.executeSQL(deleteAssignedSups);        
        }*/
        if(userLevelTypeId.equals("6")){
        String deleteAssignedPOSGroups="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+dcmUserId;
        String deleteAssignedReps="DELETE FROM SCM_salesrep WHERE salesrep_ID="+dcmUserId; 
        DBUtil.executeSQL(deleteAssignedPOSGroups);
        DBUtil.executeSQL(deleteAssignedReps);        
        }    
        /*if(userLevelTypeId.equals("3")){
        String deleteAssignedPOSGroups="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+dcmUserId;
        String deleteAssignedSups="DELETE FROM SCM_REP_SUPERVISORS WHERE REP_ID="+dcmUserId; 
        DBUtil.executeSQL(deleteAssignedPOSGroups);
        DBUtil.executeSQL(deleteAssignedSups);        
        }*/
        if(userLevelTypeId.equals("5")){
        String deleteAssignedPOSGroups="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+dcmUserId;
        String deleteAssignedTeams="DELETE FROM SCM_teamleader WHERE teamleader_ID="+dcmUserId; 
        String deleteAssignedReps="update scm_salesrep set teamlead_id=null where teamlead_id="+dcmUserId; 
        DBUtil.executeSQL(deleteAssignedPOSGroups);
        DBUtil.executeSQL(deleteAssignedTeams);        
        DBUtil.executeSQL(deleteAssignedReps); 
        }

        /*if(userLevelTypeId.equals("4")){
                    String deleteAssignedReps="DELETE FROM SCM_REP_SUPERVISORS WHERE SUP_ID="+dcmUserId;
                    DBUtil.executeSQL(deleteAssignedReps);
        }*/
        if(userLevelTypeId.equals("4")){
        String deleteAssignedPOSGroups="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+dcmUserId;
        String deleteAssignedSups="DELETE FROM SCM_supervisor WHERE supervisor_ID="+dcmUserId; 
        String deleteAssignedTeams="update scm_teamleader set sup_id=null where sup_id="+dcmUserId; 
        String deleteAssignedReps="update scm_salesrep set sup_id=null where sup_id="+dcmUserId; 
        DBUtil.executeSQL(deleteAssignedPOSGroups);
        DBUtil.executeSQL(deleteAssignedSups);        
        DBUtil.executeSQL(deleteAssignedTeams); 
        DBUtil.executeSQL(deleteAssignedReps); 
        }

        DBUtil.executeSQL(deleteDCMUserSqlStatement, con);
        DBUtil.executeSQL(deleteScmUserRegionSqlStatement, con);
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
        String sqlStatement="";
        /*sqlStatement="SELECT DCM_USER.DCM_USER_ID, DCM_USER.USER_LEVEL_TYPE_ID, DCM_USER.REGION_ID ,DCM_USER_DETAIL.USER_FULL_NAME,DCM_USER_DETAIL.USER_ADDRESS,DCM_USER_DETAIL.USER_EMAIL,DCM_USER_DETAIL.USER_MOBILE,DCM_REGION.REGION_NAME"
                    +" FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION"
                    +" WHERE DCM_USER.USER_STATUS_TYPE_ID<>3 AND DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
                    +"  DCM_USER.USER_LEVEL_TYPE_ID="+userLevelTypeId+" AND DCM_USER.DCM_USER_ID="+dcmUserId;*/
        sqlStatement="select salesrep_id as DCM_USER_ID,salesrep_name as USER_FULL_NAME, email as USER_EMAIL, mobile as USER_MOBILE, dcm_region.region_name from dcm_region,scm_salesrep, scm_user_region where dcm_region.region_id=scm_user_region.region_id and scm_salesrep.salesrep_id = scm_user_region.user_id and scm_user_region.user_level_type_id="+userLevelTypeId+" and scm_salesrep.salesrep_id="+dcmUserId;
System.out.println("getRepSupDetail "+sqlStatement);
           dcmUserDetail= DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserDetailModel.class, "fillDetail", con);
           return dcmUserDetail;
    }
    public static DCMUserDetailModel getRepTeamleadDetail(Connection con,String userLevelTypeId,String dcmUserId){

        DCMUserDetailModel dcmUserDetail=new DCMUserDetailModel();
        String sqlStatement;
        /*sqlStatement="SELECT DCM_USER.DCM_USER_ID, DCM_USER.USER_LEVEL_TYPE_ID, DCM_USER.REGION_ID ,DCM_USER_DETAIL.USER_FULL_NAME,DCM_USER_DETAIL.USER_ADDRESS,DCM_USER_DETAIL.USER_EMAIL,DCM_USER_DETAIL.USER_MOBILE,DCM_REGION.REGION_NAME"
                    +" FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION"
                    +" WHERE DCM_USER.USER_STATUS_TYPE_ID<>3 AND DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
                    +" DCM_USER.USER_LEVEL_TYPE_ID="+userLevelTypeId+" AND DCM_USER.DCM_USER_ID="+dcmUserId;*/
sqlStatement="select teamleader_id as DCM_USER_ID,teamleader_name as USER_FULL_NAME, email as USER_EMAIL, mobile as USER_MOBILE, dcm_region.region_name from dcm_region,scm_teamleader, scm_user_region where dcm_region.region_id=scm_user_region.region_id and scm_teamleader.teamleader_id = scm_user_region.user_id and scm_user_region.user_level_type_id="+userLevelTypeId+" and scm_teamleader.teamleader_id="+dcmUserId;
           dcmUserDetail= DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserDetailModel.class, "fillDetail", con);
           return dcmUserDetail;
    }

    
    
    
    public static DCMUserDetailModel getSupDetail(Connection con,String userLevelTypeId,String dcmUserId){

        DCMUserDetailModel dcmUserDetail=new DCMUserDetailModel();
        String sqlStatement;
        /*sqlStatement="SELECT DCM_USER.DCM_USER_ID, DCM_USER.USER_LEVEL_TYPE_ID, DCM_USER.REGION_ID ,DCM_USER_DETAIL.USER_FULL_NAME,DCM_USER_DETAIL.USER_ADDRESS,DCM_USER_DETAIL.USER_EMAIL,DCM_USER_DETAIL.USER_MOBILE,DCM_REGION.REGION_NAME"
                    +" FROM  DCM_USER,DCM_USER_DETAIL,DCM_REGION"
                    +" WHERE DCM_USER.USER_STATUS_TYPE_ID<>3 AND DCM_USER.DCM_USER_ID=DCM_USER_DETAIL.USER_ID AND"
                    +" DCM_USER.USER_LEVEL_TYPE_ID="+userLevelTypeId+" AND DCM_USER.DCM_USER_ID="+dcmUserId;*/
sqlStatement="select supervisor_id as DCM_USER_ID,supervisor_name as USER_FULL_NAME, email as USER_EMAIL, mobile as USER_MOBILE, dcm_region.region_name from dcm_region,scm_supervisor, scm_user_region where dcm_region.region_id=scm_user_region.region_id and scm_supervisor.supervisor_id = scm_user_region.user_id and scm_user_region.user_level_type_id="+userLevelTypeId+" and scm_supervisor.supervisor_id="+dcmUserId;
System.out.println("getSupDetail "+sqlStatement);
dcmUserDetail= DBUtil.executeSqlQuerySingleValue(sqlStatement, DCMUserDetailModel.class, "fillDetail", con);
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
            //String sqlStatement = "select scm_teamleader.sup_id, scm_salesrep.salesrep_id as rep_id,scm_supervisor.supervisor_name as SUP_NAME,scm_salesrep.creation_timestamp as CREATED_IN, dcm_user_detail.creation_user_id as CREATED_BY from scm_salesrep, scm_teamleader , scm_supervisor, dcm_user_detail where scm_salesrep.teamlead_id is not null and scm_salesrep.teamlead_id = scm_teamleader.teamleader_id and scm_salesrep.salesrep_id = dcm_user_detail.user_id and scm_teamleader.sup_id is not null and scm_supervisor.supervisor_id = scm_teamleader.sup_id and salesrep_id="+dcmUserId; 
            String sqlStatement = "select scm_salesrep.sup_id, scm_salesrep.salesrep_id as rep_id,scm_supervisor.supervisor_name as SUP_NAME,scm_salesrep.creation_timestamp as CREATED_IN, dcm_user_detail.creation_user_id as CREATED_BY from scm_salesrep, scm_supervisor, dcm_user_detail where scm_salesrep.salesrep_id = dcm_user_detail.user_id and scm_supervisor.supervisor_id = scm_salesrep.sup_id and scm_salesrep.salesrep_id="+dcmUserId; 
            System.out.println("getRepSupervisors "+sqlStatement);
            repSupervisors=DBUtil.executeSqlQueryMultiValue(sqlStatement, RepSupervisorModel.class, con);
            return repSupervisors;
    }
        
        public static RepSupervisorModel getRepSupervisor(Connection con, String dcmUserId, String superId){

            RepSupervisorModel repSupervisor= null;
            String sqlStatement="SELECT RS.SUP_ID,RS.REP_ID,UD.USER_FULL_NAME SUP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_SUPERVISORS RS WHERE RS.SUP_ID=UD.USER_ID"
                                +" AND RS.REP_ID="+dcmUserId
                                +" AND RS.SUP_ID="+superId;
            
            repSupervisor=DBUtil.executeSqlQuerySingleValue(sqlStatement, RepSupervisorModel.class, con);
            return repSupervisor;
    }
        
        
           public static boolean checkIfRepSupervisor(Connection con, String dcmUserId, String superId){

            
            /*String sqlStatement="SELECT RS.SUP_ID,RS.REP_ID,UD.USER_FULL_NAME SUP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_SUPERVISORS RS WHERE RS.SUP_ID=UD.USER_ID"
                                +" AND RS.REP_ID="+dcmUserId
                                +" AND RS.SUP_ID="+superId;*/
            String sqlStatement="select scm_teamleader.sup_id, scm_salesrep.salesrep_id as rep_id,scm_supervisor.supervisor_name as SUP_NAME,scm_salesrep.creation_timestamp as CREATED_IN, dcm_user_detail.creation_user_id as CREATED_BY from scm_salesrep, scm_teamleader , scm_supervisor, dcm_user_detail where scm_salesrep.teamlead_id is not null and scm_salesrep.teamlead_id = scm_teamleader.teamleader_id and scm_salesrep.salesrep_id = dcm_user_detail.user_id and scm_teamleader.sup_id is not null and scm_supervisor.supervisor_id = scm_teamleader.sup_id and salesrep_id="+dcmUserId+" and scm_supervisor.supervisor_id="+superId;
            
            boolean repSuperExits = DBUtil.executeSQLExistCheck(sqlStatement, con);
            
            return repSuperExits;
    }
           
              public static boolean checkIfRepTeamleader(Connection con, String dcmUserId, String teamleadId){

            
            /*String sqlStatement="SELECT RS.TEAMLEAD_ID,RS.REP_ID,UD.USER_FULL_NAME SUP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RS WHERE RS.TEAMLEAD_ID=UD.USER_ID"
                                +" AND RS.REP_ID="+dcmUserId
                                +" AND RS.TEAMLEAD_ID="+teamleadId;*/
            String sqlStatement = "select scm_salesrep.teamlead_id, scm_salesrep.salesrep_id as rep_id,scm_teamleader.teamleader_name as TEAMLEAD_NAME,scm_salesrep.creation_timestamp as CREATED_IN, dcm_user_detail.creation_user_id as CREATED_BY from scm_salesrep, scm_teamleader , dcm_user_detail where scm_salesrep.teamlead_id is not null and scm_salesrep.teamlead_id = scm_teamleader.teamleader_id and scm_salesrep.salesrep_id = dcm_user_detail.user_id and salesrep_id="+dcmUserId+" and scm_teamleader.teamleader_id="+teamleadId;
            
            boolean repTeamleadExits = DBUtil.executeSQLExistCheck(sqlStatement, con);
            
            return repTeamleadExits;
    }
        
           public static Vector<RepTeamleaderModel> getRepTeamleaders(Connection con, String dcmUserId){

            Vector<RepTeamleaderModel> repTeamleaders=new Vector();
            /*String sqlStatement="SELECT RT.TEAMLEAD_ID,RT.REP_ID,UD.USER_FULL_NAME TEAMLEAD_NAME,RT.CREATED_BY,RT.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RT WHERE RT.TEAMLEAD_ID=UD.USER_ID"
                                +" AND RT.REP_ID="+dcmUserId;*/
            String sqlStatement = "select scm_salesrep.teamlead_id, scm_salesrep.salesrep_id as rep_id,scm_teamleader.teamleader_name as TEAMLEAD_NAME,scm_salesrep.creation_timestamp as CREATED_IN, dcm_user_detail.creation_user_id as CREATED_BY from scm_salesrep, scm_teamleader , dcm_user_detail where scm_salesrep.teamlead_id is not null and scm_salesrep.teamlead_id = scm_teamleader.teamleader_id and scm_salesrep.salesrep_id = dcm_user_detail.user_id and salesrep_id="+dcmUserId;
            System.out.println("getRepTeamleaders "+sqlStatement);
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
    
    
    public static Vector<SupervisorTeamleadersModel> getSupervisorTeamleaders(Connection con, String dcmUserId){

            Vector<SupervisorTeamleadersModel> supervisorTeamleaders=new Vector();
            /*String sqlStatement="SELECT TL.TEAMLEAD_ID,TL.SUP_ID,UD.USER_FULL_NAME TEAMLEAD_NAME,TL.CREATED_BY,TL.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_TEAMLEADER_SUPERVISORS TL WHERE TL.TEAMLEAD_ID=UD.USER_ID"
                                +" AND TL.SUP_ID="+dcmUserId;*/
            String sqlStatement = "select dcm_user.user_id, scm_teamleader.*,scm_supervisor.supervisor_id,scm_supervisor.supervisor_name from dcm_user,scm_teamleader,scm_supervisor where dcm_user.dcm_user_id=scm_teamleader.teamleader_id and dcm_user.user_level_type_id=5 and scm_supervisor.supervisor_id="+dcmUserId+" and scm_teamleader.sup_id=scm_supervisor.supervisor_id order by scm_teamleader.creation_timestamp desc";
            System.out.println("Supervisor Teamleaders "+sqlStatement);
            supervisorTeamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, SupervisorTeamleadersModel.class, con);
            
            return supervisorTeamleaders;
    }
    
    public static Vector<TeamleaderSupervisorsModel> getTeamleaderSupervisors(Connection con, String dcmUserId){

            Vector<TeamleaderSupervisorsModel> supervisorTeamleaders=new Vector();
            /*String sqlStatement="SELECT TL.TEAMLEAD_ID,TL.SUP_ID,UD.USER_FULL_NAME SUP_NAME,TL.CREATED_BY,TL.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_TEAMLEADER_SUPERVISORS TL WHERE TL.SUP_ID=UD.USER_ID"
                                +" AND TL.TEAMLEAD_ID="+dcmUserId;*/
            String sqlStatement ="select dcm_user.user_id, scm_supervisor.*, scm_teamleader.teamleader_id , scm_teamleader.teamleader_name from dcm_user,scm_teamleader,scm_supervisor where dcm_user.dcm_user_id=scm_supervisor.supervisor_id and dcm_user.user_level_type_id=4 and scm_teamleader.sup_id = scm_supervisor.supervisor_id and scm_teamleader.teamleader_id = "+dcmUserId;
            System.out.println("getTeamleaderSupervisors "+sqlStatement);
            supervisorTeamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, TeamleaderSupervisorsModel.class, con);
            
            return supervisorTeamleaders;
    }
    
    
    public static Vector<SupervisorRepsModel> getSupervisorSalesReps(Connection con, String dcmUserId){

            Vector<SupervisorRepsModel> supervisorReps=new Vector();
            /*String sqlStatement="SELECT RS.REP_ID,RS.SUP_ID,UD.USER_FULL_NAME REP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_SUPERVISORS RS WHERE RS.REP_ID=UD.USER_ID"
                                +" AND RS.SUP_ID="+dcmUserId;*/
            //String sqlStatement = "select dcm_user.user_id, scm_salesrep.*,scm_supervisor.supervisor_id,scm_supervisor.supervisor_name from dcm_user,scm_salesrep, scm_teamleader,scm_supervisor where dcm_user.dcm_user_id=scm_salesrep.salesrep_id and dcm_user.user_level_type_id=6 and scm_supervisor.supervisor_id="+dcmUserId+" and scm_teamleader.sup_id=scm_supervisor.supervisor_id and scm_salesrep.teamlead_id = scm_teamleader.teamleader_id";
            String sqlStatement = "select dcm_user.user_id, scm_salesrep.*,scm_supervisor.supervisor_id,scm_supervisor.supervisor_name from dcm_user,scm_salesrep, scm_supervisor where dcm_user.dcm_user_id=scm_salesrep.salesrep_id and dcm_user.user_level_type_id=6 and scm_supervisor.supervisor_id="+dcmUserId+" and scm_salesrep.sup_id = scm_supervisor.supervisor_id";
            supervisorReps=DBUtil.executeSqlQueryMultiValue(sqlStatement, SupervisorRepsModel.class, con);
            return supervisorReps;
    }
    
    
    
    public static Vector<TeamleaderRepsModel> getTeamleaderReps(Connection con, String dcmUserId){

            Vector<TeamleaderRepsModel> repTeamleaders=new Vector();
            /*String sqlStatement="SELECT RS.REP_ID,RS.TEAMLEAD_ID,UD.USER_FULL_NAME REP_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RS WHERE RS.REP_ID=UD.USER_ID"
                                +" AND RS.TEAMLEAD_ID="+dcmUserId;*/
            String sqlStatement = "select dcm_user.user_id, scm_salesrep.* , scm_teamleader.teamleader_id , scm_teamleader.teamleader_name from dcm_user, scm_salesrep, scm_teamleader where dcm_user.dcm_user_id=scm_salesrep.salesrep_id and dcm_user.user_level_type_id=6 and scm_teamleader.teamleader_id="+dcmUserId+" and scm_salesrep.teamlead_id = scm_teamleader.teamleader_id";
            System.out.println("getTeamleaderReps "+sqlStatement);
            repTeamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, TeamleaderRepsModel.class, con);
            return repTeamleaders;
    }

    
    public static Vector<RepTeamleaderModel> getTeamleaderSalesReps(Connection con, String dcmUserId){

            Vector<RepTeamleaderModel> repTeamleaders=new Vector();
            String sqlStatement="SELECT RS.REP_ID,RS.TEAMLEAD_ID,UD.USER_FULL_NAME TEAMLEAD_NAME,RS.CREATED_BY,RS.CREATED_IN "
                                +" FROM DCM_USER_DETAIL UD,SCM_REP_TEAMLEADERS RS WHERE RS.TEAMLEAD_ID=UD.USER_ID"
                                +" AND RS.TEAMLEAD_ID="+dcmUserId;
            System.out.println("getTeamleaderReps "+sqlStatement);
            repTeamleaders=DBUtil.executeSqlQueryMultiValue(sqlStatement, RepTeamleaderModel.class, con);
            return repTeamleaders;
    }
    
    public static Vector<PersonModel> searchGENPersons(Connection con,String personName){
        Vector<PersonModel> persons=new Vector();
        /*String sqlStatement="SELECT GP.PERSON_ID,GP.PERSON_FULL_NAME, GP.PERSON_ADDRESS, GP.PERSON_EMAIL"
                            +" FROM GEN_PERSON GP, GEN_USER GU"
                            +" WHERE GU.USER_ID=GP.PERSON_ID AND GU.USER_STATUS_ID=1 AND GP.PERSON_ID NOT IN ("
                            +" SELECT USER_ID FROM DCM_USER WHERE USER_STATUS_TYPE_ID <>3 AND USER_ID IS NOT NULL)";
        if(personName!=null&& !personName.trim().equalsIgnoreCase("")){
           //sqlStatement+=" AND  LOWER(GP.PERSON_FULL_NAME) LIKE ?";
           //personName="'%"+personName.toLowerCase()+"%'";
           //persons=DBUtil.executePreparedSqlQueryMultiValue(sqlStatement, PersonModel.class, con,new Object[]{personName});
            sqlStatement+=" AND  LOWER(GP.PERSON_FULL_NAME) LIKE '%"+personName.toLowerCase()+"%'";*/
        
        String sqlStatement="SELECT dcm_user_level_type.user_level_type_name ,dcm_user_detail.user_id as PERSON_ID, dcm_user_detail.user_full_name as PERSON_FULL_NAME, dcm_user_detail.user_address as PERSON_ADDRESS, dcm_user_detail.user_email as PERSON_EMAIL"
                            +" FROM dcm_user_level_type,dcm_user,dcm_user_detail where dcm_user.user_level_type_id = dcm_user_level_type.user_level_type_id and dcm_user.dcm_user_id = dcm_user_detail.user_id and dcm_user.user_detail_id=dcm_user_detail.user_detail_id ";
        if(personName!=null&& !personName.trim().equalsIgnoreCase("")){
                            sqlStatement+= " and LOWER(dcm_user_detail.user_full_name) LIKE '%"+personName.toLowerCase()+"%'";
            persons=DBUtil.executeSqlQueryMultiValue(sqlStatement, PersonModel.class, con);           
           
        }
        else{
           sqlStatement+=" order by LOWER(dcm_user_detail.user_full_name)";
           persons=DBUtil.executeSqlQueryMultiValue(sqlStatement, PersonModel.class, con);
        }
        System.out.println("searchGENPersons "+sqlStatement);
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