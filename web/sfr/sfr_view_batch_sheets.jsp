<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
<script>
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }

        function setSearchValues(sheetSerial,posAgentId,sheetStatusTypeId,posCode)
        {
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>").value = sheetSerial;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID%>").value = posAgentId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID%>").value = sheetStatusTypeId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE%>").value = posCode;
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

        function checkBeforeSubmit(simCount,simTotalScaned)
        {
          if(simCount != simTotalScaned)
          {
            alert("SIM Count not equal Total SIM Scaned..");
            return;
          }
          else
          { 
            SFRform.submit();
          }
        }
</script>
  <body>
    <CENTER>
      <H2> POS Batch Sheets </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
<%
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  String strBatchID = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID);
  Object o = objDataHashMap.get(SFRInterfaceKey.INPUT_HIDDEN_FLAG);
  
  String flag = null;
  if (o != null)
	  flag = (String) o ; 
  
  //System.out.println("The flag isssssssssssssssssss " + flag);
  String strSearchSheetSerial = "";
  String strSearchPOSAgentId = "";
  String strSearchSheetStatusTypeId = "";
  String strSearchPOSCode = "";

  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL)){strSearchSheetSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID)){strSearchPOSAgentId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID)){strSearchSheetStatusTypeId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE)){strSearchPOSCode = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE);}  

  String errMsg = "";
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_MESSAGE))
  {
    errMsg = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_MESSAGE);
    out.println("<script>alert('"+errMsg+"');</script>");
  }
  
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_BATCH_ID+"\""+
                  " value=\""+strBatchID+"\">");   
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_FLAG+"\""+
          " value=\""+flag+"\">");
  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+"\""+
                  " value=\""+"\">");                  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+"\""+
                  " value=\""+"\">");                 
                  
  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+"\""+
                  " value=\""+"\">");  

  out.println("<input type=\"hidden\" name=\""+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+"\""+
                  " value=\""+"\">");  
                  
                  
  Vector sheets = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  
  Vector<SheetStatusTypeModel> sheetStatusTypes = (Vector<SheetStatusTypeModel>)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION); 
  //Vector posAgents = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2); 
  
  Vector batchDetails = (Vector)objDataHashMap.get(SFRInterfaceKey.SFR_HASHMAP_KEY_COLLECTION);
  
  String agentId = "";
  String agentFullName = "";
  String batchStatusTypeId = "";
  String batchStatusTypeName = "";
  Date batchDate = null;
  if(batchDetails.size()>0)
  {
    BatchModel batchModel = (BatchModel)batchDetails.get(0);
    agentId = batchModel.getAgentId();
    agentFullName = batchModel.getAgentFullName();
    if(agentFullName == null)agentFullName = "";
    batchStatusTypeId = batchModel.getBatchStatusTypeId();
    batchStatusTypeName = batchModel.getBatchStatusTypeName();
    batchDate = batchModel.getBatchDate();
  }
