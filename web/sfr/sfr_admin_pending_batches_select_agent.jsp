<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
    <script>
      function checknumber(text)
      {
        var x=text
        var anum=/(^\d+$)|(^\d+\.\d+$)/
        if (anum.test(x))
        testresult=true
        else
        {
        testresult=false
        }
        return (testresult)
      }

      function checkBeforeSubmit()
      {
        var sheetSIMCount = document.SFRform.<%=SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID%>.value
        if(checknumber(sheetSIMCount))
        {
          SFRform.submit();
        }
        else
        {
          alert("Agent Id is not a number.");
        }
      }
    </script>
  <body>
    <CENTER>
      <H2> POS Admin Pending Batches </H2>
    </CENTER>
      <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_PENDING_BATCHES+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
             
%>
    <h3>Please Enter Agent Id to get his pending batches : </h3>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableTextNote>
        <td align='center'>Agent Id</td>
        <td align='center'><input type='text' name='<%=SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID%>' id='<%=SFRInterfaceKey.INPUT_HIDDEN_BATCH_AGENT_ID%>'></td>
      </tr>
    </table>  

      </form>
      <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Submit \" name=\"Submit\" id=\"Submit\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_ADMIN_VIEW_POS_PENDING_BATCHES+"';"+
                  "checkBeforeSubmit();\">");         
%>
    </center>
  </body>
</html>
