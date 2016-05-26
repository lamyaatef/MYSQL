<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.ac.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <script>
function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
}

function checkBeforeSubmit()
{
 if(eval("document.AUTHform.<%=AuthCallInterfaceKey.INPUT_TEXT_CONTRACT_DIAL_NUMBER%>.value") == "")
    {
      alert("Dial Number must not be empty.");
      return;
    }

 else if(!IsNumeric(eval("document.AUTHform.<%=AuthCallInterfaceKey.INPUT_TEXT_CONTRACT_DIAL_NUMBER%>.value")))
 {
       alert("Dial Number must be a number.");
       return; 
 }
    
 AUTHform.submit();
  }
</script>
  <%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  String userID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  System.out.println("The user id isssssssssssssss " + userID);
  //Vector contractDataVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
  String contractDailNumber = "";
  contractDailNumber = (String)objDataHashMap.get(AuthCallInterfaceKey.INPUT_HIDDEN_DIAL_NUMBER);
  String batchStatusTypeId = "";
  String date = "";
  String batchType = "";
  String dcmId = "";
  %>
  <CENTER>
      <H2> Contract Data </H2>
    </CENTER>  
    <form name='AUTHform' id='AUTHform' action='' method='post'>
    <%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
    
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+"\""+
            " value=\""+batchStatusTypeId+"\">");
    
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+"\""+
            " value=\""+date+"\">");
    
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
            "\" value="+userID+">");
    
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+"\""+
            " value=\""+batchType+"\">");

    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+"\""+
            " value=\""+dcmId+"\">");
    
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\""+
            " value=\""+ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE+"\">");
