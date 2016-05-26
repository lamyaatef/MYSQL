package com.mobinil.sds.core.system.dcm.genericModel.DAO;

import com.mobinil.sds.core.system.dcm.genericModel.*;
import com.mobinil.sds.core.system.sip.model.sipReportChannelModel;
import com.mobinil.sds.core.system.sop.requests.model.RequestStatusModel;
import com.mobinil.sds.core.system.commission.model.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
import java.util.Vector;
import java.io.Serializable;

public class GenericModelDAO 
{
  int columCount = 0;
  int rowsCount  = 0;
  public static void addNewGenericItem(Connection con , String tableName , GenericModel genericModel)throws Exception
  {       
        Statement stmt = con.createStatement();
        /*String sql = "SELECT MAX("+genericModel.get_primary_key_name()+") as maximum from "+tableName;
        Utility.logger.debug(sql);
        ResultSet rs = stmt.executeQuery(sql);
        int id = 0;
        if(rs.next())
          id = rs.getInt("maximum")+1;*/
        String sqlStringHead = "INSERT INTO "+tableName+"("+genericModel.get_primary_key_name()+",";
        String sqlStringTail = " Values('"+genericModel.get_primary_key_value()+"','";
        String sqlString="";
        for(int i = 0 ; i < genericModel.get_columnCount(); i++)
        {
          switch(i)
          {
            
            case 0:
            {
              if(!genericModel.get_field_1_name().equals(genericModel.get_primary_key_name())){
              sqlStringHead += genericModel.get_field_1_name()+",";
              sqlStringTail += genericModel.get_field_1_value()+"','";
              }
            }
            break;
            case 1:
            {
              if(!genericModel.get_field_2_name().equals(genericModel.get_primary_key_name())){
              sqlStringHead += genericModel.get_field_2_name()+",";
              sqlStringTail += genericModel.get_field_2_value()+"','";
              }
            }
            break;
            case 2:
            {
              if(!genericModel.get_field_3_name().equals(genericModel.get_primary_key_name())){
              sqlStringHead += genericModel.get_field_3_name()+",";
              sqlStringTail += genericModel.get_field_3_value()+"','";
              }
            }
            break;
            case 3:
            {
              if(!genericModel.get_field_4_name().equals(genericModel.get_primary_key_name())){
              sqlStringHead += genericModel.get_field_4_name()+",";
              sqlStringTail += genericModel.get_field_4_value()+"','";
              }
            }
            break;
            case 4:
            {
              if(!genericModel.get_field_5_name().equals(genericModel.get_primary_key_name())){
              sqlStringHead += genericModel.get_field_5_name()+",";
              sqlStringTail += genericModel.get_field_5_value()+"','";
              }
            }
            break;
          }
        }
        int headLength = sqlStringHead.length();
        int tailLength = sqlStringTail.length();
        Utility.logger.debug("headddddd:  "+sqlStringHead);
                Utility.logger.debug("tailll:  "+sqlStringTail);
        sqlStringHead = sqlStringHead.substring(0,headLength-1);
        sqlStringTail = sqlStringTail.substring(0,tailLength-2);
        sqlStringHead += ")";
        sqlStringTail += ")";

        sqlString = sqlStringHead+sqlStringTail;
        Utility.logger.debug("GR************************"+sqlString);
        int rows = stmt.executeUpdate(sqlString);
        stmt.close();
  }
  public static GenericModel getModelByID(Connection con , String genericmodelID , String genericModelTableName) throws Exception
  {
        GenericModel genericmodel = new GenericModel();
        Statement stmt = con.createStatement();
        String sqlString ="";
        String pk_name   = GenericModelDAO.set_primary_key_name(con , genericModelTableName);
        sqlString = "SELECT * FROM "+genericModelTableName+" WHERE "+
                            pk_name+" = '"+genericmodelID + "'";
        Utility.logger.debug(sqlString);   
        int columnCount = GenericModelDAO.get_column_count(con , genericModelTableName);
        ResultSet rs = stmt.executeQuery(sqlString);
        if(rs.next())
        {
            switch(columnCount)
            {
              case 1:
              {
                genericmodel.set_field_1_value(rs.getString(1));
               }break;
              case 2:
              {
                genericmodel.set_field_1_value(rs.getString(1));                
                genericmodel.set_field_2_value(rs.getString(2));                
              }break;              
              case 3:
              {
                genericmodel.set_field_1_value(rs.getString(1));
                genericmodel.set_field_2_value(rs.getString(2));                 
                genericmodel.set_field_3_value(rs.getString(3));                
              }break;
              case 4:
              {
                genericmodel.set_field_1_value(rs.getString(1));
                genericmodel.set_field_2_value(rs.getString(2));
                genericmodel.set_field_3_value(rs.getString(3));                
                genericmodel.set_field_4_value(rs.getString(4));                
              }break;
              case 5:
              {
                genericmodel.set_field_1_value(rs.getString(1));                
                genericmodel.set_field_2_value(rs.getString(2));
                genericmodel.set_field_3_value(rs.getString(3));
                genericmodel.set_field_4_value(rs.getString(4));                
                genericmodel.set_field_5_value(rs.getString(5));                
                
              }break;  
              
            }
            genericmodel.set_primary_key_name(pk_name);
            genericmodel.set_primary_key_value(rs.getString(pk_name));
            Utility.logger.debug("DAOOOOOOOOOO: "+pk_name);
        }
            genericmodel.set_primary_key_name(pk_name);
            Utility.logger.debug("DAOOOOOOOOOO: "+pk_name);

        rs.close();
        stmt.close();
    return genericmodel;
  }
  
