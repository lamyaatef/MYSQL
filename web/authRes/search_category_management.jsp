  <%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"
         import = "com.mobinil.sds.core.system.authenticationResult.model.*"
       
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    
    </head>
  <body>
  <form  name='AuthResform' id='AuthResform' action='' method='post'>  
  <script type="text/javascript">
function loadField(id)
{
	
    document.AuthResform.<%=AuthResInterfaceKey.INPUT_HIDDEN_SEARCH_CATEGORY_ID%>.value=id;
}
</script>
<%
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String message=(String) objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
if(message != null)	{
	  if (message.equalsIgnoreCase("data exists")){
	  	


	  	      String ErrorMessage="MUST DELETE FILES CANTAINING LABEL DATA ";

	      	  out.println("<script>alert('"+ErrorMessage+"');</script>");


	  }}





Vector<AuthSearchCategryModel> vecSearchCategory  =  (Vector<AuthSearchCategryModel>)objDataHashMap.get(AuthResInterfaceKey.VECTOR_SEARCH_CATEGORY);    

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  
  out.println("<input type=\"hidden\" name=\""+AuthResInterfaceKey.INPUT_HIDDEN_SEARCH_CATEGORY_ID+"\" value=\""+"\">");
  
  




  
%>  
    <CENTER>
      <H2> Search Categroy Management</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="20%" nowrap align=middle>Name</td>
        <td width="40%" nowrap align=middle>Description</td>
         <td width="20%" nowrap align=middle>Edit</td>
          <td width="20%" nowrap align=middle>Delete</td>

      </TR>
    <% 
        if( vecSearchCategory != null)
  {
        	
        	%>  
      <%
  
        
        String catName="";
    	String catDescription="";
    	String catId="";

    	for (AuthSearchCategryModel authSearchCategryModel: vecSearchCategory)
        {        	           
    	   catId=authSearchCategryModel.getCat_id();
       	  catDescription=authSearchCategryModel.getCat_description();
       	  catName=authSearchCategryModel.getCat_name();
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=catName%></td>
        <td width="40%" nowrap align=middle><%=catDescription%></td>
     
        <TD width="10%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Edit\"  name =\"Edit \"    onclick=\"AuthResform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_EDIT_SEARCH_CATEGORY+"';"+
         "loadField("+catId+");AuthResform.submit();\">"); 
        %>
        </TD>
<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Delete\"  name =\"Delete \"    onclick=\"AuthResform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_DELETE_SEARCH_CATEGORY+"';"+
                "loadField("+catId+");AuthResform.submit();\">"); 
        %>
        </TD>

      </TR>
       
      <%
      }
    	
      %>
    </table>
      <%
      }
       %>

    <br><br>
      <center>
      <%
    out.println("<INPUT class=button type=button value= \"Add New Category\"  name =\"add new \"   + onclick=\"AuthResform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_CREATE_NEW_SEARCH_CATEGORY+"';"+
       "AuthResform.submit();\">");
	
      %>
      </center>
      
  </form>  
  </body>
</html>


  <%
  
    	
      %>