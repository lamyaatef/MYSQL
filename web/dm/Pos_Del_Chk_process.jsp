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

      String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
      String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
      String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
      String year=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECT_POS_DEL_YEAR);
      String month=(String)request.getParameter(DMInterfaceKey.CONTROL_SELECT_POS_DEL_MONTH);
      
      if( DataMigrationDao.checkPOS_DELFile(year,month))
      {
    	  
  %>

  <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Import" method="post">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=DMInterfaceKey.ACTION_POS_DEL_CHK_IMPORT %>">
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

       String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
       System.out.println("User id issssssssss" +userID);


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
        Vector vec= DataMigrationDao.getTmpPOS_DEL_CHKData();
        //if dcm delete else don't
       // if(strChannelId.equals("1"))
        //SchemaDAO.deleteProductEquationCalculate();
        Long fileid=null;
        
      if(vec.size()!=0){
    	  
    	  
    	  
    	 fileid= DataMigrationDao.insertNewPOS_DEL_CHKFile(year,month,"active",userID);  
    	  
      }  
        
        for(int i = 0;i<vec.size();i++)
        {
          POS_DEL_CHKModel model = (POS_DEL_CHKModel) vec.get(i);
          
          
          
          String dcmCode =model.getDCM_CODE();
          
          String delivered =model.getDELIVERED();
         
          String ni =model.getNI();
          
          String probl =model.getPROBL();
          
          String notd =model.getNOTD();
          
          String acc =model.getACC();
          
        
          
          
          
          
  
   
         
          
          
  //  Long fileid= DataMigrationDao.insertNewPaymentCashFile(year,month,"active");
    
    DataMigrationDao.insertNewPOS_DEL_CHKData(fileid,dcmCode,delivered,ni,probl,notd,acc); 
          
      
 
      
        //for(int i=0;i<dcmDto.getDcmModelsSize();i++)
        //{
        //  DCMModel model = dcmDto.getDcm(i);       
        //  int dcmID = model.getDcmId();
         // String dcmName = model.getDcmName();
         // for(int j=0;j<stockLatestProducts.size();j++)
        //  {
         //   ProductModel productModel = (ProductModel)stockLatestProducts.get(j);
         ///  String productCode = productModel.getStockProductCode();
        ///   String schemaId = productModel.getSchemaId();
         //   String schemaProductId = productModel.getSchemaProductId();
        //    String minimumLimit = "0";
        //    String maximumLimit = productModel.getActiveAmount();
        //    String physicalMaximumLimit = productModel.getPhysicalAmount();
        //    String isManual = SOPInterfaceKey.CONST_PRODUCT_LIMIT_IS_MANUAL;
              //////////////insert into SOP_PRODUCT_REQUEST_LIMIT//////////  
       // SchemaDAO.insertProductRequestLimit(dcmID,schemaProductId,minimumLimit,maximumLimit,isManual,physicalMaximumLimit,productCode,schemaId);
        //  }
        //}  
    //}
    //else
       }
      // {
      //  contractError = new ErrorInfo();
      //  contractError.setCellName("Creation Date");
     //   contractError.setErrorMsg("Stock Products have values for today");
        //contractError.setRowNum(inttmRowNum);
      //  report.add(contractError);  
      // }
     DataMigrationDao.deleteTmpPOS_DEL_CHKData();

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
      }
    
      %>

<!--form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="SOPform" method="post">
<%
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+SOPInterfaceKey.ACTION_GENERATE_MANUAL_DCM_PRODUCT_LIMITS_CHECKING+"\">");

%>

</form>
<script>
SOPform.submit();
</script-->