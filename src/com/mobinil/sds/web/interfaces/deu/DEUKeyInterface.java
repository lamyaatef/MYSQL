package com.mobinil.sds.web.interfaces.deu;

/*
 * this is the DEU key interface file that has the constants of differnt strings used in the DEU module
 * such as the actions names related to the DEU 
 * and the fields names in the JSP files
 */
public interface DEUKeyInterface 
{

public static final String ACTION_UPDATE_TASK="deu_update_task";

public static final String ACTION_UPDATE_FILE="deu_update_file";

public static final String ACTION_UPDATE_SOURCE="deu_update_source";

public static final String ACTION_UPDATE_CONNECTION="deu_update_connection";

public static final String ACTION_EDIT_CONNECTION="deu_edit_connection";

public static final String ACTION_NEW_CONNECTION="deu_new_connection";

public static final String ACTION_NEW_SOURCE="deu_new_source";

public static final String ACTION_EDIT_SOURCE="deu_edit_source";

public static final String ACTION_NEW_FILE="deu_new_file";

public static final String ACTION_EDIT_FILE="deu_edit_file";

public static final String ACTION_NEW_TASK="deu_new_task";

public static final String ACTION_EDIT_TASK="deu_edit_task";

public static final String ACTION_SHOW_CONNECTION_SETTINGS="deu_show_connection_settings";

public static final String ACTION_SHOW_TASK_LOGS="deu_show_task_logs";
public static final String ACTION_SEARCH_TASK_LOGS="deu_search_task_logs";
public static final String ACTION_SHOW_SECTOR_LOGS="deu_show_sector_logs";
public static final String ACTION_SEARCH_SECTOR_LOGS="deu_search_sector_logs";

public static final String ACTION_ADD_CONNECTION="deu_add_connection";
public static final String ACTION_RUN_TASK="deu_run_task";

public static final String ACTION_ADD_SOURCE="deu_add_source";
public static final String ACTION_SHOW_SECTOR_LOGS_BY_TASK="deu_show_sector_logs_by_task";

public static final String ACTION_ADD_FILE="deu_add_file";

public static final String ACTION_ADD_TASK="deu_add_task";

public static final String ACTION_SAVE_SECTORS="deu_save_sectors";
public static final String ACTION_SHOW_SECTORS="deu_show_sectors";

public static final String ACTION_SHOW_SOURCE_SETTINGS="deu_show_source_settings";

public static final String ACTION_SHOW_FILE_SETTINGS="deu_show_file_settings";

public static final String ACTION_SHOW_TASK_SETTINGS="deu_show_task_settings";

  public static final String VECTOR_CONNECTIONS = "data_connections";
  public static final String VECTOR_SOURCES = "data_sources";
  public static final String VECTOR_FILES = "data_files";
  public static final String VECTOR_TASKS = "data_tasks";
  public static final String VECTOR_SECTOR= "sector_vector";
  public static final String VECTOR_SECTOR_STATUS = "data_sector_status";
  
  public static final String VECTOR_FILE_ENCODINGS = "data_file_encodings";
  public static final String VECTOR_FILE_TYPES = "data_file_types";
  public static final String VECTOR_TASK_STATUS = "data_task_status";
  public static final String VECTOR_TASK_WEEKDAY = "data_task_week_day";
  public static final String VECTOR_TASK_FREQUENCY = "data_task_frequency";
  public static final String VECTOR_TASK_FILE = "data_task_file";

  public static final String HASHMAP_KEY_ACTION="action";
  
  public static final String[] STYLE={"TableTextColumnOdd","TableTextColumnEven"};

  public static final String CONTROL_TEXT_CONNECTION_NAME="ConnectionName";
  public static final String CONTROL_TEXT_CONNECTION_IP="ConnectionIP";
  public static final String CONTROL_TEXT_CONNECTION_PORT="ConnectionPort";
  public static final String CONTROL_TEXT_CONNECTION_SCHEMA="ConnectionSchema";
  public static final String CONTROL_TEXT_CONNECTION_USER="ConnectionUser";
  public static final String CONTROL_TEXT_CONNECTION_PASS="ConnectionPass";
  public static final String CONTROL_TEXT_CONNECTION_DESC="ConnectionDesc";
  public static final String HIDDEN_CONNECTION_ID="connectionID";

