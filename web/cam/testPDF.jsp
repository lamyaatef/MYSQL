<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
ServletOutputStream servletOutputStream= response.getOutputStream();
File reportfile=new File("c:\\temp.pdf");
FileInputStream fis=new FileInputStream(reportfile);
byte[] bytes=new byte[128000];
 int count=fis.read(bytes);  
  try  
      {  
          
        response.setContentType("application/pdf");  
        response.setContentLength(bytes.length);  
     
        servletOutputStream.write(bytes, 0, bytes.length);  
        servletOutputStream.flush();  
        servletOutputStream.close();  
   }catch(Exception e){e.printStackTrace();}  
%>

<%@page import="java.io.FileInputStream"
		import="java.io.*"
		import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="com.mobinil.sds.web.interfaces.cam.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
		%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>

</body>
</html>