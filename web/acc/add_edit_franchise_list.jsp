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
 if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_FRANCHISE_CODE%>.value") == "")
    {
      alert("Franchise Code must not be empty.");
      return;
    }
   
    ACCform.submit();
  }
</script>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  FranchiseListModel franchiseListModel = (FranchiseListModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String frachiseGroupId = (String)objDataHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);  
  System.out.println("Franchise Group id isssssssssssss " + frachiseGroupId);
  %>
  <CENTER>
      <H2> Franchise </H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+"\""+
          " value=\""+frachiseGroupId+"\">"); 
  
  
  String franchiseCode = "";
  String nextAction = AccInterfaceKey.ACTION_SAVE_FRANCHISE_LIST;
  if (franchiseListModel!=null)
  {
    nextAction = AccInterfaceKey.ACTION_UPDATE_FRANCHISE_LIST;
	  franchiseCode = franchiseListModel.getFranchiseCode();
  }
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE+"\""+
          " value=\""+franchiseCode+"\">"); 
  
  
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>Franchise Code</td>
<td class=TableTextNote><input type="text" name="franchise_code" value="<%=franchiseCode%>"></td>

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
