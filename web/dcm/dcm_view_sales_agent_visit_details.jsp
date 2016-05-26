<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"

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
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            
%>   
    <CENTER>
      <H2> Sales Agent Visits </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD align='center'>POS Name</TD>
          <TD align='center'>POS Code</TD>
          <TD align='center'>Objective</TD>
          <TD align='center'>System Comment</TD>
          <TD align='center'>Visit Date</TD>
          <TD align='center'>Status</TD>
        </tr>
        <TR>
          <TD align='center'>POS Name</TD>
          <TD align='center'>POS Code</TD>
          <TD align='center'>Objective</TD>
          <TD align='center'>System Comment</TD>
          <TD align='center'>Visit Date</TD>
          <TD align='center'>Status</TD>
        </tr>
      </table> 
      
       <br><br>
        <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
        </center>
</form>
</body>
</html>
