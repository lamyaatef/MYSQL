
<%@page import="com.mobinil.sds.core.system.dataMigration.model.DetailedPaymentReportModel"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao"%><%@ 
page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.web.interfaces.sop.*"
              import="com.mobinil.sds.web.interfaces.dm.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.sop.schemas.dao.*"
              import="com.mobinil.sds.core.system.sop.schemas.model.*"
              import="com.mobinil.sds.core.system.cr.contract.dao.*"
              import="com.mobinil.sds.core.system.cr.contract.model.*"
              import="com.mobinil.sds.core.system.cr.sheet.dao.*"
              import="com.mobinil.sds.core.system.cr.batch.dao.*"
              import="com.mobinil.sds.core.system.sa.users.dao.*"
              import="com.mobinil.sds.core.system.sa.users.model.*"
              
              import="  com.mobinil.sds.core.system.dataMigration.model.*"
              import="  com.mobinil.sds.core.system.dataMigration.DAO.*"
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"      

              import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
              import ="com.mobinil.sds.core.system.gn.dcm.model.*"
              import="java.io.IOException.*"
              import="java.sql.Connection.*"
              import=" java.sql.Statement.*"
              
    

              
              
              
              
              %>
    <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
    
    <%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.dao.AuthResDAO"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResLabelModel"%>
<%@page import="com.mobinil.sds.core.utilities.uploadAuthResThread"%>


<%


      	String appName = request.getContextPath();
      DiskFileUpload upload = new DiskFileUpload();
      List items = upload.parseRequest(request);
      String fileUniqueName ="";
      String baseDirectory =request.getRealPath("/dm/uplaodtext/");
      String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);

    //  String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
   //   String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
    //  String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
      String year=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECTED_YEAR);
      String month=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECTED_MONTH);

      
      String desc=(String)request.getParameter(DMInterfaceKey.CONTROL_INPUT_DESCRIPTION);
     // String labeName=(String)request.getParameter(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_NAME);
    %>
           
<%@page import="java.sql.Statement"%><form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="import_res" method="post">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=DMInterfaceKey.ACTION_DEL_SEARCH_IMPORT %>">
     
     <% 
     
      if( !DataMigrationDao.checkPOS_DELFile(year,month))
      {
    	  
  

    	  
  %>

  

    <script>
  	alert('FILE NOT EXIST OR DELETED  YEAR ' + '<%=year%>'+'  ,month '+ '<%=month%>  ');
  	document.import_res.submit();
    </script>

    <%
      }
      else{
      String fileNameOnClient="";

      Iterator itr = items.iterator();

      while(itr.hasNext()) {
      	FileItem item = (FileItem) itr.next();          
      	// check if the current item is a form field or an uploaded file
      	if(item.isFormField()) 
        {            
      	// get the name of the field
      	String fieldName = item.getFieldName();  
      	
  
      	if(fieldName.equals("name"))
      		request.setAttribute("msg", "Thank You: " + item.getString());
      		
      	} 
        else 
        {


        try
        {
          fileNameOnClient = item.getName();
          Utility.logger.debug("fileNameOnClient" + fileNameOnClient ) ;
          fileUniqueName = System.currentTimeMillis()+".txt";
      		File savedFile = new File(baseDirectory+fileUniqueName);		
          Utility.logger.debug("file " + savedFile);
      		item.write(savedFile);
          }
          catch (Exception e)
          {
          out.println("Error File can not be imported");
          e.printStackTrace();
          return ;
          }
      	}
      }


      HashMap dataHashMap = null;
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      
      Vector errorVector = new Vector();
      Vector statusVector = new Vector();

      
   //  desc,strUserID,baseDirectory+fileUniqueName
      
      Long	 fileid=null;
      String line = null;
      try {
    	  
    	    java.sql.Connection con=null;
			con = Utility.getConnection();

         Statement stat =con.createStatement();
			
			
			 BufferedReader input=null;
			try {
				input = new BufferedReader(new FileReader(baseDirectory+fileUniqueName));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		      int count=0;
		      int batchcount=0;
		      try {


				
               while ((line = input.readLine()) != null) {
					    	  count++;
					    	  
					    	  if(count==1 ){
									 fileid=DataMigrationDao.insertPosDelSearchFile(year,month,"processing",desc ,strUserID);  	 
					                     }
										System.out.println("line issss "+line);
										if (count != 0) {
											String fields = line;
									

											String v1=	fields;
											if(v1==null)
												
											{
												v1="";
												System.out.println("count isssssss "+count);
												
												
											}
											System.out.println("the values"+v1);
											
											
									
										//	if(DataMigrationDao.checkIfposCodeExistDel(v1,year,month))
										//	{
												batchcount++;	
											DataMigrationDao.insertPOSDelSearchData(fileid,v1,year,month,stat);
											
											
											if(batchcount==500)
											{	
												System.out.println( "the batch nis executed");
												stat.executeBatch();
												
												stat.clearBatch();
												batchcount=0;
											}
											
										//	}
										//	else{
												
												
											//	DataMigrationDao.insertInvalidPOScODEDel(fileid.toString(),v1);
												
												
										//	}
											
											
											}
									
										
 
				
			
				
				
				//	}
			//	else{
					
					
				//	DataMigrationDao.insertInvalidPOScODEHis(fileid.toString(),v1);
					
					
				//}

		
			}
stat.executeBatch();
stat.close();
DataMigrationDao.updatePOSDelChkExistFlag(fileid,month,year);
               

		   } 
			 catch (Exception e) {
				 e.printStackTrace();
			//	logger.fatal(e);
			//	systemLogger.fatal(" Process Cannot Connect to Database");
			//	systemLogger.fatal(e);
			//	counter.incCounter(countErrors + "");

			}
			 finally {
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			 DataMigrationDao.updateSearchpos_DEL_file(con,fileid.toString(), "active");

			con.close();
			} catch (java.sql.SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  
      
     out.print("<script>");
     out.print("document.import_res.action.value = '"+DMInterfaceKey.ACTION_DEL_SEARCH_IMPORT+"';" );
     out.print("document.import_res.submit();");
      
     out.print(" </script>");
      
      
      }
      %>  
      
   
    </form>
   
      
