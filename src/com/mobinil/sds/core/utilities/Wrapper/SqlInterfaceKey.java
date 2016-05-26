/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.utilities.Wrapper;

/**
 *
 * @author Medhat Osama
 */
public class SqlInterfaceKey {

    public static  String SQL_OUTAGE_CASE="select * from (select OUTAGE_NAME,OUTAGE_desc,OUTAGE_status_name,OUTAGE_start_date,ser_outage_case.OUTAGE_STATUS_ID,rownum r from ser_outage_case,SER_OUTAGE_STATUS where ser_outage_case.outage_status_id=SER_OUTAGE_STATUS.outage_status_id) ";

    
    public static  String SQL_USER="SELECT * from (select USER_FUNAME,USER_LOGIN_NAME,user_type_name,LOOKUP_ITEM_VALUE,USER_STATUS_ID,gen_user_type.USER_TYPE_ID,rownum r "+
                                  "FROM gen_user, gen_lookup, gen_user_type WHERE "+
                                  "gen_user.user_status_id = gen_lookup.LOOKUP_ITEM_ID  AND "+
                                  "gen_user.user_type_id = gen_user_type.user_type_id      AND "+
                                  "LOWER(gen_lookup.LOOKUP_ITEM_VALUE ) IN ('active', 'inactive') and "+
                                  "gen_lookup.LOOKUP_ITEM_TYPE='gena_status' order by user_id) ";

    
    
    public static  String SQL_SERVICE="select * from (select service_name,service_desc,LOOKUP_ITEM_VALUE,SERVICE_STATUS,rownum r "+

                                      " from ser_service,gen_lookup"+

                                       " where service_status=gen_lookup.LOOKUP_ITEM_ID) ";



    public static  String SQL_VENDOR="select * from (select vendor_name,vendor_desc,LOOKUP_ITEM_VALUE,vendor_status_id,rownum r "+

                                           " from gen_vendor,gen_lookup "+

                                            " where VENDOR_STATUS_ID=gen_lookup.LOOKUP_ITEM_ID) ";

    public static  String SQL_OUTAGE_STATUS="SELECT * from (select OUTAGE_STATUS_NAME,OUTAGE_STATUS_DESC,rownum r FROM SER_OUTAGE_STATUS) ";


    public static  String SQL_OUATGE_STATUS_DETAIL="select * from (select out_status_details_name,out_status_details_desc,outage_status_NAME,subsystem_NAME,ser_outage_status_detail.OUTAGE_STATUS_ID,ser_outage_status_detail.SUBSYSTEM_ID,rownum r "+

                                                        "from ser_outage_status_detail,SER_OUTAGE_STATUS,SER_SUBSYSTEM "+

                                                        "WHERE ser_outage_status_detail.OUTAGE_STATUS_ID=SER_OUTAGE_STATUS.OUTAGE_STATUS_ID "+

                                                        "AND ser_outage_status_detail.SUBSYSTEM_ID=SER_SUBSYSTEM.SUBSYSTEM_ID ) ";


    public static  String  SQL_OUATGE_TYPE="select * from (select outage_type_name,outage_type_desc,rownum r from ser_outage_type) ";

    public static  String  SQL_OUATGE_PRIORITY="select * from (select outage_priority_name,outage_priority_desc,rownum r from ser_outage_priority) ";

    public static  String  SQL_SERVICE_LOSS_TYPE="select * from (select service_loss_type_name,service_loss_type_desc,rownum r from ser_service_loss_type) ";

    public static  String  SQL_QUALITY_DEGRADATION="select * from (select quality_degradation_name,quality_degradation_desc,rownum r from ser_quality_degradation) ";

    public static  String  SQL_REVENUE_LOSS="select * from (select revenue_loss_name,revenue_loss_desc,rownum r from ser_revenue_loss) ";

    public static  String  SQL_ROLLOUT_PROGRESS="select * from (select rollout_progress_name,rollout_progress_desc,rownum r from ser_rollout_progress) ";