  public static final String CONTROL_TEXT_SOURCE_NAME="sourceName";
  public static final String CONTROL_TEXT_SOURCE_SQL="sourceSQL";
  public static final String CONTROL_SELECT_SOURCE_CONNECTION="sourceConnection";
  public static final String CONTROL_TEXT_SOURCE_DATA_VIEW="sourceDataViewID";
  public static final String CONTROL_TEXT_SOURCE_DESC="sourceDesc";
  public static final String HIDDEN_SOURCE_ID="sourceID";

  public static final String CONTROL_TEXT_NAME="name";
  public static final String CONTROL_TEXT_FILE_PATH="filePath";
  public static final String CONTROL_TEXT_FILE_NAME="fileName";
  public static final String CONTROL_SELECT_FILE_TYPE="fileType";
  public static final String CONTROL_SELECT_FILE_ENCODING="fileEncoding";
  public static final String CONTROL_TEXT_FILE_SEPARATOR="sep";
  public static final String CONTROL_CHK_FILE_TIMESTAMP="timestamp";
  public static final String CONTROL_TEXT_FILE_DESC="desc";
  public static final String HIDDEN_FILE_ID="fileID";

  public static final String CONTROL_TEXT_TASK_NAME="name";
  public static final String CONTROL_SELECT_TASK_FILE="outputFile";
  public static final String CONTROL_SELECT_TASK_FREQUENCY="frequency";
  public static final String CONTROL_SELECT_TASK_WEEKDAY="weekDay";
  public static final String CONTROL_TEXT_TASK_MONTHLY_MONTHDAY="monthlyMonthDay";
  public static final String CONTROL_TEXT_TASK_YEARLY_MONTHDAY="yearlyMonthDay";
  public static final String CONTROL_SELECT_TASK_MONTH="month";
  public static final String CONTROL_TEXT_TASK_STARTDATE="date0";
  public static final String CONTROL_TEXT_TASK_DAILY_RATE="dailyRate";
  public static final String CONTROL_TEXT_TASK_WEEKLY_RATE="weeklyRate";
  public static final String CONTROL_TEXT_TASK_MONTHLY_RATE="monthlyRate";
  public static final String CONTROL_RADIO_TASK_ENDOPTION="endOption";
  public static final String CONTROL_TEXT_TASK_ENDDATE="date1";
  public static final String CONTROL_TEXT_TASK_MAXOCCURRENCES="maxOccurrences";
  public static final String CONTROL_SELECT_TASK_STATUS="status";
  public static final String CONTROL_TEXT_TASK_RUNHOUR="runHour";
  public static final String CONTROL_TEXT_TASK_RUNMIN="runMin";
  public static final String CONTROL_TEXT_TASK_DESCRIPTION="description";  
  public static final String HIDDEN_TASK_ID="taskID";

  public static final String CONTROL_TEXT_SECTOR_SEPARATOR_XXX="separator";
  public static final String CONTROL_TEXT_SECTOR_SOURCE_XXX="source";

  public static final String CONTROL_TEXT_SECTOR_LOG_ID="sectorLogID";
  //public static final String CONTROL_TEXT_TASK_LOG_ID="taskLogID";
  public static final String CONTROL_TEXT_TASK_LOG_ID="taskLogID";
  public static final String CONTROL_SELECT_INITIAL_RELATION="initialRelation";
  public static final String CONTROL_TEXT_INITIAL_TIME="date0";
  public static final String CONTROL_SELECT_FINAL_RELATION="finalRelation";
  public static final String CONTROL_TEXT_FINAL_TIME="date1";
  public static final String CONTROL_SELECT_TASK="taskName";
  public static final String CONTROL_SELECT_FILE="fileName";
  public static final String CONTROL_SELECT_SOURCE="sourceName";
  public static final String CONTROL_SELECT_CONNECTION="connectionName";  
  public static final String CONTROL_SELECT_ROW_COUNT="rowCountRelation";  
  public static final String CONTROL_TEXT_ROW_COUNT="rowCount";
  public static final String CONTROL_SELECT_STATUS="status";
  public static final String CONTROL_SELECT_SECTOR_COUNT="secCountRelation";
  public static final String CONTROL_TEXT_SECTOR_COUNT="secCount";  
  public static final String HIDDEN_LOG_ID="logID";

  public static final String HIDDEN_TASK_NAME="taskName";

  public static final String HIDDEN_TASK_LOG_ID="taskLogID";
}