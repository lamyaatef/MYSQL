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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
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
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM%>.value = "*";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO%>.value = "*";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector functionList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  Vector actualVisits = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }

  String searchPosCode = "";
  String searchPosName = "";
  String searchFunctionId = "";
  String searchCreationDateFrom = "*";
  String searchCreationDateTo = "*";
  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE))
  searchPosCode = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME))
  searchPosName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID))
  searchFunctionId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM))
  searchCreationDateFrom = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_FROM);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO))
  searchCreationDateTo = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_VISIT_CREATION_DATE_TO);
                
%>   
    <CENTER>
      <H2> Sales Agent Actual Visits </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");      

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID+"\""+
                  " value=\""+"\">");                
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle><input value='<%=searchPosName%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle><input value='<%=searchPosCode%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>'></td>
        <td align=middle>Visit Function</td>
        <td align=middle><select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_FUNCTION_ID%>">
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
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_SALES_AGENT_ACTUAL_VISITS_FOR_AGENTS+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onClick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
       <%
        if(actualVisits.size()>0)
        {
       %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Visit Date</TD>
          <TD align='center'>Visit Details</TD>
        </tr>
        <%
        for(int i=0;i<actualVisits.size();i++)
        {
        ActualVisitModel actualVisitModel = (ActualVisitModel)actualVisits.get(i);
        String actualVisitId = actualVisitModel.getActualVisitId();
        Date actualVisitDate = actualVisitModel.getActualVisitDate();
        String userId = actualVisitModel.getUserId();
        String userFullName = actualVisitModel.getUserFullName();
        String userEmail = actualVisitModel.getUserEmail();
        String posCode = actualVisitModel.getPosCode();
        String posId = actualVisitModel.getPosId();
        String posName = actualVisitModel.getPosName();
        %>
        <TR class="<%=InterfaceKey.STYLE[i%2]%>">
          <TD><%=posName%></TD>
          <TD><%=posCode%></TD>
          <TD><%=actualVisitDate%></TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Visit Details \" name=\"visitdetails\" id=\"visitdetails\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_VIEW_SALES_AGENT_ACTUAL_VISIT_DETAILS+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID+".value = '"+actualVisitId+"';"+
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
        out.print("<INPUT class=button type=button value=\" Add New Visit \" name=\"addnewvisit\" id=\"addnewvisit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SALES_AGENT_ACTUAL_VISIT+"';"+
                  "DCMform.submit();\">");
      %>
      </center>
       
</form>
</body>
</html>
