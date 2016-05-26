<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"
%>

<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
<body>
<% 
HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector vecFranchiseGroup = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  %>
  <CENTER>
      <H2> Franchise Group List</H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+"\""+
          " value=\""+"\">"); 
  

  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<tr class=TableHeader>
      <td align='center'>Franchise Group Id</td>
      <td align='center'>Franchise Group Name</td>
      <td align='center'>Franchise Group Description</td>
      <td align='center'>Edit</td>
        <td align='center'>Delete</td>
     
      </tr>
      <%
      for (int i =0;i<vecFranchiseGroup.size();i++)
      {
    	  FranchiseGroupModel franchiseGroupModel = (FranchiseGroupModel)vecFranchiseGroup.get(i);
    	  String franchiseGroupId = franchiseGroupModel.getFranchiseGroupId();
    	  String franchiseGroupName = franchiseGroupModel.getFranchiseGroupName();
    	  String franchiseGroupDescription = franchiseGroupModel.getFranchiseGroupDescription();
    	  
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td><%=franchiseGroupId%></td>
      	<%
          out.println("<TD><a href=\"#\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_VIEW_FRANCHISE_LIST+"';"+
                      "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+".value = '"+franchiseGroupId+"';"+
                      "ACCform.submit();\">"+franchiseGroupName+"</a></TD>");
          %>
      	<td><%=franchiseGroupDescription%></td>
      	 <td>
      	  <%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_EDIT_FRANCHISE_GROUP+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+".value = '"+franchiseGroupId+"';"+
                  "ACCform.submit();\">");
          %>
      	 </td>
          
          <td align='center'>
          <%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_DELETE_FRANCHISE_GROUP+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+".value = '"+franchiseGroupId+"';"+
                  "ACCform.submit();\">");
          %>
           </td>
           
      	</tr>
    	  <%
      }
      %>
      
       </TABLE>
       </center>
       <br><br>
       <center>
       <%
      out.print("<INPUT class=button type='button' value=\" Create New Franchise Group \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_ADD_FRANCHISE_GROUP+"';"+
                  "ACCform.submit();\">");
%>
       </center>
       </form>
</body>
</html>