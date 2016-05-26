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

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String superAdminFlag = "0";    
  if(objDataHashMap.containsKey(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG))
  superAdminFlag = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);
  
  GenericModel  statusModel;
  CityModel cityModel;
  Vector statusVector = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_STATUS);
  Vector cityVector = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_CITY);
 
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
 
  
%>   
    <CENTER>
      <H2> POS List (user)</H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%

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
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_NAME%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_POS_NAME%>' value='<%=POSName%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CODE%>' value='<%=POSCode%>'></td>
        <td align=middle>Phone</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_POS_PHONE%>' value='<%=POSPhone%>'></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Commercial No.</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_POS_COMMERCIAL_NUMBER%>' value='<%=POSCommNum%>'></td>
        <input type='hidden' name='<%=DCMInterfaceKey.INPUT_HIDDEN_POS_ID%>' id='<%=DCMInterfaceKey.INPUT_HIDDEN_POS_ID%>' value=''>
        <td align=middle>City</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CITY%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_POS_CITY%>'>
                <option></option>
        <%
          for(int i = 0 ; i < cityVector.size() ; i++){
            cityModel = (CityModel)cityVector.get(i);
            String selectionState = "";
            if(cityModel.get_city_code().compareTo(POSCity) == 0) selectionState = "selected";
            out.print("<option value ='"+cityModel.get_city_code()+"' "+selectionState+">" +cityModel.get_city_english()+  "</option>");
          }
        %>        
        </select></td>
        <td align=middle>Status</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_POS_STATUS%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_POS_STATUS%>'>
        <option></option>
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
                            DCMInterfaceKey.ACTION_USER_SEARCH_POS+"';document.DCMform.submit();"+
                  "\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearSearchFields();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>

          <%
          Vector result = new Vector();

          result = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_RESULT);
          if(result != null){
          %>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>POS Name</TD>
          <TD>POS Code</TD>
          <TD>Phone</TD>
          <TD>E-mail</TD>
          <TD>City</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Warning</TD>
          <TD align='center'>Edit</TD>
          <TD align='center'>Changes Made By SA</TD>
        </tr>
          
          <%
           Vector warningVector = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_POS_WARNING_VECTOR);
          POSDetailModel resultModel;
          for(int i = 0 ; i < result.size() ; i++){
                  out.println("<TR class=TableTextNote>");
          resultModel = new POSDetailModel();
          resultModel = (POSDetailModel)result.get(i);
                    int posID    = resultModel.getPosID();
          out.println("<TD>" + resultModel.getPosName() + "</TD>");
          out.println("<TD>" + resultModel.getPOSCode() + "</TD>");
          out.println("<TD>" + resultModel.getPosPhones() + "</TD>");
          out.println("<TD>" + resultModel.getPosEmail() + "</TD>");
          out.println("<TD>" + resultModel.getPosRegionName() + "</TD>");

          out.println("<TD align='left'>");
            
           int statusIdX = resultModel.getPosStatusID();
           if(statusIdX == 3 || statusIdX == 4 || statusIdX == 5 || (superAdminFlag.compareTo("0")==0 && statusIdX == 6))
           {
              out.println("<input type='hidden' name='"+DCMInterfaceKey.INPUT_DCM_POS_LIST_STATUS+"_"+posID+"' id='"+DCMInterfaceKey.INPUT_DCM_POS_LIST_STATUS+"_"+posID+"' value='"+resultModel.getPosStatusID()+"'>");
              out.println(resultModel.getPosStatusName());   
           }
           else
           {
               out.println("<select name='"+DCMInterfaceKey.INPUT_DCM_POS_LIST_STATUS+"_"+posID+"' id='"+DCMInterfaceKey.INPUT_DCM_POS_LIST_STATUS+"_"+posID+"' onchange="+
                                "'document.DCMform.updatestatus.disabled=false; "+
                                "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value=\""+posID+"\";' >");                 
              for(int j = 0 ; j < statusVector.size() ; j ++){
                statusModel = (GenericModel)statusVector.get(j);
                String selectionState = "";
                String primaryKeyValue = statusModel.get_primary_key_value();
                if(statusModel.get_field_2_value().compareTo(resultModel.getPosStatusName()) == 0) selectionState = "selected";
                if(((statusIdX == 1) && primaryKeyValue.compareTo("5")!=0 && primaryKeyValue.compareTo("6")!=0) || ((statusIdX == 2) && superAdminFlag.compareTo("0")==0 && (primaryKeyValue.compareTo("4")==0 || primaryKeyValue.compareTo("2")==0)) || ((statusIdX == 2) && superAdminFlag.compareTo("1")==0 && (primaryKeyValue.compareTo("4")==0 || primaryKeyValue.compareTo("2")==0 || primaryKeyValue.compareTo("6")==0)) || 
                ((statusIdX == 6) && (primaryKeyValue.compareTo("4")==0 || primaryKeyValue.compareTo("6")==0)))
                out.println("<option value='"+primaryKeyValue+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
                }                          
              out.println("</select>");
            }
          out.println("</TD>");
          
          out.println("<TD align='center' width=\"5%\"><select name='"+DCMInterfaceKey.CONTROL_TEXT_POS_WARNING+"_"+posID+"' "+
                      "id='"+DCMInterfaceKey.CONTROL_TEXT_POS_WARNING+"'><option value=''></option>");
          for(int h =0 ; warningVector != null && h < warningVector.size() ; h++){
          GenericModel warningModel = (GenericModel)warningVector.get(h);
          out.println("<option value='"+warningModel.get_primary_key_value()+"'>"+warningModel.get_field_2_value()+"</option>");
          }
          out.println("</select></TD>");
          out.println("<TD align='center' width=\"5%\">");
          out.println("<input type='hidden' name='"+DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+posID+"' id='"+DCMInterfaceKey.INPUT_HIDDEN_DCM_POS_CURRENT_STATUS+"_"+posID+"'"+
              "value=\""+resultModel.getPosStatusID()+"\">");                   
          out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit_"+i+"\" id=\"edit_"+i+"\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"
                      +DCMInterfaceKey.ACTION_DCM_SA_EDIT_POS+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value='"+posID+"';"+
                      "document.DCMform.submit();\">");
          
          out.println("</TD>");
          out.println("<TD align='center' width=\"5%\">");
          
          out.print("<INPUT class=button type=button value=\"Changes\" name=\"changes\" id=\"changes\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_USER_VIEW_POS_DETAILS+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value='"+posID+"';"+
                      "DCMform.submit();\">");
          out.println(" </TD></tr>");
          }
          }
         
          %>
         
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New POS \" name=\"addnewpos\" id=\"addnewpos\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_USER_CREATE_NEW_POS+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+
                    DCMInterfaceKey.ACTION_DCM_USER_UPDATE_POS_STATUS+"'; DCMform.submit();\" disabled>");      %>
      </center>
       
</form>
</body>
</html>
