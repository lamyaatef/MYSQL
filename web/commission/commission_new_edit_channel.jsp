<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.core.system.commission.model.*"         
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
          if(NonBlank(document.formChannelDetails.<%out.print(CommissionInterfaceKey.CONTROL_TEXT_CHANNEL_NAME);%>, true, 'Channel Name'))
          {  
          document.formChannelDetails.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(CommissionInterfaceKey.ACTION_COMMISSION_SAVE_CHANNELS);%>'
          formChannelDetails.submit();
          } 
        return false;
        }
  
</SCRIPT>
  </head>
  <body>
        <CENTER>
<H2>  New&nbsp;Channel </H2>
</CENTER>
 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannelDetails" id="formChannelDetails" method="post" >

<%

HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);


  
 String channelName="";
 



Vector channelInfo = (Vector) objDataHashMap.get(CommissionInterfaceKey.CHANNEL_MODEL) ;


out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");                  

out.println("<TABLE style=\"WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px\" cellSpacing=2 cellPadding=1 width=\"746\" border=1>");
out.println("<TR class=TableTextNote>");
out.println("<TD class=TableTextNote width=\"20%\">Channel&nbsp;Name *</TD>");

out.println("<TD class=TableTextNote colSpan=4><INPUT style=\"WIDTH: 452px; HEIGHT: 22px\" size=66"
              + " value=\"" +"\""  
              + " name=\""+CommissionInterfaceKey.CONTROL_TEXT_CHANNEL_NAME+"\">");


out.println("</TD></TR>");
out.println("</TABLE>");

out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+"\">");
if (channelInfo!=null)
{
	ChannelModel cm = (ChannelModel)channelInfo.elementAt(0);
        channelName = cm.getChannelName();
        String channelId = cm.getChannelId().intValue()+"";
        

        
        out.println("<script>");
        out.println("document.formChannelDetails."+CommissionInterfaceKey.CONTROL_TEXT_CHANNEL_NAME+".value='"+channelName+"';");        
        out.println("document.formChannelDetails."+InterfaceKey.HASHMAP_KEY_USER_ID+".value='"+userId+"';");        
        out.println("document.formChannelDetails."+CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+".value='"+channelId+"';");        
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




