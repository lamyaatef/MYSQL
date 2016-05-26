<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.Timestamp"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.gn.ua.*"
         
         import="com.mobinil.sds.core.system.dcm.user.model.*" 
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
    function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(DCMform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    
    function checkBeforeSubmit()
    {
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  
%>   
    <CENTER>
      <H2> Sales Agent Details</H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_ID+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_STATUS_ID+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_NAME+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_EMAIL+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ADDRESS+"\""+
                  " value=\""+"\">");
                  
  String serverName = request.getServerName();
  int serverPort = request.getServerPort();
  String appName = request.getContextPath();

  out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_NAME+"\" "+
                 "value=\""+serverName+"\">");
  out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_SERVER_PORT+"\" "+
                 "value=\""+serverPort+"\">");
  out.println("<input type=\"hidden\" name=\""+UserAccountInterfaceKey.CONTROL_HIDDEN_CONTEXT_PATH+"\" "+
                 "value=\""+appName+"\">");

                     
  DCMUserDetailModel dcmUserDetailModel = (DCMUserDetailModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String userDetailId = dcmUserDetailModel.getUserDetailId();
  String userId = dcmUserDetailModel.getUserId();
  String userFullName = dcmUserDetailModel.getUserFullName();
  String userAddress = dcmUserDetailModel.getUserAddress();
  if(userAddress == null)userAddress = "";
  String userEmail = dcmUserDetailModel.getUserEmail();
  String userMobile = dcmUserDetailModel.getUserMobile();
  if(userMobile == null)userMobile = "";
  String regionId = dcmUserDetailModel.getRegionId();
  String regionName = dcmUserDetailModel.getRegionName(); 

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+"\""+
                  " value=\""+userId+"\">");

  Vector dcmUserDetailPending = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

    
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
    out.println("    <TR class=TableHeader>");
    out.println("      <TD align='center'>Manager Name</TD>");
    out.println("      <TD align='center'>E-mail</TD>");
    out.println("      <TD align='center'>Mobile Phone</TD>");
    out.println("      <TD align='center'>Address</TD>");
    out.println("      <TD align='center'>Region</TD>");
    out.println("    </tr>");
    out.println("    <TR>");
    out.println("      <TD align='center'>"+userFullName+"</TD>");
    out.println("      <TD align='center'>"+userEmail+"</TD>");
    out.println("      <TD align='center'>"+userMobile+"</TD>");
    out.println("      <TD align='center'>"+userAddress+"</TD>");
    out.println("      <TD align='center'>"+regionName+"</TD>");
    out.println("    </tr>");
    out.println("  </table>  ");
%> 
     
       <br><br>

      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<%
      if(dcmUserDetailPending.size()>0)
      {
      
%>
        <TR class=TableHeader>
          <TD align='center'>Name</TD>
          <TD align='center'>E-mail</TD>
          <TD align='center'>Mobile Phone</TD>
          <TD align='center'>Address</TD>
          <TD align='center'>Region</TD>
          <TD align='center'>Made By</TD>
          <TD align='center'>Changes Timestamp</TD>
          <TD align='center' colspan='2'>Action</TD>
        </tr>
<%
      }
      for(int i=0;i<dcmUserDetailPending.size();i++)
      {
      DCMUserDetailModel dcmUserDetailModelPending = (DCMUserDetailModel)dcmUserDetailPending.get(i);
      String puserDetailId = dcmUserDetailModelPending.getUserDetailId();
      String puserId = dcmUserDetailModelPending.getUserId();
      String puserFullName = dcmUserDetailModelPending.getUserFullName();
      String puserAddress = dcmUserDetailModelPending.getUserAddress();
      if(puserAddress == null)puserAddress = "";
      String puserEmail = dcmUserDetailModelPending.getUserEmail();
      String puserMobile = dcmUserDetailModelPending.getUserMobile();
      if(puserMobile == null)puserMobile = "";
      String pregionId = dcmUserDetailModelPending.getRegionId();
      String pregionName = dcmUserDetailModelPending.getRegionName();

      String puserDetailStatusId = dcmUserDetailModelPending.getUserDetailStatusId();
      String puserDetailStatusName = dcmUserDetailModelPending.getUserDetailStatusName();
      Timestamp pcreationTimestamp = dcmUserDetailModelPending.getCreationTimestamp();
      String pcreationUserId = dcmUserDetailModelPending.getCreationUserId();
      String pcreationUserFullName = dcmUserDetailModelPending.getCreationUserFullName();
      String pcreationUserEmail = dcmUserDetailModelPending.getCreationUserEmail();
%>
        <TR class=TableTextNote>
          <TD align='center'><%=puserFullName%></TD>
          <TD align='center'><%=puserEmail%></TD>
          <TD align='center'><%=puserMobile%></TD>
          <TD align='center'><%=puserAddress%></TD>
          <TD align='center'><%=pregionName%></TD>
          <TD align='center'><%=pcreationUserFullName%></TD>
          <TD align='center'><%=pcreationTimestamp%></TD>
          <TD align='center'>
          <%
           out.print("<INPUT class=button type=button value=\" Accept \" name=\"accept\" id=\"accept\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CHANGE_USER_DETAIL_STATUS+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_ID+".value = '"+puserDetailId+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_STATUS_ID+".value = '"+DCMInterfaceKey.CONST_DCM_USER_DETIAL_STATUS_ACTIVE_ID+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_NAME+".value = '"+puserFullName+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_EMAIL+".value = '"+puserEmail+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ADDRESS+".value = '"+puserAddress+"';"+
                  "DCMform.submit();\">"); 
          %>
          </TD>
          <TD align='center'>
          <%
           out.print("<INPUT class=button type=button value=\" Reject \" name=\"reject\" id=\"reject\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CHANGE_USER_DETAIL_STATUS+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_ID+".value = '"+puserDetailId+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_DETAIL_STATUS_ID+".value = '"+DCMInterfaceKey.CONST_DCM_USER_DETIAL_STATUS_REJECTED_ID+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_NAME+".value = '"+puserFullName+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_EMAIL+".value = '"+puserEmail+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ADDRESS+".value = '"+puserAddress+"';"+
                  "DCMform.submit();\">"); 
          %>
          </TD>
        </tr>
<%
      }
%>
      </table>  

      <br><br>
      <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
</form>
</body>
</html>
