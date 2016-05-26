package com.mobinil.sds.core.system.ccm.entity.dao;

import java.sql.*;
import java.util.Vector;
import java.util.Date;

import com.mobinil.sds.core.system.ccm.entity.dto.EntityDto;
import com.mobinil.sds.core.system.ccm.entity.model.EntityAditionalFieldLabelModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityAditionalFieldModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityMandatoryDataModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityOptionalFieldModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityTypesModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityFieldTypeModel;
import com.mobinil.sds.core.system.ccm.entity.model.EntityTypeFieldModel;
import com.mobinil.sds.core.system.ccm.entityproject.model.entityProjectModel;

import com.mobinil.sds.core.system.ccm.entity.model.EntityListOptionModel;

import com.mobinil.sds.core.utilities.*;
import com.mobinil.sds.web.interfaces.ccm.*;

public class ENTITYDao {

	public static Vector getEntityTypes(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "SELECT * FROM CCM_ENTITY_TYPE ";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new EntityTypesModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	
	public static Vector getSpecificEntityTypes(Connection con,String Typeid) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "SELECT * FROM CCM_ENTITY_TYPE where ENTITY_TYPE_ID ='"+Typeid+"' ";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new EntityTypesModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	

	public static Vector getEntityFieldTypes(Connection con) {
		Vector vec = new Vector();
		try {
			Statement stat = con.createStatement();
			String strSql = "select * from CCM_ENTITY_FIELD_TYPE ";
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new EntityFieldTypeModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}

	public static void insertNewEntityField(Connection con, String order_id,
			String entity_type_id,  String entity_field_label,
			String entity_field_type_id, String is_mandatory) {

		Long Row_ID = null;

		try {
			Statement stat = con.createStatement();
			Row_ID = Utility.getSequenceNextVal(con, "SEQ_CCM_ENTITY_FIELD_ID");
          
			String strSql = "insert into ccm_entity_field (ENTITY_FIELD_ID, ORDER_ID, ENTITY_TYPE_ID, SQL_QUERY, ENTITY_FIELD_LABEL, ENTITY_FIELD_TYPE_ID, IS_MANDATORY_ID) "
					+ " values("+Row_ID+"," +order_id+" ,"+entity_type_id+",'a','"+entity_field_label+"',"+entity_field_type_id+", "+is_mandatory+")";
	       System.out.println("the query is " +strSql);
	       System.out.println("the entity  type  iid is" +entity_type_id);
			stat.execute(strSql);		
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		public static Vector getEntityTypeField(Connection con,String entityTypeId){
		
		Vector vec = new Vector();
		
		try {
			Statement stat = con.createStatement();
String strSql = "SELECT * FROM ccm_entity_field,ccm_entity_field_type  where entity_type_id = '"+entityTypeId+"' and ccm_entity_field.entity_field_type_id = ccm_entity_field_type.ENTITY_FIELD_TYPE_ID";
				
			
			System.out.println("The entity field Query isssssss " + strSql);
			ResultSet res = stat.executeQuery(strSql);
			while (res.next()) {
				vec.add(new EntityTypeFieldModel(res));
			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	
	}
	  public static void updateEntityField(Connection con,String entityFieldId,String orderId,String sqlQuery,String entityFieldLabel,String  entity_field_type_id,String entityFieldTypeId,String isMandatory)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	        String strSql = "update CCM_ENTITY_FIELD set "+ 
	         " ENTITY_FIELD_LABEL = '"+entityFieldLabel+"',ENTITY_FIELD_TYPE_ID="+entity_field_type_id+", ORDER_ID = "+orderId+",IS_MANDATORY_ID  = "+isMandatory+" where ENTITY_FIELD_ID = "+entityFieldId+"";  
	      
	     System.out.println("TheQuery isssssss " + strSql);
	     stat.execute(strSql);          
	    }
	    catch(Exception e)
	    {  
	      e.printStackTrace();
	    }    
	  }
	  
	  public static void deleteEntityField(Connection con,String entityFieldId)
	  {
	    try
	    {
	      Statement stat = con.createStatement();
	        String strSql = "delete CCM_ENTITY_FIELD  where ENTITY_FIELD_ID = "+entityFieldId+"";
	        
	      
	     System.out.println("The delete field Query isssssss " + strSql);
	     stat.execute(strSql);          
	    }
	    catch(Exception e)
	    {  
	      e.printStackTrace();
	    }    
	  }
	  
	  
	  
	  public static boolean checkDataExists(Connection con,String entityFieldId)
	  {
		  boolean exist=false;
	      try
	      {
	      
	       Statement stat = con.createStatement();
	       String strSql = "select * from  CCM_ENTITY_DETAIL  where ENTITY_FIELD_ID= '"+entityFieldId+"'" ;
	       System.out.println("checkData exist query is"+strSql);
	       ResultSet res= stat.executeQuery(strSql);
	       while(res.next())
	       {
	    	   exist = true;
	    	   
	       }
	       stat.close();
	  
	      }
	      catch(Exception e)
	      {
	    	  exist=false;
	      e.printStackTrace();
	      }

	  return exist; 
		  
	  }
	  
	  
	  
	  
		public static Vector getEntityTypeFieldByRecordId(Connection con,String recordId){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();
	String strSql = "SELECT * FROM ccm_entity_field,ccm_entity_field_type  where entity_field_id = '"+recordId+"' and ccm_entity_field.entity_field_type_id = ccm_entity_field_type.ENTITY_FIELD_TYPE_ID";
	
					
				
				System.out.println("The entity field Query isssssss " + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityTypeFieldModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		
		}  
	  
	  
	  
		public static Vector getAdditionalEntityFieldLabel(Connection con,String entityTypeID){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();

	      // String strSql = "select entity_field_label from ccm_entity ,ccm_entity_field where ccm_entity.ENTITY_TYPE_ID ="+entityTypeID+"and ccm_entity_field.ENTITY_TYPE_ID ="+entityTypeID+" ";
String strSql = "select distinct(entity_field_label)from ccm_entity,ccm_entity_detail,ccm_entity_field "+
       "where ccm_entity.ENTITY_TYPE_ID ="+entityTypeID+" and ccm_entity_detail.ENTITY_FIELD_ID=ccm_entity_field.ENTITY_FIELD_ID and ccm_entity.ENTITY_ID=ccm_entity_detail.ENTITY_ID  order by ccm_entity_field.ENTITY_FIELD_LABEL asc ";

				
				System.out.println("The  get additional field label Query isssssss " + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityAditionalFieldLabelModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		
		}  
		

		
		  public static Vector getentityByFilter(Connection con,String entityTypeID,String entityName,String entityCode)
		  {
		    Vector vec = new Vector();
		    boolean andFlag = false;
		    try
		    {
		    	
				Statement stat = con.createStatement();
				 String strSql = "select * from ccm_entity";
			  System.out.println("The get entity mandatory Query isssssss " + strSql);
		     
		      //Utility.logger.debug(strSql);

		      if(entityTypeID!=null && entityTypeID.compareTo("")!=0)
		       {
		          if(!andFlag){strSql += " where ";
		          Utility.logger.debug(strSql);}
		          else{strSql += " and ";
		          Utility.logger.debug(strSql);}
		          strSql += "ENTITY_TYPE_ID = '"+entityTypeID+"' ";
		          andFlag = true;
		       }

		      if(entityName!=null && entityName.compareTo("")!=0)
		      {
		        if(!andFlag)
		        {
		          strSql += " where ";
		        }
		        else
		        {
		          strSql += " and ";
		        }
		        strSql += "ENTITY_NAME = '"+entityName+"'";
		        andFlag = true;
		      }
	      
		      if(entityCode != null && entityCode.compareTo("")!=0)
		      {
		        if(!andFlag)
		        {
		          strSql += " where ";
		        }
		        else
		        {
		          strSql += " and ";
		        }
		        strSql += "ENTITY_CODE = '"+entityCode+"'";
		        andFlag = true;
		      }

		    //  strSql += " order by INVOICE_NUMBER,DCM_ID,PAYMENT_DATE";
		      Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityMandatoryDataModel(res));
				}
		      stat.close();
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		    return vec;
		  }
		

		public static Vector getEntityMandatoryData(Connection con,String entityTypeID){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();
             String strSql  = "  select * from ccm_entity where ccm_entity.ENTITY_TYPE_ID="+ entityTypeID+" ";
		        System.out.println("The get entity mandatory Query isssssss " + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityMandatoryDataModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		}
		public static Vector getAllEntities(Connection con){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();
             String strSql  = "  select * from ccm_entity  ";
		        System.out.println("The get all entites" + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityMandatoryDataModel(res));
				}
				res.close();
				stat.close();
				con.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		}
		
		public static Vector getSpecificEntity(Connection con,String entity_id){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();
             String strSql  = "  select * from ccm_entity  where entity_id='"+entity_id+"' ";
		        System.out.println("The entity type id issssssss" + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityMandatoryDataModel(res));
				}
				res.close();
				stat.close();
				con.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		}
			
			public static Vector getEntityMandatoryDataForspecific(Connection con,String entityTypeID,String entity_id){
				
				Vector vec = new Vector();
				
				try {
					Statement stat = con.createStatement();
	             String strSql  = "  select * from ccm_entity where ccm_entity.ENTITY_TYPE_ID="+ entityTypeID+" and ccm_entity.entity_id="+ entity_id+" ";
			        System.out.println("The get entity mandatory Query isssssss " + strSql);
					ResultSet res = stat.executeQuery(strSql);
					while (res.next()) {
						vec.add(new EntityMandatoryDataModel(res));
					}
					res.close();
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return vec;
		
		}  
		
		public static Vector getAdditionalEntityFieldData(Connection con,String entityTypeID){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();

	//String strSql = "select entity_field_label from ccm_entity ,ccm_entity_field where ccm_entity.ENTITY_TYPE_ID ="+entityTypeID+"and ccm_entity_field.ENTITY_TYPE_ID ="+entityTypeID+" ";
String strSql = "select ccm_entity.entity_id,ccm_entity_detail.ENTITY_FIELD_VALUE,ccm_entity_field.entity_field_id,ccm_entity_field.ENTITY_FIELD_LABEL "+ 
"from ccm_entity,ccm_entity_detail,ccm_entity_field "+ 
"where ccm_entity.ENTITY_TYPE_ID ="+entityTypeID+""+ 
"and ccm_entity_detail.ENTITY_FIELD_ID=ccm_entity_field.ENTITY_FIELD_ID "+ 
"and ccm_entity.ENTITY_ID=ccm_entity_detail.ENTITY_ID  order by ccm_entity.entity_id ,ccm_entity_field.ENTITY_FIELD_LABEL asc ";

		
				System.out.println("The  get additional entity data " + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityAditionalFieldModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		
		}  
	  
		public static String insertNewEntity(Connection con,
				String entityName,  String entity_type_id,
				String entityAddress, String entity_code,String user_id,String status) {

			Long Row_ID = null;
			Long EntityId=null;
			Date time= new Date();
			System.out.println(" the dat catched iissssssssssssssss"+time);
			
			
		
            Date  date = new Date();
           String day="01",mon="01",year="1900";
          
           {
           day = date.getDate()+"";
           mon = date.getMonth()+"";
           year = date.getYear()+"";
           }
           

			try {
				Statement stat = con.createStatement();
				Row_ID = Utility.getSequenceNextVal(con, "SEQ_CCM_ENTITY_RECORD_ID");
				EntityId=Utility.getSequenceNextVal(con, "SEQ_CCM_ENTITY_ID");
	          
				String strSql = "insert into ccm_entity(ENTITY_ID, ENTITY_NAME ,ENTITY_TYPE_ID ,ENTITY_ADDRESS , ENTITY_CODE, USER_ID , SYSTEM_DATE,RECORD_ID ,STATUS ) "
						+ " values(" +EntityId+" ,'"+entityName+"',"+entity_type_id+",'"+entityAddress+"','"+entity_code+"', "+user_id+",to_date('" + day + "/" + mon + "/" + year + "','dd/mm/yyyy'),"+Row_ID+","+status+")";
				
		       System.out.println("the query is " +strSql);
		       System.out.println("the entity  type  iid is" +entity_type_id);
				stat.execute(strSql);	
				stat.close();
				return EntityId+"";
			} catch (SQLException e) {
//				e.printStackTrace();
				return e.getMessage();
			}
			
		}  
 
		
		
		public static void updateEntity(Connection con,String entityID,
				String entityName,  String entity_type_id,
				String entityAddress, String entity_code,String user_id,String status)
				{

	
			Date time= new Date();
			System.out.println(" the dat catched iissssssssssssssss"+time);
			
			
		
            Date  date = new Date();
           String day="01",mon="01",year="1900";
          
           {
           day = date.getDate()+"";
           mon = date.getMonth()+"";
           year = date.getYear()+"";
           }
           

			try {
				Statement stat = con.createStatement();
				
	          
			
				
				String strSql = "update CCM_ENTITY set "+ 
		         " ENTITY_NAME = '"+entityName+"',ENTITY_CODE='"+entity_code+"', ENTITY_ADDRESS = '"+entityAddress+"',STATUS = 2 where ENTITY_ID= "+entityID+"";  
				
		       System.out.println("UPDATE QYERYYYYYY ISSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS " +strSql);
		       System.out.println("the entity  type  iid is" +entity_type_id);
		     stat.executeUpdate(strSql);	
	
	
				stat.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}  
 

		public static Vector getEntityOptionalFields(Connection con,String entityTypeID){
			
			Vector vec = new Vector();
			
			try {
				
				Statement stat = con.createStatement();
                String strSql  = "select * from ccm_entity_field,ccm_entity_field_type where entity_type_id="+ entityTypeID+"  and ccm_entity_field.entity_field_type_id=ccm_entity_field_type.entity_field_type_id order by order_id asc";

		        System.out.println("The get entity OPTIONAL" + strSql);
		        
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
			     vec.add(new EntityOptionalFieldModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		
		}  

		public static void insertNewEntityOptionaldetail(Connection con,
				String entity_id,  String entity_field_id,
				String entity_field_value, String status) {

			
			Long EntityDeatilId=null;

			try {
				Statement stat = con.createStatement();
				EntityDeatilId = Utility.getSequenceNextVal(con, "SEQ_CCM_ENTITY_DETAIL_ID");

				String strSql = "INSERT INTO CCM_ENTITY_DETAIL(ENTITY_DETAIL_ID,ENTITY_ID,entity_field_id,entity_field_value,status)  "
					+ " values(" +EntityDeatilId+","+entity_id+","+entity_field_id+",'"+entity_field_value+"',"+status+")";
				
		       System.out.println("the query is " +strSql);
		     
				stat.execute(strSql);		
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		
		
		
		public static void updateEntityOptionaldetail(Connection con,
				String  entity_id,  String entity_field_id,
				String entity_field_value, String status) {

			
			

			try {
				Statement stat = con.createStatement();
				Long EntityDeatilId=null;  
				
				String strSql = "update CCM_ENTITY_DETAIL set "+ 
		         " ENTITY_FIELD_VALUE = '"+entity_field_value+"',STATUS="+status+"  where ENTITY_ID= "+entity_id+" AND ENTITY_FIELD_ID= "+entity_field_id+"";  
				
		       System.out.println("the query is " +strSql);
		     
			int rowsUpdated=stat.executeUpdate(strSql);
			
				
	       if (rowsUpdated==0)		    	   
		       {
		    	   
	    		
	    		EntityDeatilId = Utility.getSequenceNextVal(con, "SEQ_CCM_ENTITY_DETAIL_ID");
	    		
	    	   String strSql2 = "INSERT INTO CCM_ENTITY_DETAIL(ENTITY_DETAIL_ID,ENTITY_ID,entity_field_id,entity_field_value,status)  "
					+ " values(" +EntityDeatilId+","+entity_id+","+entity_field_id+",'"+entity_field_value+"',"+status+")";   
	    	   stat.execute(strSql2);
		       }

				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  
	
		public static Vector getAdditionalEntityFieldDataForspecificEntity(Connection con,String entityTypeID,String entityId){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();

String strSql ="select ccm_entity.entity_id,ccm_entity_detail.ENTITY_FIELD_VALUE,ccm_entity_field.entity_field_id,ccm_entity_field.ENTITY_FIELD_LABEL from ccm_entity,ccm_entity_detail,ccm_entity_field "+
"where ccm_entity.ENTITY_TYPE_ID ="+entityTypeID+" and "+
"ccm_entity_detail.ENTITY_FIELD_ID=ccm_entity_field.ENTITY_FIELD_ID and "+
"ccm_entity.ENTITY_ID=ccm_entity_detail.ENTITY_ID and ccm_entity_detail.ENTITY_ID="+entityId+" "+  
"order by ccm_entity.entity_id ,ccm_entity_field.ENTITY_FIELD_ID asc" ;

				System.out.println("The  get additional entity data " + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityAditionalFieldModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;
		
		}  
		
		
		 
		public static Vector getAdditionalEntityFieldLabelForSpecific(Connection con,String entityTypeID,String entityId){
			
			Vector vec = new Vector();
			
			try {
				Statement stat = con.createStatement();

	      // String strSql = "select entity_field_label from ccm_entity ,ccm_entity_field where ccm_entity.ENTITY_TYPE_ID ="+entityTypeID+"and ccm_entity_field.ENTITY_TYPE_ID ="+entityTypeID+" ";


String strSql="select entity_field_label from ccm_entity_detail,ccm_entity_field where ccm_entity_field.ENTITY_TYPE_ID ="+entityTypeID+" and ccm_entity_detail.ENTITY_FIELD_ID=ccm_entity_field.ENTITY_FIELD_ID and ccm_entity_detail.ENTITY_ID ="+entityId+" order by ccm_entity_field.ENTITY_FIELD_ID asc ";
	
	
	
				System.out.println("The  get additional field label Query isssssss " + strSql);
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityAditionalFieldLabelModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;		
			
		}  
		

		
		 public static Vector getChildEntites(Connection con,String id) 
		  {
		    Vector vector = new Vector();
		    System.out.println(" here in getChildEntites id is "+id);
		    
		 
		    entityProjectModel entityProjectModel = null;
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select distinct   (entity_ID)  FROM  VW_PROJECT_ENTITY_SERVICE   where parent_id='"+id+"'";
		      System.out.println("child entities issssssssssssss"+strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while(res.next())
		      {
		    	  entityProjectModel = new entityProjectModel();
		    	  System.out.println("EntityID in getChild is:"+res.getString("entity_ID"));
		        vector.addElement(getEntityDto(con,res.getString("entity_ID"),id));
		        
		      }
	
		 
		   res.close();
		 
		   stat.close();
		  
		    }
		    catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return vector;
		  }
		 public static Vector getChildEntites(Connection con,String id,String projectId) 
		  {
		    Vector vector = new Vector();
		    System.out.println(" here in getChildEntites id is "+id);
		    
		 
		    entityProjectModel entityProjectModel = null;
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select distinct   (entity_ID)  FROM  VW_PROJECT_ENTITY_SERVICE   where parent_id='"+id+"' and project_id ='"+projectId+"'";
		      System.out.println("child entities issssssssssssss"+strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while(res.next())
		      {
		    	  entityProjectModel = new entityProjectModel();
		    	  System.out.println("EntityID in getChild is:"+res.getString("entity_ID"));
		        vector.addElement(getEntityDto(con,res.getString("entity_ID"),projectId));
		        
		      }
	
		 
		   res.close();
		 
		   stat.close();
		  
		    }
		    catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return vector;
		  }
		 
		 
		 public static Vector getChildEntitesLevel1(Connection con,String id)
		  {
		    Vector vector = new Vector();
		    entityProjectModel entityProjectModel = null;
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select distinct (entity_ID)  FROM  VW_PROJECT_ENTITY_SERVICE   where  project_ID ='"+id+"' ";
		      System.out.println("child entities issssssssssssss"+strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while(res.next())
		      {
		    	  entityProjectModel = new entityProjectModel();
		    	  System.out.println("EntityID in getChild is:"+res.getString("entity_ID"));
		        vector.addElement(getEntityDto(con,res.getString("entity_ID"),res.getString("project_ID")));
		        
		      }
	
		 
		   res.close();
		   con.commit();
		   stat.close();
		  
		    }
		    catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return vector;
		  }
		 
			
		 public static Vector getChildEntitesforproject(Connection con,String id)
		  {
		    Vector vector = new Vector();
		    entityProjectModel entityProjectModel = null;
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select distinct (entity_ID)  FROM  VW_PROJECT_ENTITY_SERVICE   where project_ID ='"+id+"'";
		      System.out.println("child entities for project issssssssssssss"+strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while(res.next())
		      {
		    	  entityProjectModel = new entityProjectModel();
		    	  System.out.println("EntityID in getChild is:"+res.getString("entity_ID"));
		        vector.addElement(getEntityDto(con,res.getString("entity_ID"),id));
		        
		      }
	
		 
		   res.close();
		   con.commit();
		   stat.close();
		  
		    }
		    catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return vector;
		  }
		 
		 public static Vector getChildEntitesforparent(Connection con,String id)
		  {
		    Vector vector = new Vector();
		    entityProjectModel entityProjectModel = null;
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select distinct (entity_ID)  FROM  VW_PROJECT_ENTITY_SERVICE   where parent_ID ='"+id+"'";
		      System.out.println("child entities for project issssssssssssss"+strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      while(res.next())
		      {
		    	  entityProjectModel = new entityProjectModel();
		    	//  System.out.println("EntityID in getChild is:"+res.getString("entity_ID"));
		        vector.addElement(getEntityDto(con,res.getString("entity_ID"),id));
		        
		      }
	
		 
		   res.close();
		   con.commit();
		   stat.close();
		  
		    }
		    catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return vector;
		  }

		  public static entityProjectModel getentityProjectModel(Connection con,String id,String projectID)
		  {
			  entityProjectModel entityModel = null;
			  System.out.println("mahmoooooooood issssssss entityId:"+id);
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select ENTITY_NAME,PARENT_ID from VW_PROJECT_ENTITY_SERVICE where ENTITY_ID = '"+id+"' and project_id='"+projectID+"'";
		      Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  entityModel = new entityProjectModel();
		    	  entityModel.setEntityId(id);
		    	  System.out.println("Function ID:"+id);
		    	  entityModel.setEntityName(res.getString("ENTITY_NAME"));
		    	  entityModel.setParentId(res.getString("PARENT_ID"));
		      }
		      res.close();
		      stat.close();
		    }
		     catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return entityModel;
		  }

		  public static entityProjectModel getentityProjectModel(Connection con,String id)
		  {
			  entityProjectModel entityModel = null;
			 
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select ENTITY_NAME,PARENT_ID from VW_PROJECT_ENTITY_SERVICE where ENTITY_ID = '"+id+"'";
		      Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		      if (res.next())
		      {
		    	  entityModel = new entityProjectModel();
		    	  entityModel.setEntityId(id);
		    	  System.out.println("Function ID:"+id);
		    	  entityModel.setEntityName(res.getString("ENTITY_NAME"));
		    	  entityModel.setParentId(res.getString("PARENT_ID"));
		      }
		      res.close();
		      stat.close();
		    }
		     catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return entityModel;
		  }
		  
		
		  
		  
		  
		  public static  EntityDto getEntityDto(Connection con,String id)
		  {
			  EntityDto entity = new EntityDto();
			  entity.setModel(getentityProjectModel(con,id));
			  System.out.println("getentityProjectModel(con,id) id is"+id);
			  entity.setChildEntities(getChildEntites(con,id));
			  System.out.println("getChildEntites(con,id) id is"+id);
		      return entity;
		  }
		  public static  EntityDto getEntityDto(Connection con,String id,String projectId)
		  {
			  EntityDto entity = new EntityDto();
			  entity.setModel(getentityProjectModel(con,id,projectId));
			  System.out.println("getentityProjectModel(con,id,projectId) id is"+id+" projectid="+projectId);
			  entity.setChildEntities(getChildEntites(con,id,projectId));
			  System.out.println("getChildEntites(con,id,projectId) id is"+id+"projectid="+projectId);
		      return entity;
		  }
		  
		  
		  public static void addSubEntity(Connection con,String ProjectId,String parentId,String childId)
		  {
		    try
		    {
		      Statement stat = con.createStatement();
		      if(parentId==""|| parentId==null)
		      {
		    	  parentId=ProjectId;
		    	  System.out.println("in update to parenttttttttt");
		      }
		      String strSql = "update ccm_project_entity set PARENT_ID ='"+parentId+ "' where entity_ID = '"+childId+ "' and project_id ='"+ProjectId+"' ";
		    
		      Utility.logger.debug(strSql);
		      int upnum=stat.executeUpdate(strSql);
		      System.out.println("rows updated:"+upnum);
		      stat.close();
		    }
		    catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		  }
		  
		  

			
		  public static Vector getentityFilteredForParent(Connection con,String projectId,String entityTypeId)
		  {
		    Vector vec = new Vector();
		    //boolean andFlag = false;
		    try
		    {
		    	
				Statement stat = con.createStatement();
				 String strSql = "select * from ccm_entity where entity_id <>  '"+projectId+"' and ENTITY_TYPE_ID='"+entityTypeId+"'  and  entity_id not in (select distinct (entity_ID)" +  
" FROM  VW_PROJECT_ENTITY_SERVICE   where PROJECT_ID ='"+projectId+"')";
			  System.out.println("ALL entity with out parent entities Query isssssss " + strSql);
		     
		      //Utility.logger.debug(strSql);
//			    for (int j=0; j<entityIds.size(); j++)
//		   
//		       {
//		          if(!andFlag){strSql += " where ";
//		          Utility.logger.debug(strSql);}
//		          else{strSql += " and ";
//		          Utility.logger.debug(strSql);}
//		          strSql += "ENTITY_ID <> '"+entityIds+"' ";
//		          andFlag = true;
//		       }

		      Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityMandatoryDataModel(res));
				}
		      stat.close();
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		    return vec;
		  }
		  
		  
		  public static Vector getAllEntitiesFirstLevel(Connection con,String projectId)
		  {
		    Vector vec = new Vector();
		    //boolean andFlag = false;
		    try
		    {
		    	
				Statement stat = con.createStatement();
				 String strSql = "select * from ccm_entity where  entity_id not in (select distinct (entity_ID)" +  
" FROM  VW_PROJECT_ENTITY_SERVICE   where PROJECT_ID ='"+projectId+"')";
				 
				 
			  System.out.println("ALL entity with Available for project by excluding project child Query isssssss " + strSql);
		     
		      //Utility.logger.debug(strSql);
//			    for (int j=0; j<entityIds.size(); j++)
//		   
//		       {
//		          if(!andFlag){strSql += " where ";
//		          Utility.logger.debug(strSql);}
//		          else{strSql += " and ";
//		          Utility.logger.debug(strSql);}
//		          strSql += "ENTITY_ID <> '"+entityIds+"' ";
//		          andFlag = true;
//		       }

		      Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new EntityMandatoryDataModel(res));
				}
		      stat.close();
		    }
		    catch(Exception e)
		    {
		    e.printStackTrace();
		    }
		    return vec;
		  }
		  

		  
		  public static Vector getListOptions(Connection con,String entityFieldId)
		  
		  {
			  Vector vec=new Vector();
	 
			 
		    try
		    {
		      Statement stat = con.createStatement();
		      String strSql = "select * from CCM_LIST_OPTIONS where  ENTITY_FIELD_ID = '"+entityFieldId+"'";
		      Utility.logger.debug(strSql);
		      ResultSet res = stat.executeQuery(strSql);
		
		    	
		    		while (res.next()) {
						vec.add(new EntityListOptionModel(res));
					}
              res.close();
		      stat.close();
		    }
		     catch(Exception ex)
		    {
		      ex.printStackTrace();
		    }
		    return vec;
		  }
		  
		//  public static Vector getParentRegions(Connection con)
		//  {
		//    Vector vector = new Vector();
		//    RegionModel regionModel = new RegionModel();
		//    try
		//    {
		//      Statement stat = con.createStatement();
		 //     String strSql = "select REGION_ID FROM DCM_REGION where PARENT_REGION_ID is null order by REGION_NAME";
		      //Utility.logger.debug(strSql);
		//      ResultSet res = stat.executeQuery(strSql);
		 //     while(res.next())
		 //     {
		//        vector.addElement(getRegionDto(con,res.getInt(1)));
		//      }
		 //     res.close();
		 //     stat.close();
		//    }
		//    catch(Exception ex)
		//    {
	//	      ex.printStackTrace();
		 //   }
		 //   return vector;
		//  }


}
