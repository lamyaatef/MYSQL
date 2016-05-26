/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobinil.sds.web.interfaces.commission;

/**
 *
 * @author mabdelaal
 */
public interface POSTargetInterface {
    
    public static final String CONTROL_SELECT_PERIOD_TYPE_NAME="period_type_name";
    public static final String CONTROL_SELECT_YEAR_NAME="year_name";
    public static final String CONTROL_SELECT_DATE_TYPE_NAME="date_type_name";
    public static final String CONTROL_FILE_POS_FILE_UPLOAD="pos_file_upload";
        public static final String CONTROL_HIDDEN_FILE_ID_UPLOAD="hidden_file_id";
    public static final String ARRAY_OF_EXCEL_FILE_PATHS="file_paths_array";
    public static final String ACTION_UPLOAD_POS_TARGET="upload_pos_target_process";
    public static final String ACTION_MANAGEMENT_POS_TARGET  = "management_pos_target";
    public static final String ACTION_DELETE_FILE_POS_TARGET  = "delete_file_pos_target";
            
    public static final String MAP_YEARS="year_map";
    
    public static final String VECTOR_OF_FILES = "file_vector";
    public static final String COMMISSION_UPLOAD_DIR="/commission/upload";
    
    public static final String CONSTANT_QUARTER_TYPE= "1";
    public static final String CONSTANT_MONTH_TYPE = "2";
    public static final String CONSTANT_WEEK_TYPE= "3";
    public static final int CONSTANT_QUARTER_TYPE_INT= 1;
    public static final int CONSTANT_MONTH_TYPE_INT = 2;
    public static final int CONSTANT_WEEK_TYPE_INT= 3;
    
    public static final String SUCCESS_FILE_NAME = "suc";
    public static final String FAILD_FILE_NAME = "fal";
    
    
}
