<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strNextAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);

%>
    <CENTER>
      <H2> Create New Case </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+strNextAction+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
      <TR>
        <td class=TableHeader>Please Enter DCM Code : </td>
        <td><input type="text" name="<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>" id="<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>"></td>
      </tr>
      <TR>
        <td class=TableHeader>Please Enter STK Number : </td>
        <td><input type="text" name="<%=HLPInterfaceKey.INPUT_TEXT_STK_NUMBER%>" id="<%=HLPInterfaceKey.INPUT_TEXT_STK_NUMBER%>"></td>
      </tr>
      <tr>
        <td colspan=2 align="center"><input type="button" class="button" value="Submit" onclick="HLPform.submit();"></td> 
      </tr>
      
     </table> 

      

  </form>
  </body>
</html>
