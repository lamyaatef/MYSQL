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
              import="com.mobinil.sds.core.system.sa.users.dao.*"
              import="com.mobinil.sds.core.system.sa.users.model.*"
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"      

              import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
              import ="com.mobinil.sds.core.system.gn.dcm.model.*"

              import ="com.mobinil.sds.core.system.sfr.sheets.model.*"
              import ="com.mobinil.sds.core.system.sfr.sheets.dao.*"
              %>
    <%!
    public void printToStream(String s, JspWriter out ,PrintWriter pw)throws Exception
    {
      out.println(s); 
      if (pw!=null)
      {
        pw.println(s);
        pw.flush();        
      }
    }
  %>
      
<%


String appName = request.getContextPath();
DiskFileUpload upload = new DiskFileUpload();
List items = upload.parseRequest(request);
String fileUniqueName ="";
String baseDirectory =request.getRealPath("/upload/");

String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);

String fileNameOnClient="";

Iterator itr = items.iterator();

while(itr.hasNext()) 
{
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
    fileUniqueName = "stf"+System.currentTimeMillis()+".xls";
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

//ExcelImport importEngine = new ExcelImport(userID);
Vector errorVector = new Vector(30, 5);
Vector statusVector = new Vector(30,5);


java.sql.Connection con = Utility.getConnection();

//insuring that no records for that user in the temp table 
//this will eliminate the ability of same user uploading more than a file at a time

SheetDAO.deleteTmpSfrExcelFile(strUserID,con);

DataImportEngine importEngine = new DataImportEngine();
DataImportReport importReport =importEngine.ImportFileWithRowNumber(baseDirectory+fileUniqueName , operation, tableId,strUserID); 
Vector reportImport = importReport.getReport();
Vector report = new Vector(30,5);

out.println("Operation Was Completed");


  java.io.File fileHtml =null;
  java.io.FileOutputStream outFile = null;
  java.io.PrintWriter pw = null;

  fileHtml = new java.io.File(baseDirectory+"/"+fileUniqueName+".html");
  fileHtml.createNewFile();
  outFile = new java.io.FileOutputStream(fileHtml);
  pw = new PrintWriter(outFile);     
  pw.flush();

  SheetDAO.insertExcelImportLog(fileUniqueName+".html",con);
  
printToStream("<LINK REL=STYLESHEET TYPE=\"text/css\" HREF=\""+appName+"/resources/css/Template1.css\">",out,pw);
printToStream("<script type=\"text/javascript\">",out,pw);
  printToStream("function Toggle(item) {",out,pw);
  printToStream("obj=document.getElementById(item);",out,pw);
  printToStream("if (obj!=null) {",out,pw);
  printToStream("visible=(obj.style.display!=\"none\")",out,pw);
  printToStream("key=document.getElementById(\"x\" + item);",out,pw);     
  printToStream("if (visible) {",out,pw);
  printToStream("obj.style.display=\"none\";",out,pw);
  printToStream("key.innerHTML=\"<img src=\'"+appName+"/resources/img/plus.gif\' border='0'>\";",out,pw);
  printToStream("} else {",out,pw);
  printToStream("obj.style.display=\"block\";",out,pw);
  printToStream("key.innerHTML=\"<img src='"+appName+"/resources/img/minus.gif\' border='0'>\";",out,pw);      
  printToStream("}}}",out,pw);
printToStream("</script>",out,pw);  

  ErrorInfo sheetError = null;
  Vector<SheetImportModel> vecSFRExcelFile = SheetDAO.getTmpSfrExcelFile(strUserID,con);
  HashMap lastValidValue = new HashMap();
  lastValidValue.put(SheetImportModel.AGENT_ID,"");
  lastValidValue.put(SheetImportModel.POS_CODE,null);
  lastValidValue.put(SheetImportModel.SECOND_POS_CODE,null);
  boolean insertBatchFlag = false;
  Long lBatchId = null;
  Vector vecBatchIds = new Vector();
  
  java.sql.PreparedStatement stat = SheetDAO.getSheetExistPreparedStatement(con);
  //need to remember to close it after seeing performance advantage
  int vecSize = vecSFRExcelFile.size();
  

  
  for(int i = 0;i<vecSize;i++)
  {	
	  
	
    
	boolean insertSheetFlag = true;
    Long lSheetId = null;
    SheetImportModel sheetImportModel = vecSFRExcelFile.get(i);
    String tmRowNum = sheetImportModel.getRowNum();
    String agentId = sheetImportModel.getAgentId();
    String posCode = sheetImportModel.getPosCode();
    String secondPosCode = sheetImportModel.getSecondPosCode();
    int sheetSerial = sheetImportModel.getSheetSerial();
    int contractsCount = sheetImportModel.getContractsCount();


    
    boolean isSheetExist = SheetDAO.isSheetExistWithNonRejectedStatus(sheetSerial,stat);
    

        
    if(secondPosCode != null)
    {
      if(posCode == null && agentId != null)
      {
        sheetError = new ErrorInfo();
        sheetError.setCellName(posCode);
        sheetError.setErrorMsg("POS Code is missing.");
        sheetError.setRowNum(Integer.parseInt(tmRowNum));
        report.add(sheetError);
        insertBatchFlag = false;
      }
      else
      {
        if(!isSheetExist)
        {
          if(agentId != null)
          {
            String lastValueOfAgentId = (String)lastValidValue.get(SheetImportModel.AGENT_ID);
            if(lastValueOfAgentId != null && lastValueOfAgentId.compareTo(agentId)!=0)
            {
              String agentName = SheetDAO.getAgentName(agentId,con);
              if(agentName.compareTo("")!=0)
              {
                if(contractsCount != -1 && sheetSerial != -1)
                {
                	insertBatchFlag = true;
                }
               
              }
              else
              {
                sheetError = new ErrorInfo();
                sheetError.setCellName(agentId);
                sheetError.setErrorMsg("Agent ID doesn't exist in the system.");
                sheetError.setRowNum(Integer.parseInt(tmRowNum));
                report.add(sheetError);
              }
              lastValidValue.remove(SheetImportModel.AGENT_ID);
              lastValidValue.put(SheetImportModel.AGENT_ID,agentId);
            }
          }
          else
          {
            agentId = (String)lastValidValue.get(SheetImportModel.AGENT_ID);
            if(agentId.compareTo("")==0)
            {
              sheetError = new ErrorInfo();
              sheetError.setCellName(agentId);
              sheetError.setErrorMsg("Agent ID is null.");
              sheetError.setRowNum(Integer.parseInt(tmRowNum));
              report.add(sheetError);
              continue;
            }
          }
          if(posCode == null && secondPosCode == null)
          {
            insertSheetFlag = false;  
            sheetError = new ErrorInfo();
            sheetError.setCellName("Second POS Code and POS Code");
            sheetError.setErrorMsg("Second POS Code and POS Code can not be empty in the same record.");
            sheetError.setRowNum(Integer.parseInt(tmRowNum));
            report.add(sheetError);
          }
          else
          { 
            if(posCode != null)
            {
              lastValidValue.remove(SheetImportModel.POS_CODE);
              lastValidValue.put(SheetImportModel.POS_CODE,posCode);
            }
            if(secondPosCode != null)
            {
              lastValidValue.remove(SheetImportModel.SECOND_POS_CODE);
              lastValidValue.put(SheetImportModel.SECOND_POS_CODE,secondPosCode);
            }   
          }
          if(contractsCount == -1)
          {
            insertSheetFlag = false; 
            insertBatchFlag = false;
            sheetError = new ErrorInfo();
            sheetError.setCellName("CONTRACT_COUNT");
            sheetError.setErrorMsg("Contract count is empty or not a number.");
            sheetError.setRowNum(Integer.parseInt(tmRowNum));
            report.add(sheetError);
            lastValidValue.remove(SheetImportModel.AGENT_ID);
            lastValidValue.put(SheetImportModel.AGENT_ID,"");
          }
          else if(sheetSerial == -1)
          {
            insertSheetFlag = false; 
            insertBatchFlag = false;
            sheetError = new ErrorInfo();
            sheetError.setCellName("SHEET_SERIAL");
            sheetError.setErrorMsg("Sheet serial is empty or not a number.");
            sheetError.setRowNum(Integer.parseInt(tmRowNum));
            report.add(sheetError);
            lastValidValue.remove(SheetImportModel.AGENT_ID);
            lastValidValue.put(SheetImportModel.AGENT_ID,"");
          }
          if(insertBatchFlag)
          {
            insertBatchFlag = false;
            lBatchId = SheetDAO.insertBatch((String)lastValidValue.get(SheetImportModel.AGENT_ID),"1",con);
            vecBatchIds.add(lBatchId);
            if(lBatchId != null)
            {
              SheetDAO.insertBatchStatus(lBatchId,"1",strUserID,con);
            }
          }
          if(lBatchId != null && insertSheetFlag)
          {
            String lastValueOfPOSCode = (String)lastValidValue.get(SheetImportModel.POS_CODE);
            String lastValueOfSecondPOSCode = (String)lastValidValue.get(SheetImportModel.SECOND_POS_CODE);
            insertSheetFlag = false;
            String lastValueOfPOSId = SheetDAO.getPOSId(lastValueOfPOSCode,con);
            if(lastValueOfPOSId.compareTo("")!=0)
            {
              String lastValueOfSecondPOSId = SheetDAO.getSecondPOSId(lastValueOfSecondPOSCode,con);
              if(lastValueOfSecondPOSId.compareTo("")!=0)
              {
                String agentName = SheetDAO.getAgentName(agentId,con);
                if(agentName.compareTo("")!=0)
                {
                  lSheetId = SheetDAO.insertSheet(sheetSerial,lBatchId,lastValueOfPOSId,lastValueOfSecondPOSId,contractsCount,con);
                  if(lSheetId != null)
                  {
                    //SheetDAO.insertSheetStatus(sheetSerial,"1",strUserID); 
                    SheetDAO.insertSheetStatus(con,lSheetId+"","1",strUserID); 
                  }
                }
                else
                {
                  sheetError = new ErrorInfo();
                  sheetError.setCellName(agentId);
                  sheetError.setErrorMsg("Mobinil Agent does not exist in the system.");
                  sheetError.setRowNum(Integer.parseInt(tmRowNum));
                  report.add(sheetError); 
                }
              }
              else
              {
                sheetError = new ErrorInfo();
                sheetError.setCellName(secondPosCode);
                sheetError.setErrorMsg("Second POS Code does not exist in the system.");
                sheetError.setRowNum(Integer.parseInt(tmRowNum));
                report.add(sheetError); 
              }
            }
            else
            {   
              sheetError = new ErrorInfo();
              sheetError.setCellName(posCode);
              sheetError.setErrorMsg("POS Code does not exist in the system.");
              sheetError.setRowNum(Integer.parseInt(tmRowNum));
              report.add(sheetError); 
            }
          }
        }
        else
        {
          sheetError = new ErrorInfo();
          sheetError.setCellName(sheetSerial+"");
          sheetError.setErrorMsg("Sheet Serial exists in the system with non rejected status.");
          sheetError.setRowNum(Integer.parseInt(tmRowNum));
          report.add(sheetError); 
        }

      }
    }
    else
    {
      sheetError = new ErrorInfo();
      sheetError.setCellName(secondPosCode);
      sheetError.setErrorMsg("Second POS Code is missing.");
      sheetError.setRowNum(Integer.parseInt(tmRowNum));
      report.add(sheetError);
    }
    
 
  }
  stat.close();


  
  SheetDAO.deleteTmpSfrExcelFile(strUserID,con);



/////////////////////////////    
  printToStream("<center><h3>",out,pw);
  printToStream("Report : " + fileNameOnClient,out,pw);
  printToStream("</h3></center>",out,pw);

Vector batchModels = new Vector();
if(vecBatchIds.size()>0)
{
  batchModels = SheetDAO.getBatches(vecBatchIds,con);    
}


if(batchModels.size()>0)
{
  printToStream("<h4>Imported Batches</h4>",out,pw);
  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
  printToStream("<TR class=TableHeader>",out,pw);
  printToStream("<td size =5% align=left></td>",out,pw);
  printToStream("<td size =20% nowrap align=left>Batch ID</td>",out,pw);
  printToStream("<td size= 10% nowrap align=left>Agent Id</td>",out,pw);
  printToStream("<td size =10% nowrap align=left>Agent Full Name</td>",out,pw); 
  printToStream("<td size =10% nowrap align=left>Batch Date</td>",out,pw);   
  printToStream("</tr>",out,pw);

  for(int k=0;k<batchModels.size();k++)
  {
    BatchModel batchModel = (BatchModel)batchModels.get(k);
    String batchId = batchModel.getBatchId();
    String agentId = batchModel.getAgentId();
    String agentFullName = batchModel.getAgentFullName();
    String batchStatusTypeId = batchModel.getBatchStatusTypeId();
    String batchStatusTypeName = batchModel.getBatchStatusTypeName();
    Date batchDate = batchModel.getBatchDate();

    printToStream("<TR class=TableTextNote>",out,pw);
    printToStream("<td size =5% align=left><A id=\"x"+batchId+"\" href=\"javascript:Toggle('"+batchId+"');\"><img src='"+appName+"/resources/img/plus.gif' border='0'></A></td>",out,pw);
    printToStream("<td size =20% nowrap align=left>"+batchId+"</td>",out,pw);
    printToStream("<td size= 10% nowrap align=left>"+agentId+"</td>",out,pw);
    printToStream("<td size =10% nowrap align=left>"+agentFullName+"</td>",out,pw); 
    printToStream("<td size =10% nowrap align=left>"+batchDate+"</td>",out,pw);   
    printToStream("</tr>",out,pw);

    
    Vector vecSheets = SheetDAO.getSheetsByFilter(batchId,0,"",con);
    
    printToStream("<tr>",out,pw);
    printToStream("        <td colspan=5>",out,pw);
    printToStream("          <div style=\"display:none\" id=\""+batchId+"\" name=\""+batchId+"\">",out,pw);
    printToStream("          <TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
    printToStream("            <tr class=TableHeader>  ",out,pw);
    printToStream("              <td align=middle>Sheet Serial</td>",out,pw);
    printToStream("              <td align=middle>POS Code</td>",out,pw);
    printToStream("              <td align=middle>Second POS Code</td>",out,pw);
    printToStream("              <td align=middle>Sheet SIM Count</td>",out,pw);
    printToStream("            </tr>",out,pw);
    for(int z=0;z<vecSheets.size();z++)
    {
      SheetModel sheetModel = (SheetModel)vecSheets.get(z);
      String sheetSerial = sheetModel.getSheetSerial();
      String posCode = sheetModel.getPosCode();
      String secondPosCode = sheetModel.getSecondPosCode();
      int sheetSIMCount = sheetModel.getSheetSIMCount();
      
      printToStream("            <tr class=TableTextNote>",out,pw);
      printToStream("              <td align=middle>",out,pw);
      printToStream(sheetSerial+"</td>",out,pw);
      printToStream("              <td align=middle>",out,pw);
      printToStream(posCode+"</td>",out,pw);
      printToStream("              <td align=middle>",out,pw);
      printToStream(secondPosCode+"</td>",out,pw);
      printToStream("              <td align=middle>",out,pw);
      printToStream(sheetSIMCount+"</td>",out,pw);
      printToStream("            </tr>",out,pw);
    }
    printToStream("           </table>",out,pw);
    printToStream("          </dev>",out,pw);
    printToStream("        </td>",out,pw);
    printToStream("      </tr>",out,pw);
  }
  printToStream("</table>",out,pw);
}

Utility.closeConnection(con);
if (report.size()>0 || reportImport.size()>0)
{
  printToStream("<br><br><h4>Error Report</h4>",out,pw);
  printToStream("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>",out,pw);
  printToStream("<TR class=TableHeader>",out,pw);
  printToStream("<td size =20% nowrap align=left>Row Num</td>",out,pw);
  printToStream("<td size= 10% nowrap align=left>Cell Value</td>",out,pw);
  printToStream("<td size =10% nowrap align=left>Error Message</td>",out,pw); 
  printToStream("</tr>",out,pw);

  for (int i = 0 ; i < report.size();i++)
  {
    ErrorInfo error = (ErrorInfo)report.get(i);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out,pw);

    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream(error.getCellName(),out,pw);
    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out,pw);
    printToStream(error.getErrorMsg(),out,pw);
    printToStream("</td>",out,pw);
  
    printToStream("</tr>",out,pw);
         
  }
  for (int k = 0 ; k < reportImport.size();k++)
  {
    ErrorInfo error = (ErrorInfo)reportImport.get(k);
    Utility.logger.debug(error.getCellName());
    printToStream("<tr>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    if (error.getRowNum()>-1)
    printToStream((error.getRowNum()+1)+"",out,pw);

    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"20%\">",out,pw);
    printToStream(error.getCellName(),out,pw);
    printToStream("</td>",out,pw);
    printToStream("<TD class=TableTextNote align=left size=\"60%\">",out,pw);
    printToStream(error.getErrorMsg(),out,pw);
    printToStream("</td>",out,pw);
  
    printToStream("</tr>",out,pw);
         
  }
  printToStream("</TABLE>",out,pw);
}



%>
