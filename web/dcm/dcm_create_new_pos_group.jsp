<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.group.model.*"

%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<script>
    
    function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
    
    function checkBeforeSubmit()
    {
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  GroupModel groupModel = (GroupModel)objDataHashMap.get(DCMInterfaceKey.DCM_EDITED_GROUP_MODEL);
  String groupName = "";
  String groupDesc = "";
  String action = DCMInterfaceKey.ACTION_DCM_SAVE_NEW_GROUP;
  int group_id  = 0 ;
  if(groupModel != null){
  groupName = groupModel.getGroupName();
  groupDesc = groupModel.getGroupDescription();
  group_id = groupModel.getGroupID();
  action = DCMInterfaceKey.ACTION_DCM_SAVE_EDITED_GROUP;
  
  }
  
            
%>   
    <CENTER>
      <H2> POS Group Detials </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+action+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+"\""+
                  " value=\""+group_id+"\">");                               
%> 
      
      <TABLE style="WIDTH: 100%; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Group Name</TD>
          <TD class=TableTextNote colSpan=4>
          <INPUT type='text' name="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME%>" id="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME%>" value="<%=groupName%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Description</TD>
          <TD class=TableTextNote colSpan=4><TEXTAREA name="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_DESC%>" id="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_DESC%>" cols=50 rows=5><%=groupDesc%></TEXTAREA></td>
        </tr>
       </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"checkBeforeSubmit();DCMform.submit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
