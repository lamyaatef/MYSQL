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
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.core.system.dcm.function.model.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.user.model.*"
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
      var actualVisitDate = document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_DATE%>.value;
      var posCode = document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>.value;
      var functionId = document.DCMform.<%=DCMInterfaceKey.CONTROL_SELECT_DCM_ACTUAL_VISIT_FUNCTION_ID%>.value;

      if(posCode == "")
      {
        alert('POS code can not be empty.');
        return;  
      }
      if(actualVisitDate == "*")
      {
        alert('Visit Date can not be empty.');
        return;
      }
      if(functionId == "")
      {
        alert('Visit objective can not be empty.');
        return;  
      }
      DCMform.submit();
    }

    function checkBeforeOpenNewCase()
    {
      var posCode = document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>.value;
      var actualVisitId = document.DCMform.<%=DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID%>.value;

      if(actualVisitId == "")
      {
        alert("Actula visit must be saved before open case.");
        return;
      }
      if(posCode == "")
      {
        alert("POS Code can not be empty.");
        return;
      }
      DCMform.submit();
    }

    function checkBeforeSalesForm()
    {
      var posCode = document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>.value;
      var actualVisitId = document.DCMform.<%=DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID%>.value;

      if(actualVisitId == "")
      {
        alert("Actula visit must be saved before open case.");
        return;
      }
      DCMform.submit();
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector actualVisitFunctionList = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_HASHMAP_KEY_COLLECTION); 

  ActualVisitModel actualVisitModel = null;

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    actualVisitModel = (ActualVisitModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  }

  String actualVisitId = "";
  Date actualVisitDate = null;
  String strActualVisitDate = "*";
  String userId = "";
  String userFullName = "";
  String userEmail = "";
  String posCode = "";
  String posId = "";
  String posName = "";
  String actualVisitComment = "";
  String actualVisitFunctionId = "";
  if(actualVisitModel != null)
  {
    actualVisitId = actualVisitModel.getActualVisitId();
    actualVisitDate = actualVisitModel.getActualVisitDate();
    strActualVisitDate = (actualVisitDate.getMonth()+1)+"/"+actualVisitDate.getDate()+"/"+(actualVisitDate.getYear()+1900);
    userId = actualVisitModel.getUserId();
    userFullName = actualVisitModel.getUserFullName();
    userEmail = actualVisitModel.getUserEmail();
    posCode = actualVisitModel.getPosCode();
    posId = actualVisitModel.getPosId();
    Utility.logger.debug(posId);
    posName = actualVisitModel.getPosName();
    actualVisitComment = actualVisitModel.getActualVisitComment();
    if(actualVisitComment == null)actualVisitComment = "";
    actualVisitFunctionId = actualVisitModel.getFunctionId();
  }

  Vector actualVisitDetailList = new Vector();
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2))
  {
    actualVisitDetailList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  }
%>   
    <CENTER>
      <H2> Actual Visit Details </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID+"\""+
                  " value=\""+actualVisitId+"\">");                     

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+"\""+
                  " value=\""+posId+"\">");                     

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+"\""+
                  " value=\""+"\">"); 
                  
