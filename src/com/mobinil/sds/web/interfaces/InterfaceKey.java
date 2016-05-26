package com.mobinil.sds.web.interfaces;

/**
 * General interface holding all the keys used in the HashMaps and the 
 * sessions objects.
 * For example:
 * <pre>
 *      UserDTO dtoUser;
 *      request.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT,dtoUser);
 * </pre>
 *
 * @version	1.01 Feb 2004
 * @author  Ahmed Mohamed Abd Elmonem
 * @see     
 *
 * SDS
 * MobiNil
 */ 

public interface InterfaceKey 
{

////////////////////////////////////////////////////////////////////////
/*                      Returned Data HashMap Keys 
*////////////////////////////////////////////////////////////////////////

  public static final boolean FOR_LINUX = true;

// Key for the user id
  public static final String HASHMAP_KEY_USER_ID = "data_user_id";
//Key for the user id
  public static final String HASHMAP_KEY_APPLIACTION_CONTEXT = "application_context";
  
// Key for the data transfer object that contains all data
  public static final String HASHMAP_KEY_DTO_OBJECT = "data_dto";
  
// Key for a collection of data transfer object that contains all data
  public static final String HASHMAP_KEY_COLLECTION = "data_main_collection";

  public static final String HASHMAP_KEY_LIST_COLLECTION = "list_main_collection";

  public static final String HASHMAP_KEY_JOB_ID = "hashmap_key_job_id";
  
// Key for additional collection of data transfer object that contains all data
  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION = "data_additional_collection";
  public static final String HASHMAP_KEY_ADDITIONAL_FIELD = "data_additional_field";
  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION_2 = "data_additional_collection2";
  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION_3 = "data_additional_collection3";
  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION_4 = "data_additional_collection4";
  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION_5 = "data_additional_collection5";
  
// Key for the action string
  public static final String HASHMAP_KEY_ACTION = "action";
  
// Key for the included/redirect to url
  public static final String HASHMAP_KEY_URL = "url";
  
// Key for the exception caught in the WebControllerServlet
  public static final String HASHMAP_KEY_SERVLET_EXP = "servlet_exception"; 

// Key for the displayed message
  public static final String HASHMAP_KEY_MESSAGE = "message";

////////////////////////////////////////////////////////////////////////
/*                          Session Keys
*////////////////////////////////////////////////////////////////////////
 
// Key for the User ID attribute in the session object
  public static final String SESSION_KEY_USER_ID = "sessionUserId";
  

////////////////////////////////////////////////////////////////////////
/*                          Common Keys
*////////////////////////////////////////////////////////////////////////
  
// This is an example of such keys  
  public static final String HASHMAP_KEY_XXX = "";


  public static final String HIDDEN_DIV_FOR_PAGE_LOADING ="HIDDEN_DIV";


  public static final String[] STYLE={"TableTextColumnOdd","TableTextColumnEven"};

  public static final String CONTROL_INPUT_FILE = "file";
  public static final String CONTROL_INPUT_FILE_NAME = "file_name_export";
  public static final String ATTACH_ID = "attach_id";
  public static final String EXPORT_FILE_PATH = "exported_file_pathe";
  public static final String DOWNLOAD_FILE_PATH = "DOWNLOAD_FILE_PATH";
  public static final String MODULE_SUB_PATH = "module_subPath";
  public static final String HASHMAP_KEY_REQUEST_FROM_SERVLET = "hashmap_key_request_from_servlet";  
}

