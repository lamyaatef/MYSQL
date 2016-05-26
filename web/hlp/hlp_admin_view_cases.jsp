<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.core.system.hlp.casepkg.model.*"
         import="com.mobinil.sds.core.system.hlp.casepkg.dto.*" 
         import="java.sql.Timestamp"
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
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(HLPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    function setSearchValues(searchCaseTitle,searchCaseTypeId,searchCaseCategoryId,searchCaseSenderName,searchCaseSendedFrom,searchCaseSendedTo)
    {
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>").value = searchCaseTitle;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID%>").value = searchCaseTypeId;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID%>").value = searchCaseCategoryId;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME%>").value = searchCaseSenderName;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM%>").value = searchCaseSendedFrom;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO%>").value = searchCaseSendedTo;
    }
</script>  
<%       
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  CaseDTO caseDTO = (CaseDTO)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector casesList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector typeList = caseDTO.getCaseTypeList();
  Vector categoryList = caseDTO.getCaseCategoryList();

  String strSearchCaseTitle = "";
  String strSearchCaseTypeId = "";
  String strSearchCaseCategoryId = "";
  String strSearchCaseSenderName = "";
  String strSearchCaseSendedFrom = "*";
  String strSearchCaseSendedTo = "*";

  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE))
  strSearchCaseTitle = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID))
  strSearchCaseTypeId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID))
  strSearchCaseCategoryId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME))
  strSearchCaseSenderName = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM))
  strSearchCaseSendedFrom = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO))
  strSearchCaseSendedTo = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO);
%>

 <CENTER>
      <H2> Admin View Cases </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+"\""+
                  " value=\""+"\">");                
