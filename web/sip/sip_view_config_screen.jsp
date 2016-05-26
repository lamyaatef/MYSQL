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
     
      function loadConfig(configId) {
      document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_EDIT_CONFIG);%>';
      document.formChannel.<%out.print(SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID);%>.value=configId;        

       formChannel.submit();
      }

      function deleteConfig(configId) {
          document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_DELETE_CONFIG);%>';
          document.formChannel.<%out.print(SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID);%>.value=configId;        

           formChannel.submit();
          }
      function newChannel() {
      document.formChannel.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SIPInterfaceKey.ACTION_SIP_NEW_CONFIG);%>';
      formChannel.submit();
      }       
    </script>
    <CENTER>
      <H2> Items to be Excluded</H2>
    </CENTER>
  

    <form  action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="formChannel" id="formChannel" method="post">&nbsp;

    <%
      out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+"\">");
     out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_HIDDEN_CONFIG_ID+"\""+
             " value=\""+"\">");                                 
     
      HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);     
      
    %>

<%
showchannelList((HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT) , out, request);
%>      
<div align="center"><input type="button" value="Add Item" onClick="newChannel();" ></div>
 
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
  
  Vector vecChannelList = (Vector) objDataHashMap.get(SIPInterfaceKey.CONFIG_VECTOR) ;
  
  if (vecChannelList ==null)
  {
    return;
  }
  int taskListSize = vecChannelList.size();
  out.println("<TABLE class=\"tablesorter\" id=\"taskListTable\">");
    out.println("<thead>");
  out.println("<TR>");
   
out.println("<th ><font size=2>Item Name</font></a></th><th><font size=2>Edit</font></a></th><th><font size=2>Delete</font></a></th></thead><tbody>");

  for (int i=0; i<taskListSize; i++)
  {
  
    SIPConfigModel configModel=  (SIPConfigModel) vecChannelList.elementAt (i);    
    int configId = configModel.getConfigId().intValue();
    String configName=configModel.getConfigName();
    //String configType=configModel.getConfigType();
    
    
   
    out.println("<TR class=\""+InterfaceKey.STYLE[i%2]+"\">");
    out.println("<td width=\"90%\">"+configName+"</td>");   
    

    out.println("<td width=\"10%\" align=center><input type=button value=\"Edit\" onclick=\"loadConfig("+configId+");\"></td>");
    out.println("<td width=\"10%\" align=center><input type=button value=\"Delete\" onclick=\"deleteConfig("+configId+");\"></td>");


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