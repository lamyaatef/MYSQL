
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page import="java.util.HashMap"
 import="java.util.*"
 import=" com.mobinil.sds.web.interfaces.cam.*"%>

<%@page import="java.io.FileInputStream"%>
<%
try{
	 String appName = request.getContextPath();
		HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
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
		String incoming_actin = (String)dataHashMap.get(MemoInterfaceKey.CONTROL_INCOMING_ACITON);
		//System.out.print("action "+incoming_actin);
	 
		%>
		<html>
		 

		<head>
		
 
    </head>
    <body>
    <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Myform" method="post">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=incoming_actin %>">
            <script>
          
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
			document.Myform.submit();
			
			<%
			}
			%>
		</script>
    </form>
		<% 
		session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
	
		String fileName = (String) dataHashMap.get(InterfaceKey.EXPORT_FILE_PATH); // complete file path
		System.out.println("file name in jsp:"+fileName);
		String moduleSubPath = (String) dataHashMap.get(InterfaceKey.MODULE_SUB_PATH); // complete file path
		System.out.println("modulesub path in jsp"+moduleSubPath);
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
		
		System.out.println("fileName " +fileName);
		System.out.println("*************************");
		System.out.println("fileType " +fileType);
		//System.out.println(module_subPath + file_name);
		
		
		//response.sendRedirect(appName+"/servlet/com.mobinil.sds.web.controller.UploadingFileServlet");
		System.out.println(response.getContentType());

		if (fileType.trim().equalsIgnoreCase("txt")) {
			response.setContentType("text/plain");
		} else if (fileType.trim().equalsIgnoreCase("doc")) {
			response.setContentType("application/msword");
		} else if (fileType.trim().equalsIgnoreCase("xls")) {
			response.setContentType("application/vnd.ms-excel");
		} else if (fileType.trim().equalsIgnoreCase("pdf")) {
			response.setContentType("application/pdf");
		} else if (fileType.trim().equalsIgnoreCase("ppt")) {
			response.setContentType("application/ppt");
		} else {
			response.setContentType("application/octet-stream");
		}
		
		System.out.println(response.getContentType());
		
		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
		response.setHeader("cache-control", "no-cache");
		System.out.println("send redirect to: "+appName+ moduleSubPath+fileName);

		response.sendRedirect(appName+ moduleSubPath+fileName);
}catch(Exception e){e.printStackTrace();}		
%>
</body>
    </html>
 
