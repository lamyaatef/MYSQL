<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
%>
<%
/**
 * Sheet_Type_New_Edit.jsp:
 * Add or edit a Sheet Type.
 * 
 * showSheetType(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the form in which a sheet type will be edited or added.
 *
 * @version	1.01 May 2004
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
    if(NonBlank(document.SheetTypeManagment.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_NAME);%>, true, 'SheetType Name'))
    {
      if(NonBlank(document.SheetTypeManagment.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_AUTH_PERCENT);%>, true, 'SheetType Authentication Percentage'))
      {
        SheetTypeManagment.submit();
      }
    }
    return false;  
  }
</SCRIPT>
<%      
  String appName = request.getContextPath();
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type=text/javascript></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <%
    showSheetType(request, response, out);
    %>
  </body>
</html>

<%!
  /**
   * showSheetType method: 
   * Display the form in which a sheet type will be edited or added.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showSheetType(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
      String header = "New SheetType";
      String sheetTypeID = "";
      String sheetTypeName = "";
      String sheetTypeDesc = "";
      double sheetTypeAuthPercent = 0.0;
      String sheetTypeStatusID = "";
      String sheetPOSQuestionFlag = "";
      String sheetDiscardUnrealUnreachableFlag = "";
      SheetTypeModel newSheetTypeModel = (SheetTypeModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      if(newSheetTypeModel != null)
      {
        sheetTypeID = newSheetTypeModel.getSheetTypeId();
        sheetTypeName = newSheetTypeModel.getSheetTypeName();
        sheetTypeDesc = newSheetTypeModel.getSheetTypeDesc();
        sheetTypeAuthPercent = newSheetTypeModel.getSheetAuthPercentage();
        sheetTypeStatusID = newSheetTypeModel.getSheetTypeStatusId();
        sheetPOSQuestionFlag = newSheetTypeModel.getSheetPOSQuestionFlag();
        sheetDiscardUnrealUnreachableFlag = newSheetTypeModel.getSheetDiscardUnrealUnreachableFlag();
        header = sheetTypeName;
      }
      out.println("<Center>");
      out.println("<H2>"+header+"</H2>");
      out.println("</Center>");
      out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetTypeManagment\" method=\"post\">");
      out.print("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"");

      String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

      if(newSheetTypeModel != null)
      {
        out.println(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_TYPE+"\">");
      }
      else
      {
        out.println(ContractRegistrationInterfaceKey.ACTION_ADD_SHEET_TYPE+"\">");
      }
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE_ID+"\" value=\""+sheetTypeID+"\">");
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"30%\">Sheet Type *</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_NAME+"\"");
      if(newSheetTypeModel != null)
      {
        out.print(" value=\""+sheetTypeName+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"30%\">Sheet Type Description</TD><TD class=TableTextNote>");
      out.println("<input size=\"50%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_DESC+"\"");
      if(newSheetTypeModel != null)
      {
        if(sheetTypeDesc == null)
        {
          sheetTypeDesc = "";
        }
        out.print(" value=\""+sheetTypeDesc+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote width=\"30%\">SheetType Authentication Percentage (%) *</TD><TD class=TableTextNote>");
      out.println("<input size=\"10%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_TYPE_AUTH_PERCENT+"\"");
      if(newSheetTypeModel != null)
      {
        out.print(" value=\""+sheetTypeAuthPercent+"\"");
      }
      out.print(">");
      out.println("</TD></TR>");
      if(newSheetTypeModel != null)
      {
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote width=\"30%\">SheetType Status</TD>");
        out.println("<TD class=TableTextNote>");
        out.println("<SELECT name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+sheetTypeID+
                    " onchange=\"document.SheetTypeManagment.update.disabled = false;\">");
        Vector additionalDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        for(int j = 0; j<additionalDataVector.size(); j++)
        {
          SheetTypeStatusModel newSheetTypeStatusModel = (SheetTypeStatusModel)additionalDataVector.elementAt(j); 
          String statusID = newSheetTypeStatusModel.getId();
          String statusName = newSheetTypeStatusModel.getName();
          out.println("<option value=\""+statusID+"\""); 
          if(sheetTypeStatusID.compareTo(statusID) == 0)
          {
            out.print("selected");
          }
          out.print(">"+statusName+"</option>");
        }
        out.println("</SELECT>");
        out.println("</TD></TR>");
      }

      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote colspan=2><input type=\"checkbox\" name=\""+ContractRegistrationInterfaceKey.CONTROL_CHECK_BOX_POS_QUESTION+"\" value=\"1\" ");
      if(newSheetTypeModel != null)
      {
        if(sheetPOSQuestionFlag.compareTo("1") == 0)
        {
          out.print("checked");
        }
      }
      out.print(">Check if POS is reachable to verify a contract</TD></TR>");
      out.println("<TR class=TableTextNote>");
      out.println("<TD class=TableTextNote colspan=2><input type=\"checkbox\" name=\""+ContractRegistrationInterfaceKey.CONTROL_CHECK_BOX_DISCARD_UNREAL_UNREACHABLE+"\" value=\"1\" ");
      if(newSheetTypeModel != null)
      {
        if(sheetDiscardUnrealUnreachableFlag.compareTo("1") == 0)
        {
          out.print("checked");
        }
      }
      out.print(">Discard Unreal or Unreachable</TD></TR>");

      out.println("<TR class=TableTextNote><TD colspan=2 class=TableTextNote><font size=1>* indicates mandatory fields.</font></TD></TR></TABLE>");
      out.println("</TABLE>");

      out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\" width=\"100%\">");
      out.println("<tr>");
      out.println("<td align=center>");
      out.println("<input class=button type=\"button\" name=\"save\"");
      if(newSheetTypeModel != null)
      {
        out.print(" value=\" Update \"");
      }
      else
      {
        out.print(" value=\"   Add   \"");
      }
      out.print(" onclick=\"checkbeforSubmit();\">");
      out.println("</td>");
      out.println("</tr>");
      out.println("</table></form>");
    }
  }
%>
