package com.mobinil.sds.core.system.gn.reports.dao;

import com.mobinil.sds.core.system.gn.reports.model.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;

import java.util.*;
import java.sql.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.gn.reports.*;
public class ReportDAO 
{
  public ReportDAO()
  {
  }

  public static Vector getAllReports()
  {
      Vector funVec = new Vector();
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
//       ResultSet res= stat.executeQuery("select * from ADH_REPORT t1 left join ADH_GROUP_REPORT t2 on t1.REPORT_ID=t2.REPORT_ID order by REPORT_NAME");     
       ResultSet res= stat.executeQuery("select * from ADH_REPORT where REPORT_STATUS_ID='1' order by REPORT_NAME");     

       while(res.next())
       {
         funVec.add(new ReportModel(res));
       }
       stat.close();
       Utility.closeConnection(con);        
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return funVec; 
  }

  public static Vector getUserReports(String userId,int groupId)
  {
      Vector funVec = new Vector();
      try
      {
       Connection con = Utility.getConnection();
       Statement stat = con.createStatement();
//       String SQL = "SELECT   *  FROM adh_person_report t1 "    +
//"         LEFT JOIN adh_report t2 ON t1.report_id = t2.report_id"+
//"         LEFT JOIN adh_group_report t3 ON t3.report_id = t1.report_id"+
// "        LEFT JOIN adh_report_group t4 ON t4.ID = t3.GROUP_ID"+
//  "       LEFT JOIN ADH_REPORT_GROUP_STATUS t5 ON t4.GROUP_STATUS = t5.ID"         +
//  " WHERE t3.REPORT_STATUS_ID='1' and person_id = "+userId+" and group_id = "+groupId+"   "+
//  " order by t4.Group_Name";
String SQL = "SELECT   *  FROM adh_person_report  t1"+     
"         LEFT JOIN adh_report t2 ON t1.report_id = t2.report_id "+
"   WHERE person_id = "+userId+" and group_id ="+groupId+" order by t2.REPORT_NAME";
//System.out.println(SQL);
       
//       ResultSet res= stat.executeQuery("select * from ADH_REPORT,adh_person_report where adh_person_report.USER_ID = "+userId+" and adh_person_report.REPORT_ID = ADH_REPORT.REPORT_ID");     
       ResultSet res= stat.executeQuery(SQL);     
       while(res.next())
       {
         funVec.add(new ReportModel(res));
       }
       stat.close();
       Utility.closeConnection(con);        
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }

        return funVec; 
  }
  
