<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.cr.*"         
         import="com.mobinil.sds.core.system.cr.notificationList.dto.*"           
 %>

 <%
 	 String appName = request.getContextPath();
     String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <SCRIPT language=JavaScript>
function loadValues(control,value)
  {
    document.formEmailDetails.control.value=value;
  }    
        function checkbeforSubmit()
        {
           
       
        return false;
        }
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Email </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formEmailDetails" id="formEmailDetails" method="post" >

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


  
 String emailName=""; 



// notificatioListDTO taskInfo = (notificatioListDTO) objDataHashMap.get(ContractRegistrationInterfaceKey.DTO_EMAIL) ;
 notificatioListDTO taskInfo = new notificatioListDTO();

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");                  

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Email *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\"");
              //+ContractRegistrationInterfaceKey.CONTROL_TEXT_EMAIL_NAME+"\">");


out.println("</TD></TR>");
out.println("<TR class=TableTextNote><TD colspan=5 class=TableTextNote><font size=1>* indicates mandatory fields.&nbsp;</font></TD></TR></TABLE></TABLE>");

out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_EMAIL_ID+"\""+
                  " value=\""+"\">");


        emailName = taskInfo.getEmail();
        String emailId = taskInfo.getId().intValue()+"";
        

        
        out.println("<script>");
       // out.println("document.formEmailDetails."+ContractRegistrationInterfaceKey.CONTROL_TEXT_EMAIL_NAME+".value='"+emailName+"';");        
        out.println("document.formEmailDetails."+InterfaceKey.HASHMAP_KEY_USER_ID+".value='"+userId+"';");        
        out.println("document.formEmailDetails."+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_EMAIL_ID+".value='"+emailId+"';");        
        out.println("</script>");
        



%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align=middle>
      <P align=center>

 &nbsp;<INPUT class=button onclick="checkbeforSubmit();" type=button value=" Save " name=save></P></td>
</tr>
</table>
</form>

  </body>
</html>




