package com.mobinil.sds.core.system.sop.equations.model;
import java.sql.*;
import java.io.*;

public class EquationProductStockModel implements Serializable
{
  String equationProductStockId;
  String equationId;
  String productCode;

  public static final String EQUATION_PRODUCT_STOCK_ID = "EQUATION_PRODUCT_STOCK_ID";
  public static final String EQUATION_ID = "EQUATION_ID";
  public static final String PRODUCT_CODE = "PRODUCT_CODE";
  
  public EquationProductStockModel()
  {
  }

  public EquationProductStockModel(ResultSet res)
  {
    try
    {
      equationProductStockId = res.getString(EQUATION_PRODUCT_STOCK_ID);
      equationId = res.getString(EQUATION_ID);
      productCode = res.getString(PRODUCT_CODE); 
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }    
  }


	public String getEquationProductStockId()
	{
	return equationProductStockId;
	}
	public void setEquationProductStockId(String newEquationProductStockId)
	{
	equationProductStockId = newEquationProductStockId;
	}
	
	
	public String getEquationId()
	{
	return equationId;
	}
	public void setEquationId(String newEquationId)
	{
	equationId = newEquationId;
	}
	
	
	public String getProductCod()
	{
	return productCode;
	}
	public void setProductCode(String newProductCode)
	{
	productCode = newProductCode;
	}  
}