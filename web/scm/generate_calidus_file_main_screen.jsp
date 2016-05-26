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
      <TITLE>Generate Calidus File</TITLE>

      <h2>Generate Calidus File</h2>

   </HEAD>


   <body onkeypress = "if(event.keyCode==13){myform.submit();}">

<%
String appName = request.getContextPath();

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_GENERATE_CALIDUS_FILE_PROCESS;

%>


<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
     

<%
    //HashMap dataHashMap = null;
    //dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    //Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    
    out.println("<TR>");
   // out.println("<td class=TableHeader nowrap align=center>");
    //out.println("Table");
   // out.println("</TD>");        
    //out.println("<td nowrap align=left>");
    //out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+">");
   // out.println("<option></option>");
    //for(int k= 0; k<tableVec.size(); k++)
    //{
   //   DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
   //   out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
   // }
   // out.println("</Select>");
   // out.println("</td>");    
   // out.println("</tr>");
    
    
    /*out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Operation");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+">");
    out.println("<option></option>");
    out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\">"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
   
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");


    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("File");
    out.println("</td>");
    out.println("<td nowrap align=left>");*/
%>
  
<%
  //  out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
%>

    
    <input class="button" type="submit" name="Submit" value="Generate Calidus File" onclick="submitForm();">
    </center>
    </form>
   </BODY>
</HTML>
              