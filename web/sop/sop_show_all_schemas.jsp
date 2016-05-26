<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
%>
         
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function setSearchValues(schemaCode,schemaName,createDateFrom,createDateTo,startDateFrom,startDateTo,endDateFrom,endDateTo,schemaStatus)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE%>").value = schemaCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME%>").value = schemaName;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM%>").value = createDateFrom;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO%>").value = createDateTo;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM%>").value = startDateFrom;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO%>").value = startDateTo;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM%>").value = endDateFrom;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO%>").value = endDateTo;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS%>").value = schemaStatus;
        }
        function Toggle(item) 
        {
          obj=document.getElementById(item);
          if (obj!=null) 
          {
            visible=(obj.style.display!="none")
            key=document.getElementById("x"+item);
            if (visible) 
            {
              obj.style.display="none";
              key.innerHTML="<img src='../resources/img/plus.gif' border='0'>";
            } 
            else 
            {
              obj.style.display="block";
              key.innerHTML="<img src='../resources/img/minus.gif' border='0'>";
            }
          }
        }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    String strErrorMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+strErrorMsg+"');</script>");
  }

  String searchSchemaCode = "";
  String searchSchemaName = "";
  String searchCreateDateFrom = "*";
  String searchCreateDateTo = "*";
  String searchStartDateFrom = "*";
  String searchStartDateTo = "*";
  String searchEndDateFrom = "*";
  String searchEndDateTo = "*";
  String searchSchemaStatus = "";
  
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE)){searchSchemaCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME)){searchSchemaName = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM)){searchCreateDateFrom = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO)){searchCreateDateTo = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM)){searchStartDateFrom = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO)){searchStartDateTo = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM)){searchEndDateFrom = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO)){searchEndDateTo = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS)){searchSchemaStatus = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS);}

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String activeSchemaExists = (String)objDataHashMap.get(SOPInterfaceKey.ACTIVE_SCHEMA_EXISTS);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  String channelName = (String)objDataHashMap.get(SOPInterfaceKey.PAGE_HEADER);
  Vector vecSchemaList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  Vector vecSchemaStatusList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
%>   
    <CENTER>
      <H2> Schema List for <%=channelName %> </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID+"\""+
                  " value=\""+"\">");                
                  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_NEXT_STATUS_ID+"\""+
                  " value=\""+"\">");     

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
%>    
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Schema Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_CODE%>'></td>
        <td align=middle>Schema Name</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_SCHEMA_NAME%>'></td>
        <TD align=middle>Status</TD>
        <td align=middle>
          <select name='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS%>' id='<%=SOPInterfaceKey.INPUT_SEARCH_SELECT_SCHEMA_STATUS%>'>
          <option value=''></option>
          <%
          for (int j=0; j<vecSchemaStatusList.size(); j++)
          {
              SchemaStatusModel schemaStatusModel = (SchemaStatusModel) vecSchemaStatusList.get(j); 
              String schemaStatusIdX = schemaStatusModel.getSchemaStatusId();
              String schemaStatusNameX = schemaStatusModel.getSchemaStatusName();
          %>
              <option value='<%=schemaStatusIdX%>'><%=schemaStatusNameX%></option>    
          <%
          }
          %>
          </select>
        </td>
      </TR>
    <table>  
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td><A id="xsearchtable" href="javascript:Toggle('searchtable');"><img src='../resources/img/plus.gif' border='0'></A></td>                               
        <td align='left' colspan=4>Advanced Search</td>
      </tr>
    </table>  
      <div style="display:none" id="searchtable" name="searchtable">
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableTextNote>  
        <td align=middle>Creation Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_FROM%>',"*");</script></td>
        <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_CREATION_DATE_TO%>',"*");</script></td>
      </tr>
      <tr class=TableTextNote>
        <TD align=middle>Start Date</TD>
        <td align=middle>From</td>
        <TD align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_FROM%>',"*");</script></TD>
        <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_START_DATE_TO%>',"*");</script></td>
      </tr>  
      <tr class=TableTextNote>
        <TD align=middle>End Date</TD>
        <td align=middle>From</td>
        <TD align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_FROM%>',"*");</script></TD>
        <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_END_DATE_TO%>',"*");</script></td>
      </tr>
     </table> 
      </div>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr>
        <td align='center' colspan=5>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchschema\" id=\"searchschema\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_SCHEMA+"';"+
                  "SOPform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','*','*','*','*','*','*','');\">");          
        %>
        </td>
      </tr>
      </table>    
