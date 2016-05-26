package com.mobinil.sds.core.system.authenticationResult.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mobinil.sds.core.system.Model;



public class AuthSearchCategryModel extends Model
{
   private String cat_id, cat_name, cat_description;

   public String getCat_id()
   {
      return cat_id;
   }

   public void setCat_id(String catId)
   {
      cat_id = catId;
   }

   public String getCat_name()
   {
      return cat_name;
   }

   public void setCat_name(String catName)
   {
      cat_name = catName;
   }

   public String getCat_description()
   {
      return cat_description;
   }

   public void setCat_description(String catDescription)
   {
      cat_description = catDescription;
   }

   @Override
   public void fillInstance(ResultSet res)
   {
      
      try
      {
         cat_description =  res.getString ( "cat_description" );
         cat_id = res.getString ( "cat_id" );
         cat_name = res.getString ( "cat_name" );
      }
      catch ( SQLException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }
}
