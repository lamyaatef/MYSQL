    
<%@page import="org.primefaces.json.JSONObject"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.mobinil.sds.core.utilities.DBUtil"%>
<%@page import="com.mobinil.sds.core.system.request.dao.RequestDao"%>


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
          import="com.mobinil.sds.web.interfaces.sa.*"
          %>



<%
    

String appName = request.getContextPath();

String formAction = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_REGIONS; //action=
String formAction3 = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_POS_SERCH;

String formAction4 = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.SEARCH_POS_EXCEL;

String formAction5 = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_POS_DATA_EDIT;

String formAction6 = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_POS_DATA_VIEW_HISTORY;

String formAction7 = appName +"/servlet/com.mobinil.sds.web.controller.WebControllerServlet?"
                    +InterfaceKey.HASHMAP_KEY_ACTION+"="
                    +SCMInterfaceKey.ACTION_SHOW_DETAIL_POS_DATA_MANAGEMENT;

%>

     <script>
    
        function pulldownChanged(){
            
      
    }
        function DevChangePageActionWithSubmit(action)
        {

            document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value =  document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value =action
            document.formPosMangement.submit();
        }


        function Download()
        {

            document.getElementById("download").disabled=true;
            document.GenerateSheet.submit();

        }
    
    function Sheet()
    {
        document.GenerateSheet.Submit.disabled=true;
        document.GenerateSheet.submit();


    }
    function exportExcel()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.IMPORT_TEMPLATE_DATES_FOR_POS%>';
        document.getElementById("tempDown").disabled=true;
        document.formPosMangement.submit();
    }
    function preRequestDataEntryWithoutStk()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_POS_DATA_ENTRY%>';
        document.formPosMangement.submit();
    }
    function preRequestDataEntryWithStk()
    {

        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_POS_DATA_ENTRY_WITH_STK%>';
        document.formPosMangement.submit();
    }

    function detailRequest(id)
    {
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>.value  = id;
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_SHOW_DETAIL_POS_DATA_MANAGEMENT%>';
    $("#formPosMangement").attr("<%=InterfaceKey.HASHMAP_KEY_ACTION%>","<%out.print(formAction7);%>");    
    document.formPosMangement.submit();
    }


    function editRequest(id,repid,teamid,superid)
    {
        
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>.value  = id;
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_SALESREP_NAME%>.value  = repid;
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_NAME%>.value  = teamid;
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME%>.value  = superid;
        /*document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_REGION%>.value  = regionid;*/
                   
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SCMInterfaceKey.ACTION_POS_DATA_EDIT%>';
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_USER_ID%>.value;
        $("#formPosMangement").attr("<%=InterfaceKey.HASHMAP_KEY_ACTION%>","<%out.print(formAction5);%>");
        document.formPosMangement.submit();
    }
    function searchRequest()
    {
        

        if(eval("document.formPosMangement.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value") == "")
        {
            alert("Please Enter POS Code ..");
        }
        else
        {
            
            document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_SEARCH_POS_DATA_MANAGEMENT%>';
            document.formPosMangement.submit();
        }
    
        
    }
    function buildActionStr (){
        

        
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.SEARCH_POS_EXCEL%>';
        //console.log("parameter action : ",document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value);
        $("#formPosMangement").attr("<%=InterfaceKey.HASHMAP_KEY_ACTION%>","<%out.print(formAction4);%>");
       // console.log("form action new : ",$("#formPosMangement").attr("<%=InterfaceKey.HASHMAP_KEY_ACTION%>"));
        document.formPosMangement.submit();
       
    }

    function searchExcel()
    {
        if(eval("document.formDataView.<%=SCMInterfaceKey.CONTROL_TEXT_POS_CODE%>.value") == "")
        {
            alert("Please Enter POS Code ..");
        }
        else
        {
            document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.SEARCH_POS_EXCEL%>';
            document.formPosMangement.submit();
        }
    

        
        
    }
    
    function viewHistory(id)
    {
        document.formPosMangement.<%=SCMInterfaceKey.INPUT_HIDDEN_POS_ID%>.value  = id;
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value = '<%=SCMInterfaceKey.ACTION_POS_DATA_VIEW_HISTORY%>';
        $("#formPosMangement").attr("<%=InterfaceKey.HASHMAP_KEY_ACTION%>","<%out.print(formAction6);%>");
        document.formPosMangement.submit();
    }
