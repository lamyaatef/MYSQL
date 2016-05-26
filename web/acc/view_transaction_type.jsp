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
  Vector channels = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector groups = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector transactionType = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  %>
  
  <CENTER>
      <H2> Transaction Type </H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
      <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
%>


<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
      <td>Transaction Type Id</td>
      <td>Transaction Type Name</td>
      <td>Channel</td>
      <td>Group</td>
      </tr>
      <%
      	for (int i =0;i<transactionType.size();i++)
      	{
      		TransactionTypeModel transactionTypeModel = (TransactionTypeModel)transactionType.get(i);
      		String transactionTypeId = transactionTypeModel.getTransactionTypeId();
      		String transactionTypeName = transactionTypeModel.getTransactionTypeName();
      		String channelId = transactionTypeModel.getChannelId();
      		if(channelId == null)
      			{channelId = "";}
      		String groupId = transactionTypeModel.getGroupId();
      		System.out.println("The group id isssssssssss " + groupId);
      		if(groupId == null)
      			{groupId = "";}
      	%>
      	<tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td><%=transactionTypeId%></td>
      	<td><%=transactionTypeName%></td>
      	<td>
      	<select name='<%=AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID%><%=transactionTypeId%>' id='<%=AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID%><%=transactionTypeId%>'>
      	<option value=''></option>
            <%
            for(int j=0;j<channels.size();j++)
            {
              channelModel channelModel = (channelModel)channels.get(j);          
              String channelIdX = channelModel.getChannelId();
              String channelNameX = channelModel.getChannelName();
              String channelSelected = "";
              if(channelIdX.compareTo(channelId)==0)
              {
            	  channelSelected = "selected";
              }
              //if((requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE)==0 && (requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED)==0 ))||
              //    (requestStatusId.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED)==0 && (requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_ACTIVE)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_REJECTED)==0 || requestStatusIdX.compareTo(SOPInterfaceKey.CONST_REQUEST_STATUS_DELETED)==0)))
            
              %>
              <option value='<%=channelIdX%>' <%=channelSelected%>><%=channelNameX%></option>
              <%
              
            }
            %>
          </select>
          </td>
              <td>
      	<select name='<%=AccInterfaceKey.INPUT_SEARCH_TEXT_GROUP_ID%><%=transactionTypeId%>' id='<%=AccInterfaceKey.INPUT_SEARCH_TEXT_GROUP_ID%><%=transactionTypeId%>'>
      	<option value=''></option>
            <%
            for(int k=0;k<groups.size();k++)
            {
              GroupModel groupModel = (GroupModel)groups.get(k);          
              String groupIdX = groupModel.getGroupId();
              String groupNameX = groupModel.getGroupName();
              String groupSelected = "";
              if(groupIdX.compareTo(groupId)==0)
              {
            	  System.out.println("Medhat");
            	  groupSelected = "selected";
              }
             
            
              %>
              <option value='<%=groupIdX%>' <%=groupSelected%>><%=groupNameX%></option>
              <%
              
            }
            %>
          </select>
          </td>
          </tr>
      	<%
      	}
      	%>
      </TABLE>
      <br><br>
      <center>
      <%
      out.print("<INPUT class=button type=button value=\" Save \" name=\"saveTransaction\" id=\"saveTransaction\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_SAVE_TRANSACTION_TYPE+"';"+
      "ACCform.submit();\">");
      %>
      </center>
</form>
</body>
</html>