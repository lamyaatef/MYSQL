package com.mobinil.sds.core.facade.handlers;

import com.mobinil.sds.core.system.gn.reports.domain.*;
import com.mobinil.sds.core.system.gn.reports.dto.*;
import com.mobinil.sds.core.system.gn.reports.dao.*;


import com.mobinil.sds.web.interfaces.gn.reports.ReportInterfaceKey;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import com.mobinil.sds.web.interfaces.sa.UserInterfaceKey;


import java.util.*;

import com.mobinil.sds.core.system.gn.worker.* ;
import com.mobinil.sds.core.system.sa.users.dao.*;
import com.mobinil.sds.core.utilities.*;
import java.sql.*;
//import com.mobinil.sds.core.system.sa.users.dao.*;


public class ReportHandler 
{

  static final int INITIALIZE_WIZARD              = 1;
  static final int SAVE_REPORT                    = 2;
  static final int UPDATE_REPORT                  = 3;
  static final int RETRIEVE_REPORT_LIST           = 4;
  static final int LOAD_REPORT                    = 5; 
  static final int LOAD_REPORT_DETAILS            = 6; 
  static final int UPDATE_CUSTOMIZED_REPORT       = 8;
  static final int PREVIEW_REPORT                 = 9;
  /*static final int RETRIEVE_VIEW_ONLY_REPORT_LIST = 10;
  static final int SHOW_USER_REPORT_ASSIGN_SCREEN = 11; 
  static final int UPDATE_USER_REPORT_ASSIGNMENT = 12;*/ 
  static final int SAVE_REPORT_STATUS                   = 11;
  static final int ASSIGN_REPORT_TO_USER               = 13;
  static final int UPDATE_USER_REPORTS                  = 14;
  static final int RETRIEVE_REPORT_LIST_FOR_USER           = 15;
  static final int PREVIEW_REPORT_EXCEL_TEXT               = 16;
  static final int LOAD_GROUP               = 17;
  static final int NEW_GROUP               = 18;
  static final int SAVE_GROUP               = 19;
  static final int SHOW_REPORTS            = 20;
  static final int SAVE_GROUP_STATUS                   = 21;
  static final int ASSIGN_REPORT_TO_GROUP                   = 22;
  static final int VIEW_REPORTS                    = 23;
  



  //static final int CUSTOMIZE_REPORT               = 100;
  //static final int SAVE_CUSTOMIZED_REPORT         = 101;



// ----------- temporary code begin ------------------------
/*
  private void insertHardCodedUniverseKeys (HashMap paramHashMap)
  {

    //key for initialize action
    paramHashMap.put ( 
            ReportInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE, 
            new String ("1") ) ;

  }


  private void insertHardCodedReportKeys (HashMap paramHashMap)
  {
    insertHardCodedUniverseKeys (paramHashMap) ;
  }
*/
// ----------- temporary code end ------------------------



