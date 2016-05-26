<%@ page   import = "java.util.*"
                  import = "com.mobinil.sds.web.interfaces.*"
                  import = "java.io.*"
                  import = "com.mobinil.sds.core.system.cr.sheet.model.*"
                  import = "com.mobinil.sds.web.interfaces.cr.*"
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>
<body>
<SCRIPT language=JavaScript>
function IsNumeric(sText)
{
   var ValidChars = "0123456789";
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
  function saveSheetPercentagefunction()
  {
  var curpercentage =0;
    var listofinputs = document.getElementsByTagName("input");
    for(var n=0;n<listofinputs.length;n++)
    {
        if(listofinputs[n].type == "text")
        {
         if(IsNumeric(listofinputs[n].value))
         {
            curpercentage = parseInt(listofinputs[n].value);
            if(curpercentage < 0 || curpercentage > 100)
            {
                alert("Percentage doesn't have valid value");
                return;
            }
          }
          else
          {
              alert("Percentage doesn't have valid value");
              return;
          }
        }
    }
    document.myform.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value = "<%=ContractRegistrationInterfaceKey.ACTION_SAVE_SHEET_LOCAL_PERCENTAGE%>";  
    myform.submit();
  }
</script>  
    <CENTER>
      <H2>Sheet List</H2>
    </CENTER>
 <%
    String appName = request.getContextPath();
    %>
    <form name="myform" id="myform" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" method="post">
    <%
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" id=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+"\">");
    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


    String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

    Vector vecSheet = (Vector) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    String batchId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT);
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT+"\" id=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT+"\" value=\""+batchId+"\">");
    %>
    <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <TR class=TableHeader>
        <th width="50%" nowrap align=middle>Sheet Serial</th>
        <th width="50%" nowrap align=middle>Local Auth Percentage</th>
    </TR>	    
    <%
    for(int k= 0; k<vecSheet.size(); k++)
    {
      SheetModel sheetModel = (SheetModel) vecSheet.get(k); 
      String sheetId = sheetModel.getSheetId();
      String sheetSerialId = sheetModel.getSheetName();
      String sheetLocalAuthPercentage = sheetModel.getSheetLocalAuthPercentage();
      %>
      <tr class=TableTextNote>
          <td><%=sheetSerialId%></td>
          <td align='center'>
          <%
          out.println("<input type='text' name='"+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_LOCAL_PERCENTAGE+"_"+sheetSerialId+"' id='"+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_LOCAL_PERCENTAGE+"_"+sheetSerialId+"' value='"+sheetLocalAuthPercentage+"'>");
          %>
          </td>
      </tr>    
      <%
    }
    %>
    </table>
       <center>
           <input class="button" type="button" value="Save Sheet Percentage" onclick="saveSheetPercentagefunction();">
      </center>
</form>
</body>
</html>
