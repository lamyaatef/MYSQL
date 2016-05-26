<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.sql.Timestamp"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.core.system.hlp.casepkg.model.*"
         import="com.mobinil.sds.core.system.hlp.casepkg.dto.*"
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.persons.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.DCMModel"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector caseDetailsVec = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  CaseModel caseModel = (CaseModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String caseId = caseModel.getCaseId();
  String caseStatusId = caseModel.getCaseStatusId();
  String caseStatusName = caseModel.getCaseStatusName();
  String caseStatusWaningId = caseModel.getCaseStatusWarningId();
  String caseStatusWarningName = caseModel.getCaseStatusWarningName();
  String caseTilte = caseModel.getCaseTitle();
  String caseDesc = caseModel.getCaseDescription();
  if(caseDesc == null)caseDesc = "";
  String caseSuperTypeId = caseModel.getCaseSuperTypeId();
  String caseSuperTypeName = caseModel.getCaseSuperTypeName();
  String caseTypeId = caseModel.getCaseTypeId();
  String caseTypeName = caseModel.getCaseTypeName();
  String caseCategoryId = caseModel.getCaseCategoryId();
  String caseCategoryName = caseModel.getCaseCategoryName();
  String casePriorityId = caseModel.getCasePriorityId();
  String casePriorityName = caseModel.getCasePriorityName();


  DCMModel dcmModel = null;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2))
  dcmModel = (DCMModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  String dcmName = "N/A";
  String dcmCode = "N/A";
  String channelName = "N/A";
  String levelName = "N/A";
  String stkNumber = "";
  int dcmId = 0;
  if(dcmModel != null)
  {
    dcmName = dcmModel.getDcmName();
    dcmCode = dcmModel.getDcmCode();
    dcmId = dcmModel.getDcmId();
    channelName = dcmModel.getChannelName();
    levelName = dcmModel.getDcmLevel();
    stkNumber = dcmModel.getStkNumber();
    if(stkNumber==null)
    stkNumber="";
  }
%>
    <CENTER>
      <H2> Case History </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+"\""+
                  " value=\""+caseId+"\">");