  public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con)
  {
    int actionType = 0;
    HashMap dataHashMap = new HashMap ();


    try
    {

        /*String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        if(strUserID != null && strUserID.compareTo("") != 0  )
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, strUserID);           
        }*/
        // temporary code begin - to display all hashmap keys received from the client
/*
        Utility.logger.debug ( "ReportHandlerEJBBean.handle ") ;
        Utility.logger.debug ( "-- Begin HashMap ------------------------------------------------------ ") ;
        Object arrKey[] = paramHashMap.keySet().toArray() ;
        for (int i = 0 ; i < arrKey.length ; i++ )
        {
          Object o = paramHashMap.get ((String) arrKey[i]) ;
          if (o instanceof String)
            Utility.logger.debug ( (String) arrKey[i] + "\n    ----> " + (String) o ) ;
          else if (o instanceof String[])
          {
            for (int j = 0 ; j < ((String[])o ).length ; j++ )
            {
              Utility.logger.debug ( (String) arrKey[i] + "["+ j + "]"+ "\n    ----> " + ((String[])o )[j] ) ;
            }
          }
        }
        Utility.logger.debug ( "-- End HashMap ------------------------------------------------------ ") ;

*/        
        // temporary code end





      if (action.equals(ReportInterfaceKey.ACTION_INITIALIZE_WIZARD))
        actionType = INITIALIZE_WIZARD;
      else if (action.equals(ReportInterfaceKey.ACTION_SAVE_REPORT))
        actionType = SAVE_REPORT;
      else if (action.equals(ReportInterfaceKey.ACTION_UPDATE_REPORT))
        actionType = UPDATE_REPORT;
      else if (action.equals(ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST))
        actionType = RETRIEVE_REPORT_LIST ;
      else if (action.equals(ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST_FOR_USER))
        actionType = RETRIEVE_REPORT_LIST_FOR_USER ;
      else if (action.equals(ReportInterfaceKey.ACTION_LOAD_REPORT))
        actionType = LOAD_REPORT ;
      else if (action.equals(ReportInterfaceKey.ACTION_LOAD_REPORT_DETAILS) ||
              action.equals(ReportInterfaceKey.ACTION_SHOW_REPORT_PARAM)||(action.equals(ReportInterfaceKey.ACTION_SHOW_REPORT_PARAM_EXCEL)) || (action.equals(ReportInterfaceKey.ACTION_SHOW_REPORT_PARAM_TEXT)))
        actionType = LOAD_REPORT_DETAILS ;
      /*else if (action.equals(ReportInterfaceKey.ACTION_SAVE_CUSTOMIZED_REPORT))
        actionType = SAVE_CUSTOMIZED_REPORT ;*/
      else if (action.equals(ReportInterfaceKey.ACTION_UPDATE_CUSTOMIZED_REPORT))
        actionType = UPDATE_CUSTOMIZED_REPORT ;
      else if (action.equals(ReportInterfaceKey.ACTION_PREVIEW_REPORT) ||
                action.equals(ReportInterfaceKey.ACTION_EXPORT_REPORT_EXCEL_FINISHED) ||
                action.equals(ReportInterfaceKey.ACTION_EXPORT_REPORT_TEXT_FINISHED) )
        actionType = PREVIEW_REPORT ;
      else if (action.equals(ReportInterfaceKey.ACTION_EXPORT_REPORT_EXCEL) ||
                 action.equals(ReportInterfaceKey.ACTION_EXPORT_REPORT_TEXT))
        actionType = PREVIEW_REPORT_EXCEL_TEXT ;
      else if (action.equals(ReportInterfaceKey.ACTION_ASSIGN_REPORT_TO_USER))
        actionType = ASSIGN_REPORT_TO_USER ;  
      else if (action.equals(ReportInterfaceKey.ACTION_UPDATE_USER_REPORTS))
        actionType = UPDATE_USER_REPORTS ;  
      else if (action.equals(ReportInterfaceKey.ACTION_SAVE_REPORT_STATUS))
        actionType = SAVE_REPORT_STATUS ;
        else if (action.equals(ReportInterfaceKey.ACTION_LOAD_GROUP))
        actionType = LOAD_GROUP ;
        else if (action.equals(ReportInterfaceKey.ACTION_NEW_GROUP))
        actionType = NEW_GROUP ;
        else if (action.equals(ReportInterfaceKey.ACTION_SAVE_GROUP))
        actionType = SAVE_GROUP ;
       else if (action.equals(ReportInterfaceKey.ACTION_SHOW_REPORT))
        actionType = SHOW_REPORTS ;
      else if (action.equals(ReportInterfaceKey.ACTION_SAVE_GROUP_STATUS))
        actionType = SAVE_GROUP_STATUS ;

      else if (action.equals(ReportInterfaceKey.ACTION_ASSIGN_REPORT_TO_GROUP))
        actionType = ASSIGN_REPORT_TO_GROUP ;
      else if (action.equals(ReportInterfaceKey.ACTION_VIEW_REPORT))
    	  actionType = VIEW_REPORTS;

          
      /*else if (action.equals(ReportInterfaceKey.ACTION_RETRIEVE_VIEW_ONLY_REPORT_LIST))
      {
          actionType = RETRIEVE_VIEW_ONLY_REPORT_LIST;
      }
      else if (action.equals(ReportInterfaceKey.ACTION_SHOW_REPORT_USER_ASSIGN_SCREEN))
      {
          actionType = SHOW_USER_REPORT_ASSIGN_SCREEN;
      }
      else if (action.equals(ReportInterfaceKey.ACTION_UPDATE_USER_REPORT))
      {
          actionType = UPDATE_USER_REPORT_ASSIGNMENT;
      }*/
      
      switch (actionType)
      {
        case INITIALIZE_WIZARD:
          {
            ReportEngine reportEngine = new ReportEngine() ;

           // Connection conSDSConnection = Utility.getConnection();
            dataHashMap = reportEngine.initializeWizard (con);
            
            //Utility.closeConnection (conSDSConnection);
          }
        break;
        case SAVE_REPORT:
          {
            ReportEngine reportEngine = new ReportEngine() ;                      

            Vector groupList =  reportEngine.constructGroupSelectList(paramHashMap );
            String strReportName = (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_REPORT_NAME) ; 
            String strReportDesc = (String) paramHashMap.get (ReportInterfaceKey.PARAM_SAVE_REPORT_DESC) ; 
            int nReportDataViewID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_SAVE_REPORT_DATAVIEW_ID) ).intValue() ;

          //  Connection conSDSConnection = Utility.getConnection();
            int nReportID = reportEngine.saveReport (
                          con,
                          strReportName,
                          strReportDesc,
                          nReportDataViewID,groupList );
            if (nReportID == ReportInterfaceKey.ERR_NUM_UNIQUE_REPORT )
              dataHashMap.put (InterfaceKey.HASHMAP_KEY_MESSAGE, ReportInterfaceKey.ERR_STR_UNIQUE_REPORT ) ;
            else
            {
              ReportBuilderWizardDTO reportBuilderWizardDTO = reportEngine.loadReport (con, nReportID );
              dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, reportBuilderWizardDTO) ;
            }
            //Utility.closeConnection (conSDSConnection);
          }
        break;
        case UPDATE_REPORT:
          {
            ReportEngine reportEngine = new ReportEngine() ;

            int nReportID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_REPORT_ID) ).intValue() ;
            String strReportName = (String) paramHashMap.get (ReportInterfaceKey.PARAM_UPDATE_REPORT_NAME) ; 
            String strReportDesc = (String) paramHashMap.get (ReportInterfaceKey.PARAM_UPDATE_REPORT_DESC) ; 
            int nReportDataViewID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_UPDATE_REPORT_DATAVIEW_ID) ).intValue() ;

            Utility.logger.debug ("before updating, nReportID " + nReportID) ;
         //   Connection conSDSConnection = Utility.getConnection();
            reportEngine.updateReport(
                          con,
                          nReportID,
                          strReportName,
                          strReportDesc,
                          nReportDataViewID );
            Utility.logger.debug ("loading report, nReportID " + nReportID) ;
            ReportBuilderWizardDTO reportBuilderWizardDTO = reportEngine.loadReport ( con,nReportID );
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, reportBuilderWizardDTO) ;
          //  Utility.closeConnection (conSDSConnection);
          }
        break;
        case RETRIEVE_REPORT_LIST:
          {
            ReportEngine reportEngine = new ReportEngine() ;

            //String strReportUniverseID = (String) paramHashMap.get(ReportInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE)  ;

          //  Connection conSDSConnection = Utility.getConnection();
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) ;
//            int nGroupID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ).intValue() ;
            //int nReportUniverseID = (DataViewDAO.switchUniverseTypeToID ( conSDSConnection, strReportUniverseID)).intValue() ; 
            //int reportUniverseID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_RETRIEVE_REPORT_LIST_UNIVERSE) ).intValue() ;

