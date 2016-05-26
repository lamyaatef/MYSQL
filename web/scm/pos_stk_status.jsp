<%-- 
    Document   : view_stk_pos_status
    Created on : 21/10/2010, 17:31:26
    Author     : Ahmed Adel
--%>
<%@page import="com.mobinil.sds.core.system.scm.dao.STKDAO"%>
<%@page import="com.mobinil.sds.core.system.scm.dao.PoiWriteExcelFile"%>

<%@ page contentType="text/html;charset=windows-1252"
              import="org.apache.commons.fileupload.*"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.sa.importdata.model.*"
              import ="java.io.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>STK_POS_STATUS</title>
        <link href="../resources/css/Template1.css" rel="stylesheet" type="text/css" />
      <SCRIPT language=JavaScript src="../resources/js/calendar.js" type="text/javascript"></SCRIPT>
      <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
      <script type="text/javascript" src="../resources/js/jquery_tablesorter_pack.js"></script>
      <link rel="stylesheet" href="../resources/css/themes/blue/style.css" type="text/css"/>
      <script type="text/javascript" src="../resources/js/paging.js"></script>

    <script>
<%
String appName = request.getContextPath();
HashMap dataHashMap = (HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
String  strUserID = request.getParameter(InterfaceKey.HASHMAP_KEY_USER_ID);

String formAction= appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
    +InterfaceKey.HASHMAP_KEY_ACTION+"="
    +SCMInterfaceKey.ACTION_POS_STK_STATUS_PROCESS+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
    +strUserID;
String excelAction= appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
    +InterfaceKey.HASHMAP_KEY_ACTION+"="
    +SCMInterfaceKey.ACTION_POS_STATUS_EXCEL_SHEET+"&"+InterfaceKey.HASHMAP_KEY_USER_ID+"="
    +strUserID;
String alert = (String)dataHashMap.get(SCMInterfaceKey.SINGLE_IMPORT_Message);
String MSG =(String)dataHashMap.get(SCMInterfaceKey.ACTION_GET_VALID_MESSAGE);


String statusDateFrom =(String)dataHashMap.get(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM);
String statusDateTo =(String)dataHashMap.get(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO);
String POScode=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_POS_CODE);
String iqrarStatus=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_IQRAR_STATUS);
String cbillStatus=(String)dataHashMap.get(SCMInterfaceKey.CONTROL_CBILL_STATUS);
String distinationPage=(String)dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
String totalPageNumbers=(String)dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);


if(iqrarStatus==null)
{
    iqrarStatus="";
}
if(cbillStatus==null)
{
    cbillStatus="";
}
if(POScode==null)
{
    POScode="";
}
%>
     function drawCalender(argOrder,argValue)
    {
        document.write("<INPUT value="+argValue+" class=input readOnly name=\""+argOrder+"\">&nbsp;<A onclick=\"showCalendar(searchform."+argOrder+",'mm/dd/yyyy','Choose date')\">");
        document.write("<IMG height=16 src=\"../resources/img/icon_calendar.gif\" width=20 border=0></A>");
    }

    function submitForm()
    {
        document.searchform.action="<%=formAction%>";
        document.searchform.submit();
    }
    function DevChangePageActionWithSubmit(action)
    {
    document.searchform.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value=action;
    document.searchform.submit();
    }

    </script>

    </head>
    <CENTER>
        <br>
        <br>
        <h2>POS Status</h2>
        <br>
        <br>
    </CENTER>



    <body>

        <form name="searchform" action="" method="post" >
            <TABLE style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1 align="center">
      <tr class=TableHeader>
        <td align='left' colspan=4>Search</td>
      </tr>
      <tr class=TableTextNote align="center" >
          <td class="TableTextNote" colspan="2" align="center" onclick="block();">POS Code</td>
          <td colspan="2"><input type="text" name="<%=SCMInterfaceKey.CONTROL_POS_CODE%>" id="<%=SCMInterfaceKey.CONTROL_POS_CODE%>" value="<%=POScode%>">
      </td>
      </tr>
      <TR class=TableTextNote>
          <td class="TableTextNote" align="center" onclick="block();">Iqrar Status </td>
          <td align ="center">
        <select  name="<%=SCMInterfaceKey.CONTROL_IQRAR_STATUS%>" id="<%=SCMInterfaceKey.CONTROL_IQRAR_STATUS%>">
            <option></option>
            <option  value="<%=SCMInterfaceKey.RECEIVED_IQRAR_STATUS%>"<%if (iqrarStatus.equals("2")){out.print("selected");}%>>Recevied</option>
            <option  value="<%=SCMInterfaceKey.NOTRECIVED_IQRAR_STATUS%>"<%if (iqrarStatus.equals("1")){out.print("selected");}%>>Not Recevied</option>
        </select>
        </td>
        <td class="TableTextNote" align ="center" onclick="block();">C-Bill Status</td>
        <td align ="center">
        <select align ="center" name="<%=SCMInterfaceKey.CONTROL_CBILL_STATUS%>" id="<%=SCMInterfaceKey.CONTROL_CBILL_STATUS%>">
    <option></option>
    <option  value="<%=SCMInterfaceKey.VALID_CBILL_STATUS%>"  <%if (cbillStatus.equals("2")){out.print("selected");}%>  >Valid</option>
    <option  value="<%=SCMInterfaceKey.NOTVALID_CBILL_STATUS%>" <%if (cbillStatus.equals("1")){out.print("selected");}%> >Not valid</option>
        </select>
        </td>
      </TR>
      <tr>
     <td class="TableTextNote" align="center">POS Status Date From</td>
     <td class="TableTextNote" align="center" onclick="block();"><script>drawCalender('<%=SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM%>' , "<%=statusDateFrom%>");</script></td>
    <td class="TableTextNote"  align="center">POS Status Date To</td>
    <td class="TableTextNote"  align="center" onclick="block();"><script>drawCalender('<%=SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO%>' , "<%=statusDateTo%>");</script></td>
      </tr>
      <td align='center' colspan=4>
          <input type="button" class="button" value="Search" onclick="submitForm();"/>

      </td>
      
           </TABLE>
       
      <br>

      <br>
     
