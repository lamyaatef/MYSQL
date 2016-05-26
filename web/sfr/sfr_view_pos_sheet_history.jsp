<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.*"
         import="java.sql.*"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"   
%>
<%
 String appName = request.getContextPath();
 %>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SFRform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

       function setSearchValues(sheetSerial,posAgentId,sheetStatusTypeId,changeDateFrom,changeDateTo)
        {
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>").value = sheetSerial;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID%>").value = posAgentId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID%>").value = sheetStatusTypeId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM%>").value = changeDateFrom;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO%>").value = changeDateTo;
        }
        
        function checkBeforeSubmit()
        {
          var sheetSerial = document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>").value;
          if(sheetSerial == "")
          {
            alert("Sheet Serial can not be empty..");
          }
          else
          {
            SFRform.submit();
          }  
        }
</script>    
<body>
    <CENTER>
      <H2> POS Sheet History </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
 /* String strSearchSheetSerial = "";
  String strSearchPOSAgentId = "";
  String strSearchSheetStatusTypeId = "";
  String strSearchSheetChangeDateFrom = "*";
  String strSearchSheetChangeDateTo = "*";
  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL)){strSearchSheetSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID)){strSearchPOSAgentId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID)){strSearchSheetStatusTypeId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM)){strSearchSheetChangeDateFrom = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO)){strSearchSheetChangeDateTo = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO);}  
*/
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+
                  " value=\""+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+"\""+
                  " value=\""+"\">");                 
  
  Vector sheetStatus = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);                  
  Vector sheetStatusTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION); 
  Vector posAgents = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);                 
%>  
<!--TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1-->
      <!--tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote-->
      <center>
      <table  cellspacing="2" cellpadding="1" border="0" width="35%">
      <tr>
        <td align=middle>Sheet Serial:</td>
        <td align=middle><input type='text' name='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>' id='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>'></td>
        <!--td align=middle>POS Agent</td>
        <td align=middle>
          <select name='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID%>' id='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID%>'>
          <option value=""></option-->
            <%  
            for(int k=0;k<posAgents.size();k++)
            {
              POSAgentModel posAgentModel = (POSAgentModel)posAgents.get(k);
              String posAgentIdK = posAgentModel.getSecondPosId();
              String posAgentCodeK = posAgentModel.getSecondPosCode();
              %>
              <!--option value="<%=posAgentIdK%>"><%=posAgentCodeK%></option-->
              <%
            }
            %>
          <!--/select>
        </td>
        <td align=middle>Sheet Status</td>
        <td align=middle>
          <select name='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID%>' id='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID%>'>
            <option value=""></option-->
            <%  
            if(sheetStatusTypes.size()!=0)
            {
            
            for(int z=0;z<sheetStatusTypes.size();z++)
            {
              SheetStatusTypeModel sheetStatudTypeModel = (SheetStatusTypeModel)sheetStatusTypes.get(z);
              String sheetStatusTypeIdZ = sheetStatudTypeModel.getSheetStatusTypeId();
              String sheetStatusTypeNameZ = sheetStatudTypeModel.getSheetStatusTypeName();
              %>
              <!--option value="<%=sheetStatusTypeIdZ%>"><%=sheetStatusTypeNameZ%></option-->
              <%
            }
            %>
          <!--/select>
        </td>
      </tr>
      <tr class=TableTextNote>  
        <td align=middle>Change Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM%>',"*");</script></td>
        <td align=middle>To</td>
        <td align=middle colspan=2><Script>drawCalender('<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO%>',"*");</script></td>
      </tr-->
      </tr>
      <tr>
        <!--td align='center' colspan=6-->
         <td colspan=2 align=center>
        <%
        out.print("<INPUT class=button type=button value=\" Show History \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SEARCH_SHEET_STATUS+"';checkBeforeSubmit();\">");

        //out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','','*','*');\">");          
        %>
        <input class=button type="Reset" name="reset" value="Reset">
        </td>
      </tr>
      </table>
    </center>
      <br><br>
<%
        String previousBatchId = "";
        for(int i=0;i<sheetStatus.size();i++)
        {
        SheetStatusModel sheetStatusModel = (SheetStatusModel)sheetStatus.get(i);
        String sheetStatusId = sheetStatusModel.getSheetStatusId();
        String sheetStatusTypeId = sheetStatusModel.getSheetStatusTypeId();
        String sheetStatusTypeName = sheetStatusModel.getSheetStatusTypeName();
        String sheetSerial = sheetStatusModel.getSheetSerial();
        String sheetId = sheetStatusModel.getSheetId();
        Timestamp sheetStatusTimestamp = sheetStatusModel.getSheetStatusTimestamp();
        String userId = sheetStatusModel.getUserId();
        String personFullName = sheetStatusModel.getPersonFullName();
        String batchId = sheetStatusModel.getBatchId();
        String secondPosId = sheetStatusModel.getSecondPosId();
        String secondPosCode = sheetStatusModel.getSecondPosCode();
        String secondPosName = sheetStatusModel.getSecondPosName();
        String posId = sheetStatusModel.getPosId();
        String posCode = sheetStatusModel.getPosCode();
        String posName = sheetStatusModel.getPosName();
        int sheetSimCount = sheetStatusModel.getSheetSimCount();
        String agentId = sheetStatusModel.getAgentId();
        String agentFullName = sheetStatusModel.getAgentFullName();
        if(batchId.compareTo(previousBatchId)!=0)
        {
        if(previousBatchId.compareTo("")!=0)
        {
        %>
        </table>
        <%
        }
        previousBatchId = batchId;
        %>
        <br><br>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td width=5%>Sheet Serial</td>
          <td width=5%>Batch ID</td>
          <td width=5%>Agent</td>
          <td>POS Code</td>
          <td>POS Name</td>
          <td>Second POS Code</td>
          <td>Second POS Name</td>
          <td width=5%>Contract Count</td>
          <td align='center'>Warnings</td> 
        </tr>  
        <TR class=TableTextNote>
          <td><%=sheetSerial%></td>
          <td><%=batchId%></td>
          <td><%=agentFullName%></td>
          <td><%=posCode%></td>
          <td><%=posName%></td>
          <td><%=secondPosCode%></td>
          <td><%=secondPosName%></td>
          <td><%=sheetSimCount%></td>
          <td align='center'>
          <%
            out.println("<input type='button' class='button' value='Warnings' name='warnings' id='warnings' "+
                         " onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_WARNINGS+"';"+
                         "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                         "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';SFRform.submit();\">");
          %>
          </td>
        </tr>
        </table>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td>Status</td>
          <td>Timestamp</td>
          <td nowrap>Change By User</td>
        </tr>
        <%
        }
        %>
        
        <tr class=<%=InterfaceKey.STYLE[i%2]%>>
          <td><%=sheetStatusTypeName%></td>
          <td><%=sheetStatusTimestamp%></td>
          <td nowrap><%=personFullName%></td>
        </tr>
        
        <%
          if(i == sheetStatus.size()-1)
          {
            %>
            </table>
            <%
          }
          %>
         
          <%
        }
        }
    %>
    <br><br>
    <center>
    <input type="button" class="button" value=" Print " onclick="window.print();">
    </center>
    
    </form>
<%
     // out.println("<script>setSearchValues('"+strSearchSheetSerial+"','"+strSearchPOSAgentId+"','"+strSearchSheetStatusTypeId+"','"+strSearchSheetChangeDateFrom+"','"+strSearchSheetChangeDateTo+"');</script>");  
%>     
</body>
</html>