//            Vector vecReportList = reportEngine.retrieveReportList (conSDSConnection /*nReportUniverseID*/ );
               

/*  this was done to know the reports that have parameters 
 * and it was commented since it caused performance problems 28/2/2007
            Vector vecReportListWizard = new Vector();

            for (int i = 0 ; i < vecReportList.size();i++)
            {
             vecReportListWizard.add( reportEngine.loadReport(((ReportDTO)vecReportList.get(i)).getReportID()));
            }
*/
            Vector vecGroupList = ReportDAO.getGroups(con,false);
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecGroupList) ;

//            Vector reportsGroup = ReportDAO.getGroups(conSDSConnection,false);
//            dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_REPORTS, reportsGroup) ;            
            
            Vector allReports = ReportDAO.getAllReports();
            dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_ALL_REPORTS, allReports) ;


            Vector reportStatus = ReportDAO.getReportStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
            
            dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));            
            
          //  Utility.closeConnection (conSDSConnection);

          }
        break;    
        case RETRIEVE_REPORT_LIST_FOR_USER:
          {
            ReportEngine reportEngine = new ReportEngine() ;

            //String strReportUniverseID = (String) paramHashMap.get(ReportInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE)  ;

          //  Connection conSDSConnection = Utility.getConnection();
            
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST_FOR_USER) ;

            //int nReportUniverseID = (DataViewDAO.switchUniverseTypeToID ( conSDSConnection, strReportUniverseID)).intValue() ; 
            //int reportUniverseID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_RETRIEVE_REPORT_LIST_UNIVERSE) ).intValue() ;
            String userId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            Vector vecReportList = reportEngine.retrieveReportListForUser (con ,userId/*nReportUniverseID*/ );

            //Vector vecReportListWizard = new Vector();
