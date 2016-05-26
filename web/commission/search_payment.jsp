<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.payment.*" 
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.commission.model.*"       
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.payment.model.*"
         import=" com.mobinil.sds.web.interfaces.cam.MemoInterfaceKey"
%>
<head>


<link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
      <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
<script language="javascript">

function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(COMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
function clearValues()
{
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME%>.value='';
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS%>.value=0;
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE%>.value=0;
  document.COMform.<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>.value="0";
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM%>.value='';  
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO%>.value='';
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM%>.value='';
  document.COMform.<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO%>.value='';  
}
function submitExport(payment_id)
{
	document.COMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=PaymentInterfaceKey.ACTION_EXPORT_PAYMENT_SHEETS_TO_EXCEL%>' ;
	document.COMform.<%=PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID%>.value= payment_id;
	document.COMform.submit();
}
 
</script>
</head>

<body>
<script language="javascript">
 $(document).ready(function(){$("#CommissionTable").tablesorter(); });
  </script>
  
<CENTER>
      <H2> Payment Search</H2>

    </CENTER>
<%
String  real_path = request.getRealPath("/cam/camMemosFiles/");
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strError = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
        if(strError != null)
        {
        
          out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
        
        }
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector paymentStatus = (Vector)objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_STATUS);
  Vector paymentTypeVector = (Vector)objDataHashMap.get(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE);
  Vector comChannelVec = (Vector) objDataHashMap
	.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector results = null;
  if(objDataHashMap.containsKey(PaymentInterfaceKey.VECTOR_PAYMENT_TYPE))
    results = (Vector)objDataHashMap.get(CommissionInterfaceKey.VECTOR_COMMISSION_SEARCH_RESULT);
  String paymentAction = (String)objDataHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION);
  String paymentHiddenStatus =(String)objDataHashMap.get(PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS);

  String paymentName = "";
  String paymentSearchStatus = "0";
  String paymentTypa = "0";
  String paymentChannel = "0";
  String paymentStartDateFrom = "*";
  String paymentStartDateTo = "*";
  String paymentEndDateFrom = "*";
  String paymentEndDateTo = "*";
  if(objDataHashMap.containsKey(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME)){ 
    paymentName = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME);
    paymentSearchStatus = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS);
    paymentTypa = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE);
    paymentChannel = (String)objDataHashMap.get(CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL);
    paymentStartDateFrom = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM);
    paymentStartDateTo = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO);
    paymentEndDateFrom = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM);            
    paymentEndDateTo = (String)objDataHashMap.get(PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO);            
    }
  ArrayList memo_error_msg=(ArrayList)objDataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG );

  Vector userPerVector = (Vector)objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_USER_VECTOR);
%>

<form name="COMform" action="" method="post">
<input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_APPLIACTION_CONTEXT %>" value="<%=real_path %>">
<input type="hidden" name="<%=PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT%>" value="<%=objDataHashMap.get(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT) %>">  
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

 out.println("<input type=\"hidden\" name=\""+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_STATUS+"\""+
                  " value=\""+paymentHiddenStatus+"\">");             

  out.println("<input type=\"hidden\" name=\""+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION+"\""+
                  " value=\""+paymentAction+"\">");             
                                    
  out.println("<input type=\"hidden\" name=\""+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+"\""+
                  " value=\""+"\">");             

%>
<script>
<%
String incoming_action = (String)objDataHashMap.get(MemoInterfaceKey.CONTROL_INCOMING_ACITON);
String incoming_actin = (String)objDataHashMap.get(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT);

if(incoming_action !=null){
	String download_page_url= request.getContextPath()+"/cam/PopUpDownloadPage.jsp";
	session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, objDataHashMap);
%> 
 
nw = window.open('<%=download_page_url%>','Warnings','width=400,height=400');
document.COMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=incoming_actin%>' ;
document.COMform.submit();
<%}%>
		 
		</script>
