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
         import="com.mobinil.sds.core.system.sop.equations.dto.*"
         import="com.mobinil.sds.core.system.sop.equations.model.*"
         import="com.mobinil.sds.core.system.sop.schemas.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
    </head>
  <body>
<%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

  String strChannelId = (String)objDataHashMap.get(SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID);
  Utility.logger.debug("Channel id isssssssssssssssss" + strChannelId);

  Vector vecProductStock = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);       
  Vector vecEquationProductStock = (Vector)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION_2);       
  
  EquationDTO dtoEquation = (EquationDTO)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);       
  Vector equationDetail = dtoEquation.getVecEquation();

%>
    <CENTER>
      <H2> Assign Equations To Product </H2>
    </CENTER>
<form name='SOPform' id='SOPform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  out.println("<input type=\"hidden\" name=\""+SOPInterfaceKey.INPUT_HIDDEN_CHANNEL_ID+"\""+
                  " value=\""+strChannelId+"\">"); 
%>                  
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
        <TD width="10%" noWrap align=middle>Active Amount</TD>
        <TD width="10%" noWrap align=middle>Equation</TD>
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
            String isQuotaItem = productModel.getIsQuotaItem();
            String stockProductCode = productModel.getStockProductCode();
            
            String activeAmount = productModel.getActiveAmount();
            String physicalAmount = productModel.getPhysicalAmount();
            String importDate = productModel.getImportDate().substring(0,10);
            
%>
      <TR class=TableTextNote>
        <TD width="10%" noWrap align=middle><%=stockProductCode%></TD>
        <td width="10%" nowrap align=middle><%=productNameEnglish%></td>
        <td width="10%" nowrap align=middle><%=productNameArabic%></td>
        <TD width="10%" noWrap align=middle><%=activeAmount%></TD>
        <TD width="10%" noWrap align=middle>
          <select name="<%=SOPInterfaceKey.INPUT_SELECT_EQUATION_ID%>_<%=stockProductCode%>" id="<%=SOPInterfaceKey.INPUT_SELECT_EQUATION_ID%>_<%=stockProductCode%>">
          <option value=""></option>
<%
          for(int k=0;k<equationDetail.size();k++)
          {
            EquationModel equationModel =  (EquationModel)equationDetail.get(k);
            int equationId = equationModel.getEquationId();
            String strEquationId = equationId+"";
            String equationName = equationModel.getEquationTitle();
            String equationDescription = equationModel.getEquationDescription();
            if(equationDescription == null)equationDescription = "";
            int equationStatusId = equationModel.getEquationStatusId();
            String equationStatusName = equationModel.getEquationStatusName();
            String equationProductSelected = "";
            
            for(int j=0;j<vecEquationProductStock.size();j++)
            {
              EquationProductStockModel equationProductStockModel = (EquationProductStockModel)vecEquationProductStock.get(j);
              String XXequationProductStockId = equationProductStockModel.getEquationProductStockId();
              String XXequationId = equationProductStockModel.getEquationId();
              String XXproductCode = equationProductStockModel.getProductCod();
              if(XXproductCode.compareTo(stockProductCode)==0 && XXequationId.compareTo(strEquationId)==0)
              {
                equationProductSelected = "selected";
              }
            }
            %>
            <option value="<%=equationId%>" <%=equationProductSelected%>><%=equationName%></option>
            <%
          }
%>
          </select>
        </TD>
      </tr>
<%
    }
%>
    </table>

    <br><br>
    <center>
<%
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"save\" id=\"save\" onclick=\"document.SOPform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+SOPInterfaceKey.ACTION_SAVE_ASSIGN_EQUATION_TO_PRODUCTS+"';"+
                  "SOPform.submit();\">");

    out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
%>
    </center>
  </form>
</body>
</html>
