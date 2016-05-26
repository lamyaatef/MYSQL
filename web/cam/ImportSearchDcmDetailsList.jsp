<%--
    Document   : Attachment
    Created on : 05/01/2009, 03:09:04 م
    Author     : Medhat Osama
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "mgt_form";
        String page_header="Import Search DCM Details List File";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
               
        
%>
<html>
    <head>
        <%
        //HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        
       // String appName = request.getContextPath();
//        String attach_seq = request.getParameter("attach_id");
        //String attach_seq=(String)dataHashMap.get(InterfaceKey.ATTACH_ID);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Import File</title>
        <script language="JavaScript">
            var file_name;
            function checkBefore()
            {
                var input = document.getElementById("file");
                var split_input=input.value.split("\\");
                file_name=split_input[split_input.length-1];
                var file_type_arr = file_name.split(".");
                var file_type = file_type_arr[file_type_arr.length-1];
                if(file_type != "xls")
                {
                    alert('Please select Microsoft Excel file.');
                    return;
                }
                if(file_path=="")
                    alert('Please specify file to attach.');
                else{
                    //alert("document.UploadPayment.attach_id.value='"+"'");
                    //document.UploadChannelPaymentStatus.attach_id.value='';
                    //alert("hellllllllo");
                    document.UploadSearchDcmDetailsList.submit();
                }
             
            }
            var num=1;
            var file_path="";
            function CreateNewFileInput(inputFile)
            {
                file_path=inputFile.value;
                split_input=file_path.split("\\");
                file_name=split_input[split_input.length-1]
                nameID=inputFile.name; 
                eval("document.UploadSearchDcmDetailsList."+nameID+".name='"+file_name+"';");
                eval("document.UploadSearchDcmDetailsList."+nameID+".id='"+file_name+"';");             
            }
        </script>
    </head>

    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">

    <body>
    <center><h2> <%=page_header%></h2></center>
        <form ENCTYPE="multipart/form-data" action="<%=appName%>/cam/UploadSearchDcmDetailsList.jsp?" name="UploadSearchDcmDetailsList" id="UploadSearchDcmDetailsList" method="post">

            <table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                <tr class=TableHeader>
                    <td>
                <font size="4" ><b>Import</b></font> <br></td><td></td></tr>
                <br>

                <tr class="TableTextNode">

                    <td >
                        <div id="divText"> <b>File path:</b></div>
                    </td>
                    <td>

                        <div id="divFile">
                            <input type="file" name="file" id="file" size="45" onchange="CreateNewFileInput(this);"/>
                        </div>

                    </td>

                </tr>


                <tr  class="TableTextNode">
                    <td >
                        <div id="divText1"></div>
                    </td>

                    <td >
                        <div id="divFile1"></div>
                    </td>

                </tr>
            </table>

            <br>
            <input type="button" class="button"  value="Import" onclick="checkBefore();"/>
            <input type="hidden" name="attach_id" id="attach_id"/>

        </form>
    </body>
</html>