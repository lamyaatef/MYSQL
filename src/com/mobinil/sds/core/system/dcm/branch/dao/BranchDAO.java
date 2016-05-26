package com.mobinil.sds.core.system.dcm.branch.dao;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.dcm.branch.model.*;
import com.mobinil.sds.core.utilities.Utility;


public class BranchDAO 
{
  public static Vector getBranchesByFilter(Connection con   , String branchName    , String branchCode   , String mainBranchName , 
                                    int branchStatus , String startDateFrom , String startDateTo  , String endDateFrom    ,
                                    String endDateTo 
                                    ) throws Exception{
                                    
    Vector branches = new Vector();
    String sqlString = "SELECT * FROM  DCM_BRANCH WHERE BRANCH_ID IN   (SELECT BRANCH_ID FROM VW_DCM_BRANCH_POS ";
    boolean andFlag = false;
    if(!branchName.equals(""))
    {
      if(andFlag)
      sqlString += " AND BRANCH_NAME LIKE '%" + branchName +"%' AND BRANCH_ID";
      else{
      sqlString += " WHERE BRANCH_NAME  LIKE '%" + branchName + "%' ";
      andFlag = true;
      }
    }
    if(!branchCode.equals(""))
    {
      if(andFlag)
      sqlString += " AND DCM_CODE = '"+ branchCode + "' ";
      else{
      sqlString += " WHERE DCM_CODE = '"+ branchCode + "' ";
      andFlag = true;
      }
    }
    if(!mainBranchName.equals(""))
    {
      if(andFlag)
      sqlString += " AND BRANCH_NAME LIKE '%"+ branchName + "%' AND MAIN_SALES_BRANCH = 1 ";
      else{
      sqlString += " WHERE BRANCH_NAME LIKE '%"+ branchName + "%' AND MAIN_SALES_BRANCH = 1 ";
      andFlag = true;
      }
    }
    if(branchStatus != 0)
    {
      if(andFlag)
      sqlString += " AND BRANCH_STATUS_ID = "+ branchStatus  + " ";
      else{
      sqlString += " WHERE BRANCH_STATUS_ID = "+ branchStatus+ " " ;
      andFlag = true;
      }
    }
    if(!startDateFrom.equals("*"))
    {
      if(andFlag){
        if(!startDateTo.equals("*"))
          sqlString += " AND START_DATE BETWEEN  TO_DATE('"+ startDateFrom + "','dd/mm/yyyy') AND TO_DATE('"+startDateTo+"' , 'dd/mm/yyyy') ";
        else
          sqlString += " AND START_DATE >= TO_DATE('"+ startDateFrom + "','dd/mm/yyyy') ";
      }
      else{
        if(!startDateTo.equals("*"))
          sqlString += " WHERE START_DATE BETWEEN  TO_DATE('"+ startDateFrom + "','dd/mm/yyyy') AND TO_DATE('"+startDateTo+"' , 'dd/mm/yyyy') ";
        else
          sqlString += " WHERE START_DATE >= TO_DATE('"+ startDateFrom + "','dd/mm/yyyy') ";

      }
      andFlag = true;
      
    }
    if(!endDateFrom.equals("*"))
    {
      if(andFlag){
        if(!endDateTo.equals("*"))
          sqlString += " AND END_DATE BETWEEN  TO_DATE('"+ endDateFrom + "','dd/mm/yyyy') AND TO_DATE('"+endDateTo+"', 'dd/mm/yyyy') ";
        else
          sqlString += " AND END_DATE >= TO_DATE('"+ endDateFrom + "','dd/mm/yyyy') ";
      }
      else{
        if(!endDateTo.equals("*"))
          sqlString += " WHERE END_DATE BETWEEN  TO_DATE('"+ endDateFrom + "','dd/mm/yyyy') AND TO_DATE('"+endDateTo+"' , 'dd/mm/yyyy') ";
        else
          sqlString += " WHERE END_DATE >= TO_DATE('"+ endDateFrom + "','dd/mm/yyyy') ";

      }
      andFlag = true;
      
    }
    if(startDateFrom.equals("*"))
    {
      if(andFlag){
        if(!startDateTo.equals("*"))
                      sqlString += " AND START_DATE <= TO_DATE('"+ startDateTo + "','dd/mm/yyyy') ";                  
      }
      else{
        if(!startDateTo.equals("*"))
          sqlString += " WHERE START_DATE <= TO_DATE('"+ startDateTo + "','dd/mm/yyyy') ";
      }
      andFlag = true;
      
    }
    if(endDateFrom.equals("*"))
    {
      if(andFlag){
        if(!endDateTo.equals("*"))
          sqlString += " AND END_DATE <= TO_DATE('"+ endDateTo + "','dd/mm/yyyy') ";
      }
      else{
        if(!endDateTo.equals("*"))
          sqlString += " WHERE END_DATE <= TO_DATE('"+ endDateTo + "','dd/mm/yyyy') ";
      }
      andFlag = true;
      
    }

//    try
//    {
      sqlString += "  )";
      Utility.logger.debug("GR8 SQL STATEMENT : "+ sqlString);    
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sqlString);
      BranchModel branchModel =null;
      while(rs.next())
      {
        branchModel = new BranchModel();
        branchModel.set_branch_name(rs.getString("BRANCH_NAME"));
        branchModel.set_branch_id(rs.getInt("BRANCH_ID"));
        branchModel.set_branch_status_id(rs.getInt("BRANCH_STATUS_ID")); 

        branches.add(branchModel);
      }
      rs.close();
      stmt.close();

