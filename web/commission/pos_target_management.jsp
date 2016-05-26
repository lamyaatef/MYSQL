<%-- 
    Document   : pos_target_management
    Created on : Dec 8, 2011, 12:31:30 PM
    Author     : mabdelaal
--%>

<%@page import="com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"         
         import="com.mobinil.sds.web.interfaces.sa.*"         
         import="com.mobinil.sds.web.interfaces.commission.POSTargetInterface"
         import="com.mobinil.sds.core.system.commission.postarget.model.*"
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <title>POS Target Management</title>
    </head>
    <body>
        <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);          
  String appName = request.getContextPath();
  CalendarUtility cal = new CalendarUtility();
  HashMap<Integer, String> months = cal.getMonths();
  Vector<POSTargetFileModel> fileVecModel= (Vector<POSTargetFileModel>)objDataHashMap.get(POSTargetInterface.VECTOR_OF_FILES);
    %>
        <form name="managementPOSform" id="managementPOSform" action="" method="POST">
             <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=POSTargetInterface.ACTION_DELETE_FILE_POS_TARGET%>">
             <input type="hidden" name="<%=POSTargetInterface.CONTROL_HIDDEN_FILE_ID_UPLOAD%>" id="<%=POSTargetInterface.CONTROL_HIDDEN_FILE_ID_UPLOAD%>" value="">
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">                
                <tr>
                    <td class=TableHeader width="14%">Type</td>
                    <td class=TableHeader width="14%">Year</td>
                    <td class=TableHeader width="14%">Quarter</td>
                    <td class=TableHeader width="14%">Month</td>
                    <td class=TableHeader width="14%">Week</td>
                    <td class=TableHeader width="14%">Delete</td>
                    <td class=TableHeader width="15%">Download</td>                    
                    
                </tr>
                
                    <% for (POSTargetFileModel posModel :fileVecModel){%>
                    <tr>
                    <td class=TableHeader width="14%"><%=posModel.getPeriodTypeName() %></td>
                    <td class=TableHeader width="14%"><%=posModel.getYear() %></td>
                    <td class=TableHeader width="14%"><%=posModel.getQuartar()==0 ? "" : "Q"+posModel.getQuartar() %></td>
                    <td class=TableHeader width="14%"><%=posModel.getMonth()==0 ? "" : months.get(posModel.getMonth()) %></td>
                    <td class=TableHeader width="14%"><%=posModel.getWeek()==0 ? "" : posModel.getWeek() %></td>
                    <td class=TableHeader width="14%">
                        <input type="submit" value="Delete" onclick="document.managementPOSform.<%=POSTargetInterface.CONTROL_HIDDEN_FILE_ID_UPLOAD%>.value=<%=posModel.getFileId() %>;" ></td>
                    <td class=TableHeader style="white-space: nowrap" width="25%"><a href="/SDS/file/<%=posModel.getFileId()+"_"+POSTargetInterface.SUCCESS_FILE_NAME+".xlsx" %>">Download Success file</a>
                    <br> <a href="/SDS/file/<%=posModel.getFileId()+"_"+POSTargetInterface.FAILD_FILE_NAME+".xlsx" %>">Download Failed file</a>
                    </td>                    
                    </tr>
                    <%}%>
                    
                
                
            </table>
        </form>
                    
    </body>
</html>
