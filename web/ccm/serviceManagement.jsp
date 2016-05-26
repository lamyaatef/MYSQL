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
         import = "com.mobinil.sds.core.system.ccm.service.model.*"
       
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <script type="text/javascript">
      function loadField(id)
      {
		
          document.CCMform.fieldId.value=id;
      }
      </script>
    </head>
  <body>
  <form  name='CCMform' id='CCMform' action='' method='post'>  
<%


  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


    Vector vecService  = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_SERVICE);

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
 " value=\""+"\">");
  
  out.println("<input type=\"hidden\" name=fieldId value=\""+"\">");

  
%>  
    <CENTER>
      <H2> Service Management</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="20%" nowrap align=middle>Name</td>
        <td width="40%" nowrap align=middle>Description</td>
         <td width="20%" nowrap align=middle>Edit</td>
          <td width="20%" nowrap align=middle>Delete</td>

      </TR>
      <%
  
        
        String Name="";
    	String Description="";
  
    	for (int i=0; i< vecService.size(); i++)
        {
        	ServiceModel model = (ServiceModel) vecService.get(i);            
        	Name =  model.getServiceName();
        	Description=model.getServiceDescription();
      %>
      <TR class=TableTextNote>
        <td width="20%" nowrap align=middle><%=Name%></td>
        <td width="40%" nowrap align=middle><%=Description%></td>
     
        <TD width="10%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Edit\"  name =\"Edit \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_EDIT_SERVICE+"';"+
         "loadField("+model.getServiceId()+");CCMform.submit();\">"); 
        %>
        </TD>
<TD width="20%" noWrap align=middle>
        <%
        out.println("<INPUT class=button type=button value= \"Delete\"  name =\"Delete \"    onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_DELETE_SERVICE+"';"+
                "loadField("+model.getServiceId()+");CCMform.submit();\">"); 
        %>
        </TD>

      </TR>
       
      <%
      }
      %>
    </table>

    <br><br>
      <center>
      <%

     out.println("<INPUT class=button type=button value= \"Add New Service\"  name =\"add new \"   + onclick=\"CCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+CCMInterfaceKey.ACTION_CREATE_NEW_SERVICE+"';"+
       "CCMform.submit();\">");
	
      %>
      </center>
      
  </form>  
  </body>
</html>