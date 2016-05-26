<%@ page contentType="text/html;charset=windows-1256"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.cr.*"
         
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
   </HEAD>
   <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
     <form name="myform" action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID+"="+userID+"&"+InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_IMPORT_FILE);%>"
      method="post" enctype="multipart/form-data">
        Specify your File:<br />
        <input type="file" name="myfile" size="100">
        <br />
        <br />
    <input class="button" type="button" name="submitFile" value="Submit your file" onclick="checkbeforSubmit();">
    </form>
   </BODY>
</HTML>
