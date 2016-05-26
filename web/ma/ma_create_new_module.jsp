<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ma.*"
         import="com.mobinil.sds.core.system.ma.dao.*"
         import="com.mobinil.sds.core.system.ma.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    
    document.SOPform.submit();
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
  
  ModuleModel moduleModel = (ModuleModel)objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE);
  
%> 
 <CENTER>
      <H2>  Module  </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
		  " id=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"        value=\""+"\">");



  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
                  
  String nextAction=MAInterfaceKey.ACTION_SAVE_MODULE;

	String moduleID = "";
	String moduleName = "";
	String moduleDescription ="";
	String moduleStatusId="";

	String selectedStatusActive="";
	String selectedStatusNotActive="";
	String selectedStatusDelete="";
if (moduleModel != null)
{
  nextAction = MAInterfaceKey.ACTION_EDIT_MODULE;
  System.out.println(nextAction);
  moduleID = moduleModel.getModuleId();
  moduleName = moduleModel.getModuleName();
  moduleDescription = moduleModel.getModuleDesc();
  moduleStatusId=moduleModel.getModuleStatusId();
      if(moduleStatusId.equals("5"))
	  	selectedStatusActive = "selected";
	  else if(moduleStatusId.equals("6")) 
		  selectedStatusDelete = "selected";
	  else if(moduleStatusId.equals("7")) 
		  selectedStatusNotActive = "selected";
}

      out.println("<input type=\"hidden\" name=\""+MAInterfaceKey.INPUT_HIDDEN_MODULE_ID+"\""+
      " value=\""+moduleID+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>    
    

<tr>

<td class=TableHeader nowrap>
		Module Name
</td>
<td><input type="text" id="<%=MAInterfaceKey.INPUT_TEXT_MODULE_NAME%>"  name="<%=MAInterfaceKey.INPUT_TEXT_MODULE_NAME%>" value="<%=moduleName%>"></td>
</tr>
<tr>
<td class=TableHeader nowrap>
		Module Description
</td>
<td><input type="text" id="<%=MAInterfaceKey.INPUT_TEXT_MODULE_DESC%>"  name="<%=MAInterfaceKey.INPUT_TEXT_MODULE_DESC%>" value="<%=moduleDescription%>"></td>



</tr>
<% if (moduleModel != null)
{%>
<tr>

<td class=TableHeader nowrap>
		Module Status
</td>

<td>
<select id="<%=MAInterfaceKey.INPUT_SELECT_MODULE_ID%>"  name="<%=MAInterfaceKey.INPUT_SELECT_MODULE_ID%>">
	<option value ="5" <%=selectedStatusActive %> >Active</option>
    <option value ="6" <%=selectedStatusDelete %>>Delete</option>
    <option value ="7" <%=selectedStatusNotActive %>>Not Active</option>      
</select>
</td>

</tr>
<%} %>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
      "SOPform.submit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>