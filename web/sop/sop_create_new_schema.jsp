<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<script>
    function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
    
    function checkBeforeSubmit()
    {
      var schemaCodeObj = document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE%>");
      
      var schemaCode = schemaCodeObj.value;
      if(schemaCode.length=='5' && schemaCode.charAt(0)=='s' && schemaCode.charAt(1)=='c' && IsNumeric(schemaCode.charAt(2))&& IsNumeric(schemaCode.charAt(3))&& IsNumeric(schemaCode.charAt(4)))
      {
        SOPform.submit();
      }
      else 
      {
        alert("Schema code doesn't match the schema code pattern");
      }
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String suggestedSchemaCode = "";
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE))
  {
    suggestedSchemaCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE);
  }
  
  Vector vecSchemaList = (Vector) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
%>   
    <CENTER>
      <H2> Copy Schema </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
%> 

      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Schema&nbsp;Code *</TD>
          <TD class=TableTextNote colSpan=3><INPUT type='text' maxlength=5 style="WIDTH: 100px; HEIGHT: 22px" size=66 name="<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE%>" id="<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_CODE%>" value="<%=suggestedSchemaCode%>"></td>
          <TD class=TableTextNote width=\"20%\">Schema Code Pattern i.e : sc123</TD>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Schema&nbsp;Name *</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' style="WIDTH: 452px; HEIGHT: 22px" size=40 name="<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_NAME%>" id="<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_NAME%>" value=""></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Schema&nbsp;Description</TD>
          <TD class=TableTextNote colSpan=4><textarea rows=5 cols=50 name="<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_DESCRIPTION%>" id="<%=SOPInterfaceKey.INPUT_TEXT_SCHEMA_DESCRIPTION%>"></textarea></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote>Schema Base</td>
          <TD class=TableTextNote colspan=4>
            <select name='<%=SOPInterfaceKey.INPUT_SELECT_SCHEMA_ID%>' id='<%=SOPInterfaceKey.INPUT_SELECT_SCHEMA_ID%>'>
              <%
                for (int i=0; i<vecSchemaList.size(); i++)
                {
                    SchemaModel schemaModel = (SchemaModel) vecSchemaList.get(i); 
                    String schemaId = schemaModel.getSchemaId();
                    String schemaCode = schemaModel.getSchemaCode();
                    String schemaName = schemaModel.getSchemaName();
                    %>
                    <option value='<%=schemaId%>'><%=schemaCode%> - <%=schemaName%></option>  
                    <%
                }            
              %>
            </select>
          </TD>
        </tr>
       </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"saveSchema\" id=\"saveSchema\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SAVE_SCHEMA+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

      %>
      </center>
       
</form>
</body>
</html>
