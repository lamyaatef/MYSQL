package com.mobinil.sds.web.controller;

import com.mobinil.sds.core.utilities.parser.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import com.mobinil.sds.core.utilities.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mobinil.sds.web.interfaces.InterfaceKey;
import java.sql.Connection;

public class ActionMapper
{
  private ActionMapper()
  {
  }

  private static HashMap<String, ActionMapperModel> actionTable = new HashMap<String, ActionMapperModel>();

  private static boolean dataLoaded = false;

  private static final boolean  debugFlag = true;

  private static void debug(String s)
  {
    if (debugFlag)
    Utility.logger.debug(s);
  }

  public static ActionMapperModel getActionModel(String actionName)
  {
    System.out.println("In Action Mapper ");
    System.out.println("In Action Mapper - action name:"+actionName);
    if (!dataLoaded)
    {
        Connection con =null;
            try {
                 con = Utility.getConnection();
                 myActionMapperModel = new ActionMapperModel();
                 System.out.println("In Action Mapper ");
                 String sql = "select * from DEVELOPER_ACTION_MAP_SDS_TEST  where action_name is not null";
                 
                 Vector<ActionMapperModel> actionVec = DBUtil.executeSqlQueryMultiValue(sql,ActionMapperModel.class,con);
                 
                 fillActionHT(actionVec);


            } catch (SQLException ex) {
            	ex.printStackTrace();
                Logger.getLogger(ActionMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                if (con != null)
                Utility.closeConnection(con);
            }
    }
    dataLoaded =true;
 
    ActionMapperModel actionModel  = actionTable.get(actionName);

    return actionModel;

  }

  public static void resetActionTable()
  {
    dataLoaded = false;
    actionTable.clear();
  }



  static ActionMapperModel  myActionMapperModel =null;

  private static void fillActionHT(Vector<ActionMapperModel> actionVec) {
        actionTable.clear();
        for (int i = 0; i < actionVec.size(); i++) {
            ActionMapperModel actionModel = actionVec.get(i);
            //actionTable.put(actionModel.getAction_Name().toLowerCase(), actionModel);
            actionTable.put(actionModel.getAction_Name(), actionModel);//Mahmoud
        }
    }

}