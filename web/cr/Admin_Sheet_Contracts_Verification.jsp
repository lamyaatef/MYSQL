<%@ page import ="javax.servlet.*" 
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
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
%>
<%
/**
 * Sheet_Contracts_Verification.jsp:
 * Display the sheet data as a header followed by the contracts of this sheet.
 * 
 * showSheets(HttpServletRequest , HttpServletResponse , JspWriter): 
 * used to display the sheets of the selected batchs.
 *
 *
 * @version	1.01 March 2004
 * @author  Victor Saad Fahim
 * @see     
 *
 * SDS
 * MobiNil
 */ 

    String appName = request.getContextPath();
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/validation.js"></SCRIPT>
  </head>

<%
String scriptTemp = "if(NonBlank(document.SheetDetails."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+", true, 'Gunned Sim No')){"+
          "document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_CONTRACT+"';"+
          "SheetDetails.submit();}return false;";


%>


<body onkeypress = "if(event.keyCode==13){<%out.print(scriptTemp);%>}">

   

                                                    

<script type="text/javascript">

  function checkBeforSubmit(tableName)
  {
    var table = document.getElementById(tableName);
    var rows = table.rows;
    for(i=1; i<rows.length; i++)
    {
      var dataCells = rows[i].cells;
      var contractMainSimNumber = dataCells[0].innerText;
      var statusComboBox = dataCells[2].getElementsByTagName("select")[0];
      var warningComboBox = dataCells[3].getElementsByTagName("select")[0];
      if(warningComboBox != null)
      {
        for (j=0;j<warningComboBox.length;j++) 
        {
          if(warningComboBox.options[j].selected == true && warningComboBox.options[j].text !='') 
          {
            if(statusComboBox != null && statusComboBox.selectedIndex <= 0) 
            {
              alert('A new status must be selected if a warning is selected for contract '+contractMainSimNumber+'.');
              statusComboBox.focus();
              return false;
            }
          }
        }
      }
    }
    for(i=1; i<rows.length; i++)
    {
      var dataCells = rows[i].cells;
      var contractMainSimNumber = dataCells[0].innerText;
      var statusComboBox = dataCells[2].getElementsByTagName("select")[0];
      var warningComboBox = dataCells[3].getElementsByTagName("select")[0];
      if(statusComboBox != null)
      {
        for (j=0;j<statusComboBox.length;j++) 
        {
          if(statusComboBox.options[j].selected == true && statusComboBox.options[j].text =='REJECTED VERIFY') 
          {
            if(warningComboBox != null && warningComboBox.selectedIndex <= 0) 
            {
              alert('At least one warning must be selected for REJECTED VERIFY contract '+contractMainSimNumber+'.');
              warningComboBox.focus();
              return false;
            }
          }
        }
      }
    }
    return true;
  }
  function changeAll(combobox) 
  {
    comboboxs=document.getElementsByTagName("SELECT");
    for (i=0;i<comboboxs.length;i++) 
    {
      if(comboboxs[i].name.match(combobox.name) == combobox.name && ! comboboxs[i].disabled)
      {
        comboboxs[i].value = combobox.value;
      }
    }
  }
</script>
    <Center>
      <H2>Contract Verification Administration</H2>
    </Center>
        
    <left>
     <h3>Sheet Details</h3>
    </left>
      <%
      showContracts(request, response, out);
      %>
  </body>
</html>

