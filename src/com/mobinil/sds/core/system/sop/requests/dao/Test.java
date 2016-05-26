/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.sop.requests.dao;
import com.mobinil.sds.core.system.dcm.pos.model.POSDetailModel;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import com.mobinil.sds.core.system.request.model.*;
import com.mobinil.sds.core.system.scm.model.POSModel;
import com.mobinil.sds.core.system.scm.model.POSSimilar;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

/**
 *
 * @author sand
 */
public class Test {

    public static void main(String args [])
{
        try{
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            String strSql = "select update_request_status (1) from dual";
            ResultSet res = stat.executeQuery(strSql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

}

}
