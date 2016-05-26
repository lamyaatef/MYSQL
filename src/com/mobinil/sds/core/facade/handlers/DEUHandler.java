package com.mobinil.sds.core.facade.handlers;

import java.io.*;
import java.net.*;
import com.mobinil.sds.web.interfaces.deu.*;
import com.mobinil.sds.web.interfaces.*;
import com.mobinil.sds.core.system.deu.connection.dao.*;
import com.mobinil.sds.core.system.deu.connection.model.*;
import com.mobinil.sds.core.system.deu.source.dao.*;
import com.mobinil.sds.core.system.deu.source.model.*;
import com.mobinil.sds.core.system.deu.file.dao.*;
import com.mobinil.sds.core.system.deu.file.model.*;
import com.mobinil.sds.core.system.deu.frequency.dao.*;
import com.mobinil.sds.core.system.deu.sector.dao.*;
import com.mobinil.sds.core.system.deu.taskstatus.dao.*;
import com.mobinil.sds.core.system.deu.filetype.dao.*;
import com.mobinil.sds.core.system.deu.encoding.dao.*;
import com.mobinil.sds.core.system.deu.task.dao.*;
import com.mobinil.sds.core.system.deu.task.model.*;
import com.mobinil.sds.core.system.deu.weekday.dao.*;
import com.mobinil.sds.core.system.deu.tasklog.dao.*;
import com.mobinil.sds.core.system.deu.sectorlog.dao.*;
import com.mobinil.sds.core.system.deu.tasklogstatus.dao.*;
import com.mobinil.sds.core.system.deu.sectorlogstatus.dao.*;
import com.mobinil.sds.core.system.deu.runnerParams.dao.*;
import com.mobinil.sds.core.system.deu.runnerParams.model.*;
import com.mobinil.sds.core.utilities.*;
import java.util.*;



public class DEUHandler  
{
 



   private static boolean debugFlag = false; 

  private static void debug(String msg) {
    if (debugFlag)
    Utility.logger.debug(msg);
  }

    public static HashMap handle(String action, HashMap paramHashMap, java.sql.Connection con) throws Exception {

    HashMap dataHashMap = new HashMap(100);

    if (action.compareTo(DEUKeyInterface.ACTION_SHOW_CONNECTION_SETTINGS)==0)
    {
      Vector connectionVector = ConnectionDAO.getAllConnections();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, connectionVector);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_SOURCE_SETTINGS)==0)
    {
      Vector sourceVector = SourceDAO.getAllSources();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sourceVector);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_FILE_SETTINGS)==0)
    {
      Vector fileVector = FileDAO.getAllFiles();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, fileVector);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_TASK_SETTINGS)==0)
    {
      Vector taskVector = TaskDAO.getAllTasks();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, taskVector);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_ADD_CONNECTION)==0)
    {
        String strConnName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME);
        String strDbIP = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP);
        String strDbPort = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);
        String strDbSchema = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA);
        String strDbUser = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER);
        String strDbPass = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PASS);
        String strConnDesc = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);


        dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME , strConnName);
        dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP , strDbIP);        
        dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT, strDbPort);
        dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA , strDbSchema);
        dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER , strDbUser);
        dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC, strConnDesc);
        


        if(!ConnectionDAO.testConnection(strDbIP,strDbPort,strDbSchema,strDbUser,strDbPass))
        {
          dataHashMap.put("ErrorMessage","Connection Unreachable");                
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Connection Unreachable");            
        }
        else  
        {
        String returnValue = ConnectionDAO.addConnection(strConnName,strDbIP,strDbPort,strDbSchema,strDbUser,strDbPass,strConnDesc) ;
        if (returnValue!=null) 
        {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, returnValue);
        }
         else 
        {
        //to indicate that there is no error in saving and that the saving is done
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "NULL");
        }        
        }                                         
