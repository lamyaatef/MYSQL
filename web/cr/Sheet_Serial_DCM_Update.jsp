<%@ page contentType="text/html;charset=windows-1252"
                import="java.io.*"
                import="java.util.*"
                import="com.mobinil.sds.web.interfaces.*"
                import ="com.mobinil.sds.web.interfaces.cr.ContractRegistrationInterfaceKey"
                import="com.mobinil.sds.core.system.gn.dcm.dto.*"
                import="com.mobinil.sds.core.system.gn.dcm.model.*"
                import="com.mobinil.sds.core.system.cr.sheet.model.*"
                
%>

<script>
function IsNumeric(sText)
{
   var ValidChars = "0123456789";
   var IsNumber=true;
   var Char;

    if (sText.length!=7 ) //this is to validate that it is a valid sheet serial number 
  {
    isNumber = false;
    return isNumber;
    }
    
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

function checkValue(rangeOrSerial, serial, serialFrom, serialTo)
{


 var assignType = rangeOrSerial.value;

 
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

<script>
 function changeAll(combobox) 
  {
    comboboxs=document.getElementsByTagName("SELECT");    
    for (i=0;i<comboboxs.length;i++) 
    {
 //     if(comboboxs[i].name.match(combobox.name) == combobox.name && ! comboboxs[i].disabled)
      if(comboboxs[i].name != '<%out.print(ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE);%>')
      {
        comboboxs[i].value = combobox.value;
      }
    }
  }
</script>

<%!
  /**
   * printDCM method:
   * fill DCM ID combobox.
   *
   * @param	HttpServletRequest request, JspWriter out, DCMDto dcmDto
   * @return  
   * @throws  IOException
   * @see    
   * 
  */

public void printDCM(javax.servlet.http.HttpServletRequest request, javax.servlet.jsp.JspWriter out, DCMDto dcmDto , String selectedOne) 
throws java.io.IOException
{
//Utility.logger.debug("selectedOne " + selectedOne);

  //out.println("<option value=\"\"></option>");
  if (dcmDto !=null)
  {
    for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
    {
      DCMModel model = dcmDto.getDcm(index);    
      
      out.print("<option value=\""+model.getDcmId()+"\"");
      if (selectedOne!=null && selectedOne.compareTo(model.getDcmId()+"")==0)
      {
       out.print (" selected");
      }

      out.println(">"+model.getDcmName()+"</option>");
    }
  }  
}
%>



<%!
  /**
   * showUsers method: 
   * Display the system Users.
   *
   * @param	HttpServletRequest request, HttpServletResponse response, JspWriter out
   * @return  
   * @throws  ServletException, IOException
   * @see    
   *
   */ 

  public void showDCMs(HttpServletRequest request, HttpServletResponse response, JspWriter out)
  throws ServletException, IOException
  {
    String appName = request.getContextPath();
    HashMap dataHashMap ;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    HashMap additonalCollection =(HashMap)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    DCMDto dcmDto= (DCMDto) additonalCollection.get(ContractRegistrationInterfaceKey.OBJ_DCM_DTO);

    Vector sheetDCMVector = (Vector)dataHashMap.get(ContractRegistrationInterfaceKey.SHEET_DCM_ASSIGNMENT_VECTOR);
    String inputType =   (String)additonalCollection.get( ContractRegistrationInterfaceKey.OBJ_SELECTED_ASSIGN_TYPE); 
    String inputSerial = (String)additonalCollection.get( ContractRegistrationInterfaceKey.OBJL_SHEET_SERIAL_INPUT);
    String inputFrom = (String)additonalCollection.get( ContractRegistrationInterfaceKey.OBJ_SHEET_SERIAL_FROM);
    String inputTo = (String)additonalCollection.get( ContractRegistrationInterfaceKey.OBJ_SHEET_SERIAL_TO);
    //String dcmId = (String)additonalCollection.get( ContractRegistrationInterfaceKey.OBJ_SELECTED_DCM_ID);

    String message = (String) dataHashMap.get(ContractRegistrationInterfaceKey.SERVER_MESSAGE  );

    if (message!=null)
    {
    out.println("<script>");
    out.println("alert('"+ message + "');");
    out.println("</script>");
    }
   
        
     
   // Hashtable additonalCollection =(Hashtable)dataHashMap.get(InterfaceKey.HASHMAP_KEY_ADDITIONAL_COLLECTION);
    out.println("<form action=\""+appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet\" name=\"SheetSerialDCMUpdate\" method=\"post\">");

    String channelId = "1";
        if(dataHashMap.containsKey(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID))
        channelId = (String)dataHashMap.get(ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID);
        out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_CHANNEL_ID+
                    "\" value="+channelId+">");

    out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\" value=\"\">");
    out.println("<center>");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"70%\" border=1>");
    out.println("<tr>");
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
    out.println("<input class=button type=\"button\" name=\"View\" value=\"  View  \" onclick=\"checkbeforSubmit();\">");
    out.println("</center>");
  
    out.println("<br>");
    
    if (sheetDCMVector!=null && sheetDCMVector.size()!=0)
    {
    out.println("<center>");
    out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"70%\" border=1>");
     
    out.println("<tr>");
    out.print("<td width=\"40%\" class=TableHeader nowrap align=center> ");
    out.print("Sheet Serial " );
    out.println("</td>" );
    out.println("<td width=\"60%\" class=TableHeader nowrap align=left>" );
    out.print("<br>"+"DCM NAME" + " <BR>");
    out.println("<select name=\""+"dcm_select"+"\"  onchange=\"changeAll(this);\">");
    printDCM(request,out,dcmDto, "");
    out.println("</select>");
    out.println("</TD>");
    out.println("<tr>");
    for (int i = 0 ; i < sheetDCMVector.size() ; i++)
    {
    SheetDCMModel sheetDCMModel = (SheetDCMModel) sheetDCMVector.get(i);
    out.println("<tr>");
    out.println("<td width=\"40%\"  nowrap align=center> ");
    out.println(sheetDCMModel.getSheetSerialID());
    out.println("</td>" );
    out.println("<td width=\"60%\"  nowrap align=left>" );    
    out.println("<input type=\"hidden\" name=\""+ContractRegistrationInterfaceKey.CONTROL_HIDDEN_SHEET_ID+"\" value=\""+sheetDCMModel.getSheetDCMId()+"\">");

    for (int index = 0 ; index<dcmDto.getDcmModelsSize();index++)
    {
      DCMModel model = dcmDto.getDcm(index);               
      if ( sheetDCMModel.getDCMID().compareTo(model.getDcmId()+"")==0)
      {
       out.print(model.getDcmName()+"<BR>");
       break;
      }
      
    }
    

    out.println("<select name=\""+ContractRegistrationInterfaceKey.CONTROL_SELECT_DCM_ID+"\">");
    printDCM(request,out,dcmDto, sheetDCMModel.getDCMID());
    out.println("</select>");          
    out.println("</TD>");
    out.println("<tr>");
    }
    out.println("</table>");
    out.println("</center");
    }


    if (sheetDCMVector!=null && sheetDCMVector.size()!=0)
    {
      out.println("<br>");
      out.println("<br>");
      out.println("<center>");
      out.println("<input class=button type=\"button\" name=\"Update\" value=\"  Update  \" onclick=\"updateSubmit();\">");
      out.println("</center>");
    }

    
    out.println("</form>");

    if (inputSerial!=null)
    {
      out.println("<script>");
      
      if (inputType.compareTo(ContractRegistrationInterfaceKey.RANGE_INPUT_TYPE_ASSIGN_SHEET)==0)
      {
      out.println("document.SheetSerialDCMUpdate."+ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE+".options[1].selected=true;");
      }
      else
      {
      out.println("document.SheetSerialDCMUpdate."+ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE+".options[0].selected=true;");
      }
      out.println("toggle('"+ inputType + "');");
     
      out.println("</script>");
    }
  }
