<%@ 
page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.web.interfaces.sop.*"
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
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"      

              import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
              import ="com.mobinil.sds.core.system.gn.dcm.model.*"
              %>
    <%!
    public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }
  %>
      
<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/sop/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
//String strChannelId = (String)request.getParameter(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
Utility.logger.debug("Channel id issssssssssss   " +strChannelId);
System.out.println("The channel id issssssssssssss " + strChannelId);
String warehouseId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
System.out.println("The Warehouse id issssssssssssss " + warehouseId);
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


Utility.logger.debug("inside sop_data_warehouse_import_process.jsp");
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
Vector vecCurrentStockProducts = SchemaDAO.getStockProductsMatchActiveSchemaProductsByDate(strCurrentDate,strChannelId,warehouseId);
boolean checkFromLCSPhysicalAmount = false;
if(vecCurrentStockProducts.size()==0)
{
  Vector vecDataWarehouseProducts = SchemaDAO.getTmpDataWarehouse();
  //if dcm delete else don't
  if(strChannelId.equals("1"))
  SchemaDAO.deleteProductEquationCalculate();
  for(int i = 0;i<vecDataWarehouseProducts.size();i++)
  {
    ProductImportModel productImportModel = (ProductImportModel)vecDataWarehouseProducts.get(i);
    String tmRowNum = productImportModel.getRowNum();
    String tmProductCode = productImportModel.getProductCode();
    String tmActiveAmount = productImportModel.getActiveAmount();
    String strPhysicalAmount = "";  
    //
    if(checkFromLCSPhysicalAmount)
    {
      //this table will add channel Id and this function will be by channel
      strPhysicalAmount = SchemaDAO.getLCSProductPhysicalAmount(tmProductCode,strChannelId);
    }
    else
    {
      strPhysicalAmount = tmActiveAmount;
    }
    //by channel
    SchemaDAO.insertProductStock(tmProductCode,tmActiveAmount,strCurrentDate,strPhysicalAmount,strChannelId,warehouseId);
    // if dcm insert else don't
    if(strChannelId.equals("1"))
    SchemaDAO.insertProductEquationCalculate(tmProductCode);  
  } 
  
 
    //by channel id
    Vector stockLatestProducts = SchemaDAO.getLatestStockProductsMatchActiveSchemaProducts(strChannelId,warehouseId);
    String strLevel=""; 
            if(strChannelId.compareTo("1")==0)
            { strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
            else if (strChannelId.compareTo("2")==0)
            {strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
            else if (strChannelId.compareTo("3")==0)
            {strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
            else if (strChannelId.compareTo("4")==0)
            {strLevel = DCMDao.DCM_LEVEL_DISTRIBUTOR;}
	java.sql.Connection con = Utility.getConnection();            
    DCMDto dcmDto = DCMDao.getSOPUserDCMListByLevel(strLevel, strUserID,strChannelId,con);
    Utility.closeConnection (con);

    // channel ID
    SchemaDAO.deleteProductRequestLimits(strChannelId,warehouseId);
    for(int i=0;i<dcmDto.getDcmModelsSize();i++)
    {
      DCMModel model = dcmDto.getDcm(i);       
      int dcmID = model.getDcmId();
      String dcmName = model.getDcmName();
      for(int j=0;j<stockLatestProducts.size();j++)
      {
        ProductModel productModel = (ProductModel)stockLatestProducts.get(j);
        String productCode = productModel.getStockProductCode();
        String schemaId = productModel.getSchemaId();
        String schemaProductId = productModel.getSchemaProductId();
        String minimumLimit = "0";
        String maximumLimit = productModel.getActiveAmount();
        String physicalMaximumLimit = productModel.getPhysicalAmount();
        String isManual = SOPInterfaceKey.CONST_PRODUCT_LIMIT_IS_MANUAL;
        //////////////insert into SOP_PRODUCT_REQUEST_LIMIT//////////  
        SchemaDAO.insertProductRequestLimit(dcmID,schemaProductId,minimumLimit,maximumLimit,isManual,physicalMaximumLimit,productCode,schemaId,warehouseId);
      }
    }  
}
else
{
  contractError = new ErrorInfo();
  contractError.setCellName("Creation Date");
  contractError.setErrorMsg("Stock Products have values for today");
  //contractError.setRowNum(inttmRowNum);
  report.add(contractError);  
}
SchemaDAO.deleteTmpDataWarehouse();
/*Vector vecAuthTempStatus = AuthCallDAO.getAllAuthTempStatuses();
Utility.logger.debug("Auth Tmp  vector size : "+vecAuthTempStatus.size()+"dddddddd");
for(int i = 0;i<vecAuthTempStatus.size();i++)
{
  AuthTempStatusModel authTempStatusModel = (AuthTempStatusModel) vecAuthTempStatus.get(i);
  String tmRowNum = authTempStatusModel.getRowNum();
  int inttmRowNum = Integer.parseInt(tmRowNum);  
  String tmBatchId = authTempStatusModel.getBatchId();
  int inttmBatchId = Integer.parseInt(tmBatchId);  
  String tmSheetSerialNumber = authTempStatusModel.getSheetSerialNumber();
  String tmContractStatusId = authTempStatusModel.getContractStatusId();
  String tmMainSIMNumber = authTempStatusModel.getMainSIMNumber();
  String tmUserId = authTempStatusModel.getUserId();
  String tmWarning1 = authTempStatusModel.getWarning1();
  String tmWarning2 = authTempStatusModel.getWarning2();
  String tmWarning3 = authTempStatusModel.getWarning3();

  ErrorInfo contractError = null;

  ContractModel contractModel = ContractDao.getContractBySimNo(tmMainSIMNumber,tmBatchId);

  if(contractModel != null)
  {
  String contractID = contractModel.getId();
  String sheetID = contractModel.getSheetId();    
      boolean curReImportedFlag = contractModel.getReImportedFlag();
       if(curReImportedFlag == false)
       {
            String contractLastStatusId = contractModel.getContractLastStatusId();
            String contractStatusTypeId = ContractDao.getContractStatusByContractStatusId(contractLastStatusId);
            if(contractStatusTypeId.equals("5") || contractStatusTypeId.equals("7") || contractStatusTypeId.equals("8") )
            {
                    String userId = contractModel.getUserId();
                    Vector userIdExist = UserDAO.getUsersListByUserId(tmUserId);
                    if(userIdExist.size() != 0)
                    {
                            
                      
                                Vector vecContractWarnings = ContractDao.getUserContractWarningByPhase(ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE);
                                Vector vecContractSystemWarnings = new Vector();                               

                                String[] strUserWarnings = new String[3];
                             
                                
                                boolean [] userWarningDBFlag = new boolean[3];
                                for(int k=0;k<vecContractWarnings.size();k++)
                                {                              
                                    ContractWarningModel contractWarningModel = (ContractWarningModel)vecContractWarnings.get(k);
                                    String warningId = contractWarningModel.getContractWarningID();
                                    String warningName = contractWarningModel.getContractWarningName();

                               
                                    if(warningName.equals(tmWarning1))
                                    {
                                         userWarningDBFlag[0] = true;                                         
                                         strUserWarnings[0] = warningId;;
                                    }
                                    else if(warningName.equals(tmWarning2))
                                    {
                                        userWarningDBFlag[1] = true;                                        
                                        strUserWarnings[1] = warningId;
                                    }
                                    else if(warningName.equals(tmWarning3))
                                    {
                                        userWarningDBFlag[2] = true;                                        
                                        strUserWarnings[2] = warningId;
                                    }
                                }

                                    for (int v =0; v<userWarningDBFlag.length;v++)
                                    {
                                      if (!userWarningDBFlag[v])
                                      {
                                      contractError = new ErrorInfo();
                                        contractError.setCellName("Warning "+(v+1));
                                        contractError.setErrorMsg("Warning "+(v+1)+" doesn't exist in the database");
                                        contractError.setRowNum(inttmRowNum);
                                        report.add(contractError);
                                      }
                                    }                                                                       
                                   
                                    
                                    if(tmContractStatusId.equals("7")  || tmContractStatusId.equals("8"))
                                    {
                                       Utility.logger.debug("Inside Update phase ");
                                       String contractStatusID = ContractDao.updateContractRecord(contractID, userId, sheetID, tmBatchId, tmContractStatusId, strUserWarnings, vecContractSystemWarnings);                                        
                                       SheetDao.updateSheetStatusRecord(sheetID,tmBatchId,userId);        
                                       BatchDao.updateBatchStatus(tmBatchId);   

                                       Utility.logger.debug("New Status Id : "+contractStatusID); 
                                    }
                                    else
                                    {
                                      contractError = new ErrorInfo();
                                        contractError.setCellName("Contract Status");
                                        contractError.setErrorMsg("Invalid contract status in the sheet");
                                        contractError.setRowNum(inttmRowNum);
                                        report.add(contractError);  
                                    }
                      
                     } 
                     else
                     {
                        contractError = new ErrorInfo();
                            contractError.setCellName("User Id");
                            contractError.setErrorMsg("User doesn't exist");
                            contractError.setRowNum(inttmRowNum);
                            report.add(contractError);  
                     }
            }
            else 
            {
               contractError = new ErrorInfo();
                contractError.setCellName("Contract Status");
                contractError.setErrorMsg("Contract Status doesn't match Authentication phase");
                contractError.setRowNum(inttmRowNum);
                report.add(contractError);
            }
       }
       else
       {
          contractError = new ErrorInfo();
            contractError.setCellName("Contract");
            contractError.setErrorMsg("error in reimported flag");
            contractError.setRowNum(inttmRowNum);
            report.add(contractError);
       }      
  }
  else
  {
     contractError = new ErrorInfo();
      contractError.setCellName("Contract Main SIM No.");
      contractError.setErrorMsg("SIM No. doesn't exist in the system");
      contractError.setRowNum(inttmRowNum);
      report.add(contractError);
  }
}
ContractDao.deleteFromAuthTmp();
*/
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