package com.mobinil.sds.core.system.payment.dao;

import java.sql.*;
import java.util.*;
import com.mobinil.sds.core.system.payment.model.*;
import com.mobinil.sds.core.system.payment.dto.*;
import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.core.system.commission.model.*;

public class PaymentDAO 
{
	public static Vector<PaymentModel> getPaymentByFilter(Connection con , String paymentName , String paymentStatus , String paymentType ,String paymentChannel,
            String paymentStartDateFrom ,String paymentStartDateTo , String paymentEndDateFrom,
            String paymentEndDateTo)
{
	
	String sqlString = "SELECT * FROM VW_PAYMENT_DETAIL ";

	boolean andFlag = false;
	if(!paymentName.equals(""))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_NAME LIKE '%"+paymentName+"%' ";
	else
	sqlString += "  AND PAYMENT_NAME LIKE '%"+paymentName+"%' "   ;   
	andFlag = true;
	}
	if(!paymentStatus.equals("0"))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_STATUS_TYPE_ID = "+paymentStatus+" ";
	else
	sqlString += "  AND PAYMENT_STATUS_TYPE_ID = "+paymentStatus+" ";
	andFlag = true;
	}
	if(!paymentType.equals("0"))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_TYPE_ID = "+paymentType+" ";
	else
	sqlString += "  AND PAYMENT_TYPE_ID = "+paymentType+" ";
	andFlag = true;
	}
	if(!paymentStartDateFrom.equals("*"))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_START_DATE >= TO_DATE('"+paymentStartDateFrom+"','dd/mm/yyyy') ";
	else
	sqlString += "  AND PAYMENT_START_DATE >= TO_DATE('"+paymentStartDateFrom+"','dd/mm/yyyy') ";
	andFlag = true;
	}
	if(!paymentStartDateTo.equals("*"))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_START_DATE <= TO_DATE('"+paymentStartDateTo+"','dd/mm/yyyy') ";
	else
	sqlString += "  AND PAYMENT_START_DATE <= TO_DATE('"+paymentStartDateTo+"','dd/mm/yyyy') ";
	andFlag = true;
	}
	if(!paymentEndDateFrom.equals("*"))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_END_DATE >= TO_DATE('"+paymentEndDateFrom+"','dd/mm/yyyy') ";
	else
	sqlString += "  AND PAYMENT_END_DATE >= TO_DATE('"+paymentEndDateFrom+"','dd/mm/yyyy') ";
	andFlag = true;
	}
	if(!paymentEndDateTo.equals("*"))
	{
	if(andFlag == false)
	sqlString += "  WHERE PAYMENT_END_DATE <= TO_DATE('"+paymentEndDateTo+"','dd/mm/yyyy') ";
	else
	sqlString += "  AND PAYMENT_END_DATE <= TO_DATE('"+paymentEndDateTo+"','dd/mm/yyyy') ";
	andFlag = true;
	}
	if(!paymentChannel.equals("0"))
	{
	if(andFlag == false)
	sqlString += "  where payment_detail_id in (select payment_detail_id from payment_commission where "+
	"commission_detail_id in (select commission_detail_id from commission_detail where commission_channel_id = '"+paymentChannel+"'))";
	else
	sqlString += "  and payment_detail_id in (select payment_detail_id from payment_commission where "+
	"commission_detail_id in (select commission_detail_id from commission_detail where commission_channel_id = '"+paymentChannel+"'))";
	andFlag = true;
	}
	sqlString += " ORDER BY PAYMENT_STATUS_TYPE_ID";
	
	Vector<PaymentModel> paymentSearchResult = new Vector<PaymentModel>();
	Statement stmt = null ;
	try
	{
		
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlString) ;
		while(rs.next())
		{		
			paymentSearchResult.add(new PaymentModel(con,rs));		
		}
		rs.close();
	}
	catch(Exception e)
	{
		System.out.println(sqlString);
		e.printStackTrace();
	}
	finally
	{
		DBUtil.close(stmt);
	}
	
