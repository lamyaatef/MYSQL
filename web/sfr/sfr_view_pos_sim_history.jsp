<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.sql.Timestamp"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sfr.*"

         import ="com.mobinil.sds.core.system.sfr.sheets.model.*"  
         import ="com.mobinil.sds.core.system.sa.product.model.*"
         import ="com.mobinil.sds.core.system.sa.users.dto.*" 
         import ="com.mobinil.sds.core.system.sa.users.model.*"
         import ="com.mobinil.sds.core.system.sa.persons.model.*"
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

        function setSearchValues(simSerial,sheetSerial,batchId,simStatusTypeId,changeByUserId,changeDateFrom,changeDateTo,activationDateFrom,activationDateTo)
        {
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL%>").value = simSerial;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL%>").value = sheetSerial;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID%>").value = batchId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_STATUS_ID%>").value = simStatusTypeId;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_USER_ID%>").value = changeByUserId;
  
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM%>").value = changeDateFrom;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO%>").value = changeDateTo;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_FROM%>").value = activationDateFrom;
          document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_TO%>").value = activationDateTo;
        }

        function checkBeforeSubmit()
        {
           var simSerial = document.getElementById("<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL%>").value;
           if(simSerial == "")
           {
              alert("SIM Serial can not be empty..");
           }
           else
           {
              SFRform.submit();  
           }
        }
</script>  
<body>
    <CENTER>
      <H2> POS SIM History </H2>
    </CENTER>

      <form name='SFRform' id='SFRform' action='' method='post'>
<%


  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  /*String strSearchSIMSerial = "";
  String strSearchSheetSerial = "";
  String strSearchBatchId = "";
  String strSearchSIMStatusTypeId = "";
 // String strSearchContarctTypeId = "";
  String strSearchChangeByUserId = "";
  String strSearchSIMChangeDateFrom = "*";
  String strSearchSIMChangeDateTo = "*";
  String strSearchSIMActivationDateFrom = "*";
  String strSearchSIMActivationDateTo = "*";

  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL)){strSearchSIMSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL)){strSearchSheetSerial = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_SHEET_SERIAL);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID)){strSearchBatchId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_TEXT_BATCH_ID);}
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_STATUS_ID)){strSearchSIMStatusTypeId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_STATUS_ID);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_USER_ID)){strSearchChangeByUserId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_USER_ID);}  
  //if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_CONTRACT_TYPE_ID)){strSearchContarctTypeId = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_CONTRACT_TYPE_ID);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM)){strSearchSIMChangeDateFrom = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_FROM);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO)){strSearchSIMChangeDateTo = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SHEET_CHANGE_DATE_TO);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_FROM)){strSearchSIMActivationDateFrom = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_FROM);}  
  if(objDataHashMap.containsKey(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_TO)){strSearchSIMActivationDateTo = (String)objDataHashMap.get(SFRInterfaceKey.INPUT_SERACH_SELECT_SIM_ACTIVATION_DATE_TO);}  
*/

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  Object o = objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  
  System.out.println("sft history o="+o);
  Vector simStatus = null;
  

  if (o != null)
	  simStatus = (Vector) o;
  
  if (simStatus == null)
	  simStatus = new Vector();
  
  Vector simStatusTypes = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION); 

