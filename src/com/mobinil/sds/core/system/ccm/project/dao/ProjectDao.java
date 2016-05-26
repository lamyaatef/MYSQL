package com.mobinil.sds.core.system.ccm.project.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import com.mobinil.sds.core.system.ccm.entity.model.EntityMandatoryDataModel;
import com.mobinil.sds.core.system.ccm.entityproject.model.entityProjectModel;
import com.mobinil.sds.core.system.ccm.project.model.ProjectModel;
import com.mobinil.sds.core.system.ccm.schema.model.SchemaModel;
import com.mobinil.sds.core.system.ccm.service.model.ServiceModel;
import com.mobinil.sds.core.system.hlp.casepkg.dao.CaseDAO;
import com.mobinil.sds.core.utilities.Utility;
public class ProjectDao {


		public static Vector getProject(Connection con) {
			Vector vec = new Vector();
			try {
				Statement stat = con.createStatement();
				String strSql = "SELECT * FROM CCM_PROJECT,sop_schema where  CCM_PROJECT.schema_id=sop_schema.schema_id";
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new ProjectModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;

		}	
		
		
		
		public static Vector getSpecificProject(Connection con,String ProjectID) {
			Vector vec = new Vector();
			try {
				Statement stat = con.createStatement();
				String strSql = "SELECT * FROM CCM_PROJECT,sop_schema where CCM_PROJECT.PROJECT_ID='"+ProjectID+"' and   CCM_PROJECT.schema_id=sop_schema.schema_id";
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new ProjectModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;

		}	
		public static Vector getServiceAssignedProject(Connection con,String projectId) {
			Vector vec = new Vector();
			try {
				Statement stat = con.createStatement();
				String strSql = "SELECT * FROM CCM_SERVICE,CCM_PROJECT_SERVICE WHERE CCM_PROJECT_SERVICE.PROJECT_ID  ="+projectId+" AND CCM_SERVICE.SERVICE_ID =CCM_PROJECT_SERVICE.SERVICE_ID";
				ResultSet res = stat.executeQuery(strSql);
				while (res.next()) {
					vec.add(new ServiceModel(res));
				}
				res.close();
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vec;

		}
		
	
		
		public static Long insertNewProject(Connection con,
				String projectName,  String projectDetail ,String projectStautusId,String address,String Date,String schemaId)
{

			Long PROJECT_ID = null;

           

			try {
				Statement stat = con.createStatement();
				PROJECT_ID= Utility.getSequenceNextVal(con, "SEQ_CCM_PROJECT_ID");
			    //StringTokenizer st = new StringTokenizer(serviceId,",");
			  //  while (st.hasMoreTokens()) {
				String strSql = "insert into ccm_PROJECT( PROJECT_ID ,PROJECT_NAME,PROJECT_DETAIL,PROJECT_STATUS_ID,PROJECT_CREATION_DATE,PROJECT_ADDRESS,SCHEMA_ID) "
						+ " values("+PROJECT_ID+",'"+projectName+"' ,'"+projectDetail+"',"+projectStautusId+",'"+Date+"','"+address+"',"+schemaId+")";
		       System.out.println("the query is " +strSql);
		  
		       //StringTokenizer st = new StringTokenizer(serviceId,",");
				  //  while (st.hasMoreTokens()) {
				stat.execute(strSql);		
				stat.close();
				
			 //   }
				
	
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return PROJECT_ID;
		}	
		
		public static void	insertAssignedService(Connection con,String  projectId,String serviceId)
		{
			
	
			
			try {
				Statement stat = con.createStatement();
			
				String strSql = "insert into ccm_PROJECT_service( PROJECT_ID ,SERVICE_ID) "
						+ " values("+projectId+" ,"+serviceId+")";
		       System.out.println("the query is " +strSql);

				stat.execute(strSql);
				con.commit();
				stat.close();
			

			} catch (Exception e) {
				e.printStackTrace();
			}
	
			
		}
		
		public static void	deleteAssignedService(Connection con,String  projectId)
		{
			
	
			
			try {
				Statement stat = con.createStatement();
			
				String strSql = "delete from  ccm_PROJECT_service where PROJECT_ID="+projectId+" ";
						
		       System.out.println("the delete query isssssssss " +strSql);

				stat.execute(strSql);
				con.commit();
				stat.close();
			

			} catch (Exception e) {
				e.printStackTrace();
			}
	
			
		}
		
		
		
		public static void	insertAssignedEntities(Connection con,String projectId,String entityId)
		{
			Long PROJECT_ENTITY_ID=null;
			
			try {
				PROJECT_ENTITY_ID= Utility.getSequenceNextVal(con, "SEQ_CCM_PROJECT_ENTITY_ID");
				Statement stat = con.createStatement();
			
				String strSql = "insert into ccm_PROJECT_ENTITY( PROJECT_ENTITY_ID,PROJECT_ID ,ENTITY_ID,LEVEL_ID,PARENT_ID) "
						+ " values("+PROJECT_ENTITY_ID+","+projectId+" ,"+ entityId+",0,"+projectId+")";
		       System.out.println("ASSIGN ENTITY TO PROJECT query isSSSSSSSS " +strSql);

				stat.execute(strSql);	
			    con.commit();
				stat.close();
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		public static void	deleteAssignedEntities(Connection con,String projectId)
		{
			
			
			try {
				
				Statement stat = con.createStatement();
			
				String strSql = "delete from ccm_PROJECT_ENTITY where PROJECT_ID = "+projectId+"";
						
		       System.out.println("delete  ENTITY assign to project isssss " +strSql);

				stat.execute(strSql);	
			    con.commit();
				stat.close();
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		
		
		public static void	insertSubEntities(Connection con,String projectId,String entityId,String parentId)
		{
			Long PROJECT_ENTITY_ID=null;
			
			try {
				
				
				
				
				
				
				PROJECT_ENTITY_ID= Utility.getSequenceNextVal(con, "SEQ_CCM_PROJECT_ENTITY_ID");
				Statement stat = con.createStatement();
			
				String strSql = "insert into ccm_PROJECT_ENTITY( PROJECT_ENTITY_ID,PROJECT_ID ,ENTITY_ID,LEVEL_ID,PARENT_ID) "
						+ " values("+PROJECT_ENTITY_ID+","+projectId+" ,"+ entityId+",0,"+parentId+")";
				
		       System.out.println("insert Sub Entity  query issssssss " +strSql);

				stat.execute(strSql);	
			    con.commit();
				stat.close();
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		
		
		public static void	deleteSubEntities(Connection con,String projectId,String entityId)
		{
			//Long PROJECT_ENTITY_ID=null;
			
			try {
				
   //	PROJECT_ENTITY_ID= Utility.getSequenceNextVal(con, "SEQ_CCM_PROJECT_ENTITY_ID");
				Statement stat = con.createStatement();
			
				String strSql = "delete  from ccm_PROJECT_ENTITY   where  PROJECT_ID= "+projectId+" and  ENTITY_ID="+entityId+" ";
				System.out.println("delete function issssssssssssssss"+strSql);
						
				
		   
				stat.execute(strSql);	
			    con.commit();
				stat.close();
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		 public static void updateSubEnities(Connection con,String projectId,String entityId,String parentId)
		  {

		    try
		    {
		      Statement stat = con.createStatement();
		        String strSql = "update ccm_PROJECT_ENTITY set "+ 
	"PARENT_ID ='"+parentId+"' where PROJECT_ID = '"+projectId+"' AND ENTITY_ID='"+entityId+"'";  
		      
		     System.out.println("The update Entity Query isssssss " + strSql);
		     stat.execute(strSql);          
		    }
		    catch(Exception e)
		    {  
		      e.printStackTrace();
		    }    
		  }
		 
		 
       public static void updateProject(Connection con,String projectId,String projectName,String Address ,String projectDescription,String projectStatus,String date,String SchemaId  )
		  {

		    try
		    {
		      Statement stat = con.createStatement();
		        String strSql = "update ccm_PROJECT set "+ 
	 "PROJECT_NAME = '"+projectName+"',PROJECT_DETAIL ='"+projectDescription+"'  , PROJECT_STATUS_ID ="+projectStatus+"  , PROJECT_CREATION_DATE  ='"+date+"'  , PROJECT_ADDRESS  ='"+Address+"'  where PROJECT_ID = "+projectId+"";  
		      
		     System.out.println("The update Query isssssss " + strSql);
		     stat.execute(strSql);          
		    }
		    catch(Exception e)
		    {  
		      e.printStackTrace();
		    }    
		  }	
		  

		  public static Vector getAllSchemas(Connection con,String status,String channelID)
		  {
		      Vector Vec = new Vector();
		      try
		      {
		       Statement stat = con.createStatement();
		       String strSql = "select * from VW_SOP_SCHEMA where CHANNEL_ID = '"+channelID+"' and SCHEMA_STATUS_ID=' "+status+"'";
		       System.out.println("schemas list query is"+strSql);
		       ResultSet res= stat.executeQuery(strSql);
		       while(res.next())
		       {
		    	   Vec.add(new SchemaModel(res));
		       }
		       stat.close();
		      }
		      catch(Exception e)
		      {
		      e.printStackTrace();
		      }

		        return Vec; 
		  }
		  
		  public static boolean checkEntityExist(Connection con,String EntityId,String ProjectId)
		  {
			  boolean exist=false;
		      try
		      {
		      
		       Statement stat = con.createStatement();
		       String strSql = "select * from VW_PROJECT_ENTITY_SERVICE where entity_id = '"+EntityId+"' and project_id=' "+ProjectId+"'";
		       System.out.println("check entity exist query is"+strSql);
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
		  
		  
		  public static boolean checkChildExist(Connection con,String EntityId,String ProjectId)
		  {
			  boolean exist=false;
		      try
		      {
		      
		       Statement stat = con.createStatement();
		       String strSql = "select * from VW_PROJECT_ENTITY_SERVICE where parent_id= '"+EntityId+"' and project_id=' "+ProjectId+"'";
		       System.out.println("check child exist query is"+strSql);
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
		  
		  
	
			public static Vector getServiceforSpecificproject(Connection con,String projectId) {
				Vector vec = new Vector();
				try {
					Statement stat = con.createStatement();
					String strSql = "SELECT ccm_project_service .SERVICE_ID,SERVICE_NAME,SERVICE_DESCRIPTION  FROM ccm_service,ccm_project_service where ccm_project_service.PROJECT_ID="+projectId+" and  ccm_service.service_id=ccm_project_service.SERVICE_ID ";
					System.out.println("SQL ISSSSSSSSSSS"+strSql);
					ResultSet res = stat.executeQuery(strSql);
					while (res.next()) {
						vec.add(new ServiceModel(res));
					}
					res.close();
					stat.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return vec;

			}	
			

			public static Vector getEntityforSpecificproject(Connection con,String projectId) {
				Vector vec = new Vector();
				try {
					Statement stat = con.createStatement();
					String strSql = "SELECT *  FROM ccm_entity,ccm_project_entity where ccm_project_entity.PROJECT_ID='"+projectId+"'and  ccm_entity.entity_id=ccm_project_entity.entity_id  ";
					System.out.println("SQL ISSSSSSSSSSS"+strSql);
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
			
		  
		  
		  
		  
		  
		  
			  public static Vector getprojectByFilter(Connection con,String projectName,String projectAddress,String schema)
			  {
			    Vector vec = new Vector();
			    boolean andFlag = false;
			    try
			    {
			    	
					Statement stat = con.createStatement();
					String strSql = "SELECT * FROM CCM_PROJECT,sop_schema where  CCM_PROJECT.schema_id=sop_schema.schema_id";
				  System.out.println("project : " + strSql);
			     
			      //Utility.logger.debug(strSql);

			      if(projectName!=null && projectName.compareTo("")!=0)
			       {
			          if(!andFlag){strSql += "  AND  ";
			          Utility.logger.debug(strSql);}
			          else{strSql += " and ";
			          Utility.logger.debug(strSql);}
			          strSql += "project_name = '"+projectName+"' ";
			          andFlag = true;
			       }

			      if(projectAddress!=null && projectAddress.compareTo("")!=0)
			      {
			        if(!andFlag)
			        {
			          strSql += "  AND  ";
			        }
			        else
			        {
			          strSql += " and ";
			        }
			        strSql += "PROJECT_ADDRESS = '"+projectAddress+"'";
			        andFlag = true;
			      }
			      
			      if(schema!=null &&schema.compareTo("")!=0)
			      {
			        if(!andFlag)
			        {
			          strSql += "  AND  ";
			        }
			        else
			        {
			          strSql += " and ";
			        }
			        strSql += "sop_schema.SCHEMA_NAME='"+schema+"'";
			        andFlag = true;
			      }
			      
			      System.out.println("project : " + strSql);
		      
			  //     if(entityCode != null && entityCode.compareTo("")!=0)
			  //    {
			   //     if(!andFlag)
			  //      {
			  //        strSql += " where ";
			  //      }
			  //   else
			   //    {
			  //     strSql += " and ";
			   //    }
			   //     strSql += "ENTITY_CODE = '"+entityCode+"'";
			  //     andFlag = true;
			 //     }

			    //  strSql += " order by INVOICE_NUMBER,DCM_ID,PAYMENT_DATE";
			      Utility.logger.debug(strSql);
			      ResultSet res = stat.executeQuery(strSql);
					while (res.next()) {
						vec.add(new ProjectModel(res));
					}
			      stat.close();
			    }
			    catch(Exception e)
			    {
			    e.printStackTrace();
			    }
			    return vec;
			  }
				
				public static void	deleteProject(Connection con,String  projectId)
				{
					
			
					
					try {
						Statement stat = con.createStatement();
					
	                   ProjectDao.deleteAssignedService(con, projectId);
	                   
	                   ProjectDao.deleteAssignedEntities(con, projectId);
						
					  String strSql = "delete from  ccm_PROJECT where PROJECT_ID="+projectId+" ";
								
				       System.out.println("the delete query isssssssss " +strSql);

						stat.execute(strSql);
						con.commit();
						stat.close();
					

					} catch (Exception e) {
						e.printStackTrace();
					}
			
					
				}
		  

		  
		  

		}