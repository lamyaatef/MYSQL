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
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String superAdminFlag = (String)objDataHashMap.get(DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG);          
%>   
    <CENTER>
      <H2> POS Changes</H2>
    </CENTER>
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_POS_STATUS+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.CONTROL_HIDDEN_POS_SUPER_ADMIN_FLAG+"\""+
                  " value=\""+superAdminFlag+"\">");
                  
  Vector pendingModelVector = (Vector)objDataHashMap.get(DCMInterfaceKey.DCM_PENDING_POS);
  POSDetailModel currentAcceptedModel = (POSDetailModel)objDataHashMap.get(DCMInterfaceKey.DCM_ACCEPTED_POS);
  Vector statusVector = (Vector)objDataHashMap.get(DCMInterfaceKey.VECTOR_POS_STATUS);
  GenericModel statusModel = new GenericModel();

%> 
<%        if(currentAcceptedModel.getPosName() != null) {
%>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR><TD colspan='6'>Current Accepted POS</TD></TR>
        <TR class=TableHeader>
          <TD align='center'>POS Name</TD>
          <TD align='center'>POS Code</TD>
          <TD align='center'>Phone</TD>
          <TD align='center'>E-mail</TD>
          <TD align='center'>City</TD>
          <TD align='center'>Status</TD>
        </tr>
        <TR>
        <%}%>
        <%
        if(currentAcceptedModel.getPosName() != null) {
          out.println("<TD align='center'>"+currentAcceptedModel.getPosName()+"</TD>");
          out.println("<TD align='center'>"+currentAcceptedModel.getPOSCode()+"</TD>");
          String strPhoneNumber = "";
          Vector vecPhoneNumbers = currentAcceptedModel.getPosPhones();
          if(vecPhoneNumbers.size()>0)strPhoneNumber = (String)vecPhoneNumbers.get(0);
          out.println("<TD align='center'>"+strPhoneNumber+"</TD>");
          out.println("<TD align='center'>"+currentAcceptedModel.getPosEmail()+"</TD>");
          out.println("<TD align='center'>"+currentAcceptedModel.getPosCityName()+"</TD>");
          out.println("<TD align='center'>"+currentAcceptedModel.getPosStatusName()+"</TD>");
          }
          %>
        </tr>
      </table> 

      <br><br>

      <!--TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=4>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>POS Name</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Phone</td>
        <td align=middle><input type='text' name='' id=''></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Commercial No.</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Status</td>
        <td align=middle><select name='' id=''>
        <%
          for(int i = 0 ; i < statusVector.size() ; i ++){
            statusModel = (GenericModel)statusVector.get(i);
            out.print("<option>" + statusModel.get_field_2_value() + "</option>");
          }
        %>             
        </select></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Change Made By User</td>
        <td align=middle><input type='text' name='' id=''></td>
        <td align=middle>Change Type</td>
        <td align=middle><select name='' id=''></select></td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Change Date From</td>
        <td align=middle><Script>drawCalender('name1',"*");</script></td>
        <td align=middle>Change Date To</td>
        <td align=middle><Script>drawCalender('name2',"*");</script></td>
      </tr>
      <tr>
        <td align='center' colspan=4>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+"';"+
                  "\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" >");          
        %>
        </td>
      </tr>
      </table>

       

       <br><br-->

      <%if(pendingModelVector.size() != 0){%>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <tr><td colspan=10>Critical Changes</td></tr>
        <TR class=TableHeader>
          <TD align='center'>POS Name</TD>
          <TD align='center'>Phone</TD>
          <TD align='center'>E-mail</TD>
          <TD align='center'>City</TD>
          <TD align='center'>Made By SA</TD>
          <TD align='center'>Changes Timestamp</TD>
          <td align='center'>Action</td>
          <td align='center'>Changes Details</td>
          <td align='center' colspan=2>Action</td>
        </tr>
        <%}%>
        <TR class=TableTextNote>
        <%
        POSDetailModel pendingModel ;
        for(int i = 0 ; i < pendingModelVector.size() ; i++ ){
        out.println("<TR class=TableTextNote>");

        Utility.logger.debug("pending model size :  "+ pendingModelVector.size());
        pendingModel = (POSDetailModel)pendingModelVector.get(i);
                int posID = pendingModel.getPosID();

          out.println("<TD align='center'>"+pendingModel.getPosName()+"</TD>");
          out.println("<TD align='center'>"+pendingModel.getPosPhones()+"</TD>");
          out.println("<TD align='center'>"+pendingModel.getPosEmail()+"</TD>");
          out.println("<TD align='center'>"+pendingModel.getPosCityName()+"</TD>");
          /*out.println("<TD align='center'>");
          out.println("<select name='' id=''>");
        
          int statusIdX = pendingModel.getPOSStatusID();
          
          for(int j = 0 ; j < statusVector.size() ; j ++){
            statusModel = (GenericModel)statusVector.get(j);
            String selectionState = "";
            String primaryKeyValue = statusModel.get_primary_key_value();
            
            if(statusModel.get_field_2_value().compareTo(pendingModel.getPOSStatusName()) == 0) selectionState = "selected";
            if(((statusIdX == 1) && primaryKeyValue.compareTo("5")!=0 && primaryKeyValue.compareTo("6")!=0) || ((statusIdX == 2) && superAdminFlag.compareTo("0")==0 && (primaryKeyValue.compareTo("4")==0 || primaryKeyValue.compareTo("2")==0)) || ((statusIdX == 2) && superAdminFlag.compareTo("1")==0 && (primaryKeyValue.compareTo("4")==0 || primaryKeyValue.compareTo("2")==0 || primaryKeyValue.compareTo("6")==0)))
            out.println("<option value='"+statusModel.get_field_1_value()+"' "+selectionState+">" + statusModel.get_field_2_value() + "</option>");
            
            }          
          out.println("</select>");
          out.println("</TD>");*/
          out.println("<TD align='center'>"+"</TD>");
          out.println("<TD align='center'>"+pendingModel.getTimeStamp()+"</TD>");
          out.println("<td align='center'>");
          
          out.println("          </td>");
          out.println("          <TD align='center'>");
         
            out.print("<INPUT class=button type=button value=\" Changes Details \" name=\"viewchangesdetails\"_"+i+" id=\"viewchangesdetails\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_ADMIN_VIEW_POS_CHANGES_DETAILS+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value='"+posID+"';"+
                      "DCMform.submit();\">");
           
            out.print("          </TD>");

            String detailNextStatus = "2";
            if(superAdminFlag.compareTo("1")==0)detailNextStatus = "6";
            else if(superAdminFlag.compareTo("0")==0)detailNextStatus = "2";

            out.print("<TD>");
            out.print("<INPUT class=button type=button value=\"Accept\" name=\"accept\" id=\"accept\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_POS_SAVE_DETAIL+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value = '"+posID+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_POS_STATUS+".value = '"+detailNextStatus+"';"+
                      "DCMform.submit();\">");
            out.print("</TD>");
            
            out.print("<TD>");
            out.print("<INPUT class=button type=button value=\"Reject\" name=\"reject\" id=\"reject\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DCMInterfaceKey.ACTION_DCM_POS_SAVE_DETAIL+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_HIDDEN_POS_ID+".value='"+posID+"';"+
                      "document.DCMform."+DCMInterfaceKey.INPUT_POS_STATUS+".value = '3';"+
                      "DCMform.submit();\">");
            out.print("</TD>");
            
            out.print("        </tr>");
         }
          %>
      </table>  

      <br><br>
      <center>
          <input type="button" class="button" value=" Back " onclick="history.go(-1);">
      </center>
</form>
</body>
</html>