/*  this was done to know the reports that have parameters 
 * and it was commented since it caused performance problems 28/2/2007
            Vector vecReportListWizard = new Vector();

            for (int i = 0 ; i < vecReportList.size();i++)
            {
             vecReportListWizard.add( reportEngine.loadReport(((ReportDTO)vecReportList.get(i)).getReportID()));
            }
*/
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;
            dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));            

            Vector reportStatus = ReportDAO.getReportStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
           
            //Utility.closeConnection (conSDSConnection);

          }
        break;
/*
        case RETRIEVE_VIEW_ONLY_REPORT_LIST:
        {
        
          ReportEngine reportEngine = new ReportEngine() ;
          //String strUserID = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          Utility.logger.debug("user id = "+ strUserID);
                    
          Connection conSDSConnection = Utility.getConnection();
          
          //Vector vecReportList = reportEngine.retrieveReportList ( nReportUniverseID );

          Vector vecReportList = reportEngine.retrieveReportListVisibleForUser ( strUserID);
            

          Vector vecReportListWizard = new Vector();
          for (int i = 0 ; i < vecReportList.size();i++)
          {
            vecReportListWizard.add( reportEngine.loadReport(((ReportDTO)vecReportList.get(i)).getReportID()));
          }
            
          dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportListWizard) ;
          Utility.closeConnection (conSDSConnection);
        }
        break;
 */ 
        case LOAD_REPORT:
          {
          //  Connection conSDSConnection = Utility.getConnection();
            ReportEngine reportEngine = new ReportEngine () ;

            int nReportID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_LOAD_REPORT_ID) ).intValue() ;

            ReportBuilderWizardDTO reportBuilderWizardDTO 
                = reportEngine.loadReport (con, nReportID );

            //Utility.logger.debug ( "  ReportHandlerEJB:: ReportName: " + reportBuilderWizardDTO.getReport().getReportName() ) ;

            dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, reportBuilderWizardDTO) ;
            //Utility.closeConnection (conSDSConnection);
          }
        break;
        case LOAD_REPORT_DETAILS:
          {
           // Connection conSDSConnection = Utility.getConnection();
            ReportEngine reportEngine = new ReportEngine () ;

            int nReportID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_LOAD_REPORT_DETAILS_ID) ).intValue() ;

            ReportBuilderWizardDTO reportBuilderWizardDTO 
                = reportEngine.loadReport (con, nReportID );

            //Utility.logger.debug ( "  ReportHandlerEJB:: ReportName: " + reportBuilderWizardDTO.getReport().getReportName() ) ;

            dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, reportBuilderWizardDTO) ;
          //  Utility.closeConnection (conSDSConnection);
          }
        break;
