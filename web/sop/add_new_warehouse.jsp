<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*" 
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<script>
    
    function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
    
    function checkBeforeSubmit()
    {
      SOPform.submit();
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
 
            
%>   
    <CENTER>
      <H2> Warehouse </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");
  
               
%> 
      <center>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1> 
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Warehouse Name</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=SOPInterfaceKey.INPUT_TEXT_WAREHOUSE_NAME%>" id="<%=SOPInterfaceKey.INPUT_TEXT_WAREHOUSE_NAME%>" value=""></td>
        </tr>
       
       </table> 
</center>
    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SAVE_NEW_WAREHOUSE+"';"+
                  "checkBeforeSubmit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
