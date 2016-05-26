<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.*"
         import="java.io.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.aacm.*"

         import="com.mobinil.sds.core.system.aacm.model.*"
%>
<html>
 <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
  <body>


  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector amsDataVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

   response.addHeader("Content-Disposition", "attachment; filename=report.xls");
%>

<CENTER>
<H2> AMS Data </H2>
</CENTER>
<form name='AACMform' id='AACMform' action='' method='post'>


<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 


%>
      <%
    if(amsDataVec.size()!=0)
    {
    %>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
  <tr class=TableHeader>
    <td align='center'>Authorized Agent Name</td>
     <td align='center'>BSCS Code</td>
     </tr>
<%

for (int i=0;i<amsDataVec.size();i++)
{
	AMSImportModel amsImportModel = (AMSImportModel)amsDataVec.get(i);
	String authorizedAgentName = amsImportModel.getAuthAgentName();
	String bscsCode = amsImportModel.getBscsCode();
	%>

<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td><%=authorizedAgentName%></td>
   <td><%=bscsCode%></td>
   </tr>
	
	<%
}%>
</TABLE>
</center>
<% 
    }
 %>
 

    
    

   
</form>
</body>
</html>