%>
  


<html>
 <script type="text/javascript">
  function checkbeforSubmit ()
  {
  <%
   out.println("document.SheetSerialDCMUpdate."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_VIEW_SHEET_SERIAL+"';");
   out.print("var checkFlag = checkValue(document.SheetSerialDCMUpdate."+ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE+".options[document.SheetSerialDCMUpdate."
                +ContractRegistrationInterfaceKey.CONTROL_SELECT_SHEET_ASSIGN_TYPE 
                +".selectedIndex],document.SheetSerialDCMUpdate."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_INPUT);

  out.println(".value,document.SheetSerialDCMUpdate."+ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_FROM+".value,document.SheetSerialDCMUpdate."
                + ContractRegistrationInterfaceKey.CONTROL_TEXT_SHEET_SERIAL_TO+".value);");

                
   out.println(" if (checkFlag == true) ");
   out.println("{SheetSerialDCMUpdate.submit();}");
  %>
  }

  function updateSubmit ()
  {
  <%
   out.println("document.SheetSerialDCMUpdate."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+ContractRegistrationInterfaceKey.ACTION_UPDATE_SHEET_SERIAL_ASSIGNMETN+"';");
   out.println("SheetSerialDCMUpdate.submit()");
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
      <H2>Contract Registeration Administration</H2>
    </Center>
    <left>
     <h3>Update sheet serial assignment to DCMs</h3>
    </left>
    <%showDCMs(request, response, out);%>
  </body>
</html>