 /*   }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }*/
    return branches;

  }
  //public static void addNewBranch(Connection con , String branchName , String posCode , String startDate ,
    //String endDate , int mainBranch , int mainSalesBranch) throws Exception                              
    public static void addNewBranch(Connection con , Vector branches) throws Exception
  {
    String sqlString = "";
    Statement stmt =  null;
    int branchID=0 , rows = 0;
    BranchModel branch = null;
    for(int i = 0 ; i < branches.size(); i++)
    {
      branch = (BranchModel)branches.get(i);
      if(i == 0)
      {
          stmt = con.createStatement();
          Long branch_id = Utility.getSequenceNextVal(con , "SEQ_DCM_BRANCH_ID");
          branchID   = branch_id.intValue();
          sqlString =  "INSERT INTO DCM_BRANCH (BRANCH_ID , BRANCH_NAME , BRANCH_STATUS_ID) VALUES ( "+branchID+",'"+
                        branch.get_branch_name() + "', 1)" ;
          rows = stmt.executeUpdate(sqlString);
          stmt.close();
      }
      Long branch_pos_id = Utility.getSequenceNextVal(con , "SEQ_DCM_BRANCH_POS_ID");
      int branchPOSID = branch_pos_id.intValue();
      
      sqlString = "SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE = '" + branch.get_pos_code().get(0) +"'" ;
      stmt = con.createStatement();
      Utility.logger.debug(sqlString);
      ResultSet rs = stmt.executeQuery(sqlString); 
      int POS_ID = 0;
      if(rs.next())
      {
        POS_ID = rs.getInt("DCM_ID");
      }
      stmt.close();
      rs.close();
      stmt = con.createStatement();
      for(int j = 0 ; j < branch.get_main_sales_branch().size() ; j++){
      sqlString = "INSERT INTO DCM_BRANCH_POS (BRANCH_POS_ID , POS_ID , MAIN_SALES_BRANCH ,BRANCH_ID , START_DATE ,"+
                  "END_DATE,MAIN_BRANCH) VALUES("+branchPOSID+","+POS_ID+","+branch.get_main_sales_branch().get(j)+","+
                  branchID+","+"TO_DATE('"+branch.get_start_date()+"','yyyy-mm-dd'),TO_DATE('"+branch.get_end_date()+"','yyyy-mm-dd'), "+
                  branch.get_main_branch().get(0)+")";
                  Utility.logger.debug(sqlString);
      rows = stmt.executeUpdate(sqlString);
      }
      stmt.close();
    }
  }
  public static BranchModel getBranchByID(Connection con , int branch_id) throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString  = "SELECT * FROM VW_DCM_BRANCH_POS WHERE BRANCH_ID = "+branch_id;
    ResultSet rs = stmt.executeQuery(sqlString);
    BranchModel branchModel =null;
      branchModel = new BranchModel(rs);
    rs.close();
    stmt.close();
    return branchModel;
  }
  public static void updateBranchStatus(Connection con , String branchID , String branchStatusID )throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "UPDATE DCM_BRANCH SET BRANCH_STATUS_ID = "+branchID +" WHERE BRANCH_ID = "+branchStatusID;
    Utility.logger.debug(sqlString);
    int rows = stmt.executeUpdate(sqlString);
    stmt.close();
  }
  public static void saveEditedBranch(Connection con ,BranchModel Branch ,String  branchID ,int state)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString ="";
    sqlString = "SELECT DCM_ID FROM GEN_DCM WHERE DCM_CODE = '" + Branch.get_pos_code().get(0) +"'" ;
      stmt = con.createStatement();
      Utility.logger.debug(sqlString);
      ResultSet rs = stmt.executeQuery(sqlString); 
      int POS_ID = 0;
      if(rs.next())
      {
        POS_ID = rs.getInt("DCM_ID");
      }
      stmt.close();
      rs.close();
      stmt = con.createStatement();
      if(state == 0){
      sqlString = "UPDATE DCM_BRANCH_POS SET POS_ID = "+POS_ID+",MAIN_SALES_BRANCH = "+Branch.get_main_sales_branch().get(0)+",MAIN_BRANCH = "+
                  Branch.get_main_branch()+",START_DATE = TO_DATE('"+Branch.get_start_date()+"','YYYY-MM-DD'),END_DATE = TO_DATE('"+
                  Branch.get_end_date()+"','YYYY-MM-DD') WHERE BRANCH_ID = "+branchID+" AND BRANCH_POS_ID =  "+Branch.get_branch_pos_id().get(0);
      Utility.logger.debug(sqlString);
      int rows = stmt.executeUpdate(sqlString);
      sqlString = "UPDATE DCM_BRANCH SET BRANCH_NAME ='"+Branch.get_branch_name()+"' WHERE BRANCH_ID = "+branchID+"";
      rows = stmt.executeUpdate(sqlString) ;
      }
      if(state == 1)
      {
        Long branch_pos_id = Utility.getSequenceNextVal(con,"SEQ_DCM_BRANCH_POS_ID");
        int branchPOSID = branch_pos_id.intValue();
        sqlString = "INSERT INTO DCM_BRANCH_POS (BRANCH_POS_ID , POS_ID , MAIN_SALES_BRANCH ,BRANCH_ID , START_DATE ,"+
                  "END_DATE,MAIN_BRANCH) VALUES("+branchPOSID+","+POS_ID+","+Branch.get_main_sales_branch().get(0)+","+
                  branchID+","+"TO_DATE('"+Branch.get_start_date()+"','yyyy-mm-dd'),TO_DATE('"+Branch.get_end_date()+"','yyyy-mm-dd'), "+
                  Branch.get_main_branch()+")";
        Utility.logger.debug(sqlString);
        int rows = stmt.executeUpdate(sqlString);
      }
      stmt.close();
        
    
  }
  public BranchDAO()
  {
  }
}