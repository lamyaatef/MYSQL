<%-- 
    Document   : view_region_parent
    Created on : 22/11/2010, 14:26:26
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
        %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div align="center">
        <br>
        <br>
        <h2 align="center"><%=regionName%>'s Parents</h2>
        <br>
        <br>

        <%
            Vector <RegionModel> regionParents=(Vector)dataHashMap.get(DCMInterfaceKey.VECTOR_REGION_PARENTS);
            int regionParentssize=0;
            if(regionParents!=null&&regionParents.size()!=0){
            regionParentssize=regionParents.size();
        %>

        <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1" align="center">
                   <tr>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Parent Name</font></td>
                        <td class=TableHeader nowrap align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px">Entity Level</font></td>
                        
                    </tr>

               <%

                for(int i=0;i<regionParentssize;i++){
                    RegionModel regionParentReport = (RegionModel)regionParents.get(i);
                %>
                    <tr>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=regionParentReport.getRegionName()%></font></td>
                        <td align=center ><font style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=regionParentReport.getRegionLevelTypeName()%></font></td>
                    </tr>
                <%}%>
                </table>
                <br>
                <%}
                else{%>
                        <h3>This Entity Hasn't Parent</h3>
                    <%}%>
                    <input type=button value="Back" onClick="history.go(-1)">
        </div>
    </body>
</html>
