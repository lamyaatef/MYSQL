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
         import="com.mobinil.sds.core.system.sa.users.dto.*"
         import="com.mobinil.sds.core.system.sa.users.model.*"
         import="com.mobinil.sds.core.system.sa.persons.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    </head>
  <body>
  <script>
    function checkBeforeSubmit()
    {
      var caseWarningName = document.getElementById("<%=HLPInterfaceKey.INPUT_TEXT_STATUS_WARNING_NAME%>").value;
      if(caseWarningName == "")
      {
        alert("Case warning can not be empty");
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

  Vector statusList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
  String caseWarningId = "";
  String caseWarningName = "";
  String caseStatusId = "";
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  {
    CaseStatusModel caseStatusWarningModel = (CaseStatusModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    caseWarningId = caseStatusWarningModel.getCaseStatusWarningId();
    caseWarningName = caseStatusWarningModel.getCaseStatusWarningName();
    caseStatusId = caseStatusWarningModel.getCaseStatusId();
  }
%>
    <CENTER>
      <H2> Case Warning </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID+"\""+
                  " value=\""+caseWarningId+"\">");                   
%>
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Warning</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=HLPInterfaceKey.INPUT_TEXT_STATUS_WARNING_NAME%>" id="<%=HLPInterfaceKey.INPUT_TEXT_STATUS_WARNING_NAME%>" value="<%=caseWarningName%>"></td>
        </tr>  
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Case Warning Status</TD>
          <TD class=TableTextNote colSpan=4>
            <select name="<%=HLPInterfaceKey.INPUT_SELECT_STATUS_ID%>" id="<%=HLPInterfaceKey.INPUT_SELECT_STATUS_ID%>">
            <%  
            for(int i=0;i<statusList.size();i++)
            {
              CaseStatusModel caseStatusModel = (CaseStatusModel)statusList.get(i);
              String caseStatusIdX = caseStatusModel.getCaseStatusId();
              String caseStatusNameX = caseStatusModel.getCaseStatusName();
              String statusSelected = "";
              if(caseStatusIdX.compareTo(caseStatusId)==0)
              {
                statusSelected = "selected";
              }
              %>
                <option value="<%=caseStatusIdX%>" <%=statusSelected%>><%=caseStatusNameX%></option>
              <%
            }
            %>
            </select>
          </td>
        </tr>
   </table>   

   <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_SAVE_CASE_WARNING+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");

      %>
      </center>
</form>
  </body>
</html>
