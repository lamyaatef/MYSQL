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
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    if(NonBlank(document.SheetVerify.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);%>, true, 'Sheet Serial Number'))
    {
      if(NonBlank(document.SheetVerify.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);%>, true, 'POS Code'))
      {
        if(NonBlank(document.SheetVerify.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);%>, true, 'Super Dealer Code'))
        {
          document.SheetVerify.submit();
        }
      }
    } 
    return false;
  }
</SCRIPT>
<%    String appName = request.getContextPath();%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <Center>
      <H2>Contract Verification</H2>
    </Center>
    <left>
     <h3>Sheet Verification</h3>
    </left>
    <Center>
      <%
      showSheets(request, response, out);
      %>
    </Center>
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

          String sheetSerialNumber = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
          if(sheetSerialNumber == null)
          {
            sheetSerialNumber = "";
          }
          String sheetPOSCode = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
          if(sheetPOSCode == null)
          {
            sheetPOSCode = "";
          }
          String sheetSuperDealerCode = (String)additionalCollection.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
          if(sheetSuperDealerCode == null)
          {
            sheetSuperDealerCode = "";
          }

          String dcmName = newBatchDto.getBatchModel().getBatchDCMName();;
          String sheetsBatchDate = newBatchDto.getBatchModel().getBatchDate();
          String sheetsBatchType = newBatchDto.getBatchModel().getBatchType();
          String batchStatus = newBatchDto.getBatchModel().getBatchStatus();

          out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetVerify\" method=\"post\">");

          String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                      " value=\""+ContractRegistrationInterfaceKey.ACTION_VERIFY_SHEET+"\">");

          out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                      "\" value="+userID+">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                      "\" value=\""+batchID+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+
                      "\" value=\""+archivingFlag+"\">");
                      
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                      "\" value=\""+dcmID+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                      "\" value=\""+batchDate+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                      "\" value=\""+batchType+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                      "\" value=\""+batchStatusType+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+"\">");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center><font size=2>Batch ID</font></TD>");      
          out.println("<td nowrap align=center><font size=2>DCM Name</font></TD>");
          out.println("<td nowrap align=center><font size=2>Creation Date</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Type</font></TD>");
          out.println("<td nowrap align=center><font size=2>Batch Status</font></TD></TR>");
          out.println("<TR>");
          out.println("<td nowrap align=center>"+batchID+"</a></td>");
          out.println("<td nowrap>"+dcmName+"</td>");
          out.println("<td nowrap align=center>"+sheetsBatchDate+"</td>");
          out.println("<td nowrap>"+sheetsBatchType+"</td>");
          out.println("<td nowrap>"+batchStatus+"</td>");
          out.print("</tr>");
          out.println("</TABLE>");
          out.println("<br>");
          out.println("<br>");
          out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\">");
          out.println("<tr>");
          out.println("<td>Sheet Serial Number :</td>");
          out.println("<td><input class=input type=\"text\" "+
                      "name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+"\" "+
                      "value=\""+sheetSerialNumber+"\"></td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>POS Code :</td>");
          out.println("<td><input class=input type=\"text\" "+
                      "name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE+"\" "+
                      "value=\""+sheetPOSCode+"\"></td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td>Super Dealer Code :</td>");
          out.println("<td><input class=input type=\"text\" "+
                      "name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE+"\" "+
                      "value=\""+sheetSuperDealerCode+"\"></td>");
          out.println("</tr>");
          out.println("<tr>");
          out.println("<td colspan=2 align=center>");
          out.println("<input class=button type=\"button\" name=\"verify\" value=\"  Verify  \" onclick=\"checkbeforSubmit();\">");
          out.println("<input class=\"button\" type=\"Reset\" value=\"  Reset  \">");
          out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
          out.println("onclick=\"document.SheetVerify."+
                      InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                      ContractRegistrationInterfaceKey.ACTION_SEARCH_BATCH_CONTRACT_VERIFICATION+"';"+
                      "SheetVerify.submit();\">");
          out.println("</td>");
          out.println("</tr>");
          out.println("</table>");
          out.println("</form>");
        }
      }
    }
  }
%>