return paymentSearchResult;
}
	
	
  public static Vector<PaymentModel> getPaymentByFilter(Connection con , String paymentName , String paymentStatus , String paymentType ,
                                          String paymentStartDateFrom ,String paymentStartDateTo , String paymentEndDateFrom,
                                          String paymentEndDateTo)throws Exception
  {
    Statement stmt = con.createStatement();
    
    String sqlString = "SELECT * FROM VW_PAYMENT_DETAIL ";
    boolean andFlag = false;
    if(!paymentName.equals(""))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_NAME LIKE '%"+paymentName+"%' ";
      else
        sqlString += "  AND PAYMENT_NAME LIKE '%"+paymentName+"%' "   ;   
      andFlag = true;
    }
    if(!paymentStatus.equals("0"))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_STATUS_TYPE_ID = "+paymentStatus+" ";
      else
        sqlString += "  AND PAYMENT_STATUS_TYPE_ID = "+paymentStatus+" ";
      andFlag = true;
    }
    if(!paymentType.equals("0"))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_TYPE_ID = "+paymentType+" ";
      else
        sqlString += "  AND PAYMENT_TYPE_ID = "+paymentType+" ";
      andFlag = true;
    }
    if(!paymentStartDateFrom.equals("*"))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_START_DATE >= TO_DATE('"+paymentStartDateFrom+"','dd/mm/yyyy') ";
      else
        sqlString += "  AND PAYMENT_START_DATE >= TO_DATE('"+paymentStartDateFrom+"','dd/mm/yyyy') ";
      andFlag = true;
    }
    if(!paymentStartDateTo.equals("*"))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_START_DATE <= TO_DATE('"+paymentStartDateTo+"','dd/mm/yyyy') ";
      else
        sqlString += "  AND PAYMENT_START_DATE <= TO_DATE('"+paymentStartDateTo+"','dd/mm/yyyy') ";
      andFlag = true;
    }
    if(!paymentEndDateFrom.equals("*"))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_END_DATE >= TO_DATE('"+paymentEndDateFrom+"','dd/mm/yyyy') ";
      else
        sqlString += "  AND PAYMENT_END_DATE >= TO_DATE('"+paymentEndDateFrom+"','dd/mm/yyyy') ";
      andFlag = true;
    }
    if(!paymentEndDateTo.equals("*"))
    {
      if(andFlag == false)
        sqlString += "  WHERE PAYMENT_END_DATE <= TO_DATE('"+paymentEndDateTo+"','dd/mm/yyyy') ";
      else
        sqlString += "  AND PAYMENT_END_DATE <= TO_DATE('"+paymentEndDateTo+"','dd/mm/yyyy') ";
      andFlag = true;
    }
    sqlString += " ORDER BY PAYMENT_STATUS_TYPE_ID";
  
    //Utility.logger.debug(sqlString);
   System.out.println(sqlString);
    ResultSet rs = stmt.executeQuery(sqlString) ;
   
   Vector<PaymentModel> paymentSearchResult = new Vector<PaymentModel>();
   while(rs.next())
   {     
     paymentSearchResult.add(new PaymentModel(con,rs));     
   }
   
   
   return paymentSearchResult;
  }
  public static void addNewPayment(Connection con, String paymentName , String paymentType ,String paymentStatus,
                                    String paymentStartDate , String paymentEndDate ,String userID)
  {
	Statement stmt=null;
	  try
	{
		stmt = con.createStatement();
	    Long paymentDetailID = DBUtil.getSequenceNextVal(stmt,"SEQ_PAYMENT_DETAIL");
	   // int payment_detail_id = paymentDetailID.intValue();
	    String sqlString = "INSERT INTO PAYMENT_DETAIL(PAYMENT_DETAIL_ID,PAYMENT_NAME,PAYMENT_STATUS_TYPE_ID,PAYMENT_TYPE_ID,PAYMENT_START_DATE,PAYMENT_END_DATE) "+
	                        " VALUES("+paymentDetailID+",'"+paymentName+"',"+paymentStatus+","+paymentType +",TO_DATE('"+paymentStartDate+
	                        "','DD/MM/YYYY'),TO_DATE('"+paymentEndDate+"','DD/MM/YYYY'))";
	       
	    stmt.execute(sqlString);            
	    sqlString = "INSERT INTO PAYMENT_STATUS (PAYMENT_STATUS_ID, PAYMENT_DETAIL_ID,PAYMENT_STATUS_TYPE_ID,PAYMENT_STATUS_DATE,USER_ID)"+
	                " VALUES(SEQ_PAYMENT_STATUS.nextval,"+paymentDetailID+",1,TO_DATE(SYSDATE,'dd/mm/yyyy'),"+userID+")";
	    stmt.execute(sqlString);
	        
	   
	}
	  catch(Exception e)
	  {
		  
	  }
    finally
    {
       DBUtil.close(stmt);	
    }
  }
  
  public static void updatePaymentStatus(Connection con , String paymentID , String newPaymentStatus ,
                                         String userID )
  {
	String sql = " UPDATE PAYMENT_DETAIL SET PAYMENT_STATUS_TYPE_ID = "+newPaymentStatus+" WHERE PAYMENT_DETAIL_ID="+paymentID;
	DBUtil.executeSQL(sql, con);
	
    sql = "INSERT INTO PAYMENT_STATUS (PAYMENT_STATUS_ID, PAYMENT_DETAIL_ID,PAYMENT_STATUS_TYPE_ID,PAYMENT_STATUS_DATE,USER_ID)"+
                " VALUES(SEQ_PAYMENT_STATUS.nextval,"+paymentID+","+newPaymentStatus+",TO_DATE(SYSDATE,'dd/mm/yyyy'),"+userID+")";
    DBUtil.executeSQL(sql, con);
    
  }
  public static Vector getPaymentCommission(Connection con, String paymentID)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "SELECT * FROM VW_COMMISSION_DETAIL WHERE COMMISSION_START_DATE >= (SELECT PAYMENT_START_DATE FROM "+
                       " VW_PAYMENT_DETAIL WHERE PAYMENT_DETAIL_ID = "+paymentID+") AND COMMISSION_END_DATE <=(SELECT "+
                       " PAYMENT_END_DATE FROM VW_PAYMENT_DETAIL WHERE PAYMENT_DETAIL_ID = "+paymentID+") "+
                       "AND COMMISSION_STATUS_TYPE_ID= 3";
    ResultSet rs = stmt.executeQuery(sqlString);
    Utility.logger.debug(sqlString);    
    CommissionModel commissionModel = null;
    Vector commissionList = new Vector();
    while(rs.next())
    {
      commissionModel = new CommissionModel(con,rs);
      commissionList.add(commissionModel);
      commissionModel =null;
    }
    rs.close();
    stmt.close();
    return commissionList;
  }
  public static Vector getCommissionFromPayment(Connection con , String paymentID)throws Exception
  {
    Statement stmt = con.createStatement();    
    String sqlString = "SELECT * FROM VW_COMMISSION_DETAIL WHERE COMMISSION_DETAIL_ID IN"+
                "(SELECT COMMISSION_DETAIL_ID FROM PAYMENT_COMMISSION WHERE PAYMENT_DETAIL_ID="+paymentID+")";
    Utility.logger.debug(sqlString);
    ResultSet rs = stmt.executeQuery(sqlString);
    rs =stmt.executeQuery(sqlString);
    CommissionModel commissionModel = null;
    Vector commissionList = new Vector();
    
    while(rs.next())
    {
      commissionModel = new CommissionModel(con,rs);
      commissionList.add(commissionModel);
      commissionModel =null;    
    }
    return commissionList;
  }

  public static void addCommissionToPayment(Connection con , String commissionID ,String PaymentID)
  {
	  String sql = "INSERT INTO PAYMENT_COMMISSION (PAYMENT_COMMISSION_ID , PAYMENT_DETAIL_ID,COMMISSION_DETAIL_ID)"+
      "VALUES(SEQ_PAYMENT_COMMISSION.nextval,"+PaymentID+","+commissionID+")";	  
	DBUtil.executeSQL(sql, con);
  }

  public static void removeCommissionFromPayment(Connection con , String commissionID,String PaymentID)
  {	     
    String sql = " DELETE FROM PAYMENT_COMMISSION WHERE COMMISSION_DETAIL_ID="+commissionID+" AND PAYMENT_DETAIL_ID="+PaymentID;
    DBUtil.executeSQL(sql, con);   
  }
  
  
  public static PaymentModel getPaymentByID(Connection con , String paymentID)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "SELECT * FROM VW_PAYMENT_DETAIL WHERE PAYMENT_DETAIL_ID = "+paymentID;
    System.out.println(sqlString);
    ResultSet rs = stmt.executeQuery(sqlString);
    PaymentModel paymentModel = null;
    if(rs.next())
    {
      paymentModel = new PaymentModel(con,rs);
    }
    rs.close();
    stmt.close();    
    return paymentModel;
  }
  
  public static void deleteCommissionPayment(Connection  con,String paymentId)
  {
	  String strSql = "DELETE FROM PAYMENT_COMMISSION WHERE PAYMENT_DETAIL_ID='"+paymentId+"'";
	  DBUtil.executeSQL(strSql, con);	 
  }

  public static Vector getAllPaymentType(Connection con,boolean checkStatus)throws Exception
  {
    Statement stmt = con.createStatement();
    String condition="";
    if (checkStatus)
    {
      condition="and PAYMENT_TYPE.PAYMENT_TYPE_STATUS_ID=1";
    }
    String sqlString = "SELECT * FROM PAYMENT_TYPE,PAYMENT_TYPE_STATUS,cam_payment_type_method where PAYMENT_TYPE.PAYMENT_TYPE_STATUS_ID=PAYMENT_TYPE_STATUS.PAYMENT_TYPE_STATUS_ID and payment_type.PAYMENT_METHOD_TYPE_ID = cam_payment_type_method.PAYMENT_TYPE_METHOD_ID "+condition+" order by PAYMENT_TYPE.PAYMENT_TYPE_NAME";

    System.out.println(sqlString);
    
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentType = new Vector();
    while(rs.next())
    {
      PaymentTypeDTO ptdto = new PaymentTypeDTO();
      int intTypeId = rs.getInt("PAYMENT_TYPE_ID");
     ptdto.setTypeId(new Integer(intTypeId)); ;
     ptdto.setTypeName(rs.getString("PAYMENT_TYPE_NAME"));
     ptdto.setTypeStatusId(new Integer (rs.getInt("PAYMENT_TYPE_STATUS_ID"))); 
     ptdto.setTypeStatusName(rs.getString("PAYMENT_TYPE_STATUS_NAME"));
     ptdto.setMethodTypeId(new Integer(rs.getInt("PAYMENT_METHOD_TYPE_ID")));
     ptdto.setMethodTypeName(rs.getString("PAYMENT_TYPE_METHOD_NAME"));
     ptdto.setTypeCategories(getComsionCategry(con,intTypeId));
     

      allPaymentType.addElement(ptdto);
    }
    rs.close();
    stmt.close();    
    return allPaymentType;
  }


 public static Vector getComsionCategry(Connection con,int paymentId)throws Exception
  {
    Statement stmt = con.createStatement();
    String condition=null;

    condition="and COMMISSION_CONTROLLER.PAYMENT_TYPE_ID="+paymentId;

    String sqlString = "select * from COMMISSION_TYPE_CATEGORY,COMMISSION_CONTROLLER where COMMISSION_CONTROLLER.COMMISSION_TYPE_CATEGORY_ID =COMMISSION_TYPE_CATEGORY.COMMISSION_TYPE_CATEGORY_ID "+condition;
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentTypeCategories = new Vector();
    while(rs.next())
    {
      CommissionCategoryDTO ccdto = new CommissionCategoryDTO();
      ccdto.setCommissionTypeCategoryId(new Integer(rs.getInt("COMMISSION_TYPE_CATEGORY_ID")));
      ccdto.setCommissionTypeCategoryMandatory(rs.getInt("COMMISSION_IS_MANDATORY")+"");
      ccdto.setCommissionTypeCategoryName(rs.getString("COMMISSION_TYPE_CATEGORY_NAME"));
      ccdto.setPaymentTypeId(new Integer(rs.getInt("PAYMENT_TYPE_ID")));
     

      allPaymentTypeCategories.addElement(ccdto);
    }
    rs.close();
    stmt.close();    
    return allPaymentTypeCategories;
  }  

   public static Vector getAllComsionCategry(Connection con)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "select * from COMMISSION_TYPE_CATEGORY order by COMMISSION_TYPE_CATEGORY_NAME";
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentTypeCategories = new Vector();
    while(rs.next())
    {
      CommissionCategoryDTO ccdto = new CommissionCategoryDTO();
      ccdto.setCommissionTypeCategoryId(new Integer(rs.getInt("COMMISSION_TYPE_CATEGORY_ID")));
      ccdto.setCommissionTypeCategoryName(rs.getString("COMMISSION_TYPE_CATEGORY_NAME"));     
      allPaymentTypeCategories.addElement(ccdto);
    }
    rs.close();
    stmt.close();    
    return allPaymentTypeCategories;
  }  

  public static Vector getAllComsionConstrain(Connection con)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "select * from COMMISSION_CONSTRAINS order by id";
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allComisionConstrains = new Vector();
    while(rs.next())
    {
      CommissionConstrainDTO ccdto = new CommissionConstrainDTO();
      ccdto.setConstrianId(new Integer(rs.getInt("id")));
      ccdto.setConstrianName(rs.getString("name"));     

      allComisionConstrains.addElement(ccdto);
    }
    rs.close();
    stmt.close();    
    return allComisionConstrains;
  }  


  public static PaymentTypeDTO getPaymentType(Connection con,String paymentTypeId)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "SELECT * FROM PAYMENT_TYPE,PAYMENT_TYPE_STATUS,cam_payment_type_method where PAYMENT_TYPE.PAYMENT_TYPE_STATUS_ID=PAYMENT_TYPE_STATUS.PAYMENT_TYPE_STATUS_ID and    payment_type.PAYMENT_METHOD_TYPE_ID = cam_payment_type_method.PAYMENT_TYPE_METHOD_ID and PAYMENT_TYPE.PAYMENT_TYPE_ID="+paymentTypeId+" order by PAYMENT_TYPE.PAYMENT_TYPE_NAME";
    ResultSet rs = stmt.executeQuery(sqlString);
    PaymentTypeDTO ptdto=null;
    if(rs.next())
    {
       ptdto = new PaymentTypeDTO();
     ptdto.setTypeId(new Integer(rs.getInt("PAYMENT_TYPE_ID"))); ;
     ptdto.setTypeName(rs.getString("PAYMENT_TYPE_NAME"));
     ptdto.setTypeStatusId(new Integer (rs.getInt("PAYMENT_TYPE_STATUS_ID"))); 
    ptdto.setTypeStatusName(rs.getString("PAYMENT_TYPE_STATUS_NAME"));
    ptdto.setMethodTypeId(new Integer(rs.getInt("PAYMENT_METHOD_TYPE_ID")));
    ptdto.setMethodTypeName(rs.getString("PAYMENT_TYPE_METHOD_NAME"));
    ptdto.setAccrualId(rs.getString("PAYMENT_ACCRUAL_ID"));
    
    


    }
    rs.close();
    stmt.close();    
    return ptdto;
  }
  public static Vector getPaymentTypeStatus(Connection con)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "SELECT * FROM PAYMENT_TYPE_STATUS order by PAYMENT_TYPE_STATUS_NAME";
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentTypeStatus = new Vector();
    while(rs.next())
    {
      PaymentTypeStatusDTO ptsdto = new PaymentTypeStatusDTO();
     ptsdto.setStatusId(new Integer(rs.getInt("PAYMENT_TYPE_STATUS_ID"))); ;
     ptsdto.setStatusName(rs.getString("PAYMENT_TYPE_STATUS_NAME"));     

      allPaymentTypeStatus.addElement(ptsdto);
    }
    rs.close();
    stmt.close();    
    return allPaymentTypeStatus;
  }
  public static Vector getPaymentTypeMethods(Connection con)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "SELECT * FROM cam_payment_type_method order by PAYMENT_TYPE_METHOD_NAME";
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentTypeMethods = new Vector();
    while(rs.next())
    {
    	PaymentTypeTempModel ptmm = new PaymentTypeTempModel();
    	ptmm.setPAYMENT_TYPE_METHOD_ID(new Integer (rs.getInt("payment_type_method_id")));
    	ptmm.setPAYMENT_TYPE_METHOD_DESC(rs.getString("payment_type_method_desc"));
    	ptmm.setPAYMENT_TYPE_METHOD_NAME(rs.getString("payment_type_method_name"));

    	allPaymentTypeMethods.addElement(ptmm);
    }
    rs.close();
    stmt.close();    
    return allPaymentTypeMethods;
  }



  public static void updatePaymentTypeStatus(Connection con,int typeId,String typeStatusId)
  {
    String sql = "update PAYMENT_TYPE set PAYMENT_TYPE_STATUS_ID="+typeStatusId+" where PAYMENT_TYPE_ID="+typeId;
    DBUtil.executeSQL(sql, con);
  }
  public static void updatePaymentType(Connection con,int typeId,String typeName,String typeStatusId,String methodId,String accrualId)
  {
    String sql = "update PAYMENT_TYPE set PAYMENT_TYPE_NAME='"+typeName+"', PAYMENT_TYPE_STATUS_ID="+typeStatusId+" , PAYMENT_METHOD_TYPE_ID = "+methodId+" , PAYMENT_ACCRUAL_ID = "+accrualId+" where PAYMENT_TYPE_ID="+typeId;
    DBUtil.executeSQL(sql, con);
  }

   public static void insertPaymentType(Connection con,String typeName,String typeStatus,String methodId,String accrualId)
  {
	   String sql = "insert into PAYMENT_TYPE (PAYMENT_TYPE_ID,PAYMENT_TYPE_NAME,PAYMENT_TYPE_STATUS_ID,PAYMENT_METHOD_TYPE_ID,PAYMENT_ACCRUAL_ID)"+ 
	    "values (SEQ_PAYMENT_TYPE.nextval,'"+typeName+"',"+typeStatus+","+methodId+","+accrualId+")";
	   DBUtil.executeSQL(sql, con);  
  }

  public static void insertToCommissionControl(Connection con,String mandatory,String catId,String typeid)throws Exception
  {
    String sql = "insert into COMMISSION_CONTROLLER (COMMISSION_CONTROLLER_ID,COMMISSION_IS_MANDATORY,COMMISSION_TYPE_CATEGORY_ID,PAYMENT_TYPE_ID)"+ 
    "values (SEQ_COMMISSION_CONTROLLER.nextval,'"+mandatory+"',"+catId+","+typeid+")";
    
    DBUtil.executeSQL(sql, con);   
  }

   public static void deletePaymentType(Connection con,String typeid)throws Exception
  {
    String sql = "delete from COMMISSION_CONTROLLER where PAYMENT_TYPE_ID="+typeid;
    DBUtil.executeSQL(sql, con); 
  }
   public static Vector getPaymentCommissions(Connection con,String paymentId,boolean status)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "";
    if (status){
//    sqlString = "select PAYMENT_COMMISSION.COMMISSION_DETAIL_ID from PAYMENT_COMMISSION,COMMISSION_DETAIL,COMMISSION_TYPE_CATEGORY"+
//" where PAYMENT_COMMISSION.COMMISSION_DETAIL_ID = COMMISSION_DETAIL.COMMISSION_DETAIL_ID "+
// "       and COMMISSION_DETAIL.COMMISSION_TYPE_CATEGORY_ID = COMMISSION_TYPE_CATEGORY.COMMISSION_TYPE_CATEGORY_ID         "+
//  "      and PAYMENT_COMMISSION.PAYMENT_DETAIL_ID="+paymentId;
int paymentTypeId = getPaymentTypeByPaymentId(con,paymentId);
sqlString="select commission_type_category_id from COMMISSION_CONTROLLER,payment_type" +
" where COMMISSION_CONTROLLER.payment_type_id=payment_type.PAYMENT_TYPE_ID "+
" and commission_is_mandatory=1 and  COMMISSION_CONTROLLER.PAYMENT_TYPE_ID="+paymentTypeId+" group by commission_type_category_id";

  }
  else{
  sqlString="select cdt.COMMISSION_TYPE_CATEGORY_ID from Payment_Commission pct left join payment_detail pdt on pct.PAYMENT_DETAIL_ID = pdt.PAYMENT_DETAIL_ID"+  
" left join commission_detail cdt on pct.COMMISSION_DETAIL_ID = cdt.COMMISSION_DETAIL_ID"+
" where pct.PAYMENT_DETAIL_ID="+paymentId+" group by cdt.COMMISSION_TYPE_CATEGORY_ID";
//sqlString="select PAYMENT_COMMISSION.COMMISSION_DETAIL_ID,COMMISSION_CONTROLLER.COMMISSION_IS_MANDATORY from PAYMENT_COMMISSION,COMMISSION_DETAIL,COMMISSION_TYPE_CATEGORY,COMMISSION_CONTROLLER"+
//" where PAYMENT_COMMISSION.COMMISSION_DETAIL_ID = COMMISSION_DETAIL.COMMISSION_DETAIL_ID "+
// "       and COMMISSION_DETAIL.COMMISSION_TYPE_CATEGORY_ID = COMMISSION_TYPE_CATEGORY.COMMISSION_TYPE_CATEGORY_ID"+
//  "      and COMMISSION_TYPE_CATEGORY.COMMISSION_TYPE_CATEGORY_ID = COMMISSION_CONTROLLER.COMMISSION_TYPE_CATEGORY_ID "+
//   "     and PAYMENT_COMMISSION.PAYMENT_DETAIL_ID="+paymentId;
  }
