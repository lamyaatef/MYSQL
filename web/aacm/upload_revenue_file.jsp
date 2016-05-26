<%-- 
    Document   : upload_revenue_filw
    Created on : Dec 21, 2011, 1:20:45 PM
    Author     : mabdelaal
--%>

<%@page import="com.mobinil.sds.core.system.sa.importdata.ErrorInfo"%>
<%@page import="com.mobinil.sds.web.interfaces.aacm.AACMInterfaceKey"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"         
         import="com.mobinil.sds.web.interfaces.sa.*"                  
%>
<%@page import="com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <title>Upload Revenue File</title>
    </head>
    <%  
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String msg = (String) objDataHashMap.get(AACMInterfaceKey.ERROR_MSG);
  Vector<ErrorInfo> errorVec = (Vector<ErrorInfo>) objDataHashMap.get(AACMInterfaceKey.ERROR_VECTOR);
  CalendarUtility calUtil = new CalendarUtility();
  SortedMap<Integer,Integer> sortedYears = calUtil.getYears();
  HashMap<Integer,String> sortedMonths = calUtil.getMonths();  
  String appName = request.getContextPath();
    %>
    <body>
        <form name="uploadRevform" id="uploadRevform" action="" method="POST" enctype="multipart/form-data">
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">                
                <tr>
                    <td class=TableHeader width="20%">Year</td>
                    <td class=TableHeader width="80%">
                        <select  name="<%=AACMInterfaceKey.CONTROL_SELECT_YEAR_NAME %>" id="<%=AACMInterfaceKey.CONTROL_SELECT_YEAR_NAME %>">
                            <option selected="selected" value="0" label="---Select---">---Select---</option>
                            <%
                            for(int year : sortedYears.keySet())
                            {
                             out.println("<option value=\""+year+"\" label=\""+sortedYears.get(year)+"\">"+sortedYears.get(year)+"</option>");   
                            }                            
                            %>
                            
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class=TableHeader width="20%">Month</td>
                    <td class=TableHeader width="80%">
                        <select  name="<%=AACMInterfaceKey.CONTROL_SELECT_MONTH_NAME %>" id="<%=AACMInterfaceKey.CONTROL_SELECT_MONTH_NAME %>">
                            <option selected="selected" value="0" label="---Select---">---Select---</option>
                            <%
                            for(int month : sortedMonths.keySet())
                            {
                             out.println("<option value=\""+month+"\" label=\""+sortedMonths.get(month)+"\">"+sortedMonths.get(month)+"</option>");   
                            }                            
                            %>
                            
                        </select>
                    </td>
                </tr>                
                <tr>
                    <td class=TableHeader width="20%">POS File</td>
                    <td class=TableHeader width="80%">
                        <input type="file"  name="<%=AACMInterfaceKey.CONTROL_FILE_REVENUE_FILE_UPLOAD %>" id="<%=AACMInterfaceKey.CONTROL_FILE_REVENUE_FILE_UPLOAD %>"/>                                                    
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class=TableHeader align="center" width="100%">
                        <input type="button" name="savebutton" id="savebutton" value="Save" onclick="checkBeforeSubmit()"/> </td>                    
                </tr>
                
            </table>  
            <br>        
            <center>
            <%
            if (msg!=null)
                out.print(msg);
            %>        
            </center>        
            <%
            if (errorVec!=null && !errorVec.isEmpty())
            {
            %>
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">                
            <tr>
            <td class=TableHeader style="white-space: nowrap">Row Number</td>
            <td class=TableHeader style="white-space: nowrap">Cell name</td>
            <td class=TableHeader style="white-space: nowrap">Error Message</td>
            </tr>    
            <%    
            for(ErrorInfo errorInfo : errorVec)
            {
            out.print("<tr>");
            out.print("<td class=TableHeader style=\"white-space: nowrap\">");
            out.print(errorInfo.getRowNum()+1);
            out.print("</td>");
            out.print("<td class=TableHeader style=\"white-space: nowrap\">");
            out.print(errorInfo.getCellName());
            out.print("</td>");
            out.print("<td class=TableHeader style=\"white-space: nowrap\">");
            out.print(errorInfo.getErrorMsg());
            out.print("</td>");            
            out.print("</tr>");
            }
            %>
            </table>
            <%
            }
            %>
        </form>
                    
                    <script>
                        function checkBeforeSubmit(){
                            
                            var year = document.uploadRevform.<%=AACMInterfaceKey.CONTROL_SELECT_YEAR_NAME %>.value;
                            var month = document.uploadRevform.<%=AACMInterfaceKey.CONTROL_SELECT_MONTH_NAME %>.value;
                            var file = document.uploadRevform.<%=AACMInterfaceKey.CONTROL_FILE_REVENUE_FILE_UPLOAD %>.value;
                            var fileName = file.substring(file.length - 4, file.length);
                            var actionStr = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
                            
                            
                            
                            
                            
                            fileName = fileName.toLowerCase();
                            var msg = "";                            
                            if (year==0)
                                msg += "Please select year.\n";
                            if (month==0)
                                msg += "Please select month type.\n";                            
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
                                 actionStr += '<%=InterfaceKey.HASHMAP_KEY_ACTION%>=<%=AACMInterfaceKey.ACTION_UPLOAD_REVENUE_FILE_PROCESS%>';
                                actionStr +='&';                                                            
                                actionStr += '<%=AACMInterfaceKey.CONTROL_SELECT_YEAR_NAME%>='+document.uploadRevform.<%=AACMInterfaceKey.CONTROL_SELECT_YEAR_NAME%>.value;
                                actionStr +='&';
                                actionStr += '<%=AACMInterfaceKey.CONTROL_SELECT_MONTH_NAME%>='+document.uploadRevform.<%=AACMInterfaceKey.CONTROL_SELECT_MONTH_NAME%>.value;
                                actionStr +='&';
                                actionStr += '<%=AdministrationInterfaceKey.QUERY_STRING_TABLES%>=60';
                                actionStr +='&';
                                actionStr += '<%=AdministrationInterfaceKey.QUERY_STRING_OPERATION%>=<%=AdministrationInterfaceKey.DATA_IMPORT_INSERT%>';
                                
                                //if (false)
                                    {
                                       document.uploadRevform.action = actionStr;
                                       document.uploadRevform.submit(); 
                                        
                                    }
                                        
                                }
                                
                              
                            
                        }                        
    
                    </script>
                        
    </body>
</html>
