<%@ page import="com.mobinil.sds.core.system.cam.excelImport.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
%>

<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
  String file_path = (String)dataHashMap.get(MemoInterfaceKey.CONTROL_FILE_NAME);
  file_path = appName+"/cam/camMemosFiles/generated XLS files/"+file_path;
%>

<%@page import="com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey"%><html>
  <head>
    <meta http-equiv="Content-Type" content="APPLICATION/EXCL; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body>
    <center>
      <%   
        
       // ExcelImport gen = new ExcelImport(userID);
        //String templatePath = request.getRealPath("/cam/template/");
      //  System.out.println("path in jsp: "+templatePath);
      //  gen.exportTemplate(templatePath+"/Template.xls",templatePath+"/generated.xls") ;
        //java.io.File f = new java.io.File("def.xls");
        // out.println(f.exists());
        out.println("<form action=\""+file_path+"\" name=\"GenerateSheet\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");

        /*String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
*/
        out.println("</form>");
        out.println("<script type=\"text/javascript\">");
        out.println("GenerateSheet.submit();");
        out.println("</script>");
        //response.sendRedirect(""+appName+"/cr/Template/generated.xls");
  
      %>
    </center>
  </body>
</html>