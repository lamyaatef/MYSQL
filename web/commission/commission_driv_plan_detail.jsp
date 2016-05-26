<%@page import="com.mobinil.sds.core.system.commission.model.CommissionModel"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"         
         import="com.mobinil.sds.core.system.commission.factor.model.*"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<link rel="stylesheet" type="text/css"
              href="../resources/css/Template1.css"/>
</head>
<center><H2>Commission Details</H2></center>
<body>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);  
  String commissionID = (String)objDataHashMap.get(CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID);
  CommissionModel comModel = (CommissionModel)objDataHashMap.get(CommissionInterfaceKey.COMMISSION_MODEL);
  
%>


<%
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
  out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.INPUT_HIDDEN_COMMISSION_ID+"\""+
                  " value=\""+commissionID+"\">");             
                                    
%>
<% if (comModel!=null) {%>
<table style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px"
       cellSpacing=2 cellPadding=1 width="746" border=1>


<TR class="TableTextNote">
    <TD class="TableTextNote" style="white-space: nowrap; ">Driving Plan Name</TD>
    <TD class="TableTextNote" ><%=comModel.getDpName() %></TD>
</TR>
<TR class="TableTextNote">
    <TD class="TableTextNote" style="white-space: nowrap; ">Data View Name</TD>
    <TD class="TableTextNote"><%=comModel.getDataViewName()  %></TD>
    
</TR>
<TR class="TableTextNote">
    <TD class="TableTextNote" style="white-space: nowrap; ">Data View SQL </TD>
    <TD class="TableTextNote"><%= comModel.getCommissionDataViewSQL()  %></TD>
</TR>
<TR class="TableTextNote">
    <TD colspan="2" align="center" ><input type="button" value="Back" onClick="history.go(-1);" /> </TD>
  
</TR>
</tbody>
</table>
<%} else {%>
<br>
<center><H2>There is no details for this commission</H2>
<input type="button" value="Back" onClick="history.go(-1);" />
</center>
<%}%>

</body>
</html>
