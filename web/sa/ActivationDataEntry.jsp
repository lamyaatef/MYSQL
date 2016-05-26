<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import="com.mobinil.sds.core.system.sa.product.dao.*"
         import="com.mobinil.sds.core.system.sa.product.model.*"         
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"         
         import ="com.mobinil.sds.web.interfaces.sa.*"         
         import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import ="com.mobinil.sds.core.system.gn.dcm.model.*"
         import ="com.mobinil.sds.core.system.sa.activation.model.*"
        
         
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%    String appName = request.getContextPath();%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
    <SCRIPT language=JavaScript src="../resources/js/FormCheck.js" type=text/javascript></SCRIPT>
<title>
Activation Data
</title>
<h2>
Activation Data
</h2>
</head>
<body>


<script>


function IsNumeric(sText)
{
   var ValidChars = "0123456789";
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
   </script>


<script>
function checkBeforeView(year)
{
 var yearValue  =year.value;
 if (IsNumeric(yearValue) && yearValue.length==4)
 return true; 
 else return false; 
}


function checkBeforeUpdate()
{
//alert(document.Activation.<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);%>);
//alert(document.Activation.<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);%>.length);

 var fieldsArray = document.Activation.<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);%>;
for ( i = 0 ; i <fieldsArray.length; i++)
{
 if (isFloat(fieldsArray[i].value) || isEmpty(fieldsArray[i].value))
 {
 }
 else
 {
 
 return false; 
 }
}
return true; 
}
</script>
<center>
<%
   HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
   String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

   Hashtable activationTable = (Hashtable) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);

   DCMDto dcmDto = (DCMDto) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);

   Vector productVector = (Vector)dataHashMap.get(AdministrationInterfaceKey.PRODUCT_VECTOR); 
   String month =(String) dataHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
   String year = (String) dataHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
   String productId = (String) dataHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_PRODUCT_ID);

   if (year==null)
   year="";
   if (month==null)
   month="";
   else
   month=(Integer.parseInt(month)-1)+"";

   
        
   out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"Activation\" method=\"post\">"); 

   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" "+
                   "value=\"\">");

   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                  "value="+userID+">");

    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");

    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Product");
    out.println("</TD>"); 
    out.println("<td   align=left>");

    out.println(" <select  name=\""+AdministrationInterfaceKey.CONTROL_SELECT_PRODUCT_ID+"\" size=\"1\">");
    for (int i = 0 ; i < productVector.size() ;i++)
    {
     ProductModel product = (ProductModel) productVector.get(i);
     if (product.getProductId() == productId)
     {
     out.println("<option value="+product.getProductId()+" selected>"+product.getProductName()+"</option>");
     }
     else
     {
     out.println("<option value="+product.getProductId()+">"+product.getProductName()+"</option>");
     }
    }
    out.println("</select>");
    
    out.println("</TD>");     
    out.println("</tr>");
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Month");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println(" <select  name=\""+AdministrationInterfaceKey.CONTROL_SELECT_MONTH+"\" size=\"1\">");
    out.println("<option value=1>JANUARY</option>");
    out.println("<option value=2>FEBRUARY</option>");
    out.println("<option value=3>MARCH</option>");
    out.println("<option value=4>APRIL</option>");
    out.println("<option value=5>MAY</option>");
    out.println("<option value=6>JUNE</option>");
    out.println("<option value=7>JULY</option>");
    out.println("<option value=8>AUGUST</option>");
    out.println("<option value=9>SEPTEMBER</option>");
    out.println("<option value=10>OCTOBER</option>");
    out.println("<option value=11>NOVEMBER</option>");
    out.println("<option value=12>DECEMBER</option>");        
    out.println("</select>");
    if (month.compareTo("")!=0)
    {
      out.println("<script>document.Activation."+AdministrationInterfaceKey.CONTROL_SELECT_MONTH+".options["+month+"].selected=true;" + "</script>");
    }
    out.println("</TD>");
    out.println("</TR>");    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Year");
    out.println("</TD>");
    out.println("<TD>");
    out.println("<input type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+"\" value="+year+">");
    out.println("</TD>");    
    out.println("</TR>");                
    out.println("</TR>");
    out.println("</table>");
    out.println("<center>");
    out.println("<input class=button type=\"button\" name=\"View\" value=\"   View   \" ");
    out.print("onclick=\"if (checkBeforeView(document.Activation."+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+")){ document.Activation."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    AdministrationInterfaceKey.ACTION_SEARCH_ACTIVATION+"'; document.Activation.submit(); } else alert('Please Enter A Valid Year');\">");
    out.println("</center>");    
    out.println("<br><br>");

    if (dcmDto!=null)
    {
      out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=1>");
      out.println("<tr class=TableHeader>");
      out.println("<td  nowrap align=center> DCM Name </td>" );
      out.println("<td  nowrap align=center> Amount </td>" );    
      out.println("</tr>");
    
    
      for (int i = 0 ; i <dcmDto.getDcmModelsSize();i++)
      {
        DCMModel model = dcmDto.getDcm(i);        
        out.println("<TR>");
        out.println("<td nowrap align=center>");
        out.println(model.getDcmName());      
        out.println("</TD>");

        ActivationModel activationModel = (ActivationModel) activationTable.get(model.getDcmId()+"");
        String value ="";
        String rowId = "";
        if (activationModel ==null)
        {
          value="";
        }
        else
        {
          value=activationModel.getAmount() +"";
          rowId =  activationModel.getRowID();
        }
        
        out.println("<td nowrap align=center>");
        
        out.println("<input type=\"HIDDEN\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_ACTIVATION_ID+"\" value=\""+rowId+"\">");      
        out.println("<input type=\"HIDDEN\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_DCM_ID+"\" value=\""+model.getDcmId()+"\">");
        out.println("<input type=\"HIDDEN\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT_DB_VALUE+"\" value=\""+value+"\">");
        out.println("<input type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT+"\" value=\""+value+"\">");        
        out.println("</TD>");

        out.println("</tr>");            
      }
  
      out.println("</table>");

      out.println("<center>");
      out.println("<input class=button type=\"button\" name=\"Update\" value=\"   Update   \" ");
      out.print("onclick=\"if (checkBeforeUpdate()) {document.Activation."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
      AdministrationInterfaceKey.ACTION_UPDATE_ACTIVATION+"'; document.Activation.submit();} else {alert('one or more data entry value is Invalid');} \">");
      out.println("</center>");
    }  
    
    out.println("</form>");    
%>
</center>


</body>
</html>
