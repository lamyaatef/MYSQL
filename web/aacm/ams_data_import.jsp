<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.aacm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
              %>
              <%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    System.out.println("The user id issssssssssss " + strUserID);
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +AACMInterfaceKey.ACTION_UPLOAD_AMS_DATA_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                    +strUserID;
                   
%>   

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <TITLE>AMS Data Import</TITLE>
      <center>
      <h2>AMS Data Import</h2>
      </center>
   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">


<script>
function submitForm()
{
  var year= document.myform.<%out.print(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR);%>.value;
  var month = document.myform.<%out.print(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH);%>.value;
  var tableId = document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value;
  if (tableId=='')
  {
  alert('Please Select a Table');
  return;
  }

  var operation = document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value;
  if (operation =='')
  {
  alert('Please Select an Operation');
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
    if(fileName != ".xls")
    {
      alert("Invalid input. Please specify a right excel file.");
      return;
    }

    document.myform.action = document.myform.action+'&'+'<%out.print(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR);%>='+year;
    document.myform.action = document.myform.action+'&'+'<%out.print(AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH);%>='+month;    
  document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value
  document.myform.Submit.disabled = true;
  document.myform.submit();
  
}
</script>

<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
<%         
                  
    // Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    // out.println("<center>");

    // out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    // out.println("<TR style='display:none'>");
    // out.println("<td class=TableHeader nowrap align=center>");
    // out.println("Table");
    // out.println("</TD>");        
    // out.println("<td nowrap align=left>");
    // out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+">");
    // for(int k= 0; k<tableVec.size(); k++)
    // {
      // DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      // out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    // }
    // out.println("</Select>");
    // out.println("</td>");    
    // out.println("</tr>");

    // out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+" style=\"Display:none\">");
    // out.println("<option></option>");
    // out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\" selected>"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    //out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    // out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");
	%>
	<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
	<tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
                <tr>
                <td width="15%">Year:</td>
                <td>
                <select name="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR %>" id="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR%>">
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
                <select  name="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH%>" id="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH%>">
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
	<%         
                  
    Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

  //  out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
      out.println("<TR style='display:none'>");
   // out.println("<td class=TableHeader nowrap align=center>");
    //out.println("Table");
     out.println("</TD>");       
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+" style=\"Display:none\">");
    for(int k= 0; k<tableVec.size(); k++)
    {
      DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    }
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");
    //out.println("<TR>");
    //out.println("<td class=TableHeader nowrap align=center>");
   // out.println("Operation");
   // out.println("</TD>");        
   // out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+" style=\"Display:none\">");
    out.println("<option></option>");
    out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\" selected>"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    //out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");


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

    <center>
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="submitForm();">
    </center>
    </form>
   </BODY>
</HTML>
              