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
        String page_header="Deduction Reason List";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        Vector all_deduction_reason = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON);
        Vector all_deduction_reason_status = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_REASON_STATUS);
        
        
        
%>
<script language="JavaScript">
function submitNew()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_NEW_REASON%>";
document.<%=form_name%>.submit();
} 
function submitEdit(rowId)
{
document.<%=form_name%>.<%=DeductionInterfaceKey.CONTROL_HIDDEN_REASON_ID%>.value=rowId;
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_EDIT_REASON%>";
document.<%=form_name%>.submit();
}
function submitUpdateStatuses()
{
document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_UPDATE_REASON_STATUS%>";
document.<%=form_name%>.submit();
}

function submitSearch()
{
  document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_VIEW_REASON%>";
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
            <input type="hidden" name="<%=DeductionInterfaceKey.CONTROL_HIDDEN_REASON_ID%>" value="-1">   

             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <TD>Name</TD>
              <TD>
                  <input type="text" name="<%=DeductionInterfaceKey.CONTROL_TEXT_SEARCH_REASON_NAME%>">
              </TD>
              <TD>Status</TD>
              <TD>
                  <%
                   if(all_deduction_reason_status!=null && all_deduction_reason_status.size()>0)
                   {%>
                   <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON_STATUS%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_SEARCH_REASON_STATUS%>">
                   <option>
                   <%
                
                   for(int j=0;j<all_deduction_reason_status.size();j++){
                    StatusModel status = (StatusModel)all_deduction_reason_status.get(j);
                    
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
                if(all_deduction_reason != null && all_deduction_reason.size()>0){
            %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td>Name</td>
              <td>Description</td>
              <td>Status</td>
              <td>Edit</td>
              </TR>
             
             <%
             for(int i=0;i<all_deduction_reason.size();i++){
               ReasonModel item= (ReasonModel)all_deduction_reason.get(i);
             %>
             <TR class=TableTextNote>
             <td><%=item.getReason_name()%></td>
             <td><%=item.getReason_desc()%></td>
                         
             <td>
             <%
             if(all_deduction_reason_status!=null && all_deduction_reason_status.size()>0)
             {%>
             <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_REASON_STATUS+item.getReason_id()%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_REASON_STATUS+item.getReason_id()%>">
             <%
             String status_selected="";
             for(int j=0;j<all_deduction_reason_status.size();j++){
              StatusModel status = (StatusModel)all_deduction_reason_status.get(j);
              if(status.getStatus_name().equalsIgnoreCase(item.getReason_status_name()))
               status_selected="selected";
              else 
               status_selected="";
             %>
             <option <%=status_selected%> value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
             <%}%>
             </select>
             <%}%>
             </td>
             <td><input class="button" type="button" value="Edit" onclick="submitEdit(<%=item.getReason_id()%>)"></td>
             </TR>
         <%}%>
    </TABLE>
       <%}%>
        <br>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                <td align="center">
                <td align=center>
                        <input class=button type="button" name="new" value="New reason"
                               onclick="submitNew();">
                    &nbsp
                    <input class=button type="button" name="new" value="Update statuses"
                               onclick="submitUpdateStatuses();">
                </td>
                     
                </tr>
            </table>


        </form>
    </body>
</html>
