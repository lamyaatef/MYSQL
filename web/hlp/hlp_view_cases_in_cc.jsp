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
    
    function checkBeforeSubmit()
    {
      HLPform.submit();
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

    function setSearchValues(searchCaseTitle,searchCaseTypeId,searchCaseCategoryId,searchCaseStatusId,searchCaseSenderName,searchCaseSendedFrom,searchCaseSendedTo)
    {
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>").value = searchCaseTitle;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID%>").value = searchCaseTypeId;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID%>").value = searchCaseCategoryId;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID%>").value = searchCaseStatusId;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME%>").value = searchCaseSenderName;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM%>").value = searchCaseSendedFrom;
      document.getElementById("<%=HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO%>").value = searchCaseSendedTo;
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector casesUserInCC = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
  
  CaseDTO caseDTO = (CaseDTO)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector caseStatusList = caseDTO.getCaseStatusList();
  Vector caseStatusWarningList = caseDTO.getCaseStatusWarningList();

  Vector typeList = caseDTO.getCaseTypeList();
  Vector categoryList = caseDTO.getCaseCategoryList();

  String strSearchCaseTitle = "";
  String strSearchCaseTypeId = "";
  String strSearchCaseCategoryId = "";
  String strSearchCaseStatusId = "";
  String strSearchCaseSenderName = "";
  String strSearchCaseSendedFrom = "*";
  String strSearchCaseSendedTo = "*";

  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE))
  strSearchCaseTitle = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID))
  strSearchCaseTypeId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_TYPE_ID);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID))
  strSearchCaseCategoryId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_CATEGORY_ID);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID))
  strSearchCaseStatusId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME))
  strSearchCaseSenderName = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDER_NAME);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM))
  strSearchCaseSendedFrom = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_FROM);
  if(objDataHashMap.containsKey(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO))
  strSearchCaseSendedTo = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_SEARCH_TEXT_CASE_SENDED_TO);
            
%>   
    <CENTER>
      <H2> View Cases </H2>
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
        <td align=middle>Case Category</td>
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
        <td align=middle>Case Status</td>
        <td align=middle>
            <select name='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID%>' id='<%=HLPInterfaceKey.INPUT_SEARCH_SELECT_CASE_STATUS_ID%>'>
            <option value=""></option>
              <%
              for(int j=0;j<caseStatusList.size();j++)
              {
                CaseStatusModel caseStatusModel = (CaseStatusModel)caseStatusList.get(j);
                String strCaseStatusId = caseStatusModel.getCaseStatusId();
                String strCaseStatusName = caseStatusModel.getCaseStatusName();
                String statusSelected = "";
                %>
                <option value="<%=strCaseStatusId%>"><%=strCaseStatusName%></option>  
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
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SEARCH_CASE_USER_IN_CC+"';"+
                  "HLPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','','','','*','*');\">");          
        %>
        </td>
      </tr>
      </table>
      
      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        
        <%
        for(int x=0;x<casesUserInCC.size();x++)
        {
        if(x==0)
        {
        %>
        <TR class=TableHeader>
          <TD>Case Title</TD>
          <TD>Case Type</TD>
          <TD>Case Category</TD>
          <TD>Description</TD>
          <TD>Sender</TD>
          <TD>Receiver</TD>
          <TD>Timestamp</TD>
          <TD>Status</TD>
          <TD>Warning</TD>
          <TD>Case History</TD>
        </tr>
        <%
        }
        CaseDetailModel caseDetailModel = (CaseDetailModel)casesUserInCC.get(x);
        String caseDetailId = caseDetailModel.getCaseDetailId();
        String caseId = caseDetailModel.getCaseId();
        String caseTitle = caseDetailModel.getCaseTitle();
        String caseDescription = caseDetailModel.getCaseDescription();
        if(caseDescription == null)caseDescription = "";
        String senderUserId = caseDetailModel.getSenderUserId();
        String senderFullName = caseDetailModel.getSenderFullName();
        String senderEmail = caseDetailModel.getSenderEmail();
        String receiverUserId = caseDetailModel.getReceiverUserId();
        String receiverFullName = caseDetailModel.getReceiverFullName();
        String receiverEmail = caseDetailModel.getReceiverEmail();
        Timestamp caseTimestamp = caseDetailModel.getCaseTimestamp();
        String caseStatusId = caseDetailModel.getCaseStatusId();
        String caseStatusName = caseDetailModel.getCaseStatusName();
        String receiverComment = caseDetailModel.getReceiverComment();
        String casePriorityId = caseDetailModel.getCasePriorityId();
        String casePriorityName = caseDetailModel.getCasePriorityName();
        String caseTypeId = caseDetailModel.getCaseTypeId();
        String caseTypeName = caseDetailModel.getCaseTypeName();
        String caseSuperTypeId = caseDetailModel.getCaseSuperTypeId();
        String caseSuperTypeName = caseDetailModel.getCaseSuperTypeName();
        String caseCategoryId = caseDetailModel.getCaseCategoryId();
        String caseCategoryName = caseDetailModel.getCaseCategoryName();
        String caseStatusWarningId = caseDetailModel.getCaseStatusWarningId();
        String caseStatusWarningName = caseDetailModel.getCaseStatusWarningName();
        if(caseStatusWarningName == null)caseStatusWarningName = "";
        %>
        <TR class=<%=InterfaceKey.STYLE[x%2]%>>
          <%
          out.println("<TD><a href=\"#\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL_USER_IN_CC+"';"+
                      "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+".value = '"+caseId+"';"+
                      "HLPform.submit();\">"+caseTitle+"</a></TD>");
          %>
          <TD><%=caseTypeName%></TD>
          <TD><%=caseCategoryName%></TD>
          <TD><%=caseDescription%></TD>
          <TD><%=senderFullName%></TD>
          <TD><%=receiverFullName%></TD>
          <TD><%=caseTimestamp%></TD>
          <TD><%=caseStatusName%></TD>
          <TD><%=caseStatusWarningName%></TD>
          <TD>
          <%
            out.print("<INPUT class=button type=button value=\" Case History \" name=\"casehistory\" id=\"casehistory\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_CASE_HISTORY+"';"+
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
       out.println("<script>setSearchValues('"+strSearchCaseTitle+"','"+strSearchCaseTypeId+"','"+strSearchCaseCategoryId+"','"+strSearchCaseStatusId+"','"+strSearchCaseSenderName+"','"+strSearchCaseSendedFrom+"','"+strSearchCaseSendedTo+"')</script>");
       %>
</form>
</body>
</html>