<table style="BORDER-COLLAPSE: collapse" width="100%" border="1" align="center" cellpadding="0" cellspacing="1">
  <tr>
    <td colspan="8" class="TableHeader">Search</td>
  </tr>
  <tr>
    <td class="TableTextNote"  colspan="2" align="center">Payment Name </td>
    <td class="TableTextNote"  colspan="2" align="center"><input type="text" name="<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_NAME%>" value="<%=paymentName%>"/></td>
    <td class="TableTextNote" colspan="2" align="center">Payment Status </td>
    <td class="TableTextNote" colspan="2" align="center"><select name="<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_STATUS%>">
    <option value="0"></option>
    <%
      for(int i = 0 ; i < paymentStatus.size() ; i++)
      {
        GenericModel paymentStatusModel = (GenericModel)paymentStatus.get(i);
        String selected = "";
        if(!paymentStatusModel.get_field_2_value().equals("Deleted")){
          if(paymentStatusModel.get_primary_key_value().equals(paymentSearchStatus))
            selected = "selected";
          out.println("<option value='"+paymentStatusModel.get_primary_key_value()+"' "+selected+">"+paymentStatusModel.get_field_2_value()+"</option>" );
          }
      }
    %>
    </select>    </td>
    </tr>
    <tr>
    <td class="TableTextNote" colspan="2" align="center">Payment Type </td>
    <td class="TableTextNote" colspan="2" align="center"><select name="<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_TYPE%>">
    <option value="0"></option>
    <%
      for(int i = 0 ; i < paymentTypeVector.size() ; i++)
      {
        GenericModel paymentTypeModel = (GenericModel)paymentTypeVector.get(i);
        String selected = "";
        if(paymentTypeModel.get_primary_key_value().equals(paymentTypa))
          selected = "selected";        
        out.println("<option value='"+paymentTypeModel.get_primary_key_value()+"' "+selected+">"+paymentTypeModel.get_field_2_value()+"</option>" );
      }
    %>
    
    </select></td>
    <td class="TableTextNote" colspan="2" align="center">Payment Channel</td>
		<td class=TableTextNote colspan="2" ><select
			name='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>'
			id='<%=CommissionInterfaceKey.CONTROL_TEXT_COMMISSION_CHANNEL%>'>
			<option value="0"></option>
			<%
				
			for(int i = 0 ; i < comChannelVec.size() ; i++)
		      {
				CommissionChannelModel commissionChannelModel = (CommissionChannelModel) comChannelVec
				.get(i);
		        String selected = "";
		        if(commissionChannelModel.getCommissionChannelId().equals(paymentChannel))
		          selected = "selected";        
		        out.println("<option value='"+commissionChannelModel.getCommissionChannelId()+"' "+selected+">"+commissionChannelModel.getCommissionChannelName()+"</option>" );
		      }
			%>
		</select></td>
  </tr>
  <tr>
    <td class="TableTextNote" colspan="2" align="center">Payment Start Date From</td>
    <td class="TableTextNote" colspan="2" align="center"><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_FROM%>' , "<%=paymentStartDateFrom%>");</script></td>
    <td class="TableTextNote" colspan="2" align="center">Payment Start Date To</td>
    <td class="TableTextNote" colspan="2" align="center"><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_START_DATE_TO%>' , "<%=paymentStartDateTo%>");</script></td>
  </tr>
  <tr>
    <td class="TableTextNote" colspan="2" align="center">Payment End Date From</td>
    <td class="TableTextNote" colspan="2" align="center"><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_FROM%>' , "<%=paymentEndDateFrom%>");</script></td>
    <td class="TableTextNote" colspan="2" align="center">Payment End Date To</td>
    <td class="TableTextNote" colspan="2" align="center"><script>drawCalender('<%=PaymentInterfaceKey.CONTROL_TEXT_PAYMENT_END_DATE_TO%>' , "<%=paymentEndDateTo%>");</script></td>
  </tr>
  
  <tr>
    <td colspan="8" align="center">
      <input name="Submit" type="button" class="button" value="Search" onclick="document.COMform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value='<%=PaymentInterfaceKey.ACTION_PAYMENT_SEARCH_PAYMENT%>';COMform.submit();"/>
      <input name="Submit2" type="button" class="button" value="Clear Values" onclick="clearValues();"/>
    </div></td>
  </tr>
</table>
<p>&nbsp;</p>
<%  if(results!=null&&results.size() != 0)
{
%>
<table class="tablesorter" id="CommissionTable">
<thead>
  <tr>
  	<th>ID</th>
    <th>Name</th>
    <th>Type</th>
    <th nowrap>Start Date</th>
    <th nowrap>End Date</th>
    <th>Status</th>
    <th>Export To Excel</th>
    <%
    if(!paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_ALL_PAYMENT))
    {
    %>
    <th><%=paymentAction%></th>
    <th>View</th>
    <th>Export</th>
   
    <th>Delete</th>
    
    <%        if(paymentHiddenStatus.equals("1"))
    {
    %>
    <th>Commissions</th>
    <%
    }
          if(paymentHiddenStatus.equals("2"))
    {
    %>
    <th>Add To Account Module</th>
    <%
    }
    }%>
  </tr>
  </thead>
