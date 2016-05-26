<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page import="com.mobinil.sds.core.system.request.model.CalidusZipFileModel"%>
<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
<%@ page import="javax.servlet.*" import="javax.servlet.http.*"
	import="java.io.PrintWriter" import="java.io.IOException"
	import="java.util.*" import="javax.servlet.jsp.*"
	import="com.mobinil.sds.web.interfaces.*"
	import="com.mobinil.sds.web.interfaces.sa.*"
	import="com.mobinil.sds.core.system.sa.roles.model.*"
	import="com.mobinil.sds.core.system.sa.roles.dto.*"
	import="com.mobinil.sds.core.system.sa.modules.dto.*"
	import="com.mobinil.sds.core.system.sa.privileges.model.*"%>

<%
/**
 * Role_Managment.jsp:
 * Display the system Roles and each role privileges.
 * 
 * showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Display the system Roles.
 *
 * showPrivileges(HttpServletRequest request, HttpServletResponse response, JspWriter out): 
 * Build hidden divs, each holding privileges of one role.
 * A role privileges div will be visible if the role was selected.
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

    String appName = request.getContextPath();
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<LINK REL=STYLESHEET TYPE="text/css"
	HREF="<%out.print(appName);%>/resources/css/Template2.css">
</head>
<body>
<Center>
<H2>Calidus Created Files</H2>
</Center>

    
    
    
    <%


/*String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_CALIDUS_FILE_DOWNLOAD_PROCESS;*/
        
          String formAction = appName +"/servlet/com.mobinil.sds.web.controller.FileServlet";


%>
   
<script>
    
    var caDownload = function(filename,formaction) {
     var myForm = document.createElement("form");
     //myForm.action="http://localhost:8080/SDS/servlet/com.mobinil.sds.web.controller.WebControllerServlet?action=action_calidus_file_download_process";
     myForm.action = formaction;
     myForm.method="POST";
     var my_tb=document.createElement("input");
     my_tb.type="text";
     my_tb.name="fileName";
     my_tb.value=filename;
     myForm.appendChild(my_tb);
     myForm.submit();
     return false; // cancel the actual link
   }
</script>
<%
  /**
   * Display any error messages returning from the server.
   */ 
  String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
  if(strError != null)
  {
    out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
  }
%>
 <%!

  public void showRoles(HttpServletRequest request, HttpServletResponse response, JspWriter out, HttpSession session)
  throws ServletException, IOException{
      System.out.println("inside show roles");
      HashMap dataHashMap = new HashMap(100);
      dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
      Vector CalidusZipFiles = (Vector) dataHashMap.get(SCMInterfaceKey.CALIDUS_ZIP_FILES_VECTOR); 
      
      
      String appName = request.getContextPath();
      /*String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                        +SCMInterfaceKey.ACTION_CALIDUS_FILE_DOWNLOAD_PROCESS;*/
      
      String formAction = appName +"/servlet/com.mobinil.sds.web.controller.FileServlet";
      
      
      out.println("<TR>");
      for (int i = 0; i < CalidusZipFiles.size(); i++) {
            CalidusZipFileModel zipFileModel = (CalidusZipFileModel) CalidusZipFiles.get(i);
            Path p = Paths.get(zipFileModel.getFileName());
            String zipName = p.getFileName().toString();
            String fileName = zipFileModel.getFileName();
            String status = zipFileModel.getFileStatus();
            
            
            System.out.println("zip name : "+zipName);
            System.out.println("file name : "+fileName);
            
            
           
            out.println("<TR class=TableTextNote>");
            String param = "http://localhost:8080"+formAction+"?filename="+fileName; //zipname previously
            //out.println("<TD class="+InterfaceKey.STYLE[i%2]+" ><a href=\"#\" name = filename onclick=\"caDownload('"+ zipName +"','"+formAction+"')\" >"+zipName+"</a></TD>");
            out.println("<TD class="+InterfaceKey.STYLE[i%2]+" ><a href=\""+param+"\" name=filename onclick=\"Myform.submit();\">"+zipName+"</a></TD>"); 
            out.println("<TD class="+InterfaceKey.STYLE[i%2]+">"+status+"</TD>");
            
            
          //  out.println("<TD class=TableTextNote align=middle>");
            
           // out.println("<input class=button type=\"button\" name=\" Download \" value=\"   Yes   \" onclick=\"DownloadCalidus.submit();\"> ");
            

            dataHashMap.put(InterfaceKey.DOWNLOAD_FILE_PATH, fileName);
            %>
            <input type="hidden" name=" Download " onclick="javascript:Download(<%=(String)dataHashMap.get(InterfaceKey.DOWNLOAD_FILE_PATH)%>)"> 
            <%!
            //out.println("<input class=button type=\"submit\" name=\" Download \" value=\"   Yes   \" onclick=\""+formAction+'?'+"fileName="+fileName+"\"> ");
            
           // out.println("<input class=button type=\"button\" name=\" Download \" value=\"   Yes   \"  ");
            
        //    out.println("</TD>");
                    
        
      }
            System.out.println("file name in hash map outside loop is : "+(String)dataHashMap.get(InterfaceKey.DOWNLOAD_FILE_PATH));
            out.println("</tr>");
    
  }
  
  
  

%>


 <form action="<%out.print(formAction);%>" name="Myform" method="post">
      <input type="hidden" name="theFileName" value="<%=(String)dataHashMap.get(InterfaceKey.DOWNLOAD_FILE_PATH)%>">
    
	           
	           
	  




<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
	<TR class=TableHeader>		
		<td width=\ "20%\" nowrap align=center>Name</td>
		<td width=\ "50%\" nowrap align=center>Status</td>
		
		
	</tr>
	<%
        showRoles(request, response, out,session);
        %>
</TABLE>


</form>

</body>
</html>

