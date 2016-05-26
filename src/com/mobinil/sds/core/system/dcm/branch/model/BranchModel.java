package com.mobinil.sds.core.system.dcm.branch.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BranchModel 
{
  Vector branch_pos_id = new Vector();
  int branch_id = 0;
  Vector main_sales_branch = new Vector();
  int branch_status_id = 0;
  Vector pos_id = new Vector();
  Vector main_branch = new Vector();
  Date start_date ;
  Date end_date ;
  String branch_name = "";
  String branch_status_name = "";
  Vector pos_code = new Vector();
  Vector pos_names = new Vector();
  Vector branchPOSID = new Vector();
  public void set_branch_pos_id(int branchPosID)
  {
    branch_pos_id.add(branchPosID+"") ;
  }
  public Vector get_branch_pos_id()
  {
    return branch_pos_id;
  }
  
    public void set_branch_id(int branchID)
  {
    branch_id = branchID ;
  }
  public int get_branch_id()
  {
    return branch_id;
  }
  
    public void set_main_sales_branch(int maninSalesBranch)
  {
    main_sales_branch.add(maninSalesBranch+"");
  }
  public Vector get_main_sales_branch()
  {
    return main_sales_branch;
  }

    public void set_main_branch(int maninBranch)
  {
    main_branch.add(maninBranch+"");
  }
  public Vector get_main_branch()
  {
    return main_branch;
  }
  
    public void set_branch_status_id(int branchStatusID)
  {
    branch_status_id = branchStatusID;
  }
  public int get_branch_status_id()
  {
    return branch_status_id;
  }
  
    public void set_start_date(Date startDate)
  {
    start_date = startDate;
  }
  public Date get_start_date()
  {
    return start_date;
  }
  
    public void set_end_date(Date endDate)
  {
    end_date = endDate;
  }
  public Date get_end_date()
  {
    return end_date;
  }
  
    public void set_branch_name(String branchName)
  {
    branch_name = branchName;
  }
  public String get_branch_name()
  {
    return branch_name;
  }
  
    public void set_branch_status_name(String branchStatusName)
  {
    branch_status_name = branchStatusName ;
  }
  public String get_branch_status_name()
  {
    return branch_status_name;
  }

    public void set_pos_id(int posID)
  {
    pos_id.add(posID+"");
  }
  public Vector get_pos_id()
  {
    return pos_id;
  }
  public void set_pos_code(String posCode)
  {
    pos_code.add(posCode);
  }
  public Vector get_pos_code()
  {
    return pos_code;
  }
    public Vector get_pos_name()
  {
  	return pos_names;
  }
  public void set_pos_name(String newPos_name)
  {
  	pos_names.add(newPos_name);
  }
  public Vector get_branchPOSID()
  {
  	return branchPOSID;
  }
  public void set_branchPOSID(int newBranchPOSID)
  {
  	branchPOSID.add(newBranchPOSID+"");
  }
  public BranchModel(ResultSet rs)
  {
  try{
  while(rs.next())
  {
    set_branch_id(rs.getInt("BRANCH_ID"));
    set_branch_name(rs.getString("BRANCH_NAME"));
    set_branch_pos_id(rs.getInt("POS_ID"));
    set_branch_status_id(rs.getInt("BRANCH_STATUS_ID"));
    set_branch_status_name(rs.getString("BRANCH_STATUS_NAME"));
    set_end_date(rs.getDate("START_DATE"));
    set_start_date(rs.getDate("END_DATE"));
    set_main_sales_branch(rs.getInt("MAIN_SALES_BRANCH"));
    set_pos_code(rs.getString("DCM_CODE"));
    set_main_branch(rs.getInt("MAIN_BRANCH"));
    set_pos_name(rs.getString("DCM_NAME"));
    set_branchPOSID(rs.getInt("BRANCH_POS_ID"));
    
  }
  }
  
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
    
  }
  public BranchModel(){}
}