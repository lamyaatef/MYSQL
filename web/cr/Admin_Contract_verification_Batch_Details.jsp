<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<%
/**
 * Contract_Verification_Batch_Details.jsp:
 * Display the batch data as a header followed by the sheets of this batch.
 * Sheets displayed are those of a batch selected from the search results 
 * from "Contract_Verification_Batch_Search.jsp".
 * 
 * showSheets(HttpServletRequest , HttpServletResponse , JspWriter): 
 * used to display the sheets of the selected batchs.
 *
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

    String appName = request.getContextPath();
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Contract Verification Administration</H2>
    </Center>
        
    <left>
     <h3>Batch Details</h3>
    </left>
      <%
      showSheets(request, response, out);
      %>
    </form>
  </body>
</html>

<%!
/**
 * showSheets method:
 * Display the sheets of the selected batch.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  public void showSheets(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Vector dataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    Hashtable additionalCollection = (Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    if(dataVector != null && dataVector.size()!=0)
    {
      for(int i = 0; i<dataVector.size(); i++)
      {
        BatchDto newBatchDto = (BatchDto)dataVector.elementAt(i);
        if(newBatchDto != null)
        {
          String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
          String dcmID = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
          String batchID = newBatchDto.getBatchModel().getBatchId();
          String archivingFlag = newBatchDto.getBatchModel().getArchivingFlag();
          String batchDate = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
          String batchType = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
          String batchStatusType = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);

          out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"BatchDetails\" method=\"post\">");

          String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                      " value=\""+ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_SHEET_CONTRACT_VERIFY_SCREEN+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                      "\" value="+userID+">");
          
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                      "\" value=\""+batchID+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+
                      "\" value=\""+archivingFlag+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                      "\" value=\""+batchDate+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                      "\" value=\""+batchType+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                      "\" value=\""+batchStatusType+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+"\">");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center><font size=2>Batch ID</font></TD>");      
          out.println("<td nowrap align=center><font size=2>DCM Name</font></TD>");
          out.println("<td nowrap align=center><font size=2>Creation Date</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Type</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Status</font></TD></TR>");


          String dcmName = newBatchDto.getBatchModel().getBatchDCMName();;
          String sheetsBatchDate = newBatchDto.getBatchModel().getBatchDate();
          String sheetsBatchType = newBatchDto.getBatchModel().getBatchType();
          String batchStatus = newBatchDto.getBatchModel().getBatchStatus();
          out.println("<TR>");
          out.println("<td nowrap align=center>"+batchID+"</a></td>");
          out.println("<td nowrap>"+dcmName+"</td>");
          out.println("<td nowrap align=center>"+sheetsBatchDate+"</td>");
          out.println("<td nowrap>"+sheetsBatchType+"</td>");
          out.println("<td nowrap>"+batchStatus+"</td>");
          out.print("</tr>");
          out.println("<TR>");
          out.println("<TD colspan=6>");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td align=center width=\"10%\">Sheet Serial Number</td>");
          out.println("<td align=center width=\"10%\"><font size=2>POS Code</font></TD>");
          out.println("<td align=center width=\"10%\"><font size=2>Super Dealer Code</font></TD>");
          out.println("<td align=center width=\"12%\">Contract Type</td>");
          out.println("<td align=center width=\"5%\">Count</td>");
          out.println("<td align=center width=\"20%\">Sheet Status</td>");
          out.println("<td align=center>Warnings</td>");
          out.println("<td align=center>Verify</td></tr>");
          SheetStatusTypeDto newSheetStatusTypeDto = (SheetStatusTypeDto)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE);
          for(int j=0; j<newBatchDto.getSheetModelsSize(); j++)
          {
            SheetModel newSheetModel = newBatchDto.getSheetAt(j);
            String sheetID = newSheetModel.getSheetId();
            String sheetName = newSheetModel.getSheetName();
            String sheetTypeName = newSheetModel.getSheetTypeName();
            String sheetStatusName = newSheetModel.getSheetStatusName();
            String sheetPOSCode = newSheetModel.getSheetPOSCode();
            String sheetSuperDealerCode = newSheetModel.getSheetSuperDealerCode();
            SheetDto sheetDto = newBatchDto.getSheetDto();
            Vector sheetWarningsVector = sheetDto.getSheetWarning(sheetID);
            int contractCount = newSheetModel.getContractsCount();
            Enumeration contractsCountTypes = newSheetModel.getContractsTypesCount().keys();
            Enumeration contractsCountTypesCounter = newSheetModel.getContractsTypesCount().elements();
            out.println("<TR>");
            out.println("<td nowrap align=center>"+sheetName+"</td>");
            out.println("<td nowrap align=center>"+sheetPOSCode+"</td>");
            out.println("<td nowrap align=center>"+sheetSuperDealerCode+"</td>");
            out.println("<td nowrap align=left colspan=2>");
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=1 cellPadding=1 width=\"100%\" border=0>");
            while (contractsCountTypes.hasMoreElements())
            {          
              out.println("<tr><td width=\"65%\">"+(String)contractsCountTypes.nextElement()+"</td>");
              out.println("<td align=center>"+(String)contractsCountTypesCounter.nextElement()+"</td></tr>");
              out.println("<tr><td colspan =2 bgColor=#f1f1ed></td></tr>");
            }
            out.println("<tr><td bgColor=#f1f1ed><font style=\"FONT-WEIGHT: bold\">Total</font></td>");
            out.println("<td bgColor=#f1f1ed align=center><font style=\"FONT-WEIGHT: bold\">"+contractCount+"</font></td></tr>");
            out.println("</Table>");
            out.println("</td>");
            out.println("<td nowrap align=left>"+sheetStatusName+"</td>");
            out.println("<td align=left>");
            if(sheetWarningsVector !=null)
            {
              String sheetWarnings = "";
              for (int l=0; l<sheetWarningsVector.size(); l++)
              {          
                sheetWarnings+=((SheetWarningModel)sheetWarningsVector.elementAt(l)).getSheetWarningName()+"<br>";
              }
              out.println(sheetWarnings);
            }
            out.println("</td>");
            if ((sheetStatusName.compareTo(SheetModel.STATUS_REJECTED_IMPORT)==0)||
            (sheetStatusName.compareTo(SheetModel.STATUS_REJECTED_DELIVERY)==0))
            {
            out.println("<td></td>"); 
                
            }
            else
            {
            	if(newBatchDto.sheetClosed == 1 )
            	{
                 out.println("<td align=center><input disabled=disabled class=button type=\"button\" name=\"verify\" value=\"  Verify  \" ");	
            	}
            	else
            	{
                 out.println("<td align=center><input class=button type=\"button\" name=\"verify\" value=\"  Verify  \" ");
            	}
                 out.println("onclick=\"document.BatchDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                        ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_SHEET+"';"+
                        "document.BatchDetails."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+".value='"+
                        sheetName+"';"+
                        "document.BatchDetails."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE+".value='"+
                        sheetPOSCode+"';"+
                        "document.BatchDetails."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE+".value='"+
                        sheetSuperDealerCode+"'; BatchDetails.submit();\"></td>");
            }
                        
            out.println("</tr>");
          }
          out.println("</TABLE>");      
          out.print("</td>");
          out.print("</tr>");
          out.println("</TABLE>");
          out.println("<br>");
          out.println("<br>");
          out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
          out.println("<tr>");
          out.println("<td align=center>");
          out.println("<input class=button type=\"button\" name=\"print\" value=\"   Print   \" ");
          out.println("onclick=\"openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                      InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_PRINT_BATCHS+"&"+
                      ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+"=,"+batchID+"&"+
                      ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+"=,"+archivingFlag+"');\">");
          //out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
          //out.println("onclick=\"document.BatchDetails."+
          //            InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
          //            ContractRegistrationInterfaceKey.ACTION_ADMIN_SEARCH_BATCH_CONTRACT_VERIFICATION+"';BatchDetails.submit();\">");
          out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
          out.println("onclick=\"history.go(-1);\">");
          out.println("</td>");
          out.println("</tr>");
          out.println("</table>");
        }
      }
    }
  }
%>