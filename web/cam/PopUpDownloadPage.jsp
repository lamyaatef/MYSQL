
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page import="java.util.HashMap"
 import="java.util.*"
 import=" com.mobinil.sds.web.interfaces.cam.*"
 import=" com.mobinil.sds.web.interfaces.payment.*"%>

<%@page import="java.io.FileInputStream"%>
<%
try{
	 String appName = request.getContextPath();
		HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		System.out.println("aeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		boolean data_cam_fromSession=false;
		if(dataHashMap==null)
		{
			dataHashMap = (HashMap)session.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
			data_cam_fromSession=true;
		}
		ArrayList memo_error_msg=(ArrayList)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG );
		ArrayList memo_error_msg_TBViewed=(ArrayList)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG_TOBEVIEWED );
		//System.out.print("errors "+memo_error_msg);
		//System.out.print("errors "+memo_error_msg_TBViewed);
		//String incoming_actin = (String)dataHashMap.get(MemoInterfaceKey.CONTROL_INCOMING_ACITON);
		String incoming_actin = (String)dataHashMap.get(PaymentInterfaceKey.ACTION_VIEW_READY_PAYMENT);
		//System.out.print("action "+incoming_actin);
	 
		%>
		
<%@page import="com.mobinil.sds.web.interfaces.payment.PaymentInterfaceKey"%><html>
		 

		<head>
		<LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
		<script>
	 
        function submitSave()
        {
            document.Myform.submit();
        }
		 <%
				if(memo_error_msg!=null && memo_error_msg.size()>0){
					
					String error_msg="";
					for(int i=0;i<memo_error_msg.size();i++)
					{
						error_msg = (String)memo_error_msg.get(i);
					//	System.out.println("aaaaaaaaaaaaaa "+error_msg);
					%>
					alert("<%=error_msg%>");
					<%}%>
					
					window.close();
					<%
					}
					%>
		</script>
   
 
	 
    </head>
    <body>
    <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.UploadingFileServlet" name="Myform" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=incoming_actin %>">

<%
if(memo_error_msg_TBViewed!=null && memo_error_msg_TBViewed.size()>0){
%>
<table width="100%" align="center">
<tr class="TableHeader" align="center">
<td>Num.</td>
<td>Message</td>
</tr>

<%
String error_msg="";
for(int i=0;i<memo_error_msg_TBViewed.size();i++)
{
	error_msg = (String)memo_error_msg_TBViewed.get(i);
%>
<tr class="TableTextNote">
<td><%=(i+1) %></td>
<td><%=error_msg %></td>
</tr>
<%}
%>
</table>
<%
}%>
<table width="50%" align="center">
<tr>
<td><input type="button" class="button" value="Save" onclick="submitSave()"/></td>
<td><input type="button" class="button" value="Close" onclick="window.close()"/></td>
</tr>

</table>

    </form>
		
		<%		
}catch(Exception e){e.printStackTrace();}		
%>
</body>
    </html>
 
