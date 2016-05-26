<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
   %>

<%
String appName = request.getContextPath();
String ip = request.getLocalAddr();
System.out.println(ip);



    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String strUserID = (String)request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID);
    strUserID = strUserID==null ? (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID):strUserID;
    LabelModel  labelModel = (LabelModel) dataHashMap.get(AuthResInterfaceKey.VECTOR_LABEL);
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +AuthResInterfaceKey.ACTION_SIM_INFO_IMPORT_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                     +strUserID;
%>    

<script>

function submitForm()
{ 
    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    
    if(fileName != ".xls" && fileName != "xlsx")
    {
      alert("Invalid input. Please specify a right excel file.");
      return;
    }
  
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);%>='+<%=labelModel.getLabelId()%>;
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.FILE_EXT);%>='+fileName;
  document.myform.Submit.disabled = true;
  document.myform.submit();
  
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.LabelModel"%><HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>SIM Information Import</TITLE>
      <center>
      <h2>SIM Information Import</h2>
      </center>
   </HEAD>
   <script>
       <%String file = request.getParameter("fileId");
         if (file!=null){%>
      
      alert("File id is "+<%=file%>);
  <%}%>
          
   </script>

<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">

   <body onkeypress = "if(event.keyCode==13){myform.submit();}">




            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
                <tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
<%--                <tr>
                <td>Label:</td>
                <td>
                <select  name="<%=AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID%>" id="<%=AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID%>">                
                <%
                
                //for(LabelModel  model :  labelvec){
                	%>
                <option value="<%=//model.getLabelId()%>"><%=//model.getLabelName()%></option>
                <%//} %>
                </select>
                </td>
                </tr>--%>
                <tr>
                <td>Upload:</td>
                <td  align="left">
                <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
    </td>
                </tr>
           
                  

    
</table>

<BR>
<BR>
<BR>
    <center> 
    
    <input class="button" type="button" name="Submit" value="Upload" onclick="submitForm();">
   
    </center>
    </form>
   </BODY>
</HTML>
              