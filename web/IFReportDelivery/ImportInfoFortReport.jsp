<%@ page contentType="text/html;charset=windows-1252"
              import ="com.mobinil.sds.core.utilities.Utility"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"                   
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.sa.*"
              import="com.mobinil.sds.web.interfaces.ac.*"
              import="com.mobinil.sds.web.interfaces.aacm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"              
              %>
              <%
String appName = request.getContextPath();


    HashMap dataHashMap = null;
    dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);

    String strUserID = (String)dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
    System.out.println("The user id issssssssssss " + strUserID);
    
    String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +IFReportDeliveryInterfaceKey.ACTION_IMPORT_INFO_FORT_REPORT+
                    "&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
                    +strUserID;
                   
%>   


<%@page import="com.mobinil.sds.web.interfaces.cam.PaymentInterfaceKey"%>
<%@page import="com.mobinil.sds.web.interfaces.IFReportDelivery.IFReportDeliveryInterfaceKey"%><HTML>
   <HEAD>
      <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
      <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template2.css">
      <SCRIPT LANGUAGE="JavaScript" SRC="<%out.print(appName);%>/resources/js/calendar.js"></SCRIPT>
      <TITLE>Payment Status Import</TITLE>
      <center>
      <h2>Info Fort Report Import</h2>
      </center>
   </HEAD>



   <body onkeypress = "if(event.keyCode==13){myform.submit();}">


<script>
function submitForm()
{
	if(document.myform.<%=IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE%>.value == 'Report_Creation_Date')
		{
		   alert("you have to select Date");
		   return;
		}
	


    var fileName = document.myform.myfile.value;
    if(fileName == "")
    {
      alert("File field can not be empty.");
      return;
    }
    if(document.myform.myfile.value.lastIndexOf(".xls")==-1&&document.myform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }


    document.myform.action = document.myform.action+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_TABLES+"");%>='+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES);%>.value+'&'+'<%out.print(AdministrationInterfaceKey.QUERY_STRING_OPERATION+"=");%>'+document.myform.<%out.print(AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION);%>.value+'&'+'<%out.print(IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE+"=");%>'+document.myform.<%out.print(IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE);%>.value
    

    document.myform.Submit.disabled = true;
  document.myform.submit();
  
}

function drawCalender(argOrder,argValue)
{
	
    document.write("<INPUT value='"+argValue+"' class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(myform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
    document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
}
</script>

<form name="myform" action="<%out.print(formAction);%>"method="post" enctype="multipart/form-data">
<%         
                  
    // Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    // out.println("<center>");

    // out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
    // out.println("<TR style='display:none'>");
    // out.println("<td class=TableHeader nowrap align=center>");
    // out.println("Table");
    // out.println("</TD>");        
    // out.println("<td nowrap align=left>");
    // out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+">");
    // for(int k= 0; k<tableVec.size(); k++)
    // {
      // DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      // out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    // }
    // out.println("</Select>");
    // out.println("</td>");    
    // out.println("</tr>");

    // out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+" style=\"Display:none\">");
    // out.println("<option></option>");
    // out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\" selected>"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    //out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    // out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");
	%>
	<table style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="70%" border=1 align="center">
	<tr class=TableHeader>
                    <td>
                  
                <font size="4" >  <b>Upload</b> </font> <br></td><td></td></tr>
                
                <tr class="TableHeader">
                <td>Choose Date</td>
                <td>  
                <script>
                  drawCalender('<%=IFReportDeliveryInterfaceKey.CONTROL_REPORT_CREATION_DATE%>','');
          		</script>
            </td>
        </tr>
       
                
                
	<%         
                  
    Vector tableVec = (Vector) dataHashMap.get(AdministrationInterfaceKey.TABLE_DEF_VECTOR);
    out.println("<center>");

  //  out.println("<TABLE style=\"BORDER-COLLAPSE: collapse\" cellSpacing=2 cellPadding=1 width=\"50%\" border=1>");
      out.println("<TR style='display:none'>");
   // out.println("<td class=TableHeader nowrap align=center>");
    //out.println("Table");
     out.println("</TD>");       
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_TABLES+" style=\"Display:none\">");
    for(int k= 0; k<tableVec.size(); k++)
    {
      DataImportTableDefModel tableModel = (DataImportTableDefModel) tableVec.get(k);            
      out.println("<option value=\""+tableModel.getTableId()+"\">"+tableModel.getTableName()+"</option>");        
    }
    out.println("</Select>");
    out.println("</td>");    
    out.println("</tr>");
    //out.println("<TR>");
    //out.println("<td class=TableHeader nowrap align=center>");
   // out.println("Operation");
   // out.println("</TD>");        
   // out.println("<td nowrap align=left>");
    out.println("<Select name="+AdministrationInterfaceKey.CONTROL_DATA_IMPORT_OPERATION+" style=\"Display:none\">");
    out.println("<option></option>");
    out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"\" selected>"+AdministrationInterfaceKey.DATA_IMPORT_INSERT+"</option>");
    //out.println("<option value=\""+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"\">"+AdministrationInterfaceKey.DATA_IMPORT_UPDATE+"</option>");        
    out.println("</Select>");
    //out.println("</td>");    
    //out.println("</tr>");


    out.println("<tr>");
    out.println("<td class=TableHeader nowrap align=center>");
    out.println("File");
    out.println("</td>");
    out.println("<td nowrap align=left>");
%>
   <input type="hidden" name="hiddenField">
    <input type="file" name="myfile">
<%
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");
%>

    <center>
    <input class="button" type="button" name="Submit" value="Submit your file" onclick="submitForm();">
    </center>
    </form>
   </BODY>
</HTML>
              