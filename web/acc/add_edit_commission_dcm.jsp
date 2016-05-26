<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"
%>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
<body>
<script>
/*function IsNumeric(sText)
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
}*/

function checkBeforeSubmit()
{
 if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_OLD_DCM_CODE%>.value") == "")
    {
      alert("Old DCM Code must not be empty.");
      return;
    }

 if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_NEW_DCM_CODE%>.value") == "")
 {
   alert("New DCM Code must not be empty.");
   return;
 }
    
    ACCform.submit();
  }
</script>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  CommissionDcmFixModel commissionDcmFixModel = (CommissionDcmFixModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  <CENTER>
      <H2> Commission DCM </H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  
  
  
  
  String oldDcmCode = "";
  String newDcmCode = "";
  String oldDcmId = "";
  String newDcmId = "";
  String nextAction = AccInterfaceKey.ACTION_SAVE_COMMISSION_DCM;
  if (commissionDcmFixModel!=null)
  {
    nextAction = AccInterfaceKey.ACTION_UPDATE_COMMISSION_DCM;
    oldDcmCode = commissionDcmFixModel.getOldDcmCode();
    newDcmCode = commissionDcmFixModel.getNewDcmCode();
    oldDcmId = commissionDcmFixModel.getOldDcmId();
    newDcmId = commissionDcmFixModel.getNewDcmId();
  }
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_OLD_DCM_ID+"\""+
          " value=\""+oldDcmId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_NEW_DCM_ID+"\""+
          " value=\""+newDcmId+"\">"); 
  
  
  
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>Old DCM Code</td>
<td class=TableTextNote><input type="text" name="old_dcm_code" value="<%=oldDcmCode%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>New DCM Code</td>
<td class=TableTextNote><input type="text" name="new_dcm_code" value="<%=newDcmCode%>"></td>

</tr>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"
      +"checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>
