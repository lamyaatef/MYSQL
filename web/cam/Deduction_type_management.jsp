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
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "group_mgt_form";
        String page_header="Deduction Type List";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector all_group = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_GROUP);
        Vector all_status = (Vector) dataHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_STATUS);
        
        
        
%>
<script language="JavaScript">
 
function submitEdit(rowId)
{
document.<%=form_name%>.<%=PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>.value=rowId;
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_EDIT_PAYMENT_TYPE_GROUP%>";
document.<%=form_name%>.submit();
}
function submitUpdateStatuses()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_TYPE_GROUP_STATUSES%>";
document.<%=form_name%>.submit();
}
function submitNew()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_NEW_PAYMENT_TYPE_GROUP%>";

document.<%=form_name%>.submit();
}
function submitSearch()
{
  document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_VIEW_PAYMENT_TYPE_GROUP%>";
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
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_HIDDEN_GROUP_ID%>" value="-1">   

             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <TD>Deduction Type</TD>
              <TD><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SEARCH_GROUP_NAME%>"></TD>
              <TD>Status</TD>
              <TD>
                  <%
                   if(all_status!=null && all_status.size()>0)
                   {%>
                   <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_GROUP_STATUS%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_GROUP_STATUS%>">
                   <option>
                   <%
                
                   for(int j=0;j<all_status.size();j++){
                    StatusModel status = (StatusModel)all_status.get(j);
                    
                   %>
                   <option   value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
              </TD>
              
              </TR>
               <tr>
                    <td align=center colspan="7">
                        <input class=button type="button" name="new" value="Search"
                               onclick="submitSearch();">                    
                    </td>                     
                </tr>
             </TABLE>
            
            <%
                if(all_group != null && all_group.size()>0){
            %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td>Name</td>
              <td>Description</td>
              <td>Status</td>
              <td>Edit</td>
              </TR>
             
             <%
             for(int i=0;i<all_group.size();i++){
              PaymentTypeGroupModel item = (PaymentTypeGroupModel)all_group.get(i);
              int groupId = item.getGroupId();
              String strGroupId = Integer.toString(groupId);
             %>
             <TR class=TableTextNote>
             <td><%=item.getGroupName()%></td>
             <td><%=item.getGroupDesc()%></td>
             <td>
             <%
             if(all_status!=null && all_status.size()>0)
             {%>
             <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_STATUS+item.getGroupStatusId()%>_<%=strGroupId%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_STATUS+item.getGroupStatusId()%>_<%=strGroupId%>">
             <%
             String status_selected="";
             for(int j=0;j<all_status.size();j++){
              StatusModel status = (StatusModel)all_status.get(j);
              if(status.getStatus_id() == item.getGroupStatusId())
               status_selected="selected";
              else 
               status_selected="";
             %>
             <option <%=status_selected%> value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
             <%}%>
             </select>
             <%}%>
             </td>
             <td><input class="button" type="button" value="Edit" onclick="submitEdit(<%=item.getGroupId()%>)"></td>
             </TR>
         <%}%>
    </TABLE>
       <%}%>
        <br>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="new" value="New Deduction Type"
                               onclick="submitNew();">
                    &nbsp
                        <input class=button type="button" name="new" value="Update status"
                               onclick="submitUpdateStatuses();">
                    </td>
                     
                </tr>
            </table>


        </form>
    </body>
</html>
