<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
         import="com.mobinil.sds.core.system.sip.dao.*"
         import="com.mobinil.sds.core.system.sip.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    
    document.SIPform.submit();
  }
</script>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
</head>
<body>
  
<%     

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  //String strChannelId = (String)objDataHashMap.get(MAInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  
  SIPReportTypeModel sipReportModel = (SIPReportTypeModel)objDataHashMap.get(SIPInterfaceKey.VECTOR_SCHEMA_SIP);
  
%> 
 <CENTER>
      <H2>  SIP Report Type  </H2>
    </CENTER>
<form name='SIPform' id='SIPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
		  " id=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"        value=\""+"\">");



  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
                  
  String nextAction=SIPInterfaceKey.ACTION_SAVE_SIP_REPORT;

	String sipReportID = "";
	String sipReportName = "";
	
if (sipReportModel != null)
{
  nextAction = SIPInterfaceKey.ACTION_EDIT_SIP_REPORT;
  System.out.println(nextAction);
  sipReportID = sipReportModel.getSipReportId();
  sipReportName = sipReportModel.getSipReportName();
	
}
if(sipReportName==null)
{sipReportName="";}
      out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+"\""+
      " value=\""+sipReportID+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>    
    

<tr>

<td class=TableHeader nowrap>
		SIP Type Name
</td>
<td><input type="text" id="<%=SIPInterfaceKey.INPUT_TEXT_REPORT_NAME%>"  name="<%=SIPInterfaceKey.INPUT_TEXT_REPORT_NAME%>" value="<%=sipReportName%>"></td>
</tr>


</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SIPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
      "SIPform.submit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>