%>  

      <center>
      <table  cellspacing="2" cellpadding="1" border="0" width="35%">

      <tr>
        <td align=left>SIM Serial:</td>
        <td align=right><input type='text' name='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL%>' id='<%=SFRInterfaceKey.INPUT_SERACH_TEXT_SIM_SERIAL%>' size="24" maxlength="24"></td>
        
          
      </tr>
     
        <tr>
            <td colspan=2 align=center>
        <%
        out.print("<INPUT class=button type=button value=\" Show History \" name=\"searchrequest\" id=\"searchrequest\" onclick=\"document.SFRform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SFRInterfaceKey.ACTION_SEARCH_SIM_STATUS+"';checkBeforeSubmit();\">");

        //out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('','','','','','','*','*','*','*');\">");          
        %>
        <input class=button type="Reset" name="reset" value="Reset">
        </td>
      </tr>
      </table>
    </center>
      <br><br>
        <%
        String previousSheetSerial = "";
        for(int i=0;i<simStatus.size();i++)
        {
        SIMStatusModel simStatusModel = (SIMStatusModel)simStatus.get(i); 
        String simStatusId = simStatusModel.getSimStatusId();
        String simStatusTypeId = simStatusModel.getSimStatusTypeId();
        String simStatusTypeName = simStatusModel.getSimStatusTypeName();
        String simSerial = simStatusModel.getSimSerial();
        Timestamp simStatusTimestamp = simStatusModel.getSimStatusTimestamp();
        String userId = simStatusModel.getUserId();
        String personFullName = simStatusModel.getPersonFullName();
        String agentId = simStatusModel.getAgenId();
        String personFullName1 = simStatusModel.getPersonFullName1();
        String sheetSerial = simStatusModel.getSheetSerial();
        //Date activationDate = simStatusModel.getActivationDate();
        //String contractType = simStatusModel.getContractType();
        //if(contractType == null)contractType = "";
        String batchId = simStatusModel.getBatchId();
        String secondPosId = simStatusModel.getSecondPosId();
        String secondPosCode = simStatusModel.getSecondPosCode();
        String secondPosName = simStatusModel.getSecondPosName();
        String posId = simStatusModel.getPosId();
        String posName = simStatusModel.getPosName();
        String posCode = simStatusModel.getPosCode();
        String infoSource = simStatusModel.getInfoSource();
        
        if(sheetSerial.compareTo(previousSheetSerial)!=0)
        {
        if(previousSheetSerial.compareTo("")!=0)
        {
        %>
        </table><br><br>
        <%
        }
        previousSheetSerial = sheetSerial;
        %>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td>Batch ID</td>
          <td>Sheet Serial</td>
          <td>SIM Serial</td>
          <td>Info Source </td>
        </tr>
        <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <td><%=batchId%></td>
          <td><%=sheetSerial%></td>
          <td><%=simSerial%></td>
          <td><%=infoSource%></td>
        </tr>
        </table>
         <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td>POS Code</td>
          <td>POS Name</td>
          <td>Second POS Code</td>
          <td>Second POS Name</td>
        </tr>
        <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <td><%=posCode%></td>
          <td><%=posName%></td>
          <td><%=secondPosCode%></td>
         <td><%=secondPosName%></td>
        </tr>
        </table>
        
        <%
        }%>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td>Agent Id</td>
          <td>Agent Name</td>
          
        </tr>
        <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <td><%=agentId%></td>
          <td><%=personFullName1%></td>
        </tr>
        </table>
        <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR class=TableHeader>
          <td>SIM Status</td>
          <td>Change Timestamp</td>
          <td>Change By User</td>
        </tr>
        <TR class=<%=InterfaceKey.STYLE[i%2]%>>
          <td><%=simStatusTypeName%></td>
          <td><%=simStatusTimestamp%></td>
          <td><%=personFullName%></td>
        </tr>
        <%
        if(i == simStatus.size()-1)
          {
            %>
            </table>
            <%
          }
          %>
          <br><br>
          <center>
          <input type="button" class="button" value=" Print " onclick="window.print();">
          </center>
          <%
        }
        %>
      

    <br><br>
    <center>
    
    </center>
    </form>
<%
      //out.println("<script>setSearchValues('"+strSearchSIMSerial+"','"+strSearchSheetSerial+"','"+strSearchBatchId+"','"+strSearchSIMStatusTypeId+"','"+strSearchChangeByUserId+"','"+strSearchSIMChangeDateFrom+"','"+strSearchSIMChangeDateTo+"','"+strSearchSIMActivationDateFrom+"','"+strSearchSIMActivationDateTo+"');</script>");  
%>         
</body>
</html>
