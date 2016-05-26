<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.Timestamp"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
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
<script>
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
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_RECEIVER_ID%>.value") == "")
      {
        alert("Send To must not be empty.");
        return;
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CASE_TITLE%>.value") == "")
      {
        alert("Case Title must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_TYPE_ID%>.value") == "")
      {
        alert("Case Type must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID%>.value") == "")
      {
        alert("Case Category must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID%>.value") == "")
      {
        alert("Case Priority must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID%>.value") == "")
      {
        alert("Case Direction must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>.value") == "")
      {
          if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER%>.value") == "")
          {
            alert("Dial Number must not be empty because POS Code is N/A.");
            return;  
          }
          else if(!IsNumeric(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER%>.value")))
          {
            alert("Dial Number must be a number.");
            return; 
          }
          else
          {
            if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID%>.value") == "")
            {
              alert("Dial Number Type must not be empty .");
              return;  
            }
          }
      }
      HLPform.submit();
    }

    function checkBeforeSubmitForUpdate()
    {
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CASE_TITLE%>.value") == "")
      {
        alert("Case Title must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_TYPE_ID%>.value") == "")
      {
        alert("Case Type must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID%>.value") == "")
      {
        alert("Case Category must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID%>.value") == "")
      {
        alert("Case Priority must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID%>.value") == "")
      {
        alert("Case Direction must not be empty.");
        return;  
      }
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>.value") == "")
      {
          if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER%>.value") == "")
          {
            alert("Dial Number must not be empty because POS Code is N/A.");
            return;  
          }
          else if(!IsNumeric(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER%>.value")))
          {
            alert("Dial Number must be a number.");
            return; 
          }
          else
          {
            if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID%>.value") == "")
            {
              alert("Dial Number Type must not be empty .");
              return;  
            }
          }
      }
      if((eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID%>.value") == "2" || eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID%>.value") == "3") && eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID%>.value") == "")
      {
        alert("Status warning must not be empty .");
        return;  
      }
      HLPform.submit();
    }

    function addccReceiver()
    {
      var ccReceiverEamilobj = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CC_RECEIVER_EMAIL%>");
      var ccReceiverEamilSelectedIndex = ccReceiverEamilobj.selectedIndex;
      var ccReceiverEamil = ccReceiverEamilobj.options[ccReceiverEamilSelectedIndex].text+",";
      var ccReceiverId = ccReceiverEamilobj.options[ccReceiverEamilSelectedIndex].value+",";

      var ccReceiverEamiluserInterface = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>.value");
      ccReceiverEamiluserInterface = ccReceiverEamiluserInterface.replace(","+ccReceiverEamil,",");
      eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>.value = ccReceiverEamiluserInterface");

      var ccReceiverIduserInterface = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>.value");
      ccReceiverIduserInterface = ccReceiverIduserInterface.replace(","+ccReceiverId,",");
      eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>.value = ccReceiverIduserInterface");

      eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>.value = document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>.value+ccReceiverEamil");
      eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>.value = document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>.value+ccReceiverId");
    }

    function removeccReceiver()
    {
      var ccReceiverEamilobj = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CC_RECEIVER_EMAIL%>");
      var ccReceiverEamilSelectedIndex = ccReceiverEamilobj.selectedIndex;
      var ccReceiverEamil = ccReceiverEamilobj.options[ccReceiverEamilSelectedIndex].text+",";
      var ccReceiverId = ccReceiverEamilobj.options[ccReceiverEamilSelectedIndex].value+",";

      var ccReceiverEamiluserInterface = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>.value");
      ccReceiverEamiluserInterface = ccReceiverEamiluserInterface.replace(","+ccReceiverEamil,",");
      eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>.value = ccReceiverEamiluserInterface");

      var ccReceiverIduserInterface = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>.value");
      ccReceiverIduserInterface = ccReceiverIduserInterface.replace(","+ccReceiverId,",");
      eval("document.HLPform.<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>.value = ccReceiverIduserInterface");
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
<%!
 String convertFromUTF8(String s) {
  String out = null;
  //Utility.logger.debug("S before = " + s);

  try {
    out = new String(s.getBytes("Cp1256"), "UTF-8");
    //Utility.logger.debug("out = " + out);
  } catch (java.io.UnsupportedEncodingException e) {
    return null;
  }
  return out;
}

%>
<% 



  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String commingAction = (String)objDataHashMap.get(HLPInterfaceKey.HLP_INPUT_KEY_ACTION);
  String action = "";

  if(commingAction!=null&&commingAction.equalsIgnoreCase(HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL))
    action = HLPInterfaceKey.ACTION_HLP_FORWARD_CASE;
  if(commingAction!=null&&commingAction.equalsIgnoreCase(HLPInterfaceKey.ACTION_HLP_ADMIN_VIEW_CASE_DETAIL))
    action = HLPInterfaceKey.ACTION_HLP_ADMIN_FORWARD_CASE;

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strMsg+"');</script>");
  }
  CaseDTO caseDTO = (CaseDTO)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector caseStatusList = caseDTO.getCaseStatusList();
  Vector caseStatusWarningList = caseDTO.getCaseStatusWarningList();

  String strPageHeader = (String)objDataHashMap.get(HLPInterfaceKey.PAGE_HEADER);
  String strPreviousAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
  boolean boCreateNewCase = false;
  boolean boForwardCase = false;
  boolean boUpdateCaseDetail = false;
  boolean boViewCaseDetailUserInCC = false;
  
  String inputsCaseDisplay = "";
  
  if(strPreviousAction.compareTo(HLPInterfaceKey.ACTION_HLP_CREATE_NEW_CASE) == 0 )
  { 
    boCreateNewCase = true;
  }
  else if(strPreviousAction.compareTo(HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL) == 0 )
  {
    boForwardCase = true;
    inputsCaseDisplay = "style='display:none'";
  }
  else if(strPreviousAction.compareTo(HLPInterfaceKey.ACTION_HLP_UPDATE_CASE_DETAIL) == 0 )
  {
    boUpdateCaseDetail = true;
  }
  else if(strPreviousAction.compareTo(HLPInterfaceKey.ACTION_HLP_VIEW_CASE_DETAIL_USER_IN_CC) == 0 )
  {
    boViewCaseDetailUserInCC = true;
    inputsCaseDisplay = "style='display:none'";
  }
  
  Vector userList = caseDTO.getUserList();
  Vector superTypeList = caseDTO.getCaseSuperTypeList();
  Vector typeList = caseDTO.getCaseTypeList();
  Vector categoryList = caseDTO.getCaseCategoryList();
  Vector priorityList = caseDTO.getCasePriorityList();
  Vector dialNumberTypeList = caseDTO.getDialNumberType();
  Vector caseDirectionList = caseDTO.getCaseDirection();
  
  CaseModel caseModel = null;
  String caseId = "";
  String caseStatusId = "";
  String caseStatusWaningId = "";
  String caseTilteValue = "";
  String caseDescValue = "";
  String caseSuperTypeIdValue = "";
  String caseTypeIdValue = "";
  String caseCategoryIdValue = "";
  String casePriorityIdValue = "";
  String caseUserCommentsValue = "";
  String dialNumber = "";
  String dialNumberTypeId = "";
  String dialNumberTypeName = "";
  String caseDirectionId = "";
  String caseDirectionName = "";
  String createdBy = "";
  Timestamp createdTimestamp = null;
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION))
  caseModel = (CaseModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  if(caseModel != null)
  {
    caseId = caseModel.getCaseId();
    caseStatusId = caseModel.getCaseStatusId();
    if(caseStatusId.compareTo("2")==0)
    {
      boViewCaseDetailUserInCC = true;
      inputsCaseDisplay = "style='display:none'";
    }
    caseStatusWaningId = caseModel.getCaseStatusWarningId();
    caseTilteValue = caseModel.getCaseTitle();
    caseDescValue = caseModel.getCaseDescription();
    if(caseDescValue == null)caseDescValue = "";
    caseSuperTypeIdValue = caseModel.getCaseSuperTypeId();
    caseTypeIdValue = caseModel.getCaseTypeId();
    caseCategoryIdValue = caseModel.getCaseCategoryId();
    casePriorityIdValue = caseModel.getCasePriorityId();
    dialNumber = caseModel.getDialNumber();
    if(dialNumber == null)dialNumber = "";
    dialNumberTypeId = caseModel.getDialNumberTypeId();
    if(dialNumberTypeId == null)dialNumberTypeId = "";
    dialNumberTypeName = caseModel.getDialNumberTypeName();
    if(dialNumberTypeName == null)dialNumberTypeName = "";
    caseDirectionId = caseModel.getCaseDirectionId();
    if(caseDirectionId == null)caseDirectionId = "";
    caseDirectionName = caseModel.getCaseDirectionName();
    if(caseDirectionName == null)caseDirectionName = "";
    createdBy = caseModel.getInitiatorFullName();
    createdTimestamp = caseModel.getInitiateTimestamp();
  }

  DCMModel dcmModel = null;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2))
  dcmModel = (DCMModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);

