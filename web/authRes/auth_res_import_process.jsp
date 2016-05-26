
<%@page import="com.mobinil.sds.core.system.dataMigration.model.DetailedPaymentReportModel"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.DAO.DataMigrationDao"%><%@ 
page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                          
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.cr.*"
              import="com.mobinil.sds.web.interfaces.sop.*"
              import="com.mobinil.sds.web.interfaces.dm.*"
              import="com.mobinil.sds.core.system.sa.importdata.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import="com.mobinil.sds.core.system.sop.schemas.dao.*"
              import="com.mobinil.sds.core.system.sop.schemas.model.*"
              import="com.mobinil.sds.core.system.cr.contract.dao.*"
              import="com.mobinil.sds.core.system.cr.contract.model.*"
              import="com.mobinil.sds.core.system.cr.sheet.dao.*"
              import="com.mobinil.sds.core.system.cr.batch.dao.*"
              import="com.mobinil.sds.core.system.sa.users.dao.*"
              import="com.mobinil.sds.core.system.sa.users.model.*"
              
              import="  com.mobinil.sds.core.system.dataMigration.model.*"
              import="  com.mobinil.sds.core.system.dataMigration.DAO.*"
              import="com.mobinil.sds.core.system.sa.users.dto.*"
              import ="java.io.*"      

              import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
              import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
              import ="com.mobinil.sds.core.system.gn.dcm.model.*"
              %>
    <%!public void printToStream(String s, JspWriter out)throws Exception
    {
      out.println(s);    
    }%>
    
    <%@page import="com.mobinil.sds.web.interfaces.ar.AuthResInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.dao.AuthResDAO"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResLabelModel"%>
<%@page import="com.mobinil.sds.core.utilities.uploadAuthResThread"%>


<%
      String appName = request.getContextPath();
      DiskFileUpload upload = new DiskFileUpload();
      List items = upload.parseRequest(request);
      String fileUniqueName ="";
      String baseDirectory =request.getRealPath("/authRes/uplaodtext/");
      //String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
String strUserID = (String)request.getSession().getValue(InterfaceKey.HASHMAP_KEY_USER_ID);
strUserID = strUserID==null ? (String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID):strUserID;
System.out.println("user id isssssssss " + strUserID);
      //  String tableId = (String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_TABLES) ;
     //   String operation =(String)request.getParameter(AdministrationInterfaceKey.QUERY_STRING_OPERATION);
    //  String strUserID =(String)request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);
      String year=(String)request.getParameter(AuthResInterfaceKey.CONTROL_SELECT_YEAR);
      String month=(String)request.getParameter(AuthResInterfaceKey.CONTROL_SELECT_MONTH);
      String label=(String)request.getParameter(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_ID);
     // String labeName=(String)request.getParameter(AuthResInterfaceKey.CONTROL_HIDDEN_LABEL_NAME);
    %>
           <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="import_res" method="post">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="<%=AuthResInterfaceKey.ACTION_AUTH_RES_IMPORT %>">
          <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=strUserID %>">
     <% 
     
      if( AuthResDAO.checkFile(year,month,label))
      {
    	  
    Vector vec=AuthResDAO.getLabelName(label);
    String labelName="";
    
    for(int i=0; i<vec.size();i++){
    	
    	AuthResLabelModel  model = (AuthResLabelModel)   vec.get(i);
        labelName=  model.getLABEL_NAME();
    
    
    }
    	  
  %>

  

    <script>
  	alert('Must delete file of year ' + '<%=year%>'+'  ,month '+ '<%=month%> and Label  '+ '<%=labelName%> ');
  	document.import_res.submit();
    </script>

    <%
      }
      else{
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
          fileUniqueName = System.currentTimeMillis()+".txt";
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
      
      Vector errorVector = new Vector();
      Vector statusVector = new Vector();

      Long	 fileid=null;
      String line = null;
      
      
      uploadAuthResThread upResTH= new  uploadAuthResThread(year,month,"Processing",label,strUserID,baseDirectory+fileUniqueName);
      
      
      upResTH.start();
      
      out.print("<script>");
      out.print("document.import_res.action.value = '"+AuthResInterfaceKey.ACTION_AUTH_RES_IMPORT+"';" );
      out.print("document.import_res.submit();");
       
      out.print(" </script>");
      
      
      }
      %>  
 </form>
   