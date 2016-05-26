/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.mcss.credit_advice.dao;

import com.mobinil.mcss.credit_advice.model.CreditDetails;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Shady Akl
 */
public class UpdateCreditAdviceDaoImpl implements UpdateCreditAdviceDao {

    Connection newConnection = null;

    @Override
    public void clearTempTableCRDetail() {

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();


            String sqlString = "DELETE FROM CRD_CREDIT_DETAILS_TMP";

            stat.executeUpdate(sqlString);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }
    }

    @Override
    public void clearTempTableCRDetailWithSerial() {

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            String sqlString = "DELETE FROM CRD_CREDIT_DETAILS_SERIAL_TMP";

            stat.executeUpdate(sqlString);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }
    }

    @Override
    public void updateRDetailWithId() {

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();



            StringBuilder str = new StringBuilder("SELECT * FROM CRD_CREDIT_DETAILS_TMP");


            ResultSet res = stat.executeQuery(str.toString());

            List<CreditDetails> creditAdviceList = new ArrayList<CreditDetails>();

            while (res.next()) {


                Long creditAdviceId = res.getLong("CREDIT_ADVICE_ID");

                String statusName = res.getString("STATUS_ID");

                Date dateOfPayment = res.getDate("DATE_OF_PAYMENT");

                CreditDetails creditDetails = new CreditDetails();

                creditDetails.setCaId(creditAdviceId);
                creditDetails.setStatusName(statusName);
                creditDetails.setCreditPaymentDate(dateOfPayment);
                creditAdviceList.add(creditDetails);
            }

            res.close();
            stat.close();

            Statement st = newConnection.createStatement();

            for (CreditDetails creditDetails : creditAdviceList) {


                String sqlString = "UPDATE CRD_CREDIT_DETAILS SET CRD_CREDIT_DETAILS.STATUS_ID=(SELECT STATUS_ID FROM CRD_STATUS_CREDIT WHERE STATUS_NAME='" + creditDetails.getStatusName()
                        + "') , DATE_OF_PAYMENT=to_date('" + creditDetails.getCreditPaymentDate() + "','yyyy/mm/dd') WHERE CRD_CREDIT_DETAILS.CREDIT_ADVICE_ID=" + creditDetails.getCaId();

                System.out.println("SQL:" + sqlString);
                st.executeUpdate(sqlString);
            }



            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }
    }

    @Override
    public void updateRDetailWithSerial() {

        try {
            newConnection = Utility.getConnection();
            Statement stat = newConnection.createStatement();

            List<CreditDetails> creditAdviceList = new ArrayList<CreditDetails>();


            StringBuilder str = new StringBuilder("SELECT * FROM CRD_CREDIT_DETAILS_SERIAL_TMP");


            ResultSet res = stat.executeQuery(str.toString());

            while (res.next()) {


                Long creditAdviceSerial = res.getLong("CREDIT_SERIAL");

                String statusName = res.getString("STATUS_ID");

                Date dateOfPayment = res.getDate("DATE_OF_PAYMENT");




                CreditDetails creditDetails = new CreditDetails();

                creditDetails.setCreditSerial(creditAdviceSerial.toString());
                creditDetails.setStatusName(statusName);
                creditDetails.setCreditPaymentDate(dateOfPayment);
                creditAdviceList.add(creditDetails);



            }

            res.close();
            stat.close();



            Statement st = newConnection.createStatement();

            for (CreditDetails creditDetails : creditAdviceList) {


                String sqlString = "UPDATE CRD_CREDIT_DETAILS SET CRD_CREDIT_DETAILS.STATUS_ID=(SELECT STATUS_ID FROM CRD_STATUS_CREDIT WHERE STATUS_NAME='" + creditDetails.getStatusName()
                        + "') , DATE_OF_PAYMENT=to_date('" + creditDetails.getCreditPaymentDate() + "','yyyy/mm/dd') WHERE CRD_CREDIT_DETAILS.CREDIT_SERIAL=" + creditDetails.getCreditSerial();

                System.out.print(sqlString);

                st.executeUpdate(sqlString);
            }



            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Utility.closeConnection(newConnection);
        }
    }
}
