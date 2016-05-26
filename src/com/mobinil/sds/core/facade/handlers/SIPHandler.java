package com.mobinil.sds.core.facade.handlers;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.commission.dao.CommissionDAO;
import com.mobinil.sds.core.system.commission.factor.dao.FactorDAO;
import com.mobinil.sds.core.system.commission.factor.model.FactorModel;
import com.mobinil.sds.core.system.dcm.genericModel.GenericModel;
import com.mobinil.sds.core.system.dcm.genericModel.DAO.GenericModelDAO;
import com.mobinil.sds.core.system.gn.querybuilder.domain.QueryBuilderEngine;
import com.mobinil.sds.core.system.gn.reports.domain.ReportEngine;
import com.mobinil.sds.core.system.gn.reports.dto.ReportBuilderWizardDTO;
import com.mobinil.sds.core.system.sa.users.dao.UserDAO;
import com.mobinil.sds.core.system.sip.dao.SipDAO;
import com.mobinil.sds.core.system.sip.exportEngine.gatheringDistDataThread;
import com.mobinil.sds.core.system.sip.model.SIPModel;
import com.mobinil.sds.core.system.sip.model.SIPReportTypeModel;
import com.mobinil.sds.core.system.sip.model.savedSipReportModel;
import com.mobinil.sds.core.utilities.Utility;
import com.mobinil.sds.core.utilities.sipReportThread;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.commission.CommissionInterfaceKey;
import com.mobinil.sds.web.interfaces.gn.querybuilder.QueryBuilderInterfaceKey;
import com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey;
import com.mobinil.sds.web.interfaces.sa.UserInterfaceKey;
import com.mobinil.sds.web.interfaces.sip.SIPInterfaceKey;


public class SIPHandler {
		
	static final int SHOW_ALL_SIP_REPORTS = 7 ;                           
	static final int CREATE_NEW_SIP_REPORT=8;
	static final int SAVE_SIP_REPORT_TYPE=9;
	static final int DELETE_SIP_REPORT=10;
	static final int UPDATE_SIP_REPORT=11;
	static final int EDIT_SIP_REPORT=12;
	
	static final int  SAVE_NEW_SIP_REPORT_PARAM =13;

	static final int SAVE_NEW_SIP_REPORT_NO_PARAM=14;
	static final int EXPORT_SIP_LISTS=15;
	static final int EXPORT_DATA=16;
	
	static final int SAVE_NEW_SIP_REPORT =17;
	
	static final int VIEW_SIP_CHANNELS=18;
	static final int SIP_EDIT_CHANNEL=19;
	static final int SIP_SAVE_CHANNEL=20;
	static final int SIP_ASSIGN_CHANNEL_TO_USER=21;
	static final int SIP_USER_CHANNELS=22;
	static final int SIP_NEW_CHANNEL=23;
	static final int SEARCH_SIP=60;
    static final int SIP_SEARCH_SIP=61;
    static final int SIP_DELETE_CHANNEL=30;
    static final int VIEW_SIP_CONFIG=24;
	static final int SIP_EDIT_CONFIG=25;
	static final int SIP_SAVE_CONFIG=26;
	static final int SIP_NEW_CONFIG=27;
//	static final int SIP_EXPORT_DATA=28;
	static final int VIEW_SIP=29;
	static final int SIP_DELETE_CONFIG=31;

	static final int VIEW_SIP_SAVED_REPORT=32;
	static final int SIP_DELETE_SAVED_REPORT=33;
	static final int SIP_NEW_SAVED_REPORT=34;
	static final int SIP_EDIT_SAVED_REPORT=35;
	static final int SIP_SAVE_SAVED_REPORT=36;

	
    
