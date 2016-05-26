<%@page import="com.mobinil.mcss.level_relation_management.model.GenWarehouse"%>
<%@ page contentType="text/html;charset=windows-1252"
         import ="com.mobinil.sds.core.utilities.Utility"
         import="org.apache.commons.fileupload.*"
         import="java.util.*"                   
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.web.interfaces.ac.*"
         import="com.mobinil.sds.web.interfaces.dm.*"
         import="com.mobinil.sds.core.system.sa.importdata.model.*"
         import ="java.io.*" 
         import="com.mobinil.mcss.level_relation_management.LevelRelationManagementInterfaceKey"
         %>

<%
    System.out.println("in jsp");
    String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    Vector warehouses=(Vector)dataHashMap.get(LevelRelationManagementInterfaceKey.CONTROL_WAREHOUSE_DATA);
    //stem.out.println("user id ssssssssss"+strUserID);


    String formAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            + InterfaceKey.HASHMAP_KEY_ACTION + "="
            + LevelRelationManagementInterfaceKey.ACTION_UPLOAD_WAREHOUSE_TO_LEVEL_ZERO_PROCESS
            + "&" + InterfaceKey.HASHMAP_KEY_USER_ID + "="
            + strUserID;

    //    +strChannelId;
%>    

<script>

    function submitForm()
    {

        var warehouse= document.myform.<%out.print(LevelRelationManagementInterfaceKey.CONTROL_SELECT_WAREHOUSE);%>.value;
        if (warehouse=='')
        {
            alert('Please Select a warehouse');
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
  
        document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES + "");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION + "=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value
        document.myform.action = document.myform.action+'&'+'<%out.print(LevelRelationManagementInterfaceKey.CONTROL_SELECT_WAREHOUSE);%>='+warehouse;
   
        document.myform.Submit.disabled = true;
  
        document.myform.submit();
  
    }

</script>
<HTML>
    <HEAD>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
        <TITLE>Warehouse To Level Zero</TITLE>
    <center>
        <h2>Warehouse To Level Zero</h2>
    </center>
</HEAD>



<body onkeypress = "if(event.keyCode==13){myform.submit();}">

    <form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">


        <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
            <tr class=TableHeader>
                <td>

                    <font size="4" >  <b>Upload</b> </font> <br></td><td></td>
            </tr>

            <br>
              <tr>
                <td width="15%">Warehouse</td>
                <td>
                    <select name="<%=LevelRelationManagementInterfaceKey.CONTROL_SELECT_WAREHOUSE%>" id="<%=LevelRelationManagementInterfaceKey.CONTROL_SELECT_WAREHOUSE%>">
                        <option></option>
                        <%
                            if (warehouses != null || warehouses.size() != 0) {
                                for (int i = 0; i < warehouses.size(); i++) {
                                    GenWarehouse gw = (GenWarehouse) warehouses.get(i);

                        %>
                        <option  value="<%=gw.getWarehouseId()%>"><%=gw.getWarehouseName()%></option>
                        <%}
                            }%> 
                    </select>
                    <%

//                        Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
                        out.println("<center>");

                        //  out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
                        out.println("<TR style='display:none'>");
                        // out.println("<td class=TableHeader nowrap align=center>");
                        //out.println("Table");
                        out.println("</TD>");
                        out.println("<Select name=" + AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES + " style=\"Display:none\">");
//                        for (int k = 0; k < tableVec.size(); k++) {
//                            DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);
//                            out.println("<option value=\"" + tableModel.getTableId() + "\">" + tableModel.getTableName() + "</option>");
//                        }
                        out.println("</Select>");
                        out.println("</td>");
                        out.println("</tr>");
                        //out.println("<TR>");
                        //out.println("<td class=TableHeader nowrap align=center>");
                        // out.println("Operation");
                        // out.println("</TD>");        
                        // out.println("<td nowrap align=left>");
                        out.println("<Select name=" + AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION + " style=\"Display:none\">");
                        out.println("<option></option>");
                        out.println("<option value=\"" + AdministrationInterfaceKey.DATA_IMPORT_INSERT + "\" selected>" + AdministrationInterfaceKey.DATA_IMPORT_INSERT + "</option>");
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
                    <BR>
                    <BR>
                    <BR>
            <center> 

                <input class="button" type="button" name="Submit" value="Upload" onclick="submitForm();">

            </center>
    </form>
</BODY>
</HTML>