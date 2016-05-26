<%@ page contentType="text/html;charset=windows-1252"
                import="java.io.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
                import ="com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey"
                import="com.mobinil.sds.core.system.gn.dcm.dto.*"
                import="com.mobinil.sds.core.system.gn.dcm.model.*"
%>



<script>
function IsNumeric(sText)
{
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;

    //if (sText.length!=7 ) //this is to validate that it is a valid sheet serial number 
  //{
  //  isNumber = false;
  //  return isNumber;
  //  }
    
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

function checkValue(rangeOrSerial, serial, serialFrom, serialTo,batchId)
{


 var assignType = rangeOrSerial.value;

 var batchIdCheck = IsNumeric(batchId);  
  if (!batchIdCheck)
  {
  alert('Please Enter a Valid Batch ID');
  
  return false;   
  }
 if (assignType != "<%out.print(ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET);%>" )
 {
  var serialFromCheck = IsNumeric(serialFrom);    
  var serialToCheck = IsNumeric(serialTo);
  if (!serialFromCheck  || !serialToCheck )
  {
  alert('Please Enter a Valid Sheet Serial Number');
  
  return false; 
  }
  else
  {

  serialFromInt = parseInt(serialFrom);
  serialToInt = parseInt(serialTo);
  if (serialFromInt > serialToInt)
  {  
  alert('Not A Valid Range');
  return false ;
  }
  
   
  }
 }
 else
 {   
 var serialCheck = IsNumeric(serial);              
   if (serialCheck ==false)
   {
   alert('Please Enter a Valid Sheet Serial Number');
   
   return false;
   }
 }

  
 return true; 
}
</script>

<%!


  public void showDCMs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    
    out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetSerialDCMAssign\" method=\"post\">");
    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\""+ContractRegistrationInterfaceKey.ACTION_SHEET_SERIAL_LOCAL_AUTH_PERCENTAGE_LIST+"\">");

   

    out.println("<center>");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"70%\" border=1>");
    out.println("<TR >");
    out.println("<td width=\"30%\" class=TableHeader nowrap align=center>");    
    out.println("Btach ID");
    out.println("</TD>");
    out.println("<td  nowrap align=left>"); 
    out.println("<input type='text' name='"+ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT+"' id='"+ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT+"'>");
    out.println("</td>");
    out.println("</TR>");

    out.println("<TR >");
    out.println("<td width=\"30%\" class=TableHeader nowrap align=center>");    
    out.println("Type");
    out.println("</TD>");
    out.println("<td  nowrap align=left>");    
    out.print("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE+"\"  onchange=\"toggle(this.options[this.selectedIndex].value); \">");
   out.print("<option value=\""+ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET+"\">"+ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET+"</option>");
    out.print("<option value=\""+ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET+"\">"+ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET+"</option>");

    out.println("</select>");
    out.println("</TD>");    
    out.println("</tr>");

    
   
    out.println("<tr>");
    out.println("<td colspan=2>");              
    out.println("<div style=\"display:block\"  id="+ContractRegistrationInterfaceKey.SHEET_INPUT_TYPE_ASSIGN_SHEET+">");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0 name=\"\">");
    out.println("<tr>");
    out.println("<td width=\"30%\" class=TableHeader nowrap align=center>");    
    out.println("Sheet Serial");
    out.println("</TD>");
    out.println("<td  nowrap align=left>");    
    out.println("<input size=\"30%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_INPUT+"\"");    
    out.println("</TD>");
    out.println("</tr>");
    out.println("</table>");
    out.println("</div>");
    out.println("</TD>");
    out.println("</tr>");
    

    out.println("<tr>");
    out.println("<td colspan=2>");              
    out.println("<div style=\"display:none\"  id="+ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET+">");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"100%\" border=0 name=\"\">");
    out.println("<tr>");
    out.println("<td width=\"30%\" class=TableHeader nowrap align=center>");    
    out.println("From");
    out.println("</TD>");
    out.println("<td  nowrap align=left>");    
    out.println("<input size=\"30%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_FROM+"\"");
    out.println("</TD>");    
    out.println("</tr>");
    out.println("<tr>");
    out.println("<td width=\"30%\" class=TableHeader nowrap align=center>");    
    out.println("To");
    out.println("</TD>");
    out.println("<td  nowrap align=left>");    
    out.println("<input size=\"30%\" type=\"text\" name=\""+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_TO+"\"");
    out.println("</TD>");    
    out.println("</tr>");
    out.println("</table>");
    out.println("</div>");

    out.println("</TD>");
    out.println("</tr>");            
    out.println("</table>");
    out.println("</center>");
    
    out.println("<center>");
    out.println("<br>");
    out.println("<input class=button type=\"button\" name=\"Assign\" value=\"  Assign  \" onclick=\"checkbeforSubmit();\">");
    out.println("</center>");
    out.println("</form>");

  }
%>
  


<html>
 <script type="text/javascript">
  function checkbeforSubmit ()
  {
  <%
   out.println("document.SheetSerialDCMAssign."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_SHEET_SERIAL_LOCAL_AUTH_PERCENTAGE_LIST+"';");

out.print("var checkFlag = checkValue(document.SheetSerialDCMAssign."+ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE+".options[document.SheetSerialDCMAssign."
                +ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE 
                +".selectedIndex],document.SheetSerialDCMAssign."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_INPUT);

  out.println(".value,document.SheetSerialDCMAssign."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_FROM+".value,document.SheetSerialDCMAssign."
                + ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_TO+".value,document.SheetSerialDCMAssign."
                + ContractRegistrationInterfaceKey.CONTROL_TEXT_BATCH_ID_INPUT+".value);");

                
   out.println(" if (checkFlag == true) ");
   out.println("{SheetSerialDCMAssign.submit();}");

   
  %>
  }
      function toggle(item1)
      {      
        
        divs=document.getElementsByTagName("DIV");
        for (i=0;i<divs.length;i++) 
        {
          
          if(divs[i].id != item1)
          {
            divs[i].style.display="none";
          }
          else
          {            
            divs[i].style.display="block";
          }
        }
        
      }
    </script>
  <head>
    <%    String appName = request.getContextPath();%>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="<%out.print(appName);%>/resources/css/Template1.css">
  </head>
  <body>
    <Center>
      <H2>Sheet Percentage Administration</H2>
    </Center>
    <left>
     <h3>Select sheet serial numbers to set percentage</h3>
    </left>
    <%showDCMs(request, response, out);%>
  </body>
</html>
