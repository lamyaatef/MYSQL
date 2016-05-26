<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*" 
         import="com.mobinil.sds.core.system.dcm.service.model.*"
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
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>.value = "";
      document.DCMform.<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>.value = "";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  GenericModel  serviceStatusModel;
  GenericModel  serviceEligibilityModel;
  Vector servicesList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);        
  Vector servicesStatusTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector servicesEligibilityTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);

  String searchServiceName = "";
  String searchServiceStatusTypeId = "";
  String searchServiceEligibilityTypeId = "";
  String searchPosCode = "";
  String searchPosName = "";

  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME))
  searchServiceName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID))
  searchServiceStatusTypeId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID))
  searchServiceEligibilityTypeId = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE))
  searchPosCode = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE);
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME))
  searchPosName = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME);
%>   
    <CENTER>
      <H2> Services Management </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+"\""+
                  " value=\""+"\">");                
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Service Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_SERVICE_NAME%>' value='<%=searchServiceName%>'></td>
        <td align=middle>Service Status</td>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_STATUS_TYPE_ID%>'>
          <option value=''></option>
          <%
          for(int j = 0 ; j < servicesStatusTypeList.size() ; j ++)
          {
            serviceStatusModel = (GenericModel)servicesStatusTypeList.get(j);
            String selectionState = "";
            if(serviceStatusModel.get_primary_key_value().compareTo(searchServiceStatusTypeId) == 0) selectionState = "selected";
            out.println("<option value='"+serviceStatusModel.get_primary_key_value()+"' "+selectionState+">" + serviceStatusModel.get_field_2_value() + "</option>");
          }
          %>
          </select>
        </td>
        <td align=middle>Service Eligibility</td>
        <td align=middle>
          <select name='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID%>'>
          <option value=''></option>
          <%
          for(int j = 0 ; j < servicesEligibilityTypeList.size() ; j ++)
          {
            serviceEligibilityModel = (GenericModel)servicesEligibilityTypeList.get(j);
            String selectionState = "";
            if(serviceEligibilityModel.get_primary_key_value().compareTo(searchServiceEligibilityTypeId) == 0) selectionState = "selected";
            out.println("<option value='"+serviceEligibilityModel.get_primary_key_value()+"' "+selectionState+">" + serviceEligibilityModel.get_field_2_value() + "</option>");
          }
          %>
          </select>
        </td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle colspan=2><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_NAME%>' value='<%=searchPosName%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle colspan=2><input type='text' name='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>' id='<%=DCMInterfaceKey.INPUT_SEARCH_TEXT_POS_CODE%>' value='<%=searchPosCode%>'></td>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_MANAGER_SERVICES_LIST+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
       <%
        if(servicesList.size()>0)
        {
       %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>Service</TD>
          <TD>Description</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Eligibity</TD>
          <TD align='center'>POSs</TD>
          <TD align='center'>Edit</TD>
        </tr>
        <%
        for(int i=0;i<servicesList.size();i++)
        {
        ServiceModel serviceModel = (ServiceModel)servicesList.get(i);
        String posServiceId = serviceModel.getPosServiceId();
        String posServiceName = serviceModel.getPosServiceName();
        String posServiceDesc = serviceModel.getPosServiceDesc();
        if(posServiceDesc == null)posServiceDesc = "";
        String serviceEligibilityTypeId = serviceModel.getServiceEligibilityTypeId();
        String serviceEligibilityTypeName = serviceModel.getServiceEligibilityTypeName();
        String posServiceStatusTypeId = serviceModel.getPosServiceStatusTypeId();
        String posServiceStatusTypeName = serviceModel.getPosServiceStatusTypeName();
        %>
        <TR class=TableTextNote>
          <TD><%=posServiceName%></TD>
          <TD><%=posServiceDesc%></TD>
          <TD align='center'>
            <INPUT type='hidden' name="<%=DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_STATUS_TYPE_ID%>_<%=posServiceId%>" id="<%=DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_STATUS_TYPE_ID%>_<%=posServiceId%>" value="<%=posServiceStatusTypeId%>">
            <select name='<%=DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID%>_<%=posServiceId%>' id='<%=DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID%>_<%=posServiceId%>'>
            <option value=''></option>
            <%
            for(int j = 0 ; j < servicesStatusTypeList.size() ; j ++)
            {
              serviceStatusModel = (GenericModel)servicesStatusTypeList.get(j);
              String selectionState = "";
              if(serviceStatusModel.get_primary_key_value().compareTo(posServiceStatusTypeId) == 0) selectionState = "selected";
              out.println("<option value='"+serviceStatusModel.get_primary_key_value()+"' "+selectionState+">" + serviceStatusModel.get_field_2_value() + "</option>");
            }
            %>
            </select>
          </TD>
          <TD align='center'><%=serviceEligibilityTypeName%></td>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" POSs \" name=\"pos\" id=\"pos\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ASSIGN_POS_TO_SERVICE+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+".value = '"+posServiceId+"';"+
                      "DCMform.submit();\">");

          %>
          </TD>
          <TD align='center'>
          <%
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_EDIT_SERVICE+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+".value = '"+posServiceId+"';"+
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
        out.print("<INPUT class=button type=button value=\" Create New Service \" name=\"cretenewservice\" id=\"cretenewservice\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_SERVICE+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_UPDATE_SERVICES_STATUS+"';"+
                  "DCMform.submit();\">");          
      %>
      </center>
       
</form>
</body>
</html>
