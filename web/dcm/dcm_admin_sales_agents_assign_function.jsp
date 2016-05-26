<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility" 
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         
         import="com.mobinil.sds.core.system.dcm.user.model.*" 
         import="com.mobinil.sds.core.system.dcm.function.model.*"
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
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID%>.value = "";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  GenericModel  statusModel;
  Vector dcmUserStatusVector = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector functionList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector vecManagerUserList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  String strSearchDcmUserName = "";
  String strSearchDcmUserStatusTypeId = "";
  String strSearchDcmUserFunctionId = "";
  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME))
  strSearchDcmUserName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS))
  strSearchDcmUserStatusTypeId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID))
  strSearchDcmUserFunctionId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID);
            
%>   
    <CENTER>
      <H2> Admin Sales Agents Assign Function</H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+"\""+
                  " value=\""+"\">");                  
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Sales Agent Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>' value='<%=strSearchDcmUserName%>'></td>
        <td align=middle>Sales Agent Status</td>
        <td align=middle>
        <%
        out.println("<select name='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS+"' id='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_DCM_USER_LIST_STATUS+"'>");                 
          out.println("<option value=''></option>");
          for(int j = 0 ; j < dcmUserStatusVector.size() ; j ++){
            statusModel = (GenericModel)dcmUserStatusVector.get(j);
            String selectionState = "";
            if(statusModel.get_primary_key_value().compareTo(strSearchDcmUserStatusTypeId) == 0) selectionState = "selected";
            out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
            }                          
          out.println("</select>");
        %>
        </td>
        <td align=middle>Function</td>
        <td align=middle>
        <select name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_FUNCTION_ID%>'>
        <option value=''></option>
        <%
        for(int k=0;k<functionList.size();k++)
        {
          FunctionModel functionModel = (FunctionModel)functionList.get(k); 
          int functionID       = functionModel.get_function_id();
          String strFunctionId = functionID+"";
          String functionName  = functionModel.get_function_name();
          String functionDesc  = functionModel.get_function_desc() ;
          int functionStatusID = functionModel.get_function_status_id();
          String searchFunctionSelected = "";
          if(strFunctionId.compareTo(strSearchDcmUserFunctionId)==0)searchFunctionSelected = "selected";
          %>
          <option value='<%=strFunctionId%>' <%=searchFunctionSelected%>><%=functionName%></option>
          <%
        }
        %>
        </select>
        </td>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_ADMIN_SALES_AGENTS_ASSIGN_FUNCTION+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>Sales Agent Name</TD>
          <TD>E-mail</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Functions</TD>
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
          <TD align='center'><%=userStatusTypeName%></TD>
          <TD align='center'>
          <%
            if(userStatusTypeId.compareTo("1")==0)
            {
            out.print("<INPUT class=button type=button value=\" Functions \" name=\"functions\" id=\"functions\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ASSIGN_FUNCTION_TO_SALES_AGENT+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+".value = '"+userId+"';"+
                      "DCMform.submit();\">");
            }
          %>
          </TD>
        </tr>
        <%
        }
        %>
      </table> 
       
</form>
</body>
</html>
