<%@ page import="java.io.*" %>
<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
 import ="com.mobinil.sds.web.interfaces.InterfaceKey"        
  import ="com.mobinil.sds.web.interfaces.dm.*"
  import ="com.mobinil.sds.core.utilities.*"
  import="org.apache.commons.fileupload.*"
    import="  com.mobinil.sds.core.system.dataMigration.model.*"
   import="  com.mobinil.sds.core.system.dataMigration.DAO.*"
         %>
<%
//to get the content type information from JSP Request Header

 HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
        String contentType = request.getContentType();
//here we are checking the content type is not equal to Null andas well as the passed data from mulitpart/form-data is greater than orequal to 0
        String attach_seq = request.getParameter("attach_id");
		String year = request.getParameter("year_hidden");
		String month = request.getParameter("month_hidden");
		 String userID = request.getParameter("user_id");
       // System.out.println("MASHMOUD = " + request.getParameter("MAHMOUD"));
         String file_path="";
         
         if( DataMigrationDao.checkFile(year,month))
         {
       	  
     %>

       <script>
     	alert('Must delete file of year ' + '<%=year%>'+' and month '+ '<%=month%>');
     	document.Import.submit();
       </script>

       <%
         }
         else
         {
     
         if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
            DataInputStream in = new DataInputStream(request.getInputStream());
            //we are taking the length of Content type data
         
			DiskFileUpload upload = new DiskFileUpload();
			java.util.List items = upload.parseRequest(request);
			
			String baseDirectory =request.getRealPath("/dm/uploadedZip/");
			System.out.println("base directory = "+ baseDirectory);
			Iterator itr = items.iterator();
			String fileUniqueName =null;
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();          
				// check if the current item is a form field or an uploaded file
				if(item.isFormField()) 
			  {            
				// get the name of the field
				String fieldName = item.getFieldName();  
				
				// if it is name, we can set it in request to thank the user
				System.out.println("field name= " + fieldName);
					
				} 
			  else 
			  {
					// the item must be an uploaded file save it to disk. Note that there
					// seems to be a bug in item.getName() as it returns the full path on
					// the client's machine for the uploaded file name, instead of the file
					// name only. To overcome that, I have used a workaround using
					// fullFile.getName().
				//	File fullFile  = new File(item.getName()); 

			  try
			  {
			    String fileNameOnClient = item.getName();
			    System.out.println("fileNameOnClient" + fileNameOnClient ) ;
			    fileUniqueName = "ZipFile_"+attach_seq+".zip";
			    File savedFile = new File(baseDirectory+"/"+fileUniqueName);	
			    
			    
			   
			   item.write(savedFile);
			    }
			    catch (Exception e)
			    {
			    out.println("Error File can not be imported");
			    e.printStackTrace();
			    return ;
			    }
				}
			}			                                                       
           file_path =  Utility.replaceAll(request.getRealPath("/dm/uploadedZip/"+fileUniqueName),"\\", "#");
        }
         }
%>
<html>
    <head> 
        <%
        String appName = request.getContextPath();
        %>
        <LINK REL="STYLESHEET" TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    </head>
    <body>

<form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="Import" method="post">
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=DMInterfaceKey.ACTION_UPLOAD_ZIP_FILE%>">
        <input type="hidden" name="<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_YEAR%>" value="<%=year%>">
        <input type="hidden" name="<%=DMInterfaceKey.CONTROL_SELECT_UPLOAD_ZIP_MONTH%>" value="<%=month%>">
        <input type="hidden" name="<%=InterfaceKey.CONTROL_INPUT_FILE%>" id="<%=InterfaceKey.CONTROL_INPUT_FILE%>" value="<%=file_path%>">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" id="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userID%>">
        
</form>
<script> 

    document.Import.submit();
</script>
    </body>
</html>

