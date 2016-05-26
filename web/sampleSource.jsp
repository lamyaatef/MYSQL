<?xml version = '1.0' encoding = 'windows-1252'?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="1.2">
   <jsp:directive.page contentType="text/html;charset=windows-1252"/>
   <html>
      <head>
         <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
         <title>Sample Source</title>
      </head>
      <body>
      <form id="formHomeSample" name="formHomeSample" method="post" action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet">
      <input type="hidden" name="action" value="login"></input>
         <h2>The current time is: </h2>
         <p>
            <jsp:expression> new java.util.Date() </jsp:expression>
         </p>
         <input type="submit" name="submitButton" value="Submit To Servlet"></input>
      </form>
      </body>
   </html>
</jsp:root>
