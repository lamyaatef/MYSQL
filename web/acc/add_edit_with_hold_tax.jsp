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
 if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_GROUP_ID%>.value") == "")
    {
      alert("Transaction Group ID must not be empty.");
      return;
    }
    if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_GROUP_NAME%>.value") == "")
    {
      alert("Transaction Group Name must not be empty.");
      return;
    }
    if(!IsNumeric(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_GROUP_ID%>.value")))
    {
       alert("Transaction Group ID must be a number.");
       return; 
    }
    ACCform.submit();
  }
</script>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  WithHoldTaxModel withHoldTaxModel = (WithHoldTaxModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  <CENTER>
      <H2> With Hold Tax Data </H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  
  
  String dcmCode = "";
  float withHoldTaxNumber = 0.0f;
  float netTax = 0.0f;
  float factor = 100.0f;
  String nextAction = AccInterfaceKey.ACTION_SAVE_WITH_HOLD_TAX;
  if (withHoldTaxModel!=null)
  {
    nextAction = AccInterfaceKey.ACTION_UPDATE_WITH_HOLD_TAX;
    dcmCode = withHoldTaxModel.getDcmCode();
    withHoldTaxNumber = withHoldTaxModel.getWithHoldTax();
    netTax= (withHoldTaxNumber * factor);
  }
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_DCM_CODE+"\""+
          " value=\""+dcmCode+"\">"); 
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_WITH_HOLD_TAX+"\""+
          " value=\""+netTax+"\">"); 
  
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>DCM Code</td>
<td class=TableTextNote><input type="text" name="text_dcm_code" value="<%=dcmCode%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>With Hold Tax Number</td>
<td class=TableTextNote><input type="text" name="text_with_hold_tax" value="<%=netTax%>">%</td>

</tr>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"
      +"ACCform.submit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>
