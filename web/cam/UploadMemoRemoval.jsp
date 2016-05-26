<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.InterfaceKey"
	import="com.mobinil.sds.web.interfaces.cam.*"
	import="com.mobinil.sds.core.utilities.*"
	import="org.apache.commons.fileupload.*"
	import="com.mobinil.sds.core.system.sa.importdata.*"
	import="com.mobinil.sds.core.system.sa.importdata.model.*"
	import="com.mobinil.sds.core.system.cam.memo.model.*"%>
<%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
<%
	//to get the content type information from JSP Request Header
	String memoId = request.getParameter("memoID");

	String contentType = request.getContentType();
	//here we are checking the content type is not equal to Null andas well as the passed data from mulitpart/form-data is greater than orequal to 0
	//String attach_seq = request.getParameter("attach_id");
	// System.out.println("MASHMOUD = " + request.getParameter("MAHMOUD"));
	/*String file_path = "";

	if ((contentType != null)
			&& (contentType.indexOf("multipart/form-data") >= 0)) {
		DataInputStream in = new DataInputStream(request
				.getInputStream());
		//we are taking the length of Content type data

		DiskFileUpload upload = new DiskFileUpload();
		java.util.List items = upload.parseRequest(request);

		String baseDirectory = request
				.getRealPath("/cam/UploadedMemoFiles/");
		System.out.println("base directory = " + baseDirectory);
		Iterator itr = items.iterator();
		String fileUniqueName = null;
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			// check if the current item is a form field or an uploaded file
			if (item.isFormField()) {
				// get the name of the field
				String fieldName = item.getFieldName();

				// if it is name, we can set it in request to thank the user
				//	System.out.println("field name= " + fieldName);

			} else {
				// the item must be an uploaded file save it to disk. Note that there
				// seems to be a bug in item.getName() as it returns the full path on
				// the client's machine for the uploaded file name, instead of the file
				// name only. To overcome that, I have used a workaround using
				// fullFile.getName().
				//	File fullFile  = new File(item.getName()); 

				try {
					String fileNameOnClient = item.getName();
					// System.out.println("fileNameOnClient" + fileNameOnClient ) ;
					fileUniqueName = "RemovalMemoFile" + attach_seq
							+ ".xls";
					File savedFile = new File(baseDirectory + "/"
							+ fileUniqueName);

					item.write(savedFile);
				} catch (Exception e) {
					out.println("Error File can not be imported");
					e.printStackTrace();
					return;
				}
			}
		}
		file_path = Utility.replaceAll(
				request.getRealPath("/cam/UploadedMemoFiles/"
						+ fileUniqueName), "\\", "#");*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//samuel changes for the new import engine
		
		
		String appName = request.getContextPath();
      DiskFileUpload upload = new DiskFileUpload();
      List items = upload.parseRequest(request);
      String fileUniqueName ="";
      String baseDirectory =request.getRealPath("/cam/UploadedMemoFiles/");

      String tableId = (String)request.getParameter(MemoInterfaceKey.QUERY_STRING_TABLES) ;
      String operation =(String)request.getParameter(MemoInterfaceKey.QUERY_STRING_OPERATION);
      String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
     
      
      
		String fileNameOnClient="";

      Iterator itr = items.iterator();

      while(itr.hasNext()) {
      	FileItem item = (FileItem) itr.next();          
      	// check if the current item is a form field or an uploaded file
      	if(item.isFormField()) 
        {            
      	// get the name of the field
      	String fieldName = item.getFieldName();  
      	
      	// if it is name, we can set it in request to thank the user
      	if(fieldName.equals("name"))
      		request.setAttribute("msg", "Thank You: " + item.getString());
      		
      	} 
        else 
        {
      		// the item must be an uploaded file save it to disk. Note that there
      		// seems to be a bug in item.getName() as it returns the full path on
      		// the client's machine for the uploaded file name, instead of the file
      		// name only. To overcome that, I have used a workaround using
      		// fullFile.getName().
      	//	File fullFile  = new File(item.getName()); 

        try
        {
          fileNameOnClient = item.getName();
          Utility.logger.debug("fileNameOnClient" + fileNameOnClient ) ;
          fileUniqueName = System.currentTimeMillis()+".xls";
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

      //String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


      //ExcelImport importEngine = new ExcelImport(userID);
      Vector errorVector = new Vector();
      Vector statusVector = new Vector();


    //Utility.logger.debug("inside sop_data_warehouse_import_process.jsp");
      DataImportEngine importEngine = new DataImportEngine();

      DataImportReport importReport =importEngine.ImportFileWithRowNumber(baseDirectory+fileUniqueName , operation, tableId); 

      Vector reportImport = importReport.getReport();

      Vector report = new Vector();

      String operationName = importReport.getOperation();
      int numOfRecordsUpdated = importReport.getNumOfRecordsUpdated();

      //importEngine = null;

      out.println("Operation Was Completed");
      {

      if (operationName.compareTo("UPDATE")==0)
      {
      out.println("Total Number Of Records Updated = "+ numOfRecordsUpdated);
      }

                
       printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">",out);
       printToStream("<script type=\"text/javascript\">",out);
        printToStream("function Toggle(item) {",out);
        printToStream("obj=document.getElementById(item);",out);
        printToStream("if (obj!=null) {",out);
        printToStream("visible=(obj.style.display!=\"none\")",out);
        printToStream("key=document.getElementById(\"x\" + item);",out);     
        printToStream("if (visible) {",out);
        printToStream("obj.style.display=\"none\";",out);
        printToStream("key.innerHTML=\"<img src=\'"+appName+"/resources/img/plus.gif\'>\";",out);
        printToStream("} else {",out);
        printToStream("obj.style.display=\"block\";",out);
        printToStream("key.innerHTML=\"<img src='"+appName+"/resources/img/minus.gif\'>\";",out);      
        printToStream("}}}",out);
      printToStream("</script>",out);  

      java.util.Date currentDate = new java.util.Date();

      int currentYear = currentDate.getYear()+1900;
      int currentMonth = currentDate.getMonth()+1;
      int currentDay = currentDate.getDate();

      String strCurrentDate = currentYear+"/"+currentMonth+"/"+currentDay;
      ErrorInfo contractError = null;
      //this table will add channel Id and get by channel


     // Vector vecCurrentStockProducts = SchemaDAO.getStockProductsMatchActiveSchemaProductsByDate(strCurrentDate,strChannelId);
   //   boolean checkFromLCSPhysicalAmount = false;
    //  if(vecCurrentStockProducts.size()==0)
     // {
        Vector vec= MemoDAO.getTmpMemoRemoval();
        //if dcm delete else don't
       // if(strChannelId.equals("1"))
        //SchemaDAO.deleteProductEquationCalculate();
        Long fileid=null;
        
      if(vec.size()!=0){
    	  //the attatch sequence
    	  fileid=Long.valueOf((String)dataHashMap.get(InterfaceKey.ATTACH_ID));
        //System.out.println("attach_seq: "+attach_seq);
    	 
    	  
      }  
        
        for(int i = 0;i<vec.size();i++)
        {
        	MemoRemovalImportModel model = (MemoRemovalImportModel) vec.get(i);
          String tmRowNum =model.getRowNum();
          System.out.println("row num: "+tmRowNum);
          String tmScmCode =model.getDcmCode();
          System.out.println("scm code: "+tmScmCode);
          String tmReason=model.getReason();
          System.out.println("reason: "+tmReason);                   
    
    	MemoDAO.insertNewMemoRemovalData(tmScmCode,tmReason,memoId,strUserID);
          
    
        }
	     MemoDAO.deleteTmpMemoRemoval();
        
        
      if (report.size()==0 && reportImport.size()==0)
      {
        printToStream("<h3>",out);
        printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
        printToStream("</h3>",out);

      }
      else
      {
        
        printToStream("<br><br><h4>Error Report</h4>",out);
        printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out);
        printToStream("<TR class=TableHeader>",out);
        printToStream("<td size =20% nowrap align=left>Row Num</td>",out);
        printToStream("<td size= 10% nowrap align=left>Cell Value</td>",out);
        printToStream("<td size =10% nowrap align=left>Error Message</td>",out); 
        printToStream("</tr>",out);

        for (int i = 0 ; i < report.size();i++)
        {
          ErrorInfo error = (ErrorInfo)report.get(i);
          Utility.logger.debug(error.getCellName());
          printToStream("<tr>",out);
          printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
          if (error.getRowNum()>-1)
          printToStream((error.getRowNum()+1)+"",out);

          printToStream("</td>",out);
          printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
          printToStream(error.getCellName(),out);
          printToStream("</td>",out);
          printToStream("<TD class=TableTextNote align=left size=\"60%\">",out);
          printToStream(error.getErrorMsg(),out);
          printToStream("</td>",out);
        
          printToStream("</tr>",out);
               
        }
        for (int k = 0 ; k < reportImport.size();k++)
        {
          ErrorInfo error = (ErrorInfo)reportImport.get(k);
          Utility.logger.debug(error.getCellName());
          printToStream("<tr>",out);
          printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
          if (error.getRowNum()>-1)
          printToStream((error.getRowNum()+1)+"",out);

          printToStream("</td>",out);
          printToStream("<TD class=TableTextNote align=left size=\"20%\">",out);
          printToStream(error.getCellName(),out);
          printToStream("</td>",out);
          printToStream("<TD class=TableTextNote align=left size=\"60%\">",out);
          printToStream(error.getErrorMsg(),out);
          printToStream("</td>",out);
        
          printToStream("</tr>",out);
               
        }
        printToStream("</TABLE>",out);
        
      }

      }
%>

<%@page import="com.mobinil.sds.core.system.cam.memo.dao.MemoDAO"%><html>
<head>
<%
	//String appName = request.getContextPath();
%>
<LINK REL=STYLESHEET TYPE="text/css"
	HREF="<%out.print(appName);%>/resources/css/Template1.css">
</head>
<body>


</body>
</html>





