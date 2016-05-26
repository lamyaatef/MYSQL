
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mobinil.sds.web.interfaces.aacm.AACMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.aacm.model.AccmRevFile"%>
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
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
        <title>Revenue File Management</title>
    </head>
    <body>
        <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);          
  String appName = request.getContextPath();
  CalendarUtility cal = new CalendarUtility();
  HashMap<Integer, String> months = cal.getMonths();
  Vector<AccmRevFile> fileVecModel= (Vector<AccmRevFile>)objDataHashMap.get(AACMInterfaceKey.VECTOR_OF_FILES);
  String filePath = (String)objDataHashMap.get(AACMInterfaceKey.EXPORT_FILE_REVENUE_PATH);
  fileVecModel = fileVecModel == null ? new Vector<AccmRevFile>() : fileVecModel;
  SimpleDateFormat formater = new SimpleDateFormat();
  formater.applyPattern("dd-MM-yyyy");
    %>
        <form name="managementPOSform" id="managementPOSform" action="" method="POST">
             <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="">
             <input type="hidden" name="<%=AACMInterfaceKey.CONTROL_HIDDEN_FILE_ID_UPLOAD%>" id="<%=AACMInterfaceKey.CONTROL_HIDDEN_FILE_ID_UPLOAD%>" value="">
            <table style="BORDER-COLLAPSE: collapse" width="60%" border="1" align="center" cellpadding="0" cellspacing="1">                
                <tr>                    
                    <td class=TableHeader width="17%">Year</td>
                    <td class=TableHeader width="17%">Month</td>
                    <td class=TableHeader width="17%">Rows</td>
                    <td class=TableHeader width="17%">Creation</td>
                    <td class=TableHeader width="17%">Delete</td>
                    <td class=TableHeader width="15%">Export</td>                    
                    
                </tr>
                
                    <% for (AccmRevFile accmRevFile :fileVecModel){%>
                    <tr>
                    
                    <td class=TableHeader width="17%"><%=accmRevFile.getYearNumer() %></td>                    
                    <td class=TableHeader width="17%"><%= months.get(accmRevFile.getMonthNumer().intValue()) %></td>
                    <td class=TableHeader width="17%"><%=accmRevFile.getSumInsertedRows() %></td>
                    <td class=TableHeader width="17%" style="white-space: nowrap"><%=formater.format(accmRevFile.getCreationDate())%></td>
                    <td class=TableHeader width="17%" align="center">
                    <input type="submit" value="Delete" onclick="document.managementPOSform.<%=AACMInterfaceKey.CONTROL_HIDDEN_FILE_ID_UPLOAD%>.value=<%=accmRevFile.getFileId() %>;
                           document.managementPOSform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=AACMInterfaceKey.ACTION_DELETE_REV_FILE %>';" ></td>
                    <td class=TableHeader align="center" style="white-space: nowrap" width="15%">
                    <input type="submit" value="Export" onclick="document.managementPOSform.<%=AACMInterfaceKey.CONTROL_HIDDEN_FILE_ID_UPLOAD%>.value=<%=accmRevFile.getFileId() %>;
                           document.managementPOSform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=AACMInterfaceKey.ACTION_EXPORT_REV_FILE%>';" ></td>
                    </td>                    
                    </tr>
                    <%}%>
                    
                
                
            </table>
                    <center>
                    <%
                    
                    if (filePath!=null){
                           String fileName = filePath.substring(filePath.lastIndexOf(System.getProperty("file.separator"))+1);
                            out.print("<br><a href=\"/SDS/file/"+fileName+"\">Download "+fileName+"</a><br>");
                         }
                    %>
                    </center>
        </form>
                    
    </body>
</html>
