<%@page import="com.mobinil.sds.core.system.nomadFile.model.NomadLabelModel"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.LabelModel"%>
<%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
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
      <TITLE>Nomad File Import</TITLE>

      <h2>Upload Nomad Data</h2>

   </HEAD>


   <body onkeypress = "if(event.keyCode==13){myform.submit();}">


<script>
function submitForm()
{
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

  document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_LABEL_ID+"");%>='+document.myform.<%out.print(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);%>.value;
  document.myform.submit();
  
}
</script>
<%
String appName = request.getContextPath();

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +AdministrationInterfaceKey.ACTION_NOMAD_IMPORT_PROCESS; //action=



%>


<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
     

<%
    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    
    Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Table");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+">");
    out.println("<option></option>");
    for(int k= 0; k<tableVec.size(); k++)
    {
      DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      if(tableModel.getTableName().compareToIgnoreCase(AdministrationInterfaceKey.TEXT_NOMAD_DATA_TABLE_NAME)==0)
        out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    }
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Operation");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+">");
    out.println("<option></option>");
    out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\">"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");
    
    Vector labelvec = (Vector) dataHashMap.get(AuthResInterfaceKey.VECTOR_LABEL);
    %>
    <tr>
                <td class=TableHeader nowrap align=center>Label</td>
                <td nowrap align="left">
                <select  name="<%=AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID%>" id="<%=AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID%>">
                <option></option>
                <%
                String labelId= "";
                String labelname="";
                
                for(int i=0; i<labelvec.size();i++){
                	
                    NomadLabelModel  model = (NomadLabelModel)   labelvec.get(i);
                    labelId=  model.getLabelId();
                    labelname= model.getLabelName();
                	
                	
                	%>
                <option value="<%=labelId%>"><%=labelname%></option>
                <%} 
               
                %>
                </select>
                </td>
                </tr>
                
    

    <%
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

    <br><br>
    <td></td>
    <input class="button" type="button" name="Submit" value="Import File" onclick="submitForm();">
    </center>
    </form>
   </BODY>
</HTML>
              