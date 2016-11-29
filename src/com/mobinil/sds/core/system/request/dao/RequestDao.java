package com.mobinil.sds.core.system.request.dao;

import com.mobinil.sds.core.system.dcm.pos.model.POSDetailModel;
import com.mobinil.sds.core.system.dcm.region.model.RegionModel;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;   
import java.sql.Statement;
import java.util.Vector;
import com.mobinil.sds.core.system.request.model.*;
import com.mobinil.sds.core.system.sa.unupdatedcode.model.UnupdatedCode;
import com.mobinil.sds.core.system.scm.model.POSSearchExcelModel;
import com.mobinil.sds.core.system.scm.model.POSSimilar;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.HashMap;


public class RequestDao {

    public static Vector getSupervisorList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        //String sqlString = "select USER_DETAIL_ID , USER_FULL_NAME from DCM_USER_DETAIL where USER_DETAIL_STATUS_ID = 1 ORDER BY USER_FULL_NAME ASC";
        String sqlString = "select DCM_USER_DETAIL.USER_DETAIL_ID , DCM_USER_DETAIL.USER_FULL_NAME from "
                + "DCM_USER_DETAIL,DCM_USER where DCM_USER_DETAIL.USER_DETAIL_ID = DCM_USER.USER_DETAIL_ID "
                + "and DCM_USER.USER_LEVEL_TYPE_ID = 4 and DCM_USER_DETAIL.USER_DETAIL_STATUS_ID = 1 ORDER BY USER_FULL_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        SupervisorModel supervisorModel = null;
        Vector supervisorList = new Vector();

        while (rs.next()) {
            supervisorModel = new SupervisorModel();
            supervisorModel.setSupervisorId(rs.getInt("USER_DETAIL_ID"));
            supervisorModel.setSupervisorName(rs.getString("USER_FULL_NAME"));
            // supervisorModel.setSupervisorStatus(rs.getInt("USER_DETAIL_STATUS_ID"));
            supervisorList.add(supervisorModel);
        }

        stmt.close();
        rs.close();

