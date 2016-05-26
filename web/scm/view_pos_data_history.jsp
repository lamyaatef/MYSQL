<%-- 
    Document   : view_pos_data_history
    Created on : 6/12/2010, 15:18:51
    Author     : Ahmed Adel
--%>

<%@ page  import ="com.mobinil.sds.core.utilities.Utility"
          import = "java.util.*"
          import = "java.io.*"
          import = "javax.servlet.*"
          import = "javax.servlet.http.*"
          import = "javax.servlet.jsp.*"
          import = "java.io.PrintWriter"
          import = "java.io.IOException"
          import = "com.mobinil.sds.web.interfaces.scm.*"
          import = "com.mobinil.sds.core.system.fn.addfunction.model.*"
          import = "com.mobinil.sds.core.system.fn.importdata.model.*"
          import = "com.mobinil.sds.web.interfaces.*"
          import = "com.mobinil.sds.web.interfaces.fn.*"
          import = "com.mobinil.sds.web.interfaces.gn.querybuilder.*"
          import = "com.mobinil.sds.web.interfaces.dcm.*"
          import="com.mobinil.sds.core.system.dcm.pos.dao.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.*"
          import="com.mobinil.sds.core.system.dcm.genericModel.DAO.*"
          import="com.mobinil.sds.core.system.dcm.pos.model.*"
          import="com.mobinil.sds.core.system.dcm.region.model.*"
          import="com.mobinil.sds.core.system.request.model.*"
          import="com.mobinil.sds.core.system.request.dao.*"
          import="java.sql.Timestamp"
%>


<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="../resources/css/Template1.css">

    </head>
        <body>
        <center>
        <h2>POS History Master Data</h2>
        <br>
        <br>
        
