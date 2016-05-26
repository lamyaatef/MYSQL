package com.mobinil.sds.core.system.commission.factor.model;
import com.mobinil.sds.core.system.Model;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class FactorModel  extends Model
{
  int commissionFactorID = 0;
  String commissionFactorName = "";
  double commissionFactorValue = 0;
  int commissionItemID = 0;

  String commissionItemName = "";
  int commissionID = 0;
  int commissionItemAmount =0;
  int commissionDCMID = 0;

  String commissionDCMName = "";
  String commissionDCMCode ="";
  String commissionName = "";
  String commissionType = "";
  String commissionCategory = "";
  
  public int getCommissionFactorID()
  {
  	return commissionFactorID;
  }
  public void setCommissionFactorID(int newCommissionFactorID)
  {
  	commissionFactorID = newCommissionFactorID;
  }
  
  public String getCommissionFactorName()
  {
  	return commissionFactorName;
  }
  public void setCommissionFactorName(String newCommissionFactorName)
  {
  	commissionFactorName = newCommissionFactorName;
  }
  
  public double getCommissionFactorValue()
  {
  	return commissionFactorValue;
  }
  public void setCommissionFactorValue(double newCommissionFactorValue)
  {
  	commissionFactorValue = newCommissionFactorValue;
  }
  public int getCommissionItemID()
  {
  	return commissionItemID;
  }
  public void setCommissionItemID(int newCommissionItemID)
  {
  	commissionItemID = newCommissionItemID;
  }  


  public String getCommissionItemName()
  {
  	return commissionItemName;
  }
  public void setCommissionItemName(String newCommissionItemName)
  {
  	commissionItemName = newCommissionItemName;
  }

  public int getCommissionID()
  {
  	return commissionID;
  }
  public void setCommissionID(int newCommissionID)
  {
  	commissionID = newCommissionID;
  }

  public int getCommissionItemAmount()
  {
  	return commissionItemAmount;
  }
  public void setCommissionItemAmount(int newCommissionItemAmount)
  {
  	commissionItemAmount = newCommissionItemAmount;
  }

  public int getCommissionDCMID()
  {
    return commissionDCMID;
  }
  public void setCommissionDCMID(int newCommissionDCMID)
  {
    commissionDCMID = newCommissionDCMID;
  } 
  
  public String getCommissionDCMName()
  {
  	return commissionDCMName;
  }
  public void setCommissionDCMName(String newCommissionDCMName)
  {
  	commissionDCMName = newCommissionDCMName;
  }

  public String getCommissionDCMCode()
  {
  	return commissionDCMCode;
  }
  public void setCommissionDCMCode(String newCommissionDCMCode)
  {
  	commissionDCMCode = newCommissionDCMCode;
  }

  public String getCommissionName()
  {
  	return commissionName;
  }
  public void setCommissionName(String newCommissionName)
  {
  	commissionName = newCommissionName;
  }

  public String getCommissionType()
  {
  	return commissionType;
  }
  public void setCommissionType(String newCommissionType)
  {
  	commissionType = newCommissionType;
  }

  public String getCommissionCategory()
  {
  	return commissionCategory;
  }
  public void setCommissionCategory(String newCommissionCategory)
  {
  	commissionCategory = newCommissionCategory;
  }

  public FactorModel(ResultSet rs)throws Exception
  {
    setCommissionFactorID(rs.getInt("COMMISSION_FACTOR_ID"));
    setCommissionFactorName(rs.getString("COMMISSION_FACTOR_NAME"));
    setCommissionFactorValue(rs.getDouble("COMMISSION_FACTOR_VALUE"));
    setCommissionItemID(rs.getInt("COMMISSION_ITEM_ID"));
    setCommissionItemName(rs.getString("COMMISSION_ITEM_NAME"));
    setCommissionID(rs.getInt("COMMISSION_DETAIL_ID"));
    setCommissionItemAmount(rs.getInt("COMMISSION_ITEM_AMOUNT"));
    setCommissionDCMID(rs.getInt("DCM_ID"));
  }
  
  public FactorModel()
  {
  }

    @Override
    public void fillInstance(ResultSet rs) {
        try {
            setCommissionFactorID(rs.getInt("COMMISSION_FACTOR_ID"));
            setCommissionFactorName(rs.getString("COMMISSION_FACTOR_NAME"));
    setCommissionFactorValue(rs.getDouble("COMMISSION_FACTOR_VALUE"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
    }
}