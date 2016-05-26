<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<body>
    <CENTER>
      <H2> Manage Sheet Warnings </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector sheetWarningTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID+"\""+
                  " value=\""+"\">");  

%>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td align='center'>Warning</td>
          <td align='center'>Suggested Status</td>
          <td align='center'>Edit</td>
          <!--td align='center'>Delete</td-->
        </tr>  
<%
          for(int w=0;w<sheetWarningTypes.size();w++)
          {
            SheetWarningTypeModel sheetWarningTypeModel = (SheetWarningTypeModel)sheetWarningTypes.get(w);
            String sheetWarningTypeId = sheetWarningTypeModel.getSheetWarningTypeId();
            String sheetWarningTypeName = sheetWarningTypeModel.getSheetWarningTypeName();
            String suggestedSheetStatusTypeId = sheetWarningTypeModel.getSuggestedSheetStatusTypeId();
            String suggestedSheetStatusTypeName = sheetWarningTypeModel.getSuggestedSheetStatusTypeName();
            %>
            <tr class=<%=InterfaceKey.STYLE[w%2]%>>
            <td align='center'><%=sheetWarningTypeName%></td>
            <td align='center'><%=suggestedSheetStatusTypeName%></td>
            <td align='center'>
              <%
                  out.println("<input type='button' class='button' name='edit"+sheetWarningTypeId+"' id='edit"+sheetWarningTypeId+"' value='Edit' "+
                                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SFR_EDIT_SHEET_WARNING+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID+".value = '"+sheetWarningTypeId+"';"+
                                            "SFRform.submit();\">");
         
              %>
            </td>
            <!--td align='center'>
              <%
                  out.println("<input type='button' class='button' name='delete"+sheetWarningTypeId+"' id='delete"+sheetWarningTypeId+"' value='Delete' "+
                                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SFR_DELETE_SHEET_WARNING+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID+".value = '"+sheetWarningTypeId+"';"+
                                            "SFRform.submit();\">");
         
              %>
            </td-->
            </tr>
            <%
          }
%>
    </table>
    <br><br>
    <center>
    <%
                  out.println("<input type='button' class='button' name='add' id='add' value='Add New Warning' "+
                                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SFR_ADD_SHEET_WARNING+"';"+
                                            "SFRform.submit();\">");
         
    %>
    </center>
    </form>
</body>
</html>
