<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
%>
         
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

%>

<form name='SOPform' id='SOPform' action='' method='post'>
<%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+SOPInterfaceKey.ACTION_GENERATE_MANUAL_DCM_PRODUCT_LIMITS+"\">"); 

      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
%>
</form>
<script>
if(confirm("Do you want to reset all DCM product limits?"))
{
  SOPform.submit();
}
</script>
  </body>
</html>
