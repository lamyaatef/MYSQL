<%-- 
    Document   : upload_rated_report
    Created on : 23/12/2010, 15:37:17
    Author     : Ahmed Adel
--%>
<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.scm.dao.*"%>

<html>
    <%  String appName = request.getContextPath();%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <%
    HashMap dataHashMap=(HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String faildRecords=(String)dataHashMap.get(CommissionInterfaceKey.NUMBER_OF_FAILD_RECORDS);
    String passRecords=(String)dataHashMap.get(CommissionInterfaceKey.NUMBER_OF_PASS_RECORDS);
    Vector FaildSIM=(Vector)dataHashMap.get(CommissionInterfaceKey.VECTOR_OF_FAILD_SIM);
    %>

    <body>
        <center>

        <h2> Upload Rated Activity File Report</h2>

        <br>

       <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="80%" border=1>
           <tr>
               <td class=TableHeader>
                   <label>Number of imported Records</label>
               </td>
               <td class=Tabletext>
                   <label><%=passRecords%></label>
               </td>
           </tr>
           <tr>
               <td class=TableHeader>
                   <label>Number of faild Records</label>
               </td>
               <td class=Tabletext>
                   <label><%=faildRecords%></label>
               </td>
           </tr>
           <tr>
               <td class=TableHeader>
                   <label>Number of Lines Readed</label>
               </td>
               <td class=Tabletext>
                   <label><%=Integer.parseInt(faildRecords)+Integer.parseInt(passRecords)%></label>
               </td>
           </tr>
       </table>
<%
                            String Slach=System.getProperty("file.separator");
                            String baseDirectory = request.getRealPath(Slach+"commission"+Slach+"upload"+Slach);
                            String excelLink = PoiWriteExcelFile.getExcelForRatedActivation(FaildSIM, baseDirectory);
                            dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH, excelLink);
                            dataHashMap.put(InterfaceKey.MODULE_SUB_PATH, "commission"+Slach+"upload"+Slach);
                            session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
%>
<br>
<br>

<div name="Excel" id="Excel" align="center">
    <form action="com.mobinil.sds.web.controller.UploadingFileServlet" name="GenerateSheet" id="GenerateSheet" method="post">

    <input class="button"  type="submit" name="Submit" id="download" value="Download Excel Sheet" onclick="Sheet();"/>

    </form>
</div>
</center>
    </body>
    <script>
    function Sheet()
    {
        document.GenerateSheet.Submit.disabled=true;
        document.GenerateSheet.submit();


    }

</script>
</html>
