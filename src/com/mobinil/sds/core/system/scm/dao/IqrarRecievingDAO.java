/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.scm.model.IqrarRecievingModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author AHMED SAFWAT
 */
public class IqrarRecievingDAO {

    /*
 * Check if the the  POS is exist , POS Level and ACTIVE
 @return DCM_ID if it is Found, -2 if an exception occurred, -1 if POS not found
 */
public static long checkPOS(Connection argCon, IqrarRecievingModel iqrarRecievingModel) {

        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sqlStatement;
            // UPDATED BY MEDHAT
            sqlStatement="SELECT DCM_ID FROM VW_GEN_DCM_SCM WHERE  DCM_CODE=?  AND DCM_STATUS_ID=1"+
           // " AND DCM_PAYMENT_LEVEL_ID=4"+
            " AND CHANNEL_ID=1"  ;
            ps=argCon.prepareStatement(sqlStatement);
            ps.setString(1,iqrarRecievingModel.getPosCode());
            rs=ps.executeQuery();

            while(rs.next()){
                return rs.getLong(1);
            }

        } catch (Exception e) {
            try{
            ps.close();
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }

            e.printStackTrace();
            return -2;

        }finally{
            try{
            ps.close();
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }
        }

        return -1;
}
/*
 * Used to truncate Iqrar Recieving Table
 * @return 1 if the table is truncated , -2 if an exception occured
 */
public static int truncateIqrarRecievingTempTable(Connection argCon) {
        Statement st=null;
        try {
            String sqlStatement;
            sqlStatement="TRUNCATE TABLE SCM_IQRAR_RECIEVING_TEMP";
            st=argCon.createStatement();
            st.executeUpdate(sqlStatement);



        } catch (Exception e) {
            try{
            st.close();

            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }

            e.printStackTrace();
            return -2;

        }finally{
            try{
            st.close();

            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }
        }
            return 1;
    }

public static Date checkPOSIqrarRecieved(Connection argCon, long dcmId) {

        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sqlStatement;
            sqlStatement="SELECT IQRAR_RECIEVED_DATE FROM SCM_STK_OWNER WHERE DCM_ID=?";
            ps=argCon.prepareStatement(sqlStatement);
            ps.setLong(1, dcmId);
            rs=ps.executeQuery();

            while(rs.next()){
                Date iqrarRecievingDate=rs.getDate(1);
                return iqrarRecievingDate;
            }

        } catch (Exception e) {
            try{
            ps.close();
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return null;
            }

            e.printStackTrace();
            return null;

        }finally{
            try{
            ps.close();
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return null;
            }
        }

        return null;
}


public static int checkIfPOSOwnsSTKOrNot(Connection argCon, long dcmId) {

        PreparedStatement ps=null;
        ResultSet rs=null;
        int checkFlag=-1;
        try {
            String sqlStatement;
            sqlStatement="SELECT COUNT(STK_ID) COUNT FROM SCM_STK_OWNER WHERE STK_STATUS_ID<>3 AND DCM_ID=?";
            ps=argCon.prepareStatement(sqlStatement);
            ps.setLong(1, dcmId);
            rs=ps.executeQuery();

            while(rs.next()){
                checkFlag=rs.getInt("COUNT");
                return checkFlag;
            }

        } catch (Exception e) {
            try{
            ps.close();
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }

            e.printStackTrace();
            return -2;

        }finally{
            try{
            ps.close();
            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }
        }

        return checkFlag;
}

/*
 * Used to update IQRAR_VERIFIED_STATUS_ID and IQRAR_VIERIFIED_DATE on SCM_STK_OWNER TABLE to verify the pos
 * @return 1 if the table is updated , -2 if an exception occured
 */
public static int updatePosIqrarRecievedVerification(Connection argCon,long dcmId) {
        PreparedStatement ps=null;
        try {
            String sqlStatement;
            sqlStatement="UPDATE SCM_STK_OWNER SET IQRAR_RECEVING_STATUS_ID=2,IQRAR_RECIEVED_DATE=sysdate WHERE DCM_ID=?";

            ps=argCon.prepareStatement(sqlStatement);
            ps.setLong(1, dcmId);
            ps.executeUpdate();



        } catch (Exception e) {
            try{
            ps.close();

            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }

            e.printStackTrace();
            return -2;

        }finally{
            try{
            ps.close();


            }catch(SQLException sqlE){
                sqlE.printStackTrace();
                return -2;
            }
        }
            return 1;
    }

/*
 * @return Vector of all imported POSIqrarRecieving
 */

public static Vector getIqrarRecievingPOSImported(Connection argCon) {
        Vector listToReturn = new Vector(22);
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sqlStatement;
            sqlStatement="SELECT POS_CODE FROM SCM_IQRAR_RECIEVING_TEMP";
            ps=argCon.prepareStatement(sqlStatement);
            rs=ps.executeQuery();

           while (rs.next()) {
               IqrarRecievingModel item = new IqrarRecievingModel();

                item.setPosCode(rs.getString(1));


                listToReturn.add(item);
            }

        } catch (Exception e) {
            try{
            rs.close();
            ps.close();

            }catch(SQLException sqlE){
                sqlE.printStackTrace();
            }

            e.printStackTrace();

        }finally{
            try{
            rs.close();
            ps.close();

            }catch(SQLException sqlE){
                sqlE.printStackTrace();
            }
        }
        return listToReturn;
    }



}
