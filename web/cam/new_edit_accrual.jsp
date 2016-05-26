<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.common.model.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "newEdit_accrual_form";
        String page_header="New Accrual";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector all_accrual_status = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS);
        Vector all_dcm_channel = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_DCM_CHANNEL);
        
        AccrualModel item = (AccrualModel)dataHashMap.get(AccrualInterfaceKey.MODEL_ACCRUAL);

        String button_string="Add accrual";
        String button_acition=AccrualInterfaceKey.ACTION_ADD_ACCRUAL;

        String accrual_id="";
        String accrual_name="";
        String channel_name="";
        String channel_desc="";
        //double accrual_value=0;
       // String accrual_value_str = "";
        String accrual_status_name="";
        if(item != null)
        {
        button_string="Update accrual";
        page_header="Edit Accrual";
        accrual_id = item.getAccrual_id()+"";
         accrual_name=item.getAccrual_name();
         channel_name=item.getChannel_name();
         //accrual_value_str=item.getAccrual_value()+"";
          channel_desc=item.getAccural_desc();
          accrual_status_name = item.getStatus_name();
          button_acition=AccrualInterfaceKey.ACTION_UPDATE_ACCRUAL;
        }
        
        
        
%>
<script language="JavaScript">
 
function submitForm()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=button_acition%>";
document.<%=form_name%>.submit();
}

</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=page_header%></h2></center>

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=form_name%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=AccrualInterfaceKey.CONTROL_HIDDEN_ACCRUAL_ID%>" value="<%=accrual_id%>">


            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableTextNote>
              <td>Name</td><td><input name="<%=AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_NAME%>" id="<%=AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_NAME%>" type="text" value="<%=accrual_name%>"></td>
              </TR>
              
              <TR class=TableTextNote>
              <td>Channel</td>
              <td>
                   <%
                   if(all_dcm_channel!=null && all_dcm_channel.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ACCRUAL_CHANNEL%>">
                   <option>
                   <%
                   String item_selected="";
                   for(int j=0;j<all_dcm_channel.size();j++){
                    DCMChannelModel channel = (DCMChannelModel)all_dcm_channel.get(j);
                    if(channel.getChannel_name().equalsIgnoreCase(channel_name))
                     item_selected="selected";
                    else 
                     item_selected="";
                   %>
                   <option <%=item_selected%> value="<%=channel.getChannel_id()%>"><%=channel.getChannel_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
             </td>
              </TR>
              
               <TR class="TableTextNote">
              <td>Status</td>
              <td>
                   <%
                   if(all_accrual_status!=null && all_accrual_status.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS%>">
                   <option></option>
                   <%
                   String status_selected="";
                   for(int j=0;j<all_accrual_status.size();j++){
                    StatusModel status = (StatusModel)all_accrual_status.get(j);
                    if(status.getStatus_name().equalsIgnoreCase(accrual_status_name))
                     status_selected="selected";
                    else 
                     status_selected="";
                   %>
                   <option <%=status_selected%> value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
             </td>
          
              </tr>
              <TR class="TableTextNote">
              <td>Description</td>
              <td><textarea maxlength="256" rows="3" cols="40" name="<%=AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_DESCRIPTION%>"><%=channel_desc%></textarea></td>
              </TR>
               
      </TABLE>
       <br/>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align="center">
                        <input class="button" type="button" name="new" value="<%=button_string%>"
                               onclick="submitForm();">
                    
                </tr>
            </table>


        </form>
    </body>
</html>
