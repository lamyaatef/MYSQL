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
 if(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_AGENT_ID%>.value") == "")
    {
      alert("Agent ID must not be empty.");
      return;
    }

 else if(!IsNumeric(eval("document.SFRform.<%=SFRInterfaceKey.INPUT_TEXT_AGENT_ID%>.value")))
 {
       alert("Agent ID must be a number.");
       return; 
 }
    
 SFRform.submit();
  }
</script>


<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String flag = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
  %>
  <CENTER>
      <H2> Batch </H2>
  </CENTER>
    <form name='SFRform' id='SFRform' action='' method='post'>
    
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   
     
     out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_FLAG+"\""+
             " value=\""+flag+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  String agentId= "";
  
  %>
  
  <center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>   
<tr>
<td class=TableHeader nowrap>Agent ID</td>
<td class=TableTextNote><input type="text" name="text_agent_id" value="<%=agentId%>"></td>

</tr>

</table>
</center>
<br>
<center>

<%
out.println("<input type='button' class='button' name='save' id='save' value='Save' "+
                   "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SAVE_NEW_BATCH+"';"+
                   "checkBeforeSubmit();\">");



      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>
  