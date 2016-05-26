<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import=" com.mobinil.sds.web.interfaces.cam.*"
         import=" com.mobinil.sds.core.system.cam.accrual.model.*"
         import ="com.mobinil.sds.core.system.cam.accrual.dao.*"
         import ="com.mobinil.sds.web.interfaces.InterfaceKey"
         import ="com.mobinil.sds.core.system.cam.payment.model.*"
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "payment_mgt_form";
        String pageHeader="DCM Payment Status For Distribution Channel"; 
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String action=  (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        Vector everyScmStatus=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS);
         
        Vector allScm = (Vector) dataHashMap.get(PaymentInterfaceKey.VECTOR_ALL_SCM_PAYMENT);
        
        Vector allStatusCases=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_SCM_PAYMENT_STATUS_CASES);
        
       // Vector payment_types=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPES);
        Vector dates=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_AUTO_DATES);
        Vector flag=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_MANGEMENT_MANUAL_FLAG);
        PaymentScreenManagementModel itemflag=(PaymentScreenManagementModel) flag.get(0);
        PaymentScreenManagementModel itemdates=(PaymentScreenManagementModel) dates.get(0);
        Vector statusReason=(Vector)dataHashMap.get(PaymentInterfaceKey.VECTOR_EVERY_SCM_PAYMENT_STATUS_REASON);
        
        String manOrAuto=(String) dataHashMap.get(PaymentInterfaceKey.CONTROL_WORKING_MODE);
        
        
        int start=itemdates.getStartDate();
        int end=itemdates.getEndDate();
        
        java.util.Date today=new java.util.Date();
        
        Calendar calendar=Calendar.getInstance();
        
        String status_id="-1";
        
%>
<script language="JavaScript">
function submitEdit(dcmCode)
{
document.<%=formName%>.<%=PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE%>.value = dcmCode;
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_EDIT_SCM_PAYMENT_STATUS_DISTRIBUTION%>";
document.<%=formName%>.submit();

}

function submitSearch( )
{

if (document.getElementById("<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>").value == "" &&
document.getElementById("<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID%>").value == ""
)
{
alert("you have to choose criteria");
return ;
}

document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_SEARCH_DISTRIBUTION_CHANNEL%>";
document.<%=formName%>.submit();
}
function submitUpdateAll()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=PaymentInterfaceKey.ACTION_UPDATE_ALL_SCM_PAYMENT_STATUS%>";
document.<%=formName%>.submit();
}
function nextPage()
{
  
  document.getElementById('<%=PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER%>').value = parseInt(document.getElementById('<%=PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER%>').value)+1;  
  document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=PaymentInterfaceKey.ACTION_SEARCH_DISTRIBUTION_CHANNEL%>';
  document.getElementById('<%=PaymentInterfaceKey.First_Time_Search%>').value='<%=PaymentInterfaceKey.First_Time_Search_FALSE%>'; 
  document.<%=formName%>.submit();
}