<%
   HashMap dataHashMap=(HashMap)request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
   String posDetailId = request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_POS_ID);
    Vector <GeneralHistory> historicalData = (Vector)dataHashMap.get(SCMInterfaceKey.HISTORICAL_VECTOR);
    if (historicalData.size()==0)
      {%>
      <h1> There's No history for this POS   </h1>     
     <%  
}else{
%>





      <h2>POS Data</h2>
     <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                       <tr>
                           <td class=TableHeader nowrap align=center>POS Name</td>
                           <td class=TableHeader nowrap align=center>POS Email</td>
                           <td class=TableHeader nowrap align=center>POS Address</td>
                           <td class=TableHeader nowrap align=center>POS Arabic Address</td>
                           <td class=TableHeader nowrap align=center>POS Payment</td>
                           <td class=TableHeader nowrap align=center>Region</td>
                           <td class=TableHeader nowrap align=center>Governrate</td>
                           <td class=TableHeader nowrap align=center>City</td>
                           <td class=TableHeader nowrap align=center>District</td>
                           <td class=TableHeader nowrap align=center>Area</td>
                           <td class=TableHeader nowrap align=center>Demo Line</td>
                           <td class=TableHeader nowrap align=center>Updated In</td>
                           <td class=TableHeader nowrap align=center>Updated By</td>
                           <td class=TableHeader nowrap align=center>Doc Location</td>
                       </tr>
                  <%
                     for(int i = 0 ; i < historicalData.size() ; i++)
                     {
                         %>
                         <tr>
                             <% if(historicalData.get(i).getPosName()== null) historicalData.get(i).setPosName("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getPosName() %></td>
                           <% if(historicalData.get(i).getPosEmail() == null) historicalData.get(i).setPosEmail("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getPosEmail() %></td>
                           <% if(historicalData.get(i).getPOS_ARABIC_ADDRESS() == null) historicalData.get(i).setPosAddress("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getPOS_ARABIC_ADDRESS() %></td>
                           <% if(historicalData.get(i).getPosAddress() == null) historicalData.get(i).setPOS_ARABIC_ADDRESS("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getPosAddress() %></td>
                           <% if(historicalData.get(i).getPaymentLevelId()== null) historicalData.get(i).setPaymentLevelId(0);%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getPaymentTypeName(historicalData.get(i).getPaymentLevelId().toString())%>
                           <% if(historicalData.get(i).getRegionId()== null) historicalData.get(i).setRegionId(0);%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getRegionNameFromId(historicalData.get(i).getRegionId().toString())%></td>
                           <% if(historicalData.get(i).getPosGovernrate() == null) historicalData.get(i).setPosGovernrate(0);%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getRegionNameFromId(historicalData.get(i).getPosGovernrate().toString())%></td>
                           <% if(historicalData.get(i).getPosCity() == null) historicalData.get(i).setPosCity(0) ;%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getRegionNameFromId(historicalData.get(i).getPosCity().toString())%></td>
                           <% if(historicalData.get(i).getPosDistrict() == null) historicalData.get(i).setPosDistrict(0) ;%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getRegionNameFromId(historicalData.get(i).getPosDistrict().toString())%></td>
                           <% if(historicalData.get(i).getPosArea() == null) historicalData.get(i).setPosArea(0) ;%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getRegionNameFromId(historicalData.get(i).getPosArea().toString())%></td>
                           <% if(historicalData.get(i).getPosDemoLine() == null) historicalData.get(i).setPosDemoLine("") ;%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getPosDemoLine()%></td>
                           <% if(historicalData.get(i).getUpdatedIn() == null) historicalData.get(i).setUpdatedIn(Timestamp.valueOf(""));%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getUpdatedIn().toString()%></td>
                           <% if(historicalData.get(i).getUserId() == null) historicalData.get(i).setUserId("0");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getUserName(historicalData.get(i).getUserId().toString())%></td>
                           <% if(historicalData.get(i).getDocLocation() == null) historicalData.get(i).setDocLocation("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=(historicalData.get(i).getDocLocation())%></td>
                         </tr>
                         <%
                     }
                  %>
                  </table>
                
                  <h2>POS Manager Data</h2>
        <br>
         <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                       <tr>
                           <td class=TableHeader nowrap align=center>Manager Name</td>
                           <td class=TableHeader nowrap align=center>Manager Type</td>
                           <td class=TableHeader nowrap align=center>Manager Id Type</td>
                           <td class=TableHeader nowrap align=center>Manager BirthDate</td>
                           <td class=TableHeader nowrap align=center>Updated In</td>
                           <td class=TableHeader nowrap align=center>Updated By</td>
                       </tr>
                  <%
                     for(int i = 0 ; i < historicalData.size() ; i++)
                     {
                         %>
                         <tr>
                             <% if(historicalData.get(i).getManagerName() == null) historicalData.get(i).setManagerName("")  ;%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getManagerName() %></td>
                           <% if(historicalData.get(i).getManagerType() == null) historicalData.get(i).setManagerType(0);%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getIdType(historicalData.get(i).getManagerType().toString()) %></td>
                           <% if(historicalData.get(i).getManagerIdNumber() == null) historicalData.get(i).setManagerIdNumber("") ;%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getManagerIdNumber() %></td>
                           <% if(historicalData.get(i).getMangerBirthDate() == null)
                               {Date d =new Date(0000,00,00);
                               historicalData.get(i).setMangerBirthDate(d) ;
                               }%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getMangerBirthDate().toString()%></td>

                           <% if(historicalData.get(i).getUpdatedIn() == null) historicalData.get(i).setUpdatedIn(Timestamp.valueOf(""));%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getUpdatedIn().toString()%></td>

                           <% if(historicalData.get(i).getUserId() == null) historicalData.get(i).setUserId("0");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getUserName(historicalData.get(i).getUserId().toString())%></td>

                         </tr>
                         <%
                     }
                  %>
                  </table>
                          <h2>POS Owner Data</h2>
        <br>
         <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                       <tr>
                           <td class=TableHeader nowrap align=center>Owner Name</td>
                           <td class=TableHeader nowrap align=center>Owner Id Type</td>
                           <td class=TableHeader nowrap align=center>Owner Id Number</td>
                           <td class=TableHeader nowrap align=center>Owner BirthDate</td>
                           <td class=TableHeader nowrap align=center>Updated In</td>
                           <td class=TableHeader nowrap align=center>Updated By</td>
                       </tr>
                  <%
                     for(int i = 0 ; i < historicalData.size() ; i++)
                     {
                         %>
                         <tr>
                             <% if(historicalData.get(i).getOwnerName()  == null) historicalData.get(i).setOwnerName("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getOwnerName()%></td>
                           <% if(historicalData.get(i).getOwnerIdType()== null) historicalData.get(i).setOwnerIdType(0);%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getIdType(historicalData.get(i).getOwnerIdType().toString()) %></td>
                           <% if(historicalData.get(i).getOwnerIdNumber()== null) historicalData.get(i).setOwnerIdNumber("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getOwnerIdNumber() %></td>
                           <% if(historicalData.get(i).getOwnerBirthDate().toString() == null)
                               {Date d =new Date(0000,00,00);
                               historicalData.get(i).setOwnerBirthDate(d) ;
                               }%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getOwnerBirthDate().toString()%></td>
                            
                           <% if(historicalData.get(i).getUpdatedIn() == null) historicalData.get(i).setUpdatedIn(Timestamp.valueOf(""));%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getUpdatedIn().toString()%></td>

                           <% if(historicalData.get(i).getUserId() == null) historicalData.get(i).setUserId("0");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getUserName(historicalData.get(i).getUserId().toString())%></td>
                         </tr>
                         <%
                     }
                  %>
                  </table>
                    <h2>POS  Manager Phones Data</h2>
        <br>
         <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                       <tr>
                           <td class=TableHeader nowrap align=center>Phone Number</td>
                           <td class=TableHeader nowrap align=center>Updated In</td>
                           <td class=TableHeader nowrap align=center>Updated By</td>
                       </tr>
                  <%
                     for(int i = 0 ; i < historicalData.size() ; i++)
                     {
                         %>
                         <tr>
                             <% if(historicalData.get(i).getManagerPhone()== null) historicalData.get(i).setManagerPhone("");%>
                             <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getManagerPhone() %></td>
                              <% if(historicalData.get(i).getUpdatedIn() == null) historicalData.get(i).setUpdatedIn(Timestamp.valueOf(""));%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getUpdatedIn().toString()%></td>
                             <% if(historicalData.get(i).getUserId() == null) historicalData.get(i).setUserId("0");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getUserName(historicalData.get(i).getUserId().toString())%></td>
                         <%
                     }
                  %>
                  </tr>
         </table>
                          <h2>POS  Owner Phones Data</h2>
        <br>
         <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                       <tr>
                           <td class=TableHeader nowrap align=center>Phone Number</td>
                           <td class=TableHeader nowrap align=center>Updated In</td>
                           <td class=TableHeader nowrap align=center>Updated By</td>
                       </tr>
                 <%
                     for(int i = 0 ; i < historicalData.size() ; i++)
                     {
                         %>
                         <tr>
                             <% if(historicalData.get(i).getOwnerPhone()== null) historicalData.get(i).setOwnerPhone("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getOwnerPhone()%></td>
                             <% if(historicalData.get(i).getUpdatedIn() == null) historicalData.get(i).setUpdatedIn(Timestamp.valueOf(""));%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getUpdatedIn().toString()%></td>
                           <% if(historicalData.get(i).getUserId() == null) historicalData.get(i).setUserId("0");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getUserName(historicalData.get(i).getUserId().toString())%></td>
                         </tr>
                         <%
                     }
                  %>
                  </table>

            <h2>POS Phones Data</h2>
        <br>
         <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                       <tr>
                           <td class=TableHeader nowrap align=center>Phone Number</td>
                           <td class=TableHeader nowrap align=center>Updated In</td>
                           <td class=TableHeader nowrap align=center>Updated By</td>
                       </tr>
                <%
                     for(int i = 0 ; i < historicalData.size() ; i++)
                     {
                         %>
                         <tr>
                             <% if(historicalData.get(i).getPosPhone() == null) historicalData.get(i).setPosPhone("");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getPosPhone()%></td>
                             <% if(historicalData.get(i).getUpdatedIn() == null) historicalData.get(i).setUpdatedIn(Timestamp.valueOf(""));%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=historicalData.get(i).getUpdatedIn().toString()%></td>
                           <% if(historicalData.get(i).getUserId() == null) historicalData.get(i).setUserId("0");%>
                           <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=RequestDao.getUserName(historicalData.get(i).getUserId().toString())%></td>
                         </tr>
                          
                         <%
                     }
                  %>
                 </table>
                  
          <%}%>
           <br>
                  <input type="button" name="back" value="back" onclick="history.go(-1)">
                  </center>
                  </body>
</html>
