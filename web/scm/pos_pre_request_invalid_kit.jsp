<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"
         import="com.mobinil.sds.core.system.request.model.SupervisorModel"
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>POS Pre-Request</title>
 <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
</head>
<body>
<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String appName = request.getContextPath();

Vector<String> invalidStkList = (Vector<String>) objDataHashMap.get(SCMInterfaceKey.REP_KIT_STK_INVALID_LIST);
Vector<String> validStkList = (Vector<String>) objDataHashMap.get(SCMInterfaceKey.REP_KIT_STK_VALID_LIST);

String stkQuantity = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED);
String posQuantity = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_POS_QUANTITY);

String posFrom = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_POS_FROM);
String posTo = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_POS_TO);

String supervisorId = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_SUPERVISOR_ID);
String supervisorName = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_SUPERVISOR_NAME);
String alert = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_Alert);

String requestDate = (String) objDataHashMap.get(SCMInterfaceKey.REP_KIT_DATE);
String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

%>

<body onload="divAppearance()">
<br>
 <Center>
      <H2>POS Pre-Request</H2>
 </Center>
 <br>
    
 <form id="formRepKit" name="formRepKit" action="" method=post>
 
 <input type="hidden" name=<%=InterfaceKey.HASHMAP_KEY_ACTION%> value="<%= SCMInterfaceKey.ACTION_STORE_AFTER_PRINT_REQUEST %>">
 <input type=hidden name=<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value=<%=strUserID %> >
 
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_STK_INVALID_LIST%> value="<%= invalidStkList %>">
 <input type=hidden name=<%=SCMInterfaceKey.REP_KIT_STK_VALID_LIST%>" value=<%= validStkList %> >
 
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_STK_QUANTITY_REQUIRED%> value="<%= stkQuantity %>">
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_POS_QUANTITY%> value="<%= posQuantity %>">
 
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_POS_FROM%> value="<%= posFrom %>">
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_POS_TO%> value="<%= posTo %>">
  
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_SUPERVISOR_ID%> value="<%= supervisorId %>">
 <input type="hidden" name=<%=SCMInterfaceKey.REP_KIT_Alert%> value="<%= alert %>">
 
 
 <div name= "alertDiv" id="alertDiv" style="visibility:hidden">
 <table align="center">
 <tr>
 <td colspan="2"><%= alert %></td>
 </tr>
 <%
   for(int i = 0 ; i < invalidStkList.size() ; i++)
   {
	   %>
	   <tr>
        <td>STK Number: </td>
        <td><%= invalidStkList.get(i) %></td>
      </tr>
	   <% 
   }
 %>
 <tr>
 <%
    if(posQuantity.equals("0") && validStkList.size() ==0 )
    {
 %>
  <td colspan="2" align="center"> <input class=button  disabled="disabled" name="continuButton" id="continuButton" value="Continue" type=button onclick="continueAction()" /></td>
  <%
    }
  else
  {
	  %>
	   <td colspan="2"  align="center"> <input class=button  name="continuButton" id="continuButton" value="Continue" type=button onclick="continueAction()" /></td>
	  <% 
  }
  %>
           <!--td> <input class=button  name="backButton" id="backButton" value="Back" type=button onclick="history.go(-1);return false;" /> &nbsp;</td-->
 </tr>
 </table>
 
 <br>
 </div>
