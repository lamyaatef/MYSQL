package com.mobinil.sds.core.system.sop.franchise.dao;

import com.mobinil.sds.core.system.sop.franchise.model.*;
import com.mobinil.sds.web.interfaces.sop.SOPInterfaceKey;

import com.mobinil.sds.core.utilities.Utility;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import java.util.Vector;

public class FranchiseDAO {
    public FranchiseDAO() {
    }

    public static String LCS_TABLE = "stm_lcs_test";
    public static String PGW_TABLE = "stm_pgw_test";

    /**
     * @param con
     * @param model
     */
    public static void addFranchis(Connection con, FranchiseModel model)
    throws SQLException {
        Statement stat = null;
        ResultSet result = null;
        StringBuffer sql = new StringBuffer("insert into stm_franchise_current_state ");
        sql.append("(franchise_code, item_code, current_quantity, lcs_last_date, ");
        sql.append("lcs_quantity, pgw_quantity, current_date");
        if(model.getLastPGWDat() != null)
            sql.append(", pgw_last_date");
        sql.append(") values " + "('" );
        sql.append(model.getCode() + "','" + model.getItemCode() + "'," ); 
        sql.append(model.getItemQuantity() + ", to_date('" + model.getLastLCSDate());
        sql.append("','DD/MM/YYYY'), "+model.getLCSQuantity()+","+model.getPGWQuantity());
        if(model.getLastPGWDat() != null)
            sql.append(", to_date('" + model.getLastPGWDat()+"','DD/MM/YYYY')");
        sql.append(", to_date(sysdate,'dd/mm/yyyy') ");    
        sql.append(")");

        Utility.logger.debug(sql.toString());
            stat = con.createStatement();
            stat.execute(sql.toString());
            stat.close();
        
    }

    public static void addLCSInformation(Connection con, InformationModel model)
    throws SQLException
    {
        long id = Utility.getSequenceNextVal(con, "seq_stm_lcs").longValue();
        StringBuffer sql = new StringBuffer("insert into stm_lcs values(");
        sql.append(id + ","+model.getFranchiseCode()+",");
        sql.append(model.getItemCode()+",to_date('"+model.getDate()+"','dd/mm/yyyy'),");
        sql.append(model.getQuantity()+")");

        Statement stat = con.createStatement();
        stat.execute(sql.toString());
    }

    public static void addPGWInformation(Connection con, InformationModel model)
    throws SQLException
    {
        long id = Utility.getSequenceNextVal(con, "seq_stm_pgw").longValue();
        StringBuffer sql = new StringBuffer("insert into stm_pgw values(");
        sql.append(id + ","+model.getFranchiseCode()+",");
        sql.append(model.getItemCode()+",to_date('"+model.getDate()+"','dd/mm/yyyy'),");
        sql.append(model.getQuantity()+")");

        Statement stat = con.createStatement();
        stat.execute(sql.toString());
    }

