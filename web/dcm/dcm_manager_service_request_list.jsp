<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.Timestamp"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.hlp.*"
         
         import="com.mobinil.sds.core.system.dcm.user.model.*" 
         import="com.mobinil.sds.core.system.dcm.service.model.*" 
         import="com.mobinil.sds.core.system.dcm.genericModel.*"  
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
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(DCMform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    
    function checkBeforeSubmit()
    {
      
    }

    function clearValues()
    {
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM%>.value = "*";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO%>.value = "*";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID%>.value = "";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector serviceRequestList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);    
  Vector serviceRequestStatusTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);    
  Vector serviceRequestWarningTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);    

  String searchPosCode = "";
  String searchPosName = "";
  String searchServiceName = "";
  String searchCaseTitle = "";
  String searchServiceRequestInitiateDateFrom = "*";
  String searchServiceRequestInitiateDateTo = "*";
  String searchServiceRequestStatusTypeId = "";
  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE))
  searchPosCode = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME))
  searchPosName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME))
  searchServiceName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE))
  searchCaseTitle = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM))
  searchServiceRequestInitiateDateFrom = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO))
  searchServiceRequestInitiateDateTo = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID))
  searchServiceRequestStatusTypeId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID);
%>   
    <CENTER>
      <H2> Service Request List </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_SERVICE_REQUEST_ID+"\""+
                  " value=\""+"\">"); 

  out.println("<input type=\"hidden\" name=\""+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+"\""+
                  " value=\""+"\">");                 
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle><input value='<%=searchPosName%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle><input value='<%=searchPosCode%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>'></td>
        <td align=middle>Service Name</td>
        <td align=middle><input value='<%=searchServiceName%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME%>'></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Case Title</td>
        <td align=middle colspan=2><input value='<%=searchCaseTitle%>' type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_CASE_TITLE%>'></td>
        <td align=middle>Service Status Type</td>
        <td align=middle colspan=2>
        <%
          out.println("<select name='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID+"' id='"+DCMInterfaceKey.INPUT_SEARCH_SELECT_SERVICE_REQUEST_STATUS_TYPE_ID+"'>");                 
            out.println("<option value=''></option>");
            for(int j = 0 ; j < serviceRequestStatusTypeList.size() ; j ++){
              GenericModel statusModel = (GenericModel)serviceRequestStatusTypeList.get(j);
              String selectionState = "";
              if(statusModel.get_primary_key_value().compareTo(searchServiceRequestStatusTypeId) == 0) selectionState = "selected";
              out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
              }                          
            out.println("</select>");
          %>
        </td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Issuing Date From</td>
        <td align=middle colspan=2><Script>drawCalender('<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_FROM%>',"<%=searchServiceRequestInitiateDateFrom%>");</script></td>
        <td align=middle>Issuing Date To</td>
        <td align=middle colspan=2><Script>drawCalender('<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_SERVICE_REQUEST_INITIATE_DATE_TO%>',"<%=searchServiceRequestInitiateDateTo%>");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_MANAGER_SERVICE_REQUEST_LIST+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>
       <br><br>
      <%
      if(serviceRequestList.size()>0)
      {
      %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Service</TD>
          <TD>Case ID</TD>
          <TD>Case Name</TD>
          <TD>Issuing Date</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Warning</TD>
          <TD align='center'>Case Histroy</TD>
        </tr>
        <%
        for(int i=0;i<serviceRequestList.size();i++)
        {
        ServiceRequestModel serviceRequestModel = (ServiceRequestModel)serviceRequestList.get(i);
        String serviceRequestId = serviceRequestModel.getServiceRequestId();
        String caseId = serviceRequestModel.getCaseId();
        String caseTitle = serviceRequestModel.getCaseTitle();
        String caseDescription = serviceRequestModel.getCaseDescription();
        Timestamp initiateTimestamp = serviceRequestModel.getInitiateTimestamp();
        String posCode  = serviceRequestModel.getPosCode();
        String posId = serviceRequestModel.getPosId();
        String posName = serviceRequestModel.getPosName();
        String posServiceId = serviceRequestModel.getPosServiceId();
        String posServiceName  = serviceRequestModel.getPosServiceName();
        String posServiceDesc = serviceRequestModel.getPosServiceDesc();
        String serviceRequestStatusTypeId = serviceRequestModel.getServiceRequestStatusTypeId();
        String serviceRequestStatusTypeName = serviceRequestModel.getServiceRequestStatusTypeName();
        %>
        <TR class=TableTextNote>
          <TD><%=posName%></TD>
          <TD><%=posCode%></TD>
          <TD><%=posServiceName%></TD>
          <TD><%=caseId%></TD>
          <TD><%=caseTitle%></TD>
          <TD><%=initiateTimestamp%></TD>
          <TD align='center'><%=serviceRequestStatusTypeName%></TD>
          <TD align='center'>
            
          </TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Case History \" name=\"caseHistory\" id=\"caseHistory\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+HLPInterfaceKey.ACTION_HLP_VIEW_CASE_HISTORY+"';"+
                      "document.DCMform."+HLPInterfaceKey.INPUT_HIDDEN_CASE_ID+".value = '"+caseId+"';"+
                      "DCMform.submit();\">");
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
    <br><br>
      <center>
      <%
        //out.print("<INPUT class=button type=button value=\" Add Service Request \" name=\"bluecopyentry\" id=\"bluecopyentry\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SALES_AGENT_CREATE_SERVICE_REQUEST+"';"+
        //          "DCMform.submit();\">");

        //out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
        //          "\">");          
      %>
      </center>
       
</form>
</body>
</html>
