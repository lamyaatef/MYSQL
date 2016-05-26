<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.mobinil.sds.core.utilities.Utility"
	import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"
	 import = "com.mobinil.sds.core.system.ccm.service.model.*"
%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<link rel="stylesheet" type="text/css"
	href="../resources/css/Template1.css">
</head>

<body>
<form name='CCMform' id='CCMform' action='' method='post'><script>
 
function fillControl(controlName,controlValue)
{
	alert(controlName);
	alert(controlValue);
	document.getElementById(controlName).value = controlValue;
}
</script>

<script>
  function checkBeforeSubmit()
  {
    var label = document.CCMform.<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>.value;
    if(label == "")
    {
      alert("service name can not be empty.");
      return;
    }
    else
    {
    	CCMform.submit();
    }
  }
  </script>
   <%
 	HashMap objDataHashMap = (HashMap) request
 			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

 	Vector service = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_SERVICE);
 	
    //System.out.println("service vector isssssssssssssssssss"+service);
 	
    //Vector vecEntityFieldTypes = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_FIELD_TYPES);
 	
  String nextAction = CCMInterfaceKey.ACTION_SAVE_NEW_SERVICE;
 	

 	String Name="";
 	
 	String Description="";
 	String fieldLabel="";

 	String serviceId="";
 	
	
	//System.out.println("111111111111");

 %>
<CENTER>
<H2>Service</H2>
</CENTER>
<%
	out.println("<input type=\"hidden\" name=\""
			+ InterfaceKey.HASHMAP_KEY_ACTION + "\"" + " value=\""
			+ "\">");


%>
<center>
<TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
	cellSpacing=2 cellPadding=1 width="746" border=1>
	<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Name</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 200px; HEIGHT: 22px" size=20
			name="<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME%>"></td>
	</tr>
	
	
		<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Description</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 400px; HEIGHT: 44px" size=40
			name="<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_DESCRIPTION%>"
			id="<%=CCMInterfaceKey.INPUT_TEXT_SERVICE_DESCRIPTION%>"></td>
	</tr>


		

</table>
<%
if  (service.size()!=0) 
{	
	for (int i = 0; i < service.size(); i++) {
		
		ServiceModel model = (ServiceModel)service.get(i); 
		 
		Name =  model.getServiceName();
		Description=model.getServiceDescription();
		serviceId=model.getServiceId();
	    out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_SERVICE_ID+"\""+
                " value=\""+serviceId+"\">");
		  
	
	    }
	nextAction = CCMInterfaceKey.ACTION_UPDATE_SERVICE;
}
	%>
<script>

		document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_SERVICE_NAME);%>.value='<%out.print(Name);%>';
        document.CCMform.<%out.print(CCMInterfaceKey.INPUT_TEXT_SERVICE_DESCRIPTION);%>.value='<%out.print(Description);%>';

      
  
 </script>
</center>


<center><br>
<%
           
	      //    System.out.println("The action issssssssssssssss " + nextAction);
	
			out.print("<INPUT class=button type='button' value=\" Save \" name=\"Save\" id=\"details\" onclick=\"document.CCMform."
				+ InterfaceKey.HASHMAP_KEY_ACTION
				+ ".value = '"
				+ nextAction
				+ "';" + "checkBeforeSubmit();\">");
%> 





<%
           
	      //    System.out.println("The action issssssssssssssss " + nextAction);
	
out.print("<INPUT class=button type=button value=\"Back \" name=\"Back\" id=\"Back\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_SERVICE_MANAGEMENT+"';"+
" submit();\">");
%> 


<br>
</center>

</form>
</body>
</html>