<%@ page contentType="text/html;charset=windows-1256"
         import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.ac.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.batch.model.BatchSheetModel"
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
 * @author  Mohamed Mahmoud
 * @see     
 *
 * SDS
 * MobiNil
 */ 
%>




<SCRIPT language=JavaScript>
  
  function checkbeforSubmit()
  {
  alert('hi');
    if(NonBlank(document.ContractVerify.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);%>, true, 'Contract Main Sim No'))
    {
      document.ContractVerify.submit();
    } 
    return false;
  }

  function checkWarningBeforSubmit()
  {
    document.ContractDetails.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=AuthCallInterfaceKey.ACTION_UPDATE_CONTRACT%>";
    var applyedWarnings = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);%>;
    var contractStatuses = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);%>;
    var contractSystemWarningCount = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);%>;
    selectAll(applyedWarnings);
    for(i=0;i<contractStatuses.length;i++) 
    {
      if(contractStatuses.options[i].selected == true && contractStatuses.options[i].text =='REJECTED VERIFY') 
      {
        if((applyedWarnings.length > 1) || (contractSystemWarningCount.length >0) )
        {
          document.ContractDetails.submit();
        }
        else
        {
          alert('At least one warning must be selected for a REJECTED VERIFY contract.');
        }
      }
      else if(contractStatuses.options[i].selected == true && contractStatuses.options[i].text =='ACCEPTED VERIFY') 
      {
        document.ContractDetails.submit();
      }

    }//end for
  }

  function updateSystemWarning()
  {
    document.ContractDetails.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = "<%=ContractRegistrationInterfaceKey.ACTION_ADMIN_RECHECK_CONTRACT_SYSTEM_WARNING%>";
    document.ContractDetails.submit();
  }
  
  function getSuggestedStatus()
  {
    var applyedWarnings = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);%>;
    var contractStatuses = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);%>;
    var txtContractStatus = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_TEXT_SUGGESTED_WARNING_STATUS);%>;
    var contractSuggestedStatusList = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS);%>;
    var systemWarnings = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS);%>;
    
    var finalsuggestion = "";
    var suggestionflag = true;
    var selectedStatus;
    for(i=0;i<applyedWarnings.length;i++) 
    {
        var warning = applyedWarnings.options[i].text
        if(warning !='-- SELECTED WARNINGS --')
        {
            for(var k=0;k<contractSuggestedStatusList.length;k++)
            if(contractSuggestedStatusList.options[k].text == warning)
            {
              if(contractSuggestedStatusList.options[k].value =='2')
              {
                  suggestionflag = false;
              }
            }
        }
    }
    for(l=0;l<systemWarnings.length;l++) 
    {
        var warning = systemWarnings.options[l].text
        for(var p=0;p<contractSuggestedStatusList.length;p++)
        {
          if(contractSuggestedStatusList.options[p].text == warning)
          {
            if(contractSuggestedStatusList.options[p].value =='2')
            {
                suggestionflag = false;
            }
          }
        }
    }
    if(suggestionflag == false)
    {
        selectedStatus = 1;        
    }
    else if(suggestionflag == true)
    {
        selectedStatus = 0;
    }
    contractStatuses.options[selectedStatus].selected =true;
    finalsuggestion = contractStatuses.options[selectedStatus].text;
    txtContractStatus.value = finalsuggestion;
  }
</SCRIPT>
<html>
  <head>
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">
    <SCRIPT language=JavaScript src="../resources/js/tree.js" type=text/javascript></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="../resources/js/calendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="../resources/js/utilities.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="../resources/js/validation.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript" SRC="../resources/js/combobox.js"></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkWarningBeforSubmit();}">
  
    <%
    out.println("<center>");
    out.println("<H2>Contract Verification Administration</H2>");
    out.println("</Center>");
    out.println("<left>");
    out.println("<h3>Contract Details</h3>");
    out.println("</left>");
    out.println("<Center>");


    showContract(request, response, out);
    %>
  </body>
</html>

