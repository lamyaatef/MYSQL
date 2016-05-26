<%-- 
    Document   : show_stk_distributer
    Created on : 3/11/2010, 14:18:03
    Author     : Ahmed Adel
--%>
<%@page import="com.lowagie.text.Document"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.scm.*"
              import="com.mobinil.sds.core.system.scm.model.*"
              import="com.mobinil.sds.core.system.scm.dao.*"
              %>

    <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String staticticsAction=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
            String ActivestkforDistributer=appName+"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title></title>
    </head>
    <body>
        <div align="center">
        <br>
        <br>
        <h2>Distributers STKs Statictics</h2>
        <br>
        <br>


        <%
            Vector <STKDistributerStatisticsModel> distributersStatistics=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_DISTRIBUTERS_STATISTICS);
            int distributersStatisticsvectorsize=0;
            if(distributersStatistics!=null&&distributersStatistics.size()!=0){
            distributersStatisticsvectorsize=distributersStatistics.size();
        %>
        <form name="distributers" action="<%=staticticsAction%>" method="post">
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" >
                   <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Distributer Code</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Distributer Name</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Quantity</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">View STKs</font></td>
                    </tr>
               
               <%

                for(int i=0;i<distributersStatisticsvectorsize;i++){
                    STKDistributerStatisticsModel distributersStatisticsReport = (STKDistributerStatisticsModel)distributersStatistics.get(i);
                %>
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=distributersStatisticsReport.getDCM_Code()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=distributersStatisticsReport.getDCM_NAME()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=distributersStatisticsReport.getQuantity()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">
                        <input type="button" name="stkview" value="view" onclick="showstk(<%=distributersStatisticsReport.getDCM_ID()%>);">
                       </font></td>
                    </tr>
                <%}%>
                </table>

               

        </form>
                 
        <form name="activestks" id="activestks"  method="post" action="<%=ActivestkforDistributer%>">
            <input type="button" name="Submit" id="activeexcel" value="Active STKs and Download Excel Sheet" onclick="active();" align="middle">
            <%int numberOfDistributers=DistributerSTKDataDAO.getValidDistributersForActivation();
                   if(numberOfDistributers<=0)
                   {
                %>
                <script>
                    document.getElementById("activeexcel").value="There's No Distributers Valid To Activation";
                    document.getElementById("activeexcel").disabled=true;
                </script>
                <%}%>


        </form>
        
        
        <%}else{%>

        <h3 align="center">There's No Distributers have STKs</h3>

        <%}%>
    </div>
        </body>
<script>
function showstk(i)
{
var row=i;

document.distributers.action=document.distributers.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_SPECIFIC_DISTRIBUTER_STKS);%>'+
'&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID+ "");%>='+<%out.print(userId);%>+'&'+'<%out.print(SCMInterfaceKey.DISTRIBUTER_ID+ "");%>='+row


document.distributers.submit();
}
function active()
{
document.activestks.action=document.activestks.action+'<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.IMPORT_TEMPLATE_DATES);%>'+
'&'+'<%out.print(InterfaceKey.HASHMAP_KEY_USER_ID+ "");%>='+<%out.print(userId);%>;

document.getElementById("activeexcel").disabled=true;
document.activestks.submit();
}

</script>

</html>
