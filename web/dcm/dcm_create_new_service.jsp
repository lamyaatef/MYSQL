<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*" 
         import="com.mobinil.sds.core.system.dcm.service.model.*"
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
      DCMform.submit(); 
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  ServiceModel serviceModel = null;
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  serviceModel = (ServiceModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);        

  String posServiceId = "";
  String posServiceName = "";
  String posServiceDesc = "";
  String serviceEligibilityTypeId = "";
  String serviceEligibilityTypeName = "";
  String posServiceStatusTypeId = "";
  String posServiceStatusTypeName = "";

  if(serviceModel != null)
  {
    posServiceId = serviceModel.getPosServiceId();
    posServiceName = serviceModel.getPosServiceName();
    posServiceDesc = serviceModel.getPosServiceDesc();
    if(posServiceDesc == null)posServiceDesc = "";  
    serviceEligibilityTypeId = serviceModel.getServiceEligibilityTypeId();
    serviceEligibilityTypeName = serviceModel.getServiceEligibilityTypeName();
    posServiceStatusTypeId = serviceModel.getPosServiceStatusTypeId();
    posServiceStatusTypeName = serviceModel.getPosServiceStatusTypeName();
  }
  
  GenericModel  serviceStatusModel;
  GenericModel  serviceEligibilityModel;
  Vector servicesStatusTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  Vector servicesEligibilityTypeList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);            
%>   
    <CENTER>
      <H2> Service Details </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");    

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_POS_SERVICE_ID+"\""+
                  " value=\""+posServiceId+"\">");                     
%> 
      
      <TABLE style="WIDTH: 746px; BORDER-COLLAPSE: collapse; HEIGHT: 91px" cellSpacing=2 cellPadding=1 width="746" border=1>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Service Name</TD>
          <TD class=TableTextNote colSpan=4><INPUT type='text' name="<%=DCMInterfaceKey.INPUT_TEXT_POS_SERVICE_NAME%>" id="<%=DCMInterfaceKey.INPUT_TEXT_POS_SERVICE_NAME%>" value="<%=posServiceName%>"></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Description</TD>
          <TD class=TableTextNote colSpan=4><TEXTAREA name="<%=DCMInterfaceKey.INPUT_TEXTAREA_POS_SERVICE_DESC%>" id="<%=DCMInterfaceKey.INPUT_TEXTAREA_POS_SERVICE_DESC%>" cols=50 rows=5><%=posServiceDesc%></TEXTAREA></td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Status</TD>
          <TD class=TableTextNote colSpan=4>
          <select name='<%=DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID%>' id='<%=DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_STATUS_TYPE_ID%>'>
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
          </td>
        </tr>
        <TR class=TableTextNote>
          <TD class=TableTextNote width=\"20%\">Eligibility</TD>
          <TD class=TableTextNote colSpan=4>
          <select name='<%=DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID%>' id='<%=DCMInterfaceKey.INPUT_SELECT_POS_SERVICE_ELIGIBILITY_TYPE_ID%>'>
          <option value=''></option>
          <%
          for(int j = 0 ; j < servicesEligibilityTypeList.size() ; j ++)
          {
            serviceEligibilityModel = (GenericModel)servicesEligibilityTypeList.get(j);
            String selectionState = "";
            if(serviceEligibilityModel.get_primary_key_value().compareTo(serviceEligibilityTypeId) == 0) selectionState = "selected";
            out.println("<option value='"+serviceEligibilityModel.get_primary_key_value()+"' "+selectionState+">" + serviceEligibilityModel.get_field_2_value() + "</option>");
          }
          %>
          </select>
          </td>
        </tr>
       </table> 

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SAVE_SERVICE+"';"+
                  "checkBeforeSubmit();\">");
      %>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
       
</form>
</body>
</html>
