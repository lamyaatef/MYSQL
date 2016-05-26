/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobinil.sds.core.system.scm.dao;

import com.mobinil.sds.core.system.scm.model.GroupTypeModel;
import com.mobinil.sds.core.system.scm.model.POSGroupModel;
import com.mobinil.sds.core.utilities.DBUtil;
import java.sql.Connection;
import java.util.Vector;

/**
 *
 * @author AHMED SAFWAT
 */
public class RepGroupDAO {

    public static Vector<GroupTypeModel> getGroupTypes(Connection con){

        String sqlStatement;
        sqlStatement="SELECT GROUP_TYPE_ID,GROUP_TYPE_NAME,GROUP_TYPE_DESC FROM SCM_POS_GROUP_TYPE ORDER BY GROUP_TYPE_NAME";
        Vector <GroupTypeModel> groupTypes=new Vector();
        groupTypes=DBUtil.executeSqlQueryMultiValue(sqlStatement, GroupTypeModel.class, con);

        return groupTypes;

    }

    public static Vector<POSGroupModel>getPOSGroupsByGroupType(Connection con,String groupTypeId){

        Vector<POSGroupModel> posGroups=new Vector();
        String sqlStatement;
        sqlStatement="SELECT GROUP_ID,GROUP_NAME FROM SCM_POS_GROUP WHERE GROUP_TYPE_ID="+groupTypeId+" ORDER BY GROUP_NAME";
        posGroups= DBUtil.executeSqlQueryMultiValue(sqlStatement, POSGroupModel.class,"fillForRepGroupAssign", con);

        return posGroups;
    }

    public static void assignPOSGroup(Connection con,String groupId,String repId,String userId){

        String sqlStatement;
        sqlStatement="INSERT INTO SCM_REP_POS_GROUP VALUES(?,?,sysdate,?)";
        DBUtil.executePreparedStatment(sqlStatement, con, new Object[]{groupId,repId,userId});
        
    }
    public static void unassigPOSGroup(Connection con,String repId,String groupId){
        String sqlStatement;
        sqlStatement="DELETE FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+repId+" AND GROUP_ID="+groupId;
        DBUtil.executeSQL(sqlStatement);
    }
    public static boolean checkIFRepAlreadyHasAnAssignedPOSGroup(Connection con,String repId){
        String sqlStatement;
        int checkFlag=-1;
        sqlStatement="SELECT COUNT(GROUP_ID) COUNT FROM SCM_REP_POS_GROUP WHERE DCM_USER_ID="+repId;
        checkFlag=DBUtil.executeQuerySingleValueInt(sqlStatement, "COUNT", con);
        if(checkFlag>0)
            return true;
        else
            return false;
    }

}
