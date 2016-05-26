<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
         import="com.mobinil.sds.core.system.cr.phase.dto.*"
         import="com.mobinil.sds.core.system.cr.phase.model.*"
%>
<%
/**
 * Warning_New_Edit.jsp:
 * Add or edit a user.
 * 
 * showUser(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the form in which a user will be edited or added.
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
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type=text/javascript></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){WarningManagment.save.click();}">
        <%
        showContractWarning(request, response, out);
        %>
  </body>
</html>

<%!
  /**
   * showUser method: 
   * Display the form in which a user will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showContractWarning(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      String header = "New Contract Warning";
      String warningID = "";
      String warningName = "";
      String warningDesc = "";
      String warningStatusID = "";
      String warningSuggestedStatusID = "";
      ContractWarningModel newContractWarningModel = (ContractWarningModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newContractWarningModel != null)
      {
        warningID = newContractWarningModel.getContractWarningID();
        warningName = newContractWarningModel.getContractWarningName();
        warningDesc = newContractWarningModel.getContractwarningDesc();
        warningSuggestedStatusID = newContractWarningModel.getContractwarningSuggestedStatusID();
        if(warningDesc == null)
        {
          warningDesc = "";
        }
        warningStatusID = newContractWarningModel.getContractwarningStatusID();
        header = "Edit Contract Warning";
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"WarningManagment\" method=\"post\">");

      String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"");
      out.println(" value=\""+ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT_WARNING+"\">");
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_WARNING_ID+"\" value=\""+warningID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Warning *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_NAME+"\"");
      if(newContractWarningModel != null)
      {
        out.print(" value=\""+warningName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"20%\">Warning Description</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_DESC+"\"");
      if(newContractWarningModel != null)
      {
        out.print(" value=\""+warningDesc+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      if(newContractWarningModel != null)
      {
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">Warning Status</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+warningID+"\">");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          String statusID = ((WarningStatusModel)additionalDataVector.elementAt(j)).getWarningStatusID();
          String statusName = ((WarningStatusModel)additionalDataVector.elementAt(j)).getWarningStatusName();
          out.println("<option value=\""+statusID+"\""); 
          if(warningStatusID.compareTo(statusID)==0)
          {
            out.print("selected");
          }
          out.print(">"+statusName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD></TR>");

        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">Warning Suggested Status</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS+">");
        Vector vecWarningSuggestedStatus = (Vector)dataHashMap.get(ContractRegistrationInterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int k = 0; k<vecWarningSuggestedStatus.size(); k++)
        {
          String suggestedStatusID = ((WarningSuggestedStatusModel)vecWarningSuggestedStatus.elementAt(k)).getWarningSuggestedStatusID();
          String suggestedStatusName = ((WarningSuggestedStatusModel)vecWarningSuggestedStatus.elementAt(k)).getWarningSuggestedStatusName();
          out.println("<option value=\""+suggestedStatusID+"\""); 
          if(warningSuggestedStatusID.compareTo(suggestedStatusID)==0)
          {
            out.print("selected");
          }
          out.print(">"+suggestedStatusName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD>");        
        out.println("</TR>");
      }
      else
      {
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"20%\">Phase</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name=\""+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+warningID+"\">");
        PhaseDTO phaseDTO = (PhaseDTO)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Vector phasesVector = phaseDTO.getPhases();
        for(int j = 0; j<phasesVector.size(); j++)
        {
          String phaseID = ((PhaseModel)phasesVector.elementAt(j)).getPhaseID();
          String phaseName = ((PhaseModel)phasesVector.elementAt(j)).getPhaseName();
          out.println("<option value=\""+phaseID+"\">"+phaseName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD></TR>");
      }
      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TABLE>");
    
      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      out.println("<input class=button type=\"button\" name=\"save\"");
      if(newContractWarningModel != null)
      {
        out.print(" value=\" Update \"");
      }
      else
      {
        out.print(" value=\"   Add   \"");
      }
      out.print(" onclick=\"if(NonBlank("+ContractRegistrationInterfaceKey.CONTROL_TEXT_WARNING_NAME+", true, 'Warning')){document.WarningManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='");
      if(newContractWarningModel != null)
      {
        out.print(ContractRegistrationInterfaceKey.ACTION_UPDATE_CONTRACT_WARNING);
      }
      else
      {
        out.print(ContractRegistrationInterfaceKey.ACTION_ADD_CONTRACT_WARNING);
      }
      out.print("';WarningManagment.submit();} return false;\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
