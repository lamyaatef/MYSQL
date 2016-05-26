package com.mobinil.sds.core.system.sa.product.dao;
import java.io.*;
import java.sql.*;

import com.mobinil.sds.core.system.sa.product.model.*;
import com.mobinil.sds.core.utilities.*;
import java.util.*;


public class ProductDAO 
{
  private ProductDAO()
  {
  }

  public static Vector getAllProducts()
  {
    Vector productsVector = new Vector();
    try
    {
      Connection con = Utility.getConnection();
      Statement stat = con.createStatement();
      String sql = "select * from vw_gen_product ";
      ResultSet res = stat.executeQuery(sql);

      while(res.next())
      {
        ProductModel product = new ProductModel(res);
        productsVector.add(product);
      }
      stat.close();
      Utility.closeConnection(con);      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return productsVector;
  }
}