<%-- 
    Document   : uploadPOSTarget
    Created on : Dec 4, 2011, 1:30:15 PM
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
         import="com.mobinil.sds.web.interfaces.commission.POSTargetInterface"
%>
<%@page import="com.mobinil.sds.core.system.commission.postarget.utils.CalendarUtility" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <title>Upload POS Target</title>
    </head>
    <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);    
  CalendarUtility calUtil = new CalendarUtility();
  SortedMap<Integer,Integer> sortedYears = calUtil.getYears();
  String [] filePaths = (String [])objDataHashMap.get(POSTargetInterface.ARRAY_OF_EXCEL_FILE_PATHS);
  String appName = request.getContextPath();
    %>
    <body>
        <form name="uploadPOSform" id="uploadPOSform" action="" method="POST" enctype="multipart/form-data">
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">                
                <tr>
                    <td class=TableHeader width="20%">Period Type</td>
                    <td class=TableHeader width="80%">
                        <select onchange="showDate();" name="<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME %>" id="<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME %>">
                            <option selected="selected" value="0" label="---Select---">---Select---</option>
                            <option value="<%=POSTargetInterface.CONSTANT_QUARTER_TYPE %>" label="Quarterly">Quarterly</option>
                            <option value="<%=POSTargetInterface.CONSTANT_MONTH_TYPE %>" label="Monthly">Monthly</option>                        
                            <option value="<%=POSTargetInterface.CONSTANT_WEEK_TYPE %>" label="Weekly">Weekly</option>                  
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class=TableHeader width="20%">Year</td>
                    <td class=TableHeader width="80%">
                        <select onchange="showDate();" name="<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME %>" id="<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME %>">
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
                    <td class=TableHeader width="20%">Date Type</td>
                    <td class=TableHeader width="80%">
                        <div id="txtResult">
                            <select  name="<%=POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME %>" id="<%=POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME %>">
                            <option selected="selected" value="0" label="---Select---"/>                                                        
                        </select>
                            </div>
                    </td>
                </tr>
                <tr>
                    <td class=TableHeader width="20%">POS File</td>
                    <td class=TableHeader width="80%">
                        <input type="file"  name="<%=POSTargetInterface.CONTROL_FILE_POS_FILE_UPLOAD %>" id="<%=POSTargetInterface.CONTROL_FILE_POS_FILE_UPLOAD %>"/>                                                    
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class=TableHeader align="center" width="100%"><input type="button" name="savebutton" id="savebutton" value="Save" onclick="checkBeforeSubmit()"/> </td>                    
                </tr>
                
            </table>
                    <center>
                        
                    <%
                    if (filePaths!=null){
                        String fileName = "";
                     if (filePaths[0]!=null){
                           fileName = filePaths[0].substring(filePaths[0].lastIndexOf(System.getProperty("file.separator"))+1);
                            out.print("<br><a href=\"/SDS/file/"+fileName+"\">Download Success file</a><br>");
                         }
                     if (filePaths[1]!=null){
                           fileName = filePaths[1].substring(filePaths[1].lastIndexOf(System.getProperty("file.separator"))+1);
                            out.print("<br><a href=\"/SDS/file/"+fileName+"\">Download Fail file</a>");
                         }
                                         }
                    %>
                    </center>
        </form>
                    
                    <script>
                        function checkBeforeSubmit(){
                            var type = document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME %>.value;
                            var year = document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME %>.value;
                            var date = document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME %>.value;
                            var file = document.uploadPOSform.<%=POSTargetInterface.CONTROL_FILE_POS_FILE_UPLOAD %>.value;
                            var fileName = file.substring(file.length - 4, file.length);
                            var actionStr = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?';
                            
                            
                            
                            
                            
                            fileName = fileName.toLowerCase();
                            var msg = "";
                            if (type==0)
                                msg += "Please select period type.\n";
                            if (year==0)
                                msg += "Please select year.\n";
                            if (date==0)
                                msg += "Please select date type.\n";                            
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
                                 actionStr += '<%=InterfaceKey.HASHMAP_KEY_ACTION%>=<%=POSTargetInterface.ACTION_UPLOAD_POS_TARGET%>';
                                actionStr +='&';                            
                                actionStr += '<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME%>='+document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME%>.value;
                                actionStr +='&';                            
                                actionStr += '<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME%>='+document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME%>.value;
                                actionStr +='&';
                                actionStr += '<%=POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME%>='+document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_DATE_TYPE_NAME%>.value;
                                actionStr +='&';
                                actionStr += '<%=AdministrationInterfaceKey.QUERY_STRING_TABLES%>=59';
                                actionStr +='&';
                                actionStr += '<%=AdministrationInterfaceKey.QUERY_STRING_OPERATION%>=<%=AdministrationInterfaceKey.DATA_IMPORT_INSERT%>';
                                
                                //if (false)
                                    {
                                       document.uploadPOSform.action = actionStr;
                                       document.uploadPOSform.submit(); 
                                        
                                    }
                                        
                                }
                                
                              
                            
                        }
                        
                        function stateChanged() 
    { 		
        if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
        {     
            document.getElementById("txtResult").innerHTML =  xmlHttp.responseText;             
        }        
    }
    
    function GetXmlHttpObject()
    {
        var xmlHttp=null;
        try
        {
            // Firefox, Opera 8.0+, Safari
            xmlHttp=new XMLHttpRequest();
        }
        catch (e)
        {
            //Internet Explorer
            try
            {
                xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e)
            {
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
        }
        return xmlHttp;
    }
    
    function showDate()
    { 
        
        
        var type = document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME %>.value;
        var year = document.uploadPOSform.<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME %>.value;
        
	
	{
            xmlHttp=GetXmlHttpObject()
            if (xmlHttp==null)
            {
                alert ("Browser does not support HTTP Request")
                return
            }
            
            var url="<%=request.getContextPath() %>/commission/calendar_ajax.jsp"
            
            url=url+"?<%=POSTargetInterface.CONTROL_SELECT_PERIOD_TYPE_NAME %>="+type+"&<%=POSTargetInterface.CONTROL_SELECT_YEAR_NAME %>="+year;            
            
            xmlHttp.onreadystatechange=stateChanged 
            xmlHttp.open("GET",url,true)
            xmlHttp.send(null)
            
	}
	
    }
    
                        
                    </script>
                        
    </body>
</html>