</script>


        <%!
    private static void loadListsPosManagement(Connection con, HashMap dataHashMap, HttpServletRequest request, boolean returnData) {
        if (returnData) {
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE, request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE));
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS, request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS));
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE, request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE));
            dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION, request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION));
            dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS, request.getParameter(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS));
            dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS, request.getParameter(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS));
            dataHashMap.put(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS, request.getParameter(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS));
        }

        dataHashMap.put(SCMInterfaceKey.HASHMAP_GEN_DCM, RequestDao.getLookupFields(con, "GEN_DCM_STATUS", "DCM_STATUS_ID, DCM_STATUS_NAME"));
        dataHashMap.put(SCMInterfaceKey.HASHMAP_STK_STATUS, RequestDao.getLookupFields(con, "SCM_STK_STATUS where STK_STATUS_ID not in (1,3,6,5)", "STK_STATUS_ID, NAME "));
        dataHashMap.put(SCMInterfaceKey.HASHMAP_CAM_PAY_STATUS, RequestDao.getLookupFields(con, "CAM_PAYMENT_CAM_STATE", "ID, CAM_STATUS_FOR_PAYMENT"));

    }
        %>
                <%
    //////////////////////////////lamya//////////////////////////////////
    HashMap<String,Object> dataHashMap = new HashMap(100);
    Connection con = Utility.getConnection();
   String userDataId = (String) request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_USER_ID/*InterfaceKey.HASHMAP_KEY_USER_ID*/);
    String userDataName = (String) request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_SUPERVISOR_NAME/*InterfaceKey.HASHMAP_KEY_USER_ID*/);
    String teamleaderId = (String) request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_ID/*InterfaceKey.HASHMAP_KEY_USER_ID*/);
    String teamleaderName = (String) request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_TEAMLEADER_NAME/*InterfaceKey.HASHMAP_KEY_USER_ID*/);
    String salesrepId = (String) request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_ID/*InterfaceKey.HASHMAP_KEY_USER_ID*/);
    String salesrepName = (String) request.getParameter(SCMInterfaceKey.INPUT_HIDDEN_SALESREP_NAME/*InterfaceKey.HASHMAP_KEY_USER_ID*/);
   
            
                     String destinationPage = (String) request.getParameter(SCMInterfaceKey.DESTINATION_PAGE);
                     System.out.println("DESTINATION PAGE : "+destinationPage);
                    if (userDataId == null) {
                        userDataId = "";
                    }
                     if (destinationPage == null) {
                        destinationPage = "0";
                    }
                    Vector regions = new Vector();
                    Vector IDTypeVector = new Vector();
                    GenericModel IDTypeModel = new GenericModel();
                    GenericModelDAO gmDAO = new GenericModelDAO();
                    IDTypeModel = gmDAO.getColumns(con, "DCM_ID_TYPE");
                    IDTypeVector = gmDAO.getModels(con, IDTypeModel);
                    regions = RequestDao.getAllRegionDataList(con);

                    dataHashMap.put(SCMInterfaceKey.VECTOR_ID_TYPE, IDTypeVector);
                    dataHashMap.put(SCMInterfaceKey.VECTOR_REGIONS, regions);
                    dataHashMap.put(SCMInterfaceKey.DOC_VECTOR, RequestDao.getDocList(con));

//String alert = (String) request.getParameter("alert");
//String distinationPage = (String) request.getParameter("distinationPage");
    //String totalPageNumbers = (String) request.getParameter("totalPageNumbers");
    
                    String teamleadId = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_TEAMLEADER);
                    String superId = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_SUPERVISOR);
                    String repId = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_SALESREP);
                    System.out.println("jsp: supervisor: "+superId+" teamleader: "+teamleadId+" rep: "+repId);
                    
                    
                    String posDataName = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_NAME);
                    String posDataCode = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_CODE);
                    String posDataRegion = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);
                    String posDataGover = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER);
                    String posDataDistrict = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT);
                    String posDataArea = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_AREA);
                    String posDataCity = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_CITY);
                    String posDataOwnerName = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME);
                    String posDataOwnerIdNum = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER);
                    String posDataOwnerIdType = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE);
                    String posDataManagerName = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME);
                    String posDataManagerIdNum = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER);
                    String posDataManagerIdType = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE);
                    String posDataProposedDoc = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC);
                    String posDataDocNum = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM);
                    String posDataStkNum = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL);
                    String Level = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL);
                    String Payment = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL);
                    String PaymentM = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD);
                    String Channel = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL);
                    String entryDate = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENTRY_DATE);
                    String englishAddress = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_ENGLISH_ADDRESS);
                    String posPhone = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_POS_PHONE);
                    String docLocation = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_INPUT_DOC_LOCATION);
                    String posStatusId = (String) request.getParameter(SCMInterfaceKey.CONTROL_SELECT_POS_STATUS);
                    String stkStatusId = (String) request.getParameter(SCMInterfaceKey.CONTROL_SELECT_STK_STATUS);
                    String psymentStatusId = (String) request.getParameter(SCMInterfaceKey.CONTROL_SELECT_PAYMENT_STATUS);
                    
                    if (posDataRegion==null || posDataRegion.compareTo("--")==0)
                        posDataRegion="";
                    if (posDataGover==null || posDataGover.compareTo("--")==0)
                        posDataGover="";
                    if(posDataDistrict==null || posDataDistrict.compareTo("--")==0)
                        posDataDistrict="";        
                    if(posDataArea==null || posDataArea.compareTo("--")==0)
                        posDataArea="";
                    if(posDataCity==null || posDataCity.compareTo("--")==0)
                       posDataCity=""; 

