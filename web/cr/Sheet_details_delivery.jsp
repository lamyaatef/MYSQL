<%@ page contentType="text/html;charset=windows-1256"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.warning.model.*"
%>
<SCRIPT language=JavaScript>
  function checkbeforSubmit()
  {
    var applyedWarnings = document.SheetDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);%>;
    var contractStatuses = document.SheetDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);%>;
    selectAll(applyedWarnings);
    for(i=0;i<contractStatuses.length;i++) 
    {
      if(contractStatuses.options[i].selected == true && contractStatuses.options[i].text =='REJECTED DELIVERY') 
      {
        if(applyedWarnings.length > 1)
        {
          document.SheetDetails.submit();
        }
        else
        {
          alert('At least one warning must be selected for a REJECTED DELIVERY sheet.');
        }
      }
      else if(contractStatuses.options[i].selected == true && contractStatuses.options[i].text =='ACCEPTED DELIVERY') 
      {
        document.SheetDetails.submit();
      }
    }
  }
</SCRIPT>
<%      String appName = request.getContextPath();%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/combobox.js"></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <Center>
      <H2>Delivery Verification</H2>
       <h3>Sheet Details</h3>
      <%
      showSheetDetails(request, response, out);
      %>
    </Center>
  </body>
</html>

<%!

  public void showSheetDetails(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
      String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    Enumeration keys = request.getAttributeNames();
    while (keys.hasMoreElements())
    {
    Utility.logger.debug((String)keys.nextElement());
    }

 
    if(dataHashMap != null)
    {
 
      if(! dataHashMap.isEmpty())
      {     
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String dcmID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        String batchID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String batchDate = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        String batchType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        String batchStatusType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        String sheetID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        String sheetSerialNo = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_NAME);
        String sheetStatus = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_STATUS);

        SheetDto sheetDto = (SheetDto)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        Hashtable additionalCollection = (Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        SheetStatusTypeDto newSheetStatusTypeDto = (SheetStatusTypeDto)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_SHEET_STATUS_TYPE);
        SheetModel sheetModel = sheetDto.getSheet(0);

        int contractCount = sheetModel.getContractsCount();
        String sheetId = sheetModel.getSheetId();
        String sheetName= sheetModel.getSheetName();
        String sheetPosCode = sheetModel.getSheetPOSCode();
        String sheetSuperDealer = sheetModel.getSheetSuperDealerCode();
        int sheetStatusId = sheetModel.getSheetStatusId();
        String sheetStatusName = sheetModel.getSheetStatusName();
        String sheetType = sheetModel.getSheetTypeID();
        String sheetTypeName = sheetModel.getSheetTypeName();
        String posCode = sheetModel.getSheetPOSCode();
        String superDealerCode = sheetModel.getSheetSuperDealerCode();
        
        
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetDetails\" method=\"post\">");

        String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
          
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.DELIVERY_VERIFICATION_PHASE+"\">");

        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_DETAILS_STATUS+"\">");

        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                    "\" value="+userID+">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                    "\" value=\""+dcmID+"\">");


        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                    "\" value=\""+batchID+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                    "\" value=\""+batchDate+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                    "\" value=\""+batchType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                    "\" value=\""+batchStatusType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+
                    "\" value=\""+sheetId+"\">");


        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT_COUNT+
                    "\" value=\""+contractCount+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE+
                    "\" value=\""+sheetType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_STATUS+
                    "\" value=\""+sheetStatus+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_NAME+
                    "\" value=\""+sheetSerialNo+"\">");

        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
        out.println("<TR class=TableHeader>");
        out.println("<td nowrap colspan=2><font size=4><B>Sheet Serial Number : "+sheetName+"</B></font></TD></TR>");

        out.println("<TR>");
        out.println("<td class=TableHeader nowrap width=\"20%\"><font size=2>Sheet Super Dealer Code</font></TD>");
        out.println("<td nowrap align=left>"+sheetSuperDealer+"</td>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("<td class=TableHeader nowrap width=\"20%\"><font size=2>Sheet POS Code</font></TD>");
        out.println("<td nowrap>"+sheetPosCode+"</td>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("<td class=TableHeader nowrap width=\"20%\"><font size=2>Total Contracts</font></TD>");
        out.println("<td nowrap align=center>"+contractCount+"</td>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("<td class=TableHeader nowrap width=\"20%\"><font size=2>Sheet Type</font></TD>");
        out.println("<td nowrap>"+sheetTypeName+"</td>");
        out.println("</tr>");
        out.println("<TR>");
        out.println("<td class=TableHeader nowrap width=\"20%\"><font size=2>Sheet Status</font></TD>");
        out.println("<td nowrap>"+sheetStatusName+"</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=0>");
        out.println("<TR class=TableHeader>");
        out.println("<td nowrap colspan=3><font size=4><B>Sheet User Warnnings</B></font></TD></TR>");
        out.println("<TR >");
        out.println("<td nowrap rowspan=5 align=right>");
        out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"\">");
        out.println("<option>-- AVAILABLE WARNINGS --</option>");
        Vector userWarning = (Vector)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_USER_WARNING);

        Vector sheetWarning = (Vector)additionalCollection.get(ContractRegistrationInterfaceKey.OBJ_SHEET_WARNING);
        for(int i=0; i<userWarning.size(); i++)
        {

          SheetWarningModel userWarningModel = (SheetWarningModel)userWarning.elementAt(i);
          out.println("<option value="+userWarningModel.getSheetWarningID());

          /*
          for(int j=0; j<sheetWarning.size(); j++)
          {
            SheetWarningModel sheetWarningModel = (SheetWarningModel)sheetWarning.elementAt(j);
            if(sheetWarningModel.getSheetWarningID().compareTo(userWarningModel.getSheetWarningID()) ==0)
            {
              out.print("selected");
            }
          }
          */
          out.print(">"+userWarningModel.getSheetWarningName()+"</option>");
        }
        
        out.println("</select>");
        out.println("</TD>");
        out.println("</tr>");
        
        out.println("<TR>");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addToList\" value=\"     >      \""+
        " onclick=\"xaddToList("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("<td nowrap rowspan=4>");
        out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+"\">");
        out.println("<option>-- SELECTED WARNINGS --</option>");
        out.println("</select>");
        out.println("</TD>");
        out.println("</tr>");

        out.println("<TR >");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\"    >>    \""+
        " onclick=\"xaddAll("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("</tr>");

        out.println("<TR >");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"     <      \""+
        " onclick=\"xremoveFromList("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("</tr>");

        out.println("<TR >");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\"    <<    \""+
        " onclick=\"xremoveAll("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("</tr>");

        out.println("<TR >");
        out.println("<td class=TableHeader nowrap><font size=2>Sheet Status</font></TD>");      
        out.println("<td width=\"5%\" colspan=2>");
        out.println("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS +"\" size=\"1\">");

          for(int k= 0; k<newSheetStatusTypeDto.getSheetModelsSize(); k++)
          {
            int sheetStatusTypeID = newSheetStatusTypeDto.getSheetStatusTypeModel(k).getId();
            String sheetStatusTypeName = newSheetStatusTypeDto.getSheetStatusTypeModel(k).getName();          
            out.println("<option value=\""+sheetStatusTypeID+"\"");
            if(sheetStatusTypeID == sheetStatusId)
            {
              out.print("selected");
            }
            out.println(">"+sheetStatusTypeName+"</option>");
          }

        boolean suggestReject = false;

        out.println("</select>");
        out.println("</td>");
        out.println("</tr>");

        out.println("<TR >");
        out.println("<td width=\"5%\" colspan=3 align=center>");
        out.println("<input class=\"button\" type=\"button\" name=\"update\" value=\"  Update  \" onclick=\"checkbeforSubmit();\">");
        out.println("<input class=\"button\" type=\"button\" name=\"cancel\" value=\"  Cancel  \""+
        " onclick=\"document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_SHOW_BATCH_DETAILS+"';"+
        "SheetDetails.submit();\">");
        out.println("</td>");
        out.println("</tr>");

        out.println("</table>");
        out.println("<script>xaddToList(SheetDetails."+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", SheetDetails."+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");</script>");
        out.println("</form>");
      }
    }
  }
%>