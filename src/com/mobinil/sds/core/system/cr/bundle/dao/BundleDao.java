package com.mobinil.sds.core.system.cr.bundle.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sa.*;
import com.mobinil.sds.core.system.cr.bundle.model.*;
import com.mobinil.sds.core.system.dataMigration.model.PaymentLevelModel;
import com.mobinil.sds.core.system.nomadFile.model.NomadFileModel;
import com.mobinil.sds.core.system.request.model.ChannelModel;
import com.mobinil.sds.core.system.sa.product.model.ProductModel;



public class BundleDao 
{
  public BundleDao()
  {
  }

  public static Vector getAllBundleProductType (Connection con)
  {
    Vector productTypeVec = new Vector();
    BundleModel bundleModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from BUN_PRODUCT_TYPE";
      //Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while (res.next())
      {
        bundleModel = new BundleModel();
        bundleModel.setProductTypeId(res.getString(bundleModel.PRODUCT_TYPE_ID));
        bundleModel.setProductTypeName(res.getString(bundleModel.PRODUCT_TYPE_NAME));
        productTypeVec.add(bundleModel);
      }
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
    return productTypeVec;
  }
///////////////////////////////////////////////////////////////////////////////////
  public static Vector getPaymentLevels(Connection con)
  {
      Statement st = null;
      PaymentLevelModel payLevelModel = new PaymentLevelModel();
      Vector payLevelVec = new Vector();
      try{
          st = con.createStatement();
          String sql="select * from gen_dcm_payment_level order by dcm_payment_level_id asc";
          ResultSet rs= st.executeQuery(sql);
          while (rs.next())
          {
              payLevelModel = new PaymentLevelModel();
              payLevelModel.setPaymentLevelId((String)rs.getString("dcm_payment_level_id"));
              payLevelModel.setPaymentLevelName((String)rs.getString("dcm_payment_level_name"));
              payLevelVec.addElement((PaymentLevelModel)payLevelModel);                  
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return payLevelVec;
  }
  //////////////////////////////////////////////Nomad////////////////////////////
  /*public static int insertNewUser(Connection con, String userId) {
        int check = 0;
        try {
            Statement stat = con.createStatement();
            String strSql = "select * from gen_person where person_id = '" + userId + "'";
            ResultSet res = stat.executeQuery(strSql);
            if (res.next()) {
                Statement stat2 = con.createStatement();
                String strSql2 = "select * from AUTH_RES_USER_LABEL WHERE user_id = '" + userId + "'";
                ResultSet res2 = stat2.executeQuery(strSql2);
                if (res2.next()) {
                    check = 1;
                } else {
                    Statement stat3 = con.createStatement();
                    String strSql3 = "insert into AUTH_RES_USER_LABEL (USER_ID) VALUES ('" + userId + "')";
                    stat3.executeUpdate(strSql3);
                }
            } else {
                check = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
  public static void insertNomadFile(Connection con)
  {
      System.out.println("insert FILE");
      try {
            Statement stat = con.createStatement();
            String strSql = "insert into GEN_DCM_NOMAD_FILE values('file id','user id','create date','upload date','records','min','max','label','status')";



           System.out.println("INSERT files QUERY ISSSSSSSS" + strSql);
           stat.executeUpdate(strSql);
           
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
  }  
  public static Vector getallNomadfiles(Connection con, String userId) {
        Vector vec = new Vector();
        try {
            Statement stat = con.createStatement();
            String strSql = "select gf.GEN_DCM_NOMAD_FILE_ID, gf.USER_ID, gf.FILE_CREATION_DATE, gf.FILE_UPLOAD_DATE, gf.TOTAL_NUMBER_OF_RECORDS, gl.NOMAD_LABEL_NAME from  GEN_DCM_NOMAD_FILE gf, GEN_DCM_NOMAD_LABEL gl where gf.user_id = '"+userId+"' and gf.label_id = gl.NOMAD_LABEL_ID and gf.status <> 'Deleted'";



            System.out.println("GET all files QUERY ISSSSSSSS" + strSql);
            ResultSet res = stat.executeQuery(strSql);
            while (res.next()) {
                vec.add(new NomadFileModel(res));
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }*/
  /////////////////////////////////////Nomad///////////////////////////////
  public static PaymentLevelModel getPaymentLevelById(Connection con,String paymentLevelById)
  {
      Statement st = null;
      PaymentLevelModel payLevelModel = new PaymentLevelModel();
      try{
          st = con.createStatement();
          String sql="select * from gen_dcm_payment_level where dcm_payment_level_id = '"+paymentLevelById+"'";
          ResultSet rs= st.executeQuery(sql);
          while (rs.next())
          {
              payLevelModel = new PaymentLevelModel(rs);
              //payLevelModel.setPaymentLevelId((String)rs.getString("dcm_payment_level_id"));
              //payLevelModel.setPaymentLevelName((String)rs.getString("dcm_payment_level_name"));
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return payLevelModel;
  }
  
  
  public static Vector getChannels(Connection con)
  {
      Statement st = null;
      ChannelModel channelModel = new ChannelModel();
      Vector channelVec = new Vector();
      try{
          st = con.createStatement();
          String sql="select * from gen_channel order by channel_id asc";
          ResultSet rs= st.executeQuery(sql);
          while (rs.next())
          {
              channelModel = new ChannelModel();
              channelModel.setChannelId(Integer.parseInt((String)rs.getString("channel_id")));
              channelModel.setChannelName((String)rs.getString("channel_name"));
              channelVec.add((ChannelModel)channelModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return channelVec;
  }
  
  
  public static ChannelModel getChannelById(Connection con, String channelId)
  {
      Statement st = null;
      ChannelModel channelModel = new ChannelModel();
      try{
          st = con.createStatement();
          String sql="select * from gen_channel where channel_id = '"+channelId+"'";
          ResultSet rs= st.executeQuery(sql);
          if (rs.next())
          {
              channelModel = new ChannelModel();
              channelModel.setChannelId(Integer.parseInt((String)rs.getString("channel_id")));
              channelModel.setChannelName((String)rs.getString("channel_name"));
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return channelModel;
  }
  
  
  

  private static String getLatestProductId(Connection con)
  {
      Statement st = null;
      ResultSet rs = null;
      String productId = "";
      try{
          st = con.createStatement();
          String sql="select max(product_id) from gen_product";
          rs = st.executeQuery(sql);
          if (rs.next())
          {
              productId = rs.getString("max(product_id)");
              
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productId;
  }
  
  
  
  private static String getLatestChannelId(Connection con)
  {
      Statement st = null;
      ResultSet rs = null;
      String channelId = "";
      try{
          st = con.createStatement();
          String sql="select max(channel_id) from gen_channel";
          rs = st.executeQuery(sql);
          if (rs.next())
          {
              channelId = rs.getString("max(channel_id)");
              
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return channelId;
  }
  
  
  private static String getLatestPaymentLevelId(Connection con)
  {
      Statement st = null;
      ResultSet rs = null;
      String paymentLevelId = "";
      try{
          st = con.createStatement();
          String sql="select max(dcm_payment_level_id) from gen_dcm_payment_level";
          rs = st.executeQuery(sql);
          if (rs.next())
          {
              paymentLevelId = rs.getString("max(dcm_payment_level_id)");
              
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return paymentLevelId;
  }
  
  public static Vector updateChannel(Connection con, ChannelModel channelModel)
  {
      System.out.println("update channel in DB");
      Statement st = null;
      ResultSet rs = null;
      Vector channelVec = new Vector();
      try{
          st = con.createStatement();
          String sql="update gen_channel set channel_name = '"+channelModel.getChannelName()+"' where channel_id = '"+channelModel.getChannelId()+"' ";
          st.executeUpdate(sql);
          String sqlList ="select * from gen_channel order by channel_id asc";
          rs = st.executeQuery(sqlList);
          while(rs.next())
          {
              channelModel = new ChannelModel();
              channelModel.setChannelId(Integer.parseInt((String)rs.getString("channel_id")));
              channelModel.setChannelName((String)rs.getString("channel_name"));
              channelVec.add((ChannelModel)channelModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return channelVec;
  }
  
    public static Vector updatePaymentLevel(Connection con, PaymentLevelModel paymentLevelModel)
  {
      System.out.println("update payment level in DB");
      Statement st = null;
      ResultSet rs = null;
      Vector paymentLevelVec = new Vector();
      try{
          st = con.createStatement();
          String sql="update gen_dcm_payment_level set dcm_payment_level_name = '"+paymentLevelModel.getPaymentLevelName()+"' where dcm_payment_level_id = '"+paymentLevelModel.getPaymentLevelId()+"' ";
          st.executeUpdate(sql);
          String sqlList ="select * from gen_dcm_payment_level order by dcm_payment_level_id asc";
          rs = st.executeQuery(sqlList);
          while(rs.next())
          {
                paymentLevelModel = new PaymentLevelModel(rs);
                paymentLevelVec.add((PaymentLevelModel)paymentLevelModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return paymentLevelVec;
  }
  
  public static Vector updateProduct(Connection con, ProductModel productModel)
  {
      System.out.println("update product in DB");
      Statement st = null;
      ResultSet rs = null;
      Vector productVec = new Vector();
      try{
          st = con.createStatement();
          String sql="update gen_product set product_name = '"+productModel.getProductName()+"', product_desc = '"+productModel.getProductDesc()+"', product_category_id='"+productModel.getProductCategoryId()+"' where product_id = '"+productModel.getProductId()+"' ";
          st.executeUpdate(sql);
          String sql2="select gp.*,gpc.product_category_name from gen_product gp, gen_product_category gpc where gp.product_category_id = gpc.product_category_id order by gp.product_id asc";
          rs = st.executeQuery(sql2);
          while (rs.next())
          {
              productModel = new ProductModel(rs);
              productVec.add((ProductModel)productModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productVec;
  }
  
  public static Vector addNewChannel(Connection con, String channelName)
  {
      System.out.println("add new channel in DB");
      Statement st = null;
      ChannelModel channelModel = new ChannelModel();
      Vector channelVec = new Vector();
      try{
          st = con.createStatement();
          String sql="insert into gen_channel values ((select max(channel_id)+1 from gen_channel),'"+channelName+"')";
          st.executeUpdate(sql);
          String sqlList ="select * from gen_channel order by channel_id asc";
          ResultSet rs = st.executeQuery(sqlList);
          //String channelId = getLatestChannelId(con);
          while(rs.next())
          {
              channelModel = new ChannelModel();
              channelModel.setChannelId(Integer.parseInt((String)rs.getString("channel_id")));
              channelModel.setChannelName((String)rs.getString("channel_name"));
              channelVec.add((ChannelModel)channelModel);
          }
          

          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return channelVec;
  }
  
  
  public static Vector addNewPaymentLevel(Connection con, String paymentLevelName)
  {
      System.out.println("add new payment level in DB");
      Statement st = null;
      PaymentLevelModel paymentLevelModel = new PaymentLevelModel();
      Vector paymentLevelVec = new Vector();
      try{
          st = con.createStatement();
          String sql="insert into gen_dcm_payment_level values ((select max(dcm_payment_level_id)+1 from gen_dcm_payment_level),'"+paymentLevelName+"')";
          st.executeUpdate(sql);
          String sqlList ="select * from gen_dcm_payment_level order by dcm_payment_level_id asc";
          ResultSet rs = st.executeQuery(sqlList);
          //String paymentLevelId = getLatestPaymentLevelId(con);
          while(rs.next())
          {
                paymentLevelModel = new PaymentLevelModel(rs);
                paymentLevelVec.add((PaymentLevelModel)paymentLevelModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return paymentLevelVec;
  }
  
  
  
  
  public static Vector addNewProduct(Connection con, String productName, String productCatName, String productDesc)
  {
      System.out.println("add new product in DB");
      Statement st = null;
      ResultSet rs = null;
      String productCatId = getProductCategoryIdByName(con, productCatName);
      ProductModel productModel = new ProductModel();
      Vector productVec = new Vector();
      try{
          st = con.createStatement();
          String sql="insert into gen_product values ((select max(product_id)+1 from gen_product),'"+productName+"','"+productDesc+"','"+productCatId+"')";
          st.executeUpdate(sql);
          //String productId = getLatestProductId(con);
          //String sql2="select gp.*,gpc.product_category_name from gen_product gp, gen_product_category gpc where gp.product_category_id = gpc.product_category_id and gp.product_id = '"+productId+"'";
          String sql2="select gp.*,gpc.product_category_name from gen_product gp, gen_product_category gpc where gp.product_category_id = gpc.product_category_id order by gp.product_id asc";
          rs = st.executeQuery(sql2);
          while (rs.next())
          {
              productModel = new ProductModel(rs);
              productVec.add((ProductModel)productModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productVec;
  }
  
  
  public static ProductModel getProductById(Connection con, String productId)
  {
      Statement st = null;
      ProductModel productModel = new ProductModel();
      try{
          st = con.createStatement();
          String sql="select gp.*,gpc.product_category_name from gen_product gp, gen_product_category gpc where gp.product_category_id = gpc.product_category_id and gp.product_id = '"+productId+"'";
          ResultSet rs= st.executeQuery(sql);
          if (rs.next())
          {
              productModel = new ProductModel(rs);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productModel;
      
  }
  
  public static int getChannelIdByName(Connection con, String channelName)
  {
      Statement st = null;
      int channelId =0;
      try{
          st = con.createStatement();
          String sql="select channel_id from gen_channel where channel_name = '"+channelName+"' ";
          ResultSet rs= st.executeQuery(sql);
          if (rs.next())
          {
              channelId = Integer.parseInt(rs.getString("channel_id"));
              
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return channelId;
  }
  
  
    
  public static String getProductCategoryIdByName(Connection con, String productCatName)
  {
      Statement st = null;
      String productCatId = "";
      try{
          st = con.createStatement();
          String sql="select product_category_id from gen_product_category where product_category_name = '"+productCatName+"' ";
          ResultSet rs= st.executeQuery(sql);
          if (rs.next())
          {
              productCatId = rs.getString("product_category_id");
              
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productCatId;
  }
  

  public static String getProductCategoryByProductId(Connection con, String productId)
  {
      Statement st = null;
      String productCat = "";
      try{
          st = con.createStatement();
          String sql="select product_category_name from gen_product_category where product_category_id = (select product_category_id from gen_product where product_id='"+productId+"')";
          ResultSet rs= st.executeQuery(sql);
          if (rs.next())
          {
              productCat = rs.getString("product_category_name");
              
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productCat;
  }
  
  public static Vector getProductCategories(Connection con)
  {
      Statement st = null;
      ProductModel productModel = new ProductModel();
      Vector productVec = new Vector();
      try{
          st = con.createStatement();
          String sql="select * from gen_product_category";
          ResultSet rs= st.executeQuery(sql);
          while (rs.next())
          {
              productModel = new ProductModel();
              //productModel.setProductId(rs.getString("product_id"));
              //productModel.setProductName(rs.getString("product_name"));
              productModel.setProductCategoryId(rs.getString("product_category_id"));
              productModel.setProductCategoryName(rs.getString("product_category_name"));
              //productModel.setProductDesc(rs.getString("product_desc"));
              productVec.add((ProductModel)productModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productVec;
      
  }
  
  public static Vector getProducts(Connection con)
  {
      Statement st = null;
      ProductModel productModel = new ProductModel();
      Vector productVec = new Vector();
      try{
          st = con.createStatement();
          String sql="select gp.*,gpc.product_category_name from gen_product gp, gen_product_category gpc where gp.product_category_id = gpc.product_category_id order by gp.product_id asc";
          ResultSet rs= st.executeQuery(sql);
          while (rs.next())
          {
              productModel = new ProductModel(rs);
              /*productModel.setProductId(rs.getString("product_id"));
              productModel.setProductName(rs.getString("product_name"));
              productModel.setProductCategoryId(rs.getString("product_category_id"));
              productModel.setProductCategoryName(rs.getString("product_category_name"));
              productModel.setProductDesc(rs.getString("product_desc"));*/
              productVec.add((ProductModel)productModel);
          }
          st.close();
      }
      catch (Exception e){e.printStackTrace();}
      return productVec;
  }
  
  
  public static BundleModel selectProductType (Connection con,String productTypeId)
  {
    BundleModel bundleModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from BUN_PRODUCT_TYPE where PRODUCT_TYPE_ID ='"+productTypeId+"'";
      //Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        bundleModel = new BundleModel();
        bundleModel.setProductTypeId(res.getString(bundleModel.PRODUCT_TYPE_ID));
        bundleModel.setProductTypeName(res.getString(bundleModel.PRODUCT_TYPE_NAME));
      }
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return bundleModel;
  }

  public static void updateProductType(Connection con,String productTypeId,String productTypeName)throws Exception
  {
	  String strSql = "update BUN_PRODUCT_TYPE "+
      "SET PRODUCT_TYPE_NAME = '"+productTypeName+"'"+
      "WHERE PRODUCT_TYPE_ID = '"+productTypeId+"'"; 
	  DBUtil.executeSQL(strSql, con); 
     
  }

   public static void insertProductType(Connection con,String productTypeId,String ProductTypeName)
  {

	String strSql = "insert into BUN_PRODUCT_TYPE (PRODUCT_TYPE_ID,PRODUCT_TYPE_NAME)values('"+productTypeId+"','"+ProductTypeName+"')";
	DBUtil.executeSQL(strSql, con); 
  }

  public static Vector getAllBundlePromotionType (Connection con)
  {
    Vector promotionTypeVec = new Vector();
    BundleModel bundleModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from BUN_PROMOTION_TYPE";
      //Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while (res.next())
      {
        bundleModel = new BundleModel();
        bundleModel.setPromotionTypeId(res.getString(bundleModel.PROMOTION_TYPE_ID));
        bundleModel.setPromotionTypeName(res.getString(bundleModel.PROMOTION_TYPE_NAME));
        promotionTypeVec.add(bundleModel);
      }
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
    return promotionTypeVec;
  }

  public static BundleModel selectPromotionType (Connection con,String promotionTypeId)
  {
    BundleModel bundleModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from BUN_PROMOTION_TYPE where PROMOTION_TYPE_ID ='"+promotionTypeId+"'";
      //Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        bundleModel = new BundleModel();
        bundleModel.setPromotionTypeId(res.getString(bundleModel.PROMOTION_TYPE_ID));
        bundleModel.setPromotionTypeName(res.getString(bundleModel.PROMOTION_TYPE_NAME));
      }
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return bundleModel;
  }
public static void updatePromotionType(Connection con,String promotionTypeId,String promotionTypeName)throws Exception
  {	
	String strSql = "update BUN_PROMOTION_TYPE "+
    "SET PROMOTION_TYPE_NAME = '"+promotionTypeName+"'"+
    "WHERE PROMOTION_TYPE_ID = '"+promotionTypeId+"'";
	DBUtil.executeSQL(strSql, con); 
  }

   public static void insertPromotionType(Connection con,String promotionTypeId,String PromotionTypeName)
  {
	String strSql = "insert into BUN_PROMOTION_TYPE (PROMOTION_TYPE_ID,PROMOTION_TYPE_NAME)values('"+promotionTypeId+"','"+PromotionTypeName+"')";
	DBUtil.executeSQL(strSql, con);    
  }

  public static Vector getAllBundleComponent (Connection con)
  {
    Vector bundleComponentVec = new Vector();
    BundleModel bundleModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from BUN_COMPONENT";
      //Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while (res.next())
      {
        bundleModel = new BundleModel();
        bundleModel.setBunComponentId(res.getString(bundleModel.BUN_COMPONENTS_ID));
        bundleModel.setBunComponentName(res.getString(bundleModel.BUN_COMPONENTS_NAME));
        bundleComponentVec.add(bundleModel);
      }
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    } 
    return bundleComponentVec;
  }
  public static BundleModel selectBundleComponent (Connection con,String bunComponentId)
  {
    BundleModel bundleModel = null;
    try
    {
      Statement stat = con.createStatement();
      String strSql = "select * from BUN_COMPONENT where BUN_COMPONENTS_ID ='"+bunComponentId+"'";
      //Utility.logger.debug(strSql);
      ResultSet res = stat.executeQuery(strSql);
      while(res.next())
      {
        bundleModel = new BundleModel();
        bundleModel.setBunComponentId(res.getString(bundleModel.BUN_COMPONENTS_ID));
        bundleModel.setBunComponentName(res.getString(bundleModel.BUN_COMPONENTS_NAME));
      }
      stat.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return bundleModel;
  }
  public static void updateBundleComponent(Connection con,String bunComponentId,String bunComponentName)throws Exception
  {
	  String strSql = "update BUN_COMPONENT "+
      "SET BUN_COMPONENTS_NAME = '"+bunComponentName+"'"+
      "WHERE BUN_COMPONENTS_ID = '"+bunComponentId+"'";  
	  
	  DBUtil.executeSQL(strSql, con);
     
  }
  public static void insertBundleComponent(Connection con,String bunComponentId,String bunComponentName)
  {
	String strSql = "insert into BUN_COMPONENT (BUN_COMPONENTS_ID,BUN_COMPONENTS_NAME)values('"+bunComponentId+"','"+bunComponentName+"')";
	DBUtil.executeSQL(strSql, con);	
  }
}