//  System.out.println("error query is "+sqlString);
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentTypeStatus = new Vector();
    while(rs.next())
    {
      

      allPaymentTypeStatus.addElement(new Integer (rs.getInt("COMMISSION_TYPE_CATEGORY_ID")));
    }
    rs.close();
    stmt.close();    
    return allPaymentTypeStatus;
  }

   public static Vector<Integer> getPaymentTypeMandatoryCommissionCategory(Connection con,String paymentTypeId)
  {
	   Vector<Integer> allPaymentTypeStatus = new Vector<Integer>();
	   Statement stmt=null;
	   try
	   {
		    stmt = con.createStatement();		   
		    String sqlString = "select COMMISSION_TYPE_CATEGORY_ID from COMMISSION_CONTROLLER where COMMISSION_IS_MANDATORY=1 and PAYMENT_TYPE_ID="+paymentTypeId;		  
		    ResultSet rs = stmt.executeQuery(sqlString);
	
		    while(rs.next())
		    {      
		      allPaymentTypeStatus.addElement(new Integer (rs.getInt("COMMISSION_TYPE_CATEGORY_ID")));
		    }
		    rs.close();		   	   
	   }        
	   catch(Exception e)
	   {		 
				e.printStackTrace();   
	   }
	   finally
	   {
		   DBUtil.close(stmt);
	   }
    return allPaymentTypeStatus;
  }

   public static Vector getPaymentTypeCommissionCategory(Connection con,String paymentId)throws Exception
  {
    Statement stmt = con.createStatement();
    String sqlString = "";

    sqlString = "select COMMISSION_DETAIL.COMMISSION_TYPE_CATEGORY_ID from PAYMENT_COMMISSION,COMMISSION_DETAIL where PAYMENT_COMMISSION.COMMISSION_DETAIL_ID = COMMISSION_DETAIL.COMMISSION_DETAIL_ID and PAYMENT_DETAIL_ID="+paymentId;
    ResultSet rs = stmt.executeQuery(sqlString);
    Vector allPaymentTypeStatus = new Vector();
    while(rs.next())
    {
      allPaymentTypeStatus.addElement(new Integer (rs.getInt("COMMISSION_TYPE_CATEGORY_ID")));
    }
    rs.close();
    stmt.close();    
    return allPaymentTypeStatus;
  }

