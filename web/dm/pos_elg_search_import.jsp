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
    

   
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +DMInterfaceKey.ACTION_ELG_CHK_SEARCH_IMPORT_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                     +strUserID;
                  
                //    +strChannelId;
%>    

<script>

function submitForm()
{

  var year1= document.myform.<%out.print(DMInterfaceKey.CONTROL_SELECTED_YEAR);%>.value;
  var month1 = document.myform.<%out.print(DMInterfaceKey.CONTROL_SELECTED_MONTH);%>.value;


  var desc = document.myform.<%out.print(DMInterfaceKey.CONTROL_INPUT_DESCRIPTION);%>.value;



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
  
  
  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_SELECTED_YEAR);%>='+year1;
  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_SELECTED_MONTH);%>='+month1;




  document.myform.action = document.myform.action+'&'+'<%out.print(DMInterfaceKey.CONTROL_INPUT_DESCRIPTION);%>='+desc;
   
  document.myform.Submit.disabled = true;
  
  document.myform.submit();
  
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>

<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%><HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>POS ELG Search File Import</TITLE>
      <center>
      <h2>POS ELG Search File Import</h2>
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
                <select name="<%=DMInterfaceKey.CONTROL_SELECTED_YEAR %>" id="<%=DMInterfaceKey.CONTROL_SELECTED_YEAR  %>">
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
         <select name="<%=DMInterfaceKey.CONTROL_SELECTED_MONTH %>" id="<%=DMInterfaceKey.CONTROL_SELECTED_MONTH  %>">
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
                <td>Description:</td>
                <td>
                
              <INPUT TYPE=TEXT   NAME="Description"  SIZE= 40  height=10  id="<%=DMInterfaceKey.CONTROL_INPUT_DESCRIPTION%>"   >
                
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
              