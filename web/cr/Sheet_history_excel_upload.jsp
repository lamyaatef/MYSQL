<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    var fileName = document.myform.myfile.value;
    var strErrorMsg = "Specified File must have a non-blank value";
		if (fileName.length==0)
		{
      document.myform.myfile.focus();
			alert(strErrorMsg);
			return false;
		}
    else
    {
      var tempString = "";      
      for (var intLoop = 0; intLoop < fileName.length; intLoop++)
      {
        if (fileName.charAt(intLoop) != " ")
        {
          tempString = tempString + fileName.charAt(intLoop);
        }
      }
      if(tempString.length==0)
      {
        document.myform.myfile.focus();
        alert("'"+fileName+"' is not a valid " + document.myform.myfile.name + ".");
        return false;
      }
    }
    document.myform.submitFile.disabled = true;
    document.myform.submit();  
  }
</SCRIPT>
<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<HTML>
   <HEAD>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
      <TITLE>untitled1</TITLE>
      <center>
      <h2>Import Sheet Serials</h2>
      </center>
   </HEAD>
   <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
     <form name="myform" action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID+"="+userID+"&"+InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_IMPORT_FILE);%>"
      method="post" enctype="multipart/form-data">

      <%
      String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
      %>
      
      <TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1 align = 'center'>
      <tr><td  class=TableHeader nowrap width = 20% align = 'middle'>
       File</td>
        </td><td>
        <input type="file" name="myfile" size="30">
        </td></tr>
        </table>
        <br />
        <center>
    <input class="button" type="button" name="submitFile" value="Submit your file" onclick="checkbeforSubmit();">
    </center>
    </form>
   </BODY>
</HTML>

