<%-- 
    Document   : generate_excel_template
    Created on : Dec 19, 2012, 2:30:35 PM
    Author     : Mohamed Youssef
--%>

<%@ page import="com.mobinil.sds.core.system.cr.excelImport.*"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.*"
         %>

<%
    String appName = request.getContextPath();
    HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <%
            ExcelImport gen = new ExcelImport(userID);
            String templatePath = request.getRealPath("/scm/Temp/");
            System.out.println("before the export");
            gen.exportTemp(templatePath + "/Temp.xls", templatePath + "/generated_Sheet.xls");
            System.out.println("after the export");
            System.out.print(appName);
            out.println("<form action=\"" + appName + "/scm/Temp/generated_Sheet.xls\" name=\"GenerateSheet\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"" + InterfaceKey.HASHMAP_KEY_USER_ID + "\" value=" + userID + ">");

            out.println("</form>");
            out.println("<script type=\"text/javascript\">");
            out.println("GenerateSheet.submit();");
            out.println("</script>");
        %>

    </center>


</body>
</html>
