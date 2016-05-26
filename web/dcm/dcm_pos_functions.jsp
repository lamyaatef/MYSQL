<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.function.model.*"

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
    function clearValues()
    {
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME%>.value="";
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS%>.value=0;
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector statusVector = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_FUNCTION_STATUS_VECTOR);
  Vector searchResult = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_FUNCTION_SEARCH_VECTOR); 
  String searchStatusStr =(String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS);
  String searchFunctionName = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME);
  int searchStatus = 0;
  if(searchStatusStr!=null)
    searchStatus  = Integer.parseInt(searchStatusStr);
  if(searchFunctionName == null)
    searchFunctionName = "";
    
    
    
%>   

    <CENTER>
      <H2> POS Functions </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
 
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=4>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Function Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_NAME%>' value='<%=searchFunctionName%>'></td>
        <td align=middle>Function Status</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS%>'>
        <option value='0'></option>
        <%
        GenericModel functionStatusModel = null; 
          for(int i = 0 ; i < statusVector.size() ; i ++){
            functionStatusModel = (GenericModel)statusVector.get(i);
            String selectionState = "";
            if(Integer.parseInt(functionStatusModel.get_primary_key_value())== searchStatus) selectionState = "selected";
            out.print("<option value='"+functionStatusModel.get_primary_key_value()+"' "+selectionState+">" + functionStatusModel.get_field_2_value() + "</option>");
          }
        %>             
        </select></td>
      </tr>
      <tr>
        <td align='center' colspan=4>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_FUNCTION+"';"+
        
                  "DCMform.submit();\" >");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearValues();\">");
         out.println("<input type='hidden' name='"+DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID+"' value=''>");
        %>
        </td>
      </tr>
      </table>

       <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
        <%      if(searchResult != null){%>
          <TD>Function Name</TD>
          <TD>Description</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Edit</TD>
        </tr>
        
<%

      for(int i = 0 ; i < searchResult.size(); i++ ){
      FunctionModel functionModel = (FunctionModel)searchResult.get(i);
          out.print("<TR class=TableTextNote>");
          out.print("<TD>"+functionModel.get_function_name()+"</TD>");
          out.print("<TD>"+functionModel.get_function_desc()+"</TD>");
          out.print("<TD align='center'><select name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_FUNCTION_STATUS+"_"+functionModel.get_function_id()+"' id=''"+
                    "onchange=\" document.DCMform."+DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID+".value='"+
                    functionModel.get_function_id()+"';document.updatestatus.disabled=false;\">");
            functionStatusModel = null; 
          for(int j = 0 ; j < statusVector.size() ; j++){
            functionStatusModel = (GenericModel)statusVector.get(j);
            String selectionState="";
             if(functionStatusModel.get_field_2_value().compareTo(functionModel.get_function_status_name()) == 0) selectionState = "selected";
            out.print("<option value='"+functionStatusModel.get_primary_key_value()+"' "+selectionState+">" + functionStatusModel.get_field_2_value() + "</option>");
          }
          
         
          out.println("</select></TD>");
          out.print("<TD align='center'>");
          Utility.logger.debug(functionModel.get_function_id()+"");
          out.println("<input type=\"hidden\" name='"+DCMInterfaceKey.INPUT_HIDDEN_DCM_FUNCTION_STATUS+"_"+functionModel.get_function_id()+"' value='"+functionModel.get_function_status_id()+"'>");
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit_"+functionModel.get_function_id()+"\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_EDIT_POS_FUNCTION+"';"+
                      "document.DCMform."+DCMInterfaceKey.DCM_FUNCTION_HIDDEN_ID+".value='"+functionModel.get_function_id()+"';"+
                      "DCMform.submit();\">");
                      }
                      }
%>

          </TD>
        </tr>
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Function \" name=\"addnewfunction\" id=\"addnewfunction\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_POS_FUNCTION+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+
                    DCMInterfaceKey.ACTION_DCM_UPDATE_FUNCTION_STATUS+"';"+
                  "DCMform.submit();"+
                  "\">");
      %>
      </center>
       
</form>
</body>
</html>
