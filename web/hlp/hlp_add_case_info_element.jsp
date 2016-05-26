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
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(HLPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }

    function checkBeforeSubmit()
    {
      if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_TITLE%>.value") == "")
      {
        alert("Title must not be empty.");
        return;  
      }
      else if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_DATE%>.value") == "*")
      {
        alert("Date must not be empty.");
        return;  
      }
      else if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_SELECT_CASE_INFO_ELEMENT_TYPE_ID%>.value") == "")
      {
        alert("Type must not be empty.");
        return;  
      }
      else if(eval("document.HLPform.<%=HLPInterfaceKey.INPUT_TEXT_CONTACT_NAME%>.value") == "")
      {
        alert("Contact name must not be empty.");
        return;  
      }
      else
      {
        HLPform.submit();
      }
    }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strCaseId = (String)objDataHashMap.get(HLPInterfaceKey.INPUT_HIDDEN_CASE_ID);
  Vector vecCaseInfoElementTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

  String strPageHeader = (String)objDataHashMap.get(HLPInterfaceKey.PAGE_HEADER);
  String strNextAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);

  CaseInfoElementModel caseInfoElementModel = null;
  String caseInfoElementId = "";       
  String caseInfoElementUserId = "";
  String caseInfoElementUserFullName = "";
  String caseInfoElementUserEmail = "";
  Date caseInfoElementDate = null; 
  String strCaseInfoElementDate = "*";
  String caseInfoElementTypeId = "";      
  String caseInfoElementTypeName = "";      								 
  String caseInfoElementTitle = "";        
  String caseInfoElementDescription = ""; 
  String contactName = "";
  String contactInfo = "" ;
  String caseId = "" ;
  
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    caseInfoElementModel = (CaseInfoElementModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    if(caseInfoElementModel != null)
    {
      caseInfoElementId = caseInfoElementModel.getCaseInfoElementId();       
      caseInfoElementUserId = caseInfoElementModel.getUserId();                        
      caseInfoElementUserFullName = caseInfoElementModel.getUserFullName();
      caseInfoElementUserEmail = caseInfoElementModel.getUserEmail();
      caseInfoElementDate = caseInfoElementModel.getCaseInfoElementDate();         
      if(caseInfoElementDate != null)
      {
        strCaseInfoElementDate = (caseInfoElementDate.getMonth()+1)+"/"+caseInfoElementDate.getDate()+"/"+(caseInfoElementDate.getYear()+1900);
      }
      caseInfoElementTypeId = caseInfoElementModel.getCaseInfoElementTypeId();      
      caseInfoElementTypeName = caseInfoElementModel.getCaseInfoElementTypeName();      								 
      caseInfoElementTitle = caseInfoElementModel.getCaseInfoElementTitle();        
      caseInfoElementDescription = caseInfoElementModel.getCaseInfoElementDescription(); 
      if(caseInfoElementDescription == null)caseInfoElementDescription = "";
      contactName = caseInfoElementModel.getContactName();
      contactInfo  = caseInfoElementModel.getContactInfo();
      if(contactInfo == null)contactInfo = "";
    }
  }
%>
    <CENTER>
      <H2> <%=strPageHeader%> </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+strNextAction+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+"\""+
                  " value=\""+strCaseId+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_INFO_ELEMENT_ID+"\""+
                  " value=\""+caseInfoElementId+"\">");                
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1 align="center">
      <TR class=TableTextNote>
        <td>Title</td>
        <td><input type="text" name="<%=HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_TITLE%>" id="<%=HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_TITLE%>" value="<%=caseInfoElementTitle%>"></td>
      </tr>
      <TR  class=TableTextNote>
        <td>Date</td>
        <td><Script>drawCalender('<%=HLPInterfaceKey.INPUT_TEXT_CASE_INFO_ELEMENT_DATE%>',"<%=strCaseInfoElementDate%>");</script></td>
      </tr>
      <TR  class=TableTextNote>
        <td>Type</td>
        <td>
        <select name='<%=HLPInterfaceKey.INPUT_SELECT_CASE_INFO_ELEMENT_TYPE_ID%>' id='<%=HLPInterfaceKey.INPUT_SELECT_CASE_INFO_ELEMENT_TYPE_ID%>'>
        <option value=""></option>
          <%
          for(int j=0;j<vecCaseInfoElementTypeList.size();j++)
          {
            CaseInfoElementTypeModel caseInfoElementTypeModel = (CaseInfoElementTypeModel)vecCaseInfoElementTypeList.get(j);
            String strCaseInfoElementTypeId = caseInfoElementTypeModel.getCaseInfoElementTypeId();
            String strCaseInfoElementTypeName = caseInfoElementTypeModel.getCaseInfoElementTypeName();
            String caseInfoElementTypeSelected = "";
            if(strCaseInfoElementTypeId.compareTo(caseInfoElementTypeId)==0)
            {
              caseInfoElementTypeSelected = "selected";  
            }
            %>
            <option value="<%=strCaseInfoElementTypeId%>" <%=caseInfoElementTypeSelected%>><%=strCaseInfoElementTypeName%></option>  
            <%
          }
          %>
        </select>
        </td>
      </tr>
      <TR class=TableTextNote>
        <td>Description</td>
        <td><textarea rows=5 cols=50 name="<%=HLPInterfaceKey.INPUT_TEXTAREA_CASE_INFO_ELEMENT_DESCRIPTION%>" id="<%=HLPInterfaceKey.INPUT_TEXTAREA_CASE_INFO_ELEMENT_DESCRIPTION%>"><%=caseInfoElementDescription%></textarea></td>
      </tr>
      <TR class=TableTextNote>
        <td>Contact Name</td>
        <td><input type="text" name="<%=HLPInterfaceKey.INPUT_TEXT_CONTACT_NAME%>" id="<%=HLPInterfaceKey.INPUT_TEXT_CONTACT_NAME%>" value="<%=contactName%>"></td>
      </tr>
      <TR class=TableTextNote>
        <td>Contact Information</td>
        <td><textarea rows=5 cols=50 name="<%=HLPInterfaceKey.INPUT_TEXTAREA_CONTACT_INFORMATION%>" id="<%=HLPInterfaceKey.INPUT_TEXTAREA_CONTACT_INFORMATION%>"><%=contactInfo%></textarea></td>
      </tr>
     </table> 
     <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"checkBeforeSubmit();\">");
      %>
         </center>
  </form>
  </body>
</html>