  public static GenericModel getColumns(Connection con , String tableName) throws Exception
  {
        try
        {
          GenericModel genericModel = new GenericModel();
       Statement stmt = con.createStatement();
        String sqlString = "select * from " + tableName + " where rownum <=0"; //empty result set we only need meta data;
        ResultSet rs = stmt.executeQuery(sqlString);
        ResultSetMetaData RSMetaData  = rs.getMetaData();
        int columnCount = RSMetaData.getColumnCount();
        String columnName = "" ;
        genericModel.set_tableName(tableName);
        genericModel.set_columnCount(columnCount);       
        String pk_name = GenericModelDAO.set_primary_key_name(con,tableName);
        genericModel.set_primary_key_name(pk_name);
        for(int i = 1 ; i < columnCount + 1 ; i++)
        {
          columnName = RSMetaData.getColumnName(i);
          switch(i)
          {
            case 1:
              genericModel.set_field_1_name(columnName);
            case 2:
              genericModel.set_field_2_name(columnName);
            case 3:
              genericModel.set_field_3_name(columnName);
            case 4:
              genericModel.set_field_4_name(columnName);
            case 5:
              genericModel.set_field_5_name(columnName);
          }
        }
        rs.close();
        stmt.close();
    return genericModel;
        }
        catch (Exception e)
        {
          return null;  
        }
  }

  
  public static String set_primary_key_name(Connection con , String tableName) throws Exception
  {
    String pk_name = "";
        String tblName = tableName.toUpperCase();
        Statement stmt = con.createStatement();    
        String sqlString =  "select  COLUMN_NAME"+
                            " FROM ALL_CONS_COLUMNS"+
                            " WHERE CONSTRAINT_NAME IN ( SELECT CONSTRAINT_NAME"+
                            " FROM ALL_CONSTRAINTS"+
                            " where CONSTRAINT_TYPE = 'P' )"+
                            " and table_name = '"+tblName+"'";
        ResultSet rs = stmt.executeQuery(sqlString);
        if(rs.next())
          pk_name = rs.getString("COLUMN_NAME");
        rs.close();
        stmt.close();

    Utility.logger.debug("PK_NAME IS :  "+ pk_name);
    return pk_name;
  }
  
  public static Vector getModels(Connection con , GenericModel gm) throws Exception
  {
  
    int count = gm.get_columnCount();
    String tblName =  gm.get_tableName();
    Vector models = new Vector();
        Statement stmt = con.createStatement();      
        String sqlString = "SELECT * FROM " + tblName ;
        
        ResultSet rs = stmt.executeQuery(sqlString);
        String pk_name = GenericModelDAO.set_primary_key_name(con,tblName);
        
        while(rs.next()){
           GenericModel genericModel = new GenericModel();
           genericModel.set_primary_key_name(pk_name);
           genericModel.set_primary_key_value(rs.getString(pk_name));        
     
          for(int i = 1 ; i <= count ; i ++)
          {
              switch(i)          
              {
                case 1:
                {
                  genericModel.set_field_1_name(gm.get_field_1_name());
                  genericModel.set_field_1_value(rs.getString(gm.get_field_1_name()));
                }
                break;
                case 2:
                {
                  genericModel.set_field_2_name(gm.get_field_2_name());
                  genericModel.set_field_2_value(rs.getString(gm.get_field_2_name()));
                }
                break;
                case 3:
                {
                  genericModel.set_field_3_name(gm.get_field_3_name());
                  genericModel.set_field_3_value(rs.getString(gm.get_field_3_name()));
                }
                break;
                case 4:
                {
                  genericModel.set_field_4_name(gm.get_field_4_name());
                  genericModel.set_field_4_value(rs.getString(gm.get_field_4_name()));
                }
                break;
                case 5:
                {
                  genericModel.set_field_5_name(gm.get_field_5_name());
                  genericModel.set_field_5_value(rs.getString(gm.get_field_5_name()));
                }
                break;
              }
              genericModel.set_primary_key_name(pk_name);
            genericModel.set_primary_key_value(rs.getString(pk_name));
          }
          models.add(genericModel);
        }
        rs.close();
        stmt.close();      
    return models;   
  }
  
