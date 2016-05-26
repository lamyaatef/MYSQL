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
  Vector vecFranchiseList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String frachiseGroupId = (String)objDataHashMap.get(AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID);
  %>
  <CENTER>
      <H2> Franchise List</H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+"\""+
          " value=\""+frachiseGroupId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE+"\""+
          " value=\""+"\">"); 
  

  
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="50%" border=1>
<tr class=TableHeader>
      <td align='center'>Franchise Code</td>
      <td align='center'>Edit</td>
      <td align='center'>Delete</td>
     
      </tr>
      <%
      for (int i =0;i<vecFranchiseList.size();i++)
      {
    	  FranchiseListModel franchiseListModel = (FranchiseListModel)vecFranchiseList.get(i);
    	  String franchiseCode = franchiseListModel.getFranchiseCode();
    	  
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td align='center'><%=franchiseCode%></td>
      	<td align='center'>
      	<%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_EDIT_FRANCHISE_LIST+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+".value = '"+frachiseGroupId+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE+".value = '"+franchiseCode+"';"+
                  "ACCform.submit();\">");
          %>
      	</td>
      	<td align='center'>
      	<%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_DELETE_FRANCHISE_LIST+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_GROUP_ID+".value = '"+frachiseGroupId+"';"+
                  "document.ACCform."+AccInterfaceKey.INPUT_HIDDEN_FRANCHISE_CODE+".value = '"+franchiseCode+"';"+
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
      out.print("<INPUT class=button type='button' value=\" Add New Franchise  \" name=\"new\" id=\"new\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_ADD_FRANCHISE_LIST+"';"+
                  "ACCform.submit();\">");
       
       //out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
       </center>
       </form>
</body>
</html>