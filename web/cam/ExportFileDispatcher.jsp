	<%@ page language="java" contentType="text/html; charset=windows-1256"
	    pageEncoding="windows-1256"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
	
	<%@page import="java.io.InputStream"%>
	<%@page import="java.io.File"%>
	<%@page import="com.mobinil.sds.core.utilities.Utility"%>
	<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
	<%@page import="java.util.HashMap"
	 import="java.util.*"
	 import=" com.mobinil.sds.web.interfaces.cam.*"%>
	
	<%@page import="java.io.FileInputStream"%>
	<%
	 
		 String appName = request.getContextPath();
			HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
			if(dataHashMap==null)
			{
				dataHashMap = (HashMap )session.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
			}
			ArrayList memo_error_msg=(ArrayList)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG );
			ArrayList memo_error_msg_TBViewed=(ArrayList)dataHashMap.get(MemoInterfaceKey.VECTOR_MEMO_REPORT_ERROR_MSG_TOBEVIEWED );
			//System.out.print("errors "+memo_error_msg);
			System.out.print("errors "+memo_error_msg_TBViewed);
			String incoming_actin = (String)dataHashMap.get(MemoInterfaceKey.CONTROL_INCOMING_ACITON);
			System.out.print("actionnnnnnnnnnn "+incoming_actin);
			session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
			String download_page_url=appName+"/cam/PopUpDownloadPage.jsp";
			%>
			
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<script type="text/javascript">
	 
	var cont='';
	<%
	if(memo_error_msg_TBViewed!=null && memo_error_msg_TBViewed.size()>0){
	%>
	nw = window.open('<%=download_page_url%>','Warnings','width=400,height=400');
	<%}%>
	</script>
	</head>
	<body>
	   <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Myform" method="post">
	            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=incoming_actin %>">
	            <script type="text/javascript">
	            document.Myform.submit();
	            </script>
	    </form>
	</body>
	</html>