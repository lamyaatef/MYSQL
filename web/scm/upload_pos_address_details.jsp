<%@page import="com.mobinil.sds.web.interfaces.dcm.DCMInterfaceKey"%>
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
        <TITLE>Data Import</TITLE>

    <h2>Data Import</h2>

</HEAD>



<body onkeypress = "if(event.keyCode==13){myform.submit();}">


    <script>
        function submitForm()
        {
            document.myform.submit();
        }
  
  
    </script>
    <%
        String appName = request.getContextPath();

        String formAction = appName + "/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                + InterfaceKey.HASHMAP_KEY_ACTION + "="
                + DCMInterfaceKey.ACTION_UPLOAD_GENERATED_FILE;

    %>


    <form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">


        <%
            HashMap dataHashMap = null;
            dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

            Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
            out.println("<center>");

            out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");

            out.println("</tr>");

            out.println("<td nowrap align=left>");
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