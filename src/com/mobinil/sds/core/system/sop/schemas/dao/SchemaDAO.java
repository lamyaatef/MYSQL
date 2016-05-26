package com.mobinil.sds.core.system.sop.schemas.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sop.*;
import com.mobinil.sds.core.system.sop.schemas.model.*;

public class SchemaDAO {

    private SchemaDAO() {
    }

    public static Vector getAllProductsLcs(Connection con) {
        Vector sopVec = new Vector();
        ProductModel productModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_LCS_PHYSICAL_AMOUNT";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                productModel = new ProductModel();
                productModel.setProductId(res.getString(productModel.PRODUCT_ID));
                productModel.setProductCode(res.getString(productModel.PRODUCT_CODE));
                productModel.setPhysicalAmount(res.getString(productModel.PHYSICAL_AMOUNT));
                sopVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static void insertProductLcs(Connection con, Long productId, String ProductCode, String physicalAmount) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into SOP_LCS_PHYSICAL_AMOUNT (PRODUCT_ID,PRODUCT_CODE,PHYSICAL_AMOUNT)values('" + productId + "','" + ProductCode + "','" + physicalAmount + "')";
            //Utility.logger.debug(strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProductModel selectProductLcs(Connection con, String productId) {
        ProductModel productModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_LCS_PHYSICAL_AMOUNT where PRODUCT_ID ='" + productId + "'";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                productModel = new ProductModel();
                productModel.setProductId(res.getString(productModel.PRODUCT_ID));
                productModel.setProductCode(res.getString(productModel.PRODUCT_CODE));
                productModel.setPhysicalAmount(res.getString(productModel.PHYSICAL_AMOUNT));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productModel;
    }