//         dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,ConnectionDAO.getAllConnections());
      
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_EDIT_CONNECTION)==0)
    {
      String strConnectionID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_CONNECTION_ID);
            
      ConnectionModel newConnectionModel = ConnectionDAO.getConnectionByID(strConnectionID);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newConnectionModel);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_UPDATE_CONNECTION)==0)
    {
      String strConnName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME);
      String strDbIP = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP);
      String strDbPort = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT);
      String strDbSchema = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA);
      String strDbUser = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER);
      String strDbPass = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PASS);
      String strConnDesc = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC);
      String strConnectionID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_CONNECTION_ID);

      dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_NAME , strConnName);
      dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_IP , strDbIP);        
      dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_PORT, strDbPort);
      dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_SCHEMA , strDbSchema);
      dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_USER , strDbUser);
      dataHashMap.put(DEUKeyInterface.CONTROL_TEXT_CONNECTION_DESC, strConnDesc);
      dataHashMap.put(DEUKeyInterface.HIDDEN_CONNECTION_ID, strConnectionID);

      Utility.logger.debug("strDbPass ="+strDbPass);
      String decPass ="";
      
      if (strDbPass ==null || strDbPass.compareTo("")==0) {
          String pass = ConnectionDAO.getConnectionByID(strConnectionID).getPassword() ;
          decPass = PasswordUtility.decrypt(pass,5);
      }
      else {
        decPass = strDbPass;
      }
            
      if(!ConnectionDAO.testConnection(strDbIP,strDbPort,strDbSchema,strDbUser,decPass))
      {
          dataHashMap.put("ErrorMessage","Connection Unreachable");                
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Connection Unreachable");            
      }
     else  
      {
      String returnValue =ConnectionDAO.UpdateConnection(strConnectionID,strConnName,strDbIP,strDbPort,strDbSchema,strDbUser,strDbPass,strConnDesc); 
      
      if (returnValue!=null) {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, returnValue);
        }
      else {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "NULL");
        }
//      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,ConnectionDAO.getAllConnections());
     }
    }

    
    else if (action.compareTo(DEUKeyInterface.ACTION_NEW_SOURCE)==0)
    {
      Vector connectionVector = ConnectionDAO.getAllConnections();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, connectionVector);      
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_ADD_SOURCE)==0)
    {
        String strSourceName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_NAME);
        String strSQL = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);
        String strConnectionID = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_SOURCE_CONNECTION);
        String strSourceDataView = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_DATA_VIEW);
        String strSourceDesc = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_DESC);

        if (SourceDAO.addSource(strSourceName,strSQL,strConnectionID,strSourceDataView,strSourceDesc)!=1) {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Source Can't be added!");
        }
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,SourceDAO.getAllSources());
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_EDIT_SOURCE)==0)
    {
      String strSourceID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_SOURCE_ID);
      SourceModel newSourceModel = SourceDAO.getSourceByID(strSourceID);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newSourceModel);
      Vector connectionVector = ConnectionDAO.getAllConnections();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, connectionVector);  
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_UPDATE_SOURCE)==0)
    {
        String strSQL = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_SQL);
        String strConnectionID = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_SOURCE_CONNECTION);
        String strSourceDataView = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_DATA_VIEW);
        String strSourceDesc = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_DESC);
      String strSourceID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_SOURCE_ID);
      String strSourceName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SOURCE_NAME);
      
      if (SourceDAO.UpdateSource(strSourceID,strSourceName,strSQL,strConnectionID,strSourceDataView,strSourceDesc)!=1) {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Source Can't be updated!");
        }
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,SourceDAO.getAllSources());
    }

    else if (action.compareTo(DEUKeyInterface.ACTION_NEW_FILE)==0)
    { 
      HashMap additionalHashMap =new HashMap(100);
      Vector encodingVector = EncodingDAO.getAllEncodings();
      Vector filetypeVector = FiletypeDAO.getAllFiletypes();      
      additionalHashMap.put(DEUKeyInterface.VECTOR_FILE_ENCODINGS, encodingVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_FILE_TYPES, filetypeVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalHashMap);      
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_ADD_FILE)==0)
    {
      String strName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_NAME);
      String strFilePath = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_PATH);
      String strFileName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);
      String strFileExt = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);
      String strFileSep = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_SEPARATOR);
      String strFileTimeStamp = (String)paramHashMap.get(DEUKeyInterface.CONTROL_CHK_FILE_TIMESTAMP);
      String strFileEncoding = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FILE_ENCODING);
      String strFileDesc = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_DESC);
      int fileID=FileDAO.addFile(strName,strFilePath,strFileName,strFileExt,strFileSep,strFileTimeStamp,strFileEncoding,strFileDesc);

      if (fileID<1) {
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "File Can't be added!");
      }
      //dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,FileDAO.getAllFiles());
      HashMap additional=new HashMap(2);
      Vector sourcesVector = SourceDAO.getAllSources();
      additional.put(DEUKeyInterface.HIDDEN_FILE_ID, fileID+"");
      additional.put(DEUKeyInterface.VECTOR_SOURCES, sourcesVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);



      FileModel newFileModel = FileDAO.getFileByID(fileID+"");
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newFileModel);
      HashMap additionalHashMap =new HashMap(10);
      Vector encodingVector = EncodingDAO.getAllEncodings();
      Vector filetypeVector = FiletypeDAO.getAllFiletypes();      
      additionalHashMap.put(DEUKeyInterface.VECTOR_FILE_ENCODINGS, encodingVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_FILE_TYPES, filetypeVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalHashMap); 

      //hagry
      Vector sectorsVector = SectorDAO.getSectorsByFileID(fileID+"");

      dataHashMap.put(DEUKeyInterface.VECTOR_SECTOR, sectorsVector);
      

