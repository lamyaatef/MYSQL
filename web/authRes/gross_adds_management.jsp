<%-- 
    Document   : gross_adds_management
    Created on : Jan 24, 2012, 12:58:53 PM
    Author     : mabdelaal
--%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResGrossAddsFileModel"%>
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
        
    </head>
    <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);    
  CalendarUtility calUtil = new CalendarUtility();  
  HashMap<Integer,String> months = calUtil.getMonths();  
  Vector<AuthResGrossAddsFileModel> vecGrossAddsFiles = (Vector<AuthResGrossAddsFileModel>)objDataHashMap.get(AuthResInterfaceKey.VECTOR_OF_ALL_GROSS_ADDS_FILES);
  String appName = request.getContextPath();
    %>
    <body>     
    <center>
        <h3>Gross Adds files uploaded</h3>
        <form name="manageform" id="manageform" action="" method="POST" >
            <input name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" id="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" type="hidden" value="<%=AuthResInterfaceKey.ACTION_GROSS_ADDS_FILE_DELETE%>"/>
            <input name="<%=AuthResInterfaceKey.CONTROL_HIDDEN_DELETE_FILE_ID%>" id="<%=AuthResInterfaceKey.CONTROL_HIDDEN_DELETE_FILE_ID%>" type="hidden" value=""/>
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">
                <tr>
                <td class=TableHeader nowrap width="20%">Creation Date</td>
                <td class=TableHeader nowrap width="15%">Record Count</td>
                <td class=TableHeader width="15%">Month</td>
                <td class=TableHeader width="15%">Year</td>
                <td class=TableHeader width="20%">Type</td>                
                <td class=TableHeader width="15%">Delete</td>                
                    </tr>
                    
                    <%
                    for (AuthResGrossAddsFileModel model : vecGrossAddsFiles)
                                               {
                        
                        String typeName = "Unknown";

                        if (model.getType_id().compareTo("1")==0 )
                            {
                            typeName = "Voice";
                            }
                        else if  (model.getType_id().compareTo("4")==0 )
                            {
                            typeName = "MNP";
                            }
                        else if  (model.getType_id().compareTo("5")==0 )
                            {
                            typeName = "MIGRATION";
                            }
                        else if  (model.getType_id().compareTo("2")==0 )
                            {
                            typeName = "PREPAID";
                            }
else if  (model.getType_id().compareTo("3")==0 )
                            {
                            typeName = "DATALINE";
                            }



                out.println("<tr><td class=TableHeader nowrap width=\"20%\">"+model.getCreation_date()+"</td>");
                out.println("<td class=TableHeader nowrap width=\"15%\">"+model.getRecord_count()+"</td>");
                out.println("<td class=TableHeader nowrap width=\"15%\">"+months.get(Integer.valueOf(model.getMonth_number()))+"</td>");
                out.println("<td class=TableHeader nowrap width=\"15%\">"+model.getYear_number()+"</td>");
                out.println("<td class=TableHeader nowrap width=\"20%\">"+typeName+"</td>");
                out.println("<td class=TableHeader nowrap width=\"15%\"><input type=\"submit\" name=deleteBtn id=deleteBtn value=\"Delete\" onclick=\"document.manageform."+AuthResInterfaceKey.CONTROL_HIDDEN_DELETE_FILE_ID+".value='"+model.getFile_id()+"';\"></td></tr>");
                    }
                    %>
            </table>
            </form>
            </center>
    </body>
</html>
