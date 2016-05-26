package com.mobinil.sds.core.system.acc.dao;
import java.sql.*;
import java.util.*;

import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.system.acc.model.*;
import com.mobinil.sds.core.system.sop.schemas.model.ProductModel;
public class AccDAO {
	
	public static Vector getAllChannels(Connection con)
	{
		Vector channel = new Vector();
	    channelModel channelModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      System.out.println("hi this checked");
	      String strSql = "select * from gen_channel";
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  channelModel = new channelModel(res);
	    	  channel.add(channelModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return channel;
	}
	
	public static Vector getAllGroups(Connection con)
	{
		Vector groups = new Vector();
		GroupModel groupModel = null;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select * from CR_TRANSACTION_GROUP";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next())
			{
				groupModel = new GroupModel(res);
				groups.add(groupModel);
			}
			res.close();
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
		return groups;
	}
	
	public static void deleteTransactionGroup(Connection con,String groupId)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "delete from CR_TRANSACTION_GROUP where CR_TRANSACTION_GROUP_ID ='"+groupId+"'";
	      System.out.println("The delete query isssss " + strSql);
	      Utility.logger.debug("The Query isssssssssssssssss " + strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	public static GroupModel selectTranasactionGroup (Connection con,String groupId)
	  {
	    GroupModel groupModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from CR_TRANSACTION_GROUP where CR_TRANSACTION_GROUP_ID ='"+groupId+"'";
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  groupModel = new GroupModel();
	    	  groupModel.setGroupId(res.getString(groupModel.CR_TRANSACTION_GROUP_ID));
	    	  groupModel.setGroupName(res.getString(groupModel.CR_TRANSACTION_GROUP_NAME));
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return groupModel;
	  }
	
	public static void updateTransactionGroup(Connection con,String oldGroupId,String newGroupId,String groupName)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "update CR_TRANSACTION_GROUP set CR_TRANSACTION_GROUP_ID = '"+newGroupId+"' , CR_TRANSACTION_GROUP_NAME = '"+groupName+"' where CR_TRANSACTION_GROUP_ID ='"+oldGroupId+"'";
	      System.out.println("The delete query isssss " + strSql);
	      Utility.logger.debug("The Query isssssssssssssssss " + strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	public static void insertTransactionGroup(Connection con,String groupId,String groupName)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "insert into CR_TRANSACTION_GROUP (CR_TRANSACTION_GROUP_ID,CR_TRANSACTION_GROUP_NAME) values "+
							"('"+groupId+"','"+groupName+"')";
			stat.executeUpdate(strSql);
		      stat.close();
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	}
	
	public static Vector getProductValues(Connection con,String channelId)
	{
		Vector productValues = new Vector();
		ProductValuesModel productValuesModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select gen_product.product_id,gen_product.product_name,acc_product_value.product_value"+
	      				  " from gen_product left join acc_product_value"+
	      				  " on gen_product.product_id = acc_product_value.product_id and channel_id = '"+channelId+"'";
	      System.out.println("The query isssss " + strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  productValuesModel = new ProductValuesModel(res);
	    	  productValues.add(productValuesModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return productValues;
	}
	
	public static void deleteProductValue(Connection con,String channelId)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "delete from acc_product_value where channel_id = '"+channelId+"'";
			System.out.println("The delete query issssssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static void insertProductValue(Connection con,int productId,String productValue,String channelId)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "insert into acc_product_value(product_id,product_value,channel_id)"+
							" values('"+productId+"','"+productValue+"','"+channelId+"')";
			System.out.println("The insert query isssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static Vector selectDcmValue(Connection con,String channelId)
	{
		Vector dcmValues = new Vector();
		DcmValuesModel dcmValuesModel = null;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select * from ACC_CHANNEL_QUERY where channel_id = '"+channelId+"'";
			System.out.println("The first query issssssssss " + strSql);
			ResultSet res= stat.executeQuery(strSql);
			String channelQuery = "";
		      while(res.next())
		      {
		    	  channelQuery = res.getString("CHANNEL_QUERY");
		      }
		     
			String sqlQuery = "select b.dcm_id,b.dcm_name,dcm_value from ("+channelQuery+")b" +
							  " ,acc_dcm_value where b.dcm_id = acc_dcm_value.dcm_id(+)";
			System.out.println("the second query issssss " + sqlQuery);
		      Statement stat2 = con.createStatement();
		      ResultSet resQuery = stat.executeQuery(sqlQuery);
		      while(resQuery.next())
		      {
		    	  dcmValuesModel = new DcmValuesModel(resQuery);
		    	  dcmValues.add(dcmValuesModel);
		      }
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
		return dcmValues;
	}
	
	public static void deleteDcmValue(Connection con,String channelId)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "delete from acc_dcm_value where channel_id = '"+channelId+"'";
			System.out.println("The delete query issssssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static void insertDcmValue(Connection con,String dcmCode,String dcmName,String dcmValue,String channelId)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "insert into acc_dcm_value(DCM_ID,DCM_NAME,DCM_VALUE,channel_id)"+
							" values('"+dcmCode+"','"+dcmName+"','"+dcmValue+"','"+channelId+"')";
			System.out.println("The insert query isssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static Vector getDcmProductCalculation(Connection con,String channelId,String activationDateFrom,String activationDateTo)
	{
		Vector dcmProduct = new Vector();
		DcmProductEquationModel dcmProductEquationModel = null;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select dcm_id,dcm_name,dcm_value,product_name,line_count "+
							",product_id,product_value,transaction_Type_id,"+
							"(line_count * product_value + line_count * product_value * dcm_value) as evalue "+
							"from "+
							"(select dem_logistics_prepaid.dcm_id,dcm_name, count(*)line_count, "+
							"dem_logistics_prepaid.product_id,product_name,"+
							"transaction_type_id ,product_value,dcm_value "+
							"from dem_logistics_prepaid,ACC_PRODUCT_VALUE,acc_dcm_value ,gen_product "+
							"where dem_logistics_prepaid.product_id = ACC_PRODUCT_VALUE.PRODUCT_ID "+
							"and dem_logistics_prepaid.DCM_ID = acc_dcm_value.DCM_ID "+
							"and ACC_PRODUCT_VALUE.channel_id = '"+channelId+"'"+
							"and gen_product.product_id = acc_product_value.product_id "+
							"AND transaction_type_id IN (select transaction_type_id from cr_transaction_type where channel_id = '"+channelId+"') "+
							"and activation_date >= to_date('"+activationDateFrom+"','mm/dd/yyyy')"+
							"and activation_date <= to_date('"+activationDateTo+"','mm/dd/yyyy')"+
							"group by dem_logistics_prepaid.dcm_id, dem_logistics_prepaid.product_id,product_name,"+
							"transaction_type_id,product_value,dcm_name,dcm_value "+
							"union "+
							"select dem_logistics_postpaid.dcm_id,dcm_name, count(*) line_count, "+
							"dem_logistics_postpaid.product_id,product_name, transaction_type_id,product_value,dcm_value "+
							"from "+
							"dem_logistics_postpaid,ACC_PRODUCT_VALUE,acc_dcm_value,gen_product "+
							"where dem_logistics_postpaid.PRODUCT_ID = ACC_PRODUCT_VALUE.PRODUCT_ID "+
							"and dem_logistics_postpaid.DCM_ID = acc_dcm_value.DCM_ID "+
							"and ACC_PRODUCT_VALUE.channel_id = '"+channelId+"'"+
							"and gen_product.product_id = acc_product_value.product_id "+
							"AND transaction_type_id in (select transaction_type_id from cr_transaction_type where channel_id = '"+channelId+"') "+
							"and activation_date >= to_date('"+activationDateFrom+"','mm/dd/yyyy')"+
							"and activation_date <= to_date('"+activationDateTo+"','mm/dd/yyyy')"+
							"group by dem_logistics_postpaid.dcm_id,dcm_name, dem_logistics_postpaid.product_id"+
							",product_name,transaction_type_id,product_value,dcm_value)";
			
			System.out.println("The calculation query isssss " + strSql);
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				dcmProductEquationModel = new DcmProductEquationModel(res);
				dcmProduct.add(dcmProductEquationModel);
			}
			res.close();
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
		return dcmProduct;
	}
	
	public static Vector getTransactionType(Connection con)
	{
		Vector transactionType = new Vector();
		TransactionTypeModel transactionTypeModel = null;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select * from cr_transaction_type";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				transactionTypeModel = new TransactionTypeModel(res);
				transactionType.add(transactionTypeModel);
			}
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
		return transactionType;
	}
	
	public static void updateTransactionType(Connection con,String transactionTypeId,String channelId,String groupId)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "update cr_transaction_type set channel_id = '"+channelId+"' , transaction_group_id = '"+groupId+"' where transaction_type_id = '"+transactionTypeId+"'";
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	
	
	public static Long insertAccrualValue (Connection con,String channelId,String activationDateFrom,String activationDateTo)
	{
		Long accrualValueId = null;
		try
		{
			accrualValueId = Utility.getSequenceNextVal(con, "ACC_ACCRUAL_VALUE_SEQ");
			Statement stat = con.createStatement();
			String strSql = "insert into acc_accrual_value (acc_accrual_value_id,channel_id,activation_date_from,activation_date_to)values "+
							"('"+accrualValueId+"','"+channelId+"','"+activationDateFrom+"','"+activationDateTo+"')";
			System.out.println("The insert query issssssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
		return accrualValueId;
	}
	
	public static void insertAccrualValueDetail (Connection con,Long accrrualValueId,String dcmId,String productId,String lineCount,String dcmValue,String productValue,String accrualValue,String dcmName,String productName )
	{
		Long accrualValueDetailId = null;
		try
		{
			accrualValueDetailId = Utility.getSequenceNextVal(con, "ACC_ACCRUAL_VALUE_DETAIL_SEQ");
			Statement stat = con.createStatement();
			String strSql = "insert into acc_accrual_value_detail (acc_accrual_value_detail_id,dcm_id,product_id,line_count,dcm_value,product_value,accrual_value,acc_accrual_value_id,DCM_NAME,PRODUCT_NAME) values"+
							"('"+accrualValueDetailId+"','"+dcmId+"','"+productId+"','"+lineCount+"','"+dcmValue+"','"+productValue+"','"+accrualValue+"','"+accrrualValueId+"','"+dcmName+"','"+productName+"')";
			System.out.println("The detail query issssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static Vector getAllAccrualData(Connection con)
	{
		Vector accrualData = new Vector();
	    AccrualDataModel accrualDataModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select acc_accrual_value_id,acc_accrual_value.channel_id,activation_date_from,activation_date_to,channel_name"+
	      				  " from acc_accrual_value,gen_channel where acc_accrual_value.channel_id = gen_channel.channel_id";
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  accrualDataModel = new AccrualDataModel(res);
	    	  accrualData.add(accrualDataModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return accrualData;
	}
	
	public static Vector getAccrualDataById(Connection con,String accrualId)
	{
		Vector accrualDetail = new Vector();
		AccrualDetailDataModel accrualDetailDataModel = null;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select * from ACC_ACCRUAL_VALUE_DETAIL where ACC_ACCRUAL_VALUE_ID = '"+accrualId+"'";
			ResultSet res = stat.executeQuery(strSql);
			while(res.next())
			{
				accrualDetailDataModel = new AccrualDetailDataModel(res);
				accrualDetail.add(accrualDetailDataModel);
			}
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
		return accrualDetail;
	}
	
	public static Vector getAllWithHoldTax(Connection con)
	{
		Vector withHoldTaxVec = new Vector();
	    WithHoldTaxModel withHoldTaxModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ACC_WITH_HOLD_TAX";
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  withHoldTaxModel = new WithHoldTaxModel(res);
	    	  withHoldTaxVec.add(withHoldTaxModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return withHoldTaxVec;
	}
	
	public static WithHoldTaxModel selectWithHoldTax (Connection con,String dcmCode,float withHoldTaxFloat)
	  {
		WithHoldTaxModel withHoldTaxModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ACC_WITH_HOLD_TAX where DCM_CODE ='"+dcmCode+"' and WITH_HOLD_TAX = '"+withHoldTaxFloat+"'";
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  withHoldTaxModel = new WithHoldTaxModel();
	    	  withHoldTaxModel.setDcmCode(res.getString(withHoldTaxModel.DCM_CODE));
	    	  withHoldTaxModel.setWithHoldTax(res.getFloat(withHoldTaxModel.WITH_HOLD_TAX));
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return withHoldTaxModel;
	  }
	
	public static void deleteWithHoldTax(Connection con,String dcmCode,String withHoldTax)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "delete from ACC_WITH_HOLD_TAX where DCM_CODE ='"+dcmCode+"' AND WITH_HOLD_TAX = '"+withHoldTax+"'";
	      System.out.println("The delete query isssss " + strSql);
	      Utility.logger.debug("The Query isssssssssssssssss " + strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	public static boolean insertWithHoldTax(Connection con,String dcmCode,float withHoldTax)
	{
		boolean check = true;
		float factor = 100.0f;
		float netWithHoldTax = 0.0f;
		try
		{
			netWithHoldTax = withHoldTax/factor;
			System.out.println("With hold tax issssssssss " + netWithHoldTax);
			Statement stat = con.createStatement();
			String strSql = "select * from ACC_WITH_HOLD_TAX where DCM_CODE = '"+dcmCode+"'";
			ResultSet res = stat.executeQuery(strSql);
			if(res.next())
			{
				check = false;
			}
			else
			{
			Statement stat2 = con.createStatement();
			String strSql2 = "insert into ACC_WITH_HOLD_TAX (DCM_CODE,WITH_HOLD_TAX) values "+
							"('"+dcmCode+"','"+netWithHoldTax+"')";
			System.out.println("The insert query issssssssssssss " + strSql2);
			stat2.executeUpdate(strSql2);
		      stat.close();}
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    }
		return check;
	}
	
	public static boolean updateWithHoldTax(Connection con,String oldDcmCode,String newDcmCode,float oldWithHoldTax,float newWithHoldTax)
	  {
		boolean check = true;
		float factor = 100.0f;
		float netOldWithHoldTax = 0.0f;
		float newNewWithHoldTax = 0.0f;
	    try
	    {
	    	netOldWithHoldTax = oldWithHoldTax/factor;
	    	newNewWithHoldTax = newWithHoldTax/factor;
	    	Statement stat = con.createStatement();
			String strSql = "select * from ACC_WITH_HOLD_TAX where DCM_CODE = '"+newDcmCode+"'";
			ResultSet res = stat.executeQuery(strSql);
			if(res.next())
			{
				check = false;
			}
			else
			{
	      Statement stat2 = con.createStatement();
	      String strSql2 = "update ACC_WITH_HOLD_TAX set dcm_code = '"+newDcmCode+"' , with_hold_tax = '"+newNewWithHoldTax+"' where dcm_code = '"+oldDcmCode+"' and with_hold_tax ='"+netOldWithHoldTax+"'";
	      System.out.println("The update query isssss " + strSql2);
	      stat2.executeUpdate(strSql2);
	      stat.close();
			}
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return check;
	  }
	
	public static Vector getAllFranchiseGroup(Connection con)
	{
		Vector franchiseGroupVec = new Vector();
	    FranchiseGroupModel franchiseGroupModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ACC_FRANCHISE_GROUP";
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  franchiseGroupModel = new FranchiseGroupModel(res);
	    	  franchiseGroupVec.add(franchiseGroupModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return franchiseGroupVec;
	}
	
	public static FranchiseGroupModel selectFranchiseGroup (Connection con,String franchiseGroupId)
	  {
		FranchiseGroupModel franchiseGroupModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ACC_FRANCHISE_GROUP where acc_franchise_group_id  ='"+franchiseGroupId+"'";
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  franchiseGroupModel = new FranchiseGroupModel();
	    	  franchiseGroupModel.setFranchiseGroupId(res.getString(franchiseGroupModel.ACC_FRANCHISE_GROUP_ID));
	    	  franchiseGroupModel.setFranchiseGroupName(res.getString(franchiseGroupModel.FRANCHISE_GROUP_NAME));
	    	  franchiseGroupModel.setFranchiseGroupDescription(res.getString(franchiseGroupModel.FRANCHISE_GROUP_DESCRIPTION));
	    	  
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return franchiseGroupModel;
	  }
	
	public static void updateFranchiseGroup(Connection con,String franchiseGroupId,String franchiseGroupName,String franchiseGroupDescription)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "update ACC_FRANCHISE_GROUP set FRANCHISE_GROUP_NAME = '"+franchiseGroupName+"' , FRANCHISE_GROUP_DESCRIPTION = '"+franchiseGroupDescription+"' where ACC_FRANCHISE_GROUP_ID = '"+franchiseGroupId+"'";
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	
	
	public static void insertFranchiseGroup (Connection con,String franchiseGroupName,String franchiseGroupDescription)
	{
		try
		{
			Long franchiseGroupId  = Utility.getSequenceNextVal(con, "ACC_FRANCHISE_GROUP_SEQ");
			Statement stat = con.createStatement();
			String strSql = "insert into ACC_FRANCHISE_GROUP (ACC_FRANCHISE_GROUP_ID,FRANCHISE_GROUP_NAME,FRANCHISE_GROUP_DESCRIPTION)values "+
							"('"+franchiseGroupId+"','"+franchiseGroupName+"','"+franchiseGroupDescription+"')";
			System.out.println("The insert query issssssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static void deleteFranchiseGroup(Connection con,String franchiseGroupId)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "delete from ACC_FRANCHISE_GROUP where ACC_FRANCHISE_GROUP_ID ='"+franchiseGroupId+"' ";
	      System.out.println("The delete query isssss " + strSql);
	      Utility.logger.debug("The Query isssssssssssssssss " + strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	
	public static Vector getFranchiseListByGroupId(Connection con,String franchiseGroupId)
	{
		Vector franchiseListVec = new Vector();
	    FranchiseListModel franchiseListModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ACC_FRANCHISE_GROUP_ASSIGNMENT WHERE FRANCHISE_GROUP_ID = '"+franchiseGroupId+"'";
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  franchiseListModel = new FranchiseListModel(res);
	    	  franchiseListVec.add(franchiseListModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return franchiseListVec;
	}
	
	public static void insertFranchiseList (Connection con,String franchiseGroupId ,String franchiseGroup)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "insert into ACC_FRANCHISE_GROUP_ASSIGNMENT (FRANCHISE_GROUP_ID,FRANCHISE_CODE)values "+
							"('"+franchiseGroupId+"','"+ franchiseGroup+"')";
			System.out.println("The insert query issssssss " + strSql);
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static FranchiseListModel selectFranchiseList (Connection con,String franchiseGroupId,String franchiseCode)
	  {
		FranchiseListModel franchiseListModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ACC_FRANCHISE_GROUP_ASSIGNMENT where FRANCHISE_GROUP_ID  ='"+franchiseGroupId+"' and FRANCHISE_CODE = '"+franchiseCode+"'";
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  franchiseListModel = new FranchiseListModel();
	    	  franchiseListModel.setFranchiseGroupId(res.getString(franchiseListModel.FRANCHISE_GROUP_ID));
	    	  franchiseListModel.setFranchiseCode(res.getString(franchiseListModel.FRANCHISE_CODE));
	    	  
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return franchiseListModel;
	  }
	
	public static void deleteFranchiseList(Connection con,String franchiseGroupId,String franchiseCode)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "delete from ACC_FRANCHISE_GROUP_ASSIGNMENT where FRANCHISE_GROUP_ID ='"+franchiseGroupId+"' and FRANCHISE_CODE = '"+franchiseCode+"'";
	      System.out.println("The delete query isssss " + strSql);
	      Utility.logger.debug("The Query isssssssssssssssss " + strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	public static void updateFranchiseList(Connection con,String franchiseGroupId,String oldFranchiseCode,String newFranchiseCode)
	{
		try
		{
			Statement stat = con.createStatement();
			String strSql = "update ACC_FRANCHISE_GROUP_ASSIGNMENT set FRANCHISE_CODE = '"+newFranchiseCode+"'  where FRANCHISE_GROUP_ID = '"+franchiseGroupId+"' and FRANCHISE_CODE = '"+oldFranchiseCode+"'";
			stat.executeUpdate(strSql);
			stat.close();
		}
		catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	}
	
	public static Vector getAllCommissionDcm(Connection con)
	{
		Vector commissionDcmVec = new Vector();
	    CommissionDcmFixModel commissionDcmFixModel = null;
	    
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select a.old_dcm_id,old_dcm_code,old_dcm_name,b.new_dcm_id,new_dcm_code,new_dcm_name  "+
	      				  "from (select old_dcm_id,new_dcm_id,dcm_code as old_dcm_code,dcm_name as old_dcm_name from commission_dcm_fix,gen_dcm "+
	      				  "where old_dcm_id = dcm_id)A,(select old_dcm_id,new_dcm_id,dcm_code as new_dcm_code,dcm_name as new_dcm_name from commission_dcm_fix,gen_dcm "+
	      				  "where new_dcm_id = dcm_id) B "+
	      				  "where A.old_dcm_id = B.old_dcm_id and A.new_dcm_id = B.new_dcm_id";
	      				  
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  commissionDcmFixModel = new CommissionDcmFixModel(res);
	    	  commissionDcmVec.add(commissionDcmFixModel);
	      }
	   res.close();
	   stat.close();
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return commissionDcmVec;
	}
	
	public static CommissionDcmFixModel selectCommissionDcm (Connection con,String oldDcmId,String newDcmId)
	  {
		CommissionDcmFixModel commissionDcmFixModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select a.old_dcm_id,old_dcm_code,b.new_dcm_id,new_dcm_code "+
	      				  "from (select old_dcm_id,new_dcm_id,dcm_code as old_dcm_code from commission_dcm_fix,gen_dcm "+
	      				  "where old_dcm_id = dcm_id and old_dcm_id = '"+oldDcmId+"')A, "+
	      				  "(select old_dcm_id,new_dcm_id,dcm_code as new_dcm_code from commission_dcm_fix,gen_dcm "+
	      				  "where new_dcm_id = dcm_id and new_dcm_id = '"+newDcmId+"') B "+
	      				  "where A.old_dcm_id = B.old_dcm_id "+
	      				  "and A.new_dcm_id = B.new_dcm_id ";
	      				  
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while(res.next())
	      {
	    	  commissionDcmFixModel = new CommissionDcmFixModel();
	    	  commissionDcmFixModel.setOldDcmId(res.getString(commissionDcmFixModel.OLD_DCM_ID));
	    	  commissionDcmFixModel.setOldDcmCode(res.getString(commissionDcmFixModel.OLD_DCM_CODE));
	    	  commissionDcmFixModel.setNewDcmId(res.getString(commissionDcmFixModel.NEW_DCM_ID));
	    	  commissionDcmFixModel.setNewDcmCode(res.getString(commissionDcmFixModel.NEW_DCM_CODE));
	      }
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return commissionDcmFixModel;
	  }
	
	public static void deleteCommissionDcm(Connection con,String oldDcmId,String newDcmId)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "delete from COMMISSION_DCM_FIX where OLD_DCM_ID ='"+oldDcmId+"' and NEW_DCM_ID = '"+newDcmId+"'";
	      System.out.println("The delete query isssss " + strSql);
	      Utility.logger.debug("The Query isssssssssssssssss " + strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	
	public static int insertCommissionDcm(Connection con,String oldDcmCode,String newDcmCode)
	{
		int check = 0;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select * from gen_dcm where DCM_CODE = '"+oldDcmCode+"'";
			ResultSet res = stat.executeQuery(strSql);
			if(res.next())
			{
				Statement stat2 = con.createStatement();
				String StrSql2 = "select * from gen_dcm where DCM_CODE = '"+newDcmCode+"'";
				ResultSet res2 = stat2.executeQuery(StrSql2);
				if(res2.next())
				{
					Statement stat3 = con.createStatement();
					String StrSql3 = "insert into commission_dcm_fix (old_dcm_id,new_dcm_id)values "+
									 "((select distinct(dcm_id)from gen_dcm where dcm_code = '"+oldDcmCode+"'),"+
									 "(select distinct(dcm_id)from gen_dcm where dcm_code = '"+newDcmCode+"'))";
					stat3.executeUpdate(StrSql3);
				}
				else
				{
					check = 1;
				}
			}
			else
			{
				Statement stat2 = con.createStatement();
				String StrSql2 = "select * from gen_dcm where DCM_CODE = '"+newDcmCode+"'";
				ResultSet res2 = stat2.executeQuery(StrSql2);
				if(res2.next())
				{
					check = 2;
				}
				else
				{
					check = 3;
				}
			}
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    }
		return check;
	}
	
	
	public static int updateCommissionDcm(Connection con,String oldDcmCode,String newDcmCode,String oldDcmId,String newDcmId)
	{
		int check = 0;
		try
		{
			Statement stat = con.createStatement();
			String strSql = "select * from gen_dcm where DCM_CODE = '"+oldDcmCode+"'";
			ResultSet res = stat.executeQuery(strSql);
			if(res.next())
			{
				Statement stat2 = con.createStatement();
				String StrSql2 = "select * from gen_dcm where DCM_CODE = '"+newDcmCode+"'";
				ResultSet res2 = stat2.executeQuery(StrSql2);
				if(res2.next())
				{
					Statement stat3 = con.createStatement();
					String StrSql3 = "update commission_dcm_fix set old_dcm_id = (select distinct(dcm_id)from gen_dcm where dcm_code = '"+oldDcmCode+"') "+
									 ", new_dcm_id = (select distinct(dcm_id)from gen_dcm where dcm_code = '"+newDcmCode+"')"+
									 "where old_dcm_id = '"+oldDcmId+"' and new_dcm_id = '"+newDcmId+"'";
					stat3.executeUpdate(StrSql3);
				}
				else
				{
					check = 1;
				}
			}
			else
			{
				Statement stat2 = con.createStatement();
				String StrSql2 = "select * from gen_dcm where DCM_CODE = '"+newDcmCode+"'";
				ResultSet res2 = stat2.executeQuery(StrSql2);
				if(res2.next())
				{
					check = 2;
				}
				else
				{
					check = 3;
				}
			}
		}
		catch(Exception e)
	    {
	      e.printStackTrace();
	    }
		return check;
	}
	
	
}	