//      Vector sourcesVector = SourceDAO.getAllSources();
      dataHashMap.put(DEUKeyInterface.HIDDEN_FILE_ID, fileID+"");
      dataHashMap.put(DEUKeyInterface.VECTOR_SOURCES, sourcesVector);
      

    }
    else if (action.compareTo(DEUKeyInterface.ACTION_EDIT_FILE)==0)
    {
      String strFileID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
      FileModel newFileModel = FileDAO.getFileByID(strFileID);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newFileModel);
      HashMap additionalHashMap =new HashMap(10);
      Vector encodingVector = EncodingDAO.getAllEncodings();
      Vector filetypeVector = FiletypeDAO.getAllFiletypes();      
      additionalHashMap.put(DEUKeyInterface.VECTOR_FILE_ENCODINGS, encodingVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_FILE_TYPES, filetypeVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalHashMap); 
      //hagry
     // String fileID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
     
      Vector sectorsVector = SectorDAO.getSectorsByFileID(strFileID);

      dataHashMap.put(DEUKeyInterface.VECTOR_SECTOR, sectorsVector);
      

      Vector sourcesVector = SourceDAO.getAllSources();
      dataHashMap.put(DEUKeyInterface.HIDDEN_FILE_ID, strFileID+"");
      dataHashMap.put(DEUKeyInterface.VECTOR_SOURCES, sourcesVector);
      

    }
    else if (action.compareTo(DEUKeyInterface.ACTION_UPDATE_FILE)==0)
    {
         String strName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_NAME);
        String strFilePath = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_PATH);
        String strFileName = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_NAME);
        String strFileExt = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FILE_TYPE);
        String strFileSep = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_SEPARATOR);
        String strFileTimeStamp = (String)paramHashMap.get(DEUKeyInterface.CONTROL_CHK_FILE_TIMESTAMP);
        String strFileEncoding = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FILE_ENCODING);
        String strFileDesc = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FILE_DESC);
        String strFileID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
      
      if (FileDAO.UpdateFile(strFileID,strName,strFilePath,strFileName,strFileExt,strFileSep,strFileTimeStamp,strFileEncoding,strFileDesc)!=1) {
          dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "File Can't be updated!");
        }
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,FileDAO.getAllFiles());
    }





