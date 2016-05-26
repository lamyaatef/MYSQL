/**
 *
 */
package com.mobinil.mcss.lcs.model;

import com.mobinil.mcss.lcs.dao.LcsDao;
import com.mobinil.sds.core.utilities.Utility;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Shady Akl
 */
public class LCSContractData {

    private HashMap transactionTypes = null;
    private HashMap inventoryTypes = null;
    private HashMap dcmMapping = null;
    private Connection con = null;
    private Statement statSDS = null;

    public LCSContractData() {
    }

    public LCSContractData(Connection con) {
        this.con = con;
    }

    public LCSContractInfo getLCSData(String sim) throws Exception {
        LCSContractInfo lcsContract = new LCSContractInfo();


        LcsModel lcsModel = LcsDao.getLcsData(sim);



        lcsContract.transactionId = lcsModel.getLastTransactionId().toString();
        lcsContract.inventoryItemId = lcsModel.getInventoryItemId().toString();
        lcsContract.sourceTypeName = lcsModel.getFromLocation();
        lcsContract.currentStatus = lcsModel.getStatus();
        lcsContract.transactionTypeName = lcsModel.getTransacitonName();
        lcsContract.issueDate = lcsModel.getLastTransactionDate();



        statSDS = con.createStatement();

        lcsContract.mapTransactionType(transactionTypes);
        lcsContract.mapLCSDCM(statSDS, dcmMapping);
        lcsContract.mapProductID(statSDS, inventoryTypes);



        statSDS.close();
        // String returnValue ="'"+dcmId+"','"+productId+"','"+transactionTypeId+"',to_date('"+issueDate.toString()+"','yyyy-mm-dd')";
        return lcsContract;
    }

    public void loadData() {
        transactionTypes = Utility.getMap(con, "select transaction_type_name,transaction_type_id from cr_transaction_type");
        inventoryTypes = Utility.getMap(con, "select inventory_item_type,product_id from CR_PRODUCT_INVENTORY_CODES");
        dcmMapping = Utility.getMap(con, "SELECT LCS_NAME,DCM_NAME FROM CR_DCM_LCS_NAME");

        Utility.logger.info("Lookup Data Loaded Successfully");
    }
}