%>    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='center'>Batch ID</td>
        <td align='center'>Agent Name</td>
        <td align='center'>Batch Status</td>
        <td align='center'>Creation Date</td>
      </tr>
      <tr>
        <td align='center'><%=strBatchID%></td>
        <td align='center'><%=agentFullName%></td>
        <td align='center'><%=batchStatusTypeName%></td>
        <td align='center'><%=batchDate%></td>
      </tr>
      </table>

      <br><br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td align='left' colspan=4>Search</td>
      </tr>
      <TR class=TableTextNote>
        <td align=middle>Sheet Serial</td>
        <td align=middle><input type='text' name='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>' id='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>'></td>
        <td align=middle>POS Code</td>
        <td align=middle>
          <input type="text" name='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE%>' id='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_POS_CODE%>'>
        </td>
      </tr>
      <TR class=TableTextNote>  
        <td align=middle>POS Agent Code</td>
        <td align=middle>
          <input type="text" name='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID%>' id='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_POS_AGENT_ID%>'>
        </td>
        <td align=middle>Sheet Status</td>
        <td align=middle>
          <select name='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID%>' id='<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_STATUS_ID%>'>
            <option value=""></option>
            <%  
            
            for(int z=0;z<sheetStatusTypes.size();z++)
            {
              SheetStatusTypeModel sheetStatudTypeModel = sheetStatusTypes.get(z);
              String sheetStatusTypeIdZ = sheetStatudTypeModel.getSheetStatusTypeId();
              String sheetStatusTypeNameZ = sheetStatudTypeModel.getSheetStatusTypeName();
              %>
              <option value="<%=sheetStatusTypeIdZ%>"><%=sheetStatusTypeNameZ%></option>
              <%
            }
            %>
          </select>
        </td>
      </tr>
      <tr>
        <td align='center' colspan=4>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SEARCH_SHEETS+"';SFRform.submit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','','');\">");          
        %>
        </td>
      </tr>
      </table>
    
      <br>
      <% 
       if(flag!= null && flag.compareTo("1")==0)
      {
    	  %>
    	  <center>
    	  <% 
    	  
    	  out.print("<INPUT class=button type='button' value=\" Add New Sheet \" name=\"new\" id=\"new\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_ADD_NEW_SHEET+"';"+
          "SFRform.submit();\">");
      }
 %>
 </center>
 <br>
      <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td></td>
          <td align='center'>Sheet Serial</td>
          <td align='center'>POS Name</td>
          <td align='center'>POS Code</td>
          <td align='center'>Second POS Code</td>
          <td align='center'>Contract Count</td>
          <td align='center'>Status</td>
        </tr>
        <%
        for(int i=0;i<sheets.size();i++)
        {
        SheetModel sheetModel = (SheetModel)sheets.get(i);
        String sheetId = sheetModel.getSheetId();
        String sheetSerial = sheetModel.getSheetSerial();
        String batchId = sheetModel.getBatchId();
        String posId = sheetModel.getPosId();
        String posName = sheetModel.getPosName();
        String posCode = sheetModel.getPosCode();
        String secondPosId = sheetModel.getSecondPosId();
        String secondPosCode = sheetModel.getSecondPosCode();
        int sheetSIMCount = sheetModel.getSheetSIMCount();
        String sheetStatusTypeId = sheetModel.getSheetStatusTypeId();
        String sheetStatusTypeName = sheetModel.getSheetStatusTypeName();
        int sheetSIMScanedTotal = sheetModel.getSheetSIMScanedTotal();
        int sheetSIMScanedDublicate = sheetModel.getSheetSIMScanedDublicate();
        int sheetSIMScanedUnique = sheetModel.getSheetSIMScanedUnique();
        int sheetSIMScanedAccepted = sheetModel.getSheetSIMScanedAccepted();
        int sheetSIMScanedRejected = sheetModel.getSheetSIMScanedRejected();
        
        %>
        <TR class=TableTextNote>
          <%
          if(sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_REJECTED)==0)
          {
          %>
          <td>
          </td>
          <td align='center'>
            <%=sheetSerial%>
          </td>
          <%
          }
          else
          {
          %>
          <td>
            <A id="x<%=sheetSerial%>" href="javascript:Toggle('<%=sheetSerial%>');">
              <img src='../resources/img/plus.gif' border='0'>
            </A>
          </td>
          <td align='center'>
            <%
            out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_TOTAL+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSerial+"</A>");
            %>
          </td>
          <%
          }
          %>
          <td align='center'><%=posName%></td>
          <td align='center'><%=posCode%></td>
          <td align='center'><%=secondPosCode%></td>
          <td align='center'><%=sheetSIMCount%></td>
          <td align='center'>
          <%
          out.println(sheetStatusTypeName+"<br>");
          if(sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED)==0 && sheetSIMScanedTotal>0)
          {
            out.println("<input type='button' class='button' name='"+SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID+"_"+sheetSerial+"' "+
                                            "id='"+SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID+"_"+sheetSerial+"' value='close' "+
                                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SFR_UPDATE_SHEET_STATUS+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_CLOSED+"';"+
                                            "checkBeforeSubmit("+sheetSIMCount+","+sheetSIMScanedTotal+");\">");
          }
          else if(sheetStatusTypeId.compareTo(SFRInterfaceKey.CONST_SIM_STATUS_TYPE_ACCEPTED)==0 && sheetSIMScanedTotal==0)
          {
            out.println("<input type='button' class='button' name='"+SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID+"_"+sheetSerial+"' "+
                                            "id='"+SFRInterfaceKey.INPUT_SELECT_SHEET_STATUS_ID+"_"+sheetSerial+"' value='Reject' "+
                                            "onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SFR_UPDATE_SHEET_STATUS+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                                            "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+SFRInterfaceKey.CONST_SHEET_STATUS_TYPE_REJECTED+"';"+
                                            "SFRform.submit();\">");
          }
          %>
          </td>
          </tr>
            <tr>
            <td colspan=8>
              <div style="display:none" id="<%=sheetSerial%>" name="<%=sheetSerial%>">
              <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
                <tr class=TableHeader>  
                  <td align=middle>Total Contracts Scaned</td>
                  <td align=middle>Accepeted Contracts Scaned</td>
                  <td align=middle>Rejected Contracts Scaned</td>
                  <td align=middle>Unique Contracts Scaned</td>
                  <td align=middle>Duplicate Contracts Scaned</td>
                </tr>
                <tr class=TableTextNote>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_TOTAL+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedTotal+"</a>");
                  %>
                  </td>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_ACCEPTED+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedAccepted+"</a>");
                  %>
                  </td>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_REJECTED+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedRejected+"</a>");
                  %>
                  </td>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_UNIQUE+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedUnique+"</a>");
                  %>
                  </td>
                  <td align=middle>
                  <%
                  out.println("<A href=\"#\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_VIEW_SHEET_CONTRACTS_SIM_NUMBER+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SERIAL+".value = '"+sheetSerial+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_ID+".value = '"+sheetId+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_SIM_GROUP+".value = '"+SFRInterfaceKey.CONST_SHEET_SIM_SCANED_DUBLICATE+"';"+
                          "document.SFRform."+SFRInterfaceKey.INPUT_HIDDEN_SHEET_STATUS_TYPE_ID+".value = '"+sheetStatusTypeId+"';SFRform.submit();\">"+sheetSIMScanedDublicate+"</a>");
                  %>
                  </td>
                </tr>
              </table>
              </dev>
            </td>
          </tr>
        <%
        }
        %>
      </table>
      <br><br>
      
      <center>
      <input type="button" class="button" value=" Back " onclick="history.go(-1);">
          <input type="button" class="button" value=" Print " onclick="window.print();">
        </center>
      
  </form>
<%
      out.println("<script>setSearchValues('"+strSearchSheetSerial+"','"+strSearchPOSAgentId+"','"+strSearchSheetStatusTypeId+"','"+strSearchPOSCode+"');</script>");  
%>          
  </body>
</html>
