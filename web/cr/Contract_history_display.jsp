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
         import="com.mobinil.sds.core.system.cr.contract.model.*"
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
      <H2>Contract History</H2>      
        <%
          showContractHistory(request, response, out);
        %>      
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
  public void showContractHistory(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        Vector contractsVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        if(contractsVector.size() != 0)
        {
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          String tempSheetID = null;
          for(int i=0; i<contractsVector.size(); i++)
          { 
            ContractHistoryModel newContractHistoryModel = (ContractHistoryModel)contractsVector.elementAt(i);
            String contractID = newContractHistoryModel.getId();
            String contractSheetID = newContractHistoryModel.getSheetId();
            String contractMainSimNum = newContractHistoryModel.getMainSimNum();
            String contractSheetSerial = newContractHistoryModel.getSheetSerialNo();
            if(contractSheetID ==null)
            {
              contractSheetID = "";
            }
            if(tempSheetID!=null && (tempSheetID.compareTo(contractSheetID)!=0))
            {
              out.println("</TABLE></td></tr>");
            }
            if(tempSheetID==null || (tempSheetID.compareTo(contractSheetID)!=0))
            {
              out.println("<TR class=TableHeader>");
              out.println("<td nowrap align=center><font size=2>Contract Import Date</font></TD>");
              out.println("<td nowrap align=center><font size=2>Contract Main SIM Number</font></TD>");      
              out.println("<td nowrap align=center><font size=2>Sheet Serial</font></TD>");
              out.println("</tr>");
              //out.println("<script>ContractHistory."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".value=\""+contractMainSimNum+"\"</script>");
              out.println("<TR>");
              out.println("<td nowrap align=center>"+newContractHistoryModel.getContractStatusDate()+"</TD>");
              out.println("<td nowrap align=center>"+contractMainSimNum+"</TD>");      
              out.println("<td nowrap align=center>"+contractSheetSerial+"</TD>");
              out.println("</tr>");
              out.println("<tr><td colspan=3>");
              out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
              out.println("<TR class=TableHeader>");
              out.println("<td width=\"20%\" nowrap align=middle>Contract Status</td>");
              out.println("<td width=\"20%\" nowrap align=middle>Status Date</td>");
              out.println("<td width=\"20%\" nowrap align=middle>User</td>");
              out.println("<td width=\"20%\" nowrap align=middle>Warnings</td></tr>");
            }
            out.println("<TR>");
            out.println("<td>"+newContractHistoryModel.getContractStatusName()+"</td>");
            out.println("<td align=center>"+newContractHistoryModel.getContractStatusDate()+"</td>");
            out.println("<td align=center>"+newContractHistoryModel.getUserName()+"</td>");
            out.println("<td>");
            
            String contractWarningName = newContractHistoryModel.getContractWarningName();
            if(contractWarningName != null)
            {
              out.print(contractWarningName);
            }
            out.println("</td></tr>");
            tempSheetID = contractSheetID;
          }
          out.println("</TABLE></td></tr>");
          out.println("</TABLE>");      
        }
        else
        {
          
          out.println("<h4 align=left>No results available.</h4>");
        }
      }
      else
      {
        out.println("<h4 align=left>No results available.</h4>");
      }
    }
  }
%>