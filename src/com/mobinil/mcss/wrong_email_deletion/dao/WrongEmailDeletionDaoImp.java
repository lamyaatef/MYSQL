/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.wrong_email_deletion.dao;

import com.mobinil.mcss.wrong_email_deletion.model.WrongEmailDeletionModel;
import java.sql.Connection;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import org.apache.jasper.tagplugins.jstl.core.ForEach;

/**
 *
 * @author SAND
 */
public class WrongEmailDeletionDaoImp implements WrongEmailDeletionDao {

    Connection newConnection = null;

    @Override
    public List getEmails(String posCode, String date, String dateTo) {

        List<WrongEmailDeletionModel> wrongEmailDeletionList = new ArrayList<WrongEmailDeletionModel>();

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            StringBuilder str = new StringBuilder("select NEW_XYZ_BULK_EMAIL.POSCODE , new_xyz_bulk_email.ID , NEW_XYZ_BULK_EMAIL.CREATIONDATE , NEW_XYZ_BULK_EMAIL.SENDEREMAILADDRESS , NEW_XYZ_BULK_EMAIL.SENDERNAME , NEW_XYZ_BULK_EMAIL.SUBJECT , new_xyz_bulk_email.status_id , NEW_XYZ_BULK_STATUS.NAME from NEW_XYZ_BULK_EMAIL , NEW_XYZ_BULK_STATUS where NEW_XYZ_BULK_EMAIL.STATUS_ID = NEW_XYZ_BULK_STATUS.ID");

            if (posCode != null && !posCode.trim().equals("")) {
                str.append("  AND NEW_XYZ_BULK_EMAIL.POSCODE ='").append(posCode).append("'");
            }
            if (date != null && dateTo != null) {
                str.append(" AND NEW_XYZ_BULK_EMAIL.CREATIONDATE BETWEEN to_date ('").append(date).append("' , 'DD MONTH YY') AND to_date ('").append(dateTo).append("' ,'DD MONTH YY')");
            }

            System.out.println("SQL:" + str);
            ResultSet res = stat.executeQuery(str.toString());
            while (res.next()) {
                WrongEmailDeletionModel wrongEmail = new WrongEmailDeletionModel();

                wrongEmail.setPosCode(res.getString("POSCODE"));
                wrongEmail.setCreationDate(res.getDate("CREATIONDATE"));
                wrongEmail.setSenderEmailAddress(res.getString("SENDEREMAILADDRESS"));
                wrongEmail.setSenderName(res.getString("SENDERNAME"));
                wrongEmail.setSubject(res.getString("SUBJECT"));
                wrongEmail.setStatusName(res.getString("NAME"));
                wrongEmail.setId(res.getInt("id"));
                wrongEmail.setStatusId(res.getInt("status_id"));

                wrongEmailDeletionList.add(wrongEmail);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }

        return wrongEmailDeletionList;

    }

    public void archiveAndDelete(List<WrongEmailDeletionModel> adEmailList, String reason) {

        String sqlString = "";
        String sqlString1 = "";
        String sqlString2 = "";


        try {
            newConnection = Utility.getConnection();
            Statement stmt = newConnection.createStatement();

            for (WrongEmailDeletionModel wrongEmail : adEmailList) {

                int id = wrongEmail.getId();
                String posCode = wrongEmail.getPosCode();
                Date creationDate = wrongEmail.getCreationDate();
                String senderEmailAddress = wrongEmail.getSenderEmailAddress();
                String senderName = wrongEmail.getSenderName();
                String subject = wrongEmail.getSubject();
                String statusName = wrongEmail.getStatusName();

                sqlString = "insert into WRONG_EMAIL_DELETION_ARCHIVE (EmailId,POSCODE,CREATIONDATE,SENDEREMAILADDRESS,SENDERNAME,SUBJECT,STATUS,REASON) values (" + id + ",'" + posCode + "',to_date ('" + creationDate + "' , 'yyyy/mm/dd'),'" + senderEmailAddress + "','" + senderName + "','" + subject + "','" + statusName + "','" + reason + "' )";

                System.out.println("SQLlllllllllllllllllllllllllllllllllllll:" + sqlString);

                stmt.executeUpdate(sqlString);

                sqlString1 = "DELETE FROM NEW_XYZ_BULK_EMAIL where ID=" + id;
                stmt.executeUpdate(sqlString1);
                System.out.println("SQLllllllllllllllllllllllllllllllllllllllll1:" + sqlString1);

                sqlString2 = "DELETE FROM NEW_XYZ_BULK_RECORD where MAIL_ID=" + id;
                stmt.executeUpdate(sqlString2);
                System.out.println("SQLlllllllllllllllllllllllllllllllllllllll2:" + sqlString2);

            }

            stmt.close();


        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }
}
