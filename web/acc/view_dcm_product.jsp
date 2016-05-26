<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.acc.*"

         import="com.mobinil.sds.core.system.acc.model.*"

         
%>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>

<body>

<script>

function setSearchValues(channelId,activationDateFrom,activationDateTo)
       {
         document.getElementById("<%=AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID%>").value = channelId;
         document.getElementById("<%=AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM%>").value = activationDateFrom;
         document.getElementById("<%=AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO%>").value = activationDateTo;
       }

 function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(ACCform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

 function checkBeforeSubmit()
 		{
	 		if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID%>.value") == "")
     		{
       			alert("Channel must not be empty.");
       			return;
     		}
	 		else if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM%>.value") == "*")
     		{
       			alert("Activation Date from must not be empty.");
       			return;
     		}
	 		else if(eval("document.ACCform.<%=AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO%>.value") == "*")
     		{
       			alert("Activation Date to must not be empty.");
       			return;
     		}
	 		ACCform.submit();
 		}
 </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector channels = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector dcmProducts = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
	  {String message = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
	  out.println("<script>alert('"+message+"');</script>");}
  String searchChannelId="";
  String searchActivationDateFrom = "*";
  String searchActivationDateTo = "*";
  if(objDataHashMap.containsKey(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID))
  {
	  searchChannelId = (String)objDataHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID);
  }
  if(objDataHashMap.containsKey(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM))
  {
	  searchActivationDateFrom = (String)objDataHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM);
  }
  if(objDataHashMap.containsKey(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO))
  {
	  searchActivationDateTo = (String)objDataHashMap.get(AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO);
  }
  %>
  
  <CENTER>
      <H2> DCMs Products Calculations </H2>
    </CENTER>
    <form name='ACCform' id='ACCform' action='' method='post'>
    <%

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");   

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 
  
  //out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
    //      " value=\""+channelId+"\">");  
  
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
      <td align=middle>Channel Id</td>
      <td align=middle>
            <select name='<%=AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID%>' id='<%=AccInterfaceKey.INPUT_SEARCH_TEXT_CHANNEL_ID%>'>
            <option value=''></option>
              <%
              for(int j=0;j<channels.size();j++)
              {
            	  channelModel model = (channelModel)channels.get(j);       
                  String channelId = model.getChannelId();
                  String channelName = model.getChannelName();
                %>
                <option value='<%=channelId%>'><%=channelName%></option>
                <%
              }
              %>
            </select>
        </td>
        
        <td align=middle>Activation Date From</td>
        <td align=middle><Script>drawCalender('<%=AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_FROM%>','<%=searchActivationDateFrom%>',"*");</script></td>
         <td align=middle>Activation Date To</td>
        <td align=middle><Script>drawCalender('<%=AccInterfaceKey.INPUT_SEARCH_TEXT_ACTIVATION_DATE_TO%>','<%=searchActivationDateTo%>',"*");</script></td>
         </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchDcmProduct\" id=\"searchDcmProduct\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_SEARCH_DCM_PRODUCT+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','*','*');\">");          
        %>
        </td>
      </tr>
    </TABLE>
    <br><br>
    <%
    float totalEValue =0.0f ;
    if(dcmProducts.size()!=0)
    {%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
    <td align='center'>DCM Name</td>
    <td align='center'>Product Name</td>
    <td align='center'>Line Count</td>
    <td align='center'>DCM Value</td>
    <td align='center'>Product Value</td>
    <td align='center'>Accrual Value</td>
    </tr>
    <%
    	int totalLineCount = 0;
    	
    	for(int i=0;i<dcmProducts.size();i++)
    	{
    		DcmProductEquationModel dcmProductEquationModel = (DcmProductEquationModel)dcmProducts.get(i);
    		String productId = dcmProductEquationModel.getProductId();
    		String productName = dcmProductEquationModel.getProductName();
    		String productValue = dcmProductEquationModel.getProductValue();
    		String dcmId = dcmProductEquationModel.getDcmId();
    		String dcmName = dcmProductEquationModel.getDcmName();
    		String dcmValue = dcmProductEquationModel.getDcmValue();
    		String lineCount = dcmProductEquationModel.getLineCount();
    		String transactionTypeId = dcmProductEquationModel.getTransactionTypeId();
    		String eValue = dcmProductEquationModel.getEValue();
    		totalLineCount = totalLineCount + Integer.parseInt(lineCount);
    		totalEValue = totalEValue + Float.parseFloat(eValue);
    		%>
    		
    		<tr class=<%=InterfaceKey.STYLE[i%2]%>>
    <td><%=dcmName%></td>
     <td><%=productName%></td>
     <td><%=lineCount%></td>
     <td><%=dcmValue%></td>
     <td><%=productValue%></td>
     <td><%=eValue%></td>
     </tr>
    		<%
    		}%>
    	<tr>
    	<td align=right colspan=2> Total Line Count</td>
    	<td><%=totalLineCount %></td>
    	<td align=right colspan=2> Total Accrual Value</td>
    	<td><%=totalEValue %></td>
    	</tr>
    </TABLE>
    
     <%
    }
      out.println("<script>setSearchValues('"+searchChannelId+"','"+searchActivationDateFrom+"','"+searchActivationDateTo+"');</script>");
      
      out.println("<input type=\"hidden\" name=\""+AccInterfaceKey.INPUT_HIDDEN_ACCRUAL_VALUE+"\""+
              " value=\""+totalEValue+"\">");   
%>
<br><br>
<center>
<%
if(dcmProducts.size()!=0){
        out.print("<INPUT class=button type=button value=\" Export to Excel \" name=\"export\" id=\"export\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_EXPORT_DCM_PRODUCT+"';"+
                  "ACCform.submit();\">");
        
        out.print("<INPUT class=button type=button value=\" Save Accrual Value \" name=\"save\" id=\"save\" onclick=\"document.ACCform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AccInterfaceKey.ACTION_SAVE_ACCRUAL_VALUE+"';"+
        "ACCform.submit();\">");
}
%>
</center>
</form>
</body>
</html>