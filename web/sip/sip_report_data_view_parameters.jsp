<%@ page contentType="text/html;charset=windows-1256"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="javax.servlet.jsp.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.reports.*"
         import="com.mobinil.sds.core.system.gn.reports.dto.*" 
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.web.interfaces.*"
         import="com.mobinil.sds.web.interfaces.gn.querybuilder.*"
         import="com.mobinil.sds.core.system.gn.querybuilder.dto.*"
         import="com.mobinil.sds.web.interfaces.commission.*"
         import="com.mobinil.sds.web.interfaces.sip.*"
         import="com.mobinil.sds.core.system.gn.dataview.dto.DetailedDataViewDTO"
%>

<script>

	function drawTextBox(argOrder,argValue)
	{
		document.write("<input class=\"\" style=\"WIDTH: 220px\" value="+argValue+" type=\"text\" id=\"<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE%>"+argOrder+"\" name=\"<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE%>"+argOrder+"\">");
	}
  
	function drawComboBox(argOrder,argSearchInputValue,argSearchInputText)
	{
		document.write("<SELECT class=\"darkgreentext\" style=\"WIDTH: 220px\" id=\"selectSearchInput"+argOrder+"\" name=\"selectSearchInput"+argOrder+"\">");
		document.write("<OPTION selected value="+argSearchInputValue[0]+">"+argSearchInputText[0]+"</OPTION>");
        for(var i=1;i<argSearchInputText.length;i++) 
			document.write("<OPTION selected value="+argSearchInputValue[i]+">"+argSearchInputText[i]+"</OPTION>");
        document.write("</SELECT>")
        eval("document.ReportParameters.selectSearchInput"+argOrder+".selectedIndex = 0;");
	}
  
 	function drawCalender(argOrder,argValue)
	{
      document.write("<INPUT value="+argValue+" class=input readOnly name=\"<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE%>"+argOrder+"\">&nbsp;<A onclick=\"showCalendar(ReportParameters.<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE%>"+argOrder+",'dd/mm/yyyy','Choose date')\">");
      document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
  }


function validateParameters(argParametersCount)
{
    var nParameterType = -1;
    var strParameterValue = "";
    var bValid = true;
    for (var i = 0; i < Number(argParametersCount); i++) 
    {
        if(!bValid)
          break;
        nParameterType = eval("document.ReportParameters.<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE%>"+i+".value");
        strParameterValue = eval("document.ReportParameters.<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_VALUE%>"+i+".value");
        switch (nParameterType)
        {
            case '<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_NUMERIC%>':
            {
               var objNumber = strParameterValue; 
               if(isNaN(Number(objNumber)))
               {
                  bValid = false;
                  alert("The parameter number("+Number(i+1)+") is not a valid number..please enter a valid number then retry.");
               }   
               else
               if(strParameterValue.length > 38)
               {
                  bValid = false;
                  alert("The parameter number("+Number(i+1)+") is too long for a numeric value, numeric values max length is 38 digits.");
               }    
              else
                bValid = true;
              break;
            }
            case '<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_DATE%>':
            {
                var objDate;
                var nMonth;
                var nDay;
                var nYear;
                var strDate = new String(strParameterValue);
                var arrDate = new Array();
                arrDate = strDate.split("/");
              //  objDate = new Date(arrDate[1]+"/"+arrDate[0]+"/"+arrDate[2]);
    
    objDate = new Date();
 	objDate.setFullYear(arrDate[2]);	
 	objDate.setMonth(arrDate[1]-1);
 	objDate.setDate(arrDate[0]);
 	 	
 	objDate.setHours(5);
 	
             
                  bValid = true;
             
                break;
            }
            case '<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_TEXT%>':
            {
              if(strParameterValue.length > 8000)
              {
                bValid = false;
                alert("The parameter number("+Number(i+1)+") is too long for a text value, text values max length is 8000 characters.");
              }  
              else
                bValid = true;
              break;
            }
            default:
            {
                alert("Data type is not supported, please contact your system administrator.");
                break;
            }
      }
    }
  return bValid;  
}




