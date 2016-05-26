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
      var visitPlanPOSCode = document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value;
      var visitPlanUserId = document.DCMform.<%=DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID%>.value;
      var visitPlanFunctionId = document.DCMform.<%=DCMInterfaceKey.INPUT_SELECT_FUNCTION_ID%>.value;
      var visitPlanDate = document.DCMform.<%=DCMInterfaceKey.INPUT_TEXT_VISIT_DATE%>.value;
      var visitPlanStatusTypeId = document.DCMform.<%=DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID%>.value;
      if(visitPlanPOSCode == "")
      {
        alert("POS Code can not be empty");
        return;
      }
      if(visitPlanUserId == "")
      {
        alert("Sales Agent can not be empty");
        return;
      }
      if(visitPlanFunctionId == "")
      {
        alert("Function can not be empty");
        return;
      }
      if(visitPlanDate == "*")
      {
        alert("Visit date can not be empty");
        return;
      }
      if(visitPlanStatusTypeId == "")
      {
        alert("Visit status can not be empty");
        return;
      }
      DCMform.submit();
    }

    function removeAllOptions(selectname)
		{
		var elSel = document.getElementById(selectname);
		if (elSel.length == 0) 
			{
		  	return;
		  	}
		elSel.remove(0);
		removeAllOptions(selectname)  	
		}
		      
		function addOption(optvalue,opttext,selectname)
		{
			var elSel = document.getElementById(selectname);
			var elOptNew = new Option();
		
			elOptNew.text =  opttext;
			elOptNew.value =  optvalue;
			
			elSel.options[elSel.length] = elOptNew;		
		}
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String nextAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
  
  GenericModel  statusModel;
  Vector dcmVisitPlanStatusVector = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector functionList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector vecManagerUserList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  HashMap usersAssignedFunctions = new HashMap();
  if(objDataHashMap.containsKey(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION))
  {
    usersAssignedFunctions = (HashMap)objDataHashMap.get(DCMInterfaceKey.DCM_HASHMAP_KEY_ADDITIONAL_COLLECTION);
  }
  String visitPlanId = "";
  Date visitPlanDate = null;
  String strVisitPlanDate = "*";
  String posId = "";
  String posCode = "";
  String posName = "";
  String visitUserId = "";
  String visitUserFullName = "";
  String visitUserEmail = "";
  String visitFunctionId = "";
  String visitFunctionName = "";
  String visitComment = "";
  String creatorUserId = "";
  String creatorUserFullName = "";
  String creatorUserEmail = "";
  Timestamp creationDate = null;
  String lastModifiedUserId = "";
  String lastModifiedUserFullName = "";
  String lastModifiedUserEmail = "";
  Timestamp lastModifiedDate = null;
  String visitPlanStatusTypeId = "";
  String visitPlanStatusTypeName = "";
  if(objDataHashMap.containsKey(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION)) 
  {
    VisitPlanModel visitPlanModel = (VisitPlanModel)objDataHashMap.get(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION);
    visitPlanId = visitPlanModel.getVisitPlanId();
    visitPlanDate = visitPlanModel.getVisitPlanDate();
    int visitYear = visitPlanDate.getYear()+1900;
    int visitMonth = visitPlanDate.getMonth()+1;
    int visitDay = visitPlanDate.getDate();
    
    strVisitPlanDate = visitMonth+"/"+visitDay+"/"+visitYear;
    posId = visitPlanModel.getPosId();
    posCode = visitPlanModel.getPosCode();
    posName = visitPlanModel.getPosName();
    visitUserId = visitPlanModel.getUserId();
    visitUserFullName = visitPlanModel.getUserFullName();
    visitUserEmail = visitPlanModel.getUserEmail();
    visitFunctionId = visitPlanModel.getFunctionId();
    visitFunctionName = visitPlanModel.getFunctionName();
    visitComment = visitPlanModel.getVisitComment();
    if(visitComment == null)visitComment = "";
    creatorUserId = visitPlanModel.getCreatorUserId();
    creatorUserFullName = visitPlanModel.getCreatorUserFullName();
    creatorUserEmail = visitPlanModel.getCreatorUserEmail();
    creationDate = visitPlanModel.getCreationDate();
    lastModifiedUserId = visitPlanModel.getLastModifiedUserId();
    lastModifiedUserFullName = visitPlanModel.getLastModifiedUserFullName();
    lastModifiedUserEmail = visitPlanModel.getLastModifiedUserEmail();
    lastModifiedDate = visitPlanModel.getLastModifiedDate();
    visitPlanStatusTypeId = visitPlanModel.getVisitPlanStatusTypeId();
    visitPlanStatusTypeName = visitPlanModel.getVisitPlanStatusTypeName();
  }
%>   
    <CENTER>
      <H2> Visit Details </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");         

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_VISIT_PLAN_ID+"\""+
                  " value=\""+visitPlanId+"\">");                 
