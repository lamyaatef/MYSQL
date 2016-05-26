<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.aacm.*"

         import="com.mobinil.sds.core.system.aacm.model.*"
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
  
  Vector DCMNotExist = (Vector)(objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION));
  
  %>
  
  <center>
<H2> Import AMS Data </H2>
    </CENTER>
<form name='AACMform' id='AACMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  
                  
%>

<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">


 <TR class=TableTextNote>
  <TD class=TableTextNote > Please Select a Year</TD>
                <td>
                <select name="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR %>" id="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_YEAR %>">
                <option></option>
                <%for(int i=2000; i<2020;i++){ %>
                <option value="<%=i %>"><%=i %></option>
                <%} %>
                </select>
                </td>
                </tr>
   <TR class=TableTextNote>
  <TD class=TableTextNote > Please Select a Month</TD>        
    <td>
                <select  name="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH%>" id="<%=AACMInterfaceKey.CONTROL_SELECT_IMPORT_AMS_MONTH%>">
                <option></option>
                <%
                String [] months = {" " ,"Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sept", "Oct", "Nove", "Dec"};
                for(int i=1; i<months.length;i++){
                	%>
                <option value="<%=i %>"><%=months[i]%></option>
                <%} %>
                </select>
                </td>
                </TR>    
   </table>
   <br><br>
    <center>
<%
	if(DCMNotExist.size()==0)
	{
      out.print("<INPUT class=button type='button' value=\" Import Data \" name=\"import\" id=\"import\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AACMInterfaceKey.ACTION_IMPORT_AMS_DATA+"';"+
                  "AACMform.submit();\">");
	}
%>


<%
	if(DCMNotExist.size()!=0)
	{
%>
	<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
	<tr class=TableHeader>
	<td align='center'>Authorized Agent Does not Exist</td>
	</tr>
<% 
	for(int i =0;i<DCMNotExist.size();i++)
	{
		String authAgentName = (String)DCMNotExist.get(i);
%>

<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td><%=authAgentName%></td>
  </tr>
<% 
	}
%>
</table>
<%
	}
%>
</center>
 </form>
  </body>
</html>