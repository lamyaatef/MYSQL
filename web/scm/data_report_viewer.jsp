<%-- 
    Document   : data_report_viewer
    Created on : Jan 18, 2012, 2:39:02 PM
    Author     : mabdelaal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"
           import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"              
               import="java.util.*"%>
<%
            String appName = request.getContextPath();            
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String strParamReportVal = (String) dataHashMap.get(SCMInterfaceKey.CONTROL_SELECT_PARAMETER);
            String excelLink = (String) dataHashMap.get(SCMInterfaceKey.EXPORT_EXCEL_LINK);
            HashMap<String,String> hashmapUserByid = (HashMap<String,String> ) dataHashMap.get(SCMInterfaceKey.HASHMAP_USERS_PER_LEVEL);
            String action=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="                    ;
            //if 
            System.out.println("strParamReportVal iss "+strParamReportVal);
            String buttonValue="";
%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css" />
        <title>Data Report</title>
    
    </head>
    <body>
        <center>
        <br>
        <h2>Data Report</h2>
        <br>
        <form name="datarepform" id="datarepform" action="<%=action%>" method="post" enctype="multipart/form-data">
            <%if (strParamReportVal.compareTo("1")==0){ buttonValue="Upload";%>
            <input type="file" name="<%=SCMInterfaceKey.CONTROL_FILE_UPLOAD_PARAMETER %>" id="<%=SCMInterfaceKey.CONTROL_FILE_UPLOAD_PARAMETER %>" />
            
            <%}%>
            <%if (strParamReportVal.compareTo("4")==0|| strParamReportVal.compareTo("3")==0){
                buttonValue="Download";
            if (hashmapUserByid!=null && !hashmapUserByid.isEmpty()){                
                out.println("<select name=\""+SCMInterfaceKey.CONTROL_SELECT_USER_PARAMETER +"\" id=\""+SCMInterfaceKey.CONTROL_SELECT_USER_PARAMETER+"\" >");
                out.println("<option lable=\"----Select----\" value=0 />");
            for (String userId : hashmapUserByid.keySet())
                {
            out.println("<option lable=\""+hashmapUserByid.get(userId) +"\" value="+userId+" >"+hashmapUserByid.get(userId) +"</option>");
            }
            out.println("</select>");
            }
            }%>
            <input type="button" name="subBtn" id="subBtn" value="<%=buttonValue%>" onclick="checkBeforeSubmit()"/>
            <input type="hidden" name="<%=SCMInterfaceKey.CONTROL_SELECT_PARAMETER%>" id="<%=SCMInterfaceKey.CONTROL_SELECT_PARAMETER%>" value="<%=strParamReportVal%>"/>
            <%
            if (excelLink!=null)
            {
            out.print("<br><a href=\"/SDS/file/"+excelLink+"\">Download Excel Report</a><br>");
            }
            %>
        </form>
        </center>        
    </body>    
</html>
    <script>
        function checkBeforeSubmit(){
            var actionStr = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
            actionStr += '<%=SCMInterfaceKey.CONTROL_SELECT_PARAMETER%>='+document.datarepform.<%=SCMInterfaceKey.CONTROL_SELECT_PARAMETER%>.value;
            actionStr +='&';                            
            actionStr += '<%=InterfaceKey.HASHMAP_KEY_ACTION%>=';                            
            var msg = "";
        <%if (strParamReportVal.compareTo("1") == 0) {%>                                
                var file = document.datarepform.<%=SCMInterfaceKey.CONTROL_FILE_UPLOAD_PARAMETER%>.value;
                var fileName = file.substring(file.length - 4, file.length);                                
                fileName = fileName.toLowerCase();                            
                if(file == "")
                    msg += "File field can not be empty.\n";    
                else if(fileName != ".xls" && fileName != "xlsx")
                    msg += "Invalid input. Please specify a right excel file.\n";    
                actionStr += '<%=SCMInterfaceKey.ACTION_PARAM_REPORT_UPLOAD_POS%>'; 
        <%} else {%>
                var selectedUser = document.datarepform.<%=SCMInterfaceKey.CONTROL_SELECT_USER_PARAMETER%>.value;                            
                if (selectedUser==0)
                    msg += "Please select user.\n";
                actionStr += '<%=SCMInterfaceKey.ACTION_PARAM_REPORT_EXPORT_EXCEL%>'; 
                actionStr +='&';                            
                actionStr += '<%=SCMInterfaceKey.CONTROL_SELECT_USER_PARAMETER%>='+selectedUser;
        <%}%>               
                if (msg!="")
                {
                    alert (msg);
                    return;
                }
                else
                {
                    document.datarepform.action = actionStr;
                    document.datarepform.submit(); 
                }
                
                
                
            }
    </script>
    