/*
        case SAVE_CUSTOMIZED_REPORT:
          
          {
            ReportEngine reportEngine = new ReportEngine () ;

            //int nReportID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_INITIALIZE_REPORT_ID) ).intValue() ;
            String strErrorMessage = reportEngine.saveCustomizedReport ( paramHashMap );

            if (strErrorMessage != null)
              dataHashMap.put (InterfaceKey.HASHMAP_KEY_MESSAGE, strErrorMessage);


            //dataHashMap.put (InterfaceKey.HASHMAP_KEY_DTO_OBJECT, reportBuilderWizardDTO) ;
          }
        break;
*/
        case UPDATE_CUSTOMIZED_REPORT:
          {
          // Connection conSDSConnection = Utility.getConnection();

            ReportEngine reportEngine = new ReportEngine () ;

            String strErrorMessage = reportEngine.updateCustomizedReport ( con,paramHashMap );

            if (strErrorMessage != null)
              dataHashMap.put (InterfaceKey.HASHMAP_KEY_MESSAGE, strErrorMessage);


          //  Vector vecReportList = reportEngine.retrieveReportList ();
          //  dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;


            

         //   ReportEngine reportEngine = new ReportEngine() ;
          

//            Vector vecReportList = reportEngine.retrieveReportList (conSDSConnection /*nReportUniverseID*/ );
				
            //Vector vecReportListWizard = new Vector();
            //for (int i = 0 ; i < vecReportList.size();i++)
            //{
            // vecReportListWizard.add( reportEngine.loadReport(conSDSConnection,((ReportDTO)vecReportList.get(i)).getReportID()));
            //}

            
//            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;
//            Vector reportStatus = ReportDAO.getReportStatus();
//            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_UPDATE_CUSTOMIZED_REPORT) ;

//            Vector vecGroupList = ReportDAO.getGroups(conSDSConnection,false);
//            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecGroupList) ;
            String reportName = "";
            Vector vecReportList = new Vector();
            //Vector vecReportList = reportEngine.retrieveReportList (con,reportName /*nReportUniverseID*/ );
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;

            Vector reportStatus = ReportDAO.getReportStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
           // Utility.closeConnection (conSDSConnection);
            
          }
        break;
        case PREVIEW_REPORT:
          {
            if(paramHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
            {
              String jobId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
              HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
              if(workerHashMap == null)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
              }
              else
              {
                dataHashMap = workerHashMap;
                WorkerDataManagement.removeWorkerData(jobId);
              }
            }
            else
            {
                ReportWorker reportWorker = new ReportWorker(paramHashMap);
                String jobId = WorkerDataManagement.addWorker(reportWorker);
                HashMap workerHashMap = WorkerDataManagement.getWorkerData(jobId);
                if(workerHashMap == null)
                {
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
                }
                else
                {
                  dataHashMap = workerHashMap;
                  WorkerDataManagement.removeWorkerData(jobId);
                }
            }
          }
        break;
        case PREVIEW_REPORT_EXCEL_TEXT:
          {
        	  
        	
        	
            if(paramHashMap.containsKey(InterfaceKey.HASHMAP_KEY_JOB_ID))
            {
              String jobId = (String)paramHashMap.get(InterfaceKey.HASHMAP_KEY_JOB_ID);
              boolean processFinished = WorkerDataManagement.checkWorkerDataFinished(jobId);
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
              if(!processFinished)
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,ReportInterfaceKey.REPORT_LOADING);
              }
              else
              {
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,ReportInterfaceKey.REPORT_FINISHED);
              }
            }
            else
            {
                ReportWorker reportWorker = new ReportWorker(paramHashMap);
                String jobId = WorkerDataManagement.addWorker(reportWorker);
                boolean processFinished = WorkerDataManagement.checkWorkerDataFinished(jobId);
                dataHashMap.put(InterfaceKey.HASHMAP_KEY_JOB_ID,jobId);
                if(!processFinished)
                {
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,ReportInterfaceKey.REPORT_LOADING);
                }
                else
                {
                  dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE,ReportInterfaceKey.REPORT_FINISHED);
                }
            }
          }
        break;
        case ASSIGN_REPORT_TO_USER:
          {
            //  Connection m_conSDSConnection = Utility.getConnection(); 
              
              ReportEngine reportEngine = new ReportEngine () ;
//              Vector vecReportList = reportEngine.retrieveReportList (m_conSDSConnection /*nReportUniverseID*/ );
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));
              Vector vecReportList = ReportDAO.getGroups(con,true /*nReportUniverseID*/ );
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecReportList);
              Vector vecPersonReportList = reportEngine.getAllPersonReports(con);                  
              dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_PERSON_REPORT,vecPersonReportList);
          //    Utility.closeConnection (m_conSDSConnection);
          }
        break;
        case UPDATE_USER_REPORTS:
          {
         //     Connection m_conSDSConnection = Utility.getConnection(); 
              ReportEngine reportEngine = new ReportEngine () ;
              
              int paramHashMapSize = paramHashMap.size();
              String groupIdKey = (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ;
              String nGroupID =  groupIdKey ;
              
            //  System.out.println("group id ="+ nGroupID);
              String strPersonID = (String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID);
              reportEngine.deletePersonReports(con,strPersonID);                  
              for(int i=0;i<paramHashMapSize;i++) 
              {
                  String tempKey = (String) paramHashMap.keySet().toArray()[i];
                //  System.out.println(paramHashMap.get(tempKey));
                  String tempValue = (String)paramHashMap.get(tempKey);
                  //Utility.logger.debug("wwwwwww"+tempKey+"-----------------"+tempValue);

                  if(tempKey.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+strPersonID)) 
                  {
                      groupIdKey = tempKey.substring((UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX+strPersonID).length());                                           
                      reportEngine.savePersonReports(con,strPersonID,groupIdKey);
                  }
              }
              
              dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, UserDAO.getUsersList(con));

            Vector vecGroupList = ReportDAO.getGroups(con,true);
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, vecGroupList) ;
//            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_ASSIGN_REPORT_TO_USER) ;

//            Vector reportStatus = ReportDAO.getReportStatus();
//            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
              
