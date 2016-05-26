<%@ 
page contentType="text/html;charset=windows-1252"
	import="org.apache.commons.fileupload.*" 
	import="java.util.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.sip.*" 
	import="java.io.*"	 
	import="java.sql.*"
	import="com.mobinil.sds.core.utilities.Utility"
	import="com.mobinil.sds.core.system.sip.uploader.*"
	%>

<%
	String appName = request.getContextPath();
String reportFlag =  request.getParameter(SIPInterfaceKey.HIDDEN_PARAM_NAME_SIP_REPORT);
String previousAction =  request.getParameter(SIPInterfaceKey.ACTION_HIDDEN_PARAM_NAME_PREVIOUS_ACTION);
String quartar =  request.getParameter("quartar");
String year =  request.getParameter("year");
String month =  request.getParameter("month");
String userId = (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);


System.out.println("reportFlag is "+reportFlag);
System.out.println("previousAction is "+previousAction);




	DiskFileUpload upload = new DiskFileUpload();
	List items = upload.parseRequest(request);
	String fileUniqueName = "";
	//String baseDirectory = request.getRealPath("/sip/upload/");
	StringBuffer baseDirectory =new StringBuffer(new java.io.File("").getAbsolutePath());
	baseDirectory.append(System.getProperty("file.separator"));	
	baseDirectory.append("SDS");
	baseDirectory.append(System.getProperty("file.separator"));
	baseDirectory.append("upload");
	baseDirectory.append(System.getProperty("file.separator"));
	   
	   
	       	

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
				String extension = fileNameOnClient.substring((fileNameOnClient.indexOf(".")+1),fileNameOnClient.length());
				System.out.println("extension "
						+ extension);
				fileUniqueName = System.currentTimeMillis() + "."+extension;
				System.out.println("fileNameOnClient"
						+ fileNameOnClient);
				File savedFile = new File(baseDirectory.toString()
						+ fileUniqueName);
				System.out.println("file in process batch is "+baseDirectory
						+ fileUniqueName);
				Utility.logger.debug("file " + savedFile);
				System.out.println("file " + savedFile);
				item.write(savedFile);
				
				 try
		         {
		            ExcelUploader.uploadExcel ( Integer.parseInt (reportFlag) , baseDirectory.toString()+ fileUniqueName,savedFile,quartar,year,month, userId );
		         }
		         catch ( NumberFormatException e )
		         {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
		         catch ( SQLException e )
		         {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		         }
				
			} catch (Exception e) {
				out.println("Error File can not be imported");
				e.printStackTrace();
				return;
			}
		}
	}
%>
<form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formProcessUpload" id="formProcessUpload" method="post" >

<input type="hidden" name="action" value="<%=previousAction%>">
<input type="hidden" name="filePath" value="<% out.print(baseDirectory+fileUniqueName); %>">
<input type="hidden" name="reportFlag" value="<% out.print(reportFlag); %>">
	<script>
	
	<% out.print("formProcessUpload.submit();");%> 
	
	</script>
</form>