    static final int SIP_UPLOAD_DATA=37;
    static final int SIP_FILE_DETAIL=38;
    static final int SIP_FILE_DELETE=39;
    static final int SIP_CHANGE_STATUS=40;
    static final int SAVE_SIP_FACTORS=41;
    static final int SIP_FACTORS=42;
    static final int SEARCH_FILE=43;
	static final int CREATE_REPORT_TYPE=44;
	
	
static final int  SAVE_NEW_SIP_REPORT_1=45;
static final int SEARCH_SAVED_REPORT=47;

static final int EXPORT_FILE=46;
static final int ACTION_FACTOR_EDIT = 49;
static final int ACTION_FACTOR_UPDATE = 50;

static final int EXPORT_SAVED_REPORT=51;
static final int EXPORT_SAVED_REPORT_EXCEL=52;
	public static  HashMap handle(String action, HashMap paramHashMap,java.sql.Connection con)
	  {
	System.out.println ("Action in sip handler is "+action);
				  int actionType = 0;
				  String pageStatus="";
				  String sipAction = "";
				    HashMap dataHashMap = new HashMap();
				  
				    try
				    {
				    	
				    	
				    	if (action.equals(SIPInterfaceKey.ACTION_SHOW_ALL_SIP_REPORTS))                                   
						        actionType = SHOW_ALL_SIP_REPORTS;
						  else if (action.equals(SIPInterfaceKey.ACTION_CREATE_NEW_SIP_REPORT))
						  		actionType= CREATE_NEW_SIP_REPORT;
						
						  else if (action.equals(SIPInterfaceKey.ACTION_DELETE_SIP_REPORT))
						  		actionType=DELETE_SIP_REPORT;
						  else if (action.equals(SIPInterfaceKey.ACTION_UPDATE_SIP_REPORT))
						  		actionType=UPDATE_SIP_REPORT;
						  else if (action.equals(SIPInterfaceKey.ACTION_EDIT_SIP_REPORT))
						  		actionType=EDIT_SIP_REPORT;
						  else if (action.equals(SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_PARAM))
						  		actionType=SAVE_NEW_SIP_REPORT_PARAM;
						  else if (action.equals(SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_NO_PARAM))
						  		actionType=SAVE_NEW_SIP_REPORT_NO_PARAM;
						  else if(action.equals(SIPInterfaceKey.ACTION_EXPORT_SIP_LIST))
							  actionType=EXPORT_SIP_LISTS;
						  else if(action.equals(SIPInterfaceKey.ACTION_EXPORT_DATA))
							  actionType=EXPORT_DATA;
						  else if(action.equals(SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT))
							  actionType=SAVE_NEW_SIP_REPORT;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_ASSIGN_CHANNEL_TO_USER))
							  actionType=SIP_ASSIGN_CHANNEL_TO_USER;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_EDIT_CHANNEL))
							  actionType=SIP_EDIT_CHANNEL;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_USER_CHANNELS))
							  actionType=SIP_USER_CHANNELS;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_SAVE_CHANNELS))
							  actionType=SIP_SAVE_CHANNEL;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_VIEW_CHANNELS))
							  actionType=VIEW_SIP_CHANNELS;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_NEW_CHANNEL))
							  actionType=SIP_NEW_CHANNEL;
						  else if(action.equals(SIPInterfaceKey.ACTION_SIP_SAVE_CONFIG))
                             actionType=SIP_SAVE_CONFIG;                       
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_VIEW_CONFIG))
                             actionType=VIEW_SIP_CONFIG;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_NEW_CONFIG))
                             actionType=SIP_NEW_CHANNEL;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_EDIT_CONFIG))
                             actionType=SIP_EDIT_CONFIG;
                         else if(action.equals(SIPInterfaceKey.ACTION_EXPORT_DATA))
                            actionType=EXPORT_DATA;
                         else if(action.equals(SIPInterfaceKey.ACTION_VIEW_SIP))
                            actionType=VIEW_SIP;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_DELETE_CHANNEL))
                                actionType=SIP_DELETE_CHANNEL;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_DELETE_CONFIG))
                                actionType=SIP_DELETE_CONFIG;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_SAVE_SAVED_REPORT))
                             actionType=SIP_SAVE_SAVED_REPORT;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_VIEW_SAVED_REPORT))
                             actionType=VIEW_SIP_SAVED_REPORT;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_NEW_SAVED_REPORT))
                             actionType=SIP_NEW_SAVED_REPORT;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_EDIT_SAVED_REPORT))
                             actionType=SIP_EDIT_SAVED_REPORT;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_DELETE_SAVED_REPORT))
                                actionType=SIP_DELETE_SAVED_REPORT;
                         else if(action.equals(SIPInterfaceKey.ACTION_UPLOAD_SALES_SUPERVISOR))
                            actionType=SIP_UPLOAD_DATA;
                         else if(action.equals(SIPInterfaceKey.ACTION_UPLOAD_EXPOS_SUPERDEALER))
                            actionType=SIP_UPLOAD_DATA;
                         else if(action.equals(SIPInterfaceKey.ACTION_UPLOAD_DCM_EXPOS))
                            actionType=SIP_UPLOAD_DATA;				    	
                         else if(action.equals(SIPInterfaceKey.ACTION_UPLOAD_FIELD_REP_SUPERVISOR))
                            actionType=SIP_UPLOAD_DATA;
                         else if(action.equals(SIPInterfaceKey.ACTION_UPLOAD_POS_FIELD_REP))
                            actionType=SIP_UPLOAD_DATA;
                         else if(action.equals(SIPInterfaceKey.ACTION_UPLOAD_LIST_DIST_EXEC))
                            actionType=SIP_UPLOAD_DATA;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_FILE_DETAIL))
                            actionType=SIP_FILE_DETAIL;
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_FILE_DELETE))
                            actionType=SIP_FILE_DELETE;
				    	
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_CHANGE_STATUS))
                            actionType=SIP_CHANGE_STATUS;
				    	
                         else if(action.equals(SIPInterfaceKey.ACTION_SIP_FACTORS))
                            actionType=SIP_FACTORS;
				    	
                         else if(action.equals(SIPInterfaceKey.ACTION_SAVE_SIP_FACTORS))
                            actionType=SAVE_SIP_FACTORS;
				    	
				    	
				    	
						  else if (action.equals(SIPInterfaceKey.ACTION_SEARCH_SIP))
						  {
                             actionType=SEARCH_SIP;
                             pageStatus="0";
                             sipAction = SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP;
						  }
                          else if (action.equals(SIPInterfaceKey.ACTION_SIP_SEARCH_SIP))
                          {
                             actionType=SIP_SEARCH_SIP;
                             pageStatus="0";
                             sipAction = SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP;
                          }
                          else if (action.equals(SIPInterfaceKey.ACTION_VIEW_READY_SIP))
                          {
                             actionType=SEARCH_SIP;
                             pageStatus="2";
                             sipAction = SIPInterfaceKey.INPUT_HIDDEN_FINAL_SIP;
                          }  
                          else if (action.equals(SIPInterfaceKey.ACTION_VIEW_FINAL_SIP))
                          {
                             actionType=SEARCH_SIP;
                             pageStatus="3";
                             sipAction = SIPInterfaceKey.INPUT_HIDDEN_FINAL_SIP;
                          }  
                          else if (action.equals(SIPInterfaceKey.ACTION_SEARCH_FILE))
                          {
                             actionType=SEARCH_FILE;
                             //pageStatus="3";
                             //sipAction = SIPInterfaceKey.INPUT_HIDDEN_FINAL_SIP;
                          }  
                          else if (action.equals(SIPInterfaceKey.ACTION_CREATE_REPORT_TYPE))
                          	actionType=CREATE_REPORT_TYPE;
                          else if (action.equals(SIPInterfaceKey.ACTION_SAVE_SIP_REPORT_TYPE))
                            	actionType=SAVE_SIP_REPORT_TYPE;
				    	
				    	
                          else if (action.equals(SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_1))
                          	actionType=SAVE_NEW_SIP_REPORT_1;
                          else if (action.equals(SIPInterfaceKey.ACTION_EXPORT_FILE))
                            	actionType=EXPORT_FILE;
				    	
				    	
                          else if (action.equals(SIPInterfaceKey.ACTION_SEARCH_SAVED_REPORT))
                          	actionType=SEARCH_SAVED_REPORT;
				    	
                          else if (action.equals(SIPInterfaceKey.ACTION_FACTOR_EDIT))
                            	actionType=ACTION_FACTOR_EDIT;
                          else if (action.equals(SIPInterfaceKey.ACTION_FACTOR_UPDATE))
                          	actionType=ACTION_FACTOR_UPDATE;
				    	
                          else if (action.equals(SIPInterfaceKey.ACTION_EXPORT_SAVED_REPORT))
                            	actionType=EXPORT_SAVED_REPORT;
                          else if (action.equals(SIPInterfaceKey.ACTION_EXPORT_SAVED_REPORT_EXCEL))
                          	actionType=EXPORT_SAVED_REPORT_EXCEL;
				    	
				    	
				    	
				    	
				    	
				    	
				    	
				    switch(actionType) 
					    {
				       
					       case SAVE_NEW_SIP_REPORT:
		                        
	                        {
	                           try{ 
	                            
	                               
	                            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	                       //     Utility.logger.debug("USER ID:  "+strUserID);                            
	                            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	                            boolean sipReportExecutionStatus = false;
	                            String sipId = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID);
	                            String sipReprotChannel   = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CHANNEL);
	                            String sipReprotCategory  = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY);
	                            String sipReprotDesc      = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DESCRIPTION);
	                            String year = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);
	                            String label = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL);
	                            String NI_commission_ids = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS);
	                            String line_coimmission_ids = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS);
	                            String sop_report_ids = (String )paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SOP_IDS);
	                            String report_quarter = (String )paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER);
	                            
	                            String sipReprotName =(String )paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_REPORT_NAME); 
	                        