%> 
      
      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <!--TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Plan Name</TD>
          <TD class=TableTextNote colSpan=4><input type='text' name="<%=DCMInterfaceKey.INPUT_TEXT_VISIT_PLAN_NAME%>" id="<%=DCMInterfaceKey.INPUT_TEXT_VISIT_PLAN_NAME%>" value=""></td>
        </tr-->
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">POS Code</TD>
          <TD class=TableTextNote colSpan=4><input type='text' name="<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>" id="<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>" value="<%=posCode%>"></td>
        </tr>
        <%
        if(nextAction.compareTo(DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS)==0)
        {
        %>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Sales Agent</TD>
          <TD class=TableTextNote colSpan=4>
          <script>
            function changeFunctionSelect(userIdValue)
            {
              var functionListName = eval("document.DCMform.<%=DCMInterfaceKey.INPUT_SELECT_FUNCTION_ID%>.name")
              removeAllOptions(functionListName);
              <%
              for(int j=0; j<usersAssignedFunctions.size(); j++)
              {
                String strUserIdKeyValue = (String)usersAssignedFunctions.keySet().toArray()[j];
                %>
                if(<%=strUserIdKeyValue%> == userIdValue)
                {
                  <%
                  Vector functionsAssignedToUser = (Vector)usersAssignedFunctions.get(strUserIdKeyValue);
                  for(int l=0;l<functionList.size();l++)
                  {
                    FunctionModel functionModel = (FunctionModel)functionList.get(l);
                    int functionIDX       = functionModel.get_function_id();
                    String strFunctionIdX = functionIDX+"";
                    String functionNameX  = functionModel.get_function_name();
                    if(functionsAssignedToUser.contains(strFunctionIdX))
                    {
                    %>
                      addOption('<%=strFunctionIdX%>',"<%=functionNameX%>",functionListName);
                    <%
                    }
                  }
                  %>
                }
                <%
              }
              %>
            }
          </script>
          <select name="<%=DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID%>" id="<%=DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID%>" onchange="changeFunctionSelect(this.value);">
          <option value=""></option>
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
            String userSelected = "";
            if(userId.compareTo(visitUserId)==0)userSelected = "selected";
            %>
            <option value="<%=userId%>" <%=userSelected%>><%=userFullName%></option>
            <%
          }
          %>
          </select>
          </td>
        </tr>
        <%
        }
        else if(nextAction.compareTo(DCMInterfaceKey.ACTION_DCM_SAVE_VISIT_PLAN_DETAILS_FOR_AGENT)==0)
        {
        out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

           visitUserId = strUserID;      
        }
        %>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Function</TD>
          <TD class=TableTextNote colSpan=4>
          <select name="<%=DCMInterfaceKey.INPUT_SELECT_FUNCTION_ID%>" id="<%=DCMInterfaceKey.INPUT_SELECT_FUNCTION_ID%>">
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
            if(strFunctionId.compareTo(visitFunctionId)==0)functionSelected = "selected";
            if(usersAssignedFunctions.containsKey(visitUserId))
            {
              Vector functionsAssignedTo = (Vector)usersAssignedFunctions.get(visitUserId);
              if(functionsAssignedTo.contains(functionID+""))
              {
              %>
              <option value='<%=strFunctionId%>' <%=functionSelected%>><%=functionName%></option>
              <%
              }
            }
          }
          %>
          </select>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Comments</TD>
          <TD class=TableTextNote colSpan=4>
          <TEXTAREA name="<%=DCMInterfaceKey.INPUT_TEXTAREA_VISIT_PLAN_COMMENTS%>" id="<%=DCMInterfaceKey.INPUT_TEXTAREA_VISIT_PLAN_COMMENTS%>" cols=50 rows=5><%=visitComment%></TEXTAREA>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Visit Date</TD>
          <TD class=TableTextNote colSpan=4><Script>drawCalender('<%=DCMInterfaceKey.INPUT_TEXT_VISIT_DATE%>',"<%=strVisitPlanDate%>");</script></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Visit Status</TD>
          <TD class=TableTextNote colSpan=4>
          <%
          out.println("<select name='"+DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID+"' id='"+DCMInterfaceKey.INPUT_SELECT_VISIT_STATUS_ID+"'>");                 
            out.println("<option value=''></option>");
            for(int j = 0 ; j < dcmVisitPlanStatusVector.size() ; j ++){
              statusModel = (GenericModel)dcmVisitPlanStatusVector.get(j);
              String selectionState = "";
              if(statusModel.get_primary_key_value().compareTo(visitPlanStatusTypeId) == 0) selectionState = "selected";
              out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
              }                          
            out.println("</select>");
          %>
          </td>
        </tr>  
       </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                  "checkBeforeSubmit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
