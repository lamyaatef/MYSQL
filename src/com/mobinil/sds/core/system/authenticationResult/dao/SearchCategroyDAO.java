package com.mobinil.sds.core.system.authenticationResult.dao;

import java.sql.Connection;
import java.util.Vector;

import com.mobinil.sds.core.system.authenticationResult.model.AuthSearchCategryModel;
import com.mobinil.sds.core.utilities.DBUtil;

public class SearchCategroyDAO
{
   public static Vector<AuthSearchCategryModel> getAllSearchCategories(Connection con,String catId){
      
      Vector<AuthSearchCategryModel> vAllSearchCategories = new Vector<AuthSearchCategryModel>();
      
      
      String sAllSearchCategories = "select * from AUTH_SEARCH_CATEGORY";
      if (catId!=null&&catId.compareTo ( "" )!=0) sAllSearchCategories += " where CAT_ID='"+catId+"'";
      vAllSearchCategories = DBUtil.executeSqlQueryMultiValue(sAllSearchCategories,AuthSearchCategryModel.class,con);      
      return vAllSearchCategories;
   }
   
   public static void addNewSearchCategory (Connection con, String catName,String catDesc){
   String insertSearchCategorySQL = 
      "INSERT INTO AUTH_SEARCH_CATEGORY ( CAT_ID, CAT_NAME, CAT_DESCRIPTION ) VALUES (SEQ_AUTH_RES_SEARCH_CAT_ID.nextval, '"+catName+"', '"+catDesc+"')";
   DBUtil.executeSQL ( insertSearchCategorySQL,con );      
      
   }
   
   public static void deleteSearchCategory (Connection con, String catId){
      String deleteSearchCategorySQL = 
         "delete from AUTH_SEARCH_CATEGORY where  CAT_ID='"+catId+"'";
      DBUtil.executeSQL ( deleteSearchCategorySQL,con );     
         
      }
   
   public static void editSearchCategory (Connection con,String catId, String catName,String catDesc){
      String updateSearchCategorySQL = 
         "update AUTH_SEARCH_CATEGORY set CAT_NAME='"+catName+"', CAT_DESCRIPTION='"+catDesc+"' where  CAT_ID='"+catId+"'";
      DBUtil.executeSQL ( updateSearchCategorySQL,con );      
         
      }
   public static boolean checkExsitsCategory (Connection con,String catId){
      String checkSearchCategorySQL = 
          "SELECT * FROM AUTH_RES_FILE where CAT_ID = "+catId+ " and status  <> 'Deleted'";
      return DBUtil.executeSQLExistCheck ( checkSearchCategorySQL , con ) ;      
         
      }   
}