//	                            System.out.println ("sipId issssssssssss "+sipId.getClass ( ).getName ( ));
	                            if (sipId!=null && sipId.compareTo ( "" )!=0/*&&sipId.compareTo ( "null" )!=0*/)
	                            {
	                               int tempId = SipDAO.updateSipReport(con,sipId,sipReprotName,
	                                     sipReprotDesc,sipReprotCategory,strUserID,sipReprotChannel,report_quarter,year,label,NI_commission_ids,line_coimmission_ids,sop_report_ids);
	                               
	                               if (tempId!=0)
	                               {
	                                  dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , sipReprotName+" updated successfully." );
	                                  sipReportExecutionStatus = true;
	                               }
	                               else
	                               {
	                                  dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , "Faild to update "+sipReprotName+"." );
	                                  sipReportExecutionStatus = false;
	                               }
	                            }
	                            else
	                            {
	                               int sipReprotID = SipDAO.addNewsipReport(con,sipReprotName,
	                                     sipReprotDesc,sipReprotCategory,strUserID,sipReprotChannel,report_quarter,year,label,NI_commission_ids,line_coimmission_ids,sop_report_ids);
	                             if (sipReprotID!=0)
	                             {
	                                dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , sipReprotName+" Added successfully." );
	                                sipId = sipReprotID+"";
	                                sipReportExecutionStatus = true;
	                             }
	                             else
	                             {
	                                dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , "Faild to add "+sipReprotName+"." );
	                                sipReportExecutionStatus = false;
	                             }
	                               
	                               
	                            }
	                            
	                            if (sipReportExecutionStatus)
	                            {
	                            gatheringDistDataThread distThread = new gatheringDistDataThread(sipId,sipReprotCategory,label,report_quarter,year,strUserID);
	                            distThread.start ( );
	                            }
	        
	                            
	                                                 
	                            
	                            
	                           dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR,year);
	                           dataHashMap.put(SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL,label);
	                           dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS,NI_commission_ids);
	                           dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS,line_coimmission_ids);
	                           dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_SOP_IDS,sop_report_ids);
	                           dataHashMap.put(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER,report_quarter);
 
	                           dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,
	                                           SIPInterfaceKey.INPUT_HIDDEN_FINAL_SIP);

	                           dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS , "2");  
	                           
	                           dataHashMap = defaultVectors(dataHashMap,con,strUserID);
	                           
	                           }
	                           catch(Exception ex)
	                           {
	                               
	                               ex.printStackTrace();
	                           }
	                        }
	                        
	                        break; 
	                        
					    case SAVE_NEW_SIP_REPORT_PARAM:
                           {
                               
                               String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                               Utility.logger.debug("USER ID:  "+strUserID);
                               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 

                               GenericModel sipReprotTypeModel = GenericModelDAO.getColumns(con,"sip_report_type");
                            
                               Vector sipReprotTypes = GenericModelDAO.getModels(con,sipReprotTypeModel);
                            
                               dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE ,sipReprotTypes);
                               
                              // GenericModel sipReportTypeCategoryModel = GenericModelDAO.getColumns(con,"sip_report_TYPE_CATEGORY");
                            
                               
                            //   Vector sipReportTypeCategories = GenericModelDAO.getModels(con ,sipReportTypeCategoryModel);
                            //   dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE_CATEGORY, sipReportTypeCategories);

                               GenericModel sipReportStatusModel = GenericModelDAO.getColumns(con,"sip_report_status_type");
                               Vector sipReprotStatus = GenericModelDAO.getModels(con ,sipReportStatusModel);
                               dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS,sipReprotStatus);

                               dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ACTION,SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP_REPORT);
                               dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_STATUS,"0");

                               int sipReportID = Integer.parseInt((String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID));
                               System.out.println("SIP REPORT ID ="+ sipReportID);
                           
                               String dataViewIDs=  SipDAO.getSavedsipReportByID(con, sipReportID +"").getsipReportDataViewID();
                               System.out.println ("dataViewIDs issssssssssssssssssss "+dataViewIDs);
                               int dataViewID =Integer.parseInt( dataViewIDs);   //                              SipDAO.getsipReportByID(con,sipReportID+"").getsipReportDataViewID();
                               ReportBuilderWizardDTO reportBuilderWizardDTO = CommissionDAO.getDataViewParameters(con,dataViewID);
                               ReportEngine reportEngine =  new ReportEngine();
                               Vector paramList = reportEngine.constructReportParamList(paramHashMap);
                               String SQLQuery = CommissionDAO.previewReport(con,reportBuilderWizardDTO.getReport(),paramList ,dataViewID);
                               Utility.logger.debug(SQLQuery);   
                              

                             
                              
                               
                               String dataViewType =  SipDAO.getSavedsipReportByID(con, sipReportID +"").getsipReportDataViewTypeName();
                               
                               
                               System.out.println("**** updating the sql string");

                                SipDAO.updatesipReportSQLString(con, SQLQuery, sipReportID);
                               Utility.logger.debug("StartingThread");
                               Thread sipThread = new sipReportThread(dataViewType , sipReportID , strUserID);
                               sipThread.start();
                               Utility.logger.debug("Thread Started");
                              
                         
                              // to be discussed with hagry 
                          //   Vector sipReportChannelVec = GenericModelDAO.getsipReportChannel(con, strUserID);            
                          //   dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,sipReportChannelVec);
                     
                               dataHashMap.put(SIPInterfaceKey.SAVED_SIP_REPORT_ID,sipReportID+"" );
            	        	   
    			        	   dataHashMap = defaultVectors(dataHashMap,con,strUserID);
    			        	   
    			        	   
    			        		dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getAllSavedsipReportByID(con));
                                         
                           }
                           break;
                           
                           case SAVE_NEW_SIP_REPORT_NO_PARAM:
                           {
                               
                              
                        	   
                        	   String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                               Utility.logger.debug("USER ID:  "+strUserID);
                               System.out.println("The user id isssssss " + strUserID);
                               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
                            System.out.println(" The sip  Report id in  the  SAVE_NEW_SIP_REPORT_NO_PARAM    isssssssssss   "+  paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID ) );
                               int sipReportID =Integer.parseInt((String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID));
                              
                               String dataViewType =  SipDAO.getSavedsipReportByID(con, sipReportID +"").getsipReportDataViewTypeName();
                               

                              
                               System.out.println("The did not yet  thread has  started  with  the action no  parameters");         
                                 Utility.logger.debug("StartingThread");
                                Thread sipReprotThread = new sipReportThread(dataViewType , sipReportID ,strUserID);
                                sipReprotThread.start();
                                
                               System.out.println("The thread has  started  with  the action no  parameters");
                               Utility.logger.debug("Thread Started");
                               GenericModel sipReportTypeModel = GenericModelDAO.getColumns(con,"sip_report_type");
                               Vector sipReportTypes = GenericModelDAO.getModels(con,sipReportTypeModel);
                           
                               dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE , sipReportTypes);
                               
                             //  GenericModel sipReportTypeCategoryModel = GenericModelDAO.getColumns(con,"sip_report_TYPE_CATEGORY");
                             //  Vector sipReportTypeCategories = GenericModelDAO.getModels(con ,sipReportTypeCategoryModel);
                            //   dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE_CATEGORY , sipReportTypeCategories);

                               GenericModel sipReportStatusModel = GenericModelDAO.getColumns(con,"sip_report_STATUS_TYPE");
                               Vector sipReportStatus = GenericModelDAO.getModels(con ,sipReportStatusModel);
                               dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS , sipReportStatus);

                               dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ACTION,SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP_REPORT);
                               dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_STATUS,"0");
                           
                         //      Vector sipReportChannelVec = GenericModelDAO.getsipReportChannel(con, strUserID);
                          //     dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,sipReportChannelVec);
                               
                               dataHashMap.put(SIPInterfaceKey.SAVED_SIP_REPORT_ID,sipReportID+"" );
                               
                               dataHashMap = defaultVectors(dataHashMap,con,strUserID);
    			        	   
    			        	   
   			        		dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getAllSavedsipReportByID(con));
                             
                           }
                           break;  
                           
						    	case SHOW_ALL_SIP_REPORTS:
						    	     
						              {
						               
						               String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						               dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						               
						               Vector vecSchemaModule = SipDAO.getAllSipReports(con);
						               System.out.println("Vector size is ddddddddddddddddd: "+vecSchemaModule.size());
						               dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,vecSchemaModule);
						             }
						           
						    	
						    	break;
						    	
						    	case CREATE_NEW_SIP_REPORT:
						  		
						    	{
						              
						              String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						              Utility.logger.debug("USER ID:  "+strUserID);
						              dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						              
//						              GenericModel sipReportTypeCategoryModel = GenericModelDAO.getColumns(con,"SIP_REPORT_TYPE_CATEGORY");
//						              Vector commissionTypeCategories = GenericModelDAO.getModels(con ,sipReportTypeCategoryModel);
//						              dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE_CATEGORY , commissionTypeCategories);
						              
						              String sipReportId = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
						              
						              if (sipReportId!=null && sipReportId.compareTo ( "" )!=0)  
						              {
						                 dataHashMap.put(SIPInterfaceKey.SIP_REPORT_EDITTED_MODEL,SipDAO.SIPReportModelByID(con,sipReportId) );
						                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID,sipReportId );
						              }
						             
