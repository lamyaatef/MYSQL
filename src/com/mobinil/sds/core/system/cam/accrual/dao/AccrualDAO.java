package com.mobinil.sds.core.system.cam.accrual.dao;
import java.sql.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import oracle.jdbc.*;

import java.util.*;

import com.mobinil.sds.core.system.cam.accrual.model.*;
import com.mobinil.sds.core.system.cam.common.model.*;

import oracle.jdbc.driver.OracleResultSet;


public class AccrualDAO 
{
 public static int addAccrual(Connection argCon, AccrualModel accrual_model) {
        String error = "";
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.addAccrual(?, ?, ?, ?, ?, ?, ?); end;";

            CallableStatement proc = argCon.prepareCall(procCall);

            proc.setString(1, accrual_model.getAccrual_name());
            proc.setString(2, accrual_model.getAccural_desc());
            proc.setDouble(3, accrual_model.getAccrual_value());
            proc.setInt(4,accrual_model.getStatus_id());
            proc.setInt(5, accrual_model.getChannel_id());

            proc.registerOutParameter(6, OracleTypes.INTEGER);
            proc.registerOutParameter(7, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(7);
            if (error != null) {
                System.out.println("Error : " + error);
                return -1;
            } else {
                 int id = proc.getInt(6);
                 return id;
            }

        //proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
///////////////////////////////////////////
public static int addDeleteAccrualValue(Connection argCon, com.mobinil.sds.core.system.cam.accrual.model.MakerAccrualValueModel accrual_value_model, int maker_id, String operation) {
        String error = "";
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.addDeleteAccrualValue(?, ?, ?, ?, ?, ?, ?); end;";

            CallableStatement proc = argCon.prepareCall(procCall);

             proc.setInt(1,maker_id);
            proc.setInt(2, accrual_value_model.getAccrual_id());
             
            proc.setDouble(3, accrual_value_model.getAccrual_value());
            proc.setInt(4,accrual_value_model.getReason_id()  );
            proc.setString(5,operation);

            proc.registerOutParameter(6, OracleTypes.INTEGER);
            proc.registerOutParameter(7, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(7);
            if (error != null) {
                System.out.println("Error : " + error);
                return -1;
            } else {
                return proc.getInt(6);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
///////////////////////////////////////////
public static void updateAccrual(Connection argCon, AccrualModel accrual_model) {
        String error = "";
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.updateAccrual(?, ?, ?, ?, ?, ?, ?); end;";

            CallableStatement proc = argCon.prepareCall(procCall);

            proc.setInt(1,accrual_model.getAccrual_id());
            proc.setString(2, accrual_model.getAccrual_name());
            proc.setString(3, accrual_model.getAccural_desc());
            proc.setDouble(4, accrual_model.getAccrual_value());
            proc.setInt(5,accrual_model.getStatus_id());
            proc.setInt(6, accrual_model.getChannel_id());
            
            proc.registerOutParameter(7, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(7);
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
    ///////////////////////////////////////////
public static void updateAccrualValueStatus(Connection argCon, int value_id, int status_id) {
        String error = "";
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.updateAccrualValueStatus(?, ?, ?); end;";

            CallableStatement proc = argCon.prepareCall(procCall);

            proc.setInt(1, value_id);
            proc.setInt(2, status_id);
            
            proc.registerOutParameter(3, OracleTypes.VARCHAR);

            proc.execute();
            error = proc.getString(3);
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
public static Vector getAllAccural(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllAccural(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
               AccrualModel item = new  AccrualModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getDouble(5),
               result_set.getInt(6),
               result_set.getString(7),
               result_set.getString(8)
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
public static Vector searchAccrual(Connection argCon, String accrual_name, int channel_id, int status_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.searchAccrual(?, ?, ?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            if(accrual_name.equals("")) 
            {
              accrual_name=null;
              proc.setString(1, accrual_name);
            }
            else
            {
              proc.setString(1, "'"+accrual_name+"'");
            }
            proc.setInt(2, channel_id);
            proc.setInt(3, status_id);
            proc.registerOutParameter(4, OracleTypes.CURSOR);
            proc.registerOutParameter(5, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(5);
            ResultSet result_set = (ResultSet) proc.getObject(4);
            
            while (result_set.next()) {
            	NumberFormat  formatter = new DecimalFormat("###,###,###");
            	String s = formatter.format(result_set.getDouble(5));
            	
               AccrualModel item = new  AccrualModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getDouble(5),
               result_set.getInt(6),
               result_set.getString(7),
               result_set.getString(8)
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
public static Vector getAllActiveAccural(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllActiveAccural(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
               AccrualModel item = new  AccrualModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getDouble(5),
               result_set.getInt(6),
               result_set.getString(7),
               result_set.getString(8)
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
public static AccrualModel getAccrualById(Connection argCon, int accrual_id) {
        String error = "";  
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAccrualById(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, accrual_id);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();

            
            ResultSet result_set = (ResultSet) proc.getObject(2);
            error = proc.getString(3);
            while (result_set.next()) {
               AccrualModel item = new  AccrualModel(
               result_set.getInt(1),
               result_set.getString(2),
               result_set.getString(3),
               result_set.getInt(4),
               result_set.getDouble(5),
               result_set.getInt(6),
               result_set.getString(7),
               result_set.getString(8)
               );               
                return item;
            }
            if (error != null) {
                System.out.println("Error : " + error);
                return null;
            }
            proc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////
///////////////////////////////////////////
public static int getChannelAccrual(Connection argCon, int channel_id) {
        String error = "";  
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getChannelAccrual(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, channel_id);
            proc.registerOutParameter(2, OracleTypes.INTEGER);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();

            
            int result = proc.getInt(2);
            error = proc.getString(3);
            proc.close();
            return result;
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    ///////////////////////////////////////////
    ///////////////////////////////////////////
public static Vector getAllAccrualStatus(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllAccrualStatus(?, ?); end;";
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
     ///////////////////////////////////////////
      ///////////////////////////////////////////
public static Vector getAllAccrualValueStatus(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllAccrualValueStatus(?, ?); end;";
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
    
     ///////////////////////////////////////////
     public static StatusModel getStatusById(Connection connection, int status_id)
     {
        Vector all_statuses=  getAllAccrualValueStatus(connection);
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
        Vector all_statuses=  getAllAccrualValueStatus(connection);
        for(int i=0;i<all_statuses.size();i++)
        {
          StatusModel status = ( StatusModel)all_statuses.get(i);
          if(status.getStatus_name().equalsIgnoreCase(status_name))
            return status;
        }
        return null;
     }
public static Vector getAvailableMakerValues(Connection argCon, int user_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAvailableMakerValues(?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.setInt(1, user_id);
            proc.registerOutParameter(2, OracleTypes.CURSOR);
            proc.registerOutParameter(3, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(2);
            error = proc.getString(3);
            while (result_set.next()) {
            	MakerAccrualValueModel item  = new MakerAccrualValueModel(
            		       result_set.getInt(1),result_set.getInt(2),
            		       result_set.getDouble(3),result_set.getInt(4),
            		       result_set.getInt(5),result_set.getInt(6),result_set.getDate(7), result_set.getString(8),
            		       result_set.getString(9), result_set.getString(10),result_set.getString(11)
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

public static Vector searchMakerValue(Connection argCon, int accrual_id, int status_id, int type_id, String date_from, String date_to) {
    String error = "";
    Vector list_to_return = new Vector(22);
    try {
        String procCall;
        procCall = "begin cam_accrual_pkg.searchMakerValue(?, ?, ?, ?, ?, ?, ?, ?); end;";
        CallableStatement proc = argCon.prepareCall(procCall);
        proc.setInt(1, accrual_id);
        proc.setInt(2, status_id);
        proc.setInt(3, type_id);


        proc.setString(6, null);
        if(date_from!=null && !date_from.equals(""))
        {
        	//java.sql.Date d_from = new java.sql.Date(formatter.parse(date_from).getTime());
        	proc.setString(4, "'"+date_from +"'");
        	proc.setString(6, "'"+"mm/dd/yyyy" +"'");
        }
        else
        {
        	proc.setDate(4, null );
        	
        }
        
        if(date_to!=null && !date_to.equals(""))
        {
        	//java.sql.Date d_to = new java.sql.Date(formatter.parse(date_to).getTime());
        	proc.setString(5,"'"+date_to +"'");
        	proc.setString(6, "'"+"mm/dd/yyyy" +"'");
        }
        else
        {
        	proc.setDate(5, null );
        	
        }
        
        proc.registerOutParameter(7, OracleTypes.CURSOR);
        proc.registerOutParameter(8, OracleTypes.VARCHAR);
        proc.execute();
        ResultSet result_set = (ResultSet) proc.getObject(7);
        error = proc.getString(8);
        while (result_set.next()) {
       MakerAccrualValueModel item  = new MakerAccrualValueModel(
       result_set.getInt(1),result_set.getInt(2),
       result_set.getDouble(3),result_set.getInt(4),
       result_set.getInt(5),result_set.getInt(6),result_set.getDate(7), result_set.getString(8),
       result_set.getString(9), result_set.getString(10),result_set.getString(11)
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
public static Vector getAllChannels(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllChannels(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
            DCMChannelModel item = new  DCMChannelModel(
               result_set.getInt(1),
               result_set.getString(2)
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
public static int addReason(Connection argCon, ReasonModel reason) {
    String error = "";
    try {
        String procCall;
        procCall = "begin cam_accrual_pkg.addReason(?, ?, ?, ?, ?); end;";

        CallableStatement proc = argCon.prepareCall(procCall);

        proc.setString(1, reason.getReason_name());
        proc.setString(2, reason.getReason_desc());
        proc.setInt(3, reason.getReason_status_id());            

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
public static void updateReason(Connection argCon, ReasonModel reason) {
        String error = "";
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.updateReason(?, ?, ?, ?, ? ); end;";

            CallableStatement proc = argCon.prepareCall(procCall);

            proc.setInt(1, reason.getReason_id());
            proc.setString(2, reason.getReason_name());
            proc.setString(3, reason.getReason_desc());
            proc.setInt(4, reason.getReason_status_id());
            
            proc.registerOutParameter(5, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(5);
            if (error != null) {
                System.out.println("Error : " + error);
            } else {
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    ///////////////////////////////////////////
public static Vector getAllReason(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllReason(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
            ReasonModel item = new  ReasonModel(
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
public static Vector getAllActiveReason(Connection argCon) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.getAllActiveReason(?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            proc.registerOutParameter(1, OracleTypes.CURSOR);
            proc.registerOutParameter(2, OracleTypes.VARCHAR);
            proc.execute();
            ResultSet result_set = (ResultSet) proc.getObject(1);
            error = proc.getString(2);
            while (result_set.next()) {
            ReasonModel item = new  ReasonModel(
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
public static Vector getAllReasonStatus(Connection argCon) {
    String error = "";
    Vector list_to_return = new Vector(22);
    try {
        String procCall;
        procCall = "begin cam_accrual_pkg.getAllReasonStatus(?, ?); end;";
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
///////////////////////////////////////////
public static ReasonModel getReasonById(Connection connection, int id)
{
   Vector all_reason=  searchReason(connection, "", -1);
   for(int i=0;i<all_reason.size();i++)
   {
     ReasonModel reason = ( ReasonModel)all_reason.get(i);
     if(reason.getReason_id() == id)
       return reason;
   }
   return null;
}
///////////////////////////////////////////
public static ReasonModel getReasonByName(Connection connection, String name)
{
 Vector all_reason=  getAllReason(connection);
 for(int i=0;i<all_reason.size();i++)
 {
   ReasonModel reason = ( ReasonModel)all_reason.get(i);
   if(reason.getReason_name().equalsIgnoreCase(name))
     return reason;
 }
 return null;
}
//////////////////////////////
public static Vector searchReason(Connection argCon, String reason_name, int status_id) {
        String error = "";
        Vector list_to_return = new Vector(22);
        try {
            String procCall;
            procCall = "begin cam_accrual_pkg.searchReason(?, ?, ?, ?); end;";
            CallableStatement proc = argCon.prepareCall(procCall);
            
            if(reason_name.equals("")) 
            {
              reason_name=null;
              proc.setString(1, reason_name);
            }
            else
            {
              proc.setString(1, "'"+reason_name+"'");
            }
            
            proc.setInt(2, status_id);
            
            
            proc.registerOutParameter(3, OracleTypes.CURSOR);
            proc.registerOutParameter(4, OracleTypes.VARCHAR);
            proc.execute();
            error = proc.getString(4);
            ResultSet result_set = (ResultSet) proc.getObject(3);
            
            while (result_set.next()) {
               ReasonModel item = new  ReasonModel(
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
}