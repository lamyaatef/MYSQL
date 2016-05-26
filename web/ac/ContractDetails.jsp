<%@ page contentType="text/html;charset=windows-1256"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.ac.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.ac.authcall.model.*"
%>
<%
/**
 * Authentication_CAll_Contract_Details.jsp:
 * Display the Contract data. The status and warnings of this contract 
 * could be updated .
 * 
 * showContract(HttpServletRequest , HttpServletResponse , JspWriter): 
 * Display the Contract data.
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
    <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/combobox.js"></SCRIPT>
  </head>
  <body onkeypress = "if(event.keyCode==13){checkbeforSubmit();}">
    <Center>
      <H2>Authentication Call</H2>
    </Center>
        
    <left>
     <h3>Contract Details</h3>
    </left>
    <Center>
      <script type="text/javascript">

        function changeContractStatus(item)
        {
          var list = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);%>;
          if (list == null)
          alert('list is null' );
          
          for(i=0;i<list.options.length;i++) 
          {
            if (item.value == 'REAL')
            {
              if(list.options[i].text == 'ACCEPTED AUTHINTICATION') 
              {
                list.options[i].selected = true
              }
            }
            if (item.value == 'UNREAL')
            {
              if(list.options[i].text == 'REJECTED AUTHINTICATION') 
              {
                list.options[i].selected = true
              }
            }
          }
        }


        function toggle(item)
        {
          divs=document.getElementsByTagName("DIV");
          for (i=0;i<divs.length;i++) 
          {
            if(divs[i].id != item)
            {
              divs[i].style.display="none";
              
            }
          }
          obj=document.getElementById(item);
          if (obj!=null)
          {
            visible = obj.style.display!="none";
            if (visible) {
              obj.style.display="none";
            } 
            else {
               obj.style.display="";
            }
          }
        }

        function initDefaultListSelectedValue()
        {
          var contractStatuses = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);%>;
          var noValueSelectedFlag = true; 
          for(i=0;i<contractStatuses.length;i++) 
          {
              if(contractStatuses.options[i].selected == true)
              noValueSelectedFlag = false;
          }

          if (noValueSelectedFlag == true)
          {
            for(i=0;i<contractStatuses.length;i++) 
            {
              if(contractStatuses.options[i].text =='ACCEPTED AUTHINTICATION')
              contractStatuses.options[i].selected = true; 
            }
          }
        }

        function checkbeforSubmit()
        {
          var applyedWarnings = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS);%>;
          var contractStatuses = document.ContractDetails.<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);%>;
          selectAll(applyedWarnings);

          var noSelectedOptionFlag = true; 
          var acceptedAuthenticationIndex = 0 ; 

          for(i=0;i<contractStatuses.length;i++) 
          {
          
            if(contractStatuses.options[i].selected == true && contractStatuses.options[i].text =='REJECTED AUTHINTICATION') 
            {
              if(applyedWarnings.length > 1)
              {
                noSelectedOptionFlag = false; 
                document.ContractDetails.submit();
              }
              else
              {
                noSelectedOptionFlag = false; 
                alert('At least one warning must be selected for a REJECTED AUTHINTICATION contract.');
              }
            }
            else if(contractStatuses.options[i].selected == true && contractStatuses.options[i].text =='ACCEPTED AUTHINTICATION') 
            {
              noSelectedOptionFlag = false; 
              document.ContractDetails.submit();
            }
            else if (contractStatuses.options[i].text =='ACCEPTED AUTHINTICATION')
            {
              acceptedAuthenticationIndex = i; 
            }                      
          }//end for
        
          // contractStatuses.options[i].selected= true;  
        }
      </script>
      <%
      showContract(request, response, out);
      %>
    </Center>
  </body>
</html>

<%!
/**
 * showContract method:
 * Display the Contract data.
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
        String homePhone = "";
        String contractSimNO = "";
        String statusTypeName = "";
        String typeName = "";

        String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
        String dcmID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID);
        String batchID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_BATCH_ID);
        String batchDate = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_BATCH_DATE);
        String batchType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_TYPE);
        String batchStatusType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_BATCH_STATUS_TYPE);
        String sheetID = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID);
        String sheetSerialNo = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_NAME);
        String contractCount = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT_COUNT);
        String sheetType = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE);
        String sheetStatus = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_STATUS);
        String contractSimNo = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO);
        ContractModel contractModel = (ContractModel)dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
        ContractStatusTypeDto contractStatusTypeDto = (ContractStatusTypeDto)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRAT_STATUS_TYPE);
        HashMap additionalCollection = (HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
        Boolean doesContractHasPreviousWarning = (Boolean)dataHashMap.get(ContractRegistrationInterfaceKey.OBJ_CONTRACT_HAS_PREVIOUS_WARNING);
        Boolean posQuestionFlag = (Boolean)dataHashMap.get(AuthCallInterfaceKey.OBJ_POS_QUESTION_FLAG);
        
        contractId = contractModel.getId();
        if(contractModel.getAddress() != null)
        {
        address = contractModel.getAddress();
        }
        if(contractModel.getArea() != null)
        {
        area = contractModel.getArea();
        }
        if(contractModel.getCustomerIdNum() != null)
        {
        customerIDNo = contractModel.getCustomerIdNum();
        }
        if(contractModel.getCustomerIdType() != null)
        {
        customerIDType = contractModel.getCustomerIdType();
        }
        if(contractModel.getCustomerIdTypeName() != null)
        {
        customerIDTypeName = contractModel.getCustomerIdTypeName();
        }
        if(contractModel.getCustomerName() != null)
        {
        CustomerName = contractModel.getCustomerName();
        }
        if(contractModel.getDialNum() != null)
        {
        dialNumber = contractModel.getDialNum();
        }
        if(contractModel.getHomePhone() != null)
        {
        homePhone = contractModel.getHomePhone();
        }
        if(contractModel.getMainSimNum() != null)
        {
        contractSimNO = contractModel.getMainSimNum();
        }
        if(contractModel.getStatusTypeName() != null)
        {
        statusTypeName = contractModel.getStatusTypeName();
        }
        if(contractModel.getTypeName() != null)
        {
        typeName = contractModel.getTypeName();
        }
        out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"ContractDetails\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_PHASE_NAME+"\" value=\""+ContractRegistrationInterfaceKey.AUTHINTICATION_CALL_PHASE+"\">");

        out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                    " value=\""+AuthCallInterfaceKey.ACTION_UPDATE_CONTRACT_AUTH_BY_SHEET+"\">");

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
                    "\" value=\""+sheetID+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT_COUNT+
                    "\" value=\""+contractCount+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_TYPE+
                    "\" value=\""+sheetType+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_STATUS+
                    "\" value=\""+sheetStatus+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_NAME+
                    "\" value=\""+sheetSerialNo+"\">");

        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CONTRACT_ID+
                    "\" value=\""+contractId+"\">");

        String[] choosenContracts = (String[])additionalCollection.get(AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT);
        out.println("<select style=\"display:none\" name=\""+AuthCallInterfaceKey.CONTROL_HIDDEN_SHEET_CONTRACT+"\" multiple>");
        for(int i=0; i<choosenContracts.length; i++)
        {
          out.println("<option value=\""+choosenContracts[i]+"\" selected></option>");
        }
        out.println("</select>");
          out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=5 cellPadding=1 width=\"100%\" border=0>");

          out.println("<TR>");
          out.println("<td valign = \"top\">");

        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");        
        out.println("<TR class=TableHeader>");
        out.println("<td colspan=2><font size=><B>Sheet Serial Number : "+sheetSerialNo+"</B></font></TD></TR>");
        out.println("<TR>");
        out.println("<td class=TableHeader nowrap  ><font size=2>Main Sim Number</font></TD>");
        out.println("<td  align=center>"+contractSimNO+"</td>");
        out.println("</tr>");
        ;
        out.println("<TR>");
        out.println("<td class=TableHeader ><font size=2>Customer Name</font></TD>");
        out.println("<td nowrap>"+CustomerName+"</td>");
        out.println("</tr>");
        out.println("<TR>");
        out.println("<td class=TableHeader ><font size=2>Customer ID</font></TD>");
        out.println("<td nowrap align=center>"+customerIDNo+"</td>");
        out.println("</tr>");
        out.println("<TR>");
        out.println("<td class=TableHeader ><font size=2>Customer ID Type</font></TD>");
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
        out.println("<td class=TableHeader ><font size=2>Home Phone</font></TD>");
        out.println("<td nowrap align=center>"+homePhone+"</td>");
        out.println("</tr>");
        out.println("<TR>");
        out.println("<td class=TableHeader ><font size=2>Dial number</font></TD>");      
        out.println("<td nowrap align=center>"+dialNumber+"</td>");
        out.println("</tr>");
        out.println("<td class=TableHeader ><font size=2>POS Name</font></TD>");      
        out.println("<td nowrap >"+contractModel.getPosName()+"</td>");
        out.println("</tr>");
        
        out.println("<TR>");
        out.println("<td class=TableHeader ><font size=2>Contract Type</font></TD>");      
        out.println("<td nowrap>"+typeName+"</td>");
        out.println("</tr>");

        out.println("<TR>");
        out.println("<td class=TableHeader ><font size=2>Contract Was Previously Rejected</font></TD>");      
        out.println("<td>");
        if (doesContractHasPreviousWarning.booleanValue())
        {
         out.println(" <a href=\"javascript:openWindowfull('"+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"+
                      InterfaceKey.HASHMAP_KEY_ACTION+"="+ContractRegistrationInterfaceKey.ACTION_SHOW_CONTRACT_HISTORY_TELL_AUTH+"&"+
                      ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+"="+contractSimNO+"')\">YES</a><br>");         
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
        out.println("</table>");        
        out.println("</td>");
        
        out.println("<td valign = \"top\">");

          
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 border=0>");
        out.println("<TR class=TableHeader>");
        out.println("<td nowrap colspan=3><font size=3><B>Contract User Warnnings</B></font></TD></TR>");
        out.println("<TR >");
        out.println("<td nowrap rowspan=5 align=right>");
        out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+"\">");
        out.println("<option>-- AVAILABLE WARNINGS --</option>");
        Vector userWarning = (Vector)additionalCollection.get(AuthCallInterfaceKey.OBJ_USER_WARNING);
        Vector contractWarning = (Vector)additionalCollection.get(AuthCallInterfaceKey.OBJ_CONTRACT_WARNING);
        for(int i=0; i<userWarning.size(); i++)
        {
          ContractWarningModel userWarningModel = (ContractWarningModel)userWarning.elementAt(i);
          out.println("<option value="+userWarningModel.getContractWarningID());
        /*
          for(int j=0; j<contractWarning.size(); j++)
          {
            ContractWarningModel contractWarningModel = (ContractWarningModel)contractWarning.elementAt(j);
            if(contractWarningModel.getContractWarningID().compareTo(userWarningModel.getContractWarningID())==0)
            {
              out.print("selected");
            }
          }
          */
          out.print(">"+userWarningModel.getContractWarningName()+"</option>");
        }
        out.println("</select>");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR>");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addToList\" value=\"  >   \""+
        " onclick=\"xaddToList("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("<td nowrap rowspan=4>");
        out.println("<select  size=\"10\" multiple name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+"\">");
        out.println("<option>-- SELECTED WARNINGS --</option>");
        out.println("</select>");
        out.println("</TD>");
        out.println("</tr>");
        out.println("<TR >");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"addAll\" value=\" >> \""+
        " onclick=\"xaddAll("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("</tr>");
        out.println("<TR >");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeFromList\" value=\"  <   \""+
        " onclick=\"xremoveFromList("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("</tr>");
        out.println("<TR >");
        out.println("<td width=\"5%\"><input class=\"button\" type=\"button\" name=\"removeAll\" value=\" << \""+
        " onclick=\"xremoveAll("+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", "+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");\"></TD>");
        out.println("</tr>");
        out.println("<TR >");
        out.println("<td class=TableHeader nowrap colspan=3>");

        HashMap authCallStatusByContract  = new HashMap();
        
        if(additionalCollection.containsKey(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS_BY_CONTRACT))
        {
        authCallStatusByContract = (HashMap)additionalCollection.get(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS_BY_CONTRACT);
        }
        
        if(additionalCollection.containsKey(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS))
        {
            Vector authCallStatus = (Vector)additionalCollection.get(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS);
            for(int i=0; i<authCallStatus.size(); i++)
            {
              AuthCallStatusModel newAuthCallStatusModel = (AuthCallStatusModel)authCallStatus.elementAt(i);
              String authCallStatusID = newAuthCallStatusModel.getAuthCallStatusID();
              String authCallStatusName = newAuthCallStatusModel.getAuthCallStatusName();
              out.println("<input type=\"radio\" name=\""+AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS+"\" value="+authCallStatusName+" onclick=\"toggle('"+authCallStatusName+"');changeContractStatus("+AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_OWNER_STATUS +");\"");
              if(authCallStatusByContract.get("AUTH_CALL_STATUS_NAME")!=null && authCallStatusName.compareTo((String)authCallStatusByContract.get("AUTH_CALL_STATUS_NAME"))==0)
              {
                out.print("checked");
              }
              out.print(">"+authCallStatusName);
            }
        }
        out.println("</td>");
        out.println("</tr>");
        out.println("<TR >");
        out.println("<td colspan=3>");
        out.println("<div style=\"DISPLAY: none\" id=\"REACHABLE\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0>");
        out.println("<TR >");
        out.println("<td class=TableHeader nowrap width=\"30%\">Owner Name</TD>");      
        out.println("<td>");
        out.println("<select name=\""+AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_OWNER_STATUS +"\" size=\"1\" onchange=\"changeContractStatus(this);\">");

        if(additionalCollection.containsKey(AuthCallInterfaceKey.OBJ_AUTH_CALL_OWNER_STATUS))
        {
            Vector authCallOwnerStatus = (Vector)additionalCollection.get(AuthCallInterfaceKey.OBJ_AUTH_CALL_OWNER_STATUS);
            for(int i=0; i<authCallOwnerStatus.size(); i++)
            {
              AuthCallOwnerStatusModel newAuthCallOwnerStatusModel = (AuthCallOwnerStatusModel)authCallOwnerStatus.elementAt(i);
              String authCallOwnerStatusID = newAuthCallOwnerStatusModel.getAuthCallOwnerStatusID();
              String authCallOwnerStatusName = newAuthCallOwnerStatusModel.getAuthCallOwnerStatusName();
              out.println("<option value="+authCallOwnerStatusName+" ");
                                              
              if(authCallStatusByContract.get("AUTH_CALL_OWNER_STATUS_NAME")!= null && authCallOwnerStatusName.compareTo((String)authCallStatusByContract.get("AUTH_CALL_OWNER_STATUS_NAME"))==0)
              {
                out.print("selected");
              }
              out.print(">"+authCallOwnerStatusName+"</option>");
            }
        }
        
        out.println("</select>");
        out.println("</td>");
        out.println("</tr>");    
        if(posQuestionFlag.booleanValue())
        {
          out.println("<TR >");
          out.println("<td class=TableHeader nowrap width=\"30%\">POS Name</TD>");      
          out.println("<td>");
          out.println("<select name=\""+AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_POS_STATUS +"\" size=\"1\">");

          if(additionalCollection.containsKey(AuthCallInterfaceKey.OBJ_AUTH_CALL_POS_STATUS))
          {
              Vector authCallPOSStatus = (Vector)additionalCollection.get(AuthCallInterfaceKey.OBJ_AUTH_CALL_POS_STATUS);
              for(int i=0; i<authCallPOSStatus.size(); i++)
              {
                AuthCallPOSStatusModel newAuthCallPOSStatusModel = (AuthCallPOSStatusModel)authCallPOSStatus.elementAt(i);
                String authCallPOSStatusID = newAuthCallPOSStatusModel.getAuthCallPOSStatusID();
                String authCallPOSStatusName = newAuthCallPOSStatusModel.getAuthCallPOSStatusName();
                out.println("<option value="+authCallPOSStatusName+" ");
                if(authCallStatusByContract.get("AUTH_CALL_POS_STATUS_NAME")!= null && authCallPOSStatusName.compareTo((String)authCallStatusByContract.get("AUTH_CALL_POS_STATUS_NAME"))==0)
                {
                  out.print("selected");
                }
                out.print(">"+authCallPOSStatusName+"</option>");
              }
          }
          out.println("</select>");
          out.println("</td>");
          out.println("</tr>");  
        }
        out.println("</table>");
        out.println("</div>");
        out.println("<div style=\"DISPLAY: none\" id=\"UNREACHABLE\">");
        out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0>");
        out.println("<TR >");
        out.println("<td class=TableHeader nowrap width=\"30%\">Contract Status</TD>");      
        out.println("<td>");
        out.println("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS +"\" size=\"1\">");
        for(int i=0; i<contractStatusTypeDto.getContractStatusTypeModelsSize(); i++)
        {
          ContractStatusTypeModel contractStatusTypeModel = contractStatusTypeDto.getContractStatusTypeModel(i);
          int contractStatusTypeID = contractStatusTypeModel.getId();
          String contractStatusTypeName = contractStatusTypeModel.getName();
          out.println("<option value="+contractStatusTypeID+" ");
          if(contractStatusTypeName.compareTo(statusTypeName)==0)
          {
            out.print("selected");
          }
          out.print(">"+contractStatusTypeName+"</option>");
        }
        
        out.println("</select>");
        
        out.println("</td>");
        out.println("</tr>");        
        out.println("</table>");
        out.println("</div>");
        out.println("</td>");
        out.println("</tr>");
        out.println("<TR >");
        out.println("<td colspan=3 align=center>");
        out.println("<input type=\"hidden\" name=\""+AuthCallInterfaceKey.CONTROL_RADIO_VIEW_CONTRACTS+"\" value=\""+AuthCallInterfaceKey.ADD_CONTRACT+"\">");
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_CONTRACT_SIM_NO+"\" value=\""+contractSimNO+"\">");
        out.println("<input class=\"button\" type=\"button\" name=\"update\" value=\"  Update  \" onclick=\"checkbeforSubmit();\" >");
        out.println("<input class=\"button\" type=\"button\" name=\"cancel\" value=\"  Cancel  \""+
        " onclick=\"document.ContractDetails."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+AuthCallInterfaceKey.ACTION_SHOW_SHEET_DETAILS_FOR_AUTHENTICATION_CALL+"';ContractDetails.submit();\">");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<script>xaddToList(ContractDetails."+ContractRegistrationInterfaceKey.CONTROL_SELECT_USER_WARNINGS+", ContractDetails."+ContractRegistrationInterfaceKey.CONTROL_SELECT_APPLYED_USER_WARNINGS+");");

        if(additionalCollection.containsKey(AuthCallInterfaceKey.OBJ_AUTH_CALL_STATUS_BY_CONTRACT))
        {
            if(authCallStatusByContract.containsKey("AUTH_CALL_STATUS_NAME"))
            {
              out.print("toggle('"+authCallStatusByContract.get("AUTH_CALL_STATUS_NAME")+"');</script>");
            }
            else
            {
              String authCallStatusIndex = "0";
              if(statusTypeName.compareTo("REJECTED AUTHINTICATION") == 0)
              {
                authCallStatusIndex = "1";
              }
              out.print("ContractDetails."+AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS+"["+authCallStatusIndex+"].checked=true;");
              out.print("toggle(ContractDetails."+AuthCallInterfaceKey.CONTROL_SELECT_AUTH_CALL_STATUS+"["+authCallStatusIndex+"].value);</script>");
            }
        }
        out.println("</form>");
      }
    }
  }
%>