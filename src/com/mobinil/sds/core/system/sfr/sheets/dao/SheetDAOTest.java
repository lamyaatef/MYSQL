package com.mobinil.sds.core.system.sfr.sheets.dao;

import java.sql.Connection;

import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;

public class SheetDAOTest
{
    public static void test1()
    {
        System.out.println("Start Test insertSheetStatusV1");
        try
        {
            int transMax = 1;

            for (int j = 0; j < 5; j++)
            {
                Connection con = Utility.getConnection();
                DBUtil.executeSQL("truncate table sFR_SHEET_STATUS", con);
                long stime = System.currentTimeMillis();
                for (int i = 0; i < transMax; i++)
                {
                    insertSheetStatusV1(con, i + "", "1", "48");
                }

                long etime = System.currentTimeMillis();

                System.out.println("Test of " + transMax + " took ="
                        + (etime - stime));
                Utility.closeConnection(con);
                transMax = transMax * 10;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("End Test insertSheetStatusV1");
    }

    public static void test2()
    {
        System.out.println("Start Test insertSheetStatusV2");
        try
        {
            int transMax = 1;

            for (int j = 0; j < 5; j++)
            {
                Connection con = Utility.getConnection();
                DBUtil.executeSQL("truncate table sFR_SHEET_STATUS", con);
                long stime = System.currentTimeMillis();
                for (int i = 0; i < transMax; i++)
                {
                    insertSheetStatusV2(con, i + "", "1", "48");
                }

                long etime = System.currentTimeMillis();

                System.out.println("Test of " + transMax + " took ="
                        + (etime - stime));
                transMax = transMax * 10;
                Utility.closeConnection(con);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("End Test insertSheetStatusV2");
    }

    public static void test3()
    {
        System.out.println("Start Test insertSheetStatusV3");
        try
        {
            int transMax = 1;

            for (int j = 0; j < 5; j++)

            {
                Connection con = Utility.getConnection();
                DBUtil.executeSQL("truncate table sFR_SHEET_STATUS", con);
                long stime = System.currentTimeMillis();
                for (int i = 0; i < transMax; i++)
                {
                    insertSheetStatusV3(con, i + "", "1", "48");
                }

                long etime = System.currentTimeMillis();

                System.out.println("Test of " + transMax + " took ="
                        + (etime - stime));
                transMax = transMax * 10;
                Utility.closeConnection(con);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("End Test insertSheetStatusV3");
    }

    public static void main(String args[])
    {
        //I tried to run all the tests at once but then i feared for the effect of one test on the other one
        //this why i rather running each test by it self
        
        //test1();
        //test2();
        test3();
    }

    static final String insertSql = "insert into SFR_SHEET_STATUS "
            + " (SHEET_STATUS_ID,SHEET_STATUS_TYPE_ID,SHEET_ID,SHEET_STATUS_TIMESTAMP,USER_ID) "
            + " values(SEQ_SFR_SHEET_STATUS_ID.nextval,?,?,SYSDATE, ?)";

    public static void insertSheetStatusV2(Connection con, String sheetId,
            String sheetStatusTypeId, String userId)
    {

        DBUtil.executePreparedStatment(insertSql, con, sheetStatusTypeId, sheetId, userId);
    }

    public static void insertSheetStatusV3(Connection con, String sheetId,
            String sheetStatusTypeId, String userId)
    {
        String insertSql = "insert into SFR_SHEET_STATUS "
                + " (SHEET_STATUS_ID,SHEET_STATUS_TYPE_ID,SHEET_ID,SHEET_STATUS_TIMESTAMP,USER_ID) "
                + " values(SEQ_SFR_SHEET_STATUS_ID.nextval,?,?,SYSDATE, ?)";
        DBUtil.executePreparedStatment(insertSql, con, sheetStatusTypeId, sheetId, userId);
    }

    public static void insertSheetStatusV1(Connection con, String sheetId,
            String sheetStatusTypeId, String userId)
    {
        String insertSql = "insert into SFR_SHEET_STATUS "
                + " (SHEET_STATUS_ID,SHEET_STATUS_TYPE_ID,SHEET_ID,SHEET_STATUS_TIMESTAMP,USER_ID) "
                + " values(SEQ_SFR_SHEET_STATUS_ID.nextval,"
                + sheetStatusTypeId + "," + sheetId + ",SYSDATE," + userId
                + ")";

        DBUtil.executeSQL(insertSql, con);
    }

}
