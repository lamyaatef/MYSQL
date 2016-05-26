<%-- 
    Document   : upload_achiv_prepaid
    Created on : Jan 24, 2012, 12:58:33 PM
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
         import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"
%>
<%@page import="com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <title>Achievement Pre-paid File Upload </title>
    </head>
    <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);    
  String errorMsg = (String)objDataHashMap.get(AuthResInterfaceKey.ERROR_MESSAGE);
  CalendarUtility calUtil = new CalendarUtility();
  SortedMap<Integer,Integer> sortedYears = calUtil.getYears();  
  HashMap<Integer,String> months = calUtil.getMonths();
  String appName = request.getContextPath();
  
    %>
    
    <body>
        <form name="uploadform" id="uploadform" action="" method="POST" enctype="multipart/form-data">
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">
                <tr>
                <td class=TableHeader width="20%">Month</td>
                <td class=TableHeader width="80%">
                        <select  name="<%=AuthResInterfaceKey.CONTROL_SELECT_MONTH_NUMBER %>" id="<%=AuthResInterfaceKey.CONTROL_SELECT_MONTH_NUMBER %>">
                            <option selected="selected" value="0" label="---Select---">---Select---</option>
                            <%
                            for(Integer monNo : months.keySet())
                            {
                            out.println("<option value=\""+monNo+"\" label=\""+months.get(monNo) +"\">"+months.get(monNo)+"</option>");
                            }
                            %>
                        </select>
                    </td>
                    </tr>
                <tr>
                <td class=TableHeader width="20%">Year</td>
                <td class=TableHeader width="80%">
                        <select  name="<%=AuthResInterfaceKey.CONTROL_SELECT_YEAR_NUMBER %>" id="<%=AuthResInterfaceKey.CONTROL_SELECT_YEAR_NUMBER %>">
                            <option selected="selected" value="0" label="---Select---">---Select---</option>
                            <%
                            for(Integer yearNo : sortedYears.keySet())
                            {
                            out.println("<option value=\""+sortedYears.get(yearNo)+"\" label=\""+sortedYears.get(yearNo) +"\">"+sortedYears.get(yearNo)+"</option>");
                            }
                            %>
                        </select>
                    </td>
                    </tr>
                <td class=TableHeader width="20%">File</td>
                <td class=TableHeader width="80%">                        
                    <input type="file" name="<%=AuthResInterfaceKey.CONTROL_FILE_UPLOAD %>" id="<%=AuthResInterfaceKey.CONTROL_FILE_UPLOAD %>" />
                    </td>
                    </tr>
                    <tr>
                    <td colspan="2" class=TableHeader align="center" width="100%"><input type="button" name="savebutton" id="savebutton" value="Upload" onclick="checkBeforeSubmit()"/> </td>                    
                </tr>
            </table>
                    <input type="hidden" name="<%=AuthResInterfaceKey.CONTROL_HIDDEN_UPLOAD_FILE_TYPE_ID %>" id="<%=AuthResInterfaceKey.CONTROL_HIDDEN_UPLOAD_FILE_TYPE_ID %>" value="2"/>
        </form>
        <script>
            <%if (errorMsg!=null) out.println("alert('"+errorMsg+"')");%>
                        function checkBeforeSubmit(){                            
                            var typeId = document.uploadform.<%=AuthResInterfaceKey.CONTROL_HIDDEN_UPLOAD_FILE_TYPE_ID %>.value;
                            var year = document.uploadform.<%=AuthResInterfaceKey.CONTROL_SELECT_YEAR_NUMBER %>.value;
                            var month = document.uploadform.<%=AuthResInterfaceKey.CONTROL_SELECT_MONTH_NUMBER %>.value;
                            var file = document.uploadform.<%=AuthResInterfaceKey.CONTROL_FILE_UPLOAD %>.value;
                            var fileName = file.substring(file.length - 4, file.length);
                            var actionStr = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
                            
                            fileName = fileName.toLowerCase();
                            var msg = "";
                            if (month==0)
                                msg += "Please select month.\n";
                            if (year==0)
                                msg += "Please select year.\n";                                                     
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
                                 actionStr += '<%=InterfaceKey.HASHMAP_KEY_ACTION%>=<%=AuthResInterfaceKey.ACTION_UPLOAD_ACHIV_PREPAID_PROCESS%>';
                                actionStr +='&';                            
                                actionStr += '<%=AuthResInterfaceKey.CONTROL_SELECT_MONTH_NUMBER%>='+month;
                                actionStr +='&';                            
                                actionStr += '<%=AuthResInterfaceKey.CONTROL_SELECT_YEAR_NUMBER%>='+year;
                                actionStr +='&';
                                actionStr += '<%=AuthResInterfaceKey.CONTROL_HIDDEN_UPLOAD_FILE_TYPE_ID%>='+typeId;
                                
                                
                                //if (false)
                                    {
                                       document.uploadform.action = actionStr;
                                       document.uploadform.submit(); 
                                        
                                    }
                                        
                                }
                                
                              
                            
                            }</script>
    </body>
</html>
