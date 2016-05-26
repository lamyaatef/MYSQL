
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="com.mobinil.sds.core.utilities.Utility"%>
<%@page import="com.mobinil.sds.web.interfaces.InterfaceKey"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.FileInputStream"%>


<%

	 String appName = request.getContextPath();
		HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
		session.setAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT, dataHashMap);
	
		String file_name = (String) dataHashMap.get(InterfaceKey.EXPORT_FILE_PATH); // complete file path
		String module_subPath = (String) dataHashMap.get(InterfaceKey.MODULE_SUB_PATH); // complete file path
		String fileType = file_name.substring(file_name.indexOf(".") + 1,file_name.length());
		
		System.out.println(appName+ module_subPath +file_name);
		System.out.println("*************************");
		System.out.println(module_subPath + file_name);
		
		
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
		
		response.setHeader("Content-Disposition", "attachment; filename=\""+ file_name + "\"");
		response.setHeader("cache-control", "no-cache");

		response.sendRedirect(appName+ module_subPath+file_name);
		
				


%>
 
