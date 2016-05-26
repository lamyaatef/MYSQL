<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.aacm.*"
         import="com.mobinil.sds.core.system.sop.schemas.dao.*"
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
  
  String channelId = (String)objDataHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  
  String oldauthAgentName = (String)objDataHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME);
  
  String oldAuthAgentCode = (String)objDataHashMap.get(AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE);
  
  AuthAgentModel authAgentModel = (AuthAgentModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
%> 
<CENTER>
      <H2> Authorized Agent  </H2>
    </CENTER>
<form name='AACMform' id='AACMform' action='' method='post'>
<%
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
          " value=\""+channelId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_NAME+"\""+
          " value=\""+oldauthAgentName+"\">"); 

  out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_AUTHAGENT_CODE+"\""+
          " value=\""+oldAuthAgentCode+"\">");

  
  String authAgentCode = "";
  String authAgentName = "";
  String dcmId = "";
  String nextAction = AACMInterfaceKey.ACTION_SAVE_NEW_AUTHAGENT;
  if (authAgentModel!=null)
  {
    nextAction = AACMInterfaceKey.ACTION_UPDATE_AUTHAGENT;
    authAgentCode = authAgentModel.getAuthAgentCode();
    authAgentName = authAgentModel.getAuthAgentName();
    dcmId = authAgentModel.getDcmId();
  }

      out.println("<input type=\"hidden\" name=\""+AACMInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
      " value=\""+dcmId+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="80%" border=1>   



<tr>
<td class=TableHeader nowrap>Authorized Agent Code</td>
<td class=TableTextNote><input type="text" name="text_authAgent_code" value="<%=authAgentCode%>"></td>

</tr>
<tr>
<td class=TableHeader nowrap>Authorized Agent Name</td>
<td class=TableTextNote><input type="text" name="text_authAgent_name" value="<%=authAgentName%>"></td>

</tr>

</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.AACMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"
      +"AACMform.submit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>
                  