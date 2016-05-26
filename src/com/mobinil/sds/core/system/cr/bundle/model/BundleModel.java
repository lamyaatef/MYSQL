package com.mobinil.sds.core.system.cr.bundle.model;

import java.sql.*;
import java.io.*;

public class BundleModel implements Serializable
{
  String productTypeId;
  String productTypeName;
  String promotionTypeId;
  String promotionTypeName;
  String bunComponentId;
  String bunComponentName;

  public static final String PRODUCT_TYPE_ID = "PRODUCT_TYPE_ID";
  public static final String PRODUCT_TYPE_NAME = "PRODUCT_TYPE_NAME";
  public static final String PROMOTION_TYPE_ID = "PROMOTION_TYPE_ID";
  public static final String PROMOTION_TYPE_NAME = "PROMOTION_TYPE_NAME";
  public static final String BUN_COMPONENTS_ID = "BUN_COMPONENTS_ID";
  public static final String BUN_COMPONENTS_NAME = "BUN_COMPONENTS_NAME";

  public BundleModel()
  {
  }

  public String getProductTypeId()
  {
    return productTypeId;
  }
  public void setProductTypeId(String newProductTypeId)
  {
    productTypeId = newProductTypeId;
  }
////////////////////////////////////////////////////////////////
  public String getProductTypeName()
  {
    return productTypeName;
  }
  public void setProductTypeName(String newProductTypeName)
  {
    productTypeName = newProductTypeName;
  }
////////////////////////////////////////////////////////////////
  public String getPromotionTypeId()
  {
    return promotionTypeId;
  }
  public void setPromotionTypeId(String newPromotionTypeId)
  {
    promotionTypeId = newPromotionTypeId;
  }
////////////////////////////////////////////////////////////////
  public String getPromotionTypeName()
  {
    return promotionTypeName;
  }
  public void setPromotionTypeName(String newPromotionTypeName)
  {
    promotionTypeName = newPromotionTypeName;
  }
///////////////////////////////////////////////////////////////
  public String getBunComponentId()
  {
    return bunComponentId;
  }
  public void setBunComponentId(String newBunComponentId)
  {
    bunComponentId = newBunComponentId;
  }
//////////////////////////////////////////////////////////////
  public String getBunComponentName()
  {
    return bunComponentName;
  }
  public void setBunComponentName(String newBunComponentName)
  {
    bunComponentName = newBunComponentName;
  }
//////////////////////////////////////////////////////////////
}