//						             
						              dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS,pageStatus );
	                                  
	                                  dataHashMap = defaultVectors(dataHashMap,con,strUserID);
						    	   }
					  
						  	    
						  		  
						    	break;
						    	
						    	case SAVE_SIP_REPORT_TYPE:
						    	 
						            {
						          	System.out.println("sdfsdfsdfsdfsddf");
						          	dataHashMap = new HashMap ();
						                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						                 Long strSipReportId = Utility.getSequenceNextVal(con, "SIP_REPORT_TYPE_SEQ");
						                 String strSipReportName = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_REPORT_NAME);
					
						                 SipDAO.insertSipReport(con,strSipReportId,strSipReportName);
						                 Vector vecSchemaModule = SipDAO.getAllSipReports(con);
						                 dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,vecSchemaModule);
						            }
				    
			    	
						    	break;
						    	
						    	case DELETE_SIP_REPORT:
						    	{
						    		  try
						    	         {

						    	       	dataHashMap = new HashMap();
						    	           String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						    	           dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						    	           String strSipReportID = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID);
						    	           SipDAO.deleteSipReportType(con,strSipReportID);
						    	           Vector vecSchemaModule = SipDAO.getAllSipReports(con);
						    	           dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,vecSchemaModule); }
						    	        catch(Exception objExp)
						    	         {
						    	           objExp.printStackTrace();
						    	         }
						    	}
						    	break;
			    
						    	case UPDATE_SIP_REPORT:
						    	{
						   		  try
							      {
							        dataHashMap = new HashMap ();
							        String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
							        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
							        String strReportId = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID);
							        SIPReportTypeModel sipReportModel = (SIPReportTypeModel) SipDAO.selectReportType(con,strReportId);
							        System.out.println("ACCCCCCCCCCCCCCCCC");
							        dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,sipReportModel);
							      }
							        catch(Exception objExp)
							      {
							        objExp.printStackTrace();
							      }
						  		  
						    	}
						    	break;

						    	
						    	
						       
						        
						        
						    	case EDIT_SIP_REPORT:
						    		  
						    	   {try
						 		      {
						 		    	System.out.println("###########update action###########");
						 		    	dataHashMap = new HashMap();
						 		        String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
						 		        dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
						 		        String strSipReportID = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID);
						 		        String strSipReportName = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_REPORT_NAME);
						 	
						 		        
						 		        SipDAO.editSipReport(con,strSipReportID,strSipReportName);
						 		        Vector vecSchemaModule = SipDAO.getAllSipReports(con);
						 		        dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,vecSchemaModule); 

						 		      }
						 		    catch(Exception objExp)
						 		      {
						 		        objExp.printStackTrace();
						 		      }
						    	   }
						    	break;
						    	case EXPORT_SIP_LISTS:
						    		{try
						    		{
						    			
						    			String reportId = (String) paramHashMap
										.get(SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID);
						    	String path=(String)  paramHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
						    	System.out.println("Path is   "+path);
								dataHashMap.put(SIPInterfaceKey.CONTROL_HIDDEN_REPORT_TYPE_ID, reportId);

						    			path = path + "export" + reportId + ".csv";
			
						    			System.out.println("Path + file name is   "+path);
								File f = new File(path);
								if (f.exists())
									f.delete();
								dataHashMap.put(SIPInterfaceKey.FILE_EXPORT, path);
						//hagry what is that needed for
								//FileOutputStream fis = new FileOutputStream(path);
								    //hagry what is that needed for
								//int finalId=Integer.parseInt(reportId);


								return dataHashMap;
							
						    			
						    		}
						    		catch(Exception ex)
						    		{
						    			
						    			
						    		}
						    		}
						    		break;
						    	case EXPORT_DATA:
						    		{System.out.println ("here in export data");   
	                                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	                                 System.out.println ("here in export data user id "+strUserID);
	                                 Utility.logger.debug("USER ID:  "+strUserID); 
	                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,/*sipAction*/(String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION));
	                                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
	                                 
	                                 dataHashMap = defaultVectors(dataHashMap,con,strUserID);
						    		}
						    		break;	
						    	case SIP_SAVE_CHANNEL:
						        {
						        	String channelId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
						        	String channelName= (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_CHANNEL_NAME);
						        	if (channelId.compareTo("")==0||channelId==null)
						        	{
						        		SipDAO.insertSipChannels(con,channelName);	
						        	}
						        	else
						        	{
						        		SipDAO.updateSipChannels(con, channelId, channelName);
						        	}
						        	
						        	dataHashMap.put(SIPInterfaceKey.CHANNEL_VECTOR,SipDAO.getSIPChannels(con,false,""));
						        }
						    	
						    	break;
						    	
						        case SIP_EDIT_CHANNEL:
						        {
						        	String channelId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);        	
						        	dataHashMap.put(SIPInterfaceKey.CHANNEL_MODEL,SipDAO.getSIPChannels(con,true,channelId));
						        	
						        }
						        break;
						        case SIP_USER_CHANNELS:
						        {                      
						                      
						            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));            
						            dataHashMap.put(SIPInterfaceKey.CHANNEL_VECTOR,SipDAO.getSIPChannels(con,false,""));
						            dataHashMap.put(SIPInterfaceKey.USER_CHANNEL_VECTOR,SipDAO.getUserChannels(con));            
						            
						        }
						      break;
						      case SIP_ASSIGN_CHANNEL_TO_USER:
						        {   
						        	 System.out.println("dsfhsdljfh");
						            int paramHashMapSize = paramHashMap.size();
						            String strPersonID = (String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);
						            System.out.println("The user iddddddddddddddd isssssssssss " + strPersonID);
						            SipDAO.deleteUserChannels(con,strPersonID);                  
						            for(int i=0;i<paramHashMapSize;i++) 
						            {
						                String tempKey = (String) paramHashMap.keySet().toArray()[i];

						                if(tempKey.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)) 
						                {
						                    String channelIdAndPersonIdKey = tempKey.substring(9);
						                    
						                    if (channelIdAndPersonIdKey.startsWith(strPersonID)){
						                    	
						                    	String channelIdKey = channelIdAndPersonIdKey.substring(strPersonID.length());
						                    	
						                    	SipDAO.insertUserChannels(con,strPersonID,channelIdKey);	
						                    }                   
						                    
						                    
						                    
						                }
						            }
						            
						            dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
						            dataHashMap.put(SIPInterfaceKey.CHANNEL_VECTOR,SipDAO.getSIPChannels(con,false,""));
						            dataHashMap.put(SIPInterfaceKey.USER_CHANNEL_VECTOR,SipDAO.getUserChannels(con));          
						            
						        }
						      break; 
						      case VIEW_SIP_CHANNELS:
						      {	dataHashMap.put(SIPInterfaceKey.CHANNEL_VECTOR,SipDAO.getSIPChannels(con,false,""));
						      }
						    	  
						    	  break;
						      case SIP_NEW_CHANNEL:
						      {}
						    	  break;
						    	  
						      case SEARCH_SIP:
                              {                                  
                                  String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);                                  
                                  Utility.logger.debug("USER ID:  "+strUserID);                                   
                                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                                

                                  dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,sipAction/*SIPInterfaceKey.INPUT_HIDDEN_ALL_SIP*/);
                                  
                                  
                                  dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS,pageStatus );
                                  
                                  dataHashMap = defaultVectors(dataHashMap,con,strUserID);
                                  
                              }                              
                              break;
						      case SIP_CHANGE_STATUS:
                              {
                                 
                                 String sipReportId            = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
                                 String sipReportStatus        = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_CHANGE_STATUS);
                                 String sipChannelId        = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_CHANNEL_ID);
                                 String sipQuartarId        = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_QUARTAR_ID);
                                 String sipyearId        = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_YEAR_ID);
                                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                                 System.out.println ("sipQuartarId isss "+sipQuartarId);
                                 System.out.println ("sipChannelId issss "+sipChannelId);
                                 System.out.println ("sipReportStatus issss "+sipReportStatus);
                                 
                                 
                                 
                                 if (sipReportStatus.compareTo ( SIPInterfaceKey.SIP_REPORT_STATUS_FINAL)==0 )
                                 {
                                    boolean checkBeforeChangeStatus = SipDAO.checkBeforeChangeStatue ( con , sipChannelId , sipQuartarId,sipyearId );
                                    
                                    System.out.println ("checkBeforeChangeStatus isss "+checkBeforeChangeStatus);
                                    if(checkBeforeChangeStatus)
                                    {
                                       dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , SIPInterfaceKey.SIP_REPORT_STRING_ERROR_MESSAGE ); 
                                    }
                                    else
                                    {
                                       SipDAO.changeSipReportStatus ( con , sipReportId , sipReportStatus );
                                       dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , "Report changed to be final successfully." );
                                    }
                                 }
                                 else{
                                    SipDAO.changeSipReportStatus ( con , sipReportId , sipReportStatus );   
                                    dataHashMap.put ( SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE , "Report deleted successfully." );
                                 }
                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,
                                       SIPInterfaceKey.INPUT_HIDDEN_FINAL_SIP);

                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS , "2"); 
                                 
                                 
                                 dataHashMap = defaultVectors(dataHashMap,con,strUserID);
                                 dataHashMap=    searchInSIP(con,paramHashMap,dataHashMap);
                                 
                                 
                                
                                 
                                 
                                 
                              }
                              break;
                              case SIP_SEARCH_SIP:
                              {
                                  
                                  
                             dataHashMap=    searchInSIP(con,paramHashMap,dataHashMap);
//                                
                              }
                              break;
                  			case SIP_SAVE_CONFIG:
                			{
                				String configId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID);
                				String configName= (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_CONFIG_NAME);
                				String configType=(String) paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_CONFIG_TYPE);
                				if (configId.compareTo("")==0||configId==null)
                				{
                					SipDAO.insertSipConfigs(con,configName);	
                				}
                				else
                				{
                					SipDAO.updateSipConfigs(con, configId, configName,configType);
                				}
                				
                				dataHashMap.put(SIPInterfaceKey.CONFIG_VECTOR,SipDAO.getSIPConfigs(con,false,""));
                			}
                			
                			break;
                			
                			case SIP_EDIT_CONFIG:
                			{
                				String configId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID);        	
                				dataHashMap.put(SIPInterfaceKey.CONFIG_MODEL,SipDAO.getSIPConfigs(con,true,configId));
                				
                			}
                			break;
                			  case VIEW_SIP_CONFIG:
                			  {	dataHashMap.put(SIPInterfaceKey.CONFIG_VECTOR,SipDAO.getSIPConfigs(con,false,""));
                			  }
						    	  
						      break;
						      
                			  case SIP_NEW_CONFIG:
                			  {}
                			  break;
                			  case SIP_DELETE_CHANNEL:
                			  {
                					String configId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
                    				String configName= (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_CHANNEL_NAME);
                    				//String configType=(String) paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_CONFIG_TYPE);
                					SipDAO.deleteSipChannel(con, configId);
              				
              				
                					dataHashMap.put(SIPInterfaceKey.CHANNEL_VECTOR,SipDAO.getSIPChannels(con,false,""));
              		
                				  
                			  }
                			  break;
                			  case SIP_DELETE_CONFIG:
                			  {
                					String configId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID);
                    				String configName= (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_CONFIG_NAME);
                					SipDAO.deleteSipConfig(con, configId);
              				
              				
                					dataHashMap.put(SIPInterfaceKey.CONFIG_VECTOR,SipDAO.getSIPConfigs(con,false,""));
              		
                				  
                			  }
                			  break;  
                  			case SIP_SAVE_SAVED_REPORT:
                			{
                				String reportId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
                				String reportName= (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SAVED_REPORT_NAME);
                				String reportType=(String) paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SAVED_REPORT_TYPE);
                				if (reportId.compareTo("")==0||reportId==null)
                				{
                					SipDAO.insertSipReport(con,reportName,reportType);	
                				}
                				else
                				{
                					SipDAO.updateSipReport(con, reportId, reportName,reportType);
                				}
                				
                				dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getSIPReports(con,false,""));
                			}
                			
                			break;
                			
                			case SIP_EDIT_SAVED_REPORT:
                			{
                				String reportId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
                				
                				dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_MODEL,SipDAO.getSIPReports(con,true,reportId));
                				dataHashMap.put(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_TYPE_ID, (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_TYPE_ID));
                				
                				
                			}
                			break;
                			  case VIEW_SIP_SAVED_REPORT:
                			  {	
                				 // dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getSIPReports(con,false,""));
                			 
                				   String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                                   Utility.logger.debug("USER ID:  "+strUserID);
                                   System.out.println("The user id isssssss " + strUserID);
                                   dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
                			  
                             Vector   savedReportList =SipDAO.getAllSavedsipReportByID(con);
                            // System.out.println("The  vector  size  in  the VIEW_SIP_SAVED_REPORT is"+savedReportList.size());
                                   
                 
                             
                             
                      		 GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
                      	     Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
                      	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
                      	       
                      	     GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
                      	     Vector sipYear = GenericModelDAO.getModels(con ,sipYearModel);
                      	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear);
                      	       
                      	       
                      	       
                      	     GenericModel sipReportTypeModel = GenericModelDAO.getColumns(con,"sip_report_type");                                 
                      	     Vector sipReportType = GenericModelDAO.getModels(con ,sipReportTypeModel);
                      	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE, sipReportType);
                      	       
                      	     GenericModel sipReportStatusTypeModel = GenericModelDAO.getColumns(con,"sip_report_Status_type");                                 
                    	     Vector sipReportStatus = GenericModelDAO.getModels(con ,sipReportStatusTypeModel);
                    	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS, sipReportStatus);
                      		
                             
                             
                             
                             
                             
                             
                             
                             
                                   
             			//  dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getAllSavedsipReportByID(con));
                			  
                  	  }
						    	  
						      break;
						      
                			  case SIP_NEW_SAVED_REPORT:
                			  {
                				  
                		         
                				  String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                		          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
                				  QueryBuilderEngine queryBuilderEngine = new QueryBuilderEngine() ;
                				  Vector vecDataViewsList = queryBuilderEngine.listDataViews ( con,QueryBuilderInterfaceKey.UNIVERSE_TYPE_AD_HOC_REPORTS );
                				
                				  GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");   
                				  Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
                			       Vector vecSipReport = SipDAO.getAllSipReports(con);
  					               dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,vecSipReport);
                				  
                				  
                				  
                				  dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
  	                              dataHashMap = yearVector(dataHashMap,con);
					              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, vecDataViewsList);
	  
                			  }
                			  break;	  
                			  case SIP_DELETE_SAVED_REPORT:
                			  {
                					
                  				   String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                                   Utility.logger.debug("USER ID:  "+strUserID);
                                   System.out.println("The user id isssssss " + strUserID);
                                   dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID); 
                				  
                				  String reportId = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
                    			//	String reportName= (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SAVED_REPORT_NAME);
                					
                				if(SipDAO.checksavedSipExistSip(con, reportId)) 
                				{
                            dataHashMap.put(SIPInterfaceKey.SIP_REPORT_ERROR_MESSAGE   ,"  Report Used  By  Sip Report ");
                				}
                				else
                				{
                					SipDAO.deleteSavedSipReport(con, reportId);
                					
                				}
                				  
                				  
                                Vector   savedReportList =SipDAO.getAllSavedsipReportByID(con);
                              //  System.out.println("The  vector  size  in  the VIEW_SIP_SAVED_REPORT is"+savedReportList.size());
                                      
                    
                                
                                
                         		 GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
                         	     Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
                         	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
                         	       
                         	     GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
                         	     Vector sipYear = GenericModelDAO.getModels(con ,sipYearModel);
                         	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear);
                         	       
                         	       
                         	       
                         	     GenericModel sipReportTypeModel = GenericModelDAO.getColumns(con,"sip_report_type");                                 
                         	     Vector sipReportType = GenericModelDAO.getModels(con ,sipReportTypeModel);
                         	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE, sipReportType);
                         	       
                         	     GenericModel sipReportStatusTypeModel = GenericModelDAO.getColumns(con,"sip_report_Status_type");                                 
                       	     Vector sipReportStatus = GenericModelDAO.getModels(con ,sipReportStatusTypeModel);
                       	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS, sipReportStatus);
                				
                				
              				
              				
                				//	dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getAllSavedsipReportByID(con));
              		
                				  
                			  }
                			  break;  
