<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import ="com.mobinil.sds.core.utilities.Utility"
         import ="javax.servlet.*" 
         import="javax.servlet.http.*"
         import="java.io.PrintWriter"
         import="java.io.IOException"
         import="java.util.*"
         import="java.io.*"
         import="javax.servlet.jsp.*"
        import="com.mobinil.sds.web.interfaces.*"
        import = " com.mobinil.sds.core.system.*"
         
        
       
       
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<%@page import="com.mobinil.sds.web.interfaces.dm.DMInterfaceKey"%>
<%@page import="com.mobinil.sds.core.system.authenticationResult.model.AuthResStatisticsModel"%>
<%@page import="com.mobinil.sds.core.system.dataMigration.model.* "%>
<%@page import="com.mobinil.sds.core.system.cam.excelImport.Sheet"%>

<%@page import="com.mobinil.sds.web.interfaces.*"%>
<%@page import="com.mobinil.sds.core.system.cr.contract.model.lcsProductMappingModel"%>

<html>
 <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type=text/javascript></SCRIPT>     
    </head>
  <body>
<script>
 function drawCalender(argOrder,argValue)
        {
            document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(SOPform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
            document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
        }
 function setSearchValues(dateFrom,dateTo)
        {
          document.getElementById("<%=DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_FROM%>").value = dateFrom;
          document.getElementById("<%=DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_TO%>").value = dateTo;
        }
        
</script>

 <%                
  HashMap objDataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

  String strUserID = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);


  String searchDateFrom = "*";
  String searchDateTo = "*";
  
  if(objDataHashMap.containsKey(DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_FROM))
  {
	  searchDateFrom = (String)objDataHashMap.get(DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_FROM);
  }
  if(objDataHashMap.containsKey(DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_TO))
  {
	  searchDateTo= (String)objDataHashMap.get(DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_TO);
  }

%>
<CENTER>
<H2> Revenue Report </H2>
</CENTER>

<form name='DMform' id='DMform' action='' method='post'>
<%
  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_ACTION+"\""+
                  " value=\""+"\">");

  out.println("<input type=\"hidden\" name=\""+InterfaceKey.HASHMAP_KEY_USER_ID+"\""+
                  " value=\""+strUserID+"\">");   
                  

%>

<TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="100%" border=1> 
      <tr class=TableHeader>
        <td align='left' colspan=6>Search</td>
      </tr>
      <TR class=TableTextNote>
      <td align=middle>From</td>
        <td align=middle><Script>drawCalender('<%=DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_FROM%>','<%=searchDateFrom%>',"*");</script></td>
         <td align=middle>To</td>
        <td align=middle><Script>drawCalender('<%=DMInterfaceKey.INPUT_SEARCH_TEXT_DATE_TO%>','<%=searchDateTo%>',"*");</script></td>
         </tr>
      <tr>
        <td align='center' colspan=6>
        <%
        out.print("<INPUT class=button type=button value=\" Export To Excel \" name=\"export\" id=\"export\" onclick=\"document.DMform."+InterfaceKey.HASHMAP_KEY_ACTION+".value = '"+DMInterfaceKey.ACTION_EXPORT_REVENUE_REPORT+"';"+
                  "DMform.submit();\">");

                  
        %>
        </td>
      </tr>
    </TABLE>
<%
//if(objDataHashMap.containsKey(InterfaceKey.HASHMAP_KEY_ACTION))
  //{
    //String strAction = (String)objDataHashMap.get(InterfaceKey.HASHMAP_KEY_ACTION);
    //if(strAction.compareTo(SOPInterfaceKey.ACTION_SEARCH_REQUEST)==0)
    //{
     // out.println("<script>setSearchValues('"+searchInvoiceNumber+"','"+searchDcmId+"','"+searchPaymentDate+"');</script>");
    //}
  //}
%>
</form>
</body>
</html>