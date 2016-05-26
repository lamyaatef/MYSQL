<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<%
/**
 * Sheet_Contracts_Verification.jsp:
 * Display the sheet data as a header followed by the contracts of this sheet.
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
    if(NonBlank(document.SheetHistory.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);%>, true, 'Sheet Serial Number'))
    {
      document.SheetHistory.submit();
    }
    return false;
  }
</SCRIPT>
<%      
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
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
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <Center>
      <H2>Sheet History</H2>
        <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="SheetHistory" method="post">

          <%
          String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
          %>


          <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>"
          value="<%out.print(ContractRegistrationInterfaceKey.ACTION_SHOW_SHEET_HISTORY);%>">

          <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="<%out.print(userID);%>">

          <table  cellspacing="2" cellpadding="1" border="0" width="100%">
          <tr>
            <td align=right>Sheet Serial Number :</td>
            <td align=left><input class=input type="text" name="<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);%>"></td>
          </tr>
          <tr>
            <td colspan=2 align=center>
              <input class=button type="button" name="ok" value="Show History" 
              onclick="checkbeforSubmit();">
              <input class=button type="Reset" name="reset" value="Reset">
            </td>
          </tr>
        </table>
        <%
          showSheetHistory(request, response, out);
        %>
      </form>
    </Center>
  </body>
</html>

<%!
/**
 * showContracts method:
 * Display the contracts of the verified sheet.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  public void showSheetHistory(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        Vector sheetDtosVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        if(sheetDtosVector != null)
        {
          if(sheetDtosVector.size() != 0)
          {
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
            String tempBatchID = null;
            for(int i=0; i<sheetDtosVector.size(); i++)
            { 
              SheetDto newSheetDto = (SheetDto)sheetDtosVector.elementAt(i);
              SheetModel newSheetModel = newSheetDto.getSheet(0);
              String sheetID = newSheetModel.getSheetId();
              String sheetSerialNo = newSheetModel.getSheetName();
              out.println("<script>SheetHistory."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+".value=\""+sheetSerialNo+"\"</script>");
              String sheetType = newSheetModel.getSheetTypeName();           
              String sheetDCMCode = newSheetModel.getSheetDCMCode();
              String sheetDCMName = newSheetModel.getSheetDCMName();
              String sheetSuperDealerCode = newSheetModel.getSheetSuperDealerCode();
              String sheetSuperDealerName = newSheetModel.getSheetSuperDealerName();
              String sheetPOSCode = newSheetModel.getSheetPOSCode();
              String sheetPOSName = newSheetModel.getSheetPOSName();
              String sheetBatchID = newSheetModel.getSheetBatchID();
              if(sheetBatchID ==null)
              {
                sheetBatchID = "";
              }
              if (tempBatchID!=null && (tempBatchID.compareTo(sheetBatchID)!=0))
              {
                out.println("</TABLE></td></tr>");
              }
              if (tempBatchID==null || (tempBatchID.compareTo(sheetBatchID)!=0))
              {
                out.println("<TR class=TableHeader>");
                out.println("<td nowrap align=center>Sheet Serial Number</TD>");
                out.println("<td nowrap align=center>Sheet Recieve Date</TD>");
                out.println("<td nowrap align=center>Sheet Type</font></TD>");
                out.println("<td nowrap align=center>DCM Code</font></TD>");
                out.println("<td nowrap align=center>DCM Name</TD>");
                out.println("</tr>");
                
                out.println("<TR>");
                out.println("<td nowrap align=center>"+sheetSerialNo+"</TD>");
                out.println("<td nowrap align=center>"+newSheetModel.getSheetStatusDate()+"</TD>");
                out.println("<td nowrap align=center>"+sheetType+"</TD>");
                out.println("<td nowrap align=center>"+sheetDCMCode+"</TD>");
                out.println("<td nowrap align=center>"+sheetDCMName+"</TD>");
                
                out.println("</tr>");
                out.println("<TR class=TableHeader>");
                out.println("<td nowrap align=center>Super Dealer Code</TD>");
                out.println("<td nowrap align=center>Super Dealer Name</TD>");
                out.println("<td nowrap align=center>POS Code</TD>");
                out.println("<td nowrap align=center>POS Name</TD>");
                out.println("<td nowrap align=center>Batch ID</TD>");                
                out.println("<TR>");
                out.println("<td nowrap align=center>"+sheetSuperDealerCode+"</TD>");
                out.println("<td nowrap align=center>"+sheetSuperDealerName+"</TD>");
                out.println("<td nowrap align=center>"+sheetPOSCode+"</TD>");
                out.println("<td nowrap align=center>"+sheetPOSName+"</TD>");
                out.println("<td nowrap align=center>"+sheetBatchID+"</TD>");
                out.println("</tr>");
                out.println("<tr><td colspan=5>");
                out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
                out.println("<TR class=TableHeader>");
                out.println("<td width=\"20%\" nowrap align=middle>Sheet Status</td>");
                out.println("<td width=\"20%\" nowrap align=middle>Status Date</td>");
                out.println("<td width=\"20%\" nowrap align=middle>Warnings</td>");
                out.println("<td width=\"20%\" nowrap  align=center>User Updated</TD>");
                out.println("</tr>");
              }
              out.println("<TR>");
              out.println("<td align=center>"+newSheetModel.getSheetStatusName()+"</td>");
              out.println("<td align=center>"+newSheetModel.getSheetStatusDate()+"</td>");
              out.println("<td>");
            
              Vector sheetWarningsVector = newSheetDto.getSheetWarning(sheetID);
              for(int j=0; j<sheetWarningsVector.size(); j++)
              {
                SheetWarningModel newSheetWarningModel = (SheetWarningModel)sheetWarningsVector.elementAt(j);
                String sheetWarningName = newSheetWarningModel.getSheetWarningName();
                if(sheetWarningName != null)
                {
                  out.print(sheetWarningName+"<br>");
                }
              }
              out.println("</td>");

              
              out.println("<td nowrap align=center>"+newSheetModel.getStatusUserName()+"</TD>");
              out.println("</tr>");
              tempBatchID = sheetBatchID;
            }
            out.println("</TABLE></td></tr>");
            out.println("</TABLE>");      
          }
          else
          {
          out.println("<h4 align=left>No results available.</h4>");
          }
        }
      }
      else
      {
      out.println("<h4 align=left>No results available.</h4>");
      }
    }
  }
%>