//                			  case SIP_EXPORT_DATA:                			     
//                			     {
//                			     System.out.println ("here in export data");   
//                			     String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
//                			     System.out.println ("here in export data user id "+strUserID);
//                                 Utility.logger.debug("USER ID:  "+strUserID);                                   
//                                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
//                                 
//                                 dataHashMap = defaultVectors(dataHashMap,con,strUserID);
//                			     }
//                                 break;
                                 
                			  case VIEW_SIP:
                		        {
                		            
                		            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                		            Utility.logger.debug("USER ID PAYMENT  HANDLER:  "+strUserID);
                		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                		            String sipIDs = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);
                		            Vector sipFactors =  new Vector();
                		            sipFactors.add(SipDAO.getSIPFactors(con,sipIDs));                          
                		            dataHashMap.put(PaymentInterfaceKey.VECTOR_PAYMENT_COMMISSION_FACTOR, sipIDs);
                		            Vector comVec = GenericModelDAO.getCommissionChannel(con, strUserID);
                		            dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,comVec);
                		        }
                		        break;
                			  case SIP_UPLOAD_DATA:
                              {
                                 
                                   dataHashMap = yearVector(dataHashMap,con);
                              }                           
                              break;
                              case SIP_FILE_DETAIL:
                                 {
                                       
                                    dataHashMap = searchFile ( con , paramHashMap , dataHashMap )   ;
                                 }
                                    break;
                              case SIP_FILE_DELETE:
                              {
                                     String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                                     String fileID = (String) paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_FILE_ID);
                                     String typeID = (String) paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_FILE_TYPE_ID);
                                     SipDAO.deleteFile(con,fileID,typeID);   
                                     Vector files =SipDAO.getallfiles(con);                                     
                                     dataHashMap.put(SIPInterfaceKey.VECTOR_FILES,files);
                                     dataHashMap = searchFileVectors ( con , paramHashMap , dataHashMap, files);
                              }
                                 break;
                                 
                              case SAVE_SIP_FACTORS:
                              {
                                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                                 String sipID = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);                            
                                 //dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_COMMISSION_ID,sipID);
                                 String sipType = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_TYPE_ID);
                                 //dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_COMMISSION_TYPE_ID,sipType);
                                 int value = 1;
                                 if(sipType.equals("1"))
                                   value = 1;
                                 else if(sipType.equals("2"))
                                   value = -1;
                                   
                                 for(int k=0; k<paramHashMap.size(); k++)
                                 {
                                   String tempStatusString = (String)paramHashMap.keySet().toArray()[k];
                                   if(tempStatusString.startsWith(SIPInterfaceKey.CONTROL_TEXT_SIP_FACTOR_VALUE))
                                   {
                                     String factorID = tempStatusString.substring(SIPInterfaceKey.CONTROL_TEXT_SIP_FACTOR_VALUE.length ( )+1);
                                     System.out.println ("factorID isssssssss "+factorID);
                                     Utility.logger.debug("factorID: "+factorID);
                                     String factorValue = (String)paramHashMap.get(tempStatusString);
//                                     factorValue = String.valueOf(Double.parseDouble(factorValue)*value); 
                                     Utility.logger.debug(factorValue);
                                     SipDAO.setFactorValue(con ,factorID,sipID,factorValue);
                                   }
                                 }
                                 


