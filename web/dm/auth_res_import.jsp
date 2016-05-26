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


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    
    Vector labelvec = (Vector) dataHashMap.get(AuthResInterfaceKey.VECTOR_LABEL);
   
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +AuthResInterfaceKey.ACTION_AUTH_RES_IMPORT_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                     +strUserID;
                  
                //    +strChannelId;
%>    

<script>

function submitForm()
{

  var year1= document.myform.<%out.print(AuthResInterfaceKey.CONTROL_SELECT_YEAR);%>.value;
  var month1 = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_SELECT_MONTH);%>.value;
  var labelId = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);%>.value;

 // var labelName = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_NAME);%>.value;



    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    fileName = fileName.substring(fileName.length - 4, fileName.length);
    fileName = fileName.toLowerCase();
    if(fileName != ".txt")
    {
      alert("Invalid input. Please specify a right Txt file.");
      return;
    }
  
  
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_SELECT_YEAR);%>='+year1;
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_SELECT_MONTH);%>='+month1;
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);%>='+labelId;



//  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_NAME);%>='+labelName;
   
  document.myform.Submit.disabled = true;
  
  document.myform.submit();
  
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.LabelModel"%><HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Authentication Result Import</TITLE>
      <center>
      <h2>Authentication Result Import</h2>
      </center>
   </HEAD>

<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">

   <body onkeypress = "if(event.keyCode==13){myform.submit();}">




            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
                <tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
                  
                <br>
                <tr>
                <td width="15%">Year:</td>
                <td>
                <select name="<%=AuthResInterfaceKey.CONTROL_SELECT_YEAR %>" id="<%=AuthResInterfaceKey.CONTROL_SELECT_YEAR %>">
                <option></option>
                <%for(int i=2000; i<2020;i++){ %>
                <option value="<%=i %>"><%=i %></option>
                <%} %>
                </select>
                </td>
                </tr>
                
                <tr>
                <td>Month:</td>
                <td>
                <select  name="<%=AuthResInterfaceKey.CONTROL_SELECT_MONTH%>" id="<%=AuthResInterfaceKey.CONTROL_SELECT_MONTH%>">
                <option></option>
                <%
                String [] months = {" " ,"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nove", "Dec"};
                for(int i=1; i<months.length;i++){
                	%>
                <option value="<%=i%>"><%=months[i]%></option>
                <%} %>
                </select>
                    </td>
                </tr>
                <tr>
                <td>Label:</td>
                <td>
                <select  name="<%=AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID%>" id="<%=AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID%>">
                <option></option>
                <%
                
                for(int i=0; i<labelvec.size();i++){
                	
                	LabelModel  model = (LabelModel)   labelvec.get(i);
                    String labelId=  model.getLabelId();
                    String labelname= model.getLabelName();
                	
                	
                	%>
         <option value="<%=labelId%>"><%=labelname%></option>
                <%} %>
                </select>
                </td>
                </tr>
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
              