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
  String channelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  String warehouseId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);
  WarehouseChannelModel warehouseChannelModel = (WarehouseChannelModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector channelVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector warehouseVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  %>
  
  <CENTER>
      <H2>Warehouse Channel </H2>
    </CENTER>
    
    <form  name='SOPform' id='SOPform' action='' method='post'>
     <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
          " value=\""+channelId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+"\""+
          " value=\""+warehouseId+"\">"); 
  
  String nextAction = SOPInterfaceKey.ACTION_SAVE_WAREHOUSE_CHANNEL;
  String channelName = "";
  String warehouseName = "";
  if(warehouseChannelModel != null)
    {
      nextAction = SOPInterfaceKey.ACTION_UPDATE_WAREHOUSE_CHANNEL;
      channelName = warehouseChannelModel.getChannelName();
      warehouseName = warehouseChannelModel.getWarehouseName();
    }
    
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>  
<tr>
<td class=TableHeader nowrap>Channel Name</td>
<td class=TableTextNote>
          <select name='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_CHANNEL_ID%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_CHANNEL_ID%>'>
          <option value=''></option>
              <%
              String channelIdX = "";
              for(int i=0;i<channelVec.size();i++)
              {
                chanelModel channelModel = (chanelModel)channelVec.get(i);
                channelIdX = channelModel.getCHANNEL_ID();
                String channelNameX = channelModel.getCHANNEL_NAME();
                String caseSuperTypeSelected = "";
                if(channelId.compareTo(channelIdX) == 0)
                {
                  caseSuperTypeSelected = "selected";
                }
                
                %>
                <option value="<%=channelIdX%>" <%=caseSuperTypeSelected%>><%=channelNameX%></option>
                <%
              }
              %>
            </select>
        </td>
</tr>
<tr>
<td class=TableHeader nowrap>Warehouse Name</td>
<td class=TableTextNote>
          <select name='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_WAREHOUSE_ID%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_WAREHOUSE_ID%>'>
          <option value=''></option>
              <%
              String warehouseIdX = "";
              for(int j=0;j<warehouseVec.size();j++)
              {
                WarehouseModel warehouseModel = (WarehouseModel)warehouseVec.get(j);
                warehouseIdX = warehouseModel.getWarehouseId();
                String warehouseNameX = warehouseModel.getWarehouseName();
                String caseSuperTypeSelected = "";
                if(warehouseId.compareTo(warehouseIdX) == 0)
                {
                  caseSuperTypeSelected = "selected";
                }
                
                %>
                <option value="<%=warehouseIdX%>" <%=caseSuperTypeSelected%>><%=warehouseNameX%></option>
                <%
              }
              %>
            </select>
        </td>
</tr>

</table>
</center>
<br>
<center>
<%
                  
     out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                 "SOPform.submit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>
</form>
</body>
</html>

