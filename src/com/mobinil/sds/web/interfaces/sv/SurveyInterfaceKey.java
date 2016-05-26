package com.mobinil.sds.web.interfaces.sv;

/**
 * SurveyInterfaceKey interface holding all the keys used in the Survey.
 *
 * @version	1.01 Feb 2007
 * @author  Waseem Safwat Adly
 * @see     
 *
 * SDS
 * MobiNil
 */ 

public interface SurveyInterfaceKey 
{
  ////////////////////////////////////////////////////////////////////////
  /*                 Survey Actions Keys                 /
  *//////////////////////////////////////////////////////////////////////
  public static final String ACTION_CREATE_NEW_SURVEY = "create_new_survey";
  public static final String ACTION_SAVE_SURVEY = "save_survey";
  public static final String ACTION_VIEW_ALL_SURVEYS = "view_all_surveys";
  public static final String ACTION_EDIT_SURVEY = "edit_survey";  
  public static final String ACTION_UPDATE_SURVEY = "update_survey";
  public static final String ACTION_UPDATE_SURVEYS_STATUS = "update_surveys_status";
  public static final String ACTION_VIEW_SURVEY_GROUPS = "view_survey_groups";
  public static final String ACTION_CREATE_NEW_GROUP = "create_new_group";
  public static final String ACTION_SAVE_GROUP = "save_group";
  public static final String ACTION_EDIT_GROUP = "edit_group";
  public static final String ACTION_UPDATE_GROUP = "update_group";
  public static final String ACTION_UPDATE_GROUPS_STATUS = "update_groups_status";
  public static final String ACTION_VIEW_ALL_QUESTIONS = "view_all_questions";
  public static final String ACTION_CREATE_NEW_QUESTION = "create_new_question";
  public static final String ACTION_SAVE_QUESTION = "save_question";
  public static final String ACTION_EDIT_QUESTION = "edit_question";
  public static final String ACTION_UPDATE_QUESTION = "update_question";
  public static final String ACTION_UPDATE_QUESTION_STATUS = "update_question_status";

  public static final String ACTION_FILLING_CREATE_NEW_SURVEY = "filling_create_new_survey";
  public static final String ACTION_FILLING_SAVE_SURVEY = "filling_save_survey";
  public static final String ACTION_FILLING_VIEW_ALL_SURVEYS = "filling_view_all_surveys";
  public static final String ACTION_FILLING_VIEW_SURVEY_GROUPS = "filling_view_survey_groups";
  public static final String ACTION_FILLING_UPDATE_SURVEY_STATUS = "filling_update_survey_status";
  public static final String ACTION_FILLING_UPDATE_QUESTION_ANSWERS = "filling_update_question_answers";
  public static final String ACTION_FILLING_COMPLETE_FIL_SURVEY = "filling_complete_fil_survey";

  public static final String ACTION_VIEW_MISSION_GROUPS = "view_mission_groups";
  public static final String ACTION_EDIT_MISSION_GROUP = "edit_mission_group";
  public static final String ACTION_CREATE_NEW_MISSION_GROUP = "create_new_mission_group";
  public static final String ACTION_UPDATE_MISSION_GROUP_STATUS = "update_mission_group_status";
  public static final String ACTION_SAVE_MISSION_GROUP = "save_mission_group";

  public static final String ACTION_SRV_POS_SURVEY_GROUP = "srv_pos_survey_group";
  public static final String ACTION_SRV_POS_GROUP_QUESTION = "srv_pos_group_question";
  public static final String ACTION_SRV_SAVE_GROUP_QUESTION = "srv_save_group_question";
  public static final String ACTION_SRV_CREATE_NEW_POS_GROUP = "srv_create_new_pos_group";
  public static final String ACTION_SRV_CREATE_NEW_POS_SURVEY = "srv_create_new_pos_survey";
  public static final String ACTION_SRV_SAVE_POS_SURVEY_GROUP = "srv_save_pos_survey_group";
  public static final String ACTION_SRV_CREATE_NEW_POS_QUESTION = "srv_create_new_pos_question"; 
  public static final String ACTION_SRV_SAVE_POS_QUESTION = "srv_save_pos_question";
  public static final String ACTION_SRV_POS_QUESTIONS ="srv_pos_questions";
  public static final String ACTION_SRV_POS_SURVEY ="srv_pos_survey";
  public static final String ACTION_SRV_POS_GROUP ="srv_pos_group";
  public static final String ACTION_SRV_SAVE_POS_GROUP = "srv_save_pos_group";
  
  
  ////////////////////////////////////////////////////////////////////////
  /*                 Survey Inputs Keys                 /
  *//////////////////////////////////////////////////////////////////////

