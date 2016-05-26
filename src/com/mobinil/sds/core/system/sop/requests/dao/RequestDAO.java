package com.mobinil.sds.core.system.sop.requests.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sop.*;
import com.mobinil.sds.core.system.sop.schemas.model.*;
import com.mobinil.sds.core.system.sop.requests.model.*;

import com.mobinil.sds.core.system.gn.user.dao.UserAccountDAO;
import com.mobinil.sds.core.system.sa.users.dto.*;
import com.mobinil.sds.core.system.sa.users.model.*;
import com.mobinil.sds.core.system.sa.persons.model.*;

public class RequestDAO {

    private RequestDAO() {
    }

    public static HashMap getAllDCMsProducts(Connection con, String channelId, String reportId, String warehouseId) {
        HashMap products = new HashMap();
        try {
            Statement stat = con.createStatement();
            String strSql = "SELECT distinct sop_product_report.product_name as PRODUCT_NAME_ENGLISH "
                    + "FROM "
                    + "VW_GEN_DCM,SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL,sop_product_report "
                    + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                    + "and sop_product_report.product_name = sop_schema_product.PRODUCT_NAME_ENGLISH " + appendChannelCondition("and sop_product_report.channel_id", channelId)
                    + "and sop_product_report.report_id = '" + reportId + "' "
                    + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID  "
                    + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID  "
                    + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                    + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
            if (warehouseId.compareTo("") != 0) {
                strSql += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
            }
            strSql += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition("AND VW_GEN_DCM.CHANNEL_ID", channelId);
            System.out.println("The product query is " + strSql);
            String productValue = "0";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String productName = res.getString("PRODUCT_NAME_ENGLISH");
                products.put(productName, productValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static HashMap getAllDCMs(Connection con, String channelId, String reportId, String warehouseId) {
        HashMap DCMs = new HashMap();
        try {
            Statement stat = con.createStatement();
            String strSql = "SELECT distinct VW_GEN_DCM.DCM_NAME "
                    + "FROM "
                    + "VW_GEN_DCM,SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                    + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                    + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID  "
                    + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID  "
                    + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                    + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID "
                    + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition("AND VW_GEN_DCM.CHANNEL_ID", channelId);
            if (warehouseId.compareTo("") != 0) {
                strSql += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
            }
            strSql += "order by VW_GEN_DCM.DCM_NAME asc";
            //System.out.println("The DCMs query is " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String dcmName = res.getString("DCM_NAME");
                DCMs.put(dcmName, (HashMap) getAllDCMsProducts(con, channelId, reportId, warehouseId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DCMs;
    }

    public static HashMap getDCMProductByValue(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String reportId, String warehouseId) {
        HashMap DCMWithValue = new HashMap();
        HashMap productWithValue = new HashMap();
        try {
            Statement stat = con.createStatement();
            String strSql = "SELECT VW_GEN_DCM.DCM_NAME,sop_product_report.product_name as PRODUCT_NAME_ENGLISH  , "
                    + "SUM(SOP_REQUEST_DETAIL.PRODUCT_AMOUNT)as "
                    + "total_amount "
                    + "FROM "
                    + "VW_GEN_DCM,SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL,sop_product_report "
                    + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                    + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID  "
                    + "and sop_product_report.product_name = sop_schema_product.PRODUCT_NAME_ENGLISH " + appendChannelCondition("and sop_product_report.channel_id", channelId)
                    + "and sop_product_report.report_id = '" + reportId + "'"
                    + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID  "
                    + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                    + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID "
                    + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition("AND VW_GEN_DCM.CHANNEL_ID", channelId);
            if (creationDateFrom.compareTo("*") != 0) {
                strSql += "AND SOP_REQUEST.CREATION_DATE >= TO_DATE('" + creationDateFrom + "','mm/dd/yyyy') ";
            }
            if (creationDateTo.compareTo("*") != 0) {
                strSql += "AND SOP_REQUEST.CREATION_DATE <= TO_DATE('" + creationDateTo + "','mm/dd/yyyy') ";
            }
            if (paymentDateFrom.compareTo("*") != 0) {
                strSql += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE >= TO_DATE('" + paymentDateFrom + "','mm/dd/yyyy') ";
            }
            if (paymentDateTo.compareTo("*") != 0) {
                strSql += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE <= TO_DATE('" + paymentDateTo + "','mm/dd/yyyy') ";
            }
            if (warehouseId.compareTo("") != 0) {
                strSql += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
            }

            strSql += " GROUP BY VW_GEN_DCM.DCM_NAME,sop_product_report.product_name";
            System.out.println("The DCMs product with value query is " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            productWithValue = new HashMap();
            while (res.next()) {
                String dcmName = res.getString("DCM_Name");
                String productName = res.getString("PRODUCT_NAME_ENGLISH");
                String productValue = res.getString("total_amount");
                Set set = DCMWithValue.entrySet();
                Iterator i = set.iterator();
                boolean found = false;
                while (i.hasNext()) {
                    Map.Entry me = (Map.Entry) i.next();
                    if (me.getKey().equals(dcmName)) {
                        productWithValue = (HashMap) DCMWithValue.get(dcmName);
                        productWithValue.put(productName, productValue);
                        found = true;
                    }
                }
                if (!found) {
                    productWithValue = new HashMap();
                    productWithValue.put(productName, productValue);
                    DCMWithValue.put(dcmName, productWithValue);

                }

            }

            /*
             * HashMap scratchReport =
             * RequestDAO.getScratchReport(con,"1","*","*","*","*"); Set set =
             * scratchReport.entrySet(); Iterator i = set.iterator();
             * while(i.hasNext()) { Map.Entry me = (Map.Entry)i.next(); String
             * dcmName=(String)me.getKey(); HashMap allProducts
             * =(HashMap)me.getValue(); Set set2 = allProducts.entrySet();
             * Iterator i2 = set2.iterator(); while(i2.hasNext()) { Map.Entry
             * me2 = (Map.Entry)i2.next(); String productName =
             * (String)me2.getKey(); String productValue =
             * (String)me2.getValue();
             *
             * Set set3 = DCMWithValue.entrySet(); Iterator i3 =
             * set3.iterator(); while(i3.hasNext()) { Map.Entry me3 =
             * (Map.Entry)i3.next(); HashMap trueProducts
             * =(HashMap)me3.getValue(); String truedcmName =
             * (String)me3.getKey(); if(dcmName.compareTo(truedcmName)==0) {
             * trueProducts.put(productName,productValue);
             * DCMWithValue.put(truedcmName,trueProducts); } } } }
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DCMWithValue;
    }

    public static HashMap checkDCMProduct(Connection con, String channelId, String creationDateFrom, String creationDateto, String paymentDateFrom, String paymentDateTo, String reportId, String warehouseId) {
        HashMap DCMs = (HashMap) getAllDCMs(con, channelId, reportId, warehouseId);
        HashMap DCMTrue = (HashMap) getDCMProductByValue(con, channelId, creationDateFrom, creationDateto, paymentDateFrom, paymentDateTo, reportId, warehouseId);

        //System.out.println("The size of DCMTrue issssssssssssssss " + DCMTrue.size());
        Set set = DCMTrue.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            String truedcmName = (String) me.getKey();
            //System.out.println("The DCM Name is " + truedcmName);
            HashMap trueProducts = (HashMap) me.getValue();

            Set set2 = trueProducts.entrySet();
            Iterator i2 = set2.iterator();
            while (i2.hasNext()) {
                Map.Entry me2 = (Map.Entry) i2.next();
                String trueproductName = (String) me2.getKey();
                String trueproductValue = (String) me2.getValue();

                Set set3 = DCMs.entrySet();
                Iterator i3 = set3.iterator();
                while (i3.hasNext()) {
                    Map.Entry me3 = (Map.Entry) i3.next();
                    HashMap allProducts = (HashMap) me3.getValue();
                    String dcmName = (String) me3.getKey();

                    if (dcmName.compareTo(truedcmName) == 0) {
                        Set set4 = allProducts.entrySet();
                        Iterator i4 = set4.iterator();
                        while (i4.hasNext()) {
                            Map.Entry me4 = (Map.Entry) i4.next();
                            String productName = (String) me4.getKey();
                            String productValue = (String) me2.getValue();

                            if (productName.compareTo(trueproductName) == 0) {
                                allProducts.put(productName, trueproductValue);
                            }

                        }
                        DCMs.put(dcmName, allProducts);
                    }
                }
            }
        }
        return DCMs;
    }

    public static HashMap scratchDCMProductAfterUpdate(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String reportId, String warehouseId) {
        HashMap DCMs = (HashMap) checkDCMProduct(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
        HashMap DCMTrue = (HashMap) getScratchReport(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, warehouseId);

        //System.out.println("The size of DCMTrue issssssssssssssss " + DCMTrue.size());
        Set set = DCMTrue.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            String truedcmName = (String) me.getKey();
            //System.out.println("The DCM Name is " + truedcmName);
            HashMap trueProducts = (HashMap) me.getValue();

            Set set2 = trueProducts.entrySet();
            Iterator i2 = set2.iterator();
            while (i2.hasNext()) {
                Map.Entry me2 = (Map.Entry) i2.next();
                String trueproductName = (String) me2.getKey();
                String trueproductValue = (String) me2.getValue();

                Set set3 = DCMs.entrySet();
                Iterator i3 = set3.iterator();
                while (i3.hasNext()) {
                    Map.Entry me3 = (Map.Entry) i3.next();
                    HashMap allProducts = (HashMap) me3.getValue();
                    String dcmName = (String) me3.getKey();

                    if (dcmName.compareTo(truedcmName) == 0) {
                        Set set4 = allProducts.entrySet();
                        Iterator i4 = set4.iterator();
                        boolean found = false;
                        while (i4.hasNext()) {
                            Map.Entry me4 = (Map.Entry) i4.next();
                            String productName = (String) me4.getKey();
                            String productValue = (String) me2.getValue();

                            if (productName.compareTo(trueproductName) == 0) {
                                allProducts.put(productName, trueproductValue);
                                found = true;
                            }

                        }
                        if (!found) {

                            allProducts.put(trueproductName, trueproductValue);
                        }
                        DCMs.put(dcmName, allProducts);
                    }
                }
            }
        }
        return DCMs;
    }

    public static HashMap getScratchReport(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String warehouseId) {
        HashMap scratchReport = new HashMap();
        HashMap queries = new HashMap();
        boolean resQueryCheck = true;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_PRODUCT_QUERY_SCRATCH";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String productQuery = res.getString("PRODUCT_QUERY");
                String productName = res.getString("PRODUCT_NAME");
                String sqlQuery = "select * from(( " + productQuery + " FROM VW_GEN_DCM,"
                        + "SOP_SCHEMA_PRODUCT,"
                        + "SOP_REQUEST,"
                        + "SOP_REQUEST_STATUS_LOG,"
                        + "SOP_REQUEST_DETAIL"
                        + " WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4"
                        + " AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID"
                        + " AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID"
                        + " AND SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID ="
                        + " SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID"
                        + " AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID"
                        + " AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID" + appendChannelCondition(" AND VW_GEN_DCM.CHANNEL_ID", channelId);



                if (creationDateFrom.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST.CREATION_DATE >= TO_DATE('" + creationDateFrom + "','mm/dd/yyyy') ";
                }
                if (creationDateTo.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST.CREATION_DATE <= TO_DATE('" + creationDateTo + "','mm/dd/yyyy') ";
                }
                if (paymentDateFrom.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE >= TO_DATE('" + paymentDateFrom + "','mm/dd/yyyy') ";
                }
                if (paymentDateTo.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE <= TO_DATE('" + paymentDateTo + "','mm/dd/yyyy') ";
                }
                if (warehouseId.compareTo("") != 0) {
                    sqlQuery += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                }

                sqlQuery += " GROUP BY VW_GEN_DCM.DCM_NAME";
                sqlQuery += ")) A,( SELECT distinct VW_GEN_DCM.DCM_NAME as dcm_name_2 from vw_gen_dcm , SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                        + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                        + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID "
                        + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID "
                        + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                        + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
                if (warehouseId.compareTo("") != 0) {
                    sqlQuery += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                }
                sqlQuery += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition(" AND VW_GEN_DCM.CHANNEL_ID", channelId) + ") B "
                        + "where B.dcm_name_2  = A.dcm_name(+)";

                System.out.println("The Sql Qyery isssssssssssssssssss " + sqlQuery);
                Statement stat2 = con.createStatement();

                ResultSet resQuery = stat2.executeQuery(sqlQuery);

                scratchReport = new HashMap();
                if (resQuery != null) {
                    while (resQuery.next()) {
                        resQueryCheck = false;
                        String dcmName = resQuery.getString("dcm_name_2");
                        String productValue = resQuery.getString(2);
                        if (productValue == null) {
                            productValue = "0";
                        }
                        Set set = queries.entrySet();
                        Iterator i = set.iterator();
                        boolean found = false;
                        while (i.hasNext()) {
                            Map.Entry me = (Map.Entry) i.next();
                            //System.out.println(me.getKey() + " : " + me.getValue() );
                            if (me.getKey().equals(dcmName)) {
                                HashMap shm = (HashMap) queries.get(dcmName);
                                shm.put(productName, productValue);
                                found = true;
                            }
                        }
                        if (!found) {
                            HashMap shm = new HashMap();
                            shm.put(productName, productValue);
                            queries.put(dcmName, shm);

                        }
                    }
                    if (resQueryCheck) {
                        String strSql3 = "SELECT distinct VW_GEN_DCM.DCM_NAME "
                                + "FROM "
                                + "VW_GEN_DCM,SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                                + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                                + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID  "
                                + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID  "
                                + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                                + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
                        if (warehouseId.compareTo("") != 0) {
                            strSql3 += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                        }
                        strSql3 += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition(" AND VW_GEN_DCM.CHANNEL_ID", channelId)
                                + "order by VW_GEN_DCM.DCM_NAME asc";
                        Statement stat3 = con.createStatement();

                        ResultSet resQuery3 = stat3.executeQuery(strSql3);
                        while (resQuery3.next()) {
                            String dcmName = resQuery3.getString("DCM_Name");
                            String productValue = "0";
                            Set set = queries.entrySet();
                            Iterator i = set.iterator();
                            boolean found = false;
                            while (i.hasNext()) {
                                Map.Entry me = (Map.Entry) i.next();
                                //System.out.println(me.getKey() + " : " + me.getValue() );
                                if (me.getKey().equals(dcmName)) {
                                    HashMap shm = (HashMap) queries.get(dcmName);
                                    shm.put(productName, productValue);
                                    found = true;
                                }
                            }
                            if (!found) {
                                HashMap shm = new HashMap();
                                shm.put(productName, productValue);
                                queries.put(dcmName, shm);

                            }
                        }
                    }

                }
                System.out.println("The flagggggggggggggggg isssssss" + resQueryCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queries;
    }

    public static HashMap getProductReport(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String warehouseId) {
        HashMap scratchReport = new HashMap();
        HashMap queries = new HashMap();
        boolean resQueryCheck = true;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_PRODUCT_QUERY_PRODUCT";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String productQuery = res.getString("PRODUCT_QUERY");
                String productName = res.getString("PRODUCT_NAME");
                String sqlQuery = "select * from(( " + productQuery + " FROM VW_GEN_DCM,"
                        + "SOP_SCHEMA_PRODUCT,"
                        + "SOP_REQUEST,"
                        + "SOP_REQUEST_STATUS_LOG,"
                        + "SOP_REQUEST_DETAIL"
                        + " WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4"
                        + " AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID"
                        + " AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID"
                        + " AND SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID ="
                        + " SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID"
                        + " AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID"
                        + " AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID" + appendChannelCondition(" AND VW_GEN_DCM.CHANNEL_ID", channelId);



                if (creationDateFrom.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST.CREATION_DATE >= TO_DATE('" + creationDateFrom + "','mm/dd/yyyy') ";
                }
                if (creationDateTo.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST.CREATION_DATE <= TO_DATE('" + creationDateTo + "','mm/dd/yyyy') ";
                }
                if (paymentDateFrom.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE >= TO_DATE('" + paymentDateFrom + "','mm/dd/yyyy') ";
                }
                if (paymentDateTo.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE <= TO_DATE('" + paymentDateTo + "','mm/dd/yyyy') ";
                }
                if (warehouseId.compareTo("") != 0) {
                    sqlQuery += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                }

                sqlQuery += " GROUP BY VW_GEN_DCM.DCM_NAME";
                sqlQuery += ")) A,( SELECT distinct VW_GEN_DCM.DCM_NAME as dcm_name_2 from vw_gen_dcm , SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                        + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                        + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID "
                        + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID "
                        + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                        + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
                if (warehouseId.compareTo("") != 0) {
                    sqlQuery += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                }
                sqlQuery += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition(" AND VW_GEN_DCM.CHANNEL_ID", channelId) + ") B "
                        + "where B.dcm_name_2  = A.dcm_name(+)";

                //System.out.println("The Sql Qyery isssssssssssssssssss " + sqlQuery);
                Statement stat2 = con.createStatement();
                ResultSet resQuery = stat2.executeQuery(sqlQuery);
                scratchReport = new HashMap();
                if (resQuery != null) {
                    while (resQuery.next()) {
                        resQueryCheck = false;
                        String dcmName = resQuery.getString("dcm_name_2");
                        String productValue = resQuery.getString(productName);
                        if (productValue == null) {
                            productValue = "0";
                        }
                        Set set = queries.entrySet();
                        Iterator i = set.iterator();
                        boolean found = false;
                        while (i.hasNext()) {
                            Map.Entry me = (Map.Entry) i.next();
                            //System.out.println(me.getKey() + " : " + me.getValue() );
                            if (me.getKey().equals(dcmName)) {
                                HashMap shm = (HashMap) queries.get(dcmName);
                                shm.put(productName, productValue);
                                found = true;
                            }
                        }
                        if (!found) {
                            HashMap shm = new HashMap();
                            shm.put(productName, productValue);
                            queries.put(dcmName, shm);

                        }

                    }
                    if (resQueryCheck) {

                        String strSql3 = "SELECT distinct VW_GEN_DCM.DCM_NAME "
                                + "FROM "
                                + "VW_GEN_DCM,SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                                + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                                + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID  "
                                + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID  "
                                + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                                + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
                        if (warehouseId.compareTo("") != 0) {
                            strSql3 += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                        }
                        strSql3 += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition(" AND VW_GEN_DCM.CHANNEL_ID", channelId)
                                + "order by VW_GEN_DCM.DCM_NAME asc";
                        Statement stat3 = con.createStatement();

                        ResultSet resQuery3 = stat3.executeQuery(strSql3);
                        while (resQuery3.next()) {
                            String dcmName = resQuery3.getString("DCM_Name");
                            String productValue = "0";
                            Set set = queries.entrySet();
                            Iterator i = set.iterator();
                            boolean found = false;
                            while (i.hasNext()) {
                                Map.Entry me = (Map.Entry) i.next();
                                //System.out.println(me.getKey() + " : " + me.getValue() );
                                if (me.getKey().equals(dcmName)) {
                                    HashMap shm = (HashMap) queries.get(dcmName);
                                    shm.put(productName, productValue);
                                    found = true;
                                }
                            }
                            if (!found) {
                                HashMap shm = new HashMap();
                                shm.put(productName, productValue);
                                queries.put(dcmName, shm);

                            }
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return queries;
    }

    public static HashMap productDCMProductAfterUpdate(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String reportId, String warehouseId) {
        HashMap DCMs = (HashMap) checkDCMProduct(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
        HashMap DCMTrue = (HashMap) getProductReport(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, warehouseId);

        //System.out.println("The size of DCMTrue issssssssssssssss " + DCMTrue.size());
        Set set = DCMTrue.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            String truedcmName = (String) me.getKey();
            //System.out.println("The DCM Name is " + truedcmName);
            HashMap trueProducts = (HashMap) me.getValue();

            Set set2 = trueProducts.entrySet();
            Iterator i2 = set2.iterator();
            while (i2.hasNext()) {
                Map.Entry me2 = (Map.Entry) i2.next();
                String trueproductName = (String) me2.getKey();
                String trueproductValue = (String) me2.getValue();

                Set set3 = DCMs.entrySet();
                Iterator i3 = set3.iterator();
                while (i3.hasNext()) {
                    Map.Entry me3 = (Map.Entry) i3.next();
                    HashMap allProducts = (HashMap) me3.getValue();
                    String dcmName = (String) me3.getKey();

                    if (dcmName.compareTo(truedcmName) == 0) {
                        Set set4 = allProducts.entrySet();
                        Iterator i4 = set4.iterator();
                        boolean found = false;
                        while (i4.hasNext()) {
                            Map.Entry me4 = (Map.Entry) i4.next();
                            String productName = (String) me4.getKey();
                            String productValue = (String) me2.getValue();

                            if (productName.compareTo(trueproductName) == 0) {
                                allProducts.put(productName, trueproductValue);
                                found = true;
                            }

                        }
                        if (!found) {

                            allProducts.put(trueproductName, trueproductValue);
                        }
                        DCMs.put(dcmName, allProducts);
                    }
                }
            }
        }
        return DCMs;
    }

    public static HashMap getTotalReport(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String warehouseId) {
        HashMap totalReport = new HashMap();
        HashMap queries = new HashMap();
        boolean resQueryCheck = true;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_PRODUCT_QUERY_TOTAL";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String productQuery = res.getString("PRODUCT_QUERY");
                String productName = res.getString("PRODUCT_NAME");
                String sqlQuery = "select * from(( " + productQuery + " FROM VW_GEN_DCM,"
                        + "SOP_SCHEMA_PRODUCT,"
                        + "SOP_REQUEST,"
                        + "SOP_REQUEST_STATUS_LOG,"
                        + "SOP_REQUEST_DETAIL"
                        + " WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4"
                        + " AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID"
                        + " AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID"
                        + " AND SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID ="
                        + " SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID"
                        + " AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID"
                        + " AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition("AND VW_GEN_DCM.CHANNEL_ID", channelId);



                if (creationDateFrom.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST.CREATION_DATE >= TO_DATE('" + creationDateFrom + "','mm/dd/yyyy') ";
                }
                if (creationDateTo.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST.CREATION_DATE <= TO_DATE('" + creationDateTo + "','mm/dd/yyyy') ";
                }
                if (paymentDateFrom.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE >= TO_DATE('" + paymentDateFrom + "','mm/dd/yyyy') ";
                }
                if (paymentDateTo.compareTo("*") != 0) {
                    sqlQuery += "AND SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_DATE <= TO_DATE('" + paymentDateTo + "','mm/dd/yyyy') ";
                }
                if (warehouseId.compareTo("") != 0) {
                    sqlQuery += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                }
                sqlQuery += " GROUP BY VW_GEN_DCM.DCM_NAME";
                sqlQuery += ")) A,( SELECT distinct VW_GEN_DCM.DCM_NAME as dcm_name_2 from vw_gen_dcm , SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                        + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                        + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID "
                        + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID "
                        + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                        + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
                if (warehouseId.compareTo("") != 0) {
                    sqlQuery += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                }
                sqlQuery += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition("AND VW_GEN_DCM.CHANNEL_ID", channelId) + " ) B "
                        + "where B.dcm_name_2  = A.dcm_name(+)";

                //System.out.println("The Sql Qyery isssssssssssssssssss " + sqlQuery);
                Statement stat2 = con.createStatement();
                ResultSet resQuery = stat2.executeQuery(sqlQuery);
                totalReport = new HashMap();
                if (resQuery != null) {
                    while (resQuery.next()) {
                        resQueryCheck = false;
                        String dcmName = resQuery.getString("dcm_name_2");
                        String productValue = resQuery.getString(productName);
                        if (productValue == null) {
                            productValue = "0";
                        }
                        Set set = queries.entrySet();
                        Iterator i = set.iterator();
                        boolean found = false;
                        while (i.hasNext()) {
                            Map.Entry me = (Map.Entry) i.next();
                            //System.out.println(me.getKey() + " : " + me.getValue() );
                            if (me.getKey().equals(dcmName)) {
                                HashMap shm = (HashMap) queries.get(dcmName);
                                shm.put(productName, productValue);
                                found = true;
                            }
                        }
                        if (!found) {
                            HashMap shm = new HashMap();
                            shm.put(productName, productValue);
                            queries.put(dcmName, shm);

                        }

                    }
                    if (resQueryCheck) {
                        String strSql3 = "SELECT distinct VW_GEN_DCM.DCM_NAME "
                                + "FROM "
                                + "VW_GEN_DCM,SOP_SCHEMA_PRODUCT,SOP_REQUEST,SOP_REQUEST_STATUS_LOG,SOP_REQUEST_DETAIL "
                                + "WHERE SOP_REQUEST.REQUEST_STATUS_ID = 4 "
                                + "AND SOP_REQUEST.DCM_ID = VW_GEN_DCM.DCM_ID  "
                                + "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_DETAIL.REQUEST_ID  "
                                + "AND SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID = SOP_REQUEST_DETAIL.SCHEMA_PRODUCT_ID(+) "
                                + "AND SOP_REQUEST.REQUEST_STATUS_ID = SOP_REQUEST_STATUS_LOG.REQUEST_STATUS_ID ";
                        if (warehouseId.compareTo("") != 0) {
                            strSql3 += "AND SOP_REQUEST.WAREHOUSE_ID = '" + warehouseId + "' ";
                        }
                        strSql3 += "AND SOP_REQUEST.REQUEST_ID = SOP_REQUEST_STATUS_LOG.REQUEST_ID " + appendChannelCondition("AND VW_GEN_DCM.CHANNEL_ID", channelId)
                                + "order by VW_GEN_DCM.DCM_NAME asc";
                        Statement stat3 = con.createStatement();

                        ResultSet resQuery3 = stat3.executeQuery(strSql3);
                        while (resQuery3.next()) {
                            String dcmName = resQuery3.getString("DCM_Name");
                            String productValue = "0";
                            Set set = queries.entrySet();
                            Iterator i = set.iterator();
                            boolean found = false;
                            while (i.hasNext()) {
                                Map.Entry me = (Map.Entry) i.next();
//System.out.println(me.getKey() + " : " + me.getValue() );
                                if (me.getKey().equals(dcmName)) {
                                    HashMap shm = (HashMap) queries.get(dcmName);
                                    shm.put(productName, productValue);
                                    found = true;
                                }
                            }
                            if (!found) {
                                HashMap shm = new HashMap();
                                shm.put(productName, productValue);
                                queries.put(dcmName, shm);

                            }
                        }
                    }
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return queries;
    }

    public static String appendChannelCondition(String condition, String channelId) {
        return channelId != null && channelId.compareTo("") != 0 ? condition + "='" + channelId + "'" : "";
    }

    public static HashMap totalDCMProductAfterUpdate(Connection con, String channelId, String creationDateFrom, String creationDateTo, String paymentDateFrom, String paymentDateTo, String reportId, String warehouseId) {
        HashMap DCMs = (HashMap) checkDCMProduct(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, reportId, warehouseId);
        HashMap DCMTrue = (HashMap) getTotalReport(con, channelId, creationDateFrom, creationDateTo, paymentDateFrom, paymentDateTo, warehouseId);

        //System.out.println("The size of DCMTrue issssssssssssssss " + DCMTrue.size());
        Set set = DCMTrue.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            String truedcmName = (String) me.getKey();
            //System.out.println("The DCM Name is " + truedcmName);
            HashMap trueProducts = (HashMap) me.getValue();

            Set set2 = trueProducts.entrySet();
            Iterator i2 = set2.iterator();
            while (i2.hasNext()) {
                Map.Entry me2 = (Map.Entry) i2.next();
                String trueproductName = (String) me2.getKey();
                String trueproductValue = (String) me2.getValue();

                Set set3 = DCMs.entrySet();
                Iterator i3 = set3.iterator();
                while (i3.hasNext()) {
                    Map.Entry me3 = (Map.Entry) i3.next();
                    HashMap allProducts = (HashMap) me3.getValue();
                    String dcmName = (String) me3.getKey();

                    if (dcmName.compareTo(truedcmName) == 0) {
                        Set set4 = allProducts.entrySet();
                        Iterator i4 = set4.iterator();
                        boolean found = false;
                        while (i4.hasNext()) {
                            Map.Entry me4 = (Map.Entry) i4.next();
                            String productName = (String) me4.getKey();
                            String productValue = (String) me2.getValue();

                            if (productName.compareTo(trueproductName) == 0) {
                                allProducts.put(productName, trueproductValue);
                                found = true;
                            }

                        }
                        if (!found) {

                            allProducts.put(trueproductName, trueproductValue);
                        }
                        DCMs.put(dcmName, allProducts);
                    }
                }
            }
        }
        return DCMs;
    }

    public static void insertInvoiceDetail(Connection con, String invoiceNumber, String schemaProdcutID, String productQuantity, String productPrice, Long invoiceDetailId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into SOP_INVOICE_DETAIL (INVOICE_NUMBER,SCHEMA_PRODUCT_ID,PRODUCT_QUANTITY,PRODUCT_PRICE,INVOICE_DETAIL_ID)values('" + invoiceNumber + "','" + schemaProdcutID + "','" + productQuantity + "','" + productPrice + "','" + invoiceDetailId + "')";
            //Utility.logger.debug(strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector selectInvoiceDetail(Connection con, String invoiceNumber) {
        Vector sopVec = new Vector();
        InvoiceModel invoiceModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_INVOICE_DETAIL where INVOICE_NUMBER = '" + invoiceNumber + "'";
            //Utility.logger.debug("SQL--->"+strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                invoiceModel = new InvoiceModel();
                invoiceModel.setSchemaProductId(res.getString(invoiceModel.SCHEMA_PRODUCT_ID));
                invoiceModel.setProductQuantity(res.getString(invoiceModel.PRODUCT_QUANTITY));
                invoiceModel.setProductPrice(res.getString(invoiceModel.PRODUCT_PRICE));
                invoiceModel.setInvoiceDetailId(res.getString(invoiceModel.INVOICE_DETAIL_ID));
                sopVec.add(invoiceModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static void updateInvoiceDetailData(Connection con, String schemaProductId, String productQuantity, String productPrice, String invoiceDetailId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "update SOP_INVOICE_DETAIL "
                    + "SET SCHEMA_PRODUCT_ID = '" + schemaProductId + "' , PRODUCT_QUANTITY = '" + productQuantity + "' , PRODUCT_PRICE = '" + productPrice + "'"
                    + "WHERE INVOICE_DETAIL_ID = '" + invoiceDetailId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateInvoiceData(Connection con, String dcmId, String totalAmount, String invoiceNumber, String paymentSerialNumber, String paymentDate) {
        try {
            Statement stat = con.createStatement();
            String strSql = "update SOP_INVOICE "
                    + "SET DCM_ID = '" + dcmId + "' , TOTAL_AMOUNT = '" + totalAmount + "' , PAYMENT_SERIAL_NUMBER = '" + paymentSerialNumber + "' , PAYMENT_DATE = TO_DATE('" + paymentDate + "','yyyy-mm-dd')"
                    + "WHERE INVOICE_NUMBER = '" + invoiceNumber + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InvoiceModel getInviceNumber(Connection con, String invoiceDetailId) {
        InvoiceModel invoiceModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_INVOICE_DETAIL where INVOICE_DETAIL_ID = '" + invoiceDetailId + "'";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                invoiceModel = new InvoiceModel();
                invoiceModel.setSchemaProductId(res.getString(invoiceModel.SCHEMA_PRODUCT_ID));
                invoiceModel.setProductQuantity(res.getString(invoiceModel.PRODUCT_QUANTITY));
                invoiceModel.setProductPrice(res.getString(invoiceModel.PRODUCT_PRICE));
                invoiceModel.setInvoiceDetailId(res.getString(invoiceModel.INVOICE_DETAIL_ID));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceModel;
    }

    public static InvoiceModel selectInvoice(Connection con, String invoiceNumber) {
        InvoiceModel invoiceModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_INVOICE where INVOICE_NUMBER = '" + invoiceNumber + "'";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                invoiceModel = new InvoiceModel(res);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceModel;
    }

    public static void insertInvoice(Connection con, String dcmId, String totalAmount, Long invoiceNumber, String paymentSerialNumber, String paymentDate) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into SOP_INVOICE (DCM_ID,TOTAL_AMOUNT,INVOICE_NUMBER,PAYMENT_SERIAL_NUMBER,PAYMENT_DATE)values('" + dcmId + "','" + totalAmount + "','" + invoiceNumber + "','" + paymentSerialNumber + "',TO_DATE('" + paymentDate + "','yyyy-mm-dd'))";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getAllInvoices(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_INVOICE");
            while (res.next()) {
                InvoiceModel invoiceModel = new InvoiceModel(res);
                sopVec.add(invoiceModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static boolean getInvoiceDetail(Connection con, String invoiceNumber, String scehmaProductId) {
        boolean invoiceDetialExists = false;
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_INVOICE_DETAIL where INVOICE_NUMBER = " + invoiceNumber + " and SCHEMA_PRODUCT_ID = " + scehmaProductId + " ");
            if (res.next()) {
                invoiceDetialExists = true;
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceDetialExists;
    }

    public static int insertRequest(Connection con, String dcmId, String schemaId, String userId, String channelId, String warehouseId) {
        int intRequestID = 0;
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();

            long requestCode = System.currentTimeMillis();
            Long lRequestID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_ID");
            intRequestID = lRequestID.intValue();

            java.util.Date currentDate = new java.util.Date();

            int currentYear = currentDate.getYear() + 1900;
            int currentMonth = currentDate.getMonth() + 1;
            int currentDay = currentDate.getDate();

            String insertSql = "insert into SOP_REQUEST "
                    + " (REQUEST_CODE,REQUEST_ID,DCM_ID,SCHEMA_ID,CREATION_DATE,LAST_REQUEST_STATUS_LOG_ID,REQUEST_STATUS_ID,WAREHOUSE_ID) "
                    + " values('" + requestCode + "'," + lRequestID + "," + dcmId + "," + schemaId + ",TO_DATE('" + currentYear + "/" + currentMonth + "/" + currentDay + "','yyyy/mm/dd'),null," + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + ",'" + warehouseId + "')";
            System.out.println(insertSql);
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);

            Long lRequestStatusLogID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_STATUS_LOG_ID");
            String insertRequestLog = "insert into SOP_REQUEST_STATUS_LOG "
                    + " (REQUEST_STATUS_LOG_ID,REQUEST_STATUS_ID,REQUEST_ID,REQUEST_STATUS_DATE,USER_ID) "
                    + " values(" + lRequestStatusLogID + "," + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + "," + lRequestID + ",TO_DATE('" + currentYear + "/" + currentMonth + "/" + currentDay + "','yyyy/mm/dd')," + userId + ")";

            stat2.execute(insertRequestLog);

            insertRequestAcceptanceMail(con, userId, dcmId, channelId);

            stat2.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intRequestID;
    }

    public static void insertRequestAcceptanceMail(Connection con, String userId, String strDCMId, String channelId) {
        try {
            Statement stat = con.createStatement();

            String strDCMName = RequestDAO.getDCMNameById(con, strDCMId);

            Long lRequestMailID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_MAIL_ID");

            int intUserId = Integer.parseInt(userId);
            UserDTO userDTO = UserAccountDAO.getUserDTO(con, intUserId);
            UserModel userModel = userDTO.getUserModel();
            PersonModel personModel = userModel.getPersonModel();
            String personEMail = personModel.getPersonEMail();
            String personFullName = personModel.getPersonFullName();

            String insertRequestMailUser = "insert into SOP_REQUEST_MAIL (MAILID,MAILFROM,MAILTO,MAILREPLYTO,MAILCC,MAILBCC,MAILSUBJECT,MAILTEXT,SENDDATE,ATTACHMENT_FILES) "
                    + " values (" + lRequestMailID + ",'" + personEMail + "', '" + personEMail + "', NULL, NULL, NULL, 'Mobinil - SDS Request Notification','Accepted request have been made to DCM " + strDCMName + "',SYSDATE,null)";
            stat.execute(insertRequestMailUser);

            Vector requestNotificationList = getAllRequestNotification(con, channelId);
            for (int i = 0; i < requestNotificationList.size(); i++) {
                RequestNotificationModel requestNotificationModel = (RequestNotificationModel) requestNotificationList.get(i);
                String notificationPersonEMail = requestNotificationModel.getPersonEmail();
                String notificationPersonFullName = requestNotificationModel.getPersonFullName();
                lRequestMailID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_MAIL_ID");

                insertRequestMailUser = "insert into SOP_REQUEST_MAIL (MAILID,MAILFROM,MAILTO,MAILREPLYTO,MAILCC,MAILBCC,MAILSUBJECT,MAILTEXT,SENDDATE,ATTACHMENT_FILES) "
                        + " values (" + lRequestMailID + ",'" + personEMail + "', '" + notificationPersonEMail + "', NULL, NULL, NULL, 'Mobinil - SDS Request Notification','Accepted request have been made to DCM " + strDCMName + "',SYSDATE,null)";
                stat.execute(insertRequestMailUser);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendRequestNotificationToList(Connection con, String serverName, String serverPort, String appName, RequestModel requestModel, String channelId) {
        try {
            String strDcmId = requestModel.getDcmId();
            String strDCMName = requestModel.getDcmName();
            String strDcmCode = requestModel.getDcmCode();
            String requestCode = requestModel.getRequestCode();
            String requestId = requestModel.getRequestId();

            Vector vecRequestDetail = RequestDAO.getRequestDetails(con, requestId);

            Vector requestNotificationList = getAllRequestNotification(con, channelId);
            for (int i = 0; i < requestNotificationList.size(); i++) {
                RequestNotificationModel requestNotificationModel = (RequestNotificationModel) requestNotificationList.get(i);
                String notificationPersonEMail = requestNotificationModel.getPersonEmail();
                String notificationPersonFullName = requestNotificationModel.getPersonFullName();

                Utility.logger.debug(serverName + "---" + serverPort + "---" + appName + "---" + notificationPersonEMail + "---" + notificationPersonFullName + "---" + strDCMName);
                Utility.sendSOPRequestNotificationByMail(serverName, serverPort, appName, notificationPersonEMail, notificationPersonFullName, strDCMName, strDcmCode, requestCode, vecRequestDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertRequestDetails(Connection con, int requestId, String schemaProductId, String minimumLimit, String maximumLimit, String productAmount) {
        try {
            Statement stat = con.createStatement();

            Long lRequestDetailID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_DETAIL_ID");

            String insertSql = "insert into SOP_REQUEST_DETAIL "
                    + " (REQUEST_DETAIL_ID,REQUEST_ID,SCHEMA_PRODUCT_ID,MINIMUM_LIMIT,MAXIMUM_LIMIT,PRODUCT_AMOUNT) "
                    + " values(" + lRequestDetailID + "," + requestId + "," + schemaProductId + "," + minimumLimit + "," + maximumLimit + "," + productAmount + ")";
            System.out.println("The insert detail query isssssssss " + insertSql);
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertRequestDetails(Connection con, int requestId, String schemaProductId, String minimumLimit, String maximumLimit, String productAmount, String discount, String discountedAmount, String netAmount) {
        try {
            Statement stat = con.createStatement();

            Long lRequestDetailID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_DETAIL_ID");

            String insertSql = "insert into SOP_REQUEST_DETAIL "
                    + " (REQUEST_DETAIL_ID,REQUEST_ID,SCHEMA_PRODUCT_ID,MINIMUM_LIMIT,MAXIMUM_LIMIT,PRODUCT_AMOUNT,DISCOUNTED_AMOUNT,DISCOUNT_PERCENTAGE,NET_AMOUNT) "
                    + " values(" + lRequestDetailID + "," + requestId + "," + schemaProductId + "," + minimumLimit + "," + maximumLimit + "," + productAmount + "," + discountedAmount + "," + discount + "," + netAmount + ")";
            System.out.println("The insert detail query isssssssss " + insertSql);
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertRequestNotification(Connection con, String personFullName, String personEmail, String channelId) {
        try {
            Statement stat = con.createStatement();

            Long lRequestNotificationID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_NOTIFICATION");

            String insertSql = "insert into SOP_REQUEST_NOTIFICATION "
                    + " (REQUEST_NOTIFICATION_ID,PERSON_FULL_NAME,PERSON_EMAIL,CHANNEL_ID) "
                    + " values(" + lRequestNotificationID + ",'" + personFullName + "','" + personEmail + "','" + channelId + "')";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateRequestNotification(Connection con, String requestNotificationId, String personFullName, String personEmail, String channelId) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "update SOP_REQUEST_NOTIFICATION "
                    + " set PERSON_FULL_NAME = '" + personFullName + "',PERSON_EMAIL = '" + personEmail + "' "
                    + " where REQUEST_NOTIFICATION_ID = " + requestNotificationId + "  and CHANNEL_ID = '" + channelId + "'";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductStockPhysicalAmount(Connection con, String productCode, String importDate, String physicalAmount, String channelId, String warehouseId) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "update SOP_PRODUCT_STOCK "
                    + " set PHYSICAL_AMOUNT = " + physicalAmount + " "
                    + " where CHANNEL_ID = '" + channelId + "' and WAREHOUSE_ID = '" + warehouseId + "' and PRODUCT_CODE = '" + productCode + "' and IMPORT_DATE = TO_DATE('" + importDate + "','yyyy/mm/dd') ";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductRequestLimits(Connection con, String productCode, String physicalAmount, String schemaId) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "update SOP_PRODUCT_REQUEST_LIMIT "
                    + " set PHYSICAL_MAXIMUM_LIMIT = " + physicalAmount + " "
                    + " where PRODUCT_CODE = '" + productCode + "' and SCHEMA_ID = '" + schemaId + "'  ";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteRequestNotification(Connection con, String requestNotificationId, String channelId) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "delete from SOP_REQUEST_NOTIFICATION "
                    + " where REQUEST_NOTIFICATION_ID = " + requestNotificationId + " and CHANNEL_ID = '" + channelId + "'";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductRequestLimit(Connection con, String schemaProductId, String productAmount, String operation, String dcmId, String warehouseId) {
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            String operationSign = "";
            if (operation.compareTo("subtracting") == 0) {
                operationSign = "-";
            } else if (operation.compareTo("adding") == 0) {
                operationSign = "+";
            }
            String strSql = "select IS_MANUAL from SOP_PRODUCT_REQUEST_LIMIT where SCHEMA_PRODUCT_ID = " + schemaProductId + " and DCM_ID = " + dcmId + " and WAREHOUSE_ID = '" + warehouseId + "'";
            System.out.println("The select query issssssssssssssssssss " + strSql);
            Utility.logger.debug(strSql);
            ResultSet res2 = stat2.executeQuery(strSql);
            int isManual = -1;
            if (res2.next()) {
                isManual = res2.getInt("IS_MANUAL");
            }
            String updateSql = "";
            if (isManual == 1) {
                updateSql = "update SOP_PRODUCT_REQUEST_LIMIT "
                        + " set MAXIMUM_LIMIT = MAXIMUM_LIMIT " + operationSign + " " + productAmount + " "
                        + " where SCHEMA_PRODUCT_ID = " + schemaProductId + " "
                        + " and WAREHOUSE_ID = '" + warehouseId + "'"
                        + " and IS_MANUAL = " + SOPInterfaceKey.CONST_PRODUCT_LIMIT_IS_MANUAL + " ";
            } else if (isManual == 0) {
                updateSql = "update SOP_PRODUCT_REQUEST_LIMIT "
                        + " set MAXIMUM_LIMIT = MAXIMUM_LIMIT " + operationSign + " " + productAmount + " "
                        + " where SCHEMA_PRODUCT_ID = " + schemaProductId + " "
                        + " and WAREHOUSE_ID = '" + warehouseId + "'"
                        + " and DCM_ID = " + dcmId + " ";
            }
            Utility.logger.debug(updateSql);
            System.out.println("The update query issssssssssssssss " + updateSql);
            stat.execute(updateSql);

            String updatePhysicalAmount = "update SOP_PRODUCT_REQUEST_LIMIT "
                    + " set PHYSICAL_MAXIMUM_LIMIT = PHYSICAL_MAXIMUM_LIMIT " + operationSign + " " + productAmount + " "
                    + " where SCHEMA_PRODUCT_ID = " + schemaProductId + " and WAREHOUSE_ID = '" + warehouseId + "'";
            System.out.println("The update query issssssssssssssss " + updateSql);
            stat.execute(updatePhysicalAmount);

            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductStock(Connection con, String schemaProductId, String productAmount, String operation, String warehouseId) {
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            String operationSign = "";
            if (operation.compareTo("subtracting") == 0) {
                operationSign = "-";
            } else if (operation.compareTo("adding") == 0) {
                operationSign = "+";
            }
            String selectSql = " select * from SOP_PRODUCT_REQUEST_LIMIT where SCHEMA_PRODUCT_ID = " + schemaProductId + " and WAREHOUSE_ID = '" + warehouseId + "'";

            ResultSet res2 = stat2.executeQuery(selectSql);
            String productCode = "";
            if (res2.next()) {
                productCode = res2.getString("PRODUCT_CODE");
            }

            java.util.Date currentDate = new java.util.Date();

            int currentYear = currentDate.getYear() + 1900;
            int currentMonth = currentDate.getMonth() + 1;
            int currentDay = currentDate.getDate();

            String strCurrentDate = currentYear + "/" + currentMonth + "/" + currentDay;

            String updateSql = "update SOP_PRODUCT_STOCK "
                    + " set ACTIVE_AMOUNT = ACTIVE_AMOUNT " + operationSign + " " + productAmount + " "
                    + " where PRODUCT_CODE = '" + productCode + "' and warehouse_id = '" + warehouseId + "'"
                    + " and IMPORT_DATE = TO_DATE('" + strCurrentDate + "','yyyy/mm/dd') ";

            Utility.logger.debug(updateSql);
            if (productCode.compareTo("") != 0 || productCode != null) {
                stat.execute(updateSql);
            }
            res2.close();
            stat2.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getAllRequests(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from VW_SOP_REQUEST where REQUEST_STATUS_ID <> " + SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED + " order by REQUEST_STATUS_ID,CREATION_DATE ");
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static String getDCMName(Connection con, String strDCMCode) {
        String dcmName = "does not exist in the system.";
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from GEN_DCM where DCM_CODE = '" + strDCMCode + "' ";
            Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                dcmName = res.getString("DCM_NAME");
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dcmName;
    }

    public static String getDCMNameById(Connection con, String strDCMId) {
        String dcmName = "does not exist in the system.";
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from GEN_DCM where DCM_ID = " + strDCMId + " ";
            Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                dcmName = res.getString("DCM_NAME");
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dcmName;
    }

    public static RequestModel getRequestById(Connection con, String requestId) {
        RequestModel requestModel = null;
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from VW_SOP_REQUEST where REQUEST_ID = " + requestId + " ");
            if (res.next()) {
                requestModel = new RequestModel(res);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestModel;
    }

    public static Vector getInvoiceByFilter(Connection con, String invoiceNumber, String dcmId, String paymentDate) {
        Vector sopVec = new Vector();
        boolean andFlag = false;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_INVOICE ";
            //Utility.logger.debug(strSql);

            if (invoiceNumber.compareTo("") != 0) {
                if (!andFlag) {
                    strSql += " where ";
                    Utility.logger.debug(strSql);
                } else {
                    strSql += " and ";
                    Utility.logger.debug(strSql);
                }
                strSql += " INVOICE_NUMBER = '" + invoiceNumber + "' ";
                andFlag = true;
            }

            if (dcmId.compareTo("") != 0) {
                if (!andFlag) {
                    strSql += " where ";
                } else {
                    strSql += " and ";
                }
                strSql += "DCM_ID = '" + dcmId + "'";
                andFlag = true;
            }

            if (paymentDate.compareTo("*") != 0) {
                if (!andFlag) {
                    strSql += " where ";
                } else {
                    strSql += " and ";
                }
                strSql += "PAYMENT_DATE >= TO_DATE('" + paymentDate + "','mm/dd/yyyy')";
                andFlag = true;
            }
            strSql += " order by INVOICE_NUMBER,DCM_ID,PAYMENT_DATE";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                InvoiceModel invoiceModel = new InvoiceModel(res);
                sopVec.add(invoiceModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static Vector getRequestsByFilter(Connection con, String requestId, String dcmId, String requestCreationDateFrom, String requestCreationDateTo, String statusId, String channelId, String warehouseId) {
        Vector sopVec = new Vector();
        boolean andFlag = false;
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SOP_REQUEST ";

            if (requestId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " REQUEST_ID = '" + requestId + "' ";
                andFlag = true;
            }
            if (dcmId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " DCM_ID = " + dcmId + " ";
                andFlag = true;
            }
            if (requestCreationDateFrom.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CREATION_DATE >= TO_DATE('" + requestCreationDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (requestCreationDateTo.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CREATION_DATE <= TO_DATE('" + requestCreationDateTo + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (statusId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " REQUEST_STATUS_ID = " + statusId + " ";
                andFlag = true;
            } else {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " REQUEST_STATUS_ID <> " + SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED + " ";
                andFlag = true;
            }

            if (channelId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CHANNEL_ID = " + channelId + "";
                andFlag = true;
            }

            if (warehouseId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " WAREHOUSE_ID = " + warehouseId + "";
                andFlag = true;
            }

            sqlQuery += " order by REQUEST_STATUS_ID,CREATION_DATE ";
            System.out.println("The select query isssssssssssss " + sqlQuery);
            //Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getPaidDeletedRequestsByFilter(Connection con, String requestId, String dcmId, String requestCreationDateFrom, String requestCreationDateTo, String statusId, String channelId, String warehouseId) {
        Vector sopVec = new Vector();
        boolean andFlag = false;
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SOP_REQUEST ";

            if (requestId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " REQUEST_ID = '" + requestId + "' ";
                andFlag = true;
            }
            if (dcmId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " DCM_ID = " + dcmId + " ";
                andFlag = true;
            }
            if (requestCreationDateFrom.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CREATION_DATE >= TO_DATE('" + requestCreationDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (requestCreationDateTo.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CREATION_DATE <= TO_DATE('" + requestCreationDateTo + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (statusId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " REQUEST_STATUS_ID = " + statusId + " ";
                andFlag = true;
            } else {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " (REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED + " or REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_PAID + ")";
                andFlag = true;
            }

            if (channelId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CHANNEL_ID = " + channelId + "";
                andFlag = true;
            }

            if (warehouseId.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " WAREHOUSE_ID = " + warehouseId + "";
                andFlag = true;
            }

            sqlQuery += " order by REQUEST_STATUS_ID,CREATION_DATE ";
            //Utility.logger.debug(sqlQuery);
            System.out.println("the query isssssssssssssssssss " + sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getActiveOrInactiveRequestByDCM(Connection con, String dcmID, String warehouseId) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from VW_SOP_REQUEST where DCM_ID = " + dcmID + " and WAREHOUSE_ID = '" + warehouseId + "' and (REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + " or REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED + ") ");
            System.out.println("The query issssssssssssssss " + "select * from VW_SOP_REQUEST where DCM_ID = " + dcmID + " and (REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + " or REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED + ") ");
            //Utility.logger.debug("The query issssssssssssssss " + "select * from VW_SOP_REQUEST where DCM_ID = "+dcmID+" and (REQUEST_STATUS_ID = "+SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE+" or REQUEST_STATUS_ID = "+SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED+") ");
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getActiveOrInactiveRequest(Connection con, String channelId, String warehouseId) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from VW_SOP_REQUEST where CHANNEL_ID = '" + channelId + "' and WAREHOUSE_ID = '" + warehouseId + "' and (REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + " or REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED + ") ");
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getActiveOrInactiveRequestByDCMCode(Connection con, String dcmCode, String requestCode) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select VW_SOP_REQUEST.* from VW_SOP_REQUEST "
                    + " where (VW_SOP_REQUEST.REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + " or VW_SOP_REQUEST.REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED + ") "
                    + " and (VW_SOP_REQUEST.DCM_CODE = '" + dcmCode + "' or VW_SOP_REQUEST.request_code = '" + requestCode + "') ";

            Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getActiveOrInactiveRequestBySchema(Connection con, String schemaId, String channelId) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from VW_SOP_REQUEST where CHANNEL_ID = '" + channelId + "' and SCHEMA_ID = " + schemaId + " and (REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE + " or REQUEST_STATUS_ID = " + SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED + ") ");
            while (res.next()) {
                sopVec.add(new RequestModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getAllRequestStatus(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_REQUEST_STATUS order by REQUEST_STATUS_ID ");
            while (res.next()) {
                sopVec.add(new RequestStatusModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getAllRequestNotification(Connection con, String channelId) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_REQUEST_NOTIFICATION where CHANNEL_ID = '" + channelId + "' order by PERSON_FULL_NAME ");
            while (res.next()) {
                sopVec.add(new RequestNotificationModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static RequestNotificationModel getRequestNotificationById(Connection con, String requestNotificationId) {
        RequestNotificationModel requestNotificationModel = null;
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_REQUEST_NOTIFICATION where REQUEST_NOTIFICATION_ID = " + requestNotificationId + " ");
            while (res.next()) {
                requestNotificationModel = new RequestNotificationModel(res);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestNotificationModel;
    }

    public static void updateRequestStatus(Connection con, String requestID, String requestStatusId, String userId, String strDCMId, String channelId) {
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();

//      java.util.Date currentDate = new java.util.Date();
//
//      int currentYear = currentDate.getYear()+1900;
//      int currentMonth = currentDate.getMonth()+1;
//      int currentDay = currentDate.getDate();


            String insertSql = "update SOP_REQUEST "
                    + "SET REQUEST_STATUS_ID = " + requestStatusId + " "
                    + "WHERE REQUEST_ID = " + requestID + "";
            //Utility.logger.debug(insertSql);
            if (requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE) == 0) {
                insertRequestAcceptanceMail(con, userId, strDCMId, channelId);
            }
            stat.execute(insertSql);

            Long lRequestStatusLogID = Utility.getSequenceNextVal(con, "SEQ_SOP_REQUEST_STATUS_LOG_ID");
//      String insertSqlSchemaLog = "insert into SOP_REQUEST_STATUS_LOG (REQUEST_STATUS_LOG_ID,REQUEST_STATUS_ID,REQUEST_ID,REQUEST_STATUS_DATE,USER_ID) "+
//                                  " values("+lRequestStatusLogID+","+requestStatusId+","+requestID+",TO_DATE('"+currentYear+"/"+currentMonth+"/"+currentDay+"','yyyy/mm/dd'),"+userId+")";
            String insertSqlSchemaLog = "insert into SOP_REQUEST_STATUS_LOG (REQUEST_STATUS_LOG_ID,REQUEST_STATUS_ID,REQUEST_ID,REQUEST_STATUS_DATE,USER_ID) "
                    + " values(" + lRequestStatusLogID + "," + requestStatusId + "," + requestID + ",(select CREATION_DATE from sop_request where REQUEST_ID='" + requestID + "')," + userId + ")";

            stat2.execute(insertSqlSchemaLog);

            stat.close();
            stat2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getRequestDetails(Connection con, String requestId) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SOP_REQUEST_DETAIL where REQUEST_ID = " + requestId + " order by IS_QUOTA_ITEM,PRODUCT_NAME_ENGLISH ";
            System.out.println("The request details issssssss " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            Utility.logger.debug("sssssssssssssssssssssssssss " + strSql);
            while (res.next()) {

                RequestDetailModel requestDetailModel = new RequestDetailModel(res);
                requestDetailModel.setProductDiscount(res.getString("DISCOUNT_PERCENTAGE"));
                requestDetailModel.setProductDiscountAmount(res.getString("DISCOUNTED_AMOUNT"));
                requestDetailModel.setProductNetAmount(res.getString("NET_AMOUNT"));
                sopVec.add(requestDetailModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getAllProducts(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select distinct(trim(product_name_english)) as PRODUCT_NAME from sop_schema_product ");
            while (res.next()) {
                sopVec.add(new genProductModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getSpecificProducts(Connection con, String report_id, String channel_id) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from sop_product_report  where  CHANNEL_ID='" + channel_id + "' and report_id ='" + report_id + "' ");
            while (res.next()) {
                sopVec.add(new genProductModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getAllReports(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(" select * from sop_report_name");
            while (res.next()) {
                sopVec.add(new reportModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getAllChannels(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from gen_channel ");
            while (res.next()) {
                sopVec.add(new chanelModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static Vector getAllWarehouse(Connection con) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from gen_warehouse order by warehouse_id");
            while (res.next()) {
                sopVec.add(new WarehouseModel(res));
            }
            stat.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static void deleteWarehouse(Connection con, String warehouseId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from gen_warehouse where warehouse_id = '" + warehouseId + "'";
            stat.executeUpdate(strSql);
            stat.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getChannelWarehouse(Connection con) {
        Vector channelWarehouseVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select sop_warehouse_channel.CHANNEL_ID,sop_warehouse_channel.WAREHOUSE_ID,channel_name,warehouse_name "
                    + "from gen_channel,gen_warehouse,sop_warehouse_channel "
                    + "where sop_warehouse_channel.CHANNEL_ID = gen_channel.CHANNEL_ID "
                    + "and sop_warehouse_channel.WAREHOUSE_ID = gen_warehouse.WAREHOUSE_ID order by channel_name";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                channelWarehouseVec.add(new WarehouseChannelModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return channelWarehouseVec;
    }

    public static WarehouseChannelModel selectWarehouseChannel(Connection con, String channelId, String warehouseId) {
        WarehouseChannelModel warehouseChannelModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select sop_warehouse_channel.CHANNEL_ID,sop_warehouse_channel.WAREHOUSE_ID,channel_name,warehouse_name "
                    + "from gen_channel,gen_warehouse,sop_warehouse_channel "
                    + "where sop_warehouse_channel.CHANNEL_ID = gen_channel.CHANNEL_ID "
                    + "and sop_warehouse_channel.CHANNEL_ID = '" + channelId + "' "
                    + "and sop_warehouse_channel.WAREHOUSE_ID = '" + warehouseId + "' "
                    + "and sop_warehouse_channel.WAREHOUSE_ID = gen_warehouse.WAREHOUSE_ID";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                warehouseChannelModel = new WarehouseChannelModel();
                warehouseChannelModel.setChannelId(res.getString(warehouseChannelModel.CHANNEL_ID));
                warehouseChannelModel.setChannelName(res.getString(warehouseChannelModel.CHANNEL_NAME));
                warehouseChannelModel.setWarehouseId(res.getString(warehouseChannelModel.WAREHOUSE_ID));
                warehouseChannelModel.setWarehouseName(res.getString(warehouseChannelModel.WAREHOUSE_NAME));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warehouseChannelModel;
    }

    public static void updateWarehouseChannel(Connection con, String oldChannelId, String oldWarehouseId, String newChannelId, String newWarehouseId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "update sop_warehouse_channel set warehouse_id = '" + newWarehouseId + "',channel_id = '" + newChannelId + "'"
                    + " where warehouse_id = '" + oldWarehouseId + "' and channel_id = '" + oldChannelId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inserWarehouseChannel(Connection con, String channelId, String warehouseId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into sop_warehouse_channel(channel_id,warehouse_id) values "
                    + "('" + channelId + "','" + warehouseId + "')";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteWarehouseChannel(Connection con, String channelId, String warehouseId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from sop_warehouse_channel where channel_id = '" + channelId + "' and warehouse_id = '" + warehouseId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(Connection con, String reportId, String channelId) {

        try {
            Statement stat = con.createStatement();
            stat.executeQuery("delete from  sop_product_report where CHANNEL_ID='" + channelId + "' and  REPORT_ID='" + reportId + "' ");

            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void insertProductReport(Connection con, String productid, String reportId, String channelId) {

        try {

            Long SOP_PRODUCT_REPORT_ID = Utility.getSequenceNextVal(con, "SEQ_SOP_PRODUCT_REPORT_ID");
            Statement stat = con.createStatement();
            stat.executeQuery("insert into  sop_product_report(SOP_PRODUCT_REPORT_ID,PRODUCT_NAME,REPORT_ID,CHANNEL_ID) values('" + SOP_PRODUCT_REPORT_ID.toString() + "','" + productid + "','" + reportId + "','" + channelId + "') ");

            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector selectUserChannel(Connection con) {
        Vector userchannelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from sop_user_channel";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                userchannelVec.add(new UserChannelModel(res));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userchannelVec;
    }

    public static void deleteUserChannel(Connection con, String userId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from sop_user_channel where user_id = '" + userId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserChannel(Connection con, String userId, String channelId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into sop_user_channel (user_id,channel_id) values ('" + userId + "','" + channelId + "')";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getDcmChannel(Connection con, String levelName, String userId) {
        Vector dcmChannelVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select dcm_id,dcm_name,channel_id,channel_name from vw_gen_dcm,vw_gen_dcm_level where  "
                    + " vw_gen_Dcm.DCM_LEVEL_ID = vw_gen_dcm_level.DCM_LEVEL_ID and dcm_level_name='" + levelName + "' and  "
                    + "dcm_id in (select dcm_id from SOP_USER_DCM_ACCESS where user_id = '" + userId + "') order by vw_gen_dcm.dcm_name ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                dcmChannelVec.add(new ChannelDCMModel(res));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dcmChannelVec;
    }

    public static Vector getWarehouseByChannel(Connection con, String channelId) {
        Vector warehouseVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select gen_warehouse.warehouse_id,warehouse_name from gen_warehouse,SOP_WAREHOUSE_CHANNEL"
                    + " where gen_warehouse.warehouse_id = SOP_WAREHOUSE_CHANNEL.warehouse_id and channel_id = '" + channelId + "'";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                warehouseVec.add(new WarehouseModel(res));
            }
            stat.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warehouseVec;
    }

    public static void insertNewWarehosue(Connection con, String warehouseName) {

        try {
            Long warehouseId = Utility.getSequenceNextVal(con, "SEQ_GEN_WAREHOUSE");
            Statement stat = con.createStatement();
            String strSql = "insert into gen_warehouse(warehouse_id,warehouse_name )values "
                    + "('" + warehouseId + "','" + warehouseName + "')";
            stat.executeUpdate(strSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sql = "sdfsdfsd sdfsdfsd sdfsdfsdf "
                + "sdfsdfsd sdfsdf "
                + appendChannelCondition("chanelid", null);
        System.out.println("sql is " + sql);

    }
}