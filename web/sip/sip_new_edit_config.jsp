<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
         import="com.mobinil.sds.core.system.sip.model.*"         
 %>

 <%
 String appName = request.getContextPath();
     String userId=(String)request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);

 %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/Template1.css">    
        <SCRIPT language="JavaScript" src="<%out.print(appName);%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
    <SCRIPT language="JavaScript">
function loadValues(control,value)
  {
    document.formChannelDetails.control.value=value;
  }    
        function checkbeforSubmit()
        {
          if(NonBlank(document.formChannelDetails.<%out.print(SIPInterfaceKey.CONTROL_TEXT_CONFIG_NAME);%>, true, 'Config Name'))
          {  
          document.formChannelDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_SAVE_CONFIG);%>'
          formChannelDetails.submit();
          } 
        return false;
        }
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Item </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannelDetails" id="formChannelDetails" method="post" >

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


  
 String configName="";



Vector configInfo = (Vector) objDataHashMap.get(SIPInterfaceKey.CONFIG_MODEL) ;


out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");                  

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Item&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+SIPInterfaceKey.CONTROL_TEXT_CONFIG_NAME+"\">");



 

out.println("</TD></tr>");



 
out.println("</TABLE>");

out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID+"\""+
                  " value=\""+"\">");
if (configInfo!=null)
{
	SIPConfigModel cm = (SIPConfigModel)configInfo.elementAt(0);
        configName = cm.getConfigName();
        String configId = cm.getConfigId().intValue()+"";
        

        
        out.println("<script>");
        out.println("document.formChannelDetails."+SIPInterfaceKey.CONTROL_TEXT_CONFIG_NAME+".value='"+configName+"';");        
        out.println("document.formChannelDetails."+InterfaceKey.HASHMAP_KEY_USER_ID+".value='"+userId+"';");        
        out.println("document.formChannelDetails."+SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID+".value='"+configId+"';");  

        out.println("</script>");
}       



%>

<table  cellspacing="2" cellpadding="1" border="0" width="747" style="WIDTH: 747px; HEIGHT: 26px">
<tr>
<td align="middle">
      <P align="center">

 &nbsp;<INPUT class="button" onclick="checkbeforSubmit();" type="button" value=" Save " name="save"></P></td>
</tr>
</table>
</form>

  </body>
</html>




