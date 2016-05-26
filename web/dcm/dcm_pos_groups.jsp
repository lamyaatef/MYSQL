<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.dcm.*"
         import="com.mobinil.sds.core.system.dcm.genericModel.*"
         import="com.mobinil.sds.core.system.dcm.function.model.*"
         import="com.mobinil.sds.core.system.dcm.group.model.*"

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
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME%>.value="";
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS%>.value=0;
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME%>.value=0;
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME%>.value="";
      document.DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE%>.value="";
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  Vector groupStatusVector = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_GROUP_STATUS);
  GenericModel groupStatusModel = null;
  Vector groupFunctionVector = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_GROUP_FUNCTION);
  FunctionModel functionModel = null;
  Vector groupSearchResult = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_GROUP_SEARCH_RESULT);
  GroupModel groupModel = null;
  String groupName = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME);          
  String groupPOSName = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME);
  String groupPOSCode = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE);
  int groupStatusID = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS));
  int groupFunctionID = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME));
  if(groupName==null) groupName = "";
  if(groupPOSName==null) groupPOSName = "";
  if(groupPOSCode==null) groupPOSCode = "";
  if((String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS)==null) groupStatusID = 0;
  if((String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME)==null) groupFunctionID = 0;
            
%>   
    <CENTER>
      <H2> POS Groups </H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");             
                                    out.println("<input type='hidden' name='"+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+"' id='"+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+"' value=''>");
%> 
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Group Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_NAME%>' value='<%=groupName%>'></td>
        <td align=middle>Group Status</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS%>'>
        <option value='0'></option>
        <%
               
          for(int i = 0 ; i < groupStatusVector.size() ; i ++){
            groupStatusModel = (GenericModel)groupStatusVector.get(i);
            String selectionState = "";
            if(groupStatusID == Integer.parseInt(groupStatusModel.get_primary_key_value())) selectionState = "selected";
            out.print("<option value='"+groupStatusModel.get_primary_key_value()+"' "+selectionState+">" + groupStatusModel.get_field_2_value() + "</option>");
          }
        %>     
        </select></td>
        <td align=middle>Function Name</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_FUNCTION_NAME%>'>
        <option value='0'></option>
        <%
          for(int i = 0 ; i < groupFunctionVector.size() ; i ++)
          {
            functionModel = (FunctionModel)groupFunctionVector.get(i);
             String selectionState = "";
            if(groupFunctionID ==functionModel.get_function_id()) selectionState = "selected";
            out.print("<option value = '" + functionModel.get_function_id() + "' "+selectionState+">" + functionModel.get_function_name() + "</option>");
          }
        %>
        </select></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_NAME%>' value='<%=groupPOSName%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle colspan=3><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE%>' id='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_POS_CODE%>' value='<%=groupPOSCode%>'></td>
      </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_GROUP_SEARCH+"';"+
                  "DCMform.submit()\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"clearValues();\">");          
        %>
        </td>
      </tr>
      </table>

       <br><br>
         <%   if(groupSearchResult != null)
        {%>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <TD>Group Name</TD>
          <TD>Description</TD>
          <TD align='center'>Status</TD>
          <TD align='center'>Edit</TD>
          <TD align='center'>POSs</TD>
          <TD align='center'>Functions</TD>
        </tr>
        <%
       
       for(int j = 0 ; j < groupSearchResult.size() ; j++)
       {
       groupModel = new GroupModel();
       groupModel = (GroupModel)groupSearchResult.get(j);
        out.print("<TR class=TableTextNote>");
        Utility.logger.debug("cccccccc"+groupModel.getGroupName());


          out.print("<TD>"+groupModel.getGroupName()+"</TD>");
          out.print("<TD>"+groupModel.getGroupDescription()+"</TD>");
          out.print("<TD align='center'><select name='"+DCMInterfaceKey.CONTROL_TEXT_DCM_GROUP_STATUS+"_"+groupModel.getGroupID()+
                    "' id='' onchange='updatestatus.disabled=false'>");
          for(int i = 0 ; i < groupStatusVector.size() ; i ++){
            groupStatusModel = (GenericModel)groupStatusVector.get(i);
            String selectionState = "";
            if(groupModel.getGroupStatusID() == Integer.parseInt(groupStatusModel.get_primary_key_value())) selectionState = "selected";
            out.print("<option value='"+groupStatusModel.get_primary_key_value()+"' "+selectionState+">" + groupStatusModel.get_field_2_value() + "</option>");
          }
          out.println("</select></TD>");
          out.print("<TD align='center'>");
          
            out.print("<INPUT class=button type=button value=\" Edit \" name=\"edit"+"_"+groupModel.getGroupID()+"\" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_EDIT_POS_GROUP+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value='"+groupModel.getGroupID()+"';"+
                      "DCMform.submit();\">");

          out.print("</TD>");
          out.print("<TD align='center'>");
          
            out.print("<INPUT class=button type=button value=\" POSs \" name=\"pos"+"_"+groupModel.getGroupID()+"\" id=\"pos\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ASSIGN_POS_TO_POS_GROUP+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value='"+groupModel.getGroupID()+"';"+
                      "DCMform.submit();\">");

          out.println("<input type='hidden' name='"+DCMInterfaceKey.INPUT_HIDDEN_GROUP_STATUS+"_"+groupModel.getGroupID()+
                      "' value='"+groupModel.getGroupStatusID()+"'>") ;         
          out.print("</TD>");
          out.print("<TD align='center'>");
          
            out.print("<INPUT class=button type=button value=\" Functions \" name=\"functions"+"_"+groupModel.getGroupID()+"\" id=\"functions\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ASSIGN_FUNCTION_TO_POS_GROUP+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_GROUP_ID+".value='"+groupModel.getGroupID()+"';"+
                      "DCMform.submit();\">");

          
          out.print("</TD>");
        out.print("</tr>");
        }
       }
        %>
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Group \" name=\"addnewgroup\" id=\"addnewgroup\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_CREATE_NEW_POS_GROUP+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+
                  DCMInterfaceKey.ACTION_DCM_UPDATE_GROUP_STATUS+"'; DCMform.submit();"+
                  "\" DISABLED>");
      %>
      </center>
       
</form>
</body>
</html>