<br><br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <%
      if(vecSchemaList.size()!=0)
      {
      %>
      <TR class=TableHeader>
        <td width="5%" align=middle>Schema Code</td>
        <td width="10%" align=middle>Schema Name</td>
        <td width="35%" align=middle>Description</td>
        <td width="10%" align=middle>Creation Date</td>
        <TD width="10%" align=middle>Start Date</TD>
        <TD width="10%" align=middle>End Date</TD>
        <TD width="10%" align=middle>Status</TD>
        <TD width="5%" align=middle>Change Status</TD>
        <TD width="5%" align=middle>View Products</TD>
      </TR>
<%
        }
        boolean changeStatusFlag = false;
        for (int i=0; i<vecSchemaList.size(); i++)
        {
            SchemaModel schemaModel = (SchemaModel) vecSchemaList.get(i); 
            String schemaId = schemaModel.getSchemaId();
            String schemaCode = schemaModel.getSchemaCode();
            String schemaName = schemaModel.getSchemaName();
            if(schemaName == null){schemaName = "";}
            String schemaDescription = schemaModel.getSchemaDescription();
            if(schemaDescription == null){schemaDescription = "";}
            String creationDate = schemaModel.getCreationDate();
            creationDate = creationDate.substring(0,10);
            String startDate = schemaModel.getStartDate();
            if(startDate == null){startDate = "";}
            else{startDate = startDate.substring(0,10);}
            String endDate = schemaModel.getEndDate();
            if(endDate == null){endDate = "";}
            else{endDate = endDate.substring(0,10);}
            String lastSchemaStatusLogId = schemaModel.getLastSchemaStatusLogId();
            String schemaStatusId = schemaModel.getSchemaStatusId();
            if(schemaStatusId.compareTo("1")==0)
            {
              changeStatusFlag = true;
            }
            String schemaStatusName = schemaModel.getSchemaStatusName();
%>
      <TR class=<%=InterfaceKey.STYLE[i%2]%>>
        <td width="5%" nowrap align=middle><%=schemaCode%></td>
        <td width="10%" align=middle><%=schemaName%></td>
        <td width="35%" align=middle><%=schemaDescription%></td>
        <td width="10%" nowrap align=middle><%=creationDate%></td>
        <TD width="10%" noWrap align=middle><%=startDate%></TD>
        <TD width="10%" noWrap align=middle><%=endDate%></TD>
        <TD width="10%" noWrap align=middle><%=schemaStatusName%>
        <br>
        <%
           if(schemaStatusId.compareTo("3")==0)
        {
            out.println("<input type='button' class='button' value=\" Delete \" name=\"delete\" id=\"delete\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_DELETE_SCHEMA_PRODUCT+"';"+
                          "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID+".value = '"+schemaId+"';SOPform.submit();\">");
        }
        %>
        </td>
<%
        if(schemaStatusId.compareTo("1")==0)
        {
            out.println("<TD width=\"5%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"inactive\" id=\"inactive\" value=\"Inactive\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CHANGE_SCHEMA_STATUS+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID+".value = '"+schemaId+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_NEXT_STATUS_ID+".value = '"+SOPInterfaceKey.CONST_SCHEMA_STATUS_INACTIVE+"';SOPform.submit();\"></TD>");
        }
        else if(schemaStatusId.compareTo("3")==0 && !changeStatusFlag && activeSchemaExists.compareTo("false")==0)
        {
            out.println("<TD width=\"5%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"active\" id=\"active\" value=\"Active\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CHANGE_SCHEMA_STATUS+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID+".value = '"+schemaId+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_NEXT_STATUS_ID+".value = '"+SOPInterfaceKey.CONST_SCHEMA_STATUS_ACTIVE+"';SOPform.submit();\"></TD>");
        }
        else
        {
            out.println("<td></td>");
        }
        out.println("<TD width=\"5%\" noWrap align=middle><input type=\"button\" class=\"button\" name=\"viewproducts\" id=\"viewproducts\" value=\"Products\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_VIEW_SCHEMA_PRODUCTS+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_ID+".value = '"+schemaId+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+".value = '"+strChannelId+"';"+
                      "document.SOPform."+SOPInterfaceKey.INPUT_HIDDEN_SCHEMA_NEXT_STATUS_ID+".value = '"+schemaStatusId+"';SOPform.submit();\"></TD>");
%>
      </TR>

<%
      }
%>
    </table>
    
    <br><br>
      <center>
      <%
        out.print("<INPUT class=button type=button value=\" Copy Schema \" name=\"addnew\" id=\"addnew\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_CREATE_NEW_SCHEMA+"';"+
                  "SOPform.submit();\">");

      %>
      </center>
      
</form>
<%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_SCHEMA)==0)
    {
      out.println("<script>setSearchValues('"+searchSchemaCode+"','"+searchSchemaName+"','"+searchCreateDateFrom+"','"+searchCreateDateTo+"','"+searchStartDateFrom+"','"+searchStartDateTo+"','"+searchEndDateFrom+"','"+searchEndDateTo+"','"+searchSchemaStatus+"');</script>");
      if(searchCreateDateFrom.compareTo("*")!= 0 || searchCreateDateTo.compareTo("*")!= 0 || searchStartDateFrom.compareTo("*")!= 0 || searchStartDateTo.compareTo("*")!= 0 || searchEndDateFrom.compareTo("*")!= 0)
      {
      out.println("<script>Toggle('searchtable');</script>");
      }
    }
  }
%>
</body>
</html>
