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
         import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
         import="com.mobinil.sds.core.system.dcm.branch.model.*"         
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
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(DCMform."+argOrder+",'dd/mm/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }
    
    function checkBeforeSubmit()
    {
      
    }
    function clearValues()
    {
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE%>.value='';
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM%>.value='*';
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO%>.value='*';
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM%>.value='*';
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO%>.value='*';
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME%>.value='';
      DCMform.<%=DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME%>.value='';      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  Vector statusVector = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_POS_BRANCH_STATUS_VECTOR);
  GenericModel statusModel = new GenericModel();
  Vector branchesSearchResult = (Vector) objDataHashMap.get(DCMInterfaceKey.DCM_POS_BRANCHES_SEARCH_RESULT);
   String branch_code = "";
    String branch_end_date_from = "*";
    String branch_end_date_to = "*";
    String branch_start_date_from = "*";
    String branch_start_date_to = "*";
    String branch_name = "";
    String main_branch_name = "";
    int branch_status = 0;
  if(((String)objDataHashMap.get(DCMInterfaceKey.DCM_SEARCH_FLAG)).equals("true"))
  {
     branch_code = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE);
     branch_end_date_from = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM);
     branch_end_date_to = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO);
     branch_start_date_from = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM);
     branch_start_date_to = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO);
     branch_name = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME);
     main_branch_name = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME);
     branch_status = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_STATUS));

  }
  
            
%>   
    <CENTER>
      <H2> POS Branches </H2>
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
        <td align=middle>Main Branch Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_MAIN_BRANCH_NAME%>' id='' value='<%=main_branch_name%>' ></td>
        <td align=middle>Status</td>
        <td align=middle><select name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_STATUS%>' id=''>
        <%
        out.print("<option value=0> </option>");
         for(int i = 0 ; i < statusVector.size() ; i ++){
            statusModel = (GenericModel)statusVector.get(i);
            String selectionState = "";
            if(Integer.parseInt(statusModel.get_primary_key_value()) == branch_status) selectionState = "selected";
            out.print("<option value='"+ statusModel.get_primary_key_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
          }
        %>     
        </select></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Code</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_CODE%>' id='' value='<%=branch_code%>'></td>
        <td align=middle>POS Name</td>
        <td align=middle><input type='text' name='<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_NAME%>' id='' value='<%=branch_name%>'></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Start Date From</td>
        <td align=middle><Script>drawCalender('<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_FROM%>',"<%=branch_start_date_from%>");</script></td>
        <td align=middle>POS Start Date To</td>
        <td align=middle><Script>drawCalender('<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_START_DATE_TO%>',"<%=branch_start_date_to%>");</script></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS End Date From</td>
        <td align=middle><Script>drawCalender('<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_FROM%>',"<%=branch_end_date_from%>");</script></td>
        <td align=middle>POS End Date To</td>
        <td align=middle><Script>drawCalender('<%=DCMInterfaceKey.CONTROL_TEXT_DCM_BRANCH_END_DATE_TO%>',"<%=branch_end_date_to%>");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=4>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+
                  InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_SEARCH_BRACHES+"'; DCMform.submit();"+
                  "\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick='clearValues();'>");          
        out.println("<input type='hidden' name='"+DCMInterfaceKey.DCM_BRANCHES_HIDDEN_ID+
                      "' value='"+"'>");
        %>
        </td>
      </tr>
      </table>

       <br><br>
       <%
        if(branchesSearchResult != null){%>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      
        <TR class=TableHeader>
        <TD>Branch Name</TD>
        <TD align='center'>Status</TD>
        <TD align='center'>Edit</TD>
        </tr>
<%
      BranchModel branchModel = null;
    
        for(int i = 0 ; i < branchesSearchResult.size() ; i++)
        {
          branchModel = (BranchModel)branchesSearchResult.get(i);        
          out.println("<TR class=TableTextNote>");
          out.println("<TD>"+branchModel.get_branch_name()+"</TD>");
          out.println("<TD align='center'><select name='"+DCMInterfaceKey.DCM_POS_SEARCH_BRANCH_STATUS+"_"+branchModel.get_branch_id()+
                        "' id='' onchange='document.DCMform.updatestatus.disabled=false'>");
          for(int j = 0 ; j < statusVector.size(); j++ ){
            statusModel = (GenericModel)statusVector.get(j);
            String selectionState = "";
            if(Integer.parseInt(statusModel.get_primary_key_value())==branchModel.get_branch_status_id()) selectionState = "selected";
            out.println("<option value='"+statusModel.get_primary_key_value()+"' "+selectionState+">"+statusModel.get_field_2_value());
          out.println("</option>");
          }
          out.println("</select></TD>");
          out.println("<td align='center' width=\"15%\">");
            out.println("<input type='hidden' name='"+DCMInterfaceKey.INPUT_HIDDEN_BRANCH_STATUS+"_"+branchModel.get_branch_id()+
                        "' value='"+branchModel.get_branch_status_id()+"'>");
            out.print("<INPUT class=button type=button value=\"Edit\" name=\"edit\"_"+i+" id=\"edit\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_USER_VIEW_POS_RELATIONSHIPS+"';"+
                      "document.DCMform."+DCMInterfaceKey.DCM_BRANCHES_HIDDEN_ID+".value='"+branchModel.get_branch_id()+
                      "' ;DCMform.submit();\">");


          out.println("</td>");
        out.println("</tr>");
        }
        }
 %>
      </table>  

    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Add New Main Branch \" name=\"addnewfunction\" id=\"addnewfunction\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_USER_VIEW_POS_RELATIONSHIPS+"';"+
                  "DCMform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Update Status \" name=\"updatestatus\" id=\"updatestatus\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+
                DCMInterfaceKey.ACTION_DCM_BRANCH_UPDATE_STATUS+"';DCMform.submit();"+
                  "\" DISABLED>");
      %>
      </center>
       
</form>
</body>
</html>
