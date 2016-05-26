<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"

         
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
  Vector channels = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  
  <CENTER>
      <H2> DCMs Settings </H2>
    </CENTER>
<form name='ACCform' id='ACCform' action='' method='post'>

<%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  
%>

 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        <tr>
          <td class=TableHeader>
          Select Channel 
          </td>
          <td>
<%
out.println("<select name='"+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"' id='"+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"' onchange=\"document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_NAME+".value = document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+".options[document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+".selectedIndex].text;\"> ");
        out.println("<option value=''></option>");
        for (int i = 0 ; i<channels.size();i++)
        {
          channelModel model = (channelModel)channels.get(i);       
          String channelId = model.getChannelId();
          String channelName = model.getChannelName();
          out.println("<option value='"+channelId+"'>"+channelName+"</option>");  
        }
        out.println("</select>");
%>
        </td>
        </tr>
        </table>
<br>
<center>
<%
        
             out.print("<INPUT class=button type=button value=\" Next \" name=\"select\" id=\"select\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_VIEW_DCM_VALUES+"';"+
               "ACCform.submit();\">");
%>
</center>
        
</form>    
  </body>
</html>