    public static Vector searchLCSInformation(Connection con, InformationModel model,
                                            String startDate, String endDate)
    throws SQLException
    {
      boolean noSearch = true;
      StringBuffer sql = new StringBuffer("select lcs_id, franchise_code, ");
      sql.append("item_code, to_char(lcs_date,'dd/mm/yyyy'), lcs_quantity ,lcs_item_code,item_description ");
      sql.append("from stm_lcs where ");
      if(model.getFranchiseCode().compareTo("Any") != 0)
      {
        sql.append("franchise_code='"+model.getFranchiseCode()+"' and ");
        noSearch = false;
      }
      if(model.getItemCode().compareTo("Any")!=0)
      {
        sql.append("item_code='"+model.getItemCode()+"' and ");
        noSearch = false;
      }
      if(model.getSourceItemCode().compareTo("Any")!=0)
      {
        sql.append("lcs_item_code like '%"+model.getSourceItemCode()+"%' and ");
        noSearch = false;
      }
      if(model.getSourceItemDesc().compareTo("Any")!=0)
      {
        sql.append("item_description like '%"+model.getSourceItemDesc()+"%' and ");
        noSearch = false;
      }
      if(startDate.compareTo("Any")!= 0)  
      {
        sql.append("(lcs_date > to_date('"+startDate+"','dd/mm/yyyy') or ");
        sql.append("lcs_date like to_date('"+startDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(endDate.compareTo("Any")!= 0)  
      {
        sql.append("(lcs_date < to_date('"+endDate+"','dd/mm/yyyy') or ");
        sql.append("lcs_date like to_date('"+endDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(noSearch)
        sql.setLength(sql.length()-6);
      else  
        sql.setLength(sql.length()-4);

      Vector vector = new Vector();
      InformationModel info = null;
      Statement stat = con.createStatement();
      //Utility.logger.debug("SQL TEST: "+ sql.toString());
      ResultSet result = stat.executeQuery(sql.toString());
      while(result.next())
      {
        info = new InformationModel();
        info.setId(result.getInt("lcs_id"));
        info.setFranchiseCode(result.getString("franchise_code"));
        info.setItemCode(result.getString("item_code"));
        info.setDate(result.getString(4));
        info.setQuantity(result.getInt("lcs_quantity"));
        info.setSourceItemCode(result.getString("lcs_item_code"));
        info.setSourceItemDesc(result.getString("item_description"));
        vector.add(info);
      }
      result.close();
      stat.close();
      return vector;
    }

     public static Vector searchLCSInformationByRowNum(Connection con, int rowNum, InformationModel model,
                                            String startDate, String endDate)
    throws SQLException
    {
      boolean noSearch = true;
      StringBuffer sql = new StringBuffer("select * from (select ROWNUM AS row_num, lcs_id, franchise_code, ");
      sql.append("item_code, to_char(lcs_date,'dd/mm/yyyy'), lcs_quantity ,lcs_item_code,item_description ");
      sql.append("from stm_lcs where ");
      if(model.getFranchiseCode().compareTo("Any") != 0)
      {
        sql.append("franchise_code='"+model.getFranchiseCode()+"' and ");
        noSearch = false;
      }
      if(model.getItemCode().compareTo("Any")!=0)
      {
        sql.append("item_code='"+model.getItemCode()+"' and ");
        noSearch = false;
      }
      if(model.getSourceItemCode().compareTo("Any")!=0)
      {
        sql.append("lcs_item_code like '%"+model.getSourceItemCode()+"%' and ");
        noSearch = false;
      }
      if(model.getSourceItemDesc().compareTo("Any")!=0)
      {
        sql.append("item_description like '%"+model.getSourceItemDesc()+"%' and ");
        noSearch = false;
      }
      if(startDate.compareTo("Any")!= 0)  
      {
        sql.append("(lcs_date > to_date('"+startDate+"','dd/mm/yyyy') or ");
        sql.append("lcs_date like to_date('"+startDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(endDate.compareTo("Any")!= 0)  
      {
        sql.append("(lcs_date < to_date('"+endDate+"','dd/mm/yyyy') or ");
        sql.append("lcs_date like to_date('"+endDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(noSearch)
        sql.setLength(sql.length()-6);
      else  
        sql.setLength(sql.length()-4);
      sql.append(")where row_num > '"+rowNum+"'*49 and row_num <= ('"+rowNum+"'+1)*49");
      Vector vector = new Vector();
      InformationModel info = null;
      Statement stat = con.createStatement();
      //Utility.logger.debug("SQL TEST: "+ sql.toString());
      ResultSet result = stat.executeQuery(sql.toString());
      while(result.next())
      {
        info = new InformationModel();
        info.setId(result.getInt("lcs_id"));
        info.setFranchiseCode(result.getString("franchise_code"));
        info.setItemCode(result.getString("item_code"));
        info.setDate(result.getString(5));
        info.setQuantity(result.getInt("lcs_quantity"));
        info.setSourceItemCode(result.getString("lcs_item_code"));
        info.setSourceItemDesc(result.getString("item_description"));
        vector.add(info);
      }
      result.close();
      stat.close();
      return vector;
    }

    public static int countOfLCSState(Connection con, InformationModel model,
                                            String startDate, String endDate)
    throws SQLException
    {
      int count = 0;
      boolean noSearch = true;
      StringBuffer sql = new StringBuffer("select count(*)as count ");
      sql.append("from stm_lcs where ");
      if(model.getFranchiseCode().compareTo("Any") != 0)
      {
        sql.append("franchise_code='"+model.getFranchiseCode()+"' and ");
        noSearch = false;
      }
      if(model.getItemCode().compareTo("Any")!=0)
      {
        sql.append("item_code='"+model.getItemCode()+"' and ");
        noSearch = false;
      }
      if(model.getSourceItemCode().compareTo("Any")!=0)
      {
        sql.append("lcs_item_code like '%"+model.getSourceItemCode()+"%' and ");
        noSearch = false;
      }
      if(model.getSourceItemDesc().compareTo("Any")!=0)
      {
        sql.append("item_description like '%"+model.getSourceItemDesc()+"%' and ");
        noSearch = false;
      }
      if(startDate.compareTo("Any")!= 0)  
      {
        sql.append("(lcs_date > to_date('"+startDate+"','dd/mm/yyyy') or ");
        sql.append("lcs_date like to_date('"+startDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(endDate.compareTo("Any")!= 0)  
      {
        sql.append("(lcs_date < to_date('"+endDate+"','dd/mm/yyyy') or ");
        sql.append("lcs_date like to_date('"+endDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(noSearch)
        sql.setLength(sql.length()-6);
      else  
        sql.setLength(sql.length()-4);

      Vector vector = new Vector();
      InformationModel info = null;
      Statement stat = con.createStatement();
      //Utility.logger.debug("SQL TEST: "+ sql.toString());
      ResultSet result = stat.executeQuery(sql.toString());
      while(result.next())
      {
        count = result.getInt("count");
      }
      result.close();
      stat.close();
      return count;
    }
    

    public static Vector searchPGWInformation(Connection con, InformationModel model,
                                            String startDate, String endDate)
    throws SQLException
    {
      boolean noSearch = true;
      StringBuffer sql = new StringBuffer("select pgw_id, franchise_code, ");
      sql.append("item_code, to_char(pgw_date,'dd/mm/yyyy'), pgw_quantity,pgw_item_code,item_name ");
      sql.append("from stm_pgw where ");
      if(model.getFranchiseCode().compareTo("Any") != 0)
      {
        sql.append("franchise_code='"+model.getFranchiseCode()+"' and ");
        noSearch = false;
      }
      if(model.getItemCode().compareTo("Any")!=0)
      {
        sql.append("item_code='"+model.getItemCode()+"' and ");
        noSearch = false;
      }
      if(model.getSourceItemCode().compareTo("Any")!=0)
      {
        sql.append("pgw_item_code like '%"+model.getSourceItemCode()+"%' and ");
        noSearch = false;
      }
      if(model.getSourceItemName().compareTo("Any")!=0)
      {
        sql.append("item_name like '%"+model.getSourceItemName()+"%' and ");
        noSearch = false;
      }
      if(startDate.compareTo("Any")!= 0)  
      {
        sql.append("(pgw_date > to_date('"+startDate+"','dd/mm/yyyy') or ");
        sql.append("pgw_date like to_date('"+startDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(endDate.compareTo("Any")!= 0)  
      {
        sql.append("(pgw_date < to_date('"+endDate+"','dd/mm/yyyy') or ");
        sql.append("pgw_date like to_date('"+endDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }
      if(noSearch)
      sql.setLength(sql.length()-6);
      else
      sql.setLength(sql.length()-4);

      Vector vector = new Vector();
      InformationModel info = null;
      Statement stat = con.createStatement();
      //Utility.logger.debug("SQL TEST: " + sql.toString());
      ResultSet result = stat.executeQuery(sql.toString());
      while(result.next())
      {
        info = new InformationModel();
        info.setId(result.getInt("pgw_id"));
        info.setFranchiseCode(result.getString("franchise_code"));
        info.setItemCode(result.getString("item_code"));
        info.setDate(result.getString(4));
        info.setQuantity(result.getInt("pgw_quantity"));
        info.setSourceItemCode(result.getString("pgw_item_code"));
        info.setSourceItemName(result.getString("item_name"));
        vector.add(info);
      }
      result.close();
      stat.close();
      return vector;
    }


    public static Vector searchPGWInformationByRowNum(Connection con, int rowNum, InformationModel model,
                                            String startDate, String endDate)
    throws SQLException
    {
      boolean noSearch = true;
      StringBuffer sql = new StringBuffer("select * from (select ROWNUM AS row_num,pgw_id, franchise_code, ");
      sql.append("item_code, to_char(pgw_date,'dd/mm/yyyy'), pgw_quantity,pgw_item_code,item_name ");
      sql.append("from stm_pgw where ");
      if(model.getFranchiseCode().compareTo("Any") != 0)
      {
        sql.append("franchise_code='"+model.getFranchiseCode()+"' and ");
        noSearch = false;
      }
      if(model.getItemCode().compareTo("Any")!=0)
      {
        sql.append("item_code='"+model.getItemCode()+"' and ");
        noSearch = false;
      }
      if(model.getSourceItemCode().compareTo("Any")!=0)
      {
        sql.append("pgw_item_code like '%"+model.getSourceItemCode()+"%' and ");
        noSearch = false;
      }
      if(model.getSourceItemName().compareTo("Any")!=0)
      {
        sql.append("item_name like '%"+model.getSourceItemName()+"%' and ");
        noSearch = false;
      }
      if(startDate.compareTo("Any")!= 0)  
      {
        sql.append("(pgw_date > to_date('"+startDate+"','dd/mm/yyyy') or ");
        sql.append("pgw_date like to_date('"+startDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(endDate.compareTo("Any")!= 0)  
      {
        sql.append("(pgw_date < to_date('"+endDate+"','dd/mm/yyyy') or ");
        sql.append("pgw_date like to_date('"+endDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }
      
      //sql.append(")where row_num > '"+rowNum+"'*3 and row_num <= ('"+rowNum+"'+1)*3 and");
      if(noSearch)
      sql.setLength(sql.length()-6);
      else
      sql.setLength(sql.length()-4);
      sql.append(")where row_num > '"+rowNum+"'*49 and row_num <= ('"+rowNum+"'+1)*49");
      //sql.append(")");

      Vector vector = new Vector();
      InformationModel info = null;
      Statement stat = con.createStatement();
      //Utility.logger.debug("SQL TEST: " + sql.toString());
      ResultSet result = stat.executeQuery(sql.toString());
      while(result.next())
      {
        info = new InformationModel();
        info.setId(result.getInt("pgw_id"));
        info.setFranchiseCode(result.getString("franchise_code"));
        info.setItemCode(result.getString("item_code"));
        info.setDate(result.getString(5));
        info.setQuantity(result.getInt("pgw_quantity"));
        info.setSourceItemCode(result.getString("pgw_item_code"));
        info.setSourceItemName(result.getString("item_name"));
        vector.add(info);
      }
      result.close();
      stat.close();
      return vector;
    }

    public static int countOfPGWState(Connection con, InformationModel model,
                                            String startDate, String endDate)
    throws SQLException
    {
      int count = 0;
      boolean noSearch = true;
      StringBuffer sql = new StringBuffer("select count(*) as count ");
      sql.append("from stm_pgw where ");
      if(model.getFranchiseCode().compareTo("Any") != 0)
      {
        sql.append("franchise_code='"+model.getFranchiseCode()+"' and ");
        noSearch = false;
      }
      if(model.getItemCode().compareTo("Any")!=0)
      {
        sql.append("item_code='"+model.getItemCode()+"' and ");
        noSearch = false;
      }
      if(model.getSourceItemCode().compareTo("Any")!=0)
      {
        sql.append("pgw_item_code like '%"+model.getSourceItemCode()+"%' and ");
        noSearch = false;
      }
      if(model.getSourceItemName().compareTo("Any")!=0)
      {
        sql.append("item_name like '%"+model.getSourceItemName()+"%' and ");
        noSearch = false;
      }
      if(startDate.compareTo("Any")!= 0)  
      {
        sql.append("(pgw_date > to_date('"+startDate+"','dd/mm/yyyy') or ");
        sql.append("pgw_date like to_date('"+startDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }

      if(endDate.compareTo("Any")!= 0)  
      {
        sql.append("(pgw_date < to_date('"+endDate+"','dd/mm/yyyy') or ");
        sql.append("pgw_date like to_date('"+endDate+"','dd/mm/yyyy')) and ");
        noSearch = false;
      }
      if(noSearch)
      sql.setLength(sql.length()-6);
      else
      sql.setLength(sql.length()-4);

      Vector vector = new Vector();
      InformationModel info = null;
      Statement stat = con.createStatement();
      //Utility.logger.debug("SQL TEST: " + sql.toString());
      ResultSet result = stat.executeQuery(sql.toString());
      while(result.next())
      {
        count = result.getInt("count");
      }
      result.close();
      stat.close();
      return count;
    }

    
    /**
     * @param con
     * @param franchiseCode
     * @param itemCode
     * @return selects Franchise model that represent the franchise and item supplied
     * from the database 
     * if not exist returns null
     * 
     */
    public static FranchiseModel getFranchiseModel(Connection con, 
                                                   String franchiseCode, 
                                                   String itemCode)
    throws SQLException{
        FranchiseModel model = null;
        Statement stat = null;
        ResultSet result = null;
        String sql = 
            "select franchise_code, item_code, current_quantity, " + "to_char(lcs_last_date, 'DD/MM/YYYY'), " + 
            "to_char(pgw_last_date, 'DD/MM/YYYY'), lcs_quantity, pgw_quantity, to_char(current_date,'dd/mm/yyyy')" + 
            " from stm_franchise_current_state where " + "franchise_code='" + 
            franchiseCode + "' and item_code='" + itemCode + "'";
        
            stat = con.createStatement();
            result = stat.executeQuery(sql);
            String temp = null;
            if (result.next()) {
                model = new FranchiseModel();
                model.setCode(result.getString("franchise_code"));
                model.setItemCode(result.getString("item_code"));
                model.setItemQuantity(result.getInt("current_quantity"));
                model.setLastLCSDate(result.getString(4));
                model.setLastPGWDat(result.getString(5));
                model.setLCSQuantity(result.getInt("lcs_quantity"));
                model.setPGWQuantity(result.getInt("pgw_quantity"));
                model.setCurrentDate(result.getString(8));
            } 
            result.close();
            stat.close();
        
        return model;
    }

    

    /**
     * @param con
     * @param model
     */
    public static void updateFranchise(Connection con, FranchiseModel model)
    throws SQLException {
        Statement stat = null;
        StringBuffer sql = 
            new StringBuffer("update stm_franchise_current_state ");
        sql.append("set current_quantity=" + model.getItemQuantity());
        if(model.getLastLCSDate() != null)
            sql.append(", lcs_last_date=to_date('" + model.getLastLCSDate() + 
                       "','DD/MM/YYYY') ");
        if(model.getLastPGWDat() != null)
        sql.append(", pgw_last_date=to_date('" + model.getLastPGWDat() + 
                   "','DD/MM/YYYY')");
        sql.append(", lcs_quantity="+model.getLCSQuantity());
        sql.append(", pgw_quantity="+model.getPGWQuantity());
        sql.append(", current_date=to_date('"+model.getCurrentDate()+"','dd/mm/yyyy')");
        sql.append("where franchise_code='" + model.getCode() + "' ");
        sql.append("and item_code='" + model.getItemCode() + "'");

        
            stat = con.createStatement();
            stat.executeUpdate(sql.toString());
            stat.close();
        
    }

    public static Vector searchFranchiseDetails(Connection con,String franchiseCode,String itemCode,String PGWStartDate,String PGWEndDate,String LCSStartDate,String LCSEndDate)
    {
      boolean andFlag = false;
      Vector vector = new Vector();
      HashMap pgwQuantity = new HashMap();
      HashMap lcsQuantity = new HashMap();
      try
      {
        Statement PGWStat = con.createStatement();
        String strSqlPGW = "select franchise_code,item_code,sum(pgw_quantity)as sum_pgw from stm_pgw ";
        if(franchiseCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else 
          strSqlPGW += " and ";
          strSqlPGW += " franchise_code = '"+franchiseCode+"' ";
          andFlag = true;
       }
        if(itemCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else
          strSqlPGW += " and ";
          strSqlPGW += " item_code = '"+itemCode+"' ";
          andFlag = true;
       }
       if(PGWStartDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else 
          strSqlPGW+= " and ";
          strSqlPGW+= "pgw_date >= TO_DATE('"+PGWStartDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       if(PGWEndDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else
          strSqlPGW += " and ";
          strSqlPGW += "pgw_date <= TO_DATE('"+PGWEndDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       strSqlPGW += "group by item_code,franchise_code";
       //Utility.logger.debug ("The first query isssssssssssssssss " + strSqlPGW);
        ResultSet result1 = PGWStat.executeQuery(strSqlPGW);
        while (result1.next())
        {
          String pgwFranchiseCode = result1.getString("franchise_code");
          String pgwItemCode = result1.getString("item_code");
          String sumPGW = result1.getString("sum_pgw");
          pgwQuantity.put(pgwFranchiseCode+"_"+pgwItemCode,sumPGW);
        }
        result1.close();
        PGWStat.close();

        andFlag = false;
        Statement LCSStat = con.createStatement();
        String strSqlLCS = "select franchise_code,item_code,sum(lcs_quantity)as sum_lcs from stm_lcs";
        if(franchiseCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += " franchise_code = '"+franchiseCode+"' ";
          andFlag = true;
       }
        if(itemCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += " item_code = '"+itemCode+"' ";
          andFlag = true;
       }
       if(LCSStartDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS+= " and ";
          strSqlLCS += "lcs_date >= TO_DATE('"+LCSStartDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       if(LCSEndDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += "lcs_date <= TO_DATE('"+LCSEndDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       strSqlLCS += " group by item_code,franchise_code";
        //Utility.logger.debug("The query isssssssssssssss " + strSqlLCS);
        ResultSet result2 = LCSStat.executeQuery(strSqlLCS);
        while (result2.next())
        {
          String lcsFranchiseCode = result2.getString("franchise_code");
          String lcsItemCode = result2.getString("item_code");
          String sumLCS = result2.getString("sum_lcs");
          //String sourceItemCode = result2.getString("LCS_ITEM_CODE");
          //String sourceItemDesc = result2.getString("ITEM_DESCRIPTION");  
          
          FranchiseDetailsModel franchiseDetailsModel = new  FranchiseDetailsModel();
          franchiseDetailsModel.setFranchiseCode(lcsFranchiseCode);
          franchiseDetailsModel.setItemCode(lcsItemCode);
          franchiseDetailsModel.setLcsQuantity(sumLCS);
          //franchiseDetailsModel.setLcsItemCode(sourceItemCode);
          //franchiseDetailsModel.setLcsItemDesc(sourceItemDesc);

          String PGWQuantity = "";
          if(pgwQuantity.containsKey(lcsFranchiseCode+"_"+lcsItemCode))
          {
            PGWQuantity = (String)pgwQuantity.get(lcsFranchiseCode+"_"+lcsItemCode);
          }
          franchiseDetailsModel.setPgwQuantity(PGWQuantity);
          vector.add(franchiseDetailsModel);

        }
        result2.close();
        LCSStat.close();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      return vector;
    }

    public static int counfOFFranchiseDetails(Connection con,String franchiseCode,String itemCode,String PGWStartDate,String PGWEndDate,String LCSStartDate,String LCSEndDate)
    {
      boolean andFlag = false;
      int count = 0 ;
      try
      {
        Statement LCSStat = con.createStatement();
        String strSqlLCS = "select count(*) as count from ( select franchise_code,item_code,sum(lcs_quantity)as sum_lcs from stm_lcs";
        if(franchiseCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += " franchise_code = '"+franchiseCode+"' ";
          andFlag = true;
       }
        if(itemCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += " item_code = '"+itemCode+"' ";
          andFlag = true;
       }
       if(LCSStartDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS+= " and ";
          strSqlLCS += "lcs_date >= TO_DATE('"+LCSStartDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       if(LCSEndDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += "lcs_date <= TO_DATE('"+LCSEndDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       strSqlLCS += " group by item_code,franchise_code)";
        //Utility.logger.debug("The query isssssssssssssss " + strSqlLCS);
        ResultSet res = LCSStat.executeQuery(strSqlLCS);
        while (res.next())
        {
          count = res.getInt("count");
        }
        LCSStat.close();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      return count;
    }

    public static Vector searchFranchiseDetailsByRowNum(Connection con,int rowNum,String franchiseCode,String itemCode,String PGWStartDate,String PGWEndDate,String LCSStartDate,String LCSEndDate)
    {
      boolean andFlag = false;
      Vector vector = new Vector();
      HashMap pgwQuantity = new HashMap();
      HashMap lcsQuantity = new HashMap();
      try
      {
        Statement PGWStat = con.createStatement();
        String strSqlPGW = "select franchise_code,item_code,sum(pgw_quantity)as sum_pgw from stm_pgw ";
        if(franchiseCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else 
          strSqlPGW += " and ";
          strSqlPGW += " franchise_code = '"+franchiseCode+"' ";
          andFlag = true;
       }
        if(itemCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else
          strSqlPGW += " and ";
          strSqlPGW += " item_code = '"+itemCode+"' ";
          andFlag = true;
       }
       if(PGWStartDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else 
          strSqlPGW+= " and ";
          strSqlPGW+= "pgw_date >= TO_DATE('"+PGWStartDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       if(PGWEndDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlPGW += " where ";
          else
          strSqlPGW += " and ";
          strSqlPGW += "pgw_date <= TO_DATE('"+PGWEndDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       strSqlPGW += "group by item_code,franchise_code";
       //Utility.logger.debug ("The first query isssssssssssssssss " + strSqlPGW);
        ResultSet result1 = PGWStat.executeQuery(strSqlPGW);
        while (result1.next())
        {
          String pgwFranchiseCode = result1.getString("franchise_code");
          String pgwItemCode = result1.getString("item_code");
          String sumPGW = result1.getString("sum_pgw");
          pgwQuantity.put(pgwFranchiseCode+"_"+pgwItemCode,sumPGW);
        }
        result1.close();
        PGWStat.close();

        andFlag = false;
        Statement LCSStat = con.createStatement();
        String strSqlLCS = "select * from (select ROWNUM as row_num,franchise_code,item_code,LCS_ITEM_CODE,sum_lcs from "+
                            "(select franchise_code,item_code,LCS_ITEM_CODE,sum(lcs_quantity)as sum_lcs from stm_lcs";
        if(franchiseCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += " franchise_code = '"+franchiseCode+"' ";
          andFlag = true;
       }
        if(itemCode.compareTo("")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += " item_code = '"+itemCode+"' ";
          andFlag = true;
       }
       if(LCSStartDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS+= " and ";
          strSqlLCS += "lcs_date >= TO_DATE('"+LCSStartDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       if(LCSEndDate.compareTo("*")!=0)
       {
          if(!andFlag)
          strSqlLCS += " where ";
          else
          strSqlLCS += " and ";
          strSqlLCS += "lcs_date <= TO_DATE('"+LCSEndDate+"','dd/mm/yyyy')";
          andFlag = true;
       }
       strSqlLCS += " group by item_code,franchise_code,LCS_ITEM_CODE)) where row_num > '"+rowNum+"'*49 and row_num <= ('"+rowNum+"'+1)*49 ";
        //Utility.logger.debug("The query isssssssssssssss " + strSqlLCS);
        ResultSet result2 = LCSStat.executeQuery(strSqlLCS);
        while (result2.next())
        {
          String lcsFranchiseCode = result2.getString("franchise_code");
          String lcsItemCode = result2.getString("item_code");
          String lcsCode =  result2.getString("LCS_ITEM_Code");
          String sumLCS = result2.getString("sum_lcs");
          //String sourceItemCode = result2.getString("LCS_ITEM_CODE");
          //String sourceItemDesc = result2.getString("ITEM_DESCRIPTION");  
          
          FranchiseDetailsModel franchiseDetailsModel = new  FranchiseDetailsModel();
          franchiseDetailsModel.setFranchiseCode(lcsFranchiseCode);
          franchiseDetailsModel.setItemCode(lcsItemCode);
          franchiseDetailsModel.setLcsItemCode(lcsCode);
          franchiseDetailsModel.setLcsQuantity(sumLCS);
          //franchiseDetailsModel.setLcsItemCode(sourceItemCode);
          //franchiseDetailsModel.setLcsItemDesc(sourceItemDesc);

          String PGWQuantity = "";
          if(pgwQuantity.containsKey(lcsFranchiseCode+"_"+lcsItemCode))
          {
            PGWQuantity = (String)pgwQuantity.get(lcsFranchiseCode+"_"+lcsItemCode);
          }
          franchiseDetailsModel.setPgwQuantity(PGWQuantity);
          vector.add(franchiseDetailsModel);

        }
        result2.close();
        LCSStat.close();
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      return vector;
    }
    
    public static Vector searchFranchise(Connection con, FranchiseModel model)
    throws SQLException
    {
      Vector vector = new Vector();
      Statement stat = con.createStatement();
      StringBuffer sql = new StringBuffer("select stm_franchise_current_state.franchise_code, stm_franchise_current_state.item_code, ");
      sql.append("stm_franchise_current_state.current_quantity, to_char(stm_franchise_current_state.lcs_last_date,'dd/mm/yyyy'), ");
      sql.append("to_char(stm_franchise_current_state.pgw_last_date, 'dd/mm/yyyy'), stm_franchise_current_state.lcs_quantity, stm_franchise_current_state.pgw_quantity, ");
      sql.append("to_char(stm_franchise_current_state.current_date,'dd/mm/yyyy'),stm_franchise_current_state.pgw_item_code,stm_franchise_current_state.lcs_item_code,stm_franchise_current_state.pgw_item_name,stm_franchise_current_state.lcs_item_description,gen_dcm.DCM_NAME ");
      sql.append("from stm_franchise_current_state,gen_dcm where ");
      if(model.getCode().compareTo("Any")==0)
        sql.append("stm_franchise_current_state.franchise_code like'%' ");
      else
        sql.append("stm_franchise_current_state.franchise_code='"+model.getCode()+"' ");

      if(model.getItemCode().compareTo("Any")==0)  
        sql.append("and stm_franchise_current_state.item_code like '%'");
      else 
        sql.append("and stm_franchise_current_state.item_code = '"+model.getItemCode()+"'");

        sql.append("and stm_franchise_current_state.FRANCHISE_CODE = gen_dcm.DCM_CODE ");

      ResultSet result = stat.executeQuery(sql.toString())  ;
      FranchiseModel temp = null;
      while(result.next())
      {
        temp = new FranchiseModel();
        temp.setCode(result.getString("franchise_code"));
        temp.setItemCode(result.getString("item_code"));
        temp.setItemQuantity(result.getInt("current_quantity"));
        temp.setLastLCSDate(result.getString(4));
        temp.setLastPGWDat(result.getString(5));
        temp.setLCSQuantity(result.getInt("lcs_quantity"));
        temp.setPGWQuantity(result.getInt("pgw_quantity"));
        temp.setCurrentDate(result.getString(8));
        temp.setLCSItemCode(result.getString("lcs_item_code"));
        temp.setPGWItemCode(result.getString("pgw_item_code"));
        temp.setPGWItemName(result.getString("pgw_item_name"));
        temp.setLCSItemDescription(result.getString("lcs_item_description"));
        temp.setFranchiseName(result.getString("DCM_NAME"));
        vector.add(temp);
      }
      result.close();
      stat.close();
      return vector;
    }

    public static Vector searchFranchiseByRowNum(Connection con, int rowNum, FranchiseModel model)
    throws SQLException
    {
      Vector vector = new Vector();
      Statement stat = con.createStatement();
      StringBuffer sql = new StringBuffer("select * from (select ROWNUM as row_num,stm_franchise_current_state.franchise_code, stm_franchise_current_state.item_code, ");
      sql.append("stm_franchise_current_state.current_quantity, to_char(stm_franchise_current_state.lcs_last_date,'dd/mm/yyyy'), ");
      sql.append("to_char(stm_franchise_current_state.pgw_last_date, 'dd/mm/yyyy'), stm_franchise_current_state.lcs_quantity, stm_franchise_current_state.pgw_quantity, ");
      sql.append("to_char(stm_franchise_current_state.current_date,'dd/mm/yyyy'),stm_franchise_current_state.pgw_item_code,stm_franchise_current_state.lcs_item_code,stm_franchise_current_state.pgw_item_name,stm_franchise_current_state.lcs_item_description,gen_dcm.DCM_NAME ");
      sql.append("from stm_franchise_current_state,gen_dcm where ");
      if(model.getCode().compareTo("Any")==0)
        sql.append("stm_franchise_current_state.franchise_code like'%' ");
      else
        sql.append("stm_franchise_current_state.franchise_code='"+model.getCode()+"' ");

      if(model.getItemCode().compareTo("Any")==0)  
        sql.append("and stm_franchise_current_state.item_code like '%'");
      else 
        sql.append("and stm_franchise_current_state.item_code = '"+model.getItemCode()+"'");

        sql.append("and stm_franchise_current_state.FRANCHISE_CODE = gen_dcm.DCM_CODE ");
    sql.append(")where row_num > '"+rowNum+"'*49 and row_num <= ('"+rowNum+"'+1)*49");
    //Utility.logger.debug("SQL TEST: " + sql.toString());
      ResultSet result = stat.executeQuery(sql.toString())  ;
      FranchiseModel temp = null;
      while(result.next())
      {
        temp = new FranchiseModel();
        temp.setCode(result.getString("franchise_code"));
        temp.setItemCode(result.getString("item_code"));
        temp.setItemQuantity(result.getInt("current_quantity"));
        String lastLCSDate = result.getString(5);
        temp.setLastLCSDate(result.getString(5));
        String lastPGWDate = result.getString(6);
        temp.setLastPGWDat(result.getString(6));
        temp.setLCSQuantity(result.getInt("lcs_quantity"));
        temp.setPGWQuantity(result.getInt("pgw_quantity"));
        String currentDate = result.getString(9);
        temp.setCurrentDate(result.getString(9));
        temp.setLCSItemCode(result.getString("lcs_item_code"));
        temp.setPGWItemCode(result.getString("pgw_item_code"));
        temp.setPGWItemName(result.getString("pgw_item_name"));
        temp.setLCSItemDescription(result.getString("lcs_item_description"));
        temp.setFranchiseName(result.getString("DCM_NAME"));
        vector.add(temp);
      }
      result.close();
      stat.close();
      return vector;
    }

    public static int countOfFranchise (Connection con, FranchiseModel model)
    throws SQLException
    {
      int count = 0;
      Vector vector = new Vector();
      Statement stat = con.createStatement();
      StringBuffer sql = new StringBuffer("select count(*)as count ");
      sql.append("from stm_franchise_current_state,gen_dcm where ");
      if(model.getCode().compareTo("Any")==0)
        sql.append("stm_franchise_current_state.franchise_code like'%' ");
      else
        sql.append("stm_franchise_current_state.franchise_code='"+model.getCode()+"' ");

      if(model.getItemCode().compareTo("Any")==0)  
        sql.append("and stm_franchise_current_state.item_code like '%'");
      else 
        sql.append("and stm_franchise_current_state.item_code = '"+model.getItemCode()+"'");

        sql.append("and stm_franchise_current_state.FRANCHISE_CODE = gen_dcm.DCM_CODE ");

      ResultSet result = stat.executeQuery(sql.toString())  ;
      FranchiseModel temp = null;
      while(result.next())
      {
        count = result.getInt("count");
      }
      result.close();
      stat.close();
      return count;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

        /**
     * @param con
     * @param franchiseCode
     * @param itemCode
     * @param after
     * @return Vector of two elements the first is the item count the second is 
     * the latest date counted
     */
    public static Vector getLCSItemInfo(Connection con, String franchiseCode, 
                                        String itemCode, String after)
    throws SQLException{
        Vector vector = new Vector();
        Statement stat = null;
        ResultSet result = null;
        StringBuffer sql = 
            new StringBuffer("select sum(transaction_quantity), ");
        sql.append("to_char(max(transaction_date),'DD/MM/YYYY') from ");
        sql.append(LCS_TABLE + " where transaction_source_name='" + 
                   franchiseCode);
        sql.append("' and item_code='" + itemCode + "'");
        if (after != null && after.trim().length() != 0)
            sql.append(" and transaction_date > to_date('" + after + 
                       "','DD/MM/YYYY')");

        
            stat = con.createStatement();
            result = stat.executeQuery(sql.toString());
            if (result.next()) {
                vector.add(result.getString(1));
                vector.add(result.getString(2));
            }
            result.close();
            stat.close();
        
        return vector;
    }


    /**
     * @param con
     * @param franchiseCode
     * @param itemCode
     * @param after
     * @return Vector of two elements the first is the item count the second is 
     * the latest date counted
     */
    public static Vector getPGWItemInfo(Connection con, String franchiseCode, 
                                        String itemCode, String after) 
    throws SQLException{
        Vector vector = new Vector();
        Statement stat = null;
        ResultSet result = null;
        StringBuffer sql = new StringBuffer("select sum(item_quantity), ");
        sql.append("to_char(max(payment_date),'DD/MM/YYYY') from ");
        sql.append(PGW_TABLE + " where multi_channel_code='" + franchiseCode);
        sql.append("' and item_code='" + itemCode + "'");
        if (after != null && after.trim().length() != 0)
            sql.append(" and payment_date > to_date('" + after + 
                       "','DD/MM/YYYY')");

        
            stat = con.createStatement();
            result = stat.executeQuery(sql.toString());
            if (result.next()) {
                vector.add(result.getString(1));
                vector.add(result.getString(2));
            }
            result.close();
            stat.close();
       
        return vector;
    }

    /**
     * @param con
     * @return Vector of franchise codes and items codes that inserted into lcs
     * view 
     */
    public static Vector getLCSFranchiseIds(Connection con) throws SQLException{
        Vector vector = new Vector();
        Statement stat = null;
        ResultSet result = null;
        String sql = 
            "select distinct transaction_source_name, item_code from " + LCS_TABLE;
        
            stat = con.createStatement();
            result = stat.executeQuery(sql);
            while (result.next()) {
                vector.add(result.getString("transaction_source_name"));
                vector.add(result.getString("item_code"));
            }
            result.close();
            stat.close();
        
        return vector;
    }
}
