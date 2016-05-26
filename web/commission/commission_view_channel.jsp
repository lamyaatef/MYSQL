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
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="../resources/css/Template2.css">
    <SCRIPT language="JavaScript" src="../resources/js/utilities.js" type="text/javascript"></SCRIPT>
    <link href="../resources/css/Template2.css" rel="stylesheet" type="text/css" />
    <SCRIPT language="JavaScript" src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>
    <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
    <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
    <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>


    

  </head>
  <body>
  <script language="javascript">
        $(document).ready(function(){$("#taskListTable").tablesorter(); });

  </script>
  <script>  
     
      function loadChannel(channelId) {
      document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(CommissionInterfaceKey.ACTION_COMMISSION_EDIT_CHANNEL);%>';
      document.formChannel.<%out.print(CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);%>.value=channelId;        
      formChannel.submit();
      }

            
      function newChannel() {
      document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(CommissionInterfaceKey.ACTION_COMMISSION_NEW_CHANNEL);%>';
      formChannel.submit();
      }       
    </script>
    <CENTER>
      <H2> Channel List</H2>
    </CENTER>
  

    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannel" id="formChannel" method="post">&nbsp;

    <%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+CommissionInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+"\""+
             " value=\""+"\">");                                 
     
      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);     
      
    %>

<%
showchannelList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>      
<div align="center"><input type="button" value="Add Channel" onClick="newChannel();" ></div>
 
    </form>
   </body>
</html>

<%!
public void showchannelList(HashMap objDataHashMap , JspWriter out, HttpServletRequest request)
{
    String appName = request.getContextPath();

try{
  if (objDataHashMap == null)
  {
  
   return;
   }
  
  Vector vecChannelList = (Vector) objDataHashMap.get(CommissionInterfaceKey.CHANNEL_VECTOR) ;
  
  if (vecChannelList ==null)
  {
    return;
  }
  int taskListSize = vecChannelList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"taskListTable\">");
    out.println("<thead>");
  out.println("<TR>");
   
out.println("<th ><font size=2>Channel Name</font></a></th><th><font size=2>Edit</font></a></th></thead><tbody>");

  for (int i=0; i<taskListSize; i++)
  {
  
    ChannelModel channelModel=  (ChannelModel) vecChannelList.elementAt (i);    
    int channelId = channelModel.getChannelId().intValue();
    String channelName=channelModel.getChannelName();
    
    
   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"90%\">"+channelName+"</td>");   
    
    
    out.println("<td width=\"10%\" align=center><input type=button value=\"Edit\" onclick=\"loadChannel("+channelId+");\"></td>");
    

    out.println("</tr>");
    

  }
      out.println("</tbody>");
    out.println("</table>");

  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
}
%>