function prevPage()
{
  document.getElementById('<%=PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER%>').value -= 1;
  document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=PaymentInterfaceKey.ACTION_SEARCH_DISTRIBUTION_CHANNEL%>';    
  document.<%=formName%>.<%=PaymentInterfaceKey.First_Time_Search%>.value='<%=PaymentInterfaceKey.First_Time_Search_FALSE%>'; 
  document.<%=formName%>.submit();
}
function submitExportAsExcel() {
	
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=PaymentInterfaceKey.ACTION_EXPORT_DCM_DISTRIBUTION_PAYMENT_STATUS_EXCEL%>';
	document.<%=formName%>.submit();
}
function generatePaymentStatusTemplate()
{
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=PaymentInterfaceKey.ACTION_GENERATE_CHANNEL_PAYMENT_STATUS_TEMPLATE%>';
	document.<%=formName%>.submit();
}
function importPaymentStatus()
{
	document.getElementById('<%=InterfaceKey.HASHMAP_KEY_ACTION%>').value='<%=PaymentInterfaceKey.ACTION_VIEW_IMPORT_CHANNEL_PAYMENT_STATUS%>';
	document.<%=formName%>.submit();
}
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>

        <%
         
        %>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            <input type="hidden" name="<%=PaymentInterfaceKey.CONTROL_EDIT_TEXT_SCM_CODE%>" value="">
            <input type="hidden" name="<%out.print(PaymentInterfaceKey.First_Time_Search);%>" value="true">
		      <input type="hidden" name="<%out.print(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER);%>" value="<%
      if(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER) ==null)
        out.print("1");
      else 
        out.print(Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString()));
      %>">   
       <%if(allStatusCases!=null|| allStatusCases.size()!=0){%>
             <%if(action.equalsIgnoreCase(PaymentInterfaceKey.ACTION_VIEW_DISTRIBUTION_CHANNEL)){%>               
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
            <td colspan="4" align="center">Search</td>
            </tr>
            <tr>
            <td>SCM Name</td><td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>"></td>
            <td>SCM Code</td><td><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"></td>
            </tr>
            <tr>
            <td>Payment Status</td>
            <td>
            <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>">
            <option value=""></option>
            <%
             for(int k=0;k<allStatusCases.size();k++){
             PaymentStatusCasesModel c=(PaymentStatusCasesModel) allStatusCases.get(k);
            %>
             <option value="<%=c.getCamStatusForPaymentId()%>"><%=c.getCamStatusForPayment()%></option>
             <%}%>
             </select>
            </td>
            <td>Status Reason</td>
            <td>
            <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID%>">
            <option value=""></option>
            <%
             for(int k=0;k<statusReason.size();k++){
            	 PaymentStatusReasonModel c=(PaymentStatusReasonModel) statusReason.get(k);
            %>
             <option value="<%=c.getReasonId() %>"><%=c.getReason() %></option>
             <%}%>
             </select>
            </td>
            
            </tr>
            <tr>
             <td colspan="4" align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearch()"></td>
             </tr>
             </TABLE>
                        
            
            <br><br>               
               <%}
       }%>


               
               <%
              if(action.equalsIgnoreCase(PaymentInterfaceKey.ACTION_SEARCH_DISTRIBUTION_CHANNEL)||
              action.equalsIgnoreCase(PaymentInterfaceKey.ACTION_DO_EDIT_SCM_PAYMENT_STATUS)){
            	  
            	  /*
            	  System.out.println("in jsp everyScmStatus size: "+everyScmStatus.size());
            	  System.out.println("in jsp allscm size: "+allScm.size());
            	  System.out.println("in jsp allStatusCases size: "+allStatusCases.size());
            	  System.out.println("in jsp statusReason size: "+statusReason.size());
            	  */
            	  
              String scm_code=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE);
        String scm_name=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME);            
        status_id=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID);
        String reasonId=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID);
        if(reasonId.length()==0)
            reasonId="-1";
        if(status_id.length()==0)
        status_id="-1";
       // String payment_type_id=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_SELECT_SEARCH_PAYMENT_TYPES_ID);
       // if(payment_type_id.length()==0)
       // payment_type_id="-1";
              
            %>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
            <TR class=TableHeader>
            <td colspan="4" align="center">Search</td>
            </tr>
            <tr>
            <%
            if((String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME)!=null)
            {
            %>
            <td>SCM Name</td><td><input type="text" value="<%=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME)%>" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>"></td>
            <%
            }else{
            %>
            <td>SCM Name</td><td><input type="text"  name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_NAME%>"></td>
            <%
            }
            %>
            <%
            if((String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE)!=null)
            {
            %>
            <td>SCM Code</td><td><input type="text" value="<%=(String)dataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE)%>" name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"></td>
            <%
            }else{
            %>
            <td>SCM Code</td><td><input type="text"  name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"></td>
            <%
            }
            %>
                        
            </tr>
            <tr>
            <td>Payment Status</td>
            <td>
            <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_STATUS_ID%>">
            <option value=""></option>
            <%
            String status_selected="";
             for(int k=0;k<allStatusCases.size();k++){
             PaymentStatusCasesModel c=(PaymentStatusCasesModel) allStatusCases.get(k);
             
             if(c.getCamStatusForPaymentId()==Integer.parseInt(status_id))
             status_selected="selected";
             else
             status_selected="";             
              %>
              
             <option <%=status_selected%> value="<%=c.getCamStatusForPaymentId()%>"><%=c.getCamStatusForPayment()%></option>
             <%}%>
             </select>
            </td>
            <td>Status Reason</td>
            <td>
            <select name="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID%>" id="<%=PaymentInterfaceKey.CONTROL_SELECT_SEARCH_ALL_SCM_PAYMENT_REASON_ID%>">
            <option value=""></option>
            <%
             for(int k=0;k<statusReason.size();k++){
            	 PaymentStatusReasonModel c=(PaymentStatusReasonModel) statusReason.get(k);
            	 if(c.getReasonId()==Integer.parseInt(reasonId))
            		 status_selected="selected";
                 else
                	 status_selected=""; 
            %>
             <option <%=status_selected%> value="<%=c.getReasonId() %>"><%=c.getReason() %></option>
             <%}%>
             </select>
            </td>
            </tr>
            <tr>
             <td colspan="4" align="center"><input class="button" type="button" value="  Search  " onclick="javascript:submitSearch()"></td>
             </tr>
             </TABLE>
            <br>
            <%if(allScm != null && allScm.size()>0){ %>
            <table border="0" width="100%">
            <tr >
            <td align="center" colspan="5">
      <%
        if(Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>1)
          {
      %>
              <a href='javascript:prevPage();'>Previous</a>
      <%
          }
      %>
    
      page (<%=dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString()%>/<%=dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString()%>)
    
      <%
        if(Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())!=
            Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString())&& Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>=1)
          {
      %>
            <a href='javascript:nextPage();'>Next</a>
      <%
          }
      %>
    </td>
    </tr>
    </table>
             <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
              <td>SCM Name</td>
              <td>SCM Code</td>
              <td>SCM Payment Status</td>
              <td>Reason</td>
              <td>Edit</td>
              </TR>
          
             <%
             for(int i=0;i<allScm.size();i++){
              PaymentScmStatusModel item = (PaymentScmStatusModel)allScm.get(i);
              EveryDcmStatusModel everyDcmstatus=(EveryDcmStatusModel)everyScmStatus.get(i);
             %>
             <TR class="TableTextNote">
             <td><%=item.getDcmName()%></td>
             <td><%=item.getDcmCode()%></td>
             <%
             if(everyDcmstatus.getCamStatusForPayment().equals("NOT_ELIGIBLE"))
            {
             %>
             <td>Not Eligible</td>
             <td><%=everyDcmstatus.getReason()%></td>
             <%}
             else{
             %>
             <td>
             <select name="selectPaymentStatus+<%=everyDcmstatus.getScmId()%>"   id="selectPaymentStatus+<%=everyDcmstatus.getScmId()%>">
             <%            
             for(int j=0;j<allStatusCases.size();j++){
             PaymentStatusCasesModel cases=(PaymentStatusCasesModel) allStatusCases.get(j);
             if(cases.getCamStatusForPayment().equals("NOT_ELIGIBLE"))
            		 continue;
             if(cases.getCamStatusForPayment().equalsIgnoreCase(everyDcmstatus.getCamStatusForPayment()))
             status_selected="selected";
             else
             status_selected="";
             %>
             <option <%=status_selected%> value="<%=cases.getCamStatusForPaymentId()%>"><%=cases.getCamStatusForPayment()%></option>
             <%}%>
             </select>
             </td>
             <td><select name="selectStatusReason+<%=everyDcmstatus.getScmId()%>"   id="selectStatusReason+<%=everyDcmstatus.getScmId()%>">
             <%            
             for(int j=0;j<statusReason.size();j++){
             PaymentStatusReasonModel reason=(PaymentStatusReasonModel) statusReason.get(j);
             
             if(reason.getReason().equalsIgnoreCase(everyDcmstatus.getReason()))
             status_selected="selected";
             else
             status_selected="";
             %>
             <option <%=status_selected%> value="<%=reason.getReasonId()%>"><%=reason.getReason()%></option>
             <%}%>
             </select>
             </td>
             <%
             }%>
             <td>
             <%            
             if((manOrAuto.equals("AUTO")&&(calendar.get(Calendar.DAY_OF_MONTH)>=start&&calendar.get(Calendar.DAY_OF_MONTH)<=end))||(manOrAuto.equals("MANUAL") && itemflag.getFlag().equalsIgnoreCase("Enabled")))
            {
            %>
            <%
         
             if(!everyDcmstatus.getCamStatusForPayment().equals("NOT_ELIGIBLE")){
                         
             %>
             <input class="button" type="button" value="Edit" onclick="javascript:submitEdit('<%=item.getDcmCode()%>')">             
             <%} %>
            <%
            }
			%>
             </td>
             </TR>
         <%}%>
         </TABLE>
         <table border="0" width="100%">
            <tr></tr>
            <tr  >
            <td align="center" colspan="5" >
     			 <%
    	    if(Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>1)
        	  { %>
              <a href='javascript:prevPage();'>Previous</a>
      			<% }%>
    	     page (<%=dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString()%>/<%=dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString()%>)
    		     <%if(Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())!=
            Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_TOTAL_PAGES).toString())&& Integer.parseInt(dataHashMap.get(PaymentInterfaceKey.CONTROL_HIDDEN_PAGE_NUMBER).toString())>=1)
         		 {%>
            <a href='javascript:nextPage();'>Next</a>
      			<% }%>
    		</td>
            </tr>
            </table>
            <table width="100%">
            <%            
            if((manOrAuto.equals("AUTO")&&(calendar.get(Calendar.DAY_OF_MONTH)>=start&&calendar.get(Calendar.DAY_OF_MONTH)<=end))||(manOrAuto.equals("MANUAL") && itemflag.getFlag().equalsIgnoreCase("Enabled")))
            {
            %>
            
            <tr>
            <td align="center" colspan="5">
            <%
            if(!status_id.equals("2")){                         
             %>
            <input class="button" type="button" name="update" value="Update All"
                               onclick="javascript:submitUpdateAll()">
             <%} %>
            </td>
            </tr> 
            
            <%
            }
			%>
			<tr >
            <td align="center" colspan="5">
            <input class="button" type="button" name="export" value="Export As Excel"
                               onclick="javascript:submitExportAsExcel()">
             
                           
            </td>
            </tr>
			</table>
         <%}//end if all_scm%>
            
            
            
            
            
            <%}%>

			
			
            
         

        </form>
    </body>
</html>