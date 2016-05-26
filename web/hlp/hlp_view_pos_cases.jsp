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
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector casesList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  String strPosCode = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_TEXT_DCM_CODE);

%>

<CENTER>
      <H2> DCM Cases List </H2>
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

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1 id="tableCases"> 
    <tr class=TableHeader>
    <td align='center'>Title</td>
     <td align='center'>Type</td>
      <td align='center'>Category</td>
       <td align='center'>Description</td>
        <td align='center'>Created By User</td> 
         <td align='center'>Timestamp</td>
          <td align='center'>Status</td>
           <td align='center'>Warning</td>
            <td align='center'>Case History</td>
    </tr>
<%
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
          if (caseStatusWarningName == null)
          caseStatusWarningName = "";
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
 <tr class=<%=InterfaceKey.STYLE[i%2]%> >
  <TD><%=caseTitle%></TD>
   <TD><%=caseTypeName%></TD>
    <TD><%=caseCategoryName%></TD>
     <TD><%=caseDescription%></TD>
      <TD><%=initiatorFullName%></TD>
       <TD><%=initiateTimestamp%></TD>
        <TD><%=caseStatusName%></TD>
         <TD><%=caseStatusWarningName%></TD>
         <td>
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
</form>
</body>
</html>
