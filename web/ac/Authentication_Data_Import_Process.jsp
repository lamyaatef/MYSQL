<%@ 
page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.ac.authcall.dao.*"
              import="com.mobinil.sds.core.system.ac.authcall.model.*"
              import="com.mobinil.sds.core.system.cr.contract.dao.*"
              import="com.mobinil.sds.core.system.cr.contract.model.*"
              import="com.mobinil.sds.core.system.cr.sheet.dao.*"
              import="com.mobinil.sds.core.system.cr.batch.dao.*"
              import="com.mobinil.sds.core.system.sa.users.dao.*"
              import="com.mobinil.sds.core.system.sa.users.model.*"
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"              
              %>
    <%!
    public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }
  %>
      
<%
String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/ac/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);

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



DataImportEngine importEngine = new DataImportEngine();
Utility.logger.debug("inside Authentication data import process.jsp");
DataImportReport importReport =importEngine.ImportFileWithRowNumber(baseDirectory+fileUniqueName , operation, tableId); 
//Vector reportImport = importReport.getReport();

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


Vector vecAuthTempStatus = AuthCallDAO.getAllAuthTempStatuses();
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
                                       java.sql.Connection con = Utility.getConnection();
                                       String contractStatusID = ContractDao.updateContractRecord(contractID, userId, sheetID, tmBatchId, tmContractStatusId, strUserWarnings, vecContractSystemWarnings,con);                                        
                                       SheetDao.updateSheetStatusRecord(sheetID,tmBatchId,userId);        
                                       BatchDao.updateBatchStatus(tmBatchId,con);
                                       Utility.closeConnection(con);

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

if (report.size()==0)
{
  printToStream("<h3>",out);
  printToStream("Operation Completed Successfully: " + fileNameOnClient,out);
  printToStream("</h3>",out);

}
else
{
  
  printToStream("<h3>",out);
  printToStream("Errors Report : " + fileNameOnClient,out);
  printToStream("</h3>",out);

  

  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out);
  printToStream("<TR class=TableHeader>",out);
  printToStream("<td size =20% nowrap align=left>Row Num</td>",out);
  printToStream("<td size= 10% nowrap align=left>Cell Name</td>",out);
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


  printToStream("</TABLE>",out);

}

}


%>