%>   
    <CENTER>
      <H2> <%=strPageHeader%> </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+"\""+
                  " value=\""+caseId+"\">");

  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID))
  {
    String actualVisitId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID);
    String functionId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_FUNCTION_ID);
    
    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID+"\""+
                  " value=\""+actualVisitId+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_FUNCTION_ID+"\""+
                  " value=\""+functionId+"\">");
    if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID))
    {
      String serviceId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID);
      out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+"\""+
                  " value=\""+serviceId+"\">");  
    }
  }  

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_INFO_ELEMENT_ID+"\""+
                  " value=\""+"\">");
                  
   if(boForwardCase || boCreateNewCase)
   {
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD colSpan=5 align='center'>Send To</TD>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">To: </TD>
          <TD colSpan=4>
            <select name='<%=HLPInterfaceKey.INPUT_SELECT_RECEIVER_ID%>' id='<%=HLPInterfaceKey.INPUT_SELECT_RECEIVER_ID%>' >
            <option value=''></option>
            <%
            for(int i=0;i<userList.size();i++)
            {
              UserDTO usetDTO = (UserDTO)userList.get(i);
              UserModel userModel = usetDTO.getUserModel();
              PersonModel personModel = userModel.getPersonModel();
              String strPersonFullName = personModel.getPersonFullName();
              String strPersonEmail = personModel.getPersonEMail();
              int intPersonId = personModel.getPersonID();
              
              out.println("<option value=\""+intPersonId+"\">"+strPersonFullName+"("+strPersonEmail+")</option>");
             
            }
            %>
            </select>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD width=\"20%\">Cc: </TD>
          <TD>
            <INPUT readonly type='text' style="WIDTH: 375px; HEIGHT: 22px" size=66 name="<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>" id="<%=HLPInterfaceKey.INPUT_TEXT_CC_RECEIVER_EMAIL%>" value=",">
            <input type='hidden' name="<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>" id="<%=HLPInterfaceKey.INPUT_HIDDEN_CC_RECEIVER_ID%>" value=",">
          </td>
          <TD width=\"20%\">Select User: </TD>
          <TD>
            <select name='<%=HLPInterfaceKey.INPUT_SELECT_CC_RECEIVER_EMAIL%>' id='<%=HLPInterfaceKey.INPUT_SELECT_CC_RECEIVER_EMAIL%>'>
            <option value=''></option>
            <%
            for(int i=0;i<userList.size();i++)
            {
              UserDTO usetDTO = (UserDTO)userList.get(i);
              UserModel userModel = usetDTO.getUserModel();
              PersonModel personModel = userModel.getPersonModel();
              String strPersonFullName = personModel.getPersonFullName();
              String strPersonEmail = personModel.getPersonEMail();
              int intPersonId = personModel.getPersonID();
              %>
              <option value="<%=intPersonId%>"><%=strPersonEmail%></option>
              <%
            }
            %>
            </select>
          </td>
          <td> 
            <input type='button' class='button' name='addccreceiver' id='addccreceiver' value='Add' onclick="addccReceiver();">
            <input type='button' class='button' name='removeccreceiver' id='removeccreceiver' value='Remove' onclick="removeccReceiver();">
          </td>
        </tr>
      </table>  
      
      <br><br>

      <%
      }
      
      String dcmName = "N/A";
      String dcmCode = "";
      String stkNumber = "";
      String channelName = "N/A";
      String levelName = "N/A";
      int dcmId = 0;
      if(dcmModel != null)
      {
        dcmName = dcmModel.getDcmName();
        dcmCode = dcmModel.getDcmCode();
        dcmId = dcmModel.getDcmId();
        stkNumber = dcmModel.getStkNumber();
        levelName = dcmModel.getDcmLevel();
        if(stkNumber == null)
        stkNumber = "";
        channelName = dcmModel.getChannelName();
      }
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
          <TR class=TableHeader>
            <td align="center"><%=levelName%> Code</td>
            <td align="center"><%=levelName%> Name</td>
            <td align="center">STK Number</td>
            <td align="center">Channel Name</td>
            
          </tr>
          <TR class=TableTextNote>
            <td align="center"><%if(dcmCode.compareTo("")==0)out.println("N/A");else out.println(dcmCode);%><input type="hidden" name="<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>" id="<%=HLPInterfaceKey.INPUT_TEXT_DCM_CODE%>" value="<%=dcmCode%>"></td>
            <td align="center"><%=dcmName%></td>
            <td align="center"><%=stkNumber%></td>
            <td align="center"><%=channelName%></td>
          </tr>
        </table>
        <br><br>
      
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Title</TD>
          <TD class=TableTextNote colSpan=4><INPUT <%=inputsCaseDisplay%> type='text' size="40" name="<%=HLPInterfaceKey.INPUT_TEXT_CASE_TITLE%>" id="<%=HLPInterfaceKey.INPUT_TEXT_CASE_TITLE%>" value="<%=caseTilteValue%>">
          <%
          if(boForwardCase||boViewCaseDetailUserInCC)
          out.println(caseTilteValue);
          %>
          </td>
        </tr>
        <%
        if(boForwardCase || boUpdateCaseDetail || boViewCaseDetailUserInCC)
        {
        %>
          <TR class=TableTextNote>
            <TD class=TableTextNote width=\"20%\">Created By User</TD>
            <TD class=TableTextNote colSpan=4><%=createdBy%></td>
          </tr>
          <TR class=TableTextNote>
            <TD class=TableTextNote width=\"20%\">Created Timestamp</TD>
            <TD class=TableTextNote colSpan=4><%=createdTimestamp%></td>
          </tr>
        <%  
        }
        if(boUpdateCaseDetail && caseStatusId.compareTo("2")!=0)
        {
          %>
          <TR class=TableTextNote>
            <TD class=TableTextNote width=\"20%\">Status</TD>
            <TD class=TableTextNote colSpan=4>
            <script>
            function changeCaseStatuWarningSelect(statusValue,inputStatusName)
            {
              var caseStatusWarningListName = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID%>.name")
              removeAllOptions(caseStatusWarningListName);
              addOption('','',caseStatusWarningListName);  
              <%
              for(int l=0;l<caseStatusWarningList.size();l++)
              {
                CaseStatusModel caseStatusWarningModelX = (CaseStatusModel)caseStatusWarningList.get(l);
                String strCaseStatusIdX = caseStatusWarningModelX.getCaseStatusId();
                String strCaseStatusWarningNameX = caseStatusWarningModelX.getCaseStatusWarningName();
                String strCaseStatusWarningIdX = caseStatusWarningModelX.getCaseStatusWarningId();
                %>
                if(<%=strCaseStatusIdX%> == statusValue)
                {
                  addOption('<%=strCaseStatusWarningIdX%>','<%=strCaseStatusWarningNameX%>',caseStatusWarningListName);
                }
                <%
              }
              %>
            }
          </script>
            <select name="<%=HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID%>" id="<%=HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID%>" onchange="changeCaseStatuWarningSelect(this.value,this.name);">
              <%
              for(int j=0;j<caseStatusList.size();j++)
              {
                CaseStatusModel caseStatusModel = (CaseStatusModel)caseStatusList.get(j);
                String strCaseStatusId = caseStatusModel.getCaseStatusId();
                String strCaseStatusName = caseStatusModel.getCaseStatusName();
                String statusSelected = "";
                if(strCaseStatusId.compareTo(caseStatusId)==0)statusSelected = "selected";
                if(caseStatusId.compareTo("2")==0)
                {
                  if(strCaseStatusId.compareTo(caseStatusId)==0)
                  {
                  %>
                  <option value="<%=strCaseStatusId%>" <%=statusSelected%>><%=strCaseStatusName%></option>  
                  <%
                  }
                }
                else
                {
                  %>
                  <option value="<%=strCaseStatusId%>" <%=statusSelected%>><%=strCaseStatusName%></option>  
                  <%
                }
              }
              %>
            </select>
            </td>
          </tr>
          <TR class=TableTextNote>
            <TD class=TableTextNote width=\"20%\">Status Warning</TD>
            <TD class=TableTextNote colSpan=4>
            <select name="<%=HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID%>">
              <option value=""></option>
              <%
              for(int k=0;k<caseStatusWarningList.size();k++)
              {
                CaseStatusModel caseStatusWarningModel = (CaseStatusModel)caseStatusWarningList.get(k);
                String strCaseStatusIdX = caseStatusWarningModel.getCaseStatusId();
                String strCaseStatusWarningId = caseStatusWarningModel.getCaseStatusWarningId();
                String strCaseStatusWarningName = caseStatusWarningModel.getCaseStatusWarningName();
                String statusWarningSelected = "";
                if(caseStatusWaningId != null)
                {
                  if(strCaseStatusWarningId.compareTo(caseStatusWaningId) == 0)statusWarningSelected = "selected";
                }
                if(strCaseStatusIdX.compareTo(caseStatusId) == 0)
                {
                %>
                <option value="<%=strCaseStatusWarningId%>" <%=statusWarningSelected%>><%=strCaseStatusWarningName%></option>  
                <%
                }
              }
              %>
            </select>
            </td>
          </tr>
          <%
        }
        else
        {
        out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_STATUS_ID+"\""+
                  " value=\""+caseStatusId+"\">");

        out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID+"\""+
                        " value=\""+caseStatusWaningId+"\">");
        }
        %>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Dial Number</TD>
          <TD class=TableTextNote colSpan=4><INPUT <%=inputsCaseDisplay%> type='text' name="<%=HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER%>" id="<%=HLPInterfaceKey.INPUT_TEXT_DIAL_NUMBER%>" value="<%=dialNumber%>">
          <%
          if(boForwardCase||boViewCaseDetailUserInCC)
          out.println(dialNumber);
          %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Dial Number Type</TD>
          <TD class=TableTextNote colSpan=4>
          <select <%=inputsCaseDisplay%> name="<%=HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_DIAL_NUMBER_TYPE_ID%>">
            <option value=""></option>
            <%
            for(int q=0;q<dialNumberTypeList.size();q++)
            {
              DialNumberTypeModel dialNumberTypeModel = (DialNumberTypeModel)dialNumberTypeList.get(q);
              String strDialNumberTypeId = dialNumberTypeModel.getDialNumberTypeId();
              String strDialNumberTypeName = dialNumberTypeModel.getDialNumberTypeName();
              String dialNumberTypeSelected = "";
              if(dialNumberTypeId.compareTo(strDialNumberTypeId)==0)
              { 
                dialNumberTypeSelected = "selected";
              }
              %>
              <option value="<%=strDialNumberTypeId%>" <%=dialNumberTypeSelected%>><%=strDialNumberTypeName%></option>
              <%
            }
            %>
            </select>
          <%
          if(boForwardCase||boViewCaseDetailUserInCC)
          out.println(dialNumberTypeName);
          %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Direction</TD>
          <TD class=TableTextNote colSpan=4>
          <select <%=inputsCaseDisplay%> name="<%=HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_CASE_DIRECTION_ID%>">
            <option value=""></option>
            <%
            for(int u=0;u<caseDirectionList.size();u++)
            {
              CaseDirectionModel caseDirectionModel = (CaseDirectionModel)caseDirectionList.get(u);
              String strCaseDirectionId = caseDirectionModel.getCaseDirectionId();
              String strCaseDirectionName = caseDirectionModel.getCaseDirectionName();
              String caseDirectionSelected = "";
              if(caseDirectionId.compareTo(strCaseDirectionId)==0)
              { 
                caseDirectionSelected = "selected";
              }
              %>
              <option value="<%=strCaseDirectionId%>" <%=caseDirectionSelected%>><%=strCaseDirectionName%></option>
              <%
            }
            %>
            </select>
          <%
          if(boForwardCase||boViewCaseDetailUserInCC)
          out.println(caseDirectionName);
          %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Type Category</TD>
          <TD class=TableTextNote colSpan=4>
          <script>
            function changeCaseTypeSelect(superTypeValue)
            {
              var caseTypeListName = eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_TYPE_ID%>.name")
              removeAllOptions(caseTypeListName);
              <%
              for(int l=0;l<typeList.size();l++)
              {
                CaseTypeModel caseTypeModelX = (CaseTypeModel)typeList.get(l);
                String strCaseTypeIdX = caseTypeModelX.getCaseTypeId();
                String strCaseTypeNameX = caseTypeModelX.getCaseTypeName();
                String strCaseSTypeIdX = caseTypeModelX.getCaseSuperTypeId();
                %>
                if(<%=strCaseSTypeIdX%> == superTypeValue)
                {

                  addOption('<%=strCaseTypeIdX%>',"<%=convertFromUTF8(strCaseTypeNameX)%>",caseTypeListName);
                }
                <%
              }
              %>
            }
          </script>
            <select <%=inputsCaseDisplay%> name="<%=HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_SUPER_TYPE_ID%>" onchange="changeCaseTypeSelect(this.value);">
              <option value=""></option>
              <%
              String caseSelectedSuperTypeName = "";
              for(int j=0;j<superTypeList.size();j++)
              {
                CaseTypeModel caseSuperTypeModel = (CaseTypeModel)superTypeList.get(j);
                String strCaseSuperTypeId = caseSuperTypeModel.getCaseSuperTypeId();
                String strCaseSuperTypeName = caseSuperTypeModel.getCaseSuperTypeName();
                String caseSuperTypeSelected = "";
                if(caseSuperTypeIdValue.compareTo(strCaseSuperTypeId) == 0)
                {
                  caseSuperTypeSelected = "selected";
                  caseSelectedSuperTypeName = strCaseSuperTypeName;
                }
                %>
                <option value="<%=strCaseSuperTypeId%>" <%=caseSuperTypeSelected%>><%=strCaseSuperTypeName%></option>
                <%
              }
              %>
            </select>
            <%
            if(boForwardCase||boViewCaseDetailUserInCC)out.println(caseSelectedSuperTypeName);
            %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Type</TD>
          <TD class=TableTextNote colSpan=4>
            <select <%=inputsCaseDisplay%> name="<%=HLPInterfaceKey.INPUT_SELECT_TYPE_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_TYPE_ID%>">
            <option value=""></option>
              <%
              String caseSelectedTypeName = "";
              for(int k=0;k<typeList.size();k++)
              {
                CaseTypeModel caseTypeModel = (CaseTypeModel)typeList.get(k);
                String strCaseTypeId = caseTypeModel.getCaseTypeId();
                String strCaseTypeName = caseTypeModel.getCaseTypeName();
                String strCaseSTypeId = caseTypeModel.getCaseSuperTypeId();
                String typeSelected = "";
                if(caseSuperTypeIdValue.compareTo(strCaseSTypeId) == 0)
                {
                  if(caseTypeIdValue.compareTo(strCaseTypeId)==0)
                  {
                    typeSelected = "selected";
                    caseSelectedTypeName = strCaseTypeName;
                  }
                  %>
                  <option value="<%=strCaseTypeId%>" <%=typeSelected%>><%=strCaseTypeName%></option>
                  <%
                }
              }
              %>
            </select>
            <%
            if(boForwardCase||boViewCaseDetailUserInCC)out.println(caseSelectedTypeName);
            %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Category</TD>
          <TD class=TableTextNote colSpan=4>
            <select <%=inputsCaseDisplay%> name="<%=HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_CATEGORY_ID%>">
            <option value=""></option>
            <%
            String caseSelectedCategoryName = "";
            for(int f=0;f<categoryList.size();f++)
            {
              CaseCategoryModel caseCategoryModel = (CaseCategoryModel)categoryList.get(f);
              String strCaseCategoryId = caseCategoryModel.getCaseCategoryId();
              String strCaseCategoryName = caseCategoryModel.getCaseCategoryName();
              String caseCategorySeleted = "";
              if(caseCategoryIdValue.compareTo(strCaseCategoryId)==0)
              {
                caseCategorySeleted = "selected";
                caseSelectedCategoryName = strCaseCategoryName;
              }
              %>
              <option value="<%=strCaseCategoryId%>" <%=caseCategorySeleted%>><%=strCaseCategoryName%></option>
              <%
            }
            %>
            </select>
            <%
            if(boForwardCase||boViewCaseDetailUserInCC)out.println(caseSelectedCategoryName);
            %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Priority</TD>
          <TD class=TableTextNote colSpan=4>
            <select <%=inputsCaseDisplay%> name="<%=HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_PRIORITY_ID%>">
            <option value=""></option>
            <%
            String caseSelectedPriorityName = "";
            for(int q=0;q<priorityList.size();q++)
            {
              CasePriorityModel casePriorityModel = (CasePriorityModel)priorityList.get(q);
              String strCasePriorityId = casePriorityModel.getCasePriorityId();
              String strCasePriorityName = casePriorityModel.getCasePriorityName();
              String casePrioritySelected = "";
              if(casePriorityIdValue.compareTo(strCasePriorityId)==0)
              { 
                casePrioritySelected = "selected";
                caseSelectedPriorityName = strCasePriorityName;
              }
              %>
              <option value="<%=strCasePriorityId%>" <%=casePrioritySelected%>><%=strCasePriorityName%></option>
              <%
            }
            %>
            </select>
            <%
            if(boForwardCase||boViewCaseDetailUserInCC)out.println(caseSelectedPriorityName);
            %>
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Description</TD>
          <TD class=TableTextNote colSpan=4>
            <textarea <%=inputsCaseDisplay%> rows=5 cols=50 name="<%=HLPInterfaceKey.INPUT_TEXTAREA_CASE_DESCRIPTION%>" id="<%=HLPInterfaceKey.INPUT_TEXTAREA_CASE_DESCRIPTION%>"><%=caseDescValue%></textarea>
            <%
            if(boForwardCase||boViewCaseDetailUserInCC)out.println(caseDescValue);
            %>
          </td>
        </tr>
        <%
        if(!boViewCaseDetailUserInCC)
        {
        %>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">User Comments</TD>
          <TD class=TableTextNote colSpan=4>
            <textarea rows=5 cols=50 name="<%=HLPInterfaceKey.INPUT_TEXTAREA_CASE_RECEIVER_COMMENT%>" id="<%=HLPInterfaceKey.INPUT_TEXTAREA_CASE_RECEIVER_COMMENT%>"></textarea>
          </td>
        </tr>
        <%
        }
        %>
       </table> 

    <br><br>
      <center>
      <%
        if(boCreateNewCase)
        {
        out.print("<INPUT class=button type=button value=\" Save \" name=\"open\" id=\"open\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_NEW_CASE+"';"+
                  "checkBeforeSubmit();\">");
        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
        }
        else if(boForwardCase)
        {
        out.print("<INPUT class=button type=button value=\" Forward \" name=\"forward\" id=\"forward\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+action+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
        }
        else if(boUpdateCaseDetail && caseStatusId.compareTo("2")!=0)
        {
        out.print("<INPUT class=button type=button value=\" Update \" name=\"update\" id=\"update\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_UPDATED_CASE+"';"+
                  "checkBeforeSubmitForUpdate();\">");

        out.print("<INPUT class=button type=button value=\" Add Info Element \" name=\"addInfoElement\" id=\"addInfoElement\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_ADD_CASE_INFO_ELEMENT+"';"+
                  "HLPform.submit();\">");          
        }
      %>
         </center>
      <%
       if(boUpdateCaseDetail || boViewCaseDetailUserInCC)
       {                  
          Vector caseInfoElementList = caseDTO.getCaseInfoElementList();
          if(caseInfoElementList != null)
          {
            if(caseInfoElementList.size() != 0)
            {
            %>
            <br><br>
            <b>Case Information Elements</b>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
              <TR class=TableHeader>
                <td>Title</td>
                <td>Description</td>
                <td>Created By</td>
                <td>Timestamp</td>
                <td>Date</td>
                <td>Type</td>
                <td>Contact Name</td>
                <td>Contact Information</td>
                <td>Edit</td>
              </tr>
              <%
              for(int r=0;r<caseInfoElementList.size();r++)
              {
              CaseInfoElementModel caseInfoElementModel = (CaseInfoElementModel)caseInfoElementList.get(r);
              String caseInfoElementId = caseInfoElementModel.getCaseInfoElementId();       
              String caseInfoElementUserId = caseInfoElementModel.getUserId();                        
              String caseInfoElementUserFullName = caseInfoElementModel.getUserFullName();
              String caseInfoElementUserEmail = caseInfoElementModel.getUserEmail();
              Timestamp caseInfoElementTimestamp = caseInfoElementModel.getCaseInfoElementTimestamp();
              Date caseInfoElementDate = caseInfoElementModel.getCaseInfoElementDate();         
              String caseInfoElementTypeId = caseInfoElementModel.getCaseInfoElementTypeId();      
              String caseInfoElementTypeName = caseInfoElementModel.getCaseInfoElementTypeName();      								 
              String caseInfoElementTitle = caseInfoElementModel.getCaseInfoElementTitle();        
              String caseInfoElementDescription = caseInfoElementModel.getCaseInfoElementDescription(); 
              if(caseInfoElementDescription == null)caseInfoElementDescription = "";
              String contactName = caseInfoElementModel.getContactName();
              String contactInfo  = caseInfoElementModel.getContactInfo();
              if(contactInfo == null)contactInfo = "";
              %>
              <TR class=TableTextNote>
                <td><%=caseInfoElementTitle%></td>
                <td><%=caseInfoElementDescription%></td>
                <td><%=caseInfoElementUserFullName%></td>
                <td><%=caseInfoElementTimestamp%></td>
                <td nowrap><%=caseInfoElementDate%></td>
                <td><%=caseInfoElementTypeName%></td>
                <td><%=contactName%></td>
                <td><%=contactInfo%></td>
                <td>
                <%
                if(caseInfoElementUserId.compareTo(strUserID)==0 && !boViewCaseDetailUserInCC)
                {
                  out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_EDIT_CASE_INFO_ELEMENT+"';"+
                  "document.HLPform."+HLPInterfaceKey.INPUT_HIDDEN_CASE_INFO_ELEMENT_ID+".value = '"+caseInfoElementId+"';"+
                  "HLPform.submit();\">");
                }
                %>
                </td>
              </tr>
              <%
              }      
              %>
            </table>  
            <%
            }
          }
       }
      %>
</form>
</body>
</html>
