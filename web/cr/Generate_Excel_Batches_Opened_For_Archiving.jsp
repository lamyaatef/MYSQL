<!--%@ page contentType="text/html;charset=windows-1256"%-->
<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.sql.Connection"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import ="com.mobinil.sds.web.interfaces.gn.ua.*"
         import="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.dto.*"
         import="com.mobinil.sds.core.system.cr.sheet.model.*"
         import="com.mobinil.sds.core.system.cr.contract.model.*"
         import="com.mobinil.sds.core.system.cr.contract.dto.*"
         import="com.mobinil.sds.core.system.cr.contract.dao.*"
         import ="com.mobinil.sds.core.utilities.Utility"
%>

<%
response.addHeader("Content-Disposition", "attachment; filename=report.xls");
%>

<html>
    <head>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>
    </head>
  <body>
<script>  
    function checkBeforeSubmit()
    {
      
    }
</script>  
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  BatchDto batchDto = (BatchDto)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  String contractsStatus = (String)objDataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_SELECT_CONTRACT_STATUS);
%>   
<form name='DCMform' id='DCMform' action='' method='post'>
<%
  //out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
  //                " value=\""+"\">");

  //out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
  //                " value=\""+strUserID+"\">");             


  String channelId = "1";
        if(objDataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)objDataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

  if(contractsStatus.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY)==0)
  {
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR >
          <TD align='center'>Batch ID</TD>
          <TD align='center'>Sheet ID</TD>
          <TD align='center'>Contract Main SIM No.</TD>
          <TD align='center'>Dial No.</TD>
          <TD align='center'>Customer Name</TD>
          <TD align='center'>Address</TD>
          <TD align='center'>Area</TD>
          <TD align='center'>Id No.</TD>
          <TD align='center'>Customer Birthdate</TD>
          <TD align='center'>Home Telephone</TD>
          <TD align='center'>User Warnings</TD>
          <TD align='center'>System Warnings</TD>
        </tr>
<%
  }
  else if(contractsStatus.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_REJECTED_VERIFY)==0)
  {
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
        <TR >
          <TD align='center'>Batch ID</TD>
          <TD align='center'>Sheet ID</TD>
          <TD align='center'>Contract Main SIM No.</TD>
          <TD align='center'>Dial No.</TD>
          <TD align='center'>Customer Name</TD>
          <TD align='center'>User Warnings</TD>
          <TD align='center'>System Warnings</TD>
        </tr>
<%
  }
  BatchModel batchModel = batchDto.getBatchModel();
  String batchId = batchModel.getBatchId();
  
  SheetDto sheetDto = batchDto.getSheetDto();
  Vector listOfSheets = sheetDto.getSheetModels();
  String prevSheetId = "";
  Connection con = Utility.getConnection();
  for(int i=0;i<listOfSheets.size();i++)
  {
    SheetModel sheetModel = (SheetModel)listOfSheets.get(i);   
    String sheetId = sheetModel.getSheetId();
    ContractDto contractDto = ContractDao.getSheetContracts(sheetId,con);
    Vector listOfContracts = sheetDto.getSheetContracts(sheetId);
    for(int j=0;j<listOfContracts.size();j++)
    {
      ContractModel contractModel = (ContractModel)listOfContracts.get(j);
      String mainSimNum = contractModel.getMainSimNum();
      String id = contractModel.getId();
      String automaticFlag = contractModel.getAutomaticFlag();
      String typeId = contractModel.getTypeId();
      String typeName = contractModel.getTypeName();
      String dialNum = contractModel.getDialNum();
      String customerName = contractModel.getCustomerName();
      String address = contractModel.getAddress();
      if(address == null)address="";
      String area = contractModel.getArea();
      if(area == null)area = "";
      String customerIdNum = contractModel.getCustomerIdNum();
      String customerIdType = contractModel.getCustomerIdType();
      String customerIdTypeName = contractModel.getCustomerIdTypeName();
      String homePhone = contractModel.getHomePhone();
      if(homePhone == null)homePhone = "";
      String statusTypeName = contractModel.getStatusTypeName();
      String sheetSerialNo = contractModel.getSheetSerialNo();
      boolean reImportedFlag = contractModel.getReImportedFlag();
      String contractLastStatusId  = contractModel.getContractLastStatusId();
      String customerBirthDate = contractModel.getCustomerBirthDate();
      if(customerBirthDate == null)customerBirthDate="";
      String cityEnglish = contractModel.getCityEnglish();
      String cityArabic = contractModel.getCityArabic();
      String cityGovCode = contractModel.getCityGovCode();

      Vector contractUserWarnings = contractDto.getContractWarning(id);
      Vector contractSystemWarningVector = ContractDao.validateContractInContractVerification(con,id,automaticFlag,contractLastStatusId);

      if(contractsStatus.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_ACCEPTED_VERIFY)==0)
      {
      %>
        <TR>
          <%
          if(j==0)
          {
          %>
            <TD align='center'><%=batchId%></TD>
          <%
          }
          else
          {
          %>
            <TD align='center'></TD>
          <%
          }
          if(sheetId.compareTo(prevSheetId)!=0)
          {
            prevSheetId = sheetId;
          %>
          <TD align='center'><%=sheetId%></TD>
          <%
          }
          else
          {
          %>
            <TD align='center'></TD>
          <%
          }
          %>
          <TD align='center'>="<%=mainSimNum%>"</TD>
          <TD align='center'>="<%=dialNum%>"</TD>
          <TD align='center'><%=customerName%></TD>
          <TD align='center'><%=address%></TD>
          <TD align='center'><%=area%></TD>
          <TD align='center'><%=customerIdNum%></TD>
          <TD align='center'><%=customerBirthDate%></TD>
          <TD align='center'><%=homePhone%></TD>
          <td nowrap> 
          <%
          if(contractUserWarnings !=null)
          {
            String contractWarnings = "";
            for (int l=0; l<contractUserWarnings.size(); l++)
            {          
              contractWarnings+=((ContractWarningModel)contractUserWarnings.elementAt(l)).getContractWarningName()+"<br>";
            }
            out.println(contractWarnings);
          }  
          %>
          </td>
          <td nowrap>
          <%
          if(contractSystemWarningVector !=null)
          {
            String contractSystemWarnings = "";
            for (int l=0; l<contractSystemWarningVector.size(); l++)
            {          
              contractSystemWarnings+=((ContractWarningModel)contractSystemWarningVector.elementAt(l)).getContractWarningName()+"<br>";
            }
            out.println(contractSystemWarnings);
          }  
          %>
          </td>
        </tr>
      <%
      }
      else if(contractsStatus.compareTo(ContractRegistrationInterfaceKey.CONST_CONTRACT_STATUS_REJECTED_VERIFY)==0)
      {
        %>
        <TR>
        <%
        if(j==0)
        {
        %>
          <TD align='center'><%=batchId%></TD>
        <%
        }
        else
        {
        %>
          <TD align='center'></TD>
        <%
        }
        if(sheetId.compareTo(prevSheetId)!=0)
          {
            prevSheetId = sheetId;
          %>
          <TD align='center'><%=sheetId%></TD>
          <%
          }
          else
          {
          %>
            <TD align='center'></TD>
          <%
          }
          %>
          <TD align='center'>="<%=mainSimNum%>"</TD>
          <TD align='center'>="<%=dialNum%>"</TD>
          <TD align='center'><%=customerName%></TD>
          <td nowrap>
          <%
          if(contractUserWarnings !=null)
          {
            String contractWarnings = "";
            for (int l=0; l<contractUserWarnings.size(); l++)
            {          
              contractWarnings+=((ContractWarningModel)contractUserWarnings.elementAt(l)).getContractWarningName()+"<br>";
            }
            out.println(contractWarnings);
          }  
          %>
          </td>
          <td nowrap>
          <%
          if(contractSystemWarningVector !=null)
          {
            String contractSystemWarnings = "";
            for (int l=0; l<contractSystemWarningVector.size(); l++)
            {          
              contractSystemWarnings+=((ContractWarningModel)contractSystemWarningVector.elementAt(l)).getContractWarningName()+"<br>";
            }
            out.println(contractSystemWarnings);
          }  
          %>
          </td>
        </tr>
      <%
      }
    }
  }
  Utility.closeConnection(con);
  %> 
    </table>
</form>
</body>
</html>
