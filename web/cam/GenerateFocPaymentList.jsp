<%-- 
    Document   : GenerateFocPaymentList
    Created on : Dec 24, 2012, 1:02:05 PM
    Author     : Mohamed Youssef
--%>
<%@ page import="com.mobinil.sds.core.system.cr.excelImport.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
%>

<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <title>Generate payment List</title>
    </head>
    <body>
        <center>
        <%
        ExcelImport gen = new ExcelImport(userID);
        String templatePath = request.getRealPath("/cam/template/");
        System.out.println("before the export");
        gen.exportTemplateDataWarehouse(templatePath+"/Tmp.xls",templatePath+"/generated_List.xls") ;
        System.out.println("after the export");
        System.out.print(appName);
        out.println("<form action=\""+appName+"/cam/template/generated_List.xls\" name=\"GenerateList\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
        
        out.println("</form>");
        out.println("<script type=\"text/javascript\">");
        out.println("GenerateList.submit();");
        out.println("</script>");
        %>
        
    </center>
    </body>
</html>
