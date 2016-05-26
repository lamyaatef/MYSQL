
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
              %>
    <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
      
<%
      	String appName = request.getContextPath();
      DiskFileUpload upload = new DiskFileUpload();
      List items = upload.parseRequest(request);
      String fileUniqueName ="";
      String baseDirectory =request.getRealPath("/dm/uplaodPaymentReport/");
      String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);

      String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
      String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
    //  String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
      String year=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECT_PAYMENT_CASH_YEAR);
      String month=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECT_PAYMENT_CASH_MONTH);
      if( DataMigrationDao.checkCashFile(year,month))
      {
    	  
  %>

  <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Import" method="post">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=DMInterfaceKey.ACTION_PAYMENT_CASH_REPORT_IMPORT %>">
  </form>
    <script>
  	alert('Must delete file of year ' + '<%=year%>'+' and month '+ '<%=month%>');
  	document.Import.submit();
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

      Long	 fileid=null;
      String line = null;
      BufferedReader input = new BufferedReader(new FileReader(baseDirectory+fileUniqueName));
      java.sql.Connection con = Utility.getConnection();
      java.sql.PreparedStatement ps = null;
      //java.sql.PreparedStatement ps = DataMigrationDao.getInsertPaymentCashDataPreparedStatemnt(con,fileid);
      try {

			
			
			// not declared within while loop
			/*
			 * readLine is a bit quirky : it returns the content of a line
			 * MINUS the newline. it returns null only for the END of the
			 * stream. it returns an empty String if two newlines appear in
			 * a row.
			 */
		int count=0;
		
			while ((line = input.readLine()) != null) {
			     count++;
				
				 if(count==1 ){
					 fileid= DataMigrationDao.insertNewPaymentCashFile(year,month,"active",strUserID);  
					 ps = DataMigrationDao.getInsertPaymentCashDataPreparedStatemnt(con,fileid);
	                     }

			//	System.out.println("line issss "+line);
				
					String[] fields = line.split(",");
					String[] fields_apr = new String[7];

					for (int i = 0; i < fields_apr.length; i++) {
					String v1=	fields[i];
				//	System.out.println("the values       "+v1);
					
						fields_apr[i] = "";
					}
			
					if (fields != null && fields.length != 0) {
						for (int i = 0; i < fields.length; i++) {
							if (fields[i] != null && !fields[i].equals(""))
							{
							fields_apr[i] = fields[i];
							String v2=fields_apr[i];
							String v3=fields[i];
						//	System.out.println("the values v2   "+v2);
						
							}
							else
							fields_apr[i] = "";
							 fields[i]= "";
					
						}
						
	//DataMigrationDao.insertNewPaymentCashData(fileid,fields_apr[0],fields_apr[1],  fields_apr[2] ,fields_apr[3] ,fields_apr[4],fields_apr[5]);
						DataMigrationDao.insertNewPaymentCashData(ps,fileid,fields_apr[0],fields_apr[1],  fields_apr[2] ,fields_apr[3] ,fields_apr[4],fields_apr[6],fields_apr[5]);
 }
  }
   } 
	 catch (Exception e) {
		 e.printStackTrace();
	//	logger.fatal(e);
	//	systemLogger.fatal(" Process Cannot Connect to Database");
	//	systemLogger.fatal(e);
	//	counter.incCounter(countErrors + "");

	}
	 finally {
			input.close();
			if (ps!=null)try{ps.close();}catch(Exception e){}
			if (con!= null)try{Utility.closeConnection(con);}catch(Exception e){}
		}
	
	

	
	
	
	
	int insertCoumt= DataMigrationDao.getinsrerteValue(fileid);



    if (insertCoumt !=0)
     {
       printToStream("<h3>",out);
       printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
       printToStream("</h3>",out);

     }
    
    else
    {
    	
    
        printToStream("<h3>",out);
        printToStream("Operation Not Done: ",out );
        printToStream("</h3>",out);  
    
   
    }
      }
 %>

<!--form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="SOPform" method="post">
<%
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

//out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
       //           " value=\""+InterfaceKey.ACTION_GENERATE_MANUAL_DCM_PRODUCT_LIMITS_CHECKING+"\">");

%>

</form>
<script>
SOPform.submit();
</script-->