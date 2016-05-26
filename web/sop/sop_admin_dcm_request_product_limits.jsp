<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"

         import="com.mobinil.sds.core.system.sop.schemas.model.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<script>
  function checkBeforeSubmit()
  {
    var inputs = document.getElementsByTagName("input");
    for(var i=0;i<inputs.length;i++)
      {
        var inputsubstring = inputs[i].name.split("_");
        var productRequestLimitId = inputsubstring[3];
        if(inputsubstring[0]+"_"+inputsubstring[1]+"_"+inputsubstring[2] == "<%=SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT%>")
        {
          productmaximumlimit = inputs[i].value;
          if(productmaximumlimit == "")
          {
            productamount = 0;
          }
          productmaximumlimit = parseInt(productmaximumlimit);
          var activeAmountValue = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_ACTIVE_AMOUNT%>_"+productRequestLimitId+".value");
          var physicalAmountValue = eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>_"+productRequestLimitId+".value");
          activeAmountValue = parseInt(activeAmountValue);
          physicalAmountValue = parseInt(physicalAmountValue);
          
          //if(productmaximumlimit > physicalAmountValue)
          //{
            //alert("Maximum Limit Exceeds Physical Amount");
            //return;
          //}
        }
      } 
      SOPform.submit();          
  }
</script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  
  String strDcmID = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_DCM_ID);  
  
  DCMModel dcmModel = (DCMModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  String strDcmName = dcmModel.getDcmName();
  String strDcmCode = dcmModel.getDcmCode();
  
  Vector vecDCMRequestProductLimit = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);  
%>  
    <CENTER>
      <H2> DCM Product Limits </H2>
    </CENTER>

<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_DCM_ID+"\""+
                  " value=\""+strDcmID+"\">");                 
%>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableHeader>
        <td colspan=2>DCM Name</td>
        <td colspan=2>DCM Code</td>
      </tr>
      <tr>
        <td colspan=2><%=strDcmName%></td>
        <td colspan=2><%=strDcmCode%></td>
      </tr>
    </table>
    <br><br> 
<%
    boolean boIsQuotaItem = false;
    boolean boIsNonQuotaItem = false;
    for(int i=0;i<vecDCMRequestProductLimit.size();i++)
    {
      ProductModel productModel = (ProductModel)vecDCMRequestProductLimit.get(i);
      String productRequestLimitId = productModel.getProductRequestLimitId();
      String schemaProductId = productModel.getSchemaProductId();
      String productId = productModel.getProductId();
      String schemaId = productModel.getSchemaId();
      String lcsProductCode = productModel.getLcsProductCode();
      String isActive = productModel.getIsActive();
      String hasQuantity = productModel.getHasQuantity();
      String productNameEnglish = productModel.getProductNameEnglish();
      String productNameArabic = productModel.getProductNameArabic();
      String productPrice = productModel.getProductPrice();
      String salesTax = productModel.getSalesTax();
      String productWeight = productModel.getProductWeight();
      String isPointItemAnswer = "";
      String isPointItem = productModel.getIsPointItem();
      if(isPointItem.compareTo("1")==0){isPointItemAnswer = "YES";}
      else if(isPointItem.compareTo("0")==0){isPointItemAnswer = "NO";}
      String minimumLimit = productModel.getMinimumLimit();
      String maximumLimit = productModel.getMaximumLimit();
      String stockProductCode = productModel.getStockProductCode();
      String activeAmount = productModel.getActiveAmount();
      String physicalAmount = productModel.getPhysicalAmount();
      String importDate = productModel.getImportDate();
      String isManual = productModel.getIsManual();
      String isQuotaItemAnswer = "";
      String isQuotaItem = productModel.getIsQuotaItem();
      if(isQuotaItem.compareTo("1")==0)
      {
        isQuotaItemAnswer = "YES";
        if(!boIsQuotaItem)
        {
          boIsQuotaItem = true;
          if(boIsNonQuotaItem)
          {
%>
          </table>
          <br>
<%
          }
%>
       <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
          <tr>
            <td colspan=8>Quota Items</td>
          </tr>
          <tr class=TableHeader>
            <td>Product Name English</td>
            <td>Product Weight</td>
            <td>Is Point Item</td>
            <td>Product Price</td>
            <td>Min Limit</td>
            <td>Max Limit</td>
            <td>Active Amount</td>
            <!--td>Physical Amount</td-->
          </tr>  
<%
        }
      }
      else if(isQuotaItem.compareTo("0")==0)
      {
        isQuotaItemAnswer = "NO";
        if(!boIsNonQuotaItem)
        {
          boIsNonQuotaItem = true;
          if(boIsQuotaItem)
          {
%>
          </table>
          <br>
<%
          }
%>
         <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
          <tr>
            <td colspan=8>Non Quota Items</td>
          </tr>
           <tr class=TableHeader>
            <td>Product Name English</td>
            <td>Product Weight</td>
            <td>Is Point Item</td>
            <td>Product Price</td>
            <td>Min Limit</td>
            <td>Max Limit</td>
            <td>Active Amount</td>
            <!--td>Physical Amount</td-->
          </tr>  
<%
        }
      }
%>
    <tr class=TableTextNote>
        <td><%=productNameEnglish%></td>
        <td><%=productWeight%></td>
        <td><%=isPointItemAnswer%></td>
        <td><%=productPrice%></td>
        <td><input type='text' size='10' name='<%=SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT%>_<%=productRequestLimitId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_MINIMUM_LIMIT%>_<%=productRequestLimitId%>' value='<%=minimumLimit%>'></td>
        <td><input type='text' size='10' name='<%=SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT%>_<%=productRequestLimitId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_MAXIMUM_LIMIT%>_<%=productRequestLimitId%>' value='<%=maximumLimit%>'></td>
        <td><input type='hidden' size='10' name='<%=SOPInterfaceKey.INPUT_TEXT_ACTIVE_AMOUNT%>_<%=productRequestLimitId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_ACTIVE_AMOUNT%>_<%=productRequestLimitId%>' value='<%=activeAmount%>'><%=activeAmount%></td>
        <input type='hidden' size='10' name='<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>_<%=productRequestLimitId%>' id='<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>_<%=productRequestLimitId%>' value='<%=physicalAmount%>'>
    </tr> 
<%
    }
%>    
    </table>
    <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Save Limits \" name=\"save\" id=\"save\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SAVE_DCM_REQUEST_PRODUCT_LIMITS+"';"+
                  "checkBeforeSubmit();\">");

    out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_ADMIN_SELECT_DCM_REQUEST_PRODUCT_LIMITS+"';"+
    "SOPform.submit();\">");

%>                  
   </center> 
</form>
</body>
</html>