  public static Vector getModels(Connection con , GenericModel gm ,String orderBy) throws Exception
  {
  
    int count = gm.get_columnCount();
    String tblName =  gm.get_tableName();
    Vector models = new Vector();
        Statement stmt = con.createStatement();      
        String sqlString = "SELECT * FROM " + tblName + " order by "+orderBy;
        Utility.logger.debug(sqlString);
        ResultSet rs = stmt.executeQuery(sqlString);
        String pk_name = GenericModelDAO.set_primary_key_name(con,tblName);
        
        while(rs.next()){
           GenericModel genericModel = new GenericModel();
           genericModel.set_primary_key_name(pk_name);
           genericModel.set_primary_key_value(rs.getString(pk_name));        
     
          for(int i = 1 ; i <= count ; i ++)
          {
              switch(i)          
              {
                case 1:
                {
                  genericModel.set_field_1_name(gm.get_field_1_name());
                  genericModel.set_field_1_value(rs.getString(gm.get_field_1_name()));
                }
                break;
                case 2:
                {
                  genericModel.set_field_2_name(gm.get_field_2_name());
                  genericModel.set_field_2_value(rs.getString(gm.get_field_2_name()));
                }
                break;
                case 3:
                {
                  genericModel.set_field_3_name(gm.get_field_3_name());
                  genericModel.set_field_3_value(rs.getString(gm.get_field_3_name()));
                }
                break;
                case 4:
                {
                  genericModel.set_field_4_name(gm.get_field_4_name());
                  genericModel.set_field_4_value(rs.getString(gm.get_field_4_name()));
                }
                break;
                case 5:
                {
                  genericModel.set_field_5_name(gm.get_field_5_name());
                  genericModel.set_field_5_value(rs.getString(gm.get_field_5_name()));
                }
                break;
              }
              genericModel.set_primary_key_name(pk_name);
            genericModel.set_primary_key_value(rs.getString(pk_name));
          }
          models.add(genericModel);
        }
        rs.close();
        stmt.close();      
    return models;   
  }
    public static int get_column_count(Connection con , String tableName)throws Exception
  {
  int count =0;
        Statement stmt = con.createStatement();
        String sqlString = "select * from " + tableName;
        ResultSet rs = stmt.executeQuery(sqlString);
        ResultSetMetaData RSMetaData  = rs.getMetaData();
         count = RSMetaData.getColumnCount();
        if(count > 5)
          count =5;

        rs.close();
        stmt.close();
    return count;
  }
  public static int get_row_count(Connection con , String tableName)throws Exception
  {
    int count = 0;
        Statement stmt = con.createStatement();
        String sqlString = "select count(*) as count from " + tableName;
        ResultSet rs = stmt.executeQuery(sqlString);
        if(rs.next())
        {
          count = rs.getInt("count");
        }

        rs.close();
        stmt.close();
    return count;
  }
  public static Vector get_column_Names(Connection con , String tableName)throws Exception
  { 
            Vector Names = new Vector();
        int count = 0 ;
        Statement stmt = con.createStatement();
        String sqlString = "select * from " + tableName;
        ResultSet rs = stmt.executeQuery(sqlString);
        ResultSetMetaData RSMetaData  = rs.getMetaData();
        count = RSMetaData.getColumnCount();
        String columnName = "";
        if(count>5)
          count = 5;

        for(int i = 1 ; i <= count ; i ++)
        {
          columnName = RSMetaData.getColumnName(i);
          Utility.logger.debug("Column  "+i+" : " +columnName);
          Names.add(columnName);
        }

        rs.close();
        stmt.close();
    return Names;
  }
  
  public static Vector<CommissionChannelModel> getCommissionChannel(Connection con,String userId)
  {
	  Vector<CommissionChannelModel> comChannelVec = new Vector<CommissionChannelModel>();
	  try {
		  	Statement stmt = con.createStatement();
		  	String sqlString = "select * from commission_channel"+
		  					 " where commission_channel_id in (select commission_channel_id from commission_user_channel"+
		  					 " where commission_user_id = '"+userId+"') order by commission_channel_name";		  	
		  	ResultSet rs = stmt.executeQuery(sqlString);
		  	while(rs.next())
		     {
			  comChannelVec.add(new CommissionChannelModel(rs));
		     }
		  	rs.close();
		  	stmt.close();
	  	   }
	  catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return comChannelVec;
  }
  
  
  
  
  
  
  
  
  
  
  public static Vector getsipReportChannel(Connection con,String userId)
  {
	  Vector comChannelVec = new Vector();
	  try {
		  	Statement stmt = con.createStatement();
		  	String sqlString = "select * from sip_report_channel";  //+
		  			//	 " where sip_report_channel_id in (select sip_report_channel_id from sip_report_user_channel"+
		  			//		 " where sip_report_user_id = '"+userId+"')";
		  	//System.out.println("The query isssssss " + sqlString);
		  	ResultSet rs = stmt.executeQuery(sqlString);
		  	while(rs.next())
		     {
			  comChannelVec.add(new sipReportChannelModel(rs));
		     }
		  	rs.close();
		  	stmt.close();
	  	   }
	  catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return comChannelVec;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public GenericModelDAO()
  {
  }
}