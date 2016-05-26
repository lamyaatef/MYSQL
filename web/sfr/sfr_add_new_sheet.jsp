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
<body onload = "document.getElementById('text_sheet_serial').focus();">
<script language=JavaScript>
function checkEnter()
{
	//alert (event.keyCode);
    if (event.keyCode==13) 
    {
	   
	    event.keyCode=9; 
	   // alert (event.keyCode);
	    return event.keyCode ;
	    
	 }
}
</script>
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
 if(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_SHEET_SERIAL%>.value") == "")
    {
      alert("Sheet Serial must not be empty.");
      return;
    }

 if(!IsNumeric(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_SHEET_SERIAL%>.value")))
 {
       alert("Sheet Serial must be a number.");
       return; 
 }

 if(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_POS_CODE%>.value") == "")
 {
   alert("POS Code must not be empty.");
   return;
 }

 if(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_POS_AGENT_CODE%>.value") == "")
 {
   alert("POS Agent Code must not be empty.");
   return;
 }

 if(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_CONTRACTS_COUNT%>.value") == "")
 {
   alert("Contracts Count must not be empty.");
   return;
 }

  if(!IsNumeric(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_CONTRACTS_COUNT%>.value")))
 {
       alert("Contracts Count must be a number.");
       return; 
 }
    
 SFRform.submit();
  }
</script>

<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String batchId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
  String flag = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
  %>
  <CENTER>
      <H2> Sheet </H2>
  </CENTER>
    <form name='SFRform' id='SFRform' action='' method='post'>
    
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_FLAG+"\""+
          " value=\""+flag+"\">");
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+
          " value=\""+batchId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+"\""+
          " value=\""+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_TOTAL+"\">");  
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+"\""+
          " value=\""+SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_ACCEPTED+"\">");  
  
  
  String sheetSerial= "";
  String posCode = "";
  String posAgentCode = "";
  String contractsCount = "";
  %>
  
  <center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>   
<tr>
<td class=TableHeader nowrap>Sheet Serial</td>
<td class=TableTextNote><input onkeydown = "checkEnter(event);" type="text" name="text_sheet_serial"  id = "text_sheet_serial" value="<%=sheetSerial%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>POS Code</td>
<td class=TableTextNote><input onkeydown = "checkEnter(event);"  type="text" name="text_pos_code" value="<%=posCode%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>Second POS Code</td>
<td class=TableTextNote><input onkeydown = "checkEnter(event);" type="text" name="text_pos_agent_code" value="<%=posAgentCode%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>Contracts Count</td>
<td class=TableTextNote><input onkeydown = "checkEnter(event);" type="text" name="text_contracts_count" value="<%=contractsCount%>"></td>

</tr>

</table>
</center>
<br>
<center>

<%
out.println("<input type='button' class='button' name='save' id='save' value='Save' "+
                   "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SAVE_NEW_SHEET+"';"+
                   "checkBeforeSubmit();\">");



      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>
  