//              Vector vecReportList = reportEngine.retrieveReportList (m_conSDSConnection /*nReportUniverseID*/ );
//              dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION,vecReportList);
              Vector vecPersonReportList = reportEngine.getAllPersonReports(con);                  
              dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_PERSON_REPORT,vecPersonReportList);
             // Utility.closeConnection (m_conSDSConnection);
          }
        break;
        case SAVE_GROUP_STATUS:
          {
         // Connection conSDSConnection = Utility.getConnection();
//          String groupIdKey = (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ;
//          int nGroupID = new Integer (groupIdKey).intValue() ;
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];             

              if(tempStatusString.startsWith("groupstatus"))
              {                                               
                String keyValue = (String)paramHashMap.get(tempStatusString);
                int nGroupID =new Integer (Integer.parseInt(tempStatusString.substring(11, tempStatusString.length()))).intValue();
                if (keyValue.compareTo("3")==0){
                    ReportDAO.deleteGroup(con,nGroupID);
                }
                else
                {                
                ReportDAO.updateGroupStatus(con,keyValue,nGroupID+"");
                }

              }              
            }
//            ReportEngine reportEngine = new ReportEngine() ;
//            Vector vecReportList = reportEngine.retrieveReportList ( conSDSConnection);
//            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;
//            Vector reportStatus = ReportDAO.getReportStatus();
//            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
              Vector allReports = ReportDAO.getAllReports();
              dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_ALL_REPORTS, allReports) ;
              dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) ;
              Vector vecGroupList = ReportDAO.getGroups(con,false);
              dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecGroupList) ;
              Vector reportStatus = ReportDAO.getReportStatus(con);
              dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
              dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));            
           // Utility.closeConnection (con);
        }
        break;
        case NEW_GROUP:
          {
          //Connection conSDSConnection = Utility.getConnection();          
            dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));            
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_NEW_GROUP) ;
          //  Utility.closeConnection (conSDSConnection);
          }
        break;
        case LOAD_GROUP:
          {
           // Connection conSDSConnection = Utility.getConnection();


            int nGroupID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ).intValue() ;

            
            GroupDTO gdto = ReportDAO.getGroupDetails(con,nGroupID);
            gdto.setGroupId(new Integer(nGroupID));
            dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_DETAILS, gdto) ;
            
//            Vector reportsGroup = ReportDAO.getReportsPerGroup(conSDSConnection,nGroupID);//
//            dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_REPORTS, reportsGroup) ;            
            
//            Vector allReports = ReportDAO.getAllReports();
//            dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_ALL_REPORTS, allReports) ;

            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_LOAD_GROUP) ;




        
            dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));            

            
            
          //  Utility.closeConnection (con);
          }
        break;


        case SAVE_GROUP:
          {
         //   Connection conSDSConnection = Utility.getConnection();
          String gId = (String)paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID);
          if (gId!=null)
          {
          int reportId = 0;
          int nGroupID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID) ).intValue() ;
