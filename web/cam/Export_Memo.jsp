<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page 
import="java.io.FileInputStream"
		import="java.io.*"
		import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.cam.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
		%>
		<%
		 String appName = request.getContextPath();
		 HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		 String file_path = (String)dataHashMap.get(InterfaceKey.EXPORT_FILE_PATH);
		 //file_path = appName+request.getRealPath("/cam/camMemosFiles/generated PDF files")+"\\"+file_path;
		 file_path = appName+"/cam/camMemosFiles/generated PDF files/"+file_path;
		%>
<html>
<head>
<meta http-equiv="Content-Type" content="Application/pdf;charset=windows-1256"> 
<title>Insert title here</title>
<%
out.println("<form action=\""+file_path+"\" name=\"GenerateSheet\" method=\"post\">");
out.println("<input type=\"hidden\" name="+InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT+" value="+appName+">");
out.println("</form>");
          out.println("<script type=\"text/javascript\">");
          out.println("GenerateSheet.submit();");
          out.println("</script>");
          %>
</head>
<body>

</body>
</html>
