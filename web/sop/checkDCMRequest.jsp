<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"

         import="com.mobinil.sds.web.interfaces.gn.ua.UserAccountInterfaceKey"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <script>
    function checkBeforeSubmit()
    {
      var dcmCode = document.SOPform.<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE%>.value; 
      if(dcmCode == "")
      {
        alert("DCM Code can not be empty");
      }
      else
      {
        SOPform.submit();
      }
    }
  </script>
  <CENTER>
      <H2> Check DCM Request </H2>
  </CENTER>
<%
String appName = request.getContextPath();

%>
<form name='SOPform' id='SOPform' action='<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+UserAccountInterfaceKey.ACTION_GET_DCM_REQUEST+"\">");
%> 
  <table border=0 align='center'>
    <tr>
      <td>Please Enter DCM Code</td>
      <td><input type='text' name='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE%>' id='<%=SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE%>'></td>
    </tr>
    <tr>
      <td align='center' colspan=2><input type='button' class='button' value='Submit' onclick='checkBeforeSubmit();'></td>
    </tr>
  </table>
</form>
  </body>
</html>
