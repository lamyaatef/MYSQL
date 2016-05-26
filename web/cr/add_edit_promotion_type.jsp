<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.sa.*"

         import="com.mobinil.sds.core.system.cr.bundle.model.*"
%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
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
   if(eval("document.BUNform.<%=AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_ID%>.value") == "")
      {
        alert("Promotion Type ID must not be empty.");
        return;
      }
      if(!IsNumeric(eval("document.BUNform.<%=AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_ID%>.value")))
      {
         alert("Promotion Type ID must be a number.");
         return; 
      }
      if(eval("document.BUNform.<%=AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_NAME%>.value") == "")
      {
        alert("Promotion Type Name must not be empty.");
        return;
      }
      
      BUNform.submit();
    }

    function checkBeforeSubmit2()
  {
   
      if(eval("document.BUNform.<%=AdministrationInterfaceKey.INPUT_TEXT_PROMOTION_TYPE_NAME%>.value") == "")
      {
        alert("Promotion Type Name must not be empty.");
        return;
      }
     
      BUNform.submit();
    }
  </script>

  <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
  
  BundleModel bundleModel = (BundleModel)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
  
%> 
<CENTER>
      <H2> Bundle Promotion Type </H2>
    </CENTER>
<form name='BUNform' id='BUNform' action='' method='post'>
<%
  
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">"); 

  

  String promotionTypeID = "";
  String promotionTypeName = "";
  String nextAction = AdministrationInterfaceKey.ACTION_SAVE_NEW_PROMOTION_TYPE;
  if (bundleModel!=null)
  {
    nextAction = AdministrationInterfaceKey.ACTION_UPDATE_BUNDLE_PROMOTION_TYPE;
    promotionTypeID = bundleModel.getPromotionTypeId();
    promotionTypeName = bundleModel.getPromotionTypeName();
  }

      out.println("<input type=\"hidden\" name=\""+AdministrationInterfaceKey.INPUT_HIDDEN_PROMOTION_TYPE_ID+"\""+
      " value=\""+promotionTypeID+"\">");
%>
<center>
<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1>   
<tr>
<td class=TableHeader nowrap>Promotion Type ID</td>
<% if(nextAction ==  AdministrationInterfaceKey.ACTION_UPDATE_BUNDLE_PROMOTION_TYPE)
{
%>
<td class=TableTextNote><%=promotionTypeID%></td>
<%
}
else
{
%>
<td class=TableTextNote><input type="text" name="text_promotion_type_id" value="<%=promotionTypeID%>"></td>
<%
}
%>
</td>
</tr>
<tr>
<td class=TableHeader nowrap>Promotion Type Name</td>
<td class=TableTextNote><input type="text" name="text_promotion_type_name" value="<%=promotionTypeName%>"></td>
</td>
</tr>
</table>
</center>
<br>
<center>
<%
  if(nextAction ==  AdministrationInterfaceKey.ACTION_UPDATE_BUNDLE_PROMOTION_TYPE)
  {
      out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.BUNform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"
      +"checkBeforeSubmit2();\">");

   out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
   }

   else
   {
   out.print("<INPUT class=button type='button' value=\" Save \" name=\"new\" id=\"new\" onclick=\"document.BUNform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+nextAction+"';"
      +"checkBeforeSubmit();\">");

   out.print("<INPUT class=button type=button value=\" Back \" name=\"back\" id=\"back\" onclick=\"history.go(-1);\">");
   }
%>



</center>
</form>
</body>
</html>
                  
