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
         import="com.mobinil.sds.core.system.cam.memo.model.*"        
         
         %>
<%
        String appName = request.getContextPath();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String formName = "memo_mgt_form";
        String pageHeader="Add DCM To Be Eligible";
        HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String userID =(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String action=(String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
        //t dcmId=1;
        
        
        
        
%>
<script language="javaScript">
var dcmId=1;
function submitAdd()
{
	
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_ADD_PYAMENT_DCM_ELIGIBLE%>";
document.<%=formName%>.submit();
}
function submitAddDefault()
{

document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_ADD_PYAMENT_DCM_ELIGIBLE_FROM_GEN%>";
document.<%=formName%>.submit();
}
function submitSearch()
{
document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SEARCH_IN_MEMO%>";
document.<%=formName%>.submit();
}

function submitSetZero()
{
	document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=MemoInterfaceKey.ACTION_SET_NEGATIVE_TO_ZERO_IN_MEMO%>";
	document.<%=formName%>.submit();
}
function createInput(cell, text, style){  

	  var input = document.createElement('input'); // create DIV element  
	  
	  input.type = 'text';
	  input.name = "<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"+dcmId;
	  alert("<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"+dcmId)
	  input.id = 'txtRow' ;
	   cell.appendChild(input);                   // append DIV to the table cell  
	  dcmId++;
	  
	}

function appendRow(){  
	  var tbl = document.getElementById('myTable'); // table reference  
	  //var tbl = document.getElementById('myTable'); // table reference  
	  // append table row  
	  var row = tbl.insertRow(tbl.rows.length);  
	  // insert table cells to the new row  
	  for (var i=0; i<tbl.rows[1].cells.length; i++) 
	   	createInput(row.insertCell(i), i, 'row');  
		
		
		//createCell(row.insertCell(0),"From",'row');
		
		//createInput(row.insertCell(1),text,'row');
		
		//createCell(row.insertCell(2),"To",'row');
		
		//createInput(row.insertCell(3),text,'row');
		
		
	}


</script>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
        </head>
    <body>
        <center><h2> <%=pageHeader%></h2></center>
        <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
            
            
            
            
            
            <TABLE align="center" id="myTable" style="BORDER-COLLAPSE: collapse" cellSpacing="2" cellPadding="1" width="50%" border="1">
            <tr>
    		<td>DCM Code
			<A onClick="appendRow()">
              <IMG alt="Click Here to add New Row for add more dcms ... " src="<%out.print(appName);%>/resources/img/deu_down.gif" border=0>
            </A> 
			</td>
  			</tr>
			<tr><td ><input name="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>"  id="<%=PaymentInterfaceKey.CONTROL_TEXT_SCM_CODE%>" type="text"></td></tr>
	        </TABLE>
            
            <TABLE align="center">
            <tr><td colspan="2" align="center"><input class="button" type="button"  value="Set DCM" onclick="submitAdd()"></td></tr>            
            </TABLE>
            <br><br>
            
            <TABLE align="center">
            <tr><td ><input class="button" type="button" value="Add default Eligible DCM"  onclick="submitAddDefault()"></td></tr>            
            </TABLE>
            

            
                     
       </form>
    </body>
</html>