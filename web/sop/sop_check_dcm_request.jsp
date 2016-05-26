<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.requests.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  
  String strDcmCode = "";
  String strDcmName = "";
  String strRequestCreationDate = "";
  String strRequestStatus = "";

  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE))
  strDcmCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_CODE);
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME))
  strDcmName = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_NAME);
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE))
  strRequestCreationDate = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_CREATION_DATE);
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS))
  strRequestStatus = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_REQUEST_STATUS);

  Vector vecRequestDetail = new Vector();
  if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_COLLECTION))
  vecRequestDetail = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
  String appName = request.getContextPath();
%>  
<center>
<TABLE border=0 cellSpacing=0 cellPadding=0>
              <tr>
                <td><IMG src="<%out.print(appName);%>/resources/img/sds_mobinil.bmp"></td>
                </tr>
                </table>
                </center>
                <br><br>
    <CENTER>
    <CENTER>
      <H2> Requests Detail </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
%>
    <%
    //if(vecRequestDetail.size()>0)
    //{
    %>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
      <td>DCM Code</td>
      <td>DCM Name</td>
      <td>Request Creation Date</td>
      <td>Request Status</td>
    </tr>
    <tr>
      <td><%=strDcmCode%></td>
      <td><%=strDcmName%></td>
      <td><%=strRequestCreationDate%></td>
      <td><%=strRequestStatus%></td>
    </tr> 
    </table>
    <br>
   
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
           <tr class=TableHeader>
            <td>Product Name</td>
            <td>Price</td>
            <td>Amount</td>
            <td>Total</td>
            
          </tr>
    <%
    //boolean boIsQuotaItem = false;
    //boolean boIsNonQuotaItem = false;
    //}
    for(int i=0;i<vecRequestDetail.size();i++)
    {
      RequestDetailModel requestDetailModel = (RequestDetailModel)vecRequestDetail.get(i);
      String requestDetailId = requestDetailModel.getRequestDetailId();
      String requestId = requestDetailModel.getRequestId();
      String schemaProductId = requestDetailModel.getSchemaProductId();
      String minimumLimit = requestDetailModel.getMinimumLimit();
      String maximumLimit = requestDetailModel.getMaximumLimit();
      String productAmount = requestDetailModel.getProductAmount();
      String hasQuantity = requestDetailModel.getHasQuantity();
      String productNameEnglish = requestDetailModel.getProductNameEnglish();
      String productNameArabic = requestDetailModel.getProductNameArabic();
      String productPrice = requestDetailModel.getProductPrice();
      String salesTax = requestDetailModel.getSalesTax();
      String productWeight = requestDetailModel.getProductWeight();
      String isPointItemAnswer = "";
      int intProductPrice = Integer.parseInt(productPrice);
      int intProductAmount = Integer.parseInt(productAmount);
      int total = intProductPrice * intProductAmount;
      if(productAmount.compareTo("0")!=0)
      {
      //String isPointItem = requestDetailModel.getIsPointItem();
      //if(isPointItem.compareTo("1")==0){isPointItemAnswer = "YES";}
      //else if(isPointItem.compareTo("0")==0){isPointItemAnswer = "NO";}
      //String isQuotaItemAnswer = "";
      //String isQuotaItem = requestDetailModel.getIsQuotaItem();
      //if(isQuotaItem.compareTo("1")==0)
     // {
      //isQuotaItemAnswer = "YES";
        //if(!boIsQuotaItem)
        //{
          //boIsQuotaItem = true;
          //if(boIsNonQuotaItem)
          //{
%>
<tr>
      <td width=25%><%=productNameEnglish%></td>
      <td width=25%><%=productPrice%></td>
      <td width=25%><%=productAmount%></td>
      <td width=25%><%=total%> L.E</td>
    </tr>
    <%
    }
    }%>
          </table>
          <br>
<%
          //}
%>
       <!--TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
          <tr>
            <td colspan=7>Quota Items</td>
          </tr>
          <tr class=TableHeader>
            <td>Product Name</td-->
            <!--td>Weight</td>
            <td>Is Point Item</td-->
            <!--td>Price</td>
            <td>Amount</td>
            <!--td>Min Limit</td>
            <td>Max Limit</td-->
          <!--/tr-->
<%
        /*}
      }
      else if(isQuotaItem.compareTo("0")==0)
      {
      isQuotaItemAnswer = "NO";
        if(!boIsNonQuotaItem)
        {
          boIsNonQuotaItem = true;
          if(boIsQuotaItem)
          {*/
%>
          <!--/table>
          <br-->
<%
          //}
%>
         <!--TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
          <tr>
            <td colspan=7>Non Quota Items</td>
          </tr>
           <tr class=TableHeader>
            <td>Product Name</td>
            <!--td>Weight</td>
            <td>Is Point Item</td-->
            <!--td>Price</td>
            <td>Amount</td>
            <!--td>Min Limit</td>
            <td>Max Limit</td-->
          <!--/tr-->
<%
        //}      
      //}

      %>
    <!--tr class=TableTextNote>
    <!--/tr-->        
      <%
    //}
    %>  
    <!--/table-->
    <%
    //}
    //else
    //{
    %>
    <!--TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
    <tr class=TableHeader>
      <td>DCM Code</td>
      <td>DCM Name</td>
    </tr>
    <tr>
      <td><%=strDcmCode%></td>
      <td><%=strDcmName%></td>
    </tr> 
    </table>
    <h3>DCM doesn't have any requests...</h3-->
   
    <br><br>
    <center>
    <input type="button" class="button" value=" Print " onclick="window.print();">
    </center>
</body>
</html>
