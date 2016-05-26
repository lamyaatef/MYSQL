<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<%
/**
 * Contract_Contracts_Verification.jsp:
 * Display the sheet data as a header followed by the contracts of this sheet.
 * 
 * showContracts(HttpServletRequest , HttpServletResponse , JspWriter): 
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
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Contract Warnings</H2>
        <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ContractWarnings" method="post">

          <%
          String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
        
          %>
          <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>">

          <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>" value="<%out.print(userID);%>">

          <input type="hidden" name="<%out.print(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID);%>">
        <%
          showContractWarning(request, response, out);
        %>
      </form>
    </Center>
  </body>
</html>
<%
  /**
   * Display any error messages returning from the server.
   */ 

  dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }
%>

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
  public void showContractWarning(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap();
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        HashMap contractPhaseWarnings = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        Vector warningStatuses = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Vector warningSuggestedStatuses = (Vector)dataHashMap.get(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        if(! contractPhaseWarnings.isEmpty())
        {
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td nowrap align=center colspan=3><font size=2>Phase Name</font></a></TD></TR>");
          for(int i=0; i<contractPhaseWarnings.size(); i++)
          {
            String strPhaseName = (String)contractPhaseWarnings.keySet().toArray()[i];
            out.println("<TR class=TableTextNote>");
            out.println("<td colspan=2>");
            out.println("<a id=\"x"+strPhaseName+"\" href=\"javascript:Toggle('"+strPhaseName+"');\">");
            out.print("<IMG height=16 hspace=0 src=\""+appName+"/resources/img/plus.gif\" width=16 border=0 >");
            out.print("</a>");
            out.print(strPhaseName+"</TD></TR>");
            out.println("<TR class=TableHeader>");
            out.println("<TD colspan=2 class=TableTextNote>");
            out.println("<div style=\"DISPLAY:none\" id=\""+strPhaseName+"\" name=\""+strPhaseName+"\">");
            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1 id=\""+strPhaseName+"table\" name=\""+strPhaseName+"table\">");
            Vector warnings = (Vector)contractPhaseWarnings.get(strPhaseName);
            if(warnings.isEmpty())
            {
              out.println("<TR");
              out.println("<td>No warnings available.</TD>");      
              out.println("</tr>");
            }
            else
            {
              out.println("<TR class=TableHeader>");
              out.println("<td align=middle size=\"30%\">Warning</td>");
              out.println("<td align=middle size=\"30%\">Warning Description</td>");
              //out.println("<td align=middle size=\"15%\">Warning Status</td>");
              out.println("<td align=middle size=\"15%\">Warning Suggested Status</td>");
              //out.println("<td align=middle size=\"10%\">Edit</td></tr>");
              for(int j=0; j<warnings.size(); j++)
              {
                ContractWarningModel newContractWarningModel = (ContractWarningModel)warnings.elementAt(j);
                String warningID = newContractWarningModel.getContractWarningID();
                String warning = newContractWarningModel.getContractWarningName();
                String warningDesc = newContractWarningModel.getContractwarningDesc();
                String warningSuggestedStatusId = newContractWarningModel.getContractwarningSuggestedStatusID();
                String warningSuggestedStatusName = newContractWarningModel.getContractwarningSuggestedStatusName();
                if(warningDesc == null)
                {
                  warningDesc = "";
                }
                String contractWarningStatusID = newContractWarningModel.getContractwarningStatusID();
                out.println("<TR>");
                out.println("<td class=TableTextNote>"+warning+"</TD>");
                out.println("<td class=TableTextNote>"+warningDesc+"</TD>");
                
                //out.println("<td class=TableTextNote align=center>");
                out.println("<SELECT style=\"display:none\" name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+warningID+
                            " onchange=\"document.ContractWarnings.update.disabled = false;\">");
                for(int k=0; k<warningStatuses.size(); k++)
                {
                  WarningStatusModel warningStatus = (WarningStatusModel)warningStatuses.elementAt(k);
                  String warningStatusID = warningStatus.getWarningStatusID();
                  String warningStatusName = warningStatus.getWarningStatusName();
                  out.println("<option value="+warningStatusID);
                  if(contractWarningStatusID.compareTo(warningStatusID)==0)
                  {
                    out.print("selected");
                  }
                  out.print(">"+warningStatusName+"</option>");
                }
                out.println("</select>");
                //out.println("</TD>");
                
                out.println("<td class=TableTextNote align=center>");
                out.println("<SELECT name="+ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS+warningID+
                            " onchange=\"document.ContractWarnings.update.disabled = false;\">");
                for(int l=0; l<warningSuggestedStatuses.size(); l++)
                {
                  WarningSuggestedStatusModel warningSuggestedStatus = (WarningSuggestedStatusModel)warningSuggestedStatuses.elementAt(l);
                  String warningSuggestedStatusIDX = warningSuggestedStatus.getWarningSuggestedStatusID();
                  String warningSuggestedStatusNameX = warningSuggestedStatus.getWarningSuggestedStatusName();
                  out.println("<option value="+warningSuggestedStatusIDX);
                  if(warningSuggestedStatusId.compareTo(warningSuggestedStatusIDX)==0)
                  {
                    out.print("selected");
                  }
                  out.print(">"+warningSuggestedStatusNameX+"</option>");
                }
                out.println("</select>");
                out.println("</TD>");
                
                /*out.println("<TD class=TableTextNote align=middle>");
                out.println("<input class=button type=\"button\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BUTTON_NAME_EDIT_WARNING_PREFIX+warningID+"\" value=\"   Edit   \"");
                out.println("onclick=\"document.ContractWarnings."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_EDIT_CONTRACT_WARNING+"';");
                out.println("document.ContractWarnings."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID+".value='"+warningID+"';");
                out.println("ContractWarnings.submit();\">");
                out.println("</TD>");
                */
                out.println("</TR>");
              }
            }
            out.println("</TABLE></div></td></tr>");      
          }
          out.println("</TABLE>");      
        }
        out.println("<br>");
        out.println("<table>"); 
        out.println("<tr>"); 
        out.println("<td align=center>"); 
        //out.println("<input class=button type=\"button\" name=\"new\" value=\" New Warning \""); 
        //out.print("onclick=\"document.ContractWarnings."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_NEW_CONTRACT_WARNING+"';ContractWarnings.submit();\">");
        out.println("<input class=button type=\"button\" name=\"update\" value=\"Update Warnings\" ");
        out.print("onclick=\"document.ContractWarnings."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT_SYSTEM_WARNINGS_STATUSES+"';ContractWarnings.submit();\" disabled>");
        out.println("</td>"); 
        out.println("</tr>"); 
        out.println("</table>"); 
      }
    }
  }
%>