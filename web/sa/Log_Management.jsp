<%@ page import="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.core.system.security.dto.securityDTO"
         import="com.mobinil.sds.core.system.security.dto.logDTO"         
         import="com.mobinil.sds.core.system.security.dao.securityDAO"
%>
<%
    String appName = request.getContextPath();
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    
%>    
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/calendar.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>User Log  Managment</H2>
    </Center>

    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="LogManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(SecurityInterfaceKey.ACTION_SHOW_LOG);%>">
      <div name="listoflogs" id="listoflogs" >
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=0>
        <TR class=TableHeader>
          <td width="17%" nowrap class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left>From</td>
          <td width="33%" nowrap class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left><input name="<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>" type="text">
            <label>
            <input type="button" onclick="showCalendar(LogManagment.<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_FROM_DATE);%>,'dd/mm/yyyy','Choose date')" name="Button" value="...">
          </label></td>
          <td align=left class="<%out.print(InterfaceKey.STYLE[1%2]);%>" nowrap>To</td>
          <td align=left class="<%out.print(InterfaceKey.STYLE[1%2]);%>" nowrap><input name="<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>" type="text">
          <input type="button" onclick="showCalendar(LogManagment.<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_TO_DATE);%>,'dd/mm/yyyy','Choose date')" name="Submit2" value="..."></td>
        </tr>	
        
       <TR class=TableHeader>
          <td width="17%" class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left nowrap>User Ip</td>
          <td width="33%" class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left nowrap><input name="<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_USER_IP);%>"  type="text"></td>
          <td width="14%" class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left nowrap>URL</td>
          <td width="36%" class="<%out.print(InterfaceKey.STYLE[1%2]);%>" align=left nowrap><input name="<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_ACTION_URL);%>"  type="text"></td>
       </tr>	  
      <TR class=TableHeader>

      <TD align="left" class="<%out.print(InterfaceKey.STYLE[1%2]);%>">User Name </TD>
      
      <TD align="left" class="<%out.print(InterfaceKey.STYLE[1%2]);%>"><input name="<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_USER_ID);%>"  type="text"></TD>
      <TD align=left class="<%out.print(InterfaceKey.STYLE[1%2]);%>">Action</TD>
      <TD align=left class="<%out.print(InterfaceKey.STYLE[1%2]);%>"><input name="<%out.print(logInterfaceKey.CONTROL_INPUT_NAME_ACTION_NAME);%>"  type="text"></TD>
      </TR>
    </TABLE> 
    <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=center>           
            <p>
              <input name="search" type="button" class=button id="search" 
            onclick="document.LogManagment.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SecurityInterfaceKey.ACTION_GET_LOG);%>';LogManagment.submit();" value="Search">
          </td>
        </tr>
    </table>   
    <table style="BORDER-COLLAPSE: collapse" width="100%"  cellSpacing=2 cellPadding=1 border="1">
        <TR class=TableHeader>
        <td align="center">Log Date </td>
        <td align="center">User Name </td>
        <td align="center">User IP </td>
        <td align="center">Action Name </td>
        <td align="center">URL</td>
      </tr>
       <%
        showLogs(request, response, out);
        %>
    </table>
    <p>&nbsp;</p>
      </div>
 </form>
</body>
</html>
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
 public void showLogs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
    Vector logVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    System.out.println("logVector array is "+logVector);
    if (logVector!=null)
    {
    for (int i=0;i<logVector.size();i++){     
    logDTO ldto = (logDTO)logVector.get(i);
    String logDate = ldto.getActionTime();
    String userName = ldto.getUserName();
    String userIp = ldto.getUserIp();
    String actionName = ldto.getActionName();
    String actionUrl = ldto.getActionUrl();
        out.println("<TR class=TableHeader>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=left>");
        out.println(logDate);
        out.println("</TD>");


        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=left>");
        out.println(userName);
        out.println("</TD>");


        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=left>");
        out.println(userIp);
        out.println("</TD>");


        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=left>");
        out.println(actionName);
        out.println("</TD>");


        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=left>");
        out.println(actionUrl);
        out.println("</TD>");


        out.println("</TR>");
        }
        }
    }
  }

%>
