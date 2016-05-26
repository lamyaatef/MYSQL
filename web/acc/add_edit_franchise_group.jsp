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
 if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_TEXT_FRANCHISE_GROUP_NAME%>.value") == "")
    {
      alert("Franchise Group Name must not be empty.");
      return;
    }
    
    ACCform.submit();
  }
</script>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  FranchiseGroupModel franchiseGroupModel = (FranchiseGroupModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  <CENTER>
      <H2> Franchise Group List </H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  
  
  String groupName = "";
  String groupId = "";
  String groupDescription = "";
  String nextAction = AccInterfaceKey.ACTION_SAVE_FRANCHISE_GROUP;
  if (franchiseGroupModel!=null)
  {
    nextAction = AccInterfaceKey.ACTION_UPDATE_FRANCHISE_GROUP;
    groupId = franchiseGroupModel.getFranchiseGroupId();
    groupName = franchiseGroupModel.getFranchiseGroupName();
    groupDescription = franchiseGroupModel.getFranchiseGroupDescription();
  }
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+"\""+
          " value=\""+groupId+"\">"); 
  
  
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>Franchise Group Name</td>
<td class=TableTextNote><input type="text" name="franhise_group_name" value="<%=groupName%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>Franchise Group Description</td>
<td class=TableTextNote><input type="text" name="franchise_group_description" value="<%=groupDescription%>"></td>

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
