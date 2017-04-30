<%-- 
    Document   : rep_management
    Created on : Nov 2, 2010, 9:19:23 AM
    Author     : AHMED SAFWAT
--%>
<%@page import="com.mobinil.sds.core.system.scm.dao.RepManagementDAO"%>
<%@page contentType="text/html" pageEncoding="windows-1252"
        import="com.mobinil.sds.web.interfaces.*"
        import="com.mobinil.sds.web.interfaces.scm.*"
        import="com.mobinil.sds.core.system.scm.model.*"
        import="com.mobinil.sds.core.system.dcm.region.model.*"
        import="com.mobinil.sds.core.system.dcm.user.model.*"
        import="java.util.*"
        %>
<%
            HashMap dataHashMap = (HashMap) request.getAttribute(InterfaceKey.HASHMAP_KEY_DTO_OBJECT);
            String Slach = System.getProperty("file.separator");
            String base = request.getRealPath(Slach + "scm" + Slach + "upload" + Slach);
            Vector<RegionModel> regions=new Vector();
            Vector<DCMUserModel> searchResults=new Vector();
            Vector<DCMUserLevelTypeModel> repLevels=new Vector();
            String appName = request.getContextPath();
            String formName = "repManagement";
            String repformName="repForm";
            String userId = (String) dataHashMap.get(InterfaceKey.HASHMAP_KEY_USER_ID);
            regions=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_ALL_REGIONS);
            repLevels=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REP_LEVEL_TYPES);
            searchResults=(Vector)dataHashMap.get(SCMInterfaceKey.VECTOR_REP_SEARCH_RESULTS);
           
            request.setAttribute("search_vector", searchResults);
            request.getSession().setAttribute("search_vector", searchResults);
            
            dataHashMap.put(InterfaceKey.HASHMAP_KEY_REQUEST_FROM_SERVLET,request);
            //System.out.println("SEARCH RESULTS IN JSP : "+searchResults);
            String userLevelTypeId=(String)dataHashMap.get(SCMInterfaceKey.USER_LEVEL_TYPE_ID);
            String regionId=(String)dataHashMap.get(SCMInterfaceKey.REGION_ID);
            dataHashMap.put(SCMInterfaceKey.REGION_ID,regionId);
            System.out.println("RepManagement Page - regionId "+regionId);
            String searchName=(String)dataHashMap.get(SCMInterfaceKey.SEARCH_NAME);
            System.out.println("Name JSP -> "+searchName);
            
            request.setAttribute("search_name", searchName);
            request.getSession().setAttribute("search_name", searchName);
            
            userLevelTypeId=userLevelTypeId==null?"":userLevelTypeId;
            regionId=regionId==null?"":regionId;
            searchName=searchName==null?"":searchName;
            String distinationPage=(String)dataHashMap.get(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER);
            String totalPageNumbers=(String)dataHashMap.get(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <style>
                         span {
                          cursor: pointer;
                          display: inline-block;
                          margin: 0 10px;
                          font: bold;
                        }
                        span:hover {
                          border-bottom: 1px dotted black;
                        }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <LINK REL=STYLESHEET TYPE="text/css" HREF="<%=appName%>/resources/css/Template1.css">
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/validation.js" type="text/javascript"></SCRIPT>
        <SCRIPT language=JavaScript src="<%=appName%>/resources/js/sorttable.js" type="text/javascript"></SCRIPT>
<script src="../resources/js/jquery-1.11.3.js"></script>
        <title>Rep Management</title>

        <script language="JavaScript">
    
            $( document ).ready(function() {
           
                if($('#selectType').val()== "" /*&& $('#region_select').val()== "" && $('#name_select').val()== ""*/)
                    $('#export_row').hide();
                
                
              /*  $("#span1").click(function() {
                 console.log("send");
                    var recipient = "robsisneros@yahoo.com";
                   var recipient2 = "rsisneros@speakeasy.net";
                   var combine = ";"
                   var comrecip = recipient + combine + recipient2
                   var subject = "Gavilan Totals"
                   var msg = "Gavilan report is attached"
                   var attach = "c:\\BankWest\\GAVILAN_REPT.txt";
                   var recipient = $(this).text();
                   window.location.href = "mailto:" + recipient + "?subject=Mail to " + recipient + "&body=" + recipient + "&attachment=" +attach;

                });*/
                
                
                
            
});

            function openEmail(email,list,childEmails,userLevelType)
            {
               //var str = list.split("  ");
              if(userLevelType==6)
                  window.location.href = "mailto:" + email + "?subject=Mail to " + email;
              else
                  window.location.href = "mailto:" + email + "?subject=Mail to " + email + "&CC=" + childEmails +"&body=" + list;
                
            }

            function submitEditForm(dcmUserId,userLevelTypeId,regionId,region_name){
                
            document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_EDIT_REP_SUP%>";
            document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
            document.<%=formName%>.regionID.value=regionId;
            document.<%=formName%>.regionName.value=region_name;
            document.<%=formName%>.submit();

            }

            function DevChangePageActionWithSubmit(action){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value=action;
            document.<%=formName%>.submit();

            }
            
            function submitAddNewForm(){
            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_GEN_USER_SEARCH%>";
            document.<%=formName%>.submit();
            }
            function submitSearchForm(){

            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>";
            document.<%=formName%>.submit();

            }

            function confirmDelete(dcmUserId,userLevelTypeId){
                if(confirm('Are you sure you want to delete ?'))
                    {
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_DELETE_REP_SUP%>";
                        document.<%=formName%>.submit();
                    }
                else
                return;
            }
            function exportData(base,results)
            {
                var userType = document.getElementById("selectType").value;
                
                var regionSelected = document.getElementById("region_select").value;
                
                
                if(userType==null || userType=='null' || userType=='')
                    document.repManagement.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_USERS_FOR_REGION);%>'
                
                
                if(userType=='6')
                    document.repManagement.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_SALESREPS);%>'
                if(userType=='4')
                    document.<%=formName%>.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_SUPERVISORS);%>'
                if(userType=='5')
                    document.<%=formName%>.action= '<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet?<%out.print(InterfaceKey.HASHMAP_KEY_ACTION + "");%>='+'<%out.print(SCMInterfaceKey.ACTION_EXPORT_TEAMLEADERS);%>'
               
               
                document.repManagement.baseDirectory.value=base;
                document.repManagement.SearchResults.value=results;
                document.repManagement.region_select.value=regionSelected;
                
                document.repManagement.submit();
            }
            /*function exportUsers(results)
            {
                document.repManagement.SearchResults.value=results;
                document.repManagement.submit();
            }*/
            
            function change(noResults) {
            var export_but = document.getElementById("export_but");
                var export_row = document.getElementById("export_row");
            if(noResults=="false")                
            {
                $('#export_row').show();
                document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>";
                document.<%=formName%>.submit();
            }
            if(noResults=="true")        
                $('#export_row').hide();
                   /* export_but.style.display = "block";
                    export_row.style.display = "block";
                    export_but.style.visibility = 'visible';   
                    export_row.style.visibility = 'visible';   */
               
            }
            

            function viewDetail(dcmUserId,userLevelTypeId){
                        document.<%=formName%>.<%=SCMInterfaceKey.DCM_USER_ID%>.value=dcmUserId;
                        document.<%=formName%>.<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>.value=userLevelTypeId;
                        if(userLevelTypeId=="6")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_REP_DETAIL%>";
                        if(userLevelTypeId=="4")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_SUP_DETAIL%>";
                        if(userLevelTypeId=="5")
                            document.<%=formName%>.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value="<%=SCMInterfaceKey.ACTION_VIEW_TEAMLEAD_DETAIL%>";
                        document.<%=formName%>.submit();
            }
            

       </script>
    </head>
    <body>
             
                <div align="center">
            <br>
            <br>
            <h2>Rep Management</h2>
            <br>
            <br>

            <form action="<%=appName%>/servlet/com.mobinil.sds.web.controller.WebControllerServlet" name="<%=formName%>" method="post">
                
            <input type="hidden" name="baseDirectory" id="baseDirectory" value=""/>
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_ACTION%>" value="0">
            <input type="hidden" name="<%=InterfaceKey.HASHMAP_KEY_USER_ID%>" value="<%=userId%>">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_ID%>" value="-1">
            <input type="hidden" name="<%=SCMInterfaceKey.DCM_USER_LEVEL_TYPE_ID%>">
            <input type="hidden" id="SearchResults" name="<%=SCMInterfaceKey.VECTOR_REP_SEARCH_RESULTS%>" value="">
            <input type="hidden" id="regionID" name="regionID" value="">
            <input type="hidden" id="regionName" name="regionName" value="">

                <table style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                        
                    <tr class=TableTextNote>
                        <td nowrap align=center >Name</td>
                        <td colspan="4" align="center"><input type="text" id ="name_select" name="<%=SCMInterfaceKey.SEARCH_NAME%>" value="<%=searchName%>"></td>
                    </tr>
                    <tr class=TableTextNote>
                        <td align=center >Region</td>
                        <td align="center">
                    <select id ="region_select" name="<%=SCMInterfaceKey.REGION_ID%>">
                        <option value="">-----</option>
                    <%
                                if(regions!=null&&regions.size()!=0){
                                for (int i = 0; i < regions.size(); i++) {
                                    RegionModel region = (RegionModel) regions.get(i);
                    %>
                    <option value="<%=region.getRegionId()%>"
                                        <%
                                        if (regionId != null && regionId.equalsIgnoreCase(region.getRegionId())) {
                                            out.print("selected");
                                        }
                                        %>
                            ><%=region.getRegionName()%></option>
                    <%
                                }
                                }
                    %>
                    </select>

                        </td>

                        <td nowrap align=center >Level</td>
                        <td align="center">
                            <select id="selectType" name="<%=SCMInterfaceKey.USER_LEVEL_TYPE_ID%>" onchange="change('false')" >
                        <option value="">-----</option>
                    <%
                                if(repLevels!=null&&repLevels.size()!=0){
                                for (int i = 0; i < repLevels.size(); i++) {
                                    DCMUserLevelTypeModel level = (DCMUserLevelTypeModel) repLevels.get(i);
                    %>
                    <option value="<%=level.getUserLevelTypeId()%>"
                                <%
                                    if (userLevelTypeId != null && !userLevelTypeId.trim().equals("") && level.getUserLevelTypeId() == Integer.parseInt(userLevelTypeId)) {
                                        out.print("selected");
                                    }
                                %>

                            ><%=level.getUserLevelTypeName()%></option>
                    <%
                                }
                                }
                    %>
                    </select>

                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" align="center">
                            <input type="button" class="button" value="Search" onclick="submitSearchForm();">
                            &nbsp;
                            
                            <input type="hidden" class="button" value="Add New" onclick="submitAddNewForm();">
                            
                        </td>
                        
                    </tr>
                    <tr   id="export_row">        
                    <td colspan="6" align="center">
                       <input align="middle"  id="export_but" type="button"  class="button" name="Export"  value="Export List" onclick="exportData('<%=base%>','<%=searchResults%>');">
                         
                    </td>
                    </tr>
                    
                </table>


        <%
       if(searchResults!=null){
           //hide the export list button

           if(searchResults.size()==0){
%>
<script type="text/javascript"> change("true"); </script>
<%
              out.print("<font style='font-size: 11px;font-family: tahoma;line-height: 15px'>No Results based on your Search Criteria.</font>");

               }else{


        %>

        <br>
         
                <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=3 cellPadding=3 width="80%" border="1">
                    
                        
                    <tr>
                        <td class=TableHeader nowrap align=center >Name</td>
                        <td  class=TableHeader nowrap align=center>District / Image District</td>
                        <td class=TableHeader nowrap align=center>User Level Type</td>
                        <td class=TableHeader nowrap align=center>Edit</td>
                        <td class=TableHeader nowrap align=center>Delete</td>
                        <td class=TableHeader nowrap align=center>Email</td>
                    </tr>


                    <%

                                for (int i = 0; i < searchResults.size(); i++) {
                                    DCMUserModel rep = (DCMUserModel) searchResults.get(i);
                                    StringBuffer childRepsWithEmailRegion = new StringBuffer("");
                                    StringBuffer childRepsWithEmails = new StringBuffer("");
                                    if (rep.getUserLevelTypeId().compareTo("6")!=0) //if not a salesrep get the children emails and regions
                                    {
                                        childRepsWithEmailRegion = RepManagementDAO.getRespsRegionsAndEmails(rep.getUserFullName(),rep.getUserLevelTypeId());
                                        childRepsWithEmails = RepManagementDAO.getChildRespsEmails(rep.getUserFullName(),rep.getUserLevelTypeId());
                                    }
                                    
                                   
                    %>              


                    <tr>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><a href="javascript:viewDetail(<%=rep.getDcmUserId()%>,<%=rep.getUserLevelTypeId()%>);"><%=rep.getUserFullName()%></a></td>
                        <td align="center"  style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=rep.getRegionName() %></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=rep.getUserLevelTypeName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Edit" onclick="submitEditForm(<%=rep.getDcmUserId()%>,<%=rep.getUserLevelTypeId()%>,<%=rep.getRegionId()%>,'<%=rep.getRegionName()%>');"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input type="button" class="button" value="Delete" onclick="confirmDelete(<%=rep.getDcmUserId()%>,<%=rep.getUserLevelTypeId()%>);"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><span id="span1" onclick="openEmail('<%=rep.getUserEmail()%>','<%=childRepsWithEmailRegion%>','<%=childRepsWithEmails%>',<%=rep.getUserLevelTypeId()%>);"><%=rep.getUserEmail()%></span></td>
                   
                    </tr>

                    <%
                                }

                    %>
                </table>




 <jsp:include page="pagingTable.jsp"  flush="true">
                                      <jsp:param   name="action_name_when_click_enter" value="<%=SCMInterfaceKey.ACTION_SEARCH_REP%>"/>
                                      <jsp:param   name="first_page_number" value="0"/>
                                      <jsp:param   name="string_of_total_page_number" value="<%=totalPageNumbers%>"/>
                                      <jsp:param   name="control_text_page_number" value="<%=distinationPage%>"/>


</jsp:include>

                <%
                        }
           }
                %>
            </form>
                </div>
    </body>
</html>
