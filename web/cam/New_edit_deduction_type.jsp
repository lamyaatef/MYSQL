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
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         import ="com.mobinil.sds.core.system.payment.model.*"
         import ="com.mobinil.sds.core.system.payment.dto.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "new_edit_group_mgt_form";
        String page_header="New Deduction Type";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        PaymentTypeGroupModel item = (PaymentTypeGroupModel) dataHashMap.get(PaymentInterfaceKey.MODEL_GROUP);
        Vector all_status = (Vector) dataHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS);
        Vector all_types = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
        System.out.println("all_types: "+all_types.size());

         String button_string="Add deduction type";
         String button_acition=PaymentInterfaceKey.ACTION_ADD_PAYMENT_TYPE_GROUP;
        String group_name="";
        String group_desc="";
        int group_id=-1;
        int group_status_id=-1;
        Vector all_group_types=null;
        if(item !=null)
        {
        page_header="Edit Deduction Type";
        button_string="Update";
         button_acition=PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_TYPE_GROUP;
         group_id = item.getGroupId();
         System.out.println("group_id: "+group_id);
         group_status_id = item.getGroupStatusId();
         System.out.println("group_status_id: "+group_status_id);
         group_name=item.getGroupName();
         System.out.println("group_name: "+group_name);
         group_desc = item.getGroupDesc();
         System.out.println("group_desc: "+group_desc);
         all_group_types = item.getAllAssignedTypes();
         System.out.println("all_group_types: "+all_group_types);
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
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>" value="<%=group_id%>">   
 
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableTextNote>
              <td>Name</td>
              <td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_GROUP_NAME%>" value="<%=group_name%>"></td>
              <TR class=TableTextNote>
              <td>Status</td>
               <td>
                   <%
                   if(all_status!=null && all_status.size()>0)
                   {%>
                   <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_STATUS%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_STATUS%>">
                   <option></option>
                   <%
                   String status_selected="";
                   for(int j=0;j<all_status.size();j++){
                    StatusModel status = (StatusModel)all_status.get(j);
                    if(status.getStatus_id() == group_status_id)
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
              <td>Description</td>
              <td>
              <textarea maxlength="256" rows="3" cols="40" name="<%=PaymentInterfaceKey.CONTROL_TEXT_GROUP_DESC%>"><%=group_desc%></textarea>
              </td>
                      
    </TABLE>
    <br><br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
   <TR class=TableHeader>
              <td colspan="3" align="center">Payment Types</td>
              </TR>
              
                   <%
                   if(all_types!=null && all_types.size()>0)
                   {
                   String status_selected="";
                   for(int j=0;j<all_types.size();j++){
                     PaymentTypeDTO ptdto = (PaymentTypeDTO)all_types.get(j);
                     if(all_group_types!=null && all_group_types.size()>0)
                     for(int i=0;i<all_group_types.size();i++)
                     {
                        PaymentTypeDTO group_dto = (PaymentTypeDTO)all_group_types.get(i);
                        if(ptdto.getTypeId().compareTo( group_dto.getTypeId()) == 0)
                        {
                         status_selected="checked";
                         break;
                        }
                        else 
                         status_selected="";
                     }
                   %>
                   <TR class=TableTextNote>
               <td width="85%"><%=ptdto.getTypeName()%></td>
               <td><input <%=status_selected%> type="checkbox" name="<%=PaymentInterfaceKey.CONTROL_SELECT_TYPE%>" value="<%=ptdto.getTypeId()%>"></td>

                   <%}}%>
             </td>
              </TR>
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