%>
 <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="60%" border=1 align="center">
      <TR>
        <td class=TableHeader>Please Enter Contract Dial Number : </td>
        <td><input type="text" name="<%=AuthCallInterfaceKey.INPUT_TEXT_CONTRACT_DIAL_NUMBER%>" id="<%=AuthCallInterfaceKey.INPUT_TEXT_CONTRACT_DIAL_NUMBER%>" value ="<%=contractDailNumber %>"></td>
      </tr>
      <tr><td colspan=2 align="center">
      <%
      out.print("<INPUT class=button type=button value=\" Search \" name=\"searchScratch\" id=\"searchScratch\" onclick=\"document.AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+AuthCallInterfaceKey.ACTION_SEARCH_CONTRACT_DATA+"';"+
                  "checkBeforeSubmit();\">");
      
      out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
      "\" value=\"\">");
                  %>
        </td> 
      </tr>
      
     </table> 
     <br><br>
     <%
     	/*if (contractDataVec.size()!=0)
     	{System.out.println ("The vecor size isssssssssss " + contractDataVec.size());
     	String batchId = "";
     	out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
     	for(int i=0;i<contractDataVec.size();i++)
     	{
     		ContractDialNumberModel contractDialNumberModel = (ContractDialNumberModel)contractDataVec.get(i);
     		String contractDialNumber = contractDialNumberModel.getContractDialNo();
     		batchId = contractDialNumberModel.getBatchId();
     		String batchDate = contractDialNumberModel.getBatchDate();
     		String batchStatusName = contractDialNumberModel.getBatchStatusTypeName();
     		String batchTypeName = contractDialNumberModel.getBatchTypeName();
     		String dcmName = contractDialNumberModel.getDcmName();
     		String contractStatusDate = contractDialNumberModel.getContractStatusDate();
     		String contractStatusName = contractDialNumberModel.getContractStatusTypeName();
     		out.println("<TR class=TableHeader>");
     	    out.println("<td nowrap align=center>");
     	    out.println("Contract Dail Number");
     	    out.println("</TD>");
     	    out.println("<td nowrap align=center>");
    	    out.println("DCM Name");
    	    out.println("</TD>");
    	    out.println("<td nowrap align=center>");
     	    out.println("Contract Status");
     	    out.println("</TD>");
     	    out.println("<td nowrap align=center>");
    	    out.println("Contract Status Date");
    	    out.println("</TD>");
    	    out.println("</TR>");
    	    out.println("<TR>");
    	    out.println("<td nowrap>"+contractDialNumber+"</td>");
    	    out.println("<td nowrap>"+dcmName+"</td>");
    	    out.println("<td nowrap>"+contractStatusName+"</td>");
    	    out.println("<td nowrap>"+contractStatusDate+"</td>");
    	    out.println("</TR>");
    	    out.println("<TR class=TableHeader>");
     	    out.println("<td nowrap align=center>");
     	    out.println("Batch ID");
     	    out.println("</TD>");
     	    out.println("<td nowrap align=center>");
    	    out.println("Batch Status");
    	    out.println("</TD>");
    	    out.println("<td nowrap align=center>");
     	    out.println("Batch Type");
     	    out.println("</TD>");
     	    out.println("<td nowrap align=center>");
    	    out.println("Batch Date");
    	    out.println("</TD>");
    	    out.println("</TR>");
    	    out.println("<TR>");
    	    out.println("<td nowrap>"+batchId+"</td>");
    	    out.println("<td nowrap>"+batchStatusName+"</td>");
    	    out.println("<td nowrap>"+batchTypeName+"</td>");
    	    out.println("<td nowrap>"+batchDate+"</td>");
    	    out.println("</TR>");
    	    out.println("</Table>");
    	    break;
     	}
     	Hashtable batchAuthStat = BatchDao.getBatchAuthStatistics(batchId);
     	Integer accVer= (Integer) batchAuthStat.get("ACCEPTED VERIFY");
        if (accVer ==null) accVer = new Integer(0);
        
        Integer accAuth= (Integer) batchAuthStat.get("ACCEPTED AUTHINTICATION");
        if (accAuth ==null) accAuth = new Integer(0);
        
        Integer rejAuth= (Integer) batchAuthStat.get("REJECTED AUTHINTICATION");
        if (rejAuth == null) rejAuth = new Integer(0);
        
        Integer percentage = (Integer)batchAuthStat.get("percentage");
        
        int total = accVer.intValue() + accAuth.intValue()+rejAuth.intValue();
        int neededAuth = Math.round(total * percentage.intValue()/100);
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
        out.println("<TR class=TableHeader>");
      out.println("<td nowrap align=center><font size=2>Accepted Verify</font></TD>");      
      out.println("<td nowrap align=center><font size=2>Accepted Authentication</font></TD>");
      out.println("<td nowrap align=center><font size=2>Rejected Authentication</font></TD>");
      out.println("<td nowrap align=center><font size=2>Required Authentication</font></TD>");
       out.println("<td nowrap align=center><font size=2>Remaining</font></TD>");
     
      out.println("</tr>");
         out.println("<TR>");
      out.println("<td nowrap>"+accVer+"</td>");
      out.println("<td nowrap>"+accAuth+"</td>");
      out.println("<td nowrap>"+rejAuth+"</td>");
      out.println("<td nowrap>"+neededAuth+"</td>");
      out.println("<td nowrap>"+(neededAuth-accAuth.intValue()-rejAuth.intValue())+"</td>");
         out.println("</TR>");
      
       out.println("</TABLE>");
       
       
       
     	}
     if(contractDataVec.size()>1)
     {
    	 int count = 0;
    	 out.println("<br>");
    	 out.println("<center>");
    	 
    	 for(int i=0;i<contractDataVec.size();i++)
      	{	
    		 ContractDialNumberModel contractDialNumberModel = (ContractDialNumberModel)contractDataVec.get(i);
    		 String contractDialNumber = contractDialNumberModel.getContractDialNo();
      		String batchId = contractDialNumberModel.getBatchId();
      		String batchDate = contractDialNumberModel.getBatchDate();
      		count++;
      		System.out.println("the count isssssssssssssssss " + count);
      		if(count>1)
      		{
      			out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
      		out.println("<TR class=TableHeader>");
     	    out.println("<td nowrap align=center>");
     	    out.println("Batch ID");
     	    out.println("</TD>");
     	   out.println("<td nowrap align=center>");
    	    out.println("Batch Date");
    	    out.println("</TD>");
    	    out.println("</TR>");
    	    out.println("<TR>");
    	    out.println("<td align=center nowrap><a href=\"javascript:"+
    	            "document.AUTHform."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AuthCallInterfaceKey.ACTION_SHOW_CHOOSE_CONTRACTS_FOR_AUTHENTICATION_CALL_SCREEN_BY_SHEET+"';"+
    	            "document.AUTHform."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+".value="+batchId+";"+
    	            "AUTHform.submit();\">");
    	            out.println(batchId+"</a></td>");
    	    //out.println("<td nowrap>"+batchId+"</td>");
    	    out.println("<td nowrap>"+batchDate+"</td>");
    	    out.println("</TR>");
      		}
    	    out.println("</Table>");
      	}
     }*/
     	%>
     
     </form>
  </body>
</html>