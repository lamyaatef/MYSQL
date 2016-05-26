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
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
    </head>
  <body>
<script>
        function getDate()
        {
          var currentTime = new Date();
          var month = currentTime.getMonth() + 1;
          var day = currentTime.getDate();
          var year = currentTime.getFullYear();
          var today = month + "/" + day + "/" + year;
          return today;
        }
        function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }
        function setSearchValues(importDateFrom,importDateTo,productCode,productNameEnglish)
        {
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM%>").value = importDateFrom;
          document.getElementById("<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO%>").value = importDateTo;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE%>").value = productCode;
          document.getElementById("<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH%>").value = productNameEnglish;
        }
        function checkBeforeSubmit()
        {
            var importDateFrom = document.SOPform.<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM%>.value
            var importDateTo = document.SOPform.<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO%>.value
            if(importDateFrom=="*")
            {
              alert("Import Date From must not be empty.");
              return;
            }
            else if(importDateTo=="*")
            {
              alert("Import Date To must not be empty.");
              return;
            }
            SOPform.submit();
        }
</script>        
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id issssssssssssssssss" + strChannelId);
  String warehouseId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID);

  Vector vecProductStock = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

  String searchImportDateFrom = "*";
  String searchImportDateTo = "*";
  String searchProductCode = "";
  String searchProductNameEnglish = "";
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM)){searchImportDateFrom = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO)){searchImportDateTo = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE)){searchProductCode = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE);}
  if(objDataHashMap.containsKey(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH)){searchProductNameEnglish = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH);}
  
%>   
    <CENTER>
      <H2> Stock Products List </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");
                  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
  
  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_WAREHOUSE_ID+"\""+
          " value=\""+warehouseId+"\">"); 
%> 
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <tr class=TableTextNote>  
        <td align=middle colspan=2>Product Code</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE%>' id='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE%>'></td>
        <td align=middle colspan=2>English Name</td>
        <td align=middle><input type='text' name='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH%>' id='<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_NAME_ENGLISH%>'></td>
      </tr>
      <tr class=TableTextNote>  
        <td align=middle colspan=2>Import Date</td>
        <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_FROM%>',getDate());</script></td>
        <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=SOPInterfaceKey.INPUT_SEARCH_TEXT_IMPORT_DATE_TO%>',getDate());</script></td>
      </tr>
      <tr class=TableTextNote>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Search \" name=\"searchproduct\" id=\"searchproduct\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SEARCH_STOCK_PRODUCT+"';"+
                  "checkBeforeSubmit();\">");

        out.print("<INPUT class=button type=button value=\" Clear Values \" name=\"clear\" id=\"clear\" onclick=\"setSearchValues('*','*','','');\">");          
        %>
        </td>
      </tr>
    </table>

    <br><br>
    <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1>
      <%
      if(vecProductStock.size() != 0)
      {
      %>
      <TR class=TableHeader>
        <TD width="10%" noWrap align=middle>Product Code</TD>
        <td width="10%" nowrap align=middle>English Name</td>
        <td width="10%" nowrap align=middle>Arabic Name</td>
        <TD width="10%" noWrap align=middle>Current Active Amount</TD>
        <TD width="10%" noWrap align=middle>Import Date</TD>
        <TD width="10%" noWrap align=middle>Imported Active Amount</TD>
      </TR>
<%
      }
      for (int i=0; i<vecProductStock.size(); i++)
        {
            ProductModel productModel = (ProductModel) vecProductStock.get(i); 
            String schemaProductId = productModel.getSchemaProductId();
            String productId = productModel.getProductId();
            String schemaId = productModel.getSchemaId();
            String lcsProductCode = productModel.getLcsProductCode();
            String isActive = productModel.getIsActive();
            String hasQuantity = productModel.getHasQuantity();
            String productNameEnglish = productModel.getProductNameEnglish();
            if(productNameEnglish == null){productNameEnglish = "";}
            String productNameArabic = productModel.getProductNameArabic();
            if(productNameArabic == null){productNameArabic = "";}
            else
            {
              if(productNameArabic.compareTo("null")==0){productNameArabic = "";}
            }
            String productPrice = productModel.getProductPrice();
            String salesTax = productModel.getSalesTax();
            String productWeight = productModel.getProductWeight();
            String isPointItem = productModel.getIsPointItem();
            String isPointAnswer = "";
            String isPointYesSelected = "";
            String isPointNoSelected = "";
            String isQuotaAnswer = "";
            String isQuotaYesSelected = "";
            String isQuotaNoSelected = "";
            //if(isPointItem.compareTo("1")==0)
            //{
            //  isPointAnswer = "YES";
            //  isPointYesSelected = "selected";
            //}
            //else if(isPointItem.compareTo("0")==0)
            //{
            //  isPointAnswer = "NO"; 
            //  isPointNoSelected = "selected";
            //}
            String isQuotaItem = productModel.getIsQuotaItem();
            /*if(isQuotaItem.compareTo("1")==0)
            {
              isQuotaAnswer = "YES";
              isQuotaYesSelected = "selected";
            }
            else if(isQuotaItem.compareTo("0")==0)
            {
              isQuotaAnswer = "NO";  
              isQuotaNoSelected = "selected";
            }*/
            String stockProductCode = productModel.getStockProductCode();
            String activeAmount = productModel.getActiveAmount();
            String physicalAmount = productModel.getPhysicalAmount();
            String importedActiveAmount = productModel.getImportedActiveAmount();
            if (importedActiveAmount == null)importedActiveAmount = "";
            String importDate = productModel.getImportDate().substring(0,10);
            
%>
      <TR class=<%=InterfaceKey.STYLE[i%2]%>>
        <TD width="10%" noWrap align=middle><%=stockProductCode%></TD>
        <td width="10%" nowrap align=middle><%=productNameEnglish%></td>
        <td width="10%" nowrap align=middle><%=productNameArabic%></td>
        <TD width="10%" noWrap align=middle><%=activeAmount%></TD>
        <TD width="10%" noWrap align=middle><%=importDate%></TD>
        <TD width="10%" noWrap align=middle><%=importedActiveAmount%></TD>
      </tr>
<%
    }
%>
    </table> 
<%
if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  {
    String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_STOCK_PRODUCT)==0)
    {
      out.println("<script>setSearchValues('"+searchImportDateFrom+"','"+searchImportDateTo+"','"+searchProductCode+"','"+searchProductNameEnglish+"');</script>");
    }
  }
%>    
  </body>
</html>
