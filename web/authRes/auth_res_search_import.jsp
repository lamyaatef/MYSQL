<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import = "com.mobinil.sds.core.system.authenticationResult.model.*"
              import ="java.io.*"              
   %>

<%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    
    Vector labelvec = (Vector) dataHashMap.get(AuthResInterfaceKey.VECTOR_LABEL);
    
    Vector<AuthSearchCategryModel> vSearchCategory = (Vector<AuthSearchCategryModel>) dataHashMap.get(AuthResInterfaceKey.VECTOR_SEARCH_CATEGORY);
   
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +AuthResInterfaceKey.ACTION_AUTH_RES_SEARCH_IMPORT_PROCESS+
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

  var desc = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_INPUT_DESCRIPTION);%>.value;

  var vFileType = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_SELECT_FILE_TYPE_ID);%>.value;
  var vSearchCategory = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_SELECT_SEARCH_CATEGORY_ID);%>.value;
  var vSimPure = document.myform.<%out.print(AuthResInterfaceKey.CONTROL_COMBO_SEARCH_BY_ID);%>.checked;
  
  if(vFileType == "")
  {
    alert("File type field can not be empty.");
    return;
  }

  if(vSearchCategory == "")
  {
    alert("Search Category field can not be empty.");
    return;
  }
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
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_SELECT_SEARCH_CATEGORY_ID);%>='+vSearchCategory;
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_SELECT_FILE_TYPE_ID);%>='+vFileType;
  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_COMBO_SEARCH_BY_ID);%>='+vSimPure;

  

  document.myform.action = document.myform.action+'&'+'<%out.print(AuthResInterfaceKey.CONTROL_INPUT_DESCRIPTION);%>='+desc;
   
  document.myform.Submit.disabled = true;
  
  document.myform.submit();
  
}
</script>

<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.LabelModel"%><HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>Authentication Result Search File Import</TITLE>
      <center>
      <h2>Authentication Result Search File Import</h2>
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
                <td width="30%">Year:</td>
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
                <td nowrap="nowrap">Search Category:</td>
                <td>
                <select  name="<%=AuthResInterfaceKey.CONTROL_SELECT_SEARCH_CATEGORY_ID%>" id="<%=AuthResInterfaceKey.CONTROL_SELECT_SEARCH_CATEGORY_ID%>">
                <option></option>
                <%
                if (vSearchCategory!=null)
                for(AuthSearchCategryModel authSearchCategryModel:vSearchCategory){               	
                	
                    String catId=  authSearchCategryModel.getCat_id();
                    String catName= authSearchCategryModel.getCat_name();
                	
                	
                	%>
         <option value="<%=catId%>"><%=catName%></option>
                <%} %>
                </select>
                </td>
                </tr>
                 <tr>
                <td>File Type:</td>
                <td>
                <select  name="<%=AuthResInterfaceKey.CONTROL_SELECT_FILE_TYPE_ID%>" id="<%=AuthResInterfaceKey.CONTROL_SELECT_FILE_TYPE_ID%>">
                <option></option>
                
         <option value="<%=AuthResInterfaceKey.CONTROL_SELECT_OPTION_SIM_ONLY %>">Sim Only</option>
         <option value="<%=AuthResInterfaceKey.CONTROL_SELECT_OPTION_SIM_DATA%>">Sim With Extra Data</option>                
                </select>
                </td>
                </tr>
                 
                <tr>
                <td>Description:</td>
                <td>
                
              <INPUT TYPE=TEXT   NAME="Description"  SIZE= 40  height=10  id="<%=AuthResInterfaceKey.CONTROL_INPUT_DESCRIPTION%>"   >
                
                </td>
                </tr>
                
                <tr>
                <td>Upload:</td>
                <td  align="left">
                <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
    </td>
                </tr>
           <tr>
                <td>Search by sim pure:</td>
                <td>
                <input type="checkbox" name="<%=AuthResInterfaceKey.CONTROL_COMBO_SEARCH_BY_ID%>" id="<%=AuthResInterfaceKey.CONTROL_COMBO_SEARCH_BY_ID%>"> 
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
              