//          Vector selectedReports = new Vector();
          String statusId = (String)paramHashMap.get(ReportInterfaceKey.CONTROL_GROUP_STATUS);
          if (statusId.compareTo("3")==0)
          {
            ReportDAO.deleteGroup(con,nGroupID);
          }
          else
          {
             String groupName =  (String) paramHashMap.get(ReportInterfaceKey.CONTROL_GROUP_NAME)  ;
             String groupDesc =  (String) paramHashMap.get(ReportInterfaceKey.CONTROL_GROUP_DESC)  ;            
             ReportDAO.updateGroupStatus(con,groupDesc,groupName,statusId,nGroupID+"");
//           for(int j=0; j<paramHashMap.size(); j++)
//          {           
//            
//            String tempString = (String)paramHashMap.keySet().toArray()[j];
//            if(tempString.startsWith(ReportInterfaceKey.CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP))
//            {
//              reportId = new Integer(tempString.substring(
//                                          tempString.lastIndexOf(
//                                            ReportInterfaceKey.CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP)+22)).intValue();
//              selectedReports.addElement(new Integer(reportId));
//            }
//            
//           
//          }
//           ReportDAO.inserReportGroup(conSDSConnection,selectedReports,nGroupID);
          }
          }
          else
          {
            
            String groupName =  (String) paramHashMap.get(ReportInterfaceKey.CONTROL_GROUP_NAME)  ;
            String groupStatus =  (String) paramHashMap.get(ReportInterfaceKey.CONTROL_GROUP_STATUS)  ;
            String groupDesc =  (String) paramHashMap.get(ReportInterfaceKey.CONTROL_GROUP_DESC)  ;            
            GroupDTO gdto = new GroupDTO();
            gdto.setGroupDesc(groupDesc);
            gdto.setGroupStatus(new Integer(Integer.parseInt(groupStatus)));
            gdto.setGroupName(groupName);
            
            ReportDAO.insertGroup(con,gdto);


          }
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) ;
            Vector allReports = ReportDAO.getAllReports();
            dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_ALL_REPORTS, allReports) ;
            Vector vecGroupList = ReportDAO.getGroups(con,false);
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecGroupList) ;
            Vector reportStatus = ReportDAO.getReportStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
            dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));            
           // Utility.closeConnection (conSDSConnection);
          }
        break; 
        
        case VIEW_REPORTS:
        {
        	viewReports(dataHashMap);
        }
        break;
        	
         case SHOW_REPORTS:
          {
        	  showReports(dataHashMap,paramHashMap);        	          	         	  
          }
        break; 
        
        case SAVE_REPORT_STATUS:
          {
         // Connection conSDSConnection = Utility.getConnection();
          dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) ;
            for(int j=0; j<paramHashMap.size(); j++)
            {
              String tempStatusString = (String)paramHashMap.keySet().toArray()[j];
              String keyValue = (String)paramHashMap.get(tempStatusString);
              if(tempStatusString.startsWith("reportstatus"))
              {                               
                String reportId = tempStatusString.substring(12, tempStatusString.length());
                ReportDAO.updateReportStatus(con,reportId,keyValue);
              }              
            }
            ReportEngine reportEngine = new ReportEngine() ;
            String reportName = (String)paramHashMap.get(ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME);
            Vector vecReportList = reportEngine.retrieveReportList ( con,reportName);
            dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;
            Vector reportStatus = ReportDAO.getReportStatus(con);
            dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
           // Utility.closeConnection (con);
        }
        break;

        case ASSIGN_REPORT_TO_GROUP:
          {
        	  assignReportToGroup( dataHashMap,  paramHashMap, con);
           }
        break; 

