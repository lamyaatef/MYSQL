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
      <H2> Manager Sales Agents </H2>
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
          <td align='center'>Sales Agent Name</td>
          <td align='center'>E-mail</td>
          <td align='center'>Mobile Phone</td>
          <td align='center'>Status</td>
        </tr>
        <tr>
          <td align='center'>Name</td>
          <td align='center'>E-mail</td>
          <td align='center'>Phone</td>
          <td align='center'>Status</td>
        </tr>
      </table>

      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Code</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Group Name</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Function</td>
        <td align=middle><select name='' id=''></select></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Target Value From</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Target Value To</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Target Status</td>
        <td align=middle><select name='' id=''></select></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Start Date From</td>
        <td align=middle><Script>drawCalender('name1',"*");</script></td>
        <td align=middle>Start Date To</td>
        <td align=middle><Script>drawCalender('name2',"*");</script></td>
        <td align=middle>Achived From</td>
        <td align=middle><input type='text' name='' id=''></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>End Date From</td>
        <td align=middle><Script>drawCalender('name3',"*");</script></td>
        <td align=middle>End Date To</td>
        <td align=middle><Script>drawCalender('name4',"*");</script></td>
        <td align=middle>Achived To</td>
        <td align=middle><input type='text' name='' id=''></td>
      </tr>
      <tr>
        <td align='center' colspan=6>
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
        <tr>
          <td colspan=10>Function 1</td>
        </tr>
        <TR class=TableHeader>
          <TD>Name</TD>
          <TD>Code</TD>
          <TD align='center'>Target</TD>
          <TD align='center'>Achieved</TD>
          <td align='center'>Start Date</td>
          <td align='center'>End Date</td>
          <TD align='center'>Status</TD>
          <TD align='center'>Edit Target</TD>
          <TD align='center'>Close & Post</TD>
        </tr>
        <tr>
          <td colspan=10>Groups</td>
        </tr>
        <TR class=TableTextNote>
          <TD>Group Name</TD>
          <TD></TD>
          <TD align='center'>Target</TD>
          <TD align='center'>Achieved</TD>
          <td align='center'>Start Date</td>
          <td align='center'>End Date</td>
          <TD align='center'>Target Status</TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Edit Target \" name=\"edittarget\" id=\"edittarget\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_MANAGER_EDIT_SALES_AGENT_TARGET+"';"+
                      "DCMform.submit();\">");

          %>
          </TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Close & Post \" name=\"closeandpost\" id=\"closeandpost\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
                      "\">");

          %>
          </TD>
        </tr>
        <tr>
          <td colspan=10>&nbsp;</td>
        </tr>
        <tr>
          <td colspan=10>POSs</td>
        </tr>
        <TR class=TableTextNote>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD align='center'>Target</TD>
          <TD align='center'>Achieved</TD>
          <td align='center'>Start Date</td>
          <td align='center'>End Date</td>
          <TD align='center'>Target Status</TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Edit Target \" name=\"edittarget\" id=\"edittarget\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_MANAGER_EDIT_SALES_AGENT_TARGET+"';"+
                      "DCMform.submit();\">");

          %>
          </TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Close & Post \" name=\"closeandpost\" id=\"closeandpost\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
                      "\">");

          %>
          </TD>
        </tr>
      </table> 
      
      <br><br>
      <center>
        <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
