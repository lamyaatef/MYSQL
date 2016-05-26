<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
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
      var numberOfContracts = document.DCMform.<%=DCMInterfaceKey.INPUT_TEXT_SALES_FORM_CONTRACTS_COLLECTED%>.value;
      if(IsNumeric(numberOfContracts) && numberOfContracts.length > 0)
      {
        DCMform.submit();
      }
      else
      {
        alert('Number of contracts must be a number.');
      }
    }
</script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  ActualVisitModel actualVisitModel = (ActualVisitModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  

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
    posName = actualVisitModel.getPosName();
    actualVisitComment = actualVisitModel.getActualVisitComment();
    if(actualVisitComment == null)actualVisitComment = "";
  }

%>   
    <CENTER>
      <H2> Sales Form Entry From Actual Visit </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_ACTUAL_VISIT_ID+"\""+
                  " value=\""+actualVisitId+"\">");  

%> 

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class="TableHeader">
        <td>POS Code</td>
        <td>POS Name</td>
        <td>Visit Date</td>
        <td>Comments</td>
      </tr>
      <tr class="<%=InterfaceKey.STYLE[0]%>">
        <td><%=posCode%></td>
        <td><%=posName%></td>
        <td><%=strActualVisitDate%></td>
        <td><%=actualVisitComment%></td>
      </tr>
    </table>
    <br><br>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=0>
    <tr class="<%=InterfaceKey.STYLE[0]%>">
      <td width='40%'>Enter number of contracts collected : </td>
      <td><input type='text' name='<%=DCMInterfaceKey.INPUT_TEXT_SALES_FORM_CONTRACTS_COLLECTED%>' id='<%=DCMInterfaceKey.INPUT_TEXT_SALES_FORM_CONTRACTS_COLLECTED%>'></td>
    </tr>
    <tr class="<%=InterfaceKey.STYLE[0]%>">
      <td colspan=2 align='center'>
        <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SAVE_SALES_AGENT_BLUE_COPY_ENTRY_FORM+"';"+
                  "checkBeforeSubmit();\">");
        %>
      </td>
    <tr>
</table>
    
</form>
</body>
</html>    