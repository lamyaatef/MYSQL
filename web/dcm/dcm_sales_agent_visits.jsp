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

         import="com.mobinil.sds.core.system.dcm.user.model.*" 
         import="com.mobinil.sds.core.system.dcm.function.model.*" 
         import="com.mobinil.sds.core.system.dcm.genericModel.*"  
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

    function clearValues()
    {
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM%>.value = "*";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO%>.value = "*";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  GenericModel  statusModel;
  Vector vecSalesAgentsVisitPlans = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);    
  Vector dcmVisitPlanStatusVector = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector functionList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }

  String searchSalesAgentName = "";
  String searchPosCode = "";
  String searchPosName = "";
  String searchFunctionId = "";
  String searchVisitStatusId = "";
  String searchCreationDateFrom = "*";
  String searchCreationDateTo = "*";
  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME))
  searchSalesAgentName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE))
  searchPosCode = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME))
  searchPosName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID))
  searchFunctionId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID))
  searchVisitStatusId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM))
  searchCreationDateFrom = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO))
  searchCreationDateTo = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO);
            
%>   
    <CENTER>
      <H2> Sales Agent Visits </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID+"\""+
                  " value=\""+"\">");                
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Sales Agent Name</td>
        <td align=middle><input value='<%=searchSalesAgentName%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_USER_NAME%>'></td>
        <td align=middle>POS Name</td>
        <td align=middle><input value='<%=searchPosName%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle><input value='<%=searchPosCode%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>'></td>
      </tr>
      <tr class=TableTextNote>
        <td align=middle>Visit Status</td>
        <td align=middle colspan=2>
        <%
          out.println("<select name='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID+"' id='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_VISIT_STATUS_ID+"'>");                 
            out.println("<option value=''></option>");
            for(int j = 0 ; j < dcmVisitPlanStatusVector.size() ; j ++){
              statusModel = (GenericModel)dcmVisitPlanStatusVector.get(j);
              String selectionState = "";
              if(statusModel.get_primary_key_value().compareTo(searchVisitStatusId) == 0) selectionState = "selected";
              out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
              }                          
            out.println("</select>");
          %>
        </td>
        <td align=middle>Visit Function</td>
        <td align=middle colspan=2>
        <select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID%>">
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
            String functionSelected = "";
            if(strFunctionId.compareTo(searchFunctionId)==0)functionSelected = "selected";
            %>
            <option value='<%=strFunctionId%>' <%=functionSelected%>><%=functionName%></option>
            <%
          }
          %>
          </select>
        </td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Visit Date From</td>
        <td align=middle colspan=2><Script>drawCalender('<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM%>',"<%=searchCreationDateFrom%>");</script></td>
        <td align=middle>Visit Date To</td>
        <td align=middle colspan=2><Script>drawCalender('<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO%>',"<%=searchCreationDateTo%>");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_SALES_AGENT_VISITS+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onClick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
       <%
        if(vecSalesAgentsVisitPlans.size()>0)
        {
       %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>Sales Agent</TD>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Function</TD>
          <TD>Visit Date</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Edit</TD>
        </tr>
        <%
        for(int i=0;i<vecSalesAgentsVisitPlans.size();i++)
        {
        VisitPlanModel visitPlanModel = (VisitPlanModel)vecSalesAgentsVisitPlans.get(i);
        String visitPlanId = visitPlanModel.getVisitPlanId();
        Date visitPlanDate = visitPlanModel.getVisitPlanDate();
        String posId = visitPlanModel.getPosId();
        String posCode = visitPlanModel.getPosCode();
        String posName = visitPlanModel.getPosName();
        String userId = visitPlanModel.getUserId();
        String userFullName = visitPlanModel.getUserFullName();
        String userEmail = visitPlanModel.getUserEmail();
        String functionId = visitPlanModel.getFunctionId();
        String functionName = visitPlanModel.getFunctionName();
        String visitComment = visitPlanModel.getVisitComment();
        String creatorUserId = visitPlanModel.getCreatorUserId();
        String creatorUserFullName = visitPlanModel.getCreatorUserFullName();
        String creatorUserEmail = visitPlanModel.getCreatorUserEmail();
        Timestamp creationDate = visitPlanModel.getCreationDate();
        String lastModifiedUserId = visitPlanModel.getLastModifiedUserId();
        String lastModifiedUserFullName = visitPlanModel.getLastModifiedUserFullName();
        String lastModifiedUserEmail = visitPlanModel.getLastModifiedUserEmail();
        Timestamp lastModifiedDate = visitPlanModel.getLastModifiedDate();
        String visitPlanStatusTypeId = visitPlanModel.getVisitPlanStatusTypeId();
        String visitPlanStatusTypeName = visitPlanModel.getVisitPlanStatusTypeName();
        %>
        <TR class=TableTextNote>
          <TD><%=userFullName%></TD>
          <TD><%=posName%></TD>
          <TD><%=posCode%></TD>
          <TD><%=functionName%></TD>
          <TD><%=visitPlanDate%></TD>
          <TD align='center'>
          <%
          out.println("<select name='"+DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID+"_"+visitPlanId+"' id='"+DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID+"_"+visitPlanId+"'>");                 
            out.println("<option value=''></option>");
            for(int j = 0 ; j < dcmVisitPlanStatusVector.size() ; j ++){
              statusModel = (GenericModel)dcmVisitPlanStatusVector.get(j);
              String selectionState = "";
              if(statusModel.get_primary_key_value().compareTo(visitPlanStatusTypeId) == 0) selectionState = "selected";
              out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
              }                          
            out.println("</select>");

         out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_VISIT_STATUS_ID+"_"+visitPlanId+"\""+
                  " value=\""+visitPlanStatusTypeId+"\">");     
          %>
          </TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_EDIT_SALES_AGENT_VISIT+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID+".value = '"+visitPlanId+"';"+
                      "DCMform.submit();\">");

          %>
          </TD>
        </tr>
        <%
        }
        %>
      </table>  
      <%
      }
      %>
    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Visit \" name=\"addnewvisit\" id=\"addnewvisit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SALES_AGENT_VISIT+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_UPDATE_SALES_AGENT_VISITS_STATUS+"';"+
                  "DCMform.submit();\">");
      %>
      </center>
       
</form>
</body>
</html>
