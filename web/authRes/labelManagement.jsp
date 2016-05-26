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
	
    document.AuthResform.fieldId.value=id;
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





    Vector vecLabel  = (Vector) objDataHashMap.get(AuthResInterfaceKey.VECTOR_LABEL);
    System.out.println("vector  label  issssssss"+vecLabel);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  
  out.println("<input type=\"hidden\" name=fieldId value=\""+"\">");
  
  




  
%>  
    <CENTER>
      <H2> Label Management</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="20%" nowrap align=middle>Name</td>
        <td width="40%" nowrap align=middle>Description</td>
         <td width="20%" nowrap align=middle>Edit</td>
          <td width="20%" nowrap align=middle>Delete</td>

      </TR>
    <% 
        if( vecLabel != null)
  {
        	
        	%>  
      <%
  
        
        String Name="";
    	String Description="";

    	for (int i=0; i< vecLabel.size(); i++)
        {
        	LabelModel model = (LabelModel) vecLabel.get(i);            
        	Name =  model.getLabelName();
        	Description=model.getLabelDescription();
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=Name%></td>
        <td width="40%" nowrap align=middle><%=Description%></td>
     
        <TD width="10%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Edit\"  name =\"Edit \"    onclick=\"AuthResform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_EDIT_LABEL+"';"+
         "loadField("+model.getLabelId()+");AuthResform.submit();\">"); 
        %>
        </TD>
<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Delete\"  name =\"Delete \"    onclick=\"AuthResform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_DELETE_LABEL+"';"+
                "loadField("+model.getLabelId()+");AuthResform.submit();\">"); 
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
    out.println("<INPUT class=button type=button value= \"Add New Label\"  name =\"add new \"   + onclick=\"AuthResform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthResInterfaceKey.ACTION_CREATE_NEW_LABEL+"';"+
       "AuthResform.submit();\">");
	
      %>
      </center>
      
  </form>  
  </body>
</html>


  <%
  
    	
      %>