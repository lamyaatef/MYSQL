<%@ page contentType="Application/vnd.excel;charset=windows-1256"%>

<%@ page import ="javax.servlet.*" 
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
<%
response.addHeader("Content-Disposition", "attachment; filename=report.xls");
%>
<%
    HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

    Vector vecChainList = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

%>

<html>
<head>
   <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1256"/>

</head>
<body>

<Table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
<tr>
<%
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_CODE</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_NAME</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_LEVEL</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_STATUS</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_PAYMENT_LEVEL</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_CHANNEL</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_CITY</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_DISTRICT</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_MAIL</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_PHONE</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_ADDRESS</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">STK_NUMBER</td>");
out.println("<td align=middle bgcolor=\"BLACK\" ><font color=\"#ffffff\">CHAIN_RANK</td>");
%>
</tr>

<%
String chainCode;
String chainName;
String chainLevel;
String chainStatus;
String chainPaymentLevel;
String chainChannel;
String chainCity;
String chainDistrict;
String chainMail;
String chainPhone;
String chainAddress;
String chainStkNumber;
String chainRank;
for(int i = 0 ; i < vecChainList.size() ; i++)
{

  ChainModel chainModel  = (ChainModel)vecChainList.get(i);
  chainCode = chainModel.getDcmCode();
  if (chainCode == null)chainCode = "";
  chainName = chainModel.getDcmName();
  if (chainName == null)chainName = "";
  chainLevel = chainModel.getDcmLevelName();
  if (chainLevel == null )chainLevel = "";
  chainStatus = chainModel.getDcmStatusName();
  if (chainStatus == null)chainStatus = "";
  chainPaymentLevel = chainModel.getDcmPaymentLevelName();
  if (chainPaymentLevel == null)chainPaymentLevel ="";
  chainChannel = chainModel.getChannelName();
  if (chainChannel == null)chainChannel = "";
  chainCity = chainModel.getDcmCityName();
  if (chainCity == null)chainCity = "";
  chainDistrict = chainModel.getDcmDistrictName();
  if (chainDistrict == null)chainDistrict = "";
  chainMail = chainModel.getDcmMail();
  if (chainMail == null)chainMail = "";
  chainPhone = chainModel.getDcmPhone();
  if (chainPhone == null)chainPhone = "";
  chainAddress = chainModel.getDcmAddress();
  if (chainAddress == null)chainAddress = "";
  chainStkNumber = chainModel.getStkNumber();
  if (chainStkNumber == null)chainStkNumber = "";
  chainRank = chainModel.getDcmRank();
  if (chainRank == null)chainRank = "";
  out.println("<tr>");
  
  out.println("<td align=middle >"+chainCode+"</td>");
  out.println("<td align=middle>"+chainName+"</td>");
  out.println("<td align=middle>"+chainLevel+"</td>");
  out.println("<td align=middle>"+chainStatus+"</td>");
  out.println("<td align=middle>"+chainPaymentLevel+"</td>");
  out.println("<td align=middle>"+chainChannel+"</td>");
  out.println("<td align=middle>"+chainCity+"</td>");
  out.println("<td align=middle>"+chainDistrict+"</td>");
  out.println("<td align=middle>"+chainMail+"</td>");
  out.println("<td align=middle>"+chainPhone+"</td>");
  out.println("<td align=middle>"+chainAddress+"</td>");
  out.println("<td align=middle>"+chainStkNumber+"</td>");
  out.println("<td align=middle>"+chainRank+"</td>");
  out.println("</tr>");
  

}

%>

</table>
</body>
</html>
