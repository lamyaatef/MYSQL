<%-- 
    Document   : view_region_childs
    Created on : 22/11/2010, 16:23:04
    Author     : Ahmed Adel
--%>

<%@page import="com.lowagie.text.Document"%>
<%@ page contentType="text/html;charset=windows-1252"
              import="java.util.*"
              import="com.mobinil.sds.web.interfaces.*"
              import="com.mobinil.sds.web.interfaces.dcm.*"
              import="com.mobinil.sds.core.system.dcm.region.dao.*"
              import="com.mobinil.sds.core.system.dcm.region.model.*"
              %>

    <%
            String appName = request.getContextPath();
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            String regionId=(String)dataHashMap.get(DCMInterfaceKey.INPUT_TEXT_REGION_ID);
            String regionName= RegionDAO.getRegionName(regionId);
            String distinationPage=(String)dataHashMap.get(DCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
            String totalPageNumbers=(String)dataHashMap.get(DCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
            String DCMFormAction=appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?";
        %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
         <script>
        function DevChangePageActionWithSubmit(action)
    {
    document.DCMform.action=document.DCMform.action+'<%=InterfaceKey.HASHMAP_KEY_ACTION%>'+'='+action
    document.DCMform.submit();
    }
        </script>
    <body>
        <div align="center">
        <br>
        <br>
        <h2 align="center"><%=regionName%>'s Childs</h2>
        <br>
        <br>

        <%
            Vector <RegionModel> regionchilds=(Vector)dataHashMap.get(DCMInterfaceKey.VECTOR_REGION_CHILDS);
            int regionchildssize=0;
            if(regionchilds!=null&&regionchilds.size()!=0){
            regionchildssize=regionchilds.size();
        %>
   
    <form name='DCMform' id='DCMform' action='<%=DCMFormAction%>' method='post'>
        <input type="hidden" name="<%=DCMInterfaceKey.INPUT_TEXT_REGION_ID%>" value="<%=regionId%>"/>
        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" align="center">
                   <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Parent Name</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Entity Level</font></td>

                    </tr>

               <%

                for(int i=0;i<regionchildssize;i++){
                    RegionModel regionParentReport = (RegionModel)regionchilds.get(i);
                %>
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=regionParentReport.getRegionName()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=regionParentReport.getRegionLevelTypeName()%></font></td>
                    </tr>
               <%}%>
                </table>
                <div align="center">
                <jsp:include page="pagingTable.jsp"  flush="true">
                                      <jsp:param   name="action_name_when_click_enter" value="<%=DCMInterfaceKey.ACTION_VIEW_REGION_CHILDS%>"/>
                                      <jsp:param   name="first_page_number" value="0"/>
                                      <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                                      <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


                </jsp:include>
                </div>
                </form>

                     <%}else{%>
                        <h3>This Entity Hasn't Childs</h3>
                    <%}%>
                    <br>
                    <input type=button value="Back" onClick="history.go(-1)">
                    <br>
        </div>
    </body>
</html>
