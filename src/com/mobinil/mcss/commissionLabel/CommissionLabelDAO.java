package com.mobinil.mcss.commissionLabel;

import com.mobinil.sds.core.system.commission.dao.CommissionDAO;
import com.mobinil.sds.core.system.commission.model.ChannelModel;
import com.mobinil.sds.core.system.commission.model.CommissionModel;
import com.mobinil.sds.core.system.commission.model.LabelImportModel;
import com.mobinil.sds.core.system.commission.model.LabelModel;
import com.mobinil.sds.core.system.commission.model.RatedFileError;
import com.mobinil.sds.core.system.commission.model.RatedFileModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sand
 */
public class CommissionLabelDAO {

    public static Vector getAllLabels(Connection con) throws Exception {
        Vector labelVec = new Vector();
        LabelModel labelModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL";
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelModel = new LabelModel();
            labelModel.setLabelId(res.getString(labelModel.LABEL_ID));
            labelModel.setLabelName(res.getString(labelModel.LABEL_NAME));
            labelModel.setLabelDescription(res.getString(labelModel.LABEL_DESCRIPTION));
            labelVec.add(labelModel);
        }
        return labelVec;
    }

    public static LabelModel selectLabelData(Connection con, String labelId)
            throws Exception {
        LabelModel labelModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL where LABEL_ID = '"
                + labelId + "'";
        System.out.println("SQL:" + strSql);
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelModel = new LabelModel();
            labelModel.setLabelName(res.getString(labelModel.LABEL_NAME));
            labelModel.setLabelDescription(res.getString(labelModel.LABEL_DESCRIPTION));
            labelModel.setTwoValues(res.getInt(labelModel.TWOVALUES));
            labelModel.setThreeValues(res.getInt(labelModel.THREEVALUES));
        }
        return labelModel;
    }

    public static void updateLabelData(Connection con, String labelId,
            String labelName, String labelDescription, String category) throws Exception {
        Statement stat = con.createStatement();

        if (category.equals("2Values")) {
            String strSql = "update COMMISSION_LABEL" + " set LABEL_NAME = '"
                    + labelName + "' , LABEL_DESCRIPTION = '" + labelDescription
                    + "'" + ",TWO_VALUES=0,THREE_VALUES=1 where LABEL_ID = '" + labelId + "'";
            stat.executeUpdate(strSql);
        } else if (category.equals("3Values")) {
            String strSql = "update COMMISSION_LABEL" + " set LABEL_NAME = '"
                    + labelName + "' , LABEL_DESCRIPTION = '" + labelDescription
                    + "'" + ",TWO_VALUES=0,THREE_VALUES=1 where LABEL_ID = '" + labelId + "'";
            stat.executeUpdate(strSql);
        }

    }

    public static void insertNewLabel(Connection con, Long labelId,
            String labelName, String labelDescription, String category) throws Exception {

        //////////////////////////////Added by MYoussf in 15-10-2012


        int twoValues = 0;
        int threeValues = 0;

        if (category.equals("2Values")) {
            twoValues = 1;
            threeValues = 0;
        } else if (category.equals("3Values")) {
            twoValues = 0;
            threeValues = 1;
        }

        //////////////////////////////


        Statement stat = con.createStatement();
        String strSql = "insert into COMMISSION_LABEL"
                + "(LABEL_ID,LABEL_NAME,LABEL_DESCRIPTION,TWO_VALUES,THREE_VALUES) values " + "('"
                + labelId + "','" + labelName + "','" + labelDescription + "'," + twoValues + "," + threeValues + ")";
        Utility.logger.debug("The insert query is " + strSql);

        stat.executeUpdate(strSql);
    }

    public static void insertNewLabel(Connection con, Long labelId,
            String labelName, String labelDescription) throws Exception {

        //////////////////////////////Added by MYoussf in 15-10-2012

        int final_RadioCategory = 0;

        if (CommissionLabelInterfaceKey.INPUT_RADIO_RADIOCATEGORY1.equals(labelId)) {
            final_RadioCategory = 1;
        }

        //////////////////////////////


        Statement stat = con.createStatement();
        String strSql = "insert into COMMISSION_LABEL"
                + "(LABEL_ID,LABEL_NAME,LABEL_DESCRIPTION,CATEGORY) values " + "('"
                + labelId + "','" + labelName + "','" + labelDescription + "','" + final_RadioCategory + "')";
        stat.executeUpdate(strSql);
        Utility.logger.debug("The insert query is " + strSql);
    }

    public static Vector getAllLabelsDetails(Connection con, String labelId)
            throws Exception {
        Vector labelDetailsVec = new Vector();
        LabelModel labelDetailsModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL_DETAIL,commission_label where commission_label.label_id = commission_label_detail.label_id and commission_label.LABEL_ID = '"
                + labelId + "'";
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelDetailsModel = new LabelModel(res);
            labelDetailsModel.setThreeValues(res.getInt("THREE_VALUES"));
            labelDetailsModel.setTwoValues(res.getInt("TWO_VALUES"));
            labelDetailsVec.add(labelDetailsModel);

        }
        return labelDetailsVec;

    }
    ////////////////////////////////////// by mohamed youssef

    public static Vector getAllLabelsDetailsThree(Connection con, String labelId)
            throws Exception {
        Vector labelDetailsVec = new Vector();
        LabelModel labelDetailsModel = null;
        Statement stat = con.createStatement();
        String strSql = "select * from COMMISSION_LABEL_DETAIL_THREE,commission_label where commission_label.label_id=commission_label_detail_three.label_id and commission_label.LABEL_ID = '"
                + labelId + "'";

        System.out.println("SQL:" + strSql);
        ResultSet res = stat.executeQuery(strSql);
        while (res.next()) {
            labelDetailsModel = new LabelModel(res);
            labelDetailsModel.setCategory(res.getString(LabelModel.CATEGORY));
            labelDetailsModel.setThreeValues(res.getInt("THREE_VALUES"));
            labelDetailsModel.setTwoValues(res.getInt("TWO_VALUES"));

            System.out.println(labelDetailsModel.getDcmCode());

            labelDetailsVec.add(labelDetailsModel);
        }
        return labelDetailsVec;
    }
    /////////////////////////////////////

    public static void updateLabelDetails(Connection con, String labelId,
            LabelModel labelModel) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "update COMMISSION_LABEL_DETAIL" + " set DCM_CODE = '"
                + labelModel.getDcmCode() + "' , AMOUNT = '"
                + labelModel.getAmount() + "'" + " where LABEL_ID = '"
                + labelId + "' and LABEL_DETAIL_ID = '"
                + labelModel.getLabelDetailId() + "'";
        Utility.logger.debug("The update Query is " + strSql);
        stat.executeUpdate(strSql);
        stat.close();
    }

    public static void updateLabelDetailsThree(Connection con, String labelId,
            LabelModel labelModel) throws Exception {
        Statement stat = con.createStatement();
        String strSql = "update COMMISSION_LABEL_DETAIL_THREE" + " set DCM_CODE = '"
                + labelModel.getDcmCode() + "' , AMOUNT = '"
                + labelModel.getAmount() + "' , CATEGORY = '"
                + labelModel.getCategory() + "'" + " where LABEL_ID = '"
                + labelId + "' and LABEL_DETAIL_ID = '"
                + labelModel.getLabelDetailId() + "'";
        Utility.logger.debug("The update Query is " + strSql);
        stat.executeUpdate(strSql);
        stat.close();
    }

    public static void deleteAllLabelDetails(Connection con, String labelId) {
        String strSql = "DELETE FROM COMMISSION_LABEL_DETAIL WHERE LABEL_ID = ?";
        DBUtil.executePreparedStatment(strSql, con, labelId);
    }

    public static void deleteAllLabelDetailsThree(Connection con, String labelId) {
        String strSql = "DELETE FROM COMMISSION_LABEL_DETAIL_THREE WHERE LABEL_ID = ?";
        DBUtil.executePreparedStatment(strSql, con, labelId);
    }

    public static void deleteLableDcms(String labelId, Connection con) {
        String strSql = "DELETE FROM COMMISSION_LABEL_DETAIL WHERE DCM_CODE IN (SELECT DCM_CODE FROM TMP_DCM_CODE"
                + " WHERE COMMISSION_LABEL_DETAIL.DCM_CODE = TMP_DCM_CODE.DCM_CODE) AND LABEL_ID = ?";
        DBUtil.executePreparedStatment(strSql, con, labelId);
    }

    public static void deleteLableDcmsThree(String labelId, Connection con) {
        String strSql = "DELETE FROM COMMISSION_LABEL_DETAIL_THREE WHERE DCM_CODE IN (SELECT DCM_CODE FROM TMP_DCM_CODE"
                + " WHERE COMMISSION_LABEL_DETAIL_THREE.DCM_CODE = TMP_DCM_CODE.DCM_CODE) AND LABEL_ID = ?";
        DBUtil.executePreparedStatment(strSql, con, labelId);
    }

    public static void deleteTmpDcm(Connection con) {
        String strSql = "delete  from tmp_dcm_code";
        DBUtil.executeSQL(strSql, con);
    }

    public static void insertLabelDetails(Connection con, String labelId,
            LabelModel labelModel) throws Exception {
        Statement stat = con.createStatement();
        Long labelDetailsId = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_LABEL_DETAIL_ID");
        String strSql = "insert into COMMISSION_LABEL_DETAIL"
                + " (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT)" + " values ('"
                + labelDetailsId + "' , '" + labelId + "' , '"
                + labelModel.getDcmCode() + "' , '" + labelModel.getAmount()
                + "')";
        stat.executeUpdate(strSql);
        stat.close();
    }

    public static void insertLabelDetailsThree(Connection con, String labelId,
            LabelModel labelModel) throws Exception {
        Statement stat = con.createStatement();
        Long labelDetailsId = Utility.getSequenceNextVal(con, "SEQ_COMMISSION_LABEL_DETAIL_ID");
        String strSql = "insert into COMMISSION_LABEL_DETAIL_THREE"
                + " (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT,CATEGORY)" + " values ('"
                + labelDetailsId + "' , '" + labelId + "' , '"
                + labelModel.getDcmCode() + "' , '" + labelModel.getAmount() + "' , '" + labelModel.getCategory()
                + "')";
        stat.executeUpdate(strSql);
        stat.close();
    }

