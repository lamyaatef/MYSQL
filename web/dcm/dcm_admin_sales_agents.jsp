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
      <H2> Admin Sales Agents </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=4>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Sales Agent Name</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Sales Agent Status</td>
        <td align=middle><select name='' id=''></select></td>
      </tr>
      <tr>
        <td align='center' colspan=4>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
                  "\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" >");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>Sales Agent Name</TD>
          <TD>E-mail</TD>
          <TD>Mobile Phone</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Changing Requests</TD>
        </tr>
        <TR class=TableTextNote>
          <TD>Name</TD>
          <TD>E-mail</TD>
          <TD>Mobile Phone</TD>
          <TD align='center'><select name='' id=''></select></TD>
          <TD align='center'>
          <%
                  out.print("<INPUT class=button type=button value=\" Changing Requests \" name=\"changingrequests\" id=\"changingrequests\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT+"';"+
                  "DCMform.submit();\">");
          %>        
          </TD>
        </tr>
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Agent \" name=\"addnewagent\" id=\"addnewagent\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ADMIN_CREATE_NEW_SALES_AGENT+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
                  "\">");
      %>
      </center>
       
</form>
</body>
</html>
