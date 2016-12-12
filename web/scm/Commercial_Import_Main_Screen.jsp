<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <TITLE>Supervisors Bulk Import</TITLE>
<center>
      <h2>Commercial Data Import</h2>
</center>
   </HEAD>


   <body onkeypress = "if(event.keyCode==13){myform.submit();}">


<script>

  function submitForm()
{
   
   alert ('Please Wait Until Loading is Complete');
  document.myform.submit();
  
}

</script>
<%
String appName = request.getContextPath();

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_COMMERCIAL_DATA_UPLOAD_PROCESS; //action=

%>


<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
     

<%
    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    
    Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    
   
    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("File");
    out.println("</td>");
    out.println("<td  align=left>");
%>
   <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
<%
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
%>

    <br><br>
    <td></td>
    <input class="button" type="button" name="Submit" value="Import File" onclick="submitForm();">
    <text>&nbsp;&nbsp; .csv</text>
    </center> 
</BODY>
</HTML>
              