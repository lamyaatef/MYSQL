package com.mobinil.sds.core.system.sop.requests.model;

import java.sql.*;
import java.io.*;

public class RequestDetailModel implements Serializable {

    String requestDetailId;
    String requestId;
    String schemaProductId;
    String minimumLimit;
    String maximumLimit;
    String productAmount;
    String productDiscount;
    String productDiscountAmount;
    String productNetAmount;
    String hasQuantity;
    String productNameEnglish;
    String productNameArabic;
    String productPrice;
    String salesTax;
    String productWeight;
    String isPointItem;
    String isQuotaItem;
    public static final String REQUEST_DETAIL_ID = "REQUEST_DETAIL_ID";
    public static final String REQUEST_ID = "REQUEST_ID";
    public static final String SCHEMA_PRODUCT_ID = "SCHEMA_PRODUCT_ID";
    public static final String MINIMUM_LIMIT = "MINIMUM_LIMIT";
    public static final String MAXIMUM_LIMIT = "MAXIMUM_LIMIT";
    public static final String PRODUCT_AMOUNT = "PRODUCT_AMOUNT";
    public static final String HAS_QUANTITY = "HAS_QUANTITY";
    public static final String PRODUCT_NAME_ENGLISH = "PRODUCT_NAME_ENGLISH";
    public static final String PRODUCT_NAME_ARABIC = "PRODUCT_NAME_ARABIC";
    public static final String PRODUCT_PRICE = "PRODUCT_PRICE";
    public static final String SALES_TAX = "SALES_TAX";
    public static final String PRODUCT_WEIGHT = "PRODUCT_WEIGHT";
    public static final String IS_POINT_ITEM = "IS_POINT_ITEM";
    public static final String IS_QUOTA_ITEM = "IS_QUOTA_ITEM";

    public RequestDetailModel() {
    }

    public RequestDetailModel(ResultSet res) {
        try {
            requestDetailId = res.getString(REQUEST_DETAIL_ID);
            requestId = res.getString(REQUEST_ID);
            schemaProductId = res.getString(SCHEMA_PRODUCT_ID);
            minimumLimit = res.getString(MINIMUM_LIMIT);
            maximumLimit = res.getString(MAXIMUM_LIMIT);
            productAmount = res.getString(PRODUCT_AMOUNT);
            hasQuantity = res.getString(HAS_QUANTITY);
            productNameEnglish = res.getString(PRODUCT_NAME_ENGLISH);
            productNameArabic = res.getString(PRODUCT_NAME_ARABIC);
            productPrice = res.getString(PRODUCT_PRICE);
            salesTax = res.getString(SALES_TAX);
            productWeight = res.getString(PRODUCT_WEIGHT);
            isPointItem = res.getString(IS_POINT_ITEM);
            isQuotaItem = res.getString(IS_QUOTA_ITEM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRequestDetailId() {
        return requestDetailId;
    }

    public void setRequestDetailId(String newRequestDetailId) {
        requestDetailId = newRequestDetailId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String newRequestId) {
        requestId = newRequestId;
    }

    public String getSchemaProductId() {
        return schemaProductId;
    }

    public void setSchemaProductId(String newSchemaProductId) {
        schemaProductId = newSchemaProductId;
    }

    public String getMinimumLimit() {
        return minimumLimit;
    }

    public void setMinimumLimit(String newMinimumLimit) {
        minimumLimit = newMinimumLimit;
    }

    public String getMaximumLimit() {
        return maximumLimit;
    }

    public void setMaximumLimit(String newMaximumLimit) {
        maximumLimit = newMaximumLimit;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String newProductAmount) {
        productAmount = newProductAmount;
    }

    public String getHasQuantity() {
        return hasQuantity;
    }

    public void setHasQuantity(String newHasQuantity) {
        hasQuantity = newHasQuantity;
    }

    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    public void setProductNameEnglish(String newProductNameEnglish) {
        productNameEnglish = newProductNameEnglish;
    }

    public String getProductNameArabic() {
        return productNameArabic;
    }

    public void setProductNameArabic(String newProductNameArabic) {
        productNameArabic = newProductNameArabic;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String newProductPrice) {
        productPrice = newProductPrice;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(String newSalesTax) {
        salesTax = newSalesTax;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String newProductWeight) {
        productWeight = newProductWeight;
    }

    public String getIsPointItem() {
        return isPointItem;
    }

    public void setIsPointItem(String newIsPointItem) {
        isPointItem = newIsPointItem;
    }

    public String getIsQuotaItem() {
        return isQuotaItem;
    }

    public void setIsQuotaItem(String newIsQuotaItem) {
        isQuotaItem = newIsQuotaItem;
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