package com.mobinil.sds.core.facade.handlers;


import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.cam.*;
import com.mobinil.sds.core.system.cam.accrual.model.*;
import com.mobinil.sds.core.system.cam.accrual.dao.*;
import com.mobinil.sds.core.system.cam.common.model.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;



public class CAMAccrualHandler
{


    private static final int VIEW_ALL_ACCRUAL=0;
    private static final int NEW_ACCRUAL=1;
    private static final int EDIT_ACCRUAL=2;
    private static final int ADD_ACCRUAL=3;
    private static final int UPDATE_ACCRUAL=4;
    private static final int UPDATE_ACCRUAL_STATUSES=5;
    private static final int MAKER_VIEW_DE=6;
    private static final int VIEW_CHECKER_MANAGEMENT=7;
    private static final int CHECKER_APPROVE_ACCRUAL_VALUE=8;
    private static final int MAKER_DELETE_ACCRUAL_VALUE=9;
    private static final int SEARCH_ACCRUAL=10;
    
    private static final int VIEW_REASON_SEARCH = 11;
    private static final int VIEW_REASON= 12;
    private static final int NEW_REASON= 13;
    private static final int ADD_REASON= 14;
    private static final int EDIT_REASON= 15;
    private static final int UPDATE_REASON= 16;
    private static final int UPDATE_REASON_STATUS= 17;
    private static final int SEARCH_CHECKER_MANAGEMENT = 18;
   
    
  public static HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
  {
	  int actionType = 0;

     if(action.equals(AccrualInterfaceKey.ACTION_VIEW_ALL_ACCRUAL))
        actionType = VIEW_ALL_ACCRUAL;
     else if(action.equals(AccrualInterfaceKey.ACTION_NEW_ACCRUAL))
        actionType = NEW_ACCRUAL;
     else if(action.equals(AccrualInterfaceKey.ACTION_EDIT_ACCRUAL))
        actionType = EDIT_ACCRUAL;
     else if(action.equals(AccrualInterfaceKey.ACTION_ADD_ACCRUAL))
        actionType = ADD_ACCRUAL;
     else if(action.equals(AccrualInterfaceKey.ACTION_UPDATE_ACCRUAL))
        actionType = UPDATE_ACCRUAL;
     else if(action.equals(AccrualInterfaceKey.ACTION_UPDATE_ACCRUAL_STATUSES))
        actionType = UPDATE_ACCRUAL_STATUSES; 
     else if(action.equals(AccrualInterfaceKey.ACTION_VIEW_MAKER_DATE_ENTRY))
        actionType = MAKER_VIEW_DE;  
     else if(action.equals(AccrualInterfaceKey.VIEW_CHECKER_MANAGEMENT))
        actionType = VIEW_CHECKER_MANAGEMENT;
     else if(action.equals(AccrualInterfaceKey.SEARCH_CHECKER_MANAGEMENT))
         actionType = SEARCH_CHECKER_MANAGEMENT;
     else if(action.equals(AccrualInterfaceKey.ACTION_CHECKER_APPROVE_ACCRUAL_VALUE))
        actionType = CHECKER_APPROVE_ACCRUAL_VALUE; 
     else if(action.equals(AccrualInterfaceKey.ACTION_DELETE_ACCRUAL_VALUE))
        actionType = MAKER_DELETE_ACCRUAL_VALUE; 
     else if(action.equals(AccrualInterfaceKey.ACTION_SEARCH_ACCRUAL))
        actionType = SEARCH_ACCRUAL;   
     else if(action.equals(AccrualInterfaceKey.ACTION_VIEW_REASON_SEARCH))
         actionType = VIEW_REASON_SEARCH; 
     else if(action.equals(AccrualInterfaceKey.ACTION_VIEW_REASON))
         actionType = VIEW_REASON; 
         else if(action.equals(AccrualInterfaceKey.ACTION_NEW_REASON))
         actionType = NEW_REASON; 
         else if(action.equals(AccrualInterfaceKey.ACTION_ADD_REASON))
         actionType = ADD_REASON; 
         else if(action.equals(AccrualInterfaceKey.ACTION_EDIT_REASON))
         actionType = EDIT_REASON; 
         else if(action.equals(AccrualInterfaceKey.ACTION_UPDATE_REASON))
         actionType = UPDATE_REASON; 
         else if(action.equals(AccrualInterfaceKey.ACTION_UPDATE_REASON_STATUS))
             actionType = UPDATE_REASON_STATUS; 
    return handlOperations(paramHashMap, actionType, con);

  }



private static HashMap handlOperations(HashMap paramHashMap, int actionType, Connection connection)
{
    HashMap dataHashMap = new HashMap(100);
    String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    //int user_id = Integer.parseInt(strUserID);
    int user_id = -1;
    if(strUserID != null && strUserID.compareTo("") != 0)
    {
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
      user_id = Integer.parseInt(strUserID);
    }


 //   Connection connection=null;
 try
    {
	 System.out.println("Connection inc frmo cam accrual handler");
    //   connection = Utility.getConnection();
     switch (actionType)                                                                                     
     {
     
         case VIEW_ALL_ACCRUAL:
         {
        	 Vector all_searched_accruals = AccrualDAO.searchAccrual(connection, "",-1,-1);
        	 setAccrualsMonthlyValues(all_searched_accruals, connection);
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, all_searched_accruals);
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
         }
         break;
         case SEARCH_ACCRUAL:
         {            
         String accrual_name_search = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_SEARCH_NAME);
         String channel_id_search = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL);
         String status_id_search = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS);
         int channel_id_s = -1;
         int status_id_s = -1;
         
         
         if(accrual_name_search==null || accrual_name_search.equals("")) 
             accrual_name_search = "";
         if(channel_id_search!=null && !channel_id_search.equals("")) 
              channel_id_s = Integer.parseInt(channel_id_search);
              
