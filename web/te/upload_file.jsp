<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.te.*"
              import ="java.io.*"  
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <TITLE>Upload SIM Numbers</TITLE>
      <center>
      <h2>Upload Text Of SIM Numbers</h2>
      </center>
   </HEAD>



   <body onkeypress = "if(event.keyCode==13){uploadFileForm.submit();}">


<script>
function submitForm()
{

    var fileName = document.uploadFileForm.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    if(fileName != ".txt")
    {
      alert("Invalid input. Please specify a right txt file.");
      return;
    }
    

  document.uploadFileForm.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(TaskInterfaceKey.ACTION_PROCESS_FILE);%>';
  document.uploadFileForm.submit();
  
}
</script>
<%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String taskId = (String)dataHashMap.get(TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID); 
    String strUserID = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="+
                    TaskInterfaceKey.ACTION_PROCESS_FILE+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                    +strUserID+
                    "&"+TaskInterfaceKey.CONTROL_HIDDEN_TASK_ID+"="
                    +taskId;
%>    
<form name="uploadFileForm" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
<%            

    out.println("<center>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>"); 
    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("File");
    out.println("</td>");
    out.println("<td nowrap align=left>");
%>
   <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
<%
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
%>

    
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="submitForm();">
    </center>
    </form>
   </BODY>
</HTML>
              