  public static void insertUserReport(Connection con,Long userreportID,String userId,String reportId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "insert into ADH_USER_REPORTS(ID, "+
                         "USER_ID,REPORT_ID) "+
                         "values("+userreportID+","+userId+","+reportId+")";                            
      stat.execute(insertSql);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void deleteUserReport(Connection con,String userId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "delete from ADH_USER_REPORTS where USER_ID = "+userId+" " ; 
                                                                          
      stat.execute(insertSql); 
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  private static Vector<ReportStatusModel> reportStatusVector = null;
  
  public static Vector<ReportStatusModel> getReportStatus(Connection con)
  {	  
	  if (reportStatusVector == null)
	  {
		  Vector <ReportStatusModel>funVec= new Vector<ReportStatusModel>();   
	        try
	        {	 
	         Statement stat = con.createStatement();
	         ResultSet res= stat.executeQuery("select * from ADH_REPORT_STATUS");     
	         while(res.next())
	         {
	           funVec.add(new ReportStatusModel(res));
	         }
	         stat.close();
	         
	         reportStatusVector  = funVec;       
	        }
	        catch(Exception e)
	        {
	        e.printStackTrace();
	        }  
	  }	  	  
	  return reportStatusVector; 
  }
  
public static void updateReportStatus(Connection con,String reportID,String statusId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_REPORT set REPORT_STATUS_ID = "+statusId+" where REPORT_ID = "+reportID+" ";
                             
      stat.execute(insertSql);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void updateGroupStatus(Connection con,String groupDesc,String groupName,String statusId,String groupId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_REPORT_GROUP set GROUP_STATUS = "+statusId+",GROUP_NAME='"+groupName+"',GROUP_DEC='"+groupDesc+"' where ID="+groupId;
                             
      stat.execute(insertSql);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

public static void updateGroupStatus(Connection con,String statusId,String groupId)
  {
    try
    {
      Statement stat = con.createStatement();
      
      String insertSql = "update ADH_REPORT_GROUP set GROUP_STATUS = "+statusId+" where ID="+groupId;
                             
      stat.execute(insertSql);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }


  public static void updateReport(Connection con,ReportModel reportModel)
  {
    try
    {
      Statement stat = con.createStatement();

      String reportId = reportModel.getReportId();
      String reportName = reportModel.getReportName();
      String reportDataViewId = reportModel.getDataviewId();
      String reportDesc = reportModel.getReportDescription();

      
      String strSql = "update ADH_REPORT set REPORT_NAME = '"+reportName+"' "+
                      " ,REPORT_DESCRIPTION = '"+reportDesc+"' "+
                      " ,DATA_VIEW_ID = '"+reportDataViewId+"' "+
                      " where REPORT_ID = "+reportId+" ";
                             
      stat.execute(strSql);
      stat.close();
    }
    catch(Exception e)
    {  
      e.printStackTrace();
    }    
  }

  public static void insertVwAdhReport(Connection con,Long report_id, String report_name, String report_description, Long data_view_id)
  {
      try
      {
       Statement stat = con.createStatement();
//              String strSql = "INSERT INTO ADH_REPORT ( REPORT_ID,REPORT_NAME,REPORT_DESCRIPTION,DATA_VIEW_ID,REPORT_STATUS_ID) "+
//                        " values("+report_id+",'"+report_name+"','"+report_description+"',"+data_view_id+",1) ";
       String strSql = "INSERT INTO ADH_REPORT ( REPORT_ID,REPORT_NAME,REPORT_DESCRIPTION,DATA_VIEW_ID,REPORT_STATUS_ID) "+
                        " values("+report_id+",'"+report_name+"','"+report_description+"',"+data_view_id+",'1') ";
    
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }


  public static ReportModel getVwAdhReport(Connection con,int report_id)
  {
   ReportModel reportModel = null;    
      try
      {
       Statement stat = con.createStatement();
       String strSql = "SELECT * from ADH_REPORT where REPORT_ID = "+report_id+" ";
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
          reportModel = new ReportModel();
          reportModel.setDataviewId(res.getString("DATA_VIEW_ID"));
          reportModel.setReportDescription(res.getString("REPORT_DESCRIPTION"));
          reportModel.setReportName(res.getString("REPORT_NAME"));
          reportModel.setReportStatusId(res.getString("REPORT_STATUS_ID"));
       }
       stat.executeQuery(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
      return reportModel;
  }

 public static Vector getGroupDetails(Connection con,boolean type,String strGroupId)
  {
      GroupDTO gdto = null;
      Vector groupList = new Vector();
      String condition ="";
      if (type)
      {
        condition ="And GROUP_STATUS=1";
      }
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from ADH_REPORT_GROUP WHERE ID="+strGroupId+" "+condition+" order by GROUP_NAME";
       ResultSet res= stat.executeQuery(strSql);
       if(res.next())
       {
          gdto = new GroupDTO();
        int groupId=res.getInt("id");
        gdto.setGroupDesc(res.getString("GROUP_DEC"));
        gdto.setGroupId(new Integer(groupId));
        gdto.setGroupName(res.getString("GROUP_NAME"));
        gdto.setGroupStatus(new Integer(res.getInt("GROUP_STATUS")));        
        gdto.setGroupReport(getReportsPerGroup(con,Integer.parseInt(strGroupId)));
        groupList.addElement(gdto);
       }
       stat.executeQuery(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
      return groupList;
  }


    public static Vector getGroups(Connection con,boolean type)
  {
      GroupDTO gdto = null;
      Vector groupList = new Vector();
      String condition ="";
      if (type)
      {
        condition ="where GROUP_STATUS=1";
      }
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from ADH_REPORT_GROUP "+condition;
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
          gdto = new GroupDTO();
        int groupId=res.getInt("id");
        gdto.setGroupDesc(res.getString("GROUP_DEC"));
        gdto.setGroupId(new Integer(groupId));
        gdto.setGroupName(res.getString("GROUP_NAME"));
        gdto.setGroupStatus(new Integer(res.getInt("GROUP_STATUS")));        
        gdto.setGroupReport(getReportsPerGroup(con,groupId));
        groupList.addElement(gdto);
       }
       stat.executeQuery(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
      return groupList;
  }

   public static Vector getReportsPerGroup(Connection con,int group_id)
  {
   ReportModel reportModel = null; 
   Vector vecReports = new Vector();
      try
      {
       Statement stat = con.createStatement();
       String strSql = "SELECT t1.REPORT_STATUS_ID,t2.* from ADH_GROUP_REPORT t1 left join ADH_REPORT t2 on t1.REPORT_ID=t2.Report_ID where GROUP_ID="+group_id+" order by t2.REPORT_NAME";
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
          reportModel = new ReportModel();
          reportModel.setDataviewId(res.getString("DATA_VIEW_ID"));
          reportModel.setReportDescription(res.getString("REPORT_DESCRIPTION"));
          reportModel.setReportName(res.getString("REPORT_NAME"));
          reportModel.setReportStatusId(res.getString("REPORT_STATUS_ID"));
          reportModel.setReportId(res.getInt("REPORT_ID")+"");
          vecReports.addElement(reportModel);
       }
       stat.executeQuery(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
      return vecReports;
  }

  public static void insertVwAdhReportOrderBy(Connection con,Long report_order_by_id, Long report_id, Long field_id, String report_order_by_type, Long report_order_by_place)
  { 
      try
      {
       Statement stat = con.createStatement();
       String strSql = "INSERT INTO ADH_REPORT_ORDER_BY (REPORT_ORDER_BY_ID,REPORT_ID,FIELD_ID,REPORT_ORDER_BY_TYPE,REPORT_ORDER_BY_PLACE) "+
                        " values("+report_order_by_id+","+report_id+","+field_id+",'"+report_order_by_type+"',"+report_order_by_place+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static void deleteVwAdhReportOrderBy(Connection con,Long report_order_by_id)
  { 
      try
      {
       Statement stat = con.createStatement();
       String strSql = "DELETE FROM ADH_REPORT_ORDER_BY where REPORT_ORDER_BY_ID = "+report_order_by_id+" ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static void deleteVwAdhReportSelectBy(Connection con,Long report_select_id)
  { 
      try
      {
       Statement stat = con.createStatement();
       String strSql = "DELETE FROM ADH_REPORT_SELECT where REPORT_SELECT_ID = "+report_select_id+" ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static void insertVwAdhReportSelect(Connection con,Long report_select_id, Long report_id, Long field_id)
  {
      try
      {
       Statement stat = con.createStatement();
       String strSql = "INSERT INTO ADH_REPORT_SELECT (REPORT_SELECT_ID,REPORT_ID,FIELD_ID) "+
                        " values("+report_select_id+","+report_id+","+field_id+") ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static Vector getGroupStatus(Connection con)
  {
   GroupStatusDTO gsdto = null; 
   Vector vecStatus = new Vector();
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from ADH_REPORT_GROUP_STATUS";
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
          gsdto = new GroupStatusDTO();
          gsdto.setId(new Integer(res.getInt("ID")));
          gsdto.setStatus(res.getString("GROUP_STATUS_NAME"));
          
          vecStatus.addElement(gsdto);
       }
       stat.executeQuery(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
      return vecStatus;
  }

  public static void insertGroup(Connection con,GroupDTO gdto)
  {
  
      try
      {
       Statement stat = con.createStatement();
       String strSql = "INSERT INTO ADH_REPORT_GROUP(ID,GROUP_NAME,GROUP_DEC,GROUP_STATUS) "+
                        " values(SEQ_ADH_REPORT_GROUP.nextval,'"+gdto.getGroupName()+"','"+gdto.getGroupDesc()+"','"+gdto.getGroupStatus()+"') ";
       //Utility.logger.debug(strSql); 
       stat.executeUpdate(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }

  public static GroupDTO getGroupDetails(Connection con,int groupId)
  {
   GroupDTO gdto = null; 
      try
      {
       Statement stat = con.createStatement();
       String strSql = "select * from ADH_REPORT_GROUP where id="+groupId;
       ResultSet res= stat.executeQuery(strSql);
       while(res.next())
       {
          gdto = new GroupDTO();
          gdto.setGroupName(res.getString("GROUP_NAME"));
          gdto.setGroupDesc(res.getString("GROUP_DEC"));
          gdto.setGroupStatus(new Integer(res.getInt("GROUP_STATUS")));
       }
       stat.executeQuery(strSql);
       stat.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
      return gdto;
  }

   
  public static void inserReportTOGroup(Connection con,Vector reportIds,int GROUP_ID)
  {
  
      try
      {
       String deleteReportd = "delete from ADH_GROUP_REPORT where Group_id="+GROUP_ID; 
       String strSql = "insert into ADH_GROUP_REPORT values(?,"+GROUP_ID+",1) ";
       PreparedStatement stat = con.prepareStatement(strSql);
       PreparedStatement ps = con.prepareStatement(deleteReportd);
       ps.executeUpdate();    
       for (int i=0;i<reportIds.size();i++)
       {
          int reportId = ((Integer)reportIds.get(i)).intValue();
          stat.setInt(1,reportId);
      
          stat.executeUpdate();
          
       }
       
       stat.close();
       ps.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }
public static void deleteGroup(Connection con,int GROUP_ID)
  {
  
      try
      {
       String deleteReportd = "delete from ADH_GROUP_REPORT where Group_id="+GROUP_ID; 
       String deletePersonReports = "delete from ADH_PERSON_REPORT where Group_id="+GROUP_ID; 
       String strSql = "delete from ADH_REPORT_GROUP where id="+GROUP_ID; 
       Statement stat1 = con.createStatement();
       stat1.executeUpdate(deleteReportd);    
       stat1.executeUpdate(deletePersonReports);
       stat1.executeUpdate(strSql);
       stat1.close();

      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }
  
public static void insertReportToGroup(Connection con,int Report_ID,Vector groupList)
  {
  
      try
      {
       String insertReport = "insert into ADH_GROUP_REPORT values ("+Report_ID+",?,1)" ;
       PreparedStatement stat1 = con.prepareStatement(insertReport);
        GroupDTO gdto = null;
       for (int i=0;i<groupList.size();i++){
        gdto = ((GroupDTO)groupList.elementAt(i));
        Integer groupId = gdto.getGroupId();
        stat1.setInt(1,groupId.intValue());
        stat1.executeUpdate(); 
       }
   

       stat1.close();

      }
      catch(Exception e)
      {
      e.printStackTrace();
      } 
  }
}