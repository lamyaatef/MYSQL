package com.mobinil.sds.core.system.sa.roles.dao;

/**
 * RoleDAO Class accesses database to retreive or update more than one 
 * role. All methods are static.
 * 
 * 1- Gets a list of all roles as a Vector of RoleDTO. Each RoleDTO holds
 *    the data of one role as a RoleModel and its privileges as a Vector of 
 *    RolePrivilegeModel.
 *    
 * 2- Gets a HashMap holding two Vectors. The first Vector holds all available
 *    role Statuses as RoleStatusModels. The second Vector holds a list of all 
 *    modules as a Vector of ModuleDTO.
 *    
 * 3- Gets a list of all available role statuses as a Vector of
 *    RoleStatusModels.
 *    
 * 4- Retrives the status of the provided status id.
 * 
 * 5- Checks if this role is assigned to a user. This check is important before
 *    deleteing a role.
 *    
 * 6- Retrieves all role privileges as a Vector of RolePrivilegeModel.
 * 
 * 7- Deletes all privileges assigned to the role with the provided id. And 
 *    return a boolean indecating whether the deletion was successful or not.
 * 
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import com.mobinil.sds.core.system.sa.modules.dao.ModuleDAO;
import com.mobinil.sds.core.system.sa.roles.dto.RoleDTO;
import com.mobinil.sds.core.system.sa.roles.model.RoleModel;
import com.mobinil.sds.core.system.sa.roles.model.RolePrivilegeModel;
import com.mobinil.sds.core.system.sa.roles.model.RoleStatusModel;
import com.mobinil.sds.core.utilities.DBUtil;
import com.mobinil.sds.web.interfaces.sa.RoleInterfaceKey;

public class RoleDAO 
{
	/**
	 * 1- Gets a list of all roles as a Vector of RoleDTO. Each RoleDTO holds
	 *    the data of one role as a RoleModel and its privileges as a Vector of 
	 *    RolePrivilegeModel.
	 * 
	 * @param	Connection argCon
	 * @return Vector
	 * @throws  
	 * @see    
	 */	

	public static Vector <RoleDTO> getRolesList(Connection con)
	{
		String sql = "select * from VW_ADM_ROLE where ROLE_STATUS_NAME in('Active','Not Active')"+
		" and ROLE_NAME not in('Administrator') order by ROLE_NAME" ;
		Vector<RoleModel> vecRolesList=   DBUtil.executeSqlQueryMultiValue(sql, RoleModel.class, con);
		Vector<RoleDTO> roleDTOVec = new Vector<RoleDTO>();
		for (RoleModel role :vecRolesList)
		{
			RoleDTO newRoleDTO = new RoleDTO();
			newRoleDTO.setRoleModel(role);
			newRoleDTO.setRolePrivileges(getRolePrivileges(con, role.getRoleID()));
			roleDTOVec.addElement(newRoleDTO);
		}
		return roleDTOVec;
	}

	/**
	 * 2- Gets a HashMap holding two Vectors. The first Vector holds all available
	 *    role Statuses as RoleStatusModels. The second Vector holds a list of all 
	 *    modules as a Vector of ModuleDTO.
	 * 
	 * @param	Connection argCon
	 * @return HashMap
	 * @throws  
	 * @see    
	 */	

	public static HashMap<String,Object> getAdditionalData(Connection argCon)
	{
		HashMap<String,Object> colRolesData = new HashMap<String,Object>();
		colRolesData.put(RoleInterfaceKey.HASHMAP_KEY_ROLE_STATUSES_LIST, RoleDAO.getRoleStatuses(argCon));
		colRolesData.put(RoleInterfaceKey.HASHMAP_KEY_MODULES_LIST, ModuleDAO.getModulesList(argCon));
		return colRolesData;
	}

	/**
	 * 3- Gets a list of all available role statuses as a Vector of
	 *    RoleStatusModels.
	 * 
	 * @param	Connection argCon
	 * @return Vector
	 * @throws  
	 * @see    
	 */	

	public static Vector<RoleStatusModel> getRoleStatuses(Connection con)
	{

		String sql = "select * from VW_ADM_ROLE_STATUS order by ROLE_STATUS_NAME" ;

		Vector <RoleStatusModel> vecRoleStatusesList = DBUtil.executeSqlQueryMultiValue(sql, RoleStatusModel.class , con);
		return vecRoleStatusesList;
	}

	/**
	 * 4- Retrives the status of the provided status id.
	 * 
	 * @param	Connection argCon, int argStatusID
	 * @return String
	 * @throws  
	 * @see    
	 */	

	public static String getRoleStatusName(Connection con, int argStatusID)
	{		
		String sql = "select ROLE_STATUS_NAME from VW_ADM_ROLE_STATUS where ROLE_STATUS_ID = "+ argStatusID ;
		return DBUtil.executeQuerySingleValueString(sql, "ROLE_STATUS_NAME", con);
	}

	/**
	 * 5- Checks if this role is assigned to a user. This check is important 
	 *    before deleteing a role. Return a boolean indecating whether this role 
	 *    has users assigned to it or not.
	 * 
	 * @param	Connection argCon, int argRoleID
	 * @return boolean
	 * @throws  
	 * @see    
	 */	

	public static boolean hasUsers(Connection con, int argRoleID)
	{    
		String strRoleUsersListQuery = "select USER_ID from VW_ADM_USER_ROLE";
		if(argRoleID != 0)
		{
			strRoleUsersListQuery += " where ROLE_ID = "+argRoleID;
		}
		boolean hasUsers = DBUtil.executeSQLExistCheck(strRoleUsersListQuery, con);

		return hasUsers;
	}

	/**
	 * 6- Retrieves all role privileges as a Vector of RolePrivilegeModel.
	 * 
	 * @param	Connection argCon, int argRoleID
	 * @return Vector
	 * @throws  
	 * @see    
	 */	

	public static Vector<RolePrivilegeModel> getRolePrivileges(Connection con, int argRoleID)
	{
		String sql = "select * from VW_ADM_ROLE_MODULE_PRIVILAGE";
		if(argRoleID != 0)
		{
			sql += " where ROLE_ID = "+argRoleID;
		}      
		Vector<RolePrivilegeModel>   vecRolePrivilegesList = DBUtil.executeSqlQueryMultiValue(sql, RolePrivilegeModel.class, con);
		return vecRolePrivilegesList;
	}

	/**
	 * 7- Deletes all privileges assigned to the role with the provided id. And 
	 *    return a boolean indecating whether the deletion was successful or not.
	 * 
	 * @param	Connection argCon, int argRoleID
	 * @return boolean
	 * @throws  
	 * @see    
	 */	

	public static void deleteRolePrivileges(Connection con, int argRoleID)
	{
		String sql = "delete from VW_ADM_ROLE_MODULE_PRIVILAGE";
		if(argRoleID != 0)
		{
			sql += " where ROLE_ID = "+argRoleID;
		}
		DBUtil.executeSQL(sql, con);

	}

	public static void insertAdmRolePrivilage(Connection con,int roleId,int privilageId)
	{
		String strSql = "insert into ADM_ROLE_PRIVILAGE (ROLE_ID,PRIVILAGE_ID)  values("+roleId+","+privilageId+") ";
		DBUtil.executeSQL(strSql, con);	  
	}

	public static RoleModel getAdmRole(Connection con,String roleId)
	{        
		String strSql = "select * from VW_ADM_ROLE where ROLE_ID = "+roleId;
		RoleModel roleModel= DBUtil.executeSqlQuerySingleValue(strSql, RoleModel.class, con);       
		return roleModel;
	}

	public static void deleteAdmRole(Connection con,int roleId)
	{
		String strSql = "delete from ADM_ROLE where ROLE_ID = "+roleId;
		DBUtil.executeSQL(strSql, con);	  
	}

	public static void updateAdmRole(Connection con,String roleId,String roleName,String roleDesc,String roleStatusId)
	{
		String strSql = "update ADM_ROLE set ROLE_NAME = '"+roleName+"'  ,ROLE_DESC = '"+roleDesc+"' ,STATUS_ID = "+roleStatusId+"  where ROLE_ID = "+roleId;
		DBUtil.executeSQL(strSql, con);	    
	}

	public static void insertAdmRole(Connection con,String roleId,String roleName,String roleDesc,String roleStatusId)
	{
		String strSql = "insert into ADM_ROLE(ROLE_ID,ROLE_NAME,ROLE_DESC,STATUS_ID) "+
		" values("+roleId+",'"+roleName+"','"+roleDesc+"',"+roleStatusId+") ";
		DBUtil.executeSQL(strSql,con);	  
	}
}