function submitParameters()  
{
     if(validateParameters(document.ReportParameters.<%=ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM%>.value))     
        ReportParameters.submit();
}
</script>



<%!

private void drawParameters(HttpServletRequest argRequest, HttpServletResponse argResponse, JspWriter argOut) throws IOException
{
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)argRequest.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    if(dataHashMap.containsKey(SIPInterfaceKey.SIP_REPORT_DATA_VIEW_PARAMETER))
    {
    ReportBuilderWizardDTO  reportBuilderWizardDTO = (ReportBuilderWizardDTO)dataHashMap.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    ReportBuilderWizardDTO dtoDetailedReport = (ReportBuilderWizardDTO)dataHashMap.get(InterfaceKey.HASHMAP_KEY_DTO_OBJECT)      ;

    DetailedDataViewDTO detailDataViewDto = dtoDetailedReport.getDetailedDataView();
    String dataViewName = detailDataViewDto.getDataViewName();
    argOut.println("<CENTER>");
    argOut.println("<H2>  "+dataViewName+" </H2>");
    argOut.println("</CENTER>");
    
    Vector colParameters = dtoDetailedReport.getReportParameters(); 
    if( colParameters != null)
    { 
    int pageStartNum = 1 ; 
    int rowPerPageForExcel = 65000;
        if( colParameters.size() == 0)
        {  
            argOut.println("<script>");
            argOut.println("document.ReportParameters."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_NO_PARAM+"';");            
            argOut.println("document.ReportParameters."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+".value='"+colParameters.size()+"';");
            argOut.println("ReportParameters.submit();");
            argOut.println("</script>");       
        }
        else
        {
            argOut.println("<CENTER>");
            argOut.println("<H2>  Sip Report Data View Parameters </H2>");
            argOut.println("</CENTER>");
            argOut.println("<script>");
            argOut.println("document.ReportParameters."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_PARAM+"';");               
            argOut.println("document.ReportParameters."+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+".value='"+colParameters.size()+"';");
            argOut.println("</script>");
            drawDataViewParameters(argRequest,argResponse,argOut,colParameters);
        }
    }    
    }
    else
    {
           argOut.println("<script>");
            argOut.println("document.ReportParameters."+InterfaceKey.HASHMAP_KEY_ACTION+".value='"+SIPInterfaceKey.ACTION_SAVE_NEW_SIP_REPORT_NO_PARAM+"';");   
            argOut.println("document.ReportParameters.submit();"); 
            argOut.println("</script>");
         
    }
    
}

private void drawDataViewParameters(HttpServletRequest argRequest,HttpServletResponse argResponse, JspWriter argOut,Vector argParameters) throws IOException
{
    int nControlTypeID = -1;
    int nDataType = -1;
    String strValue = "";
    ParameterFieldDTO dtoParameterField; 
    argOut.println("<CENTER><H2>  Fill In  Parameters&nbsp; </H2></CENTER>");
    argOut.println("<TABLE cellSpacing=0 cellPadding=0 width=\"100%\" border=0 style=\"WIDTH: 100%\" background=\"\">");
    argOut.println("<TR><TD class=TableTextNote colSpan=2><STRONG>Submit&nbsp;the following parameters:</STRONG> </TD></TR>");
    argOut.println("<TR><TD class=TableTextNote colSpan=2> &nbsp; </TD></TR>");
    for (int i = 0; i < argParameters.size(); i++) 
    {
       dtoParameterField = (ParameterFieldDTO)argParameters.elementAt(i);
       argOut.println("<tr>");
       argOut.println("<TD width=20% nowrap class=TableTextNote>"+dtoParameterField.getLabelText()+": ");
       nControlTypeID = dtoParameterField.getControlTypeID();

        Utility.logger.debug(dtoParameterField.getFieldName()); 
        Utility.logger.debug(dtoParameterField.getFieldSQLCash());
      
       
       if(dtoParameterField.getFieldSQLCash()!=null && dtoParameterField.getFieldSQLCash().indexOf("to_date") != -1) 
       {
              nDataType = ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_DATE;
              strValue = dtoParameterField.getFieldSQLCash().substring(dtoParameterField.getFieldSQLCash().indexOf("'")+1,dtoParameterField.getFieldSQLCash().indexOf(",")-1);
       }    
       else
          if(dtoParameterField.getFieldSQLCash()!=null && dtoParameterField.getFieldSQLCash().indexOf("\'") != -1)
          {
             nDataType = ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_TEXT;
             strValue = dtoParameterField.getFieldSQLCash().substring(dtoParameterField.getFieldSQLCash().indexOf("\'")+1,dtoParameterField.getFieldSQLCash().length()-1);
          }   
          else
          {
               nDataType = ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE_NUMERIC;
               strValue = dtoParameterField.getFieldSQLCash();
          }
       argOut.println("</TD><TD class=TableTextNote>");
       drawControl(nControlTypeID,i,strValue,argOut);   
       argOut.println("<input type=\"hidden\" name="+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_ID+i+" value='"+dtoParameterField.getFieldID()+"'>");   
       argOut.println("<input type=\"hidden\" name="+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAM_TYPE+i+" value='"+nDataType+"'>");
       argOut.println("</TD><TD class=TableTextNote>&nbsp;</TD></TR>");
    }
}

