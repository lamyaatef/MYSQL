<%@ page import="javax.servlet.*"
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"
         import="com.mobinil.sds.core.system.security.dto.securityDTO"
         import="com.mobinil.sds.core.system.security.dao.securityDAO"
%>
<%
    String appName = request.getContextPath();
    HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    Vector systemDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2); 
    
    
    
    String lockCount=null ;
    String DayExpire=null ;
    String lastPass=null ;
    String passLen =null;
    String minOption=null ;
    
    
    if (systemDataVector ==null)
    {
    System.out.println("data vector is null");
    }
    else
    {
        try{
         lockCount = ((Integer)systemDataVector.elementAt(0)).toString();
         DayExpire = ((Integer)systemDataVector.elementAt(1)).toString();
         lastPass = ((Integer)systemDataVector.elementAt(2)).toString();
         passLen = ((Integer)systemDataVector.elementAt(3)).toString();
        
         minOption = ((Integer)systemDataVector.elementAt(4)).toString();
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }
%>    
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template2.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/utilities.js" type=text/javascript></SCRIPT>
  </head>
  <body>
    <Center>
      <H2>Security Managment</H2>
    </Center>

    <form action="<%out.print(appName);%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="SecurityManagment" method="post">

      <input type="hidden" name="<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>" 
      value="<%out.print(SecurityInterfaceKey.ACTION_SHOW_CURRENT_RULES);%>">
      <div name="listofrules" id="listofrules" >
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width="60%" nowrap align=center>Security Rule Name</td>
          <td width="20%" nowrap align=center>Security Type</td>
          <td width="20%" nowrap align=center>Security Status</td>
        </tr>	
         <%
        showRules(request, response, out);
        %>
       <TR class=TableHeader>
          <td width="60%" nowrap align=center>Security Rule Name</td>
          <td width="40%" colspan="2" nowrap align=center>System Properties</td>
        </tr>	  
      <TR class=TableHeader>
      <!-- LOCK_TIMES -->
      <TD class="<%out.print(InterfaceKey.STYLE[1%2]);%>">
      Times To Lock User Login
      </TD>
      
      <TD class="<%out.print(InterfaceKey.STYLE[1%2]);%>" colspan="2" align=middle>
        <input maxlength="3" style="width:50px;" value="<%out.print(lockCount);%>" type="text" name="<%out.print(SecurityInterfaceKey.CONTROL_TEXT_NAME_LOCK_TIMES);%>"
         onchange="document.SecurityManagment.update.disabled = false;"/>
       </TD>
      </TR>
      
      <TR class=TableHeader>
      <!-- DAYS_FOR_EXPIRED_LOGIN -->
      <TD class="<%out.print(InterfaceKey.STYLE[2%2]);%>">
      Days To Expire User Login
      </TD>
      
      <TD class="<%out.print(InterfaceKey.STYLE[2%2]);%>" colspan="2" align=middle>
              <input maxlength="3" style="width:50px;" type="text" value="<%out.print(DayExpire);%>" name="<%out.print(SecurityInterfaceKey.CONTROL_TEXT_NAME_DAYS_EXPIRED_LOGIN);%>"  onchange="document.SecurityManagment.update.disabled = false;"/>
      </TD>
      </TR>
      <TR class=TableHeader>
      <!-- LAST_PASSWORD_COUNT -->
      <TD class="<%out.print(InterfaceKey.STYLE[3%2]);%>">
      Last Passwords Count
      </TD>
      
      <TD class="<%out.print(InterfaceKey.STYLE[3%2]);%>" colspan="2" align=middle>
       <input maxlength="3" style="width:50px;" type="text" value="<%out.print(lastPass);%>" name="<%out.print(SecurityInterfaceKey.CONTROL_TEXT_NAME_LAST_PASSWORD_COUNT);%>" onchange="document.SecurityManagment.update.disabled = false;"/>
      </TD>
      </TR>
      
      <TR class=TableHeader>
      <!-- PASSWORD_LENGTH -->
      <TD class="<%out.print(InterfaceKey.STYLE[4%2]);%>">
      User Password Length
      </TD>
      
      <TD class="<%out.print(InterfaceKey.STYLE[4%2]);%>" colspan="2" align=middle>
              <input maxlength="3" style="width:50px;" type="text" value="<%out.print(passLen);%>" name="<%out.print(SecurityInterfaceKey.CONTROL_TEXT_NAME_PASSWORD_LENGTH_PROP);%>" onchange="document.SecurityManagment.update.disabled = false;"/>
      </TD>
      </TR>
      
      <TR class=TableHeader>
      <!-- MINIMUM_OPTIONAL -->
      <TD class="<%out.print(InterfaceKey.STYLE[5%2]);%>">
      Minimum Rules Optional
      </TD>
      
      <TD class="<%out.print(InterfaceKey.STYLE[5%2]);%>" colspan="2" align=middle>
              <input maxlength="3" style="width:50px;" type="text" value="<%out.print(minOption);%>" name="<%out.print(SecurityInterfaceKey.CONTROL_TEXT_NAME_MINIMUM_OPTIONAL);%>" onchange="document.SecurityManagment.update.disabled = false;"/>
      </TD>
      </TR>
    </TABLE> 
    <table  cellspacing="2" cellpadding="1" border="0" width="100%">
        <tr>
          <td align=center>           
            <input class=button type="button" name="update" value="Update" 
            onclick="document.SecurityManagment.<%out.print(InterfaceKey.HASHMAP_KEY_ACTION);%>.value='<%out.print(SecurityInterfaceKey.ACTION_CHECK_RULES);%>';SecurityManagment.submit();" disabled>
          </td>
        </tr>
    </table>   
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
  else{
            String strMessage = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
            if(strMessage!=null)
            {
            out.println("<script type=\"text/javascript\">");
            out.println("document.SecurityManagment."+InterfaceKey.HASHMAP_KEY_ACTION+".value=\""+SecurityInterfaceKey.ACTION_UPDATE_RULES+"\";");
            out.println("document.SecurityManagment.submit();");
            out.println("</script>");
            }

  }
%>
<%!
 public void showRules(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(! dataHashMap.isEmpty()){
    Vector securityDataVector = (Vector)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);    
      for (int i=0;i<securityDataVector.size();i++){
      String strSecurityName=((securityDTO)securityDataVector.get(i)).getSECUIRTY_NAME();
      String strTypeName=((securityDTO)securityDataVector.get(i)).getSECUIRTY_TYPE()+"";
      String strSecurityStatus=((securityDTO)securityDataVector.get(i)).getIntSECURITY_STATUS()+"";

//      System.out.println("strSecurityStatus in "+i+" is "+((securityDTO)securityDataVector.get(i).getSECURITY_STATUS()));
        out.println("<TR class=TableHeader>");
        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=left>");
        out.println(strSecurityName);
        out.println("</TD>");

        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
        out.println("<SELECT name="+SecurityInterfaceKey.CONTROL_SELECT_NAME_TYPE_RULE+(i+1)+
         " onchange=\"document.SecurityManagment.update.disabled = false;\">");
        if  (strTypeName.compareTo("1")==0)
        {
        out.println("<option value=\"1\""+"selected>OPTIONAL</option>");                
        out.println("<option value=\"2\""+">MANDATORY</option>");                
        }
        else
        {
        out.println("<option value=\"1\""+">OPTIONAL</option>");                
        out.println("<option value=\"2\""+"selected>MANDATORY</option>");                
        }
        
        out.println("</SELECT>");
        out.println("</TD>");

        out.println("<TD class="+InterfaceKey.STYLE[i%2]+" align=middle>");
         out.println("<SELECT name="+SecurityInterfaceKey.CONTROL_SELECT_NAME_STATUS_RULE+(i+1)+
         " onchange=\"document.SecurityManagment.update.disabled = false;\">");
        if  (strSecurityStatus.compareTo("1")==0)
        {
        out.println("<option value=\"1\""+"selected>ACTIVE</option>");                
        out.println("<option value=\"2\""+">NOT ACTIVE</option>");                
        }
        else
        {
        out.println("<option value=\"1\""+">ACTIVE</option>");                
        out.println("<option value=\"2\""+"selected>NOT ACTIVE</option>");                
        }        
        out.println("</SELECT>");
        

        out.println("</TD>");
        out.println("</TR>");
        }
    }
  }

%>