///////////////////////////////

    else if (action.compareTo(DEUKeyInterface.ACTION_NEW_TASK)==0)
    { 
      HashMap additionalHashMap =new HashMap(100);
      Vector fileVector = FileDAO.getAllFiles();
      Vector frequencyVector = FrequencyDAO.getAllFrequencies();
      Vector TaskStatusVector = TaskStatusDAO.getAllTaskStatus();
      Vector WeekDayVector = WeekDayDAO.getAllDays();
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_FILE, fileVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_FREQUENCY, frequencyVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_STATUS, TaskStatusVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_WEEKDAY, WeekDayVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalHashMap);      
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_ADD_TASK)==0)
    {
      String name = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_NAME);
      String outputFile = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_FILE);
      String frequency = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_FREQUENCY);
      String weekDay = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_WEEKDAY);
      String monthlyMonthDay = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_MONTHLY_MONTHDAY);
      String yearlyMonthDay = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_YEARLY_MONTHDAY);
      String month = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_MONTH);
      String startDate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_STARTDATE);
      String dailyRate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_DAILY_RATE);
      String weeklyRate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_WEEKLY_RATE);
      String monthlyRate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_MONTHLY_RATE);
      String endOption = (String)paramHashMap.get(DEUKeyInterface.CONTROL_RADIO_TASK_ENDOPTION);
      String endDate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_ENDDATE);
      String maxOccurrences = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_MAXOCCURRENCES);
      String status = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_STATUS);
      String runHour = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);
      String runMin = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);
      String description = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_DESCRIPTION);

      if (TaskDAO.addTask(name,outputFile, frequency, dailyRate, weeklyRate, 
      monthlyRate, weekDay, monthlyMonthDay, yearlyMonthDay, month, startDate, endOption, endDate,
      maxOccurrences, status, runHour,runMin, description)!=1) {
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Task Can't be added!");
      }
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,TaskDAO.getAllTasks());
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_EDIT_TASK)==0)
    {
      String strTaskID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_TASK_ID);
      TaskModel newTaskModel = TaskDAO.getTaskByID(strTaskID);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, newTaskModel);
      HashMap additionalHashMap =new HashMap(100);
      Vector fileVector = FileDAO.getAllFiles();
      Vector frequencyVector = FrequencyDAO.getAllFrequencies();
      Vector TaskStatusVector = TaskStatusDAO.getAllTaskStatus();
      Vector WeekDayVector = WeekDayDAO.getAllDays();
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_FILE, fileVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_FREQUENCY, frequencyVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_STATUS, TaskStatusVector);
      additionalHashMap.put(DEUKeyInterface.VECTOR_TASK_WEEKDAY, WeekDayVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additionalHashMap); 
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_UPDATE_TASK)==0)
    {
      String taskID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_TASK_ID);
      String name = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_NAME);
      String outputFile = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_FILE);
      String frequency = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_FREQUENCY);
      String weekDay = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_WEEKDAY);
      String monthlyMonthDay = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_MONTHLY_MONTHDAY);
      String yearlyMonthDay = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_YEARLY_MONTHDAY);
      String month = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_MONTH);
      String startDate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_STARTDATE);
      String dailyRate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_DAILY_RATE);
      String weeklyRate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_WEEKLY_RATE);
      String monthlyRate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_MONTHLY_RATE);
      String endOption = (String)paramHashMap.get(DEUKeyInterface.CONTROL_RADIO_TASK_ENDOPTION);
      String endDate = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_ENDDATE);
      String maxOccurrences = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_MAXOCCURRENCES);
      String status = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK_STATUS);
      String runHour = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_RUNHOUR);
            String runMin = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_RUNMIN);
      String description = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_DESCRIPTION);

      if (TaskDAO.updateTask(taskID,name,outputFile, frequency, dailyRate, 
      monthlyRate,weeklyRate, weekDay, monthlyMonthDay, yearlyMonthDay, month, startDate, endOption, endDate,
      maxOccurrences, status, runHour,runMin, description)!=1) {
        dataHashMap.put(InterfaceKey.HASHMAP_KEY_SERVLET_EXP, "Task Can't be Updated!");
      }
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION,TaskDAO.getAllTasks());
  }




