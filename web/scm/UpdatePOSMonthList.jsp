<%@page import="com.mobinil.sds.web.interfaces.scm.SCMInterfaceKey"%>
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
Update Current POS Month List
</title>

</head>
<body>
<br>
        <br>
        <div align="center">
        <h2>Update Current POS Month List</h2></div>
        <br>
        <br>

<center>
<%
   HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
   String userID = "";
   
   String str1 = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
   String str2 = (String)dataHashMap.get("USER_ID");
   String str3 = (String) request.getSession().getAttribute(InterfaceKey.HASHMAP_KEY_USER_ID);
   
   System.out.println("in Show Crosstab JSP : HASHMAP_KEY_USER_ID "+str1+" and USER_ID "+str2+" session HASHMAP_KEY_USER_ID "+str3);
   
   if(str1==null)
   {
       if(str2==null)
       {
           if(str3==null)
           {
               userID = "";
           }
           else
               userID = str3;
       }
       else
           userID = str2;
   }
   else
       userID = str1;
       
   System.out.println("user id in crosstab jsp is Finally : "+userID);
   dataHashMap.put(InterfaceKey.HASHMAP_KEY_USER_ID, userID);
   
   String Slach = System.getProperty("file.separator");
   String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);
   
   
   
   String monthsYears = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_LIST_COLLECTION);
   //Hashtable revenueTable = (Hashtable) dataHashMap.get(InterfaceKey.HASHMAP_KEY_COLLECTION);
   //DCMDto dcmDto = (DCMDto) dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
   
   String month =(String) dataHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_MONTH);
   String year = (String) dataHashMap.get(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);
   String list = (String) dataHashMap.get(AdministrationInterfaceKey.CONTROL_SELECT_LIST);

   if (list==null)
       list = "";
   if (year==null)
   year="";
   if (month==null)
   month="";
   else
   month=(Integer.parseInt(month)-1)+"";
   
   
   
   
        
   out.println("<form id=\"SheetRevenue\" action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetRevenue\" method=\"post\">"); 
   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" "+
                   "value=\"\">");

   out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\" "+
                  "value="+userID+">");
   
   out.println("<input type=\"hidden\" id=\""+InterfaceKey.HASHMAP_KEY_LIST_COLLECTION+"\" name=\""+InterfaceKey.HASHMAP_KEY_LIST_COLLECTION+"\" "+
                  "value="+monthsYears+">");

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
    
    
    
    
    
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Email Date");
    out.println("</TD>");
    out.println("<TD>");
    %>
    <input type="text" id="email_date" name="<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_EMAIL_DATE);%>" value=""  >
    <b>e.g. Jan_14, jan_14</b>
    <% 
    
    //out.println("<input type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+"\" value="+year+"  >");
    out.println("</TD>");    
    out.println("</TR>");
    
    
    
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Email Subject");
    out.println("</TD>");
    out.println("<TD>");
    %>
    <input type="text" id="email_date" name="<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_EMAIL_SUBJECT);%>" value=""  >
    <% 
    
    //out.println("<input type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+"\" value="+year+"  >");
    out.println("</TD>");    
    out.println("</TR>");  
    
    
    
    
    
    
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Month");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println(" <select id=\""+AdministrationInterfaceKey.CONTROL_SELECT_MONTH+"\" name=\""+AdministrationInterfaceKey.CONTROL_SELECT_MONTH+"\" size=\"1\">");
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
      out.println("<script>document.SheetRevenue."+AdministrationInterfaceKey.CONTROL_SELECT_MONTH+".options["+month+"].selected=true;" + "</script>");
    }
    out.println("</TD>");
    out.println("</TR>"); 
    
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("List");
    out.println("</TD>");        
    out.println("<td nowrap align=left>");
    out.println(" <select id=\""+AdministrationInterfaceKey.CONTROL_SELECT_LIST+"\" name=\""+AdministrationInterfaceKey.CONTROL_SELECT_LIST+"\" size=\"1\">");
    out.println("<option value='EX'>Exclusive (EX)</option>");
    out.println("<option value='QC'>Quality Club (QC)</option>");
    out.println("<option value='L1'>Level One (L1)</option>");
    out.println("</select>");
    if (list.compareTo("")!=0)
    {
      out.println("<script>document.SheetRevenue."+AdministrationInterfaceKey.CONTROL_SELECT_LIST+".options["+list+"].selected=true;" + "</script>");
    }
    out.println("</TD>");
    out.println("</TR>");    
    
    
    
    
    
    
    
    
    
    out.println("<TR>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("Year");
    out.println("</TD>");
    out.println("<TD>");
    %>
    <input type="text" id="TheYear" name="<%out.print(AdministrationInterfaceKey.CONTROL_INPUT_YEAR);%>" value="<%out.print(year);%>"  >
    <% 
    
    //out.println("<input type=\"text\" name=\""+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+"\" value="+year+"  >");
    out.println("</TD>");    
    out.println("</TR>");                
    
    
    
    
    
    
    out.println("</TR>");
    
    
    
    
    
    
    
    
    out.println("</TR>");
    out.println("</table>");
    out.println("<center>");
    out.println("<input class=button type=\"button\" name=\"View\" value=\"   Update   \" ");
    /*out.print(" onclick=\"if (checkBeforeView(document.SheetRevenue."+AdministrationInterfaceKey.CONTROL_INPUT_YEAR+")){ if (checkMonthInYear()==true){ document.SheetRevenue."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    AdministrationInterfaceKey.ACTION_EXPORT_LISTS_CROSSTAB+"'; document.SheetRevenue.submit();} } else alert('Please Enter A Valid Year');\">");*/
    
    

    out.print(" onclick=\"if (checkBeforeSubmit(document.SheetRevenue."+AdministrationInterfaceKey.CONTROL_INPUT_POS_CODE+")==true){document.SheetRevenue."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    SCMInterfaceKey.ACTION_UPDATE_POS_MONTH_LIST+"'; document.SheetRevenue.submit();} else alert('Please Enter A Valid POS Code');\">");

    
    /*out.print(" onclick=\"{ document.SheetRevenue."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+
    AdministrationInterfaceKey.ACTION_EXPORT_LISTS_CROSSTAB+"'; document.SheetRevenue.submit();} \">");*/
    
    
    out.println("<input class=button id=\"Bck\" type=\"button\" name=\"Bck\" value=\"   Back   \" ");
    out.print(" onclick=\"back();\">");
    
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
    
     function back ()
        {
            /*document.SheetRevenue.action="com.mobinil.sds.web.controller.WebControllerServlet?";
            document.SheetRevenue.action=document.SheetRevenue.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+'<%=SCMInterfaceKey.ACTION_SHOW_SAVE_LISTS%>';*/
            document.SheetRevenue.action = '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_SHOW_SAVE_LISTS);%>';                                                           
            document.SheetRevenue.submit();
            
        }
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
