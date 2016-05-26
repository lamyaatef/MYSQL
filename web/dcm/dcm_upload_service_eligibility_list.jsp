<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.dcm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"  
              import="com.mobinil.sds.core.system.dcm.service.model.*"
              %>

<HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <TITLE>Upload POS Service Eligibility</TITLE>
      <center>
      <h2>Upload POS Service Eligibility</h2>
      </center>
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
    
  document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value
  document.myform.action = document.myform.action+'&'+'<%out.print(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+"=");%>'+document.myform.<%out.print(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);%>.value
  document.myform.submit();
  
}
</script>
<%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Vector serviceListVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +DCMInterfaceKey.ACTION_DCM_UPLOAD_SERVICE_ELIGIBILITY_LIST_PROCESS+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                    +strUserID;
%>    
<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
<%                  
    Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    //out.println("<TR>");
    //out.println("<td class=TableHeader nowrap align=center>");
    //out.println("Table");
    //out.println("</TD>");        
    //out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+" style=\"Display:none\">");
    for(int k= 0; k<tableVec.size(); k++)
    {
      DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    }
    out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");
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
    out.println("Service");
    out.println("</td>");
    out.println("<td nowrap align=left>");
%>
   <select name="<%=DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID%>" id="<%=DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID%>">
    <%
    for(int g=0;g<serviceListVector.size();g++)
    {
      ServiceModel serviceModel = (ServiceModel)serviceListVector.get(g);
      String serviceId = serviceModel.getPosServiceId();
      String serviceName = serviceModel.getPosServiceName();
      %>
      <option value="<%=serviceId%>"><%=serviceName%></option>
      <%
    }
    %>
    </select>
<%
    out.println("</td>");
    out.println("</tr>");
    
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
              