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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<body>
  <CENTER>
      <H2> Sheet Warning </H2>
  </CENTER>
    <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector sheetStatusTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
  String sheetWarningTypeId = "";
  String sheetWarningTypeName = "";
  String suggestedSheetStatusTypeId = "";
  String suggestedSheetStatusTypeName = "";
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    SheetWarningTypeModel sheetWarningTypeModel = (SheetWarningTypeModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    sheetWarningTypeId = sheetWarningTypeModel.getSheetWarningTypeId();
    sheetWarningTypeName = sheetWarningTypeModel.getSheetWarningTypeName();
    suggestedSheetStatusTypeId = sheetWarningTypeModel.getSuggestedSheetStatusTypeId();
    suggestedSheetStatusTypeName = sheetWarningTypeModel.getSuggestedSheetStatusTypeName();
    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SQL_OPERATION+"\""+
                  " value=\"UPDATE\">");  
  }
  else
  {
    out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SQL_OPERATION+"\""+
                  " value=\"INSERT\">");  
  }

  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_WARNING_TYPE_ID+"\""+
                  " value=\""+sheetWarningTypeId+"\">");  

%>

      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableTextNote>
          <td align='center'>Warning</td>
          <td align='left'>
            <input type='text' name='<%=SFRInterfaceKey.INPUT_TEXT_WARNING%>' id='<%=SFRInterfaceKey.INPUT_TEXT_WARNING%>' value='<%=sheetWarningTypeName%>'>
          </td>
        </tr>
        <TR class=TableTextNote>
          <td align='center'>Suggested Status</td>
          <td align='left'>
            <select name='<%=SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID%>' id='<%=SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID%>'>
            <%
            for(int i=0;i<sheetStatusTypes.size();i++)
            {
              SheetStatusTypeModel sheetStatusTypeModel = (SheetStatusTypeModel)sheetStatusTypes.get(i);
              String sheetStatusTypeId = sheetStatusTypeModel.getSheetStatusTypeId();
              String sheetStatusTypeName = sheetStatusTypeModel.getSheetStatusTypeName();
              String statusSelected = "";
              if(sheetStatusTypeId.compareTo(suggestedSheetStatusTypeId)==0)
              { 
                  statusSelected = "selected";
              }
              %>
              <option value='<%=sheetStatusTypeId%>' <%=statusSelected%>><%=sheetStatusTypeName%></option>
              <%
            }
            %>
            </select>
          </td>
        </tr>
        </table>

        <br><br>
        <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
          
        <%
         out.println("<input type='button' class='button' name='save' id='save' value='Save' "+
                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SFR_SAVE_SHEET_WARNING+"';"+
                            "SFRform.submit();\">");
         
        %>
        </center>
  </form>
</body>
</html>
