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

         import="com.mobinil.sds.core.system.dcm.chain.model.*"
         import="com.mobinil.sds.core.system.dcm.chain.model.chainCityModel"
         
%>
<html>
    <head>
    <%
     String appName = request.getContextPath();
  %>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="<%out.print(appName);%>/resources/js/validation.js" type=text/javascript></SCRIPT>
    </head>
  <body>
  
  <SCRIPT language=JavaScript>
  function emailInValid(inputStr) {
 apos=inputStr.indexOf("@");
 //alert(apos);
 dpos=inputStr.lastIndexOf(".");
 //alert(dpos);
 //lpos=inputStr.length-1;
 mpos =inputStr.lastIndexOf(" ");
 //alert(mpos);
 if(inputStr ==""){return true;}
 if (inputStr.length >250){ alert("You entered an invalid E-Mail address.");return false;}
 if (apos<2) { alert("You entered an invalid E-Mail address.");return false;}
 else if (dpos<1) {alert("You entered an invalid E-Mail address.");return false;}
 else if (mpos>1) { alert("You entered an invalid E-Mail address.");return false; }
 else {return true;}
}
  function checkbeforSubmit()
  {
    if(NonBlank(document.DCMform.<%out.print(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_NAME);%>, true, 'Chain Name'))
    {
      if(NonBlank(document.DCMform.<%out.print(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);%>, true, 'Chain Code'))
      {
          if(emailInValid(document.DCMform.<%out.print(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_EMAIL);%>.value))
          {
            document.DCMform.submit();
          }
        
      }
    }
    return false;
  }
</SCRIPT>
  <%
    HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    ChainModel chainModel = (ChainModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
    //String appName = request.getContextPath();
    Vector vecChainChannelList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_3);
    Vector vecChainLevelList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    Vector vecChainPaymentLevelList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);
    Vector vecChainCityList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_4);
    Vector vecChainDistrictList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_5);
    Vector vecChainStatusList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_XXX);

    String nextAction = DCMInterfaceKey.ACTION_SAVE_GEN_DCM;
    String searchChainCode = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_DCM_CODE);
    String searchChainLevel = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL);
    String searchChainChannel = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL);
    String searchChainPaymentLevel = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL);
    String searchChainCity = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY);
    String searchChainDistrict = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT);
    String searchChainStatus = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS);
    String searchStkNumber = (String)objDataHashMap.get(DCMInterfaceKey.INPUT_SEARCH_TEXT_STK_NUMBER);
    int rowNum = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM));
    int count = Integer.parseInt((String)objDataHashMap.get(DCMInterfaceKey.INPUT_HIDDEN_COUNT));
   Utility.logger.debug("The Row Num issssssssssss " + rowNum);
  %>
   <CENTER>
      <H2> Chain Details </H2>
    </CENTER>

  <form name='DCMform' id='DCMform' action='' method='post'>

  <%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_ROW_NUM+"\""+
                  " value=\""+rowNum+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_COUNT+"\""+
                  " value=\""+count+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_CODE+"\""+
                  " value=\""+searchChainCode+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_LEVEL+"\""+
                  " value=\""+searchChainLevel+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_CHANNEL+"\""+
                  " value=\""+searchChainChannel+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_PAYMENT_LEVEL+"\""+
                  " value=\""+searchChainPaymentLevel+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_CITY+"\""+
                  " value=\""+searchChainCity+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_DISTRICT+"\""+
                  " value=\""+searchChainDistrict+"\">");

  out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_CHAIN_STATUS+"\""+
                  " value=\""+searchChainStatus+"\">");

    out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_STK_NUMBER+"\""+
                  " value=\""+searchStkNumber+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  String chainId = "";
  String chainCode = "";
  String chainName = "";
  String chainMail = "";
  String chainPhone = "";
  String chainChannelId = "";
  String chainLevelId = "";
  String chainPaymentLevelId = "";
  String chainCityId = "";
  String chainDistrictId = "";
  String chainStatusId = "";
  String stkNumber = "";
  String chainRank = "";
  String chainAddress = "";

  if(chainModel!=null)
  {
    nextAction = DCMInterfaceKey.ACTION_UPDATE_GEN_DCM;
    chainId = chainModel.getDcmId();
    chainCode = chainModel.getDcmCode();
    chainName = chainModel.getDcmName();
    chainChannelId = chainModel.getChannelId();
    chainLevelId = chainModel.getDcmLevelId();
    chainPaymentLevelId = chainModel.getDcmPaymentLevelId();
    if(chainPaymentLevelId == null)chainPaymentLevelId ="";
    chainCityId = chainModel.getDcmCityId();
    if(chainCityId == null)chainCityId="";
    chainDistrictId = chainModel.getDcmDistrictId();
    if(chainDistrictId == null)chainDistrictId="";
    chainStatusId = chainModel.getDcmStatusId();
    if(chainStatusId == null)chainStatusId="";
    chainMail = chainModel.getDcmMail();
    if(chainMail == null)chainMail = "";
    chainPhone = chainModel.getDcmPhone();
    if(chainPhone == null)chainPhone = "";
    chainRank = chainModel.getDcmRank();
    if(chainRank == null)chainRank = "";
    chainAddress = chainModel.getDcmAddress();
    if(chainAddress == null)chainAddress = "";
    stkNumber = chainModel.getStkNumber();
    if(stkNumber == null)stkNumber = "";
    }