<iframe id='ifrmPrint' src='#' style="width:0px; height:0px;"></iframe>
 
 <div align="center" name= "reportDiv" id="reportDiv" style="visibility:hidden">
   
   <table width="100%" height="70%" >
    <tr>
     <td width="20%"></td>
     <td><IMG align="right" width="100" height="50" src="<%out.print(appName);%>/resources/img/main_logo_mobinil.gif" /></td>
     <td width="20%"></td>
    </tr>
    
    <tr>
         <td width="20%"></td>
    <td>
      <center><font style="font-size: 18px;font-family: arial;"><u>POS Code PreRequest Declaration</u></font></center>
     <br>
    </td>
     <td width="20%"></td>
    </tr>
 
    <tr>
        <td width="20%"></td>
     <td>

     <center>
     <table name=requesterTable id=requesterTable   width="100%" height="40%">
     <tr>
      <td><font style="font-size: 16px; font-family: arial;">Requester : </font></td>
      <td><font style="font-size: 16px; font-family: arial;"><%= supervisorName %></font></td>
      <td colspan="2"></td>
     </tr>
     <tr>
      <td><font style="font-size: 16px; font-family: arial;">Request Date : </font></td>
      <td><font style="font-size: 16px; font-family: arial;"><%= requestDate %></font></td>
      <td colspan="2"></td>
     </tr>
     <tr>
       <td><font style="font-size: 16px; font-family: arial;">POS Code From : </font></td>
       <td><font style="font-size: 16px; font-family: arial;"><%= posFrom %></font></td>
       <td><font style="font-size: 16px; font-family: arial;">POS Code To :</font></td>
       <td><font style="font-size: 16px; font-family: arial;"><%= posTo %></font></td>
     </tr>
     </table>
     </td>
     <td width="20%"></td>
    </tr>
    
    <tr>
        <td width="20%"></td>
     <td>

       <table width="100%" height="40%">
         <tr> 
         <td colspan="3"><b><font style="font-size: 16px; font-family: arial;">&nbsp;STK Dials : </font></b></td>
         <td></td>
         </tr>
         
         
          <%
            for(int i = 0 ; i < validStkList.size() ; i++)
            {
	          %>
	           <tr>
               <td colspan="3"></td>
               <td><font style="font-size: 16px; font-family: arial;"><%= validStkList.get(i) %></font></td>
               </tr>
	          <% 
            }
       %>
  
       </table>
     </td>
     <td width="20%"></td>
    </tr>
    
    <tr>
        <td width="20%"></td>
     <td align=right><font style="font-size: 16px; font-family: arial;">Signature</font></td>
     <td width="20%"></td>
    </tr>
    
     
   </table>
      </div>
      <div name=printDiv id=printDiv style="visibility:hidden">  
    <br>
     <table align="center"><tr>
     <td><center><input class=button  type="button"  value="Print" onclick="printDiv()"></center></td>
    </tr></table>
    </div>

 
       <div align="center" id="ifmcontentstoprint" name="ifmcontentstoprint" style="height: 0px; width: 0px; position: absolute">

       </div>
 
 
 </form>
</body>
</html>

<script>
function divAppearance()
{
	if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_Alert%>.value") != "")
	{
		 document.getElementById("alertDiv").style.visibility = 'visible';
		 document.getElementById("reportDiv").style.visibility= 'hidden';	
	}
	else
	{
		 document.getElementById("reportDiv").style.visibility = 'visible';
		 document.getElementById("printDiv").style.visibility = 'visible';
		 document.getElementById("requesterTable").style.border = "1px solid black";
		 document.getElementById("requesterTable").style.RULES = 'NONE';
		 document.getElementById("requesterTable").style.FRAME = 'BOX';
		 document.getElementById("alertDiv").style.visibility= 'hidden';		
	}
}

function continueAction()
{
	if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_Alert%>.value") != "")
	{
		 document.getElementById("alertDiv").style.visibility= 'hidden';	
		 document.getElementById("reportDiv").style.visibility = 'visible';	
		 document.getElementById("printDiv").style.visibility = 'visible';
		 document.getElementById("requesterTable").style.border = "1px solid black";
		 document.getElementById("requesterTable").RULES = "NONE";
		 document.getElementById("requesterTable").FRAME = "BOX";
		 
	}
}


function printDiv()
{
	try{
        var oIframe = document.getElementById('ifrmPrint');
        var oContent = document.getElementById('reportDiv').innerHTML;
        var oDoc = (oIframe.contentWindow || oIframe.contentDocument);
        if (oDoc.document) oDoc = oDoc.document;
		oDoc.write("<html><head><title></title>");
		oDoc.write("</head><body onload='this.focus(); this.print();'>");
		oDoc.write(oContent + "</body></html>");
		oDoc.close();
    }
    catch(e){
	    self.print();
    }

	if(eval("document.formRepKit.<%=SCMInterfaceKey.REP_KIT_Alert%>.value") != "")
	{
	  formRepKit.submit();
	}	
}
</script>

