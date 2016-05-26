/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.scm.model.POSGroupModel;
import com.mobinil.sds.core.system.scm.model.POSModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author AHMED SAFWAT
 */
public class POSGroupsDAO {

        /*
        * Check if the the  POS is exist , POS Level and ACTIVE
        @return DCM_ID if it is Found, -2 if an exception occurred, -1 if POS not found
        */
    public static int checkPOS(Connection argCon,String posCode) {

            String sqlStatement;

            //UPDATED BY MEDHAT
            sqlStatement="SELECT DCM_ID FROM VW_GEN_DCM_SCM WHERE  DCM_CODE='"+posCode+"'  AND DCM_STATUS_ID=1 AND" +
                    //" DCM_PAYMENT_LEVEL_ID=4 AND" +
                    " CHANNEL_ID=1";
            int posId=(Integer)DBUtil.executeQuerySingleValueInt(sqlStatement, "DCM_ID", argCon);
            return posId;
    }

    public static Vector<POSGroupModel> getAllPOSGroups(Connection con, String rowNum){
        Vector<POSGroupModel> listToReturn=new Vector();
        String sqlStatement="SELECT * FROM (SELECT ROWNUM as row_num,PG.GROUP_ID,PG.GROUP_NAME,PG.DESCRIPTION,PG.GROUP_TYPE_ID,GT.GROUP_TYPE_NAME,PG.CREATED_IN,GU.PERSON_FULL_NAME CREATED_BY"
                            +" FROM SCM_POS_GROUP PG, GEN_PERSON GU, SCM_POS_GROUP_TYPE GT"
                            +" WHERE PG.GROUP_TYPE_ID=GT.GROUP_TYPE_ID AND PG.CREATED_BY=GU.PERSON_ID"
                            +" ) WHERE row_num > = ('"+rowNum+"'*20)+1 AND row_num < = ('"+rowNum+"'+1)*20  ORDER BY ROWNUM ";
        listToReturn=DBUtil.executeSqlQueryMultiValue(sqlStatement, POSGroupModel.class, con);
        return listToReturn;
    }

    public static int getGroupPOSNo(Connection con,int groupId){
        String sqlStatement;
        int noOfPOSs=0;
        sqlStatement="SELECT COUNT(GROUP_ID) COUNT FROM SCM_POS_ASSIGNED_GROUP WHERE GROUP_ID="+groupId;
        noOfPOSs=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        return noOfPOSs;
    }

    public static void insertNewGroup(Connection con,POSGroupModel posGroup,String userId){
        String sqlStatement;
        long seqVal=DBUtil.getSequenceNextVal(con, "SEQ_SCM_POS_GROUP");
        sqlStatement="INSERT INTO SCM_POS_GROUP VALUES(?,?,?,sysdate,?,?)";
        DBUtil.executePreparedStatment(sqlStatement, con, new Object[]{seqVal,posGroup.getGroupName(),posGroup.getDescription(),userId,posGroup.getGroupTypeId()});

    }

    public static void updatePOSGroup(Connection con,POSGroupModel posGroup,String userId){
        String sqlStatement;
        sqlStatement="UPDATE SDS.SCM_POS_GROUP SET GROUP_NAME=?, DESCRIPTION=?, CREATED_IN=sysdate, CREATED_BY=?, GROUP_TYPE_ID=? WHERE GROUP_ID=?";
        DBUtil.executePreparedStatment(sqlStatement, con, new Object[]{posGroup.getGroupName(),posGroup.getDescription(),userId,posGroup.getGroupTypeId(),posGroup.getGroupId()});

    }

    public static POSGroupModel getPOSGroup(Connection con, int groupId){

        String sqlStatement="SELECT GROUP_ID, GROUP_NAME, GROUP_TYPE_ID, DESCRIPTION, CREATED_IN, CREATED_BY FROM SCM_POS_GROUP WHERE GROUP_ID="+groupId;
        POSGroupModel posGroup=(POSGroupModel)DBUtil.executeSqlQuerySingleValue(sqlStatement, POSGroupModel.class,"fillPOSGroup", con);

        return posGroup;
    }
    
