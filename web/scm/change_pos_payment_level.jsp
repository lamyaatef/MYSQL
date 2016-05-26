<%-- 
    Document   : change_pos_payment_level
    Created on : Feb 21, 2011, 2:24:46 PM
    Author     : gado
--%>

<%--
    Document   : change_stk_status
    Created on : 1/11/2010, 19:25:36
    Author     : Ahmed Adel
--%>

<%@page import="com.lowagie.text.Document"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.PosPaymentLevel"
              %>

    <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);

            Vector<PosPaymentLevel> posPaymentLevels
                    =(Vector) dataHashMap.get(SCMInterfaceKey.POS_PAYMENT_LEVELS);

            String excelAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
            +InterfaceKey.HASHMAP_KEY_ACTION+"="
            +SCMInterfaceKey.CHANGE_POS_PAYMENT_LEVEL_EXCEL;
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>Change POS Payment Level</title>

</head>

    <body onkeypress = "if(event.keyCode==13){showStatus.submit();}">
        <div align="center">
        <br>
        <br>
        <h2>Change POS Payment Level</h2>
        <br>
        <br>
        </div>

 <div name="byExcel" id="byExcel" align="center">

     <form name="excelform" id="excelform" action="<%=excelAction%>" method="post" enctype="multipart/form-data">

    <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
    <tr>
    <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Excel File:</font></td>
    <td  nowrap align=center colspan="2"><font style="font-size: 11px;font-family: tahoma;line-height: 15px"/>
     <% out.println("<input type=hidden name="+SCMInterfaceKey.IMPORT_TABLE+" value="+SCMInterfaceKey.POS_STATUS_TABLE+">");%>
     <input type="file" name="myfile">
    </tr>
    </table>

    <br>
     <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
            <tr>
                <td class=TableTextNote nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"></font>Change Payment Level</td>
                <td  nowrap align=center >
                <select name="<%=SCMInterfaceKey.POS_PAYMENT_LEVEL_LIST%>" id="pospaymentlevel">
                <%for(int i=0;i<posPaymentLevels.size();i++)
                    {
                    %>
                    <option value="<%=posPaymentLevels.get(i).getDCM_PAYMENT_LEVEL_ID()%>">
                        <%=posPaymentLevels.get(i).getDCM_PAYMENT_LEVEL_NAME()%>
                    </option>
                <%
                }
                %>
               </select>
                </td>
            </tr>
        </table>
    <br>
    <input type="button" class="button" id="excelposbutton" name="Submit"  value="Change Pos Payment Level" onclick="submitchangeposexcel();">
 </form>
 </div>
    </body>
    <script>

        function submitchangeposexcel()
{
    if(document.excelform.myfile.value.lastIndexOf(".xls")==-1&&document.excelform.myfile.value.lastIndexOf(".xlsx")==-1)
    {
   alert("Please upload only Excel file");
   return false;
    }



    document.excelform.action = '<%=excelAction%>'+'&'+'<%out.print(SCMInterfaceKey.IMPORT_TABLE+"");%>='+document.excelform.<%out.print(SCMInterfaceKey.IMPORT_TABLE);%>.value+
            '&'+'<%=SCMInterfaceKey.POS_PAYMENT_LEVEL_LIST+""%>='+document.getElementById("pospaymentlevel").value

    document.excelform.submit();

}
    </script>