///////////////////////////////

    


    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_SECTORS)==0)
    {
      String fileID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
      Vector sectorsVector = SectorDAO.getSectorsByFileID(fileID);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sectorsVector);
      HashMap additional=new HashMap(2);
      Vector sourcesVector = SourceDAO.getAllSources();
      additional.put(DEUKeyInterface.HIDDEN_FILE_ID, fileID+"");
      additional.put(DEUKeyInterface.VECTOR_SOURCES, sourcesVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SAVE_SECTORS)==0)
    {
      String fileID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
      SectorDAO.deleteSectors(fileID);
           
      for ( int i=0; i<(paramHashMap.size()-1)/2; i++)
      {
        String separator = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SECTOR_SEPARATOR_XXX+(i+1));
        String sql = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SECTOR_SOURCE_XXX+(i+1));
        SectorDAO.addSector(fileID,sql,separator,(i+1)+"");
      }
      Vector fileVector = FileDAO.getAllFiles();
     dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, fileVector);   
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_TASK_LOGS)==0)
    {
      Vector logVector = TaskLogDAO.getAllTaskLogs();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, logVector);
      HashMap additional=new HashMap(3);
      Vector fileVector =FileDAO.getAllFiles();
      Vector taskVector =TaskDAO.getAllTasks();
      Vector statusVector =TaskLogStatusDAO.getAllTaskLogStatus();
      
      additional.put(DEUKeyInterface.VECTOR_TASKS, taskVector);
      additional.put(DEUKeyInterface.VECTOR_FILES, fileVector);
      additional.put(DEUKeyInterface.VECTOR_SECTOR_STATUS, statusVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SEARCH_TASK_LOGS)==0)
    {
      String logID = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_LOG_ID);
      String initialRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_INITIAL_RELATION);
      String initTime = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_INITIAL_TIME);
      String finalRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FINAL_RELATION);
      String finalTime = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FINAL_TIME);
      String task = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK);
      String file = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FILE);
      String rowCountRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_ROW_COUNT); 
      String rowCount = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_ROW_COUNT);
      String secCountRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_SECTOR_COUNT); 
      String secCount = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SECTOR_COUNT);
      String status = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_STATUS);

      Vector logVector = TaskLogDAO.searchTaskLogs(logID, initialRelation, 
      initTime, finalTime, finalRelation, task, file, secCountRelation, secCount, 
      rowCountRelation, rowCount, status);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, logVector);
      HashMap additional=new HashMap(3);
      Vector fileVector =FileDAO.getAllFiles();
      Vector taskVector =TaskDAO.getAllTasks();
      Vector statusVector =TaskLogStatusDAO.getAllTaskLogStatus();   
      additional.put(DEUKeyInterface.VECTOR_TASKS, taskVector);
      additional.put(DEUKeyInterface.VECTOR_FILES, fileVector);
      additional.put(DEUKeyInterface.VECTOR_SECTOR_STATUS, statusVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_SECTOR_LOGS)==0)
    {
      Vector logVector = SectorLogDAO.getAllSectorLogs();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, logVector);
      HashMap additional=new HashMap(5);
      Vector connectionVector = ConnectionDAO.getAllConnections();
      Vector sourceVector =SourceDAO.getAllSources();
      Vector fileVector =FileDAO.getAllFiles();
      Vector taskVector =TaskDAO.getAllTasks();
      Vector statusVector =SectorLogStatusDAO.getAllSectorLogStatus();
      
      additional.put(DEUKeyInterface.VECTOR_TASKS, taskVector);
      additional.put(DEUKeyInterface.VECTOR_FILES, fileVector);
      additional.put(DEUKeyInterface.VECTOR_SOURCES, sourceVector);
      additional.put(DEUKeyInterface.VECTOR_CONNECTIONS, connectionVector);
      additional.put(DEUKeyInterface.VECTOR_SECTOR_STATUS, statusVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);
    }
    
    else if (action.compareTo(DEUKeyInterface.ACTION_SEARCH_SECTOR_LOGS)==0)
    {
      String logID = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_SECTOR_LOG_ID);
      
      String taskWorkLogId = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_TASK_LOG_ID);
      
      String initialRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_INITIAL_RELATION);
      String initTime = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_INITIAL_TIME);
      String finalRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FINAL_RELATION);
      String finalTime = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_FINAL_TIME);
      String task = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_TASK);
      String file = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_FILE);
      String source = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_SOURCE);
      String connection = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_CONNECTION);
      String rowCountRelation = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_ROW_COUNT); 
      String rowCount = (String)paramHashMap.get(DEUKeyInterface.CONTROL_TEXT_ROW_COUNT);
      String status = (String)paramHashMap.get(DEUKeyInterface.CONTROL_SELECT_STATUS);

      Vector logVector = SectorLogDAO.searchSectorLogs(logID, initialRelation, 
      initTime, finalTime, finalRelation, task, file, source, connection, 
      rowCountRelation, rowCount, status, taskWorkLogId);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, logVector);
      HashMap additional=new HashMap(5);
      Vector connectionVector = ConnectionDAO.getAllConnections();
      Vector sourceVector =SourceDAO.getAllSources();
      Vector fileVector =FileDAO.getAllFiles();
      Vector taskVector =TaskDAO.getAllTasks();
      Vector statusVector =SectorLogStatusDAO.getAllSectorLogStatus();
   
      additional.put(DEUKeyInterface.VECTOR_TASKS, taskVector);
      additional.put(DEUKeyInterface.VECTOR_FILES, fileVector);
      additional.put(DEUKeyInterface.VECTOR_SOURCES, sourceVector);
      additional.put(DEUKeyInterface.VECTOR_CONNECTIONS, connectionVector);
      additional.put(DEUKeyInterface.VECTOR_SECTOR_STATUS, statusVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_SHOW_SECTOR_LOGS_BY_TASK)==0)
    {
      String taskLogID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_TASK_LOG_ID);
      Vector logVector = SectorLogDAO.getSectorLogs(taskLogID);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, logVector);
      HashMap additional=new HashMap(5);
      Vector connectionVector = ConnectionDAO.getAllConnections();
      Vector sourceVector =SourceDAO.getAllSources();
      Vector fileVector =FileDAO.getAllFiles();
      Vector taskVector =TaskDAO.getAllTasks();
      Vector statusVector =SectorLogStatusDAO.getAllSectorLogStatus();
      
      additional.put(DEUKeyInterface.VECTOR_TASKS, taskVector);
      additional.put(DEUKeyInterface.VECTOR_FILES, fileVector);
      additional.put(DEUKeyInterface.VECTOR_SOURCES, sourceVector);
      additional.put(DEUKeyInterface.VECTOR_CONNECTIONS, connectionVector);
      additional.put(DEUKeyInterface.VECTOR_SECTOR_STATUS, statusVector);
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION, additional);
    }
    else if (action.compareTo(DEUKeyInterface.ACTION_RUN_TASK)==0)
    {
      RunnerParamsModel params= (RunnerParamsModel)RunnerParamsDAO.getAllParams().get(0);      
      String taskID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_TASK_ID);
      try{
        Socket client;
        client= new Socket(params.getDaemonIP(), Integer.parseInt(params.getDaemonPort()));
        ObjectOutputStream server= new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
        server.writeObject(new com.mobinil.sds.core.messages.RunnerMessage(params.getDaemonPass(),taskID));
        server.close();
        client.close();
        }
      catch(Exception e){
        Utility.logger.debug("Can not create client socket! "+e);}
      Vector taskVector = TaskDAO.getAllTasks();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, taskVector);
    }
    else if (action.compareTo("deu_delete_connection")==0)
    {
      String ID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_CONNECTION_ID);
     boolean successFlag =  ConnectionDAO.deleteConnection(ID);
     
       Vector connectionVector = ConnectionDAO.getAllConnections();
     dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, connectionVector);
     if (!successFlag)
     {
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE ,"Can not delete a connection being used in a source");
       
     }
     
    }
    else if (action.compareTo("deu_delete_source")==0)
    {
    String ID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_SOURCE_ID);

      boolean successFlag = SourceDAO.deleteSource(ID);
      
      Vector sourceVector = SourceDAO.getAllSources();
     dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, sourceVector);
     if (!successFlag)
     {
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE ,"Can not delete a source being used in a file");
     }
    }
    else if (action.compareTo("deu_delete_file")==0)
    {
    String ID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_FILE_ID);
      boolean successFlag = FileDAO.deleteFile(ID);
      Vector fileVector = FileDAO.getAllFiles();
     dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, fileVector);

         if (!successFlag)
     {
       dataHashMap.put(InterfaceKey.HASHMAP_KEY_MESSAGE ,"Can not delete a file being used in a task");
     }
 

    }
    else if (action.compareTo("deu_delete_task")==0)
    {
    String ID = (String)paramHashMap.get(DEUKeyInterface.HIDDEN_TASK_ID);
      TaskDAO.deleteTask(ID);
      Vector taskVector = TaskDAO.getAllTasks();
      dataHashMap.put(InterfaceKey.HASHMAP_KEY_COLLECTION, taskVector);
    }
    return dataHashMap;
    }
}