<%!
/**
 * showSheets method:
 * Display the sheets of the selected batch.
 *
 * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
 * @return  
 * @throws  ServletException, IOException if the jsp failed
 * @see    
 * 
*/
  public void showContract(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    if(dataHashMap != null)
    {
      if(! dataHashMap.isEmpty())
      {
          


        String contractId = "";
        String address = "";
        String area = "";
        String customerIDNo = "";
        String customerIDType = "";
        String customerIDTypeName = "";
        String CustomerName = "";
        String dialNumber = "";
        String customerBirthDate = "";
        String homePhone = "";
        String contractSimNO = "";
        String statusTypeName = "";
        String typeName = "";

        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        //String dcmID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        //String archivingFlag = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG);
        //String batchDate = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        //String batchType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        //String batchStatusType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
		//String batchId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        //String sheetID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        //String sheetSerialNumber = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO);
        //String sheetPOSCode = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE);
        String sheetPOSCode = "";
        String sheetSuperDealerCode = "";
        String batchId = "";
        String dcmID = "";
        String archivingFlag = "";
        String batchDate = "";
        String batchType = "";
        String batchStatusType = "";
        String sheetID = "";
        String sheetSerialNumber = "";
        //String sheetSuperDealerCode = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE);
        String contractSimNo = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
		BatchSheetModel batchSheetModel = (BatchSheetModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"ContractDetails\" method=\"post\">");
		
        String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        if(batchSheetModel.getBatchId() != null)
        {batchId = batchSheetModel.getBatchId();}
        if(batchSheetModel.getDcmId() != null)
        {dcmID = batchSheetModel.getDcmId();}
        if(batchSheetModel.getArchivingFlag() != null)
        {archivingFlag = batchSheetModel.getArchivingFlag();}
        if(batchSheetModel.getBatchDate() != null)
        {batchDate = batchSheetModel.getBatchDate();}
        if(batchSheetModel.getBatchType() != null)
        {batchType = batchSheetModel.getBatchType();}
        if(batchSheetModel.getBatchStatusType() != null)
        {batchStatusType = batchSheetModel.getBatchStatusType();}
        if(batchSheetModel.getSheetId() != null)
        {sheetID = batchSheetModel.getSheetId();}
        if(batchSheetModel.getSheetSerialId() != null)
        {sheetSerialNumber = batchSheetModel.getSheetSerialId();}
        if(batchSheetModel.getPosCode() != null)
        {sheetPOSCode = batchSheetModel.getPosCode();}
        if(batchSheetModel.getSuperDealerCode() != null)
        {sheetSuperDealerCode = batchSheetModel.getSuperDealerCode();}
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");
        
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.CONTRACT_VERIFICATION_PHASE+"\">");

        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
        " value=\""+"\">");

        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+
                    "\" value="+userID+">");
        
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+
                    "\" value=\""+dcmID+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID+
                "\" value=\""+batchId+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_ARCHIVING_FLAG+
                    "\" value=\""+archivingFlag+"\">");
                    
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE+
                    "\" value=\""+batchDate+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE+
                    "\" value=\""+batchType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE+
                    "\" value=\""+batchStatusType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+
                    "\" value=\""+sheetID+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_NO+
                    "\" value=\""+sheetSerialNumber+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_POS_CODE+
                    "\" value=\""+sheetPOSCode+"\">");

       out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SUPER_DEALER_CODE+
                    "\" value=\""+sheetSuperDealerCode+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+
                    "\" value=\""+contractSimNo+"\">");
                    
        String strError = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_SERVLET_EXP);
        if(strError != null)
        {
          out.println("<script type=\"text/javascript\">");
          out.println("alert('"+strError+"');");
          out.println("document.ContractDetails."+InterfaceKey.HASHMAP_KEY_ACTION+
                      ".value=\""+ContractRegistrationInterfaceKey.ACTION_ADMIN_VERIFY_SHEET+"\";");
          out.println("ContractDetails.submit();");
          out.println("</script>");
        }
        else
        {
          ContractModel contractModel = (ContractModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
          ContractStatusTypeDto contractStatusTypeDto = (ContractStatusTypeDto)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE);
          HashMap warning = (HashMap)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRACT_WARNING);
          Boolean doesContractHasPreviousWarning = (Boolean)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING);
        
          contractId = contractModel.getId();
          if(contractModel.getAddress() != null)
          { address = contractModel.getAddress(); }
          if(contractModel.getArea() != null)
          { area = contractModel.getArea(); }
          if(contractModel.getCustomerIdNum() != null)
          { customerIDNo = contractModel.getCustomerIdNum(); }
          if(contractModel.getCustomerIdType() != null)
          { customerIDType = contractModel.getCustomerIdType(); }
          if(contractModel.getCustomerIdTypeName() != null)
          { customerIDTypeName = contractModel.getCustomerIdTypeName(); }
          if(contractModel.getCustomerName() != null)
          { CustomerName = contractModel.getCustomerName(); }
          if(contractModel.getDialNum() != null)
          { dialNumber = contractModel.getDialNum(); }
          if(contractModel.getCustomerBirthDate() != null)
          { customerBirthDate = contractModel.getCustomerBirthDate().substring(0,10); }
          if(contractModel.getHomePhone() != null)
          { homePhone = contractModel.getHomePhone(); }
          if(contractModel.getMainSimNum() != null)
          { contractSimNO = contractModel.getMainSimNum(); }
          if(contractModel.getStatusTypeName() != null)
          { statusTypeName = contractModel.getStatusTypeName(); }
          if(contractModel.getTypeName() != null)
          { typeName = contractModel.getTypeName(); }
          
          out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID+
                      "\" value=\""+contractId+"\">");
          
          

          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=5 cellPadding=1 width=\"100%\" border=0>");
          out.println("<TR>");
          out.println("<td valign = \"top\">");

              out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 border=1>");
              out.println("<TR class=TableHeader>");
              out.println("<td nowrap colspan=2><font size=3><B>Sheet Serial Number : "+sheetSerialNumber+"</B></font></TD></TR>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Sim Number</font></TD>");
              out.println("<td nowrap align=center>"+contractSimNO+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Customer Name</font></TD>");
              out.println("<td nowrap>"+CustomerName+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Customer ID</font></TD>");
              out.println("<td nowrap align=center>"+customerIDNo+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>ID Type</font></TD>");
              out.println("<td nowrap>"+customerIDTypeName+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Address</font></TD>");
              out.println("<td nowrap>"+address+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Area</font></TD>");
              out.println("<td nowrap>"+area+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Birth Date</font></TD>");
              out.println("<td nowrap align=center>"+customerBirthDate+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Home Phone</font></TD>");
              out.println("<td nowrap align=center>"+homePhone+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Dial number</font></TD>");      
              out.println("<td nowrap align=center>"+dialNumber+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Contract Type</font></TD>");      
              out.println("<td nowrap>"+typeName+"</td>");
              out.println("</tr>");

              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Previous Warnings</font></TD>");      
              out.println("<td>");
              if (doesContractHasPreviousWarning.booleanValue())
              {
               out.println(" <a href=\"javascript:openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                            InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACT_HISTORY_TELL_VERIFY+"&"+
                            ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+"="+contractSimNO+"')\">View History</a><br>");         
              }
              else
              {
              out.println("NO");
              }                
              out.println("</TD>");
              out.println("</tr>");
   
              out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Contract Status</font></TD>");      
              out.println("<td>");
              out.println(statusTypeName);
              out.println("</TD>");
              out.println("</tr>");
            /*
               out.println("<TR>");
              out.println("<td class=TableHeader ><font size=2>Form Number</font></TD>");      
              out.println("<td>");
        //      out.println(contractModel.getContractFormNumber());
           out.println("SAK");
              out.println("</TD>");
              out.println("</tr>");

            */
            
              out.println("</table>");
        
          out.println("</TD>");
          out.println("<TD valign = \"top\">");

              out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 border=0>");
              out.println("<TR class=TableHeader>");
              out.println("<td nowrap colspan=3><font size=3><B>Contract System Warnnings</B></font></TD></TR>");
              Vector systemWarning = (Vector)warning.get(AuthCallInterfaceKey.OBJ_SYSTEM_WARNING);
              boolean disableStatus = false;
              for(int i=0; i<systemWarning.size(); i++)
              {
                ContractWarningModel contractWarningModel = (ContractWarningModel)systemWarning.elementAt(i);
                out.println("<TR >");
                out.println("<td colspan=3>");
                out.println(contractWarningModel.getContractWarningName());
                if (contractWarningModel.getContractWarningTypeName().compareTo("SYSTEM WARNING") == 0) {
                  disableStatus = true;
                }
                out.println("</TD>");
                out.println("<TR >");
              }
              if (systemWarning.size() < 2) {
                out.println("<TR >");
                out.println("<td colspan=3><br>");
                out.println("</TD>");
                out.println("<TR >");
              }
              if (systemWarning.size() < 3) {
                out.println("<TR >");
                out.println("<td colspan=3><br>");
                out.println("</TD>");
                out.println("<TR >");
              }
              /*Add by victor in 10th of March 2005
              * Build a hidden select control holding all the system warnings of the contract so that i send them back to the server side 
              * and save the effort and time of selecting them once more form the database.
              */
              out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_SYSTEM_WARNINGS+"\" style=\"display:none\">");
              for(int i=0; i<systemWarning.size(); i++)
              {
                ContractWarningModel contractWarningModel = (ContractWarningModel)systemWarning.elementAt(i);
                out.println("<option value="+contractWarningModel.getContractWarningID()+" selected>"+contractWarningModel.getContractWarningName()+"</option>");
              }        
              out.println("</select>");
        
              out.println("<TR class=TableHeader>");
              out.println("<td nowrap colspan=3><font size=3><B>Contract User Warnnings</B></font></TD></TR>");

              out.println("<TR >");
              out.println("<td nowrap rowspan=5 align=right>");
              out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"\">");
              out.println("<option>-- AVAILABLE WARNINGS --</option>");
              Vector userWarning = (Vector)warning.get(AuthCallInterfaceKey.OBJ_USER_WARNING);
              Vector contractWarning = (Vector)warning.get(AuthCallInterfaceKey.OBJ_CONTRACT_WARNING);
        
              for(int i=0; i<userWarning.size(); i++)
              {
                ContractWarningModel userWarningModel = (ContractWarningModel)userWarning.elementAt(i);
                out.println("<option value="+userWarningModel.getContractWarningID());
                
                //this code is to mark the warnings that were previously selected while builing the list
                //so i can call xaddall to add them to the user selected warnings and remove them from this list
                for(int j=0; j<contractWarning.size(); j++)
                {
                  ContractWarningModel contractWarningModel = (ContractWarningModel)contractWarning.elementAt(j);
                  if(contractWarningModel.getContractWarningID().compareTo(userWarningModel.getContractWarningID()) == 0)
                  {
                    out.print(" selected");
                  }
                }
                
                out.print(">"+userWarningModel.getContractWarningName()+"</option>");
              }
              out.println("</select>");
              
              out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_WARNING_SUGGESTED_STATUS+"\" style=\"display:none\">");        
              for(int m=0; m<userWarning.size(); m++)
              {
                ContractWarningModel userWarningModel = (ContractWarningModel)userWarning.elementAt(m);
                out.println("<option value="+userWarningModel.getContractwarningSuggestedStatusID());                
                out.print(">"+userWarningModel.getContractWarningName()+"</option>");
              }
              for(int f=0; f<systemWarning.size(); f++)
              {
                ContractWarningModel systemWarningModel = (ContractWarningModel)systemWarning.elementAt(f);
                out.println("<option value="+systemWarningModel.getContractwarningSuggestedStatusID());                
                out.print(">"+systemWarningModel.getContractWarningName()+"</option>");
              }
              out.println("</select>");
              
              out.println("</TD>");
              out.println("</tr>");
              out.println("<TR>");
              out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addToList\" value=\"  >   \"");
              
              out.print(" onclick=\"xaddToList("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");getSuggestedStatus();\"></TD>");
              out.println("<td nowrap rowspan=4>");
              out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+"\">");
              out.println("<option>-- SELECTED WARNINGS --</option>");
              out.println("</select>");
              out.println("</TD>");
              out.println("</tr>");

              out.println("<TR >");
              out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\" >> \"");
              
              out.print(" onclick=\"xaddAll("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");getSuggestedStatus();\"></TD>");
              out.println("</tr>");

              out.println("<TR >");
              out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"  <   \"");
              
              out.print(" onclick=\"xremoveFromList("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");getSuggestedStatus();\"></TD>");
              out.println("</tr>");

              out.println("<TR >");
              out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\" << \"");
              
              out.print(" onclick=\"xremoveAll("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");getSuggestedStatus();\"></TD>");
              out.println("</tr>");

              out.println("<TR >");
              out.println("<td class=TableHeader nowrap><font size=2>Contract Status</font></TD>");      
              out.println("<td width=\"5%\" colspan=2>");
              out.println("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS +"\" size=\"1\"");
              
              out.print(">");
              boolean suggestReject = false;
        
              if (systemWarning.size()>=1)
              {
                suggestReject=true;
                Utility.logger.debug("sugest reject = true");
              }
        
              for(int i=0; i<contractStatusTypeDto.getContractStatusTypeModelsSize(); i++)
              {
                ContractStatusTypeModel contractStatusTypeModel = contractStatusTypeDto.getContractStatusTypeModel(i);
                int contractStatusTypeID = contractStatusTypeModel.getId();
                String contractStatusTypeName = contractStatusTypeModel.getName();
                if (suggestReject==true && contractStatusTypeName.compareTo(ContractModel.STATUS_REJECTED_VERIFY)==0)
                {
                  out.println("<option value="+contractStatusTypeID+" selected>"+contractStatusTypeName+"</option>");
                }
                else
                {
                  out.println("<option value="+contractStatusTypeID+">"+contractStatusTypeName+"</option>");
                }
          
              }
              out.println("</select>");
              out.println("</td>");
              out.println("</tr>");

              out.println("<TR >");
              out.println("<td class=TableHeader nowrap><font size=2>Suggested Status</font></TD>");      
              out.println("<td width=\"5%\" colspan=2>");
              out.println("<input type='text' name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SUGGESTED_WARNING_STATUS+"\" id=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SUGGESTED_WARNING_STATUS+"\" readonly>");
              out.println("</td>");
              out.println("</tr>");

              out.println("<TR >");
              out.println("<td width=\"5%\" colspan=3 align=center>"); 
              System.out.println("the active flag issssssss " + archivingFlag);
              if(archivingFlag.compareTo(ContractRegistrationInterfaceKey.CONST_ARCHIVING_FLAG_NOT_ENTERED_ARCHIVING_PROCESS)==0)
              {
                out.println("<input class=\"button\" type=\"button\" name=\"update\" value=\"  Update  \"");
                System.out.println ("getReImportedFlag issssssssssssss  " + contractModel.getReImportedFlag());
                if (contractModel.getReImportedFlag())
                {
                  out.print(" disabled");
                }
                out.print(" onclick=\"checkWarningBeforSubmit();\" >");
              }
              
              out.println("<input class=\"button\" type=\"button\" name=\"cancel\" value=\"  Cancel  \""+
              " onclick=\"document.ContractDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AuthCallInterfaceKey.ACTION_VIEW_CONTRACT_DATA+"';ContractDetails.submit();\">");

              out.println("</td>");
              out.println("</tr>");

              out.println("</table>");

          out.println("</TD>");
          out.println("</tr>");

          out.println("<tr>");
          out.println("<td align='center'>");
              out.println("<input class=\"button\" type=\"button\" name=\"RecheckSystemWarning\" value=\"  Recheck System Warning  \"");
              out.print(" onclick=\"updateSystemWarning();\" >");
          out.println("</td>");    
          out.println("</tr>");
          out.println("</table>");

          out.println("<script>xaddToList(ContractDetails."+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", ContractDetails."+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");</script>");
        }
        out.println("</form>");
      }
    }
  }
%>