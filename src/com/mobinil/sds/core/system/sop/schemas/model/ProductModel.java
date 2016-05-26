package com.mobinil.sds.core.system.sop.schemas.model;

import java.sql.*;
import java.io.*;

public class ProductModel implements Serializable {

    //
    
    //
    String schemaProductId;
    String productId;
    String productCode;
    String schemaId;
    String lcsProductCode;
    String isActive;
    String hasQuantity;
    String productNameEnglish;
    String productNameArabic;
    String productPrice;
    String salesTax;
    String productWeight;
    String productDiscount;
    String productDiscountAmount;
    String productNetAmount;
    String isPointItem;
    String isQuotaItem;
    String minimumLimit;
    String maximumLimit;
    ////for stock produts
    String stockProductCode;
    String activeAmount;
    String physicalAmount;
    String importDate;
    String isManual;
    String productRequestLimitId;
    String importedActiveAmount;
    public static final String SCHEMA_PRODUCT_ID = "SCHEMA_PRODUCT_ID";
    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final String PRODUCT_CODE = "PRODUCT_CODE";
    public static final String SCHEMA_ID = "SCHEMA_ID";
    public static final String LCS_PRODUCT_CODE = "LCS_PRODUCT_CODE";
    public static final String IS_ACTIVE = "IS_ACTIVE";
    public static final String HAS_QUANTITY = "HAS_QUANTITY";
    public static final String PRODUCT_NAME_ENGLISH = "PRODUCT_NAME_ENGLISH";
    public static final String PRODUCT_NAME_ARABIC = "PRODUCT_NAME_ARABIC";
    public static final String PRODUCT_PRICE = "PRODUCT_PRICE";
    public static final String SALES_TAX = "SALES_TAX";
    public static final String PRODUCT_WEIGHT = "PRODUCT_WEIGHT";
    public static final String PRODUCT_DISCOUNT = "PRODUCT_DISCOUNT";
    public static final String IS_POINT_ITEM = "IS_POINT_ITEM";
    public static final String IS_QUOTA_ITEM = "IS_QUOTA_ITEM";
    public static final String ACTIVE_AMOUNT = "ACTIVE_AMOUNT";
    public static final String PHYSICAL_AMOUNT = "PHYSICAL_AMOUNT";
    public static final String IMPORT_DATE = "IMPORT_DATE";
    public static final String IMPORTED_ACTIVE_AMOUNT = "IMPORTED_ACTIVE_AMOUNT";

    public ProductModel() {
    }

    public ProductModel(ResultSet res) {
        try {
            //schemaProductId = res.getString(SCHEMA_PRODUCT_ID);
            productId = res.getString(PRODUCT_ID);
            //schemaId = res.getString(SCHEMA_ID);
            lcsProductCode = res.getString(LCS_PRODUCT_CODE);
            isActive = res.getString(IS_ACTIVE);
            hasQuantity = res.getString(HAS_QUANTITY);
            productNameEnglish = res.getString(PRODUCT_NAME_ENGLISH);
            productNameArabic = res.getString(PRODUCT_NAME_ARABIC);
            productPrice = res.getString(PRODUCT_PRICE);
            salesTax = res.getString(SALES_TAX);
            //productWeight = res.getString(PRODUCT_WEIGHT);
            //isPointItem = res.getString(IS_POINT_ITEM);
            //isQuotaItem = res.getString(IS_QUOTA_ITEM);      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSchemaProductId() {
        return schemaProductId;
    }

    public void setSchemaProductId(String newSchemaProductId) {
        schemaProductId = newSchemaProductId;
    }
////////////////////////////////////////////   

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String newSchemaId) {
        schemaId = newSchemaId;
    }
////////////////////////////////////////////

    public String getProductId() {
        return productId;
    }

    public void setProductId(String newProductId) {
        productId = newProductId;
    }
////////////////////////////////////////////

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String newProductCode) {
        productCode = newProductCode;
    }
////////////////////////////////////////////

    public String getLcsProductCode() {
        return lcsProductCode;
    }

    public void setLcsProductCode(String newLcsProductCode) {
        lcsProductCode = newLcsProductCode;
    }
////////////////////////////////////////////

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String newIsActive) {
        isActive = newIsActive;
    }
////////////////////////////////////////////

    public String getHasQuantity() {
        return hasQuantity;
    }

    public void setHasQuantity(String newHasQuantity) {
        hasQuantity = newHasQuantity;
    }
////////////////////////////////////////////

    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    public void setProductNameEnglish(String newProductNameEnglish) {
        productNameEnglish = newProductNameEnglish;
    }
////////////////////////////////////////////

    public String getProductNameArabic() {
        return productNameArabic;
    }

    public void setProductNameArabic(String newProductNameArabic) {
        productNameArabic = newProductNameArabic;
    }
////////////////////////////////////////////

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String newProductPrice) {
        productPrice = newProductPrice;
    }
////////////////////////////////////////////

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String newSalesTax) {
        salesTax = newSalesTax;
    }
////////////////////////////////////////////

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String newProductWeight) {
        productWeight = newProductWeight;
    }
////////////////////////////////////////////

    public String getIsPointItem() {
        return isPointItem;
    }

    public void setIsPointItem(String newIsPointItem) {
        isPointItem = newIsPointItem;
    }
////////////////////////////////////////////

    public String getIsQuotaItem() {
        return isQuotaItem;
    }

    public void setIsQuotaItem(String newIsQuotaItem) {
        isQuotaItem = newIsQuotaItem;
    }
////////////////////////////////////////////

    public String getMinimumLimit() {
        return minimumLimit;
    }

    public void setMinimumLimit(String newMinimumLimit) {
        minimumLimit = newMinimumLimit;
    }
////////////////////////////////////////////

    public String getMaximumLimit() {
        return maximumLimit;
    }

    public void setMaximumLimit(String newMaximumLimit) {
        maximumLimit = newMaximumLimit;
    }
////////////////////////////////////////////

    public String getActiveAmount() {
        return activeAmount;
    }

    public void setActiveAmount(String newActiveAmount) {
        activeAmount = newActiveAmount;
    }
////////////////////////////////////////////

    public String getImportedActiveAmount() {
        return importedActiveAmount;
    }

    public void setImportedActiveAmount(String newImportedActiveAmount) {
        importedActiveAmount = newImportedActiveAmount;
    }
/////////////////////////////////////////////

    public String getPhysicalAmount() {
        return physicalAmount;
    }

    public void setPhysicalAmount(String newPhysicalAmount) {
        physicalAmount = newPhysicalAmount;
    }
////////////////////////////////////////////

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String newImportDate) {
        importDate = newImportDate;
    }
////////////////////////////////////////////

    public String getStockProductCode() {
        return stockProductCode;
    }

    public void setStockProductCode(String newStockProductCode) {
        stockProductCode = newStockProductCode;
    }
////////////////////////////////////////////

    public String getIsManual() {
        return isManual;
    }

    public void setIsManual(String newIsManual) {
        isManual = newIsManual;
    }
////////////////////////////////////////////

    public String getProductRequestLimitId() {
        return productRequestLimitId;
    }

    public void setProductRequestLimitId(String newProductRequestLimitId) {
        productRequestLimitId = newProductRequestLimitId;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getProductDiscountAmount() {
        return productDiscountAmount;
    }

    public void setProductDiscountAmount(String productDiscountAmount) {
        this.productDiscountAmount = productDiscountAmount;
    }

    public String getProductNetAmount() {
        return productNetAmount;
    }

    public void setProductNetAmount(String productNetAmount) {
        this.productNetAmount = productNetAmount;
    }

    
}