//                                 GenericModel sipTypeModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE");
//                                 Vector sipTypes = GenericModelDAO.getModels(con,sipTypeModel);
//
//                                 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_TYPE , sipTypes);
//                                 
//                                 GenericModel sipTypeCategoryModel = GenericModelDAO.getColumns(con,"COMMISSION_TYPE_CATEGORY");
//                                 Vector sipTypeCategories = GenericModelDAO.getModels(con ,sipTypeCategoryModel);
//                                 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_TYPE_CATEGORY , sipTypeCategories);
//
//                                 GenericModel sipStatusModel = GenericModelDAO.getColumns(con,"COMMISSION_STATUS_TYPE");
//                                 Vector sipStatus = GenericModelDAO.getModels(con ,sipStatusModel);
//                                 GenericModel statusModel = null;
//                                 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_STATUS , sipStatus);
//
//                                 Vector searchResults = CommissionDAO.searchCommissionByFilter(con,"","","2","0","0","*","*","*","*","0",strUserID);                                
//                                 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT,searchResults);

                                 dataHashMap = defaultVectors(dataHashMap,con,strUserID);
                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,
                                                 SIPInterfaceKey.INPUT_HIDDEN_FINAL_SIP);

                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS , "2");  
                              }
                                 break;
                           
                             
                                 
                              case SIP_FACTORS:
                              {
                                 String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
                                 Utility.logger.debug("USER ID:  "+strUserID);
                                 dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
                                 String sipReportID = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID);                            
                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID,sipReportID);
                                 SIPModel sipModel = SipDAO.getSipReportByID(con,sipReportID);
                                 String sipType = String.valueOf(sipModel.getsipTypeCtageoryID ( ));
                                 Vector sipFactors = SipDAO.getSipFactorValues(con,sipReportID);
                                 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_FACTORS , sipFactors);
                                 dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_TYPE_ID,sipType);
                              }
                                 break;   
                              case CREATE_REPORT_TYPE:
                          	{
                			/*	String configId = (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_TYPE_ID);
                				//String configName= (String)paramHashMap.get(SIPInterfaceKey.VECTOR_SCHEMA_SIP,);
                			//	String configType=(String) paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_CONFIG_TYPE);
                			//	if (configId.compareTo("")==0||configId==null)
                				//{
                					SipDAO.insertSipConfigs(con,configName);	
                			//	}
                			///	else
                			//	{
                			//		SipDAO.updateSipConfigs(con, configId, configName,configType);
                			//	}
                				
                				dataHashMap.put(SIPInterfaceKey.VECTOR_SCHEMA_SIP,SipDAO.getAllSipReports(con));//(con,false,""));
                			*/}
                              break;
                  	    	case SEARCH_FILE:
						  		
					    	{
					              
					            String incomingAction = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);                                  
                                String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);                                  
                                                                    
                                String sipYear            = (String)paramHashMap.get(SIPInterfaceKey.INPUT_SEARCH_YEAR);                                  
                                String sipQuarter          = (String)paramHashMap.get(SIPInterfaceKey.INPUT_SEARCH_QUARTER);                            
                                String sipType			  =(String)paramHashMap.get(SIPInterfaceKey.INPUT_SEARCH_FILE_TYPE);
                                String userIdInFilter = (String)paramHashMap.get(SIPInterfaceKey.HASHMAP_KEY_SEARCH_USER_ID);
                                
                                Vector searchResults = SipDAO.searchSIPFileByFilter( con,
                                		sipYear,                      
                                		sipQuarter,sipType,userIdInFilter
                           );
                               // dataHashMap = defaultVectors(dataHashMap,con,strUserID);
                                
                                dataHashMap = searchFileVectors ( con , paramHashMap , dataHashMap, searchResults);
                                
                                  System.out.println ("sipAction in handler issssssssss "+(String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION));
                                dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,/*sipAction*/(String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION));
                                dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID==null?"":strUserID);
                                dataHashMap.put(SIPInterfaceKey.INPUT_SEARCH_YEAR,sipYear==null?"":sipYear);
                                dataHashMap.put(SIPInterfaceKey.INPUT_SEARCH_QUARTER,sipQuarter==null?"":sipQuarter);                                    
                                dataHashMap.put(SIPInterfaceKey.INPUT_SEARCH_FILE_TYPE,sipType==null?"":sipType);
                                

					              //Utility.closeConnection (con);
					              
					              
					              // Mahmoud 
					           //   dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS,pageStatus );
                                  
                                 // dataHashMap = defaultVectors(dataHashMap,con,strUserID);
					    	   }
				  
					  	    
					  		  
					    	break; 
					    	
                  	    	case EXPORT_FILE:
                  			{
                  	  			String reportId = (String) paramHashMap
								.get(SIPInterfaceKey.CONTROL_HIDDEN_FILE_TYPE_ID);
				    	String path=(String)  paramHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
				    	System.out.println("Path is   "+path);
						dataHashMap.put(SIPInterfaceKey.CONTROL_HIDDEN_FILE_TYPE_ID, reportId);

				    			path = path + "export" + reportId + ".csv";
	
				    			System.out.println("Path + file name is   "+path);
						File f = new File(path);
						if (f.exists())
							f.delete();
						dataHashMap.put(SIPInterfaceKey.FILE_EXPORT, path);
					//	FileOutputStream fis = new FileOutputStream(path);
					
						//int finalId=Integer.parseInt(reportId);
						}
                  	    	break;
					    	
                  	    	
                  	    	
                  	    	
                  	    	
                  	     	case SEARCH_SAVED_REPORT:
                      		{
                      
    						
                      		
                      		
                      		 GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
                      	     Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
                      	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
                      	       
                      	     GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
                      	     Vector sipYear = GenericModelDAO.getModels(con ,sipYearModel);
                      	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear);
                      	       
                      	       
                      	       
                      	     GenericModel sipReportTypeModel = GenericModelDAO.getColumns(con,"sip_report_type");                                 
                      	     Vector sipReportType = GenericModelDAO.getModels(con ,sipReportTypeModel);
                      	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE, sipReportType);
                      	       
                      	     GenericModel sipReportStatusTypeModel = GenericModelDAO.getColumns(con,"sip_report_Status_type");                                 
                    	     Vector sipReportStatus = GenericModelDAO.getModels(con ,sipReportStatusTypeModel);
                    	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS, sipReportStatus);
                    	     
                    	     String sipReportQuarter   = (String)paramHashMap.get( SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER);   
                    	     String sipReportId        = (String)paramHashMap.get( SIPInterfaceKey.CONTROL_TEXT_SIP_ID);  
                    	     String sipReportYear   = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);   
                    	     String sipName      = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_NAME); 
                    	     String sipStatus   = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_STATUS);  
                    	     String sipType    = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE);   
                    	     
                    	     
                    	     System.out.println(sipReportQuarter);
                    	     System.out.println(sipReportId); 
                    	     System.out.println(sipReportYear);
                    	     System.out.println(sipName);
                    	     System.out.println(sipStatus);
                    	     System.out.println(sipType);
                    	     if(sipReportQuarter==null)
                    	     {
                    	    	 sipReportQuarter=""; 
                    	    	 
                    	     }
                    	     if(sipReportId==null)
                    	     {
                    	    	 sipReportId=""; 
                    	    	 
                    	     }
                    	     if(sipReportYear==null)
                    	     {
                    	    	 sipReportYear=""; 
                    	    	 
                    	     }
                    	     if(sipName==null)
                    	     {
                    	    	 sipName=""; 
                    	    	 
                    	     }
                    	     if(sipStatus==null)
                    	     {
                    	    	 sipStatus=""; 
                    	    	 
                    	     }
                    	     if(sipType==null)
                    	     {
                    	    	 sipType=""; 
                    	    	 
                    	     }
                    	     
                    	     
                    	     
                    	 
                      		
                    	     dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.searchSavedReportByFilter(con,sipReportId,sipName,sipReportQuarter,sipReportYear,sipStatus,sipType));
                      		
                      		}
                      	    	break;
                  	    
                  	    	
                  	 
                  	    	
                  	    	
                  	    	
                  	     	case ACTION_FACTOR_EDIT:
                  	     	{
                  	     		String sip_report_id = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
                  	     		Vector sip_factors = FactorDAO.getsipReportFactors(con, sip_report_id);
                  	     		Vector options = new Vector();
                  	     		options.add("Not Included");options.add("Included");
                  	     		dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_FACTORS, sip_factors);
                  	     		dataHashMap.put(SIPInterfaceKey.FACTOR_OPTIONS, options);
                  	     		dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID, sip_report_id);
                  	     		
                  	     	}
                  	     	break;
                  	     	
                  	     	
                  	     	
                  	    	
                  	     	case EXPORT_SAVED_REPORT:
                  	     	{
                  	     
                  	     	
                  	     		String sip_report_id = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
                  	     		Vector sip_item_amount = SipDAO.getItemNameAmount(con, sip_report_id);
                  	     	
                  	     		dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_ITEM_AMOUNT, sip_item_amount);
                  	     		dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID, sip_report_id);
                  	     		
                  	     	}
                  	     	break;
	                  	  	case EXPORT_SAVED_REPORT_EXCEL:
	              	     	{
	              	     		String sip_report_id = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
	              	     		Vector sip_item_amount = SipDAO.getItemNameAmount(con, sip_report_id);
	              	     	
	              	     		dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_ITEM_AMOUNT, sip_item_amount);
	              	     		dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID, sip_report_id);              	     		
	              	     	}
	              	     	break;
                  	     	
                  	     	
                  	     	
                  	     	
                  	     	
                  	     	
                  	     	
                  	     	case ACTION_FACTOR_UPDATE:
                  	     	{
                  	     		String sip_report_id = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_HIDDEN_SAVED_REPORT_ID);
                  	     		Vector sip_factors = FactorDAO.getsipReportFactors(con, sip_report_id);
                  	     		for(int i=0;i<sip_factors.size();i++)
                  	     		{
                  	     			FactorModel factor = (FactorModel)sip_factors.get(i);
                  	     			String options_select_value = (String)paramHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_FACTOR_VALUE+"_"+factor.getCommissionFactorID());
                  	     			System.out.println("aaaaaa "+ options_select_value);
                  	     			FactorDAO.setsipReportFactorValue(con, factor.getCommissionFactorID()+"", options_select_value);
                  	     			
                  	     		}
                  	     		
                  	     		Vector   savedReportList =SipDAO.getAllSavedsipReportByID(con);
                                System.out.println("The  vector  size  in  the VIEW_SIP_SAVED_REPORT is"+savedReportList.size());
                                      
                    
                                
                                
                         		 GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
                         	     Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
                         	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
                         	       
                         	     GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
                         	     Vector sipYear = GenericModelDAO.getModels(con ,sipYearModel);
                         	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear);
                         	       
                         	       
                         	       
                         	     GenericModel sipReportTypeModel = GenericModelDAO.getColumns(con,"sip_report_type");                                 
                         	     Vector sipReportType = GenericModelDAO.getModels(con ,sipReportTypeModel);
                         	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE, sipReportType);
                         	       
                         	     GenericModel sipReportStatusTypeModel = GenericModelDAO.getColumns(con,"sip_report_Status_type");                                 
                       	     Vector sipReportStatus = GenericModelDAO.getModels(con ,sipReportStatusTypeModel);
                       	     dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS, sipReportStatus);
                         		
                                
                                
                                
                                
                                
                                
                                
                                
                                      
                			  dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getAllSavedsipReportByID(con));

                  	     	}
                  	     	break;
                  	    	
                  	    	
                  	    	case SAVE_NEW_SIP_REPORT_1:
    				        {
    				           try{ 
    				        	
    				        	   
    				            String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    				            Utility.logger.debug("USER ID:  "+strUserID);
    				            dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID);
    				       //     String sipReprotCategory  = "";
    				            String sipReportType      = "";
    				            String sipReportDataView  = "";
    				            String dataViewType = "";
    				            String sipReprotName = "";
    				            String sipSavedReprotMonth = "";
    				            

    				       //     sipReprotCategory  = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CATEGORY);
    				            sipReportType      = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_TYPE);
    				            sipReportDataView  = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW);
    				            dataViewType        = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE); 
    				            String report_quarter = (String )paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER);
    				            
    				            String report_year = (String )paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);
    				            
    				            sipSavedReprotMonth = (String )paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_MONTH);
    				            
    				            String reportName = (String )paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SAVED_REPORT_NAME);
    				              Utility.logger.debug(dataViewType);
    				    
    				        //    String sipReprotDesc      = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DESCRIPTION);
    				        //    String sipReprotEndDate   = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_END_DATE);
    				         //   String sipReprotStartDate = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_START_DATE);
    				        //    String sipReprotChannel   = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_CHANNEL);
    				    
    				           			            
    				         //   String year = sipReprotStartDate.substring(sipReprotStartDate.length()-2,sipReprotStartDate.length());
    				           /// String temsipReprotStartDate = sipReprotStartDate.substring(0,sipReprotStartDate.length()-4)+year;
    				           // temsipReprotStartDate = temsipReprotStartDate.replaceAll("/", "");
    				            
    				          //   year = sipReprotEndDate.substring(sipReprotEndDate.length()-2,sipReprotEndDate.length());
    				         //    String temsipReprotEndDate = sipReprotEndDate.substring(0,sipReprotEndDate.length()-4)+year;
    				         //    temsipReprotEndDate = temsipReprotEndDate.replaceAll("/", "");
    				             
    				         //    String sipReprotCategoryName =  SipDAO.getsipReportCategoryName(con,sipReprotCategory);
    				         //    sipReprotCategoryName = sipReprotCategoryName.replaceAll(" ", "_");
    				         
    				        //    sipReprotName = SipDAO.getChannelName(con,sipReprotChannel)+"_"+sipReprotCategoryName+"_"+temsipReprotStartDate+"_"+temsipReprotEndDate+"_";
      
    				            
    				            
    				          //     String yearr = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);
    				        	//   String label = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL);
    				        	//   String NI_commission_ids = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS);
    				        	//   String line_coimmission_ids = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS);
    				        	//   String sop_report_ids = (String )paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SOP_IDS);
    				      
    				        	   
    				        	//   System.out.println(sipReportType);
    				        	//   System.out.println(sipReportDataView);
    				        	//   System.out.println(dataViewType);
    				        	//   System.out.println(report_quarter);
    				        	////   System.out.println(report_year);
    				        	   System.out.println(reportName);
    				        
    				            
    				            
    				            
    				            
    				     /*  */     
    				            int sipReprotID = SipDAO.addNewSavedReport( con ,reportName,		 
    				            		strUserID , sipReportDataView,dataViewType , report_quarter,  report_year,sipReportType,sipSavedReprotMonth);

            System.out.println("The sip  report id   in  the handler issssss     SAVE_NEW_SIP_REPORT   Action    issssssss" + sipReprotID);
            savedSipReportModel sipReportModel =   (savedSipReportModel) SipDAO.getSavedsipReportByID(con ,sipReprotID+"");
    				            dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID , sipReprotID+"");
    				            dataHashMap.put(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE,dataViewType);
    				            if(CommissionDAO.hasParameters(con,sipReportDataView)== true)
    				            {
    				              dataHashMap.put(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, CommissionDAO.getDataViewParameters(con,Integer.parseInt(sipReportDataView)));
    				              dataHashMap.put(SIPInterfaceKey.SIP_REPORT_DATA_VIEW_PARAMETER,"PARAM");
    				            }          
    				            GenericModel sipReportTypeModel = GenericModelDAO.getColumns(con,"SIP_REPORT_TYPE");
    				            Vector sipReportTypes = GenericModelDAO.getModels(con,sipReportTypeModel);
    				            dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE , sipReportTypes);   
    				           // GenericModel sipReportTypeCategoryModel = GenericModelDAO.getColumns(con,"SIP_REPORT_TYPE_CATEGORY");
    				         //   Vector sipReportTypeCategories = GenericModelDAO.getModels(con ,sipReportTypeCategoryModel);
    				        //    dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_TYPE_CATEGORY , sipReportTypeCategories);
    				          
    				          GenericModel sipReportStatusModel = GenericModelDAO.getColumns(con,"SIP_REPORT_STATUS_TYPE");
    				           Vector sipReportStatusVector = GenericModelDAO.getModels(con ,sipReportStatusModel);
    				            dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_STATUS , sipReportStatusVector);
    				            dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ACTION ,
    				                            (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ACTION));         
    				            dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_STATUS ,
    				                            (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_STATUS));
    				         //   Vector sipChannelVec = GenericModelDAO.getsipReportChannel(con, strUserID);
    				        //    dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,sipChannelVec);
    				            
    				            
    				       	   dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR,report_year);
    		
    			        	   dataHashMap.put(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER,report_quarter);

    			        	   
    			        	   dataHashMap = defaultVectors(dataHashMap,con,strUserID);
    			        	   
    			        	   
    			        		dataHashMap.put(SIPInterfaceKey.SAVED_REPORT_VECTOR,SipDAO.getAllSavedsipReportByID(con));
    			        	   
    				           }
    				           catch(Exception ex)
    				           {
    				        	   
    				        	   ex.printStackTrace();
    				           }
    				        }
    				        break;                      
      

					    	
                                 
                                    
					  		  default:
					  			Utility.logger.debug ("Unknown action received: " + action ); 
				     
					    	
					    }	
				    	
				    }
				    
				    catch(Exception e)
				    {
				    	e.printStackTrace();
				    	
				    }
				    return dataHashMap;
			  }
