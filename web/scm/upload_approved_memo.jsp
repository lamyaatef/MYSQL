<%-- 
    Document   : upload_approved_memo
    Created on : Jan 8, 2012, 1:30:15 PM
    Author     : mabdelaal
--%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"         
         import="com.mobinil.sds.web.interfaces.sa.*"                  
         import="com.mobinil.sds.web.interfaces.scm.*"                  
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <title>Upload Approved Memo</title>
    </head>
    <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);      
   Vector<String> errorMessages  = ( Vector<String>)objDataHashMap.get(SCMInterfaceKey.VECTOR_OF_ERROR_MESSAGES_IN_IMPORT_POS_APPROVED);
  
  String appName = request.getContextPath();
    %>
    <body>
    <center>
        <h2>Upload Approved Memo</h2>
    </center>
        <form name="uploadMemoform" id="uploadMemoform" action="" method="POST" enctype="multipart/form-data">
            <table style="BORDER-COLLAPSE: collapse" width="80%" border="1" align="center" cellpadding="0" cellspacing="1">                                
                <tr>
                    <td class=TableHeader width="20%">Excel File</td>
                    <td class=TableHeader width="80%">
                        <input type="file"  name="<%=SCMInterfaceKey.CONTROL_EXCEL_APPROVED_FILE %>" id="<%=SCMInterfaceKey.CONTROL_EXCEL_APPROVED_FILE %>"/>                                                    
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class=TableHeader align="center" width="100%"><input type="button" name="uploadbutton" id="uploadbutton" value="Upload" onclick="checkBeforeSubmit()"/> </td>                    
                </tr>
                
            </table>
                    <center>                        
                    <%
                    out.print("<br>");                            
                    out.print("<br>");                            
                    if (errorMessages!=null && !errorMessages.isEmpty())
                    {
                      out.print("<table style=\"BORDER-COLLAPSE: collapse\" width=\"80%\" border=\"1\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\">");                            
                      out.print("<tr><td align=center class=TableHeader width=\"100%\">Error Message</td></tr>");                            
                    for (String errorMsg : errorMessages)
                        {
                        out.print("<tr><td class=TableHeader width=\"100%\">"+errorMsg+"</td></tr>");
                        }
                    out.print("</table>" );                            
                    }
                    %>
                    </center>
        </form>
                    
                    <script>
                        function checkBeforeSubmit(){                            
                            var file = document.uploadMemoform.<%=SCMInterfaceKey.CONTROL_EXCEL_APPROVED_FILE %>.value;
                            var fileName = file.substring(file.length - 4, file.length);
                            var actionStr = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
                            
                            
                            
                            
                            
                            fileName = fileName.toLowerCase();
                            var msg = "";                            
                            if(file == "")
                                msg += "File field can not be empty.\n";    
                            else if(fileName != ".xls" && fileName != "xlsx")
                                msg += "Invalid input. Please specify a right excel file.\n";    
                            if (msg!="")
                                {
                                    alert (msg);
                                    return;
                                }
                                else
                                {
                                 actionStr += '<%=InterfaceKey.HASHMAP_KEY_ACTION%>=<%=SCMInterfaceKey.ACTION_PROCESS_UPLOAD_APPROVED_MEMO%>';
                                 document.uploadMemoform.action = actionStr;
                                 document.uploadMemoform.submit(); 
                                }
                        }
                        
                    </script>
    </body>
</html>