    public static void deleteGroup(Connection con,int groupId){

        String deleteGroupAssignedPOSSqlStatement="DELETE SCM_POS_ASSIGNED_GROUP WHERE GROUP_ID="+groupId;
        DBUtil.executeSQL(deleteGroupAssignedPOSSqlStatement);
        
        String deleteGroupSqlStatement="DELETE SCM_POS_GROUP WHERE GROUP_ID="+groupId;
        DBUtil.executeSQL(deleteGroupSqlStatement);

    }

    public static Vector<POSModel> getGroupAssignedPOSs(Connection con, int groupId){
        String sqlStatement="SELECT G.DCM_ID,G.DCM_CODE,G.DCM_NAME FROM GEN_DCM G,SCM_POS_ASSIGNED_GROUP A " +
                "WHERE A.DCM_ID=G.DCM_ID AND A.GROUP_ID="+groupId;
        Vector<POSModel> groupAssignedPOSs=new Vector();
        groupAssignedPOSs=DBUtil.executeSqlQueryMultiValue(sqlStatement, POSModel.class,con);
        return groupAssignedPOSs;
    }

    public static void unAssignPOSFromGroup(Connection con,int groupId,int posId ){
        String sqlStatement="DELETE SCM_POS_ASSIGNED_GROUP WHERE DCM_ID=? AND GROUP_ID=?";
        DBUtil.executePreparedStatment(sqlStatement, con, new Object[]{posId,groupId});
    }

    public static void assingPOSToGroup(Connection con,int groupId,int posId){
        String sqlStatement="INSERT INTO SCM_POS_ASSIGNED_GROUP VALUES(?,?)";
        DBUtil.executePreparedStatment(sqlStatement, con, new Object[]{groupId,posId});
    }

    /*
     * Used to truncate SCM_POS_GROUP_TEMP
     * @return 1 if the table is truncated , -2 if an exception occured
     */
    public static void truncatePOSGroupTempTable(Connection argCon) {

                String sqlStatement;
                sqlStatement="TRUNCATE TABLE SCM_POS_GROUP_TEMP";
                DBUtil.executeSQL(sqlStatement, argCon);
        }

    public static Vector<POSModel> getPOSImported(Connection argCon) {

            String sqlStatement;
            sqlStatement="SELECT POS_CODE AS DCM_CODE FROM SCM_POS_GROUP_TEMP";
            Vector<POSModel> posImported=new Vector();
            posImported= DBUtil.executeSqlQueryMultiValue(sqlStatement, POSModel.class, argCon);
            return posImported;
    }
    public static boolean checkPOSAlreadyExistInTheGroup(Connection con,int groupId,int posId){
    String sqlStatement;
    sqlStatement="SELECT DCM_ID FROM SCM_POS_ASSIGNED_GROUP WHERE DCM_ID="+posId+" AND GROUP_ID="+groupId;
    int dcmId=0;
    dcmId=DBUtil.executeQuerySingleValueInt(sqlStatement, "DCM_ID", con);
    if(dcmId<0)
        return false;
    else
        return true;
    }

    public static String getPOSGroupsTotalPages(Connection con){
        String sqlStatement;
        sqlStatement="SELECT CEIL(COUNT(*)/20) COUNT FROM (SELECT PG.GROUP_ID,PG.GROUP_NAME,PG.DESCRIPTION,PG.GROUP_TYPE_ID,GT.GROUP_TYPE_NAME,PG.CREATED_IN,GU.PERSON_FULL_NAME CREATED_BY"
                    +" FROM SCM_POS_GROUP PG, GEN_PERSON GU, SCM_POS_GROUP_TYPE GT"
                    +" WHERE PG.GROUP_TYPE_ID=GT.GROUP_TYPE_ID AND PG.CREATED_BY=GU.PERSON_ID)";
       String count="0";
       count=DBUtil.executeQuerySingleValueString(sqlStatement, "COUNT", con);
       return count;

    }

}
