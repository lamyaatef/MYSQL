<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         
         import="com.mobinil.sds.core.system.dcm.user.model.*" 
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<script>
    function checkBeforeSubmit()
    {
      
    }

    function clearValues()
    {
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE%>.value = "";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  GenericModel  statusModel;
  GenericModel  levelTypeModel;
  Vector dcmUserStatusVector = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector dcmUserLevelTypeVector = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))       
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }

  Vector vecManagerUserList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  String searchDcmUserName = "";
  String searchDcmUserStatusTypeId = "";
  String searchDcmUserLevelTypeId = "";
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME))
  searchDcmUserName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS))
  searchDcmUserStatusTypeId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE))
  searchDcmUserLevelTypeId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE);
            
%>   
    <CENTER>
      <H2> Hierarchy Management </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+"\""+
                  " value=\""+"\">");  

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID+"\""+
                  " value=\""+"\">");  
                  
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>DCM User Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>' value='<%=searchDcmUserName%>'></td>
        <td align=middle>DCM User Status</td>
        <%
        out.println("<TD align='middle'><select name='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS+"' id='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS+"'>");                 
          out.println("<option value=''></option>");
          for(int j = 0 ; j < dcmUserStatusVector.size() ; j ++){
            statusModel = (GenericModel)dcmUserStatusVector.get(j);
            String selectionState = "";
            if(statusModel.get_primary_key_value().compareTo(searchDcmUserStatusTypeId) == 0) selectionState = "selected";
            out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
            }                          
          out.println("</select></TD>");
        %>
        <td align=middle>DCM User Type</td>
        <%
        out.println("<TD align='middle'><select name='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE+"' id='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_TYPE+"'>");                 
          out.println("<option value=''></option>");
          for(int j = 0 ; j < dcmUserLevelTypeVector.size() ; j ++){
            levelTypeModel = (GenericModel)dcmUserLevelTypeVector.get(j);

              if(levelTypeModel.get_primary_key_value().compareTo("0")!=0)
              {
                String selectionState = "";
                if(levelTypeModel.get_primary_key_value().compareTo(searchDcmUserLevelTypeId) == 0) selectionState = "selected";
                out.println("<option value='"+levelTypeModel.get_primary_key_value()+"' "+selectionState+">" + levelTypeModel.get_field_2_value() + "</option>");
              }
            }                          
          out.println("</select></TD>");
        %>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_SEARCH_DCM_MANAGERS+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>DCM User Name</TD>
          <TD>E-mail</TD>
          <TD>User Type</TD>
          <TD>Region</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Assign DCM Users</TD>
          <TD align='center'>Changing Requests</TD>
          <!--TD align='center'>Upload Sales Agents</TD-->
        </tr>
        <%
        for(int i=0;i<vecManagerUserList.size();i++)
        {
        DCMUserModel dcmUserModel = (DCMUserModel)vecManagerUserList.get(i);
        String dcmUserId = dcmUserModel.getDcmUserId();
        String userId = dcmUserModel.getUserId();
        String userFullName = dcmUserModel.getUserFullName();
        String userEmail = dcmUserModel.getUserEmail();
        String managerDcmUserId = dcmUserModel.getManagerDcmUserId();
        String userLevelTypeId = dcmUserModel.getUserLevelTypeId();
        String userLevelTypeName = dcmUserModel.getUserLevelTypeName();
        String userDetailId = dcmUserModel.getUserDetailId();
        String userStatusTypeId = dcmUserModel.getUserStatusTypeId();
        String userStatusTypeName = dcmUserModel.getUserStatusTypeName();
        String regionId = dcmUserModel.getRegionId();
        String regionName = dcmUserModel.getRegionName();
        %>
        <TR class=TableTextNote>
          <TD><%=userFullName%></TD>
          <TD><%=userEmail%></TD>
          <TD><%=userLevelTypeName%></TD>
          <TD><%=regionName%></TD>
          <%
        out.println("<TD align='center'><select name='"+DCMInterfaceKey.INPUT_SELECT_DCM_USER_LIST_STATUS+"_"+dcmUserId+"' id='"+DCMInterfaceKey.INPUT_SELECT_DCM_USER_LIST_STATUS+"_"+dcmUserId+"'>");                 
          out.println("<option value=''></option>");
          for(int j = 0 ; j < dcmUserStatusVector.size() ; j ++){
            statusModel = (GenericModel)dcmUserStatusVector.get(j);
            String dcmUserStatusTypePrimaryKeyId = statusModel.get_primary_key_value();
            String selectionState = "";
            if(userStatusTypeId.compareTo(dcmUserStatusTypePrimaryKeyId+"") == 0) selectionState = "selected";
            out.println("<option value='"+dcmUserStatusTypePrimaryKeyId+"' "+selectionState+" >" + statusModel.get_field_2_value() + "</option>");
            }

          out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LIST_STATUS+"_"+dcmUserId+"\""+
                  " value=\""+userStatusTypeId+"\">");  
            
          out.println("</select></TD>");
        %>
          <TD align='center'>
          <%
            if(userLevelTypeId.compareTo(DCMInterfaceKey.CONST_DCM_USER_LEVEL_TYPE_REGIONAL_MANAGER_ID)==0)
            {
            out.print("<INPUT class=button type=button value=\" Assign DCM Users \" name=\"assignmanagers\" id=\"assignmanagers\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ASSIGN_SALES_AGENT_TO_MANAGER+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+".value = '"+userId+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID+".value = '"+userLevelTypeId+"';"+
                      "DCMform.submit();\">");
            }
            else if(userLevelTypeId.compareTo(DCMInterfaceKey.CONST_DCM_USER_LEVEL_TYPE_MANAGER_ID)==0)
            {
            out.print("<INPUT class=button type=button value=\" Assign DCM Users \" name=\"assignsalesagents\" id=\"assignsalesagents\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ASSIGN_SALES_AGENT_TO_MANAGER+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+".value = '"+userId+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID+".value = '"+userLevelTypeId+"';"+
                      "DCMform.submit();\">");
            }
            else if(userLevelTypeId.compareTo(DCMInterfaceKey.CONST_DCM_USER_LEVEL_TYPE_SALES_AGENT_ID)==0)
            {
            }
          %>
          </TD>
          <TD align='center'>
          <%
          if(userLevelTypeId.compareTo(DCMInterfaceKey.CONST_DCM_USER_LEVEL_TYPE_SALES_AGENT_ID)==0)
          {
              
                  out.print("<INPUT class=button type=button value=\" Changing Requests \" name=\"changingrequests\" id=\"changingrequests\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CHANGED_SALES_AGENTS_DATA_MANAGEMENT+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+".value = '"+userId+"';"+
                  "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_LEVEL_TYPE_ID+".value = '"+userLevelTypeId+"';"+
                  "DCMform.submit();\">");
          }
          %>        
          </TD>
          <!--TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\"Upload Sales Agents\" name=\"uploadsalesagents\" id=\"uploadsalesagents\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_UPLOAD_ASSIGN_SALES_AGENT_TO_MANAGER+"';"+
                      "DCMform.submit();\">");

          %>
          </TD-->
        </tr>
        <%
        }
        %>
      </table>  

    <br><br>
      <center>
      <%
        //out.print("<INPUT class=button type=button value=\" Add New Manager \" name=\"addnewmanager\" id=\"addnewmanager\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_MANAGER+"';"+
        //          "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_UPDATE_DCM_USER_STATUS_TYPE+"';"+
                  "DCMform.submit();\">");
      %>
      </center>
       
</form>
</body>
</html>
