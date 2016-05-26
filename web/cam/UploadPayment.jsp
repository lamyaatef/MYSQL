<%@ page import="java.io.*" %>
<%@ page import ="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
 import ="com.mobinil.sds.web.interfaces.InterfaceKey"        
  import ="com.mobinil.sds.web.interfaces.cam.*"
  import ="com.mobinil.sds.core.utilities.*"
  import="org.apache.commons.fileupload.*"
         %>
<%
//to get the content type information from JSP Request Header
        String contentType = request.getContentType();
//here we are checking the content type is not equal to Null andas well as the passed data from mulitpart/form-data is greater than orequal to 0
        String attach_seq = request.getParameter("attach_id");
       // System.out.println("MASHMOUD = " + request.getParameter("MAHMOUD"));
         String file_path="";
     
         if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
            DataInputStream in = new DataInputStream(request.getInputStream());
            //we are taking the length of Content type data
         
			DiskFileUpload upload = new DiskFileUpload();
			java.util.List items = upload.parseRequest(request);
			
			String baseDirectory =request.getRealPath("/cam/uploadedPayment/");
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
			    fileUniqueName = "paymentFile"+attach_seq+".xls";
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
           file_path =  Utility.replaceAll(request.getRealPath("/cam/uploadedPayment/"+fileUniqueName),"\\", "#");
           //System.out.println("file path from jsp = "+file_path);
           //System.out.println("written file path from jsp = "+baseDirectory+"/"+fileUniqueName);
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
        <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=PaymentInterfaceKey.ACTION_IMPORT_SCM_PAYMENT_STATUS%>">
        <input type="hidden" name="<%=InterfaceKey.CONTROL_INPUT_FILE%>" id="<%=InterfaceKey.CONTROL_INPUT_FILE%>" value="<%=file_path%>">
</form>
<script>
    document.Import.submit();
</script>
    </body>
</html>