  public static final String INPUT_HIDDEN_SURVEY_ID = "survey_id";
  public static final String INPUT_TEXT_SURVEY_NAME = "survey_name";
  public static final String INPUT_HIDDEN_SURVEY_STATUS = "survey_status_hidden";
  public static final String INPUT_SELECT_SURVEY_STATUS = "survey_status_select";
  public static final String INPUT_SELECT_SURVEY_TYPE = "survey_type";
  public static final String INPUT_TEXTAREA_SURVEY_DESCRIPTION = "survey_description";

  public static final String INPUT_SELECT_GROUP_STATUS = "group_status_select";
  public static final String INPUT_HIDDEN_GROUP_ID = "group_id";
  public static final String INPUT_TEXT_GROUP_NAME = "group_name";
  public static final String INPUT_TEXT_GROUP_WEIGHT = "group_weight";
  public static final String INPUT_TEXT_GROUP_ORDER = "group_order";
  public static final String INPUT_TEXT_GROUP_REFERENCE = "group_reference";
  public static final String INPUT_TEXTAREA_GROUP_DESCRIPTION = "group_description";
  public static final String INPUT_HIDDEN_GROUP_STATUS_ID = "group_status_id";  
  public static final String INPUT_HIDDEN_SUM_OF_GROUP_WEIGHTS = "sum_of_group_weights";
  public static final String INPUT_HIDDEN_GROUP_WEIGHT = "hidden_group_weight";
  public static final String INPUT_HIDDEN_SURVEY_TYPE_ID = "hidden_survey_type_id";
  public static final String INPUT_HIDDEN_GROUP_TYPE_ID = "hidden_group_type_id";  
  
  public static final String INPUT_HIDDEN_QUESTION_ID = "question_id";
  public static final String INPUT_SELECT_QUESTION_STATUS = "question_status_select";
  public static final String INPUT_TEXTAREA_QUESTION = "question";
  public static final String INPUT_TEXT_QUESTION_WEIGHT = "question_weight";
  public static final String INPUT_TEXT_QUESTION_ORDER = "question_order";
  public static final String INPUT_SELECT_QUESTION_MANDATORY = "question_mandatory";
  public static final String INPUT_SELECT_QUESTION_CATEGORY = "question_category";
  public static final String INPUT_SELECT_QUESTION_TYPE = "question_type";
  public static final String INPUT_HIDDEN_QUESTION_STATUS_ID = "question_status_id";
  public static final String INPUT_HIDDEN_QUESTION_WEIGHT = "hidden_question_weight";
  public static final String INPUT_HIDDEN_SUM_OF_QUESTION_WEIGHTS = "hidden_sum_of_question_weights";
  
  
  public static final String INPUT_SELECT_DCM_NAME = "dcm_select_name";
  public static final String INPUT_SELECT_SURVEY = "survey_select";
  public static final String INPUT_QUESTION_ANSWER = "question_answer";

  public static final String INPUT_HIDDEN_MISSION_GROUP_ID = "hidden_mission_group_id";
  public static final String INPUT_SELECT_MISSION_GROUP_STATUS = "select_mission_group_status";
  public static final String INPUT_TEXT_MISSION_GROUP_NAME = "text_mission_group_name";
  

  public static final String ERROR_SUM_OF_GROUP_WEIGHTS = "error_sum_of_group_weights";
  public static final String ERROR_SUM_OF_QUESTION_WEIGHTS = "error_sum_of_question_weights";

  //////////////////////////////////////////////////////////////////////////////////////
  //////// ADDITIONAL POS SURVEY KEYS//////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////
  public static final String VECTOR_POS_QUESTION = "pos_question_vector";
  public static final String VECTOR_POS_GROUP_QUESTION = "pos_group_question_vector";
  public static final String VECTOR_POS_GROUP = "pos_group_vector";
  public static final String VECTOR_POS_SURVEY = "pos_survey_vector";
  public static final String INPUT_CHECK_BOX_SELECTED_QUESTION = "selected_question";
  
  ////////////////////////////////////////////////////////////////////////
  /*                 Survey HashMap Additional Keys                 /
  *//////////////////////////////////////////////////////////////////////

  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_DETAILS = "question_details";
  public static final String HASHMAP_KEY_ADDITIONAL_COLLECTION_QUESTION_CHOICES = "question_choices";
}