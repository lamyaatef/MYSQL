package com.mobinil.sds.core.system.cam.payment.dao;
import java.sql.*;
import oracle.jdbc.*;
import java.util.*;
import com.mobinil.sds.core.system.cam.accrual.model.*;
import com.mobinil.sds.core.system.cam.common.model.*;
import com.mobinil.sds.core.system.cam.payment.model.*;
import oracle.jdbc.driver.OracleResultSet;
import com.mobinil.sds.core.system.payment.dao.*;
import com.mobinil.sds.core.system.payment.dto.*;
import com.mobinil.sds.core.utilities.DBUtil;

public class PaymentTypeDAO 
{
   public static int addPayTypeGroup(Connection argCon, PaymentTypeGroupModel group_model) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.addPayTypeGroup(?, ?, ?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setString(1, group_model.getGroupName());
            proc.setString(2, group_model.getGroupDesc());
            proc.setInt(3,group_model.getGroupStatusId());

            proc.registerOutParameter(4, OracleTypes.INTEGER);
            proc.registerOutParameter(5, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(5);
            if (error != null) {
                System.out.println("Error : " + error);
                return -1;
            } else {
                return proc.getInt(4);
            }

        //proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
///////////////////////////////////////////
 public static void assignPayTypeToGroup(Connection argCon, int group_id, int pay_type_id) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.assignPayTypeToGroup(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, pay_type_id);
            proc.setInt(2, group_id);
           
            proc.registerOutParameter(3, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(3);
            if (error != null) {
                System.out.println("Error : " + error);
                
            }  

        //proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
///////////////////////////////////////////
///////////////////////////////////////////
 public static void deleteGroupPayeType(Connection argCon, int group_id) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.deleteGroupPayeType(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, group_id);
           
            proc.registerOutParameter(2, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(2);
            if (error != null) {
                System.out.println("Error : " + error);
                
            }  

        //proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
///////////////////////////////////////////
public static void updatePayTypeGroup(Connection argCon, PaymentTypeGroupModel group_model) {
        String error = "";
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.updatePayTypeGroup(?, ?, ?, ?, ?); end;";

            CallableStatement proc = argCon.prepareCall(procCall);

          proc.setInt(1,group_model.getGroupId());
           proc.setString(2, group_model.getGroupName());
            proc.setString(3, group_model.getGroupDesc());
            proc.setInt(4,group_model.getGroupStatusId());
            
            proc.registerOutParameter(5, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(5);
            if (error != null) {
                System.out.println("Error : " + error);
                //return -1;
            } else {
                //return proc.getInt(6);
            }

        //proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // return -1;
    }
     //////////////////////////////
    public static Vector getAllPayTypeGroup(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllPayTypeGroup(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
               PaymentTypeGroupModel item = new  PaymentTypeGroupModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getString(5)
               );               
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }  
//////////////////////////////
//////////////////////////////
    public static Vector getAllGroupTypes(Connection argCon, int group_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllGroupTypes(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, group_id);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(2);
            error = proc.getString(3);
            while (result_set.next()) {
              PaymentTypeDTO item = PaymentDAO.getPaymentType(argCon,result_set.getInt(1)+"" ); 
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }  
//////////////////////////////
    public static Vector getAllPayTypeGroupStatus(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllPayTypeGroupStatus(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
               StatusModel item = new  StatusModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3)
               );               
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }  
    //////////////////////////////
     ///////////////////////////////////////////
     public static StatusModel getStatusById(Connection connection, int status_id)
     {
        Vector all_statuses=  getAllPayTypeGroupStatus(connection);
        for(int i=0;i<all_statuses.size();i++)
        {
          StatusModel status = ( StatusModel)all_statuses.get(i);
          if(status.getStatus_id() == status_id)
            return status;
        }
        return null;
     }
       ///////////////////////////////////////////
     public static StatusModel getStatusByName(Connection connection, String status_name)
     {
        Vector all_statuses=  getAllPayTypeGroupStatus(connection);
        for(int i=0;i<all_statuses.size();i++)
        {
          StatusModel status = ( StatusModel)all_statuses.get(i);
          if(status.getStatus_name().equalsIgnoreCase(status_name))
            return status;
        }
        return null;
     }

    public static Vector searchPayTypeGroup(Connection argCon, String group_name, int status_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.searchPayTypeGroup(?, ?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
             if(group_name.equals("")) 
            {
              group_name=null;
              proc.setString(1, group_name);
            }
            else
            {
              proc.setString(1, "'"+group_name+"'");
            }
            proc.setInt(2, status_id);
            proc.registerOutParameter(3, OracleTypes.CURSOR);
            proc.registerOutParameter(4, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(3);
            error = proc.getString(4);
            while (result_set.next()) {
               PaymentTypeGroupModel item = new  PaymentTypeGroupModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getString(5)
               );               
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }  
     //////////////////////////////
    public static void updatePaymentGroupType(Connection con,String groupId,String statusId)
    {
    	try
    	{
    		Statement stat = con.createStatement();
    		String strSql = "update CAM_DEDUCTION_PAY_TYPE_GROUP set GROUP_PAY_STATUS_ID = '"+statusId+"' where GROUP_PAY_TYPE_ID = '"+groupId+"'";
    		System.out.println("The update query isssssss " + strSql);
    		stat.executeUpdate(strSql);
    		stat.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    ///////////////////////////////////
     public static PaymentTypeGroupModel getPayGroupTypeByName(Connection argCon, String type_name) {

        Vector list_to_return = new Vector(22);
        PaymentTypeGroupModel item = null;
        try {

            String sql = null;
            
            if(type_name.equals(""))
                
                {
                    sql = "select tt1.*, tt2.GROUP_PAY_STATUS_NAME "
                          +" from CAM_DEDUCTION_PAY_TYPE_GROUP tt1 JOIN CAM_DED_PAY_TYPE_GROUP_STATUS tt2 ON (tt1.GROUP_PAY_STATUS_ID = tt2.GROUP_PAY_STATUS_ID  ) "
                          +" where lower(group_pay_type_name ) = 'general' order by lower(tt1.GROUP_PAY_TYPE_NAME )";       
                }
              else  
                
                  {
                  sql = "select tt1.*, tt2.GROUP_PAY_STATUS_NAME from CAM_DEDUCTION_PAY_TYPE_GROUP tt1 JOIN CAM_DED_PAY_TYPE_GROUP_STATUS  tt2 ON (tt1.GROUP_PAY_STATUS_ID = tt2.GROUP_PAY_STATUS_ID  ) "
                         + "where group_pay_type_name  = '"+type_name+"' order by lower(tt1.GROUP_PAY_TYPE_NAME )";       
                  }
            
            
            Statement stat = argCon.createStatement();
            
           System.out.println("sql = " + sql);
           ResultSet result_set = stat.executeQuery(sql);
           
            while (result_set.next()) 
            {
                item = new  PaymentTypeGroupModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getString(5)
               );               
                
            }
            
            
            
          stat.close();
          result_set.close();          
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
     //////////////////////////////
    public static Vector getAllActivePayTypeGroup(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin CAM_DEDUCTION_PKG.getAllActivePayTypeGroup(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
               PaymentTypeGroupModel item = new  PaymentTypeGroupModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getString(5)
               );               
                list_to_return.add(item);
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_to_return;
    }
    ///////////////////////////////////////////
     ///////////////////////////////////////////
     public static PaymentTypeGroupModel getGroupById(Connection connection, int  id)
     {
        Vector all_groups=  getAllActivePayTypeGroup(connection);
        for(int i=0;i<all_groups.size();i++)
        {
          PaymentTypeGroupModel group = ( PaymentTypeGroupModel)all_groups.get(i);
          if(group.getGroupId() == id)
            return group;
        }
        return null;
     }
     public static void UpdateCamPaymentDials()
     {
         DBUtil.executeSQL("call SDS.UPDATE_CAM_PAYMENT_SCM()");
         
     }
}