/*
        case SHOW_USER_REPORT_ASSIGN_SCREEN:
        {

          Connection m_conSDSConnection = Utility.getConnection();          
          Vector  usersVector = com.mobinil.sds.core.system.sa.users.dao.UserDAO.getSystemUsersList(m_conSDSConnection);
          
          HashMap additionalDataHashMap = com.mobinil.sds.core.system.sa.users.dao.UserDAO.getUserReportsAdditionalData(m_conSDSConnection);        
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
          Utility.closeConnection(m_conSDSConnection);
        }
        break; 
        case UPDATE_USER_REPORT_ASSIGNMENT:
        {
          Connection m_conSDSConnection = Utility.getConnection();
          int userID = new Integer((String)paramHashMap.get(UserInterfaceKey.CONTROL_HIDDEN_NAME_USER_ID)).intValue();
          if(UserDAO.deleteUserReports(m_conSDSConnection, userID))
          {
            ReportEngine reportEngine = new ReportEngine();
            
          for(int j=0; j<paramHashMap.size(); j++)
          {
            String tempString = (String)paramHashMap.keySet().toArray()[j];
          
            
            if(tempString.startsWith(UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX))
            {
              int nReportId = new Integer(tempString.substring(
                                    tempString.lastIndexOf(
                                      UserInterfaceKey.CONTROL_CHECKBOX_NAME_PREFIX)+9)).intValue();  
              //here we need to insert
              Utility.logger.debug("calling insert");
              reportEngine.insertUserReportAccess(userID,nReportId);
            }
          }
          reportEngine.finalize();
          
          Vector  usersVector = com.mobinil.sds.core.system.sa.users.dao.UserDAO.getSystemUsersList(m_conSDSConnection);          
          HashMap additionalDataHashMap = com.mobinil.sds.core.system.sa.users.dao.UserDAO.getUserReportsAdditionalData(m_conSDSConnection);
        
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, usersVector);
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalDataHashMap);
        }
        Utility.closeConnection(m_conSDSConnection);
        
        }
        break; 
*/
        default:
          Utility.logger.debug ("Unknown action received: " + action );
      }
    }
    catch(Exception objExp)
    {
      objExp.printStackTrace();
    }
    return dataHashMap;
  }


  
  private static void assignReportToGroup(HashMap dataHashMap, HashMap paramHashMap, Connection con)
  {
		//Connection conSDSConnection= null;
	  	try
	  	{
	  	  int nGroupID = Integer.parseInt((String)paramHashMap.get(ReportInterfaceKey.PARAM_GROUP_ID));
	         
	         Vector selectedReports = new Vector();
	          for(int j=0; j<paramHashMap.size(); j++)
	         {           
	           int reportId=0;
	           String tempString = (String)paramHashMap.keySet().toArray()[j];
	           if(tempString.startsWith(ReportInterfaceKey.CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP+nGroupID))
	           {
	             reportId = new Integer(tempString.substring(
	                                         tempString.lastIndexOf(
	                                           ReportInterfaceKey.CONTROL_CHECKBOX_ASSIGN_REPORT_TO_GROUP)+(22+(nGroupID+"").length()))).intValue();
	             selectedReports.addElement(new Integer(reportId));
	           }
	           
	          
	         }
	          ReportDAO.inserReportTOGroup(con,selectedReports,nGroupID);

	           dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_RETRIEVE_REPORT_LIST) ;
	           Vector allReports = ReportDAO.getAllReports();
	           dataHashMap.put (ReportInterfaceKey.HASHMAP_KEY_COLLECTION_ALL_REPORTS, allReports) ;
	           Vector vecGroupList = ReportDAO.getGroups(con,false);
	           dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecGroupList) ;
	           Vector reportStatus = ReportDAO.getReportStatus(con);
	           dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
	           dataHashMap.put(ReportInterfaceKey.HASHMAP_KEY_COLLECTION_GROUP_STATUS,ReportDAO.getGroupStatus(con));
	  	}
	  	catch(Exception e)
	  	{
	  	}
	  
	  
	
  }
  private static void showReports(HashMap dataHashMap,HashMap paramHashMap)
  {
	  ReportEngine reportEngine = new ReportEngine() ;
	  Connection conSDSConnection = null;
	  
      //String strReportUniverseID = (String) paramHashMap.get(ReportInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE)  ;
   try{
       conSDSConnection = Utility.getConnection();         
      //int nReportUniverseID = (DataViewDAO.switchUniverseTypeToID ( conSDSConnection, strReportUniverseID)).intValue() ; 
      //int reportUniverseID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_RETRIEVE_REPORT_LIST_UNIVERSE) ).intValue() ;
      String reportName = (String)paramHashMap.get(ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME);
      dataHashMap.put(ReportInterfaceKey.INPUT_SEARCH_TEST_REPORT_NAME, reportName);
      System.out.println("The report name issssssssssss " + reportName);
      Vector vecReportList = reportEngine.retrieveReportList (conSDSConnection ,reportName/*nReportUniverseID*/ );                  
      Vector<com.mobinil.sds.core.system.gn.reports.model.ReportStatusModel> reportStatus = ReportDAO.getReportStatus(conSDSConnection);
      
      dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_SHOW_REPORT) ;
      dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;
      dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
                  
	  }
	  catch(Exception e)
	  {
		e.printStackTrace();
		
	  }
	  finally
	  {
		  if (conSDSConnection!=null)try{Utility.closeConnection(conSDSConnection);}catch(Exception e){}
	  }

  }
  
  private static void viewReports(HashMap dataHashMap)
  {
	  ReportEngine reportEngine = new ReportEngine() ;
	  Connection conSDSConnection = null;
	  
      //String strReportUniverseID = (String) paramHashMap.get(ReportInterfaceKey.PARAM_INITIALIZE_WIZARD_UNIVERSE)  ;
   try{
       conSDSConnection = Utility.getConnection();         
      //int nReportUniverseID = (DataViewDAO.switchUniverseTypeToID ( conSDSConnection, strReportUniverseID)).intValue() ; 
      //int reportUniverseID = new Integer ( (String) paramHashMap.get(ReportInterfaceKey.PARAM_RETRIEVE_REPORT_LIST_UNIVERSE) ).intValue() ;
       Vector vecReportList = new Vector();
      //Vector vecReportList = reportEngine.retrieveReportList (conSDSConnection /*nReportUniverseID*/ );                  
      Vector<com.mobinil.sds.core.system.gn.reports.model.ReportStatusModel> reportStatus = ReportDAO.getReportStatus(conSDSConnection);
      
      dataHashMap.put (InterfaceKey.HASHMAP_KEY_ACTION, ReportInterfaceKey.ACTION_SHOW_REPORT) ;
      dataHashMap.put (InterfaceKey.HASHMAP_KEY_COLLECTION, vecReportList) ;
      dataHashMap.put( InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION , reportStatus);
                  
	  }
	  catch(Exception e)
	  {
		e.printStackTrace();
		
	  }
	  finally
	  {
		  if (conSDSConnection!=null)try{Utility.closeConnection(conSDSConnection);}catch(Exception e){}
	  }

  }

}