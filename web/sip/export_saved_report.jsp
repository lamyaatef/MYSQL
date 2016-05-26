<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"

         import="com.mobinil.sds.core.system.sip.model.*"
         
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<CENTER>
<H2>Saved  SIP Report </H2>
</CENTER>
<%
	HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
	
	String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
	
	System.out.println("The user id isssssss " + strUserID);
	
	
	
		
		Vector vecSpReport= (Vector)objDataHashMap.get(SIPInterfaceKey.VECTOR_SIP_ITEM_AMOUNT);	
		
	
	if(vecSpReport!=null)
	System.out.println("Vector size isssssssssssss " + vecSpReport.size());
%>
<Form name="SIPform" action="" method="POST">
 <% 
 out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+"\""+
                  " value=\""+"\">"); 
                   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                                
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

	%>
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
<%
   // if(vecSchemaProdcut.size()!=0)
    //{
%>
    <tr class=TableHeader>
    		<td align='center'>DCM ID</td>
     		<td align='center'>ITEM_NAME</td>
     		     		<td align='center'>ITEM_AMOUNT</td>
     		
     		     		<td align='center'>STATUS</td>
     		
    </tr>
<%
 //}
 for(int i=0;i<vecSpReport.size();i++)
 {
	 
	// SIPReportModel sipReportModel = (SIPReportModel) vecSchemaModules.get(i);

	  String dcm_id="";
	  String item_Name="";
	  String item_amount="";
	  String status="";
	  
	 sipReportItemModel sipReportModel=(sipReportItemModel) vecSpReport.get(i);
	  dcm_id=sipReportModel.getDCM_ID();
	  item_Name=sipReportModel.getSIP_REPORT_ITEM_NAME();
	  item_amount=sipReportModel.getSIP_REPORT_ITEM_AMOUNT();
	  status=sipReportModel.getINCLUDE();
	 
	 if(status.equals("0"))
	 {
		 
		 
		 status="Excluded" ;
		 
	 }
	 if(status.equals("1"))
	 {
		 
		 
		 status="Included" ;
		 
	 }
	
 

	
 %>
 <tr class=<%=InterfaceKey.STYLE[i%2]%>>
 		<td><%=dcm_id%></td>
  		<td><%=item_Name%></td>
  		<td><%=item_amount%></td>
  		<td><%=status%></td>
   		
   		
  
 </tr>
 <%} %>

</TABLE>


</Form>
</body>
</html>