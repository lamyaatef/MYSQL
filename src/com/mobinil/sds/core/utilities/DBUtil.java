package com.mobinil.sds.core.utilities;

import java.lang.reflect.Method;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import java.sql.*;

import com.mobinil.sds.core.system.Model;

public class DBUtil {

    private DBUtil() {
    }

    @SuppressWarnings("unchecked")
    private static Method getMethod(Class clazz, String methodName)
            throws SecurityException, NoSuchMethodException {
        Class[] paramterTypes = new Class[1];
        paramterTypes[0] = java.sql.ResultSet.class;
        Method mthd = clazz.getMethod(methodName, paramterTypes);
        return mthd;

    }

    /**
     * Execute select statement with opened connection
     *
     * @param sql query String
     * @param clazz model class
     * @param con DB Connection
     *
     * @return vector<clazz> contains result of sql parameter
     *
     */
    public static <T extends Model> Vector<T> executeSqlQueryMultiValue(
            String sql, Class<T> clazz, Connection con) {
        System.out.println("executeSqlQueryMultiValue");
        Vector<T> valueVector = new Vector<T>();
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                T tempModel = clazz.newInstance();
                tempModel.fillInstance(res);
                valueVector.add(tempModel);
                tempModel = null;
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
        }
        return valueVector;
    }

    
    
    public static <T extends Model> HashMap<String, T> executeSqlQueryMultiValueHM(
            String sql, String idNameField, Class<T> clazz, Connection con) {
        HashMap<String, T> valueHM = new HashMap<String, T>();
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                T tempModel = clazz.newInstance();
                tempModel.fillInstance(res);
                valueHM.put(res.getString(idNameField), tempModel);
                tempModel = null;
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
        }
        return valueHM;
    }

    public static <T extends Model> Vector<T> executeSqlQueryMultiValue(
            String sql, Class<T> clazz, String methodName, Connection con) {
        Vector<T> valueVector = new Vector<T>();
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            Method method = getMethod(clazz, methodName);
            while (res.next()) {
                T tempModel = clazz.newInstance();
                method.invoke(tempModel, res);
                valueVector.add(tempModel);
                tempModel = null;
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
        }
        return valueVector;
    }

    /**
     * Execute select statement with opened connection by preparedstatement
     *
     * @param sql query String
     * @param clazz model class
     * @param con DB Connection
     * @param params Object[] of query parameters
     *
     * @return vector<clazz> contains result of sql parameter
     *
     */
    public static <T extends Model> Vector<T> executePreparedSqlQueryMultiValue(
            String sql, Class<T> clazz, Connection con, Object... params) {
        Vector<T> valueVector = new Vector<T>();
        PreparedStatement prepStat = null;
        try {
            prepStat = getPreparedStatement(sql, con, params);
            
            ResultSet res = prepStat.executeQuery();
            while (res.next()) {
                T tempModel = clazz.newInstance();
                tempModel.fillInstance(res);
                valueVector.add(tempModel);
                tempModel = null;
            }
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(prepStat);
        }
        return valueVector;
    }

    /**
     * Execute select statement with opened connection
     *
     * @param sql query String
     * @param clazz model class
     * @param con DB Connection
     *
     * @return one clazz of query result
     *
     */
    public static <T extends Model> T executeSqlQuerySingleValue(String sql,
            Class<T> clazz, Connection con) {
        T value = null;
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                value = clazz.newInstance();
                value.fillInstance(res);
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
        }
        return value;
    }

    /**
     * Execute select statement with opened connection by preparedstatement
     *
     * @param sql query String
     * @param clazz model class
     * @param con DB Connection
     * @param params Object[] of query parameters
     *
     * @return one clazz of query result
     *
     */
    public static <T extends Model> T executePreparedSqlQuerySingleValue(String sql,
            Class<T> clazz, Connection con, Object... params) {
        T value = null;
        PreparedStatement pstat = null;
        try {
            pstat = getPreparedStatement(sql, con, params);
            ResultSet res = pstat.executeQuery();
            if (res.next()) {
                value = clazz.newInstance();
                value.fillInstance(res);
            }
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstat);
        }
        return value;
    }

    public static <T extends Model> T executeSqlQuerySingleValue(String sql,
            Class<T> clazz, String methodName, Connection con) {
        T value = null;
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            Method method = getMethod(clazz, methodName);
            if (res.next()) {
                value = clazz.newInstance();
                method.invoke(value, res);
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
        }
        return value;
    }

    /**
     * Execute update, insert and delete statement with opened connection
     *
     * @param sql query String
     * @param con DB Connection
     *
     *
     */
    public static void executeSQL(String sql, Connection con) {
        Statement stat = null;
        try {
            stat = con.createStatement();
            stat.executeUpdate(sql);
            System.out.println("sql:" + sql);
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
        }
    }

    /**
     * Execute update, insert and delete statement with opened connection
     *
     * @param sql query String
     * @param con DB Connection
     *
     *
     */
    public static int executeSQL(String sql, Statement st) {
        int rowCount = 0;
        try {

            rowCount = st.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        }
        return rowCount;
    }

    /**
     * Open connection and Execute update, insert and delete statement
     *
     * @param sql query String
     *
     *
     */
    public static void executeSQL(String sql) {
        Connection con = null;
        Statement stat = null;
        try {
            con = Utility.getConnection();
            stat = con.createStatement();
            stat.execute(sql);
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
            close(con);
        }
    }

    /**
     * Open connection and Execute select statement to check if field exists or
     * not
     *
     * @param sql query String
     *
     * @return true if exists and false if not exists
     *
     */
    public static boolean executeSQLExistCheck(String sql) {
        boolean checkFlag = false;
        Connection con = null;
        Statement stat = null;
        try {
            con = Utility.getConnection();
            stat = con.createStatement();

            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                checkFlag = true;
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
            close(con);
        }
        return checkFlag;
    }

    /**
     *
     * Execute select statement to check if field exists or not with opend
     * connection
     *
     * @param sql query String
     * @param con DB Connection
     * @return true if exists and false if not exists
     *
     */
    public static boolean executeSQLExistCheck(String sql, Connection con) {
        boolean checkFlag = false;
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                checkFlag = true;
            }
            System.out.println("sql executeSQLExistCheck:" + sql);
            res.close();
        } catch (Exception e) {
            System.out.println("exception sql executeSQLExistCheck:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);

        }
        return checkFlag;
    }

    /**
     * Open connection and Execute select statement to get only single String
     *
     * @param sql query String
     * @param fieldName choosen field in query
     * @param con DB Connection
     *
     * @return only one String of result or empty ( "" ) if there is no result
     *
     */
    public static String executeQuerySingleValueString(String sql,
            String fieldName, Connection con) {
        String value = "";
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                value = res.getString(fieldName);
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);

        }
        return value;
    }

    /**
     *
     * Execute select statement to get only single String with opend connection
     *
     * @param sql query String
     * @param fieldName choosen field in query
     *
     * @return only one String of result or empty ( "" ) if there is no result
     *
     */
    public static String executeQuerySingleValueString(String sql,
            String fieldName) {
        String value = "";
        Connection con = null;
        Statement stat = null;

        try {
            con = Utility.getConnection();
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                value = res.getString(fieldName);
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
            close(con);

        }
        return value;
    }

    public static String executeQuerySingleValueString(Connection con, String sql,
            String fieldName) {
        String value = "";
        Statement stat = null;

        try {

            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                value = res.getString(fieldName);
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);


        }
        return value;
    }

    /**
     *
     * Execute select statement to get Multi single String with opend connection
     *
     * @param sql query String
     * @param fieldName choosen field in query
     *
     * @return only one String of result or empty ( "" ) if there is no result
     *
     */
    public static Vector<String> executeQueryMultiValueString(String sql,
            String fieldName) {
        Vector<String> values = new Vector<String>();
        Connection con = null;
        Statement stat = null;

        try {
            con = Utility.getConnection();
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                values.add(res.getString(fieldName));
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);
            close(con);

        }
        return values;
    }

    /**
     *
     * Execute select statement to get only single int with opend connection
     *
     * @param sql query String
     * @param fieldName choosen field in query
     * @param con DB Connection
     *
     * @return only one int of result or -1 if there is no result
     *
     */
    public static int executeQuerySingleValueInt(String sql, String fieldName,
            Connection con) {
        int value = -1;
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            if (res.next()) {
                value = res.getInt(fieldName);
            }
            res.close();
        } catch (Exception e) {
            System.out.println("sql:" + sql);
            e.printStackTrace();
        } finally {
            close(stat);

        }
        return value;
    }

    /**
     *
     * Execute select statement to get hashmap of field 1 and field 2
     *
     * @param con DB Connection
     * @param strQuery query String
     *
     * @return HashMap<String, String> key is field 1 and value is field 2
     *
     */
    public static HashMap<String, String> getMap(Connection con, String strQuery) {
        HashMap<String, String> returnHashMap = new HashMap<String, String>();
        Statement stat = null;
        try {
            stat = con.createStatement();
            ResultSet res = stat.executeQuery(strQuery);
            while (res.next()) {
                String strKey = res.getString(1);
                String strValue = res.getString(2);
                returnHashMap.put(strKey, strValue);
            }
            res.close();

        } catch (Exception e) {
            System.out.println(strQuery);
            e.printStackTrace();
        } finally {
            close(stat);
        }
        return returnHashMap;
    }

    /**
     *
     * Connection closing
     *
     * @param con DB Connection
     *
     */
    public static void close(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            if (!conn.isClosed()) {
                Utility.closeConnection(conn);
            }
        } catch (SQLException e) {
        }
    }

    /**
     *
     * Statement closing
     *
     * @param stmt
     *
     */
    public static void close(Statement stmt) {
        if (stmt == null) {
            return;
        }
        try {
            stmt.close();
        } catch (SQLException e) {
        }
    }

    /**
     *
     * ResultSet closing
     *
     * @param res
     *
     */
    public static void close(ResultSet res) {
        if (res == null) {
            return;
        }
        try {
            res.close();
        } catch (SQLException e) {
        }
    }

    /**
     *
     * PreparedStatement closing
     *
     * @param pStmt
     *
     */
    public static void close(PreparedStatement pStmt) {
        if (pStmt == null) {
            return;
        }
        try {
            pStmt.close();
        } catch (SQLException e) {
        }
    }

    /**
     *
     * CallableStatement closing
     *
     * @param pStmt
     *
     */
    public static void close(CallableStatement pStmt) {
        if (pStmt == null) {
            return;
        }
        try {
            pStmt.close();
        } catch (SQLException e) {
        }
    }

    /**
     *
     * Get next value of sequence with opened connection
     *
     * @param argConnection DB Connection
     * @param argSequenceName sequence name
     *
     * @return Long of next value.
     *
     */
    public static Long getSequenceNextVal(Connection argConnection,
            String argSequenceName) {
        Long lNextVal = new Long(0);
        Statement newStatement = null;
        ResultSet newResultSet = null;
        String sql = "select " + argSequenceName + ".nextval from dual";
        try {
            newStatement = argConnection.createStatement();
            newResultSet = newStatement.executeQuery(sql);
            if (newResultSet.next()) {
                lNextVal = new Long((long) newResultSet.getInt(1));
            }
        } catch (Exception objExp) {
            System.out.println(sql);
            objExp.printStackTrace();
        } finally {
            close(newResultSet);
            close(newStatement);
        }
        return lNextVal;
    }// end of getSequenceNextVal

    /**
     *
     * Get next value of sequence with created statement
     *
     * @param stat connection statement
     * @param argSequenceName sequence name
     *
     * @return Long of next value.
     *
     */
    public static Long getSequenceNextVal(Statement stat, String argSequenceName)
            throws SQLException {
        Long lNextVal = new Long(0);
        // Statement newStatement=null;
        ResultSet newResultSet = null;
        String sql = "select " + argSequenceName + ".nextval from dual";
        try {
            newResultSet = stat.executeQuery(sql);
            if (newResultSet.next()) {
                lNextVal = new Long((long) newResultSet.getInt(1));
            }

        } catch (Exception objExp) {
            System.out.println(sql);
            objExp.printStackTrace();
        } finally {
            close(newResultSet);

        }
        return lNextVal;
    }

    /**
     *
     * Executes the SQL statement in this PreparedStatement object, which may be
     * any kind of SQL statement.
     *
     * @param sqlText query string
     * @param stat connection statement
     * @param params Object[] of parameters
     *
     *
     */
    public static void executePreparedStatment(String sqlText, Connection con,
            Object... params) {
        PreparedStatement pStmt = null;
        try {
            pStmt = getPreparedStatement(sqlText, con, params);
            
            pStmt.execute();

        } catch (Exception e) {
            System.out.println("error in the " +sqlText);
            printArray(params);
            e.printStackTrace();
        } finally {
            close(pStmt);
        }
    }

    /**
     *
     * print array of parameters
     *
     * @param params Object[] of parameters
     *
     *
     */
    private static void printArray(Object... params) {
        System.out.println("***********************");
        for (int i = 0; i < params.length; i++) {
            System.out.println("Param " + (i + 1) + " = " + params[i]);
        }
        System.out.println("***********************");
    }

    /**
     *
     * Set paramters in query string
     *
     * @param sqlText query String
     * @param con DB Connection
     * @param params Object[] of query parameters
     *
     * @return PreparedStatement
     *
     */
    public static PreparedStatement getPreparedStatement(String sqlText,
            Connection con, Object... params) throws SQLException {
        if (sqlText == null) {
            throw new IllegalArgumentException("Null sqlText not allowed.");
        }
        if (sqlText.equals("")) {
            throw new IllegalArgumentException("Blank sqlText not allowed.");
        }
        PreparedStatement pStmt = null;
        pStmt = con.prepareStatement(sqlText);

        for (int i = 0; i < params.length; i++) {
            pStmt.setObject(i + 1, params[i]);
        }
        return pStmt;
    }

    /**
     * this function is used to check if the column you passed his name is
     * already exist in the ResultSet
     *
     * @param columnNameToCheck
     * @param rs ResultSet
     * @return true or false
     *
     */
    public static boolean checkColumnNameToFillInModelInstance(String columnNameToCheck, ResultSet rs) {
        try {

            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            for (int i = 1; i < numColumns + 1; i++) {
                String columnName = rsmd.getColumnName(i);
                if (columnNameToCheck.equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
