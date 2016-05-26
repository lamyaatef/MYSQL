<%@ 
page contentType="text/html;charset=windows-1252"
	import="org.apache.commons.fileupload.*" 
	import="java.util.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.te.*" 
	import="java.io.*"
	import="com.mobinil.sds.core.system.te.engine.*" 
	import="java.sql.*"
	import="com.mobinil.sds.core.utilities.Utility"
	import="com.mobinil.sds.core.system.te.dao.*"
	import="com.mobinil.sds.core.system.te.dto.*"%>

<%
	String appName = request.getContextPath();
String contractFlag =  request.getParameter("contractFlag");		
String selectType =   request.getParameter("selectType");	
String contrackTypeValue =request.getParameter("batch_type");


String contractTaskName = request.getParameter(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);
String fromDateValue=  request.getParameter(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);
String toDateValue =  request.getParameter(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);

String  searchVec = request.getParameter(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST);
String updateVec = request.getParameter(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST);

System.out.println("contractFlag is "+contractFlag);
System.out.println("contractTaskName is "+contractTaskName);



	DiskFileUpload upload = new DiskFileUpload();
	List items = upload.parseRequest(request);
	String fileUniqueName = "";
	String baseDirectory = request.getRealPath("/te/upload/");
	

	String fileNameOnClient = "";

	Iterator itr = items.iterator();

	while (itr.hasNext()) {
		FileItem item = (FileItem) itr.next();
		// check if the current item is a form field or an uploaded file
		if (item.isFormField()) {
			// get the name of the field
			String fieldName = item.getFieldName();

			// if it is name, we can set it in request to thank the user
			if (fieldName.equals("name"))
				request.setAttribute("msg", "Thank You: "
						+ item.getString());

		} else {
			// the item must be an uploaded file save it to disk. Note that there
			// seems to be a bug in item.getName() as it returns the full path on
			// the client's machine for the uploaded file name, instead of the file
			// name only. To overcome that, I have used a workaround using
			// fullFile.getName().
			//	File fullFile  = new File(item.getName()); 

			try {
				fileNameOnClient = item.getName();
				Utility.logger.debug("fileNameOnClient"
						+ fileNameOnClient);
				fileUniqueName = System.currentTimeMillis() + ".txt";
				System.out.println("fileNameOnClient"
						+ fileNameOnClient);
				File savedFile = new File(baseDirectory
						+ fileUniqueName);
				System.out.println("file in process batch is "+baseDirectory
						+ fileUniqueName);
				Utility.logger.debug("file " + savedFile);
				System.out.println("file " + savedFile);
				item.write(savedFile);
			} catch (Exception e) {
				out.println("Error File can not be imported");
				e.printStackTrace();
				return;
			}
		}
	}
%>
<form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formBatchUpload" id="formBatchUpload" method="post" >

<input type="hidden" name="action" value="add_new_task_contract">

<input type="hidden" name="contractFlag" value="<%out.print(contractFlag); %>">
<input type="hidden" name="selectType" value="<%out.print(selectType); %>">
<input type="hidden" name="batch_type" value="<%out.print(contrackTypeValue); %>">

<input type="hidden" name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_CONTRACT_NAME);%>" value="<%out.print(contractTaskName); %>">
<input type="hidden" name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>" value="<%out.print(fromDateValue); %>">
<input type="hidden" name="<%out.print(TaskInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>" value="<%out.print(toDateValue); %>">
<input type="hidden" name="<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_SEARCH_SELECT_LIST);%>" value="<%out.print(searchVec); %>">
<input type="hidden" name="<%out.print(TaskInterfaceKey.PARAM_UPDATE_CUSTOMIZED_UPDATE_SELECT_LIST);%>" value="<%out.print(updateVec); %>">

<input type="hidden" name="filePath" value="<% out.print(baseDirectory+fileUniqueName); %>">
	<script>
	
	<% out.print("formBatchUpload.submit();");%> 
	
	</script>
</form>

