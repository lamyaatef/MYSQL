<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.deduction.model.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.common.model.*"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String form_name = "deduction_edit_form";
        String page_header="Edit Deduction";
//get data hashmap specidifed b4 in kpihandler
        
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
       // Vector all_deduciton = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION);
        Vector all_deduction_reason = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION_REASON);
        Vector all_deduction_status = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_DEDUCTION_STATUS);
        Vector all_pay_group_type = (Vector) dataHashMap.get(DeductionInterfaceKey.VECTOR_PAY_GROUP_TYPE);
        DeductionModel item = (DeductionModel)dataHashMap.get(DeductionInterfaceKey.MODEL_DEDUCTION);
        
%>
<script language="JavaScript">
 

function submitForm()
{
  document.<%=form_name%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=DeductionInterfaceKey.ACTION_UPDATE_DEDUCTION%>";
  
  var ele = document.getElementById('<%=DeductionInterfaceKey.CONTROL_TEXT_DEDUCTION_VALUE%>');

  
 
  if(ele.value == '' || isNaN(ele.value))
  {
	  alert('Deduction value can not be empty');
	  return;
  }

  
   
  ele = document.getElementById('<%=DeductionInterfaceKey.CONTROL_SELECT_PAY_GROUP_TYPE%>');

   
  
  if(ele.options[ele.selectedIndex].value == '')
  {
	  alert('Type Group value can not be empty');	
			  return;
  }	  

   
  ele = document.getElementById('<%=DeductionInterfaceKey.CONTROL_SELECT_REASON%>');
  if(ele.options[ele.selectedIndex].value == '')
  {
	  alert('Reaqson value can not be empty');
			  return;
  }		
  ele = document.getElementById('<%=DeductionInterfaceKey.CONTROL_SELECT_STATUS%>');
  if(ele.options[ele.selectedIndex].value == '')
  {
	  alert('Status value can not be empty');
			  return;
  }
  
   
  
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
            <input type="hidden" name="<%=DeductionInterfaceKey.CONTROL_DEDUCTION_ID%>" value="<%=item.getDeduction_id()%>">   
       
                       
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableTextNote>
              <td width="20%">Value</td><td><input type="text" name="<%=DeductionInterfaceKey.CONTROL_TEXT_DEDUCTION_VALUE%>" id="<%=DeductionInterfaceKey.CONTROL_TEXT_DEDUCTION_VALUE%>" value="<%=item.getDeduction_value()%>"></td>
              </TR>
               <TR class=TableTextNote>
              <td>DCM name</td><td><input type="text" disabled value="<%=item.getDcm_name()%>"></td>
              </TR>
               <TR class=TableTextNote>
              <td>Payment type group</td>
              <TD>
                  <%
                   if(all_pay_group_type!=null && all_pay_group_type.size()>0)
                   {%>
                   <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_PAY_GROUP_TYPE%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_PAY_GROUP_TYPE%>">
                   <option>
                   <%
                 String selected="";
                   for(int j=0;j<all_pay_group_type.size();j++){
                    PaymentTypeGroupModel type = (PaymentTypeGroupModel)all_pay_group_type.get(j);
                     if(type.getGroupName().equalsIgnoreCase(item.getPayment_group_type_name()))
                     selected="selected";
                    else 
                     selected="";
                   %>
                   <option  <%=selected%> value="<%=type.getGroupId()%>"><%=type.getGroupName()%></option>
                   <%}%>
                   </select>
                   <%}%>
              </TD>
              </TR>
               <TR class=TableTextNote>
              <td>Reason</td>
               <td>
                   <%
                   if(all_deduction_reason!=null && all_deduction_reason.size()>0)
                   {%>
                   <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_REASON%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_REASON %>">
                   <option>
                   <%
                  String selected="";
                   for(int j=0;j<all_deduction_reason.size();j++){
                   ReasonModel reason = (ReasonModel)all_deduction_reason.get(j);                     
                   if(reason.getReason_id() == item.getReason_id())
                     selected="selected";
                   else 
                     selected="";
                   %>
                   <option <%=selected%> value="<%=reason.getReason_id()%>"><%=reason.getReason_name()%></option>
                   <%}%>
                   </select>
                   <%}%>
             </td>
             </TR>
              <TR class=TableTextNote>              
              <td>Status</td>
              <td>
             <%
             if(all_deduction_status!=null && all_deduction_status.size()>0)
             {%>
             <select name="<%=DeductionInterfaceKey.CONTROL_SELECT_STATUS%>" id="<%=DeductionInterfaceKey.CONTROL_SELECT_STATUS%>">
             <option></option>
             <%
             String status_selected="";
             for(int j=0;j<all_deduction_status.size();j++){
              StatusModel status = (StatusModel)all_deduction_status.get(j);
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
               
      
    </TABLE>
        
        <br>
<table  cellspacing="2" cellpadding="1" border="0" width="100%">
                <tr>
                <td align="center">
                    <input class=button type="button" name="new" value="Update deduction"
                               onclick="submitForm();">
                </td>
                     
                </tr>
            </table>


        </form>
    </body>
</html>