public static HashMap defaultVectors(HashMap dataHashMap,java.sql.Connection con,String strUserID ) throws Exception{
       
       GenericModel sipTypeCategoryModel = GenericModelDAO.getColumns(con,"SIP_REPORT_TYPE_CATEGORY");                                 
       Vector sipTypeCategories = GenericModelDAO.getModels(con ,sipTypeCategoryModel);                                    
       dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_TYPE_CATEGORY , sipTypeCategories);
       
       GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
       Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
       dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
       
       GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
       Vector sipYear = GenericModelDAO.getModels(con ,sipYearModel);
       dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear);
       
             
       
       //hagry i modified the column name to fit my db 
       //nameof column should be tested on SDS mobinilserver
       
       GenericModel sipLabelModel = GenericModelDAO.getColumns(con,"AUTH_RES_LABEL");                                 
       Vector sipLabel = GenericModelDAO.getModels(con ,sipLabelModel);
       dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_LABEL , sipLabel);
       
       
       Vector sipChannelVec = SipDAO.getSIPChannel(con, strUserID);
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,sipChannelVec);
       /* updated to handler*/
       dataHashMap = yearVector(dataHashMap,con);
        
        
       
       return dataHashMap;
    }


public static HashMap searchInSIP (Connection con,HashMap paramHashMap,HashMap dataHashMap) throws Exception
{
   String incomingAction = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);                                  
   String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);                                  
   String sipReportName          = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_NAME);                            
   String sipReportChannel       = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL);                                 
   String sipReportTypeCategory  = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_CATEGORY);                                 
   String sipReportQuarter  = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER);                                    
   String sipReportLabel  = (String)paramHashMap.get(SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL);
   String sipReportYear          = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR);
   String sipReportLCID          = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS);                                   
   String sipReportNICom          = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS);                                    
   String sipReportSOPID          = (String)paramHashMap.get(SIPInterfaceKey.INPUT_TEXT_SOP_IDS);
   
   dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS, (String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_STATUS ));
   
   Vector searchResults = SipDAO.searchSIPReportByFilter( con,
    "",                      
    sipReportName,
    "",                             
    sipReportChannel,                               
    sipReportTypeCategory,          
    sipReportQuarter,                                   
    sipReportLabel,
    sipReportYear,
    sipReportLCID,                             
    sipReportNICom,                                 
    sipReportSOPID,
    strUserID);
   
   dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT , searchResults);
   
   dataHashMap = defaultVectors(dataHashMap,con,strUserID);
     System.out.println ("sipAction in handler issssssssss "+(String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION));
   dataHashMap.put(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION,/*sipAction*/(String)paramHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_ACTION));
   dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID,strUserID==null?"":strUserID);//                                 
   dataHashMap.put(SIPInterfaceKey.CONTROL_SELECT_SIP_QUARTER,sipReportQuarter==null?"":sipReportQuarter);                                    
   dataHashMap.put(SIPInterfaceKey.CONTROL_SELECT_SIP_LABEL,sipReportLabel==null?"":sipReportLabel);
   dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_SIP_YEAR,sipReportYear==null?"":sipReportYear);
   dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_LINE_COMMISSION_IDS,(sipReportLCID==null||sipReportLCID.compareTo ( "" )==0)?"ex: 2364,16365,18954":sipReportLCID);
   dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_NI_COMMISSION_IDS,(sipReportNICom==null||sipReportNICom.compareTo ( "" )==0)?"ex: 6544,5664,9987":sipReportNICom);                                                                       
   dataHashMap.put(SIPInterfaceKey.INPUT_TEXT_SOP_IDS,(sipReportSOPID==null||sipReportSOPID.compareTo ( "" )==0)?"ex: 3564,5895,4589":sipReportSOPID);                                  
   dataHashMap.put(SIPInterfaceKey.CONTROL_TEXT_SIP_NAME,sipReportName==null?"":sipReportName);                                 
   dataHashMap.put(SIPInterfaceKey.CONTROL_TEXT_SIP_CHANNEL, sipReportChannel==null?"":sipReportChannel);
   dataHashMap.put(SIPInterfaceKey.CONTROL_TEXT_SIP_CATEGORY,sipReportTypeCategory==null?"":sipReportTypeCategory);
   
   return dataHashMap;
      
}