private void drawControl(int argControlType,int argControlOrder,String argValue,JspWriter argOut) throws IOException
{
       argOut.println("<script>");
       switch (argControlType)
       {
          case QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_CALENDAR:
          {
                  argOut.println("drawCalender("+argControlOrder+",'"+argValue+"');");
                  break;
          }
          case QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_SELECT:
          {
//                  argOut.println("</script>");
                  break;
          }
          case QueryBuilderInterfaceKey.PARAM_PREVIEW_QUERY_CONTROL_TYPE_TEXT:
          {
                  argOut.println("drawTextBox("+argControlOrder+",'"+argValue+"');");
                  break;
          }
          default:
            break;
          
      }
      argOut.println("</script>");
}
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
    <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">          
    <SCRIPT language=JavaScript src="../resources/js/validation.js" type=text/javascript></SCRIPT>
    <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>
  </head>
  <body>

 <form action="../servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="ReportParameters" id="ReportParameters" method="post">
 <%
    HashMap dataHashMap = new HashMap(100);
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
    String commissionID = (String)dataHashMap.get(SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID);
    
    System.out.println("The sip  report_id in  jsp  isssssssssssssssss  "+commissionID);
    String dataViewType = (String)dataHashMap.get(SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE);
    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    
out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PARAMS_NUM+"\""+
                  " value=\""+"\">");

out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ID+"\""+
                  " value=\""+"\">");

out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
        " value=\""+strUserID+"\">");   
                  

out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.INPUT_HIDDEN_SIP_REPORT_ID+"\""+
                  " value=\""+commissionID+"\">");
                  Utility.logger.debug(commissionID);

                  System.out.println("The  report id  in  para data view isssssss"+ commissionID);
out.println("<input type=\"hidden\" name=\""+SIPInterfaceKey.CONTROL_TEXT_SIP_REPORT_DATA_VIEW_TYPE+"\""+
                  " value=\""+dataViewType+"\">");
                 
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_ROWS_PER_PAGE+"\""+
                  " value=\""+"\">");                    
      out.println("<input type=\"hidden\" name=\""+ReportInterfaceKey.PARAM_PREVIEW_REPORT_PAGE_NUM+"\""+
                  " value=\""+"\">");                    

                  
 drawParameters(request,response,out);
 

 out.println("</table>");
out.println("<Br>");
out.println("<Br>");
out.println("<center>");
 out.println("<INPUT class=button onclick=\"submitParameters();\" type=button value=\" Save \" name=view>");
 out.println("</center>");
 %>
 <br>
 <br>
 
 
 
 
 </form>
 
 </html>

