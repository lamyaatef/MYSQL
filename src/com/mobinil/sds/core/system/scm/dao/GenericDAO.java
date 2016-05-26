/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.utilities.DBUtil;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author SAND
 */
public class GenericDAO {
    public static boolean checkColumnName(String columnNameToCheck,ResultSet rs){
        try {

        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();

        for (int i=1; i<numColumns+1; i++)
        {
            String columnName = rsmd.getColumnName(i);
            if(columnNameToCheck.equalsIgnoreCase(columnName))
                return true;
        }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    public static String getMonthName(String monthId)
    {
        String query="SELECT M.ID, M.NAME FROM SDS.MONTHS M WHERE M.ID= "+monthId;
        return DBUtil.executeQuerySingleValueString(query,"NAME");

    }
    public static String getTimeStampSubstraction(Timestamp t1,Timestamp t2)
    {
     BigInteger ONE_BILLION = new BigInteger ("1000000000");


         BigInteger firstTime;
         BigInteger secondTime;
         BigInteger diffTime;
         BigInteger coverter=new BigInteger("1000000000000") ;
         System.out.println(t1);
         System.out.println(t2);
        firstTime  = BigInteger.valueOf(t1.getTime() / 1000 * 1000).multiply(ONE_BILLION ).add(BigInteger.valueOf(t1.getNanos()));
        secondTime = BigInteger.valueOf(t2.getTime() / 1000 * 1000).multiply(ONE_BILLION ).add(BigInteger.valueOf(t2.getNanos()));
        diffTime   = secondTime.subtract(firstTime);
        System.out.println(firstTime);
        System.out.println(secondTime);
        System.out.println(diffTime.divide(coverter));
        return diffTime.divide(coverter).toString();


    }
}