<%
if (alert =="Data" )
{



        Vector<POSModel> POSs = (Vector)dataHashMap.get(SCMInterfaceKey.POS_STATUS_SEARCH_RESULT);
        Vector<POSModel> active = (Vector)dataHashMap.get(SCMInterfaceKey.ACTIVE_POS_STATUS_SEARCH_RESULT);
                        int ActiveFlag=0; 
%>






<TABLE id="results" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border=1 align="center">

<tr class=TableHeader>
<td size =20% nowrap align=left>POS Code</td>
<td size= 10% nowrap align=left>POS Name</td>
<td size =10% nowrap align=left>STK Dial Number</td>
<td size =10% nowrap align=left>CBILL STATUS</td>
<td size =10% nowrap align=left>IQRAR STATUS</td>
<td size =10% nowrap align=left>STK STATUS</td>
</tr>


<%  
for (int i = 0; i < active.size(); i++) {
if(ActiveFlag!=3)
                                 {
                                    ActiveFlag=0;
                                 }
if(active.get(i).getCbill_Status()==2)
                                {
                                if(ActiveFlag==0)
                                ActiveFlag++;
                                }

if(active.get(i).getIqrar_Status()==2)
                                {
                                
                                if(ActiveFlag==1)
                                ActiveFlag++;
                                }
if(active.get(i).getSTK_Status()==2)
                                {
                                
                                if(ActiveFlag==2)
                                ActiveFlag++;
                                }


}   
%>



 <%
     for (int i = 0; i < POSs.size(); i++) {
         %>


<%
                              

                            POSModel POS=(POSModel)POSs.get(i);

 %>


<tr>
<td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">

<%=POS.getPOS_Code()%>
</td>

<td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
<%=POS.getPOS_NAME()%>
</td>
<td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
<%=POS.getSTK_Dial()%>
</td>
<td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
<%
                            if(POS.getCbill_Status()==1)

                                out.print("Not verified");
                            else if(POS.getCbill_Status()==2)
                                {
                                out.print("verified");
                               
                                }
%>
</td>
<td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
                            <%
                            if(POS.getIqrar_Status()==1)
                                out.print("Not verified");
                            else if(POS.getIqrar_Status()==2)
                                {
                                out.print("verified");
                            
                                }
       %>
</td>
<td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px">
                            <%
                            if(POS.getSTK_Status()==2)
                                {
                                out.print("Assigned");
                               
                                }
                            else if(POS.getSTK_Status()==4)
                                {
                                out.print("Active");
                                }
                             else if(POS.getSTK_Status()==7)
                                {
                                out.print("Suspended");
                                }
       %>
</td>
</tr>
    <%
                        }
    %>
    </TABLE>
    <div align="center">
    <jsp:include page="pagingTable.jsp"  flush="true">
                                      <jsp:param   name="action_name_when_click_enter" value="<%=SCMInterfaceKey.ACTION_POS_STK_STATUS_PROCESS%>"/>
                                      <jsp:param   name="first_page_number" value="0"/>
                                      <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                                      <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


</jsp:include>
    </div>
    </form>
<br>



<div name="Excel" id="Excel" align="center">
<form name="ExcelForm"  id="ExcelForm" action="<%=excelAction%>" method="post" enctype="multipart/form-data">
<input class="button"  type="submit" name="Submit" id="active" value="There's No POSs for Active" disabled=true onclick="Sheet();">
</form>
</div>

<%
if(ActiveFlag==3)
{
out.println("<script>");
out.println("document.getElementById(\"active\").value=\"Active STKs & Download Excel Sheet\";");
out.println("document.getElementById(\"active\").disabled=false;");
out.println("</script>");
}
}
else{
if(alert=="No data")
{out.println("<script>");
out.println("alert(\""+alert+"\");");
out.println("</script>");
}
}
%>

</body>
</html>
<script>

function Sheet()
{
        document.ExcelForm.action=document.ExcelForm.action+'&'+'<%out.print(SCMInterfaceKey.CONTROL_IQRAR_STATUS+"");%>='+document.searchform.<%out.print(SCMInterfaceKey.CONTROL_IQRAR_STATUS);%>.value+'&'+
         '<%out.print(SCMInterfaceKey.CONTROL_CBILL_STATUS+"");%>='+document.searchform.<%out.print(SCMInterfaceKey.CONTROL_CBILL_STATUS);%>.value+'&'+
         '<%out.print(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM+"");%>='+document.searchform.<%out.print(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_FROM);%>.value+'&'+
         '<%out.print(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO+"");%>='+document.searchform.<%out.print(SCMInterfaceKey.CONTROL_POS_STATUS_DATE_TO);%>.value+'&'+
         '<%out.print(SCMInterfaceKey.CONTROL_POS_CODE+"");%>='+document.searchform.<%out.print(SCMInterfaceKey.CONTROL_POS_CODE);%>.value
       document.getElementById("active").disabled=true;
       document.ExcelForm.submit();

}
</script>
<script>
function block(){
if(document.getElementById("active")!=null){
document.getElementById("active").value="There's No POSs for Active";
document.getElementById("active").disabled=true;
}
}



</script>
