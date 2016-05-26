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
        String form_name = "accrual_value_form";
        String page_header="Add Accrual value";
        String button_str="Add accrual value";

//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector all_accrual = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL);
        Vector all_reason = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_REASONS);
        Vector all_accrual_status = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS);
        String page_msg = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
        String action = (String) dataHashMap.get(AccrualInterfaceKey.ACTION_VIEW_MAKER_DATE_ENTRY);
        if(action == null)
        {
          page_header="Reverse Accrual value";
          button_str="Reverse accrual value";
          action = AccrualInterfaceKey.ACTION_DELETE_ACCRUAL_VALUE;
        }
        
%>
<script language="JavaScript">
 <%if(page_msg!=null){%>
 alert("<%=page_msg%>");
 <%} %>

function submitForm()
{
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
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=action%>">
             <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=strUserID%>">

             
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableTextNote>
              <td>Accrual</td>
              <td>
                   <%
                   if(all_accrual!=null && all_accrual.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.VECTOR_ALL_ACCRUAL%>" id="<%=AccrualInterfaceKey.VECTOR_ALL_ACCRUAL%>">
                   <option></option>
                   <%                   
                  for(int i=0;i<all_accrual.size();i++){
                  AccrualModel item = (AccrualModel)all_accrual.get(i);                
                   %>
                   <option value="<%=item.getAccrual_id()%>"><%=item.getAccrual_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
             </td>
             </TR>
              <TR class=TableTextNote>
              <td>Value</td><td>
              <input name="<%=AccrualInterfaceKey.CONTROL_TEXT_ACCRUAL_VALUE%>" type="text"></td>
              </TR>
             


             <TR class=TableTextNote>
              <td>Reason</td>
              <td>
             <%
             if(all_reason!=null && all_reason.size()>0)
             {%>
             <select name="<%=AccrualInterfaceKey.VECTOR_ALL_REASONS%>" id="<%=AccrualInterfaceKey.VECTOR_ALL_REASONS%>">
             <option></option>
             <%
             
             for(int j=0;j<all_reason.size();j++){
              ReasonModel reason = (ReasonModel)all_reason.get(j);
              
             %>
             <option  value="<%=reason.getReason_id()%>"><%=reason.getReason_name()%></option>
             <%}%>
             </select>
             <%}%>
             </td>
             </TR>  
             <%--
              <TR class=TableTextNote>
              <td>Status</td>
              <td>
             <%
             if(all_accrual_status!=null && all_accrual_status.size()>0)
             {%>
             <select disabled name="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS%>">
             <%
             String status_selected="";
             for(int j=0;j<all_accrual_status.size();j++){
              StatusModel status = (StatusModel)all_accrual_status.get(j);
              if(status.getStatus_name().equalsIgnoreCase("pending"))
               status_selected="selected";
              else 
               status_selected="";
             %>
             <option  <%=status_selected%>  value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
             <%}%>
             </select>
             <%}%>
             </td>
             </TR>  --%>
      </TABLE>
       <br/>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                        <input class=button type="button" name="new" value="<%=button_str%>"
                               onclick="submitForm();">
                    
                </tr>
            </table>


        </form>
    </body>
</html>
