<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sop.*"
         import="com.mobinil.sds.core.system.sop.schemas.dao.*"
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
 function IsNumeric(sText)
    {
       var ValidChars = "0123456789.";
       var IsNumber=true;
       var Char;
       for (i = 0; i < sText.length && IsNumber == true; i++) 
          { 
          Char = sText.charAt(i); 
          if (ValidChars.indexOf(Char) == -1) 
             {
             IsNumber = false;
             }
          }
       return IsNumber;
    }
  function checkBeforeSubmit()
  {
   if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PRODUCT_CODE%>.value") == "")
      {
        alert("Product Code must not be empty.");
        return;
      }
      if(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>.value") == "")
      {
        alert("Physical Amount must not be empty.");
        return;
      }
      if(!IsNumeric(eval("document.SOPform.<%=SOPInterfaceKey.INPUT_TEXT_PHYSICAL_AMOUNT%>.value")))
      {
         alert("Physical Amount must be a number.");
         return; 
      }
      SOPform.submit();
    }
  </script>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
  ProductModel prodcutModel = (ProductModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
%> 
<CENTER>
      <H2> LCS Product  </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  

  String productID = "";
  String productCode = "";
  String physicalAmount = "";
  String nextAction = SOPInterfaceKey.ACTION_SAVE_PRODUCT_LCS;
  if (prodcutModel!=null)
  {
    nextAction = SOPInterfaceKey.ACTION_UPDATE_PRODUCT_LCS;
    productID = prodcutModel.getProductId();
    productCode = prodcutModel.getProductCode();
    physicalAmount = prodcutModel.getPhysicalAmount();
  }

      out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_PRODUCT_ID+"\""+
      " value=\""+productID+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>Product Code</td>
<td class=TableTextNote><input type="text" name="text_product_code" value="<%=productCode%>"></td>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Physical Amount</td>
<td class=TableTextNote><input type="text" name="text_physical_amount" value="<%=physicalAmount%>"></td>
</td>
</tr>
</table>
</center>
<br>
<center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"
      +"checkBeforeSubmit();\">");

      out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>



</center>
</form>
</body>
</html>
                  