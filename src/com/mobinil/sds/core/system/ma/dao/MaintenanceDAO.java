package com.mobinil.sds.core.system.ma.dao;

import java.util.*;
import java.sql.*;

import com.mobinil.sds.core.utilities.*;

import com.mobinil.sds.web.interfaces.ma.*;
//mport com.mobinil.sds.core.system.sop.requests.model.InvoiceModel;
import com.mobinil.sds.core.system.dcm.chain.model.chainCityModel;
import com.mobinil.sds.core.system.ma.model.*;


public class MaintenanceDAO 
{
	 public MaintenanceDAO()
	  {
	  }
	 
	 public static Vector searchPrivilages(Connection con,String moduleId)
	 {
		 
		 Vector sopVec = new Vector();
		    PrivilageModel privilageModel = null;
		   // StatusModel statusModel=null;
		    try
		    {
		      Statement stat = con.createStatement();
		    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
		    //  String strSql="select module_id,module_name,module_desc,module_status_name  from ADM_MODULE,ADM_MODULE_STATUS where ADM_MODULE.module_status_id=ADM_MODULE_STATUS.module_status_id";
		      String strSql="select privilage_id,privilage_name,privilage_desc,module_name,privilage_status_name,privilage_action_name,order_value,privilage_target from ADM_PRIVILAGE, ADM_PRIVILAGE_STATUS ,ADM_MODULE where  ADM_PRIVILAGE.privilage_status_id=ADM_PRIVILAGE_STATUS.privilage_status_id and ADM_PRIVILAGE.MODULE_ID =ADM_MODULE.MODULE_ID and ADM_PRIVILAGE.MODULE_ID ="+moduleId;

		      System.out.println(strSql);
		      System.out.println("dsfdsfsdbdsf");
		      //Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while (res.next())
		      {
		    	  privilageModel=new PrivilageModel();
		    	  privilageModel.setPrivilageId(res.getString("PRIVILAGE_ID"));
		          privilageModel.setPrivilageName(res.getString("PRIVILAGE_NAME"));
		          privilageModel.setPrivilageDesc(res.getString("PRIVILAGE_DESC"));
		          privilageModel.setModuleName(res.getString("MODULE_NAME"));
		          privilageModel.setPrivilageStatusName(res.getString("PRIVILAGE_STATUS_NAME"));
		          privilageModel.setPrivilageActionName(res.getString("PRIVILAGE_ACTION_NAME"));
		          privilageModel.setOrderValue(res.getString("ORDER_VALUE"));
		          privilageModel.setPrivilageTarget(res.getString("PRIVILAGE_TARGET"));
		          //		          moduleModel.setModuleStatusId(res.getString("MODULE_STATUS_ID"));
		          sopVec.add(privilageModel);
		      }
		      stat.close();
		    }
		    catch(Exception e)
		    {
		      e.printStackTrace();
		    } 
		    return sopVec;
		 
	 }
	 public static Vector getAllModules(Connection con)
	   	{
		 Vector sopVec = new Vector();
		    ModuleModel moduleModel = null;
		   // StatusModel statusModel=null;
		    try
		    {
		      Statement stat = con.createStatement();
		    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
		    //  String strSql="select module_id,module_name,module_desc,module_status_name  from ADM_MODULE,ADM_MODULE_STATUS where ADM_MODULE.module_status_id=ADM_MODULE_STATUS.module_status_id";
		      String strSql="select module_id,module_name,module_desc,module_status_name  from ADM_MODULE,ADM_MODULE_STATUS where ADM_MODULE.module_status_id=ADM_MODULE_STATUS.module_status_id";

		      System.out.println(strSql);
		      System.out.println("dsfdsfsdbdsf");
		      //Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while (res.next())
		      {
		    	  moduleModel=new ModuleModel();
		    	  moduleModel.setModuleId(res.getString("MODULE_ID"));
		          System.out.println(res.getString("MODULE_ID"));

		          moduleModel.setModuleName(res.getString("MODULE_NAME"));
		          System.out.println(res.getString("MODULE_NAME"));

		          moduleModel.setModuleDesc(res.getString("MODULE_DESC"));
		          moduleModel.setModuleStatusName(res.getString("MODULE_STATUS_NAME"));
		          System.out.println(res.getString("MODULE_STATUS_NAME"));

		          //		          moduleModel.setModuleStatusId(res.getString("MODULE_STATUS_ID"));
		          sopVec.add(moduleModel);
		      }
		      stat.close();
		     
		    }
		    catch(Exception e)
		    {
		      e.printStackTrace();
		    } 
		    return sopVec;
		 
		 
		 
	   	}
	  public static void deleteModule (Connection con,String moduleId)
	  {
	    Vector sopVec = new Vector();
	    ModuleModel moduleModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
	      String strSql="delete  from ADM_MODULE where module_id='"+moduleId+"'";
	      System.out.println(strSql);
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	   
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  //  return sopVec;
	  }
	  public static void insertModule(Connection con,Long moduleId,String moduleName,String moduleDesc,String moduleStatusId)
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into ADM_MODULE values("+moduleId+",'"+moduleName+"','"+moduleDesc+"','"+moduleStatusId+"')";
	      System.out.print(strSql);
	      stat.executeUpdate(strSql);           
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    finally
	    {	 
	    	if (stat != null ) try{ stat.close();} catch (Exception e ) {}
	    }
	  }
	  
	  public static ModuleModel selectModule(Connection con,String moduleId)
	  {
	    Vector sopVec = new Vector();
	    ModuleModel moduleModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ADM_MODULE where MODULE_ID = '"+moduleId+"'";
	      System.out.println(strSql);
	      //Utility.logger.debug("SQL--->"+strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while (res.next())
	      {
	          moduleModel = new ModuleModel(res);

	       // itemModel = new ItemModel();
	        //itemModel.setItemId(res.getString(itemModel.ITEM_ID));
	        //itemModel.setItemName(res.getString(itemModel.ITEM_NAME));
	        //itemModel.setStatusId(res.getString(itemModel.STATUS_ID));
	        //itemModel.setInvoiceDetailId(res.getString(invoiceModel.INVOICE_DETAIL_ID));
	        //sopVec.add(itemModel);
	      }
	      stat.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return moduleModel;
	  }
	  public static void editModule(Connection con,String moduleId,String moduleName,String moduleDescription,String statusModuleId)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "update ADM_MODULE set module_name='"+moduleName+"', module_desc='"+moduleDescription+"' , module_status_id='"+statusModuleId+"' where module_id='"+moduleId+"'";
	      System.out.println(strSql);

	   //  System.out.print(strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	      
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  ////////////////////////////////////////////////
	  public static Vector getAllPrivilages(Connection con)
	   	{
		 Vector sopVec = new Vector();
		    PrivilageModel privilageModel = null;
		   // StatusModel statusModel=null;
		    try
		    {
		      Statement stat = con.createStatement();
		    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
		    //  String strSql="select module_id,module_name,module_desc,module_status_name  from ADM_MODULE,ADM_MODULE_STATUS where ADM_MODULE.module_status_id=ADM_MODULE_STATUS.module_status_id";
		      String strSql="select privilage_id,privilage_name,privilage_desc,module_name,privilage_status_name,privilage_action_name,order_value,privilage_target from ADM_PRIVILAGE, ADM_PRIVILAGE_STATUS ,ADM_MODULE where  ADM_PRIVILAGE.privilage_status_id=ADM_PRIVILAGE_STATUS.privilage_status_id and ADM_PRIVILAGE.MODULE_ID =ADM_MODULE.MODULE_ID";

		      System.out.println(strSql);
		      System.out.println("dsfdsfsdbdsf");
		      //Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while (res.next())
		      {
		    	  privilageModel=new PrivilageModel();
		    	  privilageModel.setPrivilageId(res.getString("PRIVILAGE_ID"));
		          privilageModel.setPrivilageName(res.getString("PRIVILAGE_NAME"));
		          privilageModel.setPrivilageDesc(res.getString("PRIVILAGE_DESC"));
		          privilageModel.setModuleName(res.getString("MODULE_NAME"));
		          privilageModel.setPrivilageStatusName(res.getString("PRIVILAGE_STATUS_NAME"));
		          privilageModel.setPrivilageActionName(res.getString("PRIVILAGE_ACTION_NAME"));
		          privilageModel.setOrderValue(res.getString("ORDER_VALUE"));
		          privilageModel.setPrivilageTarget(res.getString("PRIVILAGE_TARGET"));
		          //		          moduleModel.setModuleStatusId(res.getString("MODULE_STATUS_ID"));
		          sopVec.add(privilageModel);
		      }
		      stat.close();
		    }
		    catch(Exception e)
		    {
		      e.printStackTrace();
		    } 
		    return sopVec;
		 
		 
		 
	   	}
	  public static void deletePrivilage (Connection con,String privilageId)
	  {
	    Vector sopVec = new Vector();
	    PrivilageModel privilageModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	    //  String strSql = "select item_id,item_name,status_name from ITEM, status where status.status_id=item.status_id";
	      String strSql="delete  from ADM_PRIVILAGE where privilage_id='"+privilageId+"'";
	      System.out.println(strSql);
	      //Utility.logger.debug(strSql);
	      ResultSet res = stat.executeQuery(strSql);
	   
	      stat.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    } 
	  //  return sopVec;
	  }
	  
	  public static void insertPrivilage(Connection con,Long privilageId,String privilageName,String privilageDesc,String moduleId,String privilageStatusId,String privilageActionName,String orderValue,String privilageTarget)
	  {
		  Statement stat = null ; 
	    try
	    {
	      stat = con.createStatement();
	      String strSql = "insert into ADM_PRIVILAGE values("+privilageId+",'"+privilageName+"','"+privilageDesc+"','"+moduleId+"','"+privilageStatusId+"','"+privilageActionName+"','"+orderValue+"','"+privilageTarget+"')";
	      System.out.print(strSql);
	      stat.executeUpdate(strSql);           
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    finally
	    {	 
	    	if (stat != null ) try{ stat.close();} catch (Exception e ) {}
	    }
	  }
	  
	  public static PrivilageModel selectPrivilage(Connection con,String privilageId)
	  {
	    Vector sopVec = new Vector();
	    PrivilageModel privilageModel = null;
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "select * from ADM_PRIVILAGE where PRIVILAGE_ID = '"+privilageId+"'";
	      System.out.println(strSql);
	      //Utility.logger.debug("SQL--->"+strSql);
	      ResultSet res = stat.executeQuery(strSql);
	      while (res.next())
	      {
	    	  privilageModel = new PrivilageModel(res);

	       // itemModel = new ItemModel();
	        //itemModel.setItemId(res.getString(itemModel.ITEM_ID));
	        //itemModel.setItemName(res.getString(itemModel.ITEM_NAME));
	        //itemModel.setStatusId(res.getString(itemModel.STATUS_ID));
	        //itemModel.setInvoiceDetailId(res.getString(invoiceModel.INVOICE_DETAIL_ID));
	        //sopVec.add(itemModel);
	      }
	      stat.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return privilageModel;
	  }
	  public static void editPrivilage(Connection con,String privilageId,String privilageName,String privilageDescription,String moduleId,String privilageStatusId,String privilageActionName,String orderValue,String privilageTarget)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	      String strSql = "update ADM_PRIVILAGE set privilage_name='"+privilageName+"', privilage_desc='"+privilageDescription+"' , module_id='"+moduleId+"', privilage_status_id='"+privilageStatusId+"', privilage_action_name='"+privilageActionName+"', order_value='"+orderValue+"', privilage_target='"+privilageTarget+"' where module_id='"+moduleId+"'";
	      System.out.println(strSql);

	   //  System.out.print(strSql);
	      stat.executeUpdate(strSql);
	      stat.close();
	      
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  public static Vector getModulesList(Connection con)
	  {
			 Vector sopVec = new Vector();
			    ModuleModel moduleModel = null;
			   // StatusModel statusModel=null;
			    try
			    {
			      Statement stat = con.createStatement();
			      String strSql="select * from ADM_Module ORDER BY module_name";

			      ResultSet res = stat.executeQuery(strSql);
			      while (res.next())
			      {
			    	  sopVec.add(new ModuleModel(res));

			      }
			      stat.close();
			    }
			    catch(Exception e)
			    {
			      e.printStackTrace();
			    } 
			    return sopVec;
		  
		  
		  
		  
		  
	  }
	  public static Vector getPrivilagesStatusList(Connection con)
	  {
			 Vector sopVec = new Vector();
			    PrivilageStatusModel privilageStatusModel = null;
			    
			   // StatusModel statusModel=null;
			    try
			    {
			      Statement stat = con.createStatement();
			      String strSql="select * from ADM_PRIVILAGE_STATUS ORDER BY privilage_status_name";

			      ResultSet res = stat.executeQuery(strSql);
			      while (res.next())
			      {
			    	  sopVec.add(new PrivilageStatusModel(res));

			      }
			      stat.close();
			    }
			    catch(Exception e)
			    {
			      e.printStackTrace();
			    } 
			    return sopVec;
		  
		  
		  
		  
		  
	  }

}