public static int getPaymentTypeByPaymentId(Connection con,String paymentId)throws Exception
  {
	int value =0;
	String sql = "select PAYMENT_TYPE_ID from PAYMENT_DETAIL where PAYMENT_DETAIL_ID="+paymentId;
	value = DBUtil.executeQuerySingleValueInt(sql,"PAYMENT_TYPE_ID",con);        
    return value;
  }
  public static void AddToAccountModule(Connection con,String paymentId,String maker_id )throws Exception
  {
    CallableStatement proc = null;
    try
   {

      proc = con.prepareCall("{ call cam_payment_pkg.INSRET_NEW_PAYMENT(?,?) }");
      proc.setInt(1, Integer.parseInt(paymentId));
      proc.setInt(2, Integer.parseInt(maker_id));
      proc.execute();
   }
   finally
   {
	   if(proc!=null)
      try
      {proc.close();}catch (SQLException e) {}

   }

    

  }
  
  public static String selectPaymentName (Connection con,String paymentId)
  {
	  String paymentName = "";
	  String strSql = "select PAYMENT_NAME from VW_payment_DETAIL where payment_detail_id = '"+paymentId+"' ";
	  paymentName=DBUtil.executeQuerySingleValueString(strSql,"PAYMENT_NAME",con);	  
	  return paymentName ;
  }
  
  
  private PaymentDAO()
  {
  }
}