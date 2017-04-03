package com.mobinil.sds.core.system.dcm.region.dao;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.dcm.region.model.*;
import com.mobinil.sds.core.system.dcm.region.dto.*;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionDAO {

    public static Vector getChildRegions(Connection con, int id) {
        Vector vector = new Vector();
        RegionModel regionModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select REGION_ID from DCM_REGION where PARENT_REGION_ID = " + id + "order by REGION_NAME";
            System.out.println("getChildRegions "+strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                regionModel = new RegionModel();
                vector.addElement(getRegionDto(con, res.getInt(regionModel.REGION_ID)));
            }
            res.close();
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public static Vector getParentRegions(Connection con) {
        Vector vector = new Vector();
        RegionModel regionModel = new RegionModel();
        try {
            Statement stat = con.createStatement();
            String strSql = "select REGION_ID FROM DCM_REGION where PARENT_REGION_ID is null order by REGION_NAME";
            System.out.println("getParentRegions "+strSql);
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vector.addElement(getRegionDto(con, res.getInt(1)));
            }
            res.close();
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public static Vector<RegionModel> getChilds(Connection con, String RegionId, String rowNum) {
        String strSql = "SELECT * FROM (SELECT ROWNUM as row_num,REGION_ID , REGION_NAME ,REGION_LEVEL_TYPE_NAME"
                + " FROM dcm_region , DCM_REGION_LEVEL_TYPE"
                + " WHERE DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID"
                + " AND dcm_region.REGION_STATUS_TYPE_ID<>3"
                + " START WITH parent_region_id =" + RegionId
                + " CONNECT BY PRIOR region_id = parent_region_id)WHERE row_num > = ('" + rowNum + "'*20)+1 AND row_num < = ('" + rowNum + "'+1)*20  ORDER BY ROWNUM";


        Vector<RegionModel> childs = DBUtil.executeSqlQueryMultiValue(strSql, RegionModel.class, con);


        return childs;
    }

    public static int getChildstotal(Connection con, String RegionId) {
        String strSql = "SELECT CEIL(COUNT(REGION_ID)/20)"
                + " FROM dcm_region , DCM_REGION_LEVEL_TYPE"
                + " WHERE DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID"
                + " AND dcm_region.REGION_STATUS_TYPE_ID<>3"
                + " START WITH parent_region_id =" + RegionId
                + " CONNECT BY PRIOR region_id = parent_region_id";


        int childs = DBUtil.executeQuerySingleValueInt(strSql, "CEIL(COUNT(REGION_ID)/20)", con);


        return childs;
    }

    public static RegionModel getRegionModel(Connection con, int id) {
        RegionModel regionModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select REGION_NAME,PARENT_REGION_ID FROM DCM_REGION where REGION_ID = '" + id + "'";
            //Utility.logger.debug(strSql);
            System.out.println("getRegionModel "+strSql);
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                regionModel = new RegionModel();
                regionModel.setId(id);
                regionModel.setName(res.getString(regionModel.REGION_NAME));
                regionModel.setParentId(res.getString(regionModel.PARENT_REGION_ID));
            }
            res.close();
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regionModel;
    }

    public static RegionDto getRegionDto(Connection con, int id) {
        RegionDto region = new RegionDto();
        region.setModel(getRegionModel(con, id));
        region.setChildRegions(getChildRegions(con, id));
        return region;
    }

    public static Vector<RegionModel> getParentRegion(Connection con, String regionId) {
        String query = "SELECT PARENT_REGION_ID FROM DCM_REGION WHERE REGION_ID='" + regionId + "'";

        Integer Parent = DBUtil.executeQuerySingleValueInt(query, "PARENT_REGION_ID", con);

        Vector<RegionModel> parentsvector = new Vector<RegionModel>();


        while (Parent != -1) {

            String regionQuery = "SELECT REGION_ID,REGION_NAME,REGION_LEVEL_TYPE_NAME,PARENT_REGION_ID FROM DCM_REGION,DCM_REGION_LEVEL_TYPE"
                    + " WHERE REGION_ID= " + Parent + " AND DCM_REGION.REGION_LEVEL_TYPE_ID=DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID";
            RegionModel temp = DBUtil.executeSqlQuerySingleValue(regionQuery, RegionModel.class, con);
            if (temp == null) {
                break;
            }
            parentsvector.add(temp);
            if (Parent == 0) {
                break;
            }
            if (temp.getParentRegionId().equals("") || temp.getParentRegionId() == null || temp.getParentRegionId().equals("0")) {
                Parent = -1;
            } else {
                Parent = Parent.parseInt(temp.getParentRegionId());
            }
        }

        return parentsvector;
    }

    public static Vector<RegionModel> editParent(Connection con, String labelIdKey) {



        Vector<RegionModel> parentsvector = new Vector<RegionModel>();

        try {



            Statement stmt = con.createStatement();
            String sqlString = "select region_id ,region_name , REGION_LEVEL_TYPE_ID from dcm_region where REGION_LEVEL_TYPE_ID = (select REGION_LEVEL_TYPE_ID from dcm_region where region_id =" + labelIdKey + ")-1 ";

            System.out.println(sqlString);
            ResultSet governorateRS = stmt.executeQuery(sqlString);
            while (governorateRS.next()) {
                RegionModel regionModel = new RegionModel();

                regionModel.setRegionId(governorateRS.getString("region_id"));
                regionModel.setRegionName(governorateRS.getString("region_name"));


                parentsvector.add(regionModel);
            }
            governorateRS.close();
            stmt.close();



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return parentsvector;
    }
///////////////////////////////////////////////////////////////////////

    public static RegionModel selectedParent(Connection con, String labelIdKey) {



        RegionModel regionModel = null;
        try {



            Statement stmt = con.createStatement();
            String sqlString = "select region_id ,region_name  from dcm_region where REGION_ID =" + labelIdKey + "";

            System.out.println(sqlString);
            ResultSet governorateRS = stmt.executeQuery(sqlString);
            while (governorateRS.next()) {
                regionModel = new RegionModel();

                regionModel.setRegionId(governorateRS.getString("region_id"));
                regionModel.setRegionName(governorateRS.getString("region_name"));


            }
            governorateRS.close();
            stmt.close();



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return regionModel;
    }
//////////////////////////////////////////////////////////////////////    

    public static void insertNewChild(Connection con, String regionName, String parentRegionId, String campas_Code, String regionArabicName, String typeOriginalLevelId, String typeCovarageLevelId, String pop_Code, String SCEARABICNAME, String SCEENGLISHNAME, String family, String regionCode) {
        String maxIDquery = "SELECT MAX(REGION_ID) FROM DCM_REGION";
        String levelQuery = "SELECT REGION_LEVEL_TYPE_ID FROM  DCM_REGION WHERE REGION_ID =" + parentRegionId + "";
        int nextid = DBUtil.executeQuerySingleValueInt(maxIDquery, "MAX(REGION_ID)", con) + 1;
        int levelid = DBUtil.executeQuerySingleValueInt(levelQuery, "REGION_LEVEL_TYPE_ID", con);
        if (levelid == 5) {
            levelid = 5;
        } else {
            levelid++;
        }

        String query = "INSERT INTO SDS.DCM_REGION ("
                + "REGION_ID, REGION_NAME, REGION_STATUS_TYPE_ID, "
                + "PARENT_REGION_ID, REGION_LEVEL_TYPE_ID,CAMPAS_CODE,ARABIC_NAME,REGION_CODE) "
                + "VALUES(" + nextid + ",'" + regionName + "',1," + parentRegionId
                + "," + levelid + ",'" + campas_Code + "','" + regionArabicName + "','" + regionCode + "')";

        DBUtil.executeSQL(query);
       /* if (levelid == 5) {
            String areaQuery = "INSERT INTO SDS.GEN_AREA_DETAILS ("
                    + "AREA_ID, CAPMAS_CODE, COVARAGE, "
                    + "POP, SCEARABICNAME, SCEENGLISHNAME, "
                    + "TYPE_ORIGINAL, FAMILY,AREA_CODE) "
                    + "VALUES (" + nextid + ",'" + campas_Code + "' ," + typeCovarageLevelId + ","
                    + "'" + pop_Code + "','" + SCEARABICNAME + "' ,'" + SCEENGLISHNAME + "',"
                    + "" + typeOriginalLevelId + ",'" + family + "','" + regionCode + "')";

            DBUtil.executeSQL(areaQuery);

        }*/



    }

    public static String deleteRegion(Connection con, String RegionId) {


        String strSql = "SELECT region_id"
                + " FROM dcm_region"
                + " WHERE region_id IN"
                + " (SELECT region_id"
                + " FROM dcm_region"
                + " START WITH parent_region_id =" + RegionId
                + " CONNECT BY PRIOR region_id = parent_region_id) OR region_id =" + RegionId;

        Vector<RegionModel> regions = DBUtil.executeSqlQueryMultiValue(strSql, RegionModel.class, con);
        String Message = "";
        for (int i = 0; i < regions.size(); i++) {

            boolean checkExist = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_REGION WHERE REGION_ID = '" + regions.get(i).getRegionId() + "' AND REGION_STATUS_TYPE_ID <>3");

            boolean checkRep = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_USER WHERE USER_STATUS_TYPE_ID <> 3 AND REGION_ID= '" + regions.get(i).getRegionId() + "'");

            boolean checkposgovern = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID <> 4 AND POS_GOVERNRATE= '" + regions.get(i).getRegionId() + "'");

            boolean checkposarea = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID <> 4 AND POS_AREA_ID= '" + regions.get(i).getRegionId() + "'");

            boolean checkposcity = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID <> 4 AND POS_CITY_ID= '" + regions.get(i).getRegionId() + "'");

            boolean checkposdist = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID <> 4 AND POS_DISTRICT_ID= '" + regions.get(i).getRegionId() + "'");

            boolean checkposregion = DBUtil.executeSQLExistCheck("SELECT REGION_ID FROM DCM_POS_DETAIL WHERE POS_STATUS_TYPE_ID <> 4 AND REGION_ID= '" + regions.get(i).getRegionId() + "'");

            if (!checkExist) {
                Message = "Entity doesn't exist";
                break;
            }

            if (checkRep) {
                Message = "Entity is assigned to rep";
                break;
            }

            if (checkposarea) {
                Message = "Area is assigned to POS";
                break;
            }

            if (checkposdist) {
                Message = "District is assigned to POS";
                break;
            }

            if (checkposcity) {
                Message = "City is assigned to POS";
                break;
            }

            if (checkposgovern) {
                Message = "Governrate is assigned to POS";
                break;
            }

            if (checkposregion) {
                Message = "Region is assigned to POS";
                break;
            }

        }
        if (Message == "") {
            String deletequery = "UPDATE DCM_REGION SET REGION_STATUS_TYPE_ID=3 WHERE region_id IN (SELECT region_id"
                    + " FROM dcm_region"
                    + " START WITH parent_region_id =" + RegionId
                    + " CONNECT BY PRIOR region_id = parent_region_id) OR region_id =" + RegionId;
            DBUtil.executeSQL(deletequery);
        }

        return Message;

    }

    public static void moveRegion(Connection con, String parentRegionId, String regionId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "update DCM_REGION set PARENT_REGION_ID = '" + parentRegionId + "' where REGION_ID = '" + regionId + "'";
            Utility.logger.debug(strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ///////////////////////////////////// by Mohamed Youssef
    public static void updateParent(Connection con, String Id, Vector labelId) {

        try {
            Statement stat = con.createStatement();
            String areas = "";

            System.out.println("Vector Size:" + labelId.size());
            for (int i = 0; i < labelId.size(); i++) {
                System.out.println("Vector Has" + labelId.get(i));

                areas += labelId.get(i) + ",";

            }
            if (areas.endsWith(",")) {
                areas = areas.substring(0, areas.length() - 1);
            }

            System.out.println("Update WHERE REGIONS IN" + areas);


            String strSql = "update DCM_REGION set PARENT_REGION_ID = '" + Id + "' WHERE REGION_ID IN(" + areas + ")";
            Utility.logger.debug(strSql);
            stat.executeUpdate(strSql);


            String strSql2 = "SELECT REGION_LEVEL_TYPE_ID FROM DCM_REGION WHERE REGION_ID = " + Id;
            int levelid = DBUtil.executeQuerySingleValueInt(strSql2, "REGION_LEVEL_TYPE_ID", con);
            if (levelid == 2) {
                String strSql4 = "update DCM_POS_DETAIL SET REGION_ID= '" + Id + "' WHERE POS_GOVERNRATE IN(" + areas + ")";
                Utility.logger.debug(strSql4);

                stat.executeUpdate(strSql4);

            } else if (levelid == 3) {

                String strSql5 = "update DCM_POS_DETAIL SET POS_GOVERNRATE= '" + Id + "' WHERE POS_CITY_ID IN(" + areas + ")";
                Utility.logger.debug(strSql5);

                stat.executeUpdate(strSql5);

            } else if (levelid == 4) {
                String strSql6 = "update DCM_POS_DETAIL SET POS_CITY_ID= '" + Id + "' WHERE POS_DISTRICT_ID IN(" + areas + ")";
                Utility.logger.debug(strSql6);
                stat.executeUpdate(strSql6);
                String strSql7 = "update GEN_DCM SET DCM_CITY_ID='" + Id + "' WHERE DCM_DISTRICT_ID IN(" + areas + ")";
                Utility.logger.debug(strSql7);
                stat.executeQuery(strSql7);

            } else if (levelid == 5) {
                String strSql8 = "update DCM_POS_DETAIL SET POS_DISTRICT_ID = '" + Id + "' WHERE POS_AREA_ID IN(" + areas + ")";
                Utility.logger.debug(strSql8);
                stat.executeQuery(strSql8);
            }

            stat.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    ////////////////////////////////////////////////
    public static RegionModel getRegionById(Connection con, String regionId) {
        RegionModel regionModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select REGION_NAME from DCM_REGION where REGION_ID = '" + regionId + "'";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                regionModel = new RegionModel();
                regionModel.setRegionName(res.getString(regionModel.REGION_NAME));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regionModel;
    }

    public static void updateRegionName(Connection con, String regionId, String regionName) {
        try {
            Statement stat = con.createStatement();
            String strSql = "update DCM_REGION set REGION_NAME = '" + regionName + "' where REGION_ID = '" + regionId + "'";
            Utility.logger.debug(strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap getRegionDto(Connection con) {
        HashMap regions = new HashMap();
        int id;
        try {
            Statement sqlStat = con.createStatement();
            String sqlQuery = "select region_ID from dcm_region";
            ResultSet result = sqlStat.executeQuery(sqlQuery);

            while (result.next()) {
                id = result.getInt(1);
                regions.put("region_" + id, getRegionDto(con, id));
            }

            result.close();
            sqlStat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regions;
    }

    public static Vector getAllRegions(Connection con) {
        Vector regionVector = new Vector();
        RegionModel regionModel = null;

        try {
            Statement stmt = con.createStatement();
            //String sqlString = "SELECT * FROM VW_DCM_REGION order by REGION_LEVEL_TYPE_ID";

            /*String sqlString = "select lpad('----------', (level - 1)) || region_name as region_name, vw_dcm_region.* "+
             " from vw_dcm_region "+
             " connect by prior region_id = parent_region_id "+
             " start with region_id = 1 ";*/

            /*String sqlString = "select region_name as region_name, vw_dcm_region.* "
                    + " from vw_dcm_region "
                    + " connect by prior region_id = parent_region_id "
                    + " start with region_id = 1 ";*/
            
            String sqlString = "SELECT dcm_region.region_id, dcm_region.region_name,\n" +
"          dcm_region.region_status_type_id,\n" +
"          dcm_region_status_type.region_status_type_name,\n" +
"          dcm_region.parent_region_id, dcm_region.region_level_type_id,\n" +
"          dcm_region_level_type.region_level_type_name\n" +
"     FROM dcm_region, dcm_region_level_type, dcm_region_status_type\n" +
"    WHERE dcm_region.region_status_type_id =\n" +
"                                  dcm_region_status_type.region_status_type_id\n" +
"      AND dcm_region.region_level_type_id =\n" +
"                                    dcm_region_level_type.region_level_type_id";
            

            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                regionModel = new RegionModel(rs);
                regionVector.add(regionModel);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regionVector;
    }

    public static Vector getRegionsByUserId(Connection con, String strUserId) {
        Vector regionVector = new Vector();
        RegionModel regionModel = null;

        try {
            Statement stmt = con.createStatement();


            String sqlString = "select region_name as region_name,vw_dcm_region.*  from vw_dcm_region "
                    + "connect by prior region_id = parent_region_id "
                    + "start with region_id in (select region_id from dcm_user_region where dcm_user_id in "
                    + "(select dcm_user_id from dcm_user where user_id = '" + strUserId + "')) ";

            System.out.println("The Region query isssssssssssss " + sqlString);

            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                regionModel = new RegionModel(rs);
                regionVector.add(regionModel);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regionVector;
    }

    public static Vector getAllRegionsByRegionId(Connection con, int regionId) {
        Vector regionVector = new Vector();
        RegionModel regionModel = null;

        try {
            Statement stmt = con.createStatement();
            //String sqlString = "SELECT * FROM VW_DCM_REGION order by REGION_LEVEL_TYPE_ID";

            /*String sqlString = "select lpad('----------', (level - 1)) || region_name as region_name, vw_dcm_region.* "+
             " from vw_dcm_region "+
             " connect by prior region_id = parent_region_id "+
             " start with region_id = '"+regionId+"' ";*/

            String sqlString = "select region_name as region_name, vw_dcm_region.* "
                    + " from vw_dcm_region "
                    + " connect by prior region_id = parent_region_id "
                    + " start with region_id = '" + regionId + "' ";

            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                regionModel = new RegionModel(rs);
                regionVector.add(regionModel);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regionVector;
    }

    public static Vector getRegionByLevelId(Connection con, int regionLevelTypeId) {
        Vector regionVector = new Vector();
        RegionModel regionModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_DCM_REGION where REGION_LEVEL_TYPE_ID = '" + regionLevelTypeId + "'";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                regionModel = new RegionModel(res);
                regionVector.add(regionModel);
            }
            res.close();
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regionVector;
    }

    public static Vector<RegionLevelDto> getALLRegionlevels(Connection con) {
        String query = "SELECT D.REGION_LEVEL_TYPE_ID, D.REGION_LEVEL_TYPE_NAME FROM SDS.DCM_REGION_LEVEL_TYPE D ORDER BY REGION_LEVEL_TYPE_NAME";

        Vector<RegionLevelDto> levels = DBUtil.executeSqlQueryMultiValue(query, RegionLevelDto.class, con);
        return levels;




    }

    public static Vector<RegionModel> getRegionByName(String regionName, String level, Connection con, String rowNum) {
        String query = "SELECT * FROM (SELECT ROWNUM as row_num ,REGION_NAME,REGION_ID,REGION_LEVEL_TYPE_NAME,dcm_region.REGION_LEVEL_TYPE_ID FROM DCM_REGION,DCM_REGION_LEVEL_TYPE WHERE REGION_STATUS_TYPE_ID<>3 "
                + "AND DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID)WHERE row_num > = ('" + rowNum + "'*20)+1 AND row_num < = ('" + rowNum + "'+1)*20  ORDER BY ROWNUM";

        if (!regionName.equals(null) && !regionName.equals("")) {

            query = "SELECT * FROM"
                    + "(SELECT ROWNUM as row_num ,REGION_NAME,REGION_ID,REGION_LEVEL_TYPE_NAME,dcm_region.REGION_LEVEL_TYPE_ID FROM DCM_REGION,DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_NAME) LIKE LOWER('%" + regionName + "%')"
                    + " AND REGION_STATUS_TYPE_ID<>3"
                    + " AND DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID"
                    + " AND dcm_region.REGION_LEVEL_TYPE_ID =" + level + ")"
                    + " WHERE row_num > = ('0'*20)+1 AND row_num < = ('0'+1)*20  ORDER BY ROWNUM";


        }

        if ((!level.equals(null) && !level.equals(""))) {
            if (regionName.equals(null) || regionName.equals("")) {
                query = "SELECT * FROM (SELECT ROWNUM as row_num ,REGION_NAME,REGION_ID,REGION_LEVEL_TYPE_NAME,dcm_region.REGION_LEVEL_TYPE_ID FROM DCM_REGION,DCM_REGION_LEVEL_TYPE WHERE DCM_REGION.REGION_LEVEL_TYPE_ID =" + level + " AND REGION_STATUS_TYPE_ID<>3 "
                        + "AND DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID)WHERE row_num > = ('" + rowNum + "'*20)+1 AND row_num < = ('" + rowNum + "'+1)*20  ORDER BY ROWNUM";
            }
        }
System.out.println("QUERY ... "+query);

        Vector<RegionModel> regions = DBUtil.executeSqlQueryMultiValue(query, RegionModel.class, con);
        return regions;

    }

    public static int getRegionByNameCount(String regionName, String level, Connection con) {
        String query = "";

        if (!regionName.equals(null) && !regionName.equals("")) {

            query = "SELECT CEIL (COUNT(REGION_ID)/20) FROM"
                    + "(SELECT REGION_NAME,REGION_ID FROM DCM_REGION,DCM_REGION_LEVEL_TYPE WHERE LOWER(REGION_NAME) LIKE LOWER('%" + regionName + "%')"
                    + " AND REGION_STATUS_TYPE_ID<>3"
                    + " AND DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID"
                    + " AND dcm_region.REGION_LEVEL_TYPE_ID =" + level + ")";


        }

        if ((!level.equals(null) && !level.equals(""))) {
            if (regionName.equals(null) || regionName.equals("")) {
                query = "SELECT CEIL (COUNT(REGION_ID)/20) FROM DCM_REGION,DCM_REGION_LEVEL_TYPE WHERE DCM_REGION.REGION_LEVEL_TYPE_ID =" + level + " AND REGION_STATUS_TYPE_ID<>3 "
                        + "AND DCM_REGION_LEVEL_TYPE.REGION_LEVEL_TYPE_ID=dcm_region.REGION_LEVEL_TYPE_ID";
            }
        }


        int regions = DBUtil.executeQuerySingleValueInt(query, "CEIL(COUNT(REGION_ID)/20)", con);
        return regions;

    }

    public static String getRegionName(String regionId) {
        String query = "SELECT REGION_NAME FROM DCM_REGION WHERE REGION_ID=" + regionId;
        String regionName = DBUtil.executeQuerySingleValueString(query, "REGION_NAME");
        return regionName;
    }

    public static void insertNewRegion(Connection con, String regionName, String parentRegionId, Long regionId) {
        try {
            String regionLevelTypeId = null;
            Statement stat2 = con.createStatement();
            String strSqlSelect = "select REGION_LEVEL_TYPE_ID FROM DCM_REGION where REGION_ID = '" + parentRegionId + "'";
            ResultSet res = stat2.executeQuery(strSqlSelect);
            while (res.next()) {
                regionLevelTypeId = res.getString("REGION_LEVEL_TYPE_ID");

            }
            Statement stat = con.createStatement();
            int intRegionLevelTypeId = Integer.parseInt(regionLevelTypeId);
            if (intRegionLevelTypeId < 5) {
                intRegionLevelTypeId = intRegionLevelTypeId + 1;
            }
            String strSql = "insert into DCM_REGION(REGION_ID,REGION_NAME,REGION_STATUS_TYPE_ID,PARENT_REGION_ID,REGION_LEVEL_TYPE_ID)values('" + regionId + "','" + regionName + "','1','" + parentRegionId + "','" + intRegionLevelTypeId + "')";
            Utility.logger.debug(strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void insertExcelChilds(String regionId) {
        Connection con;
        try {
            con = Utility.getConnection();

            String maxIDquery = "SELECT MAX(REGION_ID) FROM DCM_REGION";
            String levelQuery = "SELECT REGION_LEVEL_TYPE_ID FROM  DCM_REGION WHERE REGION_ID =" + regionId + "";
            int nextid = DBUtil.executeQuerySingleValueInt(maxIDquery, "MAX(REGION_ID)", con);
            int levelid = DBUtil.executeQuerySingleValueInt(levelQuery, "REGION_LEVEL_TYPE_ID", con);
            if (levelid == 4) {
                levelid = 5;
                String query = "INSERT INTO SDS.DCM_REGION ("
                        + "REGION_ID, REGION_NAME,REGION_CODE,REGION_STATUS_TYPE_ID, "
                        + "PARENT_REGION_ID, REGION_LEVEL_TYPE_ID,CAMPAS_CODE,ARABIC_NAME) "
                        + "SELECT " + nextid + "+ROW_NUM,AREA_NAME,AREA_CODE,1," + regionId
                        + "," + levelid + ",CAMPAS_CODE,SCEARABICNAME FROM SCM_DISTRICT_CHILDS";

                String areaQuery = "INSERT INTO SDS.GEN_AREA_DETAILS ("
                        + "AREA_ID, CAPMAS_CODE, COVARAGE, "
                        + "POP, SCEARABICNAME, SCEENGLISHNAME, "
                        + "TYPE_ORIGINAL, FAMILY,AREA_CODE) "
                        + "SELECT " + nextid + "+ROW_NUM,CAMPAS_CODE,COVARAGE,POP,SCEARABICNAME"
                        + ",AREA_NAME,TYPE_ORIGINAL,FAMILY,AREA_CODE FROM SCM_DISTRICT_CHILDS";

                System.out.print(areaQuery);


                DBUtil.executeSQL(areaQuery);


                DBUtil.executeSQL(query);
                DBUtil.executeSQL("truncate table SCM_DISTRICT_CHILDS");

            } else {
                levelid++;

                String query = "INSERT INTO SDS.DCM_REGION ("
                        + "REGION_ID, REGION_NAME,REGION_CODE,REGION_STATUS_TYPE_ID, "
                        + "PARENT_REGION_ID, REGION_LEVEL_TYPE_ID,CAMPAS_CODE,ARABIC_NAME) "
                        + "SELECT " + nextid + "+ROW_NUM,CHILD_NAME,CHILD_CODE,1," + regionId
                        + "," + levelid + ",CAMPAS_CODE,ARABIC_NAME FROM SCM_REGION_CHILDS";

                DBUtil.executeSQL(query);
                DBUtil.executeSQL("truncate table SCM_REGION_CHILDS");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegionDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getRegionLevelById(String regionId, Connection con) {
        String query = "select REGION_LEVEL_TYPE_ID FROM DCM_REGION where REGION_ID = '" + regionId + "'";
        System.out.println("getRegionLevelById query: "+query);
        int level_Id = DBUtil.executeQuerySingleValueInt(query, "REGION_LEVEL_TYPE_ID", con);
        return level_Id;

    }

    public static Vector<CovarageLevelDto> getAllCovarageLevels(Connection con) {

        Vector<CovarageLevelDto> CovarageLevels = DBUtil.executePreparedSqlQueryMultiValue("select * from DCM_COVARAGE", CovarageLevelDto.class, con);
        return CovarageLevels;
    }

    public static Vector<Area_Level_Type_OriginalDto> getAllOriginalsLevels(Connection con) {

        Vector<Area_Level_Type_OriginalDto> All_Area_Level_Type_OriginalDto = DBUtil.executePreparedSqlQueryMultiValue("select * from GEN_AREA_ORIGINAL_TYPE", Area_Level_Type_OriginalDto.class, con);
        return All_Area_Level_Type_OriginalDto;
    }

    public static Vector<AreaDataDetailsModel> getAllDetailsForArea(Connection con, String regionId) {

        String query = "SELECT "
                + " D.AREA_ID, D.CAPMAS_CODE, C.NAME COVARAGE, "
                + " D.POP, D.SCEARABICNAME, D.SCEENGLISHNAME, "
                + " O.NAME TYPE_ORIGINAL, D.FAMILY, D.AREA_CODE"
                + " FROM GEN_AREA_DETAILS D , DCM_COVARAGE C ,GEN_AREA_ORIGINAL_TYPE O"
                + " WHERE AREA_ID=" + regionId + " AND D.COVARAGE = C.TYPE_ID AND  D.TYPE_ORIGINAL = O.TYPE_ID";

        Vector<AreaDataDetailsModel> allDetailsForArea = DBUtil.executePreparedSqlQueryMultiValue(query, AreaDataDetailsModel.class, con);

        return allDetailsForArea;
    }
}