///////////////////////////////////////////////////////////////////////// by Mohamed Youssef
    public static void insertLabelDetailsThreeFromExcel(String labelId,
            String dcmCode, String amount, String category, Connection con) {

        String strSql = "insert into COMMISSION_LABEL_DETAIL_THREE (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT,CATEGORY) values (SEQ_COMMISSION_LABEL_DETAIL_ID.nextval , '"
                + labelId + "' , '" + dcmCode + "' , '" + amount + "' , '" + category + "')";
        DBUtil.executeSQL(strSql, con);
    }

////////////////////////////////////////////////////////////////////////    
    public static void insertLabelDetailsFromExcel(String labelId,
            String dcmCode, String amount, Connection con) {

        String strSql = "insert into COMMISSION_LABEL_DETAIL (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT) values (SEQ_COMMISSION_LABEL_DETAIL_ID.nextval , '"
                + labelId + "' , '" + dcmCode + "' , '" + amount + "')";
        DBUtil.executeSQL(strSql, con);
    }
//////////////////////////////////////////////////////////////////////////////

    public static void insertLabelDetailsFromExcel(String labelId,
            String dcmCode, String amount, Statement stat) {

        String strSql = "insert into COMMISSION_LABEL_DETAIL (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT) values (SEQ_COMMISSION_LABEL_DETAIL_ID.nextval , '"
                + labelId + "' , '" + dcmCode + "' , '" + amount + "')";

        try {

            stat.execute(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
///////////////////////////////////////////////////////////////////////////////By Mohamed Youssef

    public static void insertLabelDetailsThreeFromExcel(String labelId,
            String dcmCode, String amount, String category, Statement stat) {

        String strSql = "insert into COMMISSION_LABEL_DETAIL_THREE (LABEL_DETAIL_ID,LABEL_ID,DCM_CODE,AMOUNT,CATEGORY) values (SEQ_COMMISSION_LABEL_DETAIL_ID.nextval , '"
                + labelId + "' , '" + dcmCode + "' , '" + amount + "' , '" + category + "')";

        System.out.println("SQL insertLabelDetailsThreeFromExcel: " + strSql);

        try {


            stat.execute(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    public static Vector getTmpCommissionLabel(Connection con) {
        Vector labelVec = new Vector();
        try {

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from TMP_COMMISSION_LABEL ");
            while (res.next()) {
                labelVec.add(new LabelImportModel(res));
            }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelVec;
    }

    //////////////////////////////////////////////////////////////////////// By Mohamed youssef
    public static Vector getTmpCommissionLabelThree(Connection con) {
        Vector labelVec = new Vector();
        try {

            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from TMP_COMMISSION_LABEL_THREE");
            while (res.next()) {

                LabelImportModelThree labelImportModel = new LabelImportModelThree(res);
                labelVec.add(labelImportModel);
            }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelVec;
    }

    ///////////////////////////////////////////////////////////////// by Mohamed Youssef
    public static void deleteTmpCommissionLabelThree(Connection con) {
        String deleteSql = "delete from TMP_COMMISSION_LABEL_THREE";
        DBUtil.executeSQL(deleteSql, con);
    }
    ///////////////////////////////////////////////////////////////////

    public static void deleteTmpCommissionLabel(Connection con) {
        String deleteSql = "delete from TMP_COMMISSION_LABEL";
        DBUtil.executeSQL(deleteSql, con);
    }
}