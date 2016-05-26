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

      </script>
    </head>
  <body>
  <form  name='CCMform' id='CCMform' action='' method='post'>  
<%
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
        " value=\""+"\">");

 

  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    Vector vecService  = (Vector) objDataHashMap.get(CCMInterfaceKey.VECTOR_SERVICE_ASSIGNED_PROJECT);
  String  nextAction=CCMInterfaceKey.ACTION_SEARCH_PROJECT;
 String projectName=(String)objDataHashMap.get(CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME);
%>  
    <CENTER>
      <H2> Project Services</H2>
    </CENTER>

    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <TR class=TableHeader>
        <td width="20%" nowrap align=middle>Name</td>
        <td width="40%" nowrap align=middle>Description</td>
    

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
  

      </TR>
       
      <%
      }
      %>
    </table>

    <br><br>
      <center>
      <%
      
      
      out.println("<input type=\"hidden\" name=\""+CCMInterfaceKey.INPUT_SEARCH_TEXT_PROJECT_NAME+"\""+
    	        " value=\""+projectName+"\">");

		out.print("<INPUT class=button type='button' value=\" Back \" name=\"Back\" id=\"details\" onclick=\"document.CCMform."
				+ InterfaceKey.HASHMAP_KEY_ACTION
			+ ".value = '"
			+ nextAction
				+ "';" + "CCMform.submit();\">");
      
    

      %>
      </center>
      
  </form>  
  </body>
</html>