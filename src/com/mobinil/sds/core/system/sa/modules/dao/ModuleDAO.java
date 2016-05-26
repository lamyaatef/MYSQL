package com.mobinil.sds.core.system.sa.modules.dao;

/**
 * ModuleDAO Class accesses database to retreive or update more than one 
 * module or privilege. All methods are static.
 * 
 * 1- Gets a list of all modules as a Vector of ModuleDTO. Each ModuleDTO holds
 *    the data of one module as a MduleModel and its privileges as a Vector of
 *    PrivilegeModel.
 *    
 * 2- Gets a list of all available module statuses as a Vector of
 *    ModuleStatusModel.
 *    
 * 3- Updates the status of one module and return a boolean indecating if the
 *    update was successfull or not.
 *    There was no need to create a Module CMP because the system does not 
 *    support adding modules and updating a module status was the only method 
 *    that accesses one module in the database.
 *    
 * 4- Retrieves all module privileges as a Vector of PrivilegeModel.
 * 
 * 5- Gets a list of all available privilege statuses as a Vector of
 *    PrivilegeStatusModel.
 * 
 * 6- Updates the status of one privilege and return a boolean indecating if the
 *    update was successfull or not.
 *    There was no need to create a Privilege CMP because the system does not 
 *    support adding privileges and updating a privilege status was the only 
 *    method that accesses one privilege in the database.
 *    
 * @version	1.01 Feb 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import com.mobinil.sds.core.system.sa.modules.dto.*;
import com.mobinil.sds.core.system.sa.modules.model.*;
import com.mobinil.sds.core.system.sa.privileges.model.*;
import com.mobinil.sds.web.interfaces.sa.AdministrationInterfaceKey;
import java.sql.*;

import com.mobinil.sds.core.utilities.Utility;
import java.util.*;
import com.mobinil.sds.core.utilities.CachEngine;

public class ModuleDAO 
{
  /**
   * 1- Gets a list of all modules as a Vector of ModuleDTO. Each ModuleDTO holds
   *    the data of one module as a MduleModel and its privileges as a Vector of
   *    PrivilegeModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getModulesList(Connection argCon)
  {


  Vector<ModuleDTO> vecModulesList = new Vector<ModuleDTO>();  
    try 
    {
      Statement stmtModulesList = argCon.createStatement();
      String strModulesListQuery = "select * from VW_ADM_MODULE"+
                                   " where MODULE_STATUS_NAME in('Active','Not Active')"+
                                   " order by MODULE_NAME";
      ResultSet rstModulesList = stmtModulesList.executeQuery(strModulesListQuery);
      while(rstModulesList.next())
      {
        ModuleModel newModuleModel = new ModuleModel(rstModulesList.getInt(1), 
                                                     rstModulesList.getString(2),
                                                     rstModulesList.getString(3), 
                                                     rstModulesList.getInt(4),
                                                     rstModulesList.getString(5));
        ModuleDTO newModuleDTO = new ModuleDTO();
        newModuleDTO.setModuleModel(newModuleModel);
        newModuleDTO.setModulePrivileges(getModulePrivileges(argCon, newModuleModel.getModuleID()));
        vecModulesList.addElement(newModuleDTO);
      }
      rstModulesList.close();
      stmtModulesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecModulesList;
  }

  /**
   * 2- Gets a list of all available module statuses as a Vector of
   *    ModuleStatusModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getModuleStatuses(Connection argCon)
  {
    long startTime = System.currentTimeMillis();

    Object obj =   CachEngine.getObject(AdministrationInterfaceKey.CACH_OBJ_MODULE_STATUS);
    Vector vecModuleStatusesList =null;

    if (obj ==null)
    {
  
        vecModuleStatusesList= new Vector();   
        try 
        {
          Statement stmtModuleStatusesList = argCon.createStatement();
          String strModuleStatusesListQuery = "select * from VW_ADM_MODULE_STATUS"+
                                              " where MODULE_STATUS_NAME in('Active','Not Active')"+
                                              " order by MODULE_STATUS_NAME";
          ResultSet rstModuleStatusesList = stmtModuleStatusesList.executeQuery(strModuleStatusesListQuery);
          while(rstModuleStatusesList.next())
          {
            ModuleStatusModel newModuleStatusModel = new ModuleStatusModel(rstModuleStatusesList.getInt(1), 
                                                         rstModuleStatusesList.getString(2),
                                                         rstModuleStatusesList.getString(3));
            vecModuleStatusesList.addElement(newModuleStatusModel);
          }
          rstModuleStatusesList.close();
          stmtModuleStatusesList.close();
        } 
        catch (Exception ex) 
        {
          ex.printStackTrace();
        }
        CachEngine.storeObject(AdministrationInterfaceKey.CACH_OBJ_MODULE_STATUS, vecModuleStatusesList); 
    }
    else 
    {
      vecModuleStatusesList = (Vector) obj;
    }
    Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));    
    return vecModuleStatusesList;
  }

  /**
   * 3- Updates the status of one module and return a boolean indecating if the
   *    update was successfull or not.
   * 
   * @param	Connection argCon, int argModuleID, int argStatusID
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean updateModuleStatus(Connection argCon, int argModuleID, int argStatusID)
  {
    boolean updated = false;
    try 
    {
      Statement stmtModuleStatusUpdate = argCon.createStatement();
      String strModuleStatusUpdateQuery = "update VW_ADM_MODULE set MODULE_STATUS_ID = "+
                                            argStatusID+" where MODULE_ID = "+argModuleID;
      if (stmtModuleStatusUpdate.executeUpdate(strModuleStatusUpdateQuery) >=0)
      {
        updated = true;
      }
      stmtModuleStatusUpdate.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return updated;
  }

  /**
   * 4- Retrieves all module privileges as a Vector of PrivilegeModel.
   * 
   * @param	Connection argCon, int argModuleID
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector<PrivilegeModel> getModulePrivileges(Connection argCon, int argModuleID)
  {
    Vector<PrivilegeModel> vecModulePrivilegesList = new Vector<PrivilegeModel>();
    try 
    {
      Statement stmtModulePrivilegesList = argCon.createStatement();
      String strModulePrivilegesListQuery = "select * from VW_ADM_MODULE_PRIVILAGE"+
                                            " where PRIVILAGE_STATUS_NAME in('Active','Not Active')";
      if(argModuleID != 0)
      {
        strModulePrivilegesListQuery += " and MODULE_ID = "+argModuleID;
      }
      ResultSet rstModulePrivilegesList = stmtModulePrivilegesList.executeQuery(strModulePrivilegesListQuery);
      while(rstModulePrivilegesList.next())
      {
        PrivilegeModel newPrivilegeModel = new PrivilegeModel(rstModulePrivilegesList.getInt(1), 
                                                     rstModulePrivilegesList.getInt(5),
                                                     rstModulePrivilegesList.getString(6),
                                                     rstModulePrivilegesList.getString(7),
                                                     rstModulePrivilegesList.getInt(8),
                                                     rstModulePrivilegesList.getString(9));
        newPrivilegeModel.setPrivilegeActionName(rstModulePrivilegesList.getString(10));
        newPrivilegeModel.setPrivilegeTarget(rstModulePrivilegesList.getString(12));
        vecModulePrivilegesList.addElement(newPrivilegeModel);
      }
      rstModulePrivilegesList.close();
      stmtModulePrivilegesList.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return vecModulePrivilegesList;
  }

  /**
   * 5- Gets a list of all available privilege statuses as a Vector of
   *    PrivilegeStatusModel.
   * 
   * @param	Connection argCon
   * @return Vector
   * @throws  
   * @see    
  */	

  public static Vector getPrivilegeStatuses(Connection argCon)
  {
    long startTime = System.currentTimeMillis();

    Object obj =   CachEngine.getObject(AdministrationInterfaceKey.CACH_OBJ_PRIVILAGE_STATUS);
    Vector vecPrivilegeStatusesList =null;

    if (obj ==null)
    {
  
        vecPrivilegeStatusesList= new Vector();   
        try 
        {
          Statement stmtPrivilegeStatusesList = argCon.createStatement();
          String strPrivilegeStatusesListQuery = "select * from VW_ADM_PRIVILAGE_STATUS"+
                                                 " where PRIVILAGE_STATUS_NAME in('Active','Not Active')"+
                                                 " order by PRIVILAGE_STATUS_NAME" ;
          ResultSet rstPrivilegeStatusesList = stmtPrivilegeStatusesList.executeQuery(strPrivilegeStatusesListQuery);
          while(rstPrivilegeStatusesList.next())
          {
            PrivilegeStatusModel newPrivilegeStatusModel = new PrivilegeStatusModel(rstPrivilegeStatusesList.getInt(1), 
                                                         rstPrivilegeStatusesList.getString(2),
                                                         rstPrivilegeStatusesList.getString(3));
            vecPrivilegeStatusesList.addElement(newPrivilegeStatusModel);
          }
          rstPrivilegeStatusesList.close();
          stmtPrivilegeStatusesList.close();
        } 
        catch (Exception ex) 
        {
          ex.printStackTrace();
        }
    CachEngine.storeObject(AdministrationInterfaceKey.CACH_OBJ_PRIVILAGE_STATUS , vecPrivilegeStatusesList); 
    }
    else 
    {
      vecPrivilegeStatusesList = (Vector) obj;
    }
    Utility.logger.debug("took "+( System.currentTimeMillis() - startTime));
    return vecPrivilegeStatusesList;
  }

  /**
   * 6- Updates the status of one privilege and return a boolean indecating if the
   *    update was successfull or not.
   * 
   * @param	Connection argCon, int argPrivilegeID, int argStatusID
   * @return boolean
   * @throws  
   * @see    
  */	

  public static boolean updatePrirvlegeStatus(Connection argCon, int argPrivilegeID, int argStatusID)
  {
    boolean update = false;
    try 
    {
      Statement stmtPrivilegeStatusUpdate = argCon.createStatement();
      String strPrivilegeStatusUpdateQuery = "update VW_ADM_PRIVILAGE_INST set PRIVILAGE_STATUS_ID = " +
                                              argStatusID+" where PRIVILAGE_ID = "+argPrivilegeID;
      if (stmtPrivilegeStatusUpdate.executeUpdate(strPrivilegeStatusUpdateQuery) >=0)
      {
        update = true;
      }
      stmtPrivilegeStatusUpdate.close();
    } 
    catch (Exception ex) 
    {
      ex.printStackTrace();
    }
    return update;
  }

}