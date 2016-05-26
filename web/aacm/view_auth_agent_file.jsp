<%@ page contentType="text/html;charset=windows-1252"%>
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
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

	

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  

 // if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  //{
    //String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    //out.println("<script>alert('"+strMsg+"');</script>");
  //}

  Vector vecAuthAgentFile = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  
%>
<center>
<H2> Authorized Agent File </H2>
    </CENTER>
<form name='AACMform' id='AACMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_AUTH_AGENT_FILE_ID+"\""+
          " value=\""+"\">"); 
  
 
                  
               
%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
    <td align='center'>File ID</td>
     <td align='center'>Month</td>
       <td align='center'>Year</td>
       <td align='center'>Timestamp</td>
       <td align='center'>User Name</td>
       <td align='center'>Count</td>
       <td align='center'>Export To Excel</td>
       <td align='center'>Delete</td>
    </tr>
<%
  for(int i=0;i<vecAuthAgentFile.size();i++)
  {
    AuthAgentFileModel authAgentFileModel = (AuthAgentFileModel)vecAuthAgentFile.get(i);
    String fileId = authAgentFileModel.getFileId();
    String month = authAgentFileModel.getMonth();
    String year = authAgentFileModel.getYear();
    String timestamp = authAgentFileModel.getTimestamp();
    String userId = authAgentFileModel.getUserId();
    String userName = authAgentFileModel.getUserName();
    String count = authAgentFileModel.getCount();
   
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td><%=fileId%></td>
   <td><%=month%></td>
   <td><%=year%></td>
   <td><%=timestamp%></td>
   <td><%=userName%></td>
   <td><%=count%></td>
   <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Export To Excel \" name=\"export\" id=\"export\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AACMInterfaceKey.ACTION_EXPORT_AUTH_AGENT_DATA_TO_EXCEL+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_AUTH_AGENT_FILE_ID+".value = '"+fileId+"';"+
                  "AACMform.submit();\">");
          %>
           </td>
    <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AACMInterfaceKey.ACTION_DELETE_AUTH_AGENT_FILE+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_AUTH_AGENT_FILE_ID+".value = '"+fileId+"';"+
                  "AACMform.submit();\">");
          %>
           </td>       
     
 </tr>
<%
  }
%>
</table>
 
 </form>
  </body>
</html>