<%!
/**
 * showContracts method:
 * Display the contracts of the verified sheet.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  public void showContracts(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String dcmID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        String batchID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String archivingFlag = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG);

        String batchDate ="";
        if (dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE) ==null)
        {
        }
        else
        {
         batchDate = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        }
        
        
        String batchType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        String batchStatusType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        String sheetSerialNo = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        String sheetPOSCode = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
        String sheetSuperDealerCode = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);        
        String contractSimNo = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);

        ContractStatusTypeDto newContractStatusTypeDto = (ContractStatusTypeDto)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE);
        Vector userWarning = (Vector)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_USER_WARNING);

        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetDetails\" method=\"post\">");

        String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

        
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE+"\">");
        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\">");

        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                    "\" value="+userID+">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                    "\" value=\""+dcmID+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                    "\" value=\""+batchID+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+
                    "\" value=\""+archivingFlag+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                    "\" value=\""+batchDate+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                    "\" value=\""+batchType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                    "\" value=\""+batchStatusType+"\">");

        String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
        if(request.getAttribute(InterfaceKey.HASHMAP_KEY_ACTION).equals(ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_CONTRACT)
           && strError == null)
        {
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+
                      "\" value=\""+contractSimNo+"\">");
          out.println("<script type=\"text/javascript\">");
          out.println("document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
          ".value=\""+ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_CONTRACT_DETAILS+"\";");
          out.println("SheetDetails.submit();");
          out.println("</script>");
        }
        else
        {
          if(strError != null)
          {
            out.println("<script type=\"text/javascript\">alert('"+strError+"');</script>");
          }
          SheetModel newSheetModel = (SheetModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
          //Utility.logger.debug(" newSheetModel " + newSheetModel);
          String sheetID = newSheetModel.getSheetId();
          int contractCount = newSheetModel.getContractsCount();
          String sheetType = newSheetModel.getSheetTypeName();
          String sheetStatus = newSheetModel.getSheetStatusName();

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+
                      "\" value=\""+sheetID+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT_COUNT+
                      "\" value=\""+contractCount+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE+
                      "\" value=\""+sheetType+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_STATUS+
                      "\" value=\""+sheetStatus+"\">");

          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+
                      "\" value=\""+sheetSerialNo+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE+
                    "\" value=\""+sheetPOSCode+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE+
                    "\" value=\""+sheetSuperDealerCode+"\">");

          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
          out.println("<TR class=TableHeader>");
          out.println("<td align=center><font size=2>Sheet Serial Number</font></TD>");  
          out.println("<td align=center><font size=2>SUPER DEALER CODE</font></TD>");
          out.println("<td align=center><font size=2>POS CODE</font></TD>");
          out.println("<td align=center><font size=2>Contract count</font></TD>");
          out.println("<td align=center><font size=2>Sheet Type</font></TD>");
          out.println("<td align=center><font size=2>Sheet Status</font></TD></TR>");

          out.println("<TR>");
          out.println("<td nowrap align=center>"+sheetSerialNo+"</td>");
          out.println("<td nowrap align=center>"+sheetSuperDealerCode+"</td>");
          out.println("<td nowrap align=center>"+sheetPOSCode+"</td>");
          out.println("<td nowrap align=center>"+contractCount+"</td>");
          out.println("<td nowrap>"+sheetType+"</td>");
          out.println("<td nowrap>"+sheetStatus+"</td></tr>");
          out.println("</TABLE><br>");

          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1 id=\""+sheetID+"\">");

          out.println("<TR class=TableHeader>");
          out.println("<td width=\"20%\" align=middle>Contract Main Sim No</td>");
          out.println("<td width=\"20%\" align=middle>Contract Type</td>");
          out.println("<td width=\"20%\" align=middle>");
          out.println("Contract Status" +"<br>"); 
              
          out.println("<Select name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+" onchange=\"changeAll(this);\">");
          out.println("<option>Apply status to all Contracts</option>");
          for(int k= 0; k<newContractStatusTypeDto.getContractStatusTypeModelsSize(); k++)
          {
            int contractStatusTypeID = newContractStatusTypeDto.getContractStatusTypeModel(k).getId();
            String contractStatusTypeName = newContractStatusTypeDto.getContractStatusTypeModel(k).getName();
            out.println("<option value=\""+contractStatusTypeID+"\">"+contractStatusTypeName+"</option>");
          }
          out.println("</Select>");
          out.println("</td>");
          out.println("<td width=\"20%\" align=middle>");
          out.println("Contract Warning" +"<br>"); 
              
          out.println("<Select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"_\" onchange=\"changeAll(this);\">");
          out.println("<option>Add warning to all Contracts</option>");
          for(int i=0; i<userWarning.size(); i++)
          {
            ContractWarningModel userWarningModel = (ContractWarningModel)userWarning.elementAt(i);
            String warningID = userWarningModel.getContractWarningID();
            String warningName = userWarningModel.getContractWarningName();
            out.println("<option value="+warningID+">"+warningName+"</option>");
          }
          out.println("</Select>");
          out.println("</td>");
          out.println("<td width=\"20%\" align=middle>");
          out.println("System Warnings");
          out.println("</td></tr>");
          boolean sheetIsReImported = newSheetModel.isSheetReImportFlag();
          
          ContractDto contractDto = (ContractDto)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
          for(int i = 0; i<contractDto.getContractModelsSize(); i++)
          {
            ContractModel newContractModel = contractDto.getContract(i);
            String contractID = newContractModel.getId();
            String mainSimNo = newContractModel.getMainSimNum();
            String contractType = newContractModel.getTypeName();
            String contractStatus = newContractModel.getStatusTypeName();
            String automaticFlag = newContractModel.getAutomaticFlag();
            String contractLastStatusId = newContractModel.getContractLastStatusId();
            
            Vector contractWarningVector = contractDto.getContractWarning(contractID);
            Vector contractSystemWarningVector = contractDto.getContractSystemWarning(contractID);
            boolean reimported = newContractModel.getReImportedFlag();


            
            out.println("<input type='hidden' name='"+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_LAST_STATUS+"_"+contractID+"' id='"+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_LAST_STATUS+"_"+contractID+"' value='"+automaticFlag+"_"+contractLastStatusId+"'>");

            if(reimported || sheetIsReImported)
            {
              out.println("<TR class=LockedRow>");
              out.println("<td width=\"20%\" nowrap align=center>"+mainSimNo+"</td>");
            }
            else
            {
              out.println("<TR>");
              out.println("<td width=\"20%\" nowrap align=center>");
              out.println("<a href=\"javascript:SheetDetails."+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+".value='"+mainSimNo+"';"+
              "document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_CONTRACT+"';"+
              "SheetDetails.submit();\">"+mainSimNo+"</a></td>");
            }
            out.println("<td width=\"20%\" nowrap>"+contractType+"</td>");
            out.println("<td width=\"20%\" nowrap align=center>"+contractStatus+"<br>");

            if(! reimported)
            {
              out.println("<Select name="+ContractRegistrationInterfaceKey.CONTROL_SELSCT_NAME_PREFIX+contractID+">");
              out.println("<option></option>");
              for(int k= 0; k<newContractStatusTypeDto.getContractStatusTypeModelsSize(); k++)
              {
                int contractStatusTypeID = newContractStatusTypeDto.getContractStatusTypeModel(k).getId();
                String contractStatusTypeName = newContractStatusTypeDto.getContractStatusTypeModel(k).getName();
                if (newContractModel.getStatusTypeName().equals(contractStatusTypeName))
                {
                  out.println("<option value=\""+contractStatusTypeID+"\">"+contractStatusTypeName+"</option>");
                }
                else
                {
                  out.println("<option value=\""+contractStatusTypeID+"\">"+contractStatusTypeName+"</option>");
                }
              }
              out.println("</Select>");
            }
            out.println("</td>");
            out.println("<td width=\"20%\" nowrap align=center>");

            if(contractWarningVector !=null)
            {
              String contractWarnings = "";
              for (int l=0; l<contractWarningVector.size(); l++)
              {          
                contractWarnings+=((ContractWarningModel)contractWarningVector.elementAt(l)).getContractWarningName()+"<br>";
              }
              out.println(contractWarnings);
            }
            if(! reimported)
            {
              out.println("<Select name="+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"_"+contractID+">");
              out.println("<option></option>");
              for(int l=0; l<userWarning.size(); l++)
              {
                ContractWarningModel userWarningModel = (ContractWarningModel)userWarning.elementAt(l);
                String warningID = userWarningModel.getContractWarningID();
                String warningName = userWarningModel.getContractWarningName();
                out.println("<option value="+warningID+">"+warningName+"</option>");
              }
              out.println("</Select>");
            }
            out.println("</td>");
            out.println("<td width=\"20%\" nowrap align=center>");

            if(contractSystemWarningVector !=null)
            {
              String contractSystemWarnings = "";
              for (int l=0; l<contractSystemWarningVector.size(); l++)
              {          
                contractSystemWarnings+=((ContractWarningModel)contractSystemWarningVector.elementAt(l)).getContractWarningName()+"<br>";
              }
              out.println(contractSystemWarnings);
            }
            out.println("</td></tr>");
          }
          out.println("</TABLE>");      
          out.println("<br><Center>");

          out.println("<table  cellspacing=\"2\" cellpadding=\"1\" border=\"0\">");
          
          if (! sheetIsReImported )
          {
           
            out.println("<tr>");
            out.println("<td>Gunned Sim No</td>");
            out.println("<td><input type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+"\" size=\"21\" maxlength=\"20\"></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td colspan=2 align=center>");
            if(archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_NOT_ENTERED_ARCHIVING_PROCESS)==0)
            {
            out.println("<input class=button type=\"button\" name=\"update\" value=\"  Update  \" ");
            out.print("onclick=\"if(checkBeforSubmit('"+sheetID+"')){document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                    ContractRegistrationInterfaceKey.ACTION_ADMIN_UPDATE_CONTRACT_STATUS_CONTRACT_VERIFY_SCREEN+"';"+
                    "SheetDetails.submit();}\">");
            }
          out.println("<input class=button type=\"button\" name=\"verify\" value=\"  Verify  \" ");
          out.print("onclick=\"if(NonBlank("+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+", true, 'Gunned Sim No')){"+
          "document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_CONTRACT+"';"+
          "SheetDetails.submit();}return false;\">");
                    out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
          out.print("onclick=\"document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
                    ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION+"';SheetDetails.submit();\"></td>");
          out.println("</tr>");

           }

           else
           {
              out.println("<tr>");
              out.println("<td colspan=2 align=center>");

              out.println("<input class=button type=\"button\" name=\"back\" value=\"   Back   \" ");
              out.print("onclick=\"document.SheetDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
              ContractRegistrationInterfaceKey.ACTION_ADMIN_SHOW_BATCH_DETAILS_CONTRACT_VERIFICATION+"';SheetDetails.submit();\"></td>");
              out.println("</tr>");
           }
           

           
          out.println("</table><br>");
          out.println("</Center>");
          out.println("</form>");
        }
      }
    }
  }
%>