out.println("<input type=\"hidden\" name=\""+DCMInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
                  " value=\""+chainId+"\">"); 
  %>
  <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
     <tr>
<td class=TableHeader nowrap>Chain Code</td>
<td class=TableTextNote><input type="text" name="search_text_dcm_code" size="20" value="<%=chainCode%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Chain Name</td>
<td class=TableTextNote><input type="text" name="search_text_dcm_name" size="20" value="<%=chainName%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Chain Email</td>
<td class=TableTextNote><input type="text" name="search_text_dcm_email" size="30" value="<%=chainMail%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Chain Phone</td>
<td class=TableTextNote><input type="text" name="search_text_dcm_phone" size="20" value="<%=chainPhone%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Chain Rank</td>
<td class=TableTextNote><input type="text" name="search_text_dcm_rank" size="20" value="<%=chainRank%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap >Chain Address</td>
<td class=TableTextNote ><input type="text" name="search_text_dcm_address" size="50" value="<%=chainAddress%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>STK Number</td>
<td class=TableTextNote><input type="text" name="search_text_stk_number" size="20" value="<%=stkNumber%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Chain Channel</TD>
<td class=TableTextNote>
<select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CHANNEL%>">
<option value=""></option>
<%
  String chainChannelName = "";
  for(int i=0;i<vecChainChannelList.size();i++)
  {
    chainChannelModel chainChannelModel = (chainChannelModel)vecChainChannelList.get(i);
    String strchainChannelId = chainChannelModel.getChannelId();
    String strchainChannelName = chainChannelModel.getChannelName();
    String chainChannelSelected = "";
    if(chainChannelId !=null)
    {
      if(chainChannelId.compareTo(strchainChannelId)==0)
      { 
        chainChannelSelected = "selected";
        chainChannelName = strchainChannelName;
      }
    }
%>
<option value="<%=strchainChannelId%>" <%=chainChannelSelected%>><%=strchainChannelName%></option>
<%
  }
%>
</select>
</td>
</tr>

<tr>
<td class=TableHeader nowrap>Chain Level</TD>
<td class=TableTextNote>
<select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_LEVEL%>">
<option value=""></option>
<%
  String chainLevelName = "";
  for(int j=0;j<vecChainLevelList.size();j++)
  {
    chainLevelModel chainLevelModel = (chainLevelModel)vecChainLevelList.get(j);
    String strchainLevelId = chainLevelModel.getDcmLevelId();
    String strchainLevelName = chainLevelModel.getDcmLevelName();
    String chainLevelSelected = "";
    if(chainLevelId!=null)
    {
      if(chainLevelId.compareTo(strchainLevelId)==0)
      { 
        chainLevelSelected = "selected";
        chainLevelName = strchainLevelName;
      }
    }
%>
<option value="<%=strchainLevelId%>" <%=chainLevelSelected%>><%=strchainLevelName%></option>
<%
  }
%>
</select>
</td>
</tr>
            
