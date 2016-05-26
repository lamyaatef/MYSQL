<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"
         
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
  Vector warehouseChannelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  %>
  
  <CENTER>
      <H2>Warehouse Channel List</H2>
    </CENTER>
    
    <form  name='SOPform' id='SOPform' action='' method='post'>
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
          " value=\""+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+"\""+
          " value=\""+"\">"); 
    
%>

<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>
<tr class=TableHeader>
      <td align='center'>Channel Name</td>
      <td align='center'>Warehouse Name</td>
      <td align='center'>Edit</td>
      <td align='center'>Delete</td>
     
      </tr>
      <%
      for (int i =0;i<warehouseChannelVec.size();i++)
      {
    	  WarehouseChannelModel warehouseChannelModel = (WarehouseChannelModel)warehouseChannelVec.get(i);
    	  String channelId = warehouseChannelModel.getChannelId();
    	  String channelName = warehouseChannelModel.getChannelName();
    	  String warehouseId = warehouseChannelModel.getWarehouseId();
    	  String warehouseName = warehouseChannelModel.getWarehouseName();
    	  
    	  %>
    	  <tr class=<%=InterfaceKey.STYLE[i%2]%>>
      	<td align='center'><%=channelName%></td>
      	<td align='center'><%=warehouseName%></td>
      	
      	<td align='center'>
      	<%
              out.print("<INPUT class=button type='button' value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_EDIT_WAREHOUSE_CHANNEL+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+".value = '"+channelId+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+".value = '"+warehouseId+"';"+
                  "SOPform.submit();\">");
          %>
      	</td>
      	
      	<td align='center'>
      	<%
              out.print("<INPUT class=button type='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_DELETE_WAREHOUSE_CHANNEL+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+".value = '"+channelId+"';"+
                  "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+".value = '"+warehouseId+"';"+
                  "SOPform.submit();\">");
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
      out.print("<INPUT class=button type='button' value=\" Add new Warehouse to Channel  \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_ADD_WAREHOUSE_CHANNEL+"';"+
                  "SOPform.submit();\">");
       
      // out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
       </center>
       </form>
</body>
</html>
