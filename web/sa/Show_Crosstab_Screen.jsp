<%@page import="com.mobinil.sds.core.system.sa.history.model.PayLevelHistroyModel"%>
<%@ page import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import ="com.mobinil.sds.web.interfaces.cr.*"
         import="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.dao.*"
         import="com.mobinil.sds.core.system.cr.batch.model.*"
         import="com.mobinil.sds.core.system.cr.batch.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import="com.mobinil.sds.core.system.gn.dcm.model.*"
         import="com.mobinil.sds.core.system.ac.authcall.model.*"
         import ="com.mobinil.sds.web.interfaces.sa.*"
         import ="com.mobinil.sds.core.system.gn.dcm.dao.*"
         import ="com.mobinil.sds.core.system.gn.dcm.dto.*"
         import ="com.mobinil.sds.core.system.gn.dcm.model.*"
         import ="com.mobinil.sds.core.system.sa.revenue.model.*"
        
         
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%    String appName = request.getContextPath();%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
      <SCRIPT language=JavaScript src="../resources/js/FormCheck.js" type=text/javascript></SCRIPT>
<title>
Export Lists for POS Code
</title>

</head>
<body>
<br>
        <br>
        <div align="center">
        <h2>Export Lists for POS Code</h2></div>
        <br>
        <br>

<center>
<%
   HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
   String userID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
   String Slach = System.getProperty("file.separator");
   String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);
        
   out.println("<form id=\"SheetRevenue\" action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetRevenue\" method=\"post\">"); 
   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" "+
                   "value=\"\">");

   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                  "value="+userID+">");

   out.println("<input type=\"hidden\" name=baseDirectory id=baseDirectory value=\"" +base+ "\">");    
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
 
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("POS Code");
    out.println("</TD>");
    out.println("<TD>");
    %>
    <input type="text" id="pos_code" name="<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_POS_CODE);%>" value=""  >
    <% 
    
    //out.println("<input type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+"\" value="+year+"  >");
    out.println("</TD>");    
    out.println("</TR>");                
    out.println("</TR>");
    out.println("</table>");
    out.println("<center>");
    out.println("<input class=button type=\"button\" name=\"View\" value=\"   Export   \" ");
    /*out.print(" onclick=\"if (checkBeforeView(document.SheetRevenue."+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+")){ if (checkMonthInYear()==true){ document.SheetRevenue."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    AdministrationInterfaceKey.ACTION_EXPORT_LISTS_CROSSTAB+"'; document.SheetRevenue.submit();} } else alert('Please Enter A Valid Year');\">");*/
    
    

    out.print(" onclick=\"if (checkBeforeSubmit(document.SheetRevenue."+AdministrationInterfaceKey.CONTROL_INPUT_POS_CODE+")==true){document.SheetRevenue."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    AdministrationInterfaceKey.ACTION_EXPORT_LISTS_CROSSTAB+"'; document.SheetRevenue.submit();} else alert('Please Enter A Valid POS Code');\">");

    
    /*out.print(" onclick=\"{ document.SheetRevenue."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    AdministrationInterfaceKey.ACTION_EXPORT_LISTS_CROSSTAB+"'; document.SheetRevenue.submit();} \">");*/
    
    
    out.println("</center>");    
    out.println("<br><br>");
    
    
    
    String historyId = (String) dataHashMap.get(AdministrationInterfaceKey.TEXT_PAY_LEVEL_HISTORY_ID);
    if (historyId != null)
    {
    out.println("<tr class=TableHeader>");
    out.println("<td  nowrap align=center> This Payment Level History Number Is </td>" );
    out.println("<td  nowrap align=center> "+historyId+" </td>" );    
    out.println("</tr>");
 
    }
    
    
    out.println("</form>");
    
%>
</center>


<script>
  function checkMonthInYear(){
  //    alert("checkMonthInYear");
  var bool = true;    
  var myvalue = document.getElementById("<%out.print(InterfaceKey.HASHMAP_KEY_LIST_COLLECTION);%>").value;
  var selectedMonth = document.getElementById("<%out.print(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);%>").value;
  var selectedYear = document.getElementById("TheYear").value;
  
  //alert("returned "+myvalue);
  //alert("selected month "+selectedMonth);
  //alert("selected year "+selectedYear);
  
  myvalue.split("__").forEach(function(myvalue){
  var sub = myvalue.split("_");
  
  var month = sub[0];
  var year = sub[1];
  
  //console.log("year== selectedYear && month==selectedMonth" +  year== selectedYear && month==selectedMonth);
  //console.log("year== selectedYear" +  year + "=>" + selectedYear);
  //console.log("month == selectedMonth" +  month + "=>" + selectedMonth);
  
  if (year=== selectedYear && month==selectedMonth)
  {
      
      if(confirm("date chosen exists, do u wish to overwrite?")){
      
        bool = true;
            }
        }
   
   // alert(year);
//  console.log(year +"=>" + month);
 
});

//alert (bool);
    return bool; 
}
    
</script>

  

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
    function checkBeforeSubmit(code)
{
  //  alert("checkBeforeView");
 var codeValue  =code.value;
 if (codeValue!='' && codeValue.length>=4)
 {
    // alert("checkBeforeView TRUE");
            return true;
 } 
 else {
  //   alert("checkBeforeView FALSE");
     return false;
 } 
}
    
    
    
function checkBeforeView(year)
{
  //  alert("checkBeforeView");
 var yearValue  =year.value;
 if (IsNumeric(yearValue) && yearValue.length==4)
 {
    // alert("checkBeforeView TRUE");
            return true;
 } 
 else {
  //   alert("checkBeforeView FALSE");
     return false;
 } 
}


function checkBeforeUpdate()
{
 var fieldsArray = document.SheetRevenue.<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_AMOUNT);%>;
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

  
  
</body>
</html>