    public static  String  SQL_MODULE="SELECT * from (select module_name,module_desc, lookup_item_value,module_status_id,rownum r "+
                                      "FROM adm_module JOIN gen_lookup ON module_status_id = LOOKUP_ITEM_ID WHERE "+
                                        "LOWER(LOOKUP_ITEM_VALUE) in('active','inactive') and "+
                                        "gen_lookup.LOOKUP_ITEM_TYPE = 'gena_status' ORDER BY module_name)";

    public static  String  SQL_ROLE="SELECT * from (select ROLE_NAME, ROLE_DESC,LOOKUP_ITEM_value,ROLE_STATUS_ID,rownum r "+
                                    "FROM ADM_ROLE, GEN_LOOKUP, GEN_SUBSIDIARY WHERE "+
                                    "ADM_ROLE.ROLE_STATUS_ID =  GEN_LOOKUP.LOOKUP_ITEM_ID              AND "+
                                    "GEN_LOOKUP.LOOKUP_ITEM_TYPE='gena_status'                       AND "+
                                    "ADM_ROLE.ROLE_SUBSIDIARY_ID = GEN_SUBSIDIARY.SUBSIDIARY_ID          AND "+
                                    "GEN_SUBSIDIARY.SUBSIDIARY_STATUS_ID =  GEN_LOOKUP.LOOKUP_ITEM_ID  AND "+
                                    "LOWER(GEN_LOOKUP.LOOKUP_ITEM_VALUE) = 'active' ORDER BY ROLE_NAME) ";

    public static  String  SQL_USER_TYPES="SELECT * from (select user_type_name,user_type_desc,lookup_item_value,USER_TYPE_STATUS_ID,rownum r "+
                                            "FROM gen_user_type , gen_lookup WHERE "+
                                            "user_type_id = NVL(null, user_type_id) AND "+
                                            "user_type_status_id = gen_lookup.LOOKUP_ITEM_ID AND "+
                                            "LOWER(gen_lookup.LOOKUP_ITEM_VALUE ) IN ('active', 'inactive') and "+
                                            "gen_lookup.LOOKUP_ITEM_TYPE='gena_status' ORDER BY user_type_name) ";

    public static  String SQL_SUBSIDIARY="select * from (select SUBSIDIARY_NAME,SUBSIDIARY_desc,SUBSIDIARY_ADDRESS,LOOKUP_ITEM_value,SUBSIDIARY_STATUS_ID,rownum r "+
                                        "FROM gen_subsidiary, gen_lookup WHERE "+
                                        "gen_lookup.LOOKUP_ITEM_ID = gen_subsidiary.SUBSIDIARY_STATUS_ID  AND "+
                                        "gen_subsidiary.SUBSIDIARY_ID <> 0 AND "+
                                        "gen_subsidiary.SUBSIDIARY_ID = NVL(null, gen_subsidiary.SUBSIDIARY_ID) AND "+
                                        "LOWER(gen_lookup.LOOKUP_ITEM_VALUE) IN ('active', 'inactive') and "+
                                        "gen_lookup.LOOKUP_ITEM_TYPE='gena_status'  ORDER BY subsidiary_id,subsidiary_name ) ";

