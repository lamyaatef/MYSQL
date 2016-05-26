<%@ page import="com.mobinil.sds.core.system.cam.excelImport.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
                import=" com.mobinil.sds.web.interfaces.cam.*"
%>

<%
  String appName = request.getContextPath();
  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>

<%@page import="com.mobinil.sds.core.system.cam.memo.dao.MemoDAO"%><html>
  <head>
    <meta http-equiv="Content-Type" content="APPLICATION/EXCL; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <TITLE>:::: SDS ::::</TITLE>
  </head>
  <body>
    <center>
      <%   
      String templatePath = request.getRealPath("/cam/template/");
      
      String tableId=(String)dataHashMap.get(MemoInterfaceKey.SQL_TABLE_ID);
      if(Integer.parseInt(tableId)==1){
    	  //extract payment excel file
    	  System.out.println("extract payment excel file");
    	  MemoDAO.createPaymentExcelsFile(templatePath);
    	  out.println("<form action=\""+appName+"/cam/template/payment excels\" name=\"GenerateSheet\" method=\"post\">");
    	
      }else if(Integer.parseInt(tableId)==2){
    	  //extract memo excel file
    	  System.out.println("extract memo excel file");
    	  MemoDAO.createMemoExcelsFile(templatePath);
    	  out.println("<form action=\""+appName+"/cam/template/memo excels.xls\" name=\"GenerateSheet\" method=\"post\">");
    	
      }else if(Integer.parseInt(tableId)==3){
    	  //extract memo pdf file
    	  System.out.println("extract memo pdf file");
    	  MemoDAO.createMemoPdfsFile(templatePath);
    	  out.println("<form action=\""+appName+"/cam/template/memo pdfs.xls\" name=\"GenerateSheet\" method=\"post\">");
    	
      }
      
       
        
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");

        
        out.println("</form>");
        out.println("<script type=\"text/javascript\">");
        out.println("GenerateSheet.submit();");
        out.println("</script>");
        
  
      %>
    </center>
  </body>
</html>