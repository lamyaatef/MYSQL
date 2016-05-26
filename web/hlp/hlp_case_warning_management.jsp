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
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector caseWarningList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
%>
    <CENTER>
      <H2> Case Warning Management </H2>
    </CENTER>
<form name='HLPform' id='HLPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID+"\""+
                  " value=\""+"\">");                
%> 
     <%
      String strPreviousStatus = "";
      for(int f=0;f<caseWarningList.size();f++)
      {
        CaseStatusModel caseStatusWarningModel = (CaseStatusModel)caseWarningList.get(f);
        String strCaseWarningId = caseStatusWarningModel.getCaseStatusWarningId();
        String strCaseWarningName = caseStatusWarningModel.getCaseStatusWarningName();
        String strCaseStatusId = caseStatusWarningModel.getCaseStatusId();
        String strCaseStatusName = caseStatusWarningModel.getCaseStatusName();

        if(strCaseStatusName.compareTo(strPreviousStatus)!=0)
        {
          if(strPreviousStatus.compareTo("")!=0)
          {
            %>
            </table>
            <%
          }
          strPreviousStatus = strCaseStatusName;
          %>
            <br>
              <b>Status : <%=strPreviousStatus%></b>
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
             <TR class=TableHeader>
              <TD>Case Warning Name</TD>
              <TD align="center">Edit</TD>
              <!--TD align="center">Delete</TD-->
             </tr> 
            
          <%
        }
     %>
     <TR class=<%=InterfaceKey.STYLE[f%2]%>>
      <TD><%=strCaseWarningName%></TD>
      <TD align="center">
      <%
        out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_EDIT_CASE_WARNING+"';"+
                  "document.HLPform."+HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID+".value = '"+strCaseWarningId+"';"+
                  "HLPform.submit();\">");
      %>
      </TD>
      <!--TD align="center">
      <%
        out.print("<INPUT class=button type=button value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_DELETE_CASE_WARNING+"';"+
                  "document.HLPform."+HLPInterfaceKey.INPUT_SELECT_STATUS_WARNING_ID+".value = '"+strCaseWarningId+"';"+
                  "HLPform.submit();\">");
      %>
      </TD-->
     </tr>
     <%
      }
     %>
  </table>   

  <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Warning \" name=\"addnew\" id=\"addnew\" onclick=\"document.HLPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_ADD_CASE_WARNING+"';"+
                  "HLPform.submit();\">");

      %>
      </center>
</form>
  </body>
</html>