public static HashMap searchFile(Connection con,HashMap paramHashMap,HashMap dataHashMap) throws Exception{
   
   String strUserID = (String) paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
   Vector files =SipDAO.getallfiles(con); 
   GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
 Vector sipQuarter = GenericModelDAO.getModels(con ,sipQurterModel);
 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter);
 
 GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
 Vector sipYear = GenericModelDAO.getModels(con ,sipYearModel);
 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear);
 
GenericModel sipTypeModel = GenericModelDAO.getColumns(con,"SIP_FILE_TYPE");                                 
 Vector sipType = GenericModelDAO.getModels(con ,sipTypeModel);
 dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_TYPE , sipType);
   dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT,files);
   
   return dataHashMap;
}

public static HashMap searchFileVectors(Connection con,HashMap paramHashMap,HashMap dataHashMap,Vector searchResults) throws Exception{
GenericModel sipTypeModel = GenericModelDAO.getColumns(con,"SIP_file_TYPE");                                 
Vector sipType1 = GenericModelDAO.getModels(con ,sipTypeModel);
dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_TYPE , sipType1);
dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_SEARCH_RESULT , searchResults);
GenericModel sipQurterModel = GenericModelDAO.getColumns(con,"SIP_REPORT_Quarter");                                 
Vector sipQuarter1 = GenericModelDAO.getModels(con ,sipQurterModel);
dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_QUARTER , sipQuarter1);

GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
Vector sipYear1 = GenericModelDAO.getModels(con ,sipYearModel);
dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_YEAR , sipYear1);

return dataHashMap;
}
/* updated to handler*/
public static HashMap yearVector(HashMap dataHashMap,java.sql.Connection con ) throws Exception{
GenericModel sipYearModel = GenericModelDAO.getColumns(con,"SIP_REPORT_YEAR");                                 
Vector sipYears = GenericModelDAO.getModels(con ,sipYearModel);
dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_REPORT_YEAR,sipYears);

GenericModel sipTypeModel = GenericModelDAO.getColumns(con,"SIP_FILE_TYPE");                                 
Vector sipType = GenericModelDAO.getModels(con ,sipTypeModel);
dataHashMap.put(SIPInterfaceKey.VECTOR_SIP_TYPE , sipType);
return dataHashMap;
}


}
