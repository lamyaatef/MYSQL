package com.mobinil.sds.core.system.sop.equations.dao;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.sop.*;
import com.mobinil.sds.core.system.sop.equations.model.*;

public class EquationDAO 
{
  public EquationDAO()
  {
  }

  public static Vector getAllEquationStatus(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_STATUS order by EQUATION_STATUS_ID ");     
     while(res.next())
     {
        sopVec.add(new EquationStatusModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static Vector getAllEquations(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_SOP_EQUATION order by EQUATION_STATUS_ID,EQUATION_TITLE ");     
     while(res.next())
     {
        sopVec.add(new EquationModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static Vector getActiveEquations(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from VW_SOP_EQUATION where EQUATION_STATUS_ID = 1 order by EQUATION_TITLE ");     
     while(res.next())
     {
        sopVec.add(new EquationModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static String executeQueryString(Connection con,String strQuery)
  {
    String returnValue = "";
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery(strQuery);     
     while(res.next())
     {
        returnValue = res.getString(1);
     }
     res.close();
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return returnValue; 
  }
  
  public static Vector getEquationsByFilter(Connection con,String equationId)
  {
    Vector sopVec = new Vector();
    boolean andFlag = false;
    try
    {
     Statement stat = con.createStatement();
     String sqlQuery = "select * from VW_SOP_EQUATION ";
     if(equationId.compareTo("")!=0)
     {
        if(!andFlag){sqlQuery += " where ";}
        else{sqlQuery += " and ";}
        sqlQuery += " EQUATION_ID = "+equationId+" ";
        andFlag = true;        
     }
     sqlQuery += " order by EQUATION_STATUS_ID,EQUATION_TITLE ";
     ResultSet res= stat.executeQuery(sqlQuery);     
     while(res.next())
     {
        sopVec.add(new EquationModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static Vector getEquationOperators(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_OPERATOR order by EQUATION_OPERATOR_ID ");     
     while(res.next())
     {
        sopVec.add(new EquationOperatorModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static Vector getEquationObjects(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_OBJECT_TYPE order by EQUATION_OBJECT_TYPE_ID ");     
     while(res.next())
     {
        sopVec.add(new EquationObjectTypeModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static Vector getEquationTerms(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_TERM order by EQUATION_TERM_ID ");     
     while(res.next())
     {
        sopVec.add(new EquationTermModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static EquationTermModel getEquationTermByTermId(Connection con,String termId)
  {
    EquationTermModel equationTermModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_TERM where EQUATION_TERM_ID = "+termId+" order by EQUATION_TERM_ID ");     
     while(res.next())
     {
        equationTermModel = new EquationTermModel(res);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return equationTermModel; 
  }

  public static Vector getEquationDetails(Connection con,String equationId)
  {
    Vector equationElements = new Vector();
    EquationElementModel equationElementModel = null;
    try
    {
     Statement stat = con.createStatement();
     String sqlQuery = "select * from SOP_EQUATION_DETAIL, SOP_EQUATION_OPERATOR , SOP_EQUATION_TERM "+
                        " where SOP_EQUATION_DETAIL.OBJECT_ID = SOP_EQUATION_OPERATOR.EQUATION_OPERATOR_ID (+) "+          
                        " and SOP_EQUATION_DETAIL.OBJECT_ID = SOP_EQUATION_TERM.EQUATION_TERM_ID(+) "+
                        " and equation_id  = "+equationId+" "+
                        " order by EQUATION_DETAIL_ID";
     
     ResultSet res= stat.executeQuery(sqlQuery);     
     while(res.next())
     {
        equationElementModel = new EquationElementModel();
        
        String objectTypeId = res.getString("OBJECT_TYPE_ID");
        int intObjectTypeId = Integer.parseInt(objectTypeId);
        equationElementModel.setEquationElementType(intObjectTypeId);
        String value ="";
        String valueId = "";
        if (objectTypeId.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_TERM)==0 )
        {
            value = res.getString("EQUATION_TERM_NAME");
            valueId = res.getString("EQUATION_TERM_ID");
        }
        else if (objectTypeId.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_FACTOR)==0 )
        {
          value = res.getString("OBJECT_ID");
          valueId = value;
        }
        else if (objectTypeId.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_OPERATOR)==0 )
        {
          value = res.getString("EQUATION_OPERATOR"); 
          valueId = res.getString("EQUATION_OPERATOR_ID"); 
        }
        equationElementModel.setEquationElementValue(value);
        equationElementModel.setEquationElementValueId(valueId);
        equationElements.add(equationElementModel);           
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return equationElements; 
  }

  public static int insertEquation(Connection con,Long equationId,String equationName,String equationDescription,int equationStatusId)
  {
  int returnValue = 0;
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_EQUATION (EQUATION_ID,EQUATION_TITLE,EQUATION_DESCRIPTION,EQUATION_STATUS_ID) "+
                     " values("+equationId+",'"+equationName+"','"+equationDescription+"',"+equationStatusId+")";

     //Utility.logger.debug(strSql);  
     returnValue = stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return returnValue;
  }

  public static int updateEquation(Connection con,String equationId,String equationName,String equationDescription)
  {
     int returnValue = 0;
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update SOP_EQUATION set EQUATION_TITLE = '"+equationName+"' , EQUATION_DESCRIPTION = '"+equationDescription+"' "+
                     " where EQUATION_ID = "+equationId+" ";

     //Utility.logger.debug(strSql);  
     returnValue = stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
    return returnValue;
  }

  public static void updateEquationStatus(Connection con,String equationId,String equationStatusId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "update SOP_EQUATION set EQUATION_STATUS_ID = "+equationStatusId+" "+
                     " where EQUATION_ID = "+equationId+" ";

     //Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }
  
  public static void insertEquationDetail(Connection con,Long equationDetailId,String equationId,String elementValue,String elementType)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_EQUATION_DETAIl (EQUATION_DETAIL_ID,EQUATION_ID,OBJECT_ID,OBJECT_TYPE_ID) "+
                     " values("+equationDetailId+","+equationId+","+elementValue+","+elementType+")";

     //Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void deleteEquationDetail(Connection con,String strEquationId)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "delete from SOP_EQUATION_DETAIl where EQUATION_ID = "+strEquationId+" ";

     //Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }


  public static void deleteEquationProductStock(Connection con)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "delete from SOP_EQUATION_PRODUCT_STOCK ";

     //Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static void insertEquationProductStock(Connection con,Long equationProductStockId,String equationId,String productCode)
  {
    try
    {
     Statement stat = con.createStatement();

     String strSql = "insert into SOP_EQUATION_PRODUCT_STOCK (EQUATION_PRODUCT_STOCK_ID,EQUATION_ID,PRODUCT_CODE) "+
                     " values("+equationProductStockId+","+equationId+",'"+productCode+"')";

     //Utility.logger.debug(strSql);  
     stat.executeUpdate(strSql);
     
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }
  }

  public static Vector getEquationProductStock(Connection con,String equationId)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_PRODUCT_STOCK where EQUATION_ID = "+equationId+" order by PRODUCT_CODE ");     
     while(res.next())
     {
        sopVec.add(new EquationProductStockModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static Vector getAllEquationProductStock(Connection con)
  {
    Vector sopVec = new Vector();
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_PRODUCT_STOCK order by PRODUCT_CODE ");     
     while(res.next())
     {
        sopVec.add(new EquationProductStockModel(res));
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return sopVec; 
  }

  public static EquationProductStockModel getEquationProductStockByProductCoed(Connection con,String productCode)
  {
    EquationProductStockModel equationProductStockModel = null;
    try
    {
     Statement stat = con.createStatement();
     ResultSet res= stat.executeQuery("select * from SOP_EQUATION_PRODUCT_STOCK where PRODUCT_CODE = '"+productCode+"' ");     
     while(res.next())
     {
        equationProductStockModel = new EquationProductStockModel(res);
     }
     stat.close();
    }
    catch(Exception e)
    {
    e.printStackTrace();
    }

    return equationProductStockModel; 
  }

  public static void updateDcmProductRequestLimitsByEquation(Connection con,String productCode,Vector equationDetails,HashMap dcmQuota,int totalQuotas,String productActiveAmount)
  {
      int maximumLimitForAllDcm = 0;
      for(int q=0; q<dcmQuota.size(); q++)
      {
        String strDcmId = (String)dcmQuota.keySet().toArray()[q];
        String strDcmQuota = (String)dcmQuota.get(strDcmId);

        String equation = "";
        for(int j=0;j<equationDetails.size();j++)
        {
          EquationElementModel equationElementModel = (EquationElementModel)equationDetails.get(j);
          int elementType = equationElementModel.getEquationElementType();
          String elementValue = equationElementModel.getEquationElementValue();
          String termId = equationElementModel.getEquationElementValueId();
          String strElementType = elementType+"";
          if(strElementType.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_TERM)==0)
          {
            EquationTermModel equationTermModel = EquationDAO.getEquationTermByTermId(con,termId); 
            String equationTermName = equationTermModel.getEquationTermName();  
            if(equationTermName.compareTo("DCM_QUOTA")==0)
            {
              if(strDcmQuota.compareTo("")==0)
              {
                strDcmQuota = "0";
              }
              equation += strDcmQuota;
            }
            else if(equationTermName.compareTo("ACTIVE_AMOUNT")==0)
            {
              equation += productActiveAmount;
            }
            else if(equationTermName.compareTo("DCM_TOTAL_QUOTAS")==0)
            {
              equation += totalQuotas;
            }
          }
          else if(strElementType.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_FACTOR)==0)
          {
            equation += elementValue;
          }
          else if(strElementType.compareTo(SOPInterfaceKey.CONST_EQUATION_OBJECT_TYPE_OPERATOR)==0)
          {
            equation += elementValue;
          }
        }
        try
        {
         Statement stat = con.createStatement();
         Statement stat2 = con.createStatement();
         Utility.logger.debug("Equation : "+equation);
         ResultSet res= stat.executeQuery("select ("+equation+") from dual");     
         if(res.next())
         {
            int maxLimit = res.getInt(1);
            maximumLimitForAllDcm += maxLimit;
            String strSql = "update SOP_PRODUCT_REQUEST_LIMIT set IS_MANUAL = 0 , MAXIMUM_LIMIT = "+maxLimit+" "+
                            " where DCM_ID = "+strDcmId+" and PRODUCT_CODE = '"+productCode+"' ";
            stat2.executeUpdate(strSql);                
         }
         stat2.close();
         res.close();
         stat.close();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }

      try
      {
        Statement stat3 = con.createStatement();
        String strQuery = "update SOP_PRODUCT_REQUEST_LIMIT "+
                         " set MAXIMUM_LIMIT = MAXIMUM_LIMIT - "+maximumLimitForAllDcm+" "+
                         " where PRODUCT_CODE = '"+productCode+"' "+
                         " and IS_MANUAL = "+SOPInterfaceKey.CONST_PRODUCT_LIMIT_IS_MANUAL+" "; 
        stat3.executeUpdate(strQuery);
        stat3.close();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
  }
}