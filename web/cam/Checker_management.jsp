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
                
        String form_name = "accrual_mgt_form";
        String page_header="Makers Accrual Added/Reverse Amounts";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String page_msg = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
        //Vector all_accrual = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL);
        Vector all_accrual_value = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_MAKER_VALUE);
        Vector all_accrual_status = (Vector) dataHashMap.get(AccrualInterfaceKey.VECTOR_ALL_ACCRUAL_STATUS);
        
        
        
%>
<script language="JavaScript">
 <%if(page_msg!=null){%>
 alert("<%=page_msg%>");
 <%} %>
 
function submitUpdateStatuses()
{

document.<%=form_name%>.submit();
}

function submitSearch()
{
  document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=AccrualInterfaceKey.SEARCH_CHECKER_MANAGEMENT%>";
  document.<%=form_name%>.submit();
}
</script>

<%@page import="com.mobinil.sds.core.utilities.Utility"%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=page_header%></h2></center>
       
        
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=form_name%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=AccrualInterfaceKey.ACTION_CHECKER_APPROVE_ACCRUAL_VALUE%>">
             <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=strUserID%>">
           
           <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <TD>Maker Value Type</TD>
              <TD>
                 
                   <select name="<%=AccrualInterfaceKey.CONTROL_SEARCH_VALUE_TYPE%>" id="<%=AccrualInterfaceKey.CONTROL_SEARCH_VALUE_TYPE%>">
                   <option></option>
                   <option value="1">Add amount</option>
                   <option value="-1">Reverse amount</option>
                   </select>
                   
              </TD>
              <TD>Status</TD>
              <TD>
                  <%
                   if(all_accrual_status!=null && all_accrual_status.size()>0)
                   {%>
                   <select name="<%=AccrualInterfaceKey.CONTROL_SELECT_SEARCH_ALL_ACCRUAL_STATUS%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_SEARCH_ALL_ACCRUAL_STATUS%>">
                   <option></option>
                   <%
                
                   for(int j=0;j<all_accrual_status.size();j++){
                    StatusModel status = (StatusModel)all_accrual_status.get(j);
                    
                   %>
                   <option value="<%=status.getStatus_id()%>"><%=status.getStatus_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
              </TD>
              
              </TR>
           </table>
               <table  cellspacing="2" cellpadding="1" border="0" width="100%">
            <tr>
            <td align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearch()"></td>
             </tr>
             </table>
             
             
             
            <%
                if(all_accrual_value != null && all_accrual_value.size()>0){
            %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td width="15%">Accrual</td>
              <td width="15%">Maker</td>
              <td width="15%">Date</td>
              <td width="15%">Value</td>
              <td width="10%">Type</td>
              <td width="20%">Reason</td>
              <td width="10%">Status</td>
              
              </TR>
             
             <%
             String item_type="Add amount";
             double value=0;
             for(int i=0;i<all_accrual_value.size();i++){
              MakerAccrualValueModel item = (MakerAccrualValueModel)all_accrual_value.get(i);
              String item_date = Utility.getFormattedDate(item.getValue_date());
              value = item.getAccrual_value();
              if(value<0)
              {
                item_type="Delete amount";
                value = value * -1;
              }
              else
              {
            	  item_type="Add amount";
              
              }
             %>
             <TR class=TableTextNote>
             <td><%=item.getAccrual_name()%></td>
             <td><%=item.getMaker_name()%></td>
             <td><%=item_date%></td>
             <td><%=value%></td>
             <td><%=item_type%></td>
             <td><%=item.getReason_name()%></td>
             <td>
             <%
             if(all_accrual_status!=null && all_accrual_status.size()>0)
             {
             String select_dis="";
             if(!item.getStatus_name().equalsIgnoreCase("Pending") || item.getMaker_id()==Integer.parseInt(strUserID))
                select_dis="disabled";
             %>
             <select <%=select_dis%> name="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS+item.getValue_id()%>" id="<%=AccrualInterfaceKey.CONTROL_SELECT_ALL_ACCRUAL_STATUS+item.getValue_id()%>">
             <%
             String status_selected="";
             for(int j=0;j<all_accrual_status.size();j++){
              StatusModel status = (StatusModel)all_accrual_status.get(j);
              if(status.getStatus_name().equalsIgnoreCase(item.getStatus_name()))
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
         <%}%>
    </TABLE>
       <%}%>
        <br>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                    <td align=center>
                         <input class=button type="button" name="new" value="Update Status"
                               onclick="submitUpdateStatuses();">
                    </td>
                     
                </tr>
            </table>

 
        </form>
    </body>
</html>