<%
  Utility.logger.debug("SIZE  "+results.size());
  out.println("<tbody>");
  for(int i = 0 ; i < results.size() ; i++)
  {
    PaymentModel paymentModel = (PaymentModel)results.get(i);
    String style = "";

    if(!paymentModel.getPaymentStatusName().equals("Deleted")){
    out.println("<tr>");
   	    out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" >"+paymentModel.getPaymentID()+"</td>");
        out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" >"+paymentModel.getPaymentName()+"</td>");
        out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" >"+paymentModel.getPaymentTypeName()+" </td>");
        out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" nowrap>"+paymentModel.getPaymentStartDate()+"</td>");
        out.println(" <td class=\""+InterfaceKey.STYLE[i%2]+"\" nowrap>"+paymentModel.getPaymentEndDate()+"</td>");
        out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" >"+paymentModel.getPaymentStatusName());
        out.println("</td>");
        out.println("<td><input  type=\"button\" class=\"button\" value=\"Export to excel\" onclick=\"submitExport("+paymentModel.getPaymentID()+")\"</td>");
        
        if(!paymentAction.equals(PaymentInterfaceKey.INPUT_HIDDEN_ALL_PAYMENT))
        {
          if(paymentHiddenStatus.equals(String.valueOf(paymentModel.getPaymentStatusID())))
        
          {
            out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
if (paymentAction.compareTo("Close")==0){
 strError = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
        if(strError != null)
        {
        
          
        
        }else{
            out.println("<input name=\"Submit3\" type=\"button\" class=\"button\" value=\""+paymentAction+"\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_STATUS+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");}
        }else{
            out.println("<input name=\"Submit3\" type=\"button\" class=\"button\" value=\""+paymentAction+"\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_UPDATE_PAYMENT_STATUS+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");
              }

            out.println("    </td>");
            out.println("    <td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
            out.println("      <input name=\"Submit33\" type=\"button\" class=\"button\" value=\"View\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_VIEW_PAYMENT+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");
            out.println("    </td>");
            out.println("    <td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
            out.println("      <input name=\"Submit32\" type=\"button\" class=\"button\" value=\"Export\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_EXPORT_PAYMENT_TO_EXCEL+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");
            out.println("    </td>");
            out.println("    <td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">"); 
            
            if(userPerVector.contains(Integer.parseInt(strUserID)))
			{
            out.println("      <input disabled name=\"Submit32\" type=\"button\" class=\"button\" value=\"Delete\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_DELETE_PAYMENT+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");
			}
            else
            {
            	out.println("      <input name=\"Submit32\" type=\"button\" class=\"button\" value=\"Delete\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_DELETE_PAYMENT+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");
            }
            
              out.println("    </td>");                        
            if(paymentHiddenStatus.equals("1"))
            {
              out.println("    <td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
              out.println("      <input name=\"Submit32\" type=\"button\" class=\"button\" value=\"Commissions\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_PAYMENT_COMMISSION+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "COMform.submit();\"/>");

              out.println("    </td>");
            }
            if(paymentHiddenStatus.equals("2"))
            {
              out.println("    <td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
              out.println("      <input name=\"Submit32\" type=\"button\" class=\"button\" value=\"Add\" onclick=\""+
                        "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_ADD_TO_ACCOUNT_MODULE+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ID+".value='"+paymentModel.getPaymentID()+"';"+
                        "document.COMform."+PaymentInterfaceKey.INPUT_HIDDEN_PAYMENT_ACTION+".value='"+PaymentInterfaceKey.INPUT_HIDDEN_ADD_TO_ACCOUNT+"';"+
                        "COMform.submit();\"/>");

              out.println("    </td>");
            }
            

            
          
          }
          else
          {
             out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
             out.println("    </td>");
             out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
             out.println("    </td>");
             out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");
             out.println("    </td>");
             out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");           
             out.println("    </td>");
             out.println("<td class=\""+InterfaceKey.STYLE[i%2]+"\" align=\"center\">");           
             out.println("    </td>");

          }
        }
      out.println("  </tr>");
      }
    }
    out.println("</tbody>");
    
  %>
</table>
<%}%>


<p align="center">
<%
out.println("<input type='button' name='addpayment' class='button' value='Create New Payment' onclick=\""+
            "document.COMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+PaymentInterfaceKey.ACTION_CREATE_NEW_PAYMENT+"';"+
            "COMform.submit();"+
            "\">");
%>

</p>
</form>
</body>
</html>