<tr>
<td class=TableHeader nowrap>Chain Payment Level</TD>
<td class=TableTextNote>
<select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_PAYMENT_LEVEL%>">
<option value=""></option>
<%
  String chainPaymentLevelName = "";
  for (int k=0; k<vecChainPaymentLevelList.size(); k++)
  {
    chainPaymentLevelModel chainPaymentLevelModel = (chainPaymentLevelModel)vecChainPaymentLevelList.get(k); 
    String strchainPaymentLevelId = chainPaymentLevelModel.getDcmPaymentLevelId();
    String strchainPaymentLevelName = chainPaymentLevelModel.getDcmPaymentLevelName();
    String chainPaymentLevelSelected = "";
    if(chainPaymentLevelId!=null)
    {
      if(chainPaymentLevelId.compareTo(strchainPaymentLevelId)==0)
      { 
        chainPaymentLevelSelected = "selected";
        chainPaymentLevelName = strchainPaymentLevelName;
      }
    }
%>
<option value="<%=strchainPaymentLevelId%>" <%=chainPaymentLevelSelected%>><%=strchainPaymentLevelName%></option>
<%
  }
%>
</select>
</td>
</tr>

<td class=TableHeader nowrap>Chain City</TD>
<td class=TableTextNote>
<select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_CITY%>">
<option value=""></option>
<%
  String chainCityName = "";
  for (int l=0; l<vecChainCityList.size(); l++)
  {
    chainCityModel chainCityModel = (chainCityModel)vecChainCityList.get(l);
    String strchainCityId = chainCityModel.getCityId();
    String strchainCityName = chainCityModel.getCityName();
    String chainCitySelected = "";
    if(chainCityId!=null)
    {
      if(chainCityId.compareTo(strchainCityId)==0)
      { 
        chainCitySelected = "selected";
        chainCityName = strchainCityName;
      }
    }
%>
<option value="<%=strchainCityId%>" <%=chainCitySelected%>><%=strchainCityName%></option>
<%
  }
%>
</select>
</td>
</tr>  

<td class=TableHeader nowrap>Chain District</TD>
<td class=TableTextNote>
<select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_DISTRICT%>">
<option value=""></option>
<%
  String chainDistrictName = "";
  for (int m=0; m<vecChainDistrictList.size(); m++)
  {
    chainDistrictModel chainDistrictModel = (chainDistrictModel)vecChainDistrictList.get(m); 
    String strchainDistrictId = chainDistrictModel.getDistrictId();
    String strchainDistrictName = chainDistrictModel.getDistrictName();
    String chainDistrictSelected = "";
    if(chainDistrictId!= null)
    {
      if(chainDistrictId.compareTo(strchainDistrictId)==0)
      { 
        chainDistrictSelected = "selected";
        chainDistrictName = strchainDistrictName;
      }
    }
%>
<option value="<%=strchainDistrictId%>" <%=chainDistrictSelected%>><%=strchainDistrictName%></option>
<%
  }
%>
</select>
</td>
</tr>

<td class=TableHeader nowrap>Chain Status</TD>
<td class=TableTextNote>
<select name="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS%>" id="<%=DCMInterfaceKey.INPUT_SEARCH_SELECT_CHAIN_STATUS%>">
<option value=""></option>
<%
  String chainStatusName = "";
  for (int n=0; n<vecChainStatusList.size(); n++)
  {
    chainStatusModel chainStatusModel = (chainStatusModel)vecChainStatusList.get(n); 
    String strchainStatusId = chainStatusModel.getDcmStatusId();
    String strchainStatusName = chainStatusModel.getDcmStatusName();
    String chainStatusSelected = "";
    if(chainStatusId!= null)
    {
      if(chainStatusId.compareTo(strchainStatusId)==0)
      { 
        chainStatusSelected = "selected";
        chainStatusName = strchainStatusName;
      }
    }
%>
<option value="<%=strchainStatusId%>" <%=chainStatusSelected%>><%=strchainStatusName%></option>
<%
  }
%>
</select>
</td>
</tr>
</table>
<br>
<center>
<%

  
  out.print("<INPUT class=button type=button value=\" save \" name=\"save\" id=\"save\" onclick=\"document.DCMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"+
                  "checkbeforSubmit();\">");
                 
  out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
</center>