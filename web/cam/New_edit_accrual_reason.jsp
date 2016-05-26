<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.deduction.model.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.common.model.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "ded_reason_mgt_form";
        String page_header="New Accrual Reason";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        ReasonModel item= (ReasonModel)dataHashMap.get(AccrualInterfaceKey.MODEL_ACCRUAL_REASON);
        Vector all_deduction_reason_status = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_REASON_STATUS);

         String button_string="Add reason";
         String button_acition=AccrualInterfaceKey.ACTION_ADD_REASON;
        String reason_name="";
        String reason_desc="";
        int reason_id = -1;
        int reason_status_id=-1;
        if(item !=null)
        {
         button_acition=AccrualInterfaceKey.ACTION_UPDATE_REASON;
          button_string="Update reason";
          page_header = "Edit Accrual Reason";
          reason_name = item.getReason_name();
          reason_desc = item.getReason_desc();
          reason_id = item.getReason_id();
          reason_status_id = item.getReason_status_id();
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

        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=form_name%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=AccrualInterfaceKey.CONTROL_HIDDEN_REASON_ID%>" value="<%=reason_id%>">   

              
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableTextNote>
              <td>Name</td><td><input type="text" name="<%=AccrualInterfaceKey.CONTROL_TEXT_REASON_NAME%>" value="<%=reason_name%>"></td>
              </TR>
              <TR class=TableTextNote>
              <td>Status</td>
              <td>
                   <%
                   if(all_deduction_reason_status!=null && all_deduction_reason_status.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_REASON_STATUS%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_REASON_STATUS%>">
                   <option>
                   <%
                   String status_selected="";
                   for(int j=0;j<all_deduction_reason_status.size();j++){
                    StatusModel status = (StatusModel)all_deduction_reason_status.get(j);
                    if(status.getStatus_id() == reason_status_id)
                     status_selected="selected";
                    else 
                     status_selected="";
                   %>
                   <option <%=status_selected%> value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
             </td>
              </TR>
              <TR class=TableTextNote>
              <td>Description</td><td>
              <textarea maxlength="256" rows="3" cols="40" name="<%=AccrualInterfaceKey.CONTROL_TEXT_REASON_DESC%>"><%=reason_desc%></textarea>
              </td></TR>
             
             
    </TABLE>
  
        <br>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                
                <td align=center>
                       <input class=button type="button" name="new" value="<%=button_string%>"
                               onclick="submitForm();">
                </td>
                     
                </tr>
            </table>


        </form>
    </body>
</html>
