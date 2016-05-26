

<%@page import="com.mobinil.sds.core.system.authenticationResult.model.SimInfoModel"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.dao.SimInfoDao"%>
<%@page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="java.io.File"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"
              import="com.mobinil.sds.core.system.authenticationResult.engine.SimInfoInsertJob"
              
              %>
    <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
    



<%
      String appName = request.getContextPath();
      DiskFileUpload upload = new DiskFileUpload();
      List items = upload.parseRequest(request);
      String fileUniqueName ="";
      String baseDirectory =request.getRealPath("/authRes/uploadtext/")+System.getProperty("file.separator");      
      String strUserID = (String)request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID);
      strUserID = strUserID==null ? (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID):strUserID;          
      String label=(String)request.getParameter(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);
      String fileExt=(String)request.getParameter(AuthResInterfaceKey.FILE_EXT);
      fileExt = fileExt.contains(".")? fileExt : "."+fileExt;
    %>
           <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="import_res" method="post">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=AuthResInterfaceKey.ACTION_SIM_INFO_IMPORT %>">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=strUserID %>">
    <%      
      String fileNameOnClient="";

      Iterator itr = items.iterator();

      while(itr.hasNext()) {
      	FileItem item = (FileItem) itr.next();          
      	// check if the current item is a form field or an uploaded file
      	if(item.isFormField()) 
        {            
      	// get the name of the field
      	String fieldName = item.getFieldName();  
      	if(fieldName.equals("name"))
      		request.setAttribute("msg", "Thank You: " + item.getString());
      	} 
        else 
        {
        try
        {
          fileNameOnClient = item.getName();
          Utility.logger.debug("fileNameOnClient" + fileNameOnClient ) ;
          fileUniqueName = System.currentTimeMillis()+fileExt;
      		File savedFile = new File(baseDirectory+fileUniqueName);                
          Utility.logger.debug("file " + savedFile);
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


      HashMap dataHashMap = null;
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);           
      SimInfoInsertJob simInsertThread= new  SimInfoInsertJob("Processing",label,strUserID,baseDirectory+fileUniqueName);
    Connection  con = Utility.getConnection();            
            SimInfoDao simDao = new SimInfoDao(con, new SimInfoModel("Processing",strUserID, label));            
            Long fileId =simDao.insertFileInfo();  %>
            <input type="hidden" name="fileId" value="<%=fileId %>">
    <%  simInsertThread.start();      
      out.print("<script>");
      out.print("document.import_res.action.value = '"+AuthResInterfaceKey.ACTION_SIM_INFO_IMPORT+"';" );
      out.print("document.import_res.submit();");       
      out.print(" </script>");
      
      
      
      %>  
   
 </form>
   