    public static  String SQL_KPI="SELECT  * from (select KPI_NAME,KPI_CODE,kpi_level_name,t2.LOOKUP_ITEM_VALUE,KPI_LEVEL_ID,KPI_STATUS_ID,rownum r "+
                                  "FROM   kpi_kpi t1 LEFT JOIN gen_lookup t2 ON (t1.KPI_STATUS_ID = t2.LOOKUP_ITEM_ID) "+
                                  "LEFT JOIN gen_lookup t3 ON       (t1.kpi_value_id = t3.LOOKUP_ITEM_ID) "+
                                   "LEFT JOIN  gen_lookup t4 ON (t1.MEASURE_UNIT_ID = t4.LOOKUP_ITEM_ID) "+
                                   "LEFT JOIN kpi_level t5 ON        (t1.KPI_LEVEL_ID = t5.KPI_LEVEL_ID ) "+
                                   "LEFT JOIN  kpi_category t6 ON    (t1.KPI_CATEGORY_ID =  t6.KPI_CATEGORY_ID) "+
                                   "LEFT JOIN  gen_subsidiary t7 ON (t1.SUBSIDIARY_ID = t7.SUBSIDIARY_ID) "+
                                   "LEFT JOIN  gen_lookup t8 ON (t1.DEPARTMENT_ID = t8.LOOKUP_ITEM_ID ) ";

    public static  String SQL_SIMPLE_EQU="select * from (select SIMPLE_EQU_NAME,SIMPLE_EQU_desc,lookup_item_value,t1.SIMPLE_EQU_STATUS_ID,rownum r "+
                                        "from kpi_simple_equation t1 left join gen_lookup t2 on (t1.SIMPLE_EQU_STATUS_ID = t2.LOOKUP_ITEM_ID ) "+
                                        "left join gen_lookup t3 on (t1.SIMPLE_EQU_TYPE_ID = t3.LOOKUP_ITEM_ID) where ";

    public static String SQL_KPI_CATEGORY="select * from (select kpi_category_name,kpi_category_desc,LOOKUP_ITEM_VALUE,rownum r "+
                                          "from kpi_category t1 left join gen_lookup t2 on (t1.status_id = t2.LOOKUP_ITEM_ID) ";

    public static String SQL_RMP_GEN_INPUT="select * from (select RMP_GEN_INPUT_NAME,RMP_GEN_INPUT_desc,lookup_item_value,rownum r from WOR_SUB_GEN_INPUT t1, gen_lookup t2 where "+
                                            "t1.rmp_gen_input_status_id = t2.LOOKUP_ITEM_ID and "+
                                            "t2.LOOKUP_ITEM_TYPE='gena_status' and";
    
    public static String  SQL_ACHIEVEMENT="select * from (SELECT achievement_name, gen_user.USER_FUNAME , gen_subsidiary.subsidiary_name,achievement_week,rownum r FROM "+
                                          "com_achievement t1 left join gen_user on (t1.ACHIEVEMENT_USER_CREATOR = gen_user.USER_ID  ) "+
                                            "left join gen_subsidiary on (t1.ACHIEVEMENT_SUBSIDIARY_ID =  gen_subsidiary.SUBSIDIARY_ID ) "+
                                            "left join gen_lookup t3 on (t1.ACHIEVEMENT_OTH_INTERVENTION =  t3.LOOKUP_ITEM_ID ) "+
                                            "left join gen_lookup t2 on (t1.ACHIEVEMENT_TYPE_ID =  t2.LOOKUP_ITEM_ID ) where ";


    public static String SQL_ISSUE="select * from   (SELECT DISTINCT achievement_name, gen_user.USER_FUNAME , gen_subsidiary.subsidiary_name,achievement_week, rownum r FROM "+
                                           "com_achievement t1 left join gen_user on (t1.ACHIEVEMENT_USER_CREATOR = gen_user.USER_ID  ) "+
                                           "left join gen_subsidiary on (t1.ACHIEVEMENT_SUBSIDIARY_ID =  gen_subsidiary.SUBSIDIARY_ID ) "+
                                           "left join gen_lookup t3  on (t1.ACHIEVEMENT_OTH_INTERVENTION =  t3.LOOKUP_ITEM_ID ) "+
                                           "left join gen_lookup t2 on (t1.ACHIEVEMENT_TYPE_ID =  t2.LOOKUP_ITEM_ID ) WHERE "+
                                           "lower(t2.LOOKUP_ITEM_VALUE )='issue' and "+
                                           "t2.LOOKUP_ITEM_TYPE='com_type' and ";

}
