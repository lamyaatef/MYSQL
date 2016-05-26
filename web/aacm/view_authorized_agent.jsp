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
  
  String channelId = (String)objDataHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }

  Vector vecAuthAgent = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  
%>
<center>
<H2> Authorized Agent List </H2>
    </CENTER>
<form name='AACMform' id='AACMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
          " value=\""+channelId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
          " value=\""+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME+"\""+
          " value=\""+"\">");
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE+"\""+
          " value=\""+"\">");
                  
               
%>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
    <td align='center'>Authorized Agent Code</td>
     <td align='center'>Authorized Agent Name</td>
       <td align='center'>Edit</td>
       <td align='center'>Delete</td>
    </tr>
<%
  for(int i=0;i<vecAuthAgent.size();i++)
  {
    AuthAgentModel authAgentModel = (AuthAgentModel)vecAuthAgent.get(i);
    String dcmId = authAgentModel.getDcmId();
    String authAgentCode = authAgentModel.getAuthAgentCode();
    String authAgentName = authAgentModel.getAuthAgentName();
   
%>
<tr class=<%=InterfaceKey.STYLE[i%2]%>>
  <td><%=authAgentCode%></td>
   <td><%=authAgentName%></td>
   <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AACMInterfaceKey.ACTION_EDIT_AUTHAGENT+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_DCM_ID+".value = '"+dcmId+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME+".value = '"+authAgentName+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE+".value = '"+authAgentCode+"';"+
                  "AACMform.submit();\">");
          %>
           </td>
           <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AACMInterfaceKey.ACTION_DELETE_AUTHAGENT+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE+".value = '"+authAgentCode+"';"+
                  "document.AACMform."+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME+".value = '"+authAgentName+"';"+
                  "AACMform.submit();\">");
          %>
           </td>
     
 </tr>
<%
  }
%>
</table>
 <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Create New Authorized Agent \" name=\"new\" id=\"new\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AACMInterfaceKey.ACTION_CREATE_NEW_AUTHAGENT+"';"+
                  "AACMform.submit();\">");
%>
</center>
 </form>
  </body>
</html>
