<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ma.*"
         import="com.mobinil.sds.core.system.ma.doa.*"
         import="com.mobinil.sds.core.system.ma.model.*"
%>

<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/yav-style.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/deepgrid.js" type=text/javascript></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav.js"></SCRIPT>
      <SCRIPT language=JavaScript src="../resources/js/yav/yav-config.js"></SCRIPT>
	  <SCRIPT language=JavaScript src="../resources/js/validation.js"></SCRIPT>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
	    if(NonBlank(document.MAform.<%out.print(MAInterfaceKey.INPUT_TEXT_PRIVILAGE_NAME);%>, true, 'Privilage Name'))
	      {
		      if(checkSelect(document.MAform.<%out.print(MAInterfaceKey.INPUT_SELECT_MODULE_NAME);%>,'Module Name'))
		      {
		    	//  if(checkSelect(document.MAform.
		    //	document.MAform.<%out.print(MAInterfaceKey.INPUT_SELECT_PRIVILAGE_STATUS_NAME);%>='-';
			   //  {
			   			
		      if(checkSelect(document.MAform.<%out.print(MAInterfaceKey.INPUT_TEXT_ORDER_VALUE);%>,'Module Name'))
   				 		{
				 			document.MAform.submit();
   				 		}
			    //  }
   			  }
   		  }
  }

  function checkSelect(item,caption)
  {
		if(item.value!='-')
		{
			return true;
		}
		else 
		{
	        alert("Select a valid " + caption + ".");
		
			return false;
		}
  }
</script>
</head>
<body>
  
<%     

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector vecListOfModules = (Vector)objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE_2);
  Vector vecPrivilagesStatusList= (Vector) objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE_3);
  
  PrivilageModel privilageModel = (PrivilageModel)objDataHashMap.get(MAInterfaceKey.VECTOR_SCHEMA_MODULE);
%> 
 <CENTER>
      <H2> Privilage  </H2>
    </CENTER>
<form name='MAform' id='MAform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
		  " id=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\"        value=\""+"\">");

//  out.println("<input type=\"hidden\" name=\""+MAInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
  //                " value=\""+strChannelId+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
     
  
  String nextAction=MAInterfaceKey.ACTION_SAVE_PRIVILAGE;

	String privilageID = "";
	String privilageName = "";
	String privilageDescription ="";
	String moduleName="";
	String privilageStatusName="";
	String privilageActionName="";
	String privilageTarget="";
	String orderValue="";
	
	String selectedStatusActive="";
String selectedStatusNotActive="";
if (privilageModel != null)
{
  nextAction = MAInterfaceKey.ACTION_EDIT_PRIVILAGE;
  
  privilageID = privilageModel.getPrivilageId();
  privilageName = privilageModel.getPrivilageName();
  privilageDescription = privilageModel.getPrivilageDesc();
  moduleName=privilageModel.getModuleName();
  privilageStatusName=privilageModel.getPrivilageStatusName();
  privilageActionName=privilageModel.getPrivilageActionName();
  orderValue=privilageModel.getOrderValue();
  privilageTarget=privilageModel.getPrivilageTarget();
  
  if(privilageDescription==null)
  {
	  privilageDescription="";
  }
  /*if(statusId.compareTo("0")==0)
	  	selectedStatusActive = "selected";
	  else selectedStatusNotActive = "selected";*/
}

      out.println("<input type=\"hidden\" name=\""+MAInterfaceKey.INPUT_HIDDEN_PRIVILAGE_ID+"\""+
      " value=\""+privilageID+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>    
    

<tr>

<td class=TableHeader nowrap>
		Privilage Name
</td>
<td><input type="text" id="<%=MAInterfaceKey.INPUT_TEXT_PRIVILAGE_NAME%>"  name="<%=MAInterfaceKey.INPUT_TEXT_PRIVILAGE_NAME%>" value="<%=privilageName%>"></td>
</tr>
<tr>

<td class=TableHeader nowrap>
		Privilage Description
</td>
<td>
<input type="text" id="<%=MAInterfaceKey.INPUT_TEXT_PRIVILAGE_DESC%>"  name="<%=MAInterfaceKey.INPUT_TEXT_PRIVILAGE_DESC%>" value="<%=privilageDescription%>">
</td>
</tr>

<tr>

<td class=TableHeader nowrap>
		Module Name
</td>
<td>
<select name="<%=MAInterfaceKey.INPUT_SELECT_MODULE_NAME%>" id="<%=MAInterfaceKey.INPUT_SELECT_MODULE_NAME%>">
<option value="-">-</option>
<%
  String chainCityName = "";
  for (int l=0; l<vecListOfModules.size(); l++)
  {
    ModuleModel moduleModel = (ModuleModel)vecListOfModules.get(l);
    String strModuleId = moduleModel.getModuleId();
    String strModuleName = moduleModel.getModuleName();
    String chainCitySelected = "";
   /* if(strModuleId!=null)
    {
      if(moduleId.compareTo(strModuleId)==0)
      { 
        chainCitySelected = "selected";
        chainCityName = strchainCityName;
      }
    }*/
%>
<option value="<%=strModuleId%>" ><%=strModuleName%></option>
<%
  }
%>
</select>
</td>
</tr>
<%
if(privilageModel!=null)
{
%>
<tr>

<td class=TableHeader nowrap>
		Privilage Status Name
</td>

<td>
<select name="<%=MAInterfaceKey.INPUT_SELECT_PRIVILAGE_STATUS_NAME%>" id="<%=MAInterfaceKey.INPUT_SELECT_PRIVILAGE_STATUS_NAME%>">
<option value="-">-</option>
<%
  //String chainCityName = "";
  for (int l=0; l<vecPrivilagesStatusList.size(); l++)
  {
    PrivilageStatusModel privilagesStatusModel = (PrivilageStatusModel)vecPrivilagesStatusList.get(l);
    String strPrivilageStatusId = privilagesStatusModel.getPrivilageStatusId();
    String strPrivilageStatusName = privilagesStatusModel.getPrivilageStatusName();
    String chainCitySelected = "";
   /* if(chainCityId!=null)
    {
      if(privilageStatusId.compareTo(strPrivilageStatusId)==0)
      { 
        chainCitySelected = "selected";
        chainCityName = strchainCityName;																	
      }
    }*/
%>
<option value="<%=strPrivilageStatusId%>" ><%=strPrivilageStatusName%></option>
<%
  }
%></td>
</tr>
<%} %>
<tr>

<td class=TableHeader nowrap>
		Privilage Action Name
</td>
<td>
<input type="text" id="<%=MAInterfaceKey.INPUT_TEXT_PRIVILAGE_ACTION_NAME%>"  name="<%=MAInterfaceKey.INPUT_TEXT_PRIVILAGE_ACTION_NAME%>" value="<%=privilageActionName%>">
</td>
</tr>

<tr>

<td class=TableHeader nowrap>
		Order Value
</td>
<td>
<input type="text" id="<%=MAInterfaceKey.INPUT_TEXT_ORDER_VALUE%>"  name="<%=MAInterfaceKey.INPUT_TEXT_ORDER_VALUE%>" value="<%=orderValue%>">
</td>
</tr>


</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.MAform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
      "checkbeforSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>