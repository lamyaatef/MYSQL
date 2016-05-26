<%-- 
    Document   : import_pos_cbill
    Created on : Oct 20, 2010, 8:24:47 AM
    Author     : AHMED SAFWAT
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="java.util.*"
        %>
<%
            String appName = request.getContextPath();
            String formName = "importPOSCbill";

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>IMPORT IVR activation file</title>

        <script language="JavaScript">
            function submitUploadForm(){

                if(document.<%=formName%>.importFile.value=="" ){
                    alert("Please Choose a File");
                    return;
                }
                if(document.<%=formName%>.importFile.value.lastIndexOf(".xls")==-1 && document.<%=formName%>.importFile.value.lastIndexOf(".xlsx")==-1 ){
                    alert("The Excel File Must be .xls or .xlsx file");
                    return;
                }else{
                    document.<%=formName%>.action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?action=<%=SCMInterfaceKey.ACTION_IMPORT_POS_CBILL_PROCESS %>";
                    document.<%=formName%>.submit();
                }
            }
        </script>

    </head>
    <body>

        <div align="center">
            <br>
            <br>
            <h2>Import IVR activation file</h2>
            <br>
            <br>

            <form name="<%=formName%>" method="post" enctype="multipart/form-data">

                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >

                    <tr>
                        <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:<br>(.xls or .xlsx)</font></td>

                        <td align="center"><input type="file" style="font-size: 11px;font-family: tahoma;line-height: 15px" name="importFile"></td>
                    </tr>

                    <tr>
                        <td colspan="2" align="center"><input class="button" type="button" value="save" style="font-size: 11px;font-family: tahoma;line-height: 15px" onclick="submitUploadForm();"></td>

                    </tr>

                </table>

            </form>

        </div>


    </body>
</html>
