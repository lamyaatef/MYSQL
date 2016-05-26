/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.lcs.dao;

import com.mobinil.mcss.lcs.model.LcsModel;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Shady Akl
 */
public class LcsDao {

    public static LcsModel getLcsData(String simSerial) {
        Connection newConnection = null;
        Statement stat = null;
        ResultSet res = null;
        LcsModel lcsModel = new LcsModel();
        try {
            newConnection = Utility.getConnection();
            stat = newConnection.createStatement();
            String str = "SELECT * FROM LCS_SIM_TRANSACTIONS,LCS_SIM WHERE LCS_SIM_TRANSACTIONS.TRANSACTION_ID=LCS_SIM.LAST_TRANSACTION_ID AND LCS_SIM_TRANSACTIONS.SIM_SERIAL='" + simSerial + "'";
            System.out.println("SQL:" + str);
            res = stat.executeQuery(str.toString());
            while (res.next()) {
                lcsModel = new LcsModel(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return lcsModel;
    }
}
