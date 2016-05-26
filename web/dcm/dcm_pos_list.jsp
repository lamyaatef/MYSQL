<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.pos.dao.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.pos.model.*"
         import="com.mobinil.sds.core.system.dcm.pos.dao.*"
         import="com.mobinil.sds.core.system.dcm.city.model.*"

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
      
    }
    function clearSearchFields()
    {
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_NAME%>.value = '';
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>.value = '';
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value = '';
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER%>.value = '';
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_CITY%>.selectedIndex = 0 ;
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_POS_STATUS%>.selectedIndex = 0 ;
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
  
  String strUserID = (String)session.getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
  String userIDD = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String superAdminFlag = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);          
%>   
    <CENTER>
      <H2> POS List (sa)</H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  GenericModel  statusModel;
  CityModel cityModel;
  Vector cityVector   = new Vector();
  Vector statusVector = new Vector();
  statusVector        = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_STATUS);
  cityVector          = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_CITY);
  String POSName      = "";
  String POSCode      = "";
  String POSCommNum   = "";
  String POSPhone     = "";
  String POSStatus    = "";
  String POSCity      = "";
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_POS_NAME))
    POSName           = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_POS_NAME);  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_POS_PHONE))
    POSPhone          = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_POS_PHONE);  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_POS_COMMERCIAL_NUMBER))
    POSCommNum        = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_POS_COMMERCIAL_NUMBER);  
  if(objDataHashMap.containsKey(DCMInterfaceKey.INPUT_POS_CODE))
    POSCode           = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_POS_CODE);  
  if(objDataHashMap.containsKey(DCMInterfaceKey.POS_SEARCH_CITY))
    POSCity           = (String)objDataHashMap.get(DCMInterfaceKey.POS_SEARCH_CITY);  
  if(objDataHashMap.containsKey(DCMInterfaceKey.POS_SEARCH_STATUS))
    POSStatus           = (String)objDataHashMap.get(DCMInterfaceKey.POS_SEARCH_STATUS);  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");
                   
  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG+"\""+
                  " value=\""+superAdminFlag+"\">");
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_NAME%>' id='' value='<%=POSName%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>' id='' value='<%=POSCode%>'></td>
        <td align=middle>Phone</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>' id='' value='<%=POSPhone%>'></td>
      </tr>
      <input type='hidden' name='<%=DCMInterfaceKey.INPUT_HIDDEN_POS_ID%>' id='<%=DCMInterfaceKey.INPUT_HIDDEN_POS_ID%>' value=''>
      <TR class=TableTextNote>
        <td align=middle>Commercial N0.</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER%>' id='' value='<%=POSCommNum%>'></td>
        <td align=middle>City</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CITY%>' id=''>
        <option></option>
        <%
        
          for(int i = 0 ; i < cityVector.size() ; i ++){
            cityModel = (CityModel)cityVector.get(i);
            String selectionState = "";
            if(cityModel.get_city_code().compareTo(POSCity) == 0) selectionState = "selected";
            out.print("<option value ='"+cityModel.get_city_code()+"' "+selectionState+">" +cityModel.get_city_english()+  "</option>");
          }
        %>
        </select></td>
        <td align=middle>Status</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_STATUS%>' id=''>
        <option value=''></option>
        <%
           for(int i = 0 ; i < statusVector.size() ; i ++){
            statusModel = (GenericModel)statusVector.get(i);
            String selectionState = "";
            if(POSStatus.compareTo(statusModel.get_field_1_value()) == 0 ) selectionState = "selected";
            out.print("<option value='"+statusModel.get_field_1_value()+"' "+ selectionState +">" + statusModel.get_field_2_value() + "</option>");

            }
            
        %>
        </select></td>
      </tr>
      <tr>
      
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+
                  DCMInterfaceKey.ACTION_SEARCH_POS+"'; document.DCMform.submit();"+"\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearSearchFields()\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>

        <%
      
        Vector result = null;
        result = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_RESULT);
        if(result!=null){
%>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Phone</TD>
          <TD>E-mail</TD>
          <TD>City</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Edit</TD>
        </tr>
  
<%
        POSDetailModel resultModel;
        
        for(int i = 0 ; i < result.size() ; i++){
          resultModel = new POSDetailModel();
          resultModel = (POSDetailModel)result.get(i);
          int posStatusId = resultModel.getPosStatusID();
          int posID    = resultModel.getPosID();
          String strPosStatusId = posStatusId+"";
          out.println("<TR class=TableTextNote>");
          out.println("<TD>" + resultModel.getPosName() + "</TD>");
          out.println("<TD>" + resultModel.getPOSCode()+ "</TD>");
          out.println("<TD>" + "</TD>");
          out.println("<TD>" +  resultModel.getPosEmail() + "</TD>");
          out.println("<TD>" +  resultModel.getPosRegionName() + "</TD>");
          out.println("<TD align='center'>"+resultModel.getPosStatusName());

            out.print("<input type='hidden' name='"+DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+posID+"' id='"+DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+posID+"'"+
              "value=\""+resultModel.getPosStatusID()+"\">");
            
          out.println("</select></TD>");
          out.println("<TD align='center'>");
          
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit_"+i+"\" id=\"edit_"+i+"\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"
                      +DCMInterfaceKey.ACTION_DCM_SA_EDIT_POS+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value='"+posID+"';"+
                      "document.DCMform.submit();\">");
            out.print("</TD> </tr>");
          }
          }

          %>
                    

      </table>  
      

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New POS \" name=\"addnewpos\" id=\"addnewpos\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SA_CREATE_NEW_POS+"';"+
                  "DCMform.submit();\">");

      %>
      </center>
       
</form>
</body>
</html>
