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
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(DCMform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    
    function checkBeforeSubmit()
    {
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            
%>   
    <CENTER>
      <H2> Sales Form List </H2>
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
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>POS Code</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Contracts Count</td>
        <td align=middle><input type='text' name='' id=''></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Collecting Date From</td>
        <td align=middle><Script>drawCalender('name1',"*");</script></td>
        <td align=middle>Collecting Date To</td>
        <td align=middle><Script>drawCalender('name2',"*");</script></td>
        <td align=middle>Contract Type</td>
        <td align=middle><select name='' id=''></select></td>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
                  "\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" >");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Contract Type</TD>
          <TD>Number Of Blue Copies Collected</TD>
          <TD>Collecting Date</TD>
          <!--TD align='center'>Edit</TD-->
        </tr>
        <TR class=TableTextNote>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Types</TD>
          <TD>Count</TD>
          <TD>Date</TD>
          <!--TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_MANAGER_EDIT_BLUE_COPIES_COLLECTED+"';"+
                      "DCMform.submit();\">");

          %>
          </TD-->
        </tr>
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Sales Form Entry \" name=\"bluecopyentry\" id=\"bluecopyentry\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM+"';"+
                  "DCMform.submit();\">");
      %>
      </center>
       
</form>
</body>
</html>
