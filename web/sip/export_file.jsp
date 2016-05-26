<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page        import="java.util.*"
				import="java.sql.*"
                import="com.mobinil.sds.web.interfaces.*"
                import="com.mobinil.sds.core.utilities.*"
                import="com.mobinil.sds.web.interfaces.sip.*"
                import="java.io.*"
                import="com.mobinil.sds.core.system.sip.dao.*"                
                import="com.mobinil.sds.web.interfaces.cam.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<%
		  HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		  String userID = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
		  String fileTypeContentId = (String)request.getParameter(SIPInterfaceKey.CONTROL_HIDDEN_FILE_TYPE_ID);
		  String fileId = (String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_FILE_ID);
		  //String reportId = (String)request.getParameter(SIPInterfaceKey.INPUT_HIDDEN_SIP_ID)
		  
String templatePath = request.getRealPath("/sip/download/"); 
String fileSeparator = System.getProperty("file.separator");
templatePath = templatePath+fileSeparator;

String returnedFileName="";
if(Integer.parseInt(fileTypeContentId)==1)
{
   
   returnedFileName = SipDAO.exportUploadedFile(templatePath,fileId,true);
	  
}
else 
{
   returnedFileName = SipDAO.exportUploadedFile(templatePath,fileId,false);

	  
}



	System.out.println(returnedFileName); 
	returnedFileName = fileSeparator+returnedFileName;
	String fileNameFromPath =returnedFileName.substring((returnedFileName.lastIndexOf(System.getProperty("file.separator"))+1),returnedFileName.length())  ;
	dataHashMap.put(InterfaceKey.EXPORT_FILE_PATH,fileNameFromPath);
	dataHashMap.put(InterfaceKey.MODULE_SUB_PATH,"/sip/download/");
	
	dataHashMap.put(SIPInterfaceKey.CONTROL_INCOMING_ACITON, SIPInterfaceKey.ACTION_SEARCH_SIP);
	ArrayList temp_text = new ArrayList();
	temp_text.add("Press Save To Export File.");
	dataHashMap.put(SIPInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG_TOBEVIEWED, temp_text);
	session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT ,dataHashMap);
// response.addHeader("Content-Disposition", "attachment; filename="+fileNameFromPath);
//String url = request.getContextPath()+"/cam/ExportFileDispatcher.jsp";

out.println("<form action=\"/SDS/servlet/com.mobinil.sds.web.controller.UploadingFileServlet\" name=\"GenerateSheet\" method=\"post\">");
//out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" value="+userID+">");
out.println("</form>");
out.println("<script type=\"text/javascript\">");
out.println("GenerateSheet.submit();");
out.println("</script>");  
		%>
</body>
</html>