%>           
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class="TableHeader">
        <td>Case Title</td>
        <td>Description</td>
        <td><%=levelName%> Code</td>
        <td><%=levelName%>   Name</td>
        <td>STK Number</td>
        <td>Channel Name</td>
        <td>Current Status</td>
        <td>Type</td>
        <td>Category</td>
        <td>Priority</td>
      </tr>
      <tr>
        <td><%=caseTilte%></td>
        <td><%=caseDesc%></td>
        <td><%=dcmCode%></td>
        <td><%=dcmName%></td>
        <td><%=stkNumber%></td>
        <td><%=channelName%></td>
        <td><%=caseStatusName%></td>
        <td><%=caseTypeName%></td>
        <td><%=caseCategoryName%></td>
        <td><%=casePriorityName%></td>
      </tr>
    </table>


    <%     
      for(int i=0;i<caseDetailsVec.size();i++)
      {
        CaseDetailModel caseDetailModel = (CaseDetailModel)caseDetailsVec.get(i);
        String caseDetailId = caseDetailModel.getCaseDetailId();
        String caseDetailTitle = caseDetailModel.getCaseTitle();
        String caseDetailDescription = caseDetailModel.getCaseDescription();
        if(caseDetailDescription == null)caseDetailDescription = "";
        String senderUserId = caseDetailModel.getSenderUserId();
        String senderFullName = caseDetailModel.getSenderFullName();
        String senderEmail = caseDetailModel.getSenderEmail();
        String receiverUserId = caseDetailModel.getReceiverUserId();
        String receiverFullName = caseDetailModel.getReceiverFullName();
        String receiverEmail = caseDetailModel.getReceiverEmail();
        Timestamp caseTimestamp = caseDetailModel.getCaseTimestamp();
        String caseDetailStatusId = caseDetailModel.getCaseStatusId();
        String caseDetailStatusName = caseDetailModel.getCaseStatusName();
        String receiverComment = caseDetailModel.getReceiverComment();
        if(receiverComment == null)receiverComment = "";
        String caseDetailPriorityId = caseDetailModel.getCasePriorityId();
        String caseDetailPriorityName = caseDetailModel.getCasePriorityName();
        String caseDetailTypeId = caseDetailModel.getCaseTypeId();
        String caseDetailTypeName = caseDetailModel.getCaseTypeName();
        String caseDetailSuperTypeId = caseDetailModel.getCaseSuperTypeId();
        String caseDetailSuperTypeName = caseDetailModel.getCaseSuperTypeName();
        String caseDetailCategoryId = caseDetailModel.getCaseCategoryId();
        String caseDetailCategoryName = caseDetailModel.getCaseCategoryName();
        String caseDetailStatusWarningId = caseDetailModel.getCaseStatusWarningId();
        String caseDetailStatusWarningName = caseDetailModel.getCaseStatusWarningName();
        if(caseDetailStatusWarningName == null)caseDetailStatusWarningName = "";
        String dialNumber = caseDetailModel.getDialNumber();
        if(dialNumber == null)dialNumber = "";
        String dialNumberTypeId = caseDetailModel.getDialNumberTypeId();
        String dialNumberTypeName = caseDetailModel.getDialNumberTypeName();
        if(dialNumberTypeName == null)dialNumberTypeName = "";
        String caseDirectionId = caseDetailModel.getCaseDirectionId();
        String caseDirectionName = caseDetailModel.getCaseDirectionName();
        if(caseDirectionName == null)caseDirectionName = "";
      %>
       <br><br>
       <b>Timestamp : <%=caseTimestamp%></b>
       <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <tr>
          <td class="TableHeader">Title</td>
          <td class="TableTextNote"><%=caseDetailTitle%></td>
          <td class="TableHeader">Description</td>
          <td class="TableTextNote" colspan=3><%=caseDetailDescription%></td>
        </tr>
        <tr>
          <td class="TableHeader">Dial Number</td>
          <td class="TableTextNote"><%=dialNumber%></td>
          <td class="TableHeader">Dial Number Type</td>
          <td class="TableTextNote"><%=dialNumberTypeName%></td>
          <td class="TableHeader">Case Direction</td>
          <td class="TableTextNote"><%=caseDirectionName%></td>
        </tr>
        <tr>
          <td class="TableHeader">Status</td>
          <td class="TableTextNote"><%=caseDetailStatusName%></td>
          <td class="TableHeader">Type</td>
          <td class="TableTextNote"><%=caseDetailTypeName%></td>
          <td class="TableHeader">Warning</td>
          <td class="TableTextNote"><%=caseDetailStatusWarningName%></td>
        </tr>
        <tr>
          <td class="TableHeader">Sender Name</td>
          <td class="TableTextNote"><%=senderFullName%></td>
          <td class="TableHeader">Sender E-mail</td>
          <td class="TableTextNote"><%=senderEmail%></td>
          <td class="TableHeader">Priority</td>
          <td class="TableTextNote"><%=caseDetailPriorityName%></td>
        </tr>
        <tr>
          <td class="TableHeader">Receiver Name</td>
          <td class="TableTextNote"><%=receiverFullName%></td>
          <td class="TableHeader">Receiver E-mail</td>
          <td class="TableTextNote"><%=receiverEmail%></td>
          <td class="TableHeader">Category</td>
          <td class="TableTextNote"><%=caseDetailCategoryName%></td>
        </tr>
        <tr>
          <td class="TableHeader">Type Category</td>
          <td class="TableTextNote"><%=caseDetailSuperTypeName%></td>  
          <td class="TableHeader">Receiver Comment</td>
          <td class="TableTextNote" colspan=3><%=receiverComment%></td>  
        </tr>
        </table> 
      <%
      }  
      %>
    <br><br>
        <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
        </center>
</body>
</html>
