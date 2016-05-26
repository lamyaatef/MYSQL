
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ccm.CCMInterfaceKey"
         import = "com.mobinil.sds.core.system.ccm.entity.model.*"
       
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
  <form  name='CCMform' id='CCMform' action='' method='post'>
    <script>
  function checkBeforeSubmit()
  {

    var entityVal = document.CCMform.<%=CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID%>.value;
    
    if(entityVal == "")
    {
      alert("You Must Choose an Entity Type.");
    }
    else
    {
     CCMform.submit();
    }
  }
  </script>  
  <%
     HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	 Vector vecEntityType = (Vector)objDataHashMap.get(CCMInterfaceKey.VECTOR_ENTITY_TYPES);
	 String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	 
	//String  EntityTypelId =   (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID);
	 
	   String hiddenAction = (String) objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ACTION);
	 

   	  System.out.println("hidden  action isssssssssssssssssssssssssss"+hiddenAction);
	  String nextAction="";

	
	
	   String strEntitylType = (String)objDataHashMap.get(CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_NAME);
	 
	   if (hiddenAction.equals("1")) 
	    {
	    nextAction = CCMInterfaceKey.ACTION_CREATE_NEW_ENTITY;
	    }
	    
	   if (hiddenAction.equals("2")) 
	    { 	
	   nextAction = CCMInterfaceKey.ACTION_VIEW_ENTITY_TYPE_FIELDS;
	    }
	  
	 out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                " value=\""+"\">");   

     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                " value=\""+strUserID+"\">"); 

%>

   <CENTER>
      <H2> Entity Type </H2>
    </CENTER>
    
    
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1 align="center">
        <tr>
          <td class=TableHeader>
          Select Entity Type
          </td>

          <td>
          <%
        out.println("<select name='"+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"' id='"+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"'> ");
        out.println("<option value=\"\"></option>");
        int vecSize = vecEntityType.size();
        for (int i=0;i<vecSize;i++){
        	EntityTypesModel model = (EntityTypesModel)vecEntityType.get(i);
        	 String EntityID = model.getENTITY_TYPE_ID();
             String EntityName = model.getENTITY_TYPE_NAME();
          out.println("<option value="+EntityID+">"+EntityName+"</option>");  
        }
        out.println("</select>");
        %>

        </td>
        </table>
        <%

    //out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.CONTROL_HIDDEN_ENTITY_TYPE_ID+"\""+
      //        " value=\""+"\">");   
        	%>
        	<br>
        	<center>
        	<%
        	    out.print("<INPUT class=button type='button' value=\" Next\" name=\"Next\" id=\"details\" onclick=\"document.CCMform."
				+ InterfaceKey.HASHMAP_KEY_ACTION
			   	+ ".value = '"
				+ nextAction 
				+ "';" + "checkBeforeSubmit();\">");	
        	
        	
        	
    //out.print("<INPUT class=button type='button' value=\" Next \" name=\"Next\" id=\"details\" onclick=\"document.CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_VIEW_ENTITY_TYPE_FIELDS+"';"+
      //    "CCMform.submit();\">");
        	%>
        	</center>
          
<br>
</form>
</body>
</html>