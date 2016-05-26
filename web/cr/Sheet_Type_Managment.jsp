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
 * Sheet_Type_Managment.jsp:
 * Display the system Users and each user roles.
 * 
 * showSheetTypes(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display all types of sheets. Allow of mass update for all sheet types statuses.
 *
 * @version	1.01 May 2004
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
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Sheet Type Managment</H2>
    </Center>
    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="SheetTypeManagment" method="post">

      <%String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
      %>
      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_TYPES_STATUSES);%>">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID);%>"
      value="<%out.print(userID);%>">

      <input type="hidden" name="<%out.print(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE_ID);%>" value=0>

      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="30%" nowrap align=center>Sheet Type</td>
          <td width="40%" nowrap align=center>Sheet Type Description</td>
          <td width="20%" nowrap align=center>Status</td>
          <td width="10%" nowrap align=center>Edit</td>
        </tr>	
        <%
        showSheetTypes(request, response, out);
        %>
      </TABLE>
      <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=center>
            <input class=button type="button" name="new" value=" New Type " 
            onclick="document.SheetTypeManagment.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(ContractRegistrationInterfaceKey.ACTION_NEW_SHEET_TYPE);%>';SheetTypeManagment.submit();">
            <input class=button type="button" name="update" value="Update Types" onclick="SheetTypeManagment.submit();" disabled>
          </td>
        </tr>
      </table>   
    </form>
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
   * showSheetTypes method: 
   * Display all types of sheets. Allow of mass update for all sheet types statuses.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showSheetTypes(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty())
    {
      SheetTypeDto sheetTypeDto = (SheetTypeDto)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
      for(int i = 0; i<sheetTypeDto.getSheetTypeModelsSize(); i++)
      {
        SheetTypeModel newSheetTypeModel = sheetTypeDto.getSheetTypeModel(i);
        String sheetTypeID = newSheetTypeModel.getSheetTypeId();
        String sheetTypeName = newSheetTypeModel.getSheetTypeName();
        String sheetTypeDesc = newSheetTypeModel.getSheetTypeDesc();
        String sheetTypeStatusID = newSheetTypeModel.getSheetTypeStatusId();
        out.println("<TR class=TableTextNote>");
        out.println("<TD class=TableTextNote>"+sheetTypeName+"</TD>");
        if(sheetTypeDesc == null)
        {
          sheetTypeDesc = "";
        }
        out.println("<TD class=TableTextNote>"+sheetTypeDesc+"</TD>");
        out.println("<TD class=TableTextNote align=middle>");
        
        out.println("<SELECT name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+sheetTypeID+
                    " onchange=\"document.SheetTypeManagment.update.disabled = false;\">");
        for(int j = 0; j<sheetTypeDto.getSheetTypeStatusModelsSize(); j++)
        {
          SheetTypeStatusModel newSheetTypeStatusModel = sheetTypeDto.getSheetTypeStatusModel(j);
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
        out.println("</TD>");
        out.println("<TD class=TableTextNote align=middle>");
        out.println("<input class=button type=\"button\" name=\"edit\" value=\"   Edit   \"");
        out.println("onclick=\"document.SheetTypeManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_EDIT_SHEET_TYPE+"';");
        out.println("document.SheetTypeManagment."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE_ID+".value='"+sheetTypeID+"';");
        out.println("SheetTypeManagment.submit();\">");
        out.println("</TD>");
        out.println("</TR>");
      }
    }
  }
%>