         if(status_id_search!=null && !status_id_search.equals("")) 
              status_id_s = Integer.parseInt(status_id_search);
             
            Vector all_searched_accruals = AccrualDAO.searchAccrual(connection, accrual_name_search,channel_id_s,status_id_s);
            setAccrualsMonthlyValues(all_searched_accruals, connection);

            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, all_searched_accruals);
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
         }
         break;
         case NEW_ACCRUAL:
         {
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
         }
         break;
         case EDIT_ACCRUAL:
         {
             String accrual_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_HIDDEN_ACCRUAL_ID);
            int accrual_id = -1;
            if(accrual_id_str!=null)
              accrual_id = Integer.parseInt(accrual_id_str); 
            AccrualModel accrual_model = AccrualDAO.getAccrualById(connection, accrual_id);  

            dataHashMap.put(AccrualInterfaceKey.MODEL_ACCRUAL, accrual_model);
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
         }
         break;
         case ADD_ACCRUAL:
         {
            String accrual_name = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_NAME);
            String accrual_status_id = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS);
            int accrual_status = Integer.parseInt(accrual_status_id);
            String accrual_desc = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_DESCRIPTION);
           // String accrual_val_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_VALUE);
           // double accrual_val = Double.parseDouble(accrual_val_str);
            String channel_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL);
            int channel_id = Integer.parseInt(channel_id_str);

            AccrualModel accrual = new AccrualModel();
            accrual.setAccrual_name(accrual_name);
            accrual.setStatus_id(accrual_status);
            accrual.setAccural_desc(accrual_desc);
            accrual.setAccrual_value(0);
            accrual.setChannel_id(channel_id);

            int inserted_id = AccrualDAO.addAccrual(connection, accrual);
            
             
            //dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, AccrualDAO.getAllAccural(connection));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
         }
         break;
         case UPDATE_ACCRUAL:
         {
            int accrual_id = Integer.parseInt((String)paramHashMap.get(AccrualInterfaceKey.CONTROL_HIDDEN_ACCRUAL_ID));
            String accrual_name = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_NAME);
            String accrual_status_id = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS);
            int accrual_status = Integer.parseInt(accrual_status_id);
            String accrual_desc = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_DESCRIPTION);
            //String accrual_val_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_VALUE);
            //double accrual_val = Double.parseDouble(accrual_val_str);
            String channel_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL);
            int channel_id = Integer.parseInt(channel_id_str);

            
            AccrualModel accrual = AccrualDAO.getAccrualById(connection, accrual_id);
            //accrual.setAccrual_id(accrual_id);
            accrual.setAccrual_name(accrual_name);
            accrual.setStatus_id(accrual_status);
            accrual.setAccural_desc(accrual_desc);
           // accrual.setAccrual_value(0);
            accrual.setChannel_id(channel_id);

            AccrualDAO.updateAccrual(connection, accrual);           
            
            //dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, AccrualDAO.getAllAccural(connection));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
            
         }
         break;
         case UPDATE_ACCRUAL_STATUSES:
         {
        	 Vector all_accrual = AccrualDAO.getAllAccural(connection);
        	 if(all_accrual!=null && all_accrual.size()>0)
        	 {
        		 for(int i=0;i<all_accrual.size();i++){
        			 AccrualModel accrual =(AccrualModel) all_accrual.get(i);
        		 String accrual_status_id = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS+accrual.getAccrual_id());
        		 if(accrual_status_id!=null){
        			 int accrual_status = Integer.parseInt(accrual_status_id);
        			 accrual.setStatus_id(accrual_status);
        			 AccrualDAO.updateAccrual(connection, accrual);
        		 }
        		 }
        	 }
        	 dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, AccrualDAO.getAllAccural(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualStatus(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL, AccrualDAO.getAllChannels(connection));
         }
         break;
         case MAKER_VIEW_DE:
         {
             String accrual_val_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_VALUE);
             String accrual_id_str = (String)paramHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL);
             String accrual_reason_id = (String)paramHashMap.get(AccrualInterfaceKey.VECTOR_ALL_REASONS);
             if(accrual_id_str!= null && accrual_val_str !=null && accrual_reason_id!=null)
             {
                double accrual_val = Double.parseDouble(accrual_val_str);
                int accrual_id = Integer.parseInt(accrual_id_str);
                int reason_id = Integer.parseInt(accrual_reason_id);
                MakerAccrualValueModel maker_value_m = new MakerAccrualValueModel();
                maker_value_m.setAccrual_id(accrual_id);
                maker_value_m.setReason_id(reason_id);
                maker_value_m.setAccrual_value(accrual_val);
                int inserted_accrual_value_id = AccrualDAO.addDeleteAccrualValue(connection, maker_value_m, user_id, "add");
                if(inserted_accrual_value_id != -1)
                {
                AccrualModel accrual_m= AccrualDAO.getAccrualById(connection,accrual_id);
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Accrual value inserted for accrual "+accrual_m.getAccrual_name()+" and waiting to be approved");
                }
             }
             
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, AccrualDAO.getAllActiveAccural(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_REASONS, AccrualDAO.getAllReason(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualValueStatus(connection));
             dataHashMap.put(AccrualInterfaceKey.ACTION_VIEW_MAKER_DATE_ENTRY, AccrualInterfaceKey.ACTION_VIEW_MAKER_DATE_ENTRY);
         }
         break;
          case MAKER_DELETE_ACCRUAL_VALUE:
         {
             String accrual_val_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_VALUE);
             String accrual_id_str = (String)paramHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL);
             String accrual_reason_id = (String)paramHashMap.get(AccrualInterfaceKey.VECTOR_ALL_REASONS);
             if(accrual_id_str!= null && accrual_val_str !=null && accrual_reason_id!=null)
             {
                double accrual_val = Double.parseDouble(accrual_val_str);
                int accrual_id = Integer.parseInt(accrual_id_str);
                int reason_id = Integer.parseInt(accrual_reason_id);
                MakerAccrualValueModel maker_value_m = new MakerAccrualValueModel();
                maker_value_m.setAccrual_id(accrual_id);
                maker_value_m.setReason_id(reason_id);
                maker_value_m.setAccrual_value(accrual_val*-1);
                int inserted_accrual_value_id = AccrualDAO.addDeleteAccrualValue(connection, maker_value_m, user_id, "delete");
                if(inserted_accrual_value_id != -1)
                {
                AccrualModel accrual_m= AccrualDAO.getAccrualById(connection,accrual_id);
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Accrual amount deleted for accrual "+accrual_m.getAccrual_name()+" and waiting to be approved");
                }
             }
             
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL, AccrualDAO.getAllActiveAccural(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_REASONS, AccrualDAO.getAllReason(connection));
             dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualValueStatus(connection));
         }
         break;
          case SEARCH_CHECKER_MANAGEMENT:
          {
        	  String value_type = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SEARCH_VALUE_TYPE);
        	  String value_status = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_SEARCH_ALL_ACCRUAL_STATUS);
        	  int value_statis_id=-1;
        	  int value_type_id = 0;
        	  if(value_status!=null && !value_status.equals(""))
        		  value_statis_id = Integer.parseInt(value_status);
        	  if(value_type!=null&& !value_type.equals(""))
        		  value_type_id = Integer.parseInt(value_type);
        	  Vector all_values = AccrualDAO.searchMakerValue(connection, -1, value_statis_id, value_type_id, null, null);
        	  dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_MAKER_VALUE, all_values);
              dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualValueStatus(connection));
          }
          break;
         case VIEW_CHECKER_MANAGEMENT:
         {
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_MAKER_VALUE, AccrualDAO.getAvailableMakerValues(connection, user_id));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualValueStatus(connection));
         }
         break;
          case CHECKER_APPROVE_ACCRUAL_VALUE:
         {
            Vector all_accrual_value = AccrualDAO.searchMakerValue(connection, -1, -1, 0, null, null);
            if(all_accrual_value != null && all_accrual_value.size()>0)
            {
              for(int i=0;i<all_accrual_value.size();i++)
              {
                 MakerAccrualValueModel accrual_value_model = (MakerAccrualValueModel)all_accrual_value.get(i);
                 String value_status_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS + accrual_value_model.getValue_id());
                 String value_status=null;
                 int status_id=-1;
                 if(value_status_id_str!=null)
                 {
                    status_id = Integer.parseInt(value_status_id_str);
                    StatusModel status =  AccrualDAO.getStatusById(connection, status_id);
                    value_status = status.getStatus_name();
                 }
                 if(value_status != null)
                 {
                 AccrualModel accrual_model = AccrualDAO.getAccrualById(connection, accrual_value_model.getAccrual_id());
                 if(value_status.equalsIgnoreCase("approved")){                  
  //accrual will b negative 
                    if(accrual_model.getAccrual_value()+accrual_value_model.getAccrual_value() < 0)
                    {
                      dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE, "Operation can't be done or accrual "+accrual_model.getAccrual_name()+" because operation will make the accrual negative");
                      status_id=AccrualDAO.getStatusByName(connection, "Disapproved").getStatus_id();
                      AccrualDAO.updateAccrualValueStatus(connection,accrual_value_model.getValue_id(),status_id);
                      
                    }
                    else 
                    {
                      accrual_model.setAccrual_value(accrual_model.getAccrual_value()+accrual_value_model.getAccrual_value());
                      AccrualDAO.updateAccrual(connection, accrual_model);//update accrual
                      AccrualDAO.updateAccrualValueStatus(connection,accrual_value_model.getValue_id(),status_id);
                    }
                 }
                 else if (value_status.equalsIgnoreCase("disapproved"))
                 {
                	 
                	 status_id=AccrualDAO.getStatusByName(connection, "Disapproved").getStatus_id();
                     AccrualDAO.updateAccrualValueStatus(connection,accrual_value_model.getValue_id(),status_id);
                	 
                 }
                
                 
                  //status = AccrualDAO.getStatusByName(connection, "");
                  //value_status = status.getStatus_name();
                 }
                 
              }
            }
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_MAKER_VALUE, AccrualDAO.getAvailableMakerValues(connection, user_id));
            dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS, AccrualDAO.getAllAccrualValueStatus(connection));
         }
         break;
    
     case VIEW_REASON_SEARCH:
     {
    	 dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_REASONS, AccrualDAO.searchReason(connection, "", -1)); 
        dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
     }
     break;
      case VIEW_REASON:
     {
     String reason_name_search = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_SEARCH_REASON_NAME );
     
     String status_id_search = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_SEARCH_REASON_STATUS);
     
     int status_id_s = -1; 
     
     
     
     if(status_id_search!=null && !status_id_search.equals("")) 
          status_id_s = Integer.parseInt(status_id_search);
          
    Vector all_result_reason = AccrualDAO.searchReason(connection, reason_name_search, status_id_s);
        dataHashMap.put(AccrualInterfaceKey.VECTOR_ALL_REASONS, all_result_reason);  
        dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
     }
     break;
      case NEW_REASON:
     {
        dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
     }
     break;
      case ADD_REASON:
     {
        String reason_name = (String) paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_REASON_NAME);
        String reason_desc = (String) paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_REASON_DESC);
        int status_id = Integer.parseInt((String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_REASON_STATUS));
        ReasonModel reason = new ReasonModel();
        reason.setReason_name(reason_name);
        reason.setReason_desc(reason_desc);
        reason.setReason_status_id(status_id);

        int inserted_reason_id = AccrualDAO.addReason(connection, reason);
        dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
     }
     break;
      case EDIT_REASON:
     {
        String reason_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_HIDDEN_REASON_ID);
        int reason_id = Integer.parseInt(reason_id_str);
        ReasonModel reason =  AccrualDAO.getReasonById(connection, reason_id);
         
        dataHashMap.put(AccrualInterfaceKey.MODEL_ACCRUAL_REASON, reason);
        dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
     }
     break;
      case UPDATE_REASON:
     {
        String reason_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_HIDDEN_REASON_ID);
        int reason_id = Integer.parseInt(reason_id_str);
        String reason_name = (String) paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_REASON_NAME);
        String reason_desc = (String) paramHashMap.get(AccrualInterfaceKey.CONTROL_TEXT_REASON_DESC);
        int status_id = Integer.parseInt((String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_REASON_STATUS));
        ReasonModel reason =  new ReasonModel();
        reason.setReason_id(reason_id);
        reason.setReason_name(reason_name);
        reason.setReason_desc(reason_desc);
        reason.setReason_status_id(status_id);

        AccrualDAO.updateReason(connection, reason);
        dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
     }
     break;
      case UPDATE_REASON_STATUS:
      {
    	  Vector all_reason = AccrualDAO.getAllReason(connection);
    	  if(all_reason != null && all_reason.size()>0)
    	  {
    		  for(int i=0;i<all_reason.size();i++)
    		  {
    			  ReasonModel reason = (ReasonModel)all_reason.get(i);
    			  String status_id_str = (String)paramHashMap.get(AccrualInterfaceKey.CONTROL_SELECT_REASON_STATUS+reason.getReason_id());
    			  if(status_id_str!=null)
    			  {
    				  int status_id = Integer.parseInt(status_id_str);
    				  reason.setReason_status_id(status_id);
    				  AccrualDAO.updateReason(connection, reason);
    			  }
    		  }
    	  }
    	  dataHashMap.put(AccrualInterfaceKey.VECTOR_REASON_STATUS, AccrualDAO.getAllReasonStatus(connection));
      }
      break;
     }
    }catch(Exception ex)
    {}
    finally
    {
    	
    }
    return dataHashMap;
}
public static void setAccrualsMonthlyValues(Vector all_searched_accruals, Connection connection)
{
	GregorianCalendar cal = new GregorianCalendar();
    cal.set(Calendar.MONTH, Calendar.DECEMBER);
    cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
    String start_of_year_str = "1/1/"+cal.get(Calendar.YEAR);
    String end_of_year_str = "12/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR);
    if(all_searched_accruals!=null && all_searched_accruals.size()>0)
    {
 	   for(int i=0;i<all_searched_accruals.size();i++){
 		   AccrualModel accrual = (AccrualModel)all_searched_accruals.get(i);
 		   String accrual_24_in_out_values_str = "";
 		   String[] in=new String[12];
 		   String[] out=new String[12];
 		  for(int j=0;j<in.length;j++){
 			 in[j] = out[j] = "0";
 		  }
 		   Vector all_accrual_value = AccrualDAO.searchMakerValue(connection, accrual.getAccrual_id(), -1, 0, start_of_year_str, end_of_year_str);
 		   if(all_accrual_value !=null && all_accrual_value.size()>0)
 			   for(int j=0;j<all_accrual_value.size();j++)
 			   {
 				   MakerAccrualValueModel maker_value_model=(MakerAccrualValueModel)all_accrual_value.get(j);
 				   if(maker_value_model.getStatus_name().equalsIgnoreCase("Approved"))
 				   {
 					   Date value_date = maker_value_model.getValue_date();
 				
 					   if(maker_value_model.getAccrual_value() >= 0)
 						   in[value_date.getMonth()] =  ( Double.parseDouble(in[value_date.getMonth()])+ maker_value_model.getAccrual_value()) + "";
 					   else
 						   out[value_date.getMonth()] = ( Double.parseDouble(out[value_date.getMonth()])+ maker_value_model.getAccrual_value()*-1 )+"";
 				   }
 				   
 			   }
 		   for(int j=0;j<in.length;j++){
 			   accrual_24_in_out_values_str += in[j]+"#"+out[j]+"#";
 		   }
 		   accrual.setAccrual_24_in_out_values_str(accrual_24_in_out_values_str);
 	   }
    }
}
  
}