    public static void deleteProductLcs(Connection con, String productId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from SOP_LCS_PHYSICAL_AMOUNT where PRODUCT_ID ='" + productId + "'";
            Utility.logger.debug("The Query isssssssssssssssss " + strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductLcs(Connection con, String productId, String productCode, String physicalAmount) throws Exception {
        try {
            Statement stat = con.createStatement();
            String strSql = "update SOP_LCS_PHYSICAL_AMOUNT "
                    + "SET PRODUCT_CODE = '" + productCode + "' , PHYSICAL_AMOUNT = '" + physicalAmount + "'"
                    + "WHERE PRODUCT_ID = '" + productId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateProductPgw(Connection con, String productId, String lcsProductCode, String isActive, String hasQuantity, String productNameEnglish, String productNameArabic, String productPrice, String salesTax) {
        try {
            Statement stat = con.createStatement();
            String strSql = "update SOP_PGW_VIEW "
                    + "SET LCS_PRODUCT_CODE = '" + lcsProductCode + "' , IS_ACTIVE = '" + isActive + "' , HAS_QUANTITY = '" + hasQuantity + "' , PRODUCT_NAME_ENGLISH = '" + productNameEnglish + "' , PRODUCT_NAME_ARABIC = '" + productNameArabic + "' , PRODUCT_PRICE = '" + productPrice + "' ,  SALES_TAX = '" + salesTax + "'"
                    + "WHERE PRODUCT_ID = '" + productId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertProductPgw(Connection con, Long productId, String lcsProductCode, String isActive, String hasQuantity, String productNameEnglis, String productNameArabic, String productPrice, String salesTax, String channelId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "insert into SOP_PGW_VIEW (PRODUCT_ID,LCS_PRODUCT_CODE,IS_ACTIVE,HAS_QUANTITY,PRODUCT_NAME_ENGLISH,PRODUCT_NAME_ARABIC,PRODUCT_PRICE,SALES_TAX,CHANNEL_ID)values(" + productId + ",'" + lcsProductCode + "'," + isActive + "," + hasQuantity + ",'" + productNameEnglis + "','" + productNameArabic + "'," + productPrice + "," + salesTax + ",'" + channelId + "')";
            //System.out.print(strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProductModel selectProductPgw(Connection con, String productId) {
        ProductModel productModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_PGW_VIEW where PRODUCT_ID = '" + productId + "'";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                productModel = new ProductModel(res);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productModel;
    }

    public static void deleteProductFromPgw(Connection con, String productId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from SOP_PGW_VIEW where PRODUCT_ID = '" + productId + "'";
            Utility.logger.debug("The query issssssss " + strSql);
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getAllProducts(Connection con, String channelId) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_PGW_VIEW where CHANNEL_ID = '" + channelId + "' order by LCS_PRODUCT_CODE,PRODUCT_NAME_ENGLISH");
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                sopVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static Vector getProductPgwByFilter(Connection con, String lcsProductCode, String productNameEnglish, String channelId) {
        Vector sopVec = new Vector();
        boolean andFlag = false;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_PGW_VIEW where CHANNEL_ID = '" + channelId + "' ";
            //Utility.logger.debug(strSql);

            if (lcsProductCode.compareTo("") != 0) {
                //if(!andFlag){strSql += " where ";
                //Utility.logger.debug(strSql);}
                //else{strSql += " and ";
                //Utility.logger.debug(strSql);}
                strSql += " and LCS_PRODUCT_CODE = '" + lcsProductCode + "' ";
                //andFlag = true;
            }

            if (productNameEnglish.compareTo("") != 0) {
                //if(!andFlag)
                //{
                //strSql += " where ";
                //}
                //else
                //{
                //strSql += " and ";
                //}
                strSql += " and PRODUCT_NAME_ENGLISH = '" + productNameEnglish + "'";
                andFlag = true;
            }

            strSql += " order by LCS_PRODUCT_CODE,PRODUCT_NAME_ENGLISH";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                sopVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static boolean importProduct(Connection con, String channelId) {
        boolean success = false;
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_PGW_VIEW where CHANNEL_ID = '" + channelId + "'");
            deleteImportProduct(con);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                String lcsProductCode = (String) productModel.getLcsProductCode();
                String productId = (String) productModel.getProductId();
                String isActive = (String) productModel.getIsActive();
                String hasQuantity = (String) productModel.getHasQuantity();
                String productNameEnglish = (String) productModel.getProductNameEnglish();
                String productNameArabic = (String) productModel.getProductNameArabic();
                String productPrice = (String) productModel.getProductPrice();
                String salesTax = (String) productModel.getSalesTax();
                Long lProductID = Utility.getSequenceNextVal(con, "SEQ_SOP_IMPORT_PRODUCT_ID");
                insertImportProduct(con, lProductID, lcsProductCode, isActive, hasQuantity, productNameEnglish, productNameArabic, productPrice, salesTax);
            }
            res.close();
            stat.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void insertImportProduct(Connection con, Long productID, String lcsProductCode, String isActive, String hasQuantity, String productNameEnglish, String productNameArabic, String productPrice, String salesTax) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "insert into SOP_IMPORTED_PRODUCT(PRODUCT_ID, "
                    + "LCS_PRODUCT_CODE, IS_ACTIVE,HAS_QUANTITY,PRODUCT_NAME_ENGLISH,PRODUCT_NAME_ARABIC,PRODUCT_PRICE,SALES_TAX) "
                    + "values(" + productID + ",'" + lcsProductCode + "'," + isActive + "," + hasQuantity + ",'" + productNameEnglish + "','" + productNameArabic + "','" + productPrice + "'," + salesTax + ")";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteImportProduct(Connection con) {
        try {
            Statement stat = con.createStatement();

            String deleteSql = "delete from SOP_IMPORTED_PRODUCT";
            //Utility.logger.debug(insertSql);
            stat.execute(deleteSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteImportedProductsForDistributors(Connection con, String channelId, String warehouseId) {
        boolean success = false;
        try {
            Statement stat = con.createStatement();
            String strSqlSelect = "select * from SOP_PRODUCT_STOCK where CHANNEL_ID = '" + channelId + "' and warehouse_id = '" + warehouseId + "' and IMPORT_DATE > SYSDATE - 1";
            System.out.println("The query is " + strSqlSelect);
            ResultSet res = stat.executeQuery(strSqlSelect);
            if (res.next()) {
                Statement statDelete = con.createStatement();
                String strSqlDelete = "delete from SOP_PRODUCT_STOCK where CHANNEL_ID = '" + channelId + "' and warehouse_id = '" + warehouseId + "' and IMPORT_DATE > SYSDATE - 1";
                Utility.logger.debug("The delete Query isssssssssssss " + strSqlDelete);
                statDelete.executeUpdate(strSqlDelete);
                success = true;
                statDelete.close();
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static int createSchema(Connection con, String schemaCode, String userId, String schemaName, String schemaDescription, String channelId) {
        int returnSchemaId = 0;
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            Long lSchemaID = Utility.getSequenceNextVal(con, "SEQ_SOP_SCHEMA_ID");
            java.util.Date currentDate = new java.util.Date();

            int currentYear = currentDate.getYear() + 1900;
            int currentMonth = currentDate.getMonth() + 1;
            int currentDay = currentDate.getDate();
            String insertSql = "insert into SOP_SCHEMA(SCHEMA_ID, "
                    + "SCHEMA_CODE,CREATION_DATE,START_DATE,END_DATE,LAST_SCHEMA_STATUS_LOG_ID,SCHEMA_STATUS_ID,SCHEMA_NAME,SCHEMA_DESCRIPTION,CHANNEL_ID) "
                    + "values(" + lSchemaID + ",'" + schemaCode + "',TO_DATE('" + currentYear + "/" + currentMonth + "/" + currentDay + "','yyyy/mm/dd'),null,null,null," + SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING + ",'" + schemaName + "','" + schemaDescription + "','" + channelId + "')";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);

            Long lSchemaStatusLogID = Utility.getSequenceNextVal(con, "SEQ_SOP_SCHEMA_STATUS_LOG_ID");
            String insertSqlSchemaLog = "insert into SOP_SCHEMA_STATUS_LOG (SCHEMA_STATUS_LOG_ID,SCHEMA_STATUS_ID,SCHEMA_ID,SCHEMA_STATUS_LOG_DATE,USER_ID) "
                    + " values(" + lSchemaStatusLogID + "," + SOPInterfaceKey.CONST_SCHEMA_STATUS_PREPARING + "," + lSchemaID + ",TO_DATE('" + currentYear + "/" + currentMonth + "/" + currentDay + "','yyyy/mm/dd')," + userId + ")";

            stat2.execute(insertSqlSchemaLog);

            stat.close();
            stat2.close();
            returnSchemaId = lSchemaID.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSchemaId;
    }

    public static void insertSchemaProduct(Connection con, int schemaId, String channelId) {
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            Statement stat3 = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_IMPORTED_PRODUCT where IS_ACTIVE = 1");
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                String lcsProductCode = (String) productModel.getLcsProductCode();
                String productId = (String) productModel.getProductId();
                String isActive = (String) productModel.getIsActive();
                String hasQuantity = (String) productModel.getHasQuantity();
                String productNameEnglish = (String) productModel.getProductNameEnglish();
                String productNameArabic = (String) productModel.getProductNameArabic();
                String productPrice = (String) productModel.getProductPrice();
                String salesTax = (String) productModel.getSalesTax();
                Long lSchemaProductID = Utility.getSequenceNextVal(con, "SEQ_SOP_SCHEMA_PRODUCT_ID");
                String productWeight = "0";
                String productIsPoint = "0";
                String productIsQuota = "0";

                String getActiveSchemaProduct = ""
                        + " select SOP_SCHEMA_PRODUCT.PRODUCT_WEIGHT,SOP_SCHEMA_PRODUCT.IS_POINT_ITEM,SOP_SCHEMA_PRODUCT.IS_QUOTA_ITEM "
                        + " from SOP_SCHEMA,SOP_SCHEMA_PRODUCT "
                        + " where "
                        + " SOP_SCHEMA_PRODUCT.LCS_PRODUCT_CODE = '" + lcsProductCode + "' "
                        + " and SOP_SCHEMA.CHANNEL_ID = '" + channelId + "' "
                        + " and SOP_SCHEMA_PRODUCT.SCHEMA_ID = SOP_SCHEMA.SCHEMA_ID "
                        + " and SOP_SCHEMA.SCHEMA_STATUS_ID = " + SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE + " ";
                ResultSet res3 = stat3.executeQuery(getActiveSchemaProduct);
                while (res3.next()) {
                    productWeight = res3.getString("PRODUCT_WEIGHT");
                    productIsPoint = res3.getString("IS_POINT_ITEM");
                    productIsQuota = res3.getString("IS_QUOTA_ITEM");
                }

                String insertSql = "insert into SOP_SCHEMA_PRODUCT(SCHEMA_PRODUCT_ID, "
                        + "  PRODUCT_ID,SCHEMA_ID,LCS_PRODUCT_CODE,IS_ACTIVE,HAS_QUANTITY,PRODUCT_NAME_ENGLISH,PRODUCT_NAME_ARABIC,PRODUCT_PRICE,SALES_TAX,PRODUCT_WEIGHT,IS_POINT_ITEM,IS_QUOTA_ITEM) "
                        + "values(" + lSchemaProductID + "," + productId + "," + schemaId + ",'" + lcsProductCode + "'," + isActive + "," + hasQuantity + ",'" + productNameEnglish + "','" + productNameArabic + "','" + productPrice + "'," + salesTax + "," + productWeight + "," + productIsPoint + "," + productIsQuota + ")";
                //Utility.logger.debug("wwwwwww"+insertSql);
                stat2.execute(insertSql);


            }
            res.close();
            stat.close();
            stat2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getAllSchemas(Connection con, String orderBy, String channelID) {
        Vector sopVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SOP_SCHEMA where CHANNEL_ID = '" + channelID + "' order by " + orderBy + "";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                sopVec.add(new SchemaModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static void deleteSchemaProduct(Connection con, String schemaId) {
        try {
            Statement stat = con.createStatement();
            String strSql = "delete from SOP_SCHEMA where SCHEMA_ID='" + schemaId + "'";
            stat.executeUpdate(strSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMaxSchemaCode(Connection con) {
        String maxSchemaCode = "";
        try {
            Statement stat = con.createStatement();
            String strSql = "select max(schema_code) from SOP_SCHEMA ";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                maxSchemaCode = res.getString("MAX(SCHEMA_CODE)");
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxSchemaCode;
    }

    public static boolean checkActiveSchema(Connection con, String channelId) {
        boolean activeSchemaExists = false;

        try {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SOP_SCHEMA where CHANNEL_ID = '" + channelId + "' and SCHEMA_STATUS_ID = 1";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                activeSchemaExists = true;
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activeSchemaExists;
    }

    public static SchemaModel getSchemabySchemaId(Connection con, String schemaId, String channelId) {
        SchemaModel schemaModel = null;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from VW_SOP_SCHEMA where CHANNEL_ID = '" + channelId + "' and SCHEMA_ID = " + schemaId + " ";
            //Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                schemaModel = new SchemaModel(res);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schemaModel;
    }

    public static Vector getSchemasByFilter(Connection con, String schemaCode, String schemaName, String createDateFrom, String createDateTo, String startDateFrom, String startDateTo, String endDateFrom, String endDateTo, String schemaStatus, String channelID) {
        Vector sopVec = new Vector();
        boolean andFlag = false;
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from VW_SOP_SCHEMA ";
            if (schemaCode.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " SCHEMA_CODE = '" + schemaCode + "' ";
                andFlag = true;
            }
            if (schemaName.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " SCHEMA_NAME like '%" + schemaName + "%' ";
                andFlag = true;
            }
            if (createDateFrom.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CREATION_DATE >= TO_DATE('" + createDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (createDateTo.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CREATION_DATE <= TO_DATE('" + createDateTo + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (startDateFrom.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " START_DATE >= TO_DATE('" + startDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (startDateTo.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " START_DATE <= TO_DATE('" + startDateTo + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (endDateFrom.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " END_DATE >= TO_DATE('" + endDateFrom + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (endDateTo.compareTo("*") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " END_DATE <= TO_DATE('" + endDateTo + "','mm/dd/yyyy') ";
                andFlag = true;
            }
            if (schemaStatus.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " SCHEMA_STATUS_ID = " + schemaStatus + " ";
                andFlag = true;
            }
            if (channelID.compareTo("") != 0) {
                if (!andFlag) {
                    sqlQuery += " where ";
                } else {
                    sqlQuery += " and ";
                }
                sqlQuery += " CHANNEL_ID = " + channelID + " ";
                andFlag = true;
            }
            sqlQuery += " order by SCHEMA_STATUS_ID,CREATION_DATE,SCHEMA_CODE";
            //Utility.logger.debug(sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                sopVec.add(new SchemaModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sopVec;
    }

    public static boolean checkSchemaCodeExist(Connection con, String schemaCode, String channelId) {
        boolean checkSchema = false;
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from VW_SOP_SCHEMA where CHANNEL_ID = '" + channelId + "' and SCHEMA_CODE = '" + schemaCode + "' ");
            if (res.next()) {
            } else {
                checkSchema = true;
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkSchema;
    }

    public static Vector getAllSchemaStatus(Connection con) {
        Vector schemaStatusVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_SCHEMA_STATUS order by SCHEMA_STATUS_ID");
            while (res.next()) {
                schemaStatusVec.add(new SchemaStatusModel(res));
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schemaStatusVec;
    }

    public static synchronized void updateSchemaStatus(Connection con, String schemaID, String schemaStatusId, String userId) {
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();

            java.util.Date currentDate = new java.util.Date();

            int currentYear = currentDate.getYear() + 1900;
            int currentMonth = currentDate.getMonth() + 1;
            int currentDay = currentDate.getDate();

            String updatedate = "";

            if (schemaStatusId.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_INACTIVE) == 0) {
                updatedate = "END_DATE";
            } else if (schemaStatusId.compareTo(SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE) == 0) {
                updatedate = "START_DATE";
            }
            String insertSql = "update SOP_SCHEMA "
                    + "SET SCHEMA_STATUS_ID = " + schemaStatusId + " , " + updatedate + " = TO_DATE('" + currentYear + "/" + currentMonth + "/" + currentDay + "','yyyy/mm/dd') "
                    + "WHERE SCHEMA_ID = " + schemaID + "";
            System.out.println("the update query isssssssssssssssssssss " + insertSql);
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);

            Long lSchemaStatusLogID = Utility.getSequenceNextVal(con, "SEQ_SOP_SCHEMA_STATUS_LOG_ID");
            String insertSqlSchemaLog = "insert into SOP_SCHEMA_STATUS_LOG (SCHEMA_STATUS_LOG_ID,SCHEMA_STATUS_ID,SCHEMA_ID,SCHEMA_STATUS_LOG_DATE,USER_ID) "
                    + " values(" + lSchemaStatusLogID + "," + schemaStatusId + "," + schemaID + ",TO_DATE('" + currentYear + "/" + currentMonth + "/" + currentDay + "','yyyy/mm/dd')," + userId + ")";
            System.out.println("The insert query isssssssssssssss " + insertSqlSchemaLog);
            stat2.execute(insertSqlSchemaLog);

            stat.close();
            stat2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getAllSchemaProducts(Connection con, String schemaId) {
        Vector productVec = new Vector();
        try {
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from SOP_SCHEMA_PRODUCT where SCHEMA_ID = " + schemaId + " order by IS_QUOTA_ITEM,PRODUCT_NAME_ENGLISH");
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setProductDiscount(res.getString("DISCOUNT_PERCENTAGE"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static Vector getDCMRequestProductLimits(Connection con, String dcmId, String warehouseId) {
        Vector productVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from SOP_PRODUCT_REQUEST_LIMIT,SOP_PRODUCT_STOCK A,SOP_SCHEMA_PRODUCT,SOP_SCHEMA "
                    + " where SOP_PRODUCT_REQUEST_LIMIT.DCM_ID = " + dcmId + " "
                    + " and SOP_PRODUCT_REQUEST_LIMIT.PRODUCT_CODE = A.PRODUCT_CODE "
                    + " and A.IMPORT_DATE = (select max(B.IMPORT_DATE) from SOP_PRODUCT_STOCK B) "
                    + " and A.PRODUCT_CODE = SOP_SCHEMA_PRODUCT.LCS_PRODUCT_CODE "
                    + " and A.WAREHOUSE_ID = '" + warehouseId + "' "
                    + " and SOP_SCHEMA_PRODUCT.SCHEMA_ID = SOP_SCHEMA.SCHEMA_ID "
                    + " and SOP_SCHEMA.SCHEMA_STATUS_ID = " + SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE + " "
                    + " order by SOP_SCHEMA_PRODUCT.IS_QUOTA_ITEM ";
            System.out.println("The select query isssssssssssssss " + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productModel.setMinimumLimit(res.getString("MINIMUM_LIMIT"));
                productModel.setMaximumLimit(res.getString("MAXIMUM_LIMIT"));
                productModel.setStockProductCode(res.getString("PRODUCT_CODE"));
                productModel.setActiveAmount(res.getString("ACTIVE_AMOUNT"));
                productModel.setPhysicalAmount(res.getString("PHYSICAL_AMOUNT"));
                productModel.setImportDate(res.getString("IMPORT_DATE"));
                productModel.setIsManual(res.getString("IS_MANUAL"));
                productModel.setProductRequestLimitId(res.getString("PRODUCT_REQUEST_LIMIT_ID"));
                productVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static Vector getProductStockByDate(Connection con, String importDateFrom, String importDateTo, String productCode, String productNameEnglish, String channelId, String warehouseId) {
        Vector productVec = new Vector();
        try {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();

            //String strSqlMaxSchemaId = "select max(SCHEMA_ID) from SOP_SCHEMA_PRODUCT";
            String strSqlMaxSchemaId = "select schema_id from sop_schema where CHANNEL_ID = '" + channelId + "' and schema_status_id =1";
            ResultSet res2 = stat2.executeQuery(strSqlMaxSchemaId);
            String schemaId = "";
            if (res2.next()) {
                //schemaId = res2.getString("MAX(SCHEMA_ID)");
                schemaId = res2.getString("SCHEMA_ID");
            }
            //Utility.logger.debug("aaaaaaaaaaaa"+schemaId); 

            String strSql = "select * from SOP_PRODUCT_STOCK,(select * from SOP_SCHEMA_PRODUCT A where A.SCHEMA_ID = '" + schemaId + "')C "
                    + " where SOP_PRODUCT_STOCK.CHANNEL_ID = '" + channelId + "' and"
                    + " SOP_PRODUCT_STOCK.WAREHOUSE_ID = '" + warehouseId + "' and"
                    + " SOP_PRODUCT_STOCK.PRODUCT_CODE = C.LCS_PRODUCT_CODE (+) ";

            if (importDateFrom.compareTo("*") != 0) {
                strSql += "and SOP_PRODUCT_STOCK.IMPORT_DATE >= TO_DATE('" + importDateFrom + "','mm/dd/yyyy') ";
            }
            if (importDateTo.compareTo("*") != 0) {
                strSql += "and SOP_PRODUCT_STOCK.IMPORT_DATE <= TO_DATE('" + importDateTo + "','mm/dd/yyyy') ";
            }
            if (productCode.compareTo("") != 0) {
                strSql += "and SOP_PRODUCT_STOCK.PRODUCT_CODE = '" + productCode + "' ";
            }
            if (productNameEnglish.compareTo("") != 0) {
                strSql += "and C.PRODUCT_NAME_ENGLISH = '" + productNameEnglish + "' ";
            }

            strSql += " order by SOP_PRODUCT_STOCK.IMPORT_DATE,C.PRODUCT_NAME_ENGLISH";
            //System.out.println("The query isssssssssssssssss " + strSql);
            Utility.logger.debug(strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productModel.setActiveAmount(res.getString("ACTIVE_AMOUNT"));
                productModel.setImportedActiveAmount(res.getString("IMPORTED_ACTIVE_AMOUNT"));
                productModel.setPhysicalAmount(res.getString("PHYSICAL_AMOUNT"));
                productModel.setImportDate(res.getString("IMPORT_DATE"));
                productModel.setStockProductCode(res.getString("PRODUCT_CODE"));
                productVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static Vector getActiveSchemaProducts(Connection con, String strDcmId, String channelId, String warehouseId) {
        Vector productVec = new Vector();
        try {
            Statement stat = con.createStatement();
            String sqlQuery = "select * from SOP_SCHEMA_PRODUCT,SOP_SCHEMA,SOP_PRODUCT_REQUEST_LIMIT "
                    + " where SOP_SCHEMA.SCHEMA_STATUS_ID = " + SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE + " "
                    + " and SOP_SCHEMA.SCHEMA_ID = SOP_SCHEMA_PRODUCT.SCHEMA_ID "
                    + " and SOP_SCHEMA.CHANNEL_ID = '" + channelId + "' "
                    + " and SOP_PRODUCT_REQUEST_LIMIT.SCHEMA_PRODUCT_ID = SOP_SCHEMA_PRODUCT.SCHEMA_PRODUCT_ID "
                    + " and SOP_PRODUCT_REQUEST_LIMIT.DCM_ID = " + strDcmId + " "
                    + " and SOP_PRODUCT_REQUEST_LIMIT.WAREHOUSE_ID = " + warehouseId + " "
                    + " order by SOP_SCHEMA_PRODUCT.IS_QUOTA_ITEM,SOP_SCHEMA_PRODUCT.PRODUCT_NAME_ENGLISH";
            System.out.println("The Active Query issssssssssssssssss " + sqlQuery);
            Utility.logger.debug("The Active Query issssssssssssssssss " + sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setProductDiscount(res.getString("DISCOUNT_PERCENTAGE"));
                productModel.setProductDiscountAmount(res.getString("DISCOUNTED_AMOUNT"));
                productModel.setProductNetAmount(res.getString("NET_AMOUNT"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productModel.setMinimumLimit(res.getString("MINIMUM_LIMIT"));
                productModel.setMaximumLimit(res.getString("MAXIMUM_LIMIT"));
                productModel.setPhysicalAmount(res.getString("PHYSICAL_MAXIMUM_LIMIT"));
                productVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static Vector getLatestStockProductsMatchActiveSchemaProducts(Connection con) {
        Vector productVec = new Vector();
        try {
            Statement stat = con.createStatement();

            String sqlQuery = "select * from SOP_PRODUCT_STOCK A,SOP_SCHEMA_PRODUCT,SOP_SCHEMA "
                    + " where A.IMPORT_DATE = (select max(B.IMPORT_DATE) from SOP_PRODUCT_STOCK B) "
                    + " and A.PRODUCT_CODE = SOP_SCHEMA_PRODUCT.LCS_PRODUCT_CODE "
                    + " and SOP_SCHEMA_PRODUCT.SCHEMA_ID = SOP_SCHEMA.SCHEMA_ID "
                    + " and SOP_SCHEMA.SCHEMA_STATUS_ID = " + SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE + " ";
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productModel.setStockProductCode(res.getString("PRODUCT_CODE"));
                productModel.setActiveAmount(res.getString("ACTIVE_AMOUNT"));
                productModel.setPhysicalAmount(res.getString("PHYSICAL_AMOUNT"));
                productModel.setImportDate(res.getString("IMPORT_DATE"));
                productVec.add(productModel);
            }
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static Vector getLatestStockProductsMatchActiveSchemaProducts(String channelId, String warehouseId) {

        Vector productVec = new Vector();
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String sqlQuery = "select * from SOP_PRODUCT_STOCK A,SOP_SCHEMA_PRODUCT,SOP_SCHEMA "
                    + " where A.IMPORT_DATE = (select max(B.IMPORT_DATE) from SOP_PRODUCT_STOCK B) "
                    + " and A.PRODUCT_CODE = SOP_SCHEMA_PRODUCT.LCS_PRODUCT_CODE "
                    + " and SOP_SCHEMA_PRODUCT.SCHEMA_ID = SOP_SCHEMA.SCHEMA_ID "
                    + " and A.CHANNEL_ID = '" + channelId + "' "
                    + " and A.WAREHOUSE_ID = '" + warehouseId + "' "
                    + " and SOP_SCHEMA.CHANNEL_ID = '" + channelId + "' "
                    + " and SOP_SCHEMA.SCHEMA_STATUS_ID = " + SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE + " ";
            System.out.println("The query isssssss " + sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productModel.setStockProductCode(res.getString("PRODUCT_CODE"));
                productModel.setActiveAmount(res.getString("ACTIVE_AMOUNT"));
                productModel.setPhysicalAmount(res.getString("PHYSICAL_AMOUNT"));
                productModel.setImportDate(res.getString("IMPORT_DATE"));
                productVec.add(productModel);
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static Vector getStockProductsMatchActiveSchemaProductsByDate(String strDate, String channelId, String warehouseId) {

        Vector productVec = new Vector();
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String sqlQuery = "select * from SOP_PRODUCT_STOCK A,SOP_SCHEMA_PRODUCT,SOP_SCHEMA "
                    + " where A.IMPORT_DATE = TO_DATE('" + strDate + "','yyyy/mm/dd') "
                    + " and A.PRODUCT_CODE = SOP_SCHEMA_PRODUCT.LCS_PRODUCT_CODE "
                    + " and SOP_SCHEMA_PRODUCT.SCHEMA_ID = SOP_SCHEMA.SCHEMA_ID "
                    + " and SOP_SCHEMA.CHANNEL_ID = '" + channelId + "' "
                    + " and A.CHANNEL_ID = '" + channelId + "' "
                    + " and A.WAREHOUSE_ID = '" + warehouseId + "' "
                    + " and SOP_SCHEMA.SCHEMA_STATUS_ID = " + SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE + " ";
            //Utility.logger.debug("The Query issssssssssssssss " + sqlQuery);
            ResultSet res = stat.executeQuery(sqlQuery);
            while (res.next()) {
                ProductModel productModel = new ProductModel(res);
                productModel.setSchemaProductId(res.getString("SCHEMA_PRODUCT_ID"));
                productModel.setSchemaId(res.getString("SCHEMA_ID"));
                productModel.setProductWeight(res.getString("PRODUCT_WEIGHT"));
                productModel.setIsPointItem(res.getString("IS_POINT_ITEM"));
                productModel.setIsQuotaItem(res.getString("IS_QUOTA_ITEM"));
                productModel.setStockProductCode(res.getString("PRODUCT_CODE"));
                productModel.setActiveAmount(res.getString("ACTIVE_AMOUNT"));
                productModel.setPhysicalAmount(res.getString("PHYSICAL_AMOUNT"));
                productModel.setImportDate(res.getString("IMPORT_DATE"));
                productVec.add(productModel);
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productVec;
    }

    public static void updateSchemaProduct(Connection con, String schemaProductID, String productWeight, String isPointItem, String isQuotaItem, String productDiscount,String discountedAmount,String netAmount) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "update SOP_SCHEMA_PRODUCT "
                    + "SET PRODUCT_WEIGHT = " + productWeight + " ,DISCOUNT_PERCENTAGE=" + productDiscount + " ,DISCOUNTED_AMOUNT="+discountedAmount+" ,NET_AMOUNT="+netAmount+" ,IS_POINT_ITEM = " + isPointItem + " , IS_QUOTA_ITEM = " + isQuotaItem + " "
                    + "WHERE SCHEMA_PRODUCT_ID = " + schemaProductID + "";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductRequestLimit(Connection con, String productRequestLimitId, String minimumLimit, String maximumLimit) {
        try {
            Statement stat = con.createStatement();

            String insertSql = "update SOP_PRODUCT_REQUEST_LIMIT "
                    + "SET MINIMUM_LIMIT = " + minimumLimit + " , MAXIMUM_LIMIT = " + maximumLimit + " "
                    + "WHERE PRODUCT_REQUEST_LIMIT_ID = " + productRequestLimitId + "";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertSchemaProductFromAnotherSchema(Connection con, int schemaId, ProductModel productModel) {
        try {
            Statement stat = con.createStatement();

            String lcsProductCode = (String) productModel.getLcsProductCode();
            String productId = (String) productModel.getProductId();
            String isActive = (String) productModel.getIsActive();
            String hasQuantity = (String) productModel.getHasQuantity();
            String productNameEnglish = (String) productModel.getProductNameEnglish();
            String productNameArabic = (String) productModel.getProductNameArabic();
            String productPrice = (String) productModel.getProductPrice();
            String salesTax = (String) productModel.getSalesTax();
            Long lSchemaProductID = Utility.getSequenceNextVal(con, "SEQ_SOP_SCHEMA_PRODUCT_ID");
            String productWeight = (String) productModel.getProductWeight();
            String productIsPoint = (String) productModel.getIsPointItem();
            String productIsQuota = (String) productModel.getIsQuotaItem();

            String insertSql = "insert into SOP_SCHEMA_PRODUCT(SCHEMA_PRODUCT_ID, "
                    + "  PRODUCT_ID,SCHEMA_ID,LCS_PRODUCT_CODE,IS_ACTIVE,HAS_QUANTITY,PRODUCT_NAME_ENGLISH,PRODUCT_NAME_ARABIC,PRODUCT_PRICE,SALES_TAX,PRODUCT_WEIGHT,IS_POINT_ITEM,IS_QUOTA_ITEM) "
                    + "values(" + lSchemaProductID + "," + productId + "," + schemaId + ",'" + lcsProductCode + "'," + isActive + "," + hasQuantity + ",'" + productNameEnglish + "','" + productNameArabic + "','" + productPrice + "'," + salesTax + "," + productWeight + "," + productIsPoint + "," + productIsQuota + ")";
            //Utility.logger.debug("wwwwwww"+insertSql);
            stat.execute(insertSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getTmpDataWarehouse() {
        Vector sopVec = new Vector();
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("select * from TMP_DATA_WAREHOUSE ");
            while (res.next()) {
                sopVec.add(new ProductImportModel(res));
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static String getLCSProductPhysicalAmount(String productCode, String channelId) {
        String physicalAmount = "0";
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String strSql = "select * from " + SOPInterfaceKey.TBL_SOP_LCS_PHYSICAL_AMOUNT + " where CHANNEL_ID = '" + channelId + "' and PRODUCT_CODE = '" + productCode + "' ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                physicalAmount = res.getString("PHYSICAL_AMOUNT");
            }

            res.close();
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return physicalAmount;
    }

    public static String getLCSProductPhysicalAmount(Connection con, String productCode, String channelId) {
        String physicalAmount = "0";
        try {
            Statement stat = con.createStatement();

            String strSql = "select * from " + SOPInterfaceKey.TBL_SOP_LCS_PHYSICAL_AMOUNT + " where CHANNEL_ID = '" + channelId + "' and PRODUCT_CODE = '" + productCode + "' ";
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                physicalAmount = res.getString("PHYSICAL_AMOUNT");
            }

            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return physicalAmount;
    }

    public static void insertProductStock(String productCode, String activeAmount, String importDate, String physicalAmount, String channelId, String warehouseId) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            int intActiveAmount = Integer.parseInt(activeAmount);
            int intPhysicalAmount = Integer.parseInt(physicalAmount);
            if (intActiveAmount > intPhysicalAmount) {
                activeAmount = physicalAmount;
            }
            String insertSql = "insert into SOP_PRODUCT_STOCK(PRODUCT_CODE, "
                    + "ACTIVE_AMOUNT, IMPORT_DATE,PHYSICAL_AMOUNT,CHANNEL_ID,IMPORTED_ACTIVE_AMOUNT,WAREHOUSE_ID) "
                    + "values('" + productCode + "'," + activeAmount + ",TO_DATE('" + importDate + "','yyyy/mm/dd')," + physicalAmount + ",'" + channelId + "'," + activeAmount + ",'" + warehouseId + "')";
            //Utility.logger.debug(insertSql);
            stat.execute(insertSql);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertProductRequestLimit(int dcmId, String schemaProductId, String minimumLimit, String maximumLimit, String isManual, String physicalMaximumLimit, String productCode, String schemaId, String warehouseId) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();
            Long lProductRequestLimitID = Utility.getSequenceNextVal(con, "SEQ_SOP_PRODUCT_REQUEST_LIMIT");
            String insertSql = "insert into SOP_PRODUCT_REQUEST_LIMIT"
                    + "(DCM_ID,SCHEMA_PRODUCT_ID,MINIMUM_LIMIT,MAXIMUM_LIMIT,PRODUCT_REQUEST_LIMIT_ID,IS_MANUAL,PHYSICAL_MAXIMUM_LIMIT,PRODUCT_CODE,SCHEMA_ID,WAREHOUSE_ID) "
                    + "values(" + dcmId + "," + schemaProductId + "," + minimumLimit + "," + maximumLimit + "," + lProductRequestLimitID + "," + isManual + "," + physicalMaximumLimit + ",'" + productCode + "'," + schemaId + ",'" + warehouseId + "')";

            Utility.logger.debug(insertSql);
            try {
                stat.execute(insertSql);
            } catch (SQLException se) {
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTmpDataWarehouse() {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String deleteSql = "delete from TMP_DATA_WAREHOUSE";
            //Utility.logger.debug(insertSql);
            stat.execute(deleteSql);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductRequestLimits(Connection con) {
        try {
            Statement stat = con.createStatement();

            String deleteSql = "delete from SOP_PRODUCT_REQUEST_LIMIT where IS_MANUAL = " + SOPInterfaceKey.CONST_PRODUCT_LIMIT_IS_MANUAL + " ";
            //Utility.logger.debug(insertSql);
            stat.execute(deleteSql);
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductRequestLimits(String channelId, String warehouseId) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String deleteSql = "delete from SOP_PRODUCT_REQUEST_LIMIT where DCM_ID in (select DCM_ID from gen_dcm where channel_id = " + channelId + " )and warehouse_id = '" + warehouseId + "'";

            System.out.println("The delete query is " + deleteSql);
            //Utility.logger.debug(insertSql);
            stat.execute(deleteSql);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductEquationCalculate() {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String deleteSql = "delete from SOP_PRODUCT_EQUATION_CALCULATE ";
            //Utility.logger.debug(insertSql);
            stat.execute(deleteSql);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertProductEquationCalculate(String productCode) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String strSql = "insert into SOP_PRODUCT_EQUATION_CALCULATE(PRODUCT_CODE,IS_CALCULATED) values('" + productCode + "',0)";
            //Utility.logger.debug(strSql);
            stat.execute(strSql);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductEquationCalculate(String productCode, String isCalculated) {
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String strSql = "update SOP_PRODUCT_EQUATION_CALCULATE set IS_CALCULATED = " + isCalculated + " where PRODUCT_CODE = '" + productCode + "' ";
            //Utility.logger.debug(insertSql);
            stat.execute(strSql);
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector getProductEquationCalculate(String isCalculated) {
        Vector sopVec = new Vector();
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String strSql = "select * from SOP_PRODUCT_EQUATION_CALCULATE where IS_CALCULATED = " + isCalculated + " ";
            //Utility.logger.debug(insertSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                String productCode = res.getString("PRODUCT_CODE");
                sopVec.add(productCode);
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sopVec;
    }

    public static String getProductHaveEquation(String productCode) {
        String returnValue = "";
        try {
            Connection con = Utility.getConnection();
            Statement stat = con.createStatement();

            String strSql = "select * from SOP_EQUATION_PRODUCT_STOCK where PRODUCT_CODE = '" + productCode + "' ";
            //Utility.logger.debug(insertSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                returnValue = res.getString("PRODUCT_CODE");
            }
            stat.close();
            Utility.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public static String getChannelName(String channelId, Connection con) {

        String strSql = "select channel_name from gen_channel where channel_id = '" + channelId + "'";
        String channelName = DBUtil.executeQuerySingleValueString(strSql, "channel_name");

        return channelName;
    }
}
