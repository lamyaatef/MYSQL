<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.dm.*"
              import = "com.mobinil.sds.core.system.dataMigration.model.*"
              import = "com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
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
    //  String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
      String year=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECTED_YEAR);
      System.out.println("The year issssssss " + year);
      String month=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECTED_MONTH);
      System.out.println("The year issssssss " + month);
      String problematicId =(String)request.getParameter(DMInterfaceKey.CONTROL_HIDDEN_PROBLEMATIC_LABEL_ID);
      System.out.println("The problematicId issssssss " + problematicId);
      String paymentLevelId =(String)request.getParameter(DMInterfaceKey.CONTROL_HIDDEN_PAYMENT_LEVEL_ID);
      System.out.println("The paymentLevelId issssssss " + paymentLevelId);
      String paymentLevelName = DataMigrationDao.getPyamentLevelName(paymentLevelId);
      String problematicName = DataMigrationDao.getProblematicName(problematicId);
      if( DataMigrationDao.checkNotIntializedSerialsFile(year,month,problematicId,paymentLevelId))
      {
    	  
  %>

  <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Import" method="post">
  <% out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");
%>
    
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=DMInterfaceKey.ACTION_NOT_INTIALIZED_SERIALS_IMPORT %>">
  </form>
    <script>
  	alert('Must delete file of year ' + '<%=year%>'+' and month '+ '<%=month%>'+' and Payment Level '+ '<%=paymentLevelName%>'+' and Problematic Name '+ '<%=problematicName%>');
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
      HashMap<String,Double> uniqueSims = new HashMap<String,Double>();


      Long	 fileid=null;
      String line = null;
      Double lineNo = 0d;
                Double dbSimNo = 0d;
      BufferedReader input = new BufferedReader(new FileReader(baseDirectory+fileUniqueName));
    
      java.sql.Connection con = Utility.getConnection();
      fileid= DataMigrationDao.insertNewProblematicFile(year,month,"active",strUserID,paymentLevelName,problematicName);  	 

      java.sql.PreparedStatement ps = DataMigrationDao.getInsertSimProblimiticDataPreparedStatemnt(con,fileid,problematicId,paymentLevelId);
		

      try {

		int count=0;
                
		 // long stime = System.currentTimeMillis() ;

			while ((line = input.readLine()) != null) {
			     count++;
                             lineNo ++;
				if (uniqueSims.containsKey(line)) {
                                                               dbSimNo++;
                                                            }
                                                            uniqueSims.put(line, lineNo);
				
				//System.out.println("line issss "+line);
				
					String[] fields = line.split(",");
					String[] fields_apr = new String[fields.length];

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
						
	
//			DataMigrationDao.insertSimNumber(ps,fields_apr[0]);
 }
					
					
  }
                if (!uniqueSims.isEmpty()){
                for(String simNo : uniqueSims.keySet())
                    {
  DataMigrationDao.insertSimNumber(ps,simNo);
                }
	}
			 //  long etime = System.currentTimeMillis() ;
			//System.out.println("Operation took" + (etime-stime));
			
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
	

	
	
	
	
	int insertCoumt= DataMigrationDao.getNotIntialzedinsrerteValue(fileid);



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
        if (dbSimNo>0d)
            {
        printToStream("<br><h3>",out);
        printToStream("Count Of Repeated SIMs : "+dbSimNo,out );
        printToStream("</h3>",out);
        }

        if (uniqueSims.size()>0)
            {
        printToStream("<br><h3>",out);
        printToStream("Count Of Distinct SIMs : "+uniqueSims.size(),out );
        printToStream("</h3>",out);
        }
      }
 %>