%> 
      
      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">POS Code</TD>
          <TD class=TableTextNote colSpan=4>
            <%
            if(actualVisitId.compareTo("")==0)
            {
            %>
            <input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>' value='<%=posCode%>'>
            <%
            }
            else
            {
            %>
            <%=posCode%>  
            <input type='hidden' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_POS_CODE%>' value='<%=posCode%>'>
            <%
            }
            %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Visit Date</TD>
          <TD class=TableTextNote colSpan=4><Script>drawCalender('<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_DATE%>',"<%=strActualVisitDate%>");</script></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Visit Objective</TD>
          <TD class=TableTextNote colSpan=4>
            <select name='<%=DCMInterfaceKey.CONTROL_SELECT_DCM_ACTUAL_VISIT_FUNCTION_ID%>' id='<%=DCMInterfaceKey.CONTROL_SELECT_DCM_ACTUAL_VISIT_FUNCTION_ID%>'>
            <option value=''></option>
            <%
            for(int f=0;f<actualVisitFunctionList.size();f++)
            {
              FunctionModel functionModel = (FunctionModel)actualVisitFunctionList.get(f);
              int functionID       = functionModel.get_function_id() ;
              String strFunctionId = functionID+"";
              String functionName  = functionModel.get_function_name() ;
              String functionDesc  = functionModel.get_function_desc() ;
              String stateSelected = "";
              if(strFunctionId.compareTo(actualVisitFunctionId)==0)stateSelected = "selected";
              %>
              <option value='<%=functionID+""%>' <%=stateSelected%>><%=functionName%></option>
              <%
            }
            %>
            </select>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Comments</TD>
          <TD class=TableTextNote colSpan=4><TEXTAREA name="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_COMMENTS%>" id="<%=DCMInterfaceKey.CONTROL_TEXT_DCM_ACTUAL_VISIT_COMMENTS%>" cols=50 rows=5><%=actualVisitComment%></TEXTAREA></td>
        </tr>
        
        <%
        if(actualVisitId.compareTo("")!=0)
        {
        %>
        <TR class=TableTextNote>
          <TD class=TableTextNote colspan=5>
            <table align=center border=0>
              <tr>
                <td width=25% align=center>
                <%
                  out.print("<INPUT class=button type=button value=\" Sales Form \" name=\"bluecopytotals\" id=\"bluecopytotals\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SALES_AGENT_BLUE_COPY_ENTRY_FORM+"';"+
                            "checkBeforeSalesForm();\">");

                %>
                </td>
                <td width=25% align=center>
                <%
                  out.print("<INPUT class=button type=button value=\" Service Request \" name=\"servicerequest\" id=\"servicerequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SALES_AGENT_CREATE_SERVICE_REQUEST+"';"+
                            "DCMform.submit();\">");

                %>
                </td>
                <td width=25% align=center>
                <%
                  out.print("<INPUT class=button type=button value=\" New Case \" name=\"newcase\" id=\"newcase\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_CASE_FROM_ACTUAL_VISIT+"';"+
                            "checkBeforeOpenNewCase();\">");

                %>
                </td>
                <td width=25% align=center>
                <%
                  out.print("<INPUT class=button type=button value=\" POS Account Data \" name=\"posaccountdata\" id=\"posaccountdata\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_ACTUAL_VISIT_EDIT_POS+"';"+
                            "DCMform.submit();\">");

                %>
                </td>
              </tr>
            </table>
          </TD>
        </tr>
        <%
        }
        %>
      </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SAVE_SALES_AGENT_ACTUAL_VISIT+"';"+
                  "checkBeforeSubmit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
     <%
      if(actualVisitDetailList.size()>0)
      {
      %>
      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD width='25%'>Function Name</TD>
          <TD>Function Desc</TD>
          <TD>Foriegn Key Id</TD>
          <TD align='center'>Show Details</TD>
        </tr>
        <%
        for(int j=0;j<actualVisitDetailList.size();j++)
        {
        ActualVisitDetailModel actualVisitDetailModel = (ActualVisitDetailModel)actualVisitDetailList.get(j);
        String actualVisitDetailId = actualVisitDetailModel.getActualVisitDetailId();
        String functionId = actualVisitDetailModel.getFunctionId();
        String functionName = actualVisitDetailModel.getFunctionName();
        String functionDescription = actualVisitDetailModel.getFunctionDescription();
        if(functionDescription == null)functionDescription = "";
        String functionUnit = actualVisitDetailModel.getFunctionUnit();
        String functionForiegnKeyId = actualVisitDetailModel.getFunctionForiegnKeyId();
        %>
        <TR class="<%=InterfaceKey.STYLE[j%2]%>">
          <TD><%=functionName%></TD>
          <TD><%=functionDescription%></TD>
          <TD><%=functionForiegnKeyId%></TD>
          <TD align='center'>
          <%
            if(functionId.compareTo(DCMInterfaceKey.CONST_DCM_PREDEFINED_FUNCTION_CREATE_NEW_CASE)==0)
            {
            out.print("<INPUT class=button type=button value=\" Case History \" name=\"caseHistory\" id=\"caseHistory\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_CASE_HISTORY+"';"+
                      "document.DCMform."+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+".value = '"+functionForiegnKeyId+"';"+
                      "DCMform.submit();\">");
            }      
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
</form>
</body>
</html>
