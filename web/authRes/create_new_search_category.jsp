<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.mobinil.sds.core.utilities.Utility"
	import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
    import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"
    import = "com.mobinil.sds.core.system.authenticationResult.model.*"
%>
<html> 
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<link rel="stylesheet" type="text/css"
	href="../resources/css/Template1.css">
</head>

<body>
<form name='AuthRes' id='AuthRes' action='' method='post'><script>
 
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
    var cat = document.AuthRes.<%=AuthResInterfaceKey.INPUT_TEXT_CATEGROY_NAME%>.value;
    if(cat == "")
    {
      alert("Category name can not be empty.");
      return;
    }
    else
    {
    	AuthRes.submit();
    }
  }
  </script>
   <%
 	HashMap objDataHashMap = (HashMap) request
 			.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

 	Vector<AuthSearchCategryModel> vSearchCategory = (Vector<AuthSearchCategryModel>) objDataHashMap.get(AuthResInterfaceKey.VECTOR_SEARCH_CATEGORY);
 	
    //System.out.println("label vector isssssssssssssssssss"+label);
 	
    //Vector vecEntityFieldTypes = (Vector) objDataHashMap.get(AuthResInterfaceKey.VECTOR_ENTITY_FIELD_TYPES);
 	
  String nextAction = "";
  
  
//	nextAction = AuthResInterfaceKey.ACTION_UPDATE_LABEL;
String action =(String) objDataHashMap.get("Action");

if(action.compareToIgnoreCase("create_new_search_category")==0)
	
	
{
	
	 nextAction = AuthResInterfaceKey.ACTION_SAVE_NEW_SEARCH_CATEGORY;
}
else{
nextAction = AuthResInterfaceKey.ACTION_UPDATE_SEARCH_CATEGORY;
}

 	String Name="";
 	
 	String Description="";
 	String fieldLabel="";

 	String catId="";
 	
	
	//System.out.println("111111111111");

 %>
<CENTER>
<H2>New Search Category</H2>
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
			name="<%=AuthResInterfaceKey.INPUT_TEXT_CATEGROY_NAME%>"
			id="<%=AuthResInterfaceKey.INPUT_TEXT_CATEGROY_NAME%>"></td>
	</tr>
	
	
		<TR class="TableTextNote">
		<TD class="TableTextNote" width=\"20%\">Description</TD>
		<TD class="TableTextNote" colSpan=4><INPUT
			style="WIDTH: 400px; HEIGHT: 44px" size=40
			name="<%=AuthResInterfaceKey.INPUT_TEXT_CATEGORY_DESCRIPTION%>"
			id="<%=AuthResInterfaceKey.INPUT_TEXT_CATEGORY_DESCRIPTION%>"></td>
	</tr>


		

</table>
<%

	if( vSearchCategory != null || vSearchCategory.size() !=0)
{	
	for (AuthSearchCategryModel authSearchCategryModel:vSearchCategory) {
		
		 
		 
		Name =  authSearchCategryModel.getCat_name();
		Description=authSearchCategryModel.getCat_description();
		catId=authSearchCategryModel.getCat_id();
	    out.println("<input type=\"hidden\" name=\""+AuthResInterfaceKey.INPUT_HIDDEN_SEARCH_CATEGORY_ID+"\""+
                " value=\""+catId+"\">");
		  
	
	    }
	//	nextAction = AuthResInterfaceKey.ACTION_UPDATE_LABEL;
}
	%>
<script>

		document.AuthRes.<%out.print(AuthResInterfaceKey.INPUT_TEXT_CATEGROY_NAME);%>.value='<%out.print(Name);%>';
        document.AuthRes.<%out.print(AuthResInterfaceKey.INPUT_TEXT_CATEGORY_DESCRIPTION);%>.value='<%out.print(Description);%>';

      
  
 </script>
</center>


<center><br>
<%
           
	       System.out.println("The action issssssssssssssss " + nextAction);
	
			out.print("<INPUT class=button type='button' value=\" Save \" name=\"Save\" id=\"details\" onclick=\"document.AuthRes."
				+ InterfaceKey.HASHMAP_KEY_ACTION
				+ ".value = '"
				+ nextAction
				+ "';" + "checkBeforeSubmit();\">");
%> 





<%
           
	      //    System.out.println("The action issssssssssssssss " + nextAction);
	
out.print("<INPUT class=button type=button value=\"Back \" name=\"Back\" id=\"Back\" onclick=\"document.AuthRes."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_VIEW_SEARCH_CATEGORY_MANAGEMENT+"';"+
" submit();\">");
%> 


<br>
</center>

</form>
</body>
</html>