//                    Integer totalSearch=RequestDao.searchPosDataTotal(con ,posDataOwnerIdType.trim() , posDataDocNum.trim() , posDataManagerName.trim() , posDataStkNum.trim() , posDataManagerIdType.trim() , posDataProposedDoc.trim() , posDataManagerIdNum.trim() , posDataName.trim() , posDataCode.trim() , posDataRegion.trim() , posDataGover.trim() , posDataDistrict.trim() , posDataArea.trim() ,posDataCity.trim() , posDataOwnerName.trim() ,posDataOwnerIdNum.trim(),Level,Payment,Channel);
                    System.out.println("posDataGover "+posDataGover);
                    
                    Vector<POSDetailModel> dataVec = RequestDao.searchPosData(con, 
                            posDataOwnerIdType.trim(), 
                            posDataDocNum.trim(), 
                            posDataManagerName.trim(), 
                            posDataStkNum.trim(), 
                            posDataManagerIdType.trim(), 
                            posDataProposedDoc.trim(), 
                            posDataManagerIdNum.trim(), 
                            posDataName.trim(), 
                            posDataCode.trim(), 
                            posDataRegion.trim(), 
                            posDataGover.trim(), 
                            posDataDistrict.trim(), 
                            posDataArea.trim(), 
                            posDataCity.trim(), 
                            posDataOwnerName.trim(), 
                            posDataOwnerIdNum.trim(),
                            superId.trim(),
                            teamleadId.trim(),
                            repId.trim(),
                            destinationPage, 
                            Level, 
                            Payment, 
                            Channel,
                            posStatusId, stkStatusId, psymentStatusId, posPhone, englishAddress, entryDate, docLocation);
                   
                    System.out.println(" data vec size = "+ dataVec.size());
                    
                    Integer totalSearch = ((POSDetailModel) dataVec.lastElement()).getPageCount();
                    
                    
                    dataVec.removeElementAt(dataVec.size() - 1);
                    totalSearch = totalSearch / 20;
                    totalSearch = totalSearch == 0 ? 1 : totalSearch;

                    if (dataVec.size() == 0 || dataVec == null) {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "No Data Found ...");
                    } else {
                        dataHashMap.put(SCMInterfaceKey.REP_KIT_Alert, "");
                    }
                    dataHashMap.put(SCMInterfaceKey.SIMILAR_POS_LIST, dataVec);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_NAME, posDataName);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CODE, posDataCode);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_REGION, posDataRegion);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_GOVER, posDataGover);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DISTRICT, posDataDistrict);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_AREA, posDataArea);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CITY, posDataCity);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_NAME, posDataOwnerName);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_NUMBER, posDataOwnerIdNum);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_OWNER_ID_TYPE, posDataOwnerIdType);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_NAME, posDataManagerName);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_NUMBER, posDataManagerIdNum);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_MANAGER_ID_TYPE, posDataManagerIdType);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_PROPOSED_DOC, posDataProposedDoc);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_DOC_NUM, posDataDocNum);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_STK_DIAL, posDataStkNum);
                    dataHashMap.put(SCMInterfaceKey.INPUT_CONTROL_PAGE_NUMBER, destinationPage);
                    dataHashMap.put(SCMInterfaceKey.STRING_OF_TOTAL_PAGE_NUMBER, totalSearch.toString());
                    dataHashMap.put(SCMInterfaceKey.CHANNEL_VECTOR, RequestDao.getChannelList(con));
                    dataHashMap.put(SCMInterfaceKey.LEVEL_VECTOR, RequestDao.getLevelList(con));
                    dataHashMap.put(SCMInterfaceKey.PAYMENT_LEVEL_VECTOR, RequestDao.getPaymentList(con));
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_LEVEL, Level);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_LEVEL, Payment);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_PAYMENT_METHOD, PaymentM);
                    dataHashMap.put(SCMInterfaceKey.CONTROL_TEXT_POS_CHANNEL, Channel);
                    loadListsPosManagement(con, dataHashMap, request, true);
    
    
    
    
               /*  JSONObject js = new JSONObject();
                 js.put("dataHashMap", dataHashMap);
                 response.setContentType("text/x-json;charset=UTF-8");           
                 response.setHeader("Cache-Control", "no-cache");
                 response.setContentType("application/json");
                 out.println(js);
*/
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////end - lamya////////////////////////////////////////////////////
                Vector<POSDetailModel> posDataVec = (Vector<POSDetailModel>) dataHashMap.get(SCMInterfaceKey.SIMILAR_POS_LIST);
                    if (posDataVec.size() > 0) {
                %>
                
                <table class="sortable" style="BORDER-COLLAPSE: collapse" cellSpacing=2 cellPadding=1 width="90%" border="1">
                    <tr >
                        <td class=TableHeader nowrap align=center>POS Name</td>
                        <td class=TableHeader nowrap align=center>POS Code</td>
                        <td class=TableHeader nowrap align=center>POS Address</td>
                        <td class=TableHeader nowrap align=center>Owner Name</td>
                        <td class=TableHeader nowrap align=center>Manager Name</td>
                        <td class=TableHeader nowrap align=center>Details</td>
                        <td class=TableHeader nowrap align=center>Edit</td>
                        <td class=TableHeader nowrap align=center>View History</td>
                        <td class=TableHeader nowrap align=center>Status</td>
                    </tr>
                    <%
                        for (int i = 0; i < posDataVec.size(); i++) {
                    %>
                    <tr>
                    
                        <% if (posDataVec.get(i).getPosName() == null) {
                                posDataVec.get(i).setPosName("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosName()%></td>
                        <% if (posDataVec.get(i).getPOSCode() == null) {
                                posDataVec.get(i).setPOSCode("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPOSCode()%></td>
                        <% if (posDataVec.get(i).getPosAddress() == null) {
                                posDataVec.get(i).setPosAddress("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosAddress()%></td>
                        <% if (posDataVec.get(i).getPosOwnerName() == null) {
                                posDataVec.get(i).setPosOwnerName("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosOwnerName()%></td>
                        <% if (posDataVec.get(i).getPosManagerName() == null) {
                                posDataVec.get(i).setPosManagerName("");
                            }%>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosManagerName()%></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><input class=button  type="button"  value="Details" onclick="detailRequest(<%=posDataVec.get(i).getPosID()%>)"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"> 
                            <% if (posDataVec.get(i).getPosStatusName().contains("Stop")) {%>

                            <%--    <input class=button  type="button"  value="Edit" disabled readonly> --%>
                            <%} else { posDataRegion = (String) request.getParameter(SCMInterfaceKey.CONTROL_TEXT_POS_REGION);System.out.println("pos regionnnn : "+posDataRegion);%>
                            <input class=button  type="button"  value="Edit" onclick="editRequest(<%=posDataVec.get(i).getPosID()%>,<%=posDataVec.get(i).getSalesrepName()%>,<%=posDataVec.get(i).getTeamleaderName()%>,<%=posDataVec.get(i).getSupervisorName()%>)">
                            <%}%>                        </td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"> <input class=button  type="button"  value="View History" onclick="viewHistory((<%=posDataVec.get(i).getPosID()%>))"></td>
                        <td align="center" style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=posDataVec.get(i).getPosStatusName()%></td>

                    </tr>
                    <%
                        }
                    %>
                </table>
                <div align="center">
                    <jsp:include page="pagingTable.jsp"  flush="true" >
                        <jsp:param   name="action_name_when_click_enter" value="search_pos_data_management"/>
                        <jsp:param   name="first_page_number" value="0"/>
                        <jsp:param   name="string_of_total_page_number" value="<%=totalSearch.toString()%>"/>
                        <jsp:param   name="control_text_page_number" value="<%=destinationPage%>"/>


                    </jsp:include>
                </div>
                <%
                    }
                %>

                <br>
                <center><font color=red style="font-size: 11px;font-family: tahoma;line-height: 15px"><%=dataHashMap.get(SCMInterfaceKey.REP_KIT_Alert)%></font></center>
                <br>
                <br>

                <table>
                    <tr>
                        <td>
                            <input class=button  type="button"  value="Create Data Entry " onclick="preRequestDataEntryWithoutStk();">
                        </td>
                        <td>
                            <input class=button  type="button"  value="Create Data Entry and STK " onclick="preRequestDataEntryWithStk()">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input class=button id="tempDown" type="button"  value="Generate Excel Template " onclick="exportExcel()">
                        </td>

                    </tr>
                </table>
                
                <br><br><br><br>

<script>
    function exportExcel()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.IMPORT_TEMPLATE_DATES_FOR_POS%>';
        document.getElementById("tempDown").disabled=true;
        document.formPosMangement.submit();
    }
    function preRequestDataEntryWithoutStk()
    {
        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_POS_DATA_ENTRY%>';
        document.formPosMangement.submit();
    }
    function preRequestDataEntryWithStk()
    {

        document.formPosMangement.<%=InterfaceKey.HASHMAP_KEY_ACTION%>.value  = '<%=SCMInterfaceKey.ACTION_POS_DATA_ENTRY_WITH_STK%>';
        document.formPosMangement.submit();
    }

    </script>