        return supervisorList;
    }
    public static ResultSet getFlagsByPosDetailId(Connection con, String posDetailId)
    {
        ResultSet rs = null;
        Statement stmt=null;
        try{
            stmt = con.createStatement();
            //change it to dcm code instead of pos detail
            String sql ="select * from dcm_pos_detail where pos_detail_id = '"+posDetailId+"'";
            System.out.println("get all from pos detail id : "+sql);
            rs = stmt.executeQuery(sql);
        }
        catch (Exception e){e.printStackTrace();}
        /*finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            }
        }*/
        return rs;
    }

    public static String getSupervisorName(Connection con, String userId) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select USER_FULL_NAME from DCM_USER_DETAIL where USER_DETAIL_ID = " + userId;
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        String supervisorName = "";

        if (rs.next()) {
            supervisorName = rs.getString("USER_FULL_NAME");

        }

        stmt.close();
        rs.close();

        return supervisorName;
    }

    
    
    public static boolean updatePOSLetterCode(Connection con, String oldCode, String newCode) throws Exception {
        boolean updated = false;
        Statement stmt = con.createStatement();
        String sqlString = "update gen_dcm set dcm_code='"+newCode+"' where dcm_code='"+oldCode+"'";
        Utility.logger.debug(sqlString);
        System.out.println("update pos code sql : "+sqlString);
        stmt.executeUpdate(sqlString);
        
        String sqlString2 = "update dcm_pos_detail set pos_code='"+newCode+"' where pos_code='"+oldCode+"'";
        Utility.logger.debug(sqlString2);
        System.out.println("update pos code sql : "+sqlString2);
        stmt.executeUpdate(sqlString2);
        updated = true;
        stmt.close();
        
        return updated;
        
    }

    
    
    
    
    public static int getAvailableStkCount(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select count(*) AS rowcount from SCM_STK_STOCK where STK_STATUS_ID = 1 and stock_id=" + SCMInterfaceKey.POS_STOCK_ID;
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int rowCount = 0;

        if (rs.next()) {
            rowCount = rs.getInt("rowcount");
        }

        stmt.close();
        rs.close();

        return rowCount;
    }

    public static void updateStkStatus(Connection con, String stkNumber) throws Exception {
        Statement stat = null;
        try {
            stat = con.createStatement();
            Statement stmt = con.createStatement();
            String sqlString = "update SCM_STK_STOCK set STK_STATUS_ID = 2 where stock_Id =" + SCMInterfaceKey.POS_STOCK_ID + " and STK_NUMBER = '" + stkNumber + "'";
            Utility.logger.debug(sqlString);
            System.out.print(sqlString);
            stat.executeUpdate(sqlString);
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


    }

    public static int getPosCount(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        // String sqlString = "select count(*) AS rowcount from GEN_DCM";
        String sqlString = " select max(dcm_id) AS rowcount from GEN_DCM";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int rowCount = 0;

        if (rs.next()) {
            rowCount = rs.getInt("rowcount");
        }

        stmt.close();
        rs.close();

        return rowCount;
    }

    public static void insertStk(Connection con, String supervisorId, String stkNumber, String requestDate) {
        Statement stat = null;
        try {
            stat = con.createStatement();
            String strSql = "insert into REP_STK_SUPERVISOR (STK_ID , SUPERVISOR_ID , CREATION_DATE) values ((select STK_ID from SCM_STK_STOCK where stock_Id =" + SCMInterfaceKey.POS_STOCK_ID + " and  STK_NUMBER = '" + stkNumber + "') ," + supervisorId + ",'" + requestDate + "')";
            System.out.print(strSql);
            stat.executeUpdate(strSql);
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
    }

    public static void insertStkToOwnerWithRep(Connection con, String supervisorId, String stkNumber) {
        Statement stat = null;
        try {
            stat = con.createStatement();
            String strSql = "insert into SCM_STK_OWNER (STK_ID , DCM_ID ,DCM_USER_ID ,STK_STATUS_ID , IQRAR_RECEVING_STATUS_ID , DCM_VERIFIED_STATUS_ID) values ((select STK_ID from SCM_STK_STOCK where STK_NUMBER = '" + stkNumber + "') , 0 ," + supervisorId + ",2 ,1, 1)";
            System.out.print(strSql);
            stat.executeUpdate(strSql);
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
    }

    public static boolean checkStkNumberAvailable(Connection con, String stkNumber, String stockId) {
//        Statement stmt = con.createStatement();
        String sqlString = "select * from SCM_STK_STOCK where stock_Id =" + stockId + " and STK_NUMBER = '" + stkNumber + "' and STK_STATUS_ID = 1";
        Boolean retBoolean = DBUtil.executeSQLExistCheck(sqlString, con);
        System.out.println("retBoolean is " + retBoolean);
        return retBoolean;
//        Utility.logger.debug(sqlString);
//        ResultSet rs = stmt.executeQuery(sqlString);
//        rs = stmt.executeQuery(sqlString);
//        if (rs.next()) {
//            stmt.close();
//            rs.close();
//            return true;
//        } else {
//            stmt.close();
//            rs.close();
//            return false;
//        }
    }

    public static void insertPosSupervisor(Connection con, String supervisorId, int posId, String requestDate) {
        Statement stat = null;
        try {
            stat = con.createStatement();
            String strSql = "insert into REP_POS_SUPERVISOR (POS_ID , SUPERVISOR_ID , CREATION_DATE) values(" + posId + "," + supervisorId + ",'" + requestDate + "')";
            System.out.println(strSql);
            stat.executeUpdate(strSql);
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
    }

    public static void insertPosDcm(Connection con, int posId, String greatestCode, String level) {
        Statement stat = null;
        try {
            stat = con.createStatement();
            String strSql = "insert into GEN_DCM (DCM_ID , DCM_STATUS_ID , DCM_CODE , DCM_LEVEL_ID) values(" + posId + ", 19 , '" + greatestCode + "' ," + level + ")";

            System.out.println(strSql);
            stat.executeUpdate(strSql);
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
    }

    public static void insertRequestTrack(Connection con, String supervisorId, String posQuantity, String posFrom, String posTo, String userId, String requestDate, String stkQuantity) {
        Statement stat = null;
        try {
            stat = con.createStatement();
            String strSql = "insert into REP_KIT_TRACK (SUPERVISOR_ID , STK_QUANTITY , POS_QUANTITY , POS_FROM , POS_TO , CREATION_DATE , USER_ID) "
                    + " values(" + supervisorId + ", " + stkQuantity + " , " + posQuantity + " , " + posFrom + " , " + posTo + " , '" + requestDate + "' , " + userId + ")";
            System.out.print(strSql);
            stat.executeUpdate(strSql);
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
    }

    public static Vector getChannelList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from GEN_CHANNEL ORDER BY CHANNEL_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        ChannelModel channelModel = null;
        Vector channelList = new Vector();

        while (rs.next()) {
            channelModel = new ChannelModel();
            channelModel.setChannelId(rs.getInt("CHANNEL_ID"));
            channelModel.setChannelName(rs.getString("CHANNEL_NAME"));

            channelList.add(channelModel);
        }

        stmt.close();
        rs.close();

        return channelList;
    }

    public static Vector getLevelList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from GEN_DCM_LEVEL ORDER BY DCM_LEVEL_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        LevelModel levelModel = null;
        Vector levelList = new Vector();

        while (rs.next()) {
            levelModel = new LevelModel();
            levelModel.setLevelId(rs.getInt("DCM_LEVEL_ID"));
            levelModel.setLevelName(rs.getString("DCM_LEVEL_NAME"));

            levelList.add(levelModel);
        }

        stmt.close();
        rs.close();

        return levelList;
    }
    
    
    public static Vector getStatusList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from gen_dcm_status ORDER BY DCM_STATUS_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        StatusModel statusModel = null;
        Vector statusList = new Vector();

        while (rs.next()) {
            statusModel = new StatusModel();
            statusModel.setStatusId(rs.getInt("DCM_STATUS_ID"));
            statusModel.setStatusName(rs.getString("DCM_STATUS_NAME"));
            statusModel.setStatusDesc(rs.getString("DCM_STATUS_DESC"));

            statusList.add(statusModel);
        }

        stmt.close();
        rs.close();

        return statusList;
    }

    public static Vector getCityList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select CITY_CODE , CITY_ENGLISH from GEN_CITY ORDER BY CITY_ENGLISH ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        CityModel cityModel = null;
        Vector cityList = new Vector();

        while (rs.next()) {
            cityModel = new CityModel();
            cityModel.setCityId(rs.getInt("CITY_CODE"));
            cityModel.setCityName(rs.getString("CITY_ENGLISH"));

            cityList.add(cityModel);
        }

        stmt.close();
        rs.close();

        return cityList;
    }
    
    public static UserDataModel getUserDataByDetailId(Connection con, String userDetailId) {
        
        UserDataModel userDataModel = new UserDataModel();
        try {
            Statement stmt = con.createStatement();
            String sqlString = "select * from dcm_user_detail , dcm_user where dcm_user_detail.user_detail_id = dcm_user.user_detail_id and dcm_user.dcm_user_id = dcm_user_detail.user_id and dcm_user_detail.user_detail_id='"+userDetailId+"' ";
            System.out.println("GET USER  OF ID : "+userDetailId+" query: "+sqlString);
            if(userDetailId!=null && userDetailId.compareTo("")!=0)
            
            {
                ResultSet rs = stmt.executeQuery(sqlString);
                rs = stmt.executeQuery(sqlString);
                if (rs.next()) {
                    userDataModel = new UserDataModel();
                    userDataModel.setCreationTimestamp(rs.getString("CREATION_TIMESTAMP"));
                    userDataModel.setCreationUserId(rs.getString("CREATION_USER_ID"));
                    userDataModel.setRegionId(rs.getString("REGION_ID"));
                    userDataModel.setUserAddress(rs.getString("USER_ADDRESS"));
                    userDataModel.setUserDetailId(rs.getString("USER_DETAIL_ID"));
                    userDataModel.setUserDetailStatusId(rs.getString("USER_DETAIL_STATUS_ID"));
                    userDataModel.setUserEmail(rs.getString("USER_EMAIL"));
                    userDataModel.setUserFullName(rs.getString("USER_FULL_NAME"));
                    userDataModel.setUserId(rs.getString("USER_ID"));
                    userDataModel.setUserMobile(rs.getString("USER_MOBILE"));
                    userDataModel.setDcmUserId("DCM_USER_ID");
                    userDataModel.setManagerDcmUserId("MANAGER_DCM_USER_ID");
                    userDataModel.setUserLevelTypeId("USER_LEVEL_TYPE_ID");
                }
                stmt.close();
                rs.close();
            }
            
            

        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return userDataModel;
    }
    
       public static Vector getUserDataList(Connection con, int regionId, int userLevelTypeId) {
        Vector userDataList = new Vector();
        
        
/*
1	Regional Manager
2	Manager
3	Sales Agent
0	Admin
4	Supervisor
5	Team Leader
6	Sales Rep
*/

/*
5	Area
1	Region
2	Governorate
3	City
4	District
*/

        
        
        try {
            Statement stmt = con.createStatement();
            String sqlString = "select *  from dcm_user_detail, dcm_user where dcm_user_detail.user_detail_id = dcm_user.user_detail_id  and dcm_user.user_level_type_id = "+userLevelTypeId+" and dcm_user_detail.region_id="+regionId; //4 is for Supervisors; get all supervisors details in Region(Greater Cairo, Delta, Alex,Upper Egypt, Canal North Sinai)
            System.out.println("Supervisors of type id : " +userLevelTypeId+" for region : "+regionId+"   sql   "+sqlString);
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);
            UserDataModel userDataModel = null;

            while (rs.next()) {
                userDataModel = new UserDataModel();
                userDataModel.setCreationTimestamp(rs.getString("CREATION_TIMESTAMP"));
                userDataModel.setCreationUserId(rs.getString("CREATION_USER_ID"));
                userDataModel.setRegionId(rs.getString("REGION_ID"));
                userDataModel.setUserAddress(rs.getString("USER_ADDRESS"));
                userDataModel.setUserDetailId(rs.getString("USER_DETAIL_ID"));
                userDataModel.setUserDetailStatusId(rs.getString("USER_DETAIL_STATUS_ID"));
                userDataModel.setUserEmail(rs.getString("USER_EMAIL"));
                userDataModel.setUserFullName(rs.getString("USER_FULL_NAME"));
                userDataModel.setUserId(rs.getString("USER_ID"));
                userDataModel.setUserMobile(rs.getString("USER_MOBILE"));
                userDataModel.setDcmUserId(rs.getString("DCM_USER_ID"));
                userDataModel.setManagerDcmUserId("MANAGER_DCM_USER_ID");
                userDataModel.setUserLevelTypeId("USER_LEVEL_TYPE_ID");
                userDataList.add(userDataModel);
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return userDataList;
    }
public static Vector getUserChildDataList(Connection con, int managerId, int regionId, int userLevelTypeId) {
        Vector userDataList = new Vector();
        
        
/*
1	Regional Manager
2	Manager
3	Sales Agent
0	Admin
4	Supervisor
5	Team Leader
6	Sales Rep
*/

/*
5	Area
1	Region
2	Governorate
3	City
4	District
*/

        
        
        try {
            Statement stmt = con.createStatement();
            String sqlString = "select * from dcm_user_detail where user_id in (select dcm_user_id from dcm_user where user_level_type_id ="+userLevelTypeId+"and manager_dcm_user_id="+managerId+" ) and dcm_user_detail.region_id="+regionId; 
            System.out.println("supervisor children query   "+sqlString);
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);
            UserDataModel userDataModel = null;

            while (rs.next()) {
                userDataModel = new UserDataModel();
                userDataModel.setCreationTimestamp(rs.getString("CREATION_TIMESTAMP"));
                userDataModel.setCreationUserId(rs.getString("CREATION_USER_ID"));
                userDataModel.setRegionId(rs.getString("REGION_ID"));
                userDataModel.setUserAddress(rs.getString("USER_ADDRESS"));
                userDataModel.setUserDetailId(rs.getString("USER_DETAIL_ID"));
                userDataModel.setUserDetailStatusId(rs.getString("USER_DETAIL_STATUS_ID"));
                userDataModel.setUserEmail(rs.getString("USER_EMAIL"));
                userDataModel.setUserFullName(rs.getString("USER_FULL_NAME"));
                userDataModel.setUserId(rs.getString("USER_ID"));
                userDataModel.setUserMobile(rs.getString("USER_MOBILE"));
                //userDataModel.setDcmUserId(rs.getString("DCM_USER_ID"));
                //userDataModel.setManagerDcmUserId("MANAGER_DCM_USER_ID");
                //userDataModel.setUserLevelTypeId("USER_LEVEL_TYPE_ID");
                userDataList.add(userDataModel);
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return userDataList;
    }


    public static Vector getAllRegionDataList(Connection con) {
        Vector placeDataList = new Vector();
        try {
            Statement stmt = con.createStatement();
            String sqlString = "select REGION_ID , REGION_NAME , PARENT_REGION_ID , REGION_LEVEL_TYPE_ID from DCM_REGION where REGION_STATUS_TYPE_ID <> 3 ORDER BY REGION_NAME ASC";
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);
            PlaceDataModel placeDataModel = null;

            while (rs.next()) {
                placeDataModel = new PlaceDataModel();
                placeDataModel.setRegionId(rs.getInt("REGION_ID"));
                placeDataModel.setRegionName(rs.getString("REGION_NAME"));
                placeDataModel.setTypeId(rs.getInt("REGION_LEVEL_TYPE_ID"));
                placeDataModel.setParentId(rs.getInt("PARENT_REGION_ID"));
                placeDataList.add(placeDataModel);
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return placeDataList;
    }

    public static Vector getUserLevelTypes(Connection con) {
        Vector userLevelList = new Vector();
        try {
            Statement stmt = con.createStatement();
            String sqlString = "select * from dcm_user_level_type";
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);
            UserLevelType userLevelModel = null;

            while (rs.next()) {
                userLevelModel = new UserLevelType();
                userLevelModel.setUserLevelTypeId(rs.getString("USER_LEVEL_TYPE_ID"));
                userLevelModel.setUserLevelTypeName(rs.getString("USER_LEVEL_TYPE_NAME"));
                userLevelList.add(userLevelModel);
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return userLevelList;
    }

    public static Vector getAllRegionDataListChild(Connection con, int regionID) {
        System.out.println("ALLLLLLL :"+regionID);
        Vector placeDataList = new Vector();
        try {
            Statement stmt = con.createStatement();
            String sqlString = "select REGION_ID , REGION_NAME , PARENT_REGION_ID , REGION_LEVEL_TYPE_ID from DCM_REGION where REGION_STATUS_TYPE_ID <> 3 and PARENT_REGION_ID='"+regionID+"' ORDER BY REGION_NAME ASC";
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);
            PlaceDataModel placeDataModel = null;

            while (rs.next()) {
                placeDataModel = new PlaceDataModel();
                placeDataModel.setRegionId(rs.getInt("REGION_ID"));
                placeDataModel.setRegionName(rs.getString("REGION_NAME"));
                placeDataModel.setTypeId(rs.getInt("REGION_LEVEL_TYPE_ID"));
                placeDataModel.setParentId(rs.getInt("PARENT_REGION_ID"));
                placeDataList.add(placeDataModel);
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return placeDataList;
    }

    
    
    public static Vector getDocList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from SCM_PROPOSED_DOCUMENT ORDER BY PROPOSED_DOCUMENT_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        ProposedDocument docModel = null;
        Vector docList = new Vector();

        while (rs.next()) {
            docModel = new ProposedDocument();
            docModel.setDocId(rs.getInt("PROPOSED_DOCUMENT_ID"));
            docModel.setDocName(rs.getString("PROPOSED_DOCUMENT_NAME"));

            docList.add(docModel);
        }

        stmt.close();
        rs.close();

        return docList;
    }

    public static Vector getPaymentList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from GEN_DCM_PAYMENT_LEVEL ORDER BY DCM_PAYMENT_LEVEL_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        PaymentModel paymentModel = null;
        Vector paymentList = new Vector();

        while (rs.next()) {
            paymentModel = new PaymentModel();
            paymentModel.setPaymentId(rs.getInt("DCM_PAYMENT_LEVEL_ID"));
            paymentModel.setPaymentName(rs.getString("DCM_PAYMENT_LEVEL_NAME"));

            paymentList.add(paymentModel);
        }

        stmt.close();
        rs.close();

        return paymentList;
    }

    public static Vector getIqrarTypeList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from SCM_IQRAR_RECEVING_STATUS ORDER BY IQRAR_RECEVING_STATUS_ID ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        IqrarTypeModel iqrarModel = null;
        Vector iqrarList = new Vector();

        while (rs.next()) {
            iqrarModel = new IqrarTypeModel();
            iqrarModel.setTypeId(rs.getInt("IQRAR_RECEVING_STATUS_ID"));
            iqrarModel.setName(rs.getString("NAME"));

            iqrarList.add(iqrarModel);
        }

        stmt.close();
        rs.close();

        return iqrarList;
    }

    public static Vector getPlaceList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select * from DCM_POS_PLACE_TYPE ORDER BY POS_PLACE_TYPE_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        PlaceDataModel placeModel = null;
        Vector paymentList = new Vector();

        while (rs.next()) {
            placeModel = new PlaceDataModel();
            placeModel.setRegionId(rs.getInt("POS_PLACE_TYPE_ID"));
            placeModel.setRegionName(rs.getString("POS_PLACE_TYPE_NAME"));

            paymentList.add(placeModel);
        }

        stmt.close();
        rs.close();

        return paymentList;
    }

    public static Long insertGenDcm(Connection con, POSDetailModel posModel, String statusId) {
        Long genDcmId = null;
        try {
            Statement stmt = con.createStatement();

            String posName = posModel.getPosName();
            String posCode = posModel.getPOSCode();
            String posEmail = posModel.getPosEmail();
            String posAddress = posModel.getPosAddress();

            genDcmId = Utility.getSequenceNextVal(con, "SEQ_GEN_DCM_ID");

            String sqlString = "INSERT INTO GEN_DCM (DCM_ID ,DCM_NAME, DCM_EMAIL , DCM_STATUS_ID, DCM_LEVEL_ID , DCM_ADDRESS , DCM_CODE)"
                    + "VALUES(" + genDcmId + ",'" + posName + "','" + posEmail + "'," + statusId + ",3,'" + posAddress + "','" + posCode + "')";
            stmt.executeUpdate(sqlString);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return genDcmId;
    }

    public static void updateGenDcm(Connection con, PosModel posModel, String statusId, int genDcmId, String userId) {

        try {
            Statement stmt = con.createStatement();
            
            
            String payMethod = posModel.getPaymentMethod();
            String hasSign = "";
            String reportToCalidus = "";
            
            if (posModel.isIsSignSet())
                hasSign = "1";
            else hasSign = "0";
            
            if (posModel.isReportToCalidus())
                reportToCalidus = "1";
            else reportToCalidus = "0";

            String posName = posModel.getPosDetailModel().getPosName();
            String posCode = posModel.getPosDetailModel().getPOSCode();
            String posEmail = posModel.getPosDetailModel().getPosEmail();
            String posAddress = posModel.getPosDetailModel().getPosAddress();
            String posPayment = Integer.toString(posModel.getPaymentLevelId());
            String posChannel = Integer.toString(posModel.getChannelId());
            String posLevel = Integer.toString(posModel.getLevelId());
            /*
             * Ahmed Adel add city and district
             */
            String city = Integer.toString(posModel.getCityId());
            String district = Integer.toString(posModel.getDistrictId());

            String update = "UPDATE GEN_DCM SET IS_DIRTY='0', HAS_SIGN = '"+hasSign+"', PAYMENT_TYPE_METHOD_ID ='"+payMethod+"', REPORT_TO_CALIDUS='"+reportToCalidus+"', DCM_NAME='" + posName + "' ,DCM_CITY_ID='" + city + "',DCM_DISTRICT_ID='" + district + "' ,DCM_EMAIL= '" + posEmail + "' , DCM_PAYMENT_LEVEL_ID= '" + posPayment + "' , DCM_ADDRESS='" + posAddress + "' ,DCM_STATUS_ID='" + statusId + "' ,CHANNEL_ID='" + posChannel + "',DCM_LEVEL_ID= '" + posLevel + "' WHERE DCM_ID= " + genDcmId;

            System.out.print(update);
            stmt.execute(update);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Change Request for disabling changing the status. By Ahmed Safwat 31 May
     * 2011
     *
     */
    public static void updateGenDcmUpdated(Connection con, PosModel posModel, int genDcmId, String userId) {
        System.out.println("lamya inside updateGenDcmUpdated in RequestDao.java");
        try {
            Statement stmt = con.createStatement();
            //lamya
            String payMethod = posModel.getPaymentMethod();
            String hasSign = "";
            String reportToCalidus = "";
            String isEX ="";
            String isQC ="";
            String isL1 ="";
            String isMobicash ="";
            String isNomad ="";
            
            
            if (posModel.isIsMobicash())
                isMobicash = "1";
            else isMobicash = "0";
            
            if (posModel.isIsNomad())
                isNomad = "1";
            else isNomad = "0";
            
            
            if (posModel.isIsSignSet())
                hasSign = "1";
            else hasSign = "0";
            
            if (posModel.isReportToCalidus())
                reportToCalidus = "1";
            else reportToCalidus = "0";
            
            ////////////////////
            if (posModel.isIsEX())
                isEX = "1";
            else isEX = "0";
            
            if (posModel.isIsL1())
                isL1 = "1";
            else isL1 = "0";
            
            if (posModel.isIsQC())
                isQC = "1";
            else isQC = "0";
            
            //lamya
            String posName = posModel.getPosDetailModel().getPosName();
            String posCode = posModel.getPosDetailModel().getPOSCode();
            String posEmail = posModel.getPosDetailModel().getPosEmail();
            String posAddress = posModel.getPosDetailModel().getPosAddress();
            String posPayment = Integer.toString(posModel.getPaymentLevelId());
            String posChannel = Integer.toString(posModel.getChannelId());
            String posLevel = Integer.toString(posModel.getLevelId());
            long mobicashNum = posModel.getMobicashNum();
            /*
             * Ahmed Adel add city and district
             */
            String city = Integer.toString(posModel.getCityId());
            String district = Integer.toString(posModel.getDistrictId());

            String update = "UPDATE GEN_DCM SET MOBICASH_NUMBER= "+mobicashNum+" ,PAYMENT_TYPE_METHOD_ID = '"+payMethod+"' , DCM_NAME='" + posName + "' ,DCM_CITY_ID='" + city + "',DCM_DISTRICT_ID='" + district + "' ,DCM_EMAIL= '" + posEmail + "' , DCM_PAYMENT_LEVEL_ID= '" + posPayment + "' , DCM_ADDRESS='" + posAddress + "' ,CHANNEL_ID='" + posChannel + "',DCM_LEVEL_ID= '" + posLevel + "' , REPORT_TO_CALIDUS ='"+reportToCalidus+"', IS_DIRTY = '1' ,IS_EXCLUSIVE = '"+isEX+"', IS_LEVEL_ONE = '"+isL1+"' , IS_QUALITY_CLUB = '"+isQC+"', HAS_SIGN = '"+hasSign+"', IS_MOBICASH = '"+isMobicash+"', IS_NOMAD = '"+isNomad+"' WHERE DCM_ID= " + genDcmId;

            System.out.print(update);
            stmt.execute(update);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    
    public static void updateUserDetailData(Connection con, UserDataModel userDetail) {
        System.out.println("UPDATE.....");
        try {
            Statement stmt = con.createStatement();
            String update = "UPDATE dcm_user_detail SET user_full_name= '"+userDetail.getUserFullName()+"' , user_email = '"+userDetail.getUserEmail()+"' , user_address='" + userDetail.getUserAddress() + "' ,user_mobile='" + userDetail.getUserMobile() + "' WHERE user_detail_id='"+userDetail.getUserDetailId()+"'";
            System.out.print("update supervisor data query : "+update);
            stmt.execute(update);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    
    public static void insertUserDetailData(Connection con, UserDataModel userDetail, int userLevelTypeId, int managerId, int regionId) {
        System.out.println("lamya inside insertUserDetailData in RequestDao");
        String userId="";
        String userDetailId="";
        try {
            Statement stmt = con.createStatement();
            
            String insert = "insert into dcm_user (dcm_user_id,user_id,manager_dcm_user_id,user_level_type_id,user_status_type_id,region_id,user_level_id) values ((select max(dcm_user_id)+1 from dcm_user),456,"+managerId+","+userLevelTypeId+",1,"+regionId+",0)";
            stmt.executeUpdate(insert);
            String selectId = "select dcm_user_id from dcm_user order by dcm_user_id desc";
            ResultSet rs = stmt.executeQuery(selectId);
            if (rs.next())
                userId = rs.getString("dcm_user_id");
            rs.close();
            
            String insert2= "insert into dcm_user_detail (user_detail_id,user_id,user_full_name,user_address,user_email,user_mobile,region_id,user_detail_status_id,creation_timestamp,creation_user_id) values ((select max(user_detail_id)+1 from dcm_user_detail),"+userId+",'"+userDetail.getUserFullName()+"','"+userDetail.getUserAddress()+"','"+userDetail.getUserEmail()+"','"+userDetail.getUserMobile()+"',291,1,sysdate,54)";
            stmt.executeUpdate(insert2);
            String selectId2 = "select user_detail_id from dcm_user_detail order by user_detail_id desc";
            ResultSet rs2 = stmt.executeQuery(selectId2);
            if (rs2.next())
                userDetailId = rs2.getString("user_detail_id");
            rs2.close();
            
            String update = "update dcm_user set user_detail_id="+userDetailId+" where dcm_user_id="+userId;
            stmt.execute(update);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    
///////////////////////// calidus ////////////////////////////////////////////////////
   /* public static ResultSet getCalidusData(Connection con) throws Exception{
    
        System.out.println("inside getCalidusData");
        ResultSet rs = null;
        Statement st = con.createStatement();
        String sql = "SELECT gen_dcm.dcm_code                            AS PAYEEID,\n" +
"    dcm_name                                         AS English_Name,\n" +
"    cam_payment_type_method.payment_type_method_name AS Payment_Method,\n" +
"    gen_channel.channel_name                         AS Channel_Name,\n" +
"    gen_channel.channel_id                           AS Channel_Code,\n" +
"    gen_dcm.dcm_code                                 AS POS_Code,\n" +
"    dcm_name                                         AS POS_English_Name,\n" +
"    pos_arabic_name                                  AS POS_Arabic_Name,\n" +
"    dcm_pos_owner.pos_owner_name                     AS Owner_Name,\n" +
"    dcm_pos_owner.pos_owner_id_number                AS ID_NUMBER,\n" +
"    dcm_pos_owner.pos_owner_id_type_id               AS ID_Type,\n" +
"    region_name                                      AS Sales_Region,\n" +
"    gen_city.city_english                            AS City,\n" +
"    ''                                               AS Governerate,\n" +
"    gen_district.district_code                       AS DistrictID,\n" +
"    gen_district.district_english                    AS District_Name,\n" +
"    NULL                                             AS Im_District,\n" +
"    NULL                                             AS Area_Code,\n" +
"    dcm_pos_detail.pos_area_id                       AS Area,\n" +
"    pos_address                                      AS Address,\n" +
"    pos_doc_num                                      AS Doc_NUMBER,\n" +
"    NULL                                             AS Documents,\n" +
"    dcm_pos_detail.survey_date                       AS Entry_DATE,\n" +
"    dcm_user_status_type.user_status_type_name       AS POS_Status,\n" +
"    dcm_pos_owner_phone.pos_owner_phone_number       AS Owner_Phone,\n" +
"    gen_dcm_level.dcm_level_id                       AS Level_Code,\n" +
"    NULL                                             AS Regional_Name,\n" +
"    NULL                                             AS Team_Leader,\n" +
"    NULL                                             AS Rep_PName,\n" +
"    ''                                               AS STK_Dial_No,\n" +
"    ''                                               AS STK_Status,\n" +
"    scm_stk_owner.stk_active_date                    AS STK_Active_Date,\n" +
"    scm_stk_owner.iqrar_recieved_date                AS Iqrar_Received_Date,\n" +
"    ''                                               AS Pay_Status,\n" +
"    gen_dcm_payment_level.dcm_payment_level_name     AS Pay_Level_Name,\n" +
"    pos_arabic_address                               AS Arabic_Address,\n" +
"    NULL                                             AS IsIqrar_Received,\n" +
"    NULL                                             AS IsVerified,\n" +
"    dcm_pos_detail.doc_location                      AS Document_Location,\n" +
"    dcm_pos_detail.survey_id                         AS Survey_ID,\n" +
"    dcm_pos_owner_phone.pos_owner_phone_number       AS POS_Owner_Phone_No,\n" +
"    NULL                                             AS Branch,\n" +
"    NULL                                             AS MBB_Rep,\n" +
"    NULL                                             AS IM_District_Code,\n" +
"    NULL                                             AS IsL1,\n" +
"    NULL                                             AS IsEx,\n" +
"    gen_dcm.has_sign                                 AS SIGN,\n" +
"    NULL                                             AS IsQC,\n" +
"    NULL                                             AS Commercial_Gov\n" +
"  FROM gen_dcm,\n" +
"    dcm_pos_detail,\n" +
"    cam_payment_type_method,\n" +
"    gen_channel,\n" +
"    dcm_region,\n" +
"    gen_dcm_payment_level,\n" +
"    DCM_USER_STATUS_TYPE,\n" +
"    gen_dcm_level,\n" +
"    scm_stk_owner,\n" +
"    dcm_pos_owner,\n" +
"    dcm_pos_owner_phone,\n" +
"    gen_city,\n" +
"    gen_district,\n" +
"    calidus_current_file\n" +
"  WHERE gen_dcm.dcm_code                     = dcm_pos_detail.pos_code (+)\n" +
"  AND dcm_pos_detail.flage                  IS NULL\n" +
"\n" +
"  AND gen_dcm.payment_type_method_id         = cam_payment_type_method.payment_type_method_id(+)\n" +
"  AND gen_dcm.channel_id                     = gen_channel.channel_id (+)\n" +
"  AND dcm_pos_detail.region_id               = dcm_region.region_id (+)\n" +
"  AND gen_dcm.dcm_payment_level_id           = gen_dcm_payment_level.dcm_payment_level_id (+)\n" +
"  AND dcm_pos_detail.pos_status_type_id      = DCM_USER_STATUS_TYPE.user_status_type_id (+)\n" +
"  AND gen_dcm.dcm_level_id                   = gen_dcm_level.dcm_level_id (+)\n" +
"  AND dcm_pos_detail.pos_id                  = scm_stk_owner.dcm_id (+)\n" +
"  AND dcm_pos_detail.pos_detail_id           = dcm_pos_owner.pos_Detail_id (+)\n" +
"  AND dcm_pos_owner.pos_owner_id             = dcm_pos_owner_phone.pos_owner_id (+)\n" +
"  AND gen_city.city_code                     = gen_dcm.dcm_city_id (+)\n" +
"  AND dcm_pos_detail.pos_district_id         = gen_district.district_code (+)\n" +
"  AND gen_dcm.dcm_code                       = calidus_current_file.dcm_code (+)\n" ;
        rs = st.executeQuery(sql);
        System.out.println("calidus result set : "+rs);
       // st.close();
        return rs;
    }
   */
    
    public static ResultSet getCalidusView(Connection con) throws Exception{
    
        System.out.println("inside getCalidusData");
        ResultSet rs = null;
        Statement st = con.createStatement();
        String sql = "select * from calidus_view";
        rs = st.executeQuery(sql);
        System.out.println("calidus result set : "+rs);
       // st.close();
        return rs;
    }
    public static boolean insertCalidusCurrent(Connection con) throws Exception{
        System.out.println("beginning ........");
        boolean inserted = false;
        Statement st = con.createStatement();
        //String sql= "select * from gen_dcm where report_to_calidus='1' ";
        //String posCode="";
        //ResultSet rs = st.executeQuery(sql);
        //while (rs.next())
       // {
          //  System.out.println("inside insert if result : "+rs+" before insert");
           // posCode = rs.getString("dcm_code");
            //String sql2 = "insert into calidus_reporting (dcm_code) select dcm_code from gen_dcm where report_to_calidus='1' ";
            String sql3 = "insert into calidus_current_file (dcm_code,scm_record_status) select dcm_code,'new' from gen_dcm where report_to_calidus='1'";
            //String sql4="update gen_dcm set is_dirty='1' where dcm_code in (select dcm_code from calidus_reporting) ";
           // String sql5="update dcm_pos_detail set is_dirty='1'where pos_code in (select dcm_code from calidus_reporting) ";
            //st.executeQuery(sql2);
            //System.out.println("after insert 1");
            st.executeQuery(sql3); 
            System.out.println("after insert 2");
           // st.executeUpdate(sql4);
           // System.out.println("after update1");
            
           // st.executeUpdate(sql5);
           // System.out.println("after update2");
       // }
        st.close();
        inserted = true;
        return inserted;
    
    }
    public static boolean addRemovedCurrentRecords(Connection con) throws Exception{
        boolean added = false;
        Statement st = con.createStatement();
        String sql= "insert into calidus_current_file (dcm_code,scm_record_status) select dcm_code,'removed' from gen_dcm where report_to_calidus='0' and dcm_code in (select dcm_code from calidus_reporting)";
        st.executeQuery(sql);
        st.close();
        added = true;
        return added;
    
    }
    public static boolean updateCalidusCurrent(Connection con) throws Exception{
        boolean updated = false;
        Statement st = con.createStatement();
        //and dcm_code in (select dcm_code from gen_dcm where is_dirty = '1'
        String sql= "update calidus_current_file set scm_record_status = 'changed' where dcm_code in (select dcm_code from calidus_reporting) ";
      //  String sql2 = "update calidus_current_file set scm_record_status = 'removed' where dcm_code in (select dcm_code from calidus_reporting) and dcm_code in (select dcm_code from gen_dcm where report_to_calidus='0')";    
       // String posCode = "";
        ResultSet rs = st.executeQuery(sql);
        System.out.println("inside update calidus");
        /*while(rs.next())
        {
            System.out.println("inside while update");
            posCode = rs.getString("dcm_code");
            if (rs.getString("report_to_calidus").equals("1"))
            {
                System.out.println("inside if");
                String sql2 = "update calidus_current_file set scm_record_status = 'changed' where dcm_code = '"+posCode+"' ";
                st.executeUpdate(sql2);
            }
            else if (rs.getString("report_to_calidus").equals("0"))
            {
                System.out.println("inside else");
                String sql2 = "update calidus_current_file set scm_record_status = 'removed' where dcm_code = '"+posCode+"' ";
                st.executeUpdate(sql2);
            }
            System.out.println("go back to while");
            
        }*/
        st.close();
        updated = true;
        return updated;
    
    }
    
    
    
    public static boolean deleteCalidusCurrent(Connection con) throws Exception{
    
        boolean deleted = false;
        Statement st = con.createStatement();
        String sql = "delete from calidus_current_file where scm_record_status = 'changed' and dcm_code in (select dcm_code from gen_dcm where is_dirty = '0') ";
        ResultSet rs = st.executeQuery(sql);
        System.out.println("inside delete calidus");
       // String posCode="";
     /*   while(rs.next())
        {
            posCode = rs.getString("dcm_code");
            String sql2 = "delete from calidus_current_file where scm_record_status = 'changed' and dcm_code = '"+posCode+"' ";
            st.executeUpdate(sql2);
            
        }*/
        st.close();
        deleted = true;
        return deleted;
    
    }
    public static boolean addRecordsToReporting(Connection con) throws Exception
    {
        boolean added = false;
        Statement st = con.createStatement();
        String sql= "insert into calidus_reporting (dcm_code) select dcm_code from calidus_current_file where scm_record_status = 'new' ";
        st.executeQuery(sql);
        st.close();
        added = true;
        return added;
        
    
    }
    public static boolean insertCalidusFile(Connection con, String zipFileName) throws Exception{
        
        boolean inserted = false;
        Statement st = con.createStatement();
        String sql = "insert into CALIDUS_FILES_STATUS (FILE_STATUS,file_name) values ('created','"+zipFileName+"') ";
        st.executeQuery(sql);
        System.out.println("insert file");
        st.close();
        inserted = true;
        return inserted;
        
    
    }
    public static Vector getCalidusCreatedFiles(Connection con) throws Exception{
    
        /*System.out.println("inside getCalidusCreatedFiles");
        ResultSet rs = null;
        Statement st = con.createStatement();
        String sql = "select file_name from calidus_files_status";
        rs = st.executeQuery(sql);
        st.close();
        return rs;*/
        Statement stmt = con.createStatement();
        String sqlString = "select * from calidus_files_status ORDER BY file_creation_date ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        CalidusZipFileModel zipFileModel = null;
        Vector zipFileList = new Vector();

        while (rs.next()) {
            zipFileModel = new CalidusZipFileModel();
            zipFileModel.setFileCreationDate(rs.getString("file_creation_date"));
            zipFileModel.setFileName(rs.getString("file_name"));
            zipFileModel.setFileStatus(rs.getString("file_status"));

            zipFileList.add(zipFileModel);
        }

        stmt.close();
        rs.close();

        return zipFileList;
    }
    //after generating the calidus file
    public static boolean deleteCalidusReporting(Connection con) throws Exception{
        boolean deleted = false;
        Statement st = con.createStatement();
        String sql = "delete from calidus_reporting where dcm_code in (select dcm_code from calidus_current_file where scm_record_status = 'removed') ";
        st.executeQuery(sql);
        System.out.println("inside delete calidus");
        st.close();
        deleted = true;
        return deleted;
    
    }
    
    public static void clearCalidusCurrent(Connection con) throws Exception{
        Statement st = con.createStatement();
        String trunc = "truncate table calidus_current_file";
       // String trunc2 = "truncate table calidus_reporting";
        st.executeUpdate(trunc);
        //st.executeUpdate(trunc2);
        st.close();
    }
          
    
    public static boolean clearDirtyFlagForCalidusRecords(Connection con) throws Exception{
        
        boolean deleted = false;
        Statement st = con.createStatement();
        String update1 = "update gen_dcm set is_dirty='0' where report_to_calidus='1' and dcm_code in (select dcm_code from calidus_reporting) ";
        String update2 = "update dcm_pos_detail set is_dirty='0' where report_to_calidus='1' and pos_code in (select dcm_code from calidus_reporting) ";
        //String trunc = "truncate table calidus_current_file";
        st.executeUpdate(update1);
        st.executeUpdate(update2);
        //st.executeUpdate(trunc);
        deleted = true;
        return deleted;
        
    
    }        
   /* public static boolean updateCalidusCurrentChanged(Connection con) throws Exception{
        boolean updated = false;
        Statement st = con.createStatement();
        String sql= "select * from gen_dcm where report_to_calidus='1' and is_dirty='1' and dcm_code in (select dcm_code from calidus_reporting) ";
        ResultSet rs = st.executeQuery(sql);
        String posCode="";
        while(rs.next())
        {
            posCode = rs.getString("dcm_code");
            String sql2 = "update calidus_current_file set scm_record_status = 'changed' where dcm_code = '"+posCode+"' ";
            st.executeUpdate(sql2);
            
        }
        updated = true;
        return updated;
    
    }*/
    
  //////////////////////////////////////////////  calidus  //////////////////////////////////////////////////////////  
    public static Vector getPaymentMethodList(Connection con) throws Exception {
    
        Statement stmt = con.createStatement();
        String sqlString = "select * from cam_payment_type_method ORDER BY payment_type_method_name ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        PaymentMethodModel paymentMethodModel = null;
        Vector paymentMethodList = new Vector();

        while (rs.next()) {
            paymentMethodModel = new PaymentMethodModel();
            paymentMethodModel.setPaymentMethodId(rs.getInt("payment_type_method_ID"));
            paymentMethodModel.setPaymentMethodName(rs.getString("payment_type_method_name"));

            paymentMethodList.add(paymentMethodModel);
        }

        stmt.close();
        rs.close();

        return paymentMethodList;
        
    }
    
    
    /**
     * End of Change
     * 
     *
     */
    private static boolean bulkUpdatePayMethod(Connection con,String dcmCode, String payMethod)
    {
                boolean updated = false;
                Statement stmt = null;
                ResultSet rs = null;
                
                try{
                    stmt = con.createStatement();
                    String myPayMethod = "";
                    String sql = "select payment_type_method_id from cam_payment_type_method where payment_type_method_name = '"+payMethod+"'";
                    
                    rs = stmt.executeQuery(sql);
                    if (rs.next())
                        myPayMethod = rs.getString("payment_type_method_id");


                    String update = "UPDATE GEN_DCM SET PAYMENT_TYPE_METHOD_ID = '"+myPayMethod+"' , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";

                    stmt.execute(update);
                    //updateGenDcmUpdated(con, posModel, posId, strUserID);



                    Long posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");
                    Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
                    String sqlDeleteDetail = "update dcm_pos_detail set PAYMENT_TYPE_METHOD_ID = '"+myPayMethod+"' , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
                    DBUtil.executeSQL(sqlDeleteDetail, con);
                    
                    updated = true;
                    }catch (Exception e){}
                return updated ;

                //deletePosDetailForEdit(con, posDetailId, strUserID);
                
                

               /* String sqlString = "INSERT INTO DCM_POS_DETAIL "
                    + "(IS_DIRTY,POS_DETAIL_ID, POS_ID ,POS_CODE,"
                    + "POS_NAME,POS_EMAIL,"
                    + "POS_ADDRESS,POS_STATUS_TYPE_ID,REGION_ID,"
                    + "UPDATED_IN,USER_ID"
                    + ", POS_CHANNEL_ID , POS_BRANCH_OF , POS_GOVERNRATE , POS_AREA_ID , POS_DEMO_LINE , POS_PROPOSED_DOC_ID"
                    + " , POS_DOC_NUM , POS_RATE_ID , POS_PLACE_TYPE_ID , POS_DISTRICT_ID , POS_CITY_ID,SURVEY_DATE,SURVEY_ID,DCM_PAYMENT_LEVEL_ID,POS_ARABIC_NAME,POS_ARABIC_ADDRESS,HAS_SIGN,REPORT_TO_CALIDUS, PAYMENT_TYPE_METHOD_ID,DOC_LOCATION)"
                    + "values('1'," + posDetailId
                    + ",'" + dcmId + "','" + posCode + "','" + posName + "','" + posEmail + "','" + posAddress + "','" + statusTypeId + "','" + posRegion + "'"
                    + ",sysdate,'" + UserID + "', " + posModel.getChannelId() + " , '" + posModel.getBranchOf() + "', " + posModel.getGovernateId()
                    + " , " + posModel.getAreaId() + " , '" + posModel.getDemoLineNum() + "' , " + (posModel.getProposedDocId() == -1 ? "null" : posModel.getProposedDocId())
                    + " , '" + posModel.getDocNumber() + "' , " + 4 + " , " + posModel.getRateID() + " , " + posModel.getDistrictId() + " , " + posModel.getCityId() + "," + "to_date(sysdate,'DD-MM-YY')" + ",'" + SurveyID + "'," + posPayment + ",'" + posArabicName + "','" + posArabicAddress + "','" + hasSign + "','" + reportToCalidus + "','" + payMethod + "','" + posModel.getDocLocation() + "')";

                stmt.executeUpdate(sqlString);*/
                
                //insertPosDetail(con, posModel, posId, "1");
    }
    
    
    
    private static boolean bulkUpdateDCMLevel(Connection con,String dcmCode, String dcmLevel)
    {
                boolean updated = false;
                Statement stmt = null;
                ResultSet rs = null;
                try{
                    stmt = con.createStatement();
                    String dcmLevelId = "";

                    String sql = "select dcm_level_id from gen_dcm_level where dcm_level_name = '"+dcmLevel+"'";

                    rs = stmt.executeQuery(sql);
                    if (rs.next())
                        dcmLevelId = rs.getString("dcm_level_id");


                    String update = "UPDATE GEN_DCM SET DCM_LEVEL_ID = '"+dcmLevelId+"' , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";

                    stmt.execute(update);
                    //updateGenDcmUpdated(con, posModel, posId, strUserID);



                    Long posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");
                    Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
                    String sqlDeleteDetail = "update dcm_pos_detail set PAYMENT_TYPE_METHOD_ID = '"+dcmLevelId+"' , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
                    DBUtil.executeSQL(sqlDeleteDetail, con);
                    updated = true;
                    }catch (Exception e){}
                return updated ;

                //deletePosDetailForEdit(con, posDetailId, strUserID);
                
                

               /* String sqlString = "INSERT INTO DCM_POS_DETAIL "
                    + "(IS_DIRTY,POS_DETAIL_ID, POS_ID ,POS_CODE,"
                    + "POS_NAME,POS_EMAIL,"
                    + "POS_ADDRESS,POS_STATUS_TYPE_ID,REGION_ID,"
                    + "UPDATED_IN,USER_ID"
                    + ", POS_CHANNEL_ID , POS_BRANCH_OF , POS_GOVERNRATE , POS_AREA_ID , POS_DEMO_LINE , POS_PROPOSED_DOC_ID"
                    + " , POS_DOC_NUM , POS_RATE_ID , POS_PLACE_TYPE_ID , POS_DISTRICT_ID , POS_CITY_ID,SURVEY_DATE,SURVEY_ID,DCM_PAYMENT_LEVEL_ID,POS_ARABIC_NAME,POS_ARABIC_ADDRESS,HAS_SIGN,REPORT_TO_CALIDUS, PAYMENT_TYPE_METHOD_ID,DOC_LOCATION)"
                    + "values('1'," + posDetailId
                    + ",'" + dcmId + "','" + posCode + "','" + posName + "','" + posEmail + "','" + posAddress + "','" + statusTypeId + "','" + posRegion + "'"
                    + ",sysdate,'" + UserID + "', " + posModel.getChannelId() + " , '" + posModel.getBranchOf() + "', " + posModel.getGovernateId()
                    + " , " + posModel.getAreaId() + " , '" + posModel.getDemoLineNum() + "' , " + (posModel.getProposedDocId() == -1 ? "null" : posModel.getProposedDocId())
                    + " , '" + posModel.getDocNumber() + "' , " + 4 + " , " + posModel.getRateID() + " , " + posModel.getDistrictId() + " , " + posModel.getCityId() + "," + "to_date(sysdate,'DD-MM-YY')" + ",'" + SurveyID + "'," + posPayment + ",'" + posArabicName + "','" + posArabicAddress + "','" + hasSign + "','" + reportToCalidus + "','" + payMethod + "','" + posModel.getDocLocation() + "')";

                stmt.executeUpdate(sqlString);*/
                
                //insertPosDetail(con, posModel, posId, "1");
    }
    
    
    
   /* private static boolean bulkUpdateSign(Connection con,String dcmCode, String hasSign)
    {
                System.out.println("inside update sign");
                boolean updated = false;
                String myHasSign = "";
                Statement stmt = null;
                try{
                
                stmt =  con.createStatement();
                if (hasSign.equals("Yes"))
                    myHasSign = "1";
                if (hasSign.equals("No"))
                    myHasSign = "0";
                
                String update = "UPDATE GEN_DCM SET HAS_SIGN = '"+myHasSign+"' , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";
                stmt.execute(update);
                System.out.println("after update sql in sign");
                //updateGenDcmUpdated(con, posModel, posId, strUserID);
                
                
                
                Long posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");
                Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
                String sqlDeleteDetail = "update dcm_pos_detail set HAS_SIGN = '"+myHasSign+"' , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
                DBUtil.executeSQL(sqlDeleteDetail, con);
                updated = true;
                }catch (Exception e){}
                return updated;

                //deletePosDetailForEdit(con, posDetailId, strUserID);
    }*/
    
    
    
    public static Vector getListedButUnupdatedDCMCodes(String tableId, String fieldName)
    {
        //this function is for listing the dcm codes that got updated with their features from excel sheet and do not exist in table gen_dcm in database
        
        
        
        Vector codes = new Vector();
        Statement stmt = null;
        String sql = "select table_name from adm_data_import_def where table_id ='"+tableId+"'";
        UnupdatedCode code = new UnupdatedCode();
        String tableName = "";
        try
        {
            
                Connection con = Utility.getConnection();
                stmt = con.createStatement();
                ResultSet rs= stmt.executeQuery(sql);
                if (rs.next())
                    tableName = rs.getString("table_name");
                if(fieldName.contains(","))
                    fieldName = fieldName.substring(0, fieldName.indexOf(" "));

                System.out.println("FIELD NAME : "+fieldName);
                sql = "select "+tableName+".dcm_code, "+tableName+"."+fieldName+"  from "+tableName+" , gen_dcm where "+tableName+".dcm_code = gen_dcm.dcm_code (+) and gen_dcm.dcm_code is null";
                System.out.println("Unupdated dcm codes sql : "+sql);
                rs = stmt.executeQuery(sql);
                while(rs.next())
                    {
                        code = new UnupdatedCode();
                        code.setCode(rs.getString("dcm_code"));
                       // code.setFeatureValue(rs.getString(fieldName));
                       // code.setFeature(fieldName);
                        code.setFeature(rs.getString(fieldName));
                        codes.add(code);
                    }

                String truncate = "truncate table "+tableName;
                System.out.println(truncate);
                DBUtil.executeSQL(truncate, con);
                System.out.println("codes updated but do not exist in table gen_dcm : "+codes);
        }
        catch (Exception e){}
        return codes;
    }
    private static boolean isNumeric(String str)  
        {  
          try  
          {  
            long d = Long.parseLong(str);  
          }  
          catch(NumberFormatException nfe)  
          {  
            return false;  
          }  
          return true;  
        }
    private static boolean bulkUpdateFeature(Connection con,String dcmCode, String featureName, String featureValue)
    {
        boolean updated = false;
        Statement stmt = null;
        String update="";
        String sqlDeleteDetail="";
        try{
            stmt =  con.createStatement();
            Long posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");
            Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
            if (!isNumeric(featureValue))
            {
                if (featureValue.compareToIgnoreCase("Yes")==0)
                    featureValue = "1";
                else featureValue = "0";
                update = "UPDATE GEN_DCM SET "+featureName+" = '"+featureValue+"' , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";
                sqlDeleteDetail = "update dcm_pos_detail set "+featureName+" = '"+featureValue+"' , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_code='"+dcmCode+"'";// and pos_Detail_id=" + posDetailId;
                
            }
            System.out.println("featureValue before UPDATE : "+featureValue);
            
            update = "UPDATE GEN_DCM SET "+featureName+" = "+featureValue+" , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";
            System.out.println("sql in bulkUpdate: "+update);
            stmt.execute(update);
            sqlDeleteDetail = "update dcm_pos_detail set "+featureName+" = "+featureValue+" , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_code='"+dcmCode+"'";// and pos_Detail_id=" + posDetailId;
            System.out.println("sql2 in bulkUpdate: "+sqlDeleteDetail);
            stmt.execute(sqlDeleteDetail);            
            //DBUtil.executeSQL(sqlDeleteDetail, con);
            updated = true;
        }
        catch (Exception e){}
        return updated;
    }
  /*  private static boolean bulkUpdateNomad(Connection con,String dcmCode, String nomad)
    {
                System.out.println("inside update nomad");
                boolean updated = false;
                String myNomad = "";
                Statement stmt = null;
                try{
                
                stmt =  con.createStatement();
                if (nomad.equals("Yes"))
                    myNomad = "1";
                if (nomad.equals("No"))
                    myNomad = "0";
                
                String update = "UPDATE GEN_DCM SET IS_NOMAD = '"+myNomad+"' , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";
                stmt.execute(update);
                System.out.println("after update sql in sign");
                //updateGenDcmUpdated(con, posModel, posId, strUserID);
                
                
                
                Long posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");
                Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
                String sqlDeleteDetail = "update dcm_pos_detail set IS_NOMAD = '"+myNomad+"' , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
                DBUtil.executeSQL(sqlDeleteDetail, con);
                updated = true;
                }catch (Exception e){}
                return updated;

                //deletePosDetailForEdit(con, posDetailId, strUserID);
    }*/
 /*   private static boolean bulkUpdateCalidus(Connection con,String dcmCode, String reportToCalidus)
    {
                boolean updated = false;
                String myReportToCalidus = "";
                Statement stmt = null;
                try{
                
                stmt =  con.createStatement();
                if (reportToCalidus.equals("Yes"))
                    myReportToCalidus = "1";
                if (reportToCalidus.equals("No"))
                    myReportToCalidus = "0";
                
                String update = "UPDATE GEN_DCM SET REPORT_TO_CALIDUS = '"+myReportToCalidus+"' , IS_DIRTY = '1' WHERE DCM_CODE= '" + dcmCode+"'";
                stmt.execute(update);
                //updateGenDcmUpdated(con, posModel, posId, strUserID);
                
                
                
                Long posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");
                Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
                String sqlDeleteDetail = "update dcm_pos_detail set REPORT_TO_CALIDUS = '"+myReportToCalidus+"' , IS_DIRTY = '1',flage=1,HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
                DBUtil.executeSQL(sqlDeleteDetail, con);
                updated = true;
                }catch (Exception e){}
                return updated;
    
    }*/
        
    public static String getPOSPaymentMethod(Connection con, String posCode)
    {
        Statement stmt = null;
        String payMethod = "";
        try
        {
            stmt = con.createStatement();
            String sql = "select payment_type_method_name from cam_payment_type_method where payment_type_method_id in (select payment_type_method_id from gen_dcm where dcm_code = '"+posCode+"')";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                payMethod = rs.getString("payment_type_method_name");
        }
        catch (Exception e){}
        return payMethod;
    }
    
            
            
   public static String getPOSPaymentLevel(Connection con, String posCode)
   {
       Statement stmt = null;
        String payLevel = "";
        try
        {
            stmt = con.createStatement();
            String sql = "select dcm_payment_level_name from gen_dcm_payment_level where dcm_payment_level_id in (select dcm_payment_level_id from gen_dcm where dcm_code = '"+posCode+"')";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                payLevel = rs.getString("dcm_payment_level_name");
        }
        catch (Exception e){}
        return payLevel;
       
   }
            
   
    
    public static String bulkUpdatePOSDetailFeature  (String tableId)
    {
        
        boolean updated= false;
        System.out.println(" bullk update tble id : "+tableId);
        String sql = "select table_name from adm_data_import_def where table_id ='"+tableId+"'";
        String tableName = "";
        String fieldName = "";
        boolean tableUpdated = false;
        try
        {
            
        Connection con = Utility.getConnection();
        Statement st = con.createStatement();
        ResultSet rs= st.executeQuery(sql);
        if (rs.next())
            tableName = rs.getString("table_name");
        System.out.println(" bulk update tble name : "+tableName+"     "+rs);
        String sqlTable = "select * from " +tableName;
        ResultSet rss = st.executeQuery(sqlTable); //update table gen_dcm field from the temp table that has the same field name and dcm_code
        while (rss.next())
            {
                System.out.println(" bulk update while");    
                String dcmCode = rss.getString("dcm_code");
                System.out.println(" dcm code "+dcmCode);
                
                if (tableName.compareToIgnoreCase("DCM_PAYMENT_TEMP")==0)
                {
                    tableUpdated = bulkUpdatePayMethod(con,dcmCode,rss.getString("PAYMENT_METHOD"));
                    fieldName = "PAYMENT_METHOD";
                }
                else if (tableName.compareToIgnoreCase("DCM_SIGN_TEMP")==0)
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"HAS_SIGN",rss.getString("HAS_SIGN"));
                    fieldName = "HAS_SIGN";
                }
                else if (tableName.compareToIgnoreCase("DCM_REPORT_TO_CALIDUS_TEMP")==0)  
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"REPORT_TO_CALIDUS",rss.getString("REPORT_TO_CALIDUS"));
                    fieldName = "REPORT_TO_CALIDUS";
                }
                else if (tableName.compareToIgnoreCase("DCM_LEVEL_TEMP")==0)    
                {
                    tableUpdated = bulkUpdateDCMLevel(con,dcmCode,rss.getString("DCM_LEVEL"));
                    fieldName = "DCM_LEVEL";
                }
                else if (tableName.compareToIgnoreCase("DCM_L1_TEMP")==0)    
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"IS_LEVEL_ONE",rss.getString("IS_LEVEL_ONE"));
                    fieldName = "IS_LEVEL_ONE";
                }
                else if (tableName.compareToIgnoreCase("DCM_QC_TEMP")==0)    
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"IS_QUALITY_CLUB",rss.getString("IS_QUALITY_CLUB"));
                    fieldName = "IS_QUALITY_CLUB";
                }
                else if (tableName.compareToIgnoreCase("DCM_NOMAD_TEMP")==0)    
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"IS_NOMAD",rss.getString("IS_NOMAD"));
                    fieldName = "IS_NOMAD";
                }
                else if (tableName.compareToIgnoreCase("DCM_MOBICASH_TEMP")==0)    
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"IS_MOBICASH",rss.getString("IS_MOBICASH"));
                    
                    long mobiNum = rss.getLong("MOBICASH_NUMBER");
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"MOBICASH_NUMBER",Long.toString(mobiNum));
                    fieldName = "IS_MOBICASH"+" , "+"MOBICASH_NUMBER";
                }
                else if (tableName.compareToIgnoreCase("DCM_EX_TEMP")==0)    
                {
                    tableUpdated = bulkUpdateFeature(con,dcmCode,"IS_EXCLUSIVE",rss.getString("IS_EXCLUSIVE"));
                    fieldName = "IS_EXCLUSIVE";
                }
                
                
                
            }
        
               
                
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return fieldName;
    }
    
    public static UserDataModel insertUserDetail(Connection con, String Name, String Mobile, String Email,String Address, String userLevelTypeId, String regionId, String managerId)
    
    {
        System.out.println("INSERT.....");
        UserDataModel user2 = new UserDataModel();
        
        try{
            Statement stmt = con.createStatement();
            String sqlString = "insert into dcm_user (dcm_user_id,user_id,manager_dcm_user_id,user_level_type_id,user_status_type_id,region_id,user_level_id) values ((select max(dcm_user_id)+1 from dcm_user),456,"+managerId+","+userLevelTypeId+",1,"+regionId+",0)";
            System.out.println("insert dcm user : "+sqlString);
            stmt.executeUpdate(sqlString);
            sqlString = "select * from dcm_user order by dcm_user_id desc";
            System.out.println("select dcm user : "+sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            if(rs.next())
            {
               // user2.setUserId(rs.getString("USER_ID"));
                user2.setDcmUserId(rs.getString("dcm_user_id"));
                user2.setManagerDcmUserId(managerId);
                user2.setUserLevelTypeId(userLevelTypeId);
                user2.setUserStatusTypeId(rs.getString("USER_STATUS_TYPE_ID"));
                user2.setRegionId(regionId);
                user2.setUserLevelId(rs.getString("USER_LEVEL_ID"));
            }
            rs.close();
            String sql2 = "insert into dcm_user_detail (user_detail_id,user_id,user_full_name,user_address,user_email,user_mobile,region_id,user_detail_status_id,creation_timestamp,creation_user_id) values ((select max(user_detail_id)+1 from dcm_user_detail),'"+user2.getDcmUserId()+"','"+Name+"','"+Address+"','"+Email+"','"+Mobile+"',"+regionId+",1,sysdate,54)";
            System.out.println("insert dcm user detail : "+sql2);
            stmt.executeUpdate(sql2);
            sql2 = "select * from dcm_user_detail order by user_detail_id desc";
            System.out.println("select dcm user detail : "+sql2);
            rs = stmt.executeQuery(sql2);
            if(rs.next())
            {
                user2.setUserDetailId(rs.getString("user_detail_id"));
                user2.setUserId(rs.getString("USER_ID"));
                user2.setUserFullName(Name);
                user2.setUserAddress(Address);
                user2.setUserEmail(Email);
                user2.setUserMobile(Mobile);
                user2.setUserDetailStatusId(rs.getString("USER_DETAIL_STATUS_ID"));
                user2.setCreationTimestamp(rs.getString("CREATION_TIMESTAMP"));
                user2.setCreationUserId(rs.getString("CREATION_USER_ID"));
            }
            rs.close();
            String update = "update dcm_user set user_detail_id="+user2.getUserDetailId()+" where dcm_user_id="+user2.getDcmUserId();
            System.out.println("update dcm user with detail id : "+update);
            stmt.executeUpdate(update);
            stmt.close();
        }
        catch(Exception e){System.out.println("exception : "+e);}
        return user2;
    }
    
    
    
    public static Long insertPosDetail(Connection con, PosModel posModel, int dcmId, String statusTypeId) {
        Long posDetailId = null;
        try {
            Statement stmt = con.createStatement();
            
            String hasSign = "";
            String reportToCalidus = "";
            String isEX = "";
            String isL1="";
            String isQC ="";
            String isMobicash ="";
            String isNomad ="";
            String payMethod = posModel.getPaymentMethod();
            long mobicashNum = posModel.getMobicashNum();
            
            if (posModel.isIsMobicash())
                isMobicash = "1";
            else isMobicash = "0";
            
            if (posModel.isIsNomad())
                isNomad = "1";
            else isNomad = "0";
            
            
            
            if (posModel.isIsSignSet())
                hasSign = "1";
            else hasSign = "0";
            
            if (posModel.isReportToCalidus())
                reportToCalidus = "1";
            else reportToCalidus = "0";
            //////////////////////////
            if (posModel.isIsEX())
                isEX = "1";
            else isEX = "0";
            
            if (posModel.isIsL1())
                isL1 = "1";
            else isL1 = "0";
            
            if (posModel.isIsQC())
                isQC = "1";
            else isQC = "0";
            
            
            String posLevel = posModel.getPosDetailModel().getPosLevel();
            String posName = posModel.getPosDetailModel().getPosName();
            String posCode = posModel.getPosDetailModel().getPOSCode();

            String posEmail = posModel.getPosDetailModel().getPosEmail();
            String posAddress = posModel.getPosDetailModel().getPosAddress();

            int posRegion = posModel.getPosDetailModel().getPosRegionID();

            Vector posPhones = posModel.getPosDetailModel().getPosPhones();

            String posOwnerName = posModel.getPosDetailModel().getPosOwnerName();
            int posOnwerIDType = posModel.getPosDetailModel().getPosOwnerIDTypeID();
            String posOwnerIDNumber = posModel.getPosDetailModel().getPosOwnerIDNumber();
            Vector posOwnerPhones = posModel.getPosDetailModel().getPosOwnerPhones();
            String posOwnerBirthDate = posModel.getPosDetailModel().getPosOwnerBirthDate();
            String posPayment = Integer.toString(posModel.getPaymentLevelId());
            String posManagerName = posModel.getPosDetailModel().getPosManagerName();
            int posManagerIDtype = posModel.getPosDetailModel().getPosManagerIDTypeID();
            String posManagerIDNumber = posModel.getPosDetailModel().getPosManagerIDNumber();
            Vector posManagerPhones = posModel.getPosDetailModel().getPosManagerPhones();
            String posManagerBirthDate = posModel.getPosDetailModel().getPosManagerBirthDate();
            String SurveyID = posModel.getPosDetailModel().getSurveyID();
            String posArabicName = posModel.getPosDetailModel().getPosArabicName();
            String posArabicAddress = posModel.getPosDetailModel().getPosArabicAddress();




            Integer UserID = posModel.getPosDetailModel().getUserID();

            posDetailId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_DETAIL");

            String sqlString = "INSERT INTO DCM_POS_DETAIL "
                    + "(SUPERVISOR_ID, TEAMLEADER_ID, SALESREP_ID,MOBICASH_NUMBER, IS_NOMAD,IS_MOBICASH, IS_EXCLUSIVE, IS_LEVEL_ONE, IS_QUALITY_CLUB, DCM_LEVEL_ID,IS_DIRTY,POS_DETAIL_ID, POS_ID ,POS_CODE,"
                    + "POS_NAME,POS_EMAIL,"
                    + "POS_ADDRESS,POS_STATUS_TYPE_ID,REGION_ID,"
                    + "UPDATED_IN,USER_ID"
                    + ", POS_CHANNEL_ID , POS_BRANCH_OF , POS_GOVERNRATE , POS_AREA_ID , POS_DEMO_LINE , POS_PROPOSED_DOC_ID"
                        + " , POS_DOC_NUM , POS_RATE_ID , POS_PLACE_TYPE_ID , POS_DISTRICT_ID , POS_CITY_ID,SURVEY_DATE,SURVEY_ID,DCM_PAYMENT_LEVEL_ID,POS_ARABIC_NAME,POS_ARABIC_ADDRESS,HAS_SIGN,REPORT_TO_CALIDUS, PAYMENT_TYPE_METHOD_ID,DOC_LOCATION)"
                    + "values('"+posModel.getPosDetailModel().getSupervisorName()+"','"+posModel.getPosDetailModel().getTeamleaderName()+"','"+posModel.getPosDetailModel().getSalesrepName()+"',"+mobicashNum+" , '"+isNomad+"','"+isMobicash+"','"+isEX+"','"+isL1+"','"+isQC+"', '"+posLevel+"','1'," + posDetailId
                    + ",'" + dcmId + "','" + posCode + "','" + posName + "','" + posEmail + "','" + posAddress + "','" + statusTypeId + "','" + posRegion + "'"
                    + ",sysdate,'" + UserID + "', " + posModel.getChannelId() + " , '" + posModel.getBranchOf() + "', " + posModel.getGovernateId()
                    + " , " + posModel.getAreaId() + " , '" + posModel.getDemoLineNum() + "' , " + (posModel.getProposedDocId() == -1 ? "null" : posModel.getProposedDocId())
                    + " , '" + posModel.getDocNumber() + "' , " + 4 + " , " + posModel.getRateID() + " , " + posModel.getDistrictId() + " , " + posModel.getCityId() + "," + "(select nvl(max(survey_date),sysdate) surve_date from dcm_pos_detail where pos_code='"+posCode+"')" + ",'" + SurveyID + "'," + posPayment + ",'" + posArabicName + "','" + posArabicAddress + "','" + hasSign + "','" + reportToCalidus + "','" + payMethod + "','" + posModel.getDocLocation() + "')";


            Utility.logger.debug(sqlString);
            System.out.println(sqlString);
            stmt.executeUpdate(sqlString);

            System.out.println("posPhones.size(): " + posPhones.size());

            for (int i = 0; i < posPhones.size(); i++) {
                sqlString = "INSERT INTO DCM_POS_PHONE (POS_DETAIL_ID,POS_PHONE_NUMBER) VALUES (" + posDetailId + ",'" + posPhones.get(i) + "')";
                System.out.println(sqlString);
                stmt.executeUpdate(sqlString);
            }



            insertDcmPosManager(con, posDetailId + "", posManagerName, posManagerBirthDate, posManagerIDtype + "", posManagerIDNumber, posManagerPhones, UserID.toString());


            insertDcmPosOwner(con, posDetailId + "", posOwnerName, posOwnerBirthDate, posOnwerIDType + "", posOwnerIDNumber, posOwnerPhones, UserID.toString());

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return posDetailId;
    }

    public static void insertDcmPosPartners(Connection con, Vector posPartners, String posDetailId) {
        try {
            Statement stmt = con.createStatement();

            for (int i = 0; i < posPartners.size(); i++) {
                Long partner_id = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_OWNER");
                String sqlString = "INSERT INTO DCM_POS_OWNER (POS_OWNER_ID , POS_OWNER_NAME , POS_DETAIL_ID , POS_OWNER_FLAG) VALUES"
                        + "('" + partner_id + "','" + posPartners.get(i) + "','" + posDetailId + "','0')";
                stmt.executeUpdate(sqlString);
            }

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void insertDcmPosOwner(Connection con, String posDetailId, String ownerName, String ownerBirthDate, String ownerTypeId, String ownerIdNumber, Vector posOwnerPhones, String UserId) {
        try {
            Statement stmt = con.createStatement();

//            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


            Long lOwnerId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_OWNER");
            String sqlString = "INSERT INTO DCM_POS_OWNER (POS_OWNER_ID , POS_OWNER_NAME , POS_OWNER_BIRTHDATE ,"
                    + "POS_OWNER_ID_TYPE_ID ,POS_OWNER_ID_NUMBER , POS_DETAIL_ID , POS_OWNER_FLAG) values ('" + lOwnerId + "','"
                    + ownerName + "',TO_DATE('" + /*
                     * formatter.format(
                     */ ownerBirthDate/*
                     * )
                     */ + "','dd-MM-yyyy'),'" + ownerTypeId + "','" + ownerIdNumber + "','"
                    + posDetailId + "','1')";

            stmt.executeUpdate(sqlString);


            String sqldelete = "update DCM_POS_OWNER_PHONE SET FLAGE=1,UPDATED_IN=sysdate,USER_ID=" + UserId + " WHERE POS_OWNER_ID=" + lOwnerId;

            stmt.execute(sqldelete);

            System.out.println("pos owner phones size =  " + posOwnerPhones.size());
            
            for (int j = 0; j < posOwnerPhones.size(); j++) {
                System.out.println("J = "+ j);
                sqlString = "INSERT INTO DCM_POS_OWNER_PHONE (POS_OWNER_ID,POS_OWNER_PHONE_NUMBER) VALUES (" + lOwnerId + ",'" + posOwnerPhones.get(j) + "')";
                System.out.println("sql string = "+ sqlString );
                stmt.executeUpdate(sqlString);
            }

            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static String getSurveyDate(Connection con, String posCode) throws Exception
    {
        System.out.println("inside get survey date from dcm pos detail");
        Statement stmt = con.createStatement();
        String surveyDate="";
        String sqlString = "select max(survey_date) from dcm_pos_detail where POS_CODE ='" + posCode + "'";
        System.out.println("get query : " + sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        

        if (rs.next()) {
            surveyDate = rs.getString("max(survey_date)");
            System.out.println("Survey date is : "+surveyDate);
        }

        stmt.close();
        rs.close();

        return surveyDate;
        
    }

    public static void insertDcmPosManager(Connection con, String posDetailId, String managerName, String managerBirthDate, String managerTypeId, String managerIdNumber, Vector posManagerPhones, String userId) {
        System.out.println("inset DCM pos Manager");
        try {
            Statement stmt = con.createStatement();
            System.out.println("managerBirthDate isss " + managerBirthDate);
//             DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

            if (managerBirthDate.equals("*") || managerBirthDate.equals(null) || managerBirthDate.equals("")) {
                managerBirthDate = "";
            } else {
                managerBirthDate = "to_date('" + managerBirthDate + "', 'dd/mm/yyyy')";
            }
            System.out.println("..................insertDcmPosManager manageBirthDate : "+managerBirthDate);


            Long lManagerId = Utility.getSequenceNextVal(con, "SEQ_DCM_POS_MANAGER");

            if (managerName.equals("") || managerName.equals(null)) {
                managerName = "";
            }

            String sqlString = "INSERT INTO DCM_POS_MANAGER (POS_MANAGER_ID , "
                    + "POS_MANAGER_NAME , POS_MANAGER_BIRTHDATE ,"
                    + "POS_MANAGER_ID_TYPE_ID ,POS_MANAGER_ID_NUMBER , POS_DETAIL_ID) "
                    + "values ('" + lManagerId + "','"
                    + managerName + "','" + managerBirthDate + "','" + managerTypeId + "','" + managerIdNumber + "','"
                    + posDetailId + "')";

            System.out.println("sqlString : " + sqlString);
            stmt.executeUpdate(sqlString);

            String sqldelete = "update DCM_POS_MANAGER_PHONE SET FLAGE=1,UPDATED_IN=sysdate,USER_ID=" + userId + " WHERE POS_MANAGER_ID=" + lManagerId;

            stmt.execute(sqldelete);


            for (int k = 0; k < posManagerPhones.size(); k++) {

                String temp = (String) posManagerPhones.get(k);

                if (temp == "") {
                    temp = "\'\'";
                }
                sqlString = "INSERT INTO DCM_POS_MANAGER_PHONE (POS_MANAGER_ID,POS_MANAGER_PHONE_NUMBER) VALUES (" + lManagerId + "," + temp + ")";
                System.out.print(sqlString);
                stmt.executeUpdate(sqlString);
            }
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }



    }

    public static int getPosIdByCode(Connection con, String posCode) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select DCM_ID from GEN_DCM where DCM_STATUS_ID =19 and DCM_CODE ='" + posCode + "'";
        System.out.println("the query isssssssssssssss" + sqlString);
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int dcmCode = 0;

        if (rs.next()) {
            dcmCode = rs.getInt("DCM_ID");
        }

        stmt.close();
        rs.close();

        return dcmCode;
    }

    public static int getAnyPosIdByCode(Connection con, String posCode) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select DCM_ID from GEN_DCM where  DCM_CODE ='" + posCode + "'";
        System.out.println("the query isssssssssssssss" + sqlString);
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int dcmCode = 0;

        if (rs.next()) {
            dcmCode = rs.getInt("DCM_ID");
        }

        stmt.close();
        rs.close();

        return dcmCode;
    }

    public static HashMap<String, RegionModel> getChildRegionsListHM(Connection con, String parentIds) {
        HashMap<String, RegionModel> retHM = new HashMap<String, RegionModel>();
        HashMap<String, RegionModel> temoHM = new HashMap<String, RegionModel>();
        if (parentIds != null && parentIds.compareTo("") != 0) {
            String[] parentIdsArr = parentIds.split("-");
            for (String id : parentIdsArr) {
                if (id != null && id.compareTo("") != 0 && id.compareTo("0") != 0 && id.compareTo("null") != 0) {
                    temoHM = DBUtil.executeSqlQueryMultiValueHM("select * from dcm_region where REGION_LEVEL_TYPE_ID =((select REGION_LEVEL_TYPE_ID from dcm_region where region_id = '" + id + "' )+1) and PARENT_REGION_ID='" + id + "'", "REGION_ID", RegionModel.class, con);
                    for (String parentId : temoHM.keySet()) {
                        retHM.put(parentId, temoHM.get(parentId));
                    }
                    temoHM = new HashMap<String, RegionModel>();
                }
            }
        }

        return retHM;
    }

    public static HashMap<String, String> getUsersByLevel(Connection con, String level) {
        return DBUtil.getMap(con, "select ddet.USER_ID,ddet.USER_FULL_NAME from DCM_USER_DETAIL ddet ,DCM_USER dus where dus.USER_DETAIL_ID = ddet.USER_DETAIL_ID and ddet.user_detail_status_id=1 and dus.user_level_type_id=" + level);
    }

    public static Vector<String> getUserPosCodesById(boolean isSuper, String repOrSupId) {
        String sql = "select distinct E.POS_CODE from SCM_POS_ASSIGNED_GROUP A, SCM_REP_POS_GROUP B, dcm_user_detail D,dcm_pos_detail E, SCM_REP_SUPERVISORS F where "
                + "A.DCM_ID = E.POS_ID and  A.GROUP_ID = B.GROUP_ID and B.DCM_USER_ID = F.REP_ID and F.SUP_ID = D.USER_ID and ";
        sql += isSuper ? "F.SUP_ID = " + repOrSupId : "F.REP_ID = " + repOrSupId;
        return DBUtil.executeQueryMultiValueString(sql, "POS_CODE");
    }

    public static String getPosGreatestCode(Connection con, String channel, String level) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = " SELECT  MAX_CODE+1 || '.' || EXTENSION MAX_CODE FROM SCM_MAX_POS_CODE WHERE CHANNEL_ID =" + channel + " AND LEVEL_ID =" + level;
        System.out.print(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        String dcmCode = "";

        if (rs.next()) {
            dcmCode = rs.getString("MAX_CODE");
        }

        stmt.close();
        rs.close();

        return dcmCode;
    }

    public static int getStkIdByStkCodeStatusId(Connection con, String stkCode, String statusId) throws Exception {
        int stkId = 0;
        if (stkCode != null && stkCode.compareTo("") != 0) {
            Statement stmt = con.createStatement();
            String sqlString = "select STK_ID from SCM_STK_STOCK where stock_id=" + SCMInterfaceKey.POS_STOCK_ID + " and STK_STATUS_ID = " + statusId + " and STK_NUMBER ='" + stkCode + "'";
            Utility.logger.debug(sqlString);
            System.out.println("sqlString : " + sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);


            if (rs.next()) {
                stkId = rs.getInt("STK_ID");
            }

            stmt.close();
            rs.close();
        }
        return stkId;
    }

    public static int getStkIdBySTKnum(Connection con, String stkCode) throws Exception {
        int stkId = 0;
        if (stkCode != null && stkCode.compareTo("") != 0) {
            Statement stmt = con.createStatement();
            String sqlString = "select STK_ID from SCM_STK_STOCK where stock_id=" + SCMInterfaceKey.POS_STOCK_ID + " and STK_STATUS_ID = 2 and STK_NUMBER ='" + stkCode + "'";
            Utility.logger.debug(sqlString);
            System.out.println("sqlString : " + sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);


            if (rs.next()) {
                stkId = rs.getInt("STK_ID");
            }

            stmt.close();
            rs.close();
        }
        return stkId;
    }

    public static int getEditStkIdBySTKnum(Connection con, String stkCode) throws Exception {
        int stkId = 0;
        if (stkCode != null && stkCode.compareTo("") != 0) {
            Statement stmt = con.createStatement();
            String sqlString = "select STK_ID from SCM_STK_STOCK where stock_id=" + SCMInterfaceKey.POS_STOCK_ID + " and STK_STATUS_ID = 1 and STK_NUMBER ='" + stkCode + "'";
            Utility.logger.debug(sqlString);
            System.out.println("sqlString : " + sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            rs = stmt.executeQuery(sqlString);


            if (rs.next()) {
                stkId = rs.getInt("STK_ID");
            }

            stmt.close();
            rs.close();
        }
        return stkId;
    }

    public static int checkStkInOwner(Connection con, String stkId) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select DCM_ID from SCM_STK_OWNER where STK_ID =" + stkId;
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int stkFound = 0;

        if (rs.next()) {
            if (rs.getInt("DCM_ID") != 0) {
                stkFound = 1;
            }
        }

        stmt.close();
        rs.close();

        return stkFound;
    }

    public static void insertPaymentStatus(Connection con, String status, String dcmId) throws SQLException {
        Statement stmt = con.createStatement();
        Long camId = Utility.getSequenceNextVal(con, "SEQ_PAYMENT_SCM_STATUS");
        //String sqlString = "insert into  CAM_PAYMENT_SCM_STATUS (camId , SCM_ID , PAYMENT_CAM_STATE_ID) values (SCM_PAYMENT_STATUS_ID , " + dcmId + " , " + status + ")";
        String sqlString = "insert into  CAM_PAYMENT_SCM_STATUS (SCM_PAYMENT_STATUS_ID , SCM_ID , PAYMENT_CAM_STATE_ID) values ('" + camId + "' , " + dcmId + " , " + status + ")";
        System.out.println(sqlString);
        stmt.executeUpdate(sqlString);
        stmt.close();
    }

    public static void updatePaymentStatus(Connection con, String status, String dcmId) throws SQLException {
        Statement stmt = con.createStatement();
        //String sqlString = "insert into  CAM_PAYMENT_SCM_STATUS (camId , SCM_ID , PAYMENT_CAM_STATE_ID) values (SCM_PAYMENT_STATUS_ID , " + dcmId + " , " + status + ")";
        String sqlString = "update CAM_PAYMENT_SCM_STATUS set PAYMENT_CAM_STATE_ID = " + status + " where SCM_ID =" + dcmId;
        System.out.println(sqlString);
        stmt.executeUpdate(sqlString);
        stmt.close();
    }

    public static void updateStkData(Connection con, String stkDialNum, String dcmId, String userId, String x) {
        try {
            int stkId = RequestDao.getEditStkIdBySTKnum(con, stkDialNum);
            Statement stmt = con.createStatement();
            // String sqlString = "update SCM_STK_OWNER set  DCM_ID = " + dcmId + " ,DCM_USER_ID= NULL ,IQRAR_RECEIVE_DATE = to_date('"+iqrarVerifyDate+"', 'mm/dd/yyyy') where STK_ID =" + stkId;
//            String sqlString = "update SCM_STK_OWNER set  DCM_ID = " + dcmId + " ,DCM_USER_ID= NULL , STK_ASSIGN_DATE=sysdate where STK_ID =" + stkId;
            String sqlString = " Insert into SCM_STK_OWNER "
                    + "(STK_ID, DCM_ID, DCM_USER_ID, USER_ID, UPDATED_IN,  "
                    + " DCM_VERIFIED_STATUS_ID, IQRAR_RECEVING_STATUS_ID, STK_STATUS_ID, IQRAR_RECIEVED_DATE, DCM_VERIFICATION_DATE,  "
                    + " ACTIVE_DATE, STK_VERIFICATION, STK_DELIVERY_DATE, IQRAR_DELIVERY_DATE, STK_ASSIGN_DATE,  "
                    + "STK_ACTIVE_DATE, STK_IMPORT_DATE, REASON, STK_REPORT_FLAG) "
                    + "Values "
                    + " ('" + stkId + "', '" + dcmId + "', 0, '" + userId + "', sysdate, "
                    + " 1, 1, 2, NULL, NULL,  "
                    + "NULL, NULL, NULL, NULL, sysdate,  "
                    + "Null, NULL, NULL, 0) ";
            System.out.println(sqlString);
            int result = stmt.executeUpdate(sqlString);
            if (result > 0) {
                sqlString = "Update SCM_STK_STOCK set STK_STATUS_ID=2 where stk_id= '" + stkId + "' and STK_NUMBER='" + stkDialNum + "' and stock_id=" + SCMInterfaceKey.POS_STOCK_ID;
                System.out.println("sqlString " + sqlString);
                stmt.executeUpdate(sqlString);
            }
            System.out.println("Result Update : " + result);

            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void updateStkData(Connection con, String iqrarVerifyDate, String stkDialNum, String dcmId) {
        try {
            int stkId = RequestDao.getStkIdBySTKnum(con, stkDialNum);
            Statement stmt = con.createStatement();
            String sqlString = "";
            if (iqrarVerifyDate != null && !iqrarVerifyDate.equals("")) {
                sqlString = "update SCM_STK_OWNER set  STK_ASSIGN_DATE=sysdate  ,DCM_ID = " + dcmId + " ,DCM_USER_ID= NULL ,IQRAR_RECIEVED_DATE = to_date('" + iqrarVerifyDate + "', 'dd/mm/yyyy') ,IQRAR_RECEVING_STATUS_ID=2 where STK_ID =" + stkId;
            } else {
                sqlString = "update SCM_STK_OWNER set  STK_ASSIGN_DATE=sysdate  ,DCM_ID = " + dcmId + " ,DCM_USER_ID= NULL  where STK_ID =" + stkId;
            }
            System.out.println(sqlString);
            int result = stmt.executeUpdate(sqlString);



            System.out.println("Result Update : " + result);

            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void insertStkDataToOwner(Connection con, String iqrarVerifyDate, String stkDialNum, String dcmId, String userId) {
        try {
            int stkId = RequestDao.getStkIdByStkCodeStatusId(con, stkDialNum, "1");
            Statement stmt = con.createStatement();
            String sqlString = " Insert into SCM_STK_OWNER "
                    + " (STK_ID, DCM_ID, DCM_USER_ID, USER_ID, UPDATED_IN,  "
                    + "  DCM_VERIFIED_STATUS_ID, IQRAR_RECEVING_STATUS_ID, STK_STATUS_ID, IQRAR_RECIEVED_DATE, DCM_VERIFICATION_DATE,  "
                    + " ACTIVE_DATE, STK_VERIFICATION, STK_DELIVERY_DATE, IQRAR_DELIVERY_DATE, STK_ASSIGN_DATE,  "
                    + " STK_ACTIVE_DATE, STK_IMPORT_DATE, REASON, STK_REPORT_FLAG) "
                    + " Values "
                    + "    ('" + stkId + "', '" + dcmId + "', 0, '" + userId + "', sysdate,  "
                    + "     2, " + (iqrarVerifyDate != null && !iqrarVerifyDate.equals("") ? "2" : "1") + ", 2, " + (iqrarVerifyDate != null && !iqrarVerifyDate.equals("") ? "to_date('" + iqrarVerifyDate + "', 'dd/mm/yyyy')" : "null") + ", NULL,  "
                    + "     NULL, NULL, NULL, NULL, sysdate,  "
                    + "     null, NULL, NULL, 0) ";

//          if(iqrarVerifyDate!=null&&!iqrarVerifyDate.equals(""))
//          {
//            sqlString = "update SCM_STK_OWNER set  STK_ASSIGN_DATE=sysdate  ,DCM_ID = " + dcmId + " ,DCM_USER_ID= NULL ,IQRAR_RECIEVED_DATE = to_date('"+iqrarVerifyDate+"', 'dd/mm/yyyy') ,IQRAR_RECEVING_STATUS_ID=2 where STK_ID =" + stkId;
//          }
//          else{
//            sqlString = "update SCM_STK_OWNER set  STK_ASSIGN_DATE=sysdate  ,DCM_ID = " + dcmId + " ,DCM_USER_ID= NULL  where STK_ID =" + stkId;
//          }
            System.out.println(sqlString);
            int result = stmt.executeUpdate(sqlString);
            if (result > 0) {
                sqlString = "Update SCM_STK_STOCK set STK_STATUS_ID=2 where stk_id= '" + stkId + "' and STK_NUMBER='" + stkDialNum + "' and stock_id=" + SCMInterfaceKey.POS_STOCK_ID;
                System.out.println("sqlString " + sqlString);
                stmt.executeUpdate(sqlString);
            }
            System.out.println("Result Update : " + result);

            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void updateMaxPos(Connection con, String maxPos, String channel, String level) {
        try {

            Statement stmt = con.createStatement();
            String extension = maxPos.substring(maxPos.indexOf(".")).replace(".", "");
            maxPos = maxPos.replace("." + extension, "");
            String sqlString = "update SCM_MAX_POS_CODE set MAX_CODE = " + maxPos + "  where CHANNEL_ID=" + channel + " and LEVEL_ID=" + level;
            System.out.println(sqlString);
            stmt.executeUpdate(sqlString);
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static String getUpdatedMaxPos(Connection con, String maxPos, String channel, String level) {
        try {

            Statement stmt = con.createStatement();
            String extension = maxPos.substring(maxPos.indexOf(".")).replace(".", "");
            maxPos = maxPos.replace("." + extension, "");
            String sqlString = "update SCM_MAX_POS_CODE set MAX_CODE = " + maxPos + "  where CHANNEL_ID=" + channel + " and LEVEL_ID=" + level;
            System.out.println(sqlString);
            stmt.executeUpdate(sqlString);
            stmt.close();
            return maxPos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public static Vector getSimilarName(Connection con, String posName) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select POS_NAME,POS_CODE,POS_ADDRESS from DCM_POS_DETAIL where DCM_POS_DETAIL.FLAGE IS NULL and POS_NAME like '" + posName + "%' ORDER BY POS_NAME ASC";
        Utility.logger.debug(sqlString);
        System.out.println(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        Vector<POSSimilar> nameList = new Vector();

        while (rs.next()) {
            POSSimilar pos = new POSSimilar();
            pos.setPOSCode(rs.getString("POS_CODE"));
            pos.setPOSName(rs.getString("POS_NAME"));
            pos.setPOSAddress(rs.getString("POS_ADDRESS"));
            nameList.add(pos);
        }

        stmt.close();
        rs.close();

        return nameList;
    }

    public static Vector getSimilarArea(Connection con, String posArea) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select POS_NAME,POS_CODE,POS_ADDRESS from DCM_POS_DETAIL where DCM_POS_DETAIL.FLAGE IS NULL and POS_AREA_ID = " + posArea + " ORDER BY POS_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        System.out.println(sqlString);
        Vector nameList = new Vector();

        while (rs.next()) {
            POSSimilar pos = new POSSimilar();
            pos.setPOSCode(rs.getString("POS_CODE"));
            pos.setPOSName(rs.getString("POS_NAME"));
            pos.setPOSAddress(rs.getString("POS_ADDRESS"));
            nameList.add(pos);
        }

        stmt.close();
        rs.close();

        return nameList;
    }

    public static Vector getSimilarCity(Connection con, String posCity) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select POS_NAME,POS_CODE,POS_ADDRESS from DCM_POS_DETAIL where DCM_POS_DETAIL.FLAGE IS NULL AND POS_CITY_ID= " + posCity + " ORDER BY POS_NAME ASC";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        System.out.println(sqlString);
        Vector nameList = new Vector();

        while (rs.next()) {
            POSSimilar pos = new POSSimilar();
            pos.setPOSCode(rs.getString("POS_CODE"));
            pos.setPOSName(rs.getString("POS_NAME"));
            pos.setPOSAddress(rs.getString("POS_ADDRESS"));
            nameList.add(pos);
        }

        stmt.close();
        rs.close();

        return nameList;
    }

    public static PosIqrarModel getPosIqrarData(Connection con, String posCode) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select  distinct(detail.POS_CODE) , detail.POS_NAME , detail.POS_DISTRICT_ID , "
                + "detail.POS_AREA_ID, owner.POS_OWNER_NAME , owner.POS_OWNER_ID_NUMBER , "
                + "stkStock.STK_NUMBER from DCM_POS_OWNER owner, DCM_POS_DETAIL  detail, "
                + "SCM_STK_OWNER stkOwner , SCM_STK_STOCK stkStock "
                + "where detail.FLAGE IS NULL AND owner.POS_DETAIL_ID = detail.POS_DETAIL_ID and detail.POS_ID = stkOwner.DCM_ID "
                + "and stkOwner.STK_ID = stkStock.STK_ID and detail.POS_CODE = '" + posCode + "'";

        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        System.out.println(sqlString);
        PosIqrarModel iqrarModel = new PosIqrarModel();

        while (rs.next()) {
            iqrarModel.setPosName(rs.getString("POS_NAME"));
            iqrarModel.setPosCode(rs.getString("POS_CODE"));
            iqrarModel.setAreaName(getRegionName(con, rs.getInt("POS_AREA_ID")));
            iqrarModel.setDistricName(getRegionName(con, rs.getInt("POS_DISTRICT_ID")));
            iqrarModel.setOwnerIdNo(rs.getString("POS_OWNER_ID_NUMBER"));
            iqrarModel.setOwnerName(rs.getString("POS_OWNER_NAME"));
            iqrarModel.setStkNo(rs.getString("STK_NUMBER"));
        }

        stmt.close();
        rs.close();

        return iqrarModel;
    }

    public static String getRegionName(Connection con, int regionId) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select REGION_NAME from DCM_REGION where REGION_ID =" + regionId;
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        String regionName = "";

        while (rs.next()) {
            regionName = rs.getString("REGION_NAME");
        }

        stmt.close();
        rs.close();

        return regionName;
    }

    public static int checkPosIsFound(Connection con, String posCoide) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select POS_ID from DCM_POS_DETAIL where DCM_POS_DETAIL.FLAGE IS NULL AND POS_CODE='" + posCoide + "'";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int codeFound = 0;

        if (rs.next()) {
            codeFound = rs.getInt("POS_ID");
        }

        stmt.close();
        rs.close();

        return codeFound;
    }

    public static int checkPosHasStk(Connection con, int posId) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select stk_id from SCM_STK_OWNER where dcm_id='" + posId + "'";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);
        int codeFound = 0;

        if (rs.next()) {
            codeFound = 1;
        }

        stmt.close();
        rs.close();

        return codeFound;
    }

    public static Vector<String> getPOSPdfList(Connection con) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select distinct(POS_CODE) from SCM_POS_CODE_PDF";
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);

        Vector<String> POSList = new Vector();

        while (rs.next()) {
            POSList.add(rs.getString("POS_CODE"));
        }

        stmt.close();
        rs.close();

        return POSList;
    }

    public static final void deletePosPdfTable(Connection con) {
        String sql = "delete from SCM_POS_CODE_PDF";
        DBUtil.executeSQL(sql, con);

    }

    public static void updateStkIqrarReceiving(Connection con, String dcmId) {

        try {
            Statement stmt = con.createStatement();

            String sqlString = "update SCM_STK_OWNER set IQRAR_RECEVING_STATUS_ID = 2 where dcm_id =" + dcmId;
            System.out.println(sqlString);
            stmt.executeUpdate(sqlString);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static int checkPosOwnerStatus(Connection con, int dcmId) throws Exception {
        Statement stmt = con.createStatement();
        String sqlString = "select IQRAR_RECEVING_STATUS_ID from SCM_STK_OWNER where dcm_id =" + dcmId;
        Utility.logger.debug(sqlString);
        System.out.print(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        rs = stmt.executeQuery(sqlString);

        int status = 0;
        if (rs.next()) {
            status = rs.getInt("IQRAR_RECEVING_STATUS_ID");
        }

        stmt.close();
        rs.close();

        return status;
    }

    public static HashMap<String, String> getLookupFields(Connection con, String table, String fields) {
        return DBUtil.getMap(con, "select " + fields + " from " + table);
    }

    private static String[] appendFromWhereStrs(String posDataOwnerIdType, String posDataDocNum, String posDataManagerName, String posDataStkNum, String posDataManagerIdType, String posDataProposedDoc, String posDataManagerIdNum, String posDataName, String posDataCode, String posDataRegion, String posDataGover, String posDataDistrict, String posDataArea, String posDataCity, String posDataOwnerName, String posDataOwnerIdNum, String level, String payment, String channel, String posStatusId, String stkStatusId, String paymentStatusId, String posPhone, String englishAdress, String strSurvDate, String docLocation,String supervisorId,String supervisorName, String teamleaderId, String teamleaderName, String salesrepId, String salesrepName) {
        StringBuilder fromStr = new StringBuilder();
        StringBuilder whereStr = new StringBuilder();
        String[] queryStrings = new String[2];
        if (posStatusId != null && posStatusId.compareTo("-1") != 0 && posStatusId.compareTo("") != 0) {
            fromStr.append(" , GEN_DCM dcm");
            whereStr.append(" and dcm.DCM_ID = detail.pos_id and dcm.DCM_STATUS_ID=" + posStatusId);
        }
        
        if (supervisorId != null && supervisorId.compareTo("-1") != 0 && supervisorId.compareTo("") != 0) {
            fromStr.append(" , dcm_user_detail");
            whereStr.append(" and dcm_user_detail.USER_ID = detail.supervisor_id and detail.supervisor_id=" + supervisorId);
        }
        
        
        if (stkStatusId != null && stkStatusId.compareTo("-1") != 0 && stkStatusId.compareTo("") != 0) {
            fromStr.append(" , SCM_STK_OWNER stkowner");
            whereStr.append("  and stkowner.DCM_ID = detail.pos_id and  stkowner.STK_STATUS_ID =" + stkStatusId);
        }
        if (paymentStatusId != null && paymentStatusId.compareTo("-1") != 0 && paymentStatusId.compareTo("") != 0) {
            fromStr.append(" , CAM_PAYMENT_SCM_STATUS paymentstatus");
            whereStr.append("  and paymentstatus.SCM_ID = detail.pos_id and paymentstatus.PAYMENT_CAM_STATE_ID=" + paymentStatusId);
        }
        if (posPhone != null && posPhone.compareTo("") != 0) {
            fromStr.append(" , DCM_POS_PHONE phone");
            whereStr.append("  and phone.POS_DETAIL_ID = detail.POS_DETAIL_ID and phone.POS_PHONE_NUMBER like ('%" + posPhone.trim() + "%')");
        }

        whereStr.append(englishAdress != null && englishAdress.compareTo("") != 0 ? " and lower(detail.POS_ADDRESS) like lower('%" + englishAdress.trim() + "%')" : "");
        whereStr.append(docLocation != null && docLocation.compareTo("") != 0 ? " and lower(detail.DOC_LOCATION) like lower('%" + docLocation.trim() + "%')" : "");
        whereStr.append(strSurvDate != null && strSurvDate.compareTo("") != 0 && strSurvDate.compareTo("*") != 0 ? " and detail.SURVEY_DATE = to_date('" + strSurvDate + "','mm/dd/yyyy')" : "");

        if (posDataName != null && posDataName.compareTo("") != 0) {
            whereStr.append(" and lower(POS_NAME) like '" + posDataName.toLowerCase() + "'");


        }

        if (posDataCode != null && posDataCode.compareTo("") != 0) {

            whereStr.append("  and POS_Code in ('" + posDataCode + "')");

        }

        if (posDataRegion != null && posDataRegion.compareTo("") != 0) {

            whereStr.append("  and detail.REGION_ID = " + posDataRegion);

        }

        if (posDataGover != null && posDataGover.compareTo("") != 0) {

            whereStr.append("  and POS_GOVERNRATE = " + posDataGover);

        }

        if (posDataCity != null && posDataCity.compareTo("") != 0) {

            whereStr.append("  and POS_CITY_ID = '" + posDataCity + "'");

        }

        if (posDataDistrict != null && posDataDistrict.compareTo("") != 0) {

            whereStr.append("  and POS_DISTRICT_ID = '" + posDataDistrict + "'");

        }

        if (posDataArea != null && posDataArea.compareTo("") != 0) {

            whereStr.append("  and POS_AREA_ID = " + posDataArea);

        }

        if (posDataDocNum != null && posDataDocNum.compareTo("") != 0) {

            whereStr.append("  and lower(POS_DOC_NUM) like lower( '%" + posDataDocNum + "%')");

        }

        if (posDataProposedDoc != null && posDataProposedDoc.compareTo("") != 0) {

            whereStr.append("  and POS_PROPOSED_DOC_ID = " + posDataProposedDoc);

        }


        if (posDataProposedDoc != null && posDataProposedDoc.compareTo("") != 0) {

            whereStr.append("  and POS_PROPOSED_DOC_ID = " + posDataProposedDoc);

        }


        if (posDataOwnerIdNum != null && posDataOwnerIdNum.compareTo("") != 0) {

            whereStr.append("  and POS_OWNER_ID_NUMBER = " + posDataOwnerIdNum);

        }

        if (posDataOwnerIdType != null && posDataOwnerIdType.compareTo("") != 0) {

            whereStr.append("  and POS_OWNER_ID_TYPE_ID = " + posDataOwnerIdType);

        }

        if (posDataOwnerName != null && posDataOwnerName.compareTo("") != 0) {

            whereStr.append("  and lower(POS_OWNER_NAME) like '" + posDataOwnerName.toLowerCase() + "'");

        }


        if (posDataManagerIdNum != null && posDataManagerIdNum.compareTo("") != 0) {

            whereStr.append("  and POS_MANAGER_ID_NUMBER = " + posDataManagerIdNum);

        }

        if (posDataManagerIdType != null && posDataManagerIdType.compareTo("") != 0) {

            whereStr.append("  and POS_MANAGER_ID_TYPE_ID = " + posDataManagerIdType);

        }

        if (posDataManagerName != null && posDataManagerName.compareTo("") != 0) {

            whereStr.append("  and lower(POS_MANAGER_NAME) like '" + posDataManagerName.toLowerCase() + "'");

        }


        if (posDataStkNum != null && posDataStkNum.compareTo("") != 0) {


            fromStr.append((fromStr.toString().contains("SCM_STK_OWNER") ? " , SCM_STK_STOCK stock " : " , SCM_STK_OWNER stkOwner , SCM_STK_STOCK stock "));

            whereStr.append("  and detail.POS_ID = stkOwner.DCM_ID and stock.STK_ID = stkOwner.STK_ID");
        }

        if (posDataStkNum != null && posDataStkNum.compareTo("") != 0) {

            whereStr.append("  and stock.STK_NUMBER = '" + posDataStkNum + "'");

        }
        if (channel != null && channel.compareTo("") != 0) {

            whereStr.append("  and POS_CHANNEL_ID = '" + channel + "'");

        }
        if (level != null && level.compareTo("") != 0) {

            whereStr.append("  and POS_ID IN (SELECT DCM_ID FROM GEN_DCM WHERE DCM_LEVEL_ID ='" + level + "')");

        }
        if (payment != null && payment.compareTo("") != 0) {

            whereStr.append("  and POS_ID IN (SELECT DCM_ID FROM GEN_DCM WHERE DCM_PAYMENT_LEVEL_ID ='" + payment + "')");

        }
        queryStrings[0] = fromStr.toString();
        queryStrings[1] = whereStr.toString();
        return queryStrings;

    }

    public static Vector searchPosData(Connection con, String posDataOwnerIdType, String posDataDocNum, String posDataManagerName, String posDataStkNum, String posDataManagerIdType, String posDataProposedDoc, String posDataManagerIdNum, String posDataName, String posDataCode, String posDataRegion, String posDataGover, String posDataDistrict, String posDataArea, String posDataCity, String posDataOwnerName, String posDataOwnerIdNum, String supervisorId, String supervisorName, String teamleaderId, String teamleaderName, String salesrepId, String salesrepName,String rowNum, String level, String payment, String channel, String posStatusId, String stkStatusId, String paymentStatusId, String posPhone, String englishAdress, String strSurvDate, String docLocation) throws SQLException {

        System.out.println("searchPosData");
        Vector<POSDetailModel> posDetailVec = new Vector<POSDetailModel>();
        String selectStr = "SELECT * FROM (select detail.SUPERVISOR_ID,detail.TEAMLEADER_ID,detail.SALESREP_ID,  detail.POS_DETAIL_ID , STATUS.DCM_STATUS_NAME DCM_STATUS,ROWNUM as row_num,detail.POS_NAME , detail.POS_Code , detail.POS_ADDRESS , owner.POS_OWNER_NAME ,MANAGER.POS_MANAGER_NAME,detail.DOC_LOCATION,detail.SURVEY_DATE";
        String fromStr = " from DCM_POS_DETAIL detail , DCM_POS_OWNER owner , DCM_POS_MANAGER MANAGER,VW_GEN_DCM_SCM DCM,GEN_DCM_STATUS STATUS";
        String whereStr = " where detail.pos_code = dcm.dcm_code and detail.flage is NULL and owner.POS_DETAIL_ID = detail.POS_DETAIL_ID and MANAGER.POS_DETAIL_ID (+)= detail.POS_DETAIL_ID AND STATUS.DCM_STATUS_ID=DCM.DCM_STATUS_ID ";
        
        if (posDataCode != null && posDataCode.compareTo("")!= 0 ) 
        {
            whereStr +=  " and DCM.DCM_CODE='" + posDataCode +"' "; 
        }
        

        String[] queryStrings = appendFromWhereStrs(posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, level, payment, channel, posStatusId, stkStatusId, paymentStatusId, posPhone, englishAdress, strSurvDate, docLocation,supervisorId,supervisorName,teamleaderId, teamleaderName, salesrepId, salesrepName);
        fromStr += queryStrings[0];
        whereStr += queryStrings[1];

        String queryForCount = selectStr.replaceAll("\\*", "count(*) totalPages") + fromStr + whereStr + ")";
        System.out.println("queryForCount isss " + queryForCount);
      
        String rowNumQuery = ")WHERE row_num > = ('" + rowNum + "'*20)+1 AND row_num < = ('" + rowNum + "'+1)*20  ORDER BY ROWNUM";

        String queryStr = selectStr + fromStr + whereStr + rowNumQuery;
        Statement stmt = con.createStatement();
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);

        while (rs.next()) {
            POSDetailModel posObject = new POSDetailModel();
            posObject.setPosName(rs.getString("POS_NAME"));
            posObject.setPOSCode(rs.getString("POS_Code"));
            posObject.setPosAddress(rs.getString("POS_ADDRESS"));
            posObject.setPosOwnerName(rs.getString("POS_OWNER_NAME"));
            posObject.setPosID(rs.getInt("POS_DETAIL_ID"));
            posObject.setPosManagerName(rs.getString("POS_MANAGER_NAME"));
            posObject.setPosStatusName(rs.getString("DCM_STATUS"));
            posObject.setSupervisorName(rs.getString("SUPERVISOR_ID"));
            posObject.setTeamleaderName(rs.getString("TEAMLEADER_ID"));
            posObject.setSalesrepName(rs.getString("SALESREP_ID"));
            posDetailVec.add(posObject);

        }

        stmt.close();
        rs.close();
        Integer pageCount = DBUtil.executeQuerySingleValueInt(queryForCount, "totalPages", con);
        POSDetailModel posObject = new POSDetailModel();
        posObject.setPageCount(pageCount);
        posDetailVec.add(posObject);


        return posDetailVec;

    }

    public static Vector searchPosDataExcel(Connection con, String posDataOwnerIdType, String posDataDocNum, String posDataManagerName, String posDataStkNum, String posDataManagerIdType, String posDataProposedDoc, String posDataManagerIdNum, String posDataName, String posDataCode, String posDataRegion, String posDataGover, String posDataDistrict, String posDataArea, String posDataCity, String posDataOwnerName, String posDataOwnerIdNum, String level, String payment, String channel, String posStatusId, String stkStatusId, String paymentStatusId, String posPhone, String englishAdress, String strSurvDate, String docLocation, String supervisorId,String supervisorName, String teamleaderId, String teamleaderName, String salesrepId, String salesrepName) throws SQLException {

        Vector<POSSearchExcelModel> posDetailVec = new Vector<POSSearchExcelModel>();
        UserDataModel supervisor = new UserDataModel();
        UserDataModel teamleader = new UserDataModel();
        UserDataModel salesrep = new UserDataModel();
//String query="select distinct(dcm_pos_detail.pos_id),dcm_pos_detail.USER_ID,dcm_pos_detail.pos_code,dcm_pos_detail.POS_NAME,dcm_pos_detail.POS_ARABIC_NAME,"+
//"dcm_pos_detail.region_id,dcm_pos_detail.POS_GOVERNRATE,dcm_pos_detail.POS_CITY_ID,dcm_pos_detail.POS_DISTRICT_ID ,dcm_pos_detail.pos_area_id,"+
//" DCM_USER.DCM_USER_ID \"REB ID\" ,SCM_REP_SUPERVISORS.SUP_ID ,DCM_POS_DETAIL.POS_ADDRESS,DCM_POS_DETAIL.POS_ARABIC_ADDRESS,DCM_POS_PHONE.POS_PHONE_NUMBER"+
//" ,DCM_POS_DETAIL.POS_DEMO_LINE,DCM_POS_OWNER.POS_OWNER_NAME,DCM_POS_OWNER.POS_OWNER_BIRTHDATE,DCM_POS_OWNER_PHONE.POS_OWNER_PHONE_NUMBER,"+
//" DCM_POS_OWNER.POS_OWNER_ID_NUMBER,DCM_POS_OWNER.POS_OWNER_ID_TYPE_ID,DCM_POS_MANAGER.POS_MANAGER_NAME ,"+
//" DCM_POS_MANAGER.POS_MANAGER_BIRTHDATE,DCM_POS_MANAGER_PHONE.POS_MANAGER_PHONE_NUMBER,DCM_POS_MANAGER.POS_MANAGER_ID_NUMBER,"+
//" DCM_POS_MANAGER.POS_MANAGER_ID_TYPE_ID,DCM_POS_DETAIL.POS_EMAIL"+
//" ,SCM_PROPOSED_DOCUMENT.PROPOSED_DOCUMENT_NAME,DCM_POS_DETAIL.POS_DOC_NUM,GEN_DCM_STATUS.DCM_STATUS_NAME,"+
//" SCM_STK_STOCK.STK_NUMBER,SCM_STK_OWNER.STK_DELIVERY_DATE,SCM_STK_OWNER.IQRAR_DELIVERY_DATE,SCM_STK_OWNER.IQRAR_RECIEVED_DATE,"+
//" DCM_POS_DETAIL.UPDATED_IN,SCM_CBILL_CASE.\"CASE\" ,DCM_POS_DETAIL.POS_CHANNEL_ID,DCM_POS_DETAIL.DCM_PAYMENT_LEVEL_ID,"+
//" GEN_DCM.DCM_LEVEL_ID "+
//" from dcm_pos_detail,gen_person,gen_dcm,SCM_POS_ASSIGNED_GROUP,SCM_REP_POS_GROUP,DCM_USER"+
//" ,dcm_pos_phone,DCM_POS_OWNER,DCM_POS_OWNER_PHONE,DCM_POS_MANAGER,DCM_POS_MANAGER_PHONE"+
//" ,SCM_PROPOSED_DOCUMENT,GEN_DCM_STATUS,SCM_REP_SUPERVISORS,"+
//" SCM_STK_STOCK,SCM_STK_OWNER,SCM_CBILL_CASE";
//String whereStr =" where SCM_POS_ASSIGNED_GROUP.DCM_ID =dcm_pos_detail.POS_ID"+
//" and SCM_REP_POS_GROUP.\"GROUP_ID\" = SCM_POS_ASSIGNED_GROUP.\"GROUP_ID\""+
//" and DCM_USER.DCM_USER_ID =SCM_REP_POS_GROUP.DCM_USER_ID"+
//" and SCM_REP_SUPERVISORS.REP_ID= DCM_USER.DCM_USER_ID"+
//" and DCM_POS_PHONE.POS_DETAIL_ID=DCM_POS_DETAIL.POS_DETAIL_ID"+
//" and DCM_POS_OWNER.POS_DETAIL_ID=DCM_POS_DETAIL.POS_DETAIL_ID"+
//" and DCM_POS_OWNER_PHONE.POS_OWNER_ID=DCM_POS_OWNER.POS_OWNER_ID"+
//" and DCM_POS_MANAGER.POS_DETAIL_ID(+)=DCM_POS_DETAIL.POS_DETAIL_ID"+
//" and DCM_POS_MANAGER_PHONE.POS_MANAGER_ID(+)=DCM_POS_MANAGER.POS_MANAGER_ID"+
//" and SCM_PROPOSED_DOCUMENT.PROPOSED_DOCUMENT_ID(+)=DCM_POS_DETAIL.POS_PROPOSED_DOC_ID"+
//" and gen_dcm.DCM_STATUS_ID=GEN_DCM_STATUS.DCM_STATUS_ID"+
//" and SCM_STK_OWNER.DCM_ID(+)=dcm_pos_detail.POS_ID"+
//" and SCM_STK_STOCK.STK_ID(+)=SCM_STK_OWNER.STK_ID"+
//" and SCM_CBILL_CASE.STK_ID(+)=SCM_STK_OWNER.STK_ID"+
//" and GEN_DCM.DCM_ID=dcm_pos_detail.POS_ID" +
//" and DCM_POS_DETAIL.HISTORY_ID IS NULL";


        StringBuilder query = new StringBuilder();
        /*
         * Formatted on 2011/02/17 14:40 (Formatter Plus v4.8.8)
         */
        query.append(" SELECT detail.SUPERVISOR_ID,detail.TEAMLEADER_ID,detail.SALESREP_ID,detail.pos_detail_id, getuserbyid (detail.user_id) enterby, ");
        query.append(" detail.pos_code, detail.pos_name, detail.pos_arabic_name, ");
        query.append(" getregionbyid (detail.region_id) region, ");
        query.append(" getregionbyid (detail.pos_governrate) governrate, ");
        query.append(" getregionbyid (detail.pos_city_id) city, ");
        query.append(" getregionbyid (detail.pos_district_id) district, ");
        query.append(" getregionbyid (detail.pos_area_id) area, ");
        query.append(" get_rep_super_pos_detail (detail.pos_detail_id) super_rep, ");
        query.append(" detail.pos_address, detail.pos_arabic_address, ");
        query.append(" phone.pos_phone_number, detail.pos_demo_line, ");
        query.append(" getownerormandata (detail.pos_detail_id, 1) ownerdata, ");
        query.append(" getownerormandata (detail.pos_detail_id, 2) mandata, ");
        query.append(" detail.pos_email, doc.proposed_document_name, ");
        query.append(" detail.pos_doc_num, dcmstat.dcm_status_name, stock.stk_number, ");
        query.append(" stkowner.stk_delivery_date, blcase.CASE, ");
        query.append(" stkowner.iqrar_delivery_date, stkowner.iqrar_recieved_date, ");
        query.append(" detail.survey_date, dcm.dcm_payment_level_id, dcm.channel_id, ");
        query.append(" dcm.dcm_level_id, ");
        query.append(" paymentstatus.payment_cam_state_id,");
        query.append(" detail.survey_id");
        query.append(" FROM dcm_pos_detail detail, ");
        query.append(" dcm_pos_owner owner, ");
        query.append(" dcm_pos_manager manager, ");
        query.append(" gen_dcm dcm, ");
        query.append(" gen_dcm_status dcmstat, ");
        query.append(" scm_stk_owner stkowner, ");
        query.append(" cam_payment_scm_status paymentstatus, ");
        query.append(" dcm_pos_phone phone, ");
        query.append(" scm_stk_stock stock, ");
        query.append(" scm_proposed_document doc, ");
        query.append(" scm_cbill_case blcase ");
        query.append(" WHERE detail.history_id IS NULL ");
        query.append(" AND owner.pos_detail_id = detail.pos_detail_id ");
        query.append(" AND manager.pos_detail_id(+) = detail.pos_detail_id ");
        query.append(" AND dcm.dcm_id = detail.pos_id(+) ");
        query.append(" AND stkowner.dcm_id(+) = detail.pos_id ");
        query.append(" AND paymentstatus.scm_id(+) = detail.pos_id ");
        query.append(" AND phone.pos_detail_id(+) = detail.pos_detail_id ");
        query.append(" AND detail.pos_id = stkowner.dcm_id(+) ");
        query.append(" AND stock.stk_id(+) = stkowner.stk_id ");
        query.append(" AND doc.proposed_document_id(+) = detail.pos_proposed_doc_id ");
        query.append(" AND dcm.dcm_status_id = dcmstat.dcm_status_id ");
        query.append(" AND blcase.stk_id(+) = stock.stk_id ");


        String[] queryStrings = appendFromWhereStrs(posDataOwnerIdType, posDataDocNum, posDataManagerName, posDataStkNum, posDataManagerIdType, posDataProposedDoc, posDataManagerIdNum, posDataName, posDataCode, posDataRegion, posDataGover, posDataDistrict, posDataArea, posDataCity, posDataOwnerName, posDataOwnerIdNum, level, payment, channel, posStatusId, stkStatusId, paymentStatusId, posPhone, englishAdress, strSurvDate, docLocation,supervisorId,supervisorName, teamleaderId, teamleaderName, salesrepId, salesrepName);


        String queryStr = query.toString() + queryStrings[1];
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(queryStr);
        while (rs.next()) {
            POSSearchExcelModel posSearchExcelModel = new POSSearchExcelModel();
            posSearchExcelModel.setSupervisorId(getUserDataByDetailId(con, rs.getString("SUPERVISOR_ID")).getUserFullName());//rs.getString("SUPERVISOR_ID")
            posSearchExcelModel.setTeamleaderId(getUserDataByDetailId(con, rs.getString("TEAMLEADER_ID")).getUserFullName()); //rs.getString("TEAMLEADER_ID")
            posSearchExcelModel.setSalesrepId(getUserDataByDetailId(con, rs.getString("SALESREP_ID")).getUserFullName());//rs.getString("SALESREP_ID")
            posSearchExcelModel.setArabicAddress(rs.getString("POS_ARABIC_ADDRESS"));
            posSearchExcelModel.setAreaId(rs.getString("AREA"));
            posSearchExcelModel.setCbillCase(rs.getString("CASE"));
            posSearchExcelModel.setCityId(rs.getString("CITY"));
            posSearchExcelModel.setDistrictId(rs.getString("DISTRICT"));
            posSearchExcelModel.setEnglishaddress(rs.getString("POS_ADDRESS"));
            posSearchExcelModel.setEnteryBy(rs.getString("ENTERBY"));
            posSearchExcelModel.setEntryDate(rs.getTimestamp("SURVEY_DATE"));
            posSearchExcelModel.setGovernrateId(rs.getString("GOVERNRATE"));
            posSearchExcelModel.setIqrarDeliveryDate(rs.getDate("IQRAR_DELIVERY_DATE"));
            posSearchExcelModel.setIqrarReceviedDate(rs.getDate("IQRAR_RECIEVED_DATE"));
            posSearchExcelModel.setPosPaymentstatus(rs.getInt("payment_cam_state_id"));
            String managerData = rs.getString("MANDATA");
            String[] manDataArr = spliteResult(managerData);
            // System.out.println(managerData);
            posSearchExcelModel.setManagerBirthDate(manDataArr != null ? manDataArr[1] : "");
            posSearchExcelModel.setManagerIDNumber(manDataArr != null ? manDataArr[4] : "");
            posSearchExcelModel.setManagerIDTypeNumber(manDataArr != null ? manDataArr[3] : "");
            posSearchExcelModel.setManagerName(manDataArr != null ? manDataArr[0] : "");
            posSearchExcelModel.setManagerPhoneNumber(manDataArr != null ? manDataArr[2] : "");

            String ownerrData = rs.getString("OWNERDATA");
            String[] ownerDataArr = spliteResult(ownerrData);
            posSearchExcelModel.setOwnerBirthDate(ownerDataArr != null ? ownerDataArr[1] : "");
            posSearchExcelModel.setOwnerIDNo(ownerDataArr != null ? ownerDataArr[4] : "");
            posSearchExcelModel.setOwnerIDTypeNumber(ownerDataArr != null ? ownerDataArr[3] : "");
            posSearchExcelModel.setOwnerName(ownerDataArr != null ? ownerDataArr[0] : "");
            posSearchExcelModel.setOwnerPhoneNmber(ownerDataArr != null ? ownerDataArr[2] : "");

            posSearchExcelModel.setPosArabicName(rs.getString("POS_ARABIC_NAME"));
            posSearchExcelModel.setPosCode(rs.getString("POS_CODE"));
            posSearchExcelModel.setPosDemoLine(rs.getString("POS_DEMO_LINE"));
            posSearchExcelModel.setPosDocumentNumber(rs.getString("POS_DOC_NUM"));
            posSearchExcelModel.setPosDocumentType(rs.getString("PROPOSED_DOCUMENT_NAME"));
            posSearchExcelModel.setPosEmail(rs.getString("POS_EMAIL"));
            posSearchExcelModel.setPosEnglishName(rs.getString("POS_NAME"));
            posSearchExcelModel.setPosId(rs.getString("POS_DETAIL_ID"));
            posSearchExcelModel.setPosLevel(rs.getString("DCM_LEVEL_ID"));
            posSearchExcelModel.setPosPayment(rs.getString("DCM_PAYMENT_LEVEL_ID"));
            posSearchExcelModel.setPosPhoneNumber(rs.getString("POS_PHONE_NUMBER"));


            String repData = rs.getString("SUPER_REP");
            String[] repDataArr = spliteResult(repData);
            posSearchExcelModel.setPosRebName(repDataArr != null ? repDataArr[1] : "");
            posSearchExcelModel.setPosSupervisor(repDataArr != null ? repDataArr[0] : "");

            posSearchExcelModel.setPosStatus(rs.getString("DCM_STATUS_NAME"));
            posSearchExcelModel.setPoschannel(rs.getString("CHANNEL_ID"));
            posSearchExcelModel.setRegionId(rs.getString("REGION"));
            posSearchExcelModel.setStkDeliveryDate(rs.getDate("STK_DELIVERY_DATE"));
            posSearchExcelModel.setStkNumber(rs.getString("STK_NUMBER"));
            posSearchExcelModel.setSurveyId(rs.getString("survey_id"));
            posDetailVec.add(posSearchExcelModel);
        }
        DBUtil.close(st);
        DBUtil.close(rs);
//        posDetailVec=DBUtil.executeSqlQueryMultiValue(queryStr,POSSearchExcelModel.class, con);
        return posDetailVec;
    }

    private static String[] spliteResult(String data) {
        return data != null && data.compareTo("") != 0 ? (data + " ").split("-,") : null;
    }

    public static int searchPosDataTotal(Connection con, String posDataOwnerIdType, String posDataDocNum, String posDataManagerName, String posDataStkNum, String posDataManagerIdType, String posDataProposedDoc, String posDataManagerIdNum, String posDataName, String posDataCode, String posDataRegion, String posDataGover, String posDataDistrict, String posDataArea, String posDataCity, String posDataOwnerName, String posDataOwnerIdNum, String level, String payment, String channel) throws SQLException {
        String selectStr = "select  CEIL (Count(distinct(detail.POS_DETAIL_ID))/20) Count ";
        String fromStr = " from DCM_POS_DETAIL detail , DCM_POS_OWNER owner";
        String whereStr = " where detail.flage IS NULL AND owner.POS_DETAIL_ID = detail.POS_DETAIL_ID";


        if (posDataName != null && posDataName.compareTo("") != 0) {
            whereStr = whereStr + " and lower(POS_NAME) like '" + posDataName.toLowerCase() + "'";
            // whereStr = whereStr + " OR POS_NAME = '" + posDataName.toLowerCase() + "'";
            // whereStr = whereStr + " OR POS_NAME = '" + posDataName.toUpperCase() + "'";

        }

        if (posDataCode != null && posDataCode.compareTo("") != 0) {

            whereStr = whereStr + " and POS_Code = '" + posDataCode + "'";

        }

        if (posDataRegion != null && posDataRegion.compareTo("") != 0) {

            whereStr = whereStr + " and REGION_ID = " + posDataRegion;

        }

        if (posDataGover != null && posDataGover.compareTo("") != 0) {

            whereStr = whereStr + " and POS_GOVERNRATE = " + posDataGover;

        }

        if (posDataCity != null && posDataCity.compareTo("") != 0) {

            whereStr = whereStr + " and POS_CITY_ID = '" + posDataCity + "'";

        }

        if (posDataDistrict != null && posDataDistrict.compareTo("") != 0) {

            whereStr = whereStr + " and POS_DISTRICT_ID = '" + posDataDistrict + "'";

        }

        if (posDataArea != null && posDataArea.compareTo("") != 0) {

            whereStr = whereStr + " and POS_AREA_ID = " + posDataArea;

        }

        if (posDataDocNum != null && posDataDocNum.compareTo("") != 0) {

            whereStr = whereStr + " and POS_DOC_NUM = '" + posDataDocNum + "'";

        }

        if (posDataProposedDoc != null && posDataProposedDoc.compareTo("") != 0) {

            whereStr = whereStr + " and POS_PROPOSED_DOC_ID = " + posDataProposedDoc;

        }


        if (posDataProposedDoc != null && posDataProposedDoc.compareTo("") != 0) {

            whereStr = whereStr + " and POS_PROPOSED_DOC_ID = " + posDataProposedDoc;

        }


        /*
         * if((posDataOwnerIdType.compareTo("") !=0 && posDataOwnerIdType !=
         * null) || (posDataOwnerName.compareTo("") !=0 && posDataOwnerName !=
         * null) || (posDataOwnerIdNum.compareTo("") !=0 && posDataOwnerIdNum !=
         * null) ) { fromStr = fromStr + " , DCM_POS_OWNER owner";
         * if(whereEmpty== true) whereStr = whereStr + "owner.POS_DETAIL_ID =
         * detail.POS_DETAIL_ID"; else whereStr = whereStr +
         * "owner.POS_DETAIL_ID = detail.POS_DETAIL_ID";
         *
         * }
         *
         */
        if (posDataOwnerIdNum != null && posDataOwnerIdNum.compareTo("") != 0) {

            whereStr = whereStr + " and POS_OWNER_ID_NUMBER = " + posDataOwnerIdNum;

        }

        if (posDataOwnerIdType != null && posDataOwnerIdType.compareTo("") != 0) {

            whereStr = whereStr + " and POS_OWNER_ID_TYPE_ID = " + posDataOwnerIdType;

        }

        if (posDataOwnerName != null && posDataOwnerName.compareTo("") != 0) {

            whereStr = whereStr + " and lower(POS_OWNER_NAME) like '" + posDataOwnerName.toLowerCase() + "'";
            //  whereStr = whereStr + " OR POS_OWNER_NAME = '" + posDataOwnerName.toLowerCase() + "'";
            // whereStr = whereStr + " OR POS_OWNER_NAME = '" + posDataOwnerName.toUpperCase() + "'";

        }

        /*
         * if((posDataManagerIdType.compareTo("") !=0 && posDataManagerIdType !=
         * null) || (posDataManagerName.compareTo("") !=0 && posDataManagerName
         * != null) || (posDataManagerIdNum.compareTo("") !=0 &&
         * posDataManagerIdNum != null) ) { fromStr = fromStr + " ,
         * DCM_POS_MANAGER manager"; whereStr = whereStr +
         * "manager.POS_DETAIL_ID = detail.POS_DETAIL_ID"; }
         */
        if (posDataManagerIdNum != null && posDataManagerIdNum.compareTo("") != 0) {

            whereStr = whereStr + " and POS_MANAGER_ID_NUMBER = " + posDataManagerIdNum;

        }

        if (posDataManagerIdType != null && posDataManagerIdType.compareTo("") != 0) {

            whereStr = whereStr + " and POS_MANAGER_ID_TYPE_ID = " + posDataManagerIdType;

        }

        if (posDataManagerName != null && posDataManagerName.compareTo("") != 0) {

            whereStr = whereStr + " and lower(POS_MANAGER_NAME) like '" + posDataManagerName.toLowerCase() + "'";
            //  whereStr = whereStr + " OR POS_MANAGER_NAME = '" + posDataManagerName.toUpperCase() + "'";
            // whereStr = whereStr + " OR POS_MANAGER_NAME = '" + posDataManagerName + "'";
        }


        if (posDataStkNum != null && posDataStkNum.compareTo("") != 0) {
            fromStr = fromStr + " , SCM_STK_OWNER stkOwner , SCM_STK_STOCK stock ";

            whereStr = whereStr + " and detail.POS_ID = stkOwner.DCM_ID and stock.STK_ID = stkOwner.STK_ID";
        }

        if (posDataStkNum != null && posDataStkNum.compareTo("") != 0) {

            whereStr = whereStr + " and STK_NUMBER = '" + posDataStkNum + "'";

        }

        if (channel != null && channel.compareTo("") != 0) {

            whereStr = whereStr + " and POS_CHANNEL_ID = '" + channel + "'";

        }
        if (level != null && level.compareTo("") != 0) {

            whereStr = whereStr + " and POS_ID IN (SELECT DCM_ID FROM GEN_DCM WHERE DCM_LEVEL_ID ='" + level + "')";

        }
        if (payment != null && payment.compareTo("") != 0) {

            whereStr = whereStr + " and POS_ID IN (SELECT DCM_ID FROM GEN_DCM WHERE DCM_PAYMENT_LEVEL_ID ='" + payment + "')";

        }

        String queryStr = selectStr + fromStr + whereStr;
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        int count = DBUtil.executeQuerySingleValueInt(queryStr, "Count", con);
        return count;
    }

    public static Vector getAllPosDataSearch(Connection con) throws SQLException {

        Vector<POSDetailModel> posDetailVec = new Vector<POSDetailModel>();
        String selectStr = "select detail.POS_DETAIL_ID ,detail.POS_NAME , detail.POS_Code , detail.POS_ADDRESS , owner.POS_OWNER_NAME ";
        String fromStr = " from DCM_POS_DETAIL detail , DCM_POS_OWNER owner";
        String whereStr = " where detail.FLAGE IS NULL AND owner.POS_DETAIL_ID = detail.POS_DETAIL_ID";
        String queryStr = selectStr + fromStr + whereStr;
        Statement stmt = con.createStatement();
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);

        while (rs.next()) {
            POSDetailModel posObject = new POSDetailModel();
            posObject.setPosName(rs.getString("POS_NAME"));
            posObject.setPOSCode(rs.getString("POS_Code"));
            posObject.setPosAddress(rs.getString("POS_ADDRESS"));
            posObject.setPosManagerName(rs.getString("POS_MANAGER_NAME"));
            posObject.setPosOwnerName(rs.getString("POS_OWNER_NAME"));
            posObject.setPosID(rs.getInt("POS_DETAIL_ID"));
            posDetailVec.add(posObject);

        }

        stmt.close();
        rs.close();


        return posDetailVec;
    }

    public static PosModel getPosDetailData(Connection con, PosModel posModel, String detailId) throws SQLException, Exception {

        String queryStr = "select distinct(detail.POS_DETAIL_ID),detail.supervisor_id, detail.teamleader_id,detail.salesrep_id,detail.POS_PLACE_TYPE_ID, gen.DCM_ID ,POS_BRANCH_OF ,DCM_LEVEL_NAME , CHANNEL_NAME ,"
                + " POS_RATE_DATE, POS_PLACE_TYPE_NAME , DCM_PAYMENT_LEVEL_NAME , POS_DEMO_LINE ,"
                + " POS_DISTRICT_ID , POS_CITY_ID , REGION_ID  , POS_GOVERNRATE , POS_AREA_ID "
                + "  , POS_OWNER_NAME , POS_OWNER_BIRTHDATE , POS_OWNER_ID_NUMBER ,POS_OWNER_ID_TYPE_ID"
                + "  , POS_MANAGER_NAME , POS_MANAGER_BIRTHDATE , POS_MANAGER_ID_NUMBER , POS_MANAGER_ID_TYPE_ID ,detail.POS_ARABIC_NAME,detail.POS_ARABIC_ADDRESS,detail.DOC_LOCATION,detail.survey_id,detail.POS_DOC_NUM,SCM_PROPOSED_DOCUMENT.PROPOSED_DOCUMENT_NAME"
                + " from "
                + " GEN_DCM_LEVEL poslevel, DCM_POS_PLACE_TYPE place,"
                + " GEN_DCM_PAYMENT_LEVEL poslevelPAYMENT,SCM_PROPOSED_DOCUMENT, "
                + " GEN_CHANNEL channel, "
                + " DCM_POS_DETAIL detail, "
                + " GEN_DCM gen, "
                + " DCM_POS_OWNER owner "
                + " ,DCM_POS_MANAGER MANAGER "
                + " where "
                + "  detail.FLAGE IS NULL AND "
                + " poslevelPAYMENT.DCM_PAYMENT_LEVEL_ID = detail.POS_RATE_ID "
                + " and "
                + " poslevel.DCM_LEVEL_ID = gen.DCM_LEVEL_ID "
                + " and "
                + " channel.CHANNEL_ID = detail.POS_CHANNEL_ID"
                + " AND"
                + " detail.POS_ID=gen.DCM_ID"
                + " and owner.POS_DETAIL_ID=detail.POS_DETAIL_ID "
                + " and detail.POS_PROPOSED_DOC_ID=SCM_PROPOSED_DOCUMENT.PROPOSED_DOCUMENT_ID(+)"
                + " and MANAGER.POS_DETAIL_ID(+)=detail.POS_DETAIL_ID AND place.POS_PLACE_TYPE_ID = detail.POS_PLACE_TYPE_ID  "
                + " and detail.POS_DETAIL_ID = " + detailId;




        Statement stmt = con.createStatement();
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);

        if (rs.next()) {
            posModel.setBranchOf(rs.getString("POS_BRANCH_OF"));
            
            // posModel.setRate(rs.getString("DCM_PAYMENT_LEVEL_NAME"));
            posModel.setRate(rs.getString("POS_PLACE_TYPE_NAME"));
            posModel.setRateID(rs.getInt("POS_PLACE_TYPE_ID"));
            posModel.setLevel(rs.getString("DCM_LEVEL_NAME"));
            posModel.setChannel(rs.getString("CHANNEL_NAME"));
            posModel.setDocument(rs.getString("PROPOSED_DOCUMENT_NAME"));
            // posModel.setRateDate(rs.getString("POS_PLACE_TYPE_ID"));
            posModel.setDocNumber(rs.getString("POS_DOC_NUM"));
            posModel.setDemoLineNum(rs.getString("POS_DEMO_LINE"));
            // posModel.setStkDialNumber(rs.getString("STK_NUMBER"));
            //posModel.setStkVerify(rs.getString("STK_VERIFICATION"));
            posModel.setAreaId(rs.getInt("POS_AREA_ID"));
            posModel.setDistrictId(Integer.parseInt(rs.getString("POS_DISTRICT_ID")));
            posModel.setCityId(Integer.parseInt(rs.getString("POS_CITY_ID")));
            posModel.setGovernateId(rs.getInt("POS_GOVERNRATE"));
            posModel.getPosDetailModel().setPosRegionID(rs.getInt("REGION_ID"));
            posModel.getPosDetailModel().setSupervisorName(rs.getString("supervisor_id"));
            posModel.getPosDetailModel().setTeamleaderName(rs.getString("teamleader_id"));
            posModel.getPosDetailModel().setSalesrepName(rs.getString("salesrep_id"));
            posModel.setArea(getRegionName(con, rs.getInt("POS_AREA_ID")));
            posModel.setPosDetailId(rs.getInt("POS_DETAIL_ID"));
            posModel.setPosId(rs.getInt("DCM_ID"));

            posModel.setDistrict(getRegionName(con, Integer.parseInt(rs.getString("POS_DISTRICT_ID"))));
            posModel.setGovernrate(getRegionName(con, rs.getInt("POS_GOVERNRATE")));
            posModel.setCity(getRegionName(con, Integer.parseInt(rs.getString("POS_CITY_ID"))));

            posModel.setRegion(getRegionName(con, rs.getInt("REGION_ID")));
            posModel.getPosDetailModel().setPosOwnerIDTypeName(getIdType(con, rs.getInt("POS_OWNER_ID_TYPE_ID")));
            posModel.getPosDetailModel().setPosManagerIDTypeName(getIdType(con, rs.getInt("POS_MANAGER_ID_TYPE_ID")));


            posModel.getPosDetailModel().setPosManagerBirthDate(convertDBDateToString(rs.getDate("POS_MANAGER_BIRTHDATE")));
            posModel.getPosDetailModel().setPosManagerIDNumber(rs.getString("POS_MANAGER_ID_NUMBER"));
            posModel.getPosDetailModel().setPosManagerName(rs.getString("POS_MANAGER_NAME"));

            posModel.getPosDetailModel().setPosOwnerBirthDate(convertDBDateToString(rs.getDate("POS_OWNER_BIRTHDATE")));
            posModel.getPosDetailModel().setPosOwnerIDNumber(rs.getString("POS_OWNER_ID_NUMBER"));
            posModel.getPosDetailModel().setPosOwnerName(rs.getString("POS_OWNER_NAME"));
            posModel.getPosDetailModel().setPosArabicName(rs.getString("POS_ARABIC_NAME"));
            posModel.getPosDetailModel().setPosArabicAddress(rs.getString("POS_ARABIC_ADDRESS"));
            posModel.setDocLocation(rs.getString("DOC_LOCATION"));
            posModel.getPosDetailModel().setSurveyID(rs.getString("survey_id"));


        }

        stmt.close();
        rs.close();

        posModel = getPosDetailDocData(con, posModel, detailId);
        posModel = getPosDetailSTKData(con, posModel, detailId);

        return posModel;
    }

    
    public static PosModel getPosData(Connection con, PosModel posModel, String dcmCode,String detailId) throws SQLException, Exception {

        String queryStr = "select * from gen_dcm where dcm_code = '"+dcmCode+"' ";



        Statement stmt = con.createStatement();
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);

        if (rs.next()) {
           /* posModel.setBranchOf(rs.getString("POS_BRANCH_OF"));
            // posModel.setRate(rs.getString("DCM_PAYMENT_LEVEL_NAME"));
            posModel.setRate(rs.getString("POS_PLACE_TYPE_NAME"));
            posModel.setRateID(rs.getInt("POS_PLACE_TYPE_ID"));
            posModel.setLevel(rs.getString("DCM_LEVEL_NAME"));
            posModel.setChannel(rs.getString("CHANNEL_NAME"));
            posModel.setDocument(rs.getString("PROPOSED_DOCUMENT_NAME"));
            // posModel.setRateDate(rs.getString("POS_PLACE_TYPE_ID"));
            posModel.setDocNumber(rs.getString("POS_DOC_NUM"));
            posModel.setDemoLineNum(rs.getString("POS_DEMO_LINE"));
            // posModel.setStkDialNumber(rs.getString("STK_NUMBER"));
            //posModel.setStkVerify(rs.getString("STK_VERIFICATION"));
            posModel.setAreaId(rs.getInt("POS_AREA_ID"));
            posModel.setDistrictId(Integer.parseInt(rs.getString("POS_DISTRICT_ID")));
            posModel.setCityId(Integer.parseInt(rs.getString("POS_CITY_ID")));
            posModel.setGovernateId(rs.getInt("POS_GOVERNRATE"));
            posModel.getPosDetailModel().setPosRegionID(rs.getInt("REGION_ID"));

            posModel.setArea(getRegionName(con, rs.getInt("POS_AREA_ID")));
            posModel.setPosDetailId(rs.getInt("POS_DETAIL_ID"));
            posModel.setPosId(rs.getInt("DCM_ID"));

            posModel.setDistrict(getRegionName(con, Integer.parseInt(rs.getString("POS_DISTRICT_ID"))));
            posModel.setGovernrate(getRegionName(con, rs.getInt("POS_GOVERNRATE")));
            posModel.setCity(getRegionName(con, Integer.parseInt(rs.getString("POS_CITY_ID"))));*/

            if(rs.getString("is_exclusive") != null && rs.getString("is_exclusive").compareTo("1")==0)
            
                posModel.setIsEX(true);
            else
                posModel.setIsEX(false);
            if (rs.getString("is_nomad")!=null && rs.getString("is_nomad").compareTo("1")==0)
                posModel.setIsNomad(true);
            else 
                posModel.setIsNomad(false);
            
            if (rs.getString("report_to_calidus")!=null && rs.getString("report_to_calidus").compareTo("1")==0)
                posModel.setReportToCalidus(true);
            else
                posModel.setReportToCalidus(false);
            if (rs.getString("has_sign")!=null && rs.getString("has_sign").compareTo("1")==0)
                posModel.setIsSignSet(true);
            else
                posModel.setIsSignSet(false);
            if (rs.getString("is_quality_club")!=null && rs.getString("is_quality_club").compareTo("1")==0)
                posModel.setIsQC(true);
            else
                posModel.setIsQC(false);
            if(rs.getString("is_Level_one")!=null && rs.getString("is_Level_one").compareTo("1")==0)
                posModel.setIsL1(true);
            else
                posModel.setIsL1(false);
            
            if (rs.getString("is_mobicash")!=null && rs.getString("is_mobicash").compareTo("1")==0)
                posModel.setIsMobicash(true);
            else
                posModel.setIsMobicash(false);
            
            /*posModel.setRegion(getRegionName(con, rs.getInt("REGION_ID")));
            posModel.getPosDetailModel().setPosOwnerIDTypeName(getIdType(con, rs.getInt("POS_OWNER_ID_TYPE_ID")));
            posModel.getPosDetailModel().setPosManagerIDTypeName(getIdType(con, rs.getInt("POS_MANAGER_ID_TYPE_ID")));


            posModel.getPosDetailModel().setPosManagerBirthDate(convertDBDateToString(rs.getDate("POS_MANAGER_BIRTHDATE")));
            posModel.getPosDetailModel().setPosManagerIDNumber(rs.getString("POS_MANAGER_ID_NUMBER"));
            posModel.getPosDetailModel().setPosManagerName(rs.getString("POS_MANAGER_NAME"));

            posModel.getPosDetailModel().setPosOwnerBirthDate(convertDBDateToString(rs.getDate("POS_OWNER_BIRTHDATE")));
            posModel.getPosDetailModel().setPosOwnerIDNumber(rs.getString("POS_OWNER_ID_NUMBER"));
            posModel.getPosDetailModel().setPosOwnerName(rs.getString("POS_OWNER_NAME"));
            posModel.getPosDetailModel().setPosArabicName(rs.getString("POS_ARABIC_NAME"));
            posModel.getPosDetailModel().setPosArabicAddress(rs.getString("POS_ARABIC_ADDRESS"));
            posModel.setDocLocation(rs.getString("DOC_LOCATION"));
            posModel.getPosDetailModel().setSurveyID(rs.getString("survey_id"));*/


        }

        stmt.close();
        rs.close();

        posModel = getPosDetailDocData(con, posModel, detailId);
        posModel = getPosDetailSTKData(con, posModel, detailId);

        return posModel;
    }

    
    
    
    public static PosModel getPosDetailSTKData(Connection con, PosModel posModel, String detailId) throws SQLException, Exception {

        String queryStr = "select distinct(detail.POS_DETAIL_ID), "
                + " stock.STK_NUMBER , stkOwner.STK_VERIFICATION "
                + " from "
                + " DCM_POS_DETAIL detail, "
                + " SCM_STK_OWNER stkOwner , "
                + " SCM_STK_STOCK stock "
                + " where "
                + "  detail.FLAGE IS NULL AND "
                + " detail.POS_ID = stkOwner.DCM_ID"
                + " and stock.STK_ID = stkOwner.STK_ID and detail.POS_DETAIL_ID = " + detailId;






        Statement stmt = con.createStatement();
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);

        if (rs.next()) {


            posModel.setStkDialNumber(rs.getString("STK_NUMBER"));
            posModel.setStkVerify(rs.getString("STK_VERIFICATION"));


        }

        stmt.close();
        rs.close();


        return posModel;
    }

    public static PosModel getPosDetailDocData(Connection con, PosModel posModel, String detailId) throws SQLException, Exception {

        String queryStr = "select distinct(detail.POS_DETAIL_ID),detail.POS_PROPOSED_DOC_ID,PROPOSED_DOCUMENT_NAME, POS_DOC_NUM , detail.POS_PROPOSED_DOC_ID "
                + " from "
                + " DCM_POS_DETAIL detail, "
                + " GEN_DCM gen, "
                + " SCM_PROPOSED_DOCUMENT doc"
                + " where "
                + " detail.FLAGE IS NULL AND "
                + " doc.PROPOSED_DOCUMENT_ID = detail.POS_PROPOSED_DOC_ID"
                + " and detail.POS_DETAIL_ID = " + detailId;





        Statement stmt = con.createStatement();
        Utility.logger.debug(queryStr);
        System.out.println("queryStr : " + queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);

        if (rs.next()) {
            Integer docId = rs.getInt("POS_PROPOSED_DOC_ID");
            posModel.setDocumentTypeName(rs.getString("PROPOSED_DOCUMENT_NAME"));
            posModel.setDocument(docId.toString());
            posModel.setDocNumber(rs.getString("POS_DOC_NUM"));
            posModel.setProposedDocId(rs.getInt("POS_PROPOSED_DOC_ID"));

        }

        stmt.close();
        rs.close();


        return posModel;
    }

    public static String getIdType(Connection con, int typeId) {
        String type = "";
        try {
            Statement stmt = con.createStatement();
            String sqlString = "SELECT * FROM DCM_ID_TYPE WHERE ID_TYPE_ID = " + typeId;
            System.out.println("sqlString : " + sqlString);
            Utility.logger.debug("SQLLL: " + sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);

            if (rs.next()) {
                type = rs.getString("ID_TYPE_NAME");

            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return type;
    }

    public static POSDetailModel getPOSByID(Connection con, String posDetailId) {
        POSDetailModel posDetails = new POSDetailModel();
        try {
            Statement stmt = con.createStatement();
            String sqlString = "SELECT * FROM dcm_pos_detail WHERE DCM_POS_DETAIL.FLAGE IS NULL AND POS_DETAIL_ID = " + posDetailId;
            System.out.println("sqlString : " + sqlString);
            Utility.logger.debug("SQLLL: " + sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            if (rs.next()) {
                posDetails.setPosAddress(rs.getString("POS_ADDRESS"));
                posDetails.setPosArabicAddress("POS_ARABIC_ADDRESS");
                posDetails.setPosArabicName("POS_ARABIC_NAME");
                posDetails.setPosCityID(rs.getInt("POS_CITY_ID"));
                posDetails.setPOSCode(rs.getString("POS_CODE"));
                posDetails.setPosDistrictID(rs.getInt("POS_DISTRICT_ID"));
                posDetails.setPosEmail(rs.getString("POS_EMAIL"));
                posDetails.setPosName(rs.getString("POS_NAME"));
                posDetails.setSupervisorName(rs.getString("SUPERVISOR_ID"));
                posDetails.setTeamleaderName(rs.getString("TEAMLEADER_ID"));
                posDetails.setSalesrepName(rs.getString("SALESREP_ID"));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return posDetails;
    }

    public static Vector<Integer> getAllUserChange(String posDetailId, Connection con) {
        String query = "SELECT DISTINCT(USER_ID) FROM DCM_POS_DETAIL WHERE DCM_POS_DETAIL.FLAGE=1 AND DCM_POS_DETAIL.POS_DETAIL_ID=" + posDetailId + "ORDER BY USER_ID";
        Vector<Integer> userIds = new Vector<Integer>();
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                userIds.add(rs.getInt("USER_ID"));
            }
            rs.close();
            stmt.close();
            return userIds;
        } catch (SQLException ex) {
            Logger.getLogger(RequestDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }




    }

    public static Vector getPOSPhones(Connection con, String posDetailId) {
        Vector POSPhones = new Vector();
        try {
            Statement stmt = con.createStatement();

            String sqlString = "SELECT POS_PHONE_NUMBER FROM DCM_POS_PHONE WHERE FLAGE IS NULL AND POS_DETAIL_ID =" + posDetailId;
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                Utility.logger.debug("POS PHONES : " + rs.getString("POS_PHONE_NUMBER"));
                System.out.println("####################### " + rs.getString("POS_PHONE_NUMBER"));
                POSPhones.add(rs.getString("POS_PHONE_NUMBER"));
            }
            rs.close();
            stmt.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return POSPhones;
    }

    public static Vector getOwnerPhones(Connection con, String posDetailId) {

        Vector ownerPhones = new Vector();
        try {
            Statement stmt = con.createStatement();
            int ownerID = 0;
            String sqlString = "SELECT POS_OWNER_PHONE_NUMBER FROM DCM_POS_OWNER_PHONE WHERE POS_OWNER_ID IN (SELECT POS_OWNER_ID"
                    + " FROM DCM_POS_OWNER ,DCM_POS_DETAIL  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND DCM_POS_OWNER.POS_DETAIL_ID = DCM_POS_DETAIL.POS_DETAIL_ID and DCM_POS_OWNER.POS_DETAIL_ID = " + posDetailId + ")";
            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                Utility.logger.debug("OWNER PHONE : " + rs.getString("POS_OWNER_PHONE_NUMBER"));
                String ownerPhoneNumber = rs.getString("POS_OWNER_PHONE_NUMBER");
                ownerPhones.add(ownerPhoneNumber);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ownerPhones;

    }

    public static Vector getManagerPhones(Connection con, String posDetailId) {

        Vector managerPhones = new Vector();
        int managerID = 0;
        try {
            Statement stmt = con.createStatement();


            String sqlString = "SELECT POS_MANAGER_PHONE_NUMBER FROM DCM_POS_MANAGER_PHONE WHERE POS_MANAGER_ID IN (SELECT POS_MANAGER_ID"
                    + " FROM DCM_POS_MANAGER ,DCM_POS_DETAIL  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND DCM_POS_MANAGER.POS_DETAIL_ID = DCM_POS_DETAIL.POS_DETAIL_ID and DCM_POS_Manager.POS_DETAIL_ID = " + posDetailId + ")";

            Utility.logger.debug(sqlString);
            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                Utility.logger.debug("MANAGER PHONES" + rs.getString("POS_MANAGER_PHONE_NUMBER"));
                String managerPhoneNumber = rs.getString("POS_MANAGER_PHONE_NUMBER");
                managerPhones.add(managerPhoneNumber);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return managerPhones;

    }

    public static final void deletePosDetailForEdit(Connection con, String posDetailId, String userId) {


        Long historyId = DBUtil.getSequenceNextVal(con, "SCM_History");
        String sqlDeleteDetail = "update dcm_pos_detail set flage=1,UPDATED_IN=sysdate,USER_ID=" + userId + ",HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
        DBUtil.executeSQL(sqlDeleteDetail, con);
        System.out.println("after flage =1 update");
        String sqlDeletePhones = "update dcm_pos_phone set flage=1,UPDATED_IN=sysdate,USER_ID=" + userId + ",HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
        DBUtil.executeSQL(sqlDeletePhones, con);
        System.out.println("after flage =1 update");
        String sqlDeleteManagerPhones = "update DCM_POS_MANAGER_PHONE set FLAGE=1,UPDATED_IN=sysdate,HISTORY_ID=" + historyId + " where POS_MANAGER_ID = "
                + "(SELECT POS_MANAGER_ID FROM DCM_POS_MANAGER WHERE POS_DETAIL_ID = " + posDetailId + " AND FLAGE IS NULL)";

        DBUtil.executeSQL(sqlDeleteManagerPhones);
        System.out.println("after flage =1 update");
        String sqlDeleteOwnerPhones = "update DCM_POS_OWNER_PHONE set FLAGE=1,HISTORY_ID=" + historyId + ",UPDATED_IN=sysdate where POS_OWNER_ID = "
                + "(SELECT POS_OWNER_ID FROM DCM_POS_OWNER WHERE POS_DETAIL_ID = " + posDetailId + " AND FLAGE IS NULL)";

        DBUtil.executeSQL(sqlDeleteOwnerPhones);
        System.out.println("after flage =1 update");


        String sqlDeleteManager = "update DCM_POS_MANAGER set flage=1,UPDATED_IN=sysdate,USER_ID=" + userId + ",HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
        DBUtil.executeSQL(sqlDeleteManager, con);

        String sqlDeleteOwner = "update DCM_POS_OWNER set flage=1,UPDATED_IN=sysdate,USER_ID=" + userId + ",HISTORY_ID=" + historyId + " where pos_Detail_id=" + posDetailId;
        DBUtil.executeSQL(sqlDeleteOwner, con);

    }

    public static Vector<DistributerStaticDataModel> getDistributerStaticData(Connection con) throws SQLException {

        String query = "SELECT DATA_ID,DATA_NAME,DATA_VALUE FROM SCM_DISTRIBUTER_STATIC_DATA ORDER BY DATA_ID";
        Vector<DistributerStaticDataModel> DistributerStaticData = new Vector<DistributerStaticDataModel>();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            DistributerStaticDataModel model = new DistributerStaticDataModel();
            model.setDataId(rs.getInt("DATA_ID"));
            model.setDataName(rs.getString("DATA_NAME"));
            if (rs.getString("DATA_VALUE") != null) {
                model.setDataValue(rs.getString("DATA_VALUE"));
            } else {
                model.setDataValue("");
            }
            DistributerStaticData.add(model);
        }
        rs.close();
        stmt.close();
        return DistributerStaticData;


    }

    public static Vector<DistributerSTKDetailsModel> getDistributerSTKDetails(Connection con, String dateFrom, String dateTo) throws SQLException, Exception {
        /*
         * String query = "SELECT DCM.DCM_NAME ,DCM.DCM_CODE
         * ,DCM_OWNER.POS_OWNER_NAME,CITY.REGION_NAME ,DCM.DCM_ADDRESS
         * ,STK_STOCK.STK_NUMBER,STK_OWNER.STK_ASSIGN_DATE, STK_OWNER.STK_ID" +
         * " FROM GEN_DCM DCM,DCM_REGION CITY,SCM_STK_OWNER
         * STK_OWNER,SCM_STK_STOCK STK_STOCK,DCM_POS_DETAIL DETAIL,DCM_POS_OWNER
         * DCM_OWNER" + " WHERE DCM.DCM_ID=STK_OWNER.DCM_ID AND
         * STK_OWNER.STK_ID=STK_STOCK.STK_ID AND
         * DETAIL.POS_CITY_ID=CITY.REGION_ID" + " AND DCM.DCM_LEVEL_ID=3" // + "
         * AND DCM.DCM_PAYMENT_LEVEL_ID=1" + " AND DCM.CHANNEL_ID=1" + " AND
         * STK_OWNER.DCM_VERIFIED_STATUS_ID=2" + " AND
         * STK_OWNER.STK_STATUS_ID=2" + " AND DCM.DCM_ID=DETAIL.POS_ID" + " AND
         * DETAIL.POS_DETAIL_ID=DCM_OWNER.POS_DETAIL_ID AND
         * STK_OWNER.STK_REPORT_FLAG <> 1";
         */
        String query = "SELECT DCM.DCM_NAME ,DCM.DCM_CODE ,DCM_OWNER.POS_OWNER_NAME,CITY.REGION_NAME ,DCM.DCM_ADDRESS ,STK_STOCK.STK_NUMBER,STK_OWNER.STK_ASSIGN_DATE, STK_OWNER.STK_ID"
                + " FROM GEN_DCM DCM,DCM_REGION CITY,SCM_STK_OWNER STK_OWNER,SCM_STK_STOCK STK_STOCK,DCM_POS_DETAIL DETAIL,DCM_POS_OWNER DCM_OWNER"
                + " WHERE DCM.DCM_ID=STK_OWNER.DCM_ID AND STK_OWNER.STK_ID=STK_STOCK.STK_ID AND DETAIL.POS_CITY_ID=CITY.REGION_ID"
                + " AND DETAIL.FLAGE IS NULL "
                + " AND DCM.DCM_LEVEL_ID in (2,3)"
                //  + " AND DCM.DCM_PAYMENT_LEVEL_ID=1"
                + " AND DCM.CHANNEL_ID=1"
                // + " AND STK_OWNER.DCM_VERIFIED_STATUS_ID=2"
                + " AND STK_OWNER.IQRAR_RECEVING_STATUS_ID=2"
                + " AND STK_OWNER.STK_STATUS_ID IN (2,4)"
                + " AND DCM.DCM_ID=DETAIL.POS_ID"
                + " AND DETAIL.POS_DETAIL_ID=DCM_OWNER.POS_DETAIL_ID"
                + " AND STK_OWNER.STK_ASSIGN_DATE BETWEEN TO_DATE('" + dateFrom + "','dd/mm/yyyy') AND TO_DATE('" + dateTo + "','dd/mm/yyyy') + 1";


        Statement stmt = con.createStatement();
        System.out.println("query : " + query);
        ResultSet rs = stmt.executeQuery(query);
        Vector<DistributerSTKDetailsModel> DistributerSTKData = DBUtil.executeSqlQueryMultiValue(query, DistributerSTKDetailsModel.class, con);

        /*
         * while(rs.next()) { DistributerSTKDetailsModel model = new
         * DistributerSTKDetailsModel(); try {
         * model.setDCMName(rs.getString("DCM_NAME"));
         * model.setDCMCity(rs.getString("REGION_NAME"));
         * model.setDCMAddress(rs.getString("DCM_ADDRESS"));
         * model.setSTK_DIAL(rs.getString("STK_NUMBER"));
         * model.setSTK_ASSIGN_DATE(rs.getDate("STK_ASSIGN_DATE"));
         * model.setDCMCode(rs.getString("DCM_CODE"));
         * model.setDCMOwner(rs.getString("POS_OWNER_NAME"));
         * updateStkReportFlag(con , rs.getInt("STK_ID")+""); } catch
         * (SQLException ex) {
         * Logger.getLogger(DistributerSTKDetailsModel.class.getName()).log(Level.SEVERE,
         * null, ex); } DistributerSTKData.add(model); }
         */
        System.out.println("query : " + query);
        return DistributerSTKData;
    }

    public static void updateStkReportFlag(Connection con, String stkId) throws Exception {
        Statement stat = null;
        try {
            stat = con.createStatement();
            Statement stmt = con.createStatement();
            String sqlString = "update SCM_STK_OWNER set STK_REPORT_FLAG = 1 where STK_ID = " + stkId;
            Utility.logger.debug(sqlString);
            System.out.print(sqlString);
            stat.executeUpdate(sqlString);
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


    }

    public static void main(String args[]) throws SQLException {
        Connection con = Utility.getConnection();
        searchPosDataExcel(con, null, null, null, null, null, null, null, null, "410191.000", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,null,null,null,null,null);
        DBUtil.close(con);

    }

    private static String convertDBDateToString(Date birthDate) {
        if (birthDate == null || birthDate.equals(null) || birthDate.equals("")) {
            return "";
        }
        return birthDate.getDate() + "-" + (birthDate.getMonth() + 1) + "-" + (birthDate.getYear() + 1900);
    }

    public static String getchannelNameForPOS(String POSDetailId) {
        String query = "SELECT CHANNEL_NAME FROM GEN_CHANNEL,DCM_POS_DETAIL  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND POS_CHANNEL_ID=CHANNEL_ID AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;

        String POSChannel = DBUtil.executeQuerySingleValueString(query, "CHANNEL_NAME");
        return POSChannel;
    }

    public static String getchannelIDForPOS(String POSDetailId) {
        String query = "SELECT DCM_POS_DETAIL.POS_CHANNEL_ID FROM GEN_CHANNEL,DCM_POS_DETAIL  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND POS_CHANNEL_ID=CHANNEL_ID AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;

        String POSChannel = DBUtil.executeQuerySingleValueString(query, "POS_CHANNEL_ID");
        return POSChannel;
    }
    public static String getDCMCodeForPOS(String POSDetailId) {
        String query = "SELECT POS_CODE FROM DCM_POS_DETAIL  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;

        String POSCode = DBUtil.executeQuerySingleValueString(query, "POS_CODE");
        return POSCode;
    }
    
    public static String getMobicashNum(String dcmCode) {
        String query = "select mobicash_number from gen_dcm where dcm_code = '"+dcmCode+"'";

        String mobicashNum = DBUtil.executeQuerySingleValueString(query, "mobicash_number");
        return mobicashNum;
    }
    

    public static String getLevelNameForPOS(String POSDetailId) {
        String query = "SELECT DCM_LEVEL_NAME FROM GEN_DCM_LEVEL,DCM_POS_DETAIL,GEN_DCM  WHERE GEN_DCM.DCM_LEVEL_ID=GEN_DCM_LEVEL.DCM_LEVEL_ID  "
                + "AND GEN_DCM.DCM_ID=DCM_POS_DETAIL.POS_ID "
                + " DCM_POS_DETAIL.FLAGE IS NULL "
                + "AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;
        String POSPaymentName = DBUtil.executeQuerySingleValueString(query, "DCM_LEVEL_NAME");

        return POSPaymentName;


    }

    public static String getLevelIDForPOS(String POSDetailId) {
        String query = "SELECT GEN_DCM.DCM_LEVEL_ID FROM GEN_DCM_LEVEL,DCM_POS_DETAIL,GEN_DCM  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND GEN_DCM.DCM_LEVEL_ID=GEN_DCM_LEVEL.DCM_LEVEL_ID  "
                + "AND GEN_DCM.DCM_ID=DCM_POS_DETAIL.POS_ID "
                + "AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;
        String POSPaymentName = DBUtil.executeQuerySingleValueString(query, "DCM_LEVEL_ID");

        return POSPaymentName;


    }
    
    
    
    public static String getStatusIDForPOS(String POSDetailId) {
        String query = "SELECT GEN_DCM.DCM_STATUS_ID FROM GEN_DCM_STATUS,DCM_POS_DETAIL,GEN_DCM  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND GEN_DCM.DCM_STATUS_ID=GEN_DCM_STATUS.DCM_STATUS_ID  "
                + "AND GEN_DCM.DCM_ID=DCM_POS_DETAIL.POS_ID "
                + "AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;
        String POSPaymentName = DBUtil.executeQuerySingleValueString(query, "DCM_STATUS_ID");

        return POSPaymentName;


    }

    public static String getPaymentLevelIDForPOS(String POSDetailId) {
        String query = "SELECT GEN_DCM.DCM_PAYMENT_LEVEL_ID FROM GEN_DCM_LEVEL,DCM_POS_DETAIL,GEN_DCM  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND GEN_DCM.DCM_LEVEL_ID=GEN_DCM_LEVEL.DCM_LEVEL_ID  "
                + "AND GEN_DCM.DCM_ID=DCM_POS_DETAIL.POS_ID "
                + "AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;
        String POSPaymentName = DBUtil.executeQuerySingleValueString(query, "DCM_PAYMENT_LEVEL_ID");

        return POSPaymentName;


    }
    
    public static String getPaymentMethodIDForPOS(String POSDetailId) {
        String query = "SELECT GEN_DCM.PAYMENT_TYPE_METHOD_ID FROM GEN_DCM_LEVEL,DCM_POS_DETAIL,GEN_DCM  WHERE DCM_POS_DETAIL.FLAGE IS NULL AND GEN_DCM.DCM_LEVEL_ID=GEN_DCM_LEVEL.DCM_LEVEL_ID  "
                + "AND GEN_DCM.DCM_ID=DCM_POS_DETAIL.POS_ID "
                + "AND DCM_POS_DETAIL.POS_DETAIL_ID=" + POSDetailId;
        String POSPaymentName = DBUtil.executeQuerySingleValueString(query, "PAYMENT_TYPE_METHOD_ID");

        return POSPaymentName;


    }

    public static Vector<OwnerDetailsForPOSModel> getOwnerDetailsForPOS(String POSDetailId, Connection con) {
        String query = "SELECT D.POS_OWNER_ID, D.POS_OWNER_NAME, D.POS_OWNER_BIRTHDATE, "
                + " D.POS_OWNER_ID_TYPE_ID, D.POS_OWNER_ID_NUMBER, "
                + " FROM SDS.DCM_POS_OWNER D WHERE D.POS_DETAIL_ID=" + POSDetailId;

        Vector<OwnerDetailsForPOSModel> Owners = DBUtil.executeSqlQueryMultiValue(query, OwnerDetailsForPOSModel.class, con);

        return Owners;

    }

    public static Vector<OwnerDetailsForPOSModel> getManagerDetailsForPOS(String POSDetailId, Connection con) {
        String query = " SELECT D.POS_MANAGER_ID, D.POS_MANAGER_NAME, D.POS_MANAGER_BIRTHDATE, "
                + " D.POS_MANAGER_ID_TYPE_ID, D.POS_MANAGER_ID_NUMBER, D.POS_DETAIL_ID "
                + " FROM SDS.DCM_POS_MANAGER D WHERE D.POS_DETAIL_ID=" + POSDetailId;

        Vector<OwnerDetailsForPOSModel> Owners = DBUtil.executeSqlQueryMultiValue(query, OwnerDetailsForPOSModel.class, con);

        return Owners;
    }

    public static String getPOSCodeFromId(String POSDetailId) {
        String query = "SELECT POS_CODE FROM dcm_pos_detail WHERE POS_DETAIL_ID=" + POSDetailId;
        return DBUtil.executeQuerySingleValueString(query, "POS_CODE");

    }

    public static Vector<GeneralHistory> getHistoricalData(String POSDetailId, Connection con) {

        String POSCode = RequestDao.getPOSCodeFromId(POSDetailId);

        String query = "select POS_ARABIC_ADDRESS,pos_id,DCM_PAYMENT_LEVEL_ID,POS_NAME,POS_EMAIL,POS_ADDRESS,REGION_ID,POS_GOVERNRATE , POS_AREA_ID , POS_DEMO_LINE,POS_DISTRICT_ID , POS_CITY_ID,"
                + " DCM_POS_DETAIL.UPDATED_IN,DCM_POS_DETAIL.USER_ID,POS_MANAGER_NAME , POS_MANAGER_BIRTHDATE,POS_MANAGER_ID_TYPE_ID ,POS_MANAGER_ID_NUMBER,"
                + " POS_OWNER_NAME , POS_OWNER_BIRTHDATE,POS_OWNER_ID_TYPE_ID ,POS_OWNER_ID_NUMBER ,"
                + " POS_MANAGER_PHONE_NUMBER,POS_OWNER_PHONE_NUMBER,POS_PHONE_NUMBER,DCM_POS_DETAIL.DOC_LOCATION"
                + " from"
                + " dcm_pos_detail,dcm_pos_manager,dcm_pos_manager_phone,dcm_pos_owner,dcm_pos_owner_phone,dcm_pos_phone"
                + " where "
                + " DCM_POS_DETAIL.POS_DETAIL_ID = DCM_POS_MANAGER.POS_DETAIL_ID"
                + " and DCM_POS_MANAGER.POS_MANAGER_ID = DCM_POS_MANAGER_PHONE.POS_MANAGER_ID"
                + " and DCM_POS_DETAIL.POS_DETAIL_ID = DCM_POS_OWNER.POS_DETAIL_ID"
                + " and DCM_POS_OWNER.POS_OWNER_ID = DCM_POS_OWNER_PHONE.POS_OWNER_ID"
                + " and DCM_POS_DETAIL.POS_DETAIL_ID = DCM_POS_PHONE.POS_DETAIL_ID"
                + " and DCM_POS_DETAIL.FLAGE = '1'"
                + " and DCM_POS_MANAGER.FLAGE = '1'"
                + " and DCM_POS_MANAGER_PHONE.FLAGE = '1'"
                + " and DCM_POS_OWNER_PHONE.FLAGE = '1'"
                + " and DCM_POS_OWNER.FLAGE = '1'"
                + " and DCM_POS_PHONE.FLAGE = '1'"
                + " and DCM_POS_DETAIL.HISTORY_ID = DCM_POS_MANAGER.HISTORY_ID"
                + " and DCM_POS_MANAGER.HISTORY_ID = DCM_POS_MANAGER_PHONE.HISTORY_ID"
                + " and DCM_POS_DETAIL.HISTORY_ID = DCM_POS_OWNER.HISTORY_ID"
                + " and DCM_POS_OWNER.HISTORY_ID = DCM_POS_OWNER_PHONE.HISTORY_ID"
                + " and DCM_POS_DETAIL.HISTORY_ID = DCM_POS_PHONE.HISTORY_ID"
                + " and pos_code = '" + POSCode + "'"
                + "order by DCM_POS_DETAIL.UPDATED_IN desc";
        System.out.println("query for POS history : "+query);
        Vector<GeneralHistory> historyVec = DBUtil.executeSqlQueryMultiValue(query, GeneralHistory.class, con);
        System.out.println(query);
        return historyVec;

    }

    public static String getRegionNameFromId(String regionId) {
        String query = "SELECT REGION_NAME FROM DCM_REGION WHERE REGION_ID=" + regionId;
        String regionName = DBUtil.executeQuerySingleValueString(query, "REGION_NAME");
        return regionName;
    }

    public static String getIdType(String idType) {
        String query = "SELECT ID_TYPE_NAME FROM DCM_ID_TYPE WHERE ID_TYPE_ID=" + idType;
        String typeName = DBUtil.executeQuerySingleValueString(query, "ID_TYPE_NAME");
        return typeName;
    }

    public static String getPaymentTypeName(String paymentId) {
        String query = "SELECT DCM_PAYMENT_LEVEL_NAME from GEN_DCM_PAYMENT_LEVEL WHERE DCM_PAYMENT_LEVEL_ID=" + paymentId;
        String paymentName = DBUtil.executeQuerySingleValueString(query, "DCM_PAYMENT_LEVEL_NAME");
        return paymentName;

    }

    public static String getUserName(String userId) {
        String query = "SELECT PERSON_FULL_NAME  FROM GEN_PERSON WHERE PERSON_ID=" + userId;
        String userName = DBUtil.executeQuerySingleValueString(query, "PERSON_FULL_NAME");
        return userName;
    }

    public static String getUserSuperVisor(String userId) {

        String query = "SELECT MANAGER_DCM_USER_ID FROM DCM_USER WHERE USER_ID=" + userId;
        String managerId = DBUtil.executeQuerySingleValueString(query, "MANAGER_DCM_USER_ID");

        String query2 = "SELECT PERSON_FULL_NAME FROM GEN_PERSON WHERE PERSON_ID=" + managerId;
        String supervisorName = DBUtil.executeQuerySingleValueString(query2, "PERSON_FULL_NAME");
        return supervisorName;
    }
}
