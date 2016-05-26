<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>

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

<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector channels = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector dcmProducts = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  response.addHeader("Content-Disposition", "attachment; filename=report.xls");
  
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

 <%
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
    }
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
    	
    </TABLE>
    
     
</form>
</body>
</html>