%> 
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=8>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Case Title</td>
        <td align=middle><input type='text' name='<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>'></td>
        <td align=middle>Case Type</td>
        <td align=middle>
            <select name='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID%>'>
            <option value=""></option>
              <%
              for(int k=0;k<typeList.size();k++)
              {
                CaseTypeModel caseTypeModel = (CaseTypeModel)typeList.get(k);
                String strCaseTypeId = caseTypeModel.getCaseTypeId();
                String strCaseTypeName = caseTypeModel.getCaseTypeName();
                String strCaseSTypeId = caseTypeModel.getCaseSuperTypeId();
              %>  
                <option value="<%=strCaseTypeId%>"><%=strCaseTypeName%></option>
              <%  
              }
              %>
            </select>
        </td>
        <td colspan=2 align=middle>Case Category</td>
        <td align=middle>
            <select name='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID%>'>
            <option value=""></option>
            <%
            for(int f=0;f<categoryList.size();f++)
            {
              CaseCategoryModel caseCategoryModel = (CaseCategoryModel)categoryList.get(f);
              String strCaseCategoryId = caseCategoryModel.getCaseCategoryId();
              String strCaseCategoryName = caseCategoryModel.getCaseCategoryName();
              %>
              <option value="<%=strCaseCategoryId%>"><%=strCaseCategoryName%></option>
              <%
            }
            %>
            </select>
        </td>
         </tr>
          <TR class=TableTextNote>
        <td align=middle>Created By</td>
        <td align=middle><input type='text' name='<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME%>'></td>
        <td align=middle>Created From</td>
        <td align=middle colspan='2'><Script>drawCalender('<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM%>',"*");</script></td>
        <td align=middle>Created To</td>
        <td align=middle colspan='2'><Script>drawCalender('<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO%>',"*");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=8>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_ADMIN_SEARCH_CASE+"';"+
                  "HLPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','','','*','*');\">");          
        %>
        </td>
      </tr>
      </table>
       <%
      if(casesList.size()!=0)
      {
      %>
       <br><br>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
     <TD>Title</TD>
          <TD>Type</TD>
          <TD>Category</TD>
          <TD>Description</TD>
          <TD>Created By User</TD>
          <TD>Timestamp</TD>
          <TD>Status</TD>
          <TD>Warning</TD>
          <TD>Forward</TD>
          <TD>Case History</TD>
        </tr>
         <%
         }
        for(int i=0;i<casesList.size();i++)
        {
          CaseModel caseModel = (CaseModel)casesList.get(i);
          String caseId = caseModel.getCaseId();
          String caseTitle = caseModel.getCaseTitle();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_TITLE+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_TITLE+"_"+caseId+"' value='"+caseTitle+"'>");
          String caseDescription = caseModel.getCaseDescription();
          if(caseDescription == null)caseDescription="";
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_DESCRIPTION+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_DESCRIPTION+"_"+caseId+"' value='"+caseDescription+"'>");
          String initiatorUserId = caseModel.getInitiatorUserId();
          String initiatorFullName = caseModel.getInitiatorFullName();
          String initiatorEmail = caseModel.getInitiatorEmail();
          Timestamp initiateTimestamp = caseModel.getInitiateTimestamp();
          String caseTypeId = caseModel.getCaseTypeId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_TYPE_ID+"_"+caseId+"' value='"+caseTypeId+"'>");
          String caseTypeName = caseModel.getCaseTypeName();
          String caseSuperTypeId = caseModel.getCaseSuperTypeId();
          String caseSuperTypeName = caseModel.getCaseSuperTypeName();
          String caseStatusId = caseModel.getCaseStatusId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID+"_"+caseId+"' value='"+caseStatusId+"'>");
          String caseStatusName = caseModel.getCaseStatusName();
          String casePriorityId = caseModel.getCasePriorityId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_PRIORITY_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_PRIORITY_ID+"_"+caseId+"' value='"+casePriorityId+"'>");
          String casePriorityName = caseModel.getCasePriorityName();
          String caseCategoryId = caseModel.getCaseCategoryId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_CATEGORY_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_CATEGORY_ID+"_"+caseId+"' value='"+caseCategoryId+"'>");
          String caseCategoryName = caseModel.getCaseCategoryName();
          String caseStatusWarningId = caseModel.getCaseStatusWarningId();
          if(caseStatusWarningId == null)caseStatusWarningId = "";
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_WARNING_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_WARNING_ID+"_"+caseId+"' value='"+caseStatusWarningId+"'>");
          String caseStatusWarningName = caseModel.getCaseStatusWarningName();
          if(caseStatusWarningName==null)caseStatusWarningName="";
          String currentReceiverUserId = caseModel.getCurrentReceiverUserId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_HIDDEN_RECEIVER_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_HIDDEN_RECEIVER_ID+"_"+caseId+"' value='"+currentReceiverUserId+"'>");
          String currentReceiverFullName = caseModel.getCurrentReceiverFullName();
          String currentReceiverEmail = caseModel.getCurrentReceiverEmail();

          String posCode = caseModel.getPosCode();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_TEXT_DCM_CODE+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_TEXT_DCM_CODE+"_"+caseId+"' value='"+posCode+"'>");
          String dialNumber = caseModel.getDialNumber();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER+"_"+caseId+"' value='"+dialNumber+"'>");
          String dialNumberTypeId = caseModel.getDialNumberTypeId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID+"_"+caseId+"' value='"+dialNumberTypeId+"'>");
          String dialNumberTypeName = caseModel.getDialNumberTypeName();
          String caseDirectionId = caseModel.getCaseDirectionId();
          out.println("<input type='hidden' name='"+HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID+"_"+caseId+"' id='"+HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID+"_"+caseId+"' value='"+caseDirectionId+"'>");
          String caseDirectionName = caseModel.getCaseDirectionName();
%>

 <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <TD><%=caseTitle%></TD>
          <TD><%=caseTypeName%></TD>
          <TD><%=caseCategoryName%></TD>
          <TD><%=caseDescription%></TD>
          <TD><%=initiatorFullName%></TD>
          <TD><%=initiateTimestamp%></TD>
          <TD><%=caseStatusName%></TD>
          <TD><%=caseStatusWarningName%></TD>
          <TD>
          <%
            out.print("<INPUT class=button type=button value=\" Forward \" name=\"view\" id=\"view\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_CASE_DETAIL+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+".value = '"+caseId+"';"+
                      "HLPform.submit();\">");
          %>
          </TD>
          <TD>
          <%
            out.print("<INPUT class=button type=button value=\" Case History \" name=\"view\" id=\"view\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_CASE_HISTORY+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+".value = '"+caseId+"';"+
                      "HLPform.submit();\">");
          %>
          </TD>
        </tr>
        <%
        }
        %>
        </table> 
         <%
       out.println("<script>setSearchValues('"+strSearchCaseTitle+"','"+strSearchCaseTypeId+"','"+strSearchCaseCategoryId+"','"+strSearchCaseSenderName+"','"+strSearchCaseSendedFrom+"','"+strSearchCaseSendedTo+"')</script